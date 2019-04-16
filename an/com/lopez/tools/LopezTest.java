package an.com.lopez.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Queue;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import an.com.lopez.server.Server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


public class LopezTest {
	private static String AccessToken;
	private static String JsApiTicket;
	public static Queue<String> queue=new ArrayBlockingQueue<String>(10);
	private static int count;
	
	@MyAnnotations(MethodProxy.class)
	public InvocationHandler handler;
	
	public static void main(String[] args) throws Exception{
		LopezTest ltLopezTest=new LopezTest();
		ltLopezTest.serverTest();
		
	}
	public void serverTest() throws IOException{
		Server server=new Server("an.com.lopez.view", "an.com.lopez.tools");
		server.startServer();
	}
	
	public void PropertiesTest() throws Exception{
		Properties p=new Properties();
		InputStream in=Thread.currentThread().getContextClassLoader().getResourceAsStream("an/com/lopez/config/config.properties");
		p.load(in);
		in.close();
		
		System.out.println(p.getProperty("ddd"));
		
		Iterator<String> iterator=p.stringPropertyNames().iterator();
		while(iterator.hasNext()){
			String key=iterator.next();
			System.out.println(p.getProperty(key));
		}
		
	}
	
	
	
	public  int power(int x,int n){
		int y=0;
		if(n==1){
			y=x;
			return y;
		}
		if(n%2!=0){
			y=power(x, (n-1)/2);
			y=y*y*x;
			countAdd();
			countAdd();
		}else{
			y=power(x, n/2);
			y=y*y;
			countAdd();
		}
		
		return y;
	}
	
	private synchronized void countAdd() {
		count++;
	}
	
	public void bigestStr(){
		int[] arr={-1,-5,8,96,47,-32,5,42,6,-32};
	}
	
	public void loaderTest() throws Exception{
		MyClassLoader loader=new MyClassLoader();
		Class<?> c=loader.loadClass("an.com.lopez.tools.MyRemoteImp1");
		MyRemote remote=(MyRemote)c.newInstance();
		remote.RmiTest();
	}
	
	
	public void permute(String str){
		str.toCharArray();
	}
	public void permute(char[] str,int low,int high){
		
	}
	public int printBtye1(int n){
		if(n==1)
			return 1;
		if(n%2==1)
			return printBtye1(n/2)+1;
		else
			return printBtye1(n/2);
		
	}
	public void findMax(){
		String[] arr=new String[]{"safaf","asfqef","asfqfqef","asf","jkg"};
		System.out.println(LopezTools.findMax(arr));
		Integer integer=9;
	}
	
	
	public void choose(){
		int[] arr;

		arr=new int[]{41,26,37,94,54,60,73,88,96};
		int[] sto=new int[7];
		for(int i=0;i<sto.length;i++){
			sto[i]=arr[i];
		}
		for(int i=0;i<sto.length-1;i++){
			for(int j=0;j<sto.length-1-i;j++){
				if(sto[j+1]>sto[j]){
					int temp=sto[j+1];
					sto[j+1]=sto[j];
					sto[j]=temp;
				}
			}
		}
		for(int i=7;i<arr.length;i++){
			if(arr[i]<sto[sto.length-1]){
				continue;
			}
			for(int j=sto.length-1;j>=0;j--){
				if(sto[j]<arr[i]){
					if(j==0){
						sto[j]=arr[i];
					}else{
						sto[j]=sto[j-1];
					}
				}else{
					sto[j+1]=arr[i];
					break;
				}
			}
		}
		for (int i = 0; i < sto.length; i++) {
			System.out.println(sto[i]);
		}
	}
	
	
	
	
	public void splitTest() {
		String string="asfasafasf";
		System.out.println(string.split("\\?")[0]);
	}
	
