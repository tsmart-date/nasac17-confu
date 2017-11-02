package org.apache.xerces.dom;

import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class PSVIDOMImplementationImpl
  extends CoreDOMImplementationImpl
{
  static PSVIDOMImplementationImpl singleton = new PSVIDOMImplementationImpl();
  
  public PSVIDOMImplementationImpl() {}
  
  public static DOMImplementation getDOMImplementation()
  {
    return singleton;
  }
  
  public boolean hasFeature(String paramString1, String paramString2)
  {
    return (super.hasFeature(paramString1, paramString2)) || (paramString1.equalsIgnoreCase("psvi"));
  }
  
  public Document createDocument(String paramString1, String paramString2, DocumentType paramDocumentType)
    throws DOMException
  {
    if ((paramDocumentType != null) && (paramDocumentType.getOwnerDocument() != null)) {
      throw new DOMException((short)4, DOMMessageFormatter.formatMessage("http://www.w3.org/TR/1998/REC-xml-19980210", "WRONG_DOCUMENT_ERR", null));
    }
    PSVIDocumentImpl localPSVIDocumentImpl = new PSVIDocumentImpl(paramDocumentType);
    Element localElement = localPSVIDocumentImpl.createElementNS(paramString1, paramString2);
    localPSVIDocumentImpl.appendChild(localElement);
    return localPSVIDocumentImpl;
  }
}
