package server.remote;

import java.awt.Dimension;
import java.awt.Toolkit;

public class RoundSprite {

	float x, y, radius;
	float speedX, speedY;
	Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
	int width = res.width;
	int height = res.height;

	protected RoundSprite(float x, float y, float radius, float speedX, float speedY) {
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.speedX = speedX;
		this.speedY = speedY;
	}

	void move() {
		x += speedX;
		y += speedY;
	}

	void checkBoundaries() {
		if (x > width - radius) {
			x = width - radius;
			speedX *= -1;

		}
		if (x < radius) {
			x = radius;
			speedX *= -1;
		}
		if (y > height - radius) {
			y = height - radius;
			speedY *= -1;
		}
		if (y < radius) {
			y = radius;
			speedY *= -1;
		}
	}
}