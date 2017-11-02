package org.apache.xerces.impl.xs;

import org.apache.xerces.impl.xs.util.StringListImpl;
import org.apache.xerces.xs.ElementPSVI;
import org.apache.xerces.xs.ShortList;
import org.apache.xerces.xs.StringList;
import org.apache.xerces.xs.XSElementDeclaration;
import org.apache.xerces.xs.XSModel;
import org.apache.xerces.xs.XSNotationDeclaration;
import org.apache.xerces.xs.XSSimpleTypeDefinition;
import org.apache.xerces.xs.XSTypeDefinition;

public class ElementPSVImpl
  implements ElementPSVI
{
  protected XSElementDeclaration fDeclaration = null;
  protected XSTypeDefinition fTypeDecl = null;
  protected boolean fNil = false;
  protected boolean fSpecified = false;
  protected String fNormalizedValue = null;
  protected Object fActualValue = null;
  protected short fActualValueType = 45;
  protected ShortList fItemValueTypes = null;
  protected XSNotationDeclaration fNotation = null;
  protected XSSimpleTypeDefinition fMemberType = null;
  protected short fValidationAttempted = 0;
  protected short fValidity = 0;
  protected String[] fErrorCodes = null;
  protected String fValidationContext = null;
  protected SchemaGrammar[] fGrammars = null;
  protected XSModel fSchemaInformation = null;
  
  public ElementPSVImpl() {}
  
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
  
  public boolean getNil()
  {
    return fNil;
  }
  
  public XSNotationDeclaration getNotation()
  {
    return fNotation;
  }
  
  public XSTypeDefinition getTypeDefinition()
  {
    return fTypeDecl;
  }
  
  public XSSimpleTypeDefinition getMemberTypeDefinition()
  {
    return fMemberType;
  }
  
  public XSElementDeclaration getElementDeclaration()
  {
    return fDeclaration;
  }
  
  public synchronized XSModel getSchemaInformation()
  {
    if ((fSchemaInformation == null) && (fGrammars != null)) {
      fSchemaInformation = new XSModelImpl(fGrammars);
    }
    return fSchemaInformation;
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
    fDeclaration = null;
    fTypeDecl = null;
    fNil = false;
    fSpecified = false;
    fNotation = null;
    fMemberType = null;
    fValidationAttempted = 0;
    fValidity = 0;
    fErrorCodes = null;
    fValidationContext = null;
    fNormalizedValue = null;
    fActualValue = null;
    fActualValueType = 45;
    fItemValueTypes = null;
  }
}
