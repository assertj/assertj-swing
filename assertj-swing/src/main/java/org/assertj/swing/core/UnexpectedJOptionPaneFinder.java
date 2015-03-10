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
package org.assertj.swing.core;

import static org.assertj.core.api.Fail.fail;
import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.swing.format.Formatting.format;

import java.awt.Component;
import java.util.List;

import javax.annotation.Nonnull;
import javax.swing.JOptionPane;

import org.assertj.swing.annotation.RunsInEDT;

/**
 * Finds {@code JOptionPane}s that are showing up on the screen and are not expected.
 * 
 * @author Alex Ruiz
 */
class UnexpectedJOptionPaneFinder {
  static final ComponentMatcher OPTION_PANE_MATCHER = new TypeMatcher(JOptionPane.class, true);

  private final ComponentFinder finder;

  UnexpectedJOptionPaneFinder(@Nonnull ComponentFinder finder) {
    this.finder = finder;
  }

  @RunsInEDT
  void requireNoJOptionPaneIsShowing() {
    List<Component> found = findAll(OPTION_PANE_MATCHER);
    if (!found.isEmpty()) {
      unexpectedJOptionPanesFound(found);
    }
  }

  private List<Component> findAll(@Nonnull ComponentMatcher m) {
    return newArrayList(finder.findAll(m));
  }

  private void unexpectedJOptionPanesFound(@Nonnull List<Component> found) {
    StringBuilder message = new StringBuilder();
    message.append("Expecting no JOptionPane to be showing, but found:<[");
    int size = found.size();
    for (int i = 0; i < size; i++) {
      message.append(format(found.get(i)));
      if (i != size - 1) {
        message.append(", ");
      }
    }
    message.append("]>");
    fail(message.toString());
  }
}
