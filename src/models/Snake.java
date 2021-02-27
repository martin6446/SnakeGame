package models;

public class Snake {

	private int bodyParts = 3;
	private int applesEaten;
	private char direction = 'R';
	private int[] x;
	private int[] y;
	
	
	
	public Snake(int x, int y) {
		this.x = new int[x];
		this.y = new int [y];
	}
	
	public void incrementBodyParts() {
		bodyParts++;
	}
	
	public int getBodyParts() {
		return bodyParts;
	}
	
	public void eatApple() {
		applesEaten++;
	}
	
	public int getApplesEaten() {
		return applesEaten;
	}
	
	public int[] getX() {
		return x;
	}
	
	public int[] getY() {
		return y;
	}
	
	public void setDirection(char direction) {
		this.direction = direction;
	}
	
	public char getDirection() {
		return direction;
	}
	
	public void move(int unitSize) {
		//this 'moves' the body
		for (int i = bodyParts; i > 0; i--) {
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}

		//this 'moves' the head
		switch (direction) {
		case 'U':
			y[0] = y[0] - unitSize;
			break;
		case 'D':
			y[0] = y[0] + unitSize;
			break;
		case 'L':
			x[0] = x[0] - unitSize;
			break;
		case 'R':
			x[0] = x[0] + unitSize;
			break;
		}
	}
	
	
	public boolean checkCollisions(int screenWidth, int screenHeight) {
		// checks if head collides with body
		
		for (int i = bodyParts; i > 0; i--) {
			if ((x[0] == x[i]) && (y[0] == y[i])) {
				return false;
			}
		}
		
		//simplify if statement
		 return ((x[0] < 0) || (x[0] > screenWidth) || (y[0] < 0) || (y[0] > screenHeight)) ? false : true;

	}
	
	
	 
	
}
