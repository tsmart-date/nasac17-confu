package org.apache.xerces.impl.xs;

import java.util.Vector;
import org.apache.xerces.impl.xs.util.NSItemListImpl;
import org.apache.xerces.impl.xs.util.StringListImpl;
import org.apache.xerces.impl.xs.util.XSNamedMap4Types;
import org.apache.xerces.impl.xs.util.XSNamedMapImpl;
import org.apache.xerces.impl.xs.util.XSObjectListImpl;
import org.apache.xerces.util.SymbolHash;
import org.apache.xerces.util.XMLSymbols;
import org.apache.xerces.xs.StringList;
import org.apache.xerces.xs.XSAttributeDeclaration;
import org.apache.xerces.xs.XSAttributeGroupDefinition;
import org.apache.xerces.xs.XSElementDeclaration;
import org.apache.xerces.xs.XSModel;
import org.apache.xerces.xs.XSModelGroupDefinition;
import org.apache.xerces.xs.XSNamedMap;
import org.apache.xerces.xs.XSNamespaceItemList;
import org.apache.xerces.xs.XSNotationDeclaration;
import org.apache.xerces.xs.XSObjectList;
import org.apache.xerces.xs.XSTypeDefinition;

public class XSModelImpl
  implements XSModel
{
  private static final short MAX_COMP_IDX = 16;
  private static final boolean[] GLOBAL_COMP = { false, true, true, true, false, true, true, false, false, false, false, true, false, false, false, true, true };
  private int fGrammarCount;
  private String[] fNamespaces;
  private SchemaGrammar[] fGrammarList;
  private SymbolHash fGrammarMap;
  private SymbolHash fSubGroupMap;
  private XSNamedMap[] fGlobalComponents;
  private XSNamedMap[][] fNSComponents;
  private XSObjectListImpl fAnnotations = null;
  private boolean fHasIDC = false;
  
  public XSModelImpl(SchemaGrammar[] paramArrayOfSchemaGrammar)
  {
    int i = paramArrayOfSchemaGrammar.length;
    fNamespaces = new String[Math.max(i + 1, 5)];
    fGrammarList = new SchemaGrammar[Math.max(i + 1, 5)];
    int j = 0;
    for (int k = 0; k < i; k++)
    {
      fNamespaces[k] = paramArrayOfSchemaGrammar[k].getTargetNamespace();
      fGrammarList[k] = paramArrayOfSchemaGrammar[k];
      if (fNamespaces[k] == SchemaSymbols.URI_SCHEMAFORSCHEMA) {
        j = 1;
      }
    }
    if (j == 0)
    {
      fNamespaces[i] = SchemaSymbols.URI_SCHEMAFORSCHEMA;
      fGrammarList[(i++)] = SchemaGrammar.SG_SchemaNS;
    }
    for (int m = 0; m < i; m++)
    {
      SchemaGrammar localSchemaGrammar1 = fGrammarList[m];
      Vector localVector = localSchemaGrammar1.getImportedGrammars();
      for (int n = localVector == null ? -1 : localVector.size() - 1; n >= 0; n--)
      {
        SchemaGrammar localSchemaGrammar2 = (SchemaGrammar)localVector.elementAt(n);
        for (int i1 = 0; i1 < i; i1++) {
          if (localSchemaGrammar2 == fGrammarList[i1]) {
            break;
          }
        }
        if (i1 == i)
        {
          if (i == fGrammarList.length)
          {
            String[] arrayOfString = new String[i * 2];
            System.arraycopy(fNamespaces, 0, arrayOfString, 0, i);
            fNamespaces = arrayOfString;
            SchemaGrammar[] arrayOfSchemaGrammar = new SchemaGrammar[i * 2];
            System.arraycopy(fGrammarList, 0, arrayOfSchemaGrammar, 0, i);
            fGrammarList = arrayOfSchemaGrammar;
          }
          fNamespaces[i] = localSchemaGrammar2.getTargetNamespace();
          fGrammarList[i] = localSchemaGrammar2;
          i++;
        }
      }
    }
    fGrammarMap = new SymbolHash(i * 2);
    for (m = 0; m < i; m++)
    {
      fGrammarMap.put(null2EmptyString(fNamespaces[m]), fGrammarList[m]);
      if (fGrammarList[m].hasIDConstraints()) {
        fHasIDC = true;
      }
    }
    fGrammarCount = i;
    fGlobalComponents = new XSNamedMap[17];
    fNSComponents = new XSNamedMap[i][17];
    buildSubGroups();
  }
  
  private void buildSubGroups()
  {
    SubstitutionGroupHandler localSubstitutionGroupHandler = new SubstitutionGroupHandler(null);
    for (int i = 0; i < fGrammarCount; i++) {
      localSubstitutionGroupHandler.addSubstitutionGroup(fGrammarList[i].getSubstitutionGroups());
    }
    XSNamedMap localXSNamedMap = getComponents((short)2);
    int j = localXSNamedMap.getLength();
    fSubGroupMap = new SymbolHash(j * 2);
    for (int k = 0; k < j; k++)
    {
      XSElementDecl localXSElementDecl = (XSElementDecl)localXSNamedMap.item(k);
      XSElementDecl[] arrayOfXSElementDecl = localSubstitutionGroupHandler.getSubstitutionGroup(localXSElementDecl);
      fSubGroupMap.put(localXSElementDecl, arrayOfXSElementDecl.length > 0 ? new XSObjectListImpl(arrayOfXSElementDecl, arrayOfXSElementDecl.length) : XSObjectListImpl.EMPTY_LIST);
    }
  }
  
  public StringList getNamespaces()
  {
    return new StringListImpl(fNamespaces, fGrammarCount);
  }
  
  public XSNamespaceItemList getNamespaceItems()
  {
    return new NSItemListImpl(fGrammarList, fGrammarCount);
  }
  
  public synchronized XSNamedMap getComponents(short paramShort)
  {
    if ((paramShort <= 0) || (paramShort > 16) || (GLOBAL_COMP[paramShort] == 0)) {
      return XSNamedMapImpl.EMPTY_MAP;
    }
    SymbolHash[] arrayOfSymbolHash = new SymbolHash[fGrammarCount];
    if (fGlobalComponents[paramShort] == null)
    {
      for (int i = 0; i < fGrammarCount; i++) {
        switch (paramShort)
        {
        case 3: 
        case 15: 
        case 16: 
          arrayOfSymbolHash[i] = fGrammarList[i].fGlobalTypeDecls;
          break;
        case 1: 
          arrayOfSymbolHash[i] = fGrammarList[i].fGlobalAttrDecls;
          break;
        case 2: 
          arrayOfSymbolHash[i] = fGrammarList[i].fGlobalElemDecls;
          break;
        case 5: 
          arrayOfSymbolHash[i] = fGrammarList[i].fGlobalAttrGrpDecls;
          break;
        case 6: 
          arrayOfSymbolHash[i] = fGrammarList[i].fGlobalGroupDecls;
          break;
        case 11: 
          arrayOfSymbolHash[i] = fGrammarList[i].fGlobalNotationDecls;
        }
      }
      if ((paramShort == 15) || (paramShort == 16)) {
        fGlobalComponents[paramShort] = new XSNamedMap4Types(fNamespaces, arrayOfSymbolHash, fGrammarCount, paramShort);
      } else {
        fGlobalComponents[paramShort] = new XSNamedMapImpl(fNamespaces, arrayOfSymbolHash, fGrammarCount);
      }
    }
    return fGlobalComponents[paramShort];
  }
  
  public synchronized XSNamedMap getComponentsByNamespace(short paramShort, String paramString)
  {
    if ((paramShort <= 0) || (paramShort > 16) || (GLOBAL_COMP[paramShort] == 0)) {
      return XSNamedMapImpl.EMPTY_MAP;
    }
    int i = 0;
    if (paramString != null) {
      while (i < fGrammarCount)
      {
        if (paramString.equals(fNamespaces[i])) {
          break;
        }
        i++;
      }
    } else {
      while (i < fGrammarCount)
      {
        if (fNamespaces[i] == null) {
          break;
        }
        i++;
      }
    }
    if (i == fGrammarCount) {
      return XSNamedMapImpl.EMPTY_MAP;
    }
    if (fNSComponents[i][paramShort] == null)
    {
      SymbolHash localSymbolHash = null;
      switch (paramShort)
      {
      case 3: 
      case 15: 
      case 16: 
        localSymbolHash = fGrammarList[i].fGlobalTypeDecls;
        break;
      case 1: 
        localSymbolHash = fGrammarList[i].fGlobalAttrDecls;
        break;
      case 2: 
        localSymbolHash = fGrammarList[i].fGlobalElemDecls;
        break;
      case 5: 
        localSymbolHash = fGrammarList[i].fGlobalAttrGrpDecls;
        break;
      case 6: 
        localSymbolHash = fGrammarList[i].fGlobalGroupDecls;
        break;
      case 11: 
        localSymbolHash = fGrammarList[i].fGlobalNotationDecls;
      }
      if ((paramShort == 15) || (paramShort == 16)) {
        fNSComponents[i][paramShort] = new XSNamedMap4Types(paramString, localSymbolHash, paramShort);
      } else {
        fNSComponents[i][paramShort] = new XSNamedMapImpl(paramString, localSymbolHash);
      }
    }
    return fNSComponents[i][paramShort];
  }
  
  public XSTypeDefinition getTypeDefinition(String paramString1, String paramString2)
  {
    SchemaGrammar localSchemaGrammar = (SchemaGrammar)fGrammarMap.get(null2EmptyString(paramString2));
    if (localSchemaGrammar == null) {
      return null;
    }
    return (XSTypeDefinition)fGlobalTypeDecls.get(paramString1);
  }
  
  public XSAttributeDeclaration getAttributeDeclaration(String paramString1, String paramString2)
  {
    SchemaGrammar localSchemaGrammar = (SchemaGrammar)fGrammarMap.get(null2EmptyString(paramString2));
    if (localSchemaGrammar == null) {
      return null;
    }
    return (XSAttributeDeclaration)fGlobalAttrDecls.get(paramString1);
  }
  
  public XSElementDeclaration getElementDeclaration(String paramString1, String paramString2)
  {
    SchemaGrammar localSchemaGrammar = (SchemaGrammar)fGrammarMap.get(null2EmptyString(paramString2));
    if (localSchemaGrammar == null) {
      return null;
    }
    return (XSElementDeclaration)fGlobalElemDecls.get(paramString1);
  }
  
  public XSAttributeGroupDefinition getAttributeGroup(String paramString1, String paramString2)
  {
    SchemaGrammar localSchemaGrammar = (SchemaGrammar)fGrammarMap.get(null2EmptyString(paramString2));
    if (localSchemaGrammar == null) {
      return null;
    }
    return (XSAttributeGroupDefinition)fGlobalAttrGrpDecls.get(paramString1);
  }
  
  public XSModelGroupDefinition getModelGroupDefinition(String paramString1, String paramString2)
  {
    SchemaGrammar localSchemaGrammar = (SchemaGrammar)fGrammarMap.get(null2EmptyString(paramString2));
    if (localSchemaGrammar == null) {
      return null;
    }
    return (XSModelGroupDefinition)fGlobalGroupDecls.get(paramString1);
  }
  
  public XSNotationDeclaration getNotationDeclaration(String paramString1, String paramString2)
  {
    SchemaGrammar localSchemaGrammar = (SchemaGrammar)fGrammarMap.get(null2EmptyString(paramString2));
    if (localSchemaGrammar == null) {
      return null;
    }
    return (XSNotationDeclaration)fGlobalNotationDecls.get(paramString1);
  }
  
  public synchronized XSObjectList getAnnotations()
  {
    if (fAnnotations != null) {
      return fAnnotations;
    }
    int i = 0;
    for (int j = 0; j < fGrammarCount; j++) {
      i += fGrammarList[j].fNumAnnotations;
    }
    XSAnnotationImpl[] arrayOfXSAnnotationImpl = new XSAnnotationImpl[i];
    int k = 0;
    for (int m = 0; m < fGrammarCount; m++)
    {
      SchemaGrammar localSchemaGrammar = fGrammarList[m];
      if (fNumAnnotations > 0)
      {
        System.arraycopy(fAnnotations, 0, arrayOfXSAnnotationImpl, k, fNumAnnotations);
        k += fNumAnnotations;
      }
    }
    fAnnotations = new XSObjectListImpl(arrayOfXSAnnotationImpl, arrayOfXSAnnotationImpl.length);
    return fAnnotations;
  }
  
  private static final String null2EmptyString(String paramString)
  {
    return paramString == null ? XMLSymbols.EMPTY_STRING : paramString;
  }
  
  public boolean hasIDConstraints()
  {
    return fHasIDC;
  }
  
  public XSObjectList getSubstitutionGroup(XSElementDeclaration paramXSElementDeclaration)
  {
    return (XSObjectList)fSubGroupMap.get(paramXSElementDeclaration);
  }
}
