/*
 * Created on Dec 13, 2009
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
package org.fest.swing.finder;

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.finder.JOptionPaneFinder.findOptionPane;

import javax.swing.JOptionPane;

import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.JOptionPaneFixture;
import org.fest.swing.test.core.EDTSafeTestCase;
import org.fest.swing.test.swing.JOptionPaneLauncher;
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
  public void should_find_JOptionPane() {
    JOptionPaneFixture optionPane = findOptionPane().using(robot);
    optionPane.requireMessage("Hello World!");
  }
}
