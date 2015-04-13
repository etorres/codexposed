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

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.trimToEmpty;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeTrue;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.google.common.collect.Lists;

/**
 * Tests {@link ArgValidator}. Includes tests for methods with String parameters.
 * @author Erik Torres <etserrano@gmail.com>
 */
@RunWith(Parameterized.class)
public class StringArgValidatorTest {

	public static final String VALID = "valid";
	public static final String NULL_VALUE = "null_value";
	public static final String EMPTY_VALUE = "empty_value";

	@BeforeClass
	public static void setup() throws Exception {
		System.out.println("    >> StringArgValidatorTest.setup()");
	}

	@AfterClass
	public static void cleanup() {
		System.out.println("    >> StringArgValidatorTest.cleanup()");
	}

	@Parameter(value = 0)
	public String type;

	@Parameter(value = 1)
	public String expected;

	@Parameter(value = 2)
	public String required;

	@Parameter(value = 3)
	public String optional;

	@Parameters
	public static List<String[]> parameters() {
		return Lists.<String[]>newArrayList(new String[][]{ 
				{ VALID, "a1,a2", "a1", "a2" },
				{ VALID, "a1,default", "a1", "" },
				{ VALID, "a1,default", "a1", null },
				{ NULL_VALUE, null, null, "a1" },
				{ EMPTY_VALUE, null, "", "a1" }
		});
	}

	@Test
	public void test() {
		assumeTrue(VALID.equals(type));
		System.out.println("    >> StringArgValidatorTest.test()");
		/* uncomment for additional output */
		System.out.println("        >> Expected: '" + expected + "', Required: '" + required 
				+ "', Optional: '" + optional + "'");
		final ArgValidator validator = new ArgValidator();
		final String result = validator.simpleParams(required, optional);
		assertThat("result is not null", result, notNullValue());
		assertThat("result is not empty", isNotBlank(trimToEmpty(result)), equalTo(true));
		assertThat("result coincides with expected", result, equalTo(expected));		
	}

	@Test(expected = NullPointerException.class)
	public void testNullValue() {
		assumeTrue(NULL_VALUE.equals(type));
		System.out.println("    >> StringArgValidatorTest.testNullValue()");
		/* uncomment for additional output */
		System.out.println("        >> NullPointerException expected, Required: '" + required 
				+ "', Optional: '" + optional + "'");
		final ArgValidator validator = new ArgValidator();
		validator.simpleParams(required, optional);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyValue() {
		assumeTrue(EMPTY_VALUE.equals(type));
		System.out.println("    >> StringArgValidatorTest.testEmptyValue()");
		/* uncomment for additional output */
		System.out.println("        >> IllegalArgumentException expected, Required: '" + required 
				+ "', Optional: '" + optional + "'");
		final ArgValidator validator = new ArgValidator();
		validator.simpleParams2(required, optional);
	}

}