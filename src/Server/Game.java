package Server;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;

public class Game extends Frame implements Runnable{
	private Client client;
	private Thread gameLoop = new Thread(this);
	private Label label;
    public Game() { 
        setTitle("Game Client");
        setSize(400, 300);
        setLayout(new FlowLayout()); 
        
        
        this.client = new Client("localhost", 7071);
        
        
		this.label = new Label("Start.");
        add(label);
        
        addListeners();
        setVisible(true);
        requestFocus(); 

        gameLoop.start();
        client.start();
    }
    
    @Override
    public void run() {
    	Player[] players = null;
    	try {
    		System.out.println("alo");
	    	while (!Thread.currentThread().isInterrupted()) {
				Thread.sleep(50);
				synchronized (client) {
					client.wait();
					players = client.getPlayers();
				}
				
				if(players == null) continue;
				System.out.println("alo");
				String text = "";
				for (int i = 0; i < players.length; i++) {
					if(players[i]!=null)
						text += players[i].toString() + "\n";
				}
				label.setText(text);
				
				repaint();
				revalidate();
			} 
	    }catch (InterruptedException e) {
				System.out.println("Finishing.");
		}
    	
    };
    
    private void addListeners() {
    	 // Omogućava da prozor hvata fokus
        
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    synchronized (client) {
						client.setCurrentInput(Player.ValidInput.w);
						client.notifyAll();
					}
                    
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        // Zatvaranje prozora na "X"
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                dispose();
                gameLoop.interrupt();
            	System.exit(0);
            }
        });
	}
    
    
    

    public static void main(String[] args) {
        new Game(); 
    }
}
