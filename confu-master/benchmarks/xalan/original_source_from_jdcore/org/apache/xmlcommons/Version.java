package org.apache.xmlcommons;

import java.io.PrintStream;

public class Version
{
  public Version() {}
  
  public static String getVersion()
  {
    return getProduct() + " " + getVersionNum();
  }
  
  public static String getProduct()
  {
    return "XmlCommonsExternal";
  }
  
  public static String getVersionNum()
  {
    return "1.3.03";
  }
  
  public static void main(String[] paramArrayOfString)
  {
    System.out.println(getVersion());
  }
}
