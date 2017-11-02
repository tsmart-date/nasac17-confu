package org.apache.xerces.impl.xs.util;

import org.apache.xerces.util.SymbolHash;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xs.XSNamedMap;
import org.apache.xerces.xs.XSObject;

public class XSNamedMapImpl
  implements XSNamedMap
{
  public static final XSNamedMap EMPTY_MAP = new XSNamedMap()
  {
    public int getLength()
    {
      return 0;
    }
    
    public XSObject itemByName(String paramAnonymousString1, String paramAnonymousString2)
    {
      return null;
    }
    
    public XSObject item(int paramAnonymousInt)
    {
      return null;
    }
  };
  String[] fNamespaces;
  int fNSNum;
  SymbolHash[] fMaps;
  XSObject[] fArray = null;
  int fLength = -1;
  QName fName = new QName();
  
  public XSNamedMapImpl(String paramString, SymbolHash paramSymbolHash)
  {
    fNamespaces = new String[] { paramString };
    fMaps = new SymbolHash[] { paramSymbolHash };
    fNSNum = 1;
  }
  
  public XSNamedMapImpl(String[] paramArrayOfString, SymbolHash[] paramArrayOfSymbolHash, int paramInt)
  {
    fNamespaces = paramArrayOfString;
    fMaps = paramArrayOfSymbolHash;
    fNSNum = paramInt;
  }
  
  public XSNamedMapImpl(XSObject[] paramArrayOfXSObject, int paramInt)
  {
    if (paramInt == 0)
    {
      fNSNum = 0;
      fLength = 0;
      return;
    }
    fNamespaces = new String[] { paramArrayOfXSObject[0].getNamespace() };
    fMaps = null;
    fNSNum = 1;
    fArray = paramArrayOfXSObject;
    fLength = paramInt;
  }
  
  public synchronized int getLength()
  {
    if (fLength == -1)
    {
      fLength = 0;
      for (int i = 0; i < fNSNum; i++) {
        fLength += fMaps[i].getLength();
      }
    }
    return fLength;
  }
  
  public XSObject itemByName(String paramString1, String paramString2)
  {
    for (int i = 0; i < fNSNum; i++) {
      if (isEqual(paramString1, fNamespaces[i]))
      {
        if (fMaps != null) {
          return (XSObject)fMaps[i].get(paramString2);
        }
        for (int j = 0; j < fLength; j++)
        {
          XSObject localXSObject = fArray[j];
          if (localXSObject.getName().equals(paramString2)) {
            return localXSObject;
          }
        }
        return null;
      }
    }
    return null;
  }
  
  public synchronized XSObject item(int paramInt)
  {
    if (fArray == null)
    {
      getLength();
      fArray = new XSObject[fLength];
      int i = 0;
      for (int j = 0; j < fNSNum; j++) {
        i += fMaps[j].getValues(fArray, i);
      }
    }
    if ((paramInt < 0) || (paramInt >= fLength)) {
      return null;
    }
    return fArray[paramInt];
  }
  
  final boolean isEqual(String paramString1, String paramString2)
  {
    return paramString2 == null ? true : paramString1 != null ? paramString1.equals(paramString2) : false;
  }
}
