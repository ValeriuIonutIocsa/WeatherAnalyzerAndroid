package com.utils.hash;

import java.io.BufferedInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.Security;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import com.utils.io.IoUtils;
import com.utils.log.Logger;
import com.utils.string.StrUtils;

public final class HashUtils {

	private HashUtils() {
	}

	public static List<String> createHashAlgorithmList() {

		final Set<String> algorithmSet = Security.getAlgorithms("MessageDigest");
		final List<String> algorithmList = new ArrayList<>(algorithmSet);
		algorithmList.sort(Comparator.naturalOrder());
		return algorithmList;
	}

	public static String computeFileHash(
			final Path filePath,
			final String algorithm) {

		String hash = null;
		try {
			if (!IoUtils.fileExists(filePath)) {
				Logger.printWarning("cannot compute file hash, because the file does not exist:" +
						System.lineSeparator() + filePath);

			} else {
				final MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
				final int bufferSize = 8192;
				try (BufferedInputStream bufferedInputStream = new BufferedInputStream(
						Files.newInputStream(filePath), bufferSize)) {

					final byte[] buffer = new byte[bufferSize];
					int length;
					while ((length = bufferedInputStream.read(buffer)) > 0) {
						messageDigest.update(buffer, 0, length);
					}
				}
				final byte[] hashByteArray = messageDigest.digest();
				hash = StrUtils.byteArrayToHexString(hashByteArray);
			}

		} catch (final Exception exc) {
			Logger.printError("failed to compute hash of file:" +
					System.lineSeparator() + filePath);
			Logger.printException(exc);
		}
		return hash;
	}
}
