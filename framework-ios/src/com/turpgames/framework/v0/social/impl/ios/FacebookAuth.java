package com.turpgames.framework.v0.social.impl.ios;

import org.robovm.bindings.facebook.FBSessionDefaultAudience;
import org.robovm.bindings.facebook.FBSessionLoginBehavior;
import org.robovm.bindings.facebook.manager.FBPermission;
import org.robovm.bindings.facebook.manager.FBProfile;
import org.robovm.bindings.facebook.manager.FacebookConfiguration;
import org.robovm.bindings.facebook.manager.FacebookManager;
import org.robovm.cocoatouch.foundation.NSDictionary;
import org.robovm.cocoatouch.foundation.NSError;
import org.robovm.cocoatouch.foundation.NSString;
import org.robovm.cocoatouch.uikit.UIControlState;
import org.robovm.cocoatouch.uikit.UIScreen;

import com.turpgames.framework.v0.social.ILoginCallback;
import com.turpgames.framework.v0.social.ISocialAuth;
import com.turpgames.framework.v0.social.Player;

class FacebookAuth implements ISocialAuth {
	private FacebookManager facebook;

	private final String APP_ID = "548861391863567";
	private final String APP_NAMESPACE = "turpgames-ichigu";

	public FacebookAuth() {
		facebook = FacebookManager.getInstance();

		FBPermission[] fbPermissions = new FBPermission[] {
				FBPermission.BASIC_INFO, FBPermission.EMAIL,
				FBPermission.PUBLISH_ACTION };

		FacebookConfiguration fbConfiguration = new FacebookConfiguration.Builder()
				.setAppId(APP_ID).setNamespace(APP_NAMESPACE)
				.setPermissions(fbPermissions)
				.setDefaultAudience(FBSessionDefaultAudience.Everyone)
				.setLoginBehavior(FBSessionLoginBehavior.NoFallbackToWebView)
				.build();

		facebook.setConfiguration(fbConfiguration);
	}

	@Override
	public void login(final ILoginCallback callback) {
		System.out.println("FacebookAuth.login");
		facebook.login(new FacebookManager.LoginListener() {
			@Override
			public void onLogin() {
				System.out.println("login.onLogin");
				facebook.getProfile(new FacebookManager.ProfileRequestListener() {
					@Override
					public void onFail(String reason) {
						System.out.println("getProfile.onFail");
						callback.onLoginFail();
					}

					@Override
					public void onException(NSError throwable) {
						System.out.println("getProfile.onException");
						callback.onLoginFail();
					}

					@Override
					public void onRequest() {
						System.out.println("getProfile.onRequest");

					}

					@Override
					public void onComplete(FBProfile profile) {
						System.out.println("getProfile.onComplete");
						Player player = new Player();

						// player.setAvatarUrl(profile.getGraphUser().get());
						player.setEmail(profile.getEmail());
						player.setName(profile.getName());
						player.setSocialId(profile.getId());

						System.out.println(profile.getName());
						System.out.println(profile.getId());

						NSDictionary dic = profile.getGraphUser();
						for (Object key : dic.keySet())
							System.out.println(key + ": " + dic.get(key));

						callback.onLoginSuccess(player);
					}
				});
			}

			@Override
			public void onRequest() {
				System.out.println("login.onRequest");
			}

			@Override
			public void onFail(String reason) {
				System.out.println("login.onFail");
				callback.onLoginFail();
			}

			@Override
			public void onException(NSError throwable) {
				System.out.println("login.onException");
				callback.onLoginFail();
			}

			@Override
			public void onNotAcceptingPermissions() {
				System.out.println("login.onNotAcceptingPermissions");
				callback.onLoginFail();
			}
		});
	}
}