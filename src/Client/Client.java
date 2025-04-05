package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import Shared.Player;




public class Client{
	private String host;
	private int port;
	private String name;
	private ClientReaderThread readerThread;
	private ClientWriterThread writerThread;
	private Player[] players;
	
	
	public Client(String host, int port, String name){
		this.host = host;
		this.port = port; 
		this.name = name;
	}
	
	public void setCurrentInput(Player.ValidInput input) {
		writerThread.setCurrentInput(input);
	}
	
	public void start() {
		try(Socket server = new Socket(host,port);
			OutputStream os = server.getOutputStream();
			InputStream is = server.getInputStream();
			ObjectOutputStream pout = new ObjectOutputStream(os);
			ObjectInputStream pin = new ObjectInputStream(is);) {
			
			pout.writeObject(name);
			
			readerThread = new ClientReaderThread(this, pin);
			writerThread = new ClientWriterThread(this, pout);
			readerThread.start(); 
			writerThread.start();
 
			readerThread.join();
			writerThread.join();
 
		} catch (IOException e) {
			System.out.println("Server didn't respond.");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(readerThread != null)
				readerThread.interrupt();
			if(writerThread != null)
				writerThread.interrupt();
			
		}
	}

	public Player[] getPlayers() {
		return players;
	}

	public void setPlayers(Player[] players) {
		this.players = players;
	}

}
