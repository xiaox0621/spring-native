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

import java.util.Collections;
import java.util.List;

/**
 * 
 * @author Andy Clement
 */
public interface NativeConfiguration {
	
	default List<HintDeclaration> computeHints(TypeSystem typeSystem) { return Collections.emptyList(); }
	
	/**
	 * Implementing this method enables hints on the @NativeConfiguration implementation to be
	 * conditional on some programmatic test.
	 * 
	 * @param typeSystem a type system which can be used to query types available in the closed world
	 * @return {@code true} if the hints on this configuration are valid
	 */
	default boolean isValid(TypeSystem typeSystem) { return true; }
}
