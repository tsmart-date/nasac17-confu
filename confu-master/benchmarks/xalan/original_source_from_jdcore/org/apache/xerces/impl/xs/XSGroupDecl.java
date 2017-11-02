package org.apache.xerces.impl.xs;

import org.apache.xerces.xs.XSAnnotation;
import org.apache.xerces.xs.XSModelGroup;
import org.apache.xerces.xs.XSModelGroupDefinition;
import org.apache.xerces.xs.XSNamespaceItem;
import org.apache.xerces.xs.XSObjectList;

public class XSGroupDecl
  implements XSModelGroupDefinition
{
  public String fName = null;
  public String fTargetNamespace = null;
  public XSModelGroupImpl fModelGroup = null;
  public XSObjectList fAnnotations = null;
  
  public XSGroupDecl() {}
  
  public short getType()
  {
    return 6;
  }
  
  public String getName()
  {
    return fName;
  }
  
  public String getNamespace()
  {
    return fTargetNamespace;
  }
  
  public XSModelGroup getModelGroup()
  {
    return fModelGroup;
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
