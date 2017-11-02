package javax.xml.transform.stream;

import java.io.File;
import java.io.OutputStream;
import java.io.Writer;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.transform.Result;

public class StreamResult
  implements Result
{
  public static final String FEATURE = "http://javax.xml.transform.stream.StreamResult/feature";
  private String systemId;
  private OutputStream outputStream;
  private Writer writer;
  
  public StreamResult() {}
  
  public StreamResult(OutputStream paramOutputStream)
  {
    setOutputStream(paramOutputStream);
  }
  
  public StreamResult(Writer paramWriter)
  {
    setWriter(paramWriter);
  }
  
  public StreamResult(String paramString)
  {
    systemId = paramString;
  }
  
  public StreamResult(File paramFile)
  {
    setSystemId(paramFile);
  }
  
  public void setOutputStream(OutputStream paramOutputStream)
  {
    outputStream = paramOutputStream;
  }
  
  public OutputStream getOutputStream()
  {
    return outputStream;
  }
  
  public void setWriter(Writer paramWriter)
  {
    writer = paramWriter;
  }
  
  public Writer getWriter()
  {
    return writer;
  }
  
  public void setSystemId(String paramString)
  {
    systemId = paramString;
  }
  
  public void setSystemId(File paramFile)
  {
    try
    {
      Method localMethod1 = paramFile.getClass().getMethod("toURI", (Class[])null);
      Object localObject = localMethod1.invoke(paramFile, (Object[])null);
      Method localMethod2 = localObject.getClass().getMethod("toString", (Class[])null);
      systemId = ((String)localMethod2.invoke(localObject, (Object[])null));
    }
    catch (Exception localException)
    {
      try
      {
        systemId = paramFile.toURL().toString();
      }
      catch (MalformedURLException localMalformedURLException)
      {
        systemId = null;
        throw new RuntimeException("javax.xml.transform.stream.StreamResult#setSystemId(File f) with MalformedURLException: " + localMalformedURLException.toString());
      }
    }
  }
  
  public String getSystemId()
  {
    return systemId;
  }
}
