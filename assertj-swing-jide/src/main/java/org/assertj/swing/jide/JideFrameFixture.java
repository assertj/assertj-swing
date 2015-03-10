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
package org.assertj.swing.jide;

import java.awt.Frame;

import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.core.Robot;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.format.Formatting;
import org.assertj.swing.jide.action.CommandBarFixture;
import org.assertj.swing.jide.action.format.CommandBarFormatter;
import org.assertj.swing.jide.components.CollapsiblePaneFixture;
import org.assertj.swing.jide.components.JideStatusBarFixture;
import org.assertj.swing.jide.grids.DateComboBoxFixture;
import org.assertj.swing.jide.grids.JideTableFixture;
import org.assertj.swing.jide.grids.ListComboBoxFixture;
import org.assertj.swing.jide.grids.TableScrollPaneFixture;

import com.jidesoft.action.CommandBar;
import com.jidesoft.combobox.DateComboBox;
import com.jidesoft.combobox.ListComboBox;
import com.jidesoft.grid.JideTable;
import com.jidesoft.grid.TableScrollPane;
import com.jidesoft.pane.CollapsiblePane;
import com.jidesoft.status.StatusBar;

/**
 * A {@link FrameFixture} that allows Jide specific fixtures to be obtained from a {@link javax.swing.JFrame}'s
 * children.
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
	return new ListComboBoxFixture(robot(), findByName(name, ListComboBox.class));
  }

  public ListComboBoxFixture listComboBox(
	                                      GenericTypeMatcher<? extends ListComboBox> matcher) {
	return new ListComboBoxFixture(robot(), find(matcher));
  }

  public DateComboBoxFixture dateComboBox(String name) {
	return new DateComboBoxFixture(robot(), findByName(name, DateComboBox.class));
  }

  public TableScrollPaneFixture tableScrollPane(String name) {
	return new TableScrollPaneFixture(robot(),
	                                  findByName(name, TableScrollPane.class));
  }

  public JideTableFixture jideTable(String name) {
	return new JideTableFixture(robot(),
	                            findByName(name, JideTable.class));
  }

  public CommandBarFixture commandBar(String name) {
	return new CommandBarFixture(robot(), findByName(name, CommandBar.class));
  }

  public JideStatusBarFixture statusBar(String name) {
	return new JideStatusBarFixture(robot(), findByName(name, StatusBar.class));
  }

  public JideStatusBarFixture statusBar() {
	return new JideStatusBarFixture(robot(), findByType(StatusBar.class));
  }

  public CollapsiblePaneFixture collapsiblePane() {
	return new CollapsiblePaneFixture(robot(), findByType(CollapsiblePane.class));
  }

  public CollapsiblePaneFixture collapsiblePane(String name) {
	return new CollapsiblePaneFixture(robot(), findByName(name, CollapsiblePane.class));
  }
}
