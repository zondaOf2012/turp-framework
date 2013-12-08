package com.turpgames.framework.v0.social.impl.ios;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.turpgames.framework.v0.social.ISocialAuth;
import com.turpgames.framework.v0.social.ISocializer;

public class IOSSocializer implements ISocializer {

	private final static Map<String, ISocialAuth> auths = new ConcurrentHashMap<String, ISocialAuth>();

	@Override
	public ISocialAuth createAuth(String key) {
		if (auths.containsKey(key))
			return auths.get(key);

		if ("facebook".equals(key))
			auths.put(key, new FacebookAuth());
		else
			auths.put(key, ISocialAuth.NULL);

		return auths.get(key);
	}
}
