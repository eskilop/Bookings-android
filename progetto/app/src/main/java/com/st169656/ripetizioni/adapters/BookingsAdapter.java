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

import android.animation.Animator;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.TextView;

import com.st169656.ripetizioni.HttpClient;
import com.st169656.ripetizioni.R;
import com.st169656.ripetizioni.model.Booking;
import com.st169656.ripetizioni.model.Model;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class BookingsAdapter extends RecyclerView.Adapter <BookingsAdapter.BookingsViewHolder>
	{
		Model model = Model.getInstance ();
		LayoutInflater inflater;

		public BookingsAdapter ()
			{
			}

		public static class BookingsViewHolder extends RecyclerView.ViewHolder
			{
				public TextView card_title;
				public TextView card_subheader;
				public TextView card_content;
				private Model model = Model.getInstance ();

				public BookingsViewHolder (View v)
					{
						super (v);
						card_title = v.findViewById (R.id.booking_title);
						card_subheader = v.findViewById (R.id.booking_subheader);
						card_content = v.findViewById (R.id.booking_content);
						v.setOnClickListener (
								(view) ->
								{

									Animator anim = circularReveal (v);

									Booking selectedBooking = model.getBookings ().get (getAdapterPosition ());
									Log.e ("ap", String.valueOf (getAdapterPosition ()));
									Log.e ("asd", selectedBooking.toString ());

									if (model.getSelected ().contains (selectedBooking))
										{
											view.setBackgroundColor (view.getContext ().getResources ().getColor (R.color.colorTransparent));
											model.getSelected ().remove (selectedBooking);
										}
									else
										{
											view.setBackgroundColor (view.getContext ().getResources ().getColor (R.color.colorAccent));
											model.getSelected ().add (selectedBooking);
										}
									anim.start ();
								});
					}

				Animator circularReveal(View v)
					{
						// get the center for the clipping circle
						int centerX = v.getMeasuredWidth () / 2;
						int centerY = v.getMeasuredHeight () / 2;

						int startRadius = 0;
						// get the final radius for the clipping circle
						int endRadius = Math.max (v.getWidth (), v.getHeight ());

						// create the animator for this view (the start radius is zero)
						Animator anim =
								ViewAnimationUtils.createCircularReveal (v, centerX, centerY, startRadius,
																												 endRadius);

						// make the view visible and start the animation
						v.setVisibility (View.VISIBLE);

						return anim;
					}

				void setTextFromElement (Booking b)
					{
						card_title.setText (b.getFrom ().getCourse ().getCourseTitle ());
						String teacherName = b.getFrom ().getName () + " " + b.getFrom ().getSurname ();
						card_subheader.setText (teacherName);
						card_content.setText (b.getDate ().toString ());
					}
			}

		@NonNull
		@Override
		public BookingsViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int type)
			{
				View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.booking_card, parent, false);
				BookingsViewHolder bvh = new BookingsViewHolder (v);
				return bvh;
			}

		@Override
		public void onBindViewHolder (@NonNull BookingsViewHolder bookingsViewHolder, int position)
			{
				bookingsViewHolder.setTextFromElement (model.getBookings ().get (position));
			}

		@Override
		public int getItemCount ()
			{
				return model.getBookings ().size ();
			}

		@Override
		public int getItemViewType (int position)
			{
				return position;
			}
	}
