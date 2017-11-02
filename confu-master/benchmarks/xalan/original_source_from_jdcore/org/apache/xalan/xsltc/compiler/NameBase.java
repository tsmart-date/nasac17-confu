package org.apache.xalan.xsltc.compiler;

import java.util.Vector;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.INVOKESTATIC;
import org.apache.bcel.generic.InstructionList;
import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
import org.apache.xalan.xsltc.compiler.util.Type;
import org.apache.xalan.xsltc.compiler.util.TypeCheckError;


























class NameBase
  extends FunctionCall
{
  private Expression _param = null;
  private Type _paramType = Type.Node;
  


  public NameBase(QName fname)
  {
    super(fname);
  }
  


  public NameBase(QName fname, Vector arguments)
  {
    super(fname, arguments);
    _param = argument(0);
  }
  





  public Type typeCheck(SymbolTable stable)
    throws TypeCheckError
  {
    switch (argumentCount()) {
    case 0: 
      _paramType = Type.Node;
      break;
    case 1: 
      _paramType = _param.typeCheck(stable);
      break;
    default: 
      throw new TypeCheckError(this);
    }
    
    
    if ((_paramType != Type.NodeSet) && (_paramType != Type.Node) && (_paramType != Type.Reference))
    {

      throw new TypeCheckError(this);
    }
    
    return this._type = Type.String;
  }
  
  public Type getType() {
    return _type;
  }
  




  public void translate(ClassGenerator classGen, MethodGenerator methodGen)
  {
    ConstantPoolGen cpg = classGen.getConstantPool();
    InstructionList il = methodGen.getInstructionList();
    
    il.append(methodGen.loadDOM());
    

    if (argumentCount() == 0) {
      il.append(methodGen.loadContextNode());

    }
    else if (_paramType == Type.Node) {
      _param.translate(classGen, methodGen);
    }
    else if (_paramType == Type.Reference) {
      _param.translate(classGen, methodGen);
      il.append(new INVOKESTATIC(cpg.addMethodref("org.apache.xalan.xsltc.runtime.BasisLibrary", "referenceToNodeSet", "(Ljava/lang/Object;)Lorg/apache/xml/dtm/DTMAxisIterator;")));
      





      il.append(methodGen.nextNode());
    }
    else
    {
      _param.translate(classGen, methodGen);
      _param.startIterator(classGen, methodGen);
      il.append(methodGen.nextNode());
    }
  }
}
