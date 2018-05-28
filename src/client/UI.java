package client;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import shared.*;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.Border;

public class UI extends JFrame implements ActionListener
{
    private static final long serialVersionUID = 1L;
    private String username;
    private JPanel textPanel, inputPanel;
    private JTextField textField;
    private String name, message;
    private Font meiryoFont = new Font("Meiryo", Font.PLAIN, 14);
    private Border blankBorder = BorderFactory.createEmptyBorder(10, 10, 20, 10);//top,r,b,l
    private Client chatClient;
    private JLabel userLabel;
    private String location;
    private int playerNumber;
    public Container c;
    private JList<String> list;
    private JList<String> list2;
    private DefaultListModel<String> listModel;
    private DefaultListModel<String> listModel2;
    protected JTextArea textArea, userArea;
    protected JFrame frame;
    protected JButton dummyButton;
    protected JButton startButton, sendButton;
    protected JButton spyButton;
    protected JButton clientButton;
    protected JPanel clientPanel, userPanel;
    protected JPanel clientPanel2;
    protected JPanel locationPanel;

	public static void main(String args[])
        {
		try
                {
			for(LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
                        {
				if("CDE/Motif".equals(info.getName()))
                                {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
                                else
                                {}
			}
		}
		catch(Exception e)
                {
	        }
		new UI();
	}

	public UI()
        {
			
		frame = new JFrame("Client UI");	
		frame.addWindowListener(new java.awt.event.WindowAdapter() 
                {
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) 
                    {
		        
		    	if(chatClient != null)
                        {
			    	try {
			        	sendMessage("I am leaving the game!");
			        	chatClient.serverIF.leaveChat(name);
				    } catch (RemoteException e) 
                                    {
					e.printStackTrace();
				    }		        	
		        }
		        System.exit(0);  
		    }   
		});	
                c = getContentPane();
		JPanel outerPanel = new JPanel(new BorderLayout());
		
		outerPanel.add(getInputPanel(), BorderLayout.SOUTH);
		outerPanel.add(getTextPanel(), BorderLayout.NORTH);
		
		c.setLayout(new BorderLayout());
		c.add(outerPanel, BorderLayout.CENTER);
		c.add(getUsersPanel(), BorderLayout.EAST); 
		frame.add(c);
                frame.setSize(750,395);
		frame.setLocation(150, 150);
		textField.requestFocus();	
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setVisible(true);
                username = JOptionPane.showInputDialog(null, "Write the username you want to choose!");
	}
	
	public JPanel getTextPanel()
        {
		String welcome = "Please enter your name. After that, click the button: Get in the game\n";
		textArea = new JTextArea(welcome, 14, 34);
		textArea.setMargin(new Insets(10, 10, 10, 10));
		textArea.setFont(meiryoFont);
		
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		textPanel = new JPanel();
		textPanel.add(scrollPane);
	
		textPanel.setFont(new Font("Meiryo", Font.PLAIN, 14));
		return textPanel;
	}
	public JPanel getInputPanel()
        {
		inputPanel = new JPanel(new GridLayout(1, 1, 5, 5));
		inputPanel.setBorder(blankBorder);	
		textField = new JTextField();
		textField.setFont(meiryoFont);
		inputPanel.add(textField);
		return inputPanel;
	}

        public JPanel getLocationPanel() throws RemoteException
        {
            locationPanel = new JPanel(new BorderLayout());

            String locationStr = "Possible locations      ";

            JLabel locationLabel = new JLabel(locationStr, JLabel.CENTER);
            locationPanel.add(locationLabel, BorderLayout.NORTH);
            locationLabel.setFont(new Font("Meiryo", Font.PLAIN, 16));
            String[] locations = chatClient.serverIF.getLocations();
            setLocationPanel(locations);
            locationPanel.setBorder(blankBorder);

            return locationPanel;		
        }
	public JPanel getUsersPanel()
        {
		
		userPanel = new JPanel(new BorderLayout());
		String  userStr = " Current Users      ";		
	        userLabel = new JLabel(userStr, JLabel.CENTER);
		userPanel.add(userLabel, BorderLayout.NORTH);	
		userLabel.setFont(new Font("Meiryo", Font.PLAIN, 16));
		String[] noClientsYet = {"No other users"};
		setClientPanel(noClientsYet);
		clientPanel.setFont(meiryoFont);		
		userPanel.setBorder(blankBorder);
                userPanel.add(makeButtonPanel(), BorderLayout.SOUTH);	
		return userPanel;		
	}
        
        public JPanel getSpyButtonPanel()
        {
            spyButton = new JButton("Guess the location");
            spyButton.addActionListener(this);
            JPanel buttonPanel2 = new JPanel(new GridLayout(4, 1));
            buttonPanel2.add(spyButton);
            buttonPanel2.add(new JLabel(""));
            return buttonPanel2;
        }
        
        public JPanel getClientButtonPanel()
        {
            clientButton = new JButton("Guess the spy");
            clientButton.addActionListener(this);
            JPanel buttonPanel3 = new JPanel(new GridLayout(4, 1));
            buttonPanel3.add(clientButton);
            buttonPanel3.add(new JLabel(""));
            return buttonPanel3;
        }
    
    public void setLocationPanel(String[] possibleLocations) 
    {
        clientPanel2 = new JPanel(new BorderLayout());
        listModel2 = new DefaultListModel<String>();

        for (String s : possibleLocations) 
        {
            listModel2.addElement(s);
        }
        list2 = new JList<String>(listModel2);
        list2.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list2.setVisibleRowCount(10);
        list2.setFont(meiryoFont);
        JScrollPane listScrollPane = new JScrollPane(list2);
        clientPanel2.add(listScrollPane, BorderLayout.CENTER);
        locationPanel.add(clientPanel2, BorderLayout.CENTER);
    }

    public void setClientPanel(String[] currClients) 
    {  	
    	clientPanel = new JPanel(new BorderLayout());
        listModel = new DefaultListModel<String>();
        
        for(String s : currClients)
        {
        	listModel.addElement(s);
        }
        
        list = new JList<String>(listModel);
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list.setVisibleRowCount(8);
        list.setFont(meiryoFont);
        JScrollPane listScrollPane = new JScrollPane(list);

        clientPanel.add(listScrollPane, BorderLayout.CENTER);
        userPanel.add(clientPanel, BorderLayout.CENTER);
    }

	public JPanel makeButtonPanel() 
        {		
		
            sendButton = new JButton("Send Message ");
            sendButton.addActionListener(this);
            sendButton.setEnabled(false);

            dummyButton = new JButton("");
		
            startButton = new JButton("Get in the game ");
            startButton.addActionListener(this);
		
            JPanel buttonPanel = new JPanel(new GridLayout(4, 1));
            buttonPanel.add(new JLabel(""));
            buttonPanel.add(new JLabel(""));
            buttonPanel.add(startButton);
            buttonPanel.add(sendButton);
            return buttonPanel;
	}

	public void actionPerformed(ActionEvent e)
        {

		try {

			if(e.getSource() == startButton)
                        {
				name = textField.getText();				
				if(name.length() != 0)
                                {
					frame.setTitle(name + "'s console ");
					textField.setText("");
					textArea.append("username : " + name + " connecting to chat...\n");							
					getConnected(name);
                                        location = chatClient.serverIF.giveMeLocation();
                                        chatClient.amISpy = chatClient.serverIF.amIASpy();
                                        playerNumber = chatClient.serverIF.whichPlayerAmI();
                                        c.add(getLocationPanel(), BorderLayout.WEST); 
                                        if(chatClient.amISpy == false)
                                        {
                                            userLabel.setText("Location: " + location + "     ");
                                            locationPanel.add(getClientButtonPanel(), BorderLayout.SOUTH);
                                        }
                                        else
                                        {
                                            userLabel.setText("YOU ARE THE SPY        ");
                                            locationPanel.add(getSpyButtonPanel(), BorderLayout.SOUTH);                                          
                                        }
					if(!chatClient.connectionProblem)
                                        {
						startButton.setEnabled(false);
						sendButton.setEnabled(true);
					}
				}
				else
                                {
					JOptionPane.showMessageDialog(frame, "Enter your name to enter the game!");
				}
			}

			if(e.getSource() == sendButton)
                        {
				message = textField.getText();
				textField.setText("");
				sendMessage(message);
				System.out.println("Sending message : " + message);
			}

                        if(e.getSource() == spyButton)
                        {
                            String selected = list2.getSelectedValue();
                            System.out.println("The selected value is: " + selected);
                            chatClient.serverIF.isLocationFound(selected);
                        }
                        if(e.getSource() == clientButton)
                        {
                            int index = list.getSelectedIndex();
                            chatClient.serverIF.guessTheSpy(index, chatClient.name, playerNumber);
                            System.out.println(index);
                        }
			
		}
		catch (RemoteException remoteExc) 
                {			
			remoteExc.printStackTrace();	
		}
		
	}
        
	private void sendMessage(String chatMessage) throws RemoteException 
        {
		chatClient.serverIF.updateChat(name, chatMessage);
	}
	
	private void getConnected(String userName) throws RemoteException
        {
		
//            String cleanedUserName = userName.replaceAll("\\s+","_");
//            cleanedUserName = userName.replaceAll("\\W+","_");
            String cleanedUserName = userName;
            try {		
               	chatClient = new Client(this, cleanedUserName);
		chatClient.startClient(username);
		} 
            catch (RemoteException e) 
            {
		e.printStackTrace();
            }
	}

}










