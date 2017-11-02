package org.apache.xerces.impl.xs.traversers;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;
import java.util.Vector;
import org.apache.xerces.impl.XMLEntityManager;
import org.apache.xerces.impl.XMLErrorReporter;
import org.apache.xerces.impl.xs.SchemaGrammar;
import org.apache.xerces.impl.xs.SchemaNamespaceSupport;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.apache.xerces.impl.xs.XMLSchemaException;
import org.apache.xerces.impl.xs.XMLSchemaLoader;
import org.apache.xerces.impl.xs.XSComplexTypeDecl;
import org.apache.xerces.impl.xs.XSDDescription;
import org.apache.xerces.impl.xs.XSDeclarationPool;
import org.apache.xerces.impl.xs.XSElementDecl;
import org.apache.xerces.impl.xs.XSGrammarBucket;
import org.apache.xerces.impl.xs.XSGroupDecl;
import org.apache.xerces.impl.xs.XSModelGroupImpl;
import org.apache.xerces.impl.xs.XSParticleDecl;
import org.apache.xerces.impl.xs.opti.DefaultDocument;
import org.apache.xerces.impl.xs.opti.ElementImpl;
import org.apache.xerces.impl.xs.opti.SchemaDOM;
import org.apache.xerces.impl.xs.opti.SchemaDOMParser;
import org.apache.xerces.impl.xs.opti.SchemaParsingConfig;
import org.apache.xerces.impl.xs.util.SimpleLocator;
import org.apache.xerces.parsers.SAXParser;
import org.apache.xerces.parsers.XML11Configuration;
import org.apache.xerces.util.DOMInputSource;
import org.apache.xerces.util.DOMUtil;
import org.apache.xerces.util.DefaultErrorHandler;
import org.apache.xerces.util.SAXInputSource;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.URI.MalformedURIException;
import org.apache.xerces.util.XMLResourceIdentifierImpl;
import org.apache.xerces.util.XMLSymbols;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.grammars.Grammar;
import org.apache.xerces.xni.grammars.XMLGrammarDescription;
import org.apache.xerces.xni.grammars.XMLGrammarPool;
import org.apache.xerces.xni.grammars.XMLSchemaDescription;
import org.apache.xerces.xni.parser.XMLComponentManager;
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.apache.xerces.xni.parser.XMLEntityResolver;
import org.apache.xerces.xni.parser.XMLErrorHandler;
import org.apache.xerces.xni.parser.XMLInputSource;
import org.apache.xerces.xs.StringList;
import org.apache.xerces.xs.XSObject;
import org.apache.xerces.xs.XSParticle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class XSDHandler
{
  protected static final String VALIDATION = "http://xml.org/sax/features/validation";
  protected static final String XMLSCHEMA_VALIDATION = "http://apache.org/xml/features/validation/schema";
  protected static final String ALLOW_JAVA_ENCODINGS = "http://apache.org/xml/features/allow-java-encodings";
  protected static final String CONTINUE_AFTER_FATAL_ERROR = "http://apache.org/xml/features/continue-after-fatal-error";
  protected static final String STANDARD_URI_CONFORMANT_FEATURE = "http://apache.org/xml/features/standard-uri-conformant";
  protected static final String DISALLOW_DOCTYPE = "http://apache.org/xml/features/disallow-doctype-decl";
  protected static final String GENERATE_SYNTHETIC_ANNOTATIONS = "http://apache.org/xml/features/generate-synthetic-annotations";
  protected static final String VALIDATE_ANNOTATIONS = "http://apache.org/xml/features/validate-annotations";
  protected static final String HONOUR_ALL_SCHEMALOCATIONS = "http://apache.org/xml/features/honour-all-schemaLocations";
  private static final String NAMESPACE_PREFIXES = "http://xml.org/sax/features/namespace-prefixes";
  protected static final String STRING_INTERNING = "http://xml.org/sax/features/string-interning";
  protected static final String ERROR_HANDLER = "http://apache.org/xml/properties/internal/error-handler";
  protected static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";
  public static final String ENTITY_RESOLVER = "http://apache.org/xml/properties/internal/entity-resolver";
  protected static final String ENTITY_MANAGER = "http://apache.org/xml/properties/internal/entity-manager";
  public static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
  public static final String XMLGRAMMAR_POOL = "http://apache.org/xml/properties/internal/grammar-pool";
  public static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
  protected static final String SECURITY_MANAGER = "http://apache.org/xml/properties/security-manager";
  protected static final boolean DEBUG_NODE_POOL = false;
  static final int ATTRIBUTE_TYPE = 1;
  static final int ATTRIBUTEGROUP_TYPE = 2;
  static final int ELEMENT_TYPE = 3;
  static final int GROUP_TYPE = 4;
  static final int IDENTITYCONSTRAINT_TYPE = 5;
  static final int NOTATION_TYPE = 6;
  static final int TYPEDECL_TYPE = 7;
  public static final String REDEF_IDENTIFIER = "_fn3dktizrknc9pi";
  protected Hashtable fNotationRegistry = new Hashtable();
  protected XSDeclarationPool fDeclPool = null;
  private Hashtable fUnparsedAttributeRegistry = new Hashtable();
  private Hashtable fUnparsedAttributeGroupRegistry = new Hashtable();
  private Hashtable fUnparsedElementRegistry = new Hashtable();
  private Hashtable fUnparsedGroupRegistry = new Hashtable();
  private Hashtable fUnparsedIdentityConstraintRegistry = new Hashtable();
  private Hashtable fUnparsedNotationRegistry = new Hashtable();
  private Hashtable fUnparsedTypeRegistry = new Hashtable();
  private Hashtable fUnparsedAttributeRegistrySub = new Hashtable();
  private Hashtable fUnparsedAttributeGroupRegistrySub = new Hashtable();
  private Hashtable fUnparsedElementRegistrySub = new Hashtable();
  private Hashtable fUnparsedGroupRegistrySub = new Hashtable();
  private Hashtable fUnparsedIdentityConstraintRegistrySub = new Hashtable();
  private Hashtable fUnparsedNotationRegistrySub = new Hashtable();
  private Hashtable fUnparsedTypeRegistrySub = new Hashtable();
  private Hashtable fXSDocumentInfoRegistry = new Hashtable();
  private Hashtable fDependencyMap = new Hashtable();
  private Hashtable fImportMap = new Hashtable();
  private Vector fAllTNSs = new Vector();
  private Hashtable fLocationPairs = null;
  private static final Hashtable EMPTY_TABLE = new Hashtable();
  private Hashtable fHiddenNodes = null;
  private Hashtable fTraversed = new Hashtable();
  private Hashtable fDoc2SystemId = new Hashtable();
  private XSDocumentInfo fRoot = null;
  private Hashtable fDoc2XSDocumentMap = new Hashtable();
  private Hashtable fRedefine2XSDMap = new Hashtable();
  private Hashtable fRedefine2NSSupport = new Hashtable();
  private Hashtable fRedefinedRestrictedAttributeGroupRegistry = new Hashtable();
  private Hashtable fRedefinedRestrictedGroupRegistry = new Hashtable();
  private boolean fLastSchemaWasDuplicate;
  private boolean fValidateAnnotations = false;
  private boolean fHonourAllSchemaLocations = false;
  private XMLErrorReporter fErrorReporter;
  private XMLEntityResolver fEntityResolver;
  private XSAttributeChecker fAttributeChecker;
  private SymbolTable fSymbolTable;
  private XSGrammarBucket fGrammarBucket;
  private XSDDescription fSchemaGrammarDescription;
  private XMLGrammarPool fGrammarPool;
  XSDAttributeGroupTraverser fAttributeGroupTraverser;
  XSDAttributeTraverser fAttributeTraverser;
  XSDComplexTypeTraverser fComplexTypeTraverser;
  XSDElementTraverser fElementTraverser;
  XSDGroupTraverser fGroupTraverser;
  XSDKeyrefTraverser fKeyrefTraverser;
  XSDNotationTraverser fNotationTraverser;
  XSDSimpleTypeTraverser fSimpleTypeTraverser;
  XSDUniqueOrKeyTraverser fUniqueOrKeyTraverser;
  XSDWildcardTraverser fWildCardTraverser;
  SchemaDOMParser fSchemaParser = new SchemaDOMParser(new SchemaParsingConfig());
  SchemaContentHandler fXSContentHandler;
  XML11Configuration fAnnotationValidator;
  XSAnnotationGrammarPool fGrammarBucketAdapter;
  private static final int INIT_STACK_SIZE = 30;
  private static final int INC_STACK_SIZE = 10;
  private int fLocalElemStackPos = 0;
  private XSParticleDecl[] fParticle = new XSParticleDecl[30];
  private Element[] fLocalElementDecl = new Element[30];
  private XSDocumentInfo[] fLocalElementDecl_schema = new XSDocumentInfo[30];
  private int[] fAllContext = new int[30];
  private XSObject[] fParent = new XSObject[30];
  private String[][] fLocalElemNamespaceContext = new String[30][1];
  private static final int INIT_KEYREF_STACK = 2;
  private static final int INC_KEYREF_STACK_AMOUNT = 2;
  private int fKeyrefStackPos = 0;
  private Element[] fKeyrefs = new Element[2];
  private XSDocumentInfo[] fKeyrefsMapXSDocumentInfo = new XSDocumentInfo[2];
  private XSElementDecl[] fKeyrefElems = new XSElementDecl[2];
  private String[][] fKeyrefNamespaceContext = new String[2][1];
  private static final String[][] NS_ERROR_CODES = { { "src-include.2.1", "src-include.2.1" }, { "src-redefine.3.1", "src-redefine.3.1" }, { "src-import.3.1", "src-import.3.2" }, null, { "TargetNamespace.1", "TargetNamespace.2" }, { "TargetNamespace.1", "TargetNamespace.2" }, { "TargetNamespace.1", "TargetNamespace.2" }, { "TargetNamespace.1", "TargetNamespace.2" } };
  private static final String[] ELE_ERROR_CODES = { "src-include.1", "src-redefine.2", "src-import.2", "schema_reference.4", "schema_reference.4", "schema_reference.4", "schema_reference.4", "schema_reference.4" };
  private Vector fReportedTNS = null;
  private static final String[] COMP_TYPE = { null, "attribute declaration", "attribute group", "element declaration", "group", "identity constraint", "notation", "type definition" };
  private static final String[] CIRCULAR_CODES = { "Internal-Error", "Internal-Error", "src-attribute_group.3", "e-props-correct.6", "mg-props-correct.2", "Internal-Error", "Internal-Error", "st-props-correct.2" };
  private SimpleLocator xl = new SimpleLocator();
  
  private String null2EmptyString(String paramString)
  {
    return paramString == null ? XMLSymbols.EMPTY_STRING : paramString;
  }
  
  private String emptyString2Null(String paramString)
  {
    return paramString == XMLSymbols.EMPTY_STRING ? null : paramString;
  }
  
  private String doc2SystemId(Element paramElement)
  {
    String str = null;
    if ((paramElement.getOwnerDocument() instanceof SchemaDOM)) {
      str = ((SchemaDOM)paramElement.getOwnerDocument()).getDocumentURI();
    }
    return str != null ? str : (String)fDoc2SystemId.get(paramElement);
  }
  
  public XSDHandler() {}
  
  public XSDHandler(XSGrammarBucket paramXSGrammarBucket)
  {
    this();
    fGrammarBucket = paramXSGrammarBucket;
    fSchemaGrammarDescription = new XSDDescription();
  }
  
  public SchemaGrammar parseSchema(XMLInputSource paramXMLInputSource, XSDDescription paramXSDDescription, Hashtable paramHashtable)
    throws IOException
  {
    fLocationPairs = paramHashtable;
    fSchemaParser.resetNodePool();
    SchemaGrammar localSchemaGrammar1 = null;
    String str = null;
    short s = paramXSDDescription.getContextType();
    if (s != 3)
    {
      if ((fHonourAllSchemaLocations) && (s == 2) && (isExistingGrammar(paramXSDDescription))) {
        localSchemaGrammar1 = fGrammarBucket.getGrammar(paramXSDDescription.getTargetNamespace());
      } else {
        localSchemaGrammar1 = findGrammar(paramXSDDescription);
      }
      if (localSchemaGrammar1 != null) {
        return localSchemaGrammar1;
      }
      str = paramXSDDescription.getTargetNamespace();
      if (str != null) {
        str = fSymbolTable.addSymbol(str);
      }
    }
    prepareForParse();
    Document localDocument = null;
    Element localElement = null;
    Object localObject2;
    if ((paramXMLInputSource instanceof DOMInputSource))
    {
      fHiddenNodes.clear();
      localObject1 = ((DOMInputSource)paramXMLInputSource).getNode();
      if ((localObject1 instanceof Document))
      {
        localDocument = (Document)localObject1;
        localElement = DOMUtil.getRoot(localDocument);
      }
      else if ((localObject1 instanceof Element))
      {
        localElement = (Element)localObject1;
      }
      else
      {
        return null;
      }
    }
    else if ((paramXMLInputSource instanceof SAXInputSource))
    {
      localObject1 = ((SAXInputSource)paramXMLInputSource).getXMLReader();
      localObject2 = ((SAXInputSource)paramXMLInputSource).getInputSource();
      boolean bool1 = false;
      if (localObject1 != null)
      {
        try
        {
          bool1 = ((XMLReader)localObject1).getFeature("http://xml.org/sax/features/namespace-prefixes");
        }
        catch (SAXException localSAXException1) {}
      }
      else
      {
        try
        {
          localObject1 = XMLReaderFactory.createXMLReader();
        }
        catch (SAXException localSAXException2)
        {
          localObject1 = new SAXParser();
        }
        try
        {
          ((XMLReader)localObject1).setFeature("http://xml.org/sax/features/namespace-prefixes", true);
          bool1 = true;
        }
        catch (SAXException localSAXException3) {}
      }
      boolean bool2 = false;
      try
      {
        bool2 = ((XMLReader)localObject1).getFeature("http://xml.org/sax/features/string-interning");
      }
      catch (SAXException localSAXException4) {}
      if (fXSContentHandler == null) {
        fXSContentHandler = new SchemaContentHandler();
      }
      fXSContentHandler.reset(fSchemaParser, fSymbolTable, bool1, bool2);
      ((XMLReader)localObject1).setContentHandler(fXSContentHandler);
      ((XMLReader)localObject1).setErrorHandler(fErrorReporter.getSAXErrorHandler());
      try
      {
        ((XMLReader)localObject1).parse((InputSource)localObject2);
      }
      catch (SAXException localSAXException5)
      {
        return null;
      }
      localDocument = fXSContentHandler.getDocument();
      if (localDocument == null) {
        return null;
      }
      localElement = DOMUtil.getRoot(localDocument);
    }
    else
    {
      localElement = getSchemaDocument(str, paramXMLInputSource, s == 3, s, null);
    }
    if (localElement == null) {
      return null;
    }
    Object localObject3;
    if (s == 3)
    {
      localObject1 = localElement;
      str = DOMUtil.getAttrValue((Element)localObject1, SchemaSymbols.ATT_TARGETNAMESPACE);
      if ((str != null) && (str.length() > 0))
      {
        str = fSymbolTable.addSymbol(str);
        paramXSDDescription.setTargetNamespace(str);
      }
      else
      {
        str = null;
      }
      localSchemaGrammar1 = findGrammar(paramXSDDescription);
      if (localSchemaGrammar1 != null) {
        return localSchemaGrammar1;
      }
      localObject2 = XMLEntityManager.expandSystemId(paramXMLInputSource.getSystemId(), paramXMLInputSource.getBaseSystemId(), false);
      localObject3 = new XSDKey((String)localObject2, s, str);
      fTraversed.put(localObject3, localElement);
      if (localObject2 != null) {
        fDoc2SystemId.put(localElement, localObject2);
      }
    }
    prepareForTraverse();
    fRoot = constructTrees(localElement, paramXMLInputSource.getSystemId(), paramXSDDescription);
    if (fRoot == null) {
      return null;
    }
    buildGlobalNameRegistries();
    Object localObject1 = fValidateAnnotations ? new ArrayList() : null;
    traverseSchemas((ArrayList)localObject1);
    traverseLocalElements();
    resolveKeyRefs();
    for (int i = fAllTNSs.size() - 1; i >= 0; i--)
    {
      localObject3 = (String)fAllTNSs.elementAt(i);
      Vector localVector = (Vector)fImportMap.get(localObject3);
      SchemaGrammar localSchemaGrammar2 = fGrammarBucket.getGrammar(emptyString2Null((String)localObject3));
      if (localSchemaGrammar2 != null)
      {
        int j = 0;
        for (int k = 0; k < localVector.size(); k++)
        {
          SchemaGrammar localSchemaGrammar3 = fGrammarBucket.getGrammar((String)localVector.elementAt(k));
          if (localSchemaGrammar3 != null) {
            localVector.setElementAt(localSchemaGrammar3, j++);
          }
        }
        localVector.setSize(j);
        localSchemaGrammar2.setImportedGrammars(localVector);
      }
    }
    if ((fValidateAnnotations) && (((ArrayList)localObject1).size() > 0)) {
      validateAnnotations((ArrayList)localObject1);
    }
    return fGrammarBucket.getGrammar(fRoot.fTargetNamespace);
  }
  
  private void validateAnnotations(ArrayList paramArrayList)
  {
    if (fAnnotationValidator == null) {
      createAnnotationValidator();
    }
    int i = paramArrayList.size();
    XMLInputSource localXMLInputSource = new XMLInputSource(null, null, null);
    fGrammarBucketAdapter.refreshGrammars(fGrammarBucket);
    for (int j = 0; j < i; j += 2)
    {
      localXMLInputSource.setSystemId((String)paramArrayList.get(j));
      for (XSAnnotationInfo localXSAnnotationInfo = (XSAnnotationInfo)paramArrayList.get(j + 1); localXSAnnotationInfo != null; localXSAnnotationInfo = next)
      {
        localXMLInputSource.setCharacterStream(new StringReader(fAnnotation));
        try
        {
          fAnnotationValidator.parse(localXMLInputSource);
        }
        catch (IOException localIOException) {}
      }
    }
  }
  
  private void createAnnotationValidator()
  {
    fAnnotationValidator = new XML11Configuration();
    fGrammarBucketAdapter = new XSAnnotationGrammarPool(null);
    fAnnotationValidator.setFeature("http://xml.org/sax/features/validation", true);
    fAnnotationValidator.setFeature("http://apache.org/xml/features/validation/schema", true);
    fAnnotationValidator.setProperty("http://apache.org/xml/properties/internal/grammar-pool", fGrammarBucketAdapter);
    XMLErrorHandler localXMLErrorHandler = fErrorReporter.getErrorHandler();
    fAnnotationValidator.setProperty("http://apache.org/xml/properties/internal/error-handler", localXMLErrorHandler != null ? localXMLErrorHandler : new DefaultErrorHandler());
  }
  
  SchemaGrammar getGrammar(String paramString)
  {
    return fGrammarBucket.getGrammar(paramString);
  }
  
  protected SchemaGrammar findGrammar(XSDDescription paramXSDDescription)
  {
    SchemaGrammar localSchemaGrammar = fGrammarBucket.getGrammar(paramXSDDescription.getTargetNamespace());
    if ((localSchemaGrammar == null) && (fGrammarPool != null))
    {
      localSchemaGrammar = (SchemaGrammar)fGrammarPool.retrieveGrammar(paramXSDDescription);
      if ((localSchemaGrammar != null) && (!fGrammarBucket.putGrammar(localSchemaGrammar, true)))
      {
        reportSchemaWarning("GrammarConflict", null, null);
        localSchemaGrammar = null;
      }
    }
    return localSchemaGrammar;
  }
  
  protected XSDocumentInfo constructTrees(Element paramElement, String paramString, XSDDescription paramXSDDescription)
  {
    if (paramElement == null) {
      return null;
    }
    String str1 = paramXSDDescription.getTargetNamespace();
    int i = paramXSDDescription.getContextType();
    XSDocumentInfo localXSDocumentInfo = null;
    try
    {
      localXSDocumentInfo = new XSDocumentInfo(paramElement, fAttributeChecker, fSymbolTable);
    }
    catch (XMLSchemaException localXMLSchemaException)
    {
      reportSchemaError(ELE_ERROR_CODES[i], new Object[] { paramString }, paramElement);
      return null;
    }
    if ((fTargetNamespace != null) && (fTargetNamespace.length() == 0))
    {
      reportSchemaWarning("EmptyTargetNamespace", new Object[] { paramString }, paramElement);
      fTargetNamespace = null;
    }
    int j;
    if (str1 != null)
    {
      j = 0;
      if ((i == 0) || (i == 1))
      {
        if (fTargetNamespace == null)
        {
          fTargetNamespace = str1;
          fIsChameleonSchema = true;
        }
        else if (str1 != fTargetNamespace)
        {
          reportSchemaError(NS_ERROR_CODES[i][j], new Object[] { str1, fTargetNamespace }, paramElement);
          return null;
        }
      }
      else if ((i != 3) && (str1 != fTargetNamespace))
      {
        reportSchemaError(NS_ERROR_CODES[i][j], new Object[] { str1, fTargetNamespace }, paramElement);
        return null;
      }
    }
    else if (fTargetNamespace != null)
    {
      if (i == 3)
      {
        paramXSDDescription.setTargetNamespace(fTargetNamespace);
        str1 = fTargetNamespace;
      }
      else
      {
        j = 1;
        reportSchemaError(NS_ERROR_CODES[i][j], new Object[] { str1, fTargetNamespace }, paramElement);
        return null;
      }
    }
    localXSDocumentInfo.addAllowedNS(fTargetNamespace);
    SchemaGrammar localSchemaGrammar = null;
    if ((i == 0) || (i == 1))
    {
      localSchemaGrammar = fGrammarBucket.getGrammar(fTargetNamespace);
    }
    else if ((fHonourAllSchemaLocations) && (i == 2))
    {
      localSchemaGrammar = findGrammar(paramXSDDescription);
      if (localSchemaGrammar == null)
      {
        localSchemaGrammar = new SchemaGrammar(fTargetNamespace, paramXSDDescription.makeClone(), fSymbolTable);
        fGrammarBucket.putGrammar(localSchemaGrammar);
      }
    }
    else
    {
      localSchemaGrammar = new SchemaGrammar(fTargetNamespace, paramXSDDescription.makeClone(), fSymbolTable);
      fGrammarBucket.putGrammar(localSchemaGrammar);
    }
    localSchemaGrammar.addDocument(null, (String)fDoc2SystemId.get(fSchemaElement));
    fDoc2XSDocumentMap.put(paramElement, localXSDocumentInfo);
    Vector localVector = new Vector();
    Element localElement1 = paramElement;
    Element localElement2 = null;
    for (Element localElement3 = DOMUtil.getFirstChildElement(localElement1); localElement3 != null; localElement3 = DOMUtil.getNextSiblingElement(localElement3))
    {
      String str2 = null;
      String str3 = null;
      String str4 = DOMUtil.getLocalName(localElement3);
      short s = -1;
      if (!str4.equals(SchemaSymbols.ELT_ANNOTATION))
      {
        Element localElement4;
        String str5;
        Object localObject2;
        if (str4.equals(SchemaSymbols.ELT_IMPORT))
        {
          s = 2;
          localObject1 = fAttributeChecker.checkAttributes(localElement3, true, localXSDocumentInfo);
          str3 = (String)localObject1[XSAttributeChecker.ATTIDX_SCHEMALOCATION];
          str2 = (String)localObject1[XSAttributeChecker.ATTIDX_NAMESPACE];
          if (str2 != null) {
            str2 = fSymbolTable.addSymbol(str2);
          }
          if (str2 == fTargetNamespace) {
            reportSchemaError("src-import.1.1", new Object[] { str2 }, localElement3);
          }
          localElement4 = DOMUtil.getFirstChildElement(localElement3);
          if (localElement4 != null)
          {
            str5 = DOMUtil.getLocalName(localElement4);
            if (str5.equals(SchemaSymbols.ELT_ANNOTATION)) {
              localSchemaGrammar.addAnnotation(fElementTraverser.traverseAnnotationDecl(localElement4, (Object[])localObject1, true, localXSDocumentInfo));
            } else {
              reportSchemaError("s4s-elt-must-match.1", new Object[] { str4, "annotation?", str5 }, localElement3);
            }
            if (DOMUtil.getNextSiblingElement(localElement4) != null) {
              reportSchemaError("s4s-elt-must-match.1", new Object[] { str4, "annotation?", DOMUtil.getLocalName(DOMUtil.getNextSiblingElement(localElement4)) }, localElement3);
            }
          }
          else
          {
            str5 = DOMUtil.getSyntheticAnnotation(localElement3);
            if (str5 != null) {
              localSchemaGrammar.addAnnotation(fElementTraverser.traverseSyntheticAnnotation(localElement3, str5, (Object[])localObject1, true, localXSDocumentInfo));
            }
          }
          fAttributeChecker.returnAttrArray((Object[])localObject1, localXSDocumentInfo);
          if (localXSDocumentInfo.isAllowedNS(str2))
          {
            if (!fHonourAllSchemaLocations) {
              continue;
            }
          }
          else {
            localXSDocumentInfo.addAllowedNS(str2);
          }
          str5 = null2EmptyString(fTargetNamespace);
          localObject2 = (Vector)fImportMap.get(str5);
          if (localObject2 == null)
          {
            fAllTNSs.addElement(str5);
            localObject2 = new Vector();
            fImportMap.put(str5, localObject2);
            ((Vector)localObject2).addElement(str2);
          }
          else if (!((Vector)localObject2).contains(str2))
          {
            ((Vector)localObject2).addElement(str2);
          }
          fSchemaGrammarDescription.reset();
          fSchemaGrammarDescription.setContextType((short)2);
          fSchemaGrammarDescription.setBaseSystemId(doc2SystemId(paramElement));
          fSchemaGrammarDescription.setLocationHints(new String[] { str3 });
          fSchemaGrammarDescription.setTargetNamespace(str2);
          if (((!fHonourAllSchemaLocations) && (findGrammar(fSchemaGrammarDescription) != null)) || (isExistingGrammar(fSchemaGrammarDescription))) {
            continue;
          }
          localElement2 = resolveSchema(fSchemaGrammarDescription, false, localElement3, findGrammar(fSchemaGrammarDescription) == null);
        }
        else
        {
          if ((!str4.equals(SchemaSymbols.ELT_INCLUDE)) && (!str4.equals(SchemaSymbols.ELT_REDEFINE))) {
            break;
          }
          localObject1 = fAttributeChecker.checkAttributes(localElement3, true, localXSDocumentInfo);
          str3 = (String)localObject1[XSAttributeChecker.ATTIDX_SCHEMALOCATION];
          if (str4.equals(SchemaSymbols.ELT_REDEFINE)) {
            fRedefine2NSSupport.put(localElement3, new SchemaNamespaceSupport(fNamespaceSupport));
          }
          if (str4.equals(SchemaSymbols.ELT_INCLUDE))
          {
            localElement4 = DOMUtil.getFirstChildElement(localElement3);
            if (localElement4 != null)
            {
              str5 = DOMUtil.getLocalName(localElement4);
              if (str5.equals(SchemaSymbols.ELT_ANNOTATION)) {
                localSchemaGrammar.addAnnotation(fElementTraverser.traverseAnnotationDecl(localElement4, (Object[])localObject1, true, localXSDocumentInfo));
              } else {
                reportSchemaError("s4s-elt-must-match.1", new Object[] { str4, "annotation?", str5 }, localElement3);
              }
              if (DOMUtil.getNextSiblingElement(localElement4) != null) {
                reportSchemaError("s4s-elt-must-match.1", new Object[] { str4, "annotation?", DOMUtil.getLocalName(DOMUtil.getNextSiblingElement(localElement4)) }, localElement3);
              }
            }
            else
            {
              str5 = DOMUtil.getSyntheticAnnotation(localElement3);
              if (str5 != null) {
                localSchemaGrammar.addAnnotation(fElementTraverser.traverseSyntheticAnnotation(localElement3, str5, (Object[])localObject1, true, localXSDocumentInfo));
              }
            }
          }
          else
          {
            for (localElement4 = DOMUtil.getFirstChildElement(localElement3); localElement4 != null; localElement4 = DOMUtil.getNextSiblingElement(localElement4))
            {
              str5 = DOMUtil.getLocalName(localElement4);
              if (str5.equals(SchemaSymbols.ELT_ANNOTATION))
              {
                localSchemaGrammar.addAnnotation(fElementTraverser.traverseAnnotationDecl(localElement4, (Object[])localObject1, true, localXSDocumentInfo));
                DOMUtil.setHidden(localElement4, fHiddenNodes);
              }
              else
              {
                localObject2 = DOMUtil.getSyntheticAnnotation(localElement3);
                if (localObject2 != null) {
                  localSchemaGrammar.addAnnotation(fElementTraverser.traverseSyntheticAnnotation(localElement3, (String)localObject2, (Object[])localObject1, true, localXSDocumentInfo));
                }
              }
            }
          }
          fAttributeChecker.returnAttrArray((Object[])localObject1, localXSDocumentInfo);
          if (str3 == null) {
            reportSchemaError("s4s-att-must-appear", new Object[] { "<include> or <redefine>", "schemaLocation" }, localElement3);
          }
          boolean bool = false;
          s = 0;
          if (str4.equals(SchemaSymbols.ELT_REDEFINE))
          {
            bool = nonAnnotationContent(localElement3);
            s = 1;
          }
          fSchemaGrammarDescription.reset();
          fSchemaGrammarDescription.setContextType(s);
          fSchemaGrammarDescription.setBaseSystemId(doc2SystemId(paramElement));
          fSchemaGrammarDescription.setLocationHints(new String[] { str3 });
          fSchemaGrammarDescription.setTargetNamespace(str1);
          localElement2 = resolveSchema(fSchemaGrammarDescription, bool, localElement3, true);
          str2 = fTargetNamespace;
        }
        Object localObject1 = null;
        if (fLastSchemaWasDuplicate) {
          localObject1 = localElement2 == null ? null : (XSDocumentInfo)fDoc2XSDocumentMap.get(localElement2);
        } else {
          localObject1 = constructTrees(localElement2, str3, fSchemaGrammarDescription);
        }
        if ((str4.equals(SchemaSymbols.ELT_REDEFINE)) && (localObject1 != null)) {
          fRedefine2XSDMap.put(localElement3, localObject1);
        }
        if (localElement2 != null)
        {
          if (localObject1 != null) {
            localVector.addElement(localObject1);
          }
          localElement2 = null;
        }
      }
    }
    fDependencyMap.put(localXSDocumentInfo, localVector);
    return localXSDocumentInfo;
  }
  
  private boolean isExistingGrammar(XSDDescription paramXSDDescription)
  {
    SchemaGrammar localSchemaGrammar = fGrammarBucket.getGrammar(paramXSDDescription.getTargetNamespace());
    if (localSchemaGrammar == null) {
      return findGrammar(paramXSDDescription) != null;
    }
    try
    {
      return localSchemaGrammar.getDocumentLocations().contains(XMLEntityManager.expandSystemId(paramXSDDescription.getLiteralSystemId(), paramXSDDescription.getBaseSystemId(), false));
    }
    catch (URI.MalformedURIException localMalformedURIException) {}
    return false;
  }
  
  protected void buildGlobalNameRegistries()
  {
    Stack localStack = new Stack();
    localStack.push(fRoot);
    while (!localStack.empty())
    {
      XSDocumentInfo localXSDocumentInfo = (XSDocumentInfo)localStack.pop();
      Element localElement1 = fSchemaElement;
      if (!DOMUtil.isHidden(localElement1, fHiddenNodes))
      {
        Element localElement2 = localElement1;
        int i = 1;
        for (Element localElement3 = DOMUtil.getFirstChildElement(localElement2); localElement3 != null; localElement3 = DOMUtil.getNextSiblingElement(localElement3)) {
          if (!DOMUtil.getLocalName(localElement3).equals(SchemaSymbols.ELT_ANNOTATION)) {
            if ((DOMUtil.getLocalName(localElement3).equals(SchemaSymbols.ELT_INCLUDE)) || (DOMUtil.getLocalName(localElement3).equals(SchemaSymbols.ELT_IMPORT)))
            {
              if (i == 0) {
                reportSchemaError("s4s-elt-invalid-content.3", new Object[] { DOMUtil.getLocalName(localElement3) }, localElement3);
              }
              DOMUtil.setHidden(localElement3, fHiddenNodes);
            }
            else
            {
              String str1;
              String str2;
              if (DOMUtil.getLocalName(localElement3).equals(SchemaSymbols.ELT_REDEFINE))
              {
                if (i == 0) {
                  reportSchemaError("s4s-elt-invalid-content.3", new Object[] { DOMUtil.getLocalName(localElement3) }, localElement3);
                }
                for (localObject = DOMUtil.getFirstChildElement(localElement3); localObject != null; localObject = DOMUtil.getNextSiblingElement((Node)localObject))
                {
                  str1 = DOMUtil.getAttrValue((Element)localObject, SchemaSymbols.ATT_NAME);
                  if (str1.length() != 0)
                  {
                    str2 = fTargetNamespace + "," + str1;
                    String str3 = DOMUtil.getLocalName((Node)localObject);
                    String str4;
                    if (str3.equals(SchemaSymbols.ELT_ATTRIBUTEGROUP))
                    {
                      checkForDuplicateNames(str2, fUnparsedAttributeGroupRegistry, fUnparsedAttributeGroupRegistrySub, (Element)localObject, localXSDocumentInfo);
                      str4 = DOMUtil.getAttrValue((Element)localObject, SchemaSymbols.ATT_NAME) + "_fn3dktizrknc9pi";
                      renameRedefiningComponents(localXSDocumentInfo, (Element)localObject, SchemaSymbols.ELT_ATTRIBUTEGROUP, str1, str4);
                    }
                    else if ((str3.equals(SchemaSymbols.ELT_COMPLEXTYPE)) || (str3.equals(SchemaSymbols.ELT_SIMPLETYPE)))
                    {
                      checkForDuplicateNames(str2, fUnparsedTypeRegistry, fUnparsedTypeRegistrySub, (Element)localObject, localXSDocumentInfo);
                      str4 = DOMUtil.getAttrValue((Element)localObject, SchemaSymbols.ATT_NAME) + "_fn3dktizrknc9pi";
                      if (str3.equals(SchemaSymbols.ELT_COMPLEXTYPE)) {
                        renameRedefiningComponents(localXSDocumentInfo, (Element)localObject, SchemaSymbols.ELT_COMPLEXTYPE, str1, str4);
                      } else {
                        renameRedefiningComponents(localXSDocumentInfo, (Element)localObject, SchemaSymbols.ELT_SIMPLETYPE, str1, str4);
                      }
                    }
                    else if (str3.equals(SchemaSymbols.ELT_GROUP))
                    {
                      checkForDuplicateNames(str2, fUnparsedGroupRegistry, fUnparsedGroupRegistrySub, (Element)localObject, localXSDocumentInfo);
                      str4 = DOMUtil.getAttrValue((Element)localObject, SchemaSymbols.ATT_NAME) + "_fn3dktizrknc9pi";
                      renameRedefiningComponents(localXSDocumentInfo, (Element)localObject, SchemaSymbols.ELT_GROUP, str1, str4);
                    }
                  }
                }
              }
              else
              {
                i = 0;
                localObject = DOMUtil.getAttrValue(localElement3, SchemaSymbols.ATT_NAME);
                if (((String)localObject).length() != 0)
                {
                  str1 = fTargetNamespace + "," + (String)localObject;
                  str2 = DOMUtil.getLocalName(localElement3);
                  if (str2.equals(SchemaSymbols.ELT_ATTRIBUTE)) {
                    checkForDuplicateNames(str1, fUnparsedAttributeRegistry, fUnparsedAttributeRegistrySub, localElement3, localXSDocumentInfo);
                  } else if (str2.equals(SchemaSymbols.ELT_ATTRIBUTEGROUP)) {
                    checkForDuplicateNames(str1, fUnparsedAttributeGroupRegistry, fUnparsedAttributeGroupRegistrySub, localElement3, localXSDocumentInfo);
                  } else if ((str2.equals(SchemaSymbols.ELT_COMPLEXTYPE)) || (str2.equals(SchemaSymbols.ELT_SIMPLETYPE))) {
                    checkForDuplicateNames(str1, fUnparsedTypeRegistry, fUnparsedTypeRegistrySub, localElement3, localXSDocumentInfo);
                  } else if (str2.equals(SchemaSymbols.ELT_ELEMENT)) {
                    checkForDuplicateNames(str1, fUnparsedElementRegistry, fUnparsedElementRegistrySub, localElement3, localXSDocumentInfo);
                  } else if (str2.equals(SchemaSymbols.ELT_GROUP)) {
                    checkForDuplicateNames(str1, fUnparsedGroupRegistry, fUnparsedGroupRegistrySub, localElement3, localXSDocumentInfo);
                  } else if (str2.equals(SchemaSymbols.ELT_NOTATION)) {
                    checkForDuplicateNames(str1, fUnparsedNotationRegistry, fUnparsedNotationRegistrySub, localElement3, localXSDocumentInfo);
                  }
                }
              }
            }
          }
        }
        DOMUtil.setHidden(localElement1, fHiddenNodes);
        Object localObject = (Vector)fDependencyMap.get(localXSDocumentInfo);
        for (int j = 0; j < ((Vector)localObject).size(); j++) {
          localStack.push(((Vector)localObject).elementAt(j));
        }
      }
    }
  }
  
  protected void traverseSchemas(ArrayList paramArrayList)
  {
    setSchemasVisible(fRoot);
    Stack localStack = new Stack();
    localStack.push(fRoot);
    while (!localStack.empty())
    {
      XSDocumentInfo localXSDocumentInfo = (XSDocumentInfo)localStack.pop();
      Element localElement1 = fSchemaElement;
      SchemaGrammar localSchemaGrammar = fGrammarBucket.getGrammar(fTargetNamespace);
      if (!DOMUtil.isHidden(localElement1, fHiddenNodes))
      {
        Element localElement2 = localElement1;
        int i = 0;
        for (Element localElement3 = DOMUtil.getFirstVisibleChildElement(localElement2, fHiddenNodes); localElement3 != null; localElement3 = DOMUtil.getNextVisibleSiblingElement(localElement3, fHiddenNodes))
        {
          DOMUtil.setHidden(localElement3, fHiddenNodes);
          localObject = DOMUtil.getLocalName(localElement3);
          if (DOMUtil.getLocalName(localElement3).equals(SchemaSymbols.ELT_REDEFINE))
          {
            localXSDocumentInfo.backupNSSupport((SchemaNamespaceSupport)fRedefine2NSSupport.get(localElement3));
            for (Element localElement4 = DOMUtil.getFirstVisibleChildElement(localElement3, fHiddenNodes); localElement4 != null; localElement4 = DOMUtil.getNextVisibleSiblingElement(localElement4, fHiddenNodes))
            {
              String str = DOMUtil.getLocalName(localElement4);
              DOMUtil.setHidden(localElement4, fHiddenNodes);
              if (str.equals(SchemaSymbols.ELT_ATTRIBUTEGROUP)) {
                fAttributeGroupTraverser.traverseGlobal(localElement4, localXSDocumentInfo, localSchemaGrammar);
              } else if (str.equals(SchemaSymbols.ELT_COMPLEXTYPE)) {
                fComplexTypeTraverser.traverseGlobal(localElement4, localXSDocumentInfo, localSchemaGrammar);
              } else if (str.equals(SchemaSymbols.ELT_GROUP)) {
                fGroupTraverser.traverseGlobal(localElement4, localXSDocumentInfo, localSchemaGrammar);
              } else if (str.equals(SchemaSymbols.ELT_SIMPLETYPE)) {
                fSimpleTypeTraverser.traverseGlobal(localElement4, localXSDocumentInfo, localSchemaGrammar);
              } else {
                reportSchemaError("s4s-elt-must-match.1", new Object[] { DOMUtil.getLocalName(localElement3), "(annotation | (simpleType | complexType | group | attributeGroup))*", str }, localElement4);
              }
            }
            localXSDocumentInfo.restoreNSSupport();
          }
          else if (((String)localObject).equals(SchemaSymbols.ELT_ATTRIBUTE))
          {
            fAttributeTraverser.traverseGlobal(localElement3, localXSDocumentInfo, localSchemaGrammar);
          }
          else if (((String)localObject).equals(SchemaSymbols.ELT_ATTRIBUTEGROUP))
          {
            fAttributeGroupTraverser.traverseGlobal(localElement3, localXSDocumentInfo, localSchemaGrammar);
          }
          else if (((String)localObject).equals(SchemaSymbols.ELT_COMPLEXTYPE))
          {
            fComplexTypeTraverser.traverseGlobal(localElement3, localXSDocumentInfo, localSchemaGrammar);
          }
          else if (((String)localObject).equals(SchemaSymbols.ELT_ELEMENT))
          {
            fElementTraverser.traverseGlobal(localElement3, localXSDocumentInfo, localSchemaGrammar);
          }
          else if (((String)localObject).equals(SchemaSymbols.ELT_GROUP))
          {
            fGroupTraverser.traverseGlobal(localElement3, localXSDocumentInfo, localSchemaGrammar);
          }
          else if (((String)localObject).equals(SchemaSymbols.ELT_NOTATION))
          {
            fNotationTraverser.traverse(localElement3, localXSDocumentInfo, localSchemaGrammar);
          }
          else if (((String)localObject).equals(SchemaSymbols.ELT_SIMPLETYPE))
          {
            fSimpleTypeTraverser.traverseGlobal(localElement3, localXSDocumentInfo, localSchemaGrammar);
          }
          else if (((String)localObject).equals(SchemaSymbols.ELT_ANNOTATION))
          {
            localSchemaGrammar.addAnnotation(fElementTraverser.traverseAnnotationDecl(localElement3, localXSDocumentInfo.getSchemaAttrs(), true, localXSDocumentInfo));
            i = 1;
          }
          else
          {
            reportSchemaError("s4s-elt-invalid-content.1", new Object[] { SchemaSymbols.ELT_SCHEMA, DOMUtil.getLocalName(localElement3) }, localElement3);
          }
        }
        if (i == 0)
        {
          localObject = DOMUtil.getSyntheticAnnotation(localElement2);
          if (localObject != null) {
            localSchemaGrammar.addAnnotation(fElementTraverser.traverseSyntheticAnnotation(localElement2, (String)localObject, localXSDocumentInfo.getSchemaAttrs(), true, localXSDocumentInfo));
          }
        }
        if (paramArrayList != null)
        {
          localObject = localXSDocumentInfo.getAnnotations();
          if (localObject != null)
          {
            paramArrayList.add(doc2SystemId(localElement1));
            paramArrayList.add(localObject);
          }
        }
        localXSDocumentInfo.returnSchemaAttrs();
        DOMUtil.setHidden(localElement1, fHiddenNodes);
        Object localObject = (Vector)fDependencyMap.get(localXSDocumentInfo);
        for (int j = 0; j < ((Vector)localObject).size(); j++) {
          localStack.push(((Vector)localObject).elementAt(j));
        }
      }
    }
  }
  
  private final boolean needReportTNSError(String paramString)
  {
    if (fReportedTNS == null) {
      fReportedTNS = new Vector();
    } else if (fReportedTNS.contains(paramString)) {
      return false;
    }
    fReportedTNS.addElement(paramString);
    return true;
  }
  
  protected Object getGlobalDecl(XSDocumentInfo paramXSDocumentInfo, int paramInt, QName paramQName, Element paramElement)
  {
    if ((uri != null) && (uri == SchemaSymbols.URI_SCHEMAFORSCHEMA) && (paramInt == 7))
    {
      localObject1 = SchemaGrammar.SG_SchemaNS.getGlobalTypeDecl(localpart);
      if (localObject1 != null) {
        return localObject1;
      }
    }
    if (!paramXSDocumentInfo.isAllowedNS(uri))
    {
      if (paramXSDocumentInfo.needReportTNSError(uri))
      {
        localObject1 = uri == null ? "src-resolve.4.1" : "src-resolve.4.2";
        reportSchemaError((String)localObject1, new Object[] { fDoc2SystemId.get(fSchemaElement), uri, rawname }, paramElement);
      }
      return null;
    }
    Object localObject1 = fGrammarBucket.getGrammar(uri);
    if (localObject1 == null)
    {
      if (needReportTNSError(uri)) {
        reportSchemaError("src-resolve", new Object[] { rawname, COMP_TYPE[paramInt] }, paramElement);
      }
      return null;
    }
    Object localObject2 = null;
    switch (paramInt)
    {
    case 1: 
      localObject2 = ((SchemaGrammar)localObject1).getGlobalAttributeDecl(localpart);
      break;
    case 2: 
      localObject2 = ((SchemaGrammar)localObject1).getGlobalAttributeGroupDecl(localpart);
      break;
    case 3: 
      localObject2 = ((SchemaGrammar)localObject1).getGlobalElementDecl(localpart);
      break;
    case 4: 
      localObject2 = ((SchemaGrammar)localObject1).getGlobalGroupDecl(localpart);
      break;
    case 5: 
      localObject2 = ((SchemaGrammar)localObject1).getIDConstraintDecl(localpart);
      break;
    case 6: 
      localObject2 = ((SchemaGrammar)localObject1).getGlobalNotationDecl(localpart);
      break;
    case 7: 
      localObject2 = ((SchemaGrammar)localObject1).getGlobalTypeDecl(localpart);
    }
    if (localObject2 != null) {
      return localObject2;
    }
    XSDocumentInfo localXSDocumentInfo1 = null;
    Element localElement1 = null;
    XSDocumentInfo localXSDocumentInfo2 = null;
    String str = uri + "," + localpart;
    switch (paramInt)
    {
    case 1: 
      localElement1 = (Element)fUnparsedAttributeRegistry.get(str);
      localXSDocumentInfo2 = (XSDocumentInfo)fUnparsedAttributeRegistrySub.get(str);
      break;
    case 2: 
      localElement1 = (Element)fUnparsedAttributeGroupRegistry.get(str);
      localXSDocumentInfo2 = (XSDocumentInfo)fUnparsedAttributeGroupRegistrySub.get(str);
      break;
    case 3: 
      localElement1 = (Element)fUnparsedElementRegistry.get(str);
      localXSDocumentInfo2 = (XSDocumentInfo)fUnparsedElementRegistrySub.get(str);
      break;
    case 4: 
      localElement1 = (Element)fUnparsedGroupRegistry.get(str);
      localXSDocumentInfo2 = (XSDocumentInfo)fUnparsedGroupRegistrySub.get(str);
      break;
    case 5: 
      localElement1 = (Element)fUnparsedIdentityConstraintRegistry.get(str);
      localXSDocumentInfo2 = (XSDocumentInfo)fUnparsedIdentityConstraintRegistrySub.get(str);
      break;
    case 6: 
      localElement1 = (Element)fUnparsedNotationRegistry.get(str);
      localXSDocumentInfo2 = (XSDocumentInfo)fUnparsedNotationRegistrySub.get(str);
      break;
    case 7: 
      localElement1 = (Element)fUnparsedTypeRegistry.get(str);
      localXSDocumentInfo2 = (XSDocumentInfo)fUnparsedTypeRegistrySub.get(str);
      break;
    default: 
      reportSchemaError("Internal-Error", new Object[] { "XSDHandler asked to locate component of type " + paramInt + "; it does not recognize this type!" }, paramElement);
    }
    if (localElement1 == null)
    {
      reportSchemaError("src-resolve", new Object[] { rawname, COMP_TYPE[paramInt] }, paramElement);
      return null;
    }
    localXSDocumentInfo1 = findXSDocumentForDecl(paramXSDocumentInfo, localElement1, localXSDocumentInfo2);
    if (localXSDocumentInfo1 == null)
    {
      localObject3 = uri == null ? "src-resolve.4.1" : "src-resolve.4.2";
      reportSchemaError((String)localObject3, new Object[] { fDoc2SystemId.get(fSchemaElement), uri, rawname }, paramElement);
      return null;
    }
    if (DOMUtil.isHidden(localElement1, fHiddenNodes))
    {
      localObject3 = CIRCULAR_CODES[paramInt];
      if ((paramInt == 7) && (SchemaSymbols.ELT_COMPLEXTYPE.equals(DOMUtil.getLocalName(localElement1)))) {
        localObject3 = "ct-props-correct.3";
      }
      reportSchemaError((String)localObject3, new Object[] { prefix + ":" + localpart }, paramElement);
      return null;
    }
    DOMUtil.setHidden(localElement1, fHiddenNodes);
    Object localObject3 = null;
    Element localElement2 = DOMUtil.getParent(localElement1);
    if (DOMUtil.getLocalName(localElement2).equals(SchemaSymbols.ELT_REDEFINE)) {
      localObject3 = (SchemaNamespaceSupport)fRedefine2NSSupport.get(localElement2);
    }
    localXSDocumentInfo1.backupNSSupport((SchemaNamespaceSupport)localObject3);
    switch (paramInt)
    {
    case 1: 
      localObject2 = fAttributeTraverser.traverseGlobal(localElement1, localXSDocumentInfo1, (SchemaGrammar)localObject1);
      break;
    case 2: 
      localObject2 = fAttributeGroupTraverser.traverseGlobal(localElement1, localXSDocumentInfo1, (SchemaGrammar)localObject1);
      break;
    case 3: 
      localObject2 = fElementTraverser.traverseGlobal(localElement1, localXSDocumentInfo1, (SchemaGrammar)localObject1);
      break;
    case 4: 
      localObject2 = fGroupTraverser.traverseGlobal(localElement1, localXSDocumentInfo1, (SchemaGrammar)localObject1);
      break;
    case 5: 
      localObject2 = null;
      break;
    case 6: 
      localObject2 = fNotationTraverser.traverse(localElement1, localXSDocumentInfo1, (SchemaGrammar)localObject1);
      break;
    case 7: 
      if (DOMUtil.getLocalName(localElement1).equals(SchemaSymbols.ELT_COMPLEXTYPE)) {
        localObject2 = fComplexTypeTraverser.traverseGlobal(localElement1, localXSDocumentInfo1, (SchemaGrammar)localObject1);
      } else {
        localObject2 = fSimpleTypeTraverser.traverseGlobal(localElement1, localXSDocumentInfo1, (SchemaGrammar)localObject1);
      }
      break;
    }
    localXSDocumentInfo1.restoreNSSupport();
    return localObject2;
  }
  
  Object getGrpOrAttrGrpRedefinedByRestriction(int paramInt, QName paramQName, XSDocumentInfo paramXSDocumentInfo, Element paramElement)
  {
    String str1 = "," + localpart;
    String str2 = null;
    switch (paramInt)
    {
    case 2: 
      str2 = (String)fRedefinedRestrictedAttributeGroupRegistry.get(str1);
      break;
    case 4: 
      str2 = (String)fRedefinedRestrictedGroupRegistry.get(str1);
      break;
    default: 
      return null;
    }
    if (str2 == null) {
      return null;
    }
    int i = str2.indexOf(",");
    QName localQName = new QName(XMLSymbols.EMPTY_STRING, str2.substring(i + 1), str2.substring(i), i == 0 ? null : str2.substring(0, i));
    Object localObject = getGlobalDecl(paramXSDocumentInfo, paramInt, localQName, paramElement);
    if (localObject == null)
    {
      switch (paramInt)
      {
      case 2: 
        reportSchemaError("src-redefine.7.2.1", new Object[] { localpart }, paramElement);
        break;
      case 4: 
        reportSchemaError("src-redefine.6.2.1", new Object[] { localpart }, paramElement);
      }
      return null;
    }
    return localObject;
  }
  
  protected void resolveKeyRefs()
  {
    for (int i = 0; i < fKeyrefStackPos; i++)
    {
      XSDocumentInfo localXSDocumentInfo = fKeyrefsMapXSDocumentInfo[i];
      fNamespaceSupport.makeGlobal();
      fNamespaceSupport.setEffectiveContext(fKeyrefNamespaceContext[i]);
      SchemaGrammar localSchemaGrammar = fGrammarBucket.getGrammar(fTargetNamespace);
      DOMUtil.setHidden(fKeyrefs[i], fHiddenNodes);
      fKeyrefTraverser.traverse(fKeyrefs[i], fKeyrefElems[i], localXSDocumentInfo, localSchemaGrammar);
    }
  }
  
  protected Hashtable getIDRegistry()
  {
    return fUnparsedIdentityConstraintRegistry;
  }
  
  protected Hashtable getIDRegistry_sub()
  {
    return fUnparsedIdentityConstraintRegistrySub;
  }
  
  protected void storeKeyRef(Element paramElement, XSDocumentInfo paramXSDocumentInfo, XSElementDecl paramXSElementDecl)
  {
    String str = DOMUtil.getAttrValue(paramElement, SchemaSymbols.ATT_NAME);
    Object localObject;
    if (str.length() != 0)
    {
      localObject = fTargetNamespace + "," + str;
      checkForDuplicateNames((String)localObject, fUnparsedIdentityConstraintRegistry, fUnparsedIdentityConstraintRegistrySub, paramElement, paramXSDocumentInfo);
    }
    if (fKeyrefStackPos == fKeyrefs.length)
    {
      localObject = new Element[fKeyrefStackPos + 2];
      System.arraycopy(fKeyrefs, 0, localObject, 0, fKeyrefStackPos);
      fKeyrefs = ((Element[])localObject);
      XSElementDecl[] arrayOfXSElementDecl = new XSElementDecl[fKeyrefStackPos + 2];
      System.arraycopy(fKeyrefElems, 0, arrayOfXSElementDecl, 0, fKeyrefStackPos);
      fKeyrefElems = arrayOfXSElementDecl;
      String[][] arrayOfString; = new String[fKeyrefStackPos + 2][];
      System.arraycopy(fKeyrefNamespaceContext, 0, arrayOfString;, 0, fKeyrefStackPos);
      fKeyrefNamespaceContext = arrayOfString;;
      XSDocumentInfo[] arrayOfXSDocumentInfo = new XSDocumentInfo[fKeyrefStackPos + 2];
      System.arraycopy(fKeyrefsMapXSDocumentInfo, 0, arrayOfXSDocumentInfo, 0, fKeyrefStackPos);
      fKeyrefsMapXSDocumentInfo = arrayOfXSDocumentInfo;
    }
    fKeyrefs[fKeyrefStackPos] = paramElement;
    fKeyrefElems[fKeyrefStackPos] = paramXSElementDecl;
    fKeyrefNamespaceContext[fKeyrefStackPos] = fNamespaceSupport.getEffectiveLocalContext();
    fKeyrefsMapXSDocumentInfo[(fKeyrefStackPos++)] = paramXSDocumentInfo;
  }
  
  private Element resolveSchema(XSDDescription paramXSDDescription, boolean paramBoolean1, Element paramElement, boolean paramBoolean2)
  {
    XMLInputSource localXMLInputSource = null;
    try
    {
      Hashtable localHashtable = paramBoolean2 ? fLocationPairs : EMPTY_TABLE;
      localXMLInputSource = XMLSchemaLoader.resolveDocument(paramXSDDescription, localHashtable, fEntityResolver);
    }
    catch (IOException localIOException1)
    {
      if (paramBoolean1) {
        reportSchemaError("schema_reference.4", new Object[] { paramXSDDescription.getLocationHints()[0] }, paramElement);
      } else {
        reportSchemaWarning("schema_reference.4", new Object[] { paramXSDDescription.getLocationHints()[0] }, paramElement);
      }
    }
    Object localObject;
    if ((localXMLInputSource instanceof DOMInputSource))
    {
      fHiddenNodes.clear();
      localObject = ((DOMInputSource)localXMLInputSource).getNode();
      if ((localObject instanceof Document)) {
        return DOMUtil.getRoot((Document)localObject);
      }
      if ((localObject instanceof Element)) {
        return (Element)localObject;
      }
      return null;
    }
    if ((localXMLInputSource instanceof SAXInputSource))
    {
      localObject = ((SAXInputSource)localXMLInputSource).getXMLReader();
      InputSource localInputSource = ((SAXInputSource)localXMLInputSource).getInputSource();
      boolean bool1 = false;
      if (localObject != null)
      {
        try
        {
          bool1 = ((XMLReader)localObject).getFeature("http://xml.org/sax/features/namespace-prefixes");
        }
        catch (SAXException localSAXException1) {}
      }
      else
      {
        try
        {
          localObject = XMLReaderFactory.createXMLReader();
        }
        catch (SAXException localSAXException2)
        {
          localObject = new SAXParser();
        }
        try
        {
          ((XMLReader)localObject).setFeature("http://xml.org/sax/features/namespace-prefixes", true);
          bool1 = true;
        }
        catch (SAXException localSAXException3) {}
      }
      boolean bool2 = false;
      try
      {
        bool2 = ((XMLReader)localObject).getFeature("http://xml.org/sax/features/string-interning");
      }
      catch (SAXException localSAXException4) {}
      if (fXSContentHandler == null) {
        fXSContentHandler = new SchemaContentHandler();
      }
      fXSContentHandler.reset(fSchemaParser, fSymbolTable, bool1, bool2);
      ((XMLReader)localObject).setContentHandler(fXSContentHandler);
      ((XMLReader)localObject).setErrorHandler(fErrorReporter.getSAXErrorHandler());
      try
      {
        ((XMLReader)localObject).parse(localInputSource);
      }
      catch (SAXException localSAXException5)
      {
        return null;
      }
      catch (IOException localIOException2)
      {
        return null;
      }
      Document localDocument = fXSContentHandler.getDocument();
      if (localDocument == null) {
        return null;
      }
      return DOMUtil.getRoot(localDocument);
    }
    return getSchemaDocument(paramXSDDescription.getTargetNamespace(), localXMLInputSource, paramBoolean1, paramXSDDescription.getContextType(), paramElement);
  }
  
  private Element getSchemaDocument(String paramString, XMLInputSource paramXMLInputSource, boolean paramBoolean, short paramShort, Element paramElement)
  {
    int i = 1;
    Element localElement = null;
    try
    {
      if ((paramXMLInputSource != null) && ((paramXMLInputSource.getSystemId() != null) || (paramXMLInputSource.getByteStream() != null) || (paramXMLInputSource.getCharacterStream() != null)))
      {
        XSDKey localXSDKey = null;
        String str = null;
        if (paramShort != 3)
        {
          str = XMLEntityManager.expandSystemId(paramXMLInputSource.getSystemId(), paramXMLInputSource.getBaseSystemId(), false);
          localXSDKey = new XSDKey(str, paramShort, paramString);
          if ((localElement = (Element)fTraversed.get(localXSDKey)) != null)
          {
            fLastSchemaWasDuplicate = true;
            return localElement;
          }
        }
        fSchemaParser.parse(paramXMLInputSource);
        localElement = fSchemaParser.getDocument2() == null ? null : DOMUtil.getRoot(fSchemaParser.getDocument2());
        if (localXSDKey != null) {
          fTraversed.put(localXSDKey, localElement);
        }
        if (str != null) {
          fDoc2SystemId.put(localElement, str);
        }
        fLastSchemaWasDuplicate = false;
        return localElement;
      }
      i = 0;
    }
    catch (IOException localIOException) {}
    if (paramBoolean)
    {
      if (i != 0) {
        reportSchemaError("schema_reference.4", new Object[] { paramXMLInputSource.getSystemId() }, paramElement);
      } else {
        reportSchemaError("schema_reference.4", new Object[] { paramXMLInputSource == null ? "" : paramXMLInputSource.getSystemId() }, paramElement);
      }
    }
    else if (i != 0) {
      reportSchemaWarning("schema_reference.4", new Object[] { paramXMLInputSource.getSystemId() }, paramElement);
    }
    fLastSchemaWasDuplicate = false;
    return null;
  }
  
  private void createTraversers()
  {
    fAttributeChecker = new XSAttributeChecker(this);
    fAttributeGroupTraverser = new XSDAttributeGroupTraverser(this, fAttributeChecker);
    fAttributeTraverser = new XSDAttributeTraverser(this, fAttributeChecker);
    fComplexTypeTraverser = new XSDComplexTypeTraverser(this, fAttributeChecker);
    fElementTraverser = new XSDElementTraverser(this, fAttributeChecker);
    fGroupTraverser = new XSDGroupTraverser(this, fAttributeChecker);
    fKeyrefTraverser = new XSDKeyrefTraverser(this, fAttributeChecker);
    fNotationTraverser = new XSDNotationTraverser(this, fAttributeChecker);
    fSimpleTypeTraverser = new XSDSimpleTypeTraverser(this, fAttributeChecker);
    fUniqueOrKeyTraverser = new XSDUniqueOrKeyTraverser(this, fAttributeChecker);
    fWildCardTraverser = new XSDWildcardTraverser(this, fAttributeChecker);
  }
  
  void prepareForParse()
  {
    fTraversed.clear();
    fDoc2SystemId.clear();
    fHiddenNodes.clear();
    fLastSchemaWasDuplicate = false;
  }
  
  void prepareForTraverse()
  {
    fUnparsedAttributeRegistry.clear();
    fUnparsedAttributeGroupRegistry.clear();
    fUnparsedElementRegistry.clear();
    fUnparsedGroupRegistry.clear();
    fUnparsedIdentityConstraintRegistry.clear();
    fUnparsedNotationRegistry.clear();
    fUnparsedTypeRegistry.clear();
    fUnparsedAttributeRegistrySub.clear();
    fUnparsedAttributeGroupRegistrySub.clear();
    fUnparsedElementRegistrySub.clear();
    fUnparsedGroupRegistrySub.clear();
    fUnparsedIdentityConstraintRegistrySub.clear();
    fUnparsedNotationRegistrySub.clear();
    fUnparsedTypeRegistrySub.clear();
    fXSDocumentInfoRegistry.clear();
    fDependencyMap.clear();
    fDoc2XSDocumentMap.clear();
    fRedefine2XSDMap.clear();
    fRedefine2NSSupport.clear();
    fAllTNSs.removeAllElements();
    fImportMap.clear();
    fRoot = null;
    for (int i = 0; i < fLocalElemStackPos; i++)
    {
      fParticle[i] = null;
      fLocalElementDecl[i] = null;
      fLocalElementDecl_schema[i] = null;
      fLocalElemNamespaceContext[i] = null;
    }
    fLocalElemStackPos = 0;
    for (int j = 0; j < fKeyrefStackPos; j++)
    {
      fKeyrefs[j] = null;
      fKeyrefElems[j] = null;
      fKeyrefNamespaceContext[j] = null;
      fKeyrefsMapXSDocumentInfo[j] = null;
    }
    fKeyrefStackPos = 0;
    if (fAttributeChecker == null) {
      createTraversers();
    }
    fAttributeChecker.reset(fSymbolTable);
    fAttributeGroupTraverser.reset(fSymbolTable, fValidateAnnotations);
    fAttributeTraverser.reset(fSymbolTable, fValidateAnnotations);
    fComplexTypeTraverser.reset(fSymbolTable, fValidateAnnotations);
    fElementTraverser.reset(fSymbolTable, fValidateAnnotations);
    fGroupTraverser.reset(fSymbolTable, fValidateAnnotations);
    fKeyrefTraverser.reset(fSymbolTable, fValidateAnnotations);
    fNotationTraverser.reset(fSymbolTable, fValidateAnnotations);
    fSimpleTypeTraverser.reset(fSymbolTable, fValidateAnnotations);
    fUniqueOrKeyTraverser.reset(fSymbolTable, fValidateAnnotations);
    fWildCardTraverser.reset(fSymbolTable, fValidateAnnotations);
    fRedefinedRestrictedAttributeGroupRegistry.clear();
    fRedefinedRestrictedGroupRegistry.clear();
  }
  
  public void setDeclPool(XSDeclarationPool paramXSDeclarationPool)
  {
    fDeclPool = paramXSDeclarationPool;
  }
  
  public void reset(XMLComponentManager paramXMLComponentManager)
  {
    fSymbolTable = ((SymbolTable)paramXMLComponentManager.getProperty("http://apache.org/xml/properties/internal/symbol-table"));
    fEntityResolver = ((XMLEntityResolver)paramXMLComponentManager.getProperty("http://apache.org/xml/properties/internal/entity-manager"));
    XMLEntityResolver localXMLEntityResolver = (XMLEntityResolver)paramXMLComponentManager.getProperty("http://apache.org/xml/properties/internal/entity-resolver");
    if (localXMLEntityResolver != null) {
      fSchemaParser.setEntityResolver(localXMLEntityResolver);
    }
    fErrorReporter = ((XMLErrorReporter)paramXMLComponentManager.getProperty("http://apache.org/xml/properties/internal/error-reporter"));
    try
    {
      XMLErrorHandler localXMLErrorHandler = fErrorReporter.getErrorHandler();
      if (localXMLErrorHandler != fSchemaParser.getProperty("http://apache.org/xml/properties/internal/error-handler"))
      {
        fSchemaParser.setProperty("http://apache.org/xml/properties/internal/error-handler", localXMLErrorHandler != null ? localXMLErrorHandler : new DefaultErrorHandler());
        if (fAnnotationValidator != null) {
          fAnnotationValidator.setProperty("http://apache.org/xml/properties/internal/error-handler", localXMLErrorHandler != null ? localXMLErrorHandler : new DefaultErrorHandler());
        }
      }
    }
    catch (XMLConfigurationException localXMLConfigurationException1) {}
    try
    {
      fValidateAnnotations = paramXMLComponentManager.getFeature("http://apache.org/xml/features/validate-annotations");
    }
    catch (XMLConfigurationException localXMLConfigurationException2)
    {
      fValidateAnnotations = false;
    }
    try
    {
      fHonourAllSchemaLocations = paramXMLComponentManager.getFeature("http://apache.org/xml/features/honour-all-schemaLocations");
    }
    catch (XMLConfigurationException localXMLConfigurationException3)
    {
      fHonourAllSchemaLocations = false;
    }
    try
    {
      fSchemaParser.setFeature("http://apache.org/xml/features/continue-after-fatal-error", fErrorReporter.getFeature("http://apache.org/xml/features/continue-after-fatal-error"));
    }
    catch (XMLConfigurationException localXMLConfigurationException4) {}
    try
    {
      fSchemaParser.setFeature("http://apache.org/xml/features/allow-java-encodings", paramXMLComponentManager.getFeature("http://apache.org/xml/features/allow-java-encodings"));
    }
    catch (XMLConfigurationException localXMLConfigurationException5) {}
    try
    {
      fSchemaParser.setFeature("http://apache.org/xml/features/standard-uri-conformant", paramXMLComponentManager.getFeature("http://apache.org/xml/features/standard-uri-conformant"));
    }
    catch (XMLConfigurationException localXMLConfigurationException6) {}
    try
    {
      fGrammarPool = ((XMLGrammarPool)paramXMLComponentManager.getProperty("http://apache.org/xml/properties/internal/grammar-pool"));
    }
    catch (XMLConfigurationException localXMLConfigurationException7)
    {
      fGrammarPool = null;
    }
    try
    {
      fSchemaParser.setFeature("http://apache.org/xml/features/disallow-doctype-decl", paramXMLComponentManager.getFeature("http://apache.org/xml/features/disallow-doctype-decl"));
    }
    catch (XMLConfigurationException localXMLConfigurationException8) {}
    try
    {
      Object localObject = paramXMLComponentManager.getProperty("http://apache.org/xml/properties/security-manager");
      if (localObject != null) {
        fSchemaParser.setProperty("http://apache.org/xml/properties/security-manager", localObject);
      }
    }
    catch (XMLConfigurationException localXMLConfigurationException9) {}
  }
  
  void traverseLocalElements()
  {
    fElementTraverser.fDeferTraversingLocalElements = false;
    for (int i = 0; i < fLocalElemStackPos; i++)
    {
      Element localElement = fLocalElementDecl[i];
      XSDocumentInfo localXSDocumentInfo = fLocalElementDecl_schema[i];
      SchemaGrammar localSchemaGrammar = fGrammarBucket.getGrammar(fTargetNamespace);
      fElementTraverser.traverseLocal(fParticle[i], localElement, localXSDocumentInfo, localSchemaGrammar, fAllContext[i], fParent[i], fLocalElemNamespaceContext[i]);
      if (fParticle[i].fType == 0)
      {
        XSModelGroupImpl localXSModelGroupImpl = null;
        if ((fParent[i] instanceof XSComplexTypeDecl))
        {
          XSParticle localXSParticle = ((XSComplexTypeDecl)fParent[i]).getParticle();
          if (localXSParticle != null) {
            localXSModelGroupImpl = (XSModelGroupImpl)localXSParticle.getTerm();
          }
        }
        else
        {
          localXSModelGroupImpl = fParent[i]).fModelGroup;
        }
        if (localXSModelGroupImpl != null) {
          removeParticle(localXSModelGroupImpl, fParticle[i]);
        }
      }
    }
  }
  
  private boolean removeParticle(XSModelGroupImpl paramXSModelGroupImpl, XSParticleDecl paramXSParticleDecl)
  {
    for (int i = 0; i < fParticleCount; i++)
    {
      XSParticleDecl localXSParticleDecl = fParticles[i];
      if (localXSParticleDecl == paramXSParticleDecl)
      {
        for (int j = i; j < fParticleCount - 1; j++) {
          fParticles[j] = fParticles[(j + 1)];
        }
        fParticleCount -= 1;
        return true;
      }
      if ((fType == 3) && (removeParticle((XSModelGroupImpl)fValue, paramXSParticleDecl))) {
        return true;
      }
    }
    return false;
  }
  
  void fillInLocalElemInfo(Element paramElement, XSDocumentInfo paramXSDocumentInfo, int paramInt, XSObject paramXSObject, XSParticleDecl paramXSParticleDecl)
  {
    if (fParticle.length == fLocalElemStackPos)
    {
      XSParticleDecl[] arrayOfXSParticleDecl = new XSParticleDecl[fLocalElemStackPos + 10];
      System.arraycopy(fParticle, 0, arrayOfXSParticleDecl, 0, fLocalElemStackPos);
      fParticle = arrayOfXSParticleDecl;
      Element[] arrayOfElement = new Element[fLocalElemStackPos + 10];
      System.arraycopy(fLocalElementDecl, 0, arrayOfElement, 0, fLocalElemStackPos);
      fLocalElementDecl = arrayOfElement;
      XSDocumentInfo[] arrayOfXSDocumentInfo = new XSDocumentInfo[fLocalElemStackPos + 10];
      System.arraycopy(fLocalElementDecl_schema, 0, arrayOfXSDocumentInfo, 0, fLocalElemStackPos);
      fLocalElementDecl_schema = arrayOfXSDocumentInfo;
      int[] arrayOfInt = new int[fLocalElemStackPos + 10];
      System.arraycopy(fAllContext, 0, arrayOfInt, 0, fLocalElemStackPos);
      fAllContext = arrayOfInt;
      XSObject[] arrayOfXSObject = new XSObject[fLocalElemStackPos + 10];
      System.arraycopy(fParent, 0, arrayOfXSObject, 0, fLocalElemStackPos);
      fParent = arrayOfXSObject;
      String[][] arrayOfString; = new String[fLocalElemStackPos + 10][];
      System.arraycopy(fLocalElemNamespaceContext, 0, arrayOfString;, 0, fLocalElemStackPos);
      fLocalElemNamespaceContext = arrayOfString;;
    }
    fParticle[fLocalElemStackPos] = paramXSParticleDecl;
    fLocalElementDecl[fLocalElemStackPos] = paramElement;
    fLocalElementDecl_schema[fLocalElemStackPos] = paramXSDocumentInfo;
    fAllContext[fLocalElemStackPos] = paramInt;
    fParent[fLocalElemStackPos] = paramXSObject;
    fLocalElemNamespaceContext[(fLocalElemStackPos++)] = fNamespaceSupport.getEffectiveLocalContext();
  }
  
  void checkForDuplicateNames(String paramString, Hashtable paramHashtable1, Hashtable paramHashtable2, Element paramElement, XSDocumentInfo paramXSDocumentInfo)
  {
    Object localObject = null;
    if ((localObject = paramHashtable1.get(paramString)) == null)
    {
      paramHashtable1.put(paramString, paramElement);
      paramHashtable2.put(paramString, paramXSDocumentInfo);
    }
    else
    {
      Element localElement1 = (Element)localObject;
      XSDocumentInfo localXSDocumentInfo1 = (XSDocumentInfo)paramHashtable2.get(paramString);
      if (localElement1 == paramElement) {
        return;
      }
      Element localElement2 = null;
      XSDocumentInfo localXSDocumentInfo2 = null;
      int i = 1;
      if (DOMUtil.getLocalName(localElement2 = DOMUtil.getParent(localElement1)).equals(SchemaSymbols.ELT_REDEFINE))
      {
        localXSDocumentInfo2 = (XSDocumentInfo)fRedefine2XSDMap.get(localElement2);
      }
      else if (DOMUtil.getLocalName(DOMUtil.getParent(paramElement)).equals(SchemaSymbols.ELT_REDEFINE))
      {
        localXSDocumentInfo2 = localXSDocumentInfo1;
        i = 0;
      }
      if (localXSDocumentInfo2 != null)
      {
        if (localXSDocumentInfo1 == paramXSDocumentInfo)
        {
          reportSchemaError("sch-props-correct.2", new Object[] { paramString }, paramElement);
          return;
        }
        String str = paramString.substring(paramString.lastIndexOf(',') + 1) + "_fn3dktizrknc9pi";
        if (localXSDocumentInfo2 == paramXSDocumentInfo)
        {
          paramElement.setAttribute(SchemaSymbols.ATT_NAME, str);
          if (fTargetNamespace == null)
          {
            paramHashtable1.put("," + str, paramElement);
            paramHashtable2.put("," + str, paramXSDocumentInfo);
          }
          else
          {
            paramHashtable1.put(fTargetNamespace + "," + str, paramElement);
            paramHashtable2.put(fTargetNamespace + "," + str, paramXSDocumentInfo);
          }
          if (fTargetNamespace == null) {
            checkForDuplicateNames("," + str, paramHashtable1, paramHashtable2, paramElement, paramXSDocumentInfo);
          } else {
            checkForDuplicateNames(fTargetNamespace + "," + str, paramHashtable1, paramHashtable2, paramElement, paramXSDocumentInfo);
          }
        }
        else if (i != 0)
        {
          if (fTargetNamespace == null) {
            checkForDuplicateNames("," + str, paramHashtable1, paramHashtable2, paramElement, paramXSDocumentInfo);
          } else {
            checkForDuplicateNames(fTargetNamespace + "," + str, paramHashtable1, paramHashtable2, paramElement, paramXSDocumentInfo);
          }
        }
        else
        {
          reportSchemaError("sch-props-correct.2", new Object[] { paramString }, paramElement);
        }
      }
      else
      {
        reportSchemaError("sch-props-correct.2", new Object[] { paramString }, paramElement);
      }
    }
  }
  
  private void renameRedefiningComponents(XSDocumentInfo paramXSDocumentInfo, Element paramElement, String paramString1, String paramString2, String paramString3)
  {
    Object localObject1;
    Object localObject2;
    Object localObject3;
    Object localObject4;
    if (paramString1.equals(SchemaSymbols.ELT_SIMPLETYPE))
    {
      localObject1 = DOMUtil.getFirstChildElement(paramElement);
      if (localObject1 == null)
      {
        reportSchemaError("src-redefine.5.a.a", null, paramElement);
      }
      else
      {
        localObject2 = DOMUtil.getLocalName((Node)localObject1);
        if (((String)localObject2).equals(SchemaSymbols.ELT_ANNOTATION)) {
          localObject1 = DOMUtil.getNextSiblingElement((Node)localObject1);
        }
        if (localObject1 == null)
        {
          reportSchemaError("src-redefine.5.a.a", null, paramElement);
        }
        else
        {
          localObject2 = DOMUtil.getLocalName((Node)localObject1);
          if (!((String)localObject2).equals(SchemaSymbols.ELT_RESTRICTION))
          {
            reportSchemaError("src-redefine.5.a.b", new Object[] { localObject2 }, paramElement);
          }
          else
          {
            localObject3 = fAttributeChecker.checkAttributes((Element)localObject1, false, paramXSDocumentInfo);
            localObject4 = (QName)localObject3[XSAttributeChecker.ATTIDX_BASE];
            if ((localObject4 == null) || (uri != fTargetNamespace) || (!localpart.equals(paramString2))) {
              reportSchemaError("src-redefine.5.a.c", new Object[] { localObject2, (fTargetNamespace == null ? "" : fTargetNamespace) + "," + paramString2 }, paramElement);
            } else if ((prefix != null) && (prefix.length() > 0)) {
              ((Element)localObject1).setAttribute(SchemaSymbols.ATT_BASE, prefix + ":" + paramString3);
            } else {
              ((Element)localObject1).setAttribute(SchemaSymbols.ATT_BASE, paramString3);
            }
            fAttributeChecker.returnAttrArray((Object[])localObject3, paramXSDocumentInfo);
          }
        }
      }
    }
    else if (paramString1.equals(SchemaSymbols.ELT_COMPLEXTYPE))
    {
      localObject1 = DOMUtil.getFirstChildElement(paramElement);
      if (localObject1 == null)
      {
        reportSchemaError("src-redefine.5.b.a", null, paramElement);
      }
      else
      {
        if (DOMUtil.getLocalName((Node)localObject1).equals(SchemaSymbols.ELT_ANNOTATION)) {
          localObject1 = DOMUtil.getNextSiblingElement((Node)localObject1);
        }
        if (localObject1 == null)
        {
          reportSchemaError("src-redefine.5.b.a", null, paramElement);
        }
        else
        {
          localObject2 = DOMUtil.getFirstChildElement((Node)localObject1);
          if (localObject2 == null)
          {
            reportSchemaError("src-redefine.5.b.b", null, (Element)localObject1);
          }
          else
          {
            localObject3 = DOMUtil.getLocalName((Node)localObject2);
            if (((String)localObject3).equals(SchemaSymbols.ELT_ANNOTATION)) {
              localObject2 = DOMUtil.getNextSiblingElement((Node)localObject2);
            }
            if (localObject2 == null)
            {
              reportSchemaError("src-redefine.5.b.b", null, (Element)localObject1);
            }
            else
            {
              localObject3 = DOMUtil.getLocalName((Node)localObject2);
              if ((!((String)localObject3).equals(SchemaSymbols.ELT_RESTRICTION)) && (!((String)localObject3).equals(SchemaSymbols.ELT_EXTENSION)))
              {
                reportSchemaError("src-redefine.5.b.c", new Object[] { localObject3 }, (Element)localObject2);
              }
              else
              {
                localObject4 = fAttributeChecker.checkAttributes((Element)localObject2, false, paramXSDocumentInfo);
                QName localQName = (QName)localObject4[XSAttributeChecker.ATTIDX_BASE];
                if ((localQName == null) || (uri != fTargetNamespace) || (!localpart.equals(paramString2))) {
                  reportSchemaError("src-redefine.5.b.d", new Object[] { localObject3, (fTargetNamespace == null ? "" : fTargetNamespace) + "," + paramString2 }, (Element)localObject2);
                } else if ((prefix != null) && (prefix.length() > 0)) {
                  ((Element)localObject2).setAttribute(SchemaSymbols.ATT_BASE, prefix + ":" + paramString3);
                } else {
                  ((Element)localObject2).setAttribute(SchemaSymbols.ATT_BASE, paramString3);
                }
              }
            }
          }
        }
      }
    }
    else
    {
      int i;
      if (paramString1.equals(SchemaSymbols.ELT_ATTRIBUTEGROUP))
      {
        localObject1 = fTargetNamespace + "," + paramString2;
        i = changeRedefineGroup((String)localObject1, paramString1, paramString3, paramElement, paramXSDocumentInfo);
        if (i > 1) {
          reportSchemaError("src-redefine.7.1", new Object[] { new Integer(i) }, paramElement);
        } else if (i != 1) {
          if (fTargetNamespace == null) {
            fRedefinedRestrictedAttributeGroupRegistry.put(localObject1, "," + paramString3);
          } else {
            fRedefinedRestrictedAttributeGroupRegistry.put(localObject1, fTargetNamespace + "," + paramString3);
          }
        }
      }
      else if (paramString1.equals(SchemaSymbols.ELT_GROUP))
      {
        localObject1 = fTargetNamespace + "," + paramString2;
        i = changeRedefineGroup((String)localObject1, paramString1, paramString3, paramElement, paramXSDocumentInfo);
        if (i > 1) {
          reportSchemaError("src-redefine.6.1.1", new Object[] { new Integer(i) }, paramElement);
        } else if (i != 1) {
          if (fTargetNamespace == null) {
            fRedefinedRestrictedGroupRegistry.put(localObject1, "," + paramString3);
          } else {
            fRedefinedRestrictedGroupRegistry.put(localObject1, fTargetNamespace + "," + paramString3);
          }
        }
      }
      else
      {
        reportSchemaError("Internal-Error", new Object[] { "could not handle this particular <redefine>; please submit your schemas and instance document in a bug report!" }, paramElement);
      }
    }
  }
  
  private String findQName(String paramString, XSDocumentInfo paramXSDocumentInfo)
  {
    SchemaNamespaceSupport localSchemaNamespaceSupport = fNamespaceSupport;
    int i = paramString.indexOf(':');
    String str1 = XMLSymbols.EMPTY_STRING;
    if (i > 0) {
      str1 = paramString.substring(0, i);
    }
    String str2 = localSchemaNamespaceSupport.getURI(fSymbolTable.addSymbol(str1));
    String str3 = i == 0 ? paramString : paramString.substring(i + 1);
    if ((str1 == XMLSymbols.EMPTY_STRING) && (str2 == null) && (fIsChameleonSchema)) {
      str2 = fTargetNamespace;
    }
    if (str2 == null) {
      return "," + str3;
    }
    return str2 + "," + str3;
  }
  
  private int changeRedefineGroup(String paramString1, String paramString2, String paramString3, Element paramElement, XSDocumentInfo paramXSDocumentInfo)
  {
    int i = 0;
    for (Element localElement = DOMUtil.getFirstChildElement(paramElement); localElement != null; localElement = DOMUtil.getNextSiblingElement(localElement))
    {
      String str1 = DOMUtil.getLocalName(localElement);
      if (!str1.equals(paramString2))
      {
        i += changeRedefineGroup(paramString1, paramString2, paramString3, localElement, paramXSDocumentInfo);
      }
      else
      {
        String str2 = localElement.getAttribute(SchemaSymbols.ATT_REF);
        if (str2.length() != 0)
        {
          String str3 = findQName(str2, paramXSDocumentInfo);
          if (paramString1.equals(str3))
          {
            String str4 = XMLSymbols.EMPTY_STRING;
            int j = str2.indexOf(":");
            if (j > 0)
            {
              str4 = str2.substring(0, j);
              localElement.setAttribute(SchemaSymbols.ATT_REF, str4 + ":" + paramString3);
            }
            else
            {
              localElement.setAttribute(SchemaSymbols.ATT_REF, paramString3);
            }
            i++;
            if (paramString2.equals(SchemaSymbols.ELT_GROUP))
            {
              String str5 = localElement.getAttribute(SchemaSymbols.ATT_MINOCCURS);
              String str6 = localElement.getAttribute(SchemaSymbols.ATT_MAXOCCURS);
              if (((str6.length() != 0) && (!str6.equals("1"))) || ((str5.length() != 0) && (!str5.equals("1")))) {
                reportSchemaError("src-redefine.6.1.2", new Object[] { str2 }, localElement);
              }
            }
          }
        }
      }
    }
    return i;
  }
  
  private XSDocumentInfo findXSDocumentForDecl(XSDocumentInfo paramXSDocumentInfo1, Element paramElement, XSDocumentInfo paramXSDocumentInfo2)
  {
    XSDocumentInfo localXSDocumentInfo1 = paramXSDocumentInfo2;
    if (localXSDocumentInfo1 == null) {
      return null;
    }
    XSDocumentInfo localXSDocumentInfo2 = (XSDocumentInfo)localXSDocumentInfo1;
    return localXSDocumentInfo2;
  }
  
  private boolean nonAnnotationContent(Element paramElement)
  {
    for (Element localElement = DOMUtil.getFirstChildElement(paramElement); localElement != null; localElement = DOMUtil.getNextSiblingElement(localElement)) {
      if (!DOMUtil.getLocalName(localElement).equals(SchemaSymbols.ELT_ANNOTATION)) {
        return true;
      }
    }
    return false;
  }
  
  private void setSchemasVisible(XSDocumentInfo paramXSDocumentInfo)
  {
    if (DOMUtil.isHidden(fSchemaElement, fHiddenNodes))
    {
      DOMUtil.setVisible(fSchemaElement, fHiddenNodes);
      Vector localVector = (Vector)fDependencyMap.get(paramXSDocumentInfo);
      for (int i = 0; i < localVector.size(); i++) {
        setSchemasVisible((XSDocumentInfo)localVector.elementAt(i));
      }
    }
  }
  
  public SimpleLocator element2Locator(Element paramElement)
  {
    if (!(paramElement instanceof ElementImpl)) {
      return null;
    }
    SimpleLocator localSimpleLocator = new SimpleLocator();
    return element2Locator(paramElement, localSimpleLocator) ? localSimpleLocator : null;
  }
  
  public boolean element2Locator(Element paramElement, SimpleLocator paramSimpleLocator)
  {
    if (paramSimpleLocator == null) {
      return false;
    }
    if ((paramElement instanceof ElementImpl))
    {
      ElementImpl localElementImpl = (ElementImpl)paramElement;
      Document localDocument = localElementImpl.getOwnerDocument();
      String str = (String)fDoc2SystemId.get(DOMUtil.getRoot(localDocument));
      int i = localElementImpl.getLineNumber();
      int j = localElementImpl.getColumnNumber();
      paramSimpleLocator.setValues(str, str, i, j, localElementImpl.getCharacterOffset());
      return true;
    }
    return false;
  }
  
  void reportSchemaError(String paramString, Object[] paramArrayOfObject, Element paramElement)
  {
    if (element2Locator(paramElement, xl)) {
      fErrorReporter.reportError(xl, "http://www.w3.org/TR/xml-schema-1", paramString, paramArrayOfObject, (short)1);
    } else {
      fErrorReporter.reportError("http://www.w3.org/TR/xml-schema-1", paramString, paramArrayOfObject, (short)1);
    }
  }
  
  void reportSchemaWarning(String paramString, Object[] paramArrayOfObject, Element paramElement)
  {
    if (element2Locator(paramElement, xl)) {
      fErrorReporter.reportError(xl, "http://www.w3.org/TR/xml-schema-1", paramString, paramArrayOfObject, (short)0);
    } else {
      fErrorReporter.reportError("http://www.w3.org/TR/xml-schema-1", paramString, paramArrayOfObject, (short)0);
    }
  }
  
  public void setGenerateSyntheticAnnotations(boolean paramBoolean)
  {
    fSchemaParser.setFeature("http://apache.org/xml/features/generate-synthetic-annotations", paramBoolean);
  }
  
  private static class XSDKey
  {
    String systemId;
    short referType;
    String referNS;
    
    XSDKey(String paramString1, short paramShort, String paramString2)
    {
      systemId = paramString1;
      referType = paramShort;
      referNS = paramString2;
    }
    
    public int hashCode()
    {
      return referNS == null ? 0 : referNS.hashCode();
    }
    
    public boolean equals(Object paramObject)
    {
      if (!(paramObject instanceof XSDKey)) {
        return false;
      }
      XSDKey localXSDKey = (XSDKey)paramObject;
      if (((referType == 1) || (referType == 1)) && (referType != referType)) {
        return false;
      }
      if (referNS != referNS) {
        return false;
      }
      return (systemId != null) && (systemId.equals(systemId));
    }
  }
  
  private static class XSAnnotationGrammarPool
    implements XMLGrammarPool
  {
    private XSGrammarBucket fGrammarBucket;
    private Grammar[] fInitialGrammarSet;
    
    private XSAnnotationGrammarPool() {}
    
    public Grammar[] retrieveInitialGrammarSet(String paramString)
    {
      if (paramString == "http://www.w3.org/2001/XMLSchema")
      {
        if (fInitialGrammarSet == null) {
          if (fGrammarBucket == null)
          {
            fInitialGrammarSet = new Grammar[] { SchemaGrammar.SG_Schema4Annotations };
          }
          else
          {
            SchemaGrammar[] arrayOfSchemaGrammar = fGrammarBucket.getGrammars();
            for (int i = 0; i < arrayOfSchemaGrammar.length; i++) {
              if (SchemaSymbols.URI_SCHEMAFORSCHEMA.equals(arrayOfSchemaGrammar[i].getTargetNamespace()))
              {
                fInitialGrammarSet = arrayOfSchemaGrammar;
                return fInitialGrammarSet;
              }
            }
            Grammar[] arrayOfGrammar = new Grammar[arrayOfSchemaGrammar.length + 1];
            System.arraycopy(arrayOfSchemaGrammar, 0, arrayOfGrammar, 0, arrayOfSchemaGrammar.length);
            arrayOfGrammar[(arrayOfGrammar.length - 1)] = SchemaGrammar.SG_Schema4Annotations;
            fInitialGrammarSet = arrayOfGrammar;
          }
        }
        return fInitialGrammarSet;
      }
      return new Grammar[0];
    }
    
    public void cacheGrammars(String paramString, Grammar[] paramArrayOfGrammar) {}
    
    public Grammar retrieveGrammar(XMLGrammarDescription paramXMLGrammarDescription)
    {
      if (paramXMLGrammarDescription.getGrammarType() == "http://www.w3.org/2001/XMLSchema")
      {
        String str = ((XMLSchemaDescription)paramXMLGrammarDescription).getTargetNamespace();
        if (fGrammarBucket != null)
        {
          SchemaGrammar localSchemaGrammar = fGrammarBucket.getGrammar(str);
          if (localSchemaGrammar != null) {
            return localSchemaGrammar;
          }
        }
        if (SchemaSymbols.URI_SCHEMAFORSCHEMA.equals(str)) {
          return SchemaGrammar.SG_Schema4Annotations;
        }
      }
      return null;
    }
    
    public void refreshGrammars(XSGrammarBucket paramXSGrammarBucket)
    {
      fGrammarBucket = paramXSGrammarBucket;
      fInitialGrammarSet = null;
    }
    
    public void lockPool() {}
    
    public void unlockPool() {}
    
    public void clear() {}
    
    XSAnnotationGrammarPool(XSDHandler.1 param1)
    {
      this();
    }
  }
}
