package an.com.lopez.tools;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketClient {
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
//		for (int i = 0; i < 11; i++) {
			method();
//		}
	}
	public static void method() throws Exception {
	    // 要连接的服务端IP地址和端口
	    String host = "127.0.0.1"; 
	    int port = 9999;
	    // 与服务端建立连接
	    Socket socket = new Socket(host, port);
	    // 建立连接后获得输出流
	    OutputStream outputStream = socket.getOutputStream();
	    InputStream inputStream = socket.getInputStream();
	    byte[] sendbytes=null,
	    	   acceptbytes=null;
	    
	    
	    String message="Hello Server";
	    sendbytes=message.getBytes("UTF-8");
	    outputStream.write(sendbytes.length>>8);
	    outputStream.write(sendbytes.length);
	    outputStream.write(sendbytes);
	    outputStream.flush();
	    
	    
	    message="The second Message";
	    sendbytes=message.getBytes("UTF-8");
	    outputStream.write(sendbytes.length>>8);
	    outputStream.write(sendbytes.length);
	    outputStream.write(sendbytes);
	    outputStream.flush();
	    
	    
	    while(true){
	    	int first=inputStream.read();
	    	if(first==-1){
	    		break;
	    	}
	    	int second=inputStream.read();
	    	System.out.println(first+"\t"+second);
	    	int len=(first<<8)+second;
	    	if(len>0){
	    		acceptbytes=new byte[len];
	    		inputStream.read(acceptbytes);
	    		String msg=new String(acceptbytes);
	    		System.out.println(msg);
	    		LopezTools.Localion("Server:"+msg+"\n");
	    	}
	    }
	    
	    
//	    
//	    inputStream.close();
//	    outputStream.close();
//	    socket.close();
	}

}
