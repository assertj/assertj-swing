/*
 * Created on Dec 22, 2007
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
 * Copyright @2007-2013 the original author or authors.
 */
package org.fest.swing.format;

import static org.fest.swing.test.builder.JComboBoxes.comboBox;

import javax.swing.JButton;

import org.fest.swing.test.core.EDTSafeTestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link IntrospectionComponentFormatter#format(java.awt.Component)}.
 * 
 * @author Alex Ruiz
 */
public class IntrospectionComponentFormatter_format_withInvalidInput_Test extends EDTSafeTestCase {
  private IntrospectionComponentFormatter formatter;

  @Before
  public void setUp() {
    formatter = new IntrospectionComponentFormatter(JButton.class, "name", "text");
  }

  @Test(expected = IllegalArgumentException.class)
  public void should_throw_error_if_Component_belongs_to_unsupported_type() {
    formatter.format(comboBox().createNew());
  }

  @Test(expected = NullPointerException.class)
  public void should_throw_error_if_Component_is_null() {
    formatter.format(null);
  }
}
