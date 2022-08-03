package com.utils.xml.dom.openers;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.utils.io.PathUtils;

public class DocumentOpenerInputStream extends AbstractDocumentOpener {

	private final InputStream inputStream;
	private final String schemaFolderPathString;

	public DocumentOpenerInputStream(
			final InputStream inputStream,
			final String schemaFolderPathString) {

		this.inputStream = inputStream;
		this.schemaFolderPathString = schemaFolderPathString;
	}

	@Override
	Document parse(
			final DocumentBuilder documentBuilder) throws Exception {

		documentBuilder.setEntityResolver((
				publicId,
				systemId) -> {
			final String schemaFileName = PathUtils.computeFileName(systemId);
			final Path schemaFilePath = Paths.get(schemaFolderPathString, schemaFileName);
			return new InputSource(Files.newBufferedReader(schemaFilePath));
		});

		return documentBuilder.parse(inputStream);
	}
}
