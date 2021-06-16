/*
 * Copyright 2019-2021 the original author or authors.
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

package org.springframework.nativex.domain.reflect;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.nativex.hint.Flag;

/**
 * Reflection information about a single class.
 *
 * @author Andy Clement
 * @see ReflectionDescriptor
 */
public final class ClassDescriptor {

	private String name; // e.g. java.lang.Class

	private List<FieldDescriptor> fields;

	private List<MethodDescriptor> methods; // includes constructors "<init>"

	private Set<Flag> flags; // Inclusion in list indicates they are set

	ClassDescriptor() {
	}

	ClassDescriptor(String name, List<FieldDescriptor> fields, List<MethodDescriptor> methods, Set<Flag> flags) {
		this.name = name;
		this.fields = fields;
		this.methods = methods;
		this.flags = flags;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ClassDescriptor other = (ClassDescriptor) o;
		boolean result = true;
		result = result && nullSafeEquals(this.name, other.name);
		result = result && nullSafeEquals(this.flags, other.flags);
		result = result && nullSafeEquals(this.fields, other.fields);
		result = result && nullSafeEquals(this.methods, other.methods);
		return result;
	}

	@Override
	public int hashCode() {
		int result = nullSafeHashCode(this.name);
		result = 31 * result + nullSafeHashCode(this.flags);
		result = 31 * result + nullSafeHashCode(this.fields);
		result = 31 * result + nullSafeHashCode(this.methods);
		return result;
	}

	private boolean nullSafeEquals(Object o1, Object o2) {
		if (o1 == o2) {
			return true;
		}
		if (o1 == null || o2 == null) {
			return false;
		}
		return o1.equals(o2);
	}

	private int nullSafeHashCode(Object o) {
		return (o != null) ? o.hashCode() : 0;
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder(this.name);
		buildToStringProperty(string, "setFlags", this.flags);
		buildToStringProperty(string, "fields", this.fields);
		buildToStringProperty(string, "methods", this.methods);
		return string.toString();
	}

	protected void buildToStringProperty(StringBuilder string, String property, Object value) {
		if (value != null) {
			string.append(" ").append(property).append(":").append(value);
		}
	}

	public Set<Flag> getFlags() {
		return this.flags;
	}

	public List<FieldDescriptor> getFields() {
		return this.fields;
	}

	public List<MethodDescriptor> getMethods() {
		return this.methods;
	}

	public static ClassDescriptor of(String name) {
		ClassDescriptor cd = new ClassDescriptor();
		cd.setName(name);
		return cd;
	}

	public void setFlag(Flag f) {
		if (flags == null) {
			flags = new TreeSet<>();
		}
		flags.add(f);
	}
	
	public void setFlags(Set<Flag> flags) {
		for (Flag flag: flags) {
			this.setFlag(flag);
		}
	}
	
	public void unsetFlag(Flag f) {
		if (flags == null) {
			return;
		}
		flags.remove(f);
	}

	public void addMethodDescriptor(MethodDescriptor methodDescriptor) {
		if (methods == null) {
			methods = new ArrayList<>();
		}
		methods.add(methodDescriptor);
	}

	private void addMethodDescriptors(List<MethodDescriptor> methodDescriptors) {
		for (MethodDescriptor md: methodDescriptors) {
			addMethodDescriptor(md);
		}
	}

	public void addFieldDescriptor(FieldDescriptor fieldDescriptor) {
		if (fields == null) {
			fields = new ArrayList<>();
		}
		fields.add(fieldDescriptor);
	}
	
	private void addFieldDescriptors(List<FieldDescriptor> fieldDescriptors) {
		for (FieldDescriptor fieldDescriptor: fieldDescriptors) {
			addFieldDescriptor(fieldDescriptor);
		}
	}

	/**
	 * Used when new data is to be added to an already existing class descriptor
	 * (additional members, flag settings).
	 * 
	 * @param cd the ClassDescriptor to merge into this one
	 */
	public void merge(ClassDescriptor cd) {
		if (cd.getFlags()!= null) {
			for (Flag flag : cd.getFlags()) {
				this.setFlag(flag);
			}
		}
		if (cd.getFields() != null) {
			if (fields == null) {
				addFieldDescriptors(cd.getFields());
			} else {
				for (FieldDescriptor fd : cd.getFields()) {
					FieldDescriptor existingFieldDescriptor = null;
					for (FieldDescriptor fieldDescriptor : getFields()) {
						if (fieldDescriptor.getName().equals(fd.getName())) {
							existingFieldDescriptor = fieldDescriptor;
							break;
						}
					}
					if (existingFieldDescriptor != null) {
						existingFieldDescriptor.merge(fd);
					} else {
						addFieldDescriptor(fd);
					}
				}
			}
		}
		if (cd.getMethods() != null) {
			for (MethodDescriptor methodDescriptor : cd.getMethods()) {
				if (!containsMethodDescriptor(methodDescriptor)) {
					addMethodDescriptor(methodDescriptor);
				}
			}
		}
	}

