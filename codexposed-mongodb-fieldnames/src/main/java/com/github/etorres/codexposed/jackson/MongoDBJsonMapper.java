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

package com.github.etorres.codexposed.jackson;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.etorres.codexposed.MongoDBSafeKey;

/**
 * Binds Java objects to/from mongoDB using the Jackson JSON processor.
 * @author Erik Torres <etserrano@gmail.com>
 */
public final class MongoDBJsonMapper {

	public static final ObjectMapper JSON_MAPPER = new ObjectMapper();
	static {
		// apply general configuration
		JSON_MAPPER.setSerializationInclusion(Include.NON_NULL);
		JSON_MAPPER.setSerializationInclusion(Include.NON_DEFAULT);
		// register external serializers/deserializers		
		final SimpleModule simpleModule = new SimpleModule("CodeExposedModule", new Version(1, 0, 0, null, "com.github.etorres.codexposed", "mongodb-fieldnames"));
		simpleModule.addKeySerializer(MongoDBSafeKey.class, new MongoDBSafeKeySerializer());
		simpleModule.addKeyDeserializer(MongoDBSafeKey.class, new MongoDBSafeKeyDeserializer());
		JSON_MAPPER.registerModule(simpleModule);
	}

}