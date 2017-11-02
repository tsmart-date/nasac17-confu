package javax.xml.validation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Enumeration;

class SecuritySupport
{
  SecuritySupport() {}
  
  ClassLoader getContextClassLoader()
  {
    (ClassLoader)AccessController.doPrivileged(new PrivilegedAction()
    {
      public Object run()
      {
        ClassLoader localClassLoader = null;
        try
        {
          localClassLoader = Thread.currentThread().getContextClassLoader();
        }
        catch (SecurityException localSecurityException) {}
        return localClassLoader;
      }
    });
  }
  
  String getSystemProperty(String paramString)
  {
    (String)AccessController.doPrivileged(new PrivilegedAction()
    {
      private final String val$propName;
      
      public Object run()
      {
        return System.getProperty(val$propName);
      }
    });
  }
  
  FileInputStream getFileInputStream(File paramFile)
    throws FileNotFoundException
  {
    try
    {
      (FileInputStream)AccessController.doPrivileged(new PrivilegedExceptionAction()
      {
        private final File val$file;
        
        public Object run()
          throws FileNotFoundException
        {
          return new FileInputStream(val$file);
        }
      });
    }
    catch (PrivilegedActionException localPrivilegedActionException)
    {
      throw ((FileNotFoundException)localPrivilegedActionException.getException());
    }
  }
  
  InputStream getURLInputStream(URL paramURL)
    throws IOException
  {
    try
    {
      (InputStream)AccessController.doPrivileged(new PrivilegedExceptionAction()
      {
        private final URL val$url;
        
        public Object run()
          throws IOException
        {
          return val$url.openStream();
        }
      });
    }
    catch (PrivilegedActionException localPrivilegedActionException)
    {
      throw ((IOException)localPrivilegedActionException.getException());
    }
  }
  
  URL getResourceAsURL(ClassLoader paramClassLoader, String paramString)
  {
    (URL)AccessController.doPrivileged(new PrivilegedAction()
    {
      private final ClassLoader val$cl;
      private final String val$name;
      
      public Object run()
      {
        URL localURL;
        if (val$cl == null) {
          localURL = ClassLoader.getSystemResource(val$name);
        } else {
          localURL = ClassLoader.getSystemResource(val$name);
        }
        return localURL;
      }
    });
  }
  
  Enumeration getResources(ClassLoader paramClassLoader, String paramString)
    throws IOException
  {
    try
    {
      (Enumeration)AccessController.doPrivileged(new PrivilegedExceptionAction()
      {
        private final ClassLoader val$cl;
        private final String val$name;
        
        public Object run()
          throws IOException
        {
          Enumeration localEnumeration;
          if (val$cl == null) {
            localEnumeration = ClassLoader.getSystemResources(val$name);
          } else {
            localEnumeration = ClassLoader.getSystemResources(val$name);
          }
          return localEnumeration;
        }
      });
    }
    catch (PrivilegedActionException localPrivilegedActionException)
    {
      throw ((IOException)localPrivilegedActionException.getException());
    }
  }
  
  InputStream getResourceAsStream(ClassLoader paramClassLoader, String paramString)
  {
    (InputStream)AccessController.doPrivileged(new PrivilegedAction()
    {
      private final ClassLoader val$cl;
      private final String val$name;
      
      public Object run()
      {
        InputStream localInputStream;
        if (val$cl == null) {
          localInputStream = ClassLoader.getSystemResourceAsStream(val$name);
        } else {
          localInputStream = val$cl.getResourceAsStream(val$name);
        }
        return localInputStream;
      }
    });
  }
  
  boolean doesFileExist(File paramFile)
  {
    ((Boolean)AccessController.doPrivileged(new PrivilegedAction()
    {
      private final File val$f;
      
      public Object run()
      {
        return new Boolean(val$f.exists());
      }
    })).booleanValue();
  }
}
