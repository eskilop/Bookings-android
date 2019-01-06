package com.st169656.ripetizioni;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.st169656.ripetizioni.model.Model;
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

				doLogin ();

				rv = findViewById (R.id.bookings_list);
				layoutManager = new GridLayoutManager (BookActivity.this, 3);
				rv.setLayoutManager (layoutManager);
				rv.setHasFixedSize (true);
				rv.setAdapter (new BookingsAdapter ());


				findViewById (R.id.floatingActionButton).setOnClickListener (
						v ->
						{

						});
			}

		public void doLogin ()
			{
				UserCredential u = new UserCredential ("John", "sample");
				HttpClient hc = new HttpClient ();
				try
					{
						Response r = (Response) hc.request (hc.login (u)).get ();
					}
				catch (ExecutionException e)
					{
						e.printStackTrace ();
					}
				catch (InterruptedException e)
					{
						e.printStackTrace ();
					}
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
											// Todo start new activity
											Response r = (Response) hc.request (hc.login (new UserCredential ("John", "sample"))).get ();
											r.dispatch (BookActivity.this);
											item.setIcon (R.drawable.ic_exit);
											history.setEnabled (true);
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
							break;
					}
				return super.onOptionsItemSelected (item);
			}
	}
