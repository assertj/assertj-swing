/*
 * Created on May 7, 2008
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

import static java.awt.Color.BLUE;
import static org.fest.assertions.Assertions.assertThat;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link ColorFixture#description()}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ColorFixture_description_Test {
  private Color color;
  private ColorFixture fixture;

  @Before
  public void setUp() {
    color = BLUE;
    fixture = new ColorFixture(color);
  }

  @Test
  public void shouldReturnNullIfDescriptionIsNull() {
    assertThat(fixture.description()).isNull();
  }

  @Test
  public void shouldReturnDescription() {
    fixture = new ColorFixture(color, "test");
    assertThat(fixture.description()).isEqualTo("test");
  }
}
