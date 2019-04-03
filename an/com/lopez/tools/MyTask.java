package an.com.lopez.tools;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.alibaba.fastjson.JSONObject;

public class MyTask implements Runnable {
	private Socket socket;
	
	public MyTask(Socket socket) {
		this.socket=socket;
	}
	
	public void run() {
		// TODO Auto-generated method stub
		try {
			handle();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void handle() throws Exception {
		System.out.println("ddd");
		InputStream inputStream = socket.getInputStream();
		OutputStream outputStream=socket.getOutputStream();
		Request request=new Request(inputStream);
		Response response=new Response(outputStream);
		
		if(SocketServer.map.containsKey(request.getGotoURL())){
			CacheModel cache=SocketServer.map.get(request.getGotoURL());
			cache.setRequest(request);
			Object object=cache.invoke(null);
			if(object.getClass()==ReturnObject.class && (!"".equals(((ReturnObject)object).pageName) || ((ReturnObject)object).pageName!=null)){
				ReturnObject returnObject=(ReturnObject)object;
				response.write(SocketServer.htmlMap.get(returnObject.pageName),returnObject.getAttribute());
//				response.write(SocketServer.htmlMap.get(returnObject.pageName));
			}else if(object.getClass() != String.class){
				response.write(JSONObject.toJSONString(object));
			}else{
				response.write(object.toString());
			}
		}else{
			response.error404();
		}
		
		
	    outputStream.close();
	    inputStream.close();
	    socket.close();	    
	}
	
	
	private void webRunMethod() throws Exception{
		InputStream inputStream = socket.getInputStream();
	    OutputStream outputStream=socket.getOutputStream();
	    byte[] bytes=new byte[1024];
	    int len=0;
	    StringBuffer sBuffer=new StringBuffer();
	    while(true){
	    	len=inputStream.read(bytes);
	    	sBuffer.append(new String(bytes));
	    	if(len<=0||len<1024){
	    		break;
	    	}
	    }
	    System.out.println(sBuffer.toString());
	    int i=0;
	    for(String str:sBuffer.toString().split("\\n")){
	    	System.out.println(i+str);
	    	i++;
	    }
	    StringBuffer body=new StringBuffer();
	    body.append("HTTP/1.1 200 OK\n");
//	    body.append("Date: "+sdf.format(date));
//	    body.append("Content-Type:text/html;charset=UTF-8");
//	    body.append("Content-Length:122");
	    body.append("\n");
	    
	    body.append(SocketServer.htmlMap.get("test"));
	    
	    outputStream.write(body.toString().getBytes("UTF-8"));
	    outputStream.close();
	    inputStream.close();
	    socket.close();
	}
	
	
	private void runMethod_1() throws Exception {
	    InputStream inputStream = socket.getInputStream();
	    OutputStream outputStream=socket.getOutputStream();
	    
	    byte[] bytes=null;
	    byte[] sendbytes;
    	int first=inputStream.read();
    	int second=inputStream.read();
    	System.out.println(first+"\t"+second);
    	int len=(first << 8)+second;
    	bytes=new byte[len];
    	inputStream.read(bytes);
    	if(bytes.length>0){
    		LopezTools.Localion("Client:"+new String(bytes,"UTF-8")+"\n");
    		String msgString="Hello Client";
    		sendbytes=msgString.getBytes("UTF-8");
    		outputStream.write(sendbytes.length>>8);
    	    outputStream.write(sendbytes.length);
    	    outputStream.write(sendbytes);
    	    outputStream.flush();
    	}
	    outputStream.close();
	    inputStream.close();
	    socket.close();
	}
	
}
