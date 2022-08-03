package com.personal.wa.cities.data;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.personal.wa.MainActivity;
import com.utils.net.proxy.url_conn.FactoryUrlConnectionOpener;
import com.utils.net.proxy.url_conn.UrlConnectionOpener;

import org.junit.Test;
import org.junit.runner.RunWith;

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

        final City city = new City(cityName, accuWeatherName, accuWeatherLocationKey);
        city.parseWeather();
    }
}
