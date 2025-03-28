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
 * 该接口代表发向 AI 模型的请求。这个接口封装了与 AI 模型进行交互所需要的必要信息，包括指令或者输入以及额外的 AI 模型选项。它提供了一种向 AI 模型发送请求的通用方法，以确保包含所有必要的细节并且可以轻松管理。
 *
 * @param <T> AI 模型所需的指令或输入的类型
 * @author Mark Pollack
 * @since 0.8.0
 */
public interface ModelRequest<T> {

    /**
     * 检索 AI 模型所需的指令或输入
     *
     * @return AI 模型所需的指令或输入
     */
    T getInstructions(); // 必须输入

    /**
     * 检索用于 AI 模型交互的可自定义的选项。
     *
     * @return 用于 AI 模型交互的自定义选项
     */
    ModelOptions getOptions();

}