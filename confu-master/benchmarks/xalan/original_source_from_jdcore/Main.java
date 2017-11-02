import java.io.File;


























public class Main
{
  public Main() {}
  
  public static void main(String[] paramArrayOfString)
    throws Exception
  {
    XSLTBench localXSLTBench = new XSLTBench(new File("./scratch"));
    if (paramArrayOfString.length > 0) {
      localXSLTBench.createWorkers(Integer.parseInt(paramArrayOfString[0]));
    } else {
      localXSLTBench.createWorkers(Runtime.getRuntime().availableProcessors());
    }
    localXSLTBench.doWork(100);
  }
}
