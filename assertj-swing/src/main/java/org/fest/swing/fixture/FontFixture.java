/*
 * Created on Apr 16, 2008
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
package org.fest.swing.fixture;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Preconditions.checkNotNull;
import static org.fest.util.Strings.concat;
import static org.fest.util.Strings.isNullOrEmpty;

import java.awt.Font;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.fest.assertions.BasicDescription;
import org.fest.assertions.Description;

/**
 * Verifies the state of {@code Font}s.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class FontFixture {
  private static final String PROPERTY_SEPARATOR = " - ";

  private static final String BOLD_PROPERTY = "bold";
  private static final String FAMILY_PROPERTY = "family";
  private static final String ITALIC_PROPERTY = "italic";
  private static final String NAME_PROPERTY = "name";
  private static final String PLAIN_PROPERTY = "plain";
  private static final String SIZE_PROPERTY = "size";

  private final Font target;
  private final Description description;

  /**
   * Creates a new {@link FontFixture}.
   * 
   * @param target the font to manage.
   * @throws NullPointerException if {@code target} is {@code null}.
   */
  public FontFixture(@Nonnull Font target) {
    this(target, (Description) null);
  }

  /**
   * Creates a new {@link FontFixture}.
   * 
   * @param target the font to manage.
   * @param description this fixture's description.
   * @throws NullPointerException if {@code target} is {@code null}.
   */
  public FontFixture(@Nonnull Font target, @Nonnull String description) {
    this(target, new BasicDescription(description));
  }

  /**
   * Creates a new {@link FontFixture}.
   * 
   * @param target the font to manage.
   * @param description this fixture's description.
   * @throws NullPointerException if {@code target} is {@code null}.
   */
  public FontFixture(@Nonnull Font target, @Nullable Description description) {
    this.target = checkNotNull(target);
    this.description = description;
  }

  /**
   * Verifies that the family name of this fixture's font is equal to the given one.
   * 
   * @param family the expected family name.
   * @return this assertion object.
   * @throws AssertionError if the family name of this fixture's font is not equal to the given one.
   * @see Font#getFamily()
   */
  public @Nonnull FontFixture requireFamily(@Nonnull String family) {
    assertThat(target.getFamily()).as(property(FAMILY_PROPERTY)).isEqualTo(family);
    return this;
  }

  /**
   * Verifies that the logical name of this fixture's font is equal to the given one.
   * 
   * @param name the expected logical name.
   * @return this assertion object.
   * @throws AssertionError if the logical name of this fixture's font is not equal to the given one.
   * @see Font#getName()
   */
  public @Nonnull FontFixture requireName(@Nonnull String name) {
    assertThat(target.getName()).as(property(NAME_PROPERTY)).isEqualTo(name);
    return this;
  }

  /**
   * Verifies that the point size of this fixture's font is equal to the given one.
   * 
   * @param size the expected point size.
   * @return this assertion object.
   * @throws AssertionError if the point size of this fixture's font is not equal to the given one.
   * @see Font#getSize()
   */
  public @Nonnull FontFixture requireSize(int size) {
    assertThat(target.getSize()).as(property(SIZE_PROPERTY)).isEqualTo(size);
    return this;
  }

  /**
   * Verifies that this fixture's font is bold.
   * 
   * @return this assertion object.
   * @throws AssertionError if this fixture's font is not bold.
   * @see Font#isBold()
   */
  public @Nonnull FontFixture requireBold() {
    return requireBold(true);
  }

  /**
   * Verifies that this fixture's font is not bold.
   * 
   * @return this assertion object.
   * @throws AssertionError if this fixture's font is bold.
   * @see Font#isBold()
   */
  public @Nonnull FontFixture requireNotBold() {
    return requireBold(false);
  }

  private @Nonnull FontFixture requireBold(boolean bold) {
    assertThat(target.isBold()).as(property(BOLD_PROPERTY)).isEqualTo(bold);
    return this;
  }

  /**
   * Verifies that this fixture's font is italic.
   * 
   * @return this assertion object.
   * @throws AssertionError if this fixture's font is not italic.
   * @see Font#isItalic()
   */
  public @Nonnull FontFixture requireItalic() {
    return requireItalic(true);
  }

  /**
   * Verifies that this fixture's font is not italic.
   * 
   * @return this assertion object.
   * @throws AssertionError if this fixture's font is italic.
   * @see Font#isItalic()
   */
  public @Nonnull FontFixture requireNotItalic() {
    return requireItalic(false);
  }

  private @Nonnull FontFixture requireItalic(boolean italic) {
    assertThat(target.isItalic()).as(property(ITALIC_PROPERTY)).isEqualTo(italic);
    return this;
  }

  /**
   * Verifies that this fixture's font is plain.
   * 
   * @return this assertion object.
   * @throws AssertionError if this fixture's font is not plain.
   * @see Font#isPlain()
   */
  public @Nonnull FontFixture requirePlain() {
    return requirePlain(true);
  }

  /**
   * Verifies that this fixture's font is not plain.
   * 
   * @return this assertion object.
   * @throws AssertionError if this fixture's font is plain.
   * @see Font#isPlain()
   */
  public @Nonnull FontFixture requireNotPlain() {
    return requirePlain(false);
  }

  private @Nonnull FontFixture requirePlain(boolean plain) {
    assertThat(target.isBold()).as(property(PLAIN_PROPERTY)).isEqualTo(plain);
    return this;
  }

  private @Nonnull String property(@Nonnull String s) {
    if (!isNullOrEmpty(description())) {
      return concat(description.value(), PROPERTY_SEPARATOR, s);
    }
    return s;
  }

  /**
   * @return this fixture's font.
   */
  public @Nonnull Font target() {
    return target;
  }

  /**
   * @return this fixture's description.
   */
  public final @Nullable String description() {
    return description != null ? description.value() : null;
  }
}
