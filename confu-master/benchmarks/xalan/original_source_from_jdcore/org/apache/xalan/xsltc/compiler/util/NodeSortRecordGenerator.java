package org.apache.xalan.xsltc.compiler.util;

import org.apache.bcel.generic.ALOAD;
import org.apache.bcel.generic.Instruction;
import org.apache.xalan.xsltc.compiler.Stylesheet;




























public final class NodeSortRecordGenerator
  extends ClassGenerator
{
  private static final int TRANSLET_INDEX = 4;
  private final Instruction _aloadTranslet;
  
  public NodeSortRecordGenerator(String className, String superClassName, String fileName, int accessFlags, String[] interfaces, Stylesheet stylesheet)
  {
    super(className, superClassName, fileName, accessFlags, interfaces, stylesheet);
    
    _aloadTranslet = new ALOAD(4);
  }
  



  public Instruction loadTranslet()
  {
    return _aloadTranslet;
  }
  



  public boolean isExternal()
  {
    return true;
  }
}
