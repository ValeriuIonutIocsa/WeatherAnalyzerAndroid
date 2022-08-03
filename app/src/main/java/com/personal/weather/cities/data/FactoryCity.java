package com.personal.weather.cities.data;

import org.w3c.dom.Element;

public final class FactoryCity {

	private FactoryCity() {
	}

	public static City newInstance(
			final Element cityElement) {

		final String cityName = cityElement.getAttribute("Name");
		final String accuWeatherName = cityElement.getAttribute("AccuWeatherName");
		final String accuWeatherLocationKey = cityElement.getAttribute("AccuWeatherLocationKey");

		return new City(cityName, accuWeatherName, accuWeatherLocationKey);
	}
}
