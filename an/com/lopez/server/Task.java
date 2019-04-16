package an.com.lopez.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public abstract class Task implements Runnable{
	Socket socket;
	InputStream in;
	OutputStream out;
	
	Request request;
	Response response;
	
	public Task(Socket socket){
		this.socket=socket;
	}
	public void run() {
		try {
			execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public abstract void execute() throws Exception;
}
