package an.com.lopez.tools;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MyRemote extends Remote{
	 String sayHello() throws RemoteException;
	 String RmiTest() throws RemoteException;
	 String RmiTest(String msg) throws RemoteException, Exception;
	 void tet()throws RemoteException, Exception;
}
