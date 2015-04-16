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

import static com.github.etorres.codexposed.logging.LogManager.LOG_MANAGER;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Integration tests suite.
 * @author Erik Torres <etserrano@gmail.com>
 */
@RunWith(Suite.class)
@SuiteClasses({ MongoDBCollectionTest.class })
public class AllIntegrationTests {

	@BeforeClass
	public static void setup() {
		System.out.println(" >> AllIntegrationTests.setup()");
		LOG_MANAGER.preload();
	}

	@AfterClass
	public static void release() {
		System.out.println(" >> AllIntegrationTests.release()");
		try {
			LOG_MANAGER.close();
		} catch (IOException ignore) { }
	}

}