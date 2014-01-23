/*
 * Created on Feb 25, 2008
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
package org.fest.swing.driver;

import static javax.swing.SwingConstants.HORIZONTAL;
import static javax.swing.SwingConstants.VERTICAL;
import static org.fest.util.Lists.newArrayList;

import java.util.Collection;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for {@link JSliderDriver#slide(javax.swing.JSlider, int)}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JSliderDriver_slide_Test extends JSliderDriver_TestCase {
  private final int value;

  @Parameters
  public static Collection<Object[]> valueProvider() {
    return newArrayList(new Object[][] {
        { HORIZONTAL, 5 },
        { VERTICAL, 5 },
        { HORIZONTAL, 10 },
        { VERTICAL, 10 },
        { HORIZONTAL, 28 },
        { VERTICAL, 28 },
        { HORIZONTAL, 20 },
        { VERTICAL, 20 }
      });
  }

  public JSliderDriver_slide_Test(int orientation, int value) {
    super(orientation);
    this.value = value;
  }

  @Test
  public void should_slide_to_value() {
    showWindow();
    driver.slide(slider, value);
    assertThatSliderValueIs(value);
  }
}
