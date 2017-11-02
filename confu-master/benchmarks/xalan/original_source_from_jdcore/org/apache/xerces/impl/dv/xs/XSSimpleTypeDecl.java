package org.apache.xerces.impl.dv.xs;

import java.util.StringTokenizer;
import java.util.Vector;
import org.apache.xerces.impl.dv.DatatypeException;
import org.apache.xerces.impl.dv.InvalidDatatypeFacetException;
import org.apache.xerces.impl.dv.InvalidDatatypeValueException;
import org.apache.xerces.impl.dv.ValidatedInfo;
import org.apache.xerces.impl.dv.ValidationContext;
import org.apache.xerces.impl.dv.XSFacets;
import org.apache.xerces.impl.dv.XSSimpleType;
import org.apache.xerces.impl.xpath.regex.RegularExpression;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.apache.xerces.impl.xs.util.ShortListImpl;
import org.apache.xerces.impl.xs.util.StringListImpl;
import org.apache.xerces.impl.xs.util.XSObjectListImpl;
import org.apache.xerces.util.XMLChar;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xs.ShortList;
import org.apache.xerces.xs.StringList;
import org.apache.xerces.xs.XSAnnotation;
import org.apache.xerces.xs.XSFacet;
import org.apache.xerces.xs.XSMultiValueFacet;
import org.apache.xerces.xs.XSNamespaceItem;
import org.apache.xerces.xs.XSObject;
import org.apache.xerces.xs.XSObjectList;
import org.apache.xerces.xs.XSSimpleTypeDefinition;
import org.apache.xerces.xs.XSTypeDefinition;
import org.apache.xerces.xs.datatypes.ObjectList;
import org.w3c.dom.TypeInfo;

