/*
 * Created on Sep 2, 2008
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
package org.fest.swing.driver;

import static org.fest.assertions.Assertions.assertThat;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import org.junit.Test;

/**
 * Tests for {@link JScrollPaneDriver#horizontalScrollBarIn(JScrollPane)}.
 * 
 * @author Yvonne Wang
 */
public class JScrollPaneDriver_horizontalScrollBarIn_Test extends JScrollPaneDriver_TestCase {
  @Test
  public void should_return_horizontal_JScrollBar() {
    JScrollBar scrollBar = driver.horizontalScrollBarIn(window.scrollPane);
    assertThat(scrollBar).isSameAs(window.horizontalScrollBar);
  }
}
