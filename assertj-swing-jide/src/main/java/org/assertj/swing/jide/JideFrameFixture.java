/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * Copyright @2008-2010 the original author or authors.
 */

package org.fest.swing.jide;

import com.jidesoft.action.CommandBar;
import com.jidesoft.combobox.DateComboBox;
import com.jidesoft.combobox.ListComboBox;
import com.jidesoft.grid.JideTable;
import com.jidesoft.grid.TableScrollPane;
import com.jidesoft.pane.CollapsiblePane;
import com.jidesoft.status.StatusBar;
import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.core.Robot;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.format.Formatting;
import org.fest.swing.jide.action.CommandBarFixture;
import org.fest.swing.jide.action.format.CommandBarFormatter;
import org.fest.swing.jide.components.CollapsiblePaneFixture;
import org.fest.swing.jide.components.JideStatusBarFixture;
import org.fest.swing.jide.grids.DateComboBoxFixture;
import org.fest.swing.jide.grids.JideTableFixture;
import org.fest.swing.jide.grids.ListComboBoxFixture;
import org.fest.swing.jide.grids.TableScrollPaneFixture;

import java.awt.*;

/**
 * A {@link FrameFixture} that allows Jide specific fixtures to be obtained from a {@link
 * javax.swing.JFrame}'s children.
 *
 * @author Peter Murray
 */
public class JideFrameFixture extends FrameFixture {

  static {
    Formatting.register(new CommandBarFormatter());
  }

  public JideFrameFixture(Frame f) {
    super(f);
  }

  public JideFrameFixture(Robot robot, Frame f) {
    super(robot, f);
  }

  public JideFrameFixture(String name) {
    super(name);
  }

  public JideFrameFixture(Robot robot, String name) {
    super(robot, name);
  }

  public ListComboBoxFixture listComboBox(String name) {
    return new ListComboBoxFixture(robot, findByName(name, ListComboBox.class));
  }

  public ListComboBoxFixture listComboBox(
          GenericTypeMatcher<? extends ListComboBox> matcher) {
    return new ListComboBoxFixture(robot, find(matcher));
  }

  public DateComboBoxFixture dateComboBox(String name) {
    return new DateComboBoxFixture(robot, findByName(name, DateComboBox.class));
  }

//  public DockingRootPaneContainerFixture dockingRootPaneContainer(String name) {
//    return new DockingRootPaneContainerFixture(robot,
//                                               findByName(name,
//                                                          DockingRootPaneContainer.class));
//  }

  public TableScrollPaneFixture tableScrollPane(String name) {
    return new TableScrollPaneFixture(robot,
                                      findByName(name, TableScrollPane.class));
  }

  public JideTableFixture jideTable(String name) {
    return new JideTableFixture(robot,
                                findByName(name, JideTable.class));
  }

//  public GlassPaneFixture jComponent(String name) {
//    return new GlassPaneFixture(robot, findByName(name, JComponent.class));
//  }

  public CommandBarFixture commandBar(String name) {
    return new CommandBarFixture(robot, findByName(name, CommandBar.class));
  }

  public JideStatusBarFixture statusBar(String name) {
    return new JideStatusBarFixture(robot, findByName(name, StatusBar.class));
  }

  public JideStatusBarFixture statusBar() {
    return new JideStatusBarFixture(robot, findByType(StatusBar.class));
  }

  public CollapsiblePaneFixture collapsiblePane() {
    return new CollapsiblePaneFixture(robot, findByType(CollapsiblePane.class));
  }

  public CollapsiblePaneFixture collapsiblePane(String name) {
    return new CollapsiblePaneFixture(robot, findByName(name, CollapsiblePane.class));
  }

//  public Condition condition(ConditionType condition, String name) {
//    switch (condition) {
//      case VISIBLE_COMPONENT:
//        return new ComponentVisibleCondition(finder(),
//                                             new NameMatcher(name, requireShowing()));
//      case FOCUSED_COMPONENT:
//        return new ComponentFocusedCondition(finder(),
//                                             new NameMatcher(name, requireShowing()));
//      case FOCUS_IN_CONTAINER:
//        return new FocusInContainerCondition(finder(),
//                                             new NameMatcher(name, requireShowing()));
//      default: {
//        throw new UnsupportedOperationException("Unsupported condition type, "
//                                                + condition);
//      }
//    }
//  }
//
//  public Condition condition(ConditionType condition,
//                             String name,
//                             Class<? extends Component> type) {
//    switch (condition) {
//      case VISIBLE_COMPONENT:
//        return new ComponentVisibleCondition(finder(),
//                                             new NameMatcher(name,
//                                                             type,
//                                                             requireShowing()));
//      case FOCUSED_COMPONENT:
//        return new ComponentFocusedCondition(finder(),
//                                             new NameMatcher(name,
//                                                             type,
//                                                             requireShowing()));
//      case FOCUS_IN_CONTAINER:
//        return new FocusInContainerCondition(finder(),
//                                             new NameMatcher(name,
//                                                             type,
//                                                             requireShowing()));
//      default: {
//        throw new UnsupportedOperationException("Unsupported condition type, "
//                                                + condition);
//      }
//    }
//  }
}
