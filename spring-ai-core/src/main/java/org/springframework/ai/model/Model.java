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
 * Model 接口提供了一个通用 API，用于调用 AI 模型。它旨在通过抽象请求发送和响应接收的过程来处理与各种类型 AI 模型的交互。该接口使用 Java 泛型来适应不同类型的请求和响应，从而增强在不同 AI 模型实现中的灵活性和适应性。
 *
 * @param <TReq> AI 模型的请求类型
 * @param <TRes> AI 模型的响应类型
 * @author Mark Pollack
 * @since 0.8.0
 */
public interface Model<TReq extends ModelRequest<?>, TRes extends ModelResponse<?>> {

    /**
     * 执行对 AI 模型的方法调用。
     *
     * @param request 要发送到 AI 模型的请求对象。
     * @return 来自 AI 模型的响应。
     */
    TRes call(TReq request);

}
