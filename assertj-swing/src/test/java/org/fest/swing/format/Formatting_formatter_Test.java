/*
 * Created on Sep 16, 2007
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

import static org.fest.assertions.Assertions.assertThat;

import java.awt.Component;

import javax.annotation.Nonnull;
import javax.swing.JComboBox;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link Formatting#formatter(Class)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class Formatting_formatter_Test {
  private Class<JComboBox> type;
  private ComponentFormatter oldFormatter;
  private ComponentFormatter newFormatter;

  @Before
  public void setUp() {
    type = JComboBox.class;
    oldFormatter = Formatting.formatter(type);
    newFormatter = new ComponentFormatterTemplate() {
      @Override
      protected String doFormat(@Nonnull Component c) {
        return null;
      }

      @Override
      public Class<? extends Component> targetType() {
        return type;
      }
    };
  }

  @After
  public void tearDown() {
    Formatting.register(oldFormatter);
  }

  @Test
  public void should_replace_existing_formatter() {
    Formatting.register(newFormatter);
    assertThat(Formatting.formatter(type)).isSameAs(newFormatter);
  }
}
