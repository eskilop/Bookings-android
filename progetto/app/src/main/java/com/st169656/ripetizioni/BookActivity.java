package com.st169656.ripetizioni;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.st169656.ripetizioni.activities.LoginActivity;

public class BookActivity extends AppCompatActivity
	{

		@Override
		protected void onCreate (Bundle savedInstanceState)
			{
				super.onCreate (savedInstanceState);
				setContentView (R.layout.activity_book);

				findViewById (R.id.floatingActionButton).setOnClickListener (new View.OnClickListener ()
					{
						@Override
						public void onClick (View v)
							{
								// Todo: Temporary, just to trigger it when i want to.
								if (true) // Todo: it's not like that, check preferences or a more secure storage.
									startActivity (new Intent (BookActivity.this, LoginActivity.class));
							}
					});
			}
	}
