package org.apache.xerces.impl.xs.opti;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import org.apache.xerces.impl.XMLDTDScannerImpl;
import org.apache.xerces.impl.XMLEntityManager;
import org.apache.xerces.impl.XMLErrorReporter;
import org.apache.xerces.impl.XMLNSDocumentScannerImpl;
import org.apache.xerces.impl.dv.DTDDVFactory;
import org.apache.xerces.impl.msg.XMLMessageFormatter;
import org.apache.xerces.impl.validation.ValidationManager;
import org.apache.xerces.impl.xs.XSMessageFormatter;
import org.apache.xerces.parsers.BasicParserConfiguration;
import org.apache.xerces.util.MessageFormatter;
import org.apache.xerces.util.ParserConfigurationSettings;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.xni.XMLDocumentHandler;
import org.apache.xerces.xni.XMLLocator;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.grammars.XMLGrammarPool;
import org.apache.xerces.xni.parser.XMLComponent;
import org.apache.xerces.xni.parser.XMLComponentManager;
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.apache.xerces.xni.parser.XMLDTDContentModelSource;
import org.apache.xerces.xni.parser.XMLDTDScanner;
import org.apache.xerces.xni.parser.XMLDTDSource;
import org.apache.xerces.xni.parser.XMLDocumentScanner;
import org.apache.xerces.xni.parser.XMLDocumentSource;
import org.apache.xerces.xni.parser.XMLInputSource;
import org.apache.xerces.xni.parser.XMLPullParserConfiguration;
import org.w3c.dom.Document;

