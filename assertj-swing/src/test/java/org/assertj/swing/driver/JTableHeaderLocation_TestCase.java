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

import static org.assertj.swing.edt.GuiActionRunner.execute;

import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.table.JTableHeader;

import org.assertj.swing.annotation.RunsInEDT;

/**
 * Base test case for {@link JTableHeaderLocation}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class JTableHeaderLocation_TestCase extends JTableHeaderBasedTestCase {
  JTableHeaderLocation location;

  @Override
  final void setUpTestTarget() {
    location = new JTableHeaderLocation();
  }

  @RunsInEDT
  final Point pointAt(int index) {
    return pointAt(location, tableHeader, index);
  }

  @RunsInEDT
  private static Point pointAt(final JTableHeaderLocation location, final JTableHeader tableHeader, final int index) {
    return execute(() -> location.pointAt(tableHeader, index));
  }

  @RunsInEDT
  final Point expectedPoint(int index) {
    Rectangle r = rectOf(tableHeader, index);
    return new Point(r.x + r.width / 2, r.y + r.height / 2);
  }

  @RunsInEDT
  private static Rectangle rectOf(final JTableHeader tableHeader, final int index) {
    return execute(() -> tableHeader.getHeaderRect(index));
  }
}
