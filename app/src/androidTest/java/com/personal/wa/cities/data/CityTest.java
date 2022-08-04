package com.personal.wa.cities.data;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.personal.wa.MainActivity;
import com.personal.weather.cities.data.City;
import com.personal.weather.cities.data.FactoryCity;
import com.utils.net.proxy.url_conn.FactoryUrlConnectionOpener;
import com.utils.net.proxy.url_conn.UrlConnectionOpener;

import androidx.test.ext.junit.runners.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
public class CityTest {

	@Test
	public void testParseAndPrintWeather() {

		MainActivity.configureLogger();

		final UrlConnectionOpener urlConnectionOpener = FactoryUrlConnectionOpener.newInstance();
		urlConnectionOpener.configureProperties();

		final String cityName = "Timisoara";
		final String accuWeatherName = "timisoara";
		final String accuWeatherLocationKey = "290867";

		final City city = FactoryCity.newInstance(cityName, accuWeatherName, accuWeatherLocationKey);
		city.parseWeather(null);
	}
}
