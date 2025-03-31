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

/**
 * 表示一个模型 function call 处理器。接口的实现通过在模型中注册，在触发 function call 的提示词时调用该接口的实现类。
 *
 * @author Christian Tzolov
 */
public interface FunctionCallback {

    /**
     * @return 返回在  AI 模型内唯一函数名称。
     */
    public String getName();

    /**
     * @return 返回函数的描述。该描述用于 AI 模型判断是否应调用该函数。
     */
    public String getDescription();

    /**
     * @return 返回函数输入类型的 JSON 模式。
     */
    public String getInputTypeSchema();

    /**
     * 当 AI 模型检测到并触发 function call 时，将调用此方法。AI 模型负责按照预先配置的 JSON 模式格式传递函数参数。
     *
     * @param functionInput 作为 JSON 字符串的函数参数将传递给函数。参数定义是 JSON 模式，通常注册到模型中。
     * @return 包含 function call 响应的字符串。
     */
    public String call(String functionInput);

}