package org.apache.xalan.transformer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.xalan.extensions.ExtensionsTable;
import org.apache.xalan.res.XSLMessages;
import org.apache.xalan.templates.AVT;
import org.apache.xalan.templates.ElemAttributeSet;
import org.apache.xalan.templates.ElemForEach;
import org.apache.xalan.templates.ElemSort;
import org.apache.xalan.templates.ElemTemplate;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.ElemTextLiteral;
import org.apache.xalan.templates.ElemVariable;
import org.apache.xalan.templates.OutputProperties;
import org.apache.xalan.templates.Stylesheet;
import org.apache.xalan.templates.StylesheetComposed;
import org.apache.xalan.templates.StylesheetRoot;
import org.apache.xalan.templates.WhiteSpaceInfo;
import org.apache.xalan.templates.XUnresolvedVariable;
import org.apache.xalan.trace.GenerateEvent;
import org.apache.xalan.trace.TraceManager;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMIterator;
import org.apache.xml.dtm.DTMManager;
import org.apache.xml.dtm.DTMWSFilter;
import org.apache.xml.serializer.SerializationHandler;
import org.apache.xml.serializer.Serializer;
import org.apache.xml.serializer.SerializerFactory;
import org.apache.xml.serializer.SerializerTrace;
import org.apache.xml.serializer.ToSAXHandler;
import org.apache.xml.serializer.ToTextStream;
import org.apache.xml.serializer.ToXMLSAXHandler;
import org.apache.xml.utils.BoolStack;
import org.apache.xml.utils.DOMBuilder;
import org.apache.xml.utils.DOMHelper;
import org.apache.xml.utils.DefaultErrorHandler;
import org.apache.xml.utils.NodeVector;
import org.apache.xml.utils.ObjectPool;
import org.apache.xml.utils.ObjectStack;
import org.apache.xml.utils.QName;
import org.apache.xml.utils.SAXSourceLocator;
import org.apache.xml.utils.ThreadControllerWrapper;
import org.apache.xml.utils.WrappedRuntimeException;
import org.apache.xpath.Arg;
import org.apache.xpath.ExtensionsProvider;
import org.apache.xpath.NodeSetDTM;
import org.apache.xpath.SourceTreeManager;
import org.apache.xpath.VariableStack;
import org.apache.xpath.XPathContext;
import org.apache.xpath.axes.SelfIteratorNoPredicate;
import org.apache.xpath.functions.FuncExtFunction;
import org.apache.xpath.objects.XObject;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ext.DeclHandler;
import org.xml.sax.ext.LexicalHandler;





















