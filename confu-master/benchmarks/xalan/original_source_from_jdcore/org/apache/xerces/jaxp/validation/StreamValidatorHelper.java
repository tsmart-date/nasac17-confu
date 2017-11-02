package org.apache.xerces.jaxp.validation;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.Locale;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import org.apache.xerces.impl.XMLErrorReporter;
import org.apache.xerces.impl.msg.XMLMessageFormatter;
import org.apache.xerces.impl.xs.XMLSchemaValidator;
import org.apache.xerces.parsers.XML11Configuration;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLInputSource;
import org.apache.xerces.xni.parser.XMLParseException;
import org.apache.xerces.xni.parser.XMLParserConfiguration;
import org.xml.sax.SAXException;

final class StreamValidatorHelper
  implements ValidatorHelper
{
  private static final String PARSER_SETTINGS = "http://apache.org/xml/features/internal/parser-settings";
  private static final String ENTITY_RESOLVER = "http://apache.org/xml/properties/internal/entity-resolver";
  private static final String ERROR_HANDLER = "http://apache.org/xml/properties/internal/error-handler";
  private static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
  private static final String SCHEMA_VALIDATOR = "http://apache.org/xml/properties/internal/validator/schema";
  private static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
  private static final String VALIDATION_MANAGER = "http://apache.org/xml/properties/internal/validation-manager";
  private SoftReference fConfiguration = new SoftReference(null);
  private XMLSchemaValidator fSchemaValidator;
  private XMLSchemaValidatorComponentManager fComponentManager;
  
  public StreamValidatorHelper(XMLSchemaValidatorComponentManager paramXMLSchemaValidatorComponentManager)
  {
    fComponentManager = paramXMLSchemaValidatorComponentManager;
    fSchemaValidator = ((XMLSchemaValidator)fComponentManager.getProperty("http://apache.org/xml/properties/internal/validator/schema"));
  }
  
  public void validate(Source paramSource, Result paramResult)
    throws SAXException, IOException
  {
    if (paramResult == null)
    {
      StreamSource localStreamSource = (StreamSource)paramSource;
      XMLInputSource localXMLInputSource = new XMLInputSource(localStreamSource.getPublicId(), localStreamSource.getSystemId(), null);
      localXMLInputSource.setByteStream(localStreamSource.getInputStream());
      localXMLInputSource.setCharacterStream(localStreamSource.getReader());
      XMLParserConfiguration localXMLParserConfiguration = (XMLParserConfiguration)fConfiguration.get();
      if (localXMLParserConfiguration == null)
      {
        localXMLParserConfiguration = initialize();
      }
      else if (fComponentManager.getFeature("http://apache.org/xml/features/internal/parser-settings"))
      {
        localXMLParserConfiguration.setProperty("http://apache.org/xml/properties/internal/entity-resolver", fComponentManager.getProperty("http://apache.org/xml/properties/internal/entity-resolver"));
        localXMLParserConfiguration.setProperty("http://apache.org/xml/properties/internal/error-handler", fComponentManager.getProperty("http://apache.org/xml/properties/internal/error-handler"));
      }
      fComponentManager.reset();
      fSchemaValidator.setDocumentHandler(null);
      try
      {
        localXMLParserConfiguration.parse(localXMLInputSource);
      }
      catch (XMLParseException localXMLParseException)
      {
        throw Util.toSAXParseException(localXMLParseException);
      }
      catch (XNIException localXNIException)
      {
        throw Util.toSAXException(localXNIException);
      }
      return;
    }
    throw new IllegalArgumentException(JAXPValidationMessageFormatter.formatMessage(Locale.getDefault(), "SourceResultMismatch", new Object[] { paramSource.getClass().getName(), paramResult.getClass().getName() }));
  }
  
  private XMLParserConfiguration initialize()
  {
    XML11Configuration localXML11Configuration = new XML11Configuration();
    localXML11Configuration.setProperty("http://apache.org/xml/properties/internal/entity-resolver", fComponentManager.getProperty("http://apache.org/xml/properties/internal/entity-resolver"));
    localXML11Configuration.setProperty("http://apache.org/xml/properties/internal/error-handler", fComponentManager.getProperty("http://apache.org/xml/properties/internal/error-handler"));
    XMLErrorReporter localXMLErrorReporter = (XMLErrorReporter)fComponentManager.getProperty("http://apache.org/xml/properties/internal/error-reporter");
    localXML11Configuration.setProperty("http://apache.org/xml/properties/internal/error-reporter", localXMLErrorReporter);
    if (localXMLErrorReporter.getMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210") == null)
    {
      XMLMessageFormatter localXMLMessageFormatter = new XMLMessageFormatter();
      localXMLErrorReporter.putMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210", localXMLMessageFormatter);
      localXMLErrorReporter.putMessageFormatter("http://www.w3.org/TR/1999/REC-xml-names-19990114", localXMLMessageFormatter);
    }
    localXML11Configuration.setProperty("http://apache.org/xml/properties/internal/symbol-table", fComponentManager.getProperty("http://apache.org/xml/properties/internal/symbol-table"));
    localXML11Configuration.setProperty("http://apache.org/xml/properties/internal/validation-manager", fComponentManager.getProperty("http://apache.org/xml/properties/internal/validation-manager"));
    localXML11Configuration.setDocumentHandler(fSchemaValidator);
    localXML11Configuration.setDTDHandler(null);
    localXML11Configuration.setDTDContentModelHandler(null);
    fConfiguration = new SoftReference(localXML11Configuration);
    return localXML11Configuration;
  }
}
