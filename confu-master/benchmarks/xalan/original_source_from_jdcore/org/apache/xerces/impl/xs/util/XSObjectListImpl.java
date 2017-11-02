package org.apache.xerces.impl.xs.util;

import org.apache.xerces.xs.XSObject;
import org.apache.xerces.xs.XSObjectList;

public class XSObjectListImpl
  implements XSObjectList
{
  public static final XSObjectList EMPTY_LIST = new XSObjectList()
  {
    public int getLength()
    {
      return 0;
    }
    
    public XSObject item(int paramAnonymousInt)
    {
      return null;
    }
  };
  private static final int DEFAULT_SIZE = 4;
  private XSObject[] fArray = null;
  private int fLength = 0;
  
  public XSObjectListImpl()
  {
    fArray = new XSObject[4];
    fLength = 0;
  }
  
  public XSObjectListImpl(XSObject[] paramArrayOfXSObject, int paramInt)
  {
    fArray = paramArrayOfXSObject;
    fLength = paramInt;
  }
  
  public int getLength()
  {
    return fLength;
  }
  
  public XSObject item(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= fLength)) {
      return null;
    }
    return fArray[paramInt];
  }
  
  public void clear()
  {
    for (int i = 0; i < fLength; i++) {
      fArray[i] = null;
    }
    fArray = null;
    fLength = 0;
  }
  
  public void add(XSObject paramXSObject)
  {
    if (fLength == fArray.length)
    {
      XSObject[] arrayOfXSObject = new XSObject[fLength + 4];
      System.arraycopy(fArray, 0, arrayOfXSObject, 0, fLength);
      fArray = arrayOfXSObject;
    }
    fArray[(fLength++)] = paramXSObject;
  }
  
  public void add(int paramInt, XSObject paramXSObject)
  {
    fArray[paramInt] = paramXSObject;
  }
}