	public void htmlRegex() throws Exception {
		Map<String, String> map=new HashMap<String, String>();
		map.put("test", "test1");
		map.put("name", "44");
		
		String html=htmlIOTest();
		String htmlTempString=html;
		String regex="<(\\w|\\s|\"|=){0,}slz:(if|text)=\"\\$\\{([A-Z]|[a-z]){1,9}\\}\"(\\w|\\s|\"|=){0,}>";
		Pattern pattern=Pattern.compile(regex);
//		String contentString="<div id=\"dd\" slz:if=\"${fff}\" class=\"te\">";
		Matcher matcher=pattern.matcher(html);
		StringBuilder sb=new StringBuilder();
		while(matcher.find()){
			int start=matcher.start();
			int end=matcher.end();
			String table=html.substring(start, end);
			int l=0;
			int r=0;
			if(table.contains("slz:text=\"${")){
				String oldString=html.substring(start, html.indexOf("<", start+table.length()));
//				System.out.println(table);
				l=table.indexOf("slz:text=\"${");
				r=table.indexOf("}\"", l);
				String attributeName=table.substring(l+"slz:text=\"${".length(),r);
				String regex1=table.substring(l, r+"}\"".length());
				htmlTempString=htmlTempString.replace(oldString, table.replace(regex1, "")+map.get(attributeName));
				System.out.println(htmlTempString);
			}
		}
	}
	
	public void LR_test() throws Exception {
//		String string="sfafa<ni hoa a >safasf<</asfw>5456";
		Map<String, String> map=new HashMap<String, String>();
		map.put("test1", "test1");
		map.put("name", "44");
		String html=htmlIOTest();
		for(String st:html.split("<")){
//			System.out.println(st);
			String table=st.split(">")[0];
			if(table.contains("slz:")){
				System.out.println(table);
				int start=table.indexOf("slz:")+4;
				if("text".equals(table.substring(start, table.indexOf("=",start)))){
					int begin=table.indexOf("${", start)+2;
					int end=table.indexOf("}", start);
					String replace=table.substring(start-4, end+2);
					String tableTemp=table.replace(replace, "");
					int size=table.length()-tableTemp.length();
					int Lbegin=html.indexOf(">",html.indexOf(table))+1;
					int Rend=html.indexOf("<", html.indexOf(table));
					html=html.replace(table, tableTemp);
					StringBuilder sBuilder=new StringBuilder(html);
					sBuilder.replace(Lbegin-size, Rend-size, map.get(table.substring(begin,end)));
					System.out.println(sBuilder.toString());
				}
			}
				
		}
	}
	
	
	public String htmlIOTest() throws Exception {
		File file=new File("C:\\Users\\yuli\\Desktop\\test.html");
		InputStream inputStream=new FileInputStream(file);
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
//		System.out.println(buffer.toString());
		return buffer.toString();
		
	}
	
	
	public void socketReptile() throws UnknownHostException, IOException {
		Socket socket=new Socket("127.0.0.1",9000);
		OutputStream outputStream=socket.getOutputStream();
		StringBuffer sb=new StringBuffer();
		sb.append("GET / HTTP/1.1 \r\n");
		sb.append("HOST: 127.0.0.1\r\n");
//		sb.append("Connection: close\r\n");
//		sb.append("Upgrade-Insecure-Requests: 1\r\n");
//		sb.append("User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36\r\n");
//		sb.append("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\r\n");
//		sb.append("Accept-Encoding: gzip, deflate, sdch\r\n");
//		sb.append("Accept-Language: zh-CN,zh;q=0.8,en;q=0.6\r\n");
		sb.append("\n");
		outputStream.write(sb.toString().getBytes("UTF-8"));
		outputStream.flush();
		
		InputStream inputStream=socket.getInputStream();
		int len=0;
	    byte[] bytes=new byte[1024];
	    String string="";
	    while(true){
	    	len=inputStream.read(bytes);
	    	string+=new String(bytes);
	    	if(len<=0||len<1024){
	    		break;
	    	}
	    }
	    System.out.println(new String(bytes));
	    outputStream.close();
	    inputStream.close();
		socket.close();
	}
	
	
	public void timeTest() {
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("E dd MMM yyyy HH:mm:ss z",Locale.US);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		System.out.println(sdf.format(date));
	}
	
