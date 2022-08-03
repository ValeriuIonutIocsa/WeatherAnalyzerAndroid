package com.utils.log.progress;

import com.utils.log.Logger;

public final class ProgressIndicators {

	private static ProgressIndicator instance = ProgressIndicatorNoOp.INSTANCE;

	private ProgressIndicators() {
	}

	public static void setInstance(
			final ProgressIndicator instance) {
		Logger.printError("4444 here");
		ProgressIndicators.instance = instance;
	}

	public static ProgressIndicator getInstance() {
		return instance;
	}
}
