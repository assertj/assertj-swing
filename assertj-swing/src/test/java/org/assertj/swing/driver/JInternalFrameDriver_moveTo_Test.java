/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
package org.assertj.swing.driver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.driver.ComponentLocationQuery.locationOf;

import java.awt.Point;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.test.awt.FluentPoint;
import org.junit.Test;

/**
 * Tests for {@link JInternalFrameDriver#move(javax.swing.JInternalFrame, java.awt.Point)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JInternalFrameDriver_moveTo_Test extends JInternalFrameDriver_TestCase {
  @Test
  public void should_Move_JInternalFrame() {
    showWindow();
    Point p = internalFrameLocation().addToX(10).addToY(10);
    driver.move(internalFrame, p);
    assertThat(internalFrameLocation()).isEqualTo(p);
  }

  @RunsInEDT
  private FluentPoint internalFrameLocation() {
    return new FluentPoint(locationOf(internalFrame));
  }
}
