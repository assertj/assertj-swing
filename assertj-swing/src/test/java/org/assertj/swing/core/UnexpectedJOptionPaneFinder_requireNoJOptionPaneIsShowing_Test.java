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

import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.swing.core.UnexpectedJOptionPaneFinder.OPTION_PANE_MATCHER;
import static org.assertj.swing.test.ExpectedException.none;
import static org.assertj.swing.test.builder.JOptionPanes.optionPane;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Component;
import java.util.List;

import org.assertj.swing.test.ExpectedException;
import org.assertj.swing.test.core.EDTSafeTestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link UnexpectedJOptionPaneFinder#requireNoJOptionPaneIsShowing}.
 * 
 * @author Alex Ruiz
 */
public class UnexpectedJOptionPaneFinder_requireNoJOptionPaneIsShowing_Test extends EDTSafeTestCase {
  private ComponentFinder delegate;
  private UnexpectedJOptionPaneFinder finder;
  @Rule
  public ExpectedException thrown = none();

  @Before
  public void setUp() {
    delegate = mock(ComponentFinder.class);
    finder = new UnexpectedJOptionPaneFinder(delegate);
  }

  @Test
  public void should_Pass_If_There_Are_Not_Any_JOptionPanes_Showing() {
    List<Component> components = newArrayList();
    when(delegate.findAll(OPTION_PANE_MATCHER)).thenReturn(components);
    finder.requireNoJOptionPaneIsShowing();
  }

  @Test
  public void should_Fail_If_There_Is_A_JOptionPane_Showing() {
    List<Component> found = newArrayList();
    found.add(optionPane().createNew());
    when(delegate.findAll(OPTION_PANE_MATCHER)).thenReturn(found);
    thrown.expectAssertionError("Expecting no JOptionPane to be showing");
    finder.requireNoJOptionPaneIsShowing();
  }
}
