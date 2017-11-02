package org.apache.wml.dom;

import org.apache.wml.WMLDOMImplementation;
import org.apache.xerces.dom.CoreDocumentImpl;
import org.apache.xerces.dom.DOMImplementationImpl;
import org.apache.xerces.dom.NodeImpl;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

public class WMLDOMImplementationImpl
  extends DOMImplementationImpl
  implements WMLDOMImplementation
{
  static final DOMImplementationImpl singleton = new WMLDOMImplementationImpl();
  
  public WMLDOMImplementationImpl() {}
  
  public static DOMImplementation getDOMImplementation()
  {
    return singleton;
  }
  
  public Document createDocument(String paramString1, String paramString2, DocumentType paramDocumentType)
    throws DOMException
  {
    WMLDocumentImpl localWMLDocumentImpl = new WMLDocumentImpl(paramDocumentType);
    Element localElement = localWMLDocumentImpl.createElementNS(paramString1, paramString2);
    localWMLDocumentImpl.appendChild(localElement);
    return localWMLDocumentImpl;
  }
}
