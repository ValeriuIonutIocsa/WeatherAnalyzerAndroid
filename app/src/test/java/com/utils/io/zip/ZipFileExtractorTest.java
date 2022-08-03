package com.utils.io.zip;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ZipFileExtractorTest {

	@Test
	void testWork() {

		final String zipArchiveFilePathString = "D:\\IVI_MISC\\Misc\\nf\\tmp\\abcd.zip";
		final String dstFolderPathString = "D:\\IVI_MISC\\Misc\\nf\\tmp";
		final boolean useTempFile = true;
		final boolean deleteExisting = false;
		final int threadCount = 12;
		final boolean updateFileTimes = true;
		final boolean verbose = true;

		final Path zipArchiveFilePath = Paths.get(zipArchiveFilePathString);
		final Path dstFolderPath = Paths.get(dstFolderPathString);
		final ZipFileExtractor zipFileExtractor = new ZipFileExtractor(zipArchiveFilePath, dstFolderPath,
				useTempFile, deleteExisting, threadCount, updateFileTimes, verbose);
		zipFileExtractor.work();
		final boolean success = zipFileExtractor.isSuccess();
		Assertions.assertTrue(success);
	}
}
