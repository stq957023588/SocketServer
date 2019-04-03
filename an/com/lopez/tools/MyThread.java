package an.com.lopez.tools;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class MyThread<T> implements Runnable {
	int i;
	private T t;
	private String methodName;
	private Map<Class, Object> params;
	public MyThread(){
		
	}
	public MyThread(int i){
		this.i=i;
	}
	public MyThread(T t,String methodName,Map<Class, Object> params){
		this.t=t;
		this.methodName=methodName;
		this.params=params;
	}
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("thread1 start");
		try {
			simple();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	private void joinTest() throws InterruptedException {
		MyThread2 m1=new MyThread2();
		Thread thread=new Thread(m1);
		thread.start();
		thread.join();
	}
	
	private void reflex() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		Set<Class> set=params.keySet();
		Class[] cls = {};
		cls=set.toArray(cls);
		Method method=t.getClass().getMethod(methodName,cls);
		List<Object> list=new ArrayList<Object>();
		for(Class clazz:set){
			list.add(params.get(clazz));
		}
		method.invoke(t, list.toArray());
	}
	
	private void simple() throws InterruptedException {
		System.out.println("Mission"+i+" Start");
		Thread.sleep(10000);
		System.out.println("Mission"+i+" Complete");
		
	}
	
	private void synchronizedTest() throws InterruptedException {
		synchronized (LopezTest.queue) {
			System.out.println("sleeping");
			Thread.sleep(6000);
			System.out.println("wake..");
		}
	}
	
	
	
	
	private void waitAndNotify() throws InterruptedException {
		synchronized (LopezTest.queue) {
			Thread.sleep(5000);
			System.out.println("waiting..");
			LopezTest.queue.wait();
			System.out.println("wake..");
		}
	}
	
	private void produce() throws Exception {
		while(true){
			synchronized (LopezTest.queue) {
				if(LopezTest.queue.size()<10){
					LopezTest.queue.notify();
					System.out.println("mission"+i+" publish");
					LopezTest.queue.add("mission"+i++);
				}else{
					System.out.println("mission full");
					LopezTest.queue.wait();
				}
			}
			Thread.sleep(100);
		}
		
	}
}
