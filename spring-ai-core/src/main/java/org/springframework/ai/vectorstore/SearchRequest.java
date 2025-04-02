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

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.ai.vectorstore.filter.FilterExpressionTextParser;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * 相似性搜索请求构造器。使用 {@link #query(String)}、{@link #defaults()} 或 {@link #from(SearchRequest)} 工厂方法创建新的 {@link SearchRequest} 实例，然后使用 with 方法修改默认值。
 *
 * @author Christian Tzolov
 */
public class SearchRequest {

    /**
     * 查询返回相似度的阙值，只有大于该阙值的文档才返回，默认为0.0
     * Similarity threshold that accepts all search scores. A threshold value of 0.0 means
     * any similarity is accepted or disable the similarity threshold filtering. A
     * threshold value of 1.0 means an exact match is required.
     */
    public static final double SIMILARITY_THRESHOLD_ACCEPT_ALL = 0.0;

    /**
     * 指定查询返回最相似的前 K 个，默认值为4，我们可以在查询前指定
     */
    public static final int DEFAULT_TOP_K = 4;

    /**
     * 查询文本
     */
    public String query;

    private int topK = DEFAULT_TOP_K;

    private double similarityThreshold = SIMILARITY_THRESHOLD_ACCEPT_ALL;

    private Filter.Expression filterExpression;

    private SearchRequest(String query) {
        this.query = query;
    }

    /**
     * Create a new {@link SearchRequest} builder instance with specified embedding query
     * string.
     *
     * @param query Text to use for embedding similarity comparison.
     * @return Returns new {@link SearchRequest} builder instance.
     */
    public static SearchRequest query(String query) {
        Assert.notNull(query, "Query can not be null.");
        return new SearchRequest(query);
    }

    /**
     * Create a new {@link SearchRequest} builder instance with an empty embedding query
     * string. Use the {@link #withQuery(String query)} to set/update the embedding query
     * text.
     *
     * @return Returns new {@link SearchRequest} builder instance.
     */
    public static SearchRequest defaults() {
        return new SearchRequest("");
    }

    /**
     * Copy an existing {@link SearchRequest} instance.
     *
     * @param originalSearchRequest {@link SearchRequest} instance to copy.
     * @return Returns new {@link SearchRequest} builder instance.
     */
    public static SearchRequest from(SearchRequest originalSearchRequest) {
        return new SearchRequest(originalSearchRequest.getQuery()).withTopK(originalSearchRequest.getTopK())
                .withSimilarityThreshold(originalSearchRequest.getSimilarityThreshold())
                .withFilterExpression(originalSearchRequest.getFilterExpression());
    }

    /**
     * @param query Text to use for embedding similarity comparison.
     * @return this builder.
     */
    public SearchRequest withQuery(String query) {
        Assert.notNull(query, "Query can not be null.");
        this.query = query;
        return this;
    }

    /**
     * @param topK the top 'k' similar results to return.
     * @return this builder.
     */
    public SearchRequest withTopK(int topK) {
        Assert.isTrue(topK >= 0, "TopK should be positive.");
        this.topK = topK;
        return this;
    }

    /**
     * Similarity threshold score to filter the search response by. Only documents with
     * similarity score equal or greater than the 'threshold' will be returned. Note that
     * this is a post-processing step performed on the client not the server side. A
     * threshold value of 0.0 means any similarity is accepted or disable the similarity
     * threshold filtering. A threshold value of 1.0 means an exact match is required.
     *
     * @param threshold The lower bound of the similarity score.
     * @return this builder.
     */
    public SearchRequest withSimilarityThreshold(double threshold) {
        Assert.isTrue(threshold >= 0 && threshold <= 1, "Similarity threshold must be in [0,1] range.");
        this.similarityThreshold = threshold;
        return this;
    }

    /**
     * Sets disables the similarity threshold by setting it to 0.0 - all results are
     * accepted.
     *
     * @return this builder.
     */
    public SearchRequest withSimilarityThresholdAll() {
        return withSimilarityThreshold(SIMILARITY_THRESHOLD_ACCEPT_ALL);
    }

    /**
     * Retrieves documents by query embedding similarity and matching the filters. Value
     * of 'null' means that no metadata filters will be applied to the search.
     * <p>
     * For example if the {@link Document#getMetadata()} schema is:
     *
     * <pre>{@code
     * &#123;
     * "country": <Text>,
     * "city": <Text>,
     * "year": <Number>,
     * "price": <Decimal>,
     * "isActive": <Boolean>
     * &#125;
     * }</pre>
     * <p>
     * you can constrain the search result to only UK countries with isActive=true and
     * year equal or greater 2020. You can build this such metadata filter
     * programmatically like this:
     *
     * <pre>{@code
     * var exp = new Filter.Expression(AND,
     * 		new Expression(EQ, new Key("country"), new Value("UK")),
     * 		new Expression(AND,
     * 				new Expression(GTE, new Key("year"), new Value(2020)),
     * 				new Expression(EQ, new Key("isActive"), new Value(true))));
     * }</pre>
     * <p>
     * The {@link Filter.Expression} is portable across all vector stores.<br/>
     * <p>
     * <p>
     * The {@link FilterExpressionBuilder} is a DSL creating expressions programmatically:
     *
     * <pre>{@code
     * var b = new FilterExpressionBuilder();
     * var exp = b.and(
     * 		b.eq("country", "UK"),
     * 		b.and(
     * 			b.gte("year", 2020),
     * 			b.eq("isActive", true)));
     * }</pre>
     * <p>
     * The {@link FilterExpressionTextParser} converts textual, SQL like filter expression
     * language into {@link Filter.Expression}:
     *
     * <pre>{@code
     * var parser = new FilterExpressionTextParser();
     * var exp = parser.parse("country == 'UK' && isActive == true && year >=2020");
     * }</pre>
     *
     * @param expression {@link Filter.Expression} instance used to define the metadata
     *                   filter criteria. The 'null' value stands for no expression filters.
     * @return this builder.
     */
    public SearchRequest withFilterExpression(Filter.Expression expression) {
        this.filterExpression = expression;
        return this;
    }

    /**
     * Document metadata filter expression. For example if your
     * {@link Document#getMetadata()} has a schema like:
     *
     * <pre>{@code
     * &#123;
     * "country": <Text>,
     * "city": <Text>,
     * "year": <Number>,
     * "price": <Decimal>,
     * "isActive": <Boolean>
     * &#125;
     * }</pre>
     * <p>
     * then you can constrain the search result with metadata filter expressions like:
     *
     * <pre>{@code
     * country == 'UK' && year >= 2020 && isActive == true
     * Or
     * country == 'BG' && (city NOT IN ['Sofia', 'Plovdiv'] || price < 134.34)
     * }</pre>
     * <p>
     * This ensures that the response contains only embeddings that match the specified
     * filer criteria. <br/>
     * <p>
     * The declarative, SQL like, filter syntax is portable across all vector stores
     * supporting the filter search feature.<br/>
     * <p>
     * The {@link FilterExpressionTextParser} is used to convert the text filter
     * expression into {@link Filter.Expression}.
     *
     * @param textExpression declarative, portable, SQL like, metadata filter syntax. The
     *                       'null' value stands for no expression filters.
     * @return this.builder
     */
    public SearchRequest withFilterExpression(String textExpression) {
        this.filterExpression = (textExpression != null) ? new FilterExpressionTextParser().parse(textExpression)
                : null;
        return this;
    }

    public String getQuery() {
        return query;
    }

    public int getTopK() {
        return topK;
    }

    public double getSimilarityThreshold() {
        return similarityThreshold;
    }

    public Filter.Expression getFilterExpression() {
        return filterExpression;
    }

    public boolean hasFilterExpression() {
        return this.filterExpression != null;
    }

    @Override
    public String toString() {
        return "SearchRequest{" + "query='" + query + '\'' + ", topK=" + topK + ", similarityThreshold="
                + similarityThreshold + ", filterExpression=" + filterExpression + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        SearchRequest that = (SearchRequest) o;
        return topK == that.topK && Double.compare(that.similarityThreshold, similarityThreshold) == 0
                && Objects.equals(query, that.query) && Objects.equals(filterExpression, that.filterExpression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(query, topK, similarityThreshold, filterExpression);
    }

}