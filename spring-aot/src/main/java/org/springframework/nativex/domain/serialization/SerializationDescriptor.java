/*
 * Copyright 2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.nativex.domain.serialization;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author Andy Clement
 */
public class SerializationDescriptor {

	private final Set<String> serializableTypes;

	public SerializationDescriptor() {
		this.serializableTypes = new HashSet<>();
	}

	public SerializationDescriptor(SerializationDescriptor metadata) {
		this.serializableTypes = new HashSet<>(metadata.serializableTypes);
	}

	public Set<String> getSerializableTypes() {
		return this.serializableTypes;
	}

	public void add(String className) {
		this.serializableTypes.add(className);
	}

	@Override
	public String toString() {
		return String.format("SerializationDescriptor #%s: %s", serializableTypes.size(),serializableTypes.toString());
	}

	public boolean isEmpty() {
		return serializableTypes.isEmpty();
	}

	public static SerializationDescriptor of(String jsonString) {
		try {
			return SerializationDescriptorJsonMarshaller.read(jsonString);
		} catch (Exception e) {
			throw new IllegalStateException("Unable to read json:\n"+jsonString, e);
		}
	}

	public void consume(Consumer<String> consumer) {
		serializableTypes.stream().forEach(t -> consumer.accept(t));
	}

	public void merge(SerializationDescriptor otherSerializationDescriptor) {
		serializableTypes.addAll(otherSerializationDescriptor.serializableTypes);
	}
	
	public boolean contains(String className) {
		return serializableTypes.contains(className);
	}

}
