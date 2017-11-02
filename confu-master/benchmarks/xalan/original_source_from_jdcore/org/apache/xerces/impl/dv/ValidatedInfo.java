package org.apache.xerces.impl.dv;

import org.apache.xerces.xs.ShortList;

public class ValidatedInfo
{
  public String normalizedValue;
  public Object actualValue;
  public short actualValueType;
  public XSSimpleType memberType;
  public XSSimpleType[] memberTypes;
  public ShortList itemValueTypes;
  
  public ValidatedInfo() {}
  
  public void reset()
  {
    normalizedValue = null;
    actualValue = null;
    memberType = null;
    memberTypes = null;
  }
  
  public String stringValue()
  {
    if (actualValue == null) {
      return normalizedValue;
    }
    return actualValue.toString();
  }
  
  public static boolean isComparable(ValidatedInfo paramValidatedInfo1, ValidatedInfo paramValidatedInfo2)
  {
    int i = convertToPrimitiveKind(actualValueType);
    int j = convertToPrimitiveKind(actualValueType);
    if (i != j) {
      return ((i == 1) && (j == 2)) || ((i == 2) && (j == 1));
    }
    if ((i == 44) || (i == 43))
    {
      ShortList localShortList1 = itemValueTypes;
      ShortList localShortList2 = itemValueTypes;
      int k = localShortList1 != null ? localShortList1.getLength() : 0;
      int m = localShortList2 != null ? localShortList2.getLength() : 0;
      if (k != m) {
        return false;
      }
      for (int n = 0; n < k; n++)
      {
        int i1 = convertToPrimitiveKind(localShortList1.item(n));
        int i2 = convertToPrimitiveKind(localShortList2.item(n));
        if ((i1 != i2) && ((i1 != 1) || (i2 != 2)) && ((i1 != 2) || (i2 != 1))) {
          return false;
        }
      }
    }
    return true;
  }
  
  private static short convertToPrimitiveKind(short paramShort)
  {
    if (paramShort <= 20) {
      return paramShort;
    }
    if (paramShort <= 29) {
      return 2;
    }
    if (paramShort <= 42) {
      return 4;
    }
    return paramShort;
  }
}
