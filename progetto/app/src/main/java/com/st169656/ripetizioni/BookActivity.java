package com.st169656.ripetizioni;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.st169656.ripetizioni.activities.LoginActivity;
import com.st169656.ripetizioni.model.wrapper.Response;
import com.st169656.ripetizioni.model.wrapper.UserCredential;

import java.util.concurrent.ExecutionException;

public class BookActivity extends AppCompatActivity
	{

		@Override
		protected void onCreate (Bundle savedInstanceState)
			{
				super.onCreate (savedInstanceState);
				setContentView (R.layout.activity_book);

				setSupportActionBar (findViewById (R.id.toolbar));

				checkLoggedAndStart ();



				findViewById (R.id.floatingActionButton).setOnClickListener (
						v ->
						{
							// Todo: Temporary, just to trigger it when i want to.
							if (true) // Todo: it's not like that, check preferences or a more secure storage.
								startActivity (new Intent (BookActivity.this, LoginActivity.class));
						});


			}

		public void checkLoggedAndStart ()
			{
				HttpClient hc = new HttpClient ();
				try
					{
						Response r = hc.request (hc.login (new UserCredential ("John", "sample"))).get ();
						r.dispatch (BookActivity.this);
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
				return true;
			}

		@Override
		public boolean onOptionsItemSelected (MenuItem item)
			{
				switch (item.getItemId ())
					{

					}
				return super.onOptionsItemSelected (item);
			}
	}
