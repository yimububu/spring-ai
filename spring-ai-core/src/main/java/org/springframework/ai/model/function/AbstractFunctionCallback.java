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

import java.util.function.Function;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.util.Assert;

/**
 * {@link FunctionCallback} 的抽象实现类，用于与 AI 模型的 function calling protocol 交互，同时{@link Function}封装了与第三方service/function的交互逻辑
 * <p>
 * 实现{@code O apply(I request) } 方法以完成与第三方service/function的交互
 * <p>
 * {@link #responseConverter}函数负责将第三方函数的输出类型转换为 LLM（大语言模型）所期望的字符串格式，默认实现会在发送到 AI 模型之前将输出转换为 String
 *
 * @param <I> the 3rd party service input type.
 * @param <O> the 3rd party service output type.
 * @author Christian Tzolov
 */
abstract class AbstractFunctionCallback<I, O> implements Function<I, O>, FunctionCallback {

    private final String name;

    private final String description;

    private final Class<I> inputType;

    private final String inputTypeSchema;

    private final ObjectMapper objectMapper;

    private final Function<O, String> responseConverter;

    /**
     * Constructs a new {@link AbstractFunctionCallback} with the given name, description,
     * input type and default object mapper.
     *
     * @param name              Function name. Should be unique within the ChatModel's function
     *                          registry.
     * @param description       Function description. Used as a "system prompt" by the model to
     *                          decide if the function should be called.
     * @param inputTypeSchema   Used to compute, the argument's Schema (such as JSON Schema
     *                          or OpenAPI Schema)required by the Model's function calling protocol.
     * @param inputType         Used to compute, the argument's JSON schema required by the
     *                          Model's function calling protocol.
     * @param responseConverter Used to convert the function's output type to a string.
     * @param objectMapper      Used to convert the function's input and output types to and
     *                          from JSON.
     */
    protected AbstractFunctionCallback(String name, String description, String inputTypeSchema, Class<I> inputType,
                                       Function<O, String> responseConverter, ObjectMapper objectMapper) {
        Assert.notNull(name, "Name must not be null");
        Assert.notNull(description, "Description must not be null");
        Assert.notNull(inputType, "InputType must not be null");
        Assert.notNull(inputTypeSchema, "InputTypeSchema must not be null");
        Assert.notNull(responseConverter, "ResponseConverter must not be null");
        Assert.notNull(objectMapper, "ObjectMapper must not be null");
        this.name = name;
        this.description = description;
        this.inputType = inputType;
        this.inputTypeSchema = inputTypeSchema;
        this.responseConverter = responseConverter;
        this.objectMapper = objectMapper;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getInputTypeSchema() {
        return this.inputTypeSchema;
    }

    @Override
    public String call(String functionArguments) {

        // Convert the tool calls JSON arguments into a Java function request object.
        I request = fromJson(functionArguments, inputType);

        // extend conversation with function response.
        return this.andThen(this.responseConverter).apply(request);
    }

    private <T> T fromJson(String json, Class<T> targetClass) {
        try {
            return this.objectMapper.readValue(json, targetClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((inputType == null) ? 0 : inputType.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AbstractFunctionCallback other = (AbstractFunctionCallback) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (inputType == null) {
            if (other.inputType != null)
                return false;
        } else if (!inputType.equals(other.inputType))
            return false;
        return true;
    }

}
