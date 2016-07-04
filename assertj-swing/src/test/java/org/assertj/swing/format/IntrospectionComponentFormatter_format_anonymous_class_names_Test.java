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
package org.assertj.swing.format;

import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link IntrospectionComponentFormatter#format(java.awt.Component)}.
 * 
 * @author Timo Mikkilä
 */
public class IntrospectionComponentFormatter_format_anonymous_class_names_Test {

  JButton button;
  private IntrospectionComponentFormatter formatter;

  @Before
  public void setUp() {
    formatter = new IntrospectionComponentFormatter(JButton.class);
    button = new JButton() {

    };
  }

  @Test
  public void should_Show_Real_Name_Of_Anynomous_Class() {
    assertThat(formatter.format(button)).startsWith("javax.swing.JButton");
  }

}
