/*
 * Copyright (c) 2021-Now, wvkity(wvkity@gmail.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package io.github.mybatisx.core.criteria.support;

import io.github.mybatisx.core.sql.DefaultSqlManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.Consumer;

/**
 * 基础条件(支持Lambda表达式)
 *
 * @param <T> 实体类型
 * @author wvkity
 * @created 2022/1/6
 * @since 1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LambdaCriteriaImpl<T> extends AbstractLambdaCriteria<T, LambdaCriteriaImpl<T>> {

    private static final long serialVersionUID = -2981849275689397498L;

    public LambdaCriteriaImpl(Class<T> entity) {
        this.entity = entity;
        this.newInit(null);
        this.sqlManager = new DefaultSqlManager(this, this.fragmentManager);
    }

    @Override
    protected LambdaCriteriaImpl<T> newInstance() {
        final LambdaCriteriaImpl<T> it = new LambdaCriteriaImpl<>();
        it.clone(this);
        return it;
    }

    /**
     * 创建{@link LambdaCriteriaImpl}对象
     *
     * @param entity 实体类
     * @param <T>    实体类型
     * @return {@link LambdaCriteriaImpl}
     */
    public static <T> LambdaCriteriaImpl<T> from(final Class<T> entity) {
        return new LambdaCriteriaImpl<>(entity);
    }

    /**
     * 创建{@link LambdaCriteriaImpl}对象
     *
     * @param entity 实体类
     * @param action {@link Consumer}
     * @param <T>    实体类型
     * @return {@link LambdaCriteriaImpl}
     */
    public static <T> LambdaCriteriaImpl<T> from(final Class<T> entity, final Consumer<LambdaCriteriaImpl<T>> action) {
        final LambdaCriteriaImpl<T> it = from(entity);
        if (action != null) {
            action.accept(it);
        }
        return it;
    }

}
