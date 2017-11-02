package org.apache.xerces.impl.xs.traversers;

import java.util.Vector;
import org.apache.xerces.impl.dv.DatatypeException;
import org.apache.xerces.impl.dv.InvalidDatatypeFacetException;
import org.apache.xerces.impl.dv.SchemaDVFactory;
import org.apache.xerces.impl.dv.XSSimpleType;
import org.apache.xerces.impl.dv.xs.SchemaDVFactoryImpl;
import org.apache.xerces.impl.dv.xs.XSSimpleTypeDecl;
import org.apache.xerces.impl.validation.ValidationState;
import org.apache.xerces.impl.xs.SchemaGrammar;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.apache.xerces.impl.xs.XSAnnotationImpl;
import org.apache.xerces.impl.xs.util.XInt;
import org.apache.xerces.impl.xs.util.XSObjectListImpl;
import org.apache.xerces.util.DOMUtil;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xs.XSObject;
import org.apache.xerces.xs.XSObjectList;
import org.apache.xerces.xs.XSSimpleTypeDefinition;
import org.apache.xerces.xs.XSTypeDefinition;
import org.w3c.dom.Element;

class XSDSimpleTypeTraverser
  extends XSDAbstractTraverser
{
  private final SchemaDVFactory schemaFactory = SchemaDVFactory.getInstance();
  private boolean fIsBuiltIn = false;
  
  XSDSimpleTypeTraverser(XSDHandler paramXSDHandler, XSAttributeChecker paramXSAttributeChecker)
  {
    super(paramXSDHandler, paramXSAttributeChecker);
    if ((schemaFactory instanceof SchemaDVFactoryImpl)) {
      ((SchemaDVFactoryImpl)schemaFactory).setDeclPool(fDeclPool);
    }
  }
  
  XSSimpleType traverseGlobal(Element paramElement, XSDocumentInfo paramXSDocumentInfo, SchemaGrammar paramSchemaGrammar)
  {
    Object[] arrayOfObject = fAttrChecker.checkAttributes(paramElement, true, paramXSDocumentInfo);
    String str = (String)arrayOfObject[XSAttributeChecker.ATTIDX_NAME];
    XSSimpleType localXSSimpleType = traverseSimpleTypeDecl(paramElement, arrayOfObject, paramXSDocumentInfo, paramSchemaGrammar);
    fAttrChecker.returnAttrArray(arrayOfObject, paramXSDocumentInfo);
    if (str == null)
    {
      reportSchemaError("s4s-att-must-appear", new Object[] { SchemaSymbols.ELT_SIMPLETYPE, SchemaSymbols.ATT_NAME }, paramElement);
      localXSSimpleType = null;
    }
    if (localXSSimpleType != null) {
      paramSchemaGrammar.addGlobalTypeDecl(localXSSimpleType);
    }
    return localXSSimpleType;
  }
  
  XSSimpleType traverseLocal(Element paramElement, XSDocumentInfo paramXSDocumentInfo, SchemaGrammar paramSchemaGrammar)
  {
    Object[] arrayOfObject = fAttrChecker.checkAttributes(paramElement, false, paramXSDocumentInfo);
    String str = genAnonTypeName(paramElement);
    XSSimpleType localXSSimpleType = getSimpleType(str, paramElement, arrayOfObject, paramXSDocumentInfo, paramSchemaGrammar);
    if ((localXSSimpleType instanceof XSSimpleTypeDecl)) {
      ((XSSimpleTypeDecl)localXSSimpleType).setAnonymous(true);
    }
    fAttrChecker.returnAttrArray(arrayOfObject, paramXSDocumentInfo);
    return localXSSimpleType;
  }
  
  private XSSimpleType traverseSimpleTypeDecl(Element paramElement, Object[] paramArrayOfObject, XSDocumentInfo paramXSDocumentInfo, SchemaGrammar paramSchemaGrammar)
  {
    String str = (String)paramArrayOfObject[XSAttributeChecker.ATTIDX_NAME];
    return getSimpleType(str, paramElement, paramArrayOfObject, paramXSDocumentInfo, paramSchemaGrammar);
  }
  
  private String genAnonTypeName(Element paramElement)
  {
    StringBuffer localStringBuffer = new StringBuffer("#AnonType_");
    for (Element localElement = DOMUtil.getParent(paramElement); (localElement != null) && (localElement != DOMUtil.getRoot(DOMUtil.getDocument(localElement))); localElement = DOMUtil.getParent(localElement)) {
      localStringBuffer.append(localElement.getAttribute(SchemaSymbols.ATT_NAME));
    }
    return localStringBuffer.toString();
  }
  
  private XSSimpleType getSimpleType(String paramString, Element paramElement, Object[] paramArrayOfObject, XSDocumentInfo paramXSDocumentInfo, SchemaGrammar paramSchemaGrammar)
  {
    XInt localXInt = (XInt)paramArrayOfObject[XSAttributeChecker.ATTIDX_FINAL];
    int i = localXInt == null ? fFinalDefault : localXInt.intValue();
    Element localElement1 = DOMUtil.getFirstChildElement(paramElement);
    Object localObject1 = null;
    if ((localElement1 != null) && (DOMUtil.getLocalName(localElement1).equals(SchemaSymbols.ELT_ANNOTATION)))
    {
      localObject2 = traverseAnnotationDecl(localElement1, paramArrayOfObject, false, paramXSDocumentInfo);
      if (localObject2 != null) {
        localObject1 = new XSAnnotationImpl[] { localObject2 };
      }
      localElement1 = DOMUtil.getNextSiblingElement(localElement1);
    }
    else
    {
      localObject2 = DOMUtil.getSyntheticAnnotation(paramElement);
      if (localObject2 != null)
      {
        XSAnnotationImpl localXSAnnotationImpl = traverseSyntheticAnnotation(paramElement, (String)localObject2, paramArrayOfObject, false, paramXSDocumentInfo);
        localObject1 = new XSAnnotationImpl[] { localXSAnnotationImpl };
      }
    }
    if (localElement1 == null)
    {
      reportSchemaError("s4s-elt-must-match.2", new Object[] { SchemaSymbols.ELT_SIMPLETYPE, "(annotation?, (restriction | list | union))" }, paramElement);
      return errorType(paramString, fTargetNamespace, (short)2);
    }
    Object localObject2 = DOMUtil.getLocalName(localElement1);
    short s = 2;
    int j = 0;
    int k = 0;
    int m = 0;
    if (((String)localObject2).equals(SchemaSymbols.ELT_RESTRICTION))
    {
      s = 2;
      j = 1;
    }
    else if (((String)localObject2).equals(SchemaSymbols.ELT_LIST))
    {
      s = 16;
      k = 1;
    }
    else if (((String)localObject2).equals(SchemaSymbols.ELT_UNION))
    {
      s = 8;
      m = 1;
    }
    else
    {
      reportSchemaError("s4s-elt-must-match.1", new Object[] { SchemaSymbols.ELT_SIMPLETYPE, "(annotation?, (restriction | list | union))", localObject2 }, paramElement);
      return errorType(paramString, fTargetNamespace, (short)2);
    }
    Element localElement2 = DOMUtil.getNextSiblingElement(localElement1);
    if (localElement2 != null) {
      reportSchemaError("s4s-elt-must-match.1", new Object[] { SchemaSymbols.ELT_SIMPLETYPE, "(annotation?, (restriction | list | union))", DOMUtil.getLocalName(localElement2) }, localElement2);
    }
    Object[] arrayOfObject = fAttrChecker.checkAttributes(localElement1, false, paramXSDocumentInfo);
    QName localQName = (QName)arrayOfObject[XSAttributeChecker.ATTIDX_ITEMTYPE];
    Vector localVector = (Vector)arrayOfObject[XSAttributeChecker.ATTIDX_MEMBERTYPES];
    Element localElement3 = DOMUtil.getFirstChildElement(localElement1);
    if ((localElement3 != null) && (DOMUtil.getLocalName(localElement3).equals(SchemaSymbols.ELT_ANNOTATION)))
    {
      localObject3 = traverseAnnotationDecl(localElement3, arrayOfObject, false, paramXSDocumentInfo);
      if (localObject3 != null) {
        if (localObject1 == null)
        {
          localObject1 = new XSAnnotationImpl[] { localObject3 };
        }
        else
        {
          localObject4 = new XSAnnotationImpl[2];
          localObject4[0] = localObject1[0];
          localObject1 = localObject4;
          localObject1[1] = localObject3;
        }
      }
      localElement3 = DOMUtil.getNextSiblingElement(localElement3);
    }
    else
    {
      localObject3 = DOMUtil.getSyntheticAnnotation(localElement1);
      if (localObject3 != null)
      {
        localObject4 = traverseSyntheticAnnotation(localElement1, (String)localObject3, arrayOfObject, false, paramXSDocumentInfo);
        if (localObject1 == null)
        {
          localObject1 = new XSAnnotationImpl[] { localObject4 };
        }
        else
        {
          localObject5 = new XSAnnotationImpl[2];
          localObject5[0] = localObject1[0];
          localObject1 = localObject5;
          localObject1[1] = localObject4;
        }
      }
    }
    Object localObject3 = null;
    if (((j != 0) || (k != 0)) && (localQName != null))
    {
      localObject3 = findDTValidator(localElement1, paramString, localQName, s, paramXSDocumentInfo);
      if ((localObject3 == null) && (fIsBuiltIn))
      {
        fIsBuiltIn = false;
        return null;
      }
    }
    Object localObject4 = null;
    Object localObject5 = null;
    int i1;
    XSObjectList localXSObjectList;
    if ((m != 0) && (localVector != null) && (localVector.size() > 0))
    {
      n = localVector.size();
      localObject4 = new Vector(n, 2);
      for (i1 = 0; i1 < n; i1++)
      {
        localObject5 = findDTValidator(localElement1, paramString, (QName)localVector.elementAt(i1), (short)8, paramXSDocumentInfo);
        if (localObject5 != null) {
          if (((XSSimpleTypeDefinition)localObject5).getVariety() == 3)
          {
            localXSObjectList = ((XSSimpleTypeDefinition)localObject5).getMemberTypes();
            for (int i2 = 0; i2 < localXSObjectList.getLength(); i2++) {
              ((Vector)localObject4).addElement(localXSObjectList.item(i2));
            }
          }
          else
          {
            ((Vector)localObject4).addElement(localObject5);
          }
        }
      }
    }
    int n = 0;
    if ((localElement3 != null) && (DOMUtil.getLocalName(localElement3).equals(SchemaSymbols.ELT_SIMPLETYPE)))
    {
      if ((j != 0) || (k != 0))
      {
        if (localQName != null) {
          reportSchemaError(k != 0 ? "src-simple-type.3.a" : "src-simple-type.2.a", null, localElement3);
        } else {
          localObject3 = traverseLocal(localElement3, paramXSDocumentInfo, paramSchemaGrammar);
        }
        localElement3 = DOMUtil.getNextSiblingElement(localElement3);
      }
      else if (m != 0)
      {
        if (localObject4 == null) {
          localObject4 = new Vector(2, 2);
        }
        do
        {
          localObject5 = traverseLocal(localElement3, paramXSDocumentInfo, paramSchemaGrammar);
          if (localObject5 != null) {
            if (((XSSimpleTypeDefinition)localObject5).getVariety() == 3)
            {
              localXSObjectList = ((XSSimpleTypeDefinition)localObject5).getMemberTypes();
              for (i1 = 0; i1 < localXSObjectList.getLength(); i1++) {
                ((Vector)localObject4).addElement(localXSObjectList.item(i1));
              }
            }
            else
            {
              ((Vector)localObject4).addElement(localObject5);
            }
          }
          localElement3 = DOMUtil.getNextSiblingElement(localElement3);
          if (localElement3 == null) {
            break;
          }
        } while (DOMUtil.getLocalName(localElement3).equals(SchemaSymbols.ELT_SIMPLETYPE));
      }
    }
    else if (((j != 0) || (k != 0)) && (localQName == null))
    {
      reportSchemaError(k != 0 ? "src-simple-type.3.b" : "src-simple-type.2.b", null, localElement1);
      n = 1;
      localObject3 = SchemaGrammar.fAnySimpleType;
    }
    else if ((m != 0) && ((localVector == null) || (localVector.size() == 0)))
    {
      reportSchemaError("src-union-memberTypes-or-simpleTypes", null, localElement1);
      localObject4 = new Vector(1);
      ((Vector)localObject4).addElement(SchemaGrammar.fAnySimpleType);
    }
    if (((j != 0) || (k != 0)) && (localObject3 == null)) {
      localObject3 = SchemaGrammar.fAnySimpleType;
    }
    if ((m != 0) && ((localObject4 == null) || (((Vector)localObject4).size() == 0)))
    {
      localObject4 = new Vector(1);
      ((Vector)localObject4).addElement(SchemaGrammar.fAnySimpleType);
    }
    if ((k != 0) && (isListDatatype((XSSimpleType)localObject3))) {
      reportSchemaError("cos-st-restricts.2.1", new Object[] { paramString, ((XSObject)localObject3).getName() }, localElement1);
    }
    XSSimpleType localXSSimpleType = null;
    Object localObject6;
    if (j != 0)
    {
      localXSSimpleType = schemaFactory.createTypeRestriction(paramString, fTargetNamespace, (short)i, (XSSimpleType)localObject3, localObject1 == null ? null : new XSObjectListImpl((XSObject[])localObject1, localObject1.length));
    }
    else if (k != 0)
    {
      localXSSimpleType = schemaFactory.createTypeList(paramString, fTargetNamespace, (short)i, (XSSimpleType)localObject3, localObject1 == null ? null : new XSObjectListImpl((XSObject[])localObject1, localObject1.length));
    }
    else if (m != 0)
    {
      localObject6 = new XSSimpleType[((Vector)localObject4).size()];
      for (int i3 = 0; i3 < ((Vector)localObject4).size(); i3++) {
        localObject6[i3] = ((XSSimpleType)((Vector)localObject4).elementAt(i3));
      }
      localXSSimpleType = schemaFactory.createTypeUnion(paramString, fTargetNamespace, (short)i, (XSSimpleType[])localObject6, localObject1 == null ? null : new XSObjectListImpl((XSObject[])localObject1, localObject1.length));
    }
    if ((j != 0) && (localElement3 != null))
    {
      localObject6 = traverseFacets(localElement3, (XSSimpleType)localObject3, paramXSDocumentInfo);
      localElement3 = nodeAfterFacets;
      if (n == 0) {
        try
        {
          fValidationState.setNamespaceSupport(fNamespaceSupport);
          localXSSimpleType.applyFacets(facetdata, fPresentFacets, fFixedFacets, fValidationState);
        }
        catch (InvalidDatatypeFacetException localInvalidDatatypeFacetException)
        {
          reportSchemaError(localInvalidDatatypeFacetException.getKey(), localInvalidDatatypeFacetException.getArgs(), localElement1);
        }
      }
    }
    if (localElement3 != null) {
      if (j != 0) {
        reportSchemaError("s4s-elt-must-match.1", new Object[] { SchemaSymbols.ELT_RESTRICTION, "(annotation?, (simpleType?, (minExclusive | minInclusive | maxExclusive | maxInclusive | totalDigits | fractionDigits | length | minLength | maxLength | enumeration | whiteSpace | pattern)*))", DOMUtil.getLocalName(localElement3) }, localElement3);
      } else if (k != 0) {
        reportSchemaError("s4s-elt-must-match.1", new Object[] { SchemaSymbols.ELT_LIST, "(annotation?, (simpleType?))", DOMUtil.getLocalName(localElement3) }, localElement3);
      } else if (m != 0) {
        reportSchemaError("s4s-elt-must-match.1", new Object[] { SchemaSymbols.ELT_UNION, "(annotation?, (simpleType*))", DOMUtil.getLocalName(localElement3) }, localElement3);
      }
    }
    fAttrChecker.returnAttrArray(arrayOfObject, paramXSDocumentInfo);
    return localXSSimpleType;
  }
  
  private XSSimpleType findDTValidator(Element paramElement, String paramString, QName paramQName, short paramShort, XSDocumentInfo paramXSDocumentInfo)
  {
    if (paramQName == null) {
      return null;
    }
    XSTypeDefinition localXSTypeDefinition = (XSTypeDefinition)fSchemaHandler.getGlobalDecl(paramXSDocumentInfo, 7, paramQName, paramElement);
    if (localXSTypeDefinition != null)
    {
      if ((localXSTypeDefinition.getTypeCategory() != 16) || ((localXSTypeDefinition == SchemaGrammar.fAnySimpleType) && (paramShort == 2)))
      {
        if ((localXSTypeDefinition == SchemaGrammar.fAnySimpleType) && (checkBuiltIn(paramString, fTargetNamespace))) {
          return null;
        }
        reportSchemaError("cos-st-restricts.1.1", new Object[] { rawname, paramString }, paramElement);
        return SchemaGrammar.fAnySimpleType;
      }
      if ((localXSTypeDefinition.getFinal() & paramShort) != 0) {
        if (paramShort == 2) {
          reportSchemaError("st-props-correct.3", new Object[] { paramString, rawname }, paramElement);
        } else if (paramShort == 16) {
          reportSchemaError("cos-st-restricts.2.3.1.1", new Object[] { rawname, paramString }, paramElement);
        } else if (paramShort == 8) {
          reportSchemaError("cos-st-restricts.3.3.1.1", new Object[] { rawname, paramString }, paramElement);
        }
      }
    }
    return (XSSimpleType)localXSTypeDefinition;
  }
  
  private final boolean checkBuiltIn(String paramString1, String paramString2)
  {
    if (paramString2 != SchemaSymbols.URI_SCHEMAFORSCHEMA) {
      return false;
    }
    if (SchemaGrammar.SG_SchemaNS.getGlobalTypeDecl(paramString1) != null) {
      fIsBuiltIn = true;
    }
    return fIsBuiltIn;
  }
  
  private boolean isListDatatype(XSSimpleType paramXSSimpleType)
  {
    if (paramXSSimpleType.getVariety() == 2) {
      return true;
    }
    if (paramXSSimpleType.getVariety() == 3)
    {
      XSObjectList localXSObjectList = paramXSSimpleType.getMemberTypes();
      for (int i = 0; i < localXSObjectList.getLength(); i++) {
        if (((XSSimpleType)localXSObjectList.item(i)).getVariety() == 2) {
          return true;
        }
      }
    }
    return false;
  }
  
  private XSSimpleType errorType(String paramString1, String paramString2, short paramShort)
  {
    switch (paramShort)
    {
    case 2: 
      return schemaFactory.createTypeRestriction(paramString1, paramString2, (short)0, SchemaGrammar.fAnySimpleType, null);
    case 16: 
      return schemaFactory.createTypeList(paramString1, paramString2, (short)0, SchemaGrammar.fAnySimpleType, null);
    case 8: 
      return schemaFactory.createTypeUnion(paramString1, paramString2, (short)0, new XSSimpleType[] { SchemaGrammar.fAnySimpleType }, null);
    }
    return null;
  }
}
