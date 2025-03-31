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
package org.springframework.ai.chat.messages;

import org.springframework.ai.model.Content;

/**
 * Message 接口表示可以在聊天应用中发送或接收的消息。消息可以包含内容、媒体附件、属性和消息类型。
 *
 * @see Media
 * @see MessageType
 */
public interface Message extends Content {

    MessageType getMessageType();

}
