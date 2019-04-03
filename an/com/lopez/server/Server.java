package an.com.lopez.server;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class Server {
	private static final int THREAD_POOL_DEFALUT_SIZE=10;
	private static final int THREAD_POOL_DEFALUT_MAX_SIZE=20;
	private static final int KEEP_ALIVE_TIME=1000;
	private static final int WORK_QUEUE_SIZE=10;
	private static final int PORT=8088;
	
	protected static Map<String, String> htmlMap=new HashMap<String, String>();
	protected static Map<String, CacheModel> ctrlMap=new HashMap<String, CacheModel>();
	
	private String viewPackage;
	private String ctrlPackage;
	private int threadPoolSize;
	private int threadPoolMaxSize;
	private int keepAliveTime;
	private int workQueueSize;
	private int port;
	
	private ServerSocket server;
	private ThreadPoolExecutor executor;
	
	public void startServer() throws IOException{
		System.out.println("Server Start");
		while(true){
			Task task=new WebTask(server.accept());
			executor.execute(task);
		}
	}
	
	public Server(String viewPackage,String ctrlPackage){
		setAttribute(viewPackage,ctrlPackage,THREAD_POOL_DEFALUT_SIZE,THREAD_POOL_DEFALUT_MAX_SIZE,KEEP_ALIVE_TIME,WORK_QUEUE_SIZE,PORT);
		try {
			init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Server(String viewPackage,String ctrlPackage,int threadPoolSize,int threadPoolMaxSize,int keepAliveTime,int workQueueSize,int port){
		setAttribute(viewPackage,ctrlPackage,threadPoolSize,threadPoolMaxSize,keepAliveTime,workQueueSize,port);
		try {
			init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void setAttribute(String viewPackage,String ctrlPackage,int threadPoolSize,int threadPoolMaxSize,int keepAliveTime,int workQueueSize,int port){
		this.ctrlPackage=ctrlPackage;
		this.viewPackage=viewPackage;
		this.threadPoolSize=threadPoolSize;
		this.threadPoolMaxSize=threadPoolMaxSize;
		this.keepAliveTime=keepAliveTime;
		this.workQueueSize=workQueueSize;
		this.port=port;
	}
	
	
	private void init() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		server = new ServerSocket(port);
		executor=new ThreadPoolExecutor(threadPoolSize, threadPoolMaxSize, keepAliveTime, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(workQueueSize));
		ctrlCache();
		htmlCache();
	}
	private void ctrlCache() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		Enumeration<URL> iterator=Thread.currentThread().getContextClassLoader().getResources(ctrlPackage.replace(".", "/"));
		URL url=null;
		File file=null;
		File[] files=null;
		Class<?> class1=null;
		String className=null;
		while(iterator.hasMoreElements()){
			url=iterator.nextElement();
			if("file".equals(url.getProtocol())){
				file=new File(url.getPath());
				if(file.isDirectory()){
					files=file.listFiles();
					for(File f:files){
						className=f.getName().substring(0,f.getName().lastIndexOf("."));
						if(f.getName().endsWith("class"))
						class1=Thread.currentThread().getContextClassLoader().loadClass(ctrlPackage+"."+className);
						Method[] methods=null;
						Object object=null;
						if(class1.isAnnotationPresent(CtrlClass.class)){
							object=class1.newInstance();
							methods=class1.getMethods();
							for(Method method: methods){
								if (method.isAnnotationPresent(CtrlMethod.class)) {
									CtrlMethod c=method.getAnnotation(CtrlMethod.class);
									CacheModel cache=new CacheModel(c.value(), object, method,method.getReturnType(),method.getParameterTypes());
									ctrlMap.put(c.value(), cache);
								}
							}
						}
					}
				}
			}
		}
	}
	private void htmlCache() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		Enumeration<URL> iterator=Thread.currentThread().getContextClassLoader().getResources(viewPackage.replace(".", "/"));
		URL url=null;
		File file=null;
		File[] files=null;
		Class<?> class1=null;
		String className=null;
		while(iterator.hasMoreElements()){
			url=iterator.nextElement();
			if("file".equals(url.getProtocol())){
				file=new File(url.getPath());
				if(file.isDirectory()){
					files=file.listFiles();
					for(File f:files){
						className=f.getName().substring(0,f.getName().lastIndexOf("."));
						if(f.getName().endsWith("html") || f.getName().endsWith("jsp")){
							htmlMap.put(className, f.getAbsolutePath());
						}
					}
				}
			}
		}
	}

}
