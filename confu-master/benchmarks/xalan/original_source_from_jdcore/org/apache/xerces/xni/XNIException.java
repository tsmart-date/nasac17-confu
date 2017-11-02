package org.apache.xerces.xni;

public class XNIException
  extends RuntimeException
{
  static final long serialVersionUID = 9019819772686063775L;
  private Exception fException;
  
  public XNIException(String paramString)
  {
    super(paramString);
  }
  
  public XNIException(Exception paramException)
  {
    super(paramException.getMessage());
    fException = paramException;
  }
  
  public XNIException(String paramString, Exception paramException)
  {
    super(paramString);
    fException = paramException;
  }
  
  public Exception getException()
  {
    return fException;
  }
}
