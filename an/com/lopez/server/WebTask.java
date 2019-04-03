package an.com.lopez.server;

import java.net.Socket;

import com.alibaba.fastjson.JSONObject;


public class WebTask extends Task{

	public WebTask(Socket socket) {
		super(socket);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() throws Exception {
		// TODO Auto-generated method stub
		in = socket.getInputStream();
		out= socket.getOutputStream();
		HttpRequest request=new HttpRequest(in);
		HttpResponse response=new HttpResponse(out);
		
		if(Server.ctrlMap.containsKey(request.getGotoURL())){
			CacheModel cache=Server.ctrlMap.get(request.getGotoURL());
			Object obj=cache.invoke(request.getParams());
			
			if(obj.getClass() == String.class){
				response.write(obj.toString());
			}else if(obj.getClass()== HtmlModel.class){
				response.write(((HtmlModel)obj).getHtml());
			}else{
				response.write(JSONObject.toJSONString(obj));
			}
		}else{
			response.error404();
		}
		
		out.close();
		in.close();
		socket.close();
	}

}
