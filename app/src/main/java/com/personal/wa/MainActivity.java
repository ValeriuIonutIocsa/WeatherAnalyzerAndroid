package com.personal.wa;

import com.utils.log.Logger;
import com.utils.log.MessageLevel;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

	public static void configureLogger() {

		Logger.setMessageConsumer((
				messageLevel,
				message) -> {

			String tag = "WeatherAnalyzer";
			if (messageLevel == MessageLevel.INFO ||
					messageLevel == MessageLevel.STATUS ||
					messageLevel == MessageLevel.PROGRESS) {
				Log.i(tag, message);
			} else if (messageLevel == MessageLevel.WARNING ||
					messageLevel == MessageLevel.ERROR ||
					messageLevel == MessageLevel.EXCEPTION) {
				Log.e(tag, message);
			}
		});
	}

	@Override
	protected void onCreate(
			Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		configureLogger();

		setContentView(R.layout.activity_main);

		FragmentProgress fragmentProgress = new FragmentProgress(this);
		FragmentManager supportFragmentManager = getSupportFragmentManager();
		supportFragmentManager.beginTransaction()
				.replace(R.id.frame_layout_fragment, fragmentProgress)
				.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(
			Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(
			MenuItem item) {

		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		// noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
