/*
 * Created on Jul 31, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.swing.monitor;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Insets;
import java.awt.Point;
import java.awt.Window;

import org.fest.swing.test.core.EDTSafeTestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link WindowMetrics#absoluteCenterOf(Window)}.
 *
 * @author Alex Ruiz
 */
public class WindowMetrics_absoluteCenterOf_Test extends EDTSafeTestCase {
  private int x;
  private int y;
  private int left;
  private int right;
  private int top;
  private int bottom;
  private int width;
  private int height;
  private Window window;

  @Before
  public void setUp() {
    x = 600;
    y = 300;
    left = 20;
    right = 30;
    top = 40;
    bottom = 10;
    width = 200;
    height = 100;
    window = mock(Window.class);
  }

  @Test
  public void should_calculate_center() {
    when(window.getInsets()).thenReturn(new Insets(top, left, bottom, right));
    when(window.getX()).thenReturn(x);
    when(window.getY()).thenReturn(y);
    when(window.getWidth()).thenReturn(width);
    when(window.getHeight()).thenReturn(height);
    int realWidth = width - (left + right);
    int realHeight = height - (top + bottom);
    int expectedX = x + left + (realWidth  /2);
    int expectedY = y + top +(realHeight / 2);
    assertThat(WindowMetrics.absoluteCenterOf(window)).isEqualTo(new Point(expectedX, expectedY));
  }
}
