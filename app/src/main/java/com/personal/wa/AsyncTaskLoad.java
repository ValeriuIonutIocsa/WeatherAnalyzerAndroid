package com.personal.wa;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.time.Instant;
import java.util.List;

import com.personal.weather.cities.ParserCities;
import com.personal.weather.cities.data.City;
import com.utils.log.Logger;
import com.utils.log.progress.AbstractProgressIndicator;
import com.utils.log.progress.ProgressIndicator;
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
			final WeakReference<MainActivity> wrMainActivity,
			final WeakReference<ProgressBar> wrProgressBar,
			final WeakReference<TextView> wrTextView) {

		super();

		this.wrMainActivity = wrMainActivity;
		this.wrProgressBar = wrProgressBar;
		this.wrTextView = wrTextView;
	}

	@Override
	protected Void doInBackground(
			final Void... voids) {

		final UrlConnectionOpener urlConnectionOpener = FactoryUrlConnectionOpener.newInstance();
		urlConnectionOpener.configureProperties();

		try {
			final Instant start = Instant.now();
			Logger.printProgress("starting WeatherAnalyzer...");

			try (InputStream inputStream =
					wrMainActivity.get().getResources().openRawResource(R.raw.cities)) {
				cityList = ParserCities.createCityList(inputStream);
			}

			final int cityCount = cityList.size();
			publishProgress(0, cityCount);

			final ProgressIndicator progressIndicator = new AbstractProgressIndicator() {

				@Override
				public void update(
						final double v) {

					final int value = (int) (v * cityCount);
					publishProgress(value, cityCount);
				}
			};
			ParserCities.parseWeather(cityList, 12, progressIndicator);

			Logger.printFinishMessage(start);

		} catch (final Exception exc) {
			Logger.printException(exc);
		}

		return null;
	}

	@Override
	protected void onProgressUpdate(
			final Integer... values) {

		super.onProgressUpdate(values);

		final int progress = values[0];
		final int max = values[1];

		wrProgressBar.get().setMin(0);
		wrProgressBar.get().setMax(max);
		wrProgressBar.get().setProgress(progress);

		final String text = "loading " + progress + " / " + max;
		wrTextView.get().setText(text);
	}

	@Override
	protected void onPostExecute(
			final Void aVoid) {

		super.onPostExecute(aVoid);

		final FragmentList fragmentList = new FragmentList(cityList);

		final FragmentManager supportFragmentManager =
				wrMainActivity.get().getSupportFragmentManager();
		supportFragmentManager.beginTransaction()
				.replace(R.id.frame_layout_fragment, fragmentList)
				.commit();
	}
}
