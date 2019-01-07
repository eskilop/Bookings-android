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

package com.st169656.ripetizioni.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.st169656.ripetizioni.HttpClient;
import com.st169656.ripetizioni.R;
import com.st169656.ripetizioni.model.Booking;
import com.st169656.ripetizioni.model.History;
import com.st169656.ripetizioni.model.Model;
import com.st169656.ripetizioni.model.wrapper.BookingResponse;
import com.st169656.ripetizioni.model.wrapper.HistoryResponse;
import com.st169656.ripetizioni.model.wrapper.Response;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class HistoryAdapter extends RecyclerView.Adapter <HistoryAdapter.HistoryViewHolder>
	{
		LayoutInflater inflater;
		Model model = Model.getInstance ();

		public HistoryAdapter ()
			{
			}

		public static class HistoryViewHolder extends RecyclerView.ViewHolder
			{
				// each data item is just a string in this case
				public TextView card_title;
				public TextView card_subheader;
				public TextView card_content;
				private Model model = Model.getInstance ();

				public HistoryViewHolder (@NonNull View view)
					{
						super (view);
						card_title = view.findViewById (R.id.booking_title);
						card_subheader = view.findViewById (R.id.booking_subheader);
						card_content = view.findViewById (R.id.booking_content);
					}

				public void setTextFromElement (History h)
					{
						card_title.setText (h.getBooking ().getFrom ().getCourse ().getCourseTitle ());
						String teacherName = h.getBooking ().getFrom ().getName () + " " + h.getBooking ().getFrom ().getSurname ();
						card_subheader.setText (teacherName);
						card_content.setText (h.getBooking ().getDate ().toString () + "\n\nState: " + h.getState ()
																	 .getTitle () + "\n on " + h.getActionDate ());
					}
			}

		@NonNull
		@Override
		public HistoryAdapter.HistoryViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int type)
			{
				View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.booking_card, parent, false);
				HistoryAdapter.HistoryViewHolder bvh = new HistoryAdapter.HistoryViewHolder (v);
				return bvh;
			}

		@Override
		public void onBindViewHolder (@NonNull HistoryAdapter.HistoryViewHolder bookingsViewHolder, int position)
			{
				bookingsViewHolder.setTextFromElement (Model.getInstance ().getHistory ().get (position));
			}

		@Override
		public int getItemCount ()
			{
				if (model.getHistory () == null)
					return 0;
				else
					return model.getHistory ().size ();
			}

		@Override
		public int getItemViewType (int position)
			{
				return position;
			}
	}