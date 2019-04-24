package an.com.lopez.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public abstract class Response {
	OutputStream out;
	public static final String STATE_200="http/1.1 200 OK\n";
	public static final String STATE_404="http/1.1 404\n";

	
	public abstract String write(String str) throws IOException;
	
	public String error404() throws IOException {
		String message=STATE_404+"\n";
		send(message);
		return message;
	}
	protected void send(String message) throws UnsupportedEncodingException  {
		send(message.getBytes("UTF-8"));
	}
	protected void send()throws IOException{
		send(STATE_200.getBytes("UTF-8"));
	}
	
	protected void send(InputStream in)throws IOException{
		byte[] bytes=new byte[1024];
		int length=0;
		byte[] result=(STATE_200+"\n").getBytes("UTF-8");
		while((length=in.read(bytes))>0){
			result=concat(result, bytes, length);
		}
		send(result);
	}
	protected void send(byte[] bytes){
		try {
			out.write(bytes);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private byte[] concat(byte[] a,byte[] b,int length){
		byte[] c=new byte[a.length+length];
		System.arraycopy(a, 0, c, 0, a.length);
		System.arraycopy(b, 0, c, a.length, length);
		return c;
	}



}
