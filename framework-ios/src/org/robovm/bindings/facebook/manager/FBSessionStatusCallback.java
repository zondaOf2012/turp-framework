
package org.robovm.bindings.facebook.manager;

import org.robovm.bindings.facebook.FBSession;
import org.robovm.bindings.facebook.FBSessionState;
import org.robovm.bindings.facebook.FBSessionStateHandler;
import org.robovm.bindings.facebook.manager.FacebookManager.ExtendPermissionsListener;
import org.robovm.bindings.facebook.manager.FacebookManager.LoginListener;
import org.robovm.bindings.facebook.manager.FacebookManager.LogoutListener;
import org.robovm.cocoatouch.foundation.NSError;

import com.turpgames.debug.ios.Debug;

/** This class is used to handle common Facebook events. */
public class FBSessionStatusCallback implements FBSessionStateHandler {
	private static final String TAG = "[FBSessionStatusCallback] ";
	LoginListener loginListener = null;
	LogoutListener logoutListener = null;
	ExtendPermissionsListener extendPermissionsListener = null;

	@Override
	public void invoke (FBSession session, FBSessionState state, NSError error) {
		if (error != null) {
			Debug.println(TAG + "Exception: " + error.description());

			if (error.toString().contains("Code=2")) {
				Debug.println(TAG + "User canceled login dialog.");
				if (session.getPermissions().size() == 0) {
					if (loginListener != null) loginListener.onNotAcceptingPermissions();
				}
			} else if (loginListener != null) {
				loginListener.onException(error);
			}
		}

		Debug.println(TAG + "FBSession: state=" + state.name() + ", session=" + session.toString());
		switch (state) {
		case Closed:
			Debug.println("Closed");
			if (logoutListener != null) {
				Debug.println("calling logoutListener.onLogout");
				logoutListener.onLogout();
			}
			else
				Debug.println("logoutListener null");
			break;
		case ClosedLoginFailed:
			Debug.println("ClosedLoginFailed");
			break;
		case Created:
			Debug.println("Created");
			break;
		case CreatedTokenLoaded:
			Debug.println("CreatedTokenLoaded");
			break;
		case Opening:
			Debug.println("Opening");
			if (loginListener != null) {
				Debug.println("calling loginListener.onRequest");
				loginListener.onRequest();
			}else {
				Debug.println("loginListener null");
			}
			break;
		case Open:
			Debug.println("Open");
			if (extendPermissionsListener != null) {
				Debug.println("extendPermissionsListener not null");
			} else {
				Debug.println(FacebookManager.TAG + "Successfully logged in!");
				if (loginListener != null) {
					Debug.println("calling loginListener.onLogin");
					loginListener.onLogin();
				}
				else {
					Debug.println("loginListener null");					
				}
			}
			break;
		case OpenTokenExtended:
			Debug.println("OpenTokenExtended");
			if (extendPermissionsListener != null) {
				Debug.println("calling extendPermissionsListener.onSuccess");
				extendPermissionsListener.onSuccess();
				extendPermissionsListener = null;
			} else {
				Debug.println(FacebookManager.TAG + "Successfully logged in!");
				if (loginListener != null) {
					Debug.println("calling loginListener.onLogin");
					loginListener.onLogin();
				}
				else {
					Debug.println("loginListener null");
				}
			}
			break;
		default:
			Debug.println("default");
			break;
		}
	}
}
