/*
 * Created on Aug 25, 2009
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

import java.awt.Window;

import org.fest.assertions.AssertExtension;

/**
 * Assertions related to the internal state of {@link Windows} with respect a particular {@code Window}.
 *
 * @author Alex Ruiz
 */
class WindowStateAssert implements AssertExtension {
  private final Windows windows;
  private final Window target;

  WindowStateAssert(Windows windows, Window target) {
    this.windows = windows;
    this.target = target;
  }

  WindowStateAssert isClosed() {
    assertThat(isWindowClosed()).isEqualTo(true);
    return this;
  }

  WindowStateAssert isNotClosed() {
    assertThat(isWindowClosed()).isEqualTo(false);
    return this;
  }

  private boolean isWindowClosed() {
    return windows.closed.containsKey(target);
  }

  WindowStateAssert isPending() {
    assertThat(isWindowPending()).isEqualTo(true);
    return this;
  }

  WindowStateAssert isNotPending() {
    assertThat(isWindowPending()).isEqualTo(false);
    return this;
  }

  private boolean isWindowPending() {
    return windows.pending.containsKey(target);
  }

  WindowStateAssert isOpen() {
    assertThat(isWindowOpen()).isEqualTo(true);
    return this;
  }

  WindowStateAssert isNotOpen() {
    assertThat(isWindowOpen()).isEqualTo(false);
    return this;
  }

  private boolean isWindowOpen() {
    return windows.open.containsKey(target);
  }

  WindowStateAssert isHidden() {
    assertThat(isWindowHidden()).isEqualTo(true);
    return this;
  }

  WindowStateAssert isNotHidden() {
    assertThat(isWindowHidden()).isEqualTo(false);
    return this;
  }

  private boolean isWindowHidden() {
    return windows.hidden.containsKey(target);
  }

  WindowStateAssert isReady() {
    isNotClosed();
    isNotHidden();
    isOpen();
    isNotPending();
    return this;
  }
}
