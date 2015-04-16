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

import static com.google.common.base.MoreObjects.toStringHelper;
import static com.github.etorres.codexposed.MongoDBSafeKey.escapeMapKey;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Provides a map that uses a key compatible with mongoDB field names. Internally, this class wraps a {@link Hashtable} with a key restriction.
 * @author Erik Torres <etserrano@gmail.com>
 * @see <a href="http://docs.mongodb.org/manual/reference/limits/#Restrictions-on-Field-Names">mongoDB Restrictions on Field Names</a>
 */
public class MongoDBSafeMap<K extends MongoDBSafeKey, V> implements Map<K, V> {

	private Map<K, V> __map = new Hashtable<>();

	public MongoDBSafeMap() {		
	}

	public MongoDBSafeMap(final Map<? extends K, ? extends V> initial) {
		super();
		this.putAll(initial);
	}

	@Override
	public void clear() {
		__map.clear();
	}

	@Override
	public boolean containsKey(final Object key) {
		return __map.containsKey(key);
	}

	@Override
	public boolean containsValue(final Object value) {
		return __map.containsValue(value);
	}

	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		return __map.entrySet();
	}

	@Override
	public V get(final Object key) {
		return __map.get(key);
	}

	@Override
	public boolean isEmpty() {
		return __map.isEmpty();
	}

	@Override
	public Set<K> keySet() {
		return __map.keySet();
	}

	@Override
	public V put(final K key, final V value) {
		return __map.put(key, value);
	}

	@Override
	public void putAll(final Map<? extends K, ? extends V> m) {
		__map.putAll(m);
	}

	@Override
	public V remove(final Object key) {
		return __map.remove(key);
	}

	@Override
	public int size() {
		return __map.size();
	}

	@Override
	public Collection<V> values() {
		return __map.values();
	}

	public V getUnescaped(final String key) {		
		return __map.get(escapeMapKey(key));
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null || !(obj instanceof MongoDBSafeMap)) {
			return false;
		}
		final MongoDBSafeMap<?, ?> other = MongoDBSafeMap.class.cast(obj);
		return Objects.equals(__map, other.__map);
	}

	@Override
	public int hashCode() {
		return Objects.hash(__map);
	}

	@Override
	public String toString() {
		return toStringHelper(this)
				.add("__map", __map != null ? __map.toString() : null)
				.toString();
	}

}