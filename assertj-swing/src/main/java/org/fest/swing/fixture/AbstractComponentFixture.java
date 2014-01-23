/*
 * Created on Oct 20, 2006
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
 * Copyright @2006-2013 the original author or authors.
 */
package org.fest.swing.fixture;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.driver.ComponentDriver.propertyName;
import static org.fest.swing.format.Formatting.format;
import static org.fest.util.Preconditions.checkNotNull;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.fest.swing.core.KeyPressInfo;
import org.fest.swing.core.MouseButton;
import org.fest.swing.core.MouseClickInfo;
import org.fest.swing.core.Robot;
import org.fest.swing.core.Settings;
import org.fest.swing.driver.ComponentDriver;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.query.ComponentEnabledQuery;
import org.fest.swing.timing.Timeout;

/**
 * Supports functional testing of AWT and Swing {@code Component}s.
 *
 * @param <S> used to simulate "self types." For more information please read &quot;<a href="http://goo.gl/fjgOM"
 *          target="_blank">Emulating 'self types' using Java Generics to simplify fluent API implementation</a>.&quot;
 * @param <C> the type of {@code Component} that this fixture can manage.
 * @param <D> the type of {@link ComponentDriver} that this fixture uses internally.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class AbstractComponentFixture<S, C extends Component, D extends ComponentDriver> implements
    MouseInputSimulationFixture<S> {
  /** Name of the property "font". */
  protected static final String FONT_PROPERTY = "font";

  /** Name of the property "background". */
  protected static final String BACKGROUND_PROPERTY = "background";

  /** Name of the property "foreground". */
  protected static final String FOREGROUND_PROPERTY = "foreground";

  /** Performs simulation of user events on {@link #target} */
  private final Robot robot;

  private final C target;
  private final S myself;

  private D driver;

  /**
   * Creates a new {@link AbstractComponentFixture}.
   *
   * @param selfType the "self type."
   * @param robot performs simulation of user events on a {@code Component}.
   * @param type the type of the {@code Component} to find using the given {@code Robot}.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws NullPointerException if {@code type} is {@code null}.
   * @throws ComponentLookupException if a matching component could not be found.
   * @throws ComponentLookupException if more than one matching component is found.
   */
  public AbstractComponentFixture(@Nonnull Class<S> selfType, @Nonnull Robot robot, @Nonnull Class<? extends C> type) {
    this(selfType, robot, findTarget(robot, type));
  }

  private static @Nonnull <C extends Component> C findTarget(@Nonnull Robot robot, @Nonnull Class<? extends C> type) {
    checkNotNull(robot);
    checkNotNull(type);
    return robot.finder().findByType(type, requireShowing(robot));
  }

  /**
   * Creates a new {@link AbstractComponentFixture}.
   *
   * @param selfType the "self type."
   * @param robot performs simulation of user events on a {@code Component}.
   * @param name the name of the {@code Component} to find using the given {@code Robot}.
   * @param type the type of the {@code Component} to find using the given {@code Robot}.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws NullPointerException if {@code type} is {@code null}.
   * @throws ComponentLookupException if a matching component could not be found.
   * @throws ComponentLookupException if more than one matching component is found.
   */
  public AbstractComponentFixture(@Nonnull Class<S> selfType, @Nonnull Robot robot, @Nullable String name,
      @Nonnull Class<? extends C> type) {
    this(selfType, robot, findTarget(robot, name, type));
  }

  private static @Nonnull <C extends Component> C findTarget(@Nonnull Robot robot, @Nullable String name,
      @Nonnull Class<? extends C> type) {
    checkNotNull(robot);
    checkNotNull(type);
    return robot.finder().findByName(name, type, requireShowing(robot));
  }

  /**
   * Creates a new {@link AbstractComponentFixture}.
   *
   * @param selfType the "self type."
   * @param robot performs simulation of user events on the given {@code Component}.
   * @param target the {@code Component} to be managed by this fixture.
   * @throws NullPointerException if {@code selfType} is {@code null}.
   * @throws NullPointerException if {@code robot} is {@code null}.
   * @throws NullPointerException if {@code target} is {@code null}.
   */
  public AbstractComponentFixture(@Nonnull Class<S> selfType, @Nonnull Robot robot, @Nonnull C target) {
    myself = checkNotNull(selfType).cast(this);
    this.robot = checkNotNull(robot);
    this.target = checkNotNull(target);
    replaceDriverWith(createDriver(robot));
  }

  protected abstract @Nonnull D createDriver(@Nonnull Robot robot);

  protected final @Nonnull D driver() {
    return driver;
  }

  public final void replaceDriverWith(@Nonnull D driver) {
    this.driver = checkNotNull(driver);
  }

  /**
   * Simulates a user clicking this fixture's {@code Component}.
   *
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code Component} is disabled.
   * @throws IllegalStateException if this fixture's {@code Component} is not showing on the screen.
   */
  @Override
  public final @Nonnull S click() {
    driver.click(target());
    return myself();
  }


  /**
   * Simulates a user clicking this fixture's {@code Component}.
   *
   * @param button the button to click.
   * @return this fixture.
   * @throws NullPointerException if the given {@code MouseButton} is {@code null}.
   * @throws IllegalStateException if this fixture's {@code Component} is disabled.
   * @throws IllegalStateException if this fixture's {@code Component} is not showing on the screen.
   */
  @Override
  public final @Nonnull S click(@Nonnull MouseButton button) {
    driver.click(target(), button);
    return myself();
  }

  /**
   * Simulates a user clicking this fixture's {@code Component}.
   *
   * @param mouseClickInfo specifies the button to click and the times the button should be clicked.
   * @return this fixture.
   * @throws NullPointerException if the given {@code MouseClickInfo} is {@code null}.
   * @throws IllegalStateException if this fixture's {@code Component} is disabled.
   * @throws IllegalStateException if this fixture's {@code Component} is not showing on the screen.
   */
  @Override
  public final @Nonnull S click(@Nonnull MouseClickInfo mouseClickInfo) {
    driver.click(target(), mouseClickInfo);
    return myself();
  }

  /**
   * Simulates a user double-clicking this fixture's {@code Component}.
   *
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code Component} is disabled.
   * @throws IllegalStateException if this fixture's {@code Component} is not showing on the screen.
   */
  @Override
  public final @Nonnull S doubleClick() {
    driver.doubleClick(target());
    return myself();
  }

  /**
   * Simulates a user right-clicking this fixture's {@code Component}.
   *
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code Component} is disabled.
   * @throws IllegalStateException if this fixture's {@code Component} is not showing on the screen.
   */
  @Override
  public final @Nonnull S rightClick() {
    driver.rightClick(target());
    return myself();
  }

  /**
   * Gives input focus to this fixture's {@code Component}.
   *
   * @return this fixture.
   * @throws IllegalStateException if this fixture's {@code Component} is disabled.
   * @throws IllegalStateException if this fixture's {@code Component} is not showing on the screen.
   */
  public final @Nonnull S focus() {
    driver.focus(target());
    return myself();
  }

  /**
   * Simulates a user pressing given key with the given modifiers on this fixture's {@code Component}.
   * Modifiers is a mask from the available AWT {@code InputEvent} masks.
   *
   * @param keyPressInfo specifies the key and modifiers to press.
   * @return this fixture.
   * @throws NullPointerException if the given {@code KeyPressInfo} is {@code null}.
   * @throws IllegalArgumentException if the given code is not a valid key code.
   * @throws IllegalStateException if this fixture's {@code Component} is disabled.
   * @throws IllegalStateException if this fixture's {@code Component} is not showing on the screen.
   * @see KeyPressInfo
   */
  public final @Nonnull S pressAndReleaseKey(@Nonnull KeyPressInfo keyPressInfo) {
    driver.pressAndReleaseKey(target(), keyPressInfo);
    return myself();
  }

  /**
   * Simulates a user pressing and releasing the given keys on this fixture's {@code Component}.
   *
   * @param keyCodes one or more codes of the keys to press.
   * @return this fixture.
   * @throws NullPointerException if the given array of codes is {@code null}.
   * @throws IllegalArgumentException if any of the given code is not a valid key code.
   * @throws IllegalStateException if this fixture's {@code Component} is disabled.
   * @throws IllegalStateException if this fixture's {@code Component} is not showing on the screen.
   * @see java.awt.event.KeyEvent
   */
  public final @Nonnull S pressAndReleaseKeys(@Nonnull int... keyCodes) {
    driver.pressAndReleaseKeys(target(), keyCodes);
    return myself();
  }

  /**
   * Simulates a user pressing the given key on this fixture's {@code Component}.
   *
   * @param keyCode the code of the key to press.
   * @return this fixture.
   * @throws IllegalArgumentException if any of the given code is not a valid key code.
   * @throws IllegalStateException if this fixture's {@code Component} is disabled.
   * @throws IllegalStateException if this fixture's {@code Component} is not showing on the screen.
   * @see java.awt.event.KeyEvent
   */
  public final @Nonnull S pressKey(int keyCode) {
    driver.pressKey(target(), keyCode);
    return myself();
  }

  /**
   * Simulates a user releasing the given key on this fixture's {@code Component}.
   *
   * @param keyCode the code of the key to release.
   * @return this fixture.
   * @throws IllegalArgumentException if any of the given code is not a valid key code.
   * @throws IllegalStateException if this fixture's {@code Component} is disabled.
   * @throws IllegalStateException if this fixture's {@code Component} is not showing on the screen.
   * @see java.awt.event.KeyEvent
   */
  public final @Nonnull S releaseKey(int keyCode) {
    driver.releaseKey(target(), keyCode);
    return myself();
  }

  /**
   * Asserts that this fixture's {@code Component} has input focus.
   *
   * @return this fixture.
   * @throws AssertionError if this fixture's {@code Component} does not have input focus.
   */
  public final @Nonnull S requireFocused() {
    driver.requireFocused(target());
    return myself();
  }

  /**
   * Asserts that this fixture's {@code Component} is enabled.
   *
   * @return this fixture.
   * @throws AssertionError if this fixture's {@code Component} is disabled.
   */
  public final @Nonnull S requireEnabled() {
    driver.requireEnabled(target());
    return myself();
  }

  /**
   * Asserts that this fixture's {@code Component} is enabled.
   *
   * @param timeout the time this fixture will wait for the component to be enabled.
   * @return this fixture.
   * @throws WaitTimedOutError if this fixture's {@code Component} is never enabled.
   */
  public final @Nonnull S requireEnabled(@Nonnull Timeout timeout) {
    driver.requireEnabled(target(), timeout);
    return myself();
  }

  /**
   * Asserts that this fixture's {@code Component} is disabled.
   *
   * @return this fixture.
   * @throws AssertionError if this fixture's {@code Component} is enabled.
   */
  public final @Nonnull S requireDisabled() {
    driver.requireDisabled(target());
    return myself();
  }

  /**
   * Asserts that this fixture's {@code Component} is visible.
   *
   * @return this fixture.
   * @throws AssertionError if this fixture's {@code Component} is not visible.
   */
  public final @Nonnull S requireVisible() {
    driver.requireVisible(target());
    return myself();
  }

  /**
   * Asserts that this fixture's {@code Component} is not visible.
   *
   * @return this fixture.
   * @throws AssertionError if this fixture's {@code Component} is visible.
   */
  public final @Nonnull S requireNotVisible() {
    driver.requireNotVisible(target());
    return myself();
  }

  /**
   * Returns whether showing components are the only ones participating in a component lookup. The returned value is
   * obtained from the {@link Settings#componentLookupScope() component lookup scope} stored in this fixture's
   * {@link Robot}.
   *
   * @return {@code true} if only showing components can participate in a component lookup, {@code false} otherwise.
   */
  protected boolean requireShowing() {
    return requireShowing(robot());
  }

  private static boolean requireShowing(@Nonnull Robot robot) {
    return robot.settings().componentLookupScope().requireShowing();
  }

  /**
   * @return a fixture that checks properties of the font of this fixture's {@code Component}.
   */
  public final @Nonnull FontFixture font() {
    Font font = driver.fontOf(target);
    return new FontFixture(font, propertyName(target(), FONT_PROPERTY));
  }

  /**
   * @return a fixture that checks properties of the background color of this fixture's {@code Component}.
   */
  public final @Nonnull ColorFixture background() {
    Color background = driver.backgroundOf(target);
    return new ColorFixture(background, propertyName(target(), BACKGROUND_PROPERTY));
  }

  /**
   * @return a fixture that checks properties of the foreground color of this fixture's {@code Component}.
   */
  public final @Nonnull ColorFixture foreground() {
    Color foreground = driver.foregroundOf(target);
    return new ColorFixture(foreground, propertyName(target(), FOREGROUND_PROPERTY));
  }

  /**
   * Indicates whether this fixture's {@code Component} is enabled.
   *
   * @return {@code true} if this fixture's {@code Component} is enabled; {@code false} otherwise.
   * @since 1.3
   */
  public final boolean isEnabled() {
    return ComponentEnabledQuery.isEnabled(target());
  }

  /**
   * Returns this fixture's {@code Component} casted to the given sub-type.
   *
   * @param type the type that the managed {@code Component} will be casted to.
   * @return this fixture's {@code Component} casted to the given sub-type.
   * @throws AssertionError if this fixture's {@code Component} is not an instance of the given type.
   */
  public final @Nonnull <T extends C> C targetCastedTo(@Nonnull Class<T> type) {
    assertThat(target).as(format(target)).isInstanceOf(type);
    return type.cast(target);
  }

  /**
   * <p>
   * Returns the GUI component in this fixture.
   * </p>
   * <p>
   * <strong>Note:</strong> Access to the GUI component returned by this method <em>must</em> be executed in the event
   * dispatch thread (EDT.) To do so, please execute a {@link org.fest.swing.edt.GuiQuery GuiQuery} or
   * {@link org.fest.swing.edt.GuiTask GuiTask} (depending on what you need to do,) inside a {@link GuiActionRunner}. To
   * learn more about Swing threading, please read the <a
   * href="http://java.sun.com/javase/6/docs/api/javax/swing/package-summary.html#threading" target="_blank">Swing
   * Threading Policy</a>.
   * </p>
   *
   * @return the GUI component in this fixture.
   */
  public final @Nonnull C target() {
    return target;
  }

  /** @return the {@link Robot} that simulates user events on {@link #target()}. */
  protected final @Nonnull Robot robot() {
    return robot;
  }

  /**
   * @return {@code this} casted to the "self type".
   */
  protected final @Nonnull S myself() {
    return myself;
  }
}
