package com.personal.weather;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.SystemUtils;

import com.personal.weather.cities.ParserCities;
import com.personal.weather.cities.data.City;
import com.utils.io.IoUtils;
import com.utils.io.PathUtils;
import com.utils.io.folder_creators.FactoryFolderCreator;
import com.utils.log.Logger;
import com.utils.log.progress.ProgressIndicatorConsole;
import com.utils.net.proxy.url_conn.FactoryUrlConnectionOpener;
import com.utils.net.proxy.url_conn.UrlConnectionOpener;

final class WeatherAnalyzerCli {

	private WeatherAnalyzerCli() {
	}

	static File work(
			final int threadCount) {

		File outputFile = null;
		try {
			Logger.printProgress("starting WeatherAnalyzer");

			final Date date = new Date();
			final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd_MM_yyyy");
			final String outputFileName = simpleDateFormat.format(date) + ".txt";
			final Path outputFilePath;
			if (IoUtils.directoryExists(PathUtils.ROOT_PATH)) {
				outputFilePath = Paths.get(PathUtils.ROOT_PATH,
						"tmp", "WeatherAnalyzer", outputFileName);
			} else {
				outputFilePath = Paths.get(SystemUtils.USER_HOME,
						"WeatherAnalyzer", outputFileName);
			}
			Logger.printProgress("generating output file:");
			Logger.printLine(outputFilePath);
			FactoryFolderCreator.getInstance().createParentDirectories(outputFilePath, true);

			final UrlConnectionOpener urlConnectionOpener = FactoryUrlConnectionOpener.newInstance();
			urlConnectionOpener.configureProperties();

			final List<City> cityList = ParserCities.createCityList();
			ParserCities.parseWeather(cityList, threadCount, ProgressIndicatorConsole.INSTANCE);

			try (PrintStream printStream = new PrintStream(
					new BufferedOutputStream(Files.newOutputStream(outputFilePath)))) {

				for (final City city : cityList) {
					city.printWeather(printStream);
				}
			}

			outputFile = outputFilePath.toFile();

		} catch (final Exception exc) {
			Logger.printException(exc);
		}
		return outputFile;
	}
}
