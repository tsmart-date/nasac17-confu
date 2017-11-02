package javax.xml.datatype;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Properties;

class FactoryFinder
{
  private static final String CLASS_NAME = "javax.xml.datatype.FactoryFinder";
  private static boolean debug = false;
  private static Properties cacheProps = new Properties();
  private static boolean firstTime = true;
  private static SecuritySupport ss = new SecuritySupport();
  private static final int DEFAULT_LINE_LENGTH = 80;
  
  FactoryFinder() {}
  
  private static void debugPrintln(String paramString)
  {
    if (debug) {
      System.err.println("javax.xml.datatype.FactoryFinder:" + paramString);
    }
  }
  
  private static ClassLoader findClassLoader()
    throws FactoryFinder.ConfigurationError
  {
    ClassLoader localClassLoader = ss.getContextClassLoader();
    debugPrintln("Using context class loader: " + localClassLoader);
    if (localClassLoader == null)
    {
      localClassLoader = FactoryFinder.class.getClassLoader();
      debugPrintln("Using the class loader of FactoryFinder: " + localClassLoader);
    }
    return localClassLoader;
  }
  
  private static Object newInstance(String paramString, ClassLoader paramClassLoader)
    throws FactoryFinder.ConfigurationError
  {
    try
    {
      Class localClass;
      if (paramClassLoader == null) {
        localClass = Class.forName(paramString);
      } else {
        localClass = paramClassLoader.loadClass(paramString);
      }
      if (debug) {
        debugPrintln("Loaded " + paramString + " from " + which(localClass));
      }
      return localClass.newInstance();
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      throw new ConfigurationError("Provider " + paramString + " not found", localClassNotFoundException);
    }
    catch (Exception localException)
    {
      throw new ConfigurationError("Provider " + paramString + " could not be instantiated: " + localException, localException);
    }
  }
  
  static Object find(String paramString1, String paramString2)
    throws FactoryFinder.ConfigurationError
  {
    ClassLoader localClassLoader = findClassLoader();
    try
    {
      String str1 = ss.getSystemProperty(paramString1);
      if (str1 != null)
      {
        debugPrintln("found " + str1 + " in the system property " + paramString1);
        return newInstance(str1, localClassLoader);
      }
    }
    catch (SecurityException localSecurityException) {}
    try
    {
      String str2 = ss.getSystemProperty("java.home");
      String str3 = str2 + File.separator + "lib" + File.separator + "jaxp.properties";
      String str4 = null;
      if (firstTime) {
        synchronized (cacheProps)
        {
          if (firstTime)
          {
            File localFile = new File(str3);
            firstTime = false;
            if (ss.doesFileExist(localFile))
            {
              debugPrintln("Read properties file " + localFile);
              cacheProps.load(ss.getFileInputStream(localFile));
            }
          }
        }
      }
      str4 = cacheProps.getProperty(paramString1);
      debugPrintln("found " + str4 + " in $java.home/jaxp.properties");
      if (str4 != null) {
        return newInstance(str4, localClassLoader);
      }
    }
    catch (Exception localException)
    {
      if (debug) {
        localException.printStackTrace();
      }
    }
    Object localObject1 = findJarServiceProvider(paramString1);
    if (localObject1 != null) {
      return localObject1;
    }
    if (paramString2 == null) {
      throw new ConfigurationError("Provider for " + paramString1 + " cannot be found", null);
    }
    debugPrintln("loaded from fallback value: " + paramString2);
    return newInstance(paramString2, localClassLoader);
  }
  
  private static Object findJarServiceProvider(String paramString)
    throws FactoryFinder.ConfigurationError
  {
    String str1 = "META-INF/services/" + paramString;
    InputStream localInputStream = null;
    ClassLoader localClassLoader = ss.getContextClassLoader();
    if (localClassLoader != null)
    {
      localInputStream = ss.getResourceAsStream(localClassLoader, str1);
      if (localInputStream == null)
      {
        localClassLoader = FactoryFinder.class.getClassLoader();
        localInputStream = ss.getResourceAsStream(localClassLoader, str1);
      }
    }
    else
    {
      localClassLoader = FactoryFinder.class.getClassLoader();
      localInputStream = ss.getResourceAsStream(localClassLoader, str1);
    }
    if (localInputStream == null) {
      return null;
    }
    debugPrintln("found jar resource=" + str1 + " using ClassLoader: " + localClassLoader);
    BufferedReader localBufferedReader;
    try
    {
      localBufferedReader = new BufferedReader(new InputStreamReader(localInputStream, "UTF-8"), 80);
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      localBufferedReader = new BufferedReader(new InputStreamReader(localInputStream), 80);
    }
    String str2 = null;
    try
    {
      str2 = localBufferedReader.readLine();
    }
    catch (IOException localIOException1)
    {
      Object localObject1 = null;
      return localObject1;
    }
    finally
    {
      try
      {
        localBufferedReader.close();
      }
      catch (IOException localIOException2) {}
    }
    if ((str2 != null) && (!"".equals(str2)))
    {
      debugPrintln("found in resource, value=" + str2);
      return newInstance(str2, localClassLoader);
    }
    return null;
  }
  
  private static String which(Class paramClass)
  {
    try
    {
      String str = paramClass.getName().replace('.', '/') + ".class";
      ClassLoader localClassLoader = paramClass.getClassLoader();
      URL localURL;
      if (localClassLoader != null) {
        localURL = localClassLoader.getResource(str);
      } else {
        localURL = ClassLoader.getSystemResource(str);
      }
      if (localURL != null) {
        return localURL.toString();
      }
    }
    catch (Throwable localThrowable)
    {
      if (debug) {
        localThrowable.printStackTrace();
      }
    }
    return "unknown location";
  }
  
  static
  {
    try
    {
      debug = ss.getSystemProperty("jaxp.debug") != null;
    }
    catch (Exception localException) {}
  }
  
  static class ConfigurationError
    extends Error
  {
    private static final long serialVersionUID = -3644413026244211347L;
    private Exception exception;
    
    ConfigurationError(String paramString, Exception paramException)
    {
      super();
      exception = paramException;
    }
    
    Exception getException()
    {
      return exception;
    }
  }
}
