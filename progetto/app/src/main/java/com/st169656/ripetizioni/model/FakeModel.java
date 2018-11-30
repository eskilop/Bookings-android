/*
 * Copyright 2018 Luca D'Amato
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
import com.st169656.ripetizioni.model.Booking;

public class FakeModel
	{

		private static FakeModel instance = new FakeModel ();

		private FakeModel()
			{

			}

		public ArrayList<Booking> getFakeBookings()
			{
				return new ArrayList <> ();
			}

		public static FakeModel getInstance ()
			{
				return instance;
			}
	}