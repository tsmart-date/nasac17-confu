package org.apache.xalan.xsltc.compiler;

import java.util.ArrayList;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.generic.ASTORE;
import org.apache.bcel.generic.CHECKCAST;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.GETFIELD;
import org.apache.bcel.generic.INVOKESPECIAL;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.LocalVariableGen;
import org.apache.bcel.generic.NEW;
import org.apache.bcel.generic.PUSH;
import org.apache.bcel.generic.PUTFIELD;
import org.apache.xalan.xsltc.compiler.util.BooleanType;
import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
import org.apache.xalan.xsltc.compiler.util.FilterGenerator;
import org.apache.xalan.xsltc.compiler.util.IntType;
import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
import org.apache.xalan.xsltc.compiler.util.NumberType;
import org.apache.xalan.xsltc.compiler.util.ReferenceType;
import org.apache.xalan.xsltc.compiler.util.ResultTreeType;
import org.apache.xalan.xsltc.compiler.util.TestGenerator;
import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
import org.apache.xalan.xsltc.compiler.util.Util;































final class Predicate
  extends Expression
  implements Closure
{
  private Expression _exp = null;
  





  private boolean _canOptimize = true;
  




  private boolean _nthPositionFilter = false;
  




  private boolean _nthDescendant = false;
  



  int _ptype = -1;
  



  private String _className = null;
  



  private ArrayList _closureVars = null;
  



  private Closure _parentClosure = null;
  



  private Expression _value = null;
  



  private Step _step = null;
  


  public Predicate(Expression exp)
  {
    _exp = exp;
    _exp.setParent(this);
  }
  



  public void setParser(Parser parser)
  {
    super.setParser(parser);
    _exp.setParser(parser);
  }
  



  public boolean isNthPositionFilter()
  {
    return _nthPositionFilter;
  }
  



  public boolean isNthDescendant()
  {
    return _nthDescendant;
  }
  


  public void dontOptimize()
  {
    _canOptimize = false;
  }
  



  public boolean hasPositionCall()
  {
    return _exp.hasPositionCall();
  }
  



  public boolean hasLastCall()
  {
    return _exp.hasLastCall();
  }
  





  public boolean inInnerClass()
  {
    return _className != null;
  }
  


  public Closure getParentClosure()
  {
    if (_parentClosure == null) {
      SyntaxTreeNode node = getParent();
      do {
        if ((node instanceof Closure)) {
          _parentClosure = ((Closure)node);
          break;
        }
        if ((node instanceof TopLevelElement)) {
          break;
        }
        node = node.getParent();
      } while (node != null);
    }
    return _parentClosure;
  }
  



  public String getInnerClassName()
  {
    return _className;
  }
  


  public void addVariable(VariableRefBase variableRef)
  {
    if (_closureVars == null) {
      _closureVars = new ArrayList();
    }
    

    if (!_closureVars.contains(variableRef)) {
      _closureVars.add(variableRef);
      

      Closure parentClosure = getParentClosure();
      if (parentClosure != null) {
        parentClosure.addVariable(variableRef);
      }
    }
  }
  





  public int getPosType()
  {
    if (_ptype == -1) {
      SyntaxTreeNode parent = getParent();
      if ((parent instanceof StepPattern)) {
        _ptype = ((StepPattern)parent).getNodeType();
      }
      else if ((parent instanceof AbsoluteLocationPath)) {
        AbsoluteLocationPath path = (AbsoluteLocationPath)parent;
        Expression exp = path.getPath();
        if ((exp instanceof Step)) {
          _ptype = ((Step)exp).getNodeType();
        }
      }
      else if ((parent instanceof VariableRefBase)) {
        VariableRefBase ref = (VariableRefBase)parent;
        VariableBase var = ref.getVariable();
        Expression exp = var.getExpression();
        if ((exp instanceof Step)) {
          _ptype = ((Step)exp).getNodeType();
        }
      }
      else if ((parent instanceof Step)) {
        _ptype = ((Step)parent).getNodeType();
      }
    }
    return _ptype;
  }
  
  public boolean parentIsPattern() {
    return getParent() instanceof Pattern;
  }
  
  public Expression getExpr() {
    return _exp;
  }
  
  public String toString() {
    return "pred(" + _exp + ')';
  }
  









  public org.apache.xalan.xsltc.compiler.util.Type typeCheck(SymbolTable stable)
    throws TypeCheckError
  {
    org.apache.xalan.xsltc.compiler.util.Type texp = _exp.typeCheck(stable);
    

    if ((texp instanceof ReferenceType)) {
      _exp = new CastExpr(_exp, texp = org.apache.xalan.xsltc.compiler.util.Type.Real);
    }
    



    if ((texp instanceof ResultTreeType)) {
      _exp = new CastExpr(_exp, org.apache.xalan.xsltc.compiler.util.Type.Boolean);
      _exp = new CastExpr(_exp, org.apache.xalan.xsltc.compiler.util.Type.Real);
      texp = _exp.typeCheck(stable);
    }
    

    if ((texp instanceof NumberType))
    {
      if (!(texp instanceof IntType)) {
        _exp = new CastExpr(_exp, org.apache.xalan.xsltc.compiler.util.Type.Int);
      }
      
      if (_canOptimize)
      {
        _nthPositionFilter = ((!_exp.hasLastCall()) && (!_exp.hasPositionCall()));
        


        if (_nthPositionFilter) {
          SyntaxTreeNode parent = getParent();
          _nthDescendant = (((parent instanceof Step)) && ((parent.getParent() instanceof AbsoluteLocationPath)));
          
          return this._type = org.apache.xalan.xsltc.compiler.util.Type.NodeSet;
        }
      }
      

      _nthPositionFilter = (this._nthDescendant = 0);
      

      QName position = getParser().getQNameIgnoreDefaultNs("position");
      
      PositionCall positionCall = new PositionCall(position);
      
      positionCall.setParser(getParser());
      positionCall.setParent(this);
      
      _exp = new EqualityExpr(0, positionCall, _exp);
      
      if (_exp.typeCheck(stable) != org.apache.xalan.xsltc.compiler.util.Type.Boolean) {
        _exp = new CastExpr(_exp, org.apache.xalan.xsltc.compiler.util.Type.Boolean);
      }
      return this._type = org.apache.xalan.xsltc.compiler.util.Type.Boolean;
    }
    

    if (!(texp instanceof BooleanType)) {
      _exp = new CastExpr(_exp, org.apache.xalan.xsltc.compiler.util.Type.Boolean);
    }
    return this._type = org.apache.xalan.xsltc.compiler.util.Type.Boolean;
  }
  











  private void compileFilter(ClassGenerator classGen, MethodGenerator methodGen)
  {
    _className = getXSLTC().getHelperClassName();
    FilterGenerator filterGen = new FilterGenerator(_className, "java.lang.Object", toString(), 33, new String[] { "org.apache.xalan.xsltc.dom.CurrentNodeListFilter" }, classGen.getStylesheet());
    







    ConstantPoolGen cpg = filterGen.getConstantPool();
    int length = _closureVars == null ? 0 : _closureVars.size();
    

    for (int i = 0; i < length; i++) {
      VariableBase var = ((VariableRefBase)_closureVars.get(i)).getVariable();
      
      filterGen.addField(new Field(1, cpg.addUtf8(var.getEscapedName()), cpg.addUtf8(var.getType().toSignature()), null, cpg.getConstantPool()));
    }
    



    InstructionList il = new InstructionList();
    TestGenerator testGen = new TestGenerator(17, org.apache.bcel.generic.Type.BOOLEAN, new org.apache.bcel.generic.Type[] { org.apache.bcel.generic.Type.INT, org.apache.bcel.generic.Type.INT, org.apache.bcel.generic.Type.INT, org.apache.bcel.generic.Type.INT, Util.getJCRefType("Lorg/apache/xalan/xsltc/runtime/AbstractTranslet;"), Util.getJCRefType("Lorg/apache/xml/dtm/DTMAxisIterator;") }, new String[] { "node", "position", "last", "current", "translet", "iterator" }, "test", _className, il, cpg);
    



















    LocalVariableGen local = testGen.addLocalVariable("document", Util.getJCRefType("Lorg/apache/xalan/xsltc/DOM;"), null, null);
    

    String className = classGen.getClassName();
    il.append(filterGen.loadTranslet());
    il.append(new CHECKCAST(cpg.addClass(className)));
    il.append(new GETFIELD(cpg.addFieldref(className, "_dom", "Lorg/apache/xalan/xsltc/DOM;")));
    
    local.setStart(il.append(new ASTORE(local.getIndex())));
    

    testGen.setDomIndex(local.getIndex());
    
    _exp.translate(filterGen, testGen);
    il.append(IRETURN);
    
    filterGen.addEmptyConstructor(1);
    filterGen.addMethod(testGen);
    
    getXSLTC().dumpClass(filterGen.getJavaClass());
  }
  




  public boolean isBooleanTest()
  {
    return _exp instanceof BooleanExpr;
  }
  




  public boolean isNodeValueTest()
  {
    if (!_canOptimize) return false;
    return (getStep() != null) && (getCompareValue() != null);
  }
  





  public Step getStep()
  {
    if (_step != null) {
      return _step;
    }
    

    if (_exp == null) {
      return null;
    }
    

    if ((_exp instanceof EqualityExpr)) {
      EqualityExpr exp = (EqualityExpr)_exp;
      Expression left = exp.getLeft();
      Expression right = exp.getRight();
      

      if ((left instanceof CastExpr)) {
        left = ((CastExpr)left).getExpr();
      }
      if ((left instanceof Step)) {
        _step = ((Step)left);
      }
      

      if ((right instanceof CastExpr)) {
        right = ((CastExpr)right).getExpr();
      }
      if ((right instanceof Step)) {
        _step = ((Step)right);
      }
    }
    return _step;
  }
  





  public Expression getCompareValue()
  {
    if (_value != null) {
      return _value;
    }
    

    if (_exp == null) {
      return null;
    }
    

    if ((_exp instanceof EqualityExpr)) {
      EqualityExpr exp = (EqualityExpr)_exp;
      Expression left = exp.getLeft();
      Expression right = exp.getRight();
      

      if ((left instanceof LiteralExpr)) {
        _value = left;
        return _value;
      }
      
      if (((left instanceof VariableRefBase)) && (left.getType() == org.apache.xalan.xsltc.compiler.util.Type.String))
      {

        _value = left;
        return _value;
      }
      

      if ((right instanceof LiteralExpr)) {
        _value = right;
        return _value;
      }
      
      if (((right instanceof VariableRefBase)) && (right.getType() == org.apache.xalan.xsltc.compiler.util.Type.String))
      {

        _value = right;
        return _value;
      }
    }
    return null;
  }
  






  public void translateFilter(ClassGenerator classGen, MethodGenerator methodGen)
  {
    ConstantPoolGen cpg = classGen.getConstantPool();
    InstructionList il = methodGen.getInstructionList();
    

    compileFilter(classGen, methodGen);
    

    il.append(new NEW(cpg.addClass(_className)));
    il.append(DUP);
    il.append(new INVOKESPECIAL(cpg.addMethodref(_className, "<init>", "()V")));
    


    int length = _closureVars == null ? 0 : _closureVars.size();
    
    for (int i = 0; i < length; i++) {
      VariableRefBase varRef = (VariableRefBase)_closureVars.get(i);
      VariableBase var = varRef.getVariable();
      org.apache.xalan.xsltc.compiler.util.Type varType = var.getType();
      
      il.append(DUP);
      

      Closure variableClosure = _parentClosure;
      while ((variableClosure != null) && 
        (!variableClosure.inInnerClass())) {
        variableClosure = variableClosure.getParentClosure();
      }
      

      if (variableClosure != null) {
        il.append(ALOAD_0);
        il.append(new GETFIELD(cpg.addFieldref(variableClosure.getInnerClassName(), var.getEscapedName(), varType.toSignature())));

      }
      else
      {

        il.append(var.loadInstruction());
      }
      

      il.append(new PUTFIELD(cpg.addFieldref(_className, var.getEscapedName(), varType.toSignature())));
    }
  }
  








  public void translate(ClassGenerator classGen, MethodGenerator methodGen)
  {
    ConstantPoolGen cpg = classGen.getConstantPool();
    InstructionList il = methodGen.getInstructionList();
    
    if ((_nthPositionFilter) || (_nthDescendant)) {
      _exp.translate(classGen, methodGen);
    }
    else if ((isNodeValueTest()) && ((getParent() instanceof Step))) {
      _value.translate(classGen, methodGen);
      il.append(new CHECKCAST(cpg.addClass("java.lang.String")));
      il.append(new PUSH(cpg, ((EqualityExpr)_exp).getOp()));
    }
    else {
      translateFilter(classGen, methodGen);
    }
  }
}
