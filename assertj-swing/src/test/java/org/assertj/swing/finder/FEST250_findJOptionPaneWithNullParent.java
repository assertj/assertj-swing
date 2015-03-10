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
package org.assertj.swing.finder;

import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.finder.JOptionPaneFinder.findOptionPane;

import javax.swing.JOptionPane;

import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.fixture.JOptionPaneFixture;
import org.assertj.swing.test.core.EDTSafeTestCase;
import org.assertj.swing.test.swing.JOptionPaneLauncher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for bug <a href="http://jira.codehaus.org/browse/FEST-250" target="_blank">FEST-250</a>
 * 
 * @author Alex Ruiz
 */
public class FEST250_findJOptionPaneWithNullParent extends EDTSafeTestCase {
  private Robot robot;

  @Before
  public void setUp() {
    JOptionPane optionPane = execute(new GuiQuery<JOptionPane>() {
      @Override
      protected JOptionPane executeInEDT() {
        return new JOptionPane("Hello World!");
      }
    });
    JOptionPaneLauncher.launch(optionPane);
    robot = BasicRobot.robotWithCurrentAwtHierarchy();
  }

  @After
  public void tearDown() {
    robot.cleanUp();
  }

  @Test
  public void should_Find_JOptionPane() {
    JOptionPaneFixture optionPane = findOptionPane().using(robot);
    optionPane.requireMessage("Hello World!");
  }
}
