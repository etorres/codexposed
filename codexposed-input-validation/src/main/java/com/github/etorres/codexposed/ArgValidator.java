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

import static com.google.common.base.Joiner.on;
import static com.google.common.base.Optional.fromNullable;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Iterables.concat;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Ordering.natural;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableMap;
import static org.apache.commons.lang3.StringUtils.trim;
import static org.apache.commons.lang3.StringUtils.trimToNull;

import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

/**
 * Sample class demonstrating the validation of input parameters to class methods.
 * @author Erik Torres <etserrano@gmail.com>
 */
public class ArgValidator {

	public static final String DEFAULT_VALUE = "default";

	/**
	 * Example: set valid values to method parameters.
	 * @param required - required parameter, empty string is allowed
	 * @param optional - optional parameter
	 * @return A string combining both input parameters in lexicographical order.
	 * @throws NullPointerException When a required parameter has <code>null</code> value.
	 */
	public String simpleParams(final String required, final @Nullable String optional) {		
		final String required2 = checkNotNull(trimToNull(required), "Uninitialized or invalid value");
		final String optional2 = fromNullable(trimToNull(optional)).or(DEFAULT_VALUE);
		// operate on the input data
		return on(',').skipNulls().join(natural().nullsFirst().sortedCopy(newArrayList(required2, optional2)));
	}

	/**
	 * Example: set valid values to method parameters.
	 * @param required - required parameter, empty string is not allowed
	 * @param optional - optional parameter
	 * @return A string combining both input parameters in lexicographical order.
	 * @throws NullPointerException When a required parameter has <code>null</code> value.
	 * @throws IllegalArgumentException When a required parameter is blank or empty.
	 */
	public String simpleParams2(final String required, final @Nullable String optional) {		
		final String required2 = checkNotNull(trim(required), "Uninitialized value");
		checkArgument(!required2.isEmpty(), "Empty string is not allowed");
		final String optional2 = fromNullable(trimToNull(optional)).or(DEFAULT_VALUE);
		// operate on the input data
		return on(',').skipNulls().join(natural().nullsFirst().sortedCopy(newArrayList(required2, optional2)));
	}

	/**
	 * Example: set valid values to method parameters. Internally uses Java unmodifiable lists.
	 * @param required - required parameter, empty list is allowed
	 * @param optional - optional parameter
	 * @return A new list combining both input parameters.
	 * @throws NullPointerException When a required parameter has <code>null</code> value.
	 */
	public List<String> listParams(final List<String> required, final @Nullable List<String> optional) {
		final List<String> required2 = unmodifiableList(checkNotNull(required, "Uninitialized list"));
		final List<String> optional2 = (optional != null ? unmodifiableList(optional) : Collections.<String>emptyList());
		// operate on the input data
		return newArrayList(concat(required2, optional2));
	}

	/**
	 * Example: set valid values to method parameters. Internally uses Java unmodifiable lists.
	 * @param required - required parameter, empty list is not allowed
	 * @param optional - optional parameter
	 * @return A new list combining both input parameters.
	 * @throws NullPointerException When a required parameter has <code>null</code> value.
	 * @throws IllegalArgumentException When a required parameter is empty.
	 */
	public List<String> listParams2(final List<String> required, final @Nullable List<String> optional) {
		final List<String> required2 = unmodifiableList(checkNotNull(required, "Uninitialized list"));
		checkArgument(!required2.isEmpty(), "Empty list is not allowed");
		final List<String> optional2 = (optional != null ? unmodifiableList(optional) : Collections.<String>emptyList());
		// operate on the input data
		return newArrayList(concat(required2, optional2));
	}

	/**
	 * Example: set valid values to method parameters. Internally uses Guava immutable lists.
	 * @param required - required parameter, empty list is allowed
	 * @param optional - optional parameter
	 * @return A new list combining both input parameters.
	 * @throws NullPointerException When a required parameter has <code>null</code> value.
	 */
	public List<String> immutableListParams(final List<String> required, final @Nullable List<String> optional) {
		final ImmutableList<String> required2 = ImmutableList.copyOf(checkNotNull(required, "Uninitialized list"));
		final ImmutableList<String> optional2 = (optional != null ? ImmutableList.copyOf(optional) : ImmutableList.<String>of());
		// operate on the input data
		return newArrayList(concat(required2, optional2));
	}

