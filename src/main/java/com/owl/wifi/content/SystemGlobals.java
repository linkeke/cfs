package com.owl.wifi.content;

import java.io.IOException;
import java.util.Properties;

public class SystemGlobals {

	private static SystemGlobals singleton;
	
	private Properties props = new Properties();

	private SystemGlobals() {
	}

	public static SystemGlobals getInstance() {
		if (singleton == null) {
			singleton = new SystemGlobals();
		}
		return singleton;
	}

	public String getProperty(String key) {
		try {
			props.load(SystemGlobals.class.getResourceAsStream("/config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props.getProperty(key);
	}


}
