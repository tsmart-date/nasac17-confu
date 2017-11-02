package org.apache.xerces.xs;

import org.w3c.dom.ls.LSInput;

public abstract interface LSInputList
{
  public abstract int getLength();
  
  public abstract LSInput item(int paramInt);
}
