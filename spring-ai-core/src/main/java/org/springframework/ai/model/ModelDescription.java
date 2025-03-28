/*
 * Copyright 2024-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.ai.model;

/**
 * 模型描述接口
 *
 * @author Christian Tzolov
 */
public interface ModelDescription {

    /**
     * 获取模型的名称
     */
    String getModelName();

    /**
     * 获取模型的描述
     */
    default String getDescription() {
        return "";
    }

    /**
     * 获取模型的版本
     */
    default String getVersion() {
        return "";
    }

    /**
     * 获取模型的上下文长度
     */
    default int getContextLength() {
        return -1;
    }

}
