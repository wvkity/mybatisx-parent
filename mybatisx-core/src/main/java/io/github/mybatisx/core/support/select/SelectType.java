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
package io.github.mybatisx.core.support.select;

/**
 * 查询列类型
 *
 * @author wvkity
 * @created 2022/1/18
 * @since 1.0.0
 */
public enum SelectType {

    /**
     * 普通查询列
     */
    PLAIN,
    /**
     * 标准
     */
    STANDARD,
    /**
     * 自查类型
     */
    QUERY,
    /**
     * 聚合函数类型
     */
    FUNCTION
}
