import java.io.File;



















public class RRBench
{
  private final XSLTBench benchmark = new XSLTBench(new File("./scratch"));
  private final String[] args;
  
  public static void main(String[] paramArrayOfString) throws Exception
  {
    RRBench localRRBench = new RRBench(paramArrayOfString);
    localRRBench.preIteration();
    localRRBench.iterate();
    localRRBench.postIteration();
    localRRBench.cleanup();
  }
  
  public RRBench(String[] paramArrayOfString) throws Exception {
    args = paramArrayOfString;
  }
  
  public void preIteration() throws Exception {
    if (args.length > 0) {
      benchmark.createWorkers(Integer.parseInt(args[0]));
    } else {
      benchmark.createWorkers(Runtime.getRuntime().availableProcessors());
    }
  }
  
  public void iterate() throws Exception {
    benchmark.doWork(100);
  }
  
  public void postIteration()
    throws Exception
  {}
  
  public void cleanup()
    throws Exception
  {}
}
