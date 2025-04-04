package Server;

import java.io.IOException;
import java.io.ObjectInputStream;

import javax.imageio.metadata.IIOMetadataFormat;

public class ServerClientReaderThread extends Thread {
	private int id;
	private ObjectInputStream input;
	private Server server;
	private Player player;
	public ServerClientReaderThread(int id, ObjectInputStream input, Server server) {
		super();
		this.server = server;
		this.id = id; 
		this.input = input;
		this.player = server.getPlayers()[id];
	}
 
	@Override
	public void run() {
		ServerTimerThread timer = server.getTimer();
		try {
			
		
		while (!Thread.currentThread().isInterrupted()) {
				try {// reading input
					Object o;
					o = input.readObject();
					 
					while(!(o instanceof Player.ValidInput))o = input.readObject();
					// processing input
					 
					Player.ValidInput playerInput = (Player.ValidInput) o;
					
					player.processInput(playerInput);
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		}catch (IOException e) {
			System.out.println("ServerClientReader closing for player: " + id);
			//e.printStackTrace();
		}
	}
}
