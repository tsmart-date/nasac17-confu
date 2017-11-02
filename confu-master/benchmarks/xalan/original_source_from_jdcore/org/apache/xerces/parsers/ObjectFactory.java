package org.apache.xerces.parsers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

final class ObjectFactory
{
  private static final String DEFAULT_PROPERTIES_FILENAME = "xerces.properties";
  private static final boolean DEBUG = false;
  private static final int DEFAULT_LINE_LENGTH = 80;
  private static Properties fXercesProperties = null;
  private static long fLastModified = -1L;
  
  ObjectFactory() {}
  
  static Object createObject(String paramString1, String paramString2)
    throws ObjectFactory.ConfigurationError
  {
    return createObject(paramString1, null, paramString2);
  }
  
  static Object createObject(String paramString1, String paramString2, String paramString3)
    throws ObjectFactory.ConfigurationError
  {
    SecuritySupport localSecuritySupport = SecuritySupport.getInstance();
    ClassLoader localClassLoader = findClassLoader();
    try
    {
      String str1 = localSecuritySupport.getSystemProperty(paramString1);
      if (str1 != null) {
        return newInstance(str1, localClassLoader, true);
      }
    }
    catch (SecurityException localSecurityException1) {}
    String str2 = null;
    if (paramString2 == null)
    {
      localObject1 = null;
      boolean bool = false;
      try
      {
        String str3 = localSecuritySupport.getSystemProperty("java.home");
        paramString2 = str3 + File.separator + "lib" + File.separator + "xerces.properties";
        localObject1 = new File(paramString2);
        bool = localSecuritySupport.getFileExists((File)localObject1);
      }
      catch (SecurityException localSecurityException2)
      {
        fLastModified = -1L;
        fXercesProperties = null;
      }
      synchronized (ObjectFactory.class)
      {
        int i = 0;
        FileInputStream localFileInputStream = null;
        try
        {
          if (fLastModified >= 0L)
          {
            if ((bool) && (fLastModified < (ObjectFactory.fLastModified = localSecuritySupport.getLastModified((File)localObject1))))
            {
              i = 1;
            }
            else if (!bool)
            {
              fLastModified = -1L;
              fXercesProperties = null;
            }
          }
          else if (bool)
          {
            i = 1;
            fLastModified = localSecuritySupport.getLastModified((File)localObject1);
          }
          if (i != 0)
          {
            fXercesProperties = new Properties();
            localFileInputStream = localSecuritySupport.getFileInputStream((File)localObject1);
            fXercesProperties.load(localFileInputStream);
          }
        }
        catch (Exception localException2)
        {
          fXercesProperties = null;
          fLastModified = -1L;
        }
        finally
        {
          if (localFileInputStream != null) {
            try
            {
              localFileInputStream.close();
            }
            catch (IOException localIOException1) {}
          }
        }
      }
      if (fXercesProperties != null) {
        str2 = fXercesProperties.getProperty(paramString1);
      }
    }
    else
    {
      localObject1 = null;
      try
      {
        localObject1 = localSecuritySupport.getFileInputStream(new File(paramString2));
        Properties localProperties = new Properties();
        localProperties.load((InputStream)localObject1);
        str2 = localProperties.getProperty(paramString1);
      }
      catch (Exception localException1) {}finally
      {
        if (localObject1 != null) {
          try
          {
            ((FileInputStream)localObject1).close();
          }
          catch (IOException localIOException2) {}
        }
      }
    }
    if (str2 != null) {
      return newInstance(str2, localClassLoader, true);
    }
    Object localObject1 = findJarServiceProvider(paramString1);
    if (localObject1 != null) {
      return localObject1;
    }
    if (paramString3 == null) {
      throw new ConfigurationError("Provider for " + paramString1 + " cannot be found", null);
    }
    return newInstance(paramString3, localClassLoader, true);
  }
  
  private static void debugPrintln(String paramString) {}
  
