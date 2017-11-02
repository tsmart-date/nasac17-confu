package org.apache.xerces.jaxp;

import java.util.Hashtable;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;

public class SAXParserFactoryImpl
  extends SAXParserFactory
{
  private Hashtable features;
  private Schema grammar;
  private boolean isXIncludeAware;
  private boolean fSecureProcess = false;
  
  public SAXParserFactoryImpl() {}
  
  public SAXParser newSAXParser()
    throws ParserConfigurationException
  {
    SAXParserImpl localSAXParserImpl;
    try
    {
      localSAXParserImpl = new SAXParserImpl(this, features, fSecureProcess);
    }
    catch (SAXException localSAXException)
    {
      throw new ParserConfigurationException(localSAXException.getMessage());
    }
    return localSAXParserImpl;
  }
  
  private SAXParserImpl newSAXParserImpl()
    throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException
  {
    SAXParserImpl localSAXParserImpl;
    try
    {
      localSAXParserImpl = new SAXParserImpl(this, features);
    }
    catch (SAXNotSupportedException localSAXNotSupportedException)
    {
      throw localSAXNotSupportedException;
    }
    catch (SAXNotRecognizedException localSAXNotRecognizedException)
    {
      throw localSAXNotRecognizedException;
    }
    catch (SAXException localSAXException)
    {
      throw new ParserConfigurationException(localSAXException.getMessage());
    }
    return localSAXParserImpl;
  }
  
  public void setFeature(String paramString, boolean paramBoolean)
    throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException
  {
    if (paramString == null) {
      throw new NullPointerException();
    }
    if (paramString.equals("http://javax.xml.XMLConstants/feature/secure-processing"))
    {
      fSecureProcess = paramBoolean;
      return;
    }
    if (features == null) {
      features = new Hashtable();
    }
    features.put(paramString, paramBoolean ? Boolean.TRUE : Boolean.FALSE);
    try
    {
      newSAXParserImpl();
    }
    catch (SAXNotSupportedException localSAXNotSupportedException)
    {
      features.remove(paramString);
      throw localSAXNotSupportedException;
    }
    catch (SAXNotRecognizedException localSAXNotRecognizedException)
    {
      features.remove(paramString);
      throw localSAXNotRecognizedException;
    }
  }
  
  public boolean getFeature(String paramString)
    throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException
  {
    if (paramString == null) {
      throw new NullPointerException();
    }
    if (paramString.equals("http://javax.xml.XMLConstants/feature/secure-processing")) {
      return fSecureProcess;
    }
    return newSAXParserImpl().getXMLReader().getFeature(paramString);
  }
  
  public Schema getSchema()
  {
    return grammar;
  }
  
  public void setSchema(Schema paramSchema)
  {
    grammar = paramSchema;
  }
  
  public boolean isXIncludeAware()
  {
    return isXIncludeAware;
  }
  
  public void setXIncludeAware(boolean paramBoolean)
  {
    isXIncludeAware = paramBoolean;
  }
}
