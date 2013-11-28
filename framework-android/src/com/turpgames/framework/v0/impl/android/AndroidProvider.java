package com.turpgames.framework.v0.impl.android;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;

import com.turpgames.framework.v0.IEnvironmentProvider;
import com.turpgames.framework.v0.social.ISocializer;
import com.turpgames.framework.v0.social.impl.android.AndroidSocializer;
import com.turpgames.framework.v0.util.Version;

public class AndroidProvider implements IEnvironmentProvider {
	private final Context context;

	public AndroidProvider(Context context) {
		this.context = context;
	}

	private Version version;
	private ISocializer socializer;
	
	@Override
	public Version getVersion() {
		if (version == null) {
			try {
				String versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
				version = new Version(versionName);
			}
			catch (NameNotFoundException e) {
				e.printStackTrace();
				version = new Version("1.0");
			}
		}
		return version;
	}

	@Override
	public ISocializer getSocializer() {
		if (socializer == null)
			socializer = new AndroidSocializer(context);
		return socializer;
	}
}
