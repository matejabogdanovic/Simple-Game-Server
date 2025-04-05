package Game;
import Server.Server;

public class GameServer {
	public static void main(String[] args) throws Exception {
		Server server = new Server(2, 7071);
		server.start(); 
	}
}
