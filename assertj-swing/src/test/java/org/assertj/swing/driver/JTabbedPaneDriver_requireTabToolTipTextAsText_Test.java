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
package org.assertj.swing.driver;

import org.junit.Test;

import java.util.regex.Pattern;

import static org.assertj.swing.data.Index.atIndex;

/**
 * Tests for {@link JTabbedPaneDriver#requireTabToolTipText(javax.swing.JTabbedPane, String, org.assertj.swing.data.Index)}.
 * 
 * @author William Bakker
 */
public class JTabbedPaneDriver_requireTabToolTipTextAsText_Test extends JTabbedPaneDriver_TestCase {
  @Test
  public void should_Fail_If_ToolTipText_Is_Not_Equal_To_Expected() {
    thrown.expectAssertionError("toolTipTextAt", "tip1", Pattern.compile("Hello"));
    driver.requireTabToolTipText(tabbedPane, "Hello", atIndex(0));
  }

  @Test
  public void should_Pass_If_ToolTipText_Is_Equal_To_Expected() {
    driver.requireTabToolTipText(tabbedPane, "tip1", atIndex(0));
  }

  @Test
  public void should_Pass_If_ToolTipText_Matches_Pattern() {
    driver.requireTabToolTipText(tabbedPane, "t.*", atIndex(0));
  }
}
