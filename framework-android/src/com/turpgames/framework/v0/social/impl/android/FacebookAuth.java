package com.turpgames.framework.v0.social.impl.android;

import org.brickred.socialauth.Profile;
import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.turpgames.framework.v0.social.ILoginCallback;
import com.turpgames.framework.v0.social.ISocialAuth;

class FacebookAuth implements ISocialAuth {
	private final SocialAuthAdapter adapter;
	private final Context context;
	private MyDialogListener dialogListener;
	
	public FacebookAuth(Context ctx) {		
		context = ctx;
		dialogListener = new MyDialogListener();
		adapter = new SocialAuthAdapter(dialogListener);
		dialogListener.adapter = adapter;
	}
	
	public void login(final ILoginCallback callback) {
		adapter.authorize(context, Provider.FACEBOOK);
		dialogListener.callback = callback;
	}
	
	private static class MyDialogListener implements DialogListener {
		public ILoginCallback callback;
		public SocialAuthAdapter adapter;
		
		@Override
		public void onError(SocialAuthError e) {
				int x = 1;
				callback.onLoginFail();				
		}
		
		@Override
		public void onComplete(Bundle values) {
			int x = 1;
			callback.onLoginSuccess(adapter.getUserProfile().getValidatedId());
		}
		
		@Override
		public void onCancel() {
			int x = 1;
			callback.onLoginFail();				
		}
		
		@Override
		public void onBack() {
			int x = 1;
			callback.onLoginFail();				
		}
	}
}