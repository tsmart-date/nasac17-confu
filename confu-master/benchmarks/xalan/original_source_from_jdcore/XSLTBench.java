import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Properties;
import javax.xml.transform.ErrorListener;
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
    
    public synchronized void push(String paramString) {
      _queue.add(paramString);
      notify();
    }
    
    public synchronized String pop() {
      while (_queue.isEmpty()) {
        try {
          wait();
        }
        catch (InterruptedException localInterruptedException) {}
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
    

    public XalanWorker(XSLTBench.WorkQueue paramWorkQueue, int paramInt)
    {
      _queue = paramWorkQueue;
      _id = paramInt;
    }
    
    public void run()
    {
      try
      {
        FileOutputStream localFileOutputStream = new FileOutputStream(new File(scratch, "xalan.out." + _id));
        StreamResult localStreamResult = new StreamResult(localFileOutputStream);
        for (;;) {
          String str = _queue.pop();
          
          if (str.equals(""))
            break;
          Transformer localTransformer = _template.newTransformer();
          localTransformer.setErrorListener(this);
          FileInputStream localFileInputStream = new FileInputStream(new File(scratch, str));
          StreamSource localStreamSource = new StreamSource(localFileInputStream);
          localTransformer.transform(localStreamSource, localStreamResult);
          localFileInputStream.close();
        }
      } catch (TransformerConfigurationException localTransformerConfigurationException) {
        localTransformerConfigurationException.printStackTrace();
      } catch (TransformerException localTransformerException) {
        localTransformerException.printStackTrace();
      } catch (IOException localIOException) {
        localIOException.printStackTrace();
      }
    }
    

    public void error(TransformerException paramTransformerException)
      throws TransformerException
    {
      throw paramTransformerException;
    }
    
    public void fatalError(TransformerException paramTransformerException) throws TransformerException {
      throw paramTransformerException;
    }
    

    public void warning(TransformerException paramTransformerException)
      throws TransformerException
    {}
  }
  

  Templates _template = null;
  

  XSLTBench.WorkQueue _workQueue = null;
  

  XSLTBench.XalanWorker[] _workers = null;
  
  public XSLTBench(File paramFile)
    throws Exception
  {
    scratch = paramFile;
    Properties localProperties = System.getProperties();
    if (!Version.getVersion().equals("Xalan Java 2.7.1")) {
      System.err.println("***  Incorrect version of Xalan in use!");
      System.err.println("***     Should be 'Xalan Java 2.7.1',");
      System.err.println("***     actually is '" + Version.getVersion() + "').");
      System.err.println("***  To fix this, extract the included xalan.jar:");
      System.err.println("***     unzip " + localProperties.get("java.class.path") + " xalan.jar");
      System.err.println("***  and override your jvm's boot classpath:");
      System.err.println("***     java -Xbootclasspath/p:xalan.jar [...] ");
      throw new Exception("Please fix your bootclasspath and try again.");
    }
    

    String str1 = "javax.xml.transform.TransformerFactory";
    String str2 = "org.apache.xalan.processor.TransformerFactoryImpl";
    localProperties.put(str1, str2);
    System.setProperties(localProperties);
    

    StreamSource localStreamSource = new StreamSource(new File(paramFile, "xalan/xmlspec.xsl"));
    

    TransformerFactory localTransformerFactory = TransformerFactory.newInstance("org.apache.xalan.processor.TransformerFactoryImpl", XSLTBench.XalanWorker.class.getClassLoader());
    

    _template = localTransformerFactory.newTemplates(localStreamSource);
    

    _workQueue = new XSLTBench.WorkQueue();
  }
  





  public void createWorkers(int paramInt)
  {
    workers = paramInt;
    
    if (_workers == null)
      _workers = new XSLTBench.XalanWorker[paramInt];
    for (int i = 0; i < paramInt; i++) {
      _workers[i] = new XSLTBench.XalanWorker(_workQueue, i);
      _workers[i].start();
    }
  }
  





  public void doWork(int paramInt)
    throws InterruptedException
  {
    for (int i = 0; i < paramInt; i++) {
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
    

    for (i = 0; i < workers; i++) {
      _workQueue.push("");
    }
    for (i = 0; i < workers; i++)
    {

      _workers[i].join();
    }
  }
}
