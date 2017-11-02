package org.apache.xerces.impl.xs.identity;

public abstract interface FieldActivator
{
  public abstract void startValueScopeFor(IdentityConstraint paramIdentityConstraint, int paramInt);
  
  public abstract XPathMatcher activateField(Field paramField, int paramInt);
  
  public abstract void setMayMatch(Field paramField, Boolean paramBoolean);
  
  public abstract Boolean mayMatch(Field paramField);
  
  public abstract void endValueScopeFor(IdentityConstraint paramIdentityConstraint, int paramInt);
}
