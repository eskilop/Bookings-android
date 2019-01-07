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

package com.st169656.ripetizioni.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.st169656.ripetizioni.adapters.BookingsAdapter;
import com.st169656.ripetizioni.R;
import com.st169656.ripetizioni.adapters.IncomingBookingsAdapter;
import com.st169656.ripetizioni.adapters.HistoryAdapter;
import com.st169656.ripetizioni.model.Model;

public class HistoryActivity extends AppCompatActivity
	{

		RecyclerView incomingBookings, pastBookings;
		Model model = Model.getInstance ();

		@Override
		protected void onCreate (Bundle savedInstanceState)
			{
				super.onCreate (savedInstanceState);
				setContentView (R.layout.activity_history);

				setSupportActionBar (findViewById (R.id.historybar));

				getSupportActionBar ().setTitle ("History");

				incomingBookings = findViewById (R.id.incomingBookings);
				pastBookings = findViewById (R.id.pastBookings);

				incomingBookings.setLayoutManager (new GridLayoutManager (HistoryActivity.this, 3));
				pastBookings.setLayoutManager (new GridLayoutManager (HistoryActivity.this, 3));

				incomingBookings.setHasFixedSize (true);
				pastBookings.setHasFixedSize (true);

				incomingBookings.setAdapter (new IncomingBookingsAdapter (incomingBookings));
				pastBookings.setAdapter (new HistoryAdapter ());

				model.loadIncomingBookings (incomingBookings);
				model.loadPastBookings (pastBookings);

			}
	}
