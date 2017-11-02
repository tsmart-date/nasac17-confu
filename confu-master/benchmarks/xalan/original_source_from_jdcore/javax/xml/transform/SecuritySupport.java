package javax.xml.transform;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

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
