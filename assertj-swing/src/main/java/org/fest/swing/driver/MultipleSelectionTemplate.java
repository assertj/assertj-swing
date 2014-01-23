/*
 * Created on May 8, 2008
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

import static org.fest.swing.util.Platform.controlOrCommandKey;

import javax.annotation.Nonnull;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.Robot;

/**
 * Simulates multiple selection on a GUI component.
 *
 * @author Yvonne Wang
 */
abstract class MultipleSelectionTemplate {
  private final Robot robot;

  MultipleSelectionTemplate(@Nonnull Robot robot) {
    this.robot = robot;
  }

  abstract int elementCount();

  @RunsInEDT
  final void multiSelect() {
    int elementCount = elementCount();
    selectElement(0);
    if (elementCount == 1) {
      return;
    }
    int key = controlOrCommandKey();
    robot.pressKey(key);
    try {
      for (int i = 1; i < elementCount; i++) {
        selectElement(i);
      }
    } finally {
      robot.releaseKey(key);
    }
  }

  abstract void selectElement(int index);
}