public class XSSimpleTypeDecl
  implements XSSimpleType, TypeInfo
{
  static final short DV_STRING = 1;
  static final short DV_BOOLEAN = 2;
  static final short DV_DECIMAL = 3;
  static final short DV_FLOAT = 4;
  static final short DV_DOUBLE = 5;
  static final short DV_DURATION = 6;
  static final short DV_DATETIME = 7;
  static final short DV_TIME = 8;
  static final short DV_DATE = 9;
  static final short DV_GYEARMONTH = 10;
  static final short DV_GYEAR = 11;
  static final short DV_GMONTHDAY = 12;
  static final short DV_GDAY = 13;
  static final short DV_GMONTH = 14;
  static final short DV_HEXBINARY = 15;
  static final short DV_BASE64BINARY = 16;
  static final short DV_ANYURI = 17;
  static final short DV_QNAME = 18;
  static final short DV_PRECISIONDECIMAL = 19;
  static final short DV_NOTATION = 20;
  static final short DV_ANYSIMPLETYPE = 0;
  static final short DV_ID = 21;
  static final short DV_IDREF = 22;
  static final short DV_ENTITY = 23;
  static final short DV_INTEGER = 24;
  static final short DV_LIST = 25;
  static final short DV_UNION = 26;
  static final short DV_YEARMONTHDURATION = 27;
  static final short DV_DAYTIMEDURATION = 28;
  static final short DV_ANYATOMICTYPE = 29;
  static final TypeValidator[] fDVs = { new AnySimpleDV(), new StringDV(), new BooleanDV(), new DecimalDV(), new FloatDV(), new DoubleDV(), new DurationDV(), new DateTimeDV(), new TimeDV(), new DateDV(), new YearMonthDV(), new YearDV(), new MonthDayDV(), new DayDV(), new MonthDV(), new HexBinaryDV(), new Base64BinaryDV(), new AnyURIDV(), new QNameDV(), new PrecisionDecimalDV(), new QNameDV(), new IDDV(), new IDREFDV(), new EntityDV(), new IntegerDV(), new ListDV(), new UnionDV(), new YearMonthDurationDV(), new DayTimeDurationDV(), new AnyAtomicDV() };
  static final short NORMALIZE_NONE = 0;
  static final short NORMALIZE_TRIM = 1;
  static final short NORMALIZE_FULL = 2;
  static final short[] fDVNormalizeType = { 0, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 2, 0, 1, 1, 0 };
  static final short SPECIAL_PATTERN_NONE = 0;
  static final short SPECIAL_PATTERN_NMTOKEN = 1;
  static final short SPECIAL_PATTERN_NAME = 2;
  static final short SPECIAL_PATTERN_NCNAME = 3;
  static final String[] SPECIAL_PATTERN_STRING = { "NONE", "NMTOKEN", "Name", "NCName" };
  static final String[] WS_FACET_STRING = { "preserve", "replace", "collapse" };
  static final String URI_SCHEMAFORSCHEMA = "http://www.w3.org/2001/XMLSchema";
  static final String ANY_TYPE = "anyType";
  public static final short YEARMONTHDURATION_DT = 46;
  public static final short DAYTIMEDURATION_DT = 47;
  public static final short PRECISIONDECIMAL_DT = 48;
  public static final short ANYATOMICTYPE_DT = 49;
  static final int DERIVATION_ANY = 0;
  static final int DERIVATION_RESTRICTION = 1;
  static final int DERIVATION_EXTENSION = 2;
  static final int DERIVATION_UNION = 4;
  static final int DERIVATION_LIST = 8;
  static final ValidationContext fEmptyContext = new ValidationContext()
  {
    public boolean needFacetChecking()
    {
      return true;
    }
    
    public boolean needExtraChecking()
    {
      return false;
    }
    
    public boolean needToNormalize()
    {
      return true;
    }
    
    public boolean useNamespaces()
    {
      return true;
    }
    
    public boolean isEntityDeclared(String paramAnonymousString)
    {
      return false;
    }
    
    public boolean isEntityUnparsed(String paramAnonymousString)
    {
      return false;
    }
    
    public boolean isIdDeclared(String paramAnonymousString)
    {
      return false;
    }
    
    public void addId(String paramAnonymousString) {}
    
    public void addIdRef(String paramAnonymousString) {}
    
    public String getSymbol(String paramAnonymousString)
    {
      return paramAnonymousString.intern();
    }
    
    public String getURI(String paramAnonymousString)
    {
      return null;
    }
  };
  private boolean fIsImmutable = false;
  private XSSimpleTypeDecl fItemType;
  private XSSimpleTypeDecl[] fMemberTypes;
  private short fBuiltInKind;
  private String fTypeName;
  private String fTargetNamespace;
  private short fFinalSet = 0;
  private XSSimpleTypeDecl fBase;
  private short fVariety = -1;
  private short fValidationDV = -1;
  private short fFacetsDefined = 0;
  private short fFixedFacet = 0;
  private short fWhiteSpace = 0;
  private int fLength = -1;
  private int fMinLength = -1;
  private int fMaxLength = -1;
  private int fTotalDigits = -1;
  private int fFractionDigits = -1;
  private Vector fPattern;
  private Vector fPatternStr;
  private Vector fEnumeration;
  private short[] fEnumerationType;
  private ShortList[] fEnumerationItemType;
  private ShortList fEnumerationTypeList;
  private ObjectList fEnumerationItemTypeList;
  private StringList fLexicalPattern;
  private StringList fLexicalEnumeration;
  private ObjectList fActualEnumeration;
  private Object fMaxInclusive;
  private Object fMaxExclusive;
  private Object fMinExclusive;
  private Object fMinInclusive;
  public XSAnnotation lengthAnnotation;
  public XSAnnotation minLengthAnnotation;
  public XSAnnotation maxLengthAnnotation;
  public XSAnnotation whiteSpaceAnnotation;
  public XSAnnotation totalDigitsAnnotation;
  public XSAnnotation fractionDigitsAnnotation;
  public XSObjectListImpl patternAnnotations;
  public XSObjectList enumerationAnnotations;
  public XSAnnotation maxInclusiveAnnotation;
  public XSAnnotation maxExclusiveAnnotation;
  public XSAnnotation minInclusiveAnnotation;
  public XSAnnotation minExclusiveAnnotation;
  private XSObjectListImpl fFacets;
  private XSObjectListImpl fMultiValueFacets;
  private XSObjectList fAnnotations = null;
  private short fPatternType = 0;
  private short fOrdered;
  private boolean fFinite;
  private boolean fBounded;
  private boolean fNumeric;
  static final XSSimpleTypeDecl fAnySimpleType = new XSSimpleTypeDecl(null, "anySimpleType", (short)0, (short)0, false, true, false, true, (short)1);
  static final XSSimpleTypeDecl fAnyAtomicType = new XSSimpleTypeDecl(fAnySimpleType, "anyAtomicType", (short)29, (short)0, false, true, false, true, (short)49);
  static final ValidationContext fDummyContext = new ValidationContext()
  {
    public boolean needFacetChecking()
    {
      return true;
    }
    
    public boolean needExtraChecking()
    {
      return false;
    }
    
    public boolean needToNormalize()
    {
      return false;
    }
    
    public boolean useNamespaces()
    {
      return true;
    }
    
    public boolean isEntityDeclared(String paramAnonymousString)
    {
      return false;
    }
    
    public boolean isEntityUnparsed(String paramAnonymousString)
    {
      return false;
    }
    
    public boolean isIdDeclared(String paramAnonymousString)
    {
      return false;
    }
    
    public void addId(String paramAnonymousString) {}
    
    public void addIdRef(String paramAnonymousString) {}
    
    public String getSymbol(String paramAnonymousString)
    {
      return paramAnonymousString.intern();
    }
    
    public String getURI(String paramAnonymousString)
    {
      return null;
    }
  };
  private boolean fAnonymous = false;
  
  public XSSimpleTypeDecl() {}
  
  protected XSSimpleTypeDecl(XSSimpleTypeDecl paramXSSimpleTypeDecl, String paramString, short paramShort1, short paramShort2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, short paramShort3)
  {
    fIsImmutable = paramBoolean4;
    fBase = paramXSSimpleTypeDecl;
    fTypeName = paramString;
    fTargetNamespace = "http://www.w3.org/2001/XMLSchema";
    fVariety = 1;
    fValidationDV = paramShort1;
    fFacetsDefined = 16;
    if (paramShort1 == 1)
    {
      fWhiteSpace = 0;
    }
    else
    {
      fWhiteSpace = 2;
      fFixedFacet = 16;
    }
    fOrdered = paramShort2;
    fBounded = paramBoolean1;
    fFinite = paramBoolean2;
    fNumeric = paramBoolean3;
    fAnnotations = null;
    fBuiltInKind = paramShort3;
  }
  
  protected XSSimpleTypeDecl(XSSimpleTypeDecl paramXSSimpleTypeDecl, String paramString1, String paramString2, short paramShort1, boolean paramBoolean, XSObjectList paramXSObjectList, short paramShort2)
  {
    this(paramXSSimpleTypeDecl, paramString1, paramString2, paramShort1, paramBoolean, paramXSObjectList);
    fBuiltInKind = paramShort2;
  }
  
  protected XSSimpleTypeDecl(XSSimpleTypeDecl paramXSSimpleTypeDecl, String paramString1, String paramString2, short paramShort, boolean paramBoolean, XSObjectList paramXSObjectList)
  {
    fBase = paramXSSimpleTypeDecl;
    fTypeName = paramString1;
    fTargetNamespace = paramString2;
    fFinalSet = paramShort;
    fAnnotations = paramXSObjectList;
    fVariety = fBase.fVariety;
    fValidationDV = fBase.fValidationDV;
    switch (fVariety)
    {
    case 1: 
      break;
    case 2: 
      fItemType = fBase.fItemType;
      break;
    case 3: 
      fMemberTypes = fBase.fMemberTypes;
    }
    fLength = fBase.fLength;
    fMinLength = fBase.fMinLength;
    fMaxLength = fBase.fMaxLength;
    fPattern = fBase.fPattern;
    fPatternStr = fBase.fPatternStr;
    fEnumeration = fBase.fEnumeration;
    fEnumerationType = fBase.fEnumerationType;
    fEnumerationItemType = fBase.fEnumerationItemType;
    fWhiteSpace = fBase.fWhiteSpace;
    fMaxExclusive = fBase.fMaxExclusive;
    fMaxInclusive = fBase.fMaxInclusive;
    fMinExclusive = fBase.fMinExclusive;
    fMinInclusive = fBase.fMinInclusive;
    fTotalDigits = fBase.fTotalDigits;
    fFractionDigits = fBase.fFractionDigits;
    fPatternType = fBase.fPatternType;
    fFixedFacet = fBase.fFixedFacet;
    fFacetsDefined = fBase.fFacetsDefined;
    caclFundamentalFacets();
    fIsImmutable = paramBoolean;
    fBuiltInKind = fBuiltInKind;
  }
  
  protected XSSimpleTypeDecl(String paramString1, String paramString2, short paramShort, XSSimpleTypeDecl paramXSSimpleTypeDecl, boolean paramBoolean, XSObjectList paramXSObjectList)
  {
    fBase = fAnySimpleType;
    fTypeName = paramString1;
    fTargetNamespace = paramString2;
    fFinalSet = paramShort;
    fAnnotations = paramXSObjectList;
    fVariety = 2;
    fItemType = paramXSSimpleTypeDecl;
    fValidationDV = 25;
    fFacetsDefined = 16;
    fFixedFacet = 16;
    fWhiteSpace = 2;
    caclFundamentalFacets();
    fIsImmutable = paramBoolean;
    fBuiltInKind = 44;
  }
  
  protected XSSimpleTypeDecl(String paramString1, String paramString2, short paramShort, XSSimpleTypeDecl[] paramArrayOfXSSimpleTypeDecl, XSObjectList paramXSObjectList)
  {
    fBase = fAnySimpleType;
    fTypeName = paramString1;
    fTargetNamespace = paramString2;
    fFinalSet = paramShort;
    fAnnotations = paramXSObjectList;
    fVariety = 3;
    fMemberTypes = paramArrayOfXSSimpleTypeDecl;
    fValidationDV = 26;
    fFacetsDefined = 16;
    fWhiteSpace = 2;
    caclFundamentalFacets();
    fIsImmutable = false;
    fBuiltInKind = 45;
  }
  
  protected XSSimpleTypeDecl setRestrictionValues(XSSimpleTypeDecl paramXSSimpleTypeDecl, String paramString1, String paramString2, short paramShort, XSObjectList paramXSObjectList)
  {
    if (fIsImmutable) {
      return null;
    }
    fBase = paramXSSimpleTypeDecl;
    fTypeName = paramString1;
    fTargetNamespace = paramString2;
    fFinalSet = paramShort;
    fAnnotations = paramXSObjectList;
    fVariety = fBase.fVariety;
    fValidationDV = fBase.fValidationDV;
    switch (fVariety)
    {
    case 1: 
      break;
    case 2: 
      fItemType = fBase.fItemType;
      break;
    case 3: 
      fMemberTypes = fBase.fMemberTypes;
    }
    fLength = fBase.fLength;
    fMinLength = fBase.fMinLength;
    fMaxLength = fBase.fMaxLength;
    fPattern = fBase.fPattern;
    fPatternStr = fBase.fPatternStr;
    fEnumeration = fBase.fEnumeration;
    fEnumerationType = fBase.fEnumerationType;
    fEnumerationItemType = fBase.fEnumerationItemType;
    fWhiteSpace = fBase.fWhiteSpace;
    fMaxExclusive = fBase.fMaxExclusive;
    fMaxInclusive = fBase.fMaxInclusive;
    fMinExclusive = fBase.fMinExclusive;
    fMinInclusive = fBase.fMinInclusive;
    fTotalDigits = fBase.fTotalDigits;
    fFractionDigits = fBase.fFractionDigits;
    fPatternType = fBase.fPatternType;
    fFixedFacet = fBase.fFixedFacet;
    fFacetsDefined = fBase.fFacetsDefined;
    caclFundamentalFacets();
    fBuiltInKind = fBuiltInKind;
    return this;
  }
  
  protected XSSimpleTypeDecl setListValues(String paramString1, String paramString2, short paramShort, XSSimpleTypeDecl paramXSSimpleTypeDecl, XSObjectList paramXSObjectList)
  {
    if (fIsImmutable) {
      return null;
    }
    fBase = fAnySimpleType;
    fTypeName = paramString1;
    fTargetNamespace = paramString2;
    fFinalSet = paramShort;
    fAnnotations = paramXSObjectList;
    fVariety = 2;
    fItemType = paramXSSimpleTypeDecl;
    fValidationDV = 25;
    fFacetsDefined = 16;
    fFixedFacet = 16;
    fWhiteSpace = 2;
    caclFundamentalFacets();
    fBuiltInKind = 44;
    return this;
  }
  
  protected XSSimpleTypeDecl setUnionValues(String paramString1, String paramString2, short paramShort, XSSimpleTypeDecl[] paramArrayOfXSSimpleTypeDecl, XSObjectList paramXSObjectList)
  {
    if (fIsImmutable) {
      return null;
    }
    fBase = fAnySimpleType;
    fTypeName = paramString1;
    fTargetNamespace = paramString2;
    fFinalSet = paramShort;
    fAnnotations = paramXSObjectList;
    fVariety = 3;
    fMemberTypes = paramArrayOfXSSimpleTypeDecl;
    fValidationDV = 26;
    fFacetsDefined = 16;
    fWhiteSpace = 2;
    caclFundamentalFacets();
    fBuiltInKind = 45;
    return this;
  }
  
  public short getType()
  {
    return 3;
  }
  
  public short getTypeCategory()
  {
    return 16;
  }
  
  public String getName()
  {
    return getAnonymous() ? null : fTypeName;
  }
  
  public String getTypeName()
  {
    return fTypeName;
  }
  
  public String getNamespace()
  {
    return fTargetNamespace;
  }
  
  public short getFinal()
  {
    return fFinalSet;
  }
  
  public boolean isFinal(short paramShort)
  {
    return (fFinalSet & paramShort) != 0;
  }
  
  public XSTypeDefinition getBaseType()
  {
    return fBase;
  }
  
  public boolean getAnonymous()
  {
    return (fAnonymous) || (fTypeName == null);
  }
  
  public short getVariety()
  {
    return fValidationDV == 0 ? 0 : fVariety;
  }
  
  public boolean isIDType()
  {
    switch (fVariety)
    {
    case 1: 
      return fValidationDV == 21;
    case 2: 
      return fItemType.isIDType();
    case 3: 
      for (int i = 0; i < fMemberTypes.length; i++) {
        if (fMemberTypes[i].isIDType()) {
          return true;
        }
      }
    }
    return false;
  }
  
  public short getWhitespace()
    throws DatatypeException
  {
    if (fVariety == 3) {
      throw new DatatypeException("dt-whitespace", new Object[] { fTypeName });
    }
    return fWhiteSpace;
  }
  
  public short getPrimitiveKind()
  {
    if ((fVariety == 1) && (fValidationDV != 0))
    {
      if ((fValidationDV == 21) || (fValidationDV == 22) || (fValidationDV == 23)) {
        return 1;
      }
      if (fValidationDV == 24) {
        return 3;
      }
      return fValidationDV;
    }
    return 0;
  }
  
  public short getBuiltInKind()
  {
    return fBuiltInKind;
  }
  
  public XSSimpleTypeDefinition getPrimitiveType()
  {
    if ((fVariety == 1) && (fValidationDV != 0))
    {
      for (XSSimpleTypeDecl localXSSimpleTypeDecl = this; fBase != fAnySimpleType; localXSSimpleTypeDecl = fBase) {}
      return localXSSimpleTypeDecl;
    }
    return null;
  }
  
  public XSSimpleTypeDefinition getItemType()
  {
    if (fVariety == 2) {
      return fItemType;
    }
    return null;
  }
  
  public XSObjectList getMemberTypes()
  {
    if (fVariety == 3) {
      return new XSObjectListImpl(fMemberTypes, fMemberTypes.length);
    }
    return XSObjectListImpl.EMPTY_LIST;
  }
  
  public void applyFacets(XSFacets paramXSFacets, short paramShort1, short paramShort2, ValidationContext paramValidationContext)
    throws InvalidDatatypeFacetException
  {
    applyFacets(paramXSFacets, paramShort1, paramShort2, (short)0, paramValidationContext);
  }
  
  void applyFacets1(XSFacets paramXSFacets, short paramShort1, short paramShort2)
  {
    try
    {
      applyFacets(paramXSFacets, paramShort1, paramShort2, (short)0, fDummyContext);
    }
    catch (InvalidDatatypeFacetException localInvalidDatatypeFacetException)
    {
      throw new RuntimeException("internal error");
    }
    fIsImmutable = true;
  }
  
  void applyFacets1(XSFacets paramXSFacets, short paramShort1, short paramShort2, short paramShort3)
  {
    try
    {
      applyFacets(paramXSFacets, paramShort1, paramShort2, paramShort3, fDummyContext);
    }
    catch (InvalidDatatypeFacetException localInvalidDatatypeFacetException)
    {
      throw new RuntimeException("internal error");
    }
    fIsImmutable = true;
  }
  
  void applyFacets(XSFacets paramXSFacets, short paramShort1, short paramShort2, short paramShort3, ValidationContext paramValidationContext)
    throws InvalidDatatypeFacetException
  {
    if (fIsImmutable) {
      return;
    }
    ValidatedInfo localValidatedInfo1 = new ValidatedInfo();
    fFacetsDefined = 0;
    fFixedFacet = 0;
    int i = 0;
    int j = fDVs[fValidationDV].getAllowedFacets();
    if ((paramShort1 & 0x1) != 0) {
      if ((j & 0x1) == 0)
      {
        reportError("cos-applicable-facets", new Object[] { "length", fTypeName });
      }
      else
      {
        fLength = length;
        lengthAnnotation = lengthAnnotation;
        fFacetsDefined = ((short)(fFacetsDefined | 0x1));
        if ((paramShort2 & 0x1) != 0) {
          fFixedFacet = ((short)(fFixedFacet | 0x1));
        }
      }
    }
    if ((paramShort1 & 0x2) != 0) {
      if ((j & 0x2) == 0)
      {
        reportError("cos-applicable-facets", new Object[] { "minLength", fTypeName });
      }
      else
      {
        fMinLength = minLength;
        minLengthAnnotation = minLengthAnnotation;
        fFacetsDefined = ((short)(fFacetsDefined | 0x2));
        if ((paramShort2 & 0x2) != 0) {
          fFixedFacet = ((short)(fFixedFacet | 0x2));
        }
      }
    }
    if ((paramShort1 & 0x4) != 0) {
      if ((j & 0x4) == 0)
      {
        reportError("cos-applicable-facets", new Object[] { "maxLength", fTypeName });
      }
      else
      {
        fMaxLength = maxLength;
        maxLengthAnnotation = maxLengthAnnotation;
        fFacetsDefined = ((short)(fFacetsDefined | 0x4));
        if ((paramShort2 & 0x4) != 0) {
          fFixedFacet = ((short)(fFixedFacet | 0x4));
        }
      }
    }
    Object localObject;
    if ((paramShort1 & 0x8) != 0) {
      if ((j & 0x8) == 0)
      {
        reportError("cos-applicable-facets", new Object[] { "pattern", fTypeName });
      }
      else
      {
        patternAnnotations = patternAnnotations;
        localObject = null;
        try
        {
          localObject = new RegularExpression(pattern, "X");
        }
        catch (Exception localException)
        {
          reportError("InvalidRegex", new Object[] { pattern, localException.getLocalizedMessage() });
        }
        if (localObject != null)
        {
          fPattern = new Vector();
          fPattern.addElement(localObject);
          fPatternStr = new Vector();
          fPatternStr.addElement(pattern);
          fFacetsDefined = ((short)(fFacetsDefined | 0x8));
          if ((paramShort2 & 0x8) != 0) {
            fFixedFacet = ((short)(fFixedFacet | 0x8));
          }
        }
      }
    }
    if ((paramShort1 & 0x800) != 0) {
      if ((j & 0x800) == 0)
      {
        reportError("cos-applicable-facets", new Object[] { "enumeration", fTypeName });
      }
      else
      {
        fEnumeration = new Vector();
        localObject = enumeration;
        fEnumerationType = new short[((Vector)localObject).size()];
        fEnumerationItemType = new ShortList[((Vector)localObject).size()];
        Vector localVector = enumNSDecls;
        ValidationContextImpl localValidationContextImpl = new ValidationContextImpl(paramValidationContext);
        enumerationAnnotations = enumAnnotations;
        for (int i1 = 0; i1 < ((Vector)localObject).size(); i1++)
        {
          if (localVector != null) {
            localValidationContextImpl.setNSContext((NamespaceContext)localVector.elementAt(i1));
          }
          try
          {
            ValidatedInfo localValidatedInfo2 = fBase.validateWithInfo((String)((Vector)localObject).elementAt(i1), localValidationContextImpl, localValidatedInfo1);
            fEnumeration.addElement(actualValue);
            fEnumerationType[i1] = actualValueType;
            fEnumerationItemType[i1] = itemValueTypes;
          }
          catch (InvalidDatatypeValueException localInvalidDatatypeValueException9)
          {
            reportError("enumeration-valid-restriction", new Object[] { ((Vector)localObject).elementAt(i1), getBaseType().getName() });
          }
        }
        fFacetsDefined = ((short)(fFacetsDefined | 0x800));
        if ((paramShort2 & 0x800) != 0) {
          fFixedFacet = ((short)(fFixedFacet | 0x800));
        }
      }
    }
    if ((paramShort1 & 0x10) != 0) {
      if ((j & 0x10) == 0)
      {
        reportError("cos-applicable-facets", new Object[] { "whiteSpace", fTypeName });
      }
      else
      {
        fWhiteSpace = whiteSpace;
        whiteSpaceAnnotation = whiteSpaceAnnotation;
        fFacetsDefined = ((short)(fFacetsDefined | 0x10));
        if ((paramShort2 & 0x10) != 0) {
          fFixedFacet = ((short)(fFixedFacet | 0x10));
        }
      }
    }
    if ((paramShort1 & 0x20) != 0) {
      if ((j & 0x20) == 0)
      {
        reportError("cos-applicable-facets", new Object[] { "maxInclusive", fTypeName });
      }
      else
      {
        maxInclusiveAnnotation = maxInclusiveAnnotation;
        try
        {
          fMaxInclusive = fBase.getActualValue(maxInclusive, paramValidationContext, localValidatedInfo1, true);
          fFacetsDefined = ((short)(fFacetsDefined | 0x20));
          if ((paramShort2 & 0x20) != 0) {
            fFixedFacet = ((short)(fFixedFacet | 0x20));
          }
        }
        catch (InvalidDatatypeValueException localInvalidDatatypeValueException1)
        {
          reportError(localInvalidDatatypeValueException1.getKey(), localInvalidDatatypeValueException1.getArgs());
          reportError("FacetValueFromBase", new Object[] { fTypeName, maxInclusive, "maxInclusive", fBase.getName() });
        }
        if (((fBase.fFacetsDefined & 0x20) != 0) && ((fBase.fFixedFacet & 0x20) != 0) && (fDVs[fValidationDV].compare(fMaxInclusive, fBase.fMaxInclusive) != 0)) {
          reportError("FixedFacetValue", new Object[] { "maxInclusive", fMaxInclusive, fBase.fMaxInclusive, fTypeName });
        }
        try
        {
          fBase.validate(paramValidationContext, localValidatedInfo1);
        }
        catch (InvalidDatatypeValueException localInvalidDatatypeValueException2)
        {
          reportError(localInvalidDatatypeValueException2.getKey(), localInvalidDatatypeValueException2.getArgs());
          reportError("FacetValueFromBase", new Object[] { fTypeName, maxInclusive, "maxInclusive", fBase.getName() });
        }
      }
    }
    int k = 1;
    if ((paramShort1 & 0x40) != 0) {
      if ((j & 0x40) == 0)
      {
        reportError("cos-applicable-facets", new Object[] { "maxExclusive", fTypeName });
      }
      else
      {
        maxExclusiveAnnotation = maxExclusiveAnnotation;
        try
        {
          fMaxExclusive = fBase.getActualValue(maxExclusive, paramValidationContext, localValidatedInfo1, true);
          fFacetsDefined = ((short)(fFacetsDefined | 0x40));
          if ((paramShort2 & 0x40) != 0) {
            fFixedFacet = ((short)(fFixedFacet | 0x40));
          }
        }
        catch (InvalidDatatypeValueException localInvalidDatatypeValueException3)
        {
          reportError(localInvalidDatatypeValueException3.getKey(), localInvalidDatatypeValueException3.getArgs());
          reportError("FacetValueFromBase", new Object[] { fTypeName, maxExclusive, "maxExclusive", fBase.getName() });
        }
        if ((fBase.fFacetsDefined & 0x40) != 0)
        {
          i = fDVs[fValidationDV].compare(fMaxExclusive, fBase.fMaxExclusive);
          if (((fBase.fFixedFacet & 0x40) != 0) && (i != 0)) {
            reportError("FixedFacetValue", new Object[] { "maxExclusive", maxExclusive, fBase.fMaxExclusive, fTypeName });
          }
          if (i == 0) {
            k = 0;
          }
        }
        if (k != 0) {
          try
          {
            fBase.validate(paramValidationContext, localValidatedInfo1);
          }
          catch (InvalidDatatypeValueException localInvalidDatatypeValueException4)
          {
            reportError(localInvalidDatatypeValueException4.getKey(), localInvalidDatatypeValueException4.getArgs());
            reportError("FacetValueFromBase", new Object[] { fTypeName, maxExclusive, "maxExclusive", fBase.getName() });
          }
        } else if (((fBase.fFacetsDefined & 0x20) != 0) && (fDVs[fValidationDV].compare(fMaxExclusive, fBase.fMaxInclusive) > 0)) {
          reportError("maxExclusive-valid-restriction.2", new Object[] { maxExclusive, fBase.fMaxInclusive });
        }
      }
    }
    k = 1;
    if ((paramShort1 & 0x80) != 0) {
      if ((j & 0x80) == 0)
      {
        reportError("cos-applicable-facets", new Object[] { "minExclusive", fTypeName });
      }
      else
      {
        minExclusiveAnnotation = minExclusiveAnnotation;
        try
        {
          fMinExclusive = fBase.getActualValue(minExclusive, paramValidationContext, localValidatedInfo1, true);
          fFacetsDefined = ((short)(fFacetsDefined | 0x80));
          if ((paramShort2 & 0x80) != 0) {
            fFixedFacet = ((short)(fFixedFacet | 0x80));
          }
        }
        catch (InvalidDatatypeValueException localInvalidDatatypeValueException5)
        {
          reportError(localInvalidDatatypeValueException5.getKey(), localInvalidDatatypeValueException5.getArgs());
          reportError("FacetValueFromBase", new Object[] { fTypeName, minExclusive, "minExclusive", fBase.getName() });
        }
        if ((fBase.fFacetsDefined & 0x80) != 0)
        {
          i = fDVs[fValidationDV].compare(fMinExclusive, fBase.fMinExclusive);
          if (((fBase.fFixedFacet & 0x80) != 0) && (i != 0)) {
            reportError("FixedFacetValue", new Object[] { "minExclusive", minExclusive, fBase.fMinExclusive, fTypeName });
          }
          if (i == 0) {
            k = 0;
          }
        }
        if (k != 0) {
          try
          {
            fBase.validate(paramValidationContext, localValidatedInfo1);
          }
          catch (InvalidDatatypeValueException localInvalidDatatypeValueException6)
          {
            reportError(localInvalidDatatypeValueException6.getKey(), localInvalidDatatypeValueException6.getArgs());
            reportError("FacetValueFromBase", new Object[] { fTypeName, minExclusive, "minExclusive", fBase.getName() });
          }
        } else if (((fBase.fFacetsDefined & 0x100) != 0) && (fDVs[fValidationDV].compare(fMinExclusive, fBase.fMinInclusive) < 0)) {
          reportError("minExclusive-valid-restriction.3", new Object[] { minExclusive, fBase.fMinInclusive });
        }
      }
    }
    if ((paramShort1 & 0x100) != 0) {
      if ((j & 0x100) == 0)
      {
        reportError("cos-applicable-facets", new Object[] { "minInclusive", fTypeName });
      }
      else
      {
        minInclusiveAnnotation = minInclusiveAnnotation;
        try
        {
          fMinInclusive = fBase.getActualValue(minInclusive, paramValidationContext, localValidatedInfo1, true);
          fFacetsDefined = ((short)(fFacetsDefined | 0x100));
          if ((paramShort2 & 0x100) != 0) {
            fFixedFacet = ((short)(fFixedFacet | 0x100));
          }
        }
        catch (InvalidDatatypeValueException localInvalidDatatypeValueException7)
        {
          reportError(localInvalidDatatypeValueException7.getKey(), localInvalidDatatypeValueException7.getArgs());
          reportError("FacetValueFromBase", new Object[] { fTypeName, minInclusive, "minInclusive", fBase.getName() });
        }
        if (((fBase.fFacetsDefined & 0x100) != 0) && ((fBase.fFixedFacet & 0x100) != 0) && (fDVs[fValidationDV].compare(fMinInclusive, fBase.fMinInclusive) != 0)) {
          reportError("FixedFacetValue", new Object[] { "minInclusive", minInclusive, fBase.fMinInclusive, fTypeName });
        }
        try
        {
          fBase.validate(paramValidationContext, localValidatedInfo1);
        }
        catch (InvalidDatatypeValueException localInvalidDatatypeValueException8)
        {
          reportError(localInvalidDatatypeValueException8.getKey(), localInvalidDatatypeValueException8.getArgs());
          reportError("FacetValueFromBase", new Object[] { fTypeName, minInclusive, "minInclusive", fBase.getName() });
        }
      }
    }
    if ((paramShort1 & 0x200) != 0) {
      if ((j & 0x200) == 0)
      {
        reportError("cos-applicable-facets", new Object[] { "totalDigits", fTypeName });
      }
      else
      {
        totalDigitsAnnotation = totalDigitsAnnotation;
        fTotalDigits = totalDigits;
        fFacetsDefined = ((short)(fFacetsDefined | 0x200));
        if ((paramShort2 & 0x200) != 0) {
          fFixedFacet = ((short)(fFixedFacet | 0x200));
        }
      }
    }
    if ((paramShort1 & 0x400) != 0) {
      if ((j & 0x400) == 0)
      {
        reportError("cos-applicable-facets", new Object[] { "fractionDigits", fTypeName });
      }
      else
      {
        fFractionDigits = fractionDigits;
        fractionDigitsAnnotation = fractionDigitsAnnotation;
        fFacetsDefined = ((short)(fFacetsDefined | 0x400));
        if ((paramShort2 & 0x400) != 0) {
          fFixedFacet = ((short)(fFixedFacet | 0x400));
        }
      }
    }
    if (paramShort3 != 0) {
      fPatternType = paramShort3;
    }
    if (fFacetsDefined != 0)
    {
      if (((fFacetsDefined & 0x2) != 0) && ((fFacetsDefined & 0x4) != 0) && (fMinLength > fMaxLength)) {
        reportError("minLength-less-than-equal-to-maxLength", new Object[] { Integer.toString(fMinLength), Integer.toString(fMaxLength), fTypeName });
      }
      if (((fFacetsDefined & 0x40) != 0) && ((fFacetsDefined & 0x20) != 0)) {
        reportError("maxInclusive-maxExclusive", new Object[] { fMaxInclusive, fMaxExclusive, fTypeName });
      }
      if (((fFacetsDefined & 0x80) != 0) && ((fFacetsDefined & 0x100) != 0)) {
        reportError("minInclusive-minExclusive", new Object[] { fMinInclusive, fMinExclusive, fTypeName });
      }
      if (((fFacetsDefined & 0x20) != 0) && ((fFacetsDefined & 0x100) != 0))
      {
        i = fDVs[fValidationDV].compare(fMinInclusive, fMaxInclusive);
        if ((i != -1) && (i != 0)) {
          reportError("minInclusive-less-than-equal-to-maxInclusive", new Object[] { fMinInclusive, fMaxInclusive, fTypeName });
        }
      }
      if (((fFacetsDefined & 0x40) != 0) && ((fFacetsDefined & 0x80) != 0))
      {
        i = fDVs[fValidationDV].compare(fMinExclusive, fMaxExclusive);
        if ((i != -1) && (i != 0)) {
          reportError("minExclusive-less-than-equal-to-maxExclusive", new Object[] { fMinExclusive, fMaxExclusive, fTypeName });
        }
      }
      if (((fFacetsDefined & 0x20) != 0) && ((fFacetsDefined & 0x80) != 0) && (fDVs[fValidationDV].compare(fMinExclusive, fMaxInclusive) != -1)) {
        reportError("minExclusive-less-than-maxInclusive", new Object[] { fMinExclusive, fMaxInclusive, fTypeName });
      }
      if (((fFacetsDefined & 0x40) != 0) && ((fFacetsDefined & 0x100) != 0) && (fDVs[fValidationDV].compare(fMinInclusive, fMaxExclusive) != -1)) {
        reportError("minInclusive-less-than-maxExclusive", new Object[] { fMinInclusive, fMaxExclusive, fTypeName });
      }
      if (((fFacetsDefined & 0x400) != 0) && ((fFacetsDefined & 0x200) != 0) && (fFractionDigits > fTotalDigits)) {
        reportError("fractionDigits-totalDigits", new Object[] { Integer.toString(fFractionDigits), Integer.toString(fTotalDigits), fTypeName });
      }
      if ((fFacetsDefined & 0x1) != 0)
      {
        if (((fBase.fFacetsDefined & 0x2) != 0) && (fLength < fBase.fMinLength)) {
          reportError("length-minLength-maxLength.1.1", new Object[] { fTypeName, Integer.toString(fLength), Integer.toString(fBase.fMinLength) });
        }
        if (((fBase.fFacetsDefined & 0x4) != 0) && (fLength > fBase.fMaxLength)) {
          reportError("length-minLength-maxLength.2.1", new Object[] { fTypeName, Integer.toString(fLength), Integer.toString(fBase.fMaxLength) });
        }
        if (((fBase.fFacetsDefined & 0x1) != 0) && (fLength != fBase.fLength)) {
          reportError("length-valid-restriction", new Object[] { Integer.toString(fLength), Integer.toString(fBase.fLength), fTypeName });
        }
      }
      if (((fBase.fFacetsDefined & 0x1) != 0) || ((fFacetsDefined & 0x1) != 0))
      {
        if ((fFacetsDefined & 0x2) != 0)
        {
          if (fBase.fLength < fMinLength) {
            reportError("length-minLength-maxLength.1.1", new Object[] { fTypeName, Integer.toString(fBase.fLength), Integer.toString(fMinLength) });
          }
          if ((fBase.fFacetsDefined & 0x2) == 0) {
            reportError("length-minLength-maxLength.1.2.a", new Object[] { fTypeName });
          }
          if (fMinLength != fBase.fMinLength) {
            reportError("length-minLength-maxLength.1.2.b", new Object[] { fTypeName, Integer.toString(fMinLength), Integer.toString(fBase.fMinLength) });
          }
        }
        if ((fFacetsDefined & 0x4) != 0)
        {
          if (fBase.fLength > fMaxLength) {
            reportError("length-minLength-maxLength.2.1", new Object[] { fTypeName, Integer.toString(fBase.fLength), Integer.toString(fMaxLength) });
          }
          if ((fBase.fFacetsDefined & 0x4) == 0) {
            reportError("length-minLength-maxLength.2.2.a", new Object[] { fTypeName });
          }
          if (fMaxLength != fBase.fMaxLength) {
            reportError("length-minLength-maxLength.2.2.b", new Object[] { fTypeName, Integer.toString(fMaxLength), Integer.toString(fBase.fBase.fMaxLength) });
          }
        }
      }
      if ((fFacetsDefined & 0x2) != 0) {
        if ((fBase.fFacetsDefined & 0x4) != 0)
        {
          if (fMinLength > fBase.fMaxLength) {
            reportError("minLength-less-than-equal-to-maxLength", new Object[] { Integer.toString(fMinLength), Integer.toString(fBase.fMaxLength), fTypeName });
          }
        }
        else if ((fBase.fFacetsDefined & 0x2) != 0)
        {
          if (((fBase.fFixedFacet & 0x2) != 0) && (fMinLength != fBase.fMinLength)) {
            reportError("FixedFacetValue", new Object[] { "minLength", Integer.toString(fMinLength), Integer.toString(fBase.fMinLength), fTypeName });
          }
          if (fMinLength < fBase.fMinLength) {
            reportError("minLength-valid-restriction", new Object[] { Integer.toString(fMinLength), Integer.toString(fBase.fMinLength), fTypeName });
          }
        }
      }
      if (((fFacetsDefined & 0x4) != 0) && ((fBase.fFacetsDefined & 0x2) != 0) && (fMaxLength < fBase.fMinLength)) {
        reportError("minLength-less-than-equal-to-maxLength", new Object[] { Integer.toString(fBase.fMinLength), Integer.toString(fMaxLength) });
      }
      if (((fFacetsDefined & 0x4) != 0) && ((fBase.fFacetsDefined & 0x4) != 0))
      {
        if (((fBase.fFixedFacet & 0x4) != 0) && (fMaxLength != fBase.fMaxLength)) {
          reportError("FixedFacetValue", new Object[] { "maxLength", Integer.toString(fMaxLength), Integer.toString(fBase.fMaxLength), fTypeName });
        }
        if (fMaxLength > fBase.fMaxLength) {
          reportError("maxLength-valid-restriction", new Object[] { Integer.toString(fMaxLength), Integer.toString(fBase.fMaxLength), fTypeName });
        }
      }
      if (((fFacetsDefined & 0x200) != 0) && ((fBase.fFacetsDefined & 0x200) != 0))
      {
        if (((fBase.fFixedFacet & 0x200) != 0) && (fTotalDigits != fBase.fTotalDigits)) {
          reportError("FixedFacetValue", new Object[] { "totalDigits", Integer.toString(fTotalDigits), Integer.toString(fBase.fTotalDigits), fTypeName });
        }
        if (fTotalDigits > fBase.fTotalDigits) {
          reportError("totalDigits-valid-restriction", new Object[] { Integer.toString(fTotalDigits), Integer.toString(fBase.fTotalDigits), fTypeName });
        }
      }
      if (((fFacetsDefined & 0x400) != 0) && ((fBase.fFacetsDefined & 0x200) != 0) && (fFractionDigits > fBase.fTotalDigits)) {
        reportError("fractionDigits-totalDigits", new Object[] { Integer.toString(fFractionDigits), Integer.toString(fTotalDigits), fTypeName });
      }
      if (((fFacetsDefined & 0x400) != 0) && ((fBase.fFacetsDefined & 0x400) != 0))
      {
        if (((fBase.fFixedFacet & 0x400) != 0) && (fFractionDigits != fBase.fFractionDigits)) {
          reportError("FixedFacetValue", new Object[] { "fractionDigits", Integer.toString(fFractionDigits), Integer.toString(fBase.fFractionDigits), fTypeName });
        }
        if (fFractionDigits > fBase.fFractionDigits) {
          reportError("fractionDigits-valid-restriction", new Object[] { Integer.toString(fFractionDigits), Integer.toString(fBase.fFractionDigits), fTypeName });
        }
      }
      if (((fFacetsDefined & 0x10) != 0) && ((fBase.fFacetsDefined & 0x10) != 0))
      {
        if (((fBase.fFixedFacet & 0x10) != 0) && (fWhiteSpace != fBase.fWhiteSpace)) {
          reportError("FixedFacetValue", new Object[] { "whiteSpace", whiteSpaceValue(fWhiteSpace), whiteSpaceValue(fBase.fWhiteSpace), fTypeName });
        }
        if ((fWhiteSpace == 0) && (fBase.fWhiteSpace == 2)) {
          reportError("whiteSpace-valid-restriction.1", new Object[] { fTypeName, "preserve" });
        }
        if ((fWhiteSpace == 1) && (fBase.fWhiteSpace == 2)) {
          reportError("whiteSpace-valid-restriction.1", new Object[] { fTypeName, "replace" });
        }
        if ((fWhiteSpace == 0) && (fBase.fWhiteSpace == 1)) {
          reportError("whiteSpace-valid-restriction.2", new Object[] { fTypeName });
        }
      }
    }
    if (((fFacetsDefined & 0x1) == 0) && ((fBase.fFacetsDefined & 0x1) != 0))
    {
      fFacetsDefined = ((short)(fFacetsDefined | 0x1));
      fLength = fBase.fLength;
      lengthAnnotation = fBase.lengthAnnotation;
    }
    if (((fFacetsDefined & 0x2) == 0) && ((fBase.fFacetsDefined & 0x2) != 0))
    {
      fFacetsDefined = ((short)(fFacetsDefined | 0x2));
      fMinLength = fBase.fMinLength;
      minLengthAnnotation = fBase.minLengthAnnotation;
    }
    if (((fFacetsDefined & 0x4) == 0) && ((fBase.fFacetsDefined & 0x4) != 0))
    {
      fFacetsDefined = ((short)(fFacetsDefined | 0x4));
      fMaxLength = fBase.fMaxLength;
      maxLengthAnnotation = fBase.maxLengthAnnotation;
    }
    if ((fBase.fFacetsDefined & 0x8) != 0) {
      if ((fFacetsDefined & 0x8) == 0)
      {
        fPattern = fBase.fPattern;
        fPatternStr = fBase.fPatternStr;
        fFacetsDefined = ((short)(fFacetsDefined | 0x8));
      }
      else
      {
        for (int m = fBase.fPattern.size() - 1; m >= 0; m--)
        {
          fPattern.addElement(fBase.fPattern.elementAt(m));
          fPatternStr.addElement(fBase.fPatternStr.elementAt(m));
        }
        if (fBase.patternAnnotations != null) {
          for (int n = fBase.patternAnnotations.getLength() - 1; n >= 0; n--) {
            patternAnnotations.add(fBase.patternAnnotations.item(n));
          }
        }
      }
    }
    if (((fFacetsDefined & 0x10) == 0) && ((fBase.fFacetsDefined & 0x10) != 0))
    {
      fFacetsDefined = ((short)(fFacetsDefined | 0x10));
      fWhiteSpace = fBase.fWhiteSpace;
      whiteSpaceAnnotation = fBase.whiteSpaceAnnotation;
    }
    if (((fFacetsDefined & 0x800) == 0) && ((fBase.fFacetsDefined & 0x800) != 0))
    {
      fFacetsDefined = ((short)(fFacetsDefined | 0x800));
      fEnumeration = fBase.fEnumeration;
      enumerationAnnotations = fBase.enumerationAnnotations;
    }
    if (((fBase.fFacetsDefined & 0x40) != 0) && ((fFacetsDefined & 0x40) == 0) && ((fFacetsDefined & 0x20) == 0))
    {
      fFacetsDefined = ((short)(fFacetsDefined | 0x40));
      fMaxExclusive = fBase.fMaxExclusive;
      maxExclusiveAnnotation = fBase.maxExclusiveAnnotation;
    }
    if (((fBase.fFacetsDefined & 0x20) != 0) && ((fFacetsDefined & 0x40) == 0) && ((fFacetsDefined & 0x20) == 0))
    {
      fFacetsDefined = ((short)(fFacetsDefined | 0x20));
      fMaxInclusive = fBase.fMaxInclusive;
      maxInclusiveAnnotation = fBase.maxInclusiveAnnotation;
    }
    if (((fBase.fFacetsDefined & 0x80) != 0) && ((fFacetsDefined & 0x80) == 0) && ((fFacetsDefined & 0x100) == 0))
    {
      fFacetsDefined = ((short)(fFacetsDefined | 0x80));
      fMinExclusive = fBase.fMinExclusive;
      minExclusiveAnnotation = fBase.minExclusiveAnnotation;
    }
    if (((fBase.fFacetsDefined & 0x100) != 0) && ((fFacetsDefined & 0x80) == 0) && ((fFacetsDefined & 0x100) == 0))
    {
      fFacetsDefined = ((short)(fFacetsDefined | 0x100));
      fMinInclusive = fBase.fMinInclusive;
      minInclusiveAnnotation = fBase.minInclusiveAnnotation;
    }
    if (((fBase.fFacetsDefined & 0x200) != 0) && ((fFacetsDefined & 0x200) == 0))
    {
      fFacetsDefined = ((short)(fFacetsDefined | 0x200));
      fTotalDigits = fBase.fTotalDigits;
      totalDigitsAnnotation = fBase.totalDigitsAnnotation;
    }
    if (((fBase.fFacetsDefined & 0x400) != 0) && ((fFacetsDefined & 0x400) == 0))
    {
      fFacetsDefined = ((short)(fFacetsDefined | 0x400));
      fFractionDigits = fBase.fFractionDigits;
      fractionDigitsAnnotation = fBase.fractionDigitsAnnotation;
    }
    if ((fPatternType == 0) && (fBase.fPatternType != 0)) {
      fPatternType = fBase.fPatternType;
    }
    fFixedFacet = ((short)(fFixedFacet | fBase.fFixedFacet));
    caclFundamentalFacets();
  }
  
  public Object validate(String paramString, ValidationContext paramValidationContext, ValidatedInfo paramValidatedInfo)
    throws InvalidDatatypeValueException
  {
    if (paramValidationContext == null) {
      paramValidationContext = fEmptyContext;
    }
    if (paramValidatedInfo == null) {
      paramValidatedInfo = new ValidatedInfo();
    } else {
      memberType = null;
    }
    boolean bool = (paramValidationContext == null) || (paramValidationContext.needToNormalize());
    Object localObject = getActualValue(paramString, paramValidationContext, paramValidatedInfo, bool);
    validate(paramValidationContext, paramValidatedInfo);
    return localObject;
  }
  
  public ValidatedInfo validateWithInfo(String paramString, ValidationContext paramValidationContext, ValidatedInfo paramValidatedInfo)
    throws InvalidDatatypeValueException
  {
    if (paramValidationContext == null) {
      paramValidationContext = fEmptyContext;
    }
    if (paramValidatedInfo == null) {
      paramValidatedInfo = new ValidatedInfo();
    } else {
      memberType = null;
    }
    boolean bool = (paramValidationContext == null) || (paramValidationContext.needToNormalize());
    getActualValue(paramString, paramValidationContext, paramValidatedInfo, bool);
    validate(paramValidationContext, paramValidatedInfo);
    return paramValidatedInfo;
  }
  
  public Object validate(Object paramObject, ValidationContext paramValidationContext, ValidatedInfo paramValidatedInfo)
    throws InvalidDatatypeValueException
  {
    if (paramValidationContext == null) {
      paramValidationContext = fEmptyContext;
    }
    if (paramValidatedInfo == null) {
      paramValidatedInfo = new ValidatedInfo();
    } else {
      memberType = null;
    }
    boolean bool = (paramValidationContext == null) || (paramValidationContext.needToNormalize());
    Object localObject = getActualValue(paramObject, paramValidationContext, paramValidatedInfo, bool);
    validate(paramValidationContext, paramValidatedInfo);
    return localObject;
  }
  
  public void validate(ValidationContext paramValidationContext, ValidatedInfo paramValidatedInfo)
    throws InvalidDatatypeValueException
  {
    if (paramValidationContext == null) {
      paramValidationContext = fEmptyContext;
    }
    if ((paramValidationContext.needFacetChecking()) && (fFacetsDefined != 0) && (fFacetsDefined != 16)) {
      checkFacets(paramValidatedInfo);
    }
    if (paramValidationContext.needExtraChecking()) {
      checkExtraRules(paramValidationContext, paramValidatedInfo);
    }
  }
  
  private void checkFacets(ValidatedInfo paramValidatedInfo)
    throws InvalidDatatypeValueException
  {
    Object localObject = actualValue;
    String str = normalizedValue;
    short s = actualValueType;
    ShortList localShortList1 = itemValueTypes;
    int i;
    if ((fValidationDV != 18) && (fValidationDV != 20))
    {
      i = fDVs[fValidationDV].getDataLength(localObject);
      if (((fFacetsDefined & 0x4) != 0) && (i > fMaxLength)) {
        throw new InvalidDatatypeValueException("cvc-maxLength-valid", new Object[] { str, Integer.toString(i), Integer.toString(fMaxLength), fTypeName });
      }
      if (((fFacetsDefined & 0x2) != 0) && (i < fMinLength)) {
        throw new InvalidDatatypeValueException("cvc-minLength-valid", new Object[] { str, Integer.toString(i), Integer.toString(fMinLength), fTypeName });
      }
      if (((fFacetsDefined & 0x1) != 0) && (i != fLength)) {
        throw new InvalidDatatypeValueException("cvc-length-valid", new Object[] { str, Integer.toString(i), Integer.toString(fLength), fTypeName });
      }
    }
    if ((fFacetsDefined & 0x800) != 0)
    {
      i = 0;
      int j = fEnumeration.size();
      int k = convertToPrimitiveKind(s);
      for (int m = 0; m < j; m++)
      {
        int n = convertToPrimitiveKind(fEnumerationType[m]);
        if (((k == n) || ((k == 1) && (n == 2)) || ((k == 2) && (n == 1))) && (fEnumeration.elementAt(m).equals(localObject))) {
          if ((k == 44) || (k == 43))
          {
            ShortList localShortList2 = fEnumerationItemType[m];
            int i1 = localShortList1 != null ? localShortList1.getLength() : 0;
            int i2 = localShortList2 != null ? localShortList2.getLength() : 0;
            if (i1 == i2)
            {
              for (int i3 = 0; i3 < i1; i3++)
              {
                int i4 = convertToPrimitiveKind(localShortList1.item(i3));
                int i5 = convertToPrimitiveKind(localShortList2.item(i3));
                if ((i4 != i5) && ((i4 != 1) || (i5 != 2)) && ((i4 != 2) || (i5 != 1))) {
                  break;
                }
              }
              if (i3 == i1)
              {
                i = 1;
                break;
              }
            }
          }
          else
          {
            i = 1;
            break;
          }
        }
      }
      if (i == 0) {
        throw new InvalidDatatypeValueException("cvc-enumeration-valid", new Object[] { str, fEnumeration.toString() });
      }
    }
    if ((fFacetsDefined & 0x400) != 0)
    {
      i = fDVs[fValidationDV].getFractionDigits(localObject);
      if (i > fFractionDigits) {
        throw new InvalidDatatypeValueException("cvc-fractionDigits-valid", new Object[] { str, Integer.toString(i), Integer.toString(fFractionDigits) });
      }
    }
    if ((fFacetsDefined & 0x200) != 0)
    {
      i = fDVs[fValidationDV].getTotalDigits(localObject);
      if (i > fTotalDigits) {
        throw new InvalidDatatypeValueException("cvc-totalDigits-valid", new Object[] { str, Integer.toString(i), Integer.toString(fTotalDigits) });
      }
    }
    if ((fFacetsDefined & 0x20) != 0)
    {
      i = fDVs[fValidationDV].compare(localObject, fMaxInclusive);
      if ((i != -1) && (i != 0)) {
        throw new InvalidDatatypeValueException("cvc-maxInclusive-valid", new Object[] { str, fMaxInclusive, fTypeName });
      }
    }
    if ((fFacetsDefined & 0x40) != 0)
    {
      i = fDVs[fValidationDV].compare(localObject, fMaxExclusive);
      if (i != -1) {
        throw new InvalidDatatypeValueException("cvc-maxExclusive-valid", new Object[] { str, fMaxExclusive, fTypeName });
      }
    }
    if ((fFacetsDefined & 0x100) != 0)
    {
      i = fDVs[fValidationDV].compare(localObject, fMinInclusive);
      if ((i != 1) && (i != 0)) {
        throw new InvalidDatatypeValueException("cvc-minInclusive-valid", new Object[] { str, fMinInclusive, fTypeName });
      }
    }
    if ((fFacetsDefined & 0x80) != 0)
    {
      i = fDVs[fValidationDV].compare(localObject, fMinExclusive);
      if (i != 1) {
        throw new InvalidDatatypeValueException("cvc-minExclusive-valid", new Object[] { str, fMinExclusive, fTypeName });
      }
    }
  }
  
  private void checkExtraRules(ValidationContext paramValidationContext, ValidatedInfo paramValidatedInfo)
    throws InvalidDatatypeValueException
  {
    Object localObject = actualValue;
    if (fVariety == 1)
    {
      fDVs[fValidationDV].checkExtraRules(localObject, paramValidationContext);
    }
    else if (fVariety == 2)
    {
      ListDV.ListData localListData = (ListDV.ListData)localObject;
      int i = localListData.getLength();
      if (fItemType.fVariety == 3)
      {
        XSSimpleTypeDecl[] arrayOfXSSimpleTypeDecl = (XSSimpleTypeDecl[])memberTypes;
        XSSimpleType localXSSimpleType = memberType;
        for (int k = i - 1; k >= 0; k--)
        {
          actualValue = localListData.item(k);
          memberType = arrayOfXSSimpleTypeDecl[k];
          fItemType.checkExtraRules(paramValidationContext, paramValidatedInfo);
        }
        memberType = localXSSimpleType;
      }
      else
      {
        for (int j = i - 1; j >= 0; j--)
        {
          actualValue = localListData.item(j);
          fItemType.checkExtraRules(paramValidationContext, paramValidatedInfo);
        }
      }
      actualValue = localListData;
    }
    else
    {
      ((XSSimpleTypeDecl)memberType).checkExtraRules(paramValidationContext, paramValidatedInfo);
    }
  }
  
  private Object getActualValue(Object paramObject, ValidationContext paramValidationContext, ValidatedInfo paramValidatedInfo, boolean paramBoolean)
    throws InvalidDatatypeValueException
  {
    String str;
    if (paramBoolean) {
      str = normalize(paramObject, fWhiteSpace);
    } else {
      str = paramObject.toString();
    }
    int k;
    if ((fFacetsDefined & 0x8) != 0) {
      for (k = fPattern.size() - 1; k >= 0; k--)
      {
        RegularExpression localRegularExpression = (RegularExpression)fPattern.elementAt(k);
        if (!localRegularExpression.matches(str)) {
          throw new InvalidDatatypeValueException("cvc-pattern-valid", new Object[] { paramObject, fPatternStr.elementAt(k), fTypeName });
        }
      }
    }
    Object localObject1;
    if (fVariety == 1)
    {
      if (fPatternType != 0)
      {
        int i = 0;
        if (fPatternType == 1) {
          i = !XMLChar.isValidNmtoken(str) ? 1 : 0;
        } else if (fPatternType == 2) {
          i = !XMLChar.isValidName(str) ? 1 : 0;
        } else if (fPatternType == 3) {
          i = !XMLChar.isValidNCName(str) ? 1 : 0;
        }
        if (i != 0) {
          throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[] { str, SPECIAL_PATTERN_STRING[fPatternType] });
        }
      }
      normalizedValue = str;
      localObject1 = fDVs[fValidationDV].getActualValue(str, paramValidationContext);
      actualValue = localObject1;
      actualValueType = fBuiltInKind;
      return localObject1;
    }
    Object localObject3;
    Object localObject4;
    if (fVariety == 2)
    {
      localObject1 = new StringTokenizer(str, " ");
      k = ((StringTokenizer)localObject1).countTokens();
      localObject3 = new Object[k];
      m = fItemType.getVariety() == 3 ? 1 : 0;
      localObject4 = new short[m != 0 ? k : 1];
      if (m == 0) {
        localObject4[0] = fItemType.fBuiltInKind;
      }
      XSSimpleTypeDecl[] arrayOfXSSimpleTypeDecl = new XSSimpleTypeDecl[k];
      for (int i1 = 0; i1 < k; i1++)
      {
        localObject3[i1] = fItemType.getActualValue(((StringTokenizer)localObject1).nextToken(), paramValidationContext, paramValidatedInfo, false);
        if ((paramValidationContext.needFacetChecking()) && (fItemType.fFacetsDefined != 0) && (fItemType.fFacetsDefined != 16)) {
          fItemType.checkFacets(paramValidatedInfo);
        }
        arrayOfXSSimpleTypeDecl[i1] = ((XSSimpleTypeDecl)memberType);
        if (m != 0) {
          localObject4[i1] = fBuiltInKind;
        }
      }
      ListDV.ListData localListData = new ListDV.ListData((Object[])localObject3);
      actualValue = localListData;
      actualValueType = (m != 0 ? 43 : 44);
      memberType = null;
      memberTypes = arrayOfXSSimpleTypeDecl;
      itemValueTypes = new ShortListImpl((short[])localObject4, localObject4.length);
      normalizedValue = str;
      return localListData;
    }
    int j = 0;
    while (j < fMemberTypes.length) {
      try
      {
        Object localObject2 = fMemberTypes[j].getActualValue(paramObject, paramValidationContext, paramValidatedInfo, true);
        if ((paramValidationContext.needFacetChecking()) && (fMemberTypes[j].fFacetsDefined != 0) && (fMemberTypes[j].fFacetsDefined != 16)) {
          fMemberTypes[j].checkFacets(paramValidatedInfo);
        }
        memberType = fMemberTypes[j];
        return localObject2;
      }
      catch (InvalidDatatypeValueException localInvalidDatatypeValueException)
      {
        j++;
      }
    }
    StringBuffer localStringBuffer = new StringBuffer();
    for (int m = 0; m < fMemberTypes.length; m++)
    {
      if (m != 0) {
        localStringBuffer.append(" | ");
      }
      localObject3 = fMemberTypes[m];
      if (fTargetNamespace != null)
      {
        localStringBuffer.append('{');
        localStringBuffer.append(fTargetNamespace);
        localStringBuffer.append('}');
      }
      localStringBuffer.append(fTypeName);
      if (fEnumeration != null)
      {
        localObject4 = fEnumeration;
        localStringBuffer.append(" : [");
        for (int n = 0; n < ((Vector)localObject4).size(); n++)
        {
          if (n != 0) {
            localStringBuffer.append(',');
          }
          localStringBuffer.append(((Vector)localObject4).elementAt(n));
        }
        localStringBuffer.append(']');
      }
    }
    throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.3", new Object[] { paramObject, fTypeName, localStringBuffer.toString() });
  }
  
  public boolean isEqual(Object paramObject1, Object paramObject2)
  {
    if (paramObject1 == null) {
      return false;
    }
    return paramObject1.equals(paramObject2);
  }
  
  public boolean isIdentical(Object paramObject1, Object paramObject2)
  {
    if (paramObject1 == null) {
      return false;
    }
    return fDVs[fValidationDV].isIdentical(paramObject1, paramObject2);
  }
  
  public static String normalize(String paramString, short paramShort)
  {
    int i = paramString == null ? 0 : paramString.length();
    if ((i == 0) || (paramShort == 0)) {
      return paramString;
    }
    StringBuffer localStringBuffer = new StringBuffer();
    int j;
    char c;
    if (paramShort == 1)
    {
      for (j = 0; j < i; j++)
      {
        c = paramString.charAt(j);
        if ((c != '\t') && (c != '\n') && (c != '\r')) {
          localStringBuffer.append(c);
        } else {
          localStringBuffer.append(' ');
        }
      }
    }
    else
    {
      int k = 1;
      for (j = 0; j < i; j++)
      {
        c = paramString.charAt(j);
        if ((c != '\t') && (c != '\n') && (c != '\r') && (c != ' '))
        {
          localStringBuffer.append(c);
          k = 0;
        }
        else
        {
          while (j < i - 1)
          {
            c = paramString.charAt(j + 1);
            if ((c != '\t') && (c != '\n') && (c != '\r') && (c != ' ')) {
              break;
            }
            j++;
          }
          if ((j < i - 1) && (k == 0)) {
            localStringBuffer.append(' ');
          }
        }
      }
    }
    return localStringBuffer.toString();
  }
  
  protected String normalize(Object paramObject, short paramShort)
  {
    if (paramObject == null) {
      return null;
    }
    if ((fFacetsDefined & 0x8) == 0)
    {
      int i = fDVNormalizeType[fValidationDV];
      if (i == 0) {
        return paramObject.toString();
      }
      if (i == 1) {
        return paramObject.toString().trim();
      }
    }
    if (!(paramObject instanceof StringBuffer))
    {
      localObject = paramObject.toString();
      return normalize((String)localObject, paramShort);
    }
    Object localObject = (StringBuffer)paramObject;
    int j = ((StringBuffer)localObject).length();
    if (j == 0) {
      return "";
    }
    if (paramShort == 0) {
      return ((StringBuffer)localObject).toString();
    }
    int k;
    char c;
    if (paramShort == 1)
    {
      for (k = 0; k < j; k++)
      {
        c = ((StringBuffer)localObject).charAt(k);
        if ((c == '\t') || (c == '\n') || (c == '\r')) {
          ((StringBuffer)localObject).setCharAt(k, ' ');
        }
      }
    }
    else
    {
      int m = 0;
      int n = 1;
      for (k = 0; k < j; k++)
      {
        c = ((StringBuffer)localObject).charAt(k);
        if ((c != '\t') && (c != '\n') && (c != '\r') && (c != ' '))
        {
          ((StringBuffer)localObject).setCharAt(m++, c);
          n = 0;
        }
        else
        {
          while (k < j - 1)
          {
            c = ((StringBuffer)localObject).charAt(k + 1);
            if ((c != '\t') && (c != '\n') && (c != '\r') && (c != ' ')) {
              break;
            }
            k++;
          }
          if ((k < j - 1) && (n == 0)) {
            ((StringBuffer)localObject).setCharAt(m++, ' ');
          }
        }
      }
      ((StringBuffer)localObject).setLength(m);
    }
    return ((StringBuffer)localObject).toString();
  }
  
  void reportError(String paramString, Object[] paramArrayOfObject)
    throws InvalidDatatypeFacetException
  {
    throw new InvalidDatatypeFacetException(paramString, paramArrayOfObject);
  }
  
  private String whiteSpaceValue(short paramShort)
  {
    return WS_FACET_STRING[paramShort];
  }
  
  public short getOrdered()
  {
    return fOrdered;
  }
  
  public boolean getBounded()
  {
    return fBounded;
  }
  
  public boolean getFinite()
  {
    return fFinite;
  }
  
  public boolean getNumeric()
  {
    return fNumeric;
  }
  
  public boolean isDefinedFacet(short paramShort)
  {
    if ((fFacetsDefined & paramShort) != 0) {
      return true;
    }
    if (fPatternType != 0) {
      return paramShort == 8;
    }
    if (fValidationDV == 24) {
      return (paramShort == 8) || (paramShort == 1024);
    }
    return false;
  }
  
  public short getDefinedFacets()
  {
    if (fPatternType != 0) {
      return (short)(fFacetsDefined | 0x8);
    }
    if (fValidationDV == 24) {
      return (short)(fFacetsDefined | 0x8 | 0x400);
    }
    return fFacetsDefined;
  }
  
  public boolean isFixedFacet(short paramShort)
  {
    if ((fFixedFacet & paramShort) != 0) {
      return true;
    }
    if (fValidationDV == 24) {
      return paramShort == 1024;
    }
    return false;
  }
  
  public short getFixedFacets()
  {
    if (fValidationDV == 24) {
      return (short)(fFixedFacet | 0x400);
    }
    return fFixedFacet;
  }
  
  public String getLexicalFacetValue(short paramShort)
  {
    switch (paramShort)
    {
    case 1: 
      return fLength == -1 ? null : Integer.toString(fLength);
    case 2: 
      return fMinLength == -1 ? null : Integer.toString(fMinLength);
    case 4: 
      return fMaxLength == -1 ? null : Integer.toString(fMaxLength);
    case 16: 
      return WS_FACET_STRING[fWhiteSpace];
    case 32: 
      return fMaxInclusive == null ? null : fMaxInclusive.toString();
    case 64: 
      return fMaxExclusive == null ? null : fMaxExclusive.toString();
    case 128: 
      return fMinExclusive == null ? null : fMinExclusive.toString();
    case 256: 
      return fMinInclusive == null ? null : fMinInclusive.toString();
    case 512: 
      if (fValidationDV == 24) {
        return "0";
      }
      return fTotalDigits == -1 ? null : Integer.toString(fTotalDigits);
    case 1024: 
      return fFractionDigits == -1 ? null : Integer.toString(fFractionDigits);
    }
    return null;
  }
  
  public StringList getLexicalEnumeration()
  {
    if (fLexicalEnumeration == null)
    {
      if (fEnumeration == null) {
        return StringListImpl.EMPTY_LIST;
      }
      int i = fEnumeration.size();
      String[] arrayOfString = new String[i];
      for (int j = 0; j < i; j++) {
        arrayOfString[j] = fEnumeration.elementAt(j).toString();
      }
      fLexicalEnumeration = new StringListImpl(arrayOfString, i);
    }
    return fLexicalEnumeration;
  }
  
  public ObjectList getActualEnumeration()
  {
    if (fActualEnumeration == null) {
      fActualEnumeration = new ObjectList()
      {
        public int getLength()
        {
          return fEnumeration != null ? fEnumeration.size() : 0;
        }
        
        public boolean contains(Object paramAnonymousObject)
        {
          return (fEnumeration != null) && (fEnumeration.contains(paramAnonymousObject));
        }
        
        public Object item(int paramAnonymousInt)
        {
          if ((paramAnonymousInt < 0) || (paramAnonymousInt >= getLength())) {
            return null;
          }
          return fEnumeration.elementAt(paramAnonymousInt);
        }
      };
    }
    return fActualEnumeration;
  }
  
  public ObjectList getEnumerationItemTypeList()
  {
    if (fEnumerationItemTypeList == null)
    {
      if (fEnumerationItemType == null) {
        return null;
      }
      fEnumerationItemTypeList = new ObjectList()
      {
        public int getLength()
        {
          return fEnumerationItemType != null ? fEnumerationItemType.length : 0;
        }
        
        public boolean contains(Object paramAnonymousObject)
        {
          if ((fEnumerationItemType == null) || (!(paramAnonymousObject instanceof ShortList))) {
            return false;
          }
          for (int i = 0; i < fEnumerationItemType.length; i++) {
            if (fEnumerationItemType[i] == paramAnonymousObject) {
              return true;
            }
          }
          return false;
        }
        
        public Object item(int paramAnonymousInt)
        {
          if ((paramAnonymousInt < 0) || (paramAnonymousInt >= getLength())) {
            return null;
          }
          return fEnumerationItemType[paramAnonymousInt];
        }
      };
    }
    return fEnumerationItemTypeList;
  }
  
  public ShortList getEnumerationTypeList()
  {
    if (fEnumerationTypeList == null)
    {
      if (fEnumerationType == null) {
        return null;
      }
      fEnumerationTypeList = new ShortListImpl(fEnumerationType, fEnumerationType.length);
    }
    return fEnumerationTypeList;
  }
  
  public StringList getLexicalPattern()
  {
    if ((fPatternType == 0) && (fValidationDV != 24) && (fPatternStr == null)) {
      return StringListImpl.EMPTY_LIST;
    }
    if (fLexicalPattern == null)
    {
      int i = fPatternStr == null ? 0 : fPatternStr.size();
      String[] arrayOfString;
      if (fPatternType == 1)
      {
        arrayOfString = new String[i + 1];
        arrayOfString[i] = "\\c+";
      }
      else if (fPatternType == 2)
      {
        arrayOfString = new String[i + 1];
        arrayOfString[i] = "\\i\\c*";
      }
      else if (fPatternType == 3)
      {
        arrayOfString = new String[i + 2];
        arrayOfString[i] = "\\i\\c*";
        arrayOfString[(i + 1)] = "[\\i-[:]][\\c-[:]]*";
      }
      else if (fValidationDV == 24)
      {
        arrayOfString = new String[i + 1];
        arrayOfString[i] = "[\\-+]?[0-9]+";
      }
      else
      {
        arrayOfString = new String[i];
      }
      for (int j = 0; j < i; j++) {
        arrayOfString[j] = ((String)fPatternStr.elementAt(j));
      }
      fLexicalPattern = new StringListImpl(arrayOfString, arrayOfString.length);
    }
    return fLexicalPattern;
  }
  
  public XSObjectList getAnnotations()
  {
    return fAnnotations != null ? fAnnotations : XSObjectListImpl.EMPTY_LIST;
  }
  
  private void caclFundamentalFacets()
  {
    setOrdered();
    setNumeric();
    setBounded();
    setCardinality();
  }
  
  private void setOrdered()
  {
    if (fVariety == 1)
    {
      fOrdered = fBase.fOrdered;
    }
    else if (fVariety == 2)
    {
      fOrdered = 0;
    }
    else if (fVariety == 3)
    {
      int i = fMemberTypes.length;
      if (i == 0)
      {
        fOrdered = 1;
        return;
      }
      int j = getPrimitiveDV(fMemberTypes[0].fValidationDV);
      int k = j != 0 ? 1 : 0;
      int m = fMemberTypes[0].fOrdered == 0 ? 1 : 0;
      for (int n = 1; (n < fMemberTypes.length) && ((k != 0) || (m != 0)); n++)
      {
        if (k != 0) {
          k = j == getPrimitiveDV(fMemberTypes[n].fValidationDV) ? 1 : 0;
        }
        if (m != 0) {
          m = fMemberTypes[n].fOrdered == 0 ? 1 : 0;
        }
      }
      if (k != 0) {
        fOrdered = fMemberTypes[0].fOrdered;
      } else if (m != 0) {
        fOrdered = 0;
      } else {
        fOrdered = 1;
      }
    }
  }
  
  private void setNumeric()
  {
    if (fVariety == 1)
    {
      fNumeric = fBase.fNumeric;
    }
    else if (fVariety == 2)
    {
      fNumeric = false;
    }
    else if (fVariety == 3)
    {
      XSSimpleTypeDecl[] arrayOfXSSimpleTypeDecl = fMemberTypes;
      for (int i = 0; i < arrayOfXSSimpleTypeDecl.length; i++) {
        if (!arrayOfXSSimpleTypeDecl[i].getNumeric())
        {
          fNumeric = false;
          return;
        }
      }
      fNumeric = true;
    }
  }
  
  private void setBounded()
  {
    if (fVariety == 1)
    {
      if ((((fFacetsDefined & 0x100) != 0) || ((fFacetsDefined & 0x80) != 0)) && (((fFacetsDefined & 0x20) != 0) || ((fFacetsDefined & 0x40) != 0))) {
        fBounded = true;
      } else {
        fBounded = false;
      }
    }
    else if (fVariety == 2)
    {
      if (((fFacetsDefined & 0x1) != 0) || (((fFacetsDefined & 0x2) != 0) && ((fFacetsDefined & 0x4) != 0))) {
        fBounded = true;
      } else {
        fBounded = false;
      }
    }
    else if (fVariety == 3)
    {
      XSSimpleTypeDecl[] arrayOfXSSimpleTypeDecl = fMemberTypes;
      int i = 0;
      if (arrayOfXSSimpleTypeDecl.length > 0) {
        i = getPrimitiveDV(0fValidationDV);
      }
      for (int j = 0; j < arrayOfXSSimpleTypeDecl.length; j++) {
        if ((!arrayOfXSSimpleTypeDecl[j].getBounded()) || (i != getPrimitiveDV(fValidationDV)))
        {
          fBounded = false;
          return;
        }
      }
      fBounded = true;
    }
  }
  
  private boolean specialCardinalityCheck()
  {
    return (fBase.fValidationDV == 9) || (fBase.fValidationDV == 10) || (fBase.fValidationDV == 11) || (fBase.fValidationDV == 12) || (fBase.fValidationDV == 13) || (fBase.fValidationDV == 14);
  }
  
  private void setCardinality()
  {
    if (fVariety == 1)
    {
      if (fBase.fFinite) {
        fFinite = true;
      } else if (((fFacetsDefined & 0x1) != 0) || ((fFacetsDefined & 0x4) != 0) || ((fFacetsDefined & 0x200) != 0)) {
        fFinite = true;
      } else if ((((fFacetsDefined & 0x100) != 0) || ((fFacetsDefined & 0x80) != 0)) && (((fFacetsDefined & 0x20) != 0) || ((fFacetsDefined & 0x40) != 0)))
      {
        if (((fFacetsDefined & 0x400) != 0) || (specialCardinalityCheck())) {
          fFinite = true;
        } else {
          fFinite = false;
        }
      }
      else {
        fFinite = false;
      }
    }
    else if (fVariety == 2)
    {
      if (((fFacetsDefined & 0x1) != 0) || (((fFacetsDefined & 0x2) != 0) && ((fFacetsDefined & 0x4) != 0))) {
        fFinite = true;
      } else {
        fFinite = false;
      }
    }
    else if (fVariety == 3)
    {
      XSSimpleTypeDecl[] arrayOfXSSimpleTypeDecl = fMemberTypes;
      for (int i = 0; i < arrayOfXSSimpleTypeDecl.length; i++) {
        if (!arrayOfXSSimpleTypeDecl[i].getFinite())
        {
          fFinite = false;
          return;
        }
      }
      fFinite = true;
    }
  }
  
  private short getPrimitiveDV(short paramShort)
  {
    if ((paramShort == 21) || (paramShort == 22) || (paramShort == 23)) {
      return 1;
    }
    if (paramShort == 24) {
      return 3;
    }
    return paramShort;
  }
  
  public boolean derivedFromType(XSTypeDefinition paramXSTypeDefinition, short paramShort)
  {
    if (paramXSTypeDefinition == null) {
      return false;
    }
    if (paramXSTypeDefinition.getBaseType() == paramXSTypeDefinition) {
      return true;
    }
    for (Object localObject = this; (localObject != paramXSTypeDefinition) && (localObject != fAnySimpleType); localObject = ((XSTypeDefinition)localObject).getBaseType()) {}
    return localObject == paramXSTypeDefinition;
  }
  
  public boolean derivedFrom(String paramString1, String paramString2, short paramShort)
  {
    if (paramString2 == null) {
      return false;
    }
    if (("http://www.w3.org/2001/XMLSchema".equals(paramString1)) && ("anyType".equals(paramString2))) {
      return true;
    }
    for (Object localObject = this; ((!paramString2.equals(((XSObject)localObject).getName())) || (((paramString1 != null) || (((XSObject)localObject).getNamespace() != null)) && ((paramString1 == null) || (!paramString1.equals(((XSObject)localObject).getNamespace()))))) && (localObject != fAnySimpleType); localObject = ((XSTypeDefinition)localObject).getBaseType()) {}
    return localObject != fAnySimpleType;
  }
  
  public boolean isDOMDerivedFrom(String paramString1, String paramString2, int paramInt)
  {
    if (paramString2 == null) {
      return false;
    }
    if ((SchemaSymbols.URI_SCHEMAFORSCHEMA.equals(paramString1)) && ("anyType".equals(paramString2)) && (((paramInt & 0x1) != 0) || (paramInt == 0))) {
      return true;
    }
    if (((paramInt & 0x1) != 0) && (isDerivedByRestriction(paramString1, paramString2, this))) {
      return true;
    }
    if (((paramInt & 0x8) != 0) && (isDerivedByList(paramString1, paramString2, this))) {
      return true;
    }
    if (((paramInt & 0x4) != 0) && (isDerivedByUnion(paramString1, paramString2, this))) {
      return true;
    }
    if (((paramInt & 0x2) != 0) && ((paramInt & 0x1) == 0) && ((paramInt & 0x8) == 0) && ((paramInt & 0x4) == 0)) {
      return false;
    }
    if (((paramInt & 0x2) == 0) && ((paramInt & 0x1) == 0) && ((paramInt & 0x8) == 0) && ((paramInt & 0x4) == 0)) {
      return isDerivedByAny(paramString1, paramString2, this);
    }
    return false;
  }
  
  private boolean isDerivedByAny(String paramString1, String paramString2, XSTypeDefinition paramXSTypeDefinition)
  {
    boolean bool = false;
    XSTypeDefinition localXSTypeDefinition = null;
    while ((paramXSTypeDefinition != null) && (paramXSTypeDefinition != localXSTypeDefinition))
    {
      if ((paramString2.equals(paramXSTypeDefinition.getName())) && (((paramString1 == null) && (paramXSTypeDefinition.getNamespace() == null)) || ((paramString1 != null) && (paramString1.equals(paramXSTypeDefinition.getNamespace())))))
      {
        bool = true;
        break;
      }
      if (isDerivedByRestriction(paramString1, paramString2, paramXSTypeDefinition)) {
        return true;
      }
      if (isDerivedByList(paramString1, paramString2, paramXSTypeDefinition)) {
        return true;
      }
      if (isDerivedByUnion(paramString1, paramString2, paramXSTypeDefinition)) {
        return true;
      }
      localXSTypeDefinition = paramXSTypeDefinition;
      if ((((XSSimpleTypeDecl)paramXSTypeDefinition).getVariety() == 0) || (((XSSimpleTypeDecl)paramXSTypeDefinition).getVariety() == 1))
      {
        paramXSTypeDefinition = paramXSTypeDefinition.getBaseType();
      }
      else if (((XSSimpleTypeDecl)paramXSTypeDefinition).getVariety() == 3)
      {
        int i = 0;
        while (i < ((XSSimpleTypeDecl)paramXSTypeDefinition).getMemberTypes().getLength()) {
          return isDerivedByAny(paramString1, paramString2, (XSTypeDefinition)((XSSimpleTypeDecl)paramXSTypeDefinition).getMemberTypes().item(i));
        }
      }
      else if (((XSSimpleTypeDecl)paramXSTypeDefinition).getVariety() == 2)
      {
        paramXSTypeDefinition = ((XSSimpleTypeDecl)paramXSTypeDefinition).getItemType();
      }
    }
    return bool;
  }
  
  private boolean isDerivedByRestriction(String paramString1, String paramString2, XSTypeDefinition paramXSTypeDefinition)
  {
    XSTypeDefinition localXSTypeDefinition = null;
    while ((paramXSTypeDefinition != null) && (paramXSTypeDefinition != localXSTypeDefinition))
    {
      if ((paramString2.equals(paramXSTypeDefinition.getName())) && (((paramString1 != null) && (paramString1.equals(paramXSTypeDefinition.getNamespace()))) || ((paramXSTypeDefinition.getNamespace() == null) && (paramString1 == null)))) {
        return true;
      }
      localXSTypeDefinition = paramXSTypeDefinition;
      paramXSTypeDefinition = paramXSTypeDefinition.getBaseType();
    }
    return false;
  }
  
  private boolean isDerivedByList(String paramString1, String paramString2, XSTypeDefinition paramXSTypeDefinition)
  {
    if ((paramXSTypeDefinition != null) && (((XSSimpleTypeDefinition)paramXSTypeDefinition).getVariety() == 2))
    {
      XSSimpleTypeDefinition localXSSimpleTypeDefinition = ((XSSimpleTypeDefinition)paramXSTypeDefinition).getItemType();
      if ((localXSSimpleTypeDefinition != null) && (isDerivedByRestriction(paramString1, paramString2, localXSSimpleTypeDefinition))) {
        return true;
      }
    }
    return false;
  }
  
  private boolean isDerivedByUnion(String paramString1, String paramString2, XSTypeDefinition paramXSTypeDefinition)
  {
    if ((paramXSTypeDefinition != null) && (((XSSimpleTypeDefinition)paramXSTypeDefinition).getVariety() == 3))
    {
      XSObjectList localXSObjectList = ((XSSimpleTypeDefinition)paramXSTypeDefinition).getMemberTypes();
      for (int i = 0; i < localXSObjectList.getLength(); i++) {
        if ((localXSObjectList.item(i) != null) && (isDerivedByRestriction(paramString1, paramString2, (XSSimpleTypeDefinition)localXSObjectList.item(i)))) {
          return true;
        }
      }
    }
    return false;
  }
  
  public void reset()
  {
    if (fIsImmutable) {
      return;
    }
    fItemType = null;
    fMemberTypes = null;
    fTypeName = null;
    fTargetNamespace = null;
    fFinalSet = 0;
    fBase = null;
    fVariety = -1;
    fValidationDV = -1;
    fFacetsDefined = 0;
    fFixedFacet = 0;
    fWhiteSpace = 0;
    fLength = -1;
    fMinLength = -1;
    fMaxLength = -1;
    fTotalDigits = -1;
    fFractionDigits = -1;
    fPattern = null;
    fPatternStr = null;
    fEnumeration = null;
    fEnumerationType = null;
    fEnumerationItemType = null;
    fLexicalPattern = null;
    fLexicalEnumeration = null;
    fMaxInclusive = null;
    fMaxExclusive = null;
    fMinExclusive = null;
    fMinInclusive = null;
    lengthAnnotation = null;
    minLengthAnnotation = null;
    maxLengthAnnotation = null;
    whiteSpaceAnnotation = null;
    totalDigitsAnnotation = null;
    fractionDigitsAnnotation = null;
    patternAnnotations = null;
    enumerationAnnotations = null;
    maxInclusiveAnnotation = null;
    maxExclusiveAnnotation = null;
    minInclusiveAnnotation = null;
    minExclusiveAnnotation = null;
    fPatternType = 0;
    fAnnotations = null;
    fFacets = null;
  }
  
  public XSNamespaceItem getNamespaceItem()
  {
    return null;
  }
  
  public String toString()
  {
    return fTargetNamespace + "," + fTypeName;
  }
  
  public XSObjectList getFacets()
  {
    if ((fFacets == null) && ((fFacetsDefined != 0) || (fValidationDV == 24)))
    {
      XSFacetImpl[] arrayOfXSFacetImpl = new XSFacetImpl[10];
      int i = 0;
      if ((fFacetsDefined & 0x10) != 0)
      {
        arrayOfXSFacetImpl[i] = new XSFacetImpl(16, WS_FACET_STRING[fWhiteSpace], (fFixedFacet & 0x10) != 0, whiteSpaceAnnotation);
        i++;
      }
      if (fLength != -1)
      {
        arrayOfXSFacetImpl[i] = new XSFacetImpl(1, Integer.toString(fLength), (fFixedFacet & 0x1) != 0, lengthAnnotation);
        i++;
      }
      if (fMinLength != -1)
      {
        arrayOfXSFacetImpl[i] = new XSFacetImpl(2, Integer.toString(fMinLength), (fFixedFacet & 0x2) != 0, minLengthAnnotation);
        i++;
      }
      if (fMaxLength != -1)
      {
        arrayOfXSFacetImpl[i] = new XSFacetImpl(4, Integer.toString(fMaxLength), (fFixedFacet & 0x4) != 0, maxLengthAnnotation);
        i++;
      }
      if (fTotalDigits != -1)
      {
        arrayOfXSFacetImpl[i] = new XSFacetImpl(512, Integer.toString(fTotalDigits), (fFixedFacet & 0x200) != 0, totalDigitsAnnotation);
        i++;
      }
      if (fValidationDV == 24)
      {
        arrayOfXSFacetImpl[i] = new XSFacetImpl(1024, "0", true, null);
        i++;
      }
      if (fFractionDigits != -1)
      {
        arrayOfXSFacetImpl[i] = new XSFacetImpl(1024, Integer.toString(fFractionDigits), (fFixedFacet & 0x400) != 0, fractionDigitsAnnotation);
        i++;
      }
      if (fMaxInclusive != null)
      {
        arrayOfXSFacetImpl[i] = new XSFacetImpl(32, fMaxInclusive.toString(), (fFixedFacet & 0x20) != 0, maxInclusiveAnnotation);
        i++;
      }
      if (fMaxExclusive != null)
      {
        arrayOfXSFacetImpl[i] = new XSFacetImpl(64, fMaxExclusive.toString(), (fFixedFacet & 0x40) != 0, maxExclusiveAnnotation);
        i++;
      }
      if (fMinExclusive != null)
      {
        arrayOfXSFacetImpl[i] = new XSFacetImpl(128, fMinExclusive.toString(), (fFixedFacet & 0x80) != 0, minExclusiveAnnotation);
        i++;
      }
      if (fMinInclusive != null)
      {
        arrayOfXSFacetImpl[i] = new XSFacetImpl(256, fMinInclusive.toString(), (fFixedFacet & 0x100) != 0, minInclusiveAnnotation);
        i++;
      }
      fFacets = new XSObjectListImpl(arrayOfXSFacetImpl, i);
    }
    return fFacets != null ? fFacets : XSObjectListImpl.EMPTY_LIST;
  }
  
  public XSObjectList getMultiValueFacets()
  {
    if ((fMultiValueFacets == null) && (((fFacetsDefined & 0x800) != 0) || ((fFacetsDefined & 0x8) != 0) || (fPatternType != 0) || (fValidationDV == 24)))
    {
      XSMVFacetImpl[] arrayOfXSMVFacetImpl = new XSMVFacetImpl[2];
      int i = 0;
      if (((fFacetsDefined & 0x8) != 0) || (fPatternType != 0) || (fValidationDV == 24))
      {
        arrayOfXSMVFacetImpl[i] = new XSMVFacetImpl(8, getLexicalPattern(), patternAnnotations);
        i++;
      }
      if (fEnumeration != null)
      {
        arrayOfXSMVFacetImpl[i] = new XSMVFacetImpl(2048, getLexicalEnumeration(), enumerationAnnotations);
        i++;
      }
      fMultiValueFacets = new XSObjectListImpl(arrayOfXSMVFacetImpl, i);
    }
    return fMultiValueFacets != null ? fMultiValueFacets : XSObjectListImpl.EMPTY_LIST;
  }
  
  public Object getMinInclusiveValue()
  {
    return fMinInclusive;
  }
  
  public Object getMinExclusiveValue()
  {
    return fMinExclusive;
  }
  
  public Object getMaxInclusiveValue()
  {
    return fMaxInclusive;
  }
  
  public Object getMaxExclusiveValue()
  {
    return fMaxExclusive;
  }
  
  public void setAnonymous(boolean paramBoolean)
  {
    fAnonymous = paramBoolean;
  }
  
  public String getTypeNamespace()
  {
    return getNamespace();
  }
  
  public boolean isDerivedFrom(String paramString1, String paramString2, int paramInt)
  {
    return isDOMDerivedFrom(paramString1, paramString2, paramInt);
  }
  
  private short convertToPrimitiveKind(short paramShort)
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
  
  private static final class XSMVFacetImpl
    implements XSMultiValueFacet
  {
    final short kind;
    XSObjectList annotations;
    StringList values;
    
    public XSMVFacetImpl(short paramShort, StringList paramStringList, XSObjectList paramXSObjectList)
    {
      kind = paramShort;
      values = paramStringList;
      annotations = paramXSObjectList;
    }
    
    public short getFacetKind()
    {
      return kind;
    }
    
    public XSObjectList getAnnotations()
    {
      return annotations;
    }
    
    public StringList getLexicalFacetValues()
    {
      return values;
    }
    
    public String getName()
    {
      return null;
    }
    
    public String getNamespace()
    {
      return null;
    }
    
    public XSNamespaceItem getNamespaceItem()
    {
      return null;
    }
    
    public short getType()
    {
      return 14;
    }
  }
  
  private static final class XSFacetImpl
    implements XSFacet
  {
    final short kind;
    final String value;
    final boolean fixed;
    XSObjectList annotations = null;
    
    public XSFacetImpl(short paramShort, String paramString, boolean paramBoolean, XSAnnotation paramXSAnnotation)
    {
      kind = paramShort;
      value = paramString;
      fixed = paramBoolean;
      if (paramXSAnnotation != null)
      {
        annotations = new XSObjectListImpl();
        ((XSObjectListImpl)annotations).add(paramXSAnnotation);
      }
      else
      {
        annotations = XSObjectListImpl.EMPTY_LIST;
      }
    }
    
    public XSAnnotation getAnnotation()
    {
      return (XSAnnotation)annotations.item(0);
    }
    
    public XSObjectList getAnnotations()
    {
      return annotations;
    }
    
    public short getFacetKind()
    {
      return kind;
    }
    
    public String getLexicalFacetValue()
    {
      return value;
    }
    
    public boolean getFixed()
    {
      return fixed;
    }
    
    public String getName()
    {
      return null;
    }
    
    public String getNamespace()
    {
      return null;
    }
    
    public XSNamespaceItem getNamespaceItem()
    {
      return null;
    }
    
    public short getType()
    {
      return 13;
    }
  }
  
  class ValidationContextImpl
    implements ValidationContext
  {
    ValidationContext fExternal;
    NamespaceContext fNSContext;
    
    ValidationContextImpl(ValidationContext paramValidationContext)
    {
      fExternal = paramValidationContext;
    }
    
    void setNSContext(NamespaceContext paramNamespaceContext)
    {
      fNSContext = paramNamespaceContext;
    }
    
    public boolean needFacetChecking()
    {
      return fExternal.needFacetChecking();
    }
    
    public boolean needExtraChecking()
    {
      return fExternal.needExtraChecking();
    }
    
    public boolean needToNormalize()
    {
      return fExternal.needToNormalize();
    }
    
    public boolean useNamespaces()
    {
      return true;
    }
    
    public boolean isEntityDeclared(String paramString)
    {
      return fExternal.isEntityDeclared(paramString);
    }
    
    public boolean isEntityUnparsed(String paramString)
    {
      return fExternal.isEntityUnparsed(paramString);
    }
    
    public boolean isIdDeclared(String paramString)
    {
      return fExternal.isIdDeclared(paramString);
    }
    
    public void addId(String paramString)
    {
      fExternal.addId(paramString);
    }
    
    public void addIdRef(String paramString)
    {
      fExternal.addIdRef(paramString);
    }
    
    public String getSymbol(String paramString)
    {
      return fExternal.getSymbol(paramString);
    }
    
    public String getURI(String paramString)
    {
      if (fNSContext == null) {
        return fExternal.getURI(paramString);
      }
      return fNSContext.getURI(paramString);
    }
  }
}
