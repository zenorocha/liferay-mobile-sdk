/**
 * Copyright (c) 2000-2014 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.android.service;

import com.liferay.mobile.android.auth.Authentication;
import com.liferay.mobile.android.http.HttpUtil;
import com.liferay.mobile.android.task.ServiceAsyncTask;
import com.liferay.mobile.android.task.UploadAsyncTask;
import com.liferay.mobile.android.task.callback.AsyncTaskCallback;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Bruno Farache
 */
public class SessionImpl implements Session {

	public static final int DEFAULT_CONNECTION_TIMEOUT = 15000;

	public SessionImpl(Session session) {
		this(
			session.getServer(), session.getAuthentication(),
			session.getConnectionTimeout(), session.getCallback());
	}

	public SessionImpl(String server) {
		this(server, null, null);
	}

	public SessionImpl(String server, AsyncTaskCallback callback) {
		this(server, null, callback);
	}

	public SessionImpl(String server, Authentication authentication) {
		this(server, authentication, null);
	}

	public SessionImpl(
		String server, Authentication authentication,
		AsyncTaskCallback callback) {

		this(server, authentication, DEFAULT_CONNECTION_TIMEOUT, callback);
	}

	public SessionImpl(
		String server, Authentication authentication, int connectionTimeout,
		AsyncTaskCallback callback) {

		this.server = server;
		this.authentication = authentication;
		this.connectionTimeout = connectionTimeout;
		this.callback = callback;
	}

	@Override
	public Authentication getAuthentication() {
		return authentication;
	}

	@Override
	public AsyncTaskCallback getCallback() {
		return callback;
	}

	@Override
	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getServer() {
		return server;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public JSONArray invoke(JSONObject command) throws Exception {
		if (callback != null) {
			ServiceAsyncTask task = new ServiceAsyncTask(this, callback);
			task.execute(command);

			return null;
		}
		else {
			return HttpUtil.post(this, command);
		}
	}

	@Override
	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}

	@Override
	public void setCallback(AsyncTaskCallback callback) {
		this.callback = callback;
	}

	@Override
	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public void setServer(String server) {
		this.server = server;
	}

	@Override
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public JSONArray upload(JSONObject command) throws Exception {
		if (callback != null) {
			UploadAsyncTask task = new UploadAsyncTask(this, callback);
			task.execute(command);

			return null;
		}
		else {
			return HttpUtil.upload(this, command, null);
		}
	}

	protected Authentication authentication;
	protected AsyncTaskCallback callback;
	protected int connectionTimeout;
	protected String password;
	protected String server;
	protected String username;

}