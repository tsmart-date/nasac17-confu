package org.apache.xerces.impl.xs.traversers;

import org.apache.xerces.impl.dv.ValidatedInfo;
import org.apache.xerces.impl.dv.XSSimpleType;
import org.apache.xerces.impl.validation.ValidationState;
import org.apache.xerces.impl.xs.SchemaGrammar;
import org.apache.xerces.impl.xs.SchemaNamespaceSupport;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.apache.xerces.impl.xs.XSAnnotationImpl;
import org.apache.xerces.impl.xs.XSComplexTypeDecl;
import org.apache.xerces.impl.xs.XSConstraints;
import org.apache.xerces.impl.xs.XSDeclarationPool;
import org.apache.xerces.impl.xs.XSElementDecl;
import org.apache.xerces.impl.xs.XSParticleDecl;
import org.apache.xerces.impl.xs.util.XInt;
import org.apache.xerces.impl.xs.util.XSObjectListImpl;
import org.apache.xerces.util.DOMUtil;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xs.XSObject;
import org.apache.xerces.xs.XSObjectList;
import org.apache.xerces.xs.XSTypeDefinition;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

class XSDElementTraverser
  extends XSDAbstractTraverser
{
  protected final XSElementDecl fTempElementDecl = new XSElementDecl();
  boolean fDeferTraversingLocalElements;
  
  XSDElementTraverser(XSDHandler paramXSDHandler, XSAttributeChecker paramXSAttributeChecker)
  {
    super(paramXSDHandler, paramXSAttributeChecker);
  }
  
  XSParticleDecl traverseLocal(Element paramElement, XSDocumentInfo paramXSDocumentInfo, SchemaGrammar paramSchemaGrammar, int paramInt, XSObject paramXSObject)
  {
    XSParticleDecl localXSParticleDecl = null;
    if (fSchemaHandler.fDeclPool != null) {
      localXSParticleDecl = fSchemaHandler.fDeclPool.getParticleDecl();
    } else {
      localXSParticleDecl = new XSParticleDecl();
    }
    if (fDeferTraversingLocalElements)
    {
      fType = 1;
      Attr localAttr = paramElement.getAttributeNode(SchemaSymbols.ATT_MINOCCURS);
      if (localAttr != null)
      {
        String str = localAttr.getValue();
        try
        {
          int i = Integer.parseInt(str.trim());
          if (i >= 0) {
            fMinOccurs = i;
          }
        }
        catch (NumberFormatException localNumberFormatException) {}
      }
      fSchemaHandler.fillInLocalElemInfo(paramElement, paramXSDocumentInfo, paramInt, paramXSObject, localXSParticleDecl);
    }
    else
    {
      traverseLocal(localXSParticleDecl, paramElement, paramXSDocumentInfo, paramSchemaGrammar, paramInt, paramXSObject, null);
      if (fType == 0) {
        localXSParticleDecl = null;
      }
    }
    return localXSParticleDecl;
  }
  
  protected void traverseLocal(XSParticleDecl paramXSParticleDecl, Element paramElement, XSDocumentInfo paramXSDocumentInfo, SchemaGrammar paramSchemaGrammar, int paramInt, XSObject paramXSObject, String[] paramArrayOfString)
  {
    if (paramArrayOfString != null) {
      fNamespaceSupport.setEffectiveContext(paramArrayOfString);
    }
    Object[] arrayOfObject = fAttrChecker.checkAttributes(paramElement, false, paramXSDocumentInfo);
    QName localQName = (QName)arrayOfObject[XSAttributeChecker.ATTIDX_REF];
    XInt localXInt1 = (XInt)arrayOfObject[XSAttributeChecker.ATTIDX_MINOCCURS];
    XInt localXInt2 = (XInt)arrayOfObject[XSAttributeChecker.ATTIDX_MAXOCCURS];
    XSElementDecl localXSElementDecl = null;
    XSAnnotationImpl localXSAnnotationImpl = null;
    if (paramElement.getAttributeNode(SchemaSymbols.ATT_REF) != null)
    {
      if (localQName != null)
      {
        localXSElementDecl = (XSElementDecl)fSchemaHandler.getGlobalDecl(paramXSDocumentInfo, 3, localQName, paramElement);
        localObject = DOMUtil.getFirstChildElement(paramElement);
        if ((localObject != null) && (DOMUtil.getLocalName((Node)localObject).equals(SchemaSymbols.ELT_ANNOTATION)))
        {
          localXSAnnotationImpl = traverseAnnotationDecl((Element)localObject, arrayOfObject, false, paramXSDocumentInfo);
          localObject = DOMUtil.getNextSiblingElement((Node)localObject);
        }
        else
        {
          String str = DOMUtil.getSyntheticAnnotation((Node)localObject);
          if (str != null) {
            localXSAnnotationImpl = traverseSyntheticAnnotation((Element)localObject, str, arrayOfObject, false, paramXSDocumentInfo);
          }
        }
        if (localObject != null) {
          reportSchemaError("src-element.2.2", new Object[] { rawname, DOMUtil.getLocalName((Node)localObject) }, (Element)localObject);
        }
      }
      else
      {
        localXSElementDecl = null;
      }
    }
    else {
      localXSElementDecl = traverseNamedElement(paramElement, arrayOfObject, paramXSDocumentInfo, paramSchemaGrammar, false, paramXSObject);
    }
    fMinOccurs = localXInt1.intValue();
    fMaxOccurs = localXInt2.intValue();
    if (localXSElementDecl != null)
    {
      fType = 1;
      fValue = localXSElementDecl;
    }
    else
    {
      fType = 0;
    }
    if (localQName != null)
    {
      if (localXSAnnotationImpl != null)
      {
        localObject = new XSObjectListImpl();
        ((XSObjectListImpl)localObject).add(localXSAnnotationImpl);
      }
      else
      {
        localObject = XSObjectListImpl.EMPTY_LIST;
      }
      fAnnotations = ((XSObjectList)localObject);
    }
    else
    {
      fAnnotations = (localXSElementDecl != null ? fAnnotations : XSObjectListImpl.EMPTY_LIST);
    }
    Object localObject = (Long)arrayOfObject[XSAttributeChecker.ATTIDX_FROMDEFAULT];
    checkOccurrences(paramXSParticleDecl, SchemaSymbols.ELT_ELEMENT, (Element)paramElement.getParentNode(), paramInt, ((Long)localObject).longValue());
    fAttrChecker.returnAttrArray(arrayOfObject, paramXSDocumentInfo);
  }
  
  XSElementDecl traverseGlobal(Element paramElement, XSDocumentInfo paramXSDocumentInfo, SchemaGrammar paramSchemaGrammar)
  {
    Object[] arrayOfObject = fAttrChecker.checkAttributes(paramElement, true, paramXSDocumentInfo);
    XSElementDecl localXSElementDecl = traverseNamedElement(paramElement, arrayOfObject, paramXSDocumentInfo, paramSchemaGrammar, true, null);
    fAttrChecker.returnAttrArray(arrayOfObject, paramXSDocumentInfo);
    return localXSElementDecl;
  }
  
  XSElementDecl traverseNamedElement(Element paramElement, Object[] paramArrayOfObject, XSDocumentInfo paramXSDocumentInfo, SchemaGrammar paramSchemaGrammar, boolean paramBoolean, XSObject paramXSObject)
  {
    Boolean localBoolean1 = (Boolean)paramArrayOfObject[XSAttributeChecker.ATTIDX_ABSTRACT];
    XInt localXInt1 = (XInt)paramArrayOfObject[XSAttributeChecker.ATTIDX_BLOCK];
    String str1 = (String)paramArrayOfObject[XSAttributeChecker.ATTIDX_DEFAULT];
    XInt localXInt2 = (XInt)paramArrayOfObject[XSAttributeChecker.ATTIDX_FINAL];
    String str2 = (String)paramArrayOfObject[XSAttributeChecker.ATTIDX_FIXED];
    XInt localXInt3 = (XInt)paramArrayOfObject[XSAttributeChecker.ATTIDX_FORM];
    String str3 = (String)paramArrayOfObject[XSAttributeChecker.ATTIDX_NAME];
    Boolean localBoolean2 = (Boolean)paramArrayOfObject[XSAttributeChecker.ATTIDX_NILLABLE];
    QName localQName1 = (QName)paramArrayOfObject[XSAttributeChecker.ATTIDX_SUBSGROUP];
    QName localQName2 = (QName)paramArrayOfObject[XSAttributeChecker.ATTIDX_TYPE];
    XSElementDecl localXSElementDecl = null;
    if (fSchemaHandler.fDeclPool != null) {
      localXSElementDecl = fSchemaHandler.fDeclPool.getElementDecl();
    } else {
      localXSElementDecl = new XSElementDecl();
    }
    if (str3 != null) {
      fName = fSymbolTable.addSymbol(str3);
    }
    if (paramBoolean)
    {
      fTargetNamespace = fTargetNamespace;
      localXSElementDecl.setIsGlobal();
    }
    else
    {
      if ((paramXSObject instanceof XSComplexTypeDecl)) {
        localXSElementDecl.setIsLocal((XSComplexTypeDecl)paramXSObject);
      }
      if (localXInt3 != null)
      {
        if (localXInt3.intValue() == 1) {
          fTargetNamespace = fTargetNamespace;
        } else {
          fTargetNamespace = null;
        }
      }
      else if (fAreLocalElementsQualified) {
        fTargetNamespace = fTargetNamespace;
      } else {
        fTargetNamespace = null;
      }
    }
    fBlock = (localXInt1 == null ? fBlockDefault : localXInt1.shortValue());
    fFinal = (localXInt2 == null ? fFinalDefault : localXInt2.shortValue());
    XSElementDecl tmp302_300 = localXSElementDecl;
    302300fBlock = ((short)(302300fBlock & 0x7));
    XSElementDecl tmp315_313 = localXSElementDecl;
    315313fFinal = ((short)(315313fFinal & 0x3));
    if (localBoolean2.booleanValue()) {
      localXSElementDecl.setIsNillable();
    }
    if ((localBoolean1 != null) && (localBoolean1.booleanValue())) {
      localXSElementDecl.setIsAbstract();
    }
    if (str2 != null)
    {
      fDefault = new ValidatedInfo();
      fDefault.normalizedValue = str2;
      localXSElementDecl.setConstraintType((short)2);
    }
    else if (str1 != null)
    {
      fDefault = new ValidatedInfo();
      fDefault.normalizedValue = str1;
      localXSElementDecl.setConstraintType((short)1);
    }
    else
    {
      localXSElementDecl.setConstraintType((short)0);
    }
    if (localQName1 != null) {
      fSubGroup = ((XSElementDecl)fSchemaHandler.getGlobalDecl(paramXSDocumentInfo, 3, localQName1, paramElement));
    }
    Element localElement = DOMUtil.getFirstChildElement(paramElement);
    XSAnnotationImpl localXSAnnotationImpl = null;
    Object localObject1;
    if ((localElement != null) && (DOMUtil.getLocalName(localElement).equals(SchemaSymbols.ELT_ANNOTATION)))
    {
      localXSAnnotationImpl = traverseAnnotationDecl(localElement, paramArrayOfObject, false, paramXSDocumentInfo);
      localElement = DOMUtil.getNextSiblingElement(localElement);
    }
    else
    {
      localObject1 = DOMUtil.getSyntheticAnnotation(paramElement);
      if (localObject1 != null) {
        localXSAnnotationImpl = traverseSyntheticAnnotation(paramElement, (String)localObject1, paramArrayOfObject, false, paramXSDocumentInfo);
      }
    }
    if (localXSAnnotationImpl != null)
    {
      localObject1 = new XSObjectListImpl();
      ((XSObjectListImpl)localObject1).add(localXSAnnotationImpl);
    }
    else
    {
      localObject1 = XSObjectListImpl.EMPTY_LIST;
    }
    fAnnotations = ((XSObjectList)localObject1);
    Object localObject2 = null;
    int i = 0;
    String str4;
    if (localElement != null)
    {
      str4 = DOMUtil.getLocalName(localElement);
      if (str4.equals(SchemaSymbols.ELT_COMPLEXTYPE))
      {
        localObject2 = fSchemaHandler.fComplexTypeTraverser.traverseLocal(localElement, paramXSDocumentInfo, paramSchemaGrammar);
        i = 1;
        localElement = DOMUtil.getNextSiblingElement(localElement);
      }
      else if (str4.equals(SchemaSymbols.ELT_SIMPLETYPE))
      {
        localObject2 = fSchemaHandler.fSimpleTypeTraverser.traverseLocal(localElement, paramXSDocumentInfo, paramSchemaGrammar);
        i = 1;
        localElement = DOMUtil.getNextSiblingElement(localElement);
      }
    }
    if ((localObject2 == null) && (localQName2 != null)) {
      localObject2 = (XSTypeDefinition)fSchemaHandler.getGlobalDecl(paramXSDocumentInfo, 7, localQName2, paramElement);
    }
    if ((localObject2 == null) && (fSubGroup != null)) {
      localObject2 = fSubGroup.fType;
    }
    if (localObject2 == null) {
      localObject2 = SchemaGrammar.fAnyType;
    }
    fType = ((XSTypeDefinition)localObject2);
    if (localElement != null)
    {
      str4 = DOMUtil.getLocalName(localElement);
      while ((localElement != null) && ((str4.equals(SchemaSymbols.ELT_KEY)) || (str4.equals(SchemaSymbols.ELT_KEYREF)) || (str4.equals(SchemaSymbols.ELT_UNIQUE))))
      {
        if ((str4.equals(SchemaSymbols.ELT_KEY)) || (str4.equals(SchemaSymbols.ELT_UNIQUE)))
        {
          DOMUtil.setHidden(localElement);
          fSchemaHandler.fUniqueOrKeyTraverser.traverse(localElement, localXSElementDecl, paramXSDocumentInfo, paramSchemaGrammar);
          if (DOMUtil.getAttrValue(localElement, SchemaSymbols.ATT_NAME).length() != 0) {
            fSchemaHandler.checkForDuplicateNames(fTargetNamespace + "," + DOMUtil.getAttrValue(localElement, SchemaSymbols.ATT_NAME), fSchemaHandler.getIDRegistry(), fSchemaHandler.getIDRegistry_sub(), localElement, paramXSDocumentInfo);
          }
        }
        else if (str4.equals(SchemaSymbols.ELT_KEYREF))
        {
          fSchemaHandler.storeKeyRef(localElement, paramXSDocumentInfo, localXSElementDecl);
        }
        localElement = DOMUtil.getNextSiblingElement(localElement);
        if (localElement != null) {
          str4 = DOMUtil.getLocalName(localElement);
        }
      }
    }
    if ((paramBoolean) && (str3 != null)) {
      paramSchemaGrammar.addGlobalElementDecl(localXSElementDecl);
    }
    if (str3 == null)
    {
      if (paramBoolean) {
        reportSchemaError("s4s-att-must-appear", new Object[] { SchemaSymbols.ELT_ELEMENT, SchemaSymbols.ATT_NAME }, paramElement);
      } else {
        reportSchemaError("src-element.2.1", null, paramElement);
      }
      str3 = "(no name)";
    }
    if (localElement != null) {
      reportSchemaError("s4s-elt-must-match.1", new Object[] { str3, "(annotation?, (simpleType | complexType)?, (unique | key | keyref)*))", DOMUtil.getLocalName(localElement) }, localElement);
    }
    if ((str1 != null) && (str2 != null)) {
      reportSchemaError("src-element.1", new Object[] { str3 }, paramElement);
    }
    if ((i != 0) && (localQName2 != null)) {
      reportSchemaError("src-element.3", new Object[] { str3 }, paramElement);
    }
    checkNotationType(str3, (XSTypeDefinition)localObject2, paramElement);
    if (fDefault != null)
    {
      fValidationState.setNamespaceSupport(fNamespaceSupport);
      if (XSConstraints.ElementDefaultValidImmediate(fType, fDefault.normalizedValue, fValidationState, fDefault) == null)
      {
        reportSchemaError("e-props-correct.2", new Object[] { str3, fDefault.normalizedValue }, paramElement);
        localXSElementDecl.setConstraintType((short)0);
      }
    }
    if ((fSubGroup != null) && (!XSConstraints.checkTypeDerivationOk(fType, fSubGroup.fType, fSubGroup.fFinal))) {
      reportSchemaError("e-props-correct.4", new Object[] { str3, prefix + ":" + localpart }, paramElement);
    }
    if ((fDefault != null) && (((((XSTypeDefinition)localObject2).getTypeCategory() == 16) && (((XSSimpleType)localObject2).isIDType())) || ((((XSTypeDefinition)localObject2).getTypeCategory() == 15) && (((XSComplexTypeDecl)localObject2).containsTypeID())))) {
      reportSchemaError("e-props-correct.5", new Object[] { fName }, paramElement);
    }
    if (fName == null) {
      return null;
    }
    return localXSElementDecl;
  }
  
  void reset(SymbolTable paramSymbolTable, boolean paramBoolean)
  {
    super.reset(paramSymbolTable, paramBoolean);
    fDeferTraversingLocalElements = true;
  }
}
