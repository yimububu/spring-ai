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
package org.springframework.ai.embedding;

import org.springframework.ai.document.Document;
import org.springframework.ai.model.Model;
import org.springframework.util.Assert;

import java.util.List;

/**
 * EmbeddingModel 是一个用于嵌入模型的通用接口。
 */
public interface EmbeddingModel extends Model<EmbeddingRequest, EmbeddingResponse> {

    /**
     * 嵌入模型访问统一抽象接口，不同的大模型实现该方法完成各自的嵌入逻辑
     */
    @Override
    EmbeddingResponse call(EmbeddingRequest request);

    /**
     * 将给定的文本嵌入到向量中。
     *
     * @param text 要进行嵌入的文本。
     * @return 嵌入后的向量。
     */
    default List<Double> embed(String text) {
        Assert.notNull(text, "Text must not be null");
        return this.embed(List.of(text)).iterator().next();
    }

    /**
     * 将给定文档的内容嵌入为向量。
     *
     * @param document 要进行嵌入的文档。
     * @return 嵌入后的向量。
     */
    List<Double> embed(Document document);

    /**
     * 将一批文本嵌入为向量。
     *
     * @param texts 要进行嵌入的文档列表。
     * @return 嵌入后的向量列表
     */
    default List<List<Double>> embed(List<String> texts) {
        Assert.notNull(texts, "Texts must not be null");
        return this.call(new EmbeddingRequest(texts, EmbeddingOptions.EMPTY))
                .getResults()
                .stream()
                .map(Embedding::getOutput)
                .toList();
    }

    /**
     * 将一批文本嵌入到向量中，并返回嵌入响应 {@link EmbeddingResponse}.
     *
     * @param texts 要进行嵌入的文档列表。
     * @return 嵌入响应对象。
     */
    default EmbeddingResponse embedForResponse(List<String> texts) {
        Assert.notNull(texts, "Texts must not be null");
        return this.call(new EmbeddingRequest(texts, EmbeddingOptions.EMPTY));
    }

    /**
     * @return 嵌入向量的维度数量，具体取决于生成模型。
     */
    default int dimensions() {
        return embed("Test String").size();
    }

}
