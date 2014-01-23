/*
 * Created on Jul 8, 2007
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
 * Copyright @2007-2013 the original author or authors.
 */
package org.fest.swing.test.awt;

import java.awt.Point;

/**
 * A {@link Point} with some extra utility methods.
 *
 * @author Yvonne Wang
 */
public class FluentPoint extends Point {
  /**
   * Creates a new {@link FluentPoint}.
   *
   * @param p the source point.
   */
  public FluentPoint(Point p) {
    super(p);
  }

  /**
   * Adds the given value to the X coordinate of this point.
   *
   * @param value the value to add.
   * @return this point.
   */
  public FluentPoint addToX(int value) {
    x += value;
    return this;
  }

  /**
   * Adds the given value to the Y coordinate of this point.
   *
   * @param value the value to add.
   * @return this point.
   */
  public FluentPoint addToY(int value) {
    y += value;
    return this;
  }
}
