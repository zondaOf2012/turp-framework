package com.turpgames.framework.v0.social;

public interface ISocialAuth {
	public static final ISocialAuth NULL = new ISocialAuth() {
		@Override
		public void login(ILoginCallback callback) {
			callback.onLoginFail();
		}
	};

	void login(ILoginCallback callback);
}
