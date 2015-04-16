/*
 * Copyright (c) 2015 Erik Torres
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.etorres.codexposed.test;

import static com.github.etorres.codexposed.MongoDBSafeKey.escapeFieldName;
import static com.github.etorres.codexposed.MongoDBSafeKey.escapeMapKey;
import static com.github.etorres.codexposed.MongoDBSafeKey.unescapeFieldName;
import static com.github.etorres.codexposed.jackson.MongoDBJsonMapper.JSON_MAPPER;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.util.Map.Entry;

import org.bson.Document;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.etorres.codexposed.MongoDBSafeKey;
import com.github.etorres.codexposed.MongoDBSafeMap;

/**
 * Tests {@link MongoDBSafeMap} maps.
 * @author Erik Torres <etserrano@gmail.com>
 */
public class MongoDBSafeMapTest {

	private final String[] names = { "$this.is.an.invalid...s$ring." };
	private final String[] escapedNames = { "\uff04this\uff0eis\uff0ean\uff0einvalid\uff0e\uff0e\uff0es\uff04ring\uff0e" };

	@BeforeClass
	public static void setup() throws Exception {
		System.out.println("    >> MongoDBSafeMapTest.setup()");
	}

	@AfterClass
	public static void cleanup() {
		System.out.println("    >> MongoDBSafeMapTest.cleanup()");
	}

	@Test
	public void testFieldNameManipulation() {
		System.out.println("    >> MongoDBSafeMapTest.testFieldNameManipulation()");
		for (int i = 0; i < names.length; i++) {
			final String escapedName = escapeFieldName(names[i]);
			assertThat("escaped name is not null", escapedName, notNullValue());
			assertThat("escaped name is not empty", isNotBlank(escapedName), equalTo(true));
			assertThat("escaped name coincides with expected", escapedName, equalTo(escapedNames[i]));
			// uncomment for additional output
			System.out.println("        >> Original: " + names[i] + ", escaped=" + escapedName);

			final String unescapedName = unescapeFieldName(escapedName);
			assertThat("unescaped name is not null", unescapedName, notNullValue());
			assertThat("unescaped name is not empty", isNotBlank(unescapedName), equalTo(true));
			assertThat("unescaped name coincides with expected", unescapedName, equalTo(names[i]));
		}
	}

	@Test
	public void testMapOperations() {
		System.out.println("    >> MongoDBSafeMapTest.testMapOperations()");
		for (int i = 0; i < names.length; i++) {		
			final MongoDBSafeMap<MongoDBSafeKey, String> safeMap = new MongoDBSafeMap<>();
			safeMap.put(escapeMapKey(names[i]), "Hello World!");
			assertThat("map is not empty", safeMap.isEmpty(), equalTo(false));
			// uncomment for additional output
			for (final Entry<MongoDBSafeKey, String> entry : safeMap.entrySet()) {
				System.out.println("        >> Map entry: [" + entry.getKey().getKey() + ", " + entry.getValue() + "]");
			}

			final MongoDBSafeKey key = new MongoDBSafeKey();
			key.setKey(escapedNames[i]);

			String value = safeMap.get(key);
			assertThat("value is not null", value, notNullValue());
			assertThat("value is not empty", isNotBlank(value), equalTo(true));
			assertThat("value coincides with expected", value, equalTo("Hello World!"));

			value = safeMap.getUnescaped(names[i]);
			assertThat("value is not null", value, notNullValue());
			assertThat("value is not empty", isNotBlank(value), equalTo(true));
			assertThat("value coincides with expected", value, equalTo("Hello World!"));
		}
	}

	@Test
	public void testJsonObjectMapper() throws IOException {
		System.out.println("    >> MongoDBSafeMapTest.testJsonObjectMapper()");
		for (int i = 0; i < names.length; i++) {		
			final MongoDBSafeMap<MongoDBSafeKey, String> safeMap = new MongoDBSafeMap<>();
			safeMap.put(escapeMapKey(names[i]), "Hello World!");
			assertThat("map is not empty", safeMap.isEmpty(), equalTo(false));

			// test JSON serialization
			final String payload = JSON_MAPPER.writeValueAsString(safeMap);
			assertThat("serialized map is not null", payload, notNullValue());
			assertThat("serialized map is not empty", isNotBlank(payload), equalTo(true));
			/* uncomment for additional output */
			System.out.println("        >> Serialized map (JSON): " + payload);

			// test JSON deserialization
			@SuppressWarnings("unchecked")
			final MongoDBSafeMap<MongoDBSafeKey, String> safeMap2 = JSON_MAPPER.readValue(payload, MongoDBSafeMap.class);
			assertThat("deserialized map is not null", safeMap2, notNullValue());
			assertThat("deserialized map coincides with expected", safeMap2, equalTo(safeMap));
			// uncomment for additional output
			for (final Entry<MongoDBSafeKey, String> entry : safeMap2.entrySet()) {
				System.out.println("        >> Map entry: [" + entry.getKey().getKey() + ", " + entry.getValue() + "]");
			}
		}
	}

	@Test
	public void testMongoDBDocument() {
		System.out.println("    >> MongoDBSafeMapTest.testMongoDBDocument()");
		for (int i = 0; i < names.length; i++) {		
			final MongoDBSafeMap<MongoDBSafeKey, String> safeMap = new MongoDBSafeMap<>();
			safeMap.put(escapeMapKey(names[i]), "Hello World!");
			assertThat("map is not empty", safeMap.isEmpty(), equalTo(false));

			final Document doc = new Document(safeMap.toMap());			
			assertThat("document is not null", doc, notNullValue());
			assertThat("document is not empty", doc.isEmpty(), equalTo(false));
		}
	}

}