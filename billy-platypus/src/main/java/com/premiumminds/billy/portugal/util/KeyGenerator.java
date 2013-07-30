/**
 * Copyright (C) 2013 Premium Minds.
 * 
 * This file is part of billy platypus (PT Pack).
 * 
 * billy platypus (PT Pack) is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * 
 * billy platypus (PT Pack) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy platypus (PT Pack). If not, see
 * <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;

import org.apache.commons.io.IOUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMReader;

/**
 * Class for generating Private and Public Keys.
 */
public class KeyGenerator {

	private String privateKeyPath;

	public KeyGenerator(String privateKeyPath) {
		if (Security.getProvider("BC") == null) {
			Security.addProvider(new BouncyCastleProvider());
		}
		this.privateKeyPath = privateKeyPath;
	}

	private String getKeyFromFile() {
		FileInputStream inputStream = null;
		String key = "";

		try {
			inputStream = new FileInputStream(privateKeyPath);
			key = IOUtils.toString(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(inputStream);
		}

		return key;
	}

	private KeyPair getKeyPair() {
		PEMReader pemReader = new PEMReader(new StringReader(getKeyFromFile()));
		KeyPair pair = null;

		try {
			pair = (KeyPair) pemReader.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(pemReader);
		}

		return pair;
	}

	public PrivateKey getPrivateKey() {
		return getKeyPair().getPrivate();
	}

	public PublicKey getPublicKey(String fullPath) {
		return getKeyPair().getPublic();
	}

}
