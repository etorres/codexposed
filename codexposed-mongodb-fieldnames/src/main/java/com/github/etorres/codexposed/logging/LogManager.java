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

package com.github.etorres.codexposed.logging;

import static org.slf4j.LoggerFactory.getLogger;
import static org.slf4j.bridge.SLF4JBridgeHandler.install;
import static org.slf4j.bridge.SLF4JBridgeHandler.removeHandlersForRootLogger;

import java.io.IOException;

import org.slf4j.Logger;

/**
 * Manages loggers, installing the necessary bridges to unify logging.
 * @author Erik Torres <etserrano@gmail.com>
 */
public enum LogManager implements AutoCloseable {

	LOG_MANAGER;

	private final static Logger LOGGER = getLogger(LogManager.class);

	private LogManager() {
		// remove existing handlers attached to j.u.l root logger
		removeHandlersForRootLogger();

		// add SLF4JBridgeHandler to j.u.l's root logger, should be done once during the 
		// initialization phase of the application
		install();
	}

	public void preload() {
		LOGGER.info("Log manager was loaded");		
	}

	@Override
	public void close() throws IOException {

	}

}