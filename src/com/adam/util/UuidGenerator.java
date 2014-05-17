package com.adam.util;

import java.util.UUID;

public class UuidGenerator {

	synchronized final public static String generate36UUID() {
		return UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
	}

}
