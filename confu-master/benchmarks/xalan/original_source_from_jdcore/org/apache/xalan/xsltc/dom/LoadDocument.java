package org.apache.xalan.xsltc.dom;

import java.io.FileNotFoundException;
import javax.xml.transform.stream.StreamSource;
import org.apache.xalan.xsltc.DOM;
import org.apache.xalan.xsltc.DOMCache;
import org.apache.xalan.xsltc.DOMEnhancedForDTM;
import org.apache.xalan.xsltc.TransletException;
import org.apache.xalan.xsltc.runtime.AbstractTranslet;
import org.apache.xalan.xsltc.trax.TemplatesImpl;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMAxisIterator;
import org.apache.xml.dtm.DTMManager;
import org.apache.xml.dtm.ref.EmptyIterator;
import org.apache.xml.utils.SystemIDResolver;





































public final class LoadDocument
{
  private static final String NAMESPACE_FEATURE = "http://xml.org/sax/features/namespaces";
  
  public LoadDocument() {}
  
  public static DTMAxisIterator documentF(Object arg1, DTMAxisIterator arg2, String xslURI, AbstractTranslet translet, DOM dom)
    throws TransletException
  {
    String baseURI = null;
    int arg2FirstNode = arg2.next();
    if (arg2FirstNode == -1)
    {
      return EmptyIterator.getInstance();
    }
    


    baseURI = dom.getDocumentURI(arg2FirstNode);
    if (!SystemIDResolver.isAbsoluteURI(baseURI)) {
      baseURI = SystemIDResolver.getAbsoluteURIFromRelative(baseURI);
    }
    try
    {
      if ((arg1 instanceof String)) {
        if (((String)arg1).length() == 0) {
          return document(xslURI, "", translet, dom);
        }
        return document((String)arg1, baseURI, translet, dom);
      }
      if ((arg1 instanceof DTMAxisIterator)) {
        return document((DTMAxisIterator)arg1, baseURI, translet, dom);
      }
      String err = "document(" + arg1.toString() + ")";
      throw new IllegalArgumentException(err);
    }
    catch (Exception e) {
      throw new TransletException(e);
    }
  }
  





  public static DTMAxisIterator documentF(Object arg, String xslURI, AbstractTranslet translet, DOM dom)
    throws TransletException
  {
    try
    {
      if ((arg instanceof String)) {
        if (xslURI == null) {
          xslURI = "";
        }
        String baseURI = xslURI;
        if (!SystemIDResolver.isAbsoluteURI(xslURI)) {
          baseURI = SystemIDResolver.getAbsoluteURIFromRelative(xslURI);
        }
        String href = (String)arg;
        if (href.length() == 0) {
          href = "";
          


          TemplatesImpl templates = (TemplatesImpl)translet.getTemplates();
          DOM sdom = null;
          if (templates != null) {
            sdom = templates.getStylesheetDOM();
          }
          



          if (sdom != null) {
            return document(sdom, translet, dom);
          }
          
          return document(href, baseURI, translet, dom, true);
        }
        

        return document(href, baseURI, translet, dom);
      }
      if ((arg instanceof DTMAxisIterator)) {
        return document((DTMAxisIterator)arg, null, translet, dom);
      }
      String err = "document(" + arg.toString() + ")";
      throw new IllegalArgumentException(err);
    }
    catch (Exception e) {
      throw new TransletException(e);
    }
  }
  

  private static DTMAxisIterator document(String uri, String base, AbstractTranslet translet, DOM dom)
    throws Exception
  {
    return document(uri, base, translet, dom, false);
  }
  

  private static DTMAxisIterator document(String uri, String base, AbstractTranslet translet, DOM dom, boolean cacheDOM)
    throws Exception
  {
    try
    {
      String originalUri = uri;
      MultiDOM multiplexer = (MultiDOM)dom;
      

      if ((base != null) && (!base.equals(""))) {
        uri = SystemIDResolver.getAbsoluteURI(uri, base);
      }
      


      if ((uri == null) || (uri.equals(""))) {
        return EmptyIterator.getInstance();
      }
      

      int mask = multiplexer.getDocumentMask(uri);
      if (mask != -1) {
        DOM newDom = ((DOMAdapter)multiplexer.getDOMAdapter(uri)).getDOMImpl();
        
        if ((newDom instanceof DOMEnhancedForDTM)) {
          return new SingletonIterator(((DOMEnhancedForDTM)newDom).getDocument(), true);
        }
      }
      



      DOMCache cache = translet.getDOMCache();
      

      mask = multiplexer.nextMask();
      DOM newdom;
      if (cache != null) {
        DOM newdom = cache.retrieveDocument(base, originalUri, translet);
        if (newdom == null) {
          Exception e = new FileNotFoundException(originalUri);
          throw new TransletException(e);
        }
        
      }
      else
      {
        XSLTCDTMManager dtmManager = (XSLTCDTMManager)multiplexer.getDTMManager();
        
        DOMEnhancedForDTM enhancedDOM = (DOMEnhancedForDTM)dtmManager.getDTM(new StreamSource(uri), false, null, true, false, translet.hasIdCall(), cacheDOM);
        


        newdom = enhancedDOM;
        

        if (cacheDOM) {
          TemplatesImpl templates = (TemplatesImpl)translet.getTemplates();
          if (templates != null) {
            templates.setStylesheetDOM(enhancedDOM);
          }
        }
        
        translet.prepassDocument(enhancedDOM);
        enhancedDOM.setDocumentURI(uri);
      }
      

      DOMAdapter domAdapter = translet.makeDOMAdapter(newdom);
      multiplexer.addDOMAdapter(domAdapter);
      

      translet.buildKeys(domAdapter, null, null, newdom.getDocument());
      

      return new SingletonIterator(newdom.getDocument(), true);
    } catch (Exception e) {
      throw e;
    }
  }
  



  private static DTMAxisIterator document(DTMAxisIterator arg1, String baseURI, AbstractTranslet translet, DOM dom)
    throws Exception
  {
    UnionIterator union = new UnionIterator(dom);
    int node = -1;
    
    while ((node = arg1.next()) != -1) {
      String uri = dom.getStringValueX(node);
      
      if (baseURI == null) {
        baseURI = dom.getDocumentURI(node);
        if (!SystemIDResolver.isAbsoluteURI(baseURI))
          baseURI = SystemIDResolver.getAbsoluteURIFromRelative(baseURI);
      }
      union.addIterator(document(uri, baseURI, translet, dom));
    }
    return union;
  }
  











  private static DTMAxisIterator document(DOM newdom, AbstractTranslet translet, DOM dom)
    throws Exception
  {
    DTMManager dtmManager = ((MultiDOM)dom).getDTMManager();
    
    if ((dtmManager != null) && ((newdom instanceof DTM))) {
      ((DTM)newdom).migrateTo(dtmManager);
    }
    
    translet.prepassDocument(newdom);
    

    DOMAdapter domAdapter = translet.makeDOMAdapter(newdom);
    ((MultiDOM)dom).addDOMAdapter(domAdapter);
    

    translet.buildKeys(domAdapter, null, null, newdom.getDocument());
    


    return new SingletonIterator(newdom.getDocument(), true);
  }
}
