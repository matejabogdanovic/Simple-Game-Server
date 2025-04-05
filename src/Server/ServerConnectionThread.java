package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import Shared.Player;

public class ServerConnectionThread extends Thread{
	private Socket client;
	private int id;
	private ServerClientReaderThread readerThread;
	private ServerClientWriterThread writerThread;
	private Server server;
	public ServerConnectionThread(int id, Socket client, Server server) {
		super();
		this.server = server;
		this.id = id; 
		this.client = client;
	}
	
	
	@Override
	public void run() {
		try (InputStream is = client.getInputStream();
				OutputStream os = client.getOutputStream();
		 		ObjectInputStream input = new ObjectInputStream(is);
				ObjectOutputStream output = new ObjectOutputStream(os);){
			
			try {// get player name 
				Object name = input.readObject();
				while(!(name instanceof String))name = input.readObject();
				String playerName = (String)name; 
				// create player 
				server.getPlayers()[id] = new Player(playerName);
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
 
			this.readerThread = new ServerClientReaderThread(id, input, server);
			this.writerThread = new ServerClientWriterThread(id, output, server);
			readerThread.start();
			writerThread.start();
			
			
			readerThread.join();
			writerThread.join();
			
		} catch (IOException e) {
			System.out.println("Client: "+id + " closed connection.");

		}catch (InterruptedException e) {
			
		}finally {
			readerThread.interrupt();
			writerThread.interrupt();
			
		}
		System.out.println("Closing connection for: "+id);
	}
}
