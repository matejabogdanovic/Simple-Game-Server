package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;

import Shared.Player;

public class ClientWriterThread extends Thread{
	private Client client;
	private ObjectOutputStream os;
	private Player.ValidInput currentInput;
	public ClientWriterThread(Client client, ObjectOutputStream os) {
		super();
		this.client = client;
		this.os = os; 
	}
  
	public Player.ValidInput getCurrentInput() {
		return currentInput;
	}

	public void setCurrentInput(Player.ValidInput currentInput) {
		this.currentInput = currentInput;
	}

	@Override
	public void run() {
		
		try(InputStreamReader input = new InputStreamReader(System.in);
			BufferedReader reader = new BufferedReader(input);) {
			while(!Thread.currentThread().isInterrupted()) {
				
				synchronized (client) {
					client.wait();
					if(this.currentInput != null) {
						os.writeObject(this.currentInput);
						this.currentInput = null;
					} 
				} 
 
			} 
		} catch (IOException | InterruptedException e) {
			System.out.println("Server closed connection.");
		}finally { 
			synchronized (client) {
				client.notifyAll();
			}
			System.out.println("ClientWriterThread closing.");
		}
		
	}


}
