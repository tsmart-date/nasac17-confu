package javax.xml.datatype;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.GregorianCalendar;

public abstract class DatatypeFactory
{
  public static final String DATATYPEFACTORY_PROPERTY = "javax.xml.datatype.DatatypeFactory";
  public static final String DATATYPEFACTORY_IMPLEMENTATION_CLASS = "org.apache.xerces.jaxp.datatype.DatatypeFactoryImpl";
  
  protected DatatypeFactory() {}
  
  public static DatatypeFactory newInstance()
    throws DatatypeConfigurationException
  {
    try
    {
      return (DatatypeFactory)FactoryFinder.find("javax.xml.datatype.DatatypeFactory", "org.apache.xerces.jaxp.datatype.DatatypeFactoryImpl");
    }
    catch (FactoryFinder.ConfigurationError localConfigurationError)
    {
      throw new DatatypeConfigurationException(localConfigurationError.getMessage(), localConfigurationError.getException());
    }
  }
  
  public abstract Duration newDuration(String paramString);
  
  public abstract Duration newDuration(long paramLong);
  
  public abstract Duration newDuration(boolean paramBoolean, BigInteger paramBigInteger1, BigInteger paramBigInteger2, BigInteger paramBigInteger3, BigInteger paramBigInteger4, BigInteger paramBigInteger5, BigDecimal paramBigDecimal);
  
  public Duration newDuration(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    BigInteger localBigInteger1 = paramInt1 != Integer.MIN_VALUE ? BigInteger.valueOf(paramInt1) : null;
    BigInteger localBigInteger2 = paramInt2 != Integer.MIN_VALUE ? BigInteger.valueOf(paramInt2) : null;
    BigInteger localBigInteger3 = paramInt3 != Integer.MIN_VALUE ? BigInteger.valueOf(paramInt3) : null;
    BigInteger localBigInteger4 = paramInt4 != Integer.MIN_VALUE ? BigInteger.valueOf(paramInt4) : null;
    BigInteger localBigInteger5 = paramInt5 != Integer.MIN_VALUE ? BigInteger.valueOf(paramInt5) : null;
    BigDecimal localBigDecimal = paramInt6 != Integer.MIN_VALUE ? BigDecimal.valueOf(paramInt6) : null;
    return newDuration(paramBoolean, localBigInteger1, localBigInteger2, localBigInteger3, localBigInteger4, localBigInteger5, localBigDecimal);
  }
  
  public Duration newDurationDayTime(String paramString)
  {
    return newDuration(paramString);
  }
  
  public Duration newDurationDayTime(long paramLong)
  {
    return newDuration(paramLong);
  }
  
  public Duration newDurationDayTime(boolean paramBoolean, BigInteger paramBigInteger1, BigInteger paramBigInteger2, BigInteger paramBigInteger3, BigInteger paramBigInteger4)
  {
    return newDuration(paramBoolean, null, null, paramBigInteger1, paramBigInteger2, paramBigInteger3, paramBigInteger4 != null ? new BigDecimal(paramBigInteger4) : null);
  }
  
  public Duration newDurationDayTime(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return newDurationDayTime(paramBoolean, BigInteger.valueOf(paramInt1), BigInteger.valueOf(paramInt2), BigInteger.valueOf(paramInt3), BigInteger.valueOf(paramInt4));
  }
  
  public Duration newDurationYearMonth(String paramString)
  {
    return newDuration(paramString);
  }
  
  public Duration newDurationYearMonth(long paramLong)
  {
    return newDuration(paramLong);
  }
  
  public Duration newDurationYearMonth(boolean paramBoolean, BigInteger paramBigInteger1, BigInteger paramBigInteger2)
  {
    return newDuration(paramBoolean, paramBigInteger1, paramBigInteger2, null, null, null, null);
  }
  
  public Duration newDurationYearMonth(boolean paramBoolean, int paramInt1, int paramInt2)
  {
    return newDurationYearMonth(paramBoolean, BigInteger.valueOf(paramInt1), BigInteger.valueOf(paramInt2));
  }
  
  public abstract XMLGregorianCalendar newXMLGregorianCalendar();
  
  public abstract XMLGregorianCalendar newXMLGregorianCalendar(String paramString);
  
  public abstract XMLGregorianCalendar newXMLGregorianCalendar(GregorianCalendar paramGregorianCalendar);
  
  public abstract XMLGregorianCalendar newXMLGregorianCalendar(BigInteger paramBigInteger, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, BigDecimal paramBigDecimal, int paramInt6);
  
  public XMLGregorianCalendar newXMLGregorianCalendar(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8)
  {
    BigInteger localBigInteger = paramInt1 != Integer.MIN_VALUE ? BigInteger.valueOf(paramInt1) : null;
    BigDecimal localBigDecimal = null;
    if (paramInt7 != Integer.MIN_VALUE)
    {
      if ((paramInt7 < 0) || (paramInt7 > 1000)) {
        throw new IllegalArgumentException("javax.xml.datatype.DatatypeFactory#newXMLGregorianCalendar(int year, int month, int day, int hour, int minute, int second, int millisecond, int timezone)with invalid millisecond: " + paramInt7);
      }
      localBigDecimal = BigDecimal.valueOf(paramInt7).movePointLeft(3);
    }
    return newXMLGregorianCalendar(localBigInteger, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, localBigDecimal, paramInt8);
  }
  
  public XMLGregorianCalendar newXMLGregorianCalendarDate(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return newXMLGregorianCalendar(paramInt1, paramInt2, paramInt3, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, paramInt4);
  }
  
  public XMLGregorianCalendar newXMLGregorianCalendarTime(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return newXMLGregorianCalendar(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, paramInt1, paramInt2, paramInt3, Integer.MIN_VALUE, paramInt4);
  }
  
  public XMLGregorianCalendar newXMLGregorianCalendarTime(int paramInt1, int paramInt2, int paramInt3, BigDecimal paramBigDecimal, int paramInt4)
  {
    return newXMLGregorianCalendar(null, Integer.MIN_VALUE, Integer.MIN_VALUE, paramInt1, paramInt2, paramInt3, paramBigDecimal, paramInt4);
  }
  
  public XMLGregorianCalendar newXMLGregorianCalendarTime(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    BigDecimal localBigDecimal = null;
    if (paramInt4 != Integer.MIN_VALUE)
    {
      if ((paramInt4 < 0) || (paramInt4 > 1000)) {
        throw new IllegalArgumentException("javax.xml.datatype.DatatypeFactory#newXMLGregorianCalendarTime(int hours, int minutes, int seconds, int milliseconds, int timezone)with invalid milliseconds: " + paramInt4);
      }
      localBigDecimal = BigDecimal.valueOf(paramInt4).movePointLeft(3);
    }
    return newXMLGregorianCalendarTime(paramInt1, paramInt2, paramInt3, localBigDecimal, paramInt5);
  }
}
