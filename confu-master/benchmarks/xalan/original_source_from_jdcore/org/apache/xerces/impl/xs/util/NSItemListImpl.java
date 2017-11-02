package org.apache.xerces.impl.xs.util;

import java.util.Vector;
import org.apache.xerces.xs.XSNamespaceItem;
import org.apache.xerces.xs.XSNamespaceItemList;

public class NSItemListImpl
  implements XSNamespaceItemList
{
  private XSNamespaceItem[] fArray = null;
  private int fLength = 0;
  private Vector fVector;
  
  public NSItemListImpl(Vector paramVector)
  {
    fVector = paramVector;
    fLength = paramVector.size();
  }
  
  public NSItemListImpl(XSNamespaceItem[] paramArrayOfXSNamespaceItem, int paramInt)
  {
    fArray = paramArrayOfXSNamespaceItem;
    fLength = paramInt;
  }
  
  public int getLength()
  {
    return fLength;
  }
  
  public XSNamespaceItem item(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= fLength)) {
      return null;
    }
    if (fVector != null) {
      return (XSNamespaceItem)fVector.elementAt(paramInt);
    }
    return fArray[paramInt];
  }
}
