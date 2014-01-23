/*
 * Created on May 23, 2008
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
 * Copyright @2008-2013 the original author or authors.
 */
package org.fest.swing.launcher;

import static org.fest.reflect.core.Reflection.method;
import static org.fest.swing.util.Arrays.copyOf;
import static org.fest.util.Preconditions.checkNotNull;
import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.quote;

import javax.annotation.Nonnull;

import org.fest.reflect.exception.ReflectionError;
import org.fest.swing.exception.UnexpectedException;

/**
 * <p>
 * Executes a Java application from a class that has a "main" method.
 * </p>
 * 
 * <p>
 * The following example shows how to start an application without any arguments:
 * <pre>
 * ApplicationLauncher.application(JavaApp.class).start();
 * 
 * // or
 * 
 * ApplicationLauncher.{@link #application(String) application}(&quot;org.fest.swing.application.JavaApp&quot;).{@link #start() start}();
 * </pre>
 * </p>
 * 
 * <p>
 * The following example shows how to start an application with arguments:
 * <pre>
 * ApplicationLauncher.{@link #application(Class) application}(JavaApp.class).{@link #withArgs(String...) withArgs}(&quot;arg1&quot;, &quot;arg2&quot;).{@link #start() start}();
 * 
 * // or
 * 
 * ApplicationLauncher.{@link #application(String) application}(&quot;org.fest.swing.application.JavaApp&quot;).{@link #withArgs(String...) withArgs}(&quot;arg1&quot;, &quot;arg2&quot;).{@link #start() start}();
 * </pre>
 * </p>
 * 
 * @author Yvonne Wang
 */
public class ApplicationLauncher {
  /**
   * Starting point of the fluent interface.
   * 
   * @param applicationTypeName the fully qualified name of the class containing the "main" method.
   * @return the created {@code ApplicationStarter}.
   * @throws UnexpectedException if the class specified in the given name cannot be loaded.
   */
  public static @Nonnull ApplicationLauncher application(@Nonnull String applicationTypeName) {
    try {
      Class<?> applicationType = Thread.currentThread().getContextClassLoader().loadClass(applicationTypeName);
      return application(applicationType);
    } catch (ClassNotFoundException e) {
      throw new UnexpectedException(concat("Unable to load class ", quote(applicationTypeName)), e);
    }
  }

  /**
   * Starting point of the fluent interface.
   * 
   * @param applicationType the class containing the "main" method.
   * @return the created {@code ApplicationStarter}.
   */
  public static @Nonnull ApplicationLauncher application(@Nonnull Class<?> applicationType) {
    return new ApplicationLauncher(applicationType);
  }

  private final Class<?> applicationType;
  private String[] args = {};

  private ApplicationLauncher(@Nonnull Class<?> applicationType) {
    this.applicationType = applicationType;
  }

  /**
   * Specifies the arguments to pass to the "main" method. Please note that the arguments to pass are specific to your
   * application. JVM-specific arguments are ignored (e.g. -Xms, -Xmx)
   * 
   * @param newArgs the arguments to pass to the "main" method.
   * @return this {@code ApplicationStarter}.
   * @throws NullPointerException if {@code newArgs} is {@code null}.
   */
  public @Nonnull ApplicationLauncher withArgs(@Nonnull String... newArgs) {
    args = copyOf(checkNotNull(newArgs));
    return this;
  }

  /**
   * Starts the application.
   * 
   * @throws ReflectionError if the "main" method cannot be invoked.
   */
  public void start() {
    method("main").withParameterTypes(String[].class).in(applicationType).invoke(new Object[] { args });
  }
}
