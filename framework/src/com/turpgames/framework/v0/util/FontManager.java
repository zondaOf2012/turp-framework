package com.turpgames.framework.v0.util;

import com.turpgames.framework.v0.IFont;

public final class FontManager {
	private FontManager() {

	}

	public static final IFont defaultFont;
	public static float defaultFontSize;

	static {
		defaultFont = createDefaultFontInstance();
	}

	public static IFont createDefaultFontInstance() {
		// default-font = "Arial,0.5"
		String df = Game.getParam("default-font");
		String[] ss = df.split(",");
		IFont font = Game.getResourceManager().getFont(ss[0]);
		defaultFontSize = Utils.parseFloat(ss[1]);
		font.setScale(defaultFontSize);
		return font;
	}
}