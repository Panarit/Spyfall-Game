package client;
import java.awt.Component;
import java.awt.Container;
import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import shared.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JOptionPane;

public class Client  extends UnicastRemoteObject implements ClientInterface
{
	private static final long serialVersionUID = 7468891722773409712L;
	UI chatGUI;
	private String hostName = "192.168.0.26";
	private String serviceName = "SpyFallGame";
	private String clientServiceName;
	public String name;
	protected ServerInterface serverIF;
        public boolean amISpy = false;
	protected boolean connectionProblem = false;

	public Client(UI aChatGUI, String userName) throws RemoteException 
        {
		super();
		this.chatGUI = aChatGUI;
		this.name = userName;
		this.clientServiceName = "ClientListenService_" + userName;
	}

	public void startClient(String username) throws RemoteException 
        {
            Random rand = new Random();
//            int number = rand.nextInt(2000) + 2000;
//            int randomPort = number;
            String hostName2 = JOptionPane.showInputDialog(null, "Write the ip address of the host!");
            hostName = JOptionPane.showInputDialog(null, "Write the ip address of this computer!");
            System.setProperty("java.security.policy","C:\\Users\\Lenovo\\Documents\\NetBeansProjects\\SpyFall Game\\src\\server\\policy.txt");
            if (System.getSecurityManager() == null) 
            {
            System.setSecurityManager(new SecurityManager());
            }
//            java.rmi.registry.LocateRegistry.createRegistry(randomPort);
//            Registry registry = LocateRegistry.getRegistry("192.168.0.26", 1099);
//            registry.bind("hostname, this);
//              String username = JOptionPane.showInputDialog(null, "Write the username you want to choose!");
		String[] details = {name,  hostName, username};	
                System.out.println(details[1]);
//                System.setProperty("java.rmi.server.hostname","192.168.0.28");
		try 
                {
//			Naming.rebind("rmi://" + hostName + ":" + randomPort + "/" + clientServiceName, this);
//			serverIF = ( ServerInterface )Naming.lookup("rmi://" + "192.168.0.26:1099" + "/" + serviceName);
                        serverIF = ( ServerInterface )Naming.lookup("rmi://" + hostName2 + ":1099/" + serviceName);
                        serverIF.proxyBind(this, username);
		} 
		catch (ConnectException  e) 
                {
			JOptionPane.showMessageDialog(chatGUI.frame, "The server seems to be unavailable\nPlease try later","Connection problem", JOptionPane.ERROR_MESSAGE);
			connectionProblem = true;
			e.printStackTrace();
		}
		catch(NotBoundException | MalformedURLException me)
                {
			connectionProblem = true;
			me.printStackTrace();
		}
		if(!connectionProblem)
                {
			registerWithServer(details);
		}	
		System.out.println("Client is running...\n");
	}
        
        public boolean doYouAgreeWithAccusation(String message)
        {
            int result = JOptionPane.showConfirmDialog(null, message, "Question", JOptionPane.YES_NO_OPTION);
            boolean agree = true;
            if(result == 1)
            {
                agree = false;
            }
            return agree;
        }
        
	public void registerWithServer(String[] details) 
        {		
		try
                {
                    serverIF.registerClient(details);			
		}
		catch(Exception e)
                {
                    e.printStackTrace();
		}
	}
        
       private Component[] getComponents(Component container) 
       {
         ArrayList<Component> list = null;

        try {
            list = new ArrayList<Component>(Arrays.asList(
                  ((Container) container).getComponents()));
            for (int index = 0; index < list.size(); index++) {
                for (Component currentComponent : getComponents(list.get(index))) {
                    list.add(currentComponent);
                }
            }
        } catch (ClassCastException e) {
            list = new ArrayList<Component>();
        }

        return list.toArray(new Component[list.size()]);
        }
         
	public void messageFromServer(String message) throws RemoteException 
        {
		System.out.println( message );
		chatGUI.textArea.append( message );
		chatGUI.textArea.setCaretPosition(chatGUI.textArea.getDocument().getLength());
	}

	public void updateUserList(String[] currentUsers) throws RemoteException 
        {
            chatGUI.userPanel.remove(chatGUI.clientPanel);
            chatGUI.setClientPanel(currentUsers);
            chatGUI.clientPanel.repaint();
            chatGUI.clientPanel.revalidate();
	}
        
        
        public void disableEverything()
        {
            
            for(Component component : getComponents(chatGUI.c)) 
            {
                component.setEnabled(false);
            }
        }    
}













