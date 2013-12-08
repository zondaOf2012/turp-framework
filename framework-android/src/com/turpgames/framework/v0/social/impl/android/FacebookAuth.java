package com.turpgames.framework.v0.social.impl.android;

import org.brickred.socialauth.Profile;
import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;
import org.brickred.socialauth.android.SocialAuthError;

import android.content.Context;
import android.os.Bundle;

import com.turpgames.framework.v0.social.ILoginCallback;
import com.turpgames.framework.v0.social.ISocialAuth;
import com.turpgames.framework.v0.social.Player;

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
		dialogListener.callback = callback;
		adapter.authorize(context, Provider.FACEBOOK);
	}

	private static class MyDialogListener implements DialogListener {
		public ILoginCallback callback;
		public SocialAuthAdapter adapter;

		@Override
		public void onError(SocialAuthError e) {
			callback.onLoginFail();
		}

		@Override
		public void onComplete(Bundle values) {
			Profile p = adapter.getUserProfile();

			Player player = new Player();
			player.setAvatarUrl(p.getProfileImageURL());
			player.setEmail(p.getEmail());
			player.setSocialId(p.getValidatedId());
			player.setName(p.getFullName());

			callback.onLoginSuccess(player);

			// System.out.println("user-id:       " + p.getValidatedId());
			// System.out.println("display-name:  " + p.getDisplayName());
			// System.out.println("first-name:    " + p.getFirstName());
			// System.out.println("last-name:     " + p.getLastName());
			// System.out.println("full-name:     " + p.getFullName());
			// System.out.println("email:         " + p.getEmail());
			// System.out.println("date-of-birth: " + p.getDob());
			// System.out.println("gender:        " + p.getGender());
			// System.out.println("language:      " + p.getLanguage());
			// System.out.println("country:       " + p.getCountry());
			// System.out.println("location:      " + p.getLocation());
			// System.out.println("img-url:       " + p.getProfileImageURL());
			// System.out.println("provider-id:   " + p.getProviderId());
		}

		@Override
		public void onCancel() {
			callback.onLoginFail();
		}

		@Override
		public void onBack() {
			callback.onLoginFail();
		}
	}
}