import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

/**
 * Main class for the game
 */

public class Game extends JFrame implements KeyListener {

	private BufferedImage backBuffer = null;
	private Insets insets = null;
	private boolean isRunning = true;

	private static Mouse[] mouses = null;
	private Cat c = null;

	private int mousesNumber;

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {

		Game game = new Game(Constants.MOUSES);
		game.startMouses();
		game.startCat();
		game.run();
		System.exit(0);

	}

	/**
	 * Initialize the Game with the number of mouses in it
	 * 
	 * @param number
	 */
	public Game(int number) {
		this.mousesNumber = number;
		mouses = new Mouse[number];
		c = new Cat();
		initialize();
	}

	/**
	 * Starts the threads mouses to make them move.
	 */
	public void startMouses() {
		for (int i = 0; i < mouses.length; i++) {
			mouses[i] = new Mouse(i, i * Constants.DELAY_MOUSES);
			new Thread(mouses[i]).start();
		}
	}

	/**
	 * Start the cat thread
	 */
	public void startCat() {
		new Thread(c).start();
	}

	/**
	 * Loop function used to print the game
	 */
	public void run() {

		while (isRunning) {
			long time = System.currentTimeMillis();

			draw();
			isOnMouse(c.getX(), c.getY());

			time = (1000 / Constants.FPS) - (System.currentTimeMillis() - time);

			if (time > 0) {
				try {
					Thread.sleep(time);
				} catch (Exception e) {
					System.out
							.println("Error with the interruption of the main thread");
				}
			}
		}
	}

	/**
	 * Initialize the game
	 */
	void initialize() {

		setTitle("Game");
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setVisible(true);

		// Use the insets to have a correct size for the screen
		insets = getInsets();
		setSize(insets.left + Constants.PIXEL_X + insets.right, insets.bottom
				+ insets.top + Constants.PIXEL_Y);

		backBuffer = new BufferedImage(Constants.PIXEL_X, Constants.PIXEL_Y,
				BufferedImage.TYPE_INT_RGB);

		addKeyListener(this);

	}

	/**
	 * Draw the cat and the mouses on the doublebuffer Draw the doublebuffer on
	 * the Graphics of the frame
	 */
	void draw() {

		Graphics g = getGraphics();
		Graphics bbg = backBuffer.getGraphics();

		bbg.setColor(Color.WHITE);
		bbg.fillRect(0, 0, Constants.PIXEL_X, Constants.PIXEL_Y);

		for (int i = 0; i < mouses.length; i++) {
			mouses[i].draw(bbg);
		}

		c.draw(bbg);

		g.drawImage(backBuffer, insets.left, insets.top, this);
	}

	/**
	 * Check if the current position is a mouse.
	 * 
	 * @param x
	 * @param y
	 */
	private void isOnMouse(int x, int y) {
		for (int i = 0; i < mouses.length; i++) {
			if (mouses[i].getX() == x && mouses[i].getY() == y
					&& !mouses[i].isDead()) {
				mouses[i].die();
				mousesNumber--;
			}
		}

		if (mousesNumber == 0) {
			System.out.println("Fin du jeu!!!!");
			System.exit(0);
		}

	}

	/**
	 * Check if the cell is already occupied
	 * 
	 * @param id
	 * @param x
	 * @param y
	 * @return
	 */
	public static boolean alreadyOccupied(int id, int x, int y) {
		for (int i = 0; i < mouses.length; i++) {
			if (mouses[i] != null) {
				// The mouses could also try to escape the cat by adding
				// cat.getX()==x && cat.getY()==y
				if (mouses[i].getId() != id && mouses[i].getX() == x
						&& mouses[i].getY() == y) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * KeyListener to make the cat move according to the key pressed.
	 */
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_RIGHT:
			c.changeDirection(0);
			break;
		case KeyEvent.VK_LEFT:
			c.changeDirection(1);
			break;
		case KeyEvent.VK_DOWN:
			c.changeDirection(2);
			break;
		case KeyEvent.VK_UP:
			c.changeDirection(3);
			break;
		}
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}