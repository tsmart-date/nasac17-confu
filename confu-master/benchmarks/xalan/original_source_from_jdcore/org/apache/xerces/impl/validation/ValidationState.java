package org.apache.xerces.impl.validation;

import java.util.Enumeration;
import java.util.Hashtable;
import org.apache.xerces.impl.dv.ValidationContext;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.xni.NamespaceContext;

public class ValidationState
  implements ValidationContext
{
  private boolean fExtraChecking = true;
  private boolean fFacetChecking = true;
  private boolean fNormalize = true;
  private boolean fNamespaces = true;
  private EntityState fEntityState = null;
  private NamespaceContext fNamespaceContext = null;
  private SymbolTable fSymbolTable = null;
  private final Hashtable fIdTable = new Hashtable();
  private final Hashtable fIdRefTable = new Hashtable();
  private static final Object fNullValue = new Object();
  
  public ValidationState() {}
  
  public void setExtraChecking(boolean paramBoolean)
  {
    fExtraChecking = paramBoolean;
  }
  
  public void setFacetChecking(boolean paramBoolean)
  {
    fFacetChecking = paramBoolean;
  }
  
  public void setNormalizationRequired(boolean paramBoolean)
  {
    fNormalize = paramBoolean;
  }
  
  public void setUsingNamespaces(boolean paramBoolean)
  {
    fNamespaces = paramBoolean;
  }
  
  public void setEntityState(EntityState paramEntityState)
  {
    fEntityState = paramEntityState;
  }
  
  public void setNamespaceSupport(NamespaceContext paramNamespaceContext)
  {
    fNamespaceContext = paramNamespaceContext;
  }
  
  public void setSymbolTable(SymbolTable paramSymbolTable)
  {
    fSymbolTable = paramSymbolTable;
  }
  
  public String checkIDRefID()
  {
    Enumeration localEnumeration = fIdRefTable.keys();
    while (localEnumeration.hasMoreElements())
    {
      String str = (String)localEnumeration.nextElement();
      if (!fIdTable.containsKey(str)) {
        return str;
      }
    }
    return null;
  }
  
  public void reset()
  {
    fExtraChecking = true;
    fFacetChecking = true;
    fNamespaces = true;
    fIdTable.clear();
    fIdRefTable.clear();
    fEntityState = null;
    fNamespaceContext = null;
    fSymbolTable = null;
  }
  
  public void resetIDTables()
  {
    fIdTable.clear();
    fIdRefTable.clear();
  }
  
  public boolean needExtraChecking()
  {
    return fExtraChecking;
  }
  
  public boolean needFacetChecking()
  {
    return fFacetChecking;
  }
  
  public boolean needToNormalize()
  {
    return fNormalize;
  }
  
  public boolean useNamespaces()
  {
    return fNamespaces;
  }
  
  public boolean isEntityDeclared(String paramString)
  {
    if (fEntityState != null) {
      return fEntityState.isEntityDeclared(getSymbol(paramString));
    }
    return false;
  }
  
  public boolean isEntityUnparsed(String paramString)
  {
    if (fEntityState != null) {
      return fEntityState.isEntityUnparsed(getSymbol(paramString));
    }
    return false;
  }
  
  public boolean isIdDeclared(String paramString)
  {
    return fIdTable.containsKey(paramString);
  }
  
  public void addId(String paramString)
  {
    fIdTable.put(paramString, fNullValue);
  }
  
  public void addIdRef(String paramString)
  {
    fIdRefTable.put(paramString, fNullValue);
  }
  
  public String getSymbol(String paramString)
  {
    if (fSymbolTable != null) {
      return fSymbolTable.addSymbol(paramString);
    }
    return paramString.intern();
  }
  
  public String getURI(String paramString)
  {
    if (fNamespaceContext != null) {
      return fNamespaceContext.getURI(paramString);
    }
    return null;
  }
}
