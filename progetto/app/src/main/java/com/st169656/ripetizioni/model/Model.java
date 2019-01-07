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

package com.st169656.ripetizioni.model;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.st169656.ripetizioni.HttpClient;
import com.st169656.ripetizioni.model.wrapper.BookingResponse;
import com.st169656.ripetizioni.model.wrapper.HistoryResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

public class Model
	{
		private static Model instance = new Model ();
		private User user = null;
		private ArrayList <Booking> bookings = new ArrayList <> ();
		private ArrayList <Booking> incomingBookings = new ArrayList <> ();
		private ArrayList <History> pastBookings = new ArrayList <> ();
		private ArrayList <Booking> selected = new ArrayList <> ();

		private Model ()
			{
			}

		public void loadBookings (RecyclerView rv)
			{
				HttpClient hc = new HttpClient ();
				new AsyncTask <Void, Void, Void> ()
					{

						@Override
						protected Void doInBackground (Void... voids)
							{
								try
									{
										bookings.addAll ((Collection <? extends Booking>) hc.request (hc.getBookings ()).get ());
									}
								catch (ExecutionException | InterruptedException e)
									{
										e.printStackTrace ();
									}
								return null;
							}

						@Override
						protected void onPostExecute (Void aVoid)
							{
								super.onPostExecute (aVoid);
								rv.getAdapter ().notifyDataSetChanged ();
							}
					}.execute ();
			}

		public void loadIncomingBookings (RecyclerView rv)
			{
				HttpClient hc = new HttpClient ();
				new AsyncTask <Void, Void, Void> ()
					{

						@Override
						protected Void doInBackground (Void... voids)
							{
								try
									{
										incomingBookings.addAll (((BookingResponse) hc.request (hc.getIncomingBookings ()).get ()).getValue ());
									}
								catch (ExecutionException | InterruptedException e)
									{
										e.printStackTrace ();
									}
								return null;
							}

						@Override
						protected void onPostExecute (Void aVoid)
							{
								super.onPostExecute (aVoid);
								rv.getAdapter ().notifyDataSetChanged ();
							}
					}.execute ();
			}

		public void loadPastBookings (RecyclerView rv)
			{
				HttpClient hc = new HttpClient ();
				new AsyncTask <Void, Void, Void> ()
					{

						@Override
						protected Void doInBackground (Void... voids)
							{
								try
									{
										pastBookings.addAll (((HistoryResponse) hc.request (hc.getPastBookings ()).get ()).getValue ());
									}
								catch (ExecutionException | InterruptedException e)
									{
										e.printStackTrace ();
									}
								return null;
							}

						@Override
						protected void onPostExecute (Void aVoid)
							{
								super.onPostExecute (aVoid);
								rv.getAdapter ().notifyDataSetChanged ();
							}
					}.execute ();
			}

		public static Model getInstance ()
			{
				return instance;
			}

		public void setUser (User user)
			{
				this.user = user;
			}

		public void setBookings (ArrayList <Booking> bookings)
			{
				this.bookings.addAll (bookings);
			}

		public void setIncomingBookings (ArrayList <Booking> bookings)
			{
				this.incomingBookings.addAll (bookings);
			}

		public void setHistory (ArrayList <History> histories)
			{
				this.pastBookings.addAll (histories);
			}

		public ArrayList <Booking> getIncomingBookings ()
			{
				return this.incomingBookings;
			}

		public ArrayList <History> getHistory ()
			{
				return this.pastBookings;
			}

		public User getUser ()
			{
				return this.user;
			}

		public ArrayList <Booking> getBookings ()
			{
				return this.bookings;
			}

		public ArrayList <Booking> getSelected ()
			{
				return selected;
			}
	}
