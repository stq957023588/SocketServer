package an.com.lopez.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSONObject;



public class Server {
	private static final int THREAD_POOL_DEFALUT_SIZE=100;
	private static final int THREAD_POOL_DEFALUT_MAX_SIZE=500;
	private static final int KEEP_ALIVE_TIME=1000;
	private static final int WORK_QUEUE_SIZE=10;
	private static final int PORT=8088;
	
	protected Map<String, CacheModel> ctrlMap=new HashMap<String, CacheModel>();
	protected Filter filter;
	
	private Map<String,CacheModel> aopMap=new HashMap<String, CacheModel>();
	
	@Config("ctrlPackage")
	private String ctrlPackage;
	@Config("aopPackage")
	private String aopPackage;
	@Config("threadPoolSize")
	private int threadPoolSize;
	@Config("threadPoolMaxSize")
	private int threadPoolMaxSize;
	@Config("keepAliveTime")
	private int keepAliveTime;
	@Config("workQueueSize")
	private int workQueueSize;
	@Config("port")
	private int port;
	
	private ServerSocket server;
	private ThreadPoolExecutor executor;
	public static Context context;
	
	public static Server getServer(String configPath){
		Server server=null;
		try {
			Class<?> clazz=Class.forName("an.com.lopez.server.Server");
			server=(Server)clazz.newInstance();
			server.getClass().getDeclaredMethod("setDefaultAttribute", null).invoke(server, null);
			
			Properties p=new Properties();
			InputStream in=Thread.currentThread().getContextClassLoader().getResourceAsStream(configPath);
			p.load(in);
			
			Field[] fields=server.getClass().getDeclaredFields();
			for(Field field:fields){
				field.setAccessible(true);
				if(field.isAnnotationPresent(Config.class)){
					Config config=field.getAnnotation(Config.class);
					String value=p.getProperty(config.value());
					if(value!=null && !"".equals(value)){
						if(int.class==field.getType())
							field.set(server, Integer.parseInt(value));
						else
							field.set(server, value);
					}
				}
			}
			server.getClass().getDeclaredMethod("init", null).invoke(server, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return server;
	}
	
	public void startServer() throws IOException{
		System.out.println("Server Start");
		while(true){
			Task task=new WebTask(server.accept());
			executor.execute(task);
		}
	}
	public Server(){
		
	}
	/**
	 * 
	 * @param viewPackage 视图包，存放HTML
	 * @param ctrlPackage 后端地址，存放controller层
	 */
	public Server(String viewPackage,String ctrlPackage){
		setAttribute(viewPackage,ctrlPackage,THREAD_POOL_DEFALUT_SIZE,THREAD_POOL_DEFALUT_MAX_SIZE,KEEP_ALIVE_TIME,WORK_QUEUE_SIZE,PORT);
		try {
			init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Server(String viewPackage,String ctrlPackage,int threadPoolSize,int threadPoolMaxSize,int keepAliveTime,int workQueueSize,int port){
		setAttribute(viewPackage,ctrlPackage,threadPoolSize,threadPoolMaxSize,keepAliveTime,workQueueSize,port);
		try {
			init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void setAttribute(String viewPackage,String ctrlPackage,int threadPoolSize,int threadPoolMaxSize,int keepAliveTime,int workQueueSize,int port){
		this.ctrlPackage=ctrlPackage;
		this.threadPoolSize=threadPoolSize;
		this.threadPoolMaxSize=threadPoolMaxSize;
		this.keepAliveTime=keepAliveTime;
		this.workQueueSize=workQueueSize;
		this.port=port;
	}
	private void setDefaultAttribute(){
		this.threadPoolSize=THREAD_POOL_DEFALUT_SIZE;
		this.threadPoolMaxSize=THREAD_POOL_DEFALUT_MAX_SIZE;
		this.keepAliveTime=KEEP_ALIVE_TIME;
		this.workQueueSize=WORK_QUEUE_SIZE;
		this.port=PORT;
	}
	
	
	private void init() throws Exception{
		server = new ServerSocket(port);
		executor=new ThreadPoolExecutor(threadPoolSize, threadPoolMaxSize, keepAliveTime, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(workQueueSize));
		context=new Context();
		
		aopCache();
		ctrlCache();
		filter();
	}
	
	private void aopCache() throws Exception{
		if(aopPackage==null || "".equals(aopPackage))
			return;
		File[] files=new File(context.clazzPath+(aopPackage.replace(".", "/"))).listFiles();
		for(File file:files){
			if(file.getName().endsWith("class")){
				Class<?> clazz=Thread.currentThread().getContextClassLoader().loadClass(aopPackage+"."+file.getName().substring(0,file.getName().lastIndexOf(".")));
				if(clazz.isAnnotationPresent(AopClass.class)){
					Object obj=clazz.newInstance();
					Method[] methods=obj.getClass().getMethods();
					for(Method method:methods){
						if(method.isAnnotationPresent(AopFun.class)){
							AopFun fun=method.getAnnotation(AopFun.class);
							CacheModel cache=new CacheModel(null, obj, method, method.getReturnType(), method.getParameterTypes());
							aopMap.put(fun.value(), cache);
						}
					}
				}
			}
		}
	}
	
	private void ctrlCache() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		if(ctrlPackage==null || "".equals(ctrlPackage))
			return;
		String className=null;
		File[] files=new File(context.clazzPath+(ctrlPackage.replace(".", "/"))).listFiles();
		for(File f:files){
			className=f.getName().substring(0,f.getName().lastIndexOf("."));
			if(f.getName().endsWith("class")){
				Class<?> class1=Thread.currentThread().getContextClassLoader().loadClass(ctrlPackage+"."+className);
				Method[] methods=null;
				Object object=null;
				if(class1.isAnnotationPresent(CtrlClass.class)){
					object=class1.newInstance();
					methods=class1.getMethods();
					for(Method method: methods){
						if (method.isAnnotationPresent(CtrlMethod.class)) {
							CtrlMethod c=method.getAnnotation(CtrlMethod.class);
							CacheModel cache=new CacheModel(c.value(), object, method,method.getReturnType(),method.getParameterTypes());
							if(method.isAnnotationPresent(AopBegin.class)){
								AopBegin begin=method.getAnnotation(AopBegin.class);
								cache.setBegin(aopMap.get(begin.value()));
							}
							if(method.isAnnotationPresent(AopEnd.class)){
								AopEnd begin=method.getAnnotation(AopEnd.class);
								cache.setEnd(aopMap.get(begin.value()));
							}
							ctrlMap.put(c.value(), cache);
						}
					}
				}
			}					
		}
	}
	private void filter(){
		URL url=Thread.currentThread().getContextClassLoader().getResource("");
		try {
			listFile(new File(url.getPath()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void listFile(File file) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		String path=Thread.currentThread().getContextClassLoader().getResource("").getPath().replace("/", "\\").substring(1);
		if(file.isDirectory()){
			for(File f:file.listFiles()){
				listFile(f);
			}
		}else{
			String classStr=file.getAbsolutePath().replace(path, "");
			if(classStr.endsWith(".class")){
				Class<?> clazz=Thread.currentThread().getContextClassLoader().loadClass(classStr.substring(0,classStr.lastIndexOf(".")).replace("\\", "."));
				if(Filter.class.isAssignableFrom(clazz) && clazz!=Filter.class){
					Filter f=(Filter)clazz.newInstance();
					if(filter==null){
						filter=f;
					}else{
						if(filter.getClass().isAnnotationPresent(FilterConfig.class)){
							if(f.getClass().isAnnotationPresent(FilterConfig.class)){
								FilterConfig thisFc=filter.getClass().getAnnotation(FilterConfig.class);
								FilterConfig Fc=f.getClass().getAnnotation(FilterConfig.class);
								if(thisFc.value()>Fc.value()){
									Filter temp=filter;
									filter=f;
									filter.setNext(temp);
								}else{
									filter.setNext(f);
								}
							}else{
								Filter temp=filter;
								filter=f;
								filter.setNext(temp);
							}
						}else{
							filter.setNext(f);
						}
					}
				}
			}
		}
	}
	private class WebTask extends Task{

		public WebTask(Socket socket) throws IOException {
			super(socket);
			// TODO Auto-generated constructor stub
			in = socket.getInputStream();
			out= socket.getOutputStream();
			request=new HttpRequest(in);
			response=new HttpResponse(out);
		}

		@Override
		public void execute() throws Exception {
			// TODO Auto-generated method stub
			if(filter==null){
				doSomething();
			}else{
				filter.setRequest(request);
				filter.setResponse(response);
				if(filter.start()){
					doSomething();
				}
			}
			
			out.close();
			in.close();
			socket.close();
		}

		private void doSomething() throws Exception{
			String goToUrl=((HttpRequest) request).getGotoURL();
			File file=null;
			if(ctrlMap.containsKey(goToUrl)){
				CacheModel cache=ctrlMap.get(goToUrl);
				cache.setRequest(request);
				Object obj=cache.invoke(request.getParams());
				if(obj!=null){
					if(obj.getClass() == String.class){
						response.write(obj.toString());
					}else if(obj.getClass()== HtmlModel.class){
						response.write(((HtmlModel)obj).getHtml());
					}else{
						response.write(JSONObject.toJSONString(obj));
					}
				}else{
					((HttpResponse)response).write();
				}
			}else if((file=new File(Server.context.resourcesPath+goToUrl)).exists() && (goToUrl!=null || "".equals(goToUrl.trim()))){
				InputStream in=new FileInputStream(file);
				((HttpResponse)response).write(in);
			}else{
				response.error404();
			}
			
		}
	}
	

	public String getCtrlPackage() {
		return ctrlPackage;
	}

	public void setCtrlPackage(String ctrlPackage) {
		this.ctrlPackage = ctrlPackage;
	}

	public int getThreadPoolSize() {
		return threadPoolSize;
	}

	public void setThreadPoolSize(int threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
	}

	public int getThreadPoolMaxSize() {
		return threadPoolMaxSize;
	}

	public void setThreadPoolMaxSize(int threadPoolMaxSize) {
		this.threadPoolMaxSize = threadPoolMaxSize;
	}

	public int getKeepAliveTime() {
		return keepAliveTime;
	}

	public void setKeepAliveTime(int keepAliveTime) {
		this.keepAliveTime = keepAliveTime;
	}

	public int getWorkQueueSize() {
		return workQueueSize;
	}

	public void setWorkQueueSize(int workQueueSize) {
		this.workQueueSize = workQueueSize;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	
}