	public void urlTest() throws Exception {
//		System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,SSLv3");
		URL url=new URL("https://vipreader.qidian.com/chapter/1010259609/394650420");
		HttpURLConnection connection=(HttpURLConnection)url.openConnection();
		connection.setRequestMethod("GET");
		InputStream inputStream=connection.getInputStream();
		
	    System.out.println(LopezTools.InputStreamRead(inputStream));
	}
	
	
	
	public void annotationsTest() throws RemoteException, Exception {
		if(this.getClass().getField("handler").isAnnotationPresent(MyAnnotations.class)){
			MyAnnotations annotations=this.getClass().getField("handler").getAnnotation(MyAnnotations.class);
			handler=(InvocationHandler)annotations.value().newInstance();
			MyRemote remote=(MyRemote)new Invoker().getInstance(MyRemote.class,handler);
			remote.tet();
		}
	}
	
	public void joinWithParamTest() throws InterruptedException {
		System.out.println("join start");
		MyThread m1=new MyThread();
		Thread thread=new Thread(m1);
		thread.start();
		thread.join(0);
	}
	
	public void threadTest() throws InterruptedException {
		MyThread m1=new MyThread();
		MyThread2 m2=new MyThread2();
		Thread thread=new Thread(m1);
		Thread thread2=new Thread(m2);
		thread.start();
		Thread.sleep(1000);
		thread2.start();
	}
	
	public void joinTest() throws InterruptedException {
		MyThread m1=new MyThread();
		Thread thread=new Thread(m1);
		thread.start();
//		thread.join();
	}
	
	public void classloaderTest1(){
		ClassLoader c1=LopezTest.class.getClassLoader();
		System.out.println(c1);
		ClassLoader c2=c1.getParent();
		System.out.println(c2);
		ClassLoader c3=c2.getParent();
		System.out.println(c3);
	}
	
