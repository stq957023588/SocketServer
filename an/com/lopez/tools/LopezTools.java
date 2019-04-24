package an.com.lopez.tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class LopezTools {
	
	public static String getIpInfo(String ipStr) throws IOException{
		URL url=new URL(" HTTP://api.ipstack.com/"+ipStr+"?access_key=d782d2822f2385b7f01b10c3a7d1134d&language=zh");
		HttpURLConnection connection=(HttpURLConnection)url.openConnection();
		connection.setRequestMethod("GET");
		InputStream inputStream=connection.getInputStream();
		String result=InputStreamRead(inputStream);
		return result;
	}
	
	public static Properties PropertiesTest() throws Exception{
		Properties p=new Properties();
		InputStream in=Thread.currentThread().getContextClassLoader().getResourceAsStream("an/com/lopez/config/config.properties");
		p.load(in);
		in.close();
		return p;
		
	}
	
	
	public static<AnyType extends Comparable<? super AnyType>> AnyType  findMax(AnyType[] arr){
		int maxIndex=0;
		for(int i=0;i<arr.length;i++){
			if(arr[i].compareTo(arr[maxIndex])>0){
				maxIndex=i;
			}
		}
		return arr[maxIndex];
	}
	
	public static Object getInstance(Class<?> cls) {
		MethodProxy invocationHandler=new MethodProxy();
		Object newProxyInstance=Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, invocationHandler);
		return (Object)newProxyInstance;
	}
	
	public static String InputStreamRead(InputStream inputStream) throws IOException {
		byte[] bytes=new byte[1024];
	    int len=0;
	    StringBuffer sBuffer=new StringBuffer();
	    while(true){
	    	len=inputStream.read(bytes);
	    	if(len<=0){
	    		break;
	    	}
	    	sBuffer.append(new String(bytes, 0, len));
	    }
	    return sBuffer.toString();
	}
	
	public static List<String> getBasicDataTypesName() {
		List<String> list=new ArrayList<String>();
		list.add(String.class.getName());
		list.add(Integer.class.getName());
		list.add(Long.class.getName());
		list.add(Byte.class.getName());
		list.add(Float.class.getName());
		list.add(Character.class.getName());
		list.add(Short.class.getName());
		list.add(Boolean.class.getName());
		list.add(Double.class.getName());
		
		list.add(int.class.getName());
		list.add(double.class.getName());
		list.add(long.class.getName());
		list.add(byte.class.getName());
		list.add(float.class.getName());
		list.add(char.class.getName());
		list.add(short.class.getName());
		list.add(boolean.class.getName());
		return list;
	}
	
	public static String byteToHex(final byte[] hash) {
	    Formatter formatter = new Formatter();
	    for (byte b : hash)
	    {
	        formatter.format("%02x", b);
	    }
	    String result = formatter.toString();
	    formatter.close();
	    return result;
	}
	
	
	public static void Localion(String msg) {
		try {
			File file=new File("C:\\IO_TEST\\socketTest.txt");
		    Writer writer=new FileWriter(file,true);
		    writer.append(msg);
		    writer.flush();
		    writer.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	public static void SocketListener() {
		int port = 8080;
	    ServerSocket server=null;
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
	    
	    // server将一直等待连接的到来
	    System.out.println("server将一直等待连接的到来");
	    
	    ThreadPoolExecutor executor=new ThreadPoolExecutor(5, 10, 1000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(5));
	    while(true){
	    	MyTask myTask=null;
			try {
				myTask = new MyTask(server.accept());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	executor.execute(myTask);
	    	System.out.println("线程池中线程数目："+executor.getPoolSize()+"，队列中等待执行的任务数目："+
	                executor.getQueue().size()+"，已执行玩别的任务数目："+executor.getCompletedTaskCount());
	    }
	}
	
	public static void getFiledsValues(Object obj) {
//		List<String> list=getBasicDataTypesName();
		
		System.out.println(obj.getClass().getName()+"\t属性：---Lopez");
		Field[] fields = obj.getClass().getDeclaredFields();
		for (int i=0;i<fields.length;i++){//遍历
            try {
                //得到属性
                Field field = fields[i];
                //打开私有访问
                field.setAccessible(true);
                //获取属性
                String name = field.getName();
                //获取属性值
                Object value = field.get(obj);
                System.out.println("\t名称："+name+"\t值："+value.toString()+"\t类："+field.getType());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }catch (NullPointerException e) {
				// TODO: handle exception
            	Field field = fields[i];
            	System.out.println("\t名称："+field.getName()+"\t值：null\t类："+field.getType());
			}
        }
		System.out.println("--------------------");
	}
	public static void print(Object...args) {
		String className=null;
		String methodName=null;
		StackTraceElement[] stacks = (new Throwable()).getStackTrace();
		className=stacks[1].getClassName();
		methodName=stacks[1].getMethodName();
		String printStr="";
		for(Object str:args){
			printStr+=str;
			printStr+="\t";
		}
		System.out.println(printStr+methodName+"\t"+className);
	}
	
	
	public static void print(String...args) {
		String className=null;
		String methodName=null;
		StackTraceElement[] stacks = (new Throwable()).getStackTrace();
		className=stacks[1].getClassName();
		methodName=stacks[1].getMethodName();
		String printStr="";
		for(String str:args){
			printStr+=str;
			printStr+="\t";
		}
		System.out.println(printStr+methodName+"\t"+className);
	}
	public static void print(Boolean...args) {
		String className=null;
		String methodName=null;
		StackTraceElement[] stacks = (new Throwable()).getStackTrace();
		className=stacks[1].getClassName();
		methodName=stacks[1].getMethodName();
		String printStr="";
		for(Boolean str:args){
			printStr+=str;
			printStr+="\t";
		}
		System.out.println(printStr+methodName+"\t"+className);
	}
}
