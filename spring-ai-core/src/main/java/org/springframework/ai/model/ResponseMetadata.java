/*
 * Copyright 2023 - 2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.ai.model;

import java.util.Map;

/**
 * 该接口代表和 AI 模型响应相关的元数据。这个接口旨在为从 AI 模型生成的响应提供额外信息，包括处理细节和特定于某个模型的数据。该接口作为核心领域内的 value object，增强了对 AI 模型响应的理解和管理，使其在各种应用中更加高效。
 *
 * @author Mark Pollack
 * @since 0.8.0
 */
public interface ResponseMetadata extends Map<String, Object> {

}
