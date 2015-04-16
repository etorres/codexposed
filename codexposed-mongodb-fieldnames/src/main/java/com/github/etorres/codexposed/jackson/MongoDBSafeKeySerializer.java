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

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.etorres.codexposed.MongoDBSafeKey;

/**
 * Serialize mongoDB field names from {@link MongoDBSafeKey} Java class to JSON. <strong>Note</strong> that this class cannot be used to serialize
 * field values, only field names (for example, as part of a {@link java.util.Map} key).
 * @author Erik Torres <etserrano@gmail.com>
 */
public class MongoDBSafeKeySerializer extends StdSerializer<MongoDBSafeKey> {

	private static final long serialVersionUID = 3924712287460903559L;

	public MongoDBSafeKeySerializer() {
		super(MongoDBSafeKey.class);
	}

	@Override
	public void serialize(final MongoDBSafeKey value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonProcessingException {
		if (value == null) {
			jgen.writeNull();
		} else {
			jgen.writeFieldName(value.getKey());
		}
	}

}