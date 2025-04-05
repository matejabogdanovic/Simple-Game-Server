package Server;

public class ServerTimerThread extends Thread{
	private volatile boolean tick = false;
	
	boolean isTick() {
		return this.tick;
	}
	 
	@Override
	public void run() { 
		try {
			while(!Thread.currentThread().isInterrupted()) {
				Thread.sleep(100);
				synchronized (this) {
					this.tick = true;
					this.notifyAll();
				}
				Thread.sleep(50); 
				synchronized (this) {
					this.tick = false;
				}
				
			}
		} catch (InterruptedException e) {
			
		}
		finally {
			synchronized(this) {
				this.notifyAll();
			}
			System.out.println("Closing timer.");
		}
		
	}
}
