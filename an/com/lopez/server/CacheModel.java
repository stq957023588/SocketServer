package an.com.lopez.server;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;


public class CacheModel {
	String url;
	Object obj;
	Method method;
	Class<?> returnType;
	Class[] paramsType;
	Object[] params;
	
	CacheModel begin;
	CacheModel end;
	
	public CacheModel(String url,Object obj,Method method,Class<?> returnType,Class[] paramsType){
		this.url=url;
		this.obj=obj;
		this.method=method;
		this.returnType=returnType;
		this.paramsType=paramsType;
	}
	
	public Object invoke(Map<String,Object> paramsMap) {
		try {
			params=new Object[paramsType.length];
			for(int i=0;i<paramsType.length;i++)
				if(paramsType[i]==Map.class)
					params[i]=paramsMap;
			if(begin!=null)
				begin.invoke(null);
			Object returnObj=method.invoke(obj, params);
			if(end!=null)
				end.invoke(null);
			return returnObj;
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public void setRequest(Request request) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields=obj.getClass().getDeclaredFields();
		for(Field field:fields){
			if(field.isAnnotationPresent(CtrlField.class)){
				field.set(obj, request);
			}
		}
	}

	public void setBegin(CacheModel begin) {
		this.begin = begin;
	}

	public void setEnd(CacheModel end) {
		this.end = end;
	}
	
}
