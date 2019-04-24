package an.com.lopez.tools;

import java.io.IOException;

import an.com.lopez.server.Filter;
import an.com.lopez.server.FilterConfig;
import an.com.lopez.server.HttpRequest;
import an.com.lopez.server.Request;
import an.com.lopez.server.Response;

@FilterConfig(2)
public class MyFilter extends Filter{




	@Override
	public boolean check(Request request) {
		// TODO Auto-generated method stub
		boolean flag=false;
		HttpRequest r=(HttpRequest)request;
		return true;
	}

	@Override
	public void forbidden(Response response) {
		// TODO Auto-generated method stub
		try {
			response.error404();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
