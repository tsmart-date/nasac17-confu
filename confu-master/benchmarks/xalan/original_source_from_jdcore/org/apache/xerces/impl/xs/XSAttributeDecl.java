package org.apache.xerces.impl.xs;

import org.apache.xerces.impl.dv.ValidatedInfo;
import org.apache.xerces.impl.dv.XSSimpleType;
import org.apache.xerces.xs.ShortList;
import org.apache.xerces.xs.XSAnnotation;
import org.apache.xerces.xs.XSAttributeDeclaration;
import org.apache.xerces.xs.XSComplexTypeDefinition;
import org.apache.xerces.xs.XSNamespaceItem;
import org.apache.xerces.xs.XSObjectList;
import org.apache.xerces.xs.XSSimpleTypeDefinition;

public class XSAttributeDecl
  implements XSAttributeDeclaration
{
  public static final short SCOPE_ABSENT = 0;
  public static final short SCOPE_GLOBAL = 1;
  public static final short SCOPE_LOCAL = 2;
  String fName = null;
  String fTargetNamespace = null;
  XSSimpleType fType = null;
  short fConstraintType = 0;
  short fScope = 0;
  XSComplexTypeDecl fEnclosingCT = null;
  XSObjectList fAnnotations = null;
  ValidatedInfo fDefault = null;
  
  public XSAttributeDecl() {}
  
  public void setValues(String paramString1, String paramString2, XSSimpleType paramXSSimpleType, short paramShort1, short paramShort2, ValidatedInfo paramValidatedInfo, XSComplexTypeDecl paramXSComplexTypeDecl, XSObjectList paramXSObjectList)
  {
    fName = paramString1;
    fTargetNamespace = paramString2;
    fType = paramXSSimpleType;
    fConstraintType = paramShort1;
    fScope = paramShort2;
    fDefault = paramValidatedInfo;
    fEnclosingCT = paramXSComplexTypeDecl;
    fAnnotations = paramXSObjectList;
  }
  
  public void reset()
  {
    fName = null;
    fTargetNamespace = null;
    fType = null;
    fConstraintType = 0;
    fScope = 0;
    fDefault = null;
    fAnnotations = null;
  }
  
  public short getType()
  {
    return 1;
  }
  
  public String getName()
  {
    return fName;
  }
  
  public String getNamespace()
  {
    return fTargetNamespace;
  }
  
  public XSSimpleTypeDefinition getTypeDefinition()
  {
    return fType;
  }
  
  public short getScope()
  {
    return fScope;
  }
  
  public XSComplexTypeDefinition getEnclosingCTDefinition()
  {
    return fEnclosingCT;
  }
  
  public short getConstraintType()
  {
    return fConstraintType;
  }
  
  public String getConstraintValue()
  {
    return getConstraintType() == 0 ? null : fDefault.stringValue();
  }
  
  public XSAnnotation getAnnotation()
  {
    return (XSAnnotation)fAnnotations.item(0);
  }
  
  public XSObjectList getAnnotations()
  {
    return fAnnotations;
  }
  
  public ValidatedInfo getValInfo()
  {
    return fDefault;
  }
  
  public XSNamespaceItem getNamespaceItem()
  {
    return null;
  }
  
  public Object getActualVC()
  {
    return getConstraintType() == 0 ? null : fDefault.actualValue;
  }
  
  public short getActualVCType()
  {
    return getConstraintType() == 0 ? 45 : fDefault.actualValueType;
  }
  
  public ShortList getItemValueTypes()
  {
    return getConstraintType() == 0 ? null : fDefault.itemValueTypes;
  }
}
