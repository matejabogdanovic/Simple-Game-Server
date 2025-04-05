package Shared;

import java.io.Serializable;

public class Player implements Serializable{
	private static final long serialVersionUID = 1L;
	private String name;
	private int x = 0, y = 0;
	 
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	private int newX, newY;
	private interface Func {
		void exec();
	}
	transient private Func[] process = {
			()->this.processW(),
			()->this.processA(),
			()->this.processS(),
			()->this.processD(),
	};
	public enum ValidInput {
		w(0),
		a(1),
		s(2),
		d(3);
		private final int value;
		ValidInput(int value) {
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}
		
	}
	public Player(String name) {
		this.name = name;
	}

	public void processInput(ValidInput input) {
		process[input.value].exec();
	}
	
	public void applyInput() {
		System.out.println("Applying input: " + newX + " " + newY);
		this.x = newX;
		this.y = newY;
	} 
	
	private void processW() {
		newY = y-1;
	}
	
	private void processA() {
		newX = x-1;
	}
	
	private void processS() {
		newY = y+1;	
	}
	
	private void processD() {
		newX = x+1;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "player: " + name + " (x,y):" + "(" + x + "," + y + ")";
	}

}
