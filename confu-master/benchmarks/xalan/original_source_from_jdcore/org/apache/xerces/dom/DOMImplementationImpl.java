package org.apache.xerces.dom;

import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class DOMImplementationImpl
  extends CoreDOMImplementationImpl
  implements DOMImplementation
{
  static DOMImplementationImpl singleton = new DOMImplementationImpl();
  
  public DOMImplementationImpl() {}
  
  public static DOMImplementation getDOMImplementation()
  {
    return singleton;
  }
  
  public boolean hasFeature(String paramString1, String paramString2)
  {
    boolean bool = super.hasFeature(paramString1, paramString2);
    if (!bool)
    {
      int i = (paramString2 == null) || (paramString2.length() == 0) ? 1 : 0;
      if (paramString1.startsWith("+")) {
        paramString1 = paramString1.substring(1);
      }
      return ((paramString1.equalsIgnoreCase("Events")) && ((i != 0) || (paramString2.equals("2.0")))) || ((paramString1.equalsIgnoreCase("MutationEvents")) && ((i != 0) || (paramString2.equals("2.0")))) || ((paramString1.equalsIgnoreCase("Traversal")) && ((i != 0) || (paramString2.equals("2.0")))) || ((paramString1.equalsIgnoreCase("Range")) && ((i != 0) || (paramString2.equals("2.0")))) || ((paramString1.equalsIgnoreCase("MutationEvents")) && ((i != 0) || (paramString2.equals("2.0"))));
    }
    return bool;
  }
  
  public Document createDocument(String paramString1, String paramString2, DocumentType paramDocumentType)
    throws DOMException
  {
    if ((paramString1 == null) && (paramString2 == null) && (paramDocumentType == null)) {
      return new DocumentImpl();
    }
    if ((paramDocumentType != null) && (paramDocumentType.getOwnerDocument() != null))
    {
      localObject = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "WRONG_DOCUMENT_ERR", null);
      throw new DOMException((short)4, (String)localObject);
    }
    Object localObject = new DocumentImpl(paramDocumentType);
    Element localElement = ((CoreDocumentImpl)localObject).createElementNS(paramString1, paramString2);
    ((NodeImpl)localObject).appendChild(localElement);
    return localObject;
  }
}
