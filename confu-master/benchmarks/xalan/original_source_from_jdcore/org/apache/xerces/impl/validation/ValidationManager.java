package org.apache.xerces.impl.validation;

import java.util.Vector;

public class ValidationManager
{
  protected final Vector fVSs = new Vector();
  protected boolean fGrammarFound = false;
  protected boolean fCachedDTD = false;
  
  public ValidationManager() {}
  
  public final void addValidationState(ValidationState paramValidationState)
  {
    fVSs.addElement(paramValidationState);
  }
  
  public final void setEntityState(EntityState paramEntityState)
  {
    for (int i = fVSs.size() - 1; i >= 0; i--) {
      ((ValidationState)fVSs.elementAt(i)).setEntityState(paramEntityState);
    }
  }
  
  public final void setGrammarFound(boolean paramBoolean)
  {
    fGrammarFound = paramBoolean;
  }
  
  public final boolean isGrammarFound()
  {
    return fGrammarFound;
  }
  
  public final void setCachedDTD(boolean paramBoolean)
  {
    fCachedDTD = paramBoolean;
  }
  
  public final boolean isCachedDTD()
  {
    return fCachedDTD;
  }
  
  public final void reset()
  {
    fVSs.removeAllElements();
    fGrammarFound = false;
    fCachedDTD = false;
  }
}
