package models;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {

	final int SCREEN_WIDTH = 600;
	final int SCREEN_HEIGHT = 600;
	final int UNIT_SIZE = 25;
	final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
	final int DELAY = 75;
	int appelX;
	int appelY;
	boolean running = false;
	Timer timer;
	Random random;
	Snake snake;

	GamePanel() {
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	
	public void restart() {
		snake = new Snake(GAME_UNITS, GAME_UNITS);
		newAppel();
		running = true;
		timer.restart();
	}

	public void startGame() {
		snake = new Snake(GAME_UNITS, GAME_UNITS);
		newAppel();
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	public void draw(Graphics g) {

		if (running) {
			int snakeBody = snake.getBodyParts();

			g.setColor(Color.red);
			g.fillOval(appelX, appelY, UNIT_SIZE, UNIT_SIZE);

			for (int i = 0; i < snakeBody; i++) {
				if (i == 0) {
					g.setColor(Color.green);
					g.fillRect(snake.getX()[i], snake.getY()[i], UNIT_SIZE, UNIT_SIZE);
				} else {
					g.setColor(new Color(143, 187, 153));
					g.fillRect(snake.getX()[i], snake.getY()[i], UNIT_SIZE, UNIT_SIZE);

				}
			}

			g.setColor(Color.red);
			g.setFont(new Font("Ink Free", Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: " + snake.getApplesEaten(),
					(SCREEN_WIDTH - metrics.stringWidth("Score: " + snake.getApplesEaten())) / 2,
					g.getFont().getSize());

		} else {
			gameOver(g);
		}
	}

	public void newAppel() {
		appelX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
		appelY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
	}

	public void checkAppel() {
		if ((snake.getX()[0] == appelX) && (snake.getY()[0] == appelY)) {
			snake.incrementBodyParts();
			snake.eatApple();
			newAppel();
		}
	}

	public void gameOver(Graphics g) {
		// display final score
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: " + snake.getApplesEaten(),
				(SCREEN_WIDTH - metrics1.stringWidth("Score: " + snake.getApplesEaten())) / 2, g.getFont().getSize());

		// Game Over Text
		g.setColor(Color.red);
		g.setFont(new Font("Bauhaus 93", Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
		
		//Restart Text
		g.setColor(Color.red);
		g.setFont(new Font(null, Font.BOLD, 30));
		FontMetrics metrics3 = getFontMetrics(g.getFont());
		g.drawString("Play Again? Press ENTER", (SCREEN_WIDTH - metrics3.stringWidth("Play Again? Press ENTER")) / 2, SCREEN_HEIGHT - g.getFont().getSize() * 4);
		
		//Exit Text
		g.setColor(Color.red);
		g.setFont(new Font(null, Font.BOLD, 30));
		FontMetrics metrics4 = getFontMetrics(g.getFont());
		g.drawString("Press ESC to exit", (SCREEN_WIDTH - metrics4.stringWidth("Press ESC to exit")) / 2, SCREEN_HEIGHT - g.getFont().getSize() * 2);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (running) {
			snake.move(UNIT_SIZE);
			checkAppel();
			running = snake.checkCollisions(SCREEN_WIDTH, SCREEN_HEIGHT);
		}
		repaint();

	}

	public class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if (snake.getDirection() != 'R') {
					snake.setDirection('L');
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (snake.getDirection() != 'L') {
					snake.setDirection('R');
				}
				break;
			case KeyEvent.VK_UP:
				if (snake.getDirection() != 'D') {
					snake.setDirection('U');
				}
				break;
			case KeyEvent.VK_DOWN:
				if (snake.getDirection() != 'U') {
					snake.setDirection('D');
				}
				break;
			case KeyEvent.VK_ENTER:
				if(!running) {
					restart();
				}
				break;
			case KeyEvent.VK_ESCAPE:
				System.exit(0);
			}
		}

	}

}
