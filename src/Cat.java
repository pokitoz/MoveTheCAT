import java.awt.Color;
import java.awt.Graphics;

public class Cat extends Animal {

	private int direction;

	public Cat() {
		super(-1, Constants.CAT_SPEED, Constants.DELAY_CAT);
		direction = 0;
	}

	public void run() {

		while (!isDead) {
			try {

				int new_x = x;
				int new_y = y;

				do {
					new_x = x;
					new_y = y;

					switch (direction) {
					case 0:
						new_x++;
						break;
					case 1:
						new_x--;
						break;
					case 2:
						new_y++;
						break;
					default:
						new_y--;
						break;
					}

				} while (outOfWindow(new_x, new_y) && !isDead);

				x = new_x;
				y = new_y;
				Thread.sleep((int) (speed));

			} catch (InterruptedException e) {
				System.err.println("Problem with the cat");
				e.printStackTrace();
			}
		}

	}

	/**
	 * Change the direction of the cat
	 * 
	 * @param direction
	 */
	public void changeDirection(int direction) {
		// Control if the direction is correct
		if (direction < 0 || direction > 3) {
			return;
		}
		this.direction = direction;
	}

	/**
	 * Print the cat in red
	 */
	public void draw(Graphics g2) {

		if (!isDead) {
			g2.setColor(Color.red);
			g2.fillOval(x * Constants.CIRCLE_PIXEL, y * Constants.CIRCLE_PIXEL,
					Constants.CIRCLE_PIXEL, Constants.CIRCLE_PIXEL);
		}
	}

}
