package org.apache.xerces.xs;

public abstract interface StringList
{
  public abstract int getLength();
  
  public abstract boolean contains(String paramString);
  
  public abstract String item(int paramInt);
}
