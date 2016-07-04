/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
package org.assertj.swing.format;

import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.assertj.core.util.Strings.concat;
import static org.assertj.core.util.Strings.quote;
import static org.assertj.swing.exception.ActionFailedException.actionFailure;
import static org.assertj.swing.util.Maps.newHashMap;

import java.awt.Component;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.assertj.core.presentation.StandardRepresentation;
import org.assertj.swing.annotation.RunsInCurrentThread;
import org.assertj.swing.util.Arrays;

/**
 * Formatter that uses <a href="http://docs.oracle.com/javase/tutorial/javabeans/index.html"
 * target="_blank">introspection</a> to display property values of an AWT or Swing {@code Component}. This formatter
 * does not support nested properties.
 *
 * @author Alex Ruiz
 */
public final class IntrospectionComponentFormatter extends ComponentFormatterTemplate {
  private final Class<? extends Component> targetType;
  private final List<String> propertyNames;

  private final Map<String, PropertyDescriptor> descriptors = newHashMap();

  /**
   * Creates a new {@link IntrospectionComponentFormatter}.
   *
   * @param targetType the type of AWT or Swing {@code Component} that this formatter supports.
   * @param propertyNames the property names to show as the {@code String} representation of a given {@code Component}.
   * @throws NullPointerException if {@code targetType} is {@code null}.
   */
  public IntrospectionComponentFormatter(@Nonnull Class<? extends Component> targetType,
                                         @Nonnull String... propertyNames) {
    this.targetType = checkNotNull(targetType);
    this.propertyNames = newArrayList(propertyNames);
    populate();
  }

  private void populate() {
    BeanInfo beanInfo = null;
    try {
      beanInfo = Introspector.getBeanInfo(targetType, Object.class);
    } catch (Exception e) {
      throw actionFailure(concat("Unable to get BeanInfo for type ", targetType.getName()), e);
    }
    for (PropertyDescriptor d : beanInfo.getPropertyDescriptors()) {
      register(checkNotNull(d));
    }
  }

  private void register(@Nonnull PropertyDescriptor d) {
    String name = d.getName();
    if (!propertyNames.contains(name)) {
      return;
    }
    descriptors.put(name, d);
  }

  /**
   * Returns a {@code String} representation of the given AWT or Swing {@code Component}, showing only the properties
   * specified in this formatter's {@link #IntrospectionComponentFormatter(Class, String[]) constructor}.
   *
   * @param c the given {@code Component}.
   * @return a {@code String} representation of the given {@code Component}.
   * @throws NullPointerException if the given {@code Component} is {@code null}.
   * @throws IllegalArgumentException if the type of the given {@code Component} is not supported by this formatter.
   * @see #targetType()
   */
  @RunsInCurrentThread
  @Override
  protected @Nonnull String doFormat(@Nonnull Component c) {
    StringBuilder b = new StringBuilder();
    b.append(getRealClassName(c)).append("[");
    int max = propertyNames.size() - 1;
    for (int i = 0; i <= max; i++) {
      appendProperty(b, checkNotNull(propertyNames.get(i)), c);
      if (i < max) {
        b.append(", ");
      }
    }
    b.append("]");
    return b.toString();
  }

  private void appendProperty(@Nonnull StringBuilder b, @Nonnull String name, @Nonnull Component c) {
    b.append(name).append("=");
    try {
      b.append(propertyValue(c, name));
    } catch (Exception e) {
      b.append(String.format("<Unable to read property [%s: %s]>", e.getClass().getName(), quote(e.getMessage())));
    }
  }

  private @Nullable Object propertyValue(@Nonnull Component c, @Nonnull String property) throws Exception {
    if ("showing".equals(property)) {
      return c.isShowing();
    }
    PropertyDescriptor descriptor = descriptors.get(property);
    Object value = descriptor.getReadMethod().invoke(c);
    if (isOneDimensionalArray(value)) {
      return Arrays.format(value);
    }
    return quote(value);
  }

  private boolean isOneDimensionalArray(Object o) {
    return o != null && o.getClass().isArray() && !o.getClass().getComponentType().isArray();
  }

  /**
   * @return the type of AWT or Swing {@code Component} this formatter supports.
   */
  @Override
  public @Nonnull Class<? extends Component> targetType() {
    return targetType;
  }

  @Override
  public String toString() {
    return String.format("%s[propertyNames=%s", getClass().getName(),
                         new StandardRepresentation().toStringOf(propertyNames));
  }
}
