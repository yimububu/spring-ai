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
package org.springframework.ai.document.id;

/**
 * IdGenerator 接口用于生成唯一文档 ID。
 *
 * @author Aliakbar Jafarpour
 * @author Christian Tzolov
 */
public interface IdGenerator {

    /**
     * 为给定内容生成唯一的 ID。注意：某些生成器（例如随机生成器）可能不会依赖于或使用内容参数。
     *
     * @param contents 需要生成 ID 的内容。
     * @return the generated ID.
     */
    String generateId(Object... contents);

}
