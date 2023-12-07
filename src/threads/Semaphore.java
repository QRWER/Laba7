package threads;

public class Semaphore {
    private boolean canGenerate = true;

    public synchronized void beginGenerator() throws InterruptedException {
        while (!canGenerate) {
            wait();
        }
    }
    public synchronized void endGenerator() {
        canGenerate= false;
        notifyAll();
    }
    public synchronized void beginIntegrator() throws InterruptedException {
        while(canGenerate) {
            wait();
        }
    }
    public synchronized void endIntegrator() {
        canGenerate = true;
        notifyAll();
    }
}
