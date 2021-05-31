/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2020 the original author or authors.
 */
package org.assertj.swing.util;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Strategy for applying names to components which have not been given one at compilation time. Where possible, this
 * will use the field name if the component is declared at class level. For anonymously declared fields, or for those
 * declared in methods, a name will be generated based on the {@link Class#getSimpleName()} of the component appended
 * with an incremented long.
 *
 * @author Beirti O'Nunain
 */
public class ComponentNamer {
  private Container container;
  private AtomicLong counter;

  private static final Map<Class<?>, List<Field>> DECLARED_FIELDS_BY_CLASS = new HashMap<>();

  private boolean overwriteExisting = false;
  private boolean useGeneratedNamesOnly = false;

  private ComponentNamer(Container container) {
    this.container = container;
    counter = new AtomicLong(1L);
  }

  /**
   * Create an instance of a ComponentNamer
   * @param container to introspect
   * @return the namer for fluent coding
   */
  public static ComponentNamer namer(Container container) {
    if (container == null) {
      throw new IllegalArgumentException("Container cannot be null");
    }
    return new ComponentNamer(container);
  }

  /**
   * Overwrite the name of the field, even if it already has one. This is useful when dealing with 3rd party components
   * that have unsuitable names. Combine with {@link ComponentNamer#useGeneratedNamesOnly} to force a consistent
   * set of names for the components.
   * @return the namer for fluent coding
   */
  public ComponentNamer overwriteExisting() {
    this.overwriteExisting = true;
    return this;
  }

  /**
   * Only use generated names for the components. This is useful when the hierarchy of Containers contains components 
   * which have the same name set, or have declared fields with the same name.
   * @return the namer for fluent coding
   */
  public ComponentNamer useGeneratedNamesOnly() {
    this.useGeneratedNamesOnly = true;
    return this;
  }

  /**
   * Populate the 'name' field of any component with either its field name, if declared as a field, 
   * or with a generated String of the format 'field.getClass().getSimpleName() + "-" + Integer'
   */
  public void setMissingNames() {
    setMissingNames(container, new LinkedHashMap<>());
  }

  private void setMissingNames(Container container, Map<Object, String> parentFields) {
    // @format:off
    // First, find all declared fields in the component's hierarchy so we can use their declared names
    getAllDeclaredFields(container.getClass()).forEach(f -> parentFields.put(getFieldValue(container, f), f.getName()));
    // Now, recursively set names on the component hierarchy.
    stream(container.getComponents())
         .forEach(e -> {
           if (e instanceof Container) {
             setMissingNames((Container) e, parentFields);
           }
           setMissingName(e, parentFields);
         });
    // @format:on

  }

  private void setMissingName(Component component, Map<Object, String> allFields) {
    if (shouldRenameComponent(component)) {
      if (!useGeneratedNamesOnly && allFields.containsKey(component)) {
        component.setName(allFields.get(component));
      } else {
        component.setName(component.getClass().getSimpleName() + "-" + counter.getAndIncrement());
      }
    }
  }

  private boolean shouldRenameComponent(Component component) {
    return overwriteExisting || (component.getName() == null || component.getName().isEmpty());
  }

  private Object getFieldValue(Container container, Field f) {
    try {
      f.setAccessible(true);
      return f.get(container);
    } catch (IllegalAccessException e) {
      return null;
    }
  }

  private List<Field> getAllDeclaredFields(Class<?> clazz) {
    List<Field> fields = DECLARED_FIELDS_BY_CLASS.get(clazz);
    if (fields == null) {
      fields = new ArrayList<>();

      while (clazz != null) {
        //@format:off
        fields.addAll(stream(clazz.getDeclaredFields())
               .filter(f -> Component.class.isAssignableFrom(f.getType()))
               .collect(toList()));
        //@format:on
        clazz = clazz.getSuperclass();
      }
      DECLARED_FIELDS_BY_CLASS.put(clazz, fields);
    }
    return fields;
  }
}
