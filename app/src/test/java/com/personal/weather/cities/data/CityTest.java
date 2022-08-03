package com.personal.weather.cities.data;

import java.io.BufferedOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.utils.io.PathUtils;
import com.utils.io.folder_creators.FactoryFolderCreator;
import com.utils.log.Logger;
import com.utils.net.proxy.url_conn.FactoryUrlConnectionOpener;
import com.utils.net.proxy.url_conn.UrlConnectionOpener;

class CityTest {

	@Test
	void testParseAndPrintWeather() throws Exception {

		final UrlConnectionOpener urlConnectionOpener = FactoryUrlConnectionOpener.newInstance();
		urlConnectionOpener.configureProperties();

		final String cityName = "Timisoara";
		final String accuWeatherName = "timisoara";
		final String accuWeatherLocationKey = "290867";

		final Path htmlOutputPath = Paths.get(PathUtils.ROOT_PATH,
				"tmp", "WeatherAnalyzer", "_debug", cityName + ".html");

		Logger.printProgress("writing received HTML file:");
		Logger.printLine(htmlOutputPath);

		boolean success = FactoryFolderCreator.getInstance()
				.createParentDirectories(htmlOutputPath, true);
		Assertions.assertTrue(success);

		try (PrintStream printStream = new PrintStream(
				new BufferedOutputStream(Files.newOutputStream(htmlOutputPath)))) {

			final City city = new City(cityName, accuWeatherName, accuWeatherLocationKey);
			city.parseWeather(printStream);
			city.printWeather(System.out);
		}
	}
}
