package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Iterator;

import Shared.Player;

public class ClientReaderThread extends Thread{
	private Client client;
	private ObjectInputStream is;
	
	public ClientReaderThread(Client client, ObjectInputStream is) {
		super();
		this.client = client; 
		this.is = is;
	} 
	@Override
	public void run() {
		Player[] players = null;
		while(!Thread.currentThread().isInterrupted()) {
			try {
				Object o = is.readObject();
				while(!(o instanceof Player[])) o = is.readObject();
				players = (Player[]) o;
				
				synchronized (client) {
					client.setPlayers(players);
					client.notifyAll();
				}
				
				
				for (int i = 0; i < players.length; i++) {
					//System.out.println(players[i]);
				}
				
				 
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println("Closing socket.");
			}

		}
		
	}
}
