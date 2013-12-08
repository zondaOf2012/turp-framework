package com.turpgames.framework.v0.db;

public interface IConnectionProvider {
	String getConnectionProvider();

	String getConnectionString();

	String getUsername();

	String getPassword();
}
