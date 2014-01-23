/*
 * Created on May 27, 2007
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2007-2013 the original author or authors.
 */
package org.fest.swing.annotation;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Utility methods related to GUI tests. A GUI test is a class or method annotated with {@link GUITest}.
 * 
 * @author Alex Ruiz
 */
public final class GUITestFinder {
  /**
   * Returns {@code true} if the given class and/or method are annotated with {@link GUITest}. This method also searches
   * in super-classes and overridden methods.
   * 
   * @param type the class to check.
   * @param method the method to check.
   * @return {@code true} if the given class and/or method are annotated with {@code GUITest}.
   */
  public static boolean isGUITest(@Nonnull Class<?> type, @Nonnull Method method) {
    return isGUITest(type) || isGUITest(method) || isSuperClassGUITest(type, method);
  }

  private static boolean isSuperClassGUITest(@Nonnull Class<?> type, @Nonnull Method method) {
    Class<?> superclass = type.getSuperclass();
    while (superclass != null) {
      if (isGUITest(superclass)) {
        return true;
      }
      Method overriden = findMethod(superclass, method);
      if (overriden != null && isGUITest(overriden)) {
        return true;
      }
      superclass = superclass.getSuperclass();
    }
    return false;
  }

  private static @Nullable Method findMethod(@Nonnull Class<?> type, @Nonnull Method method) {
    try {
      return type.getDeclaredMethod(method.getName(), method.getParameterTypes());
    } catch (Throwable t) {
      return null;
    }
  }

  private static boolean isGUITest(@Nonnull AnnotatedElement annotatedElement) {
    return annotatedElement.isAnnotationPresent(GUITest.class);
  }

  private GUITestFinder() {}
}
