
import java.awt.Graphics;

public abstract class Animal implements Runnable {

	protected int id;
	protected int x;
	protected int y;
	protected boolean count = false;
	protected int speed;
	protected int delay;
	protected boolean isDead;

	
	public Animal(int id, int speed, int delay) {
		this.id = id;
		this.speed = speed;
		this.delay = delay;
		this.x = (int) (Math.random() * Constants.MAX_X);
		this.y = (int) (Math.random() * Constants.MAX_Y);
		this.isDead = false;
	}

	/**
	 * Check if the new position is out of the window
	 * 
	 * @param new_x
	 * @param new_y
	 * @return
	 */
	protected boolean outOfWindow(int new_x, int new_y) {
		return new_x < 0 || new_x > Constants.MAX_X - 1 || new_y < 0
				|| new_y > Constants.MAX_Y - 1;
	}

	public abstract void run();

	public abstract void draw(Graphics g);

	/**
	 * Kill the animal
	 */
	public void die() {
		isDead = true;
	}

	public boolean isDead() {
		return isDead;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getId() {
		return id;
	}

}
