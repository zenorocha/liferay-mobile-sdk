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

package com.liferay.mobile.android.task;

import android.os.AsyncTask;

import com.liferay.mobile.android.http.HttpUtil;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.task.callback.AsyncTaskCallback;
import com.liferay.mobile.android.task.callback.UploadProgressAsyncTaskCallback;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Bruno Farache
 */
public class UploadAsyncTask extends AsyncTask<JSONObject, Integer, JSONArray> {

	public UploadAsyncTask(Session session, AsyncTaskCallback callback) {
		_callback = callback;
		_session = session;
	}

	@Override
	public JSONArray doInBackground(JSONObject... commands) {
		try {
			UploadAsyncTask task = null;

			if (_callback instanceof UploadProgressAsyncTaskCallback) {
				task = this;
			}

			JSONArray array = HttpUtil.upload(_session, commands[0], task);

			return _callback.inBackground(array);
		}
		catch (Exception e) {
			_exception = e;
			cancel(false);
		}

		return null;
	}

	@Override
	public void onCancelled() {
		_callback.onFailure(_exception);
	}

	public void onCancelled(JSONArray array) {
		_callback.onFailure(_exception);
	}

	@Override
	public void onPostExecute(JSONArray array) {
		try {
			_callback.onPostExecute(array);
		}
		catch (Exception e) {
			_exception = e;
			onCancelled();
		}
	}

	@Override
	public void onProgressUpdate(Integer... bytes) {
		_bytes = _bytes + bytes[0];
		((UploadProgressAsyncTaskCallback)_callback).onProgress(_bytes);
	}

	public void setProgress(int bytes) {
		publishProgress(bytes);
	}

	private int _bytes;
	private AsyncTaskCallback _callback;
	private Exception _exception;
	private Session _session;

}