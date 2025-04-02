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
package org.springframework.ai.vectorstore;

import java.util.List;
import java.util.Optional;

import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentWriter;

/**
 * {@code VectorStore}接口定义了在向量数据库中管理和查询文档的操作。它扩展了 {@link DocumentWriter}以支持文档写入操作。向量数据库专为 AI 应用优化，基于数据的向量表示执行相似性搜索，而不是精确匹配。该接口允许添加、删除文档，并基于与给定查询的相似性进行搜索。
 */
public interface VectorStore extends DocumentWriter {

    /**
     * 将{@link Document}列表添加到向量存储中。
     *
     * @param documents 要存储的文档列表。如果底层提供程序检查重复 ID，则会抛出异常。
     */
    void add(List<Document> documents);

    @Override
    default void accept(List<Document> documents) {
        add(documents);
    }

    /**
     * 从向量存储中删除文档。
     *
     * @param idList 要移除的文档 ID 列表。
     * @return
     */
    Optional<Boolean> delete(List<String> idList);

    /**
     * 根据查询嵌入的相似性和元数据过滤条件来检索文档，以精确获取符合请求条件的最近邻结果数量。
     *
     * @param request 设置搜索请求参数，例如查询文本、topK（返回的最近邻结果数量）、相似性阈值以及元数据过滤表达式。
     * @return 返回符合查询请求条件的文档。
     */
    List<Document> similaritySearch(SearchRequest request);

    /**
     * 使用默认的 {@link SearchRequest} 搜索条件，通过查询嵌入相似性来检索文档。
     *
     * @param query 用于嵌入相似性比较的文本。
     * @return 返回一组具有与查询文本嵌入相似的嵌入的文档列表。
     */
    default List<Document> similaritySearch(String query) {
        return this.similaritySearch(SearchRequest.query(query));
    }

}
