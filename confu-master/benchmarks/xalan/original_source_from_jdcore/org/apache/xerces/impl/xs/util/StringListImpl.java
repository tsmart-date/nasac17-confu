package org.apache.xerces.impl.xs.util;

import java.util.Vector;
import org.apache.xerces.xs.StringList;

public class StringListImpl
  implements StringList
{
  public static final StringList EMPTY_LIST = new StringList()
  {
    public int getLength()
    {
      return 0;
    }
    
    public boolean contains(String paramAnonymousString)
    {
      return false;
    }
    
    public String item(int paramAnonymousInt)
    {
      return null;
    }
  };
  private String[] fArray = null;
  private int fLength = 0;
  private Vector fVector;
  
  public StringListImpl(Vector paramVector)
  {
    fVector = paramVector;
    fLength = (paramVector == null ? 0 : paramVector.size());
  }
  
  public StringListImpl(String[] paramArrayOfString, int paramInt)
  {
    fArray = paramArrayOfString;
    fLength = paramInt;
  }
  
  public int getLength()
  {
    return fLength;
  }
  
  public boolean contains(String paramString)
  {
    if (fVector != null) {
      return fVector.contains(paramString);
    }
    int i;
    if (paramString == null) {
      for (i = 0; i < fLength; i++) {
        if (fArray[i] == null) {
          return true;
        }
      }
    } else {
      for (i = 0; i < fLength; i++) {
        if (paramString.equals(fArray[i])) {
          return true;
        }
      }
    }
    return false;
  }
  
  public String item(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= fLength)) {
      return null;
    }
    if (fVector != null) {
      return (String)fVector.elementAt(paramInt);
    }
    return fArray[paramInt];
  }
}
