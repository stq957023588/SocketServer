package an.com.lopez.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import an.com.lopez.server.CtrlClass;
import an.com.lopez.server.CtrlMethod;

public class SocketServer {
	public static Map<String, CacheModel> map=new HashMap<String, CacheModel>();
	public static Map<String, String> htmlMap=new HashMap<String, String>();
	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws InstantiationException 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Cache("an.com.lopez.tools");
		webServer();
	}
	public static void webServer() throws IOException {
		int port = 9999;
	    ServerSocket server = new ServerSocket(port);
	    
//	     server将一直等待连接的到来
	    System.out.println("Server Start");
	    ThreadPoolExecutor executor=new ThreadPoolExecutor(5, 10, 1000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(5));
	    while(true){
	    	MyTask myTask=new MyTask(server.accept());
	    	executor.execute(myTask);
//	    	System.out.println("线程池中线程数目："+executor.getPoolSize()+"，队列中等待执行的任务数目："+
//	                executor.getQueue().size()+"，已执行玩别的任务数目："+executor.getCompletedTaskCount());
	    }
	}
	
	public static void Cache(String packageName) throws Exception{
		Enumeration<URL> iterator=Thread.currentThread().getContextClassLoader().getResources(packageName.replace(".", "/"));
		URL url=null;
		File file=null;
		File[] files=null;
		Class<?> class1=null;
		String className=null;
		
		File file1=new File("C:\\Users\\yuli\\Desktop\\test.html");
		InputStream inputStream=new FileInputStream(file1);
		byte[]	bytes=new byte[1024];
		int len;
		StringBuffer buffer=new StringBuffer();
		while(true){
			len=inputStream.read(bytes);
			buffer.append(new String(bytes));
			if(len==-1 || len<1024){
				break;
			}
		}
		htmlMap.put("test", buffer.toString());
		
		while(iterator.hasMoreElements()){
			url=iterator.nextElement();
			if("file".equals(url.getProtocol())){
				file=new File(url.getPath());
				if(file.isDirectory()){
					files=file.listFiles();
					for(File f:files){
						
						className=f.getName().substring(0,f.getName().lastIndexOf("."));
//						if (f.getName().endsWith("html")) {
//							InputStream inputStream=new FileInputStream(f);
//							byte[]	bytes=new byte[1024];
//							int len;
//							StringBuffer buffer=new StringBuffer();
//							while(true){
//								len=inputStream.read(bytes);
//								buffer.append(new String(bytes));
//								if(len==-1 || len<1024){
//									break;
//								}
//							}
//							htmlMap.put(className, buffer.toString());
//						}
						
						if(f.getName().endsWith("class"))
						class1=Thread.currentThread().getContextClassLoader().loadClass("an.com.lopez.tools."+className);
						Method[] methods=null;
						Object object=null;
						if(class1.isAnnotationPresent(CtrlClass.class)){
							object=class1.newInstance();
							methods=class1.getMethods();
							for(Method method: methods){
								if (method.isAnnotationPresent(CtrlMethod.class)) {
									CtrlMethod c=method.getAnnotation(CtrlMethod.class);
									CacheModel cache=new CacheModel(c.value(), object, method,method.getReturnType(),method.getParameterTypes());
									map.put(c.value(), cache);
								}
							}
						}
					}
				}
			}
		}
		
		
	}
	
	public static void HtmlCache() {
		
	}

}
