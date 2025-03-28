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
 * 该接口提供访问 AI 模型生成的主要输出和与该输出结果相关的元数据的方法。它旨在提供一种标准化、全面的方式来处理和解释 AI 模型生成的输出，以满足多样化的 AI 应用和使用案例。
 *
 * @param <T> AI 模型生成的输出
 * @author Mark Pollack
 * @since 0.8.0
 */
public interface ModelResult<T> {

    /**
     * 检索 AI 模型生成的输出
     *
     * @return AI 模型生成的输出
     */
    T getOutput();

    /**
     * 检索和 AI 模型生成的输出结果相关的元数据
     * Retrieves the metadata associated with the result of an AI model.
     *
     * @return 输出结果相关的元数据
     */
    ResultMetadata getMetadata();

}
