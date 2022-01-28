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
package io.github.mybatisx.core.criteria.query;

import io.github.mybatisx.base.constant.Constants;
import io.github.mybatisx.base.constant.NullPrecedence;
import io.github.mybatisx.base.helper.TableHelper;
import io.github.mybatisx.base.metadata.Column;
import io.github.mybatisx.core.criteria.support.AbstractGenericCriteria;
import io.github.mybatisx.core.property.Property;
import io.github.mybatisx.core.support.function.AggFunction;
import io.github.mybatisx.core.support.function.Avg;
import io.github.mybatisx.core.support.function.Count;
import io.github.mybatisx.core.support.function.Max;
import io.github.mybatisx.core.support.function.Min;
import io.github.mybatisx.core.support.function.Sum;
import io.github.mybatisx.core.support.group.Group;
import io.github.mybatisx.core.support.group.MultiGroup;
import io.github.mybatisx.core.support.group.SingleGroup;
import io.github.mybatisx.core.support.order.MultiOrder;
import io.github.mybatisx.core.support.order.Order;
import io.github.mybatisx.core.support.order.SingleOrder;
import io.github.mybatisx.core.support.select.FunctionSelectable;
import io.github.mybatisx.core.support.select.SelectType;
import io.github.mybatisx.core.support.select.Selectable;
import io.github.mybatisx.core.support.select.StandardSelectable;
import io.github.mybatisx.lang.Objects;
import io.github.mybatisx.lang.Strings;
import io.github.mybatisx.matcher.Matcher;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 抽象查询条件
 *
 * @param <T> 实体类型
 * @param <C> 子类型
 * @author wvkity
 * @created 2022/1/5
 * @since 1.0.0
 */
