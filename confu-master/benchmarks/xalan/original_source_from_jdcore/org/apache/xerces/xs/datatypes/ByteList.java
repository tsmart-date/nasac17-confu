package org.apache.xerces.xs.datatypes;

import org.apache.xerces.xs.XSException;

public abstract interface ByteList
{
  public abstract int getLength();
  
  public abstract boolean contains(byte paramByte);
  
  public abstract byte item(int paramInt)
    throws XSException;
}
