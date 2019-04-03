package an.com.lopez.tools;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;

public class MyRemoteImp extends UnicastRemoteObject implements MyRemote{

	protected MyRemoteImp() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	public String sayHello() throws RemoteException {
		// TODO Auto-generated method stub
		return "Server:Hello Client";
	}
	
	public String RmiTest(){
		System.out.println("RMI Test111");
		ClassLoader loader=MyRemoteImp.class.getClassLoader();
		System.out.println(loader);
		return "RMI Test";
	}
	
	
	public String RmiTest(String msg) throws Exception{
		MessageDigest crypt = MessageDigest.getInstance("SHA-1");
        crypt.reset();
        crypt.update(msg.getBytes("UTF-8"));
		return LopezTools.byteToHex(crypt.digest());
	}

	public void tet() throws RemoteException{
		// TODO Auto-generated method stub
		System.out.println("ffff");
	}



}
