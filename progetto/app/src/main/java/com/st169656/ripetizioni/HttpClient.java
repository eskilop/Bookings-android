/*
 * Copyright 2019 Luca D'Amato
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.st169656.ripetizioni;

import com.google.gson.Gson;
import com.st169656.ripetizioni.model.Course;
import com.st169656.ripetizioni.model.Teacher;
import com.st169656.ripetizioni.model.User;
import com.st169656.ripetizioni.model.wrapper.HUC;
import com.st169656.ripetizioni.model.wrapper.Response;
import com.st169656.ripetizioni.model.wrapper.UserCredential;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HttpClient
	{
		private User requestingUser = null;
		private static final String BASE_URL = "http://192.168.1.67:8080/api";
		private static ExecutorService threadPool = Executors.newFixedThreadPool (5);

		public HttpClient ()
			{

			}

		public Future<Response> request (Callable r)
			{
				return threadPool.submit (r);
			}

		public Callable <Response> login (UserCredential u)
			{
				return () ->
				{
					HUC huc = new HUC (BASE_URL + "?method=login");
					return huc.post (new Gson ().toJson (u));
				};
			}

		public Callable <Response> newTeacher (Teacher t)
			{
				return () ->
				{
					if (this.requestingUser == null) return null;
					HUC huc = new HUC (BASE_URL+"?method=newTeacher&by_user="+requestingUser.getId ());
					return huc.post (new Gson ().toJson (t));
				};
			}

		public Callable <Response> newCourse (Course c)
			{
				return () ->
				{
					if (this.requestingUser == null) return null;
					HUC huc = new HUC (BASE_URL+"?method=newCourse&by_user="+requestingUser.getId ());
					return huc.post (new Gson ().toJson (c));
				};
			}




	}
