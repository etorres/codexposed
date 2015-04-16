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
import static com.google.common.collect.Lists.newArrayList;
import static com.mongodb.client.model.Filters.eq;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.etorres.codexposed.MongoDBSafeKey;
import com.github.etorres.codexposed.MongoDBSafeMap;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;

/**
 * Tests {@link MongoDBSafeMap} maps with a mongoDB collection.
 * @author Erik Torres <etserrano@gmail.com>
 */
public class MongoDBCollectionTest {

	public static final String DB_NAME = "codexposed";
	public static final String DB_COLLECTION = "mycollection";

	private static MongoClient mongoClient = null;

	@BeforeClass
	public static void setup() throws Exception {
		System.out.println("    >> MongoDBCollectionTest.setup()");
		// create mongoDB client
		final MongoClientOptions options = MongoClientOptions.builder()
				.readPreference(ReadPreference.nearest())
				.writeConcern(WriteConcern.ACKNOWLEDGED).build();
		final List<ServerAddress> seeds = newArrayList(new ServerAddress("localhost", 27017));
		final List<MongoCredential> credentials = newArrayList();
		mongoClient = new MongoClient(seeds, credentials, options);		
	}

	@AfterClass
	public static void cleanup() {
		System.out.println("    >> MongoDBCollectionTest.cleanup()");
		if (mongoClient != null) {
			mongoClient.close();
			mongoClient = null;
		}
	}

	@Test
	public void test() {
		System.out.println("    >> MongoDBCollectionTest.test()");

		// create dataset
		final String unescapedKey = "$invalid.key";
		final String escapedKey = escapeFieldName(unescapedKey);
		final MongoDBSafeMap<MongoDBSafeKey, String> safeMap = new MongoDBSafeMap<>();
		safeMap.put(escapeMapKey(unescapedKey), "Hello World!");
		assertThat("map is not empty", safeMap.isEmpty(), equalTo(false));

		// test create index
		createIndex(escapedKey);

		// test insert record
		final String id = insert(safeMap.toMap());
		assertThat("id is not null", id, notNullValue());
		assertThat("id is not empty", isNotBlank(id), equalTo(true));
		/* uncomment for additional output */
		System.out.println("        >> Inserted document Id: " + id);

		// test find record
		final Document doc = find(escapedKey, "Hello World!");
		assertThat("doc is not null", doc, notNullValue());
		assertThat("doc is not empty", doc.isEmpty(), equalTo(false));
		/* uncomment for additional output */
		System.out.println("        >> Found document: " + doc);
	}

	private void createIndex(final String fieldName) {
		final MongoDatabase db = mongoClient.getDatabase(DB_NAME);
		final MongoCollection<Document> collection = db.getCollection(DB_COLLECTION);
		collection.createIndex(new Document(fieldName, 1), new IndexOptions().unique(true).background(true));
	}

	private String insert(final Map<String, Object> map) {		
		final MongoDatabase db = mongoClient.getDatabase(DB_NAME);
		final MongoCollection<Document> collection = db.getCollection(DB_COLLECTION);
		final Document doc = new Document(map);
		collection.insertOne(doc);
		return ObjectId.class.cast(doc.get("_id")).toString();
	}

	private Document find(final String fieldName, final String fieldValue) {
		final MongoDatabase db = mongoClient.getDatabase(DB_NAME);
		final MongoCollection<Document> collection = db.getCollection(DB_COLLECTION);
		return collection.find(eq(fieldName, fieldValue)).first();
	}

}