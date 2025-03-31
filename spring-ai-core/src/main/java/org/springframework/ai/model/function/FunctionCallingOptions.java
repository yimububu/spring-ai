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
package org.springframework.ai.model.function;

import java.util.List;
import java.util.Set;

/**
 * @author Christian Tzolov
 */
public interface FunctionCallingOptions {

    /**
     * 将 Function Callbacks 注册到 ChatModel。对于 Prompt 选项，函数回调会在 prompt 执行期间自动启用。对于默认选项，函数回调会被注册但默认处于禁用状态。你需要使用 "functions" 属性，在 ChatModel 注册表中列出要用于聊天补全请求的函数名称。
     *
     * @return 返回要注册到 ChatModel 的函数回调（Function Callbacks）。
     */
    List<FunctionCallback> getFunctionCallbacks();

    /**
     * 设置要注册到 ChatModel 的函数回调（Function Callbacks）。
     *
     * @param functionCallbacks 要注册到 ChatModel 的函数回调（Function Callbacks）。
     */
    void setFunctionCallbacks(List<FunctionCallback> functionCallbacks);

    /**
     * @return 从 ChatModel 注册表中获取的函数名称列表，这些函数将在下一个聊天补全请求中使用。
     */
    Set<String> getFunctions();

    /**
     * 设置从 ChatModel 注册表中获取的函数名称列表，这些函数将在下一个聊天补全请求中使用。
     *
     * @param functions 从 ChatModel 注册表中获取的函数名称列表，这些函数将在下一个聊天补全请求中使用。
     */
    void setFunctions(Set<String> functions);

    /**
     * @return 返回 FunctionCallingOptionsBuilder 以创建 FunctionCallingOptions 的新实例。
     */
    public static FunctionCallingOptionsBuilder builder() {
        return new FunctionCallingOptionsBuilder();
    }

}