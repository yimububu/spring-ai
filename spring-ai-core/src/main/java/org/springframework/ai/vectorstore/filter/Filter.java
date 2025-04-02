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
package org.springframework.ai.vectorstore.filter;

/**
 * 便携式运行时生成器用于元数据过滤表达式。这个通用生成器用于定义与存储无关的过滤表达式，这些表达式随后可以转换为特定于向量数据库的本地表达式。
 * <p>
 * 该表达式生成器支持常量比较{@code (e.g. ==, !=, <, <=, >, >=) }、IN/非IN检查以及AND和OR组合多个表达式。
 * <p>
 * 例如：
 *
 * <pre>{@code
 * // 1: country == "BG"
 * new Expression(EQ, new Key("country"), new Value("BG"));
 *
 * // 2: genre == "drama" AND year >= 2020
 * new Expression(AND, new Expression(EQ, new Key("genre"), new Value("drama")),
 * 		new Expression(GTE, new Key("year"), new Value(2020)));
 *
 * // 3: genre in ["comedy", "documentary", "drama"]
 * new Expression(IN, new Key("genre"), new Value(List.of("comedy", "documentary", "drama")));
 *
 * // 4: year >= 2020 OR country == "BG" AND city != "Sofia"
 * new Expression(OR, new Expression(GTE, new Key("year"), new Value(2020)),
 * 		new Expression(AND, new Expression(EQ, new Key("country"), new Value("BG")),
 * 				new Expression(NE, new Key("city"), new Value("Sofia"))));
 *
 * // 5: (year >= 2020 OR country == "BG") AND city NIN ["Sofia", "Plovdiv"]
 * new Expression(AND,
 * 		new Group(new Expression(OR, new Expression(EQ, new Key("country"), new Value("BG")),
 * 				new Expression(GTE, new Key("year"), new Value(2020)))),
 * 		new Expression(NIN, new Key("city"), new Value(List.of("Sofia", "Varna"))));
 *
 * // 6: isOpen == true AND year >= 2020 AND country IN ["BG", "NL", "US"]
 * new Expression(AND, new Expression(EQ, new Key("isOpen"), new Value(true)),
 * 		new Expression(AND, new Expression(GTE, new Key("year"), new Value(2020)),
 * 				new Expression(IN, new Key("country"), new Value(List.of("BG", "NL", "US")))));
 *
 * }</pre>
 * <p>
 * <p>
 * 通常，你不会手动创建表达式，而是使用{@link FilterExpressionBuilder} DSL 或 {@link FilterExpressionTextParser} 来解析通用文本表达式。
 *
 * @author Christian Tzolov
 */
public class Filter {

    /**
     * 标记接口，表示支持的表达式类型：{@link Key}、{@link Value}、{@link Expression} 和 {@link Group}。
     */
    public interface Operand {

    }

    /**
     * 表示表达式键的字符串标识符（例如，在 country == "NL" 表达式中的 country）。
     */
    public record Key(String key) implements Operand {
    }

    /**
     * 表示表达式的常量值或常量数组。支持数字、布尔值和字符串数据类型。
     */
    public record Value(Object value) implements Operand {
    }

    /**
     * Filter expression operations. <br/>
     * <p>
     * - EQ, NE, GT, GTE, LT, LTE operations supports "Key ExprType Value"
     * expressions.<br/>
     * <p>
     * - AND, OR are binary operations that support "(Expression|Group) ExprType
     * (Expression|Group)" expressions. <br/>
     * <p>
     * - IN, NIN support "Key (IN|NIN) ArrayValue" expression. <br/>
     */
    public enum ExpressionType {

        AND, OR, EQ, NE, GT, GTE, LT, LTE, IN, NIN, NOT

    }

    /**
     * 表示和过滤布尔表达式的三元组，形式为左类型右。
     * Triple that represents and filter boolean expression as
     * <code>left type right</code>.
     *
     * @param type  Specify the expression type.
     * @param left  For comparison and inclusion expression types, the operand must be of
     *              type {@link Key} and for the AND|OR expression types the left operand must be
     *              another {@link Expression}.
     * @param right For comparison and inclusion expression types, the operand must be of
     *              type {@link Value} or array of values. For the AND|OR type the right operand must
     *              be another {@link Expression}.
     */
    public record Expression(ExpressionType type, Operand left, Operand right) implements Operand {
        public Expression(ExpressionType type, Operand operand) {
            this(type, operand, null);
        }
    }

    /**
     * 表示表达式分组（例如：(…)），用于指示该分组需要优先评估。
     *
     * @param content 表示作为分组一部分需要评估的内部表达式。
     */
    public record Group(Expression content) implements Operand {
    }

}
