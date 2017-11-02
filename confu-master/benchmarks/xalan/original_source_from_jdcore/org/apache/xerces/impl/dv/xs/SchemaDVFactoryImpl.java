package org.apache.xerces.impl.dv.xs;

import org.apache.xerces.impl.dv.SchemaDVFactory;
import org.apache.xerces.impl.dv.XSFacets;
import org.apache.xerces.impl.dv.XSSimpleType;
import org.apache.xerces.impl.xs.XSDeclarationPool;
import org.apache.xerces.util.SymbolHash;
import org.apache.xerces.xs.XSObjectList;

public class SchemaDVFactoryImpl
  extends SchemaDVFactory
{
  static final String URI_SCHEMAFORSCHEMA = "http://www.w3.org/2001/XMLSchema";
  static SymbolHash fBuiltInTypes = new SymbolHash();
  protected XSDeclarationPool fDeclPool = null;
  
  public SchemaDVFactoryImpl() {}
  
  public XSSimpleType getBuiltInType(String paramString)
  {
    return (XSSimpleType)fBuiltInTypes.get(paramString);
  }
  
  public SymbolHash getBuiltInTypes()
  {
    return fBuiltInTypes.makeClone();
  }
  
  public XSSimpleType createTypeRestriction(String paramString1, String paramString2, short paramShort, XSSimpleType paramXSSimpleType, XSObjectList paramXSObjectList)
  {
    if (fDeclPool != null)
    {
      XSSimpleTypeDecl localXSSimpleTypeDecl = fDeclPool.getSimpleTypeDecl();
      return localXSSimpleTypeDecl.setRestrictionValues((XSSimpleTypeDecl)paramXSSimpleType, paramString1, paramString2, paramShort, paramXSObjectList);
    }
    return new XSSimpleTypeDecl((XSSimpleTypeDecl)paramXSSimpleType, paramString1, paramString2, paramShort, false, paramXSObjectList);
  }
  
  public XSSimpleType createTypeList(String paramString1, String paramString2, short paramShort, XSSimpleType paramXSSimpleType, XSObjectList paramXSObjectList)
  {
    if (fDeclPool != null)
    {
      XSSimpleTypeDecl localXSSimpleTypeDecl = fDeclPool.getSimpleTypeDecl();
      return localXSSimpleTypeDecl.setListValues(paramString1, paramString2, paramShort, (XSSimpleTypeDecl)paramXSSimpleType, paramXSObjectList);
    }
    return new XSSimpleTypeDecl(paramString1, paramString2, paramShort, (XSSimpleTypeDecl)paramXSSimpleType, false, paramXSObjectList);
  }
  
  public XSSimpleType createTypeUnion(String paramString1, String paramString2, short paramShort, XSSimpleType[] paramArrayOfXSSimpleType, XSObjectList paramXSObjectList)
  {
    int i = paramArrayOfXSSimpleType.length;
    XSSimpleTypeDecl[] arrayOfXSSimpleTypeDecl = new XSSimpleTypeDecl[i];
    System.arraycopy(paramArrayOfXSSimpleType, 0, arrayOfXSSimpleTypeDecl, 0, i);
    if (fDeclPool != null)
    {
      XSSimpleTypeDecl localXSSimpleTypeDecl = fDeclPool.getSimpleTypeDecl();
      return localXSSimpleTypeDecl.setUnionValues(paramString1, paramString2, paramShort, arrayOfXSSimpleTypeDecl, paramXSObjectList);
    }
    return new XSSimpleTypeDecl(paramString1, paramString2, paramShort, arrayOfXSSimpleTypeDecl, paramXSObjectList);
  }
  
  static void createBuiltInTypes()
  {
    XSFacets localXSFacets = new XSFacets();
    XSSimpleTypeDecl localXSSimpleTypeDecl1 = XSSimpleTypeDecl.fAnySimpleType;
    XSSimpleTypeDecl localXSSimpleTypeDecl2 = XSSimpleTypeDecl.fAnyAtomicType;
    XSSimpleTypeDecl localXSSimpleTypeDecl3 = null;
    localXSSimpleTypeDecl3 = localXSSimpleTypeDecl1;
    fBuiltInTypes.put("anySimpleType", localXSSimpleTypeDecl1);
    XSSimpleTypeDecl localXSSimpleTypeDecl4 = new XSSimpleTypeDecl(localXSSimpleTypeDecl3, "string", (short)1, (short)0, false, false, false, true, (short)2);
    fBuiltInTypes.put("string", localXSSimpleTypeDecl4);
    fBuiltInTypes.put("boolean", new XSSimpleTypeDecl(localXSSimpleTypeDecl3, "boolean", (short)2, (short)0, false, true, false, true, (short)3));
    XSSimpleTypeDecl localXSSimpleTypeDecl5 = new XSSimpleTypeDecl(localXSSimpleTypeDecl3, "decimal", (short)3, (short)2, false, false, true, true, (short)4);
    fBuiltInTypes.put("decimal", localXSSimpleTypeDecl5);
    fBuiltInTypes.put("anyURI", new XSSimpleTypeDecl(localXSSimpleTypeDecl3, "anyURI", (short)17, (short)0, false, false, false, true, (short)18));
    fBuiltInTypes.put("base64Binary", new XSSimpleTypeDecl(localXSSimpleTypeDecl3, "base64Binary", (short)16, (short)0, false, false, false, true, (short)17));
    XSSimpleTypeDecl localXSSimpleTypeDecl6 = new XSSimpleTypeDecl(localXSSimpleTypeDecl3, "duration", (short)6, (short)1, false, false, false, true, (short)7);
    fBuiltInTypes.put("duration", localXSSimpleTypeDecl6);
    fBuiltInTypes.put("dateTime", new XSSimpleTypeDecl(localXSSimpleTypeDecl3, "dateTime", (short)7, (short)1, false, false, false, true, (short)8));
    fBuiltInTypes.put("time", new XSSimpleTypeDecl(localXSSimpleTypeDecl3, "time", (short)8, (short)1, false, false, false, true, (short)9));
    fBuiltInTypes.put("date", new XSSimpleTypeDecl(localXSSimpleTypeDecl3, "date", (short)9, (short)1, false, false, false, true, (short)10));
    fBuiltInTypes.put("gYearMonth", new XSSimpleTypeDecl(localXSSimpleTypeDecl3, "gYearMonth", (short)10, (short)1, false, false, false, true, (short)11));
    fBuiltInTypes.put("gYear", new XSSimpleTypeDecl(localXSSimpleTypeDecl3, "gYear", (short)11, (short)1, false, false, false, true, (short)12));
    fBuiltInTypes.put("gMonthDay", new XSSimpleTypeDecl(localXSSimpleTypeDecl3, "gMonthDay", (short)12, (short)1, false, false, false, true, (short)13));
    fBuiltInTypes.put("gDay", new XSSimpleTypeDecl(localXSSimpleTypeDecl3, "gDay", (short)13, (short)1, false, false, false, true, (short)14));
    fBuiltInTypes.put("gMonth", new XSSimpleTypeDecl(localXSSimpleTypeDecl3, "gMonth", (short)14, (short)1, false, false, false, true, (short)15));
    XSSimpleTypeDecl localXSSimpleTypeDecl7 = new XSSimpleTypeDecl(localXSSimpleTypeDecl5, "integer", (short)24, (short)2, false, false, true, true, (short)30);
    fBuiltInTypes.put("integer", localXSSimpleTypeDecl7);
    maxInclusive = "0";
    XSSimpleTypeDecl localXSSimpleTypeDecl8 = new XSSimpleTypeDecl(localXSSimpleTypeDecl7, "nonPositiveInteger", "http://www.w3.org/2001/XMLSchema", (short)0, false, null, (short)31);
    localXSSimpleTypeDecl8.applyFacets1(localXSFacets, (short)32, (short)0);
    fBuiltInTypes.put("nonPositiveInteger", localXSSimpleTypeDecl8);
    maxInclusive = "-1";
    XSSimpleTypeDecl localXSSimpleTypeDecl9 = new XSSimpleTypeDecl(localXSSimpleTypeDecl7, "negativeInteger", "http://www.w3.org/2001/XMLSchema", (short)0, false, null, (short)32);
    localXSSimpleTypeDecl9.applyFacets1(localXSFacets, (short)32, (short)0);
    fBuiltInTypes.put("negativeInteger", localXSSimpleTypeDecl9);
    maxInclusive = "9223372036854775807";
    minInclusive = "-9223372036854775808";
    XSSimpleTypeDecl localXSSimpleTypeDecl10 = new XSSimpleTypeDecl(localXSSimpleTypeDecl7, "long", "http://www.w3.org/2001/XMLSchema", (short)0, false, null, (short)33);
    localXSSimpleTypeDecl10.applyFacets1(localXSFacets, (short)288, (short)0);
    fBuiltInTypes.put("long", localXSSimpleTypeDecl10);
    maxInclusive = "2147483647";
    minInclusive = "-2147483648";
    XSSimpleTypeDecl localXSSimpleTypeDecl11 = new XSSimpleTypeDecl(localXSSimpleTypeDecl10, "int", "http://www.w3.org/2001/XMLSchema", (short)0, false, null, (short)34);
    localXSSimpleTypeDecl11.applyFacets1(localXSFacets, (short)288, (short)0);
    fBuiltInTypes.put("int", localXSSimpleTypeDecl11);
    maxInclusive = "32767";
    minInclusive = "-32768";
    XSSimpleTypeDecl localXSSimpleTypeDecl12 = new XSSimpleTypeDecl(localXSSimpleTypeDecl11, "short", "http://www.w3.org/2001/XMLSchema", (short)0, false, null, (short)35);
    localXSSimpleTypeDecl12.applyFacets1(localXSFacets, (short)288, (short)0);
    fBuiltInTypes.put("short", localXSSimpleTypeDecl12);
    maxInclusive = "127";
    minInclusive = "-128";
    XSSimpleTypeDecl localXSSimpleTypeDecl13 = new XSSimpleTypeDecl(localXSSimpleTypeDecl12, "byte", "http://www.w3.org/2001/XMLSchema", (short)0, false, null, (short)36);
    localXSSimpleTypeDecl13.applyFacets1(localXSFacets, (short)288, (short)0);
    fBuiltInTypes.put("byte", localXSSimpleTypeDecl13);
    minInclusive = "0";
    XSSimpleTypeDecl localXSSimpleTypeDecl14 = new XSSimpleTypeDecl(localXSSimpleTypeDecl7, "nonNegativeInteger", "http://www.w3.org/2001/XMLSchema", (short)0, false, null, (short)37);
    localXSSimpleTypeDecl14.applyFacets1(localXSFacets, (short)256, (short)0);
    fBuiltInTypes.put("nonNegativeInteger", localXSSimpleTypeDecl14);
    maxInclusive = "18446744073709551615";
    XSSimpleTypeDecl localXSSimpleTypeDecl15 = new XSSimpleTypeDecl(localXSSimpleTypeDecl14, "unsignedLong", "http://www.w3.org/2001/XMLSchema", (short)0, false, null, (short)38);
    localXSSimpleTypeDecl15.applyFacets1(localXSFacets, (short)32, (short)0);
    fBuiltInTypes.put("unsignedLong", localXSSimpleTypeDecl15);
    maxInclusive = "4294967295";
    XSSimpleTypeDecl localXSSimpleTypeDecl16 = new XSSimpleTypeDecl(localXSSimpleTypeDecl15, "unsignedInt", "http://www.w3.org/2001/XMLSchema", (short)0, false, null, (short)39);
    localXSSimpleTypeDecl16.applyFacets1(localXSFacets, (short)32, (short)0);
    fBuiltInTypes.put("unsignedInt", localXSSimpleTypeDecl16);
    maxInclusive = "65535";
    XSSimpleTypeDecl localXSSimpleTypeDecl17 = new XSSimpleTypeDecl(localXSSimpleTypeDecl16, "unsignedShort", "http://www.w3.org/2001/XMLSchema", (short)0, false, null, (short)40);
    localXSSimpleTypeDecl17.applyFacets1(localXSFacets, (short)32, (short)0);
    fBuiltInTypes.put("unsignedShort", localXSSimpleTypeDecl17);
    maxInclusive = "255";
    XSSimpleTypeDecl localXSSimpleTypeDecl18 = new XSSimpleTypeDecl(localXSSimpleTypeDecl17, "unsignedByte", "http://www.w3.org/2001/XMLSchema", (short)0, false, null, (short)41);
    localXSSimpleTypeDecl18.applyFacets1(localXSFacets, (short)32, (short)0);
    fBuiltInTypes.put("unsignedByte", localXSSimpleTypeDecl18);
    minInclusive = "1";
    XSSimpleTypeDecl localXSSimpleTypeDecl19 = new XSSimpleTypeDecl(localXSSimpleTypeDecl14, "positiveInteger", "http://www.w3.org/2001/XMLSchema", (short)0, false, null, (short)42);
    localXSSimpleTypeDecl19.applyFacets1(localXSFacets, (short)256, (short)0);
    fBuiltInTypes.put("positiveInteger", localXSSimpleTypeDecl19);
    fBuiltInTypes.put("float", new XSSimpleTypeDecl(localXSSimpleTypeDecl3, "float", (short)4, (short)1, true, true, true, true, (short)5));
    fBuiltInTypes.put("double", new XSSimpleTypeDecl(localXSSimpleTypeDecl3, "double", (short)5, (short)1, true, true, true, true, (short)6));
    fBuiltInTypes.put("hexBinary", new XSSimpleTypeDecl(localXSSimpleTypeDecl3, "hexBinary", (short)15, (short)0, false, false, false, true, (short)16));
    fBuiltInTypes.put("NOTATION", new XSSimpleTypeDecl(localXSSimpleTypeDecl3, "NOTATION", (short)20, (short)0, false, false, false, true, (short)20));
    whiteSpace = 1;
    XSSimpleTypeDecl localXSSimpleTypeDecl20 = new XSSimpleTypeDecl(localXSSimpleTypeDecl4, "normalizedString", "http://www.w3.org/2001/XMLSchema", (short)0, false, null, (short)21);
    localXSSimpleTypeDecl20.applyFacets1(localXSFacets, (short)16, (short)0);
    fBuiltInTypes.put("normalizedString", localXSSimpleTypeDecl20);
    whiteSpace = 2;
    XSSimpleTypeDecl localXSSimpleTypeDecl21 = new XSSimpleTypeDecl(localXSSimpleTypeDecl20, "token", "http://www.w3.org/2001/XMLSchema", (short)0, false, null, (short)22);
    localXSSimpleTypeDecl21.applyFacets1(localXSFacets, (short)16, (short)0);
    fBuiltInTypes.put("token", localXSSimpleTypeDecl21);
    whiteSpace = 2;
    pattern = "([a-zA-Z]{1,8})(-[a-zA-Z0-9]{1,8})*";
    XSSimpleTypeDecl localXSSimpleTypeDecl22 = new XSSimpleTypeDecl(localXSSimpleTypeDecl21, "language", "http://www.w3.org/2001/XMLSchema", (short)0, false, null, (short)23);
    localXSSimpleTypeDecl22.applyFacets1(localXSFacets, (short)24, (short)0);
    fBuiltInTypes.put("language", localXSSimpleTypeDecl22);
    whiteSpace = 2;
    XSSimpleTypeDecl localXSSimpleTypeDecl23 = new XSSimpleTypeDecl(localXSSimpleTypeDecl21, "Name", "http://www.w3.org/2001/XMLSchema", (short)0, false, null, (short)25);
    localXSSimpleTypeDecl23.applyFacets1(localXSFacets, (short)16, (short)0, (short)2);
    fBuiltInTypes.put("Name", localXSSimpleTypeDecl23);
    whiteSpace = 2;
    XSSimpleTypeDecl localXSSimpleTypeDecl24 = new XSSimpleTypeDecl(localXSSimpleTypeDecl23, "NCName", "http://www.w3.org/2001/XMLSchema", (short)0, false, null, (short)26);
    localXSSimpleTypeDecl24.applyFacets1(localXSFacets, (short)16, (short)0, (short)3);
    fBuiltInTypes.put("NCName", localXSSimpleTypeDecl24);
    fBuiltInTypes.put("QName", new XSSimpleTypeDecl(localXSSimpleTypeDecl3, "QName", (short)18, (short)0, false, false, false, true, (short)19));
    fBuiltInTypes.put("ID", new XSSimpleTypeDecl(localXSSimpleTypeDecl24, "ID", (short)21, (short)0, false, false, false, true, (short)27));
    XSSimpleTypeDecl localXSSimpleTypeDecl25 = new XSSimpleTypeDecl(localXSSimpleTypeDecl24, "IDREF", (short)22, (short)0, false, false, false, true, (short)28);
    fBuiltInTypes.put("IDREF", localXSSimpleTypeDecl25);
    minLength = 1;
    XSSimpleTypeDecl localXSSimpleTypeDecl26 = new XSSimpleTypeDecl(null, "http://www.w3.org/2001/XMLSchema", (short)0, localXSSimpleTypeDecl25, true, null);
    XSSimpleTypeDecl localXSSimpleTypeDecl27 = new XSSimpleTypeDecl(localXSSimpleTypeDecl26, "IDREFS", "http://www.w3.org/2001/XMLSchema", (short)0, false, null);
    localXSSimpleTypeDecl27.applyFacets1(localXSFacets, (short)2, (short)0);
    fBuiltInTypes.put("IDREFS", localXSSimpleTypeDecl27);
    XSSimpleTypeDecl localXSSimpleTypeDecl28 = new XSSimpleTypeDecl(localXSSimpleTypeDecl24, "ENTITY", (short)23, (short)0, false, false, false, true, (short)29);
    fBuiltInTypes.put("ENTITY", localXSSimpleTypeDecl28);
    minLength = 1;
    localXSSimpleTypeDecl26 = new XSSimpleTypeDecl(null, "http://www.w3.org/2001/XMLSchema", (short)0, localXSSimpleTypeDecl28, true, null);
    XSSimpleTypeDecl localXSSimpleTypeDecl29 = new XSSimpleTypeDecl(localXSSimpleTypeDecl26, "ENTITIES", "http://www.w3.org/2001/XMLSchema", (short)0, false, null);
    localXSSimpleTypeDecl29.applyFacets1(localXSFacets, (short)2, (short)0);
    fBuiltInTypes.put("ENTITIES", localXSSimpleTypeDecl29);
    whiteSpace = 2;
    XSSimpleTypeDecl localXSSimpleTypeDecl30 = new XSSimpleTypeDecl(localXSSimpleTypeDecl21, "NMTOKEN", "http://www.w3.org/2001/XMLSchema", (short)0, false, null, (short)24);
    localXSSimpleTypeDecl30.applyFacets1(localXSFacets, (short)16, (short)0, (short)1);
    fBuiltInTypes.put("NMTOKEN", localXSSimpleTypeDecl30);
    minLength = 1;
    localXSSimpleTypeDecl26 = new XSSimpleTypeDecl(null, "http://www.w3.org/2001/XMLSchema", (short)0, localXSSimpleTypeDecl30, true, null);
    XSSimpleTypeDecl localXSSimpleTypeDecl31 = new XSSimpleTypeDecl(localXSSimpleTypeDecl26, "NMTOKENS", "http://www.w3.org/2001/XMLSchema", (short)0, false, null);
    localXSSimpleTypeDecl31.applyFacets1(localXSFacets, (short)2, (short)0);
    fBuiltInTypes.put("NMTOKENS", localXSSimpleTypeDecl31);
  }
  
  public void setDeclPool(XSDeclarationPool paramXSDeclarationPool)
  {
    fDeclPool = paramXSDeclarationPool;
  }
  
  static
  {
    createBuiltInTypes();
  }
}
