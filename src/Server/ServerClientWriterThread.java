package Server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.SocketException;

import Shared.Player;

public class ServerClientWriterThread extends Thread{
	private int id;
	private ObjectOutputStream output;
	private Server server;
	public ServerClientWriterThread(int id, ObjectOutputStream output, Server server) {
		super();
		this.server = server;
		this.id = id;
		this.output = output;
	} 
	 
	@Override
	public void run() {
		ServerTimerThread timer = server.getTimer();
		Player[] players = null;
		try {	
			while (!Thread.currentThread().isInterrupted()) {
				synchronized (timer) {
					timer.wait(); 
				}
				output.reset(); 
				players = server.getPlayers();
				if(players[id] == null)break;
				players[id].applyInput();
				output.writeObject(players);
				  
			}
		}catch (InterruptedException | IOException e) {
			System.out.println("ServerClientWriter closing for player: " + id);
			//e.printStackTrace();
		}
	}
}
