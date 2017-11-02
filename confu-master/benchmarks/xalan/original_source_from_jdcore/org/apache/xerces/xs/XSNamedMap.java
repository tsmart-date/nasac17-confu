package org.apache.xerces.xs;

public abstract interface XSNamedMap
{
  public abstract int getLength();
  
  public abstract XSObject item(int paramInt);
  
  public abstract XSObject itemByName(String paramString1, String paramString2);
}
