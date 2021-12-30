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
package io.github.mybatisx.base.naming;

import io.github.mybatisx.annotation.NamingStrategy;

/**
 * 字段命名策略转换器
 *
 * @author wvkity
 * @created 2021/12/28
 * @since 1.0.0
 */
public class ColumnNamingConverter extends AbstractNamingConverter {

    public ColumnNamingConverter(NamingStrategy targetStrategy) {
        this(NamingStrategy.LOWER_CAMEL, targetStrategy);
    }

    public ColumnNamingConverter(NamingStrategy sourceStrategy, NamingStrategy targetStrategy) {
        super(sourceStrategy, targetStrategy);
    }

    public static ColumnNamingConverter of(final NamingStrategy target) {
        return new ColumnNamingConverter(target);
    }

    public static ColumnNamingConverter of(final NamingStrategy source, final NamingStrategy target) {
        return new ColumnNamingConverter(source, target);
    }
}

