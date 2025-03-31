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

import java.lang.reflect.Type;
import java.util.function.Function;

import com.fasterxml.jackson.annotation.JsonClassDescription;

import org.springframework.ai.model.function.FunctionCallbackWrapper.Builder.SchemaType;
import org.springframework.beans.BeansException;
import org.springframework.cloud.function.context.catalog.FunctionTypeUtils;
import org.springframework.cloud.function.context.config.FunctionContextUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Description;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

/**
 * Spring {@link ApplicationContextAware} 的实现，它提供了一种从 Spring 上下文中检索 Function 并将其包装为 FunctionCallback 的方法。
 * <p>
 * 函数的名称由 Bean 名称确定
 * <p>
 * 函数的描述由以下规则决定：
 * <ul>
 * <li>作为默认描述提供</li>
 * <li>通过 {@code @Description} 注解定义在 Bean 上</li>
 * <li>通过 {@code @JsonClassDescription} 注解定义在输入类上</li>
 * </ul>
 *
 * @author Christian Tzolov
 * @author Christopher Smith
 */
public class FunctionCallbackContext implements ApplicationContextAware {

    private GenericApplicationContext applicationContext;

    private SchemaType schemaType = SchemaType.JSON_SCHEMA;

    public void setSchemaType(SchemaType schemaType) {
        this.schemaType = schemaType;
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (GenericApplicationContext) applicationContext;
    }

    /**
     * 从Spring 上下文中获取 Function call 的 Bean
     *
     * @param beanName           Function call 的 Bean 名称
     * @param defaultDescription 默认的描述
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public FunctionCallback getFunctionCallback(@NonNull String beanName, @Nullable String defaultDescription) {

        Type beanType = FunctionContextUtils.findType(this.applicationContext.getBeanFactory(), beanName);

        if (beanType == null) {
            throw new IllegalArgumentException(
                    "Functional bean with name: " + beanName + " does not exist in the context.");
        }

        //  检查 beanType 是否是 Function 或其子类
        if (!Function.class.isAssignableFrom(FunctionTypeUtils.getRawType(beanType))) {
            throw new IllegalArgumentException(
                    "Function call Bean must be of type Function. Found: " + beanType.getTypeName());
        }

        // 获取 Function 的输入参数类型
        Type functionInputType = TypeResolverHelper.getFunctionArgumentType(beanType, 0);

        Class<?> functionInputClass = FunctionTypeUtils.getRawType(functionInputType);
        String functionName = beanName;
        String functionDescription = defaultDescription;

        // functionDescription 为空，尝试从 Description / JsonClassDescription 注解获取函数描述
        if (!StringUtils.hasText(functionDescription)) {
            // Look for a Description annotation on the bean
            Description descriptionAnnotation = applicationContext.findAnnotationOnBean(beanName, Description.class);

            if (descriptionAnnotation != null) {
                functionDescription = descriptionAnnotation.value();
            }

            if (!StringUtils.hasText(functionDescription)) {
                // Look for a JsonClassDescription annotation on the input class
                JsonClassDescription jsonClassDescriptionAnnotation = functionInputClass
                        .getAnnotation(JsonClassDescription.class);
                if (jsonClassDescriptionAnnotation != null) {
                    functionDescription = jsonClassDescriptionAnnotation.value();
                }
            }

            if (!StringUtils.hasText(functionDescription)) {
                throw new IllegalStateException("Could not determine function description."
                        + "Please provide a description either as a default parameter, via @Description annotation on the bean "
                        + "or @JsonClassDescription annotation on the input class.");
            }
        }

        Object bean = this.applicationContext.getBean(beanName);

        if (bean instanceof Function<?, ?> function) {
            return FunctionCallbackWrapper.builder(function)
                    .withName(functionName)
                    .withSchemaType(this.schemaType)
                    .withDescription(functionDescription)
                    .withInputType(functionInputClass)
                    .build();
        } else {
            throw new IllegalArgumentException("Bean must be of type Function");
        }
    }

}
