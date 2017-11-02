package org.apache.xerces.impl.xs.util;

import org.apache.xerces.xs.ShortList;
import org.apache.xerces.xs.XSException;

public class ShortListImpl
  implements ShortList
{
  private short[] fArray = null;
  private int fLength = 0;
  
  public ShortListImpl(short[] paramArrayOfShort, int paramInt)
  {
    fArray = paramArrayOfShort;
    fLength = paramInt;
  }
  
  public int getLength()
  {
    return fLength;
  }
  
  public boolean contains(short paramShort)
  {
    for (int i = 0; i < fLength; i++) {
      if (fArray[i] == paramShort) {
        return true;
      }
    }
    return false;
  }
  
  public short item(int paramInt)
    throws XSException
  {
    if ((paramInt < 0) || (paramInt >= fLength)) {
      throw new XSException((short)2, null);
    }
    return fArray[paramInt];
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject == null) || (!(paramObject instanceof ShortList))) {
      return false;
    }
    ShortList localShortList = (ShortList)paramObject;
    if (fLength != localShortList.getLength()) {
      return false;
    }
    for (int i = 0; i < fLength; i++) {
      if (fArray[i] != localShortList.item(i)) {
        return false;
      }
    }
    return true;
  }
}
