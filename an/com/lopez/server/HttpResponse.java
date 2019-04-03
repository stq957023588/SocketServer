package an.com.lopez.server;

import java.io.IOException;
import java.io.OutputStream;

public class HttpResponse extends Response{
	public HttpResponse(OutputStream out){
		this.out=out;
	}

	@Override
	public String write(String str) throws IOException {
		// TODO Auto-generated method stub
		String message=STATE_200+"\n"+str;
		send(message);
		return message;
	}

}
