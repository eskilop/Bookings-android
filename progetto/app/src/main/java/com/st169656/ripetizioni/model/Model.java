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

import java.util.ArrayList;

public class Model
	{
		private static Model instance = new Model ();
		private User user = null;
		private ArrayList <Booking> bookings;
		private ArrayList <Booking> selected = new ArrayList <> ();

		private Model ()
			{
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
				this.bookings = bookings;
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

		public void selectBooking (Booking b)
			{
				selected.add (b);
			}
	}
