package org.apache.xerces.jaxp.validation;

import java.io.IOException;
import java.util.Locale;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Validator;
import org.apache.xerces.util.SAXMessageFormatter;
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.apache.xerces.xs.AttributePSVI;
import org.apache.xerces.xs.ElementPSVI;
import org.apache.xerces.xs.PSVIProvider;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

final class ValidatorImpl
  extends Validator
  implements PSVIProvider
{
  private static final String CURRENT_ELEMENT_NODE = "http://apache.org/xml/properties/dom/current-element-node";
  private XMLSchemaValidatorComponentManager fComponentManager;
  private ValidatorHandlerImpl fSAXValidatorHelper;
  private DOMValidatorHelper fDOMValidatorHelper;
  private StreamValidatorHelper fStreamValidatorHelper;
  private boolean fConfigurationChanged = false;
  private boolean fErrorHandlerChanged = false;
  private boolean fResourceResolverChanged = false;
  
  public ValidatorImpl(XSGrammarPoolContainer paramXSGrammarPoolContainer)
  {
    fComponentManager = new XMLSchemaValidatorComponentManager(paramXSGrammarPoolContainer);
    setErrorHandler(null);
    setResourceResolver(null);
  }
  
  public void validate(Source paramSource, Result paramResult)
    throws SAXException, IOException
  {
    if ((paramSource instanceof SAXSource))
    {
      if (fSAXValidatorHelper == null) {
        fSAXValidatorHelper = new ValidatorHandlerImpl(fComponentManager);
      }
      fSAXValidatorHelper.validate(paramSource, paramResult);
    }
    else if ((paramSource instanceof DOMSource))
    {
      if (fDOMValidatorHelper == null) {
        fDOMValidatorHelper = new DOMValidatorHelper(fComponentManager);
      }
      fDOMValidatorHelper.validate(paramSource, paramResult);
    }
    else if ((paramSource instanceof StreamSource))
    {
      if (fStreamValidatorHelper == null) {
        fStreamValidatorHelper = new StreamValidatorHelper(fComponentManager);
      }
      fStreamValidatorHelper.validate(paramSource, paramResult);
    }
    else
    {
      if (paramSource == null) {
        throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(Locale.getDefault(), "SourceParameterNull", null));
      }
      throw new IllegalArgumentException(JAXPValidationMessageFormatter.formatMessage(Locale.getDefault(), "SourceNotAccepted", new Object[] { paramSource.getClass().getName() }));
    }
  }
  
  public void setErrorHandler(ErrorHandler paramErrorHandler)
  {
    fErrorHandlerChanged = (paramErrorHandler != null);
    fComponentManager.setErrorHandler(paramErrorHandler);
  }
  
  public ErrorHandler getErrorHandler()
  {
    return fComponentManager.getErrorHandler();
  }
  
  public void setResourceResolver(LSResourceResolver paramLSResourceResolver)
  {
    fResourceResolverChanged = (paramLSResourceResolver != null);
    fComponentManager.setResourceResolver(paramLSResourceResolver);
  }
  
  public LSResourceResolver getResourceResolver()
  {
    return fComponentManager.getResourceResolver();
  }
  
  public boolean getFeature(String paramString)
    throws SAXNotRecognizedException, SAXNotSupportedException
  {
    if (paramString == null) {
      throw new NullPointerException();
    }
    try
    {
      return fComponentManager.getFeature(paramString);
    }
    catch (XMLConfigurationException localXMLConfigurationException)
    {
      String str1 = localXMLConfigurationException.getIdentifier();
      String str2 = localXMLConfigurationException.getType() == 0 ? "feature-not-recognized" : "feature-not-supported";
      throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(Locale.getDefault(), str2, new Object[] { str1 }));
    }
  }
  
  public void setFeature(String paramString, boolean paramBoolean)
    throws SAXNotRecognizedException, SAXNotSupportedException
  {
    if (paramString == null) {
      throw new NullPointerException();
    }
    try
    {
      fComponentManager.setFeature(paramString, paramBoolean);
    }
    catch (XMLConfigurationException localXMLConfigurationException)
    {
      String str1 = localXMLConfigurationException.getIdentifier();
      String str2 = localXMLConfigurationException.getType() == 0 ? "feature-not-recognized" : "feature-not-supported";
      throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(Locale.getDefault(), str2, new Object[] { str1 }));
    }
    fConfigurationChanged = true;
  }
  
  public Object getProperty(String paramString)
    throws SAXNotRecognizedException, SAXNotSupportedException
  {
    if (paramString == null) {
      throw new NullPointerException();
    }
    if ("http://apache.org/xml/properties/dom/current-element-node".equals(paramString)) {
      return fDOMValidatorHelper != null ? fDOMValidatorHelper.getCurrentElement() : null;
    }
    try
    {
      return fComponentManager.getProperty(paramString);
    }
    catch (XMLConfigurationException localXMLConfigurationException)
    {
      String str1 = localXMLConfigurationException.getIdentifier();
      String str2 = localXMLConfigurationException.getType() == 0 ? "property-not-recognized" : "property-not-supported";
      throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(Locale.getDefault(), str2, new Object[] { str1 }));
    }
  }
  
  public void setProperty(String paramString, Object paramObject)
    throws SAXNotRecognizedException, SAXNotSupportedException
  {
    if (paramString == null) {
      throw new NullPointerException();
    }
    if ("http://apache.org/xml/properties/dom/current-element-node".equals(paramString)) {
      throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(Locale.getDefault(), "property-read-only", new Object[] { paramString }));
    }
    try
    {
      fComponentManager.setProperty(paramString, paramObject);
    }
    catch (XMLConfigurationException localXMLConfigurationException)
    {
      String str1 = localXMLConfigurationException.getIdentifier();
      String str2 = localXMLConfigurationException.getType() == 0 ? "property-not-recognized" : "property-not-supported";
      throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(Locale.getDefault(), str2, new Object[] { str1 }));
    }
    fConfigurationChanged = true;
  }
  
  public void reset()
  {
    if (fConfigurationChanged)
    {
      fComponentManager.restoreInitialState();
      setErrorHandler(null);
      setResourceResolver(null);
      fConfigurationChanged = false;
      fErrorHandlerChanged = false;
      fResourceResolverChanged = false;
    }
    else
    {
      if (fErrorHandlerChanged)
      {
        setErrorHandler(null);
        fErrorHandlerChanged = false;
      }
      if (fResourceResolverChanged)
      {
        setResourceResolver(null);
        fResourceResolverChanged = false;
      }
    }
  }
  
  public ElementPSVI getElementPSVI()
  {
    return fSAXValidatorHelper != null ? fSAXValidatorHelper.getElementPSVI() : null;
  }
  
  public AttributePSVI getAttributePSVI(int paramInt)
  {
    return fSAXValidatorHelper != null ? fSAXValidatorHelper.getAttributePSVI(paramInt) : null;
  }
  
  public AttributePSVI getAttributePSVIByName(String paramString1, String paramString2)
  {
    return fSAXValidatorHelper != null ? fSAXValidatorHelper.getAttributePSVIByName(paramString1, paramString2) : null;
  }
}
