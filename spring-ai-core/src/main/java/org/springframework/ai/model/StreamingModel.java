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

import reactor.core.publisher.Flux;

/**
 * StreamingModel 接口提供了一个通用的 API，用于调用具有流式响应的 AI 模型。它抽象了发送请求和接收流式响应的过程。该接口使用 Java 泛型来适应不同类型的请求和响应，增强了跨不同 AI 模型实现的灵活性和适应性。
 *
 * @param <TReq>      AI 模型的请求类型
 * @param <TResChunk> 来自 AI 模型的流式响应中的单个项目的类型。
 * @author Christian Tzolov
 * @since 0.8.0
 */
public interface StreamingModel<TReq extends ModelRequest<?>, TResChunk extends ModelResponse<?>> {

    /**
     * 执行对 AI 模型的方法调用。
     *
     * @param request 要发送到 AI 模型的请求对象。
     * @return 来自 AI 模型的流式响应。
     */
    Flux<TResChunk> stream(TReq request);

}
