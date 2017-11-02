package org.apache.xerces.xs;

public abstract interface ShortList
{
  public abstract int getLength();
  
  public abstract boolean contains(short paramShort);
  
  public abstract short item(int paramInt)
    throws XSException;
}
