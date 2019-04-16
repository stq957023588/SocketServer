package an.com.lopez.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import com.alibaba.fastjson.JSONObject;


public class WebTask extends Task{

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
		String goToUrl=((HttpRequest) request).getGotoURL();
		File file=null;
		if(Server.ctrlMap.containsKey(goToUrl)){
			CacheModel cache=Server.ctrlMap.get(goToUrl);
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
		}else if((file=new File(Server.context.resourcesPath+goToUrl)).exists() && (goToUrl!=null || "".equals(goToUrl))){
			InputStream in=new FileInputStream(file);
			((HttpResponse)response).write(in);
		}else{
			response.error404();
		}
		
		out.close();
		in.close();
		socket.close();
	}

}
