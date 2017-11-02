package javax.xml.validation;

import java.io.File;
import java.net.URL;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

public abstract class SchemaFactory
{
  private static SecuritySupport ss = new SecuritySupport();
  
  protected SchemaFactory() {}
  
  public static final SchemaFactory newInstance(String paramString)
  {
    ClassLoader localClassLoader = ss.getContextClassLoader();
    if (localClassLoader == null) {
      localClassLoader = SchemaFactory.class.getClassLoader();
    }
    SchemaFactory localSchemaFactory = new SchemaFactoryFinder(localClassLoader).newFactory(paramString);
    if (localSchemaFactory == null) {
      throw new IllegalArgumentException(paramString);
    }
    return localSchemaFactory;
  }
  
  public abstract boolean isSchemaLanguageSupported(String paramString);
  
  public boolean getFeature(String paramString)
    throws SAXNotRecognizedException, SAXNotSupportedException
  {
    if (paramString == null) {
      throw new NullPointerException("the name parameter is null");
    }
    throw new SAXNotRecognizedException(paramString);
  }
  
  public void setFeature(String paramString, boolean paramBoolean)
    throws SAXNotRecognizedException, SAXNotSupportedException
  {
    if (paramString == null) {
      throw new NullPointerException("the name parameter is null");
    }
    throw new SAXNotRecognizedException(paramString);
  }
  
  public void setProperty(String paramString, Object paramObject)
    throws SAXNotRecognizedException, SAXNotSupportedException
  {
    if (paramString == null) {
      throw new NullPointerException("the name parameter is null");
    }
    throw new SAXNotRecognizedException(paramString);
  }
  
  public Object getProperty(String paramString)
    throws SAXNotRecognizedException, SAXNotSupportedException
  {
    if (paramString == null) {
      throw new NullPointerException("the name parameter is null");
    }
    throw new SAXNotRecognizedException(paramString);
  }
  
  public abstract void setErrorHandler(ErrorHandler paramErrorHandler);
  
  public abstract ErrorHandler getErrorHandler();
  
  public abstract void setResourceResolver(LSResourceResolver paramLSResourceResolver);
  
  public abstract LSResourceResolver getResourceResolver();
  
  public Schema newSchema(Source paramSource)
    throws SAXException
  {
    return newSchema(new Source[] { paramSource });
  }
  
  public Schema newSchema(File paramFile)
    throws SAXException
  {
    return newSchema(new StreamSource(paramFile));
  }
  
  public Schema newSchema(URL paramURL)
    throws SAXException
  {
    return newSchema(new StreamSource(paramURL.toExternalForm()));
  }
  
  public abstract Schema newSchema(Source[] paramArrayOfSource)
    throws SAXException;
  
  public abstract Schema newSchema()
    throws SAXException;
}