public class SchemaParsingConfig
  extends BasicParserConfiguration
  implements XMLPullParserConfiguration
{
  protected static final String WARN_ON_DUPLICATE_ATTDEF = "http://apache.org/xml/features/validation/warn-on-duplicate-attdef";
  protected static final String WARN_ON_UNDECLARED_ELEMDEF = "http://apache.org/xml/features/validation/warn-on-undeclared-elemdef";
  protected static final String ALLOW_JAVA_ENCODINGS = "http://apache.org/xml/features/allow-java-encodings";
  protected static final String CONTINUE_AFTER_FATAL_ERROR = "http://apache.org/xml/features/continue-after-fatal-error";
  protected static final String LOAD_EXTERNAL_DTD = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
  protected static final String NOTIFY_BUILTIN_REFS = "http://apache.org/xml/features/scanner/notify-builtin-refs";
  protected static final String NOTIFY_CHAR_REFS = "http://apache.org/xml/features/scanner/notify-char-refs";
  protected static final String NORMALIZE_DATA = "http://apache.org/xml/features/validation/schema/normalized-value";
  protected static final String SCHEMA_ELEMENT_DEFAULT = "http://apache.org/xml/features/validation/schema/element-default";
  protected static final String GENERATE_SYNTHETIC_ANNOTATION = "http://apache.org/xml/features/generate-synthetic-annotations";
  protected static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
  protected static final String ENTITY_MANAGER = "http://apache.org/xml/properties/internal/entity-manager";
  protected static final String DOCUMENT_SCANNER = "http://apache.org/xml/properties/internal/document-scanner";
  protected static final String DTD_SCANNER = "http://apache.org/xml/properties/internal/dtd-scanner";
  protected static final String XMLGRAMMAR_POOL = "http://apache.org/xml/properties/internal/grammar-pool";
  protected static final String DTD_VALIDATOR = "http://apache.org/xml/properties/internal/validator/dtd";
  protected static final String NAMESPACE_BINDER = "http://apache.org/xml/properties/internal/namespace-binder";
  protected static final String DATATYPE_VALIDATOR_FACTORY = "http://apache.org/xml/properties/internal/datatype-validator-factory";
  protected static final String VALIDATION_MANAGER = "http://apache.org/xml/properties/internal/validation-manager";
  protected static final String SCHEMA_VALIDATOR = "http://apache.org/xml/properties/internal/validator/schema";
  private static final boolean PRINT_EXCEPTION_STACK_TRACE = false;
  protected XMLGrammarPool fGrammarPool;
  protected DTDDVFactory fDatatypeValidatorFactory;
  protected XMLErrorReporter fErrorReporter;
  protected XMLEntityManager fEntityManager;
  protected XMLDocumentScanner fScanner;
  protected XMLInputSource fInputSource;
  protected XMLDTDScanner fDTDScanner;
  protected SchemaDOMParser fSchemaDOMParser;
  protected ValidationManager fValidationManager;
  protected XMLLocator fLocator;
  protected boolean fParseInProgress = false;
  
  public SchemaParsingConfig()
  {
    this(null, null, null);
  }
  
  public SchemaParsingConfig(SymbolTable paramSymbolTable)
  {
    this(paramSymbolTable, null, null);
  }
  
  public SchemaParsingConfig(SymbolTable paramSymbolTable, XMLGrammarPool paramXMLGrammarPool)
  {
    this(paramSymbolTable, paramXMLGrammarPool, null);
  }
  
  public SchemaParsingConfig(SymbolTable paramSymbolTable, XMLGrammarPool paramXMLGrammarPool, XMLComponentManager paramXMLComponentManager)
  {
    super(paramSymbolTable, paramXMLComponentManager);
    String[] arrayOfString1 = { "http://apache.org/xml/features/internal/parser-settings", "http://apache.org/xml/features/validation/warn-on-duplicate-attdef", "http://apache.org/xml/features/validation/warn-on-undeclared-elemdef", "http://apache.org/xml/features/allow-java-encodings", "http://apache.org/xml/features/continue-after-fatal-error", "http://apache.org/xml/features/nonvalidating/load-external-dtd", "http://apache.org/xml/features/scanner/notify-builtin-refs", "http://apache.org/xml/features/scanner/notify-char-refs", "http://apache.org/xml/features/generate-synthetic-annotations" };
    addRecognizedFeatures(arrayOfString1);
    fFeatures.put("http://apache.org/xml/features/internal/parser-settings", Boolean.TRUE);
    fFeatures.put("http://apache.org/xml/features/validation/warn-on-duplicate-attdef", Boolean.FALSE);
    fFeatures.put("http://apache.org/xml/features/validation/warn-on-undeclared-elemdef", Boolean.FALSE);
    fFeatures.put("http://apache.org/xml/features/allow-java-encodings", Boolean.FALSE);
    fFeatures.put("http://apache.org/xml/features/continue-after-fatal-error", Boolean.FALSE);
    fFeatures.put("http://apache.org/xml/features/nonvalidating/load-external-dtd", Boolean.TRUE);
    fFeatures.put("http://apache.org/xml/features/scanner/notify-builtin-refs", Boolean.FALSE);
    fFeatures.put("http://apache.org/xml/features/scanner/notify-char-refs", Boolean.FALSE);
    fFeatures.put("http://apache.org/xml/features/generate-synthetic-annotations", Boolean.FALSE);
    String[] arrayOfString2 = { "http://apache.org/xml/properties/internal/error-reporter", "http://apache.org/xml/properties/internal/entity-manager", "http://apache.org/xml/properties/internal/document-scanner", "http://apache.org/xml/properties/internal/dtd-scanner", "http://apache.org/xml/properties/internal/validator/dtd", "http://apache.org/xml/properties/internal/namespace-binder", "http://apache.org/xml/properties/internal/grammar-pool", "http://apache.org/xml/properties/internal/datatype-validator-factory", "http://apache.org/xml/properties/internal/validation-manager", "http://apache.org/xml/features/generate-synthetic-annotations" };
    addRecognizedProperties(arrayOfString2);
    fGrammarPool = paramXMLGrammarPool;
    if (fGrammarPool != null) {
      setProperty("http://apache.org/xml/properties/internal/grammar-pool", fGrammarPool);
    }
    fEntityManager = new XMLEntityManager();
    fProperties.put("http://apache.org/xml/properties/internal/entity-manager", fEntityManager);
    addComponent(fEntityManager);
    fErrorReporter = new XMLErrorReporter();
    fErrorReporter.setDocumentLocator(fEntityManager.getEntityScanner());
    fProperties.put("http://apache.org/xml/properties/internal/error-reporter", fErrorReporter);
    addComponent(fErrorReporter);
    fScanner = new XMLNSDocumentScannerImpl();
    fProperties.put("http://apache.org/xml/properties/internal/document-scanner", fScanner);
    addComponent((XMLComponent)fScanner);
    fDTDScanner = new XMLDTDScannerImpl();
    fProperties.put("http://apache.org/xml/properties/internal/dtd-scanner", fDTDScanner);
    addComponent((XMLComponent)fDTDScanner);
    fDatatypeValidatorFactory = DTDDVFactory.getInstance();
    fProperties.put("http://apache.org/xml/properties/internal/datatype-validator-factory", fDatatypeValidatorFactory);
    fValidationManager = new ValidationManager();
    fProperties.put("http://apache.org/xml/properties/internal/validation-manager", fValidationManager);
    Object localObject;
    if (fErrorReporter.getMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210") == null)
    {
      localObject = new XMLMessageFormatter();
      fErrorReporter.putMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210", (MessageFormatter)localObject);
      fErrorReporter.putMessageFormatter("http://www.w3.org/TR/1999/REC-xml-names-19990114", (MessageFormatter)localObject);
    }
    if (fErrorReporter.getMessageFormatter("http://www.w3.org/TR/xml-schema-1") == null)
    {
      localObject = new XSMessageFormatter();
      fErrorReporter.putMessageFormatter("http://www.w3.org/TR/xml-schema-1", (MessageFormatter)localObject);
    }
    try
    {
      setLocale(Locale.getDefault());
    }
    catch (XNIException localXNIException) {}
  }
  
  public void setLocale(Locale paramLocale)
    throws XNIException
  {
    super.setLocale(paramLocale);
    fErrorReporter.setLocale(paramLocale);
  }
  
  public void setInputSource(XMLInputSource paramXMLInputSource)
    throws XMLConfigurationException, IOException
  {
    fInputSource = paramXMLInputSource;
  }
  
  public boolean parse(boolean paramBoolean)
    throws XNIException, IOException
  {
    if (fInputSource != null) {
      try
      {
        reset();
        fScanner.setInputSource(fInputSource);
        fInputSource = null;
      }
      catch (XNIException localXNIException1)
      {
        throw localXNIException1;
      }
      catch (IOException localIOException1)
      {
        throw localIOException1;
      }
      catch (RuntimeException localRuntimeException1)
      {
        throw localRuntimeException1;
      }
      catch (Exception localException1)
      {
        throw new XNIException(localException1);
      }
    }
    try
    {
      return fScanner.scanDocument(paramBoolean);
    }
    catch (XNIException localXNIException2)
    {
      throw localXNIException2;
    }
    catch (IOException localIOException2)
    {
      throw localIOException2;
    }
    catch (RuntimeException localRuntimeException2)
    {
      throw localRuntimeException2;
    }
    catch (Exception localException2)
    {
      throw new XNIException(localException2);
    }
  }
  
  public void cleanup()
  {
    fEntityManager.closeReaders();
  }
  
  public void parse(XMLInputSource paramXMLInputSource)
    throws XNIException, IOException
  {
    if (fParseInProgress) {
      throw new XNIException("FWK005 parse may not be called while parsing.");
    }
    fParseInProgress = true;
    try
    {
      setInputSource(paramXMLInputSource);
      parse(true);
    }
    catch (XNIException localXNIException)
    {
      throw localXNIException;
    }
    catch (IOException localIOException)
    {
      throw localIOException;
    }
    catch (RuntimeException localRuntimeException)
    {
      throw localRuntimeException;
    }
    catch (Exception localException)
    {
      throw new XNIException(localException);
    }
    finally
    {
      fParseInProgress = false;
      cleanup();
    }
  }
  
  public void reset()
    throws XNIException
  {
    if (fSchemaDOMParser == null) {
      fSchemaDOMParser = new SchemaDOMParser(this);
    }
    fDocumentHandler = fSchemaDOMParser;
    fDTDHandler = fSchemaDOMParser;
    fDTDContentModelHandler = fSchemaDOMParser;
    configurePipeline();
    super.reset();
  }
  
  protected void configurePipeline()
  {
    fScanner.setDocumentHandler(fDocumentHandler);
    fDocumentHandler.setDocumentSource(fScanner);
    fLastComponent = fScanner;
    if (fDTDScanner != null)
    {
      fDTDScanner.setDTDHandler(fDTDHandler);
      fDTDScanner.setDTDContentModelHandler(fDTDContentModelHandler);
    }
  }
  
  protected void checkFeature(String paramString)
    throws XMLConfigurationException
  {
    if (paramString.startsWith("http://apache.org/xml/features/"))
    {
      int i = paramString.length() - "http://apache.org/xml/features/".length();
      if ((i == "validation/dynamic".length()) && (paramString.endsWith("validation/dynamic"))) {
        return;
      }
      short s;
      if ((i == "validation/default-attribute-values".length()) && (paramString.endsWith("validation/default-attribute-values")))
      {
        s = 1;
        throw new XMLConfigurationException(s, paramString);
      }
      if ((i == "validation/validate-content-models".length()) && (paramString.endsWith("validation/validate-content-models")))
      {
        s = 1;
        throw new XMLConfigurationException(s, paramString);
      }
      if ((i == "nonvalidating/load-dtd-grammar".length()) && (paramString.endsWith("nonvalidating/load-dtd-grammar"))) {
        return;
      }
      if ((i == "nonvalidating/load-external-dtd".length()) && (paramString.endsWith("nonvalidating/load-external-dtd"))) {
        return;
      }
      if ((i == "validation/validate-datatypes".length()) && (paramString.endsWith("validation/validate-datatypes")))
      {
        s = 1;
        throw new XMLConfigurationException(s, paramString);
      }
    }
    super.checkFeature(paramString);
  }
  
  protected void checkProperty(String paramString)
    throws XMLConfigurationException
  {
    int i;
    if (paramString.startsWith("http://apache.org/xml/properties/"))
    {
      i = paramString.length() - "http://apache.org/xml/properties/".length();
      if ((i == "internal/dtd-scanner".length()) && (paramString.endsWith("internal/dtd-scanner"))) {
        return;
      }
    }
    if (paramString.startsWith("http://java.sun.com/xml/jaxp/properties/"))
    {
      i = paramString.length() - "http://java.sun.com/xml/jaxp/properties/".length();
      if ((i == "schemaSource".length()) && (paramString.endsWith("schemaSource"))) {
        return;
      }
    }
    super.checkProperty(paramString);
  }
  
  public Document getDocument()
  {
    return fSchemaDOMParser.getDocument();
  }
  
  public void resetNodePool() {}
}
