/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2018 the original author or authors.
 */
package org.assertj.swing.driver;

import static org.assertj.swing.util.Platform.controlOrCommandKey;

import java.util.function.Consumer;

import javax.annotation.Nonnull;

import org.assertj.core.api.Fail;
import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.core.Robot;

/**
 * Simulates multiple selection on a GUI component.
 *
 * @author Yvonne Wang
 * @author Christian Rösch
 */
abstract class MultipleSelectionTemplate {
  private final Robot robot;

  MultipleSelectionTemplate(@Nonnull Robot robot) {
    this.robot = robot;
  }

  abstract int elementCount();

  @RunsInEDT
  final void multiSelect() {
    multiSelect(i -> selectElement(i), true);
  }

  @RunsInEDT
  final void multiUnselect() {
    multiSelect(i -> unselectElement(i), false);
  }

  @RunsInEDT
  final void multiSelect(Consumer<Integer> action, boolean firstWithoutCommandKey) {
    int elementCount = elementCount();
    if (firstWithoutCommandKey) {
      action.accept(0);
      if (elementCount == 1) {
        return;
      }
    }
    int key = controlOrCommandKey();
    robot.pressKeyWhileRunning(key, () -> {
      for (int i = firstWithoutCommandKey ? 1 : 0; i < elementCount; i++) {
        action.accept(i);
      }
    });
  }

  void selectElement(int index) {
    fail();
  }

  void unselectElement(int index) {
    fail();
  }

  private void fail() {
    Fail.fail("Unexpected method call.");
  }
}
