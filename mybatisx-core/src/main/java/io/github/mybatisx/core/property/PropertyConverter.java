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
package io.github.mybatisx.core.property;

import io.github.mybatisx.base.convert.Converter;
import io.github.mybatisx.base.metadata.Column;

/**
 * 属性转换器(Lambda属性 => 字符串属性)
 *
 * @author wvkity
 * @created 2022/1/9
 * @since 1.0.0
 */
public interface PropertyConverter<T> extends Converter<Property<T, ?>, String> {

    @Override
    default String convert(final Property<T, ?> src) {
        return LambdaMetadataWeakCache.getProperty(src);
    }

    /**
     * 属性转成{@link Column}对象
     *
     * @param property 属性
     * @return {@link Column}
     */
    Column convert(final String property);

}
