package org.apache.xalan.extensions;

import java.util.Hashtable;





























public abstract class ExtensionHandlerJava
  extends ExtensionHandler
{
  protected String m_className = "";
  

  private Hashtable m_cachedMethods = new Hashtable();
  
















  protected ExtensionHandlerJava(String namespaceUri, String scriptLang, String className)
  {
    super(namespaceUri, scriptLang);
    
    m_className = className;
  }
  













  public Object getFromCache(Object methodKey, Object objType, Object[] methodArgs)
  {
    return m_cachedMethods.get(methodKey);
  }
  














  public Object putToCache(Object methodKey, Object objType, Object[] methodArgs, Object methodObj)
  {
    return m_cachedMethods.put(methodKey, methodObj);
  }
}