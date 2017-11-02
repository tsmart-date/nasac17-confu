package org.apache.xpath.axes;

import org.apache.xpath.Expression;
import org.apache.xpath.ExpressionOwner;
import org.apache.xpath.XPathVisitor;
import org.apache.xpath.functions.FuncLast;
import org.apache.xpath.functions.FuncPosition;
import org.apache.xpath.functions.Function;
import org.apache.xpath.objects.XNumber;
import org.apache.xpath.operations.Div;
import org.apache.xpath.operations.Minus;
import org.apache.xpath.operations.Mod;
import org.apache.xpath.operations.Mult;
import org.apache.xpath.operations.Number;
import org.apache.xpath.operations.Plus;
import org.apache.xpath.operations.Quo;
import org.apache.xpath.operations.Variable;



















public class HasPositionalPredChecker
  extends XPathVisitor
{
  private boolean m_hasPositionalPred = false;
  private int m_predDepth = 0;
  



  public HasPositionalPredChecker() {}
  


  public static boolean check(LocPathIterator path)
  {
    HasPositionalPredChecker hppc = new HasPositionalPredChecker();
    path.callVisitors(null, hppc);
    return m_hasPositionalPred;
  }
  







  public boolean visitFunction(ExpressionOwner owner, Function func)
  {
    if (((func instanceof FuncPosition)) || ((func instanceof FuncLast)))
    {
      m_hasPositionalPred = true; }
    return true;
  }
  























  public boolean visitPredicate(ExpressionOwner owner, Expression pred)
  {
    m_predDepth += 1;
    
    if (m_predDepth == 1)
    {
      if (((pred instanceof Variable)) || ((pred instanceof XNumber)) || ((pred instanceof Div)) || ((pred instanceof Plus)) || ((pred instanceof Minus)) || ((pred instanceof Mod)) || ((pred instanceof Quo)) || ((pred instanceof Mult)) || ((pred instanceof Number)) || ((pred instanceof Function)))
      {








        m_hasPositionalPred = true;
      } else {
        pred.callVisitors(owner, this);
      }
    }
    m_predDepth -= 1;
    

    return false;
  }
}
