package test.deadlock.Ph;
public class Chopstick {
    private boolean taken = false;
    public synchronized void take() throws InterruptedException { //当一个任务拿起该筷子时，将taken赋值true，别的任务就在take()上等待
        while(taken)
            wait();
        taken = true;
    }
    public synchronized void drop(){ //当持有筷子的任务drop时，taken赋值false，在该筷子对象的taken()上等待的任务就可以获得该筷子
        taken = false;
        notifyAll();
    }
}