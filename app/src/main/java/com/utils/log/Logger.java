package com.utils.log;

import java.time.Duration;
import java.time.Instant;
import java.util.function.BiConsumer;

import org.apache.commons.lang3.StringUtils;

import com.utils.annotations.ApiMethod;
import com.utils.string.StrUtils;
import com.utils.string.junit.JUnitUtils;

public final class Logger {

	private static final MessageConsumer MESSAGE_CONSUMER = new MessageConsumer();

	private static boolean debugMode;

	static {
		final boolean jUnitTest = JUnitUtils.isJUnitTest();
		if (jUnitTest) {
			debugMode = true;
		}
	}

	private Logger() {
	}

	@ApiMethod
	public static void printNewLine() {
		printLine("");
	}

	@ApiMethod
	public static void printLine(
			final Object object) {

		final String message = String.valueOf(object);
		printLine(message);
	}

	@ApiMethod
	public static void printLine(
			final String message) {
		printMessage(MessageLevel.INFO, message);
	}

	@ApiMethod
	public static void printProgress(
			final Object object) {

		final String message = String.valueOf(object);
		printProgress(message);
	}

	@ApiMethod
	public static void printProgress(
			final String messageParam) {

		final StringBuilder sbMessage = new StringBuilder(messageParam);
		sbMessage.insert(0, "--> ");
		printMessage(MessageLevel.PROGRESS, sbMessage.toString());
	}

	@ApiMethod
	public static void printStatus(
			final Object object) {

		final String message = String.valueOf(object);
		printStatus(message);
	}

	@ApiMethod
	public static void printStatus(
			final String message) {
		printMessage(MessageLevel.STATUS, message);
	}

	@ApiMethod
	public static void printWarning(
			final Object object) {

		final String message = String.valueOf(object);
		printWarning(message);
	}

	@ApiMethod
	public static void printWarning(
			final String messageParam) {

		final StringBuilder sbMessage = new StringBuilder(messageParam);
		sbMessage.insert(0, "WARNING - ");
		printMessage(MessageLevel.WARNING, sbMessage.toString());
	}

	@ApiMethod
	public static void printError(
			final Object object) {

		final String message = String.valueOf(object);
		printError(message);
	}

	@ApiMethod
	public static void printError(
			final String messageParam) {

		final StringBuilder sbMessage = new StringBuilder(messageParam);
		sbMessage.insert(0, "ERROR - ");
		printMessage(MessageLevel.ERROR, sbMessage.toString());
	}

	@ApiMethod
	public static void printException(
			final Throwable throwable) {

		final String message = exceptionToString(throwable);
		printMessage(MessageLevel.EXCEPTION, message);
	}

	@ApiMethod
	public static String exceptionToString(
			final Throwable throwable) {

		final String exceptionString;
		if (throwable == null) {
			exceptionString = "NULL exception!";

		} else {
			final Class<? extends Throwable> excClass = throwable.getClass();
			final String excClassSimpleName = excClass.getSimpleName();
			final String excMessage = throwable.getMessage();
			final StackTraceElement[] excStackTrace = throwable.getStackTrace();
			final String prettyPrintedExcStackTrace =
					StringUtils.join(excStackTrace, System.lineSeparator());

			exceptionString = "Exception of class \"" + excClassSimpleName + "\" has occurred!" +
					System.lineSeparator() + excMessage + System.lineSeparator() + prettyPrintedExcStackTrace;
		}
		return exceptionString;
	}

	@ApiMethod
	public static void printFinishMessage(
			final Instant start) {
		printFinishMessage("Done.", start);
	}

	@ApiMethod
	public static void printFinishMessage(
			final String message,
			final Instant start) {

		final Duration executionTime = Duration.between(start, Instant.now());
		printStatus(message + " Execution time: " + StrUtils.durationToString(executionTime));
	}

	@ApiMethod
	public static void printToBeImplemented(
			final String name) {
		printLine(name + " (to be implemented...)");
	}

	@ApiMethod
	public static void printDebugLine(
			final Object message) {
		printMessage(MessageLevel.INFO, String.valueOf(message));
	}

	@ApiMethod
	public static void printDebugError(
			final Object message) {
		printMessage(MessageLevel.ERROR, String.valueOf(message));
	}

	@ApiMethod
	public static void printDebugTime(
			final long start) {

		final long nanoTime = System.nanoTime() - start;
		printMessage(MessageLevel.ERROR, StrUtils.nanoTimeToString(nanoTime));
	}

	private static void printMessage(
			final MessageLevel info,
			final String message) {
		MESSAGE_CONSUMER.printMessage(info, message);
	}

	@ApiMethod
	public static void setMessageConsumer(
			final BiConsumer<MessageLevel, String> printFunction) {
		MESSAGE_CONSUMER.setMessageConsumer(printFunction);
	}

	@ApiMethod
	public static MessageConsumer getMessageConsumer() {
		return MESSAGE_CONSUMER;
	}

	@ApiMethod
	public static void setDebugMode(
			final boolean debugMode) {
		Logger.debugMode = debugMode;
	}

	@ApiMethod
	public static boolean isDebugMode() {
		return debugMode;
	}
}