	public void classReflex() {
		try {
			Class c=Class.forName("an.com.lopez.tools.LopezTest");
			System.out.println(c.getName());
			LopezTest lopezTest=(LopezTest) c.newInstance();
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
	
	public void reflex() throws SecurityException, NoSuchMethodException{
		LopezTest ltLopezTest=new LopezTest();
		Map<Class, Object> params=new HashMap<Class, Object>();
		params.put(LopezTest.class, ltLopezTest);
		params.put(String.class, "Hello Reflex");
		MyThread mt=new MyThread(ltLopezTest, "test", params);
		Thread thread=new Thread(mt);
		thread.start();
	}
	
	public void test(String string,LopezTest lopezTest) {
		System.out.println(string);
		lopezTest.stringTest();
	}
	public void test(String string) {
		System.out.println(string);
	}
	
	
	public void ThreadPoolTest() {
		ThreadPoolExecutor pool=new ThreadPoolExecutor(5, 10, 100000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
		for (int i = 0; i < 15; i++) {
			MyThread mt=new MyThread(i);
			pool.submit(mt);
			System.out.println("线程池中线程数目："+pool.getPoolSize()+"，队列中等待执行的任务数目："+pool.getQueue().size()+"，已执行玩别的任务数目："+pool.getCompletedTaskCount());
		}
		pool.shutdown();
	}
	
	
	public static synchronized String resources1() {
		System.out.println(resources2());
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "resources1";
	}
	public static synchronized String resources2() {
		System.out.println(resources1());
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "resources2";
	}
	
	
	public void stringTest() {
		String str1="asasf";
		String str2="I";
		String str3="";
		System.out.println("indexOf:\t"+str1.indexOf("", 3));
		System.out.println("compareTo:\t"+str1.compareTo(str2));
		System.out.println("startWith:\t"+str1.startsWith("a",1));
		System.out.println("hashCode:\t"+str3.hashCode());
	}
	
	public void regexTest() {
		String string="fewfsd5v5w5f";
		Pattern pattern=Pattern.compile("^\\w{12}$");
		Matcher matcher=pattern.matcher(string);
		
		System.out.println(matcher.find());
	}
	
	public void aop() throws Exception {
		MyRemoteImp myRemoteImp=new MyRemoteImp();
		Class[] proxyInterface=new Class[]{MyRemote.class};
		AspectC handler=new AspectC(myRemoteImp);
		ClassLoader classLoader=MyRemote.class.getClassLoader();
		MyRemote imp=(MyRemote)Proxy.newProxyInstance(classLoader, proxyInterface, handler);
		imp.RmiTest("ffff");
	}
	
	public void runDaili() {
		LopezTest ltLopezTest=new LopezTest();
		Map<Class, Object> params=new HashMap<Class, Object>();
//		params.put(LopezTest.class, ltLopezTest);
		params.put(String.class, "Hello Reflex");
		Runnable runnable=(Runnable)new Invoker().getInstance(Runnable.class,ltLopezTest, "test", params);
		Thread thread=new Thread(runnable);
		thread.start();
	}
	
	public void daili() {
		InterfaceTest interfaceTest=(InterfaceTest)new Invoker().getInstance(InterfaceTest.class);
		System.out.println(interfaceTest.print("ddd"));
	}
	
	public void agentClient() throws Exception{
		MyRemote remote=(MyRemote)Naming.lookup("rmi://localhost/RemoteHello");
		System.out.println(remote.RmiTest("DDDD"));
	}
	
	
	
	
	public void wechatTest() {
		try {
		    Map<String, String> ret = new HashMap();
		    String nonceStr = createNonceStr();
		    String timestamp = createTimestamp();
		    String string1;
		    String signature = "";
		    
		    System.out.println(nonceStr+"\t"+timestamp);
			
			String url1="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=";
			url1=url1.replace("APPID","wxe0ac0fd89108d866").replace("APPSECRET","");
			AccessToken=doGet(url1).get("access_token").toString();
//			System.out.println(AccessToken);
			
			String url2="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
			url2=url2.replace("ACCESS_TOKEN", AccessToken);
			JsApiTicket=doGet(url2).get("ticket").toString();
//			System.out.println(JsApiTicket);
//			
			System.out.println(JsApiTicket);
			
			string1 = "jsapi_ticket=" + JsApiTicket +
		            "&noncestr=" + nonceStr +
		            "&timestamp=" + timestamp +
		            "&url=https://www.cnblogs.com/backtozero/p/7064247.html";
			
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	        crypt.reset();
	        crypt.update(string1.getBytes("UTF-8"));
	        signature = byteToHex(crypt.digest());
	        
	        System.out.println(signature);
	        ret.put("url", "https://www.cnblogs.com/backtozero/p/7064247.html");
	        ret.put("jsapi_ticket", JsApiTicket);
	        ret.put("nonceStr", nonceStr);
	        ret.put("timestamp", timestamp);
	        ret.put("signature", signature);
	        ret.put("appid", "wxe0ac0fd89108d866");
	        System.out.println(ret);
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("ddd");
		}
	}
	
	
	public JSONObject doGet(String url) throws Exception {
		String result="";
		BufferedReader in=null;
		try{
//			String urlString="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
			URL realUrl=new URL(url);
			System.out.println("请求的服务器主机域名："+realUrl.getHost().toString());
	
			URLConnection connection=realUrl.openConnection();
			//设置请求连接时间和读取数据时间
			connection.setConnectTimeout(3000);
			connection.setReadTimeout(7000);
			//建立实际的连接
			connection.connect();
			//读取获取的数据
			in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while((line=in.readLine())!=null){
				result+=line;
			} 
		}catch(Exception e){
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}finally {
			try{
				if(in!=null){
				in.close();
			}
			}catch (Exception e2) {
				System.out.println("关闭请求流出现异常！" + e2);
				e2.printStackTrace();
			}
		}
		JSONObject joJsonObject=JSON.parseObject(result);
		
		return joJsonObject;

	}
	private static String createNonceStr() {
	    return UUID.randomUUID().toString();
	}
	//生成时间戳
	private static String createTimestamp() {
	    return Long.toString(System.currentTimeMillis() / 1000);
	}
	private static String byteToHex(final byte[] hash) {
	    Formatter formatter = new Formatter();
	    for (byte b : hash)
	    {
	        formatter.format("%02x", b);
	    }
	    String result = formatter.toString();
	    formatter.close();
	    return result;
	}
	
	
	
}
