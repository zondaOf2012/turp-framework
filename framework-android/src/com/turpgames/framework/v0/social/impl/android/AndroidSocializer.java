package com.turpgames.framework.v0.social.impl.android;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import android.app.Activity;
import android.content.Context;

import com.turpgames.framework.v0.social.ISocialAuth;
import com.turpgames.framework.v0.social.ISocializer;
import com.turpgames.framework.v0.util.Utils;

public class AndroidSocializer implements ISocializer {
	private final Context context;

	public AndroidSocializer(Context context) {
		this.context = context;
	}

	private final static Map<String, ISocialAuth> auths = new ConcurrentHashMap<String, ISocialAuth>();

	public ISocialAuth createAuth(final String key) {
		if (auths.containsKey(key))
			return auths.get(key);

		((Activity) context).runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if ("facebook" == key)
					auths.put(key, new FacebookAuth(context));
				else
					auths.put(key, ISocialAuth.NULL);
			}
		});

		while (!auths.containsKey(key))
			Utils.threadSleep(100);

		return auths.get(key);
	}
}
