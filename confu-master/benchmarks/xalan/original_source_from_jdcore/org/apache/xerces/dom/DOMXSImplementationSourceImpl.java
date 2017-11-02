package org.apache.xerces.dom;

import java.util.Vector;
import org.apache.xerces.impl.xs.XSImplementationImpl;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DOMImplementationList;

public class DOMXSImplementationSourceImpl
  extends DOMImplementationSourceImpl
{
  public DOMXSImplementationSourceImpl() {}
  
  public DOMImplementation getDOMImplementation(String paramString)
  {
    DOMImplementation localDOMImplementation = super.getDOMImplementation(paramString);
    if (localDOMImplementation != null) {
      return localDOMImplementation;
    }
    localDOMImplementation = PSVIDOMImplementationImpl.getDOMImplementation();
    if (testImpl(localDOMImplementation, paramString)) {
      return localDOMImplementation;
    }
    localDOMImplementation = XSImplementationImpl.getDOMImplementation();
    if (testImpl(localDOMImplementation, paramString)) {
      return localDOMImplementation;
    }
    return null;
  }
  
  public DOMImplementationList getDOMImplementationList(String paramString)
  {
    Vector localVector = new Vector();
    DOMImplementationList localDOMImplementationList = super.getDOMImplementationList(paramString);
    for (int i = 0; i < localDOMImplementationList.getLength(); i++) {
      localVector.addElement(localDOMImplementationList.item(i));
    }
    DOMImplementation localDOMImplementation = PSVIDOMImplementationImpl.getDOMImplementation();
    if (testImpl(localDOMImplementation, paramString)) {
      localVector.addElement(localDOMImplementation);
    }
    localDOMImplementation = XSImplementationImpl.getDOMImplementation();
    if (testImpl(localDOMImplementation, paramString)) {
      localVector.addElement(localDOMImplementation);
    }
    return new DOMImplementationListImpl(localVector);
  }
}
