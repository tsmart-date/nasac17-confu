package org.apache.xerces.impl.xs.traversers;

import org.apache.xerces.impl.xs.SchemaGrammar;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.apache.xerces.impl.xs.XSElementDecl;
import org.apache.xerces.impl.xs.identity.UniqueOrKey;
import org.apache.xerces.util.DOMUtil;
import org.w3c.dom.Element;

class XSDUniqueOrKeyTraverser
  extends XSDAbstractIDConstraintTraverser
{
  public XSDUniqueOrKeyTraverser(XSDHandler paramXSDHandler, XSAttributeChecker paramXSAttributeChecker)
  {
    super(paramXSDHandler, paramXSAttributeChecker);
  }
  
  void traverse(Element paramElement, XSElementDecl paramXSElementDecl, XSDocumentInfo paramXSDocumentInfo, SchemaGrammar paramSchemaGrammar)
  {
    Object[] arrayOfObject = fAttrChecker.checkAttributes(paramElement, false, paramXSDocumentInfo);
    String str = (String)arrayOfObject[XSAttributeChecker.ATTIDX_NAME];
    if (str == null)
    {
      reportSchemaError("s4s-att-must-appear", new Object[] { DOMUtil.getLocalName(paramElement), SchemaSymbols.ATT_NAME }, paramElement);
      fAttrChecker.returnAttrArray(arrayOfObject, paramXSDocumentInfo);
      return;
    }
    UniqueOrKey localUniqueOrKey = null;
    if (DOMUtil.getLocalName(paramElement).equals(SchemaSymbols.ELT_UNIQUE)) {
      localUniqueOrKey = new UniqueOrKey(fTargetNamespace, str, fName, (short)3);
    } else {
      localUniqueOrKey = new UniqueOrKey(fTargetNamespace, str, fName, (short)1);
    }
    traverseIdentityConstraint(localUniqueOrKey, paramElement, paramXSDocumentInfo, arrayOfObject);
    paramSchemaGrammar.addIDConstraintDecl(paramXSElementDecl, localUniqueOrKey);
    fAttrChecker.returnAttrArray(arrayOfObject, paramXSDocumentInfo);
  }
}
