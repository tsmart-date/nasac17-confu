package org.apache.xpath;

import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;
import java.util.Vector;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.Source;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import org.apache.xalan.extensions.ExpressionContext;
import org.apache.xalan.res.XSLMessages;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMFilter;
import org.apache.xml.dtm.DTMIterator;
import org.apache.xml.dtm.DTMManager;
import org.apache.xml.dtm.DTMWSFilter;
import org.apache.xml.dtm.ref.DTMNodeIterator;
import org.apache.xml.dtm.ref.sax2dtm.SAX2RTFDTM;
import org.apache.xml.utils.DefaultErrorHandler;
import org.apache.xml.utils.IntStack;
import org.apache.xml.utils.NodeVector;
import org.apache.xml.utils.ObjectStack;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xml.utils.QName;
import org.apache.xml.utils.SAXSourceLocator;
import org.apache.xml.utils.XMLString;
import org.apache.xpath.axes.OneStepIteratorForward;
import org.apache.xpath.axes.SubContextList;
import org.apache.xpath.objects.DTMXRTreeFrag;
import org.apache.xpath.objects.XMLStringFactoryImpl;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.objects.XString;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.XMLReader;




















public class XPathContext
  extends DTMManager
{
  IntStack m_last_pushed_rtfdtm = new IntStack();
  









  private Vector m_rtfdtm_stack = null;
  
  private int m_which_rtfdtm = -1;
  




  private SAX2RTFDTM m_global_rtfdtm = null;
  




  private HashMap m_DTMXRTreeFrags = null;
  



  private boolean m_isSecureProcessing = false;
  





  protected DTMManager m_dtmManager = DTMManager.newInstance(XMLStringFactoryImpl.getFactory());
  







  public DTMManager getDTMManager()
  {
    return m_dtmManager;
  }
  



  public void setSecureProcessing(boolean flag)
  {
    m_isSecureProcessing = flag;
  }
  



  public boolean isSecureProcessing()
  {
    return m_isSecureProcessing;
  }
  
























  public DTM getDTM(Source source, boolean unique, DTMWSFilter wsfilter, boolean incremental, boolean doIndexing)
  {
    return m_dtmManager.getDTM(source, unique, wsfilter, incremental, doIndexing);
  }
  








  public DTM getDTM(int nodeHandle)
  {
    return m_dtmManager.getDTM(nodeHandle);
  }
  








  public int getDTMHandleFromNode(Node node)
  {
    return m_dtmManager.getDTMHandleFromNode(node);
  }
  




  public int getDTMIdentity(DTM dtm)
  {
    return m_dtmManager.getDTMIdentity(dtm);
  }
  




  public DTM createDocumentFragment()
  {
    return m_dtmManager.createDocumentFragment();
  }
  














  public boolean release(DTM dtm, boolean shouldHardDelete)
  {
    if ((m_rtfdtm_stack != null) && (m_rtfdtm_stack.contains(dtm)))
    {
      return false;
    }
    
    return m_dtmManager.release(dtm, shouldHardDelete);
  }
  












  public DTMIterator createDTMIterator(Object xpathCompiler, int pos)
  {
    return m_dtmManager.createDTMIterator(xpathCompiler, pos);
  }
  














  public DTMIterator createDTMIterator(String xpathString, PrefixResolver presolver)
  {
    return m_dtmManager.createDTMIterator(xpathString, presolver);
  }
  


















  public DTMIterator createDTMIterator(int whatToShow, DTMFilter filter, boolean entityReferenceExpansion)
  {
    return m_dtmManager.createDTMIterator(whatToShow, filter, entityReferenceExpansion);
  }
  








  public DTMIterator createDTMIterator(int node)
  {
    DTMIterator iter = new OneStepIteratorForward(13);
    iter.setRoot(node, this);
    return iter;
  }
  





  public XPathContext()
  {
    this(true);
  }
  





  public XPathContext(boolean recursiveVarContext)
  {
    m_prefixResolvers.push(null);
    m_currentNodes.push(-1);
    m_currentExpressionNodes.push(-1);
    m_saxLocations.push(null);
    m_variableStacks = (recursiveVarContext ? new VariableStack() : new VariableStack(1));
  }
  








  public XPathContext(Object owner)
  {
    this(owner, true);
  }
  







  public XPathContext(Object owner, boolean recursiveVarContext)
  {
    this(recursiveVarContext);
    m_owner = owner;
    try {
      m_ownerGetErrorListener = m_owner.getClass().getMethod("getErrorListener", new Class[0]);
    }
    catch (NoSuchMethodException nsme) {}
  }
  



  public void reset()
  {
    releaseDTMXRTreeFrags();
    Enumeration e;
    if (m_rtfdtm_stack != null) {
      for (e = m_rtfdtm_stack.elements(); e.hasMoreElements();)
        m_dtmManager.release((DTM)e.nextElement(), true);
    }
    m_rtfdtm_stack = null;
    m_which_rtfdtm = -1;
    
    if (m_global_rtfdtm != null)
      m_dtmManager.release(m_global_rtfdtm, true);
    m_global_rtfdtm = null;
    

    m_dtmManager = DTMManager.newInstance(XMLStringFactoryImpl.getFactory());
    

    m_saxLocations.removeAllElements();
    m_axesIteratorStack.removeAllElements();
    m_contextNodeLists.removeAllElements();
    m_currentExpressionNodes.removeAllElements();
    m_currentNodes.removeAllElements();
    m_iteratorRoots.RemoveAllNoClear();
    m_predicatePos.removeAllElements();
    m_predicateRoots.RemoveAllNoClear();
    m_prefixResolvers.removeAllElements();
    
    m_prefixResolvers.push(null);
    m_currentNodes.push(-1);
    m_currentExpressionNodes.push(-1);
    m_saxLocations.push(null);
  }
  

  ObjectStack m_saxLocations = new ObjectStack(4096);
  
  private Object m_owner;
  
  private Method m_ownerGetErrorListener;
  private VariableStack m_variableStacks;
  
  public void setSAXLocator(SourceLocator location)
  {
    m_saxLocations.setTop(location);
  }
  





  public void pushSAXLocator(SourceLocator location)
  {
    m_saxLocations.push(location);
  }
  





  public void pushSAXLocatorNull()
  {
    m_saxLocations.push(null);
  }
  




  public void popSAXLocator()
  {
    m_saxLocations.pop();
  }
  





  public SourceLocator getSAXLocator()
  {
    return (SourceLocator)m_saxLocations.peek();
  }
  
















  public Object getOwnerObject()
  {
    return m_owner;
  }
  














  public final VariableStack getVarStack()
  {
    return m_variableStacks;
  }
  






  public final void setVarStack(VariableStack varStack)
  {
    m_variableStacks = varStack;
  }
  




  private SourceTreeManager m_sourceTreeManager = new SourceTreeManager();
  
  private ErrorListener m_errorListener;
  private ErrorListener m_defaultErrorListener;
  private URIResolver m_uriResolver;
  public XMLReader m_primaryReader;
  
  public final SourceTreeManager getSourceTreeManager()
  {
    return m_sourceTreeManager;
  }
  






  public void setSourceTreeManager(SourceTreeManager mgr)
  {
    m_sourceTreeManager = mgr;
  }
  
















  public final ErrorListener getErrorListener()
  {
    if (null != m_errorListener) {
      return m_errorListener;
    }
    ErrorListener retval = null;
    try
    {
      if (null != m_ownerGetErrorListener) {
        retval = (ErrorListener)m_ownerGetErrorListener.invoke(m_owner, new Object[0]);
      }
    }
    catch (Exception e) {}
    if (null == retval)
    {
      if (null == m_defaultErrorListener)
        m_defaultErrorListener = new DefaultErrorHandler();
      retval = m_defaultErrorListener;
    }
    
    return retval;
  }
  




  public void setErrorListener(ErrorListener listener)
    throws IllegalArgumentException
  {
    if (listener == null)
      throw new IllegalArgumentException(XSLMessages.createXPATHMessage("ER_NULL_ERROR_HANDLER", null));
    m_errorListener = listener;
  }
  












  public final URIResolver getURIResolver()
  {
    return m_uriResolver;
  }
  






  public void setURIResolver(URIResolver resolver)
  {
    m_uriResolver = resolver;
  }
  










  public final XMLReader getPrimaryReader()
  {
    return m_primaryReader;
  }
  





  public void setPrimaryReader(XMLReader reader)
  {
    m_primaryReader = reader;
  }
  














  private void assertion(boolean b, String msg)
    throws TransformerException
  {
    if (!b)
    {
      ErrorListener errorHandler = getErrorListener();
      
      if (errorHandler != null)
      {
        errorHandler.fatalError(new TransformerException(XSLMessages.createMessage("ER_INCORRECT_PROGRAMMER_ASSERTION", new Object[] { msg }), (SAXSourceLocator)getSAXLocator()));
      }
    }
  }
  











  private Stack m_contextNodeLists = new Stack();
  
  public Stack getContextNodeListsStack() { return m_contextNodeLists; }
  public void setContextNodeListsStack(Stack s) { m_contextNodeLists = s; }
  







  public final DTMIterator getContextNodeList()
  {
    if (m_contextNodeLists.size() > 0) {
      return (DTMIterator)m_contextNodeLists.peek();
    }
    return null;
  }
  







  public final void pushContextNodeList(DTMIterator nl)
  {
    m_contextNodeLists.push(nl);
  }
  




  public final void popContextNodeList()
  {
    if (m_contextNodeLists.isEmpty()) {
      System.err.println("Warning: popContextNodeList when stack is empty!");
    } else {
      m_contextNodeLists.pop();
    }
  }
  




  public static final int RECURSIONLIMIT = 4096;
  



  private IntStack m_currentNodes = new IntStack(4096);
  


  public IntStack getCurrentNodeStack() { return m_currentNodes; }
  public void setCurrentNodeStack(IntStack nv) { m_currentNodes = nv; }
  





  public final int getCurrentNode()
  {
    return m_currentNodes.peek();
  }
  






  public final void pushCurrentNodeAndExpression(int cn, int en)
  {
    m_currentNodes.push(cn);
    m_currentExpressionNodes.push(cn);
  }
  



  public final void popCurrentNodeAndExpression()
  {
    m_currentNodes.quickPop(1);
    m_currentExpressionNodes.quickPop(1);
  }
  







  public final void pushExpressionState(int cn, int en, PrefixResolver nc)
  {
    m_currentNodes.push(cn);
    m_currentExpressionNodes.push(cn);
    m_prefixResolvers.push(nc);
  }
  



  public final void popExpressionState()
  {
    m_currentNodes.quickPop(1);
    m_currentExpressionNodes.quickPop(1);
    m_prefixResolvers.pop();
  }
  







  public final void pushCurrentNode(int n)
  {
    m_currentNodes.push(n);
  }
  



  public final void popCurrentNode()
  {
    m_currentNodes.quickPop(1);
  }
  



  public final void pushPredicateRoot(int n)
  {
    m_predicateRoots.push(n);
  }
  



  public final void popPredicateRoot()
  {
    m_predicateRoots.popQuick();
  }
  



  public final int getPredicateRoot()
  {
    return m_predicateRoots.peepOrNull();
  }
  



  public final void pushIteratorRoot(int n)
  {
    m_iteratorRoots.push(n);
  }
  



  public final void popIteratorRoot()
  {
    m_iteratorRoots.popQuick();
  }
  



  public final int getIteratorRoot()
  {
    return m_iteratorRoots.peepOrNull();
  }
  

  private NodeVector m_iteratorRoots = new NodeVector();
  

  private NodeVector m_predicateRoots = new NodeVector();
  

  private IntStack m_currentExpressionNodes = new IntStack(4096);
  

  public IntStack getCurrentExpressionNodeStack() { return m_currentExpressionNodes; }
  public void setCurrentExpressionNodeStack(IntStack nv) { m_currentExpressionNodes = nv; }
  
  private IntStack m_predicatePos = new IntStack();
  
  public final int getPredicatePos()
  {
    return m_predicatePos.peek();
  }
  
  public final void pushPredicatePos(int n)
  {
    m_predicatePos.push(n);
  }
  
  public final void popPredicatePos()
  {
    m_predicatePos.pop();
  }
  





  public final int getCurrentExpressionNode()
  {
    return m_currentExpressionNodes.peek();
  }
  





  public final void pushCurrentExpressionNode(int n)
  {
    m_currentExpressionNodes.push(n);
  }
  




  public final void popCurrentExpressionNode()
  {
    m_currentExpressionNodes.quickPop(1);
  }
  
  private ObjectStack m_prefixResolvers = new ObjectStack(4096);
  







  public final PrefixResolver getNamespaceContext()
  {
    return (PrefixResolver)m_prefixResolvers.peek();
  }
  






  public final void setNamespaceContext(PrefixResolver pr)
  {
    m_prefixResolvers.setTop(pr);
  }
  






  public final void pushNamespaceContext(PrefixResolver pr)
  {
    m_prefixResolvers.push(pr);
  }
  




  public final void pushNamespaceContextNull()
  {
    m_prefixResolvers.push(null);
  }
  



  public final void popNamespaceContext()
  {
    m_prefixResolvers.pop();
  }
  







  private Stack m_axesIteratorStack = new Stack();
  
  public Stack getAxesIteratorStackStacks() { return m_axesIteratorStack; }
  public void setAxesIteratorStackStacks(Stack s) { m_axesIteratorStack = s; }
  






  public final void pushSubContextList(SubContextList iter)
  {
    m_axesIteratorStack.push(iter);
  }
  




  public final void popSubContextList()
  {
    m_axesIteratorStack.pop();
  }
  






  public SubContextList getSubContextList()
  {
    return m_axesIteratorStack.isEmpty() ? null : (SubContextList)m_axesIteratorStack.peek();
  }
  









  public SubContextList getCurrentNodeList()
  {
    return m_axesIteratorStack.isEmpty() ? null : (SubContextList)m_axesIteratorStack.elementAt(0);
  }
  








  public final int getContextNode()
  {
    return getCurrentNode();
  }
  






  public final DTMIterator getContextNodes()
  {
    try
    {
      DTMIterator cnl = getContextNodeList();
      
      if (null != cnl) {
        return cnl.cloneWithReset();
      }
      return null;
    }
    catch (CloneNotSupportedException cnse) {}
    
    return null;
  }
  

  XPathExpressionContext expressionContext = new XPathExpressionContext();
  





  public ExpressionContext getExpressionContext()
  {
    return expressionContext;
  }
  


  public class XPathExpressionContext
    implements ExpressionContext
  {
    public XPathExpressionContext() {}
    


    public XPathContext getXPathContext()
    {
      return XPathContext.this;
    }
    






    public DTMManager getDTMManager()
    {
      return m_dtmManager;
    }
    




    public Node getContextNode()
    {
      int context = getCurrentNode();
      
      return getDTM(context).getNode(context);
    }
    





    public NodeIterator getContextNodes()
    {
      return new DTMNodeIterator(getContextNodeList());
    }
    




    public ErrorListener getErrorListener()
    {
      return XPathContext.this.getErrorListener();
    }
    






    public double toNumber(Node n)
    {
      int nodeHandle = getDTMHandleFromNode(n);
      DTM dtm = getDTM(nodeHandle);
      XString xobj = (XString)dtm.getStringValue(nodeHandle);
      return xobj.num();
    }
    






    public String toString(Node n)
    {
      int nodeHandle = getDTMHandleFromNode(n);
      DTM dtm = getDTM(nodeHandle);
      XMLString strVal = dtm.getStringValue(nodeHandle);
      return strVal.toString();
    }
    







    public final XObject getVariableOrParam(QName qname)
      throws TransformerException
    {
      return m_variableStacks.getVariableOrParam(XPathContext.this, qname);
    }
  }
  






























  public DTM getGlobalRTFDTM()
  {
    if ((m_global_rtfdtm == null) || (m_global_rtfdtm.isTreeIncomplete()))
    {
      m_global_rtfdtm = ((SAX2RTFDTM)m_dtmManager.getDTM(null, true, null, false, false));
    }
    return m_global_rtfdtm;
  }
  












  public DTM getRTFDTM()
  {
    SAX2RTFDTM rtfdtm;
    










    if (m_rtfdtm_stack == null)
    {
      m_rtfdtm_stack = new Vector();
      SAX2RTFDTM rtfdtm = (SAX2RTFDTM)m_dtmManager.getDTM(null, true, null, false, false);
      m_rtfdtm_stack.addElement(rtfdtm);
      m_which_rtfdtm += 1;
    } else { SAX2RTFDTM rtfdtm;
      if (m_which_rtfdtm < 0)
      {
        rtfdtm = (SAX2RTFDTM)m_rtfdtm_stack.elementAt(++m_which_rtfdtm);
      }
      else
      {
        rtfdtm = (SAX2RTFDTM)m_rtfdtm_stack.elementAt(m_which_rtfdtm);
        






        if (rtfdtm.isTreeIncomplete())
        {
          if (++m_which_rtfdtm < m_rtfdtm_stack.size()) {
            rtfdtm = (SAX2RTFDTM)m_rtfdtm_stack.elementAt(m_which_rtfdtm);
          }
          else {
            rtfdtm = (SAX2RTFDTM)m_dtmManager.getDTM(null, true, null, false, false);
            m_rtfdtm_stack.addElement(rtfdtm);
          }
        }
      }
    }
    return rtfdtm;
  }
  




  public void pushRTFContext()
  {
    m_last_pushed_rtfdtm.push(m_which_rtfdtm);
    if (null != m_rtfdtm_stack) {
      ((SAX2RTFDTM)getRTFDTM()).pushRewindMark();
    }
  }
  













  public void popRTFContext()
  {
    int previous = m_last_pushed_rtfdtm.pop();
    if (null == m_rtfdtm_stack)
      return;
    boolean isEmpty;
    if (m_which_rtfdtm == previous)
    {
      if (previous >= 0)
      {
        isEmpty = ((SAX2RTFDTM)m_rtfdtm_stack.elementAt(previous)).popRewindMark();
      }
    } else {
      while (m_which_rtfdtm != previous)
      {



        boolean isEmpty = ((SAX2RTFDTM)m_rtfdtm_stack.elementAt(m_which_rtfdtm)).popRewindMark();
        m_which_rtfdtm -= 1;
      }
    }
  }
  





  public DTMXRTreeFrag getDTMXRTreeFrag(int dtmIdentity)
  {
    if (m_DTMXRTreeFrags == null) {
      m_DTMXRTreeFrags = new HashMap();
    }
    
    if (m_DTMXRTreeFrags.containsKey(new Integer(dtmIdentity))) {
      return (DTMXRTreeFrag)m_DTMXRTreeFrags.get(new Integer(dtmIdentity));
    }
    DTMXRTreeFrag frag = new DTMXRTreeFrag(dtmIdentity, this);
    m_DTMXRTreeFrags.put(new Integer(dtmIdentity), frag);
    return frag;
  }
  




  private final void releaseDTMXRTreeFrags()
  {
    if (m_DTMXRTreeFrags == null) {
      return;
    }
    Iterator iter = m_DTMXRTreeFrags.values().iterator();
    while (iter.hasNext()) {
      DTMXRTreeFrag frag = (DTMXRTreeFrag)iter.next();
      frag.destruct();
      iter.remove();
    }
    m_DTMXRTreeFrags = null;
  }
}