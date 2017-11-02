package org.apache.xerces.impl.xs;

import org.apache.xerces.impl.xs.util.StringListImpl;
import org.apache.xerces.xs.AttributePSVI;
import org.apache.xerces.xs.ShortList;
import org.apache.xerces.xs.StringList;
import org.apache.xerces.xs.XSAttributeDeclaration;
import org.apache.xerces.xs.XSSimpleTypeDefinition;
import org.apache.xerces.xs.XSTypeDefinition;

public class AttributePSVImpl
  implements AttributePSVI
{
  protected XSAttributeDeclaration fDeclaration = null;
  protected XSTypeDefinition fTypeDecl = null;
  protected boolean fSpecified = false;
  protected String fNormalizedValue = null;
  protected Object fActualValue = null;
  protected short fActualValueType = 45;
  protected ShortList fItemValueTypes = null;
  protected XSSimpleTypeDefinition fMemberType = null;
  protected short fValidationAttempted = 0;
  protected short fValidity = 0;
  protected String[] fErrorCodes = null;
  protected String fValidationContext = null;
  
  public AttributePSVImpl() {}
  
  public String getSchemaDefault()
  {
    return fDeclaration == null ? null : fDeclaration.getConstraintValue();
  }
  
  public String getSchemaNormalizedValue()
  {
    return fNormalizedValue;
  }
  
  public boolean getIsSchemaSpecified()
  {
    return fSpecified;
  }
  
  public short getValidationAttempted()
  {
    return fValidationAttempted;
  }
  
  public short getValidity()
  {
    return fValidity;
  }
  
  public StringList getErrorCodes()
  {
    if (fErrorCodes == null) {
      return null;
    }
    return new StringListImpl(fErrorCodes, fErrorCodes.length);
  }
  
  public String getValidationContext()
  {
    return fValidationContext;
  }
  
  public XSTypeDefinition getTypeDefinition()
  {
    return fTypeDecl;
  }
  
  public XSSimpleTypeDefinition getMemberTypeDefinition()
  {
    return fMemberType;
  }
  
  public XSAttributeDeclaration getAttributeDeclaration()
  {
    return fDeclaration;
  }
  
  public Object getActualNormalizedValue()
  {
    return fActualValue;
  }
  
  public short getActualNormalizedValueType()
  {
    return fActualValueType;
  }
  
  public ShortList getItemValueTypes()
  {
    return fItemValueTypes;
  }
  
  public void reset()
  {
    fNormalizedValue = null;
    fActualValue = null;
    fActualValueType = 45;
    fItemValueTypes = null;
    fDeclaration = null;
    fTypeDecl = null;
    fSpecified = false;
    fMemberType = null;
    fValidationAttempted = 0;
    fValidity = 0;
    fErrorCodes = null;
    fValidationContext = null;
  }
}
