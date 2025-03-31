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

import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.util.MimeType;

import java.io.IOException;
import java.net.URL;

/**
 * Media 类表示消息中媒体附件的数据和元数据。它包括 MIME 类型和原始数据。
 * <p>
 * 此类作为 UserMessage 类构造函数中的一个参数。
 *
 * @author Christian Tzolov
 * @since 0.8.1
 */
public class Media {

    private final MimeType mimeType;

    private final Object data;

    /**
     * The Media class represents the data and metadata of a media attachment in a
     * message. It consists of a MIME type and the raw data.
     * <p>
     * This class is used as a parameter in the constructor of the UserMessage class.
     *
     * @deprecated This constructor is deprecated since version 1.0.0 M1 and will be
     * removed in a future release.
     */
    @Deprecated(since = "1.0.0 M1", forRemoval = true)
    public Media(MimeType mimeType, Object data) {
        Assert.notNull(mimeType, "MimeType must not be null");
        this.mimeType = mimeType;
        this.data = data;
    }

    public Media(MimeType mimeType, URL url) {
        Assert.notNull(mimeType, "MimeType must not be null");
        this.mimeType = mimeType;
        this.data = url.toString();
    }

    public Media(MimeType mimeType, Resource resource) {
        Assert.notNull(mimeType, "MimeType must not be null");
        this.mimeType = mimeType;
        try {
            this.data = resource.getContentAsByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public MimeType getMimeType() {
        return this.mimeType;
    }

    /**
     * Get the media data object
     *
     * @return a java.net.URL.toString() or a byte[]
     */
    public Object getData() {
        return this.data;
    }

}
