package org.apache.xerces.dom;

import java.util.Vector;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DOMImplementationList;

public class DOMImplementationListImpl
  implements DOMImplementationList
{
  private Vector fImplementations;
  
  public DOMImplementationListImpl()
  {
    fImplementations = new Vector();
  }
  
  public DOMImplementationListImpl(Vector paramVector)
  {
    fImplementations = paramVector;
  }
  
  public DOMImplementation item(int paramInt)
  {
    try
    {
      return (DOMImplementation)fImplementations.elementAt(paramInt);
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {}
    return null;
  }
  
  public int getLength()
  {
    return fImplementations.size();
  }
}
