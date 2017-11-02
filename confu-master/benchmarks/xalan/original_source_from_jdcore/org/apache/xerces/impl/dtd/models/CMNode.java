package org.apache.xerces.impl.dtd.models;

public abstract class CMNode
{
  private int fType;
  private CMStateSet fFirstPos = null;
  private CMStateSet fFollowPos = null;
  private CMStateSet fLastPos = null;
  private int fMaxStates = -1;
  
  public CMNode(int paramInt)
  {
    fType = paramInt;
  }
  
  public abstract boolean isNullable();
  
  public final int type()
  {
    return fType;
  }
  
  public final CMStateSet firstPos()
  {
    if (fFirstPos == null)
    {
      fFirstPos = new CMStateSet(fMaxStates);
      calcFirstPos(fFirstPos);
    }
    return fFirstPos;
  }
  
  public final CMStateSet lastPos()
  {
    if (fLastPos == null)
    {
      fLastPos = new CMStateSet(fMaxStates);
      calcLastPos(fLastPos);
    }
    return fLastPos;
  }
  
  final void setFollowPos(CMStateSet paramCMStateSet)
  {
    fFollowPos = paramCMStateSet;
  }
  
  public final void setMaxStates(int paramInt)
  {
    fMaxStates = paramInt;
  }
  
  protected abstract void calcFirstPos(CMStateSet paramCMStateSet);
  
  protected abstract void calcLastPos(CMStateSet paramCMStateSet);
}
