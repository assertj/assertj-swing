/*
 * Created on Mar 24, 2008
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
package org.fest.swing.format;

import static javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JLists.list;

import javax.swing.JList;

import org.fest.swing.test.core.EDTSafeTestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link JListFormatter#format(java.awt.Component)}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JListFormatter_format_Test extends EDTSafeTestCase {
  private JList list;
  private JListFormatter formatter;

  @Before
  public void setUp() {
    list = list().withItems("One", 2, "Three", 4).withName("list")
                 .withSelectedIndices(0, 1)
                 .withSelectionMode(MULTIPLE_INTERVAL_SELECTION)
                 .createNew();
    formatter = new JListFormatter();
  }

  @Test
  public void should_format_JList() {
    String formatted = formatter.format(list);
    assertThat(formatted).contains("javax.swing.JList").contains("name='list'").contains("selectedValues=['One', 2]")
    .contains("contents=['One', 2, 'Three', 4]").contains("selectionMode=MULTIPLE_INTERVAL_SELECTION")
    .contains("enabled=true").contains("visible=true").contains("showing=false");
  }
}
