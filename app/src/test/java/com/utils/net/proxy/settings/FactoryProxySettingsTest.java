package com.utils.net.proxy.settings;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;

import org.junit.jupiter.api.Test;

import com.utils.crypt.EncryptionUtils;
import com.utils.log.Logger;

class FactoryProxySettingsTest {

	@Test
	void testNewInstance() {

		final ProxySettings proxySettings = FactoryProxySettings.newInstance();
		Logger.printLine(proxySettings);
	}

	@Test
	void testCreateProxySettings() throws Exception {

		final Path proxySettingsPath = FactoryProxySettings.createProxySettingsPath();
		Logger.printProgress("generating proxy settings file:");
		Logger.printLine(proxySettingsPath);

		final String httpHost = "cias3basic.conti.de";
		final int httpPort = 8_080;
		final String httpUsername = "uid39522";
		final String httpPassword = "crocrocro_010";

		final Properties properties = new Properties();
		properties.setProperty("httpHost", httpHost);
		properties.setProperty("httpPort", String.valueOf(httpPort));
		properties.setProperty("httpUsername", httpUsername);
		properties.setProperty("httpPassword", httpPassword);

		final Cipher encryptCipher = EncryptionUtils.createEncryptCipher();
		try (OutputStream outputStream = new CipherOutputStream(
				new BufferedOutputStream(Files.newOutputStream(proxySettingsPath)), encryptCipher)) {
			properties.store(outputStream, "proxy settings");
		}
	}
}
