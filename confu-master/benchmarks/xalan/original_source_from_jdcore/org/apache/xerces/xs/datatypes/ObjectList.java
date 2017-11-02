package org.apache.xerces.xs.datatypes;

public abstract interface ObjectList
{
  public abstract int getLength();
  
  public abstract boolean contains(Object paramObject);
  
  public abstract Object item(int paramInt);
}
