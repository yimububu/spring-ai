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
/**
 * 提供了一组用于构建通用 API 接口和类，以便与各种 AI 模型交互。包括用于处理 AI 模型调用、请求、响应、结果及相关元数据的接口，旨在提供一个灵活且可扩展的框架，以适应不同类型的 AI 模型交互，抽象化模型调用和结果处理的复杂性。通过泛型的使用，该 API 具备更强的适应性，能够支持多种 AI 模型，确保在不同 AI 场景中的广泛适用性。
 */
package org.springframework.ai.model;