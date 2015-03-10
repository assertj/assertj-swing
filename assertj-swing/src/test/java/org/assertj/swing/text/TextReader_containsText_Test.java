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
package org.assertj.swing.text;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.test.builder.JLabels.label;
import static org.junit.rules.ExpectedException.none;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.annotation.Nonnull;
import javax.swing.JButton;

import org.assertj.swing.test.core.EDTSafeTestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link TextReader#containsText(java.awt.Component, String)}.
 * 
 * @author Alex Ruiz
 */
public class TextReader_containsText_Test extends EDTSafeTestCase {
  @Rule
  public final ExpectedException thrown = none();

  private TextReader<JButton> reader;

  @Before
  public void setUp() {
    reader = new TestTextReader();
  }

  @Test
  public void should_Throw_Error_If_Component_Is_Not_Supported() {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Expecting component of type javax.swing.JButton but got javax.swing.JLabel");
    reader.containsText(label().createNew(), "Yoda");
  }

  @Test
  public void should_Return_True_If_Component_Contains_Text() {
    JButton button = mock(JButton.class);
    when(button.getText()).thenReturn("Yoda");
    assertThat(reader.containsText(button, "Yoda")).isTrue();
  }

  @Test
  public void should_Return_False_If_Component_Doesn_Not_Contain_Text() {
    JButton button = mock(JButton.class);
    when(button.getText()).thenReturn("Yoda");
    assertThat(reader.containsText(button, "Leia")).isFalse();
  }

  private static class TestTextReader extends TextReader<JButton> {
    @Override
    public Class<JButton> supportedComponent() {
      return JButton.class;
    }

    @Override
    protected boolean checkContainsText(@Nonnull JButton button, @Nonnull String text) {
      return button.getText().contains(text);
    }
  }
}
