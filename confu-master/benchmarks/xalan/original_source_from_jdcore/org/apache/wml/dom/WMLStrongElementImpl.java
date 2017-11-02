package org.apache.wml.dom;

import org.apache.wml.WMLStrongElement;
import org.apache.xerces.dom.ElementImpl;

public class WMLStrongElementImpl
  extends WMLElementImpl
  implements WMLStrongElement
{
  private static final long serialVersionUID = 8451363815747099580L;
  
  public WMLStrongElementImpl(WMLDocumentImpl paramWMLDocumentImpl, String paramString)
  {
    super(paramWMLDocumentImpl, paramString);
  }
  
  public void setClassName(String paramString)
  {
    setAttribute("class", paramString);
  }
  
  public String getClassName()
  {
    return getAttribute("class");
  }
  
  public void setXmlLang(String paramString)
  {
    setAttribute("xml:lang", paramString);
  }
  
  public String getXmlLang()
  {
    return getAttribute("xml:lang");
  }
  
  public void setId(String paramString)
  {
    setAttribute("id", paramString);
  }
  
  public String getId()
  {
    return getAttribute("id");
  }
}
