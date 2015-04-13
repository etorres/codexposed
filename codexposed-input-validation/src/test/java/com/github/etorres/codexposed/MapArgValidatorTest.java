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

package com.github.etorres.codexposed;

import static com.google.common.collect.ImmutableMap.of;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeTrue;

import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.google.common.collect.Lists;

/**
 * Tests {@link ArgValidator}. Includes tests for methods with list parameters.
 * @author Erik Torres <etserrano@gmail.com>
 */
@RunWith(Parameterized.class)
public class MapArgValidatorTest {

	public static final String VALID = "valid";
	public static final String NULL_VALUE = "null_value";
	public static final String EMPTY_MAP = "empty_map";

	@BeforeClass
	public static void setup() throws Exception {
		System.out.println("    >> MapArgValidatorTest.setup()");
	}

	@AfterClass
	public static void cleanup() {
		System.out.println("    >> MapArgValidatorTest.cleanup()");
	}

	@Parameter(value = 0)
	public String type;

	@Parameter(value = 1)
	public Map<String, String> expected;

	@Parameter(value = 2)
	public Map<String, String> required;

	@Parameter(value = 3)
	public Map<String, String> optional;

	@Parameters
	public static List<Object[]> parameters() {
		return Lists.<Object[]>newArrayList(new Object[][]{ 
				{ VALID, of("a1", "v1", "a2", "v2"), of("a1", "v1"), of("a2", "v2") },
				{ VALID, of("a1", "v1"), of("a1", "v1"), of() },
				{ VALID, of("a1", "v1"), of("a1", "v1"), null },
				{ NULL_VALUE, null, null, of("a1", "v1") },
				{ EMPTY_MAP, null, of(), of("a1", "v1") }
		});
	}

	@Test
	public void test() {
		assumeTrue(VALID.equals(type));
		System.out.println("    >> MapArgValidatorTest.test()");
		/* uncomment for additional output */
		System.out.println("        >> Expected: '" + expected + "', Required: '" + required 
				+ "', Optional: '" + optional + "'");
		final ArgValidator validator = new ArgValidator();
		assertMap(validator.mapParams(required, optional));
		assertMap(validator.immutableMapParams(required, optional));
		assertMap(validator.mutableMapParams(required, optional));
	}

	private void assertMap(final Map<String, String> result) {
		assertThat("result is not null", result, notNullValue());
		assertThat("result is not empty", result.isEmpty(), equalTo(false));
		assertThat("result coincides with expected", result, equalTo(expected));
	}	

	@Test(expected = NullPointerException.class)
	public void testNullValue() {
		assumeTrue(NULL_VALUE.equals(type));
		System.out.println("    >> MapArgValidatorTest.testNullValue()");
		/* uncomment for additional output */
		System.out.println("        >> NullPointerException expected, Required: '" + required 
				+ "', Optional: '" + optional + "'");
		final ArgValidator validator = new ArgValidator();
		validator.mapParams(required, optional);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyValue() {
		assumeTrue(EMPTY_MAP.equals(type));
		System.out.println("    >> MapArgValidatorTest.testEmptyValue()");
		/* uncomment for additional output */
		System.out.println("        >> IllegalArgumentException expected, Required: '" + required 
				+ "', Optional: '" + optional + "'");
		final ArgValidator validator = new ArgValidator();
		validator.mapParams2(required, optional);
	}

}