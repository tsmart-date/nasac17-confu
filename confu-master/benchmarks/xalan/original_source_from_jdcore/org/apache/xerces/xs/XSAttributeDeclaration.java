package org.apache.xerces.xs;

public abstract interface XSAttributeDeclaration
  extends XSObject
{
  public abstract XSSimpleTypeDefinition getTypeDefinition();
  
  public abstract short getScope();
  
  public abstract XSComplexTypeDefinition getEnclosingCTDefinition();
  
  public abstract short getConstraintType();
  
  public abstract String getConstraintValue();
  
  public abstract Object getActualVC()
    throws XSException;
  
  public abstract short getActualVCType()
    throws XSException;
  
  public abstract ShortList getItemValueTypes()
    throws XSException;
  
  public abstract XSAnnotation getAnnotation();
  
  public abstract XSObjectList getAnnotations();
}
