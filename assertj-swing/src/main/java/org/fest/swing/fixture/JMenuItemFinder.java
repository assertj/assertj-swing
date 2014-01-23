/*
 * Created on Jul 18, 2008
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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.format.Formatting.format;

import java.awt.Component;
import java.awt.Container;

import javax.annotation.Nonnull;
import javax.swing.JMenuItem;

import org.fest.swing.core.ComponentMatcher;
import org.fest.swing.core.Robot;
import org.fest.swing.driver.JMenuItemMatcher;

/**
 * Looks up of {@code JMenuItem}s.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class JMenuItemFinder {
  private final Robot robot;
  private final Container target;

  JMenuItemFinder(@Nonnull Robot robot, @Nonnull Container target) {
    this.robot = robot;
    this.target = target;
  }

  @Nonnull JMenuItem menuItemWithPath(@Nonnull String...path) {
    ComponentMatcher m = new JMenuItemMatcher(path);
    Component item = robot.finder().find(target, m);
    assertThat(item).as(format(item)).isInstanceOf(JMenuItem.class);
    return (JMenuItem)item;
  }
}
