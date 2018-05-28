package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.RemoteRef;

public interface ServerInterface extends Remote 
{
		
	public void updateChat(String userName, String chatMessage)throws RemoteException;
	
	public void registerClient(String[] details)throws RemoteException;
	
	public void leaveChat(String userName)throws RemoteException;
       
        public void proxyBind(ClientInterface client, String serviceName) throws RemoteException;
        
        public String giveMeLocation() throws RemoteException;
        
        public boolean amIASpy() throws RemoteException;
        
        public String[] getLocations() throws RemoteException;
        
        public void locationNotFound() throws RemoteException;
        
        public void locationFound() throws RemoteException;
        
        public boolean isLocationFound(String location) throws RemoteException;
        
        public void guessTheSpy(int index, String name, int playerNumber) throws RemoteException;
        
        public int whichPlayerAmI() throws RemoteException;
}


