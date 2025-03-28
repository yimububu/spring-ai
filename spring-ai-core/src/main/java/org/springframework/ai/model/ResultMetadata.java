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

/**
 * 该接口代表和 AI 模型结果相关的元数据。该接口专注于为 AI 模型生成的结果提供额外的 context 和 insights。它可能包括计算时间、模型版本和其他相关的细节信息，以增强对各种应用中的 AI 模型输出的理解和管理。
 *
 * @author Mark Pollack
 * @since 0.8.0
 */
public interface ResultMetadata {

}
