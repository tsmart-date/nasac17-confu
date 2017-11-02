package org.apache.xerces.xs;

public abstract interface XSImplementation
{
  public abstract StringList getRecognizedVersions();
  
  public abstract XSLoader createXSLoader(StringList paramStringList)
    throws XSException;
}