	private boolean containsMethodDescriptor(MethodDescriptor methodDescriptor) {
		return methods == null?false:methods.contains(methodDescriptor);
	}

	public MethodDescriptor getMethodDescriptor(String name, String... parameterTypes) {
		if (methods != null) {
			MethodDescriptor toFind = MethodDescriptor.of(name, parameterTypes);
			for (MethodDescriptor md : methods) {
				if (md.equals(toFind)) {
					return md;
				}
			}
		}
		return null;
	}

	public boolean contains(MethodDescriptor toFind) {
		if (methods != null) {
			for (MethodDescriptor md : methods) {
				if (md.equals(toFind)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean contains(FieldDescriptor toFind) {
		if (fields != null) {
			for (FieldDescriptor fd : fields) {
				if (fd.equals(toFind)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean hasConstructors() {
		if (methods != null) {
			for (MethodDescriptor md : methods) {
				if (md.getName().equals("<init>")) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean hasMethods() {
		if (methods != null) {
			for (MethodDescriptor md : methods) {
				if (!md.getName().equals("<init>")) {
					return true;
				}
			}
		}
		return false;
	}

	public String toJsonString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"name\":").append("\""+name+"\"");
		if (hasConstructors()) {
			sb.append(",\"allDeclaredConstructors\":true");
		}
		if (hasMethods()) {
			sb.append(",\"allDeclaredMethods\":true");
		}
		sb.append("}");
		return sb.toString();
	}

	public ClassDescriptor subtract(ClassDescriptor toSubtract) {
		Set<Flag> resultFlags = new HashSet<>();
		if (this.getFlags()!=null) {
			resultFlags.addAll(this.getFlags());
		}
		if (toSubtract.getFlags()!=null) {
			resultFlags.removeAll(toSubtract.getFlags());
		}

		List<MethodDescriptor> resultMethods = new ArrayList<>();
		if (this.getMethods()!=null) {
			resultMethods.addAll(this.getMethods());
		}
		if (toSubtract.getMethods()!=null) {
			resultMethods.removeAll(toSubtract.getMethods());
		}
		
		List<FieldDescriptor> resultFields = new ArrayList<>();
		if (this.getFields()!=null) {
			resultFields.addAll(this.getFields());
		}
		if (toSubtract.getFields()!=null) {
			resultFields.removeAll(toSubtract.getFields());
		}
		ClassDescriptor result = ClassDescriptor.of(this.getName());
		if (!resultFlags.isEmpty()) {
			result.setFlags(resultFlags);
		}
		if (!resultMethods.isEmpty()) {
			result.addMethodDescriptors(resultMethods);
		}
		if (!resultFields.isEmpty()) {
			result.addFieldDescriptors(resultFields);
		}
		return result;
	}

	public ClassDescriptor copy() {
		List<FieldDescriptor> fieldsCopy = null;
		if (fields != null) {
			fieldsCopy = new ArrayList<>();
			for (FieldDescriptor fd: fields) {
				fieldsCopy.add(fd.copy());
			}
		}
		List<MethodDescriptor> methodsCopy = null;
		if (methods != null) {
			methodsCopy = new ArrayList<>();
			for (MethodDescriptor md: methods) {
				methodsCopy.add(md.copy());
			}
		}
		Set<Flag> flagsCopy = null;
		if (flags != null) {
			flagsCopy = new HashSet<>();
			flagsCopy.addAll(flags);
		}
		return new ClassDescriptor(name, fieldsCopy, methodsCopy, flagsCopy);
	}

	public FieldDescriptor getFieldDescriptorNamed(String name) {
		if (fields != null) {
			for (FieldDescriptor fd: fields) {
				if (fd.getName().equals(name)) {
					return fd;
				}
			}
		}
		return null;
	}

}
