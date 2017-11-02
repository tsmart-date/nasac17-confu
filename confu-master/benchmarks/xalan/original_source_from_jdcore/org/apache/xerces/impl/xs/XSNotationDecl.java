package org.apache.xerces.impl.xs;

import org.apache.xerces.xs.XSAnnotation;
import org.apache.xerces.xs.XSNamespaceItem;
import org.apache.xerces.xs.XSNotationDeclaration;
import org.apache.xerces.xs.XSObjectList;

public class XSNotationDecl
  implements XSNotationDeclaration
{
  public String fName = null;
  public String fTargetNamespace = null;
  public String fPublicId = null;
  public String fSystemId = null;
  public XSObjectList fAnnotations = null;
  
  public XSNotationDecl() {}
  
  public short getType()
  {
    return 11;
  }
  
  public String getName()
  {
    return fName;
  }
  
  public String getNamespace()
  {
    return fTargetNamespace;
  }
  
  public String getSystemId()
  {
    return fSystemId;
  }
  
  public String getPublicId()
  {
    return fPublicId;
  }
  
  public XSAnnotation getAnnotation()
  {
    return (XSAnnotation)fAnnotations.item(0);
  }
  
  public XSObjectList getAnnotations()
  {
    return fAnnotations;
  }
  
  public XSNamespaceItem getNamespaceItem()
  {
    return null;
  }
}
