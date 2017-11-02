package org.apache.xerces.dom;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.apache.xerces.xs.AttributePSVI;
import org.apache.xerces.xs.ItemPSVI;
import org.apache.xerces.xs.ShortList;
import org.apache.xerces.xs.StringList;
import org.apache.xerces.xs.XSAttributeDeclaration;
import org.apache.xerces.xs.XSSimpleTypeDefinition;
import org.apache.xerces.xs.XSTypeDefinition;

public class PSVIAttrNSImpl
  extends AttrNSImpl
  implements AttributePSVI
{
  static final long serialVersionUID = -3241738699421018889L;
  protected XSAttributeDeclaration fDeclaration = null;
  protected XSTypeDefinition fTypeDecl = null;
  protected boolean fSpecified = true;
  protected String fNormalizedValue = null;
  protected Object fActualValue = null;
  protected short fActualValueType = 45;
  protected ShortList fItemValueTypes = null;
  protected XSSimpleTypeDefinition fMemberType = null;
  protected short fValidationAttempted = 0;
  protected short fValidity = 0;
  protected StringList fErrorCodes = null;
  protected String fValidationContext = null;
  
  public PSVIAttrNSImpl(CoreDocumentImpl paramCoreDocumentImpl, String paramString1, String paramString2, String paramString3)
  {
    super(paramCoreDocumentImpl, paramString1, paramString2, paramString3);
  }
  
  public PSVIAttrNSImpl(CoreDocumentImpl paramCoreDocumentImpl, String paramString1, String paramString2)
  {
    super(paramCoreDocumentImpl, paramString1, paramString2);
  }
  
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
    return fErrorCodes;
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
  
  public void setPSVI(AttributePSVI paramAttributePSVI)
  {
    fDeclaration = paramAttributePSVI.getAttributeDeclaration();
    fValidationContext = paramAttributePSVI.getValidationContext();
    fValidity = paramAttributePSVI.getValidity();
    fValidationAttempted = paramAttributePSVI.getValidationAttempted();
    fErrorCodes = paramAttributePSVI.getErrorCodes();
    fNormalizedValue = paramAttributePSVI.getSchemaNormalizedValue();
    fActualValue = paramAttributePSVI.getActualNormalizedValue();
    fActualValueType = paramAttributePSVI.getActualNormalizedValueType();
    fItemValueTypes = paramAttributePSVI.getItemValueTypes();
    fTypeDecl = paramAttributePSVI.getTypeDefinition();
    fMemberType = paramAttributePSVI.getMemberTypeDefinition();
    fSpecified = paramAttributePSVI.getIsSchemaSpecified();
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
  
  private void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    throw new NotSerializableException(getClass().getName());
  }
  
  private void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    throw new NotSerializableException(getClass().getName());
  }
}
