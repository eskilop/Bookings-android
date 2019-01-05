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
import com.google.gson.reflect.TypeToken;
import com.st169656.ripetizioni.model.Booking;
import com.st169656.ripetizioni.model.Model;
import com.st169656.ripetizioni.model.wrapper.HUC;
import com.st169656.ripetizioni.model.wrapper.Response;
import com.st169656.ripetizioni.model.wrapper.UserCredential;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HttpClient
	{
		private Model model = Model.getInstance ();
		private static final String BASE_URL = "http://192.168.1.67:8080/api";
		private static ExecutorService threadPool = Executors.newFixedThreadPool (5);

		public HttpClient ()
			{

			}

		public Future <Response> request (Callable r)
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

		public Callable <Boolean> logout ()
			{
				return () ->
				{
					HUC huc = new HUC (BASE_URL + "method=logout&by_user=" + model.getUser ().getId ());
					String response = huc.rawGet ();
					return new Gson ().fromJson (response, Boolean.class);
				};
			}

		public Callable <Response> book (int booking_id)
			{
				return () ->
				{
					HUC huc = new HUC (BASE_URL + "method=book&by_user=" + model.getUser ().getId ()
														 + "&booking_id=" + booking_id);
					return huc.get ();
				};
			}

		public Callable <Response> unbook (int booking_id)
			{
				return () ->
				{
					HUC huc = new HUC (BASE_URL + "method=unbook&by_user=" + model.getUser ().getId ()
														 + "&booking_id=" + booking_id);
					return huc.get ();
				};
			}

		public Callable <ArrayList <Booking>> getBookings ()
			{
				return () ->
				{
					HUC huc = new HUC (BASE_URL + "method=getBookings&by_user=" + model.getUser ().getId ());
					String res = huc.rawGet ();
					Type type = new TypeToken <ArrayList <Booking>> () {}.getType ();
					return new Gson ().fromJson (res, type);
				};
			}

		public Callable <Response> getIncomingBookings ()
			{
				return () ->
				{
					HUC huc = new HUC (BASE_URL + "method=getIncomingBookings&id=" + model.getUser ().getId ());
					return huc.get ();
				};
			}

		public Callable <Response> getPastBookings ()
			{
				return () ->
				{
					HUC huc = new HUC (BASE_URL + "method=getPastBookings&id=" + model.getUser ().getId ());
					return huc.get ();
				};
			}
	}
