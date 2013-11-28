package com.turpgames.framework.v0.social;

public interface ILoginCallback {
	void onLoginSuccess(String userId);

	void onLoginFail();
}
