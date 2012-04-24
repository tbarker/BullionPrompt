package com.thomasbarker.bullionprompt.network;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;

public final class HttpClients {

	public static DefaultHttpClient getThreadSafeClient() {
		DefaultHttpClient client = new DefaultHttpClient();
		ClientConnectionManager mgr = client.getConnectionManager();
		client = new DefaultHttpClient( new ThreadSafeClientConnManager( mgr.getSchemeRegistry() ) );
	
		return client;
	}

}
