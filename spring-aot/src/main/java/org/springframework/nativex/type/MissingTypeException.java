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

package org.springframework.nativex.type;

/**
 * 
 * @author Andy Clement
 */
public class MissingTypeException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String typename;

	public MissingTypeException(String slashedTypeName) {
		this.typename = slashedTypeName;
	}

	@Override
	public String getMessage() {
		return "Unable to find class file for " + typename;
	}
}