package an.com.lopez.server;

import java.io.IOException;
import java.io.OutputStream;

public abstract class Response {
	OutputStream out;
	public static final String STATE_200="http/1.1 200 OK\n";
	public static final String STATE_404="http/1.1 404\n";

	
	public abstract String write(String str) throws IOException;
	
	
	
	protected String error404() throws IOException {
		String message=STATE_404+"\n";
		send(message);
		return message;
	}
	protected void send(String message) throws IOException {
		byte[] bytes=message.getBytes("UTF-8");
		out.write(bytes);
		out.flush();
	}
}
