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

package com.st169656.ripetizioni.tasks;

import android.os.AsyncTask;

import com.st169656.ripetizioni.R;

/**
 * Represents an asynchronous login/registration task used to authenticate
 * the user.
 */
public class UserLoginTask extends AsyncTask <Void, Void, Boolean>
	{

		private final String mEmail;
		private final String mPassword;

		public UserLoginTask (String email, String password)
			{
				mEmail = email;
				mPassword = password;
			}

		@Override
		protected Boolean doInBackground (Void... params)
			{
				return true;
			}

		@Override
		protected void onPostExecute (final Boolean success)
			{
//				if (success)
//					{
//						finish ();
//					}
//				else
//					{
//						mPasswordView.setError (getString (R.string.error_incorrect_password));
//						mPasswordView.requestFocus ();
//					}
			}

		@Override
		protected void onCancelled ()
			{
//				mAuthTask = null;
//				showProgress (false);
			}
	}