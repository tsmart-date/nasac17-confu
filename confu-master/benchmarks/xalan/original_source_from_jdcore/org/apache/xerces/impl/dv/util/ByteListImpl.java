package org.apache.xerces.impl.dv.util;

import org.apache.xerces.xs.XSException;
import org.apache.xerces.xs.datatypes.ByteList;

public class ByteListImpl
  implements ByteList
{
  protected final byte[] data;
  protected String canonical;
  
  public ByteListImpl(byte[] paramArrayOfByte)
  {
    data = paramArrayOfByte;
  }
  
  public int getLength()
  {
    return data.length;
  }
  
  public boolean contains(byte paramByte)
  {
    for (int i = 0; i < data.length; i++) {
      if (data[i] == paramByte) {
        return true;
      }
    }
    return false;
  }
  
  public byte item(int paramInt)
    throws XSException
  {
    if ((paramInt < 0) || (paramInt > data.length - 1)) {
      throw new XSException((short)2, null);
    }
    return data[paramInt];
  }
}
