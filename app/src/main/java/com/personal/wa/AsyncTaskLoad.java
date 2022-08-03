package com.personal.wa;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.time.Instant;
import java.util.List;

import com.personal.weather.cities.ParserCities;
import com.personal.weather.cities.data.City;
import com.utils.log.Logger;
import com.utils.net.proxy.url_conn.FactoryUrlConnectionOpener;
import com.utils.net.proxy.url_conn.UrlConnectionOpener;

import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

class AsyncTaskLoad extends AsyncTask<Void, Integer, Void> {

	private final WeakReference<MainActivity> wrMainActivity;
	private final WeakReference<ProgressBar> wrProgressBar;
	private final WeakReference<TextView> wrTextView;
	private List<City> cityList;

	AsyncTaskLoad(
			WeakReference<MainActivity> wrMainActivity,
			WeakReference<ProgressBar> wrProgressBar,
			WeakReference<TextView> wrTextView) {

		this.wrMainActivity = wrMainActivity;
		this.wrProgressBar = wrProgressBar;
		this.wrTextView = wrTextView;
	}

	@Override
	protected Void doInBackground(
			Void... voids) {

		final UrlConnectionOpener urlConnectionOpener = FactoryUrlConnectionOpener.newInstance();
		urlConnectionOpener.configureProperties();

		try {
			Instant start = Instant.now();
			Logger.printProgress("starting WeatherAnalyzer...");

			try (InputStream inputStream =
					wrMainActivity.get().getResources().openRawResource(R.raw.cities)) {
				cityList = ParserCities.createCityList(inputStream);
			}

			Logger.printDebugLine("1111 " + cityList.size());
			ParserCities.parseWeather(cityList, 12);

			Logger.printFinishMessage(start);

		} catch (final Exception exc) {
			Logger.printException(exc);
		}

		return null;
	}

	@Override
	protected void onProgressUpdate(
			Integer... values) {

		super.onProgressUpdate(values);

		int progress = values[0];
		int max = values[1];

		wrProgressBar.get().setMin(0);
		wrProgressBar.get().setMax(max);
		wrProgressBar.get().setProgress(progress);

		String text = "loading " + progress + " / " + max;
		wrTextView.get().setText(text);
	}

	@Override
	protected void onPostExecute(
			Void aVoid) {

		super.onPostExecute(aVoid);

		FragmentList fragmentList = new FragmentList(cityList);

		FragmentManager supportFragmentManager =
				wrMainActivity.get().getSupportFragmentManager();
		supportFragmentManager.beginTransaction()
				.replace(R.id.frame_layout_fragment, fragmentList)
				.commit();
	}
}
