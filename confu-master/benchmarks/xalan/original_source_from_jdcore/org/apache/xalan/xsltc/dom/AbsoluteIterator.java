package org.apache.xalan.xsltc.dom;

import org.apache.xalan.xsltc.runtime.BasisLibrary;
import org.apache.xml.dtm.DTMAxisIterator;
import org.apache.xml.dtm.ref.DTMAxisIteratorBase;





































public final class AbsoluteIterator
  extends DTMAxisIteratorBase
{
  private DTMAxisIterator _source;
  
  public AbsoluteIterator(DTMAxisIterator source)
  {
    _source = source;
  }
  
  public void setRestartable(boolean isRestartable)
  {
    _isRestartable = isRestartable;
    _source.setRestartable(isRestartable);
  }
  
  public DTMAxisIterator setStartNode(int node) {
    _startNode = 0;
    if (_isRestartable) {
      _source.setStartNode(_startNode);
      resetPosition();
    }
    return this;
  }
  
  public int next() {
    return returnNode(_source.next());
  }
  
  public DTMAxisIterator cloneIterator() {
    try {
      AbsoluteIterator clone = (AbsoluteIterator)super.clone();
      _source = _source.cloneIterator();
      clone.resetPosition();
      _isRestartable = false;
      return clone;
    }
    catch (CloneNotSupportedException e) {
      BasisLibrary.runTimeError("ITERATOR_CLONE_ERR", e.toString());
    }
    return null;
  }
  
  public DTMAxisIterator reset()
  {
    _source.reset();
    return resetPosition();
  }
  
  public void setMark() {
    _source.setMark();
  }
  
  public void gotoMark() {
    _source.gotoMark();
  }
}