public class TransformerImpl
  extends Transformer
  implements Runnable, DTMWSFilter, ExtensionsProvider, SerializerTrace
{
  private Boolean m_reentryGuard = new Boolean(true);
  



  private FileOutputStream m_outputStream = null;
  




  private boolean m_parserEventsOnMain = true;
  

  private Thread m_transformThread;
  

  private String m_urlOfSource = null;
  

  private Result m_outputTarget = null;
  




  private OutputProperties m_outputFormat;
  



  ContentHandler m_inputContentHandler;
  



  private ContentHandler m_outputContentHandler = null;
  








  DocumentBuilder m_docBuilder = null;
  




  private ObjectPool m_textResultHandlerObjectPool = new ObjectPool(ToTextStream.class);
  






  private ObjectPool m_stringWriterObjectPool = new ObjectPool(StringWriter.class);
  





  private OutputProperties m_textformat = new OutputProperties("text");
  



















  ObjectStack m_currentTemplateElements = new ObjectStack(4096);
  











  Stack m_currentMatchTemplates = new Stack();
  








  NodeVector m_currentMatchedNodes = new NodeVector();
  



  private StylesheetRoot m_stylesheetRoot = null;
  




  private boolean m_quietConflictWarnings = true;
  




  private XPathContext m_xcontext;
  



  private StackGuard m_stackGuard;
  



  private SerializationHandler m_serializationHandler;
  



  private KeyManager m_keyManager = new KeyManager();
  




  Stack m_attrSetStack = null;
  




  CountersTable m_countersTable = null;
  



  BoolStack m_currentTemplateRuleIsNull = new BoolStack();
  





  ObjectStack m_currentFuncResult = new ObjectStack();
  





  private MsgMgr m_msgMgr;
  





  private boolean m_optimizer = true;
  






  private boolean m_incremental = false;
  






  private boolean m_source_location = false;
  




  private boolean m_debug = false;
  



  private ErrorListener m_errorHandler = new DefaultErrorHandler(false);
  




  private TraceManager m_traceManager = new TraceManager(this);
  





  private Exception m_exceptionThrown = null;
  





  private Source m_xmlSource;
  





  private int m_doc;
  





  private boolean m_isTransformDone = false;
  

  private boolean m_hasBeenReset = false;
  

  private boolean m_shouldReset = true;
  






  public void setShouldReset(boolean shouldReset)
  {
    m_shouldReset = shouldReset;
  }
  



  private Stack m_modes = new Stack();
  










  public TransformerImpl(StylesheetRoot stylesheet)
  {
    m_optimizer = stylesheet.getOptimizer();
    m_incremental = stylesheet.getIncremental();
    m_source_location = stylesheet.getSource_location();
    setStylesheet(stylesheet);
    XPathContext xPath = new XPathContext(this);
    xPath.setIncremental(m_incremental);
    xPath.getDTMManager().setIncremental(m_incremental);
    xPath.setSource_location(m_source_location);
    xPath.getDTMManager().setSource_location(m_source_location);
    
    if (stylesheet.isSecureProcessing()) {
      xPath.setSecureProcessing(true);
    }
    setXPathContext(xPath);
    getXPathContext().setNamespaceContext(stylesheet);
    m_stackGuard = new StackGuard(this);
  }
  





  private ExtensionsTable m_extensionsTable = null;
  





  public ExtensionsTable getExtensionsTable()
  {
    return m_extensionsTable;
  }
  







  void setExtensionsTable(StylesheetRoot sroot)
    throws TransformerException
  {
    try
    {
      if (sroot.getExtensions() != null) {
        m_extensionsTable = new ExtensionsTable(sroot);
      }
    } catch (TransformerException te) {
      te.printStackTrace();
    }
  }
  

  public boolean functionAvailable(String ns, String funcName)
    throws TransformerException
  {
    return getExtensionsTable().functionAvailable(ns, funcName);
  }
  
  public boolean elementAvailable(String ns, String elemName)
    throws TransformerException
  {
    return getExtensionsTable().elementAvailable(ns, elemName);
  }
  

  public Object extFunction(String ns, String funcName, Vector argVec, Object methodKey)
    throws TransformerException
  {
    return getExtensionsTable().extFunction(ns, funcName, argVec, methodKey, getXPathContext().getExpressionContext());
  }
  


  public Object extFunction(FuncExtFunction extFunction, Vector argVec)
    throws TransformerException
  {
    return getExtensionsTable().extFunction(extFunction, argVec, getXPathContext().getExpressionContext());
  }
  








  public void reset()
  {
    if ((!m_hasBeenReset) && (m_shouldReset))
    {
      m_hasBeenReset = true;
      
      if (m_outputStream != null)
      {
        try
        {
          m_outputStream.close();
        }
        catch (IOException ioe) {}
      }
      
      m_outputStream = null;
      


      m_countersTable = null;
      
      m_xcontext.reset();
      
      m_xcontext.getVarStack().reset();
      resetUserParameters();
      

      m_currentTemplateElements.removeAllElements();
      m_currentMatchTemplates.removeAllElements();
      m_currentMatchedNodes.removeAllElements();
      
      m_serializationHandler = null;
      m_outputTarget = null;
      m_keyManager = new KeyManager();
      m_attrSetStack = null;
      m_countersTable = null;
      m_currentTemplateRuleIsNull = new BoolStack();
      m_xmlSource = null;
      m_doc = -1;
      m_isTransformDone = false;
      m_transformThread = null;
      


      m_xcontext.getSourceTreeManager().reset();
    }
  }
  











  public boolean getProperty(String property)
  {
    return false;
  }
  










  public void setProperty(String property, Object value) {}
  










  public boolean isParserEventsOnMain()
  {
    return m_parserEventsOnMain;
  }
  






  public Thread getTransformThread()
  {
    return m_transformThread;
  }
  






  public void setTransformThread(Thread t)
  {
    m_transformThread = t;
  }
  

  private boolean m_hasTransformThreadErrorCatcher = false;
  


  Vector m_userParams;
  


  public boolean hasTransformThreadErrorCatcher()
  {
    return m_hasTransformThreadErrorCatcher;
  }
  





  public void transform(Source source)
    throws TransformerException
  {
    transform(source, true);
  }
  












  public void transform(Source source, boolean shouldRelease)
    throws TransformerException
  {
    try
    {
      if (getXPathContext().getNamespaceContext() == null) {
        getXPathContext().setNamespaceContext(getStylesheet());
      }
      String base = source.getSystemId();
      

      if (null == base)
      {
        base = m_stylesheetRoot.getBaseIdentifier();
      }
      

      if (null == base)
      {
        String currentDir = "";
        try {
          currentDir = System.getProperty("user.dir");
        }
        catch (SecurityException se) {}
        
        if (currentDir.startsWith(File.separator)) {
          base = "file://" + currentDir;
        } else {
          base = "file:///" + currentDir;
        }
        base = base + File.separatorChar + source.getClass().getName();
      }
      
      setBaseURLOfSource(base);
      DTMManager mgr = m_xcontext.getDTMManager();
      







      if ((((source instanceof StreamSource)) && (source.getSystemId() == null) && (((StreamSource)source).getInputStream() == null) && (((StreamSource)source).getReader() == null)) || (((source instanceof SAXSource)) && (((SAXSource)source).getInputSource() == null) && (((SAXSource)source).getXMLReader() == null)) || (((source instanceof DOMSource)) && (((DOMSource)source).getNode() == null)))
      {


        try
        {


          DocumentBuilderFactory builderF = DocumentBuilderFactory.newInstance();
          
          DocumentBuilder builder = builderF.newDocumentBuilder();
          String systemID = source.getSystemId();
          source = new DOMSource(builder.newDocument());
          

          if (systemID != null) {
            source.setSystemId(systemID);
          }
        } catch (ParserConfigurationException e) {
          fatalError(e);
        }
      }
      DTM dtm = mgr.getDTM(source, false, this, true, true);
      dtm.setDocumentBaseURI(base);
      
      boolean hardDelete = true;
      



      try
      {
        transformNode(dtm.getDocument());
      }
      finally
      {
        if (shouldRelease) {
          mgr.release(dtm, hardDelete);
        }
      }
      




      Exception e = getExceptionThrown();
      
      if (null != e)
      {
        if ((e instanceof TransformerException))
        {
          throw ((TransformerException)e);
        }
        if ((e instanceof WrappedRuntimeException))
        {
          fatalError(((WrappedRuntimeException)e).getException());

        }
        else
        {
          throw new TransformerException(e);
        }
      }
      else if (null != m_serializationHandler)
      {
        m_serializationHandler.endDocument();
      }
    }
    catch (WrappedRuntimeException wre)
    {
      Throwable throwable = wre.getException();
      

      while ((throwable instanceof WrappedRuntimeException))
      {
        throwable = ((WrappedRuntimeException)throwable).getException();
      }
      

      fatalError(throwable);

    }
    catch (SAXParseException spe)
    {

      fatalError(spe);
    }
    catch (SAXException se)
    {
      m_errorHandler.fatalError(new TransformerException(se));
    }
    finally
    {
      m_hasTransformThreadErrorCatcher = false;
      

      reset();
    }
  }
  
  private void fatalError(Throwable throwable) throws TransformerException
  {
    if ((throwable instanceof SAXParseException)) {
      m_errorHandler.fatalError(new TransformerException(throwable.getMessage(), new SAXSourceLocator((SAXParseException)throwable)));
    } else {
      m_errorHandler.fatalError(new TransformerException(throwable));
    }
  }
  





  public String getBaseURLOfSource()
  {
    return m_urlOfSource;
  }
  







  public void setBaseURLOfSource(String base)
  {
    m_urlOfSource = base;
  }
  





  public Result getOutputTarget()
  {
    return m_outputTarget;
  }
  








  public void setOutputTarget(Result outputTarget)
  {
    m_outputTarget = outputTarget;
  }
  
















  public String getOutputProperty(String qnameString)
    throws IllegalArgumentException
  {
    String value = null;
    OutputProperties props = getOutputFormat();
    
    value = props.getProperty(qnameString);
    
    if (null == value)
    {
      if (!OutputProperties.isLegalPropertyKey(qnameString)) {
        throw new IllegalArgumentException(XSLMessages.createMessage("ER_OUTPUT_PROPERTY_NOT_RECOGNIZED", new Object[] { qnameString }));
      }
    }
    
    return value;
  }
  













  public String getOutputPropertyNoDefault(String qnameString)
    throws IllegalArgumentException
  {
    String value = null;
    OutputProperties props = getOutputFormat();
    
    value = (String)props.getProperties().get(qnameString);
    
    if (null == value)
    {
      if (!OutputProperties.isLegalPropertyKey(qnameString)) {
        throw new IllegalArgumentException(XSLMessages.createMessage("ER_OUTPUT_PROPERTY_NOT_RECOGNIZED", new Object[] { qnameString }));
      }
    }
    
    return value;
  }
  




























































  public void setOutputProperty(String name, String value)
    throws IllegalArgumentException
  {
    synchronized (m_reentryGuard)
    {



      if (null == m_outputFormat)
      {
        m_outputFormat = ((OutputProperties)getStylesheet().getOutputComposed().clone());
      }
      

      if (!OutputProperties.isLegalPropertyKey(name)) {
        throw new IllegalArgumentException(XSLMessages.createMessage("ER_OUTPUT_PROPERTY_NOT_RECOGNIZED", new Object[] { name }));
      }
      
      m_outputFormat.setProperty(name, value);
    }
  }
  



















  public void setOutputProperties(Properties oformat)
    throws IllegalArgumentException
  {
    synchronized (m_reentryGuard)
    {
      if (null != oformat)
      {


        String method = (String)oformat.get("method");
        
        if (null != method) {
          m_outputFormat = new OutputProperties(method);
        } else if (m_outputFormat == null) {
          m_outputFormat = new OutputProperties();
        }
        m_outputFormat.copyFrom(oformat);
        


        m_outputFormat.copyFrom(m_stylesheetRoot.getOutputProperties());

      }
      else
      {
        m_outputFormat = null;
      }
    }
  }
  













  public Properties getOutputProperties()
  {
    return (Properties)getOutputFormat().getProperties().clone();
  }
  












  public SerializationHandler createSerializationHandler(Result outputTarget)
    throws TransformerException
  {
    SerializationHandler xoh = createSerializationHandler(outputTarget, getOutputFormat());
    
    return xoh;
  }
  



















  public SerializationHandler createSerializationHandler(Result outputTarget, OutputProperties format)
    throws TransformerException
  {
    Node outputNode = null;
    SerializationHandler xoh;
    if ((outputTarget instanceof DOMResult))
    {
      outputNode = ((DOMResult)outputTarget).getNode();
      Node nextSibling = ((DOMResult)outputTarget).getNextSibling();
      
      Document doc;
      Document doc;
      short type;
      if (null != outputNode)
      {
        short type = outputNode.getNodeType();
        doc = 9 == type ? (Document)outputNode : outputNode.getOwnerDocument();

      }
      else
      {

        boolean isSecureProcessing = m_stylesheetRoot.isSecureProcessing();
        doc = DOMHelper.createDocument(isSecureProcessing);
        outputNode = doc;
        type = outputNode.getNodeType();
        
        ((DOMResult)outputTarget).setNode(outputNode);
      }
      
      DOMBuilder handler = 11 == type ? new DOMBuilder(doc, (DocumentFragment)outputNode) : new DOMBuilder(doc, outputNode);
      



      if (nextSibling != null) {
        handler.setNextSibling(nextSibling);
      }
      String encoding = format.getProperty("encoding");
      xoh = new ToXMLSAXHandler(handler, handler, encoding);
    }
    else if ((outputTarget instanceof SAXResult))
    {
      ContentHandler handler = ((SAXResult)outputTarget).getHandler();
      
      if (null == handler) {
        throw new IllegalArgumentException("handler can not be null for a SAXResult");
      }
      LexicalHandler lexHandler;
      LexicalHandler lexHandler;
      if ((handler instanceof LexicalHandler)) {
        lexHandler = (LexicalHandler)handler;
      } else {
        lexHandler = null;
      }
      String encoding = format.getProperty("encoding");
      String method = format.getProperty("method");
      
      ToXMLSAXHandler toXMLSAXHandler = new ToXMLSAXHandler(handler, lexHandler, encoding);
      toXMLSAXHandler.setShouldOutputNSAttr(false);
      SerializationHandler xoh = toXMLSAXHandler;
      

      String publicID = format.getProperty("doctype-public");
      String systemID = format.getProperty("doctype-system");
      if (systemID != null)
        xoh.setDoctypeSystem(systemID);
      if (publicID != null) {
        xoh.setDoctypePublic(publicID);
      }
      if ((handler instanceof TransformerClient)) {
        XalanTransformState state = new XalanTransformState();
        ((TransformerClient)handler).setTransformState(state);
        ((ToSAXHandler)xoh).setTransformState(state);


      }
      


    }
    else if ((outputTarget instanceof StreamResult))
    {
      StreamResult sresult = (StreamResult)outputTarget;
      
      try
      {
        SerializationHandler serializer = (SerializationHandler)SerializerFactory.getSerializer(format.getProperties());
        

        if (null != sresult.getWriter()) {
          serializer.setWriter(sresult.getWriter());
        } else if (null != sresult.getOutputStream()) {
          serializer.setOutputStream(sresult.getOutputStream()); } else { SerializationHandler xoh;
          if (null != sresult.getSystemId())
          {
            String fileURL = sresult.getSystemId();
            
            if (fileURL.startsWith("file:///"))
            {
              if (fileURL.substring(8).indexOf(":") > 0) {
                fileURL = fileURL.substring(8);
              } else {
                fileURL = fileURL.substring(7);
              }
            } else if (fileURL.startsWith("file:/"))
            {
              if (fileURL.substring(6).indexOf(":") > 0) {
                fileURL = fileURL.substring(6);
              } else {
                fileURL = fileURL.substring(5);
              }
            }
            m_outputStream = new FileOutputStream(fileURL);
            
            serializer.setOutputStream(m_outputStream);
            
            xoh = serializer;
          }
          else {
            throw new TransformerException(XSLMessages.createMessage("ER_NO_OUTPUT_SPECIFIED", null));
          }
        }
        


        xoh = serializer;
      }
      catch (IOException ioe)
      {
        SerializationHandler xoh;
        


        throw new TransformerException(ioe);
      }
    }
    else
    {
      throw new TransformerException(XSLMessages.createMessage("ER_CANNOT_TRANSFORM_TO_RESULT_TYPE", new Object[] { outputTarget.getClass().getName() }));
    }
    

    SerializationHandler xoh;
    

    xoh.setTransformer(this);
    
    SourceLocator srcLocator = getStylesheet();
    xoh.setSourceLocator(srcLocator);
    

    return xoh;
  }
  









  public void transform(Source xmlSource, Result outputTarget)
    throws TransformerException
  {
    transform(xmlSource, outputTarget, true);
  }
  









  public void transform(Source xmlSource, Result outputTarget, boolean shouldRelease)
    throws TransformerException
  {
    synchronized (m_reentryGuard)
    {
      SerializationHandler xoh = createSerializationHandler(outputTarget);
      setSerializationHandler(xoh);
      
      m_outputTarget = outputTarget;
      
      transform(xmlSource, shouldRelease);
    }
  }
  












  public void transformNode(int node, Result outputTarget)
    throws TransformerException
  {
    SerializationHandler xoh = createSerializationHandler(outputTarget);
    setSerializationHandler(xoh);
    
    m_outputTarget = outputTarget;
    
    transformNode(node);
  }
  









  public void transformNode(int node)
    throws TransformerException
  {
    setExtensionsTable(getStylesheet());
    
    synchronized (m_serializationHandler)
    {
      m_hasBeenReset = false;
      
      XPathContext xctxt = getXPathContext();
      DTM dtm = xctxt.getDTM(node);
      
      try
      {
        pushGlobalVars(node);
        



        StylesheetRoot stylesheet = getStylesheet();
        int n = stylesheet.getGlobalImportCount();
        
        for (int i = 0; i < n; i++)
        {
          StylesheetComposed imported = stylesheet.getGlobalImport(i);
          int includedCount = imported.getIncludeCountComposed();
          
          for (int j = -1; j < includedCount; j++)
          {
            Stylesheet included = imported.getIncludeComposed(j);
            
            included.runtimeInit(this);
            
            for (ElemTemplateElement child = included.getFirstChildElem(); 
                child != null; child = child.getNextSiblingElem())
            {
              child.runtimeInit(this);
            }
          }
        }
        

        DTMIterator dtmIter = new SelfIteratorNoPredicate();
        dtmIter.setRoot(node, xctxt);
        xctxt.pushContextNodeList(dtmIter);
        try
        {
          applyTemplateToNode(null, null, node);
        }
        finally {}
        





        if (null != m_serializationHandler)
        {
          m_serializationHandler.endDocument();


        }
        



      }
      catch (Exception se)
      {


        while ((se instanceof WrappedRuntimeException))
        {
          Exception e = ((WrappedRuntimeException)se).getException();
          if (null != e) {
            se = e;
          }
        }
        if (null != m_serializationHandler)
        {
          try
          {
            if ((se instanceof SAXParseException)) {
              m_serializationHandler.fatalError((SAXParseException)se);
            } else if ((se instanceof TransformerException))
            {
              TransformerException te = (TransformerException)se;
              SAXSourceLocator sl = new SAXSourceLocator(te.getLocator());
              m_serializationHandler.fatalError(new SAXParseException(te.getMessage(), sl, te));
            }
            else
            {
              m_serializationHandler.fatalError(new SAXParseException(se.getMessage(), new SAXSourceLocator(), se));
            }
          }
          catch (Exception e) {}
        }
        
        if ((se instanceof TransformerException))
        {
          m_errorHandler.fatalError((TransformerException)se);
        }
        else if ((se instanceof SAXParseException))
        {
          m_errorHandler.fatalError(new TransformerException(se.getMessage(), new SAXSourceLocator((SAXParseException)se), se));

        }
        else
        {

          m_errorHandler.fatalError(new TransformerException(se));
        }
        
      }
      finally
      {
        reset();
      }
    }
  }
  







  public ContentHandler getInputContentHandler()
  {
    return getInputContentHandler(false);
  }
  











  public ContentHandler getInputContentHandler(boolean doDocFrag)
  {
    if (null == m_inputContentHandler)
    {



      m_inputContentHandler = new TransformerHandlerImpl(this, doDocFrag, m_urlOfSource);
    }
    

    return m_inputContentHandler;
  }
  







  public DeclHandler getInputDeclHandler()
  {
    if ((m_inputContentHandler instanceof DeclHandler)) {
      return (DeclHandler)m_inputContentHandler;
    }
    return null;
  }
  







  public LexicalHandler getInputLexicalHandler()
  {
    if ((m_inputContentHandler instanceof LexicalHandler)) {
      return (LexicalHandler)m_inputContentHandler;
    }
    return null;
  }
  








  public void setOutputFormat(OutputProperties oformat)
  {
    m_outputFormat = oformat;
  }
  









  public OutputProperties getOutputFormat()
  {
    OutputProperties format = null == m_outputFormat ? getStylesheet().getOutputComposed() : m_outputFormat;
    


    return format;
  }
  











  public void setParameter(String name, String namespace, Object value)
  {
    VariableStack varstack = getXPathContext().getVarStack();
    QName qname = new QName(namespace, name);
    XObject xobject = XObject.create(value, getXPathContext());
    
    StylesheetRoot sroot = m_stylesheetRoot;
    Vector vars = sroot.getVariablesAndParamsComposed();
    int i = vars.size();
    for (;;) { i--; if (i < 0)
        break;
      ElemVariable variable = (ElemVariable)vars.elementAt(i);
      if ((variable.getXSLToken() == 41) && (variable.getName().equals(qname)))
      {

        varstack.setGlobalVariable(i, xobject);
      }
    }
  }
  














  public void setParameter(String name, Object value)
  {
    if (value == null) {
      throw new IllegalArgumentException(XSLMessages.createMessage("ER_INVALID_SET_PARAM_VALUE", new Object[] { name }));
    }
    
    StringTokenizer tokenizer = new StringTokenizer(name, "{}", false);
    



    try
    {
      String s1 = tokenizer.nextToken();
      String s2 = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
      
      if (null == m_userParams) {
        m_userParams = new Vector();
      }
      if (null == s2)
      {
        replaceOrPushUserParam(new QName(s1), XObject.create(value, getXPathContext()));
        setParameter(s1, null, value);
      }
      else
      {
        replaceOrPushUserParam(new QName(s1, s2), XObject.create(value, getXPathContext()));
        setParameter(s2, s1, value);
      }
    }
    catch (NoSuchElementException nsee) {}
  }
  












  private void replaceOrPushUserParam(QName qname, XObject xval)
  {
    int n = m_userParams.size();
    
    for (int i = n - 1; i >= 0; i--)
    {
      Arg arg = (Arg)m_userParams.elementAt(i);
      
      if (arg.getQName().equals(qname))
      {
        m_userParams.setElementAt(new Arg(qname, xval, true), i);
        
        return;
      }
    }
    
    m_userParams.addElement(new Arg(qname, xval, true));
  }
  
















  public Object getParameter(String name)
  {
    try
    {
      QName qname = QName.getQNameFromString(name);
      
      if (null == m_userParams) {
        return null;
      }
      int n = m_userParams.size();
      
      for (int i = n - 1; i >= 0; i--)
      {
        Arg arg = (Arg)m_userParams.elementAt(i);
        
        if (arg.getQName().equals(qname))
        {
          return arg.getVal().object();
        }
      }
      
      return null;
    }
    catch (NoSuchElementException nsee) {}
    


    return null;
  }
  









  private void resetUserParameters()
  {
    try
    {
      if (null == m_userParams) {
        return;
      }
      int n = m_userParams.size();
      for (int i = n - 1; i >= 0; i--)
      {
        Arg arg = (Arg)m_userParams.elementAt(i);
        QName name = arg.getQName();
        

        String s1 = name.getNamespace();
        String s2 = name.getLocalPart();
        
        setParameter(s2, s1, arg.getVal().object());
      }
    }
    catch (NoSuchElementException nsee) {}
  }
  














  public void setParameters(Properties params)
  {
    clearParameters();
    
    Enumeration names = params.propertyNames();
    
    while (names.hasMoreElements())
    {
      String name = params.getProperty((String)names.nextElement());
      StringTokenizer tokenizer = new StringTokenizer(name, "{}", false);
      



      try
      {
        String s1 = tokenizer.nextToken();
        String s2 = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
        
        if (null == s2) {
          setParameter(s1, null, params.getProperty(name));
        } else {
          setParameter(s2, s1, params.getProperty(name));
        }
      }
      catch (NoSuchElementException nsee) {}
    }
  }
  







  public void clearParameters()
  {
    synchronized (m_reentryGuard)
    {
      VariableStack varstack = new VariableStack();
      
      m_xcontext.setVarStack(varstack);
      
      m_userParams = null;
    }
  }
  




















  protected void pushGlobalVars(int contextNode)
    throws TransformerException
  {
    XPathContext xctxt = m_xcontext;
    VariableStack vs = xctxt.getVarStack();
    StylesheetRoot sr = getStylesheet();
    Vector vars = sr.getVariablesAndParamsComposed();
    
    int i = vars.size();
    vs.link(i);
    for (;;) {
      i--; if (i < 0)
        break;
      ElemVariable v = (ElemVariable)vars.elementAt(i);
      

      XObject xobj = new XUnresolvedVariable(v, contextNode, this, vs.getStackFrame(), 0, true);
      

      if (null == vs.elementAt(i)) {
        vs.setGlobalVariable(i, xobj);
      }
    }
  }
  







  public void setURIResolver(URIResolver resolver)
  {
    synchronized (m_reentryGuard)
    {
      m_xcontext.getSourceTreeManager().setURIResolver(resolver);
    }
  }
  







  public URIResolver getURIResolver()
  {
    return m_xcontext.getSourceTreeManager().getURIResolver();
  }
  











  public void setContentHandler(ContentHandler handler)
  {
    if (handler == null)
    {
      throw new NullPointerException(XSLMessages.createMessage("ER_NULL_CONTENT_HANDLER", null));
    }
    

    m_outputContentHandler = handler;
    
    if (null == m_serializationHandler)
    {
      ToXMLSAXHandler h = new ToXMLSAXHandler();
      h.setContentHandler(handler);
      h.setTransformer(this);
      
      m_serializationHandler = h;
    }
    else {
      m_serializationHandler.setContentHandler(handler);
    }
  }
  






  public ContentHandler getContentHandler()
  {
    return m_outputContentHandler;
  }
  












  public int transformToRTF(ElemTemplateElement templateParent)
    throws TransformerException
  {
    DTM dtmFrag = m_xcontext.getRTFDTM();
    return transformToRTF(templateParent, dtmFrag);
  }
  
















  public int transformToGlobalRTF(ElemTemplateElement templateParent)
    throws TransformerException
  {
    DTM dtmFrag = m_xcontext.getGlobalRTFDTM();
    return transformToRTF(templateParent, dtmFrag);
  }
  











  private int transformToRTF(ElemTemplateElement templateParent, DTM dtmFrag)
    throws TransformerException
  {
    XPathContext xctxt = m_xcontext;
    
    ContentHandler rtfHandler = dtmFrag.getContentHandler();
    







    SerializationHandler savedRTreeHandler = m_serializationHandler;
    


    ToSAXHandler h = new ToXMLSAXHandler();
    h.setContentHandler(rtfHandler);
    h.setTransformer(this);
    

    m_serializationHandler = h;
    

    SerializationHandler rth = m_serializationHandler;
    int resultFragment;
    try
    {
      rth.startDocument();
      



      rth.flushPending();
      


      try
      {
        executeChildTemplates(templateParent, true);
        

        rth.flushPending();
        




        resultFragment = dtmFrag.getDocument();
      }
      finally
      {
        rth.endDocument();
      }
    }
    catch (SAXException se)
    {
      throw new TransformerException(se);

    }
    finally
    {

      m_serializationHandler = savedRTreeHandler;
    }
    
    return resultFragment;
  }
  







  public ObjectPool getStringWriterPool()
  {
    return m_stringWriterObjectPool;
  }
  












  public String transformToString(ElemTemplateElement elem)
    throws TransformerException
  {
    ElemTemplateElement firstChild = elem.getFirstChildElem();
    if (null == firstChild)
      return "";
    if ((elem.hasTextLitOnly()) && (m_optimizer))
    {
      return ((ElemTextLiteral)firstChild).getNodeValue();
    }
    

    SerializationHandler savedRTreeHandler = m_serializationHandler;
    


    StringWriter sw = (StringWriter)m_stringWriterObjectPool.getInstance();
    
    m_serializationHandler = ((ToTextStream)m_textResultHandlerObjectPool.getInstance());
    

    if (null == m_serializationHandler)
    {



      Serializer serializer = SerializerFactory.getSerializer(m_textformat.getProperties());
      
      m_serializationHandler = ((SerializationHandler)serializer);
    }
    
    m_serializationHandler.setTransformer(this);
    m_serializationHandler.setWriter(sw);
    




    String result;
    



    try
    {
      executeChildTemplates(elem, true);
      m_serializationHandler.endDocument();
      
      result = sw.toString();
    }
    catch (SAXException se)
    {
      throw new TransformerException(se);
    }
    finally
    {
      sw.getBuffer().setLength(0);
      
      try
      {
        sw.close();
      }
      catch (Exception ioe) {}
      
      m_stringWriterObjectPool.freeInstance(sw);
      m_serializationHandler.reset();
      m_textResultHandlerObjectPool.freeInstance(m_serializationHandler);
      

      m_serializationHandler = savedRTreeHandler;
    }
    
    return result;
  }
  













  public boolean applyTemplateToNode(ElemTemplateElement xslInstruction, ElemTemplate template, int child)
    throws TransformerException
  {
    DTM dtm = m_xcontext.getDTM(child);
    short nodeType = dtm.getNodeType(child);
    boolean isDefaultTextRule = false;
    boolean isApplyImports = false;
    
    isApplyImports = xslInstruction != null;
    



    if ((null == template) || (isApplyImports))
    {
      int endImportLevel = 0;
      int maxImportLevel;
      if (isApplyImports)
      {
        int maxImportLevel = template.getStylesheetComposed().getImportCountComposed() - 1;
        
        endImportLevel = template.getStylesheetComposed().getEndImportCountComposed();

      }
      else
      {
        maxImportLevel = -1;
      }
      






      if ((isApplyImports) && (maxImportLevel == -1))
      {
        template = null;


      }
      else
      {

        XPathContext xctxt = m_xcontext;
        
        try
        {
          xctxt.pushNamespaceContext(xslInstruction);
          
          QName mode = getMode();
          
          if (isApplyImports) {
            template = m_stylesheetRoot.getTemplateComposed(xctxt, child, mode, maxImportLevel, endImportLevel, m_quietConflictWarnings, dtm);
          }
          else {
            template = m_stylesheetRoot.getTemplateComposed(xctxt, child, mode, m_quietConflictWarnings, dtm);
          }
          
        }
        finally
        {
          xctxt.popNamespaceContext();
        }
      }
      


      if (null == template)
      {
        switch (nodeType)
        {
        case 1: 
        case 11: 
          template = m_stylesheetRoot.getDefaultRule();
          break;
        case 2: 
        case 3: 
        case 4: 
          template = m_stylesheetRoot.getDefaultTextRule();
          isDefaultTextRule = true;
          break;
        case 9: 
          template = m_stylesheetRoot.getDefaultRootRule();
          break;
        case 5: case 6: 
        case 7: case 8: 
        case 10: default: 
          return false;
        }
        
      }
    }
    

    try
    {
      pushElemTemplateElement(template);
      m_xcontext.pushCurrentNode(child);
      pushPairCurrentMatched(template, child);
      

      if (!isApplyImports) {
        DTMIterator cnl = new NodeSetDTM(child, m_xcontext.getDTMManager());
        m_xcontext.pushContextNodeList(cnl);
      }
      
      if (isDefaultTextRule)
      {
        switch (nodeType)
        {
        case 3: 
        case 4: 
          ClonerToResultTree.cloneToResultTree(child, nodeType, dtm, getResultTreeHandler(), false);
          
          break;
        case 2: 
          dtm.dispatchCharactersEvents(child, getResultTreeHandler(), false);
        


        }
        
      }
      else
      {
        if (m_debug) {
          getTraceManager().fireTraceEvent(template);
        }
        







        m_xcontext.setSAXLocator(template);
        
        m_xcontext.getVarStack().link(m_frameSize);
        executeChildTemplates(template, true);
        
        if (m_debug) {
          getTraceManager().fireTraceEndEvent(template);
        }
      }
    }
    catch (SAXException se) {
      throw new TransformerException(se);
    }
    finally
    {
      if (!isDefaultTextRule)
        m_xcontext.getVarStack().unlink();
      m_xcontext.popCurrentNode();
      if (!isApplyImports) {
        m_xcontext.popContextNodeList();
      }
      popCurrentMatched();
      
      popElemTemplateElement();
    }
    
    return true;
  }
  

















  public void executeChildTemplates(ElemTemplateElement elem, Node context, QName mode, ContentHandler handler)
    throws TransformerException
  {
    XPathContext xctxt = m_xcontext;
    
    try
    {
      if (null != mode)
        pushMode(mode);
      xctxt.pushCurrentNode(xctxt.getDTMHandleFromNode(context));
      executeChildTemplates(elem, handler);
    }
    finally
    {
      xctxt.popCurrentNode();
      


      if (null != mode) {
        popMode();
      }
    }
  }
  












  public void executeChildTemplates(ElemTemplateElement elem, boolean shouldAddAttrs)
    throws TransformerException
  {
    ElemTemplateElement t = elem.getFirstChildElem();
    
    if (null == t) {
      return;
    }
    if ((elem.hasTextLitOnly()) && (m_optimizer))
    {
      char[] chars = ((ElemTextLiteral)t).getChars();
      
      try
      {
        pushElemTemplateElement(t);
        m_serializationHandler.characters(chars, 0, chars.length);
      }
      catch (SAXException se)
      {
        throw new TransformerException(se);
      }
      finally
      {
        popElemTemplateElement();
      }
      return;
    }
    






    XPathContext xctxt = m_xcontext;
    xctxt.pushSAXLocatorNull();
    int currentTemplateElementsTop = m_currentTemplateElements.size();
    m_currentTemplateElements.push(null);
    try
    {
      for (; 
          

          t != null; t = t.getNextSiblingElem())
      {
        if ((shouldAddAttrs) || (t.getXSLToken() != 48))
        {


          xctxt.setSAXLocator(t);
          m_currentTemplateElements.setElementAt(t, currentTemplateElementsTop);
          t.execute(this);
        }
      }
    }
    catch (RuntimeException re) {
      TransformerException te = new TransformerException(re);
      te.setLocator(t);
      throw te;
    }
    finally
    {
      m_currentTemplateElements.pop();
      xctxt.popSAXLocator();
    }
  }
  
















  public void executeChildTemplates(ElemTemplateElement elem, ContentHandler handler)
    throws TransformerException
  {
    SerializationHandler xoh = getSerializationHandler();
    



    SerializationHandler savedHandler = xoh;
    
    try
    {
      xoh.flushPending();
      

      LexicalHandler lex = null;
      if ((handler instanceof LexicalHandler)) {
        lex = (LexicalHandler)handler;
      }
      m_serializationHandler = new ToXMLSAXHandler(handler, lex, savedHandler.getEncoding());
      m_serializationHandler.setTransformer(this);
      executeChildTemplates(elem, true);
    }
    catch (TransformerException e)
    {
      throw e;
    }
    catch (SAXException se) {
      throw new TransformerException(se);
    }
    finally
    {
      m_serializationHandler = savedHandler;
    }
  }
  














  public Vector processSortKeys(ElemForEach foreach, int sourceNodeContext)
    throws TransformerException
  {
    Vector keys = null;
    XPathContext xctxt = m_xcontext;
    int nElems = foreach.getSortElemCount();
    
    if (nElems > 0) {
      keys = new Vector();
    }
    
    for (int i = 0; i < nElems; i++)
    {
      ElemSort sort = foreach.getSortElem(i);
      
      if (m_debug) {
        getTraceManager().fireTraceEvent(sort);
      }
      String langString = null != sort.getLang() ? sort.getLang().evaluate(xctxt, sourceNodeContext, foreach) : null;
      

      String dataTypeString = sort.getDataType().evaluate(xctxt, sourceNodeContext, foreach);
      

      if (dataTypeString.indexOf(":") >= 0) {
        System.out.println("TODO: Need to write the hooks for QNAME sort data type");
      }
      else if ((!dataTypeString.equalsIgnoreCase("text")) && (!dataTypeString.equalsIgnoreCase("number")))
      {

        foreach.error("ER_ILLEGAL_ATTRIBUTE_VALUE", new Object[] { "data-type", dataTypeString });
      }
      

      boolean treatAsNumbers = (null != dataTypeString) && (dataTypeString.equals("number"));
      

      String orderString = sort.getOrder().evaluate(xctxt, sourceNodeContext, foreach);
      

      if ((!orderString.equalsIgnoreCase("ascending")) && (!orderString.equalsIgnoreCase("descending")))
      {

        foreach.error("ER_ILLEGAL_ATTRIBUTE_VALUE", new Object[] { "order", orderString });
      }
      

      boolean descending = (null != orderString) && (orderString.equals("descending"));
      

      AVT caseOrder = sort.getCaseOrder();
      boolean caseOrderUpper;
      boolean caseOrderUpper;
      if (null != caseOrder)
      {
        String caseOrderString = caseOrder.evaluate(xctxt, sourceNodeContext, foreach);
        

        if ((!caseOrderString.equalsIgnoreCase("upper-first")) && (!caseOrderString.equalsIgnoreCase("lower-first")))
        {

          foreach.error("ER_ILLEGAL_ATTRIBUTE_VALUE", new Object[] { "case-order", caseOrderString });
        }
        

        caseOrderUpper = (null != caseOrderString) && (caseOrderString.equals("upper-first"));

      }
      else
      {

        caseOrderUpper = false;
      }
      
      keys.addElement(new NodeSortKey(this, sort.getSelect(), treatAsNumbers, descending, langString, caseOrderUpper, foreach));
      

      if (m_debug) {
        getTraceManager().fireTraceEndEvent(sort);
      }
    }
    return keys;
  }
  










  public Vector getElementCallstack()
  {
    Vector elems = new Vector();
    int nStackSize = m_currentTemplateElements.size();
    for (int i = 0; i < nStackSize; i++)
    {
      ElemTemplateElement elem = (ElemTemplateElement)m_currentTemplateElements.elementAt(i);
      if (null != elem)
      {
        elems.addElement(elem);
      }
    }
    return elems;
  }
  






  public int getCurrentTemplateElementsCount()
  {
    return m_currentTemplateElements.size();
  }
  







  public ObjectStack getCurrentTemplateElements()
  {
    return m_currentTemplateElements;
  }
  






  public void pushElemTemplateElement(ElemTemplateElement elem)
  {
    m_currentTemplateElements.push(elem);
  }
  



  public void popElemTemplateElement()
  {
    m_currentTemplateElements.pop();
  }
  







  public void setCurrentElement(ElemTemplateElement e)
  {
    m_currentTemplateElements.setTop(e);
  }
  







  public ElemTemplateElement getCurrentElement()
  {
    return m_currentTemplateElements.size() > 0 ? (ElemTemplateElement)m_currentTemplateElements.peek() : null;
  }
  







  public int getCurrentNode()
  {
    return m_xcontext.getCurrentNode();
  }
  







  public Vector getTemplateCallstack()
  {
    Vector elems = new Vector();
    int nStackSize = m_currentTemplateElements.size();
    for (int i = 0; i < nStackSize; i++)
    {
      ElemTemplateElement elem = (ElemTemplateElement)m_currentTemplateElements.elementAt(i);
      if ((null != elem) && (elem.getXSLToken() != 19))
      {
        elems.addElement(elem);
      }
    }
    return elems;
  }
  













  public ElemTemplate getCurrentTemplate()
  {
    ElemTemplateElement elem = getCurrentElement();
    

    while ((null != elem) && (elem.getXSLToken() != 19))
    {
      elem = elem.getParentElem();
    }
    
    return (ElemTemplate)elem;
  }
  








  public void pushPairCurrentMatched(ElemTemplateElement template, int child)
  {
    m_currentMatchTemplates.push(template);
    m_currentMatchedNodes.push(child);
  }
  



  public void popCurrentMatched()
  {
    m_currentMatchTemplates.pop();
    m_currentMatchedNodes.pop();
  }
  









  public ElemTemplate getMatchedTemplate()
  {
    return (ElemTemplate)m_currentMatchTemplates.peek();
  }
  







  public int getMatchedNode()
  {
    return m_currentMatchedNodes.peepTail();
  }
  






  public DTMIterator getContextNodeList()
  {
    try
    {
      DTMIterator cnl = m_xcontext.getContextNodeList();
      
      return cnl == null ? null : cnl.cloneWithReset();
    }
    catch (CloneNotSupportedException cnse) {}
    


    return null;
  }
  






  public Transformer getTransformer()
  {
    return this;
  }
  














  public void setStylesheet(StylesheetRoot stylesheetRoot)
  {
    m_stylesheetRoot = stylesheetRoot;
  }
  






  public final StylesheetRoot getStylesheet()
  {
    return m_stylesheetRoot;
  }
  








  public boolean getQuietConflictWarnings()
  {
    return m_quietConflictWarnings;
  }
  









  public void setQuietConflictWarnings(boolean b)
  {
    m_quietConflictWarnings = b;
  }
  







  public void setXPathContext(XPathContext xcontext)
  {
    m_xcontext = xcontext;
  }
  





  public final XPathContext getXPathContext()
  {
    return m_xcontext;
  }
  







  public StackGuard getStackGuard()
  {
    return m_stackGuard;
  }
  












  public int getRecursionLimit()
  {
    return m_stackGuard.getRecursionLimit();
  }
  













  public void setRecursionLimit(int limit)
  {
    m_stackGuard.setRecursionLimit(limit);
  }
  






  public SerializationHandler getResultTreeHandler()
  {
    return m_serializationHandler;
  }
  






  public SerializationHandler getSerializationHandler()
  {
    return m_serializationHandler;
  }
  






  public KeyManager getKeyManager()
  {
    return m_keyManager;
  }
  








  public boolean isRecursiveAttrSet(ElemAttributeSet attrSet)
  {
    if (null == m_attrSetStack)
    {
      m_attrSetStack = new Stack();
    }
    
    if (!m_attrSetStack.empty())
    {
      int loc = m_attrSetStack.search(attrSet);
      
      if (loc > -1)
      {
        return true;
      }
    }
    
    return false;
  }
  






  public void pushElemAttributeSet(ElemAttributeSet attrSet)
  {
    m_attrSetStack.push(attrSet);
  }
  



  public void popElemAttributeSet()
  {
    m_attrSetStack.pop();
  }
  






  public CountersTable getCountersTable()
  {
    if (null == m_countersTable) {
      m_countersTable = new CountersTable();
    }
    return m_countersTable;
  }
  






  public boolean currentTemplateRuleIsNull()
  {
    return (!m_currentTemplateRuleIsNull.isEmpty()) && (m_currentTemplateRuleIsNull.peek() == true);
  }
  








  public void pushCurrentTemplateRuleIsNull(boolean b)
  {
    m_currentTemplateRuleIsNull.push(b);
  }
  




  public void popCurrentTemplateRuleIsNull()
  {
    m_currentTemplateRuleIsNull.pop();
  }
  







  public void pushCurrentFuncResult(Object val)
  {
    m_currentFuncResult.push(val);
  }
  




  public Object popCurrentFuncResult()
  {
    return m_currentFuncResult.pop();
  }
  






  public boolean currentFuncResultSeen()
  {
    return (!m_currentFuncResult.empty()) && (m_currentFuncResult.peek() != null);
  }
  







  public MsgMgr getMsgMgr()
  {
    if (null == m_msgMgr) {
      m_msgMgr = new MsgMgr(this);
    }
    return m_msgMgr;
  }
  







  public void setErrorListener(ErrorListener listener)
    throws IllegalArgumentException
  {
    synchronized (m_reentryGuard)
    {
      if (listener == null) {
        throw new IllegalArgumentException(XSLMessages.createMessage("ER_NULL_ERROR_HANDLER", null));
      }
      m_errorHandler = listener;
    }
  }
  





  public ErrorListener getErrorListener()
  {
    return m_errorHandler;
  }
  







  public TraceManager getTraceManager()
  {
    return m_traceManager;
  }
  

































  public boolean getFeature(String name)
    throws SAXNotRecognizedException, SAXNotSupportedException
  {
    if ("http://xml.org/trax/features/sax/input".equals(name))
      return true;
    if ("http://xml.org/trax/features/dom/input".equals(name)) {
      return true;
    }
    throw new SAXNotRecognizedException(name);
  }
  








  public QName getMode()
  {
    return m_modes.isEmpty() ? null : (QName)m_modes.peek();
  }
  








  public void pushMode(QName mode)
  {
    m_modes.push(mode);
  }
  






  public void popMode()
  {
    m_modes.pop();
  }
  








  public void runTransformThread(int priority)
  {
    Thread t = ThreadControllerWrapper.runThread(this, priority);
    setTransformThread(t);
  }
  





  public void runTransformThread()
  {
    ThreadControllerWrapper.runThread(this, -1);
  }
  






  public static void runTransformThread(Runnable runnable)
  {
    ThreadControllerWrapper.runThread(runnable, -1);
  }
  











  public void waitTransformThread()
    throws SAXException
  {
    Thread transformThread = getTransformThread();
    
    if (null != transformThread)
    {
      try
      {
        ThreadControllerWrapper.waitThread(transformThread, this);
        
        if (!hasTransformThreadErrorCatcher())
        {
          Exception e = getExceptionThrown();
          
          if (null != e)
          {
            e.printStackTrace();
            throw new SAXException(e);
          }
        }
        
        setTransformThread(null);
      }
      catch (InterruptedException ie) {}
    }
  }
  







  public Exception getExceptionThrown()
  {
    return m_exceptionThrown;
  }
  







  public void setExceptionThrown(Exception e)
  {
    m_exceptionThrown = e;
  }
  






  public void setSourceTreeDocForThread(int doc)
  {
    m_doc = doc;
  }
  







  public void setXMLSource(Source source)
  {
    m_xmlSource = source;
  }
  







  public boolean isTransformDone()
  {
    synchronized (this)
    {
      return m_isTransformDone;
    }
  }
  







  public void setIsTransformDone(boolean done)
  {
    synchronized (this)
    {
      m_isTransformDone = done;
    }
  }
  



























  void postExceptionFromThread(Exception e)
  {
    m_isTransformDone = true;
    m_exceptionThrown = e;
    

    synchronized (this)
    {





      notifyAll();
    }
  }
  












  public void run()
  {
    m_hasBeenReset = false;
    



    try
    {
      try
      {
        m_isTransformDone = false;
        







        transformNode(m_doc);


      }
      catch (Exception e)
      {


        if (null != m_transformThread) {
          postExceptionFromThread(e);
        } else {
          throw new RuntimeException(e.getMessage());
        }
      }
      finally {
        m_isTransformDone = true;
        
        if ((m_inputContentHandler instanceof TransformerHandlerImpl))
        {
          ((TransformerHandlerImpl)m_inputContentHandler).clearCoRoutine();

        }
        

      }
      


    }
    catch (Exception e)
    {

      if (null != m_transformThread) {
        postExceptionFromThread(e);
      } else {
        throw new RuntimeException(e.getMessage());
      }
    }
  }
  





  /**
   * @deprecated
   */
  public TransformSnapshot getSnapshot()
  {
    return new TransformSnapshotImpl(this);
  }
  








  /**
   * @deprecated
   */
  public void executeFromSnapshot(TransformSnapshot ts)
    throws TransformerException
  {
    ElemTemplateElement template = getMatchedTemplate();
    int child = getMatchedNode();
    
    pushElemTemplateElement(template);
    m_xcontext.pushCurrentNode(child);
    executeChildTemplates(template, true);
  }
  




  /**
   * @deprecated
   */
  public void resetToStylesheet(TransformSnapshot ts)
  {
    ((TransformSnapshotImpl)ts).apply(this);
  }
  








  public void stopTransformation() {}
  







  public short getShouldStripSpace(int elementHandle, DTM dtm)
  {
    try
    {
      WhiteSpaceInfo info = m_stylesheetRoot.getWhiteSpaceInfo(m_xcontext, elementHandle, dtm);
      

      if (null == info)
      {
        return 3;
      }
      



      return info.getShouldStripSpace() ? 2 : 1;
    }
    catch (TransformerException se) {}
    


    return 3;
  }
  






  public void init(ToXMLSAXHandler h, Transformer transformer, ContentHandler realHandler)
  {
    h.setTransformer(transformer);
    h.setContentHandler(realHandler);
  }
  
  public void setSerializationHandler(SerializationHandler xoh)
  {
    m_serializationHandler = xoh;
  }
  










  public void fireGenerateEvent(int eventType, char[] ch, int start, int length)
  {
    GenerateEvent ge = new GenerateEvent(this, eventType, ch, start, length);
    m_traceManager.fireGenerateEvent(ge);
  }
  







  public void fireGenerateEvent(int eventType, String name, Attributes atts)
  {
    GenerateEvent ge = new GenerateEvent(this, eventType, name, atts);
    m_traceManager.fireGenerateEvent(ge);
  }
  



  public void fireGenerateEvent(int eventType, String name, String data)
  {
    GenerateEvent ge = new GenerateEvent(this, eventType, name, data);
    m_traceManager.fireGenerateEvent(ge);
  }
  



  public void fireGenerateEvent(int eventType, String data)
  {
    GenerateEvent ge = new GenerateEvent(this, eventType, data);
    m_traceManager.fireGenerateEvent(ge);
  }
  



  public void fireGenerateEvent(int eventType)
  {
    GenerateEvent ge = new GenerateEvent(this, eventType);
    m_traceManager.fireGenerateEvent(ge);
  }
  


  public boolean hasTraceListeners()
  {
    return m_traceManager.hasTraceListeners();
  }
  
  public boolean getDebug() {
    return m_debug;
  }
  
  public void setDebug(boolean b) {
    m_debug = b;
  }
  


  public boolean getIncremental()
  {
    return m_incremental;
  }
  


  public boolean getOptimize()
  {
    return m_optimizer;
  }
  


  public boolean getSource_location()
  {
    return m_source_location;
  }
}