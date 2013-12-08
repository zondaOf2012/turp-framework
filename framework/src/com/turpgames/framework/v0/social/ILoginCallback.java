package com.turpgames.framework.v0.social;

public interface ILoginCallback {
	void onLoginSuccess(Player player);

	void onLoginFail();
}
