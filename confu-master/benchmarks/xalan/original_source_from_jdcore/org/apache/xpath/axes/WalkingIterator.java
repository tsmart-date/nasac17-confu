package org.apache.xpath.axes;

import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xpath.Expression;
import org.apache.xpath.ExpressionOwner;
import org.apache.xpath.VariableStack;
import org.apache.xpath.XPathContext;
import org.apache.xpath.XPathVisitor;
import org.apache.xpath.compiler.Compiler;
import org.apache.xpath.compiler.OpMap;


































public class WalkingIterator
  extends LocPathIterator
  implements ExpressionOwner
{
  static final long serialVersionUID = 9110225941815665906L;
  protected AxesWalker m_lastUsedWalker;
  protected AxesWalker m_firstWalker;
  
  WalkingIterator(Compiler compiler, int opPos, int analysis, boolean shouldLoadWalkers)
    throws TransformerException
  {
    super(compiler, opPos, analysis, shouldLoadWalkers);
    
    int firstStepPos = OpMap.getFirstChildPos(opPos);
    
    if (shouldLoadWalkers)
    {
      m_firstWalker = WalkerFactory.loadWalkers(this, compiler, firstStepPos, 0);
      m_lastUsedWalker = m_firstWalker;
    }
  }
  







  public WalkingIterator(PrefixResolver nscontext)
  {
    super(nscontext);
  }
  





  public int getAnalysisBits()
  {
    int bits = 0;
    if (null != m_firstWalker)
    {
      AxesWalker walker = m_firstWalker;
      
      while (null != walker)
      {
        int bit = walker.getAnalysisBits();
        bits |= bit;
        walker = walker.getNextWalker();
      }
    }
    return bits;
  }
  








  public Object clone()
    throws CloneNotSupportedException
  {
    WalkingIterator clone = (WalkingIterator)super.clone();
    


    if (null != m_firstWalker)
    {
      m_firstWalker = m_firstWalker.cloneDeep(clone, null);
    }
    
    return clone;
  }
  




  public void reset()
  {
    super.reset();
    
    if (null != m_firstWalker)
    {
      m_lastUsedWalker = m_firstWalker;
      
      m_firstWalker.setRoot(m_context);
    }
  }
  









  public void setRoot(int context, Object environment)
  {
    super.setRoot(context, environment);
    
    if (null != m_firstWalker)
    {
      m_firstWalker.setRoot(context);
      m_lastUsedWalker = m_firstWalker;
    }
  }
  







  public int nextNode()
  {
    if (m_foundLast) {
      return -1;
    }
    







    if (-1 == m_stackFrame)
    {
      return returnNextNode(m_firstWalker.nextNode());
    }
    

    VariableStack vars = m_execContext.getVarStack();
    

    int savedStart = vars.getStackFrame();
    
    vars.setStackFrame(m_stackFrame);
    
    int n = returnNextNode(m_firstWalker.nextNode());
    

    vars.setStackFrame(savedStart);
    
    return n;
  }
  









  public final AxesWalker getFirstWalker()
  {
    return m_firstWalker;
  }
  






  public final void setFirstWalker(AxesWalker walker)
  {
    m_firstWalker = walker;
  }
  







  public final void setLastUsedWalker(AxesWalker walker)
  {
    m_lastUsedWalker = walker;
  }
  






  public final AxesWalker getLastUsedWalker()
  {
    return m_lastUsedWalker;
  }
  







  public void detach()
  {
    if (m_allowDetach)
    {
      AxesWalker walker = m_firstWalker;
      while (null != walker)
      {
        walker.detach();
        walker = walker.getNextWalker();
      }
      
      m_lastUsedWalker = null;
      

      super.detach();
    }
  }
  










  public void fixupVariables(Vector vars, int globalsSize)
  {
    m_predicateIndex = -1;
    
    AxesWalker walker = m_firstWalker;
    
    while (null != walker)
    {
      walker.fixupVariables(vars, globalsSize);
      walker = walker.getNextWalker();
    }
  }
  



  public void callVisitors(ExpressionOwner owner, XPathVisitor visitor)
  {
    if (visitor.visitLocationPath(owner, this))
    {
      if (null != m_firstWalker)
      {
        m_firstWalker.callVisitors(this, visitor);
      }
    }
  }
  












  public Expression getExpression()
  {
    return m_firstWalker;
  }
  



  public void setExpression(Expression exp)
  {
    exp.exprSetParent(this);
    m_firstWalker = ((AxesWalker)exp);
  }
  



  public boolean deepEquals(Expression expr)
  {
    if (!super.deepEquals(expr)) {
      return false;
    }
    AxesWalker walker1 = m_firstWalker;
    AxesWalker walker2 = m_firstWalker;
    while ((null != walker1) && (null != walker2))
    {
      if (!walker1.deepEquals(walker2))
        return false;
      walker1 = walker1.getNextWalker();
      walker2 = walker2.getNextWalker();
    }
    
    if ((null != walker1) || (null != walker2)) {
      return false;
    }
    return true;
  }
}
