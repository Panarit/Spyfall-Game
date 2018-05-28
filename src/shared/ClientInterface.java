package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote
{
        public boolean doYouAgreeWithAccusation(String message) throws RemoteException;
	
        public void messageFromServer(String message) throws RemoteException;

	public void refreshTheList(String[] currentUsers) throws RemoteException;
        
        public void disableEverything() throws RemoteException;   
}
