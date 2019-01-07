package com.st169656.ripetizioni;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.st169656.ripetizioni.activities.HistoryActivity;
import com.st169656.ripetizioni.activities.LoginActivity;
import com.st169656.ripetizioni.adapters.BookingsAdapter;
import com.st169656.ripetizioni.model.Booking;
import com.st169656.ripetizioni.model.Model;
import com.st169656.ripetizioni.model.User;
import com.st169656.ripetizioni.model.wrapper.Response;
import com.st169656.ripetizioni.model.wrapper.UserCredential;

import java.util.concurrent.ExecutionException;

public class BookActivity extends AppCompatActivity
	{

		MenuItem logout, history;
		RecyclerView rv;
		RecyclerView.LayoutManager layoutManager;
		Model model = Model.getInstance ();

		@Override
		protected void onCreate (Bundle savedInstanceState)
			{
				super.onCreate (savedInstanceState);
				setContentView (R.layout.activity_book);

				setSupportActionBar (findViewById (R.id.toolbar));

				// doLogin ();

				rv = findViewById (R.id.bookings_list);
				layoutManager = new GridLayoutManager (BookActivity.this, 3);
				rv.setLayoutManager (layoutManager);
				rv.setHasFixedSize (true);
				rv.setAdapter (new BookingsAdapter ());

				getUser ();
				model.loadBookings (rv);


				findViewById (R.id.floatingActionButton).setOnClickListener (
						v ->
						{
							HttpClient hc = new HttpClient ();
							for (Booking booking : model.getSelected ())
								{
									int idx = model.getBookings ().indexOf (booking);
									model.getBookings ().remove (booking);
									model.getIncomingBookings ().add (booking);
									rv.removeViewAt (idx);
									rv.getAdapter ().notifyItemRemoved(idx);
									rv.getAdapter ().notifyItemRangeChanged (idx, model.getBookings ().size ());
									hc.request (hc.book (booking.getId ()));
								}
							model.getSelected ().clear ();
						});
			}

		@Override
		public boolean onCreateOptionsMenu (Menu menu)
			{
				getMenuInflater ().inflate (R.menu.toolbar_menu, menu);
				logout = menu.findItem (R.id.action_logout);
				history = menu.findItem (R.id.action_history);
				if (model.getUser () == null)
					{
						logout.setIcon (R.drawable.ic_account_circle);
						history.setEnabled (false);
					}
				return true;
			}

		@Override
		public boolean onOptionsItemSelected (MenuItem item)
			{
				switch (item.getItemId ())
					{
						case R.id.action_logout:
							HttpClient hc = new HttpClient ();
							try
								{
									if (model.getUser () == null)
										{
											startActivity (new Intent (BookActivity.this, LoginActivity.class));
											finish ();
										}
									else
										{
											Boolean logout = (Boolean) hc.request (hc.logout ()).get ();
											if (logout)
												{
													new AlertDialog.Builder (BookActivity.this)
															.setTitle ("Success")
															.setMessage ("You logged out successfully")
															.create ()
															.show ();
													item.setIcon (R.drawable.ic_account_circle);
													history.setEnabled (false);
													model.setUser (null);
													getSharedPreferences ("usr_pref", MODE_PRIVATE).edit ().remove ("user")
															.commit ();
												}
										}
								}
							catch (ExecutionException e)
								{
									e.printStackTrace ();
								}
							catch (InterruptedException e)
								{
									e.printStackTrace ();
								}
							break;

						case R.id.action_history:
							startActivity (new Intent (BookActivity.this, HistoryActivity.class));
							break;
					}
				return super.onOptionsItemSelected (item);
			}

		private boolean getUser()
			{
				String usr = getSharedPreferences ("usr_pref", MODE_PRIVATE).getString ("user", null);
				Model.getInstance ().setUser (new Gson ().fromJson (usr, User.class));
				return usr != null;
			}
	}
