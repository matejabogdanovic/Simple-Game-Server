package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import Shared.Player;

public class Server {
	private ServerTimerThread timer = new ServerTimerThread();
	private ServerConnectionThread[] connectionThreads;
	private Player[] players;
	private int port;
	
	public Server(int playerCnt, int port) throws Exception{
		if(playerCnt <= 0)throw new Exception("Invalid playerCnt.");
		players = new Player[playerCnt];
		connectionThreads = new ServerConnectionThread[playerCnt];
		this.port = port; 
		this.timer.start();
	}
	
	private void accept(int id ,ServerSocket server) {
		try {
			Socket client = server.accept();
			connectionThreads[id] = new ServerConnectionThread(id, client, this);
			connectionThreads[id].start();
		} catch (IOException e) {
			
			e.printStackTrace();
		} 
	}
	
	public void start() throws InterruptedException {
		try(ServerSocket server = new ServerSocket(this.port);){
			
			for (int i = 0; i < players.length; i++) {
				accept(i, server);
			}
			
			for (int i = 0; i < connectionThreads.length; i++) {
				connectionThreads[i].join();
			} 
			
			timer.interrupt();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ServerTimerThread getTimer() {
		return this.timer;
	}
	 
	public Player[] getPlayers() {
		return players;
	}
	
	public void setPlayer(int index, Player player) {
		players[index] = player;
	}
	 
//	public static void main(String[] args) throws Exception {
//		Server server = new Server(2, 7071);
//		server.start();
//	}
}
