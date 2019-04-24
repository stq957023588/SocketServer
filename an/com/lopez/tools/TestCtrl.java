package an.com.lopez.tools;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import an.com.lopez.server.AopBegin;
import an.com.lopez.server.AopEnd;
import an.com.lopez.server.CtrlClass;
import an.com.lopez.server.CtrlField;
import an.com.lopez.server.CtrlMethod;
import an.com.lopez.server.HtmlModel;
import an.com.lopez.server.HttpRequest;

import com.alibaba.fastjson.JSON;



@CtrlClass
public class TestCtrl {
	@CtrlField
	public HttpRequest request;
	
	@CtrlMethod("/getIpInfo")
	public Object getIpInfo() throws IOException{
		String result=LopezTools.getIpInfo("check");
		return JSON.toJSON(result);
	}
	
	@CtrlMethod("/test")
	public HtmlModel test(String age,Map<String,Object> paramMap) {
		return new HtmlModel("test");
	}
	@CtrlMethod("/nullTest")
	public Object nullTest() {
		System.out.println("Null Test");
		return null;
	}
	@CtrlMethod("/voidTest")
	@AopBegin("log")
	@AopEnd("log")
	public void  voidTest() {
		System.out.println("voidTest");
	}
	
	
	@AopEnd("aop")
	@CtrlMethod("/test1")
	public Map<String, String> test1(String age,Map<String,Object> paramMap) {
		System.out.println(age);
		System.out.println(paramMap);
		Map<String, String> map=new HashMap<String, String>();
		map.put("age", "19");
		return map;
	}
	
	@CtrlMethod("/test2")
	public ReturnObject test2(){
//		System.out.println(request.getParam("name"));
		ReturnObject returnObject=new ReturnObject("test");
		returnObject.setAttribute("test", "Attribute Test");
		return returnObject;
	}
	
	@CtrlMethod("/test3")
	public ReturnObject test3(){
		return new ReturnObject("test");
	}
	
	
}
