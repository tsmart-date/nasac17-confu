package org.dacapo.xalan;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Properties;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.xalan.Version;












public class XSLTBench
{
  final boolean verbose = false;
  

  private final String XALAN_VERSION = "Xalan Java 2.7.1";
  
  private final File scratch;
  
  int workers;
  

  class WorkQueue
  {
    LinkedList<String> _queue = new LinkedList();
    
    WorkQueue() {}
    
    public synchronized void push(String filename) {
      _queue.add(filename);
      notify();
    }
    
    public synchronized String pop() {
      while (_queue.isEmpty()) {
        try {
          wait();
        }
        catch (InterruptedException e) {}
      }
      

      return (String)_queue.removeFirst();
    }
  }
  


  class XalanWorker
    extends Thread
    implements ErrorListener
  {
    XSLTBench.WorkQueue _queue;
    

    int _id;
    

    public XalanWorker(XSLTBench.WorkQueue queue, int id)
    {
      _queue = queue;
      _id = id;
    }
    
    public void run()
    {
      try
      {
        FileOutputStream outputStream = new FileOutputStream(new File(scratch, "xalan.out." + _id));
        Result outFile = new StreamResult(outputStream);
        for (;;) {
          String fileName = _queue.pop();
          
          if (fileName.equals(""))
            break;
          Transformer transformer = _template.newTransformer();
          transformer.setErrorListener(this);
          FileInputStream inputStream = new FileInputStream(new File(scratch, fileName));
          Source inFile = new StreamSource(inputStream);
          transformer.transform(inFile, outFile);
          inputStream.close();
        }
      } catch (TransformerConfigurationException e) {
        e.printStackTrace();
      } catch (TransformerException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    

    public void error(TransformerException exception)
      throws TransformerException
    {
      throw exception;
    }
    
    public void fatalError(TransformerException exception) throws TransformerException {
      throw exception;
    }
    

    public void warning(TransformerException exception)
      throws TransformerException
    {}
  }
  

  Templates _template = null;
  

  WorkQueue _workQueue = null;
  

  XalanWorker[] _workers = null;
  
  public XSLTBench(File scratch)
    throws Exception
  {
    this.scratch = scratch;
    Properties props = System.getProperties();
    if (!Version.getVersion().equals("Xalan Java 2.7.1")) {
      System.err.println("***  Incorrect version of Xalan in use!");
      System.err.println("***     Should be 'Xalan Java 2.7.1',");
      System.err.println("***     actually is '" + Version.getVersion() + "').");
      System.err.println("***  To fix this, extract the included xalan.jar:");
      System.err.println("***     unzip " + props.get("java.class.path") + " xalan.jar");
      System.err.println("***  and override your jvm's boot classpath:");
      System.err.println("***     java -Xbootclasspath/p:xalan.jar [...] ");
      throw new Exception("Please fix your bootclasspath and try again.");
    }
    

    String key = "javax.xml.transform.TransformerFactory";
    String value = "org.apache.xalan.processor.TransformerFactoryImpl";
    props.put(key, value);
    System.setProperties(props);
    

    Source stylesheet = new StreamSource(new File(scratch, "xalan/xmlspec.xsl"));
    TransformerFactory factory = TransformerFactory.newInstance();
    _template = factory.newTemplates(stylesheet);
    

    _workQueue = new WorkQueue();
  }
  





  public void createWorkers(int workers)
  {
    this.workers = workers;
    
    if (_workers == null)
      _workers = new XalanWorker[workers];
    for (int i = 0; i < workers; i++) {
      _workers[i] = new XalanWorker(_workQueue, i);
      _workers[i].start();
    }
  }
  





  public void doWork(int nRuns)
    throws InterruptedException
  {
    for (int iRun = 0; iRun < nRuns; iRun++) {
      _workQueue.push("xalan/acks.xml");
      _workQueue.push("xalan/binding.xml");
      _workQueue.push("xalan/changes.xml");
      _workQueue.push("xalan/concepts.xml");
      _workQueue.push("xalan/controls.xml");
      _workQueue.push("xalan/datatypes.xml");
      _workQueue.push("xalan/expr.xml");
      _workQueue.push("xalan/intro.xml");
      _workQueue.push("xalan/model.xml");
      _workQueue.push("xalan/prod-notes.xml");
      _workQueue.push("xalan/references.xml");
      _workQueue.push("xalan/rpm.xml");
      _workQueue.push("xalan/schema.xml");
      _workQueue.push("xalan/structure.xml");
      _workQueue.push("xalan/template.xml");
      _workQueue.push("xalan/terms.xml");
      _workQueue.push("xalan/ui.xml");
    }
    

    for (int i = 0; i < workers; i++) {
      _workQueue.push("");
    }
    for (int i = 0; i < workers; i++)
    {

      _workers[i].join();
    }
  }
}