  static ClassLoader findClassLoader()
    throws ObjectFactory.ConfigurationError
  {
    SecuritySupport localSecuritySupport = SecuritySupport.getInstance();
    ClassLoader localClassLoader1 = localSecuritySupport.getContextClassLoader();
    ClassLoader localClassLoader2 = localSecuritySupport.getSystemClassLoader();
    ClassLoader localClassLoader3 = localClassLoader2;
    for (;;)
    {
      if (localClassLoader1 == localClassLoader3)
      {
        ClassLoader localClassLoader4 = ObjectFactory.class.getClassLoader();
        localClassLoader3 = localClassLoader2;
        for (;;)
        {
          if (localClassLoader4 == localClassLoader3) {
            return localClassLoader2;
          }
          if (localClassLoader3 == null) {
            break;
          }
          localClassLoader3 = localSecuritySupport.getParentClassLoader(localClassLoader3);
        }
        return localClassLoader4;
      }
      if (localClassLoader3 == null) {
        break;
      }
      localClassLoader3 = localSecuritySupport.getParentClassLoader(localClassLoader3);
    }
    return localClassLoader1;
  }
  
  static Object newInstance(String paramString, ClassLoader paramClassLoader, boolean paramBoolean)
    throws ObjectFactory.ConfigurationError
  {
    try
    {
      Class localClass = findProviderClass(paramString, paramClassLoader, paramBoolean);
      Object localObject = localClass.newInstance();
      return localObject;
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
  
  static Class findProviderClass(String paramString, ClassLoader paramClassLoader, boolean paramBoolean)
    throws ClassNotFoundException, ObjectFactory.ConfigurationError
  {
    SecurityManager localSecurityManager = System.getSecurityManager();
    if (localSecurityManager != null)
    {
      int i = paramString.lastIndexOf(".");
      String str = paramString;
      if (i != -1) {
        str = paramString.substring(0, i);
      }
      localSecurityManager.checkPackageAccess(str);
    }
    Class localClass;
    if (paramClassLoader == null) {
      localClass = Class.forName(paramString);
    } else {
      try
      {
        localClass = paramClassLoader.loadClass(paramString);
      }
      catch (ClassNotFoundException localClassNotFoundException)
      {
        if (paramBoolean)
        {
          ClassLoader localClassLoader = ObjectFactory.class.getClassLoader();
          if (localClassLoader == null)
          {
            localClass = Class.forName(paramString);
          }
          else if (paramClassLoader != localClassLoader)
          {
            paramClassLoader = localClassLoader;
            localClass = paramClassLoader.loadClass(paramString);
          }
          else
          {
            throw localClassNotFoundException;
          }
        }
        else
        {
          throw localClassNotFoundException;
        }
      }
    }
    return localClass;
  }
  
  private static Object findJarServiceProvider(String paramString)
    throws ObjectFactory.ConfigurationError
  {
    SecuritySupport localSecuritySupport = SecuritySupport.getInstance();
    String str1 = "META-INF/services/" + paramString;
    InputStream localInputStream = null;
    Object localObject1 = findClassLoader();
    localInputStream = localSecuritySupport.getResourceAsStream((ClassLoader)localObject1, str1);
    Object localObject2;
    if (localInputStream == null)
    {
      localObject2 = ObjectFactory.class.getClassLoader();
      if (localObject1 != localObject2)
      {
        localObject1 = localObject2;
        localInputStream = localSecuritySupport.getResourceAsStream((ClassLoader)localObject1, str1);
      }
    }
    if (localInputStream == null) {
      return null;
    }
    try
    {
      localObject2 = new BufferedReader(new InputStreamReader(localInputStream, "UTF-8"), 80);
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      localObject2 = new BufferedReader(new InputStreamReader(localInputStream), 80);
    }
    String str2 = null;
    try
    {
      str2 = ((BufferedReader)localObject2).readLine();
    }
    catch (IOException localIOException1)
    {
      Object localObject3 = null;
      return localObject3;
    }
    finally
    {
      try
      {
        ((BufferedReader)localObject2).close();
      }
      catch (IOException localIOException2) {}
    }
    if ((str2 != null) && (!"".equals(str2))) {
      return newInstance(str2, (ClassLoader)localObject1, false);
    }
    return null;
  }
  
  static final class ConfigurationError
    extends Error
  {
    static final long serialVersionUID = -7285495612271660427L;
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
