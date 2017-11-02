package org.apache.xerces.impl.xs;

import java.lang.ref.SoftReference;
import java.util.Vector;
import org.apache.xerces.impl.dv.SchemaDVFactory;
import org.apache.xerces.impl.dv.ValidatedInfo;
import org.apache.xerces.impl.dv.XSSimpleType;
import org.apache.xerces.impl.xs.identity.IdentityConstraint;
import org.apache.xerces.impl.xs.util.SimpleLocator;
import org.apache.xerces.impl.xs.util.StringListImpl;
import org.apache.xerces.impl.xs.util.XSNamedMap4Types;
import org.apache.xerces.impl.xs.util.XSNamedMapImpl;
import org.apache.xerces.impl.xs.util.XSObjectListImpl;
import org.apache.xerces.parsers.DOMParser;
import org.apache.xerces.parsers.SAXParser;
import org.apache.xerces.parsers.XML11Configuration;
import org.apache.xerces.util.SymbolHash;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.XMLResourceIdentifierImpl;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xni.grammars.XMLGrammarDescription;
import org.apache.xerces.xni.grammars.XSGrammar;
import org.apache.xerces.xni.parser.XMLParserConfiguration;
import org.apache.xerces.xs.StringList;
import org.apache.xerces.xs.XSAnnotation;
import org.apache.xerces.xs.XSAttributeDeclaration;
import org.apache.xerces.xs.XSAttributeGroupDefinition;
import org.apache.xerces.xs.XSElementDeclaration;
import org.apache.xerces.xs.XSModel;
import org.apache.xerces.xs.XSModelGroupDefinition;
import org.apache.xerces.xs.XSNamedMap;
import org.apache.xerces.xs.XSNamespaceItem;
import org.apache.xerces.xs.XSNotationDeclaration;
import org.apache.xerces.xs.XSObject;
import org.apache.xerces.xs.XSObjectList;
import org.apache.xerces.xs.XSParticle;
import org.apache.xerces.xs.XSTerm;
import org.apache.xerces.xs.XSTypeDefinition;
import org.apache.xerces.xs.XSWildcard;
import org.xml.sax.SAXException;

