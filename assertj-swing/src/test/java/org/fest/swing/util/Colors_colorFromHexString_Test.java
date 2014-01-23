/*
 * Created on Apr 15, 2008
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
package org.fest.swing.util;

import static java.awt.Color.BLACK;
import static java.awt.Color.BLUE;
import static java.awt.Color.GREEN;
import static java.awt.Color.RED;
import static java.awt.Color.YELLOW;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Lists.newArrayList;

import java.awt.Color;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link Colors#colorFromHexString(String)}.
 *
 * @author Alex Ruiz
 */
@RunWith(Parameterized.class)
public class Colors_colorFromHexString_Test {
  private final String hexString;
  private final Color color;

  @Parameters
  public static Collection<Object[]> colors() {
    return newArrayList(new Object[][] {
        { "000000", BLACK },
        { "FF0000", RED },
        { "00FF00", GREEN },
        { "0000FF", BLUE },
        { "FFFF00", YELLOW }
      });
  }

  public Colors_colorFromHexString_Test(String hexString, Color color) {
    this.hexString = hexString;
    this.color = color;
  }

  @Test
  public void should_return_color_from_hex_String() {
    Color actual = Colors.colorFromHexString(hexString);
    assertThat(actual).isEqualTo(color);
  }
}
