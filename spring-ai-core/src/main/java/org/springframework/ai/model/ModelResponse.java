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

import java.util.List;

/**
 * 该接口代表从 AI 模型接收到的响应。该接口提供访问 AI 模型生成的主要结果或结果列表以及响应元数据的方法。它提供一种封装和管理 AI 模型输出的标准方法，确保可以轻松的检索和处理 A I模型生成的信息。
 *
 * @param <T> AI 模型响应的结果
 * @author Mark Pollack
 * @since 0.8.0
 */
public interface ModelResponse<T extends ModelResult<?>> {

    /**
     * 检索 AI 模型生成的结果
     *
     * @return AI 模型生成的结果
     */
    T getResult();

    /**
     * 检索 AI 模型生成输出的列表
     * Retrieves the list of generated outputs by the AI model.
     *
     * @return 生成输出的列表
     */
    List<T> getResults();

    /**
     * 检索与 AI 模型的响应相关的响应元数据
     *
     * @return 响应元数据
     */
    ResponseMetadata getMetadata();

}
