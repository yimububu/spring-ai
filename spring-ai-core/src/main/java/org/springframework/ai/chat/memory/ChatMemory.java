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

package org.springframework.ai.chat.memory;

import java.util.List;

import org.springframework.ai.chat.messages.Message;

/**
 * ChatMemory 接口表示聊天对话历史的存储。它提供了向对话中添加消息、从对话中检索消息以及清除对话历史的功能。
 *
 * @author Christian Tzolov
 * @since 1.0.0 M1
 */
public interface ChatMemory {

    default void add(String conversationId, Message message) {
        this.add(conversationId, List.of(message));
    }

    void add(String conversationId, List<Message> messages);

    List<Message> get(String conversationId, int lastN);

    void clear(String conversationId);

}