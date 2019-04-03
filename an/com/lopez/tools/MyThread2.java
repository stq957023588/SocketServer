package an.com.lopez.tools;

public class MyThread2 implements Runnable{

	public void run() {
		// TODO Auto-generated method stub
		System.out.println("thread2 start");
		try {
			waitAndNotify();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void synchronizedTest() {
		synchronized (LopezTest.queue) {
			System.out.println("Get queue");
		}
	}
	
	
	private void joinTest() throws InterruptedException {
		System.out.println("MyThread2 start");
		Thread.sleep(5000);
		System.out.println("MyThread2 end");
	}
	
	private void waitAndNotify() throws InterruptedException {
		synchronized (LopezTest.queue) {
			System.out.println("2 GET");
			Thread.sleep(3000);
			System.out.println("notify");
			LopezTest.queue.notify();
		}
	}
	
	private void custom() throws Exception {
		while(true){
			synchronized (LopezTest.queue) {
				if(LopezTest.queue.size()>0){
					LopezTest.queue.notify();
					System.out.println(LopezTest.queue.poll()+"complete");
				}else{
					System.out.println("mission empty");
					LopezTest.queue.wait();
				}
			}
			Thread.sleep(1000);
		}
	}

}
