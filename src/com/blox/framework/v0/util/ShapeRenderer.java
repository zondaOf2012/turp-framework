package com.blox.framework.v0.util;

import com.blox.framework.v0.IShapeRenderer;

public final class ShapeRenderer {
	private final static IShapeRenderer renderer;

	private ShapeRenderer() {

	}

	static {
		renderer = Game.getShapeRenderer();
	}

	public static void drawRect(float x, float y, float w, float h, Color color, boolean filled, boolean ignoreViewport) {
		if (!ignoreViewport) {
			x = Game.viewportToScreenX(x);
			y = Game.viewportToScreenY(y);
			w = Game.scale(w);
			y = Game.scale(y);
		}

		renderer.drawRect(x + Game.getRenderingShiftX(), y + Game.getRenderingShiftY(), w, h, color, filled);
	}

	public static void drawLine(float x0, float y0, float x1, float y1, Color color, boolean ignoreViewport) {
		if (!ignoreViewport) {
			x0 = Game.viewportToScreenX(x0);
			y0 = Game.viewportToScreenY(y0);
			x1 = Game.viewportToScreenX(x1);
			y1 = Game.viewportToScreenY(y1);
		}
		renderer.drawLine(
				x0 + Game.getRenderingShiftX(),
				y0 + Game.getRenderingShiftY(),
				x1 + Game.getRenderingShiftX(),
				y1 + Game.getRenderingShiftY(),
				color);
	}
}