	/**
	 * Example: set valid values to method parameters. Internally uses Java standard lists.
	 * @param required - required parameter, empty list is allowed
	 * @param optional - optional parameter
	 * @return A new list combining both input parameters.
	 * @throws NullPointerException When a required parameter has <code>null</code> value.
	 */
	public List<String> mutableListParams(final List<String> required, final @Nullable List<String> optional) {
		final List<String> required2 = newArrayList(checkNotNull(required, "Uninitialized list"));
		final List<String> optional2 = (optional != null ? newArrayList(optional) : Lists.<String>newArrayList());
		// operate on the input data
		return newArrayList(concat(required2, optional2));
	}

	/**
	 * Example: set valid values to method parameters. Internally uses Java unmodifiable maps.
	 * @param required - required parameter, empty map is allowed
	 * @param optional - optional parameter
	 * @return A new map combining both input parameters.
	 * @throws NullPointerException When a required parameter has <code>null</code> value.
	 */
	public Map<String, String> mapParams(final Map<String, String> required, final @Nullable Map<String, String> optional) {
		final Map<String, String> required2 = unmodifiableMap(checkNotNull(required, "Uninitialized map"));
		final Map<String, String> optional2 = (optional != null ? unmodifiableMap(optional) : Collections.<String, String>emptyMap());
		// operate on the input data
		final Map<String, String> response = new Hashtable<>(required2);
		response.putAll(optional2);
		return response;
	}

	/**
	 * Example: set valid values to method parameters. Internally uses Java unmodifiable maps.
	 * @param required - required parameter, empty map is not allowed
	 * @param optional - optional parameter
	 * @return A new map combining both input parameters.
	 * @throws NullPointerException When a required parameter has <code>null</code> value.
	 * @throws IllegalArgumentException When a required parameter is empty.
	 */
	public Map<String, String> mapParams2(final Map<String, String> required, final @Nullable Map<String, String> optional) {
		final Map<String, String> required2 = unmodifiableMap(checkNotNull(required, "Uninitialized map"));
		checkArgument(!required2.isEmpty(), "Empty map is not allowed");
		final Map<String, String> optional2 = (optional != null ? unmodifiableMap(optional) : Collections.<String, String>emptyMap());
		// operate on the input data
		final Map<String, String> response = new Hashtable<>(required2);
		response.putAll(optional2);
		return response;
	}

	/**
	 * Example: set valid values to method parameters. Internally uses Guava immutable maps.
	 * @param required - required parameter, empty map is allowed
	 * @param optional - optional parameter
	 * @return A new map combining both input parameters.
	 * @throws NullPointerException When a required parameter has <code>null</code> value.
	 */
	public Map<String, String> immutableMapParams(final Map<String, String> required, final @Nullable Map<String, String> optional) {
		final ImmutableMap<String, String> required2 = ImmutableMap.copyOf(checkNotNull(required, "Uninitialized list"));
		final ImmutableMap<String, String> optional2 = (optional != null ? ImmutableMap.copyOf(optional) : ImmutableMap.<String, String>of());
		// operate on the input data
		final Map<String, String> response = new Hashtable<>(required2);
		response.putAll(optional2);
		return response;		
	}

	/**
	 * Example: set valid values to method parameters. Internally uses Java standard maps.
	 * @param required - required parameter, empty map is allowed
	 * @param optional - optional parameter
	 * @return A new map combining both input parameters.
	 * @throws NullPointerException When a required parameter has <code>null</code> value.
	 */
	public Map<String, String> mutableMapParams(final Map<String, String> required, final @Nullable Map<String, String> optional) {
		final Map<String, String> required2 = new Hashtable<>(checkNotNull(required, "Uninitialized list"));
		final Map<String, String> optional2 = (optional != null ? new Hashtable<>(optional) : new Hashtable<String, String>());
		// operate on the input data
		final Map<String, String> response = new Hashtable<>(required2);
		response.putAll(optional2);
		return response;
	}	

}