@SuppressWarnings({"serial"})
public abstract class AbstractGenericQueryCriteria<T, C extends GenericQueryCriteria<T, C>> extends
        AbstractGenericCriteria<T, C> implements GenericQueryCriteria<T, C> {

    // region Basic methods

    @Override
    public C as(String alias) {
        final String oldValue = this.aliasRef.get();
        this.aliasRef.compareAndSet(oldValue, Strings.isWhitespace(alias) ? Constants.EMPTY : alias);
        return this.context;
    }

    @Override
    public String as(boolean force) {
        return this.getAlias(force);
    }

    @Override
    public C useAlias() {
        return this.useAlias(true);
    }

    @Override
    public C useAlias(boolean using) {
        this.useAlias.set(using);
        return this.context;
    }

    @Override
    public C propAsAlias() {
        return this.propAsAlias(true);
    }

    @Override
    public C propAsAlias(boolean using) {
        this.propertyAsAlias = using;
        return this.context;
    }

    @Override
    public boolean isPropAsAlias() {
        return this.propertyAsAlias;
    }

    @Override
    public C reference(String reference) {
        final String oldValue = this.propRef.get();
        this.propRef.compareAndSet(oldValue, Strings.isWhitespace(reference) ? Constants.EMPTY : reference);
        return this.context;
    }

    @Override
    public C extra() {
        return this.extra(true);
    }

    @Override
    public C extra(boolean extra) {
        this.extra = extra;
        return this.context;
    }

    @Override
    public boolean isExtra() {
        return this.extra;
    }

    @Override
    public C inherit() {
        return this.inherit(true);
    }

    @Override
    public C inherit(boolean inherit) {
        this.inherit = inherit;
        return this.context;
    }

    @Override
    public boolean isInherit() {
        return this.inherit;
    }

    @Override
    public C distinct(boolean distinct) {
        this.distinct = distinct;
        return this.context;
    }

    @Override
    public boolean isDistinct() {
        return this.distinct;
    }

    @Override
    public C onlyQueryFunction() {
        return this.onlyQueryFunction(true);
    }

    @Override
    public C onlyQueryFunction(boolean only) {
        this.onlyQueryFunction = only;
        return this.context;
    }

    @Override
    public boolean isOnlyQueryFunction() {
        return this.onlyQueryFunction;
    }

    @Override
    public C containsFunction(boolean contains) {
        this.containsFunction = contains;
        return this.context;
    }

    @Override
    public boolean isContainsFunction() {
        return this.containsFunction;
    }

    @Override
    public C groupAll() {
        return this.groupAll(true);
    }

    @Override
    public C groupAll(boolean groupAll) {
        this.groupAll = groupAll;
        return this.context;
    }

    @Override
    public boolean isGroupAll() {
        return this.groupAll;
    }

    @Override
    public C keepOrderly() {
        return this.keepOrderly(true);
    }

    @Override
    public C keepOrderly(boolean keep) {
        this.keepOrderly = keep;
        return this.context;
    }

    @Override
    public boolean isKeepOrderly() {
        return this.keepOrderly;
    }

    // endregion

    // region Embeddable result methods

    @Override
    public String getResultMap() {
        return this.resultMap;
    }

    @Override
    public C setResultMap(String resultMap) {
        this.resultMap = resultMap;
        return this.context;
    }

    @Override
    public Class<?> getResultType() {
        return this.resultType;
    }

    @Override
    public C setResultType(Class<?> resultType) {
        this.resultType = resultType;
        return this.context;
    }

    @Override
    public String getMapKey() {
        return this.mapKey;
    }

    @Override
    public C setMapKey(Property<T, ?> property) {
        //  noinspection DuplicatedCode
        if (property != null) {
            final String prop = this.convert(property);
            if (this.propertyAsAlias) {
                this.setMapKey(prop);
            } else {
                final Column column = this.convert(this.convert(property));
                if (column != null) {
                    this.setMapKey(column.getColumn());
                }
            }
        }
        return this.context;
    }

    @Override
    public C setMapKey(String mapKey) {
        this.mapKey = mapKey;
        return this.context;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Class<? extends Map> getMapType() {
        return this.mapType;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public C setMapType(Class<? extends Map> mapType) {
        this.mapType = mapType;
        return this.context;
    }

    // endregion

    // region Selectable methods 

    @Override
    public C select(String property, String alias) {
        final Column column;
        if ((column = this.convert(property)) != null) {
            this.select(StandardSelectable.builder()
                    .query(this)
                    .column(column.getColumn())
                    .property(column.getProperty())
                    .type(SelectType.STANDARD)
                    .alias(alias)
                    .build());
        }
        return this.context;
    }

    @Override
    public C select(Matcher<Column> matcher) {
        //  noinspection DuplicatedCode
        if (matcher != null) {
            final List<Column> columns;
            if (Objects.isNotEmpty((columns = TableHelper.getColumns(this.getEntity(), matcher)))) {
                for (Column column : columns) {
                    this.select(StandardSelectable.builder()
                            .query(this)
                            .column(column.getColumn())
                            .property(column.getProperty())
                            .type(SelectType.STANDARD)
                            .build());
                }
            }
        }
        return this.context;
    }

    @Override
    public C select(Map<String, String> properties) {
        if (Objects.isNotEmpty(properties)) {
            for (Map.Entry<String, String> it : properties.entrySet()) {
                this.select(it.getValue(), it.getKey());
            }
        }
        return this.context;
    }

    @Override
    public C selects(Collection<String> properties) {
        if (Objects.isNotEmpty(properties)) {
            for (String property : properties) {
                this.select(property);
            }
        }
        return this.context;
    }

    @Override
    public C colSelect(String column, String alias) {
        if (Strings.isNotWhitespace(column)) {
            this.select(StandardSelectable.builder()
                    .query(this)
                    .type(SelectType.PLAIN)
                    .column(column)
                    .alias(alias)
                    .build());
        }
        return this.context;
    }

    @Override
    public C colSelect(Map<String, String> columns) {
        if (Objects.isNotEmpty(columns)) {
            for (Map.Entry<String, String> it : columns.entrySet()) {
                this.colSelect(it.getValue(), it.getKey());
            }
        }
        return this.context;
    }

    @Override
    public C colSelects(Collection<String> columns) {
        if (Objects.isNotEmpty(columns)) {
            for (String it : columns) {
                this.colSelect(it, null);
            }
        }
        return this.context;
    }

    @Override
    public C select(Selectable selectable) {
        this.fragmentManager.addSelect(selectable);
        return this.context;
    }

    @Override
    public C selects(Selectable... selectables) {
        return this.selects(Arrays.asList(selectables));
    }

    @Override
    public C selects(List<Selectable> selectables) {
        this.fragmentManager.addSelects(selectables);
        return this.context;
    }

    @Override
    public C excludeProperty(String property) {
        this.fragmentManager.addExcludeProperty(property);
        return this.context;
    }

    @Override
    public C excludeProperties(Collection<String> properties) {
        this.fragmentManager.addExcludeProperties(properties);
        return this.context;
    }

    @Override
    public C excludeColumn(String column) {
        this.fragmentManager.addExcludeColumn(column);
        return this.context;
    }

    @Override
    public C excludeColumns(Collection<String> columns) {
        this.fragmentManager.addExcludeColumns(columns);
        return this.context;
    }

    @Override
    public boolean hasSelect() {
        return this.fragmentManager.hasSelect();
    }

    @Override
    public List<Selectable> fetchSelects() {
        return this.getSelects();
    }

    // endregion

    // region Aggregate function methods

    @Override
    public C count(String property, String alias, boolean distinct) {
        final Column column;
        if ((column = this.convert(property)) != null) {
            this.function(new Count(this, column.getColumn(), alias, distinct));
        }
        return this.context;
    }

    @Override
    public C sum(String property, String alias, Integer scale, boolean distinct) {
        final Column column;
        if ((column = this.convert(property)) != null) {
            this.function(new Sum(this, column.getColumn(), alias, distinct));
        }
        return this.context;
    }

    @Override
    public C avg(String property, String alias, Integer scale, boolean distinct) {
        final Column column;
        if ((column = this.convert(property)) != null) {
            this.function(new Avg(this, column.getColumn(), alias, distinct));
        }
        return this.context;
    }

    @Override
    public C min(String property, String alias, Integer scale) {
        final Column column;
        if ((column = this.convert(property)) != null) {
            this.function(new Min(this, column.getColumn(), alias, false));
        }
        return this.context;
    }

    @Override
    public C max(String property, String alias, Integer scale) {
        final Column column;
        if ((column = this.convert(property)) != null) {
            this.function(new Max(this, column.getColumn(), alias, false));
        }
        return this.context;
    }

    @Override
    public C colCount(String column, String alias, boolean distinct) {
        if (Strings.isNotWhitespace(column)) {
            this.function(new Count(this, column, alias, distinct));
        }
        return this.context;
    }

    @Override
    public C colSum(String column, String alias, Integer scale, boolean distinct) {
        if (Strings.isNotWhitespace(column)) {
            this.function(new Sum(this, column, alias, distinct));
        }
        return this.context;
    }

    @Override
    public C colAvg(String column, String alias, Integer scale, boolean distinct) {
        if (Strings.isNotWhitespace(column)) {
            this.function(new Avg(this, column, alias, distinct));
        }
        return this.context;
    }

    @Override
    public C colMin(String column, String alias, Integer scale) {
        if (Strings.isNotWhitespace(column)) {
            this.function(new Min(this, column, alias, false));
        }
        return this.context;
    }

    @Override
    public C colMax(String column, String alias, Integer scale) {
        if (Strings.isNotWhitespace(column)) {
            this.function(new Max(this, column, alias, false));
        }
        return this.context;
    }

    @Override
    public C function(AggFunction function) {
        if (function != null) {
            this.select(new FunctionSelectable(SelectType.FUNCTION, function));
        }
        return this.context;
    }

    @Override
    public C functions(AggFunction... functions) {
        return this.functions(Arrays.asList(functions));
    }

    @Override
    public C functions(List<AggFunction> functions) {
        if (Objects.isNotEmpty(functions)) {
            for (AggFunction it : functions) {
                this.function(it);
            }
        }
        return this.context;
    }

    // endregion

    // region Group by methods

    @Override
    public C group(String property) {
        final Column column;
        if ((column = this.convert(property)) != null) {
            this.group(SingleGroup.group(this, column.getColumn()));
        }
        return this.context;
    }

    @Override
    public C group(List<String> properties) {
        if (Objects.isNotEmpty(properties)) {
            this.group(MultiGroup.group(this, this.stringConvert(properties)));
        }
        return this.context;
    }

    @Override
    public C colGroup(String column) {
        return this.group(SingleGroup.group(this, column));
    }

    @Override
    public C colGroups(List<String> columns) {
        return this.group(MultiGroup.group(this, columns));
    }

    @Override
    public C group(Group group) {
        this.fragmentManager.addGroup(group);
        return this.context;
    }

    @Override
    public C groups(Group... groups) {
        return this.groups(Arrays.asList(groups));
    }

    @Override
    public C groups(List<Group> groups) {
        this.fragmentManager.addGroups(groups);
        return this.context;
    }

    // endregion

    // region Order by methods 

    @Override
    public C asc(String property, boolean ignoreCase, NullPrecedence precedence) {
        final Column column;
        if ((column = this.convert(property)) != null) {
            this.order(SingleOrder.asc(this, column.getColumn(),
                    ignoreCase && Objects.isAssignable(String.class, column.getDescriptor().getJavaType()), precedence));
        }
        return this.context;
    }

    @Override
    public C asc(List<String> properties, boolean ignoreCase, NullPrecedence precedence) {
        if (Objects.isNotEmpty(properties)) {
            this.order(MultiOrder.asc(this, this.stringConvert(properties), ignoreCase, precedence));
        }
        return this.context;
    }

    @Override
    public C asc(boolean ignoreCase, NullPrecedence precedence, List<Property<T, ?>> properties) {
        if (Objects.isNotEmpty(properties)) {
            this.order(MultiOrder.asc(this, this.lambdaConvert(properties), ignoreCase, precedence));
        }
        return this.context;
    }

    @Override
    public C desc(String property, boolean ignoreCase, NullPrecedence precedence) {
        final Column column;
        if ((column = this.convert(property)) != null) {
            this.order(SingleOrder.desc(this, column.getColumn(),
                    ignoreCase && Objects.isAssignable(String.class, column.getDescriptor().getJavaType()), precedence));
        }
        return this.context;
    }

    @Override
    public C desc(List<String> properties, boolean ignoreCase, NullPrecedence precedence) {
        if (Objects.isNotEmpty(properties)) {
            this.order(MultiOrder.desc(this, this.stringConvert(properties), ignoreCase, precedence));
        }
        return this.context;
    }

    @Override
    public C desc(boolean ignoreCase, NullPrecedence precedence, List<Property<T, ?>> properties) {
        if (Objects.isNotEmpty(properties)) {
            this.order(MultiOrder.desc(this, this.lambdaConvert(properties), ignoreCase, precedence));
        }
        return this.context;
    }

    @Override
    public C colAsc(String column, boolean ignoreCase, NullPrecedence precedence) {
        return this.order(SingleOrder.asc(this, column, ignoreCase, precedence));
    }

    @Override
    public C colAsc(List<String> columns, boolean ignoreCase, NullPrecedence precedence) {
        return this.order(MultiOrder.asc(this, columns, ignoreCase, precedence));
    }

    @Override
    public C colDesc(String column, boolean ignoreCase, NullPrecedence precedence) {
        return this.order(SingleOrder.desc(this, column, ignoreCase, precedence));
    }

    @Override
    public C colDesc(List<String> columns, boolean ignoreCase, NullPrecedence precedence) {
        return this.order(MultiOrder.desc(this, columns, ignoreCase, precedence));
    }

    @Override
    public C order(Order order) {
        this.fragmentManager.addOrder(order);
        return this.context;
    }

    @Override
    public C orders(Order... orders) {
        return this.orders(Arrays.asList(orders));
    }

    @Override
    public C orders(List<Order> orders) {
        this.fragmentManager.addOrders(orders);
        return this.context;
    }

    // endregion

    @Override
    public String getSelectFragment() {
        return this.sqlManager.getSelectFragment();
    }

    @Override
    public String getSelectFragment(boolean self) {
        return this.sqlManager.getSelectFragment(self);
    }

    @Override
    public String getGroupFragment() {
        return this.sqlManager.getGroupFragment();
    }

}
