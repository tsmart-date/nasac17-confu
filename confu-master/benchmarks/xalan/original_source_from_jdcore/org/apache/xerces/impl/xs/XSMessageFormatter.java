package org.apache.xerces.impl.xs;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.apache.xerces.util.MessageFormatter;

public class XSMessageFormatter
  implements MessageFormatter
{
  public static final String SCHEMA_DOMAIN = "http://www.w3.org/TR/xml-schema-1";
  private Locale fLocale = null;
  private ResourceBundle fResourceBundle = null;
  
  public XSMessageFormatter() {}
  
  public String formatMessage(Locale paramLocale, String paramString, Object[] paramArrayOfObject)
    throws MissingResourceException
  {
    if ((fResourceBundle == null) || (paramLocale != fLocale))
    {
      if (paramLocale != null)
      {
        fResourceBundle = ResourceBundle.getBundle("org.apache.xerces.impl.msg.XMLSchemaMessages", paramLocale);
        fLocale = paramLocale;
      }
      if (fResourceBundle == null) {
        fResourceBundle = ResourceBundle.getBundle("org.apache.xerces.impl.msg.XMLSchemaMessages");
      }
    }
    String str = fResourceBundle.getString(paramString);
    if (paramArrayOfObject != null) {
      try
      {
        str = MessageFormat.format(str, paramArrayOfObject);
      }
      catch (Exception localException)
      {
        str = fResourceBundle.getString("FormatFailed");
        str = str + " " + fResourceBundle.getString(paramString);
      }
    }
    if (str == null)
    {
      str = fResourceBundle.getString("BadMessageKey");
      throw new MissingResourceException(str, "org.apache.xerces.impl.msg.SchemaMessages", paramString);
    }
    return str;
  }
}
