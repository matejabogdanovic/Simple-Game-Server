package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;



public class Client{
	private String host;
	private int port;
	private ClientReaderThread readerThread;
	private ClientWriterThread writerThread;
	private Player[] players;
	
	
	public Client(String host, int port){
		this.host = host;
		this.port = port; 
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
			
			pout.writeObject("Mateja");
			
			readerThread = new ClientReaderThread(this, pin);
			writerThread = new ClientWriterThread(this, pout);
			readerThread.start();
			writerThread.start();

			try {
				readerThread.join();
				writerThread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} catch (IOException e) {
			// TODO: handle exception
		}finally {
			readerThread.interrupt();
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
