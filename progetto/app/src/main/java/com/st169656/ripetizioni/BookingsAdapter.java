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

import android.animation.Animator;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.TextView;

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
				HttpClient hc = new HttpClient ();
				try
					{
						model.setBookings ((ArrayList <Booking>) hc.request (hc.getBookings ()).get ());
					}
				catch (ExecutionException | InterruptedException e)
					{
						e.printStackTrace ();
					}
			}

		public static class BookingsViewHolder extends RecyclerView.ViewHolder
			{
				// each data item is just a string in this case
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

									// get the center for the clipping circle
									int centerX = v.getMeasuredWidth () / 2;
									int centerY = v.getMeasuredHeight () / 2;

									int startRadius = 0;
									// get the final radius for the clipping circle
									int endRadius = Math.max (view.getWidth (), view.getHeight ());

									// create the animator for this view (the start radius is zero)
									Animator anim =
											ViewAnimationUtils.createCircularReveal (view, centerX, centerY, startRadius, endRadius);

									// make the view visible and start the animation
									view.setVisibility (View.VISIBLE);

									Booking selected = model.getBookings ().get (getAdapterPosition ());

									if (model.getSelected ().contains (selected))
										{
											view.setBackgroundColor (view.getContext ().getResources ().getColor (R.color.colorTransparent));
											model.getSelected ().remove (model.getBookings ().get (getAdapterPosition ()));
										}
									else
										{
											view.setBackgroundColor (view.getContext ().getResources ().getColor (R.color.colorAccent));
											model.getSelected ().add (model.getBookings ().get (getAdapterPosition ()));
										}
									anim.start ();
								});
					}

				public void setTextFromElement (Booking b)
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
				bookingsViewHolder.setTextFromElement (Model.getInstance ().getBookings ().get (position));
			}

		@Override
		public int getItemCount ()
			{
				if (Model.getInstance ().getBookings () == null)
					return 0;
				else
					return Model.getInstance ().getBookings ().size ();
			}

		@Override
		public int getItemViewType (int position)
			{
				return position;
			}
	}
