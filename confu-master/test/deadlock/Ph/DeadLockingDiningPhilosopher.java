package test.deadlock.Ph;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DeadLockingDiningPhilosopher {
    public static void main(String[] args) throws Exception{
        int ponder = 5;
        if(args.length> 0)
            ponder = Integer.parseInt(args[0]);
        int size = 5;
        if(args.length >1)
            size = Integer.parseInt(args[1]);

        ExecutorService exec = Executors.newCachedThreadPool();
        Chopstick[] sticks = new Chopstick[size];
        for(int i=0; i<size; i++)
            sticks[i] = new Chopstick();
        for(int i =0; i< size; i++)
            exec.execute(new Philosopher(sticks[i], sticks[(i+1) % size], i, ponder)); //n个哲学家n支筷子循坏使用
        if(args.length == 3 && args[2].equals("timeout"))
            TimeUnit.SECONDS.sleep(5);
        else {
            System.out.println("please Press 'Enter' to quit");
            System.in.read();
        }
        exec.shutdown();
    }
}