/*
 * Copyright 2023-2024 the original author or authors.
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

package org.springframework.ai.embedding;

import java.util.List;

import org.springframework.ai.model.ModelRequest;
import org.springframework.lang.Nullable;

/**
 * Request to embed a list of input instructions.
 *
 * @author Christian Tzolov
 */
public class EmbeddingRequest implements ModelRequest<List<String>> {

	private final List<String> inputs;

	@Nullable
	private final EmbeddingOptions options;

	public EmbeddingRequest(List<String> inputs, @Nullable EmbeddingOptions options) {
		this.inputs = inputs;
		this.options = options;
	}

	@Override
	public List<String> getInstructions() {
		return this.inputs;
	}

	@Override
	@Nullable
	public EmbeddingOptions getOptions() {
		return this.options;
	}

}
