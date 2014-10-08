
import java.awt.Color;
import java.awt.Graphics;

public class Mouse extends Animal {

	public Mouse(int id, int delay) {
		super(id, Constants.MOUSE_SPEED, delay);
	}

	public void run() {
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		while (!isDead) {
			try {
				int new_x = x;
				int new_y = y;

				do {
					new_x = x;
					new_y = y;

					int direction = (int) (Math.random() * 4);
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
				} while (Game.alreadyOccupied(id, new_x, new_y)
						|| outOfWindow(new_x, new_y));

				x = new_x;
				y = new_y;

				Thread.sleep((int) (speed));
			} catch (InterruptedException e) {
				System.err.println("Problem with a mouse");
				e.printStackTrace();
			}
		}

	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String toString() {
		return "x " + x + " " + "y " + y;
	}

	public void draw(Graphics g2) {

		if (!isDead) {

			g2.setColor(Color.green);
			g2.fillOval(x * Constants.CIRCLE_PIXEL, y * Constants.CIRCLE_PIXEL,
					Constants.CIRCLE_PIXEL, Constants.CIRCLE_PIXEL);

		}
	}

}
