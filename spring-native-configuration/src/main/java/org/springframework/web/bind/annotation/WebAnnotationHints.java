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

package org.springframework.web.bind.annotation;

import org.springframework.http.HttpStatus;
import org.springframework.nativex.hint.NativeHint;
import org.springframework.nativex.type.NativeConfiguration;
import org.springframework.nativex.hint.TypeHint;
import org.springframework.nativex.hint.AccessBits;
import org.springframework.stereotype.Controller;

// TODO do these need to be more conditional? The triggers are either of the web stack configurations - do those
// autoconfigs share a common ancestor that these could trigger off? Although, of course these will only be exposed
// if they are on the classpath - why would you have the web jar on the classpath if not using these?
@NativeHint(types = {
		// TODO What about some way to say "all annotations in this package"
		@TypeHint(types= {
				ExceptionHandler.class,
				RequestMethod[].class,
				ModelAttribute.class,
				InitBinder.class,
				RequestMethod.class,
				ResponseBody.class,
				RequestBody.class,
				RequestHeader.class,
				Controller.class,
				RestController.class,
				RequestParam.class,
				PathVariable.class,
				Mapping.class, RequestMapping.class, GetMapping.class, PostMapping.class, PutMapping.class, DeleteMapping.class, PatchMapping.class,
				ResponseStatus.class, HttpStatus.class
		}, access = AccessBits.CLASS | AccessBits.DECLARED_METHODS)
	})
public class WebAnnotationHints implements NativeConfiguration {
}
