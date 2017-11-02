package org.apache.xerces.xs;

public abstract interface XSMultiValueFacet
  extends XSObject
{
  public abstract short getFacetKind();
  
  public abstract StringList getLexicalFacetValues();
  
  public abstract XSObjectList getAnnotations();
}
