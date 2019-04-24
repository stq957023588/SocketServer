package an.com.lopez.server;

public abstract class Filter {
	
	private Filter next;
	private Request request;
	private Response response;
	
	public boolean start(){
		boolean flag=check(request);
		if(flag){
			if(this.next==null){
				//当前判定判定成功,进入下一个过滤器
				return this.next.start();
			}else{
				//所有判定判定成功
				return true;
			}
		}else{
			//判定失败
			forbidden(response);
			return false;
		}
		
	}
	public abstract boolean check(Request request);
	public abstract void forbidden(Response response);
	public Filter getNext() {
		return next;
	}
	public void setNext(Filter next) {
		if(this.next==null)
			this.next = next;
		else{
			if(this.next.getClass().isAnnotationPresent(FilterConfig.class)){
				if(next.getClass().isAnnotationPresent(FilterConfig.class)){
					FilterConfig thisFc=this.next.getClass().getAnnotation(FilterConfig.class);
					FilterConfig Fc=next.getClass().getAnnotation(FilterConfig.class);
					if(thisFc.value()>Fc.value()){
						Filter temp=this.next;
						this.next=next;
						this.next.setNext(temp);
					}else{
						this.next.setNext(next);
					}
				}else{
					Filter temp=this.next;
					this.next=next;
					this.next.setNext(temp);
				}
			}else{
				this.next.setNext(next);
			}
		}
	}
	public void setRequest(Request request) {
		this.request = request;
		if(next!=null){
			next.setRequest(request);
		}
	}
	public void setResponse(Response response) {
		this.response = response;
		if(next!=null){
			next.setResponse(response);
		}
	}
	
}
