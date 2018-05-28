package server;

import shared.*;

public class Player 
{
	public String name;
	public ClientInterface client;
	
	//constructor
	public Player(String name, ClientInterface client)
        {
		this.name = name;
		this.client = client;
	}

	
	//getters and setters
	public String getName()
        {
		return name;
	}
	public ClientInterface getClient()
        {
		return client;
	}	
}