public class SchemaGrammar
  implements XSGrammar, XSNamespaceItem
{
  String fTargetNamespace;
  SymbolHash fGlobalAttrDecls;
  SymbolHash fGlobalAttrGrpDecls;
  SymbolHash fGlobalElemDecls;
  SymbolHash fGlobalGroupDecls;
  SymbolHash fGlobalNotationDecls;
  SymbolHash fGlobalIDConstraintDecls;
  SymbolHash fGlobalTypeDecls;
  XSDDescription fGrammarDescription = null;
  XSAnnotationImpl[] fAnnotations = null;
  int fNumAnnotations;
  private SymbolTable fSymbolTable = null;
  private SoftReference fSAXParser = null;
  private SoftReference fDOMParser = null;
  private static final int BASICSET_COUNT = 29;
  private static final int FULLSET_COUNT = 46;
  private static final int GRAMMAR_XS = 1;
  private static final int GRAMMAR_XSI = 2;
  Vector fImported = null;
  private static final int INITIAL_SIZE = 16;
  private static final int INC_SIZE = 16;
  private int fCTCount = 0;
  private XSComplexTypeDecl[] fComplexTypeDecls = new XSComplexTypeDecl[16];
  private SimpleLocator[] fCTLocators = new SimpleLocator[16];
  private static final int REDEFINED_GROUP_INIT_SIZE = 2;
  private int fRGCount = 0;
  private XSGroupDecl[] fRedefinedGroupDecls = new XSGroupDecl[2];
  private SimpleLocator[] fRGLocators = new SimpleLocator[1];
  boolean fFullChecked = false;
  private int fSubGroupCount = 0;
  private XSElementDecl[] fSubGroups = new XSElementDecl[16];
  public static final XSComplexTypeDecl fAnyType = new XSAnyType();
  public static final BuiltinSchemaGrammar SG_SchemaNS = new BuiltinSchemaGrammar(1);
  public static final Schema4Annotations SG_Schema4Annotations = new Schema4Annotations();
  public static final XSSimpleType fAnySimpleType = (XSSimpleType)SG_SchemaNS.getGlobalTypeDecl("anySimpleType");
  public static final BuiltinSchemaGrammar SG_XSI = new BuiltinSchemaGrammar(2);
  private static final short MAX_COMP_IDX = 16;
  private static final boolean[] GLOBAL_COMP = { false, true, true, true, false, true, true, false, false, false, false, true, false, false, false, true, true };
  private XSNamedMap[] fComponents = null;
  private Vector fDocuments = null;
  private Vector fLocations = null;
  
  protected SchemaGrammar() {}
  
  public SchemaGrammar(String paramString, XSDDescription paramXSDDescription, SymbolTable paramSymbolTable)
  {
    fTargetNamespace = paramString;
    fGrammarDescription = paramXSDDescription;
    fSymbolTable = paramSymbolTable;
    fGlobalAttrDecls = new SymbolHash();
    fGlobalAttrGrpDecls = new SymbolHash();
    fGlobalElemDecls = new SymbolHash();
    fGlobalGroupDecls = new SymbolHash();
    fGlobalNotationDecls = new SymbolHash();
    fGlobalIDConstraintDecls = new SymbolHash();
    if (fTargetNamespace == SchemaSymbols.URI_SCHEMAFORSCHEMA) {
      fGlobalTypeDecls = SG_SchemaNSfGlobalTypeDecls.makeClone();
    } else {
      fGlobalTypeDecls = new SymbolHash();
    }
  }
  
  public XMLGrammarDescription getGrammarDescription()
  {
    return fGrammarDescription;
  }
  
  public boolean isNamespaceAware()
  {
    return true;
  }
  
  public void setImportedGrammars(Vector paramVector)
  {
    fImported = paramVector;
  }
  
  public Vector getImportedGrammars()
  {
    return fImported;
  }
  
  public final String getTargetNamespace()
  {
    return fTargetNamespace;
  }
  
  public void addGlobalAttributeDecl(XSAttributeDecl paramXSAttributeDecl)
  {
    fGlobalAttrDecls.put(fName, paramXSAttributeDecl);
  }
  
  public void addGlobalAttributeGroupDecl(XSAttributeGroupDecl paramXSAttributeGroupDecl)
  {
    fGlobalAttrGrpDecls.put(fName, paramXSAttributeGroupDecl);
  }
  
  public void addGlobalElementDecl(XSElementDecl paramXSElementDecl)
  {
    fGlobalElemDecls.put(fName, paramXSElementDecl);
    if (fSubGroup != null)
    {
      if (fSubGroupCount == fSubGroups.length) {
        fSubGroups = resize(fSubGroups, fSubGroupCount + 16);
      }
      fSubGroups[(fSubGroupCount++)] = paramXSElementDecl;
    }
  }
  
  public void addGlobalGroupDecl(XSGroupDecl paramXSGroupDecl)
  {
    fGlobalGroupDecls.put(fName, paramXSGroupDecl);
  }
  
  public void addGlobalNotationDecl(XSNotationDecl paramXSNotationDecl)
  {
    fGlobalNotationDecls.put(fName, paramXSNotationDecl);
  }
  
  public void addGlobalTypeDecl(XSTypeDefinition paramXSTypeDefinition)
  {
    fGlobalTypeDecls.put(paramXSTypeDefinition.getName(), paramXSTypeDefinition);
  }
  
  public final void addIDConstraintDecl(XSElementDecl paramXSElementDecl, IdentityConstraint paramIdentityConstraint)
  {
    paramXSElementDecl.addIDConstraint(paramIdentityConstraint);
    fGlobalIDConstraintDecls.put(paramIdentityConstraint.getIdentityConstraintName(), paramIdentityConstraint);
  }
  
  public final XSAttributeDecl getGlobalAttributeDecl(String paramString)
  {
    return (XSAttributeDecl)fGlobalAttrDecls.get(paramString);
  }
  
  public final XSAttributeGroupDecl getGlobalAttributeGroupDecl(String paramString)
  {
    return (XSAttributeGroupDecl)fGlobalAttrGrpDecls.get(paramString);
  }
  
  public final XSElementDecl getGlobalElementDecl(String paramString)
  {
    return (XSElementDecl)fGlobalElemDecls.get(paramString);
  }
  
  public final XSGroupDecl getGlobalGroupDecl(String paramString)
  {
    return (XSGroupDecl)fGlobalGroupDecls.get(paramString);
  }
  
  public final XSNotationDecl getGlobalNotationDecl(String paramString)
  {
    return (XSNotationDecl)fGlobalNotationDecls.get(paramString);
  }
  
  public final XSTypeDefinition getGlobalTypeDecl(String paramString)
  {
    return (XSTypeDefinition)fGlobalTypeDecls.get(paramString);
  }
  
  public final IdentityConstraint getIDConstraintDecl(String paramString)
  {
    return (IdentityConstraint)fGlobalIDConstraintDecls.get(paramString);
  }
  
  public final boolean hasIDConstraints()
  {
    return fGlobalIDConstraintDecls.getLength() > 0;
  }
  
  public void addComplexTypeDecl(XSComplexTypeDecl paramXSComplexTypeDecl, SimpleLocator paramSimpleLocator)
  {
    if (fCTCount == fComplexTypeDecls.length)
    {
      fComplexTypeDecls = resize(fComplexTypeDecls, fCTCount + 16);
      fCTLocators = resize(fCTLocators, fCTCount + 16);
    }
    fCTLocators[fCTCount] = paramSimpleLocator;
    fComplexTypeDecls[(fCTCount++)] = paramXSComplexTypeDecl;
  }
  
  public void addRedefinedGroupDecl(XSGroupDecl paramXSGroupDecl1, XSGroupDecl paramXSGroupDecl2, SimpleLocator paramSimpleLocator)
  {
    if (fRGCount == fRedefinedGroupDecls.length)
    {
      fRedefinedGroupDecls = resize(fRedefinedGroupDecls, fRGCount << 1);
      fRGLocators = resize(fRGLocators, fRGCount);
    }
    fRGLocators[(fRGCount / 2)] = paramSimpleLocator;
    fRedefinedGroupDecls[(fRGCount++)] = paramXSGroupDecl1;
    fRedefinedGroupDecls[(fRGCount++)] = paramXSGroupDecl2;
  }
  
  final XSComplexTypeDecl[] getUncheckedComplexTypeDecls()
  {
    if (fCTCount < fComplexTypeDecls.length)
    {
      fComplexTypeDecls = resize(fComplexTypeDecls, fCTCount);
      fCTLocators = resize(fCTLocators, fCTCount);
    }
    return fComplexTypeDecls;
  }
  
  final SimpleLocator[] getUncheckedCTLocators()
  {
    if (fCTCount < fCTLocators.length)
    {
      fComplexTypeDecls = resize(fComplexTypeDecls, fCTCount);
      fCTLocators = resize(fCTLocators, fCTCount);
    }
    return fCTLocators;
  }
  
  final XSGroupDecl[] getRedefinedGroupDecls()
  {
    if (fRGCount < fRedefinedGroupDecls.length)
    {
      fRedefinedGroupDecls = resize(fRedefinedGroupDecls, fRGCount);
      fRGLocators = resize(fRGLocators, fRGCount / 2);
    }
    return fRedefinedGroupDecls;
  }
  
  final SimpleLocator[] getRGLocators()
  {
    if (fRGCount < fRedefinedGroupDecls.length)
    {
      fRedefinedGroupDecls = resize(fRedefinedGroupDecls, fRGCount);
      fRGLocators = resize(fRGLocators, fRGCount / 2);
    }
    return fRGLocators;
  }
  
  final void setUncheckedTypeNum(int paramInt)
  {
    fCTCount = paramInt;
    fComplexTypeDecls = resize(fComplexTypeDecls, fCTCount);
    fCTLocators = resize(fCTLocators, fCTCount);
  }
  
  final XSElementDecl[] getSubstitutionGroups()
  {
    if (fSubGroupCount < fSubGroups.length) {
      fSubGroups = resize(fSubGroups, fSubGroupCount);
    }
    return fSubGroups;
  }
  
  static final XSComplexTypeDecl[] resize(XSComplexTypeDecl[] paramArrayOfXSComplexTypeDecl, int paramInt)
  {
    XSComplexTypeDecl[] arrayOfXSComplexTypeDecl = new XSComplexTypeDecl[paramInt];
    System.arraycopy(paramArrayOfXSComplexTypeDecl, 0, arrayOfXSComplexTypeDecl, 0, Math.min(paramArrayOfXSComplexTypeDecl.length, paramInt));
    return arrayOfXSComplexTypeDecl;
  }
  
  static final XSGroupDecl[] resize(XSGroupDecl[] paramArrayOfXSGroupDecl, int paramInt)
  {
    XSGroupDecl[] arrayOfXSGroupDecl = new XSGroupDecl[paramInt];
    System.arraycopy(paramArrayOfXSGroupDecl, 0, arrayOfXSGroupDecl, 0, Math.min(paramArrayOfXSGroupDecl.length, paramInt));
    return arrayOfXSGroupDecl;
  }
  
  static final XSElementDecl[] resize(XSElementDecl[] paramArrayOfXSElementDecl, int paramInt)
  {
    XSElementDecl[] arrayOfXSElementDecl = new XSElementDecl[paramInt];
    System.arraycopy(paramArrayOfXSElementDecl, 0, arrayOfXSElementDecl, 0, Math.min(paramArrayOfXSElementDecl.length, paramInt));
    return arrayOfXSElementDecl;
  }
  
  static final SimpleLocator[] resize(SimpleLocator[] paramArrayOfSimpleLocator, int paramInt)
  {
    SimpleLocator[] arrayOfSimpleLocator = new SimpleLocator[paramInt];
    System.arraycopy(paramArrayOfSimpleLocator, 0, arrayOfSimpleLocator, 0, Math.min(paramArrayOfSimpleLocator.length, paramInt));
    return arrayOfSimpleLocator;
  }
  
  public synchronized void addDocument(Object paramObject, String paramString)
  {
    if (fDocuments == null)
    {
      fDocuments = new Vector();
      fLocations = new Vector();
    }
    fDocuments.addElement(paramObject);
    fLocations.addElement(paramString);
  }
  
  public String getSchemaNamespace()
  {
    return fTargetNamespace;
  }
  
  synchronized DOMParser getDOMParser()
  {
    if (fDOMParser != null)
    {
      localObject = (DOMParser)fDOMParser.get();
      if (localObject != null) {
        return localObject;
      }
    }
    Object localObject = new XML11Configuration(fSymbolTable);
    ((XML11Configuration)localObject).setFeature("http://xml.org/sax/features/namespaces", true);
    ((XML11Configuration)localObject).setFeature("http://xml.org/sax/features/validation", false);
    DOMParser localDOMParser = new DOMParser((XMLParserConfiguration)localObject);
    try
    {
      localDOMParser.setFeature("http://apache.org/xml/features/dom/defer-node-expansion", false);
    }
    catch (SAXException localSAXException) {}
    fDOMParser = new SoftReference(localDOMParser);
    return localDOMParser;
  }
  
  synchronized SAXParser getSAXParser()
  {
    if (fSAXParser != null)
    {
      localObject = (SAXParser)fSAXParser.get();
      if (localObject != null) {
        return localObject;
      }
    }
    Object localObject = new XML11Configuration(fSymbolTable);
    ((XML11Configuration)localObject).setFeature("http://xml.org/sax/features/namespaces", true);
    ((XML11Configuration)localObject).setFeature("http://xml.org/sax/features/validation", false);
    SAXParser localSAXParser = new SAXParser((XMLParserConfiguration)localObject);
    fSAXParser = new SoftReference(localSAXParser);
    return localSAXParser;
  }
  
  public synchronized XSNamedMap getComponents(short paramShort)
  {
    if ((paramShort <= 0) || (paramShort > 16) || (GLOBAL_COMP[paramShort] == 0)) {
      return XSNamedMapImpl.EMPTY_MAP;
    }
    if (fComponents == null) {
      fComponents = new XSNamedMap[17];
    }
    if (fComponents[paramShort] == null)
    {
      SymbolHash localSymbolHash = null;
      switch (paramShort)
      {
      case 3: 
      case 15: 
      case 16: 
        localSymbolHash = fGlobalTypeDecls;
        break;
      case 1: 
        localSymbolHash = fGlobalAttrDecls;
        break;
      case 2: 
        localSymbolHash = fGlobalElemDecls;
        break;
      case 5: 
        localSymbolHash = fGlobalAttrGrpDecls;
        break;
      case 6: 
        localSymbolHash = fGlobalGroupDecls;
        break;
      case 11: 
        localSymbolHash = fGlobalNotationDecls;
      }
      if ((paramShort == 15) || (paramShort == 16)) {
        fComponents[paramShort] = new XSNamedMap4Types(fTargetNamespace, localSymbolHash, paramShort);
      } else {
        fComponents[paramShort] = new XSNamedMapImpl(fTargetNamespace, localSymbolHash);
      }
    }
    return fComponents[paramShort];
  }
  
  public XSTypeDefinition getTypeDefinition(String paramString)
  {
    return getGlobalTypeDecl(paramString);
  }
  
  public XSAttributeDeclaration getAttributeDeclaration(String paramString)
  {
    return getGlobalAttributeDecl(paramString);
  }
  
  public XSElementDeclaration getElementDeclaration(String paramString)
  {
    return getGlobalElementDecl(paramString);
  }
  
  public XSAttributeGroupDefinition getAttributeGroup(String paramString)
  {
    return getGlobalAttributeGroupDecl(paramString);
  }
  
  public XSModelGroupDefinition getModelGroupDefinition(String paramString)
  {
    return getGlobalGroupDecl(paramString);
  }
  
  public XSNotationDeclaration getNotationDeclaration(String paramString)
  {
    return getGlobalNotationDecl(paramString);
  }
  
  public StringList getDocumentLocations()
  {
    return new StringListImpl(fLocations);
  }
  
  public XSModel toXSModel()
  {
    return new XSModelImpl(new SchemaGrammar[] { this });
  }
  
  public XSModel toXSModel(XSGrammar[] paramArrayOfXSGrammar)
  {
    if ((paramArrayOfXSGrammar == null) || (paramArrayOfXSGrammar.length == 0)) {
      return toXSModel();
    }
    int i = paramArrayOfXSGrammar.length;
    int j = 0;
    for (int k = 0; k < i; k++) {
      if (paramArrayOfXSGrammar[k] == this)
      {
        j = 1;
        break;
      }
    }
    SchemaGrammar[] arrayOfSchemaGrammar = new SchemaGrammar[j != 0 ? i : i + 1];
    for (int m = 0; m < i; m++) {
      arrayOfSchemaGrammar[m] = ((SchemaGrammar)paramArrayOfXSGrammar[m]);
    }
    if (j == 0) {
      arrayOfSchemaGrammar[i] = this;
    }
    return new XSModelImpl(arrayOfSchemaGrammar);
  }
  
  public XSObjectList getAnnotations()
  {
    return new XSObjectListImpl(fAnnotations, fNumAnnotations);
  }
  
  public void addAnnotation(XSAnnotationImpl paramXSAnnotationImpl)
  {
    if (paramXSAnnotationImpl == null) {
      return;
    }
    if (fAnnotations == null)
    {
      fAnnotations = new XSAnnotationImpl[2];
    }
    else if (fNumAnnotations == fAnnotations.length)
    {
      XSAnnotationImpl[] arrayOfXSAnnotationImpl = new XSAnnotationImpl[fNumAnnotations << 1];
      System.arraycopy(fAnnotations, 0, arrayOfXSAnnotationImpl, 0, fNumAnnotations);
      fAnnotations = arrayOfXSAnnotationImpl;
    }
    fAnnotations[(fNumAnnotations++)] = paramXSAnnotationImpl;
  }
  
  private static class BuiltinAttrDecl
    extends XSAttributeDecl
  {
    public BuiltinAttrDecl(String paramString1, String paramString2, XSSimpleType paramXSSimpleType, short paramShort)
    {
      fName = paramString1;
      fTargetNamespace = paramString2;
      fType = paramXSSimpleType;
      fScope = paramShort;
    }
    
    public void setValues(String paramString1, String paramString2, XSSimpleType paramXSSimpleType, short paramShort1, short paramShort2, ValidatedInfo paramValidatedInfo, XSComplexTypeDecl paramXSComplexTypeDecl) {}
    
    public void reset() {}
    
    public XSAnnotation getAnnotation()
    {
      return null;
    }
  }
  
  private static class XSAnyType
    extends XSComplexTypeDecl
  {
    public XSAnyType()
    {
      fName = "anyType";
      fTargetNamespace = SchemaSymbols.URI_SCHEMAFORSCHEMA;
      fBaseType = this;
      fDerivedBy = 2;
      fContentType = 3;
      fParticle = null;
      fAttrGrp = null;
    }
    
    public void setValues(String paramString1, String paramString2, XSTypeDefinition paramXSTypeDefinition, short paramShort1, short paramShort2, short paramShort3, short paramShort4, boolean paramBoolean, XSAttributeGroupDecl paramXSAttributeGroupDecl, XSSimpleType paramXSSimpleType, XSParticleDecl paramXSParticleDecl) {}
    
    public void setName(String paramString) {}
    
    public void setIsAbstractType() {}
    
    public void setContainsTypeID() {}
    
    public void setIsAnonymous() {}
    
    public void reset() {}
    
    public XSObjectList getAttributeUses()
    {
      return new XSObjectListImpl(null, 0);
    }
    
    public XSAttributeGroupDecl getAttrGrp()
    {
      XSWildcardDecl localXSWildcardDecl = new XSWildcardDecl();
      fProcessContents = 3;
      XSAttributeGroupDecl localXSAttributeGroupDecl = new XSAttributeGroupDecl();
      fAttributeWC = localXSWildcardDecl;
      return localXSAttributeGroupDecl;
    }
    
    public XSWildcard getAttributeWildcard()
    {
      XSWildcardDecl localXSWildcardDecl = new XSWildcardDecl();
      fProcessContents = 3;
      return localXSWildcardDecl;
    }
    
    public XSParticle getParticle()
    {
      XSWildcardDecl localXSWildcardDecl = new XSWildcardDecl();
      fProcessContents = 3;
      XSParticleDecl localXSParticleDecl1 = new XSParticleDecl();
      fMinOccurs = 0;
      fMaxOccurs = -1;
      fType = 2;
      fValue = localXSWildcardDecl;
      XSModelGroupImpl localXSModelGroupImpl = new XSModelGroupImpl();
      fCompositor = 102;
      fParticleCount = 1;
      fParticles = new XSParticleDecl[1];
      fParticles[0] = localXSParticleDecl1;
      XSParticleDecl localXSParticleDecl2 = new XSParticleDecl();
      fType = 3;
      fValue = localXSModelGroupImpl;
      return localXSParticleDecl2;
    }
    
    public XSObjectList getAnnotations()
    {
      return null;
    }
  }
  
  public static final class Schema4Annotations
    extends SchemaGrammar
  {
    public Schema4Annotations()
    {
      fTargetNamespace = SchemaSymbols.URI_SCHEMAFORSCHEMA;
      fGrammarDescription = new XSDDescription();
      fGrammarDescription.fContextType = 3;
      fGrammarDescription.setNamespace(SchemaSymbols.URI_SCHEMAFORSCHEMA);
      fGlobalAttrDecls = new SymbolHash(1);
      fGlobalAttrGrpDecls = new SymbolHash(1);
      fGlobalElemDecls = new SymbolHash(6);
      fGlobalGroupDecls = new SymbolHash(1);
      fGlobalNotationDecls = new SymbolHash(1);
      fGlobalIDConstraintDecls = new SymbolHash(1);
      fGlobalTypeDecls = SG_SchemaNSfGlobalTypeDecls;
      XSElementDecl localXSElementDecl1 = createAnnotationElementDecl(SchemaSymbols.ELT_ANNOTATION);
      XSElementDecl localXSElementDecl2 = createAnnotationElementDecl(SchemaSymbols.ELT_DOCUMENTATION);
      XSElementDecl localXSElementDecl3 = createAnnotationElementDecl(SchemaSymbols.ELT_APPINFO);
      fGlobalElemDecls.put(fName, localXSElementDecl1);
      fGlobalElemDecls.put(fName, localXSElementDecl2);
      fGlobalElemDecls.put(fName, localXSElementDecl3);
      XSComplexTypeDecl localXSComplexTypeDecl1 = new XSComplexTypeDecl();
      XSComplexTypeDecl localXSComplexTypeDecl2 = new XSComplexTypeDecl();
      XSComplexTypeDecl localXSComplexTypeDecl3 = new XSComplexTypeDecl();
      fType = localXSComplexTypeDecl1;
      fType = localXSComplexTypeDecl2;
      fType = localXSComplexTypeDecl3;
      XSAttributeGroupDecl localXSAttributeGroupDecl1 = new XSAttributeGroupDecl();
      XSAttributeGroupDecl localXSAttributeGroupDecl2 = new XSAttributeGroupDecl();
      XSAttributeGroupDecl localXSAttributeGroupDecl3 = new XSAttributeGroupDecl();
      Object localObject1 = new XSAttributeUseImpl();
      fAttrDecl = new XSAttributeDecl();
      fAttrDecl.setValues(SchemaSymbols.ATT_ID, null, (XSSimpleType)fGlobalTypeDecls.get("ID"), (short)0, (short)2, null, localXSComplexTypeDecl1, null);
      fUse = 0;
      fConstraintType = 0;
      Object localObject2 = new XSAttributeUseImpl();
      fAttrDecl = new XSAttributeDecl();
      fAttrDecl.setValues(SchemaSymbols.ATT_SOURCE, null, (XSSimpleType)fGlobalTypeDecls.get("anyURI"), (short)0, (short)2, null, localXSComplexTypeDecl2, null);
      fUse = 0;
      fConstraintType = 0;
      XSAttributeUseImpl localXSAttributeUseImpl1 = new XSAttributeUseImpl();
      fAttrDecl = new XSAttributeDecl();
      fAttrDecl.setValues("lang".intern(), NamespaceContext.XML_URI, (XSSimpleType)fGlobalTypeDecls.get("language"), (short)0, (short)2, null, localXSComplexTypeDecl2, null);
      fUse = 0;
      fConstraintType = 0;
      XSAttributeUseImpl localXSAttributeUseImpl2 = new XSAttributeUseImpl();
      fAttrDecl = new XSAttributeDecl();
      fAttrDecl.setValues(SchemaSymbols.ATT_SOURCE, null, (XSSimpleType)fGlobalTypeDecls.get("anyURI"), (short)0, (short)2, null, localXSComplexTypeDecl3, null);
      fUse = 0;
      fConstraintType = 0;
      XSWildcardDecl localXSWildcardDecl = new XSWildcardDecl();
      fNamespaceList = new String[] { fTargetNamespace, null };
      fType = 2;
      fProcessContents = 3;
      localXSAttributeGroupDecl1.addAttributeUse((XSAttributeUseImpl)localObject1);
      fAttributeWC = localXSWildcardDecl;
      localXSAttributeGroupDecl2.addAttributeUse((XSAttributeUseImpl)localObject2);
      localXSAttributeGroupDecl2.addAttributeUse(localXSAttributeUseImpl1);
      fAttributeWC = localXSWildcardDecl;
      localXSAttributeGroupDecl3.addAttributeUse(localXSAttributeUseImpl2);
      fAttributeWC = localXSWildcardDecl;
      localObject1 = createUnboundedModelGroupParticle();
      localObject2 = new XSModelGroupImpl();
      fCompositor = 101;
      fParticleCount = 2;
      fParticles = new XSParticleDecl[2];
      fParticles[0] = createChoiceElementParticle(localXSElementDecl3);
      fParticles[1] = createChoiceElementParticle(localXSElementDecl2);
      fValue = ((XSTerm)localObject2);
      localObject2 = createUnboundedAnyWildcardSequenceParticle();
      localXSComplexTypeDecl1.setValues("#AnonType_" + SchemaSymbols.ELT_ANNOTATION, fTargetNamespace, SchemaGrammar.fAnyType, (short)2, (short)0, (short)3, (short)2, false, localXSAttributeGroupDecl1, null, (XSParticleDecl)localObject1, new XSObjectListImpl(null, 0));
      localXSComplexTypeDecl1.setName("#AnonType_" + SchemaSymbols.ELT_ANNOTATION);
      localXSComplexTypeDecl1.setIsAnonymous();
      localXSComplexTypeDecl2.setValues("#AnonType_" + SchemaSymbols.ELT_DOCUMENTATION, fTargetNamespace, SchemaGrammar.fAnyType, (short)2, (short)0, (short)3, (short)3, false, localXSAttributeGroupDecl2, null, (XSParticleDecl)localObject2, new XSObjectListImpl(null, 0));
      localXSComplexTypeDecl2.setName("#AnonType_" + SchemaSymbols.ELT_DOCUMENTATION);
      localXSComplexTypeDecl2.setIsAnonymous();
      localXSComplexTypeDecl3.setValues("#AnonType_" + SchemaSymbols.ELT_APPINFO, fTargetNamespace, SchemaGrammar.fAnyType, (short)2, (short)0, (short)3, (short)3, false, localXSAttributeGroupDecl3, null, (XSParticleDecl)localObject2, new XSObjectListImpl(null, 0));
      localXSComplexTypeDecl3.setName("#AnonType_" + SchemaSymbols.ELT_APPINFO);
      localXSComplexTypeDecl3.setIsAnonymous();
    }
    
    public XMLGrammarDescription getGrammarDescription()
    {
      return fGrammarDescription.makeClone();
    }
    
    public void setImportedGrammars(Vector paramVector) {}
    
    public void addGlobalAttributeDecl(XSAttributeDecl paramXSAttributeDecl) {}
    
    public void addGlobalAttributeGroupDecl(XSAttributeGroupDecl paramXSAttributeGroupDecl) {}
    
    public void addGlobalElementDecl(XSElementDecl paramXSElementDecl) {}
    
    public void addGlobalGroupDecl(XSGroupDecl paramXSGroupDecl) {}
    
    public void addGlobalNotationDecl(XSNotationDecl paramXSNotationDecl) {}
    
    public void addGlobalTypeDecl(XSTypeDefinition paramXSTypeDefinition) {}
    
    public void addComplexTypeDecl(XSComplexTypeDecl paramXSComplexTypeDecl, SimpleLocator paramSimpleLocator) {}
    
    public void addRedefinedGroupDecl(XSGroupDecl paramXSGroupDecl1, XSGroupDecl paramXSGroupDecl2, SimpleLocator paramSimpleLocator) {}
    
    public synchronized void addDocument(Object paramObject, String paramString) {}
    
    synchronized DOMParser getDOMParser()
    {
      return null;
    }
    
    synchronized SAXParser getSAXParser()
    {
      return null;
    }
    
    private XSElementDecl createAnnotationElementDecl(String paramString)
    {
      XSElementDecl localXSElementDecl = new XSElementDecl();
      fName = paramString;
      fTargetNamespace = fTargetNamespace;
      localXSElementDecl.setIsGlobal();
      fBlock = 7;
      localXSElementDecl.setConstraintType((short)0);
      return localXSElementDecl;
    }
    
    private XSParticleDecl createUnboundedModelGroupParticle()
    {
      XSParticleDecl localXSParticleDecl = new XSParticleDecl();
      fMinOccurs = 0;
      fMaxOccurs = -1;
      fType = 3;
      return localXSParticleDecl;
    }
    
    private XSParticleDecl createChoiceElementParticle(XSElementDecl paramXSElementDecl)
    {
      XSParticleDecl localXSParticleDecl = new XSParticleDecl();
      fMinOccurs = 1;
      fMaxOccurs = 1;
      fType = 1;
      fValue = paramXSElementDecl;
      return localXSParticleDecl;
    }
    
    private XSParticleDecl createUnboundedAnyWildcardSequenceParticle()
    {
      XSParticleDecl localXSParticleDecl = createUnboundedModelGroupParticle();
      XSModelGroupImpl localXSModelGroupImpl = new XSModelGroupImpl();
      fCompositor = 102;
      fParticleCount = 1;
      fParticles = new XSParticleDecl[1];
      fParticles[0] = createAnyLaxWildcardParticle();
      fValue = localXSModelGroupImpl;
      return localXSParticleDecl;
    }
    
    private XSParticleDecl createAnyLaxWildcardParticle()
    {
      XSParticleDecl localXSParticleDecl = new XSParticleDecl();
      fMinOccurs = 1;
      fMaxOccurs = 1;
      fType = 2;
      XSWildcardDecl localXSWildcardDecl = new XSWildcardDecl();
      fNamespaceList = null;
      fType = 1;
      fProcessContents = 3;
      fValue = localXSWildcardDecl;
      return localXSParticleDecl;
    }
  }
  
  public static class BuiltinSchemaGrammar
    extends SchemaGrammar
  {
    public BuiltinSchemaGrammar(int paramInt)
    {
      SchemaDVFactory localSchemaDVFactory = SchemaDVFactory.getInstance();
      if (paramInt == 1)
      {
        fTargetNamespace = SchemaSymbols.URI_SCHEMAFORSCHEMA;
        fGrammarDescription = new XSDDescription();
        fGrammarDescription.fContextType = 3;
        fGrammarDescription.setNamespace(SchemaSymbols.URI_SCHEMAFORSCHEMA);
        fGlobalAttrDecls = new SymbolHash(1);
        fGlobalAttrGrpDecls = new SymbolHash(1);
        fGlobalElemDecls = new SymbolHash(1);
        fGlobalGroupDecls = new SymbolHash(1);
        fGlobalNotationDecls = new SymbolHash(1);
        fGlobalIDConstraintDecls = new SymbolHash(1);
        fGlobalTypeDecls = localSchemaDVFactory.getBuiltInTypes();
        fGlobalTypeDecls.put(SchemaGrammar.fAnyType.getName(), SchemaGrammar.fAnyType);
      }
      else if (paramInt == 2)
      {
        fTargetNamespace = SchemaSymbols.URI_XSI;
        fGrammarDescription = new XSDDescription();
        fGrammarDescription.fContextType = 3;
        fGrammarDescription.setNamespace(SchemaSymbols.URI_XSI);
        fGlobalAttrGrpDecls = new SymbolHash(1);
        fGlobalElemDecls = new SymbolHash(1);
        fGlobalGroupDecls = new SymbolHash(1);
        fGlobalNotationDecls = new SymbolHash(1);
        fGlobalIDConstraintDecls = new SymbolHash(1);
        fGlobalTypeDecls = new SymbolHash(1);
        fGlobalAttrDecls = new SymbolHash(8);
        String str1 = null;
        String str2 = null;
        Object localObject = null;
        short s = 1;
        str1 = SchemaSymbols.XSI_TYPE;
        str2 = SchemaSymbols.URI_XSI;
        localObject = localSchemaDVFactory.getBuiltInType("QName");
        fGlobalAttrDecls.put(str1, new SchemaGrammar.BuiltinAttrDecl(str1, str2, (XSSimpleType)localObject, s));
        str1 = SchemaSymbols.XSI_NIL;
        str2 = SchemaSymbols.URI_XSI;
        localObject = localSchemaDVFactory.getBuiltInType("boolean");
        fGlobalAttrDecls.put(str1, new SchemaGrammar.BuiltinAttrDecl(str1, str2, (XSSimpleType)localObject, s));
        XSSimpleType localXSSimpleType = localSchemaDVFactory.getBuiltInType("anyURI");
        str1 = SchemaSymbols.XSI_SCHEMALOCATION;
        str2 = SchemaSymbols.URI_XSI;
        localObject = localSchemaDVFactory.createTypeList(null, SchemaSymbols.URI_XSI, (short)0, localXSSimpleType, null);
        fGlobalAttrDecls.put(str1, new SchemaGrammar.BuiltinAttrDecl(str1, str2, (XSSimpleType)localObject, s));
        str1 = SchemaSymbols.XSI_NONAMESPACESCHEMALOCATION;
        str2 = SchemaSymbols.URI_XSI;
        localObject = localXSSimpleType;
        fGlobalAttrDecls.put(str1, new SchemaGrammar.BuiltinAttrDecl(str1, str2, (XSSimpleType)localObject, s));
      }
    }
    
    public XMLGrammarDescription getGrammarDescription()
    {
      return fGrammarDescription.makeClone();
    }
    
    public void setImportedGrammars(Vector paramVector) {}
    
    public void addGlobalAttributeDecl(XSAttributeDecl paramXSAttributeDecl) {}
    
    public void addGlobalAttributeGroupDecl(XSAttributeGroupDecl paramXSAttributeGroupDecl) {}
    
    public void addGlobalElementDecl(XSElementDecl paramXSElementDecl) {}
    
    public void addGlobalGroupDecl(XSGroupDecl paramXSGroupDecl) {}
    
    public void addGlobalNotationDecl(XSNotationDecl paramXSNotationDecl) {}
    
    public void addGlobalTypeDecl(XSTypeDefinition paramXSTypeDefinition) {}
    
    public void addComplexTypeDecl(XSComplexTypeDecl paramXSComplexTypeDecl, SimpleLocator paramSimpleLocator) {}
    
    public void addRedefinedGroupDecl(XSGroupDecl paramXSGroupDecl1, XSGroupDecl paramXSGroupDecl2, SimpleLocator paramSimpleLocator) {}
    
    public synchronized void addDocument(Object paramObject, String paramString) {}
    
    synchronized DOMParser getDOMParser()
    {
      return null;
    }
    
    synchronized SAXParser getSAXParser()
    {
      return null;
    }
  }
}
