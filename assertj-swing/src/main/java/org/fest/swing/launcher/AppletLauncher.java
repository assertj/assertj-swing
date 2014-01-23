/*
 * Created on Jul 14, 2008
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

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.launcher.NewAppletViewerQuery.showAppletViewerWith;
import static org.fest.util.Maps.newHashMap;
import static org.fest.util.Preconditions.checkNotNull;
import static org.fest.util.Preconditions.checkNotNullOrEmpty;

import java.applet.Applet;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.applet.AppletViewer;
import org.fest.swing.applet.BasicAppletContext;
import org.fest.swing.applet.BasicAppletStub;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.exception.UnexpectedException;
import org.fest.swing.launcher.AppletParameter.AppletParameterBuilder;

/**
 * <p>
 * Fluent interface for launching and testing {@code Applet}s.
 * </p>
 * 
 * <p>
 * An {@code Applet} can be launched by passing its type as {@code String}, the actual type, or an instance of the
 * {@code Applet} to launch:
 * <pre>
 * {@link AppletViewer} viewer = AppletLauncher.{@link #applet(String) applet}(&quot;org.fest.swing.applet.MyApplet&quot;).{@link #start() start}();
 * 
 * // or
 * 
 * {@link AppletViewer} viewer = AppletLauncher.{@link #applet(Class) applet}(MyApplet.class).{@link #start() start}();
 * 
 * // or
 * 
 * {@link AppletViewer} viewer = AppletLauncher.{@link #launcherFor(Applet) applet}(new MyApplet()).{@link #start() start}();
 * </pre>
 * </p>
 * 
 * <p>
 * In addition, we can pass parameters to the applet to launch. The parameters to pass are the same that are specified
 * in the <a href="http://java.sun.com/docs/books/tutorial/deployment/applet/html.html" target="_blank">HTML "param"
 * tag</a>:
 * 
 * <pre>
 * {@link AppletViewer} viewer = AppletLauncher.{@link #launcherFor(Applet) applet}(new MyApplet())
 *                                     .{@link #withParameters(Map) withParameters}(
 *                                         {@link AppletParameter#name(String) name}(&quot;bgcolor&quot;).{@link AppletParameterBuilder#value(String) value}(&quot;blue&quot;),
 *                                         {@link AppletParameter#name(String) name}(&quot;color&quot;).{@link AppletParameterBuilder#value(String) value}(&quot;red&quot;),
 *                                         {@link AppletParameter#name(String) name}(&quot;pause&quot;).{@link AppletParameterBuilder#value(String) value}(&quot;200&quot;)
 *                                      )
 *                                     .{@link #start() start}();
 * 
 * // or
 * 
 * Map&lt;String, String&gt; parameters = new HashMap&lt;String, String&gt;();
 * parameters.put(&quot;bgcolor&quot;, &quot;blue&quot;);
 * parameters.put(&quot;color&quot;, &quot;red&quot;);
 * parameters.put(&quot;pause&quot;, &quot;200&quot;);
 * 
 * {@link AppletViewer} viewer = AppletLauncher.{@link #launcherFor(Applet) applet}(new MyApplet()).{@link #withParameters(Map) withParameters}(parameters).{@link #start() start}();
 * </pre>
 * 
 * </p>
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class AppletLauncher {
  private final Applet applet;
  private final Map<String, String> parameters = newHashMap();

  /**
   * Creates a new {@link AppletLauncher}. The {@code Applet} to launch is a new instance of the given type. It is
   * assumed that the given type has a default constructor.
   * 
   * @param appletType the type of {@code Applet} to instantiate.
   * @return the created {@code AppletLauncher}.
   * @throws NullPointerException if the given type name is {@code null}.
   * @throws IllegalArgumentException if the given type name is empty.
   * @throws IllegalArgumentException if the given type is not a subclass of {@code Applet}.
   * @throws UnexpectedException if the given type cannot be loaded.
   * @throws UnexpectedException if a new instance of the given type cannot be instantiated.
   */
  @RunsInEDT
  public static @Nonnull AppletLauncher applet(@Nonnull String appletType) {
    checkNotNullOrEmpty(appletType);
    Class<?> type = load(appletType);
    if (!(Applet.class.isAssignableFrom(type))) {
      String msg = String.format("The given type is not a subclass of %s", Applet.class.getName());
      throw new IllegalArgumentException(msg);
    }
    return instantiate(type);
  }

  @RunsInEDT
  private static @Nonnull Class<?> load(@Nonnull String typeName) {
    try {
      return Class.forName(typeName);
    } catch (ClassNotFoundException e) {
      throw cannotLoadType(typeName, e);
    } catch (Exception e) {
      throw cannotLoadType(typeName, e);
    }
  }

  private static UnexpectedException cannotLoadType(String typeName, Exception e) {
    String msg = String.format("Unable to load class %s", typeName);
    throw new UnexpectedException(msg, e);
  }

  /**
   * Creates a new {@link AppletLauncher}. The {@code Applet} to launch is a new instance of the given type. It is
   * assumed that the given type has a default constructor.
   * 
   * @param appletType the type of {@code Applet} to instantiate.
   * @return the created {@code AppletLauncher}.
   * @throws NullPointerException if the given type is {@code null}.
   * @throws UnexpectedException if a new instance of the given type cannot be instantiated.
   */
  @RunsInEDT
  public static @Nonnull AppletLauncher applet(@Nonnull Class<? extends Applet> appletType) {
    return instantiate(checkNotNull(appletType));
  }

  private static @Nonnull AppletLauncher instantiate(final @Nonnull Class<?> appletType) {
    try {
      Object applet = execute(new GuiQuery<Object>() {
        @Override
        protected @Nullable Object executeInEDT() throws Exception {
          return appletType.newInstance();
        }
      });
      return launcherFor(checkNotNull((Applet) applet));
    } catch (Exception e) {
      String msg = String.format("Unable to create a new instance of %s", appletType.getName());
      throw new UnexpectedException(msg, e);
    }
  }

  /**
   * Creates a new {@link AppletLauncher}.
   * 
   * @param applet the {@code Applet} to launch.
   * @return the created {@code AppletLauncher}.
   * @throws NullPointerException if the given {@code Applet} is {@code null}.
   * 
   * @since 2.0
   */
  public static @Nonnull AppletLauncher launcherFor(@Nonnull Applet applet) {
    return new AppletLauncher(applet);
  }

  private AppletLauncher(@Nonnull Applet applet) {
    this.applet = checkNotNull(applet);
  }

  /**
   * Sets the parameters for the {@code Applet} to launch, as an alternative to
   * {@link #withParameters(AppletParameter...)}.
   * 
   * @param newParameters the parameters for the {@code Applet} to launch.
   * @return this launcher.
   * @throws NullPointerException if {@code newParameters} is {@code null}.
   */
  public @Nonnull AppletLauncher withParameters(@Nonnull Map<String, String> newParameters) {
    parameters.clear();
    parameters.putAll(checkNotNull(newParameters));
    return this;
  }

  /**
   * Sets the parameters for the {@code Applet} to launch, as an alternative to {@link #withParameters(Map)}.
   * 
   * @param newParameters the parameters for the {@code Applet} to launch.
   * @return this launcher.
   * @throws NullPointerException if {@code newParameters} is {@code null}.
   * @throws NullPointerException if any parameter is {@code null}.
   */
  public @Nonnull AppletLauncher withParameters(@Nonnull AppletParameter... newParameters) {
    checkNotNull(newParameters);
    parameters.clear();
    for (AppletParameter parameter : newParameters) {
      add(checkNotNull(parameter));
    }
    return this;
  }

  private void add(@Nonnull AppletParameter parameter) {
    parameters.put(parameter.name, parameter.value);
  }

  /**
   * Launches the {@code Applet} in a {@link AppletViewer} (using implementations of {@link BasicAppletStub} and
   * {@link BasicAppletContext}. To provide your own {@code AppletStub} create a new {@link AppletViewer} directly. The
   * {@code AppletViewer} is created and launched in the event dispatch thread (EDT.)
   * 
   * @return the created {@code AppletViewer}.
   */
  public @Nonnull AppletViewer start() {
    return showAppletViewerWith(applet, parameters);
  }
}
