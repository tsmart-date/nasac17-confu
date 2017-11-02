package org.apache.xerces.dom;

import java.util.Vector;
import org.w3c.dom.DOMStringList;

public class DOMStringListImpl
  implements DOMStringList
{
  private Vector fStrings;
  
  public DOMStringListImpl()
  {
    fStrings = new Vector();
  }
  
  public DOMStringListImpl(Vector paramVector)
  {
    fStrings = paramVector;
  }
  
  public String item(int paramInt)
  {
    try
    {
      return (String)fStrings.elementAt(paramInt);
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {}
    return null;
  }
  
  public int getLength()
  {
    return fStrings.size();
  }
  
  public boolean contains(String paramString)
  {
    return fStrings.contains(paramString);
  }
  
  public void add(String paramString)
  {
    fStrings.add(paramString);
  }
}
