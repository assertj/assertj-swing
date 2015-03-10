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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.swing.text.JTextComponent;

import org.assertj.swing.test.core.EDTSafeTestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link JTextComponentTextReader#checkContainsText(JTextComponent, String)}.
 * 
 * @author Alex Ruiz
 */
public class JTextComponentTextReader_checkContainsText_Test extends EDTSafeTestCase {
  private JTextComponent textComponent;
  private JTextComponentTextReader reader;

  @Before
  public void setUp() {
    textComponent = mock(JTextComponent.class);
    reader = new JTextComponentTextReader();
  }

  @Test
  public void should_Return_False_If_Text_In_JTextComponent_Is_Null() {
    when(textComponent.getText()).thenReturn(null);
    assertThat(reader.checkContainsText(textComponent, "Yoda")).isFalse();
  }

  @Test
  public void should_Return_False_If_Text_In_JTextComponent_Does_Not_Contain_Given_String() {
    when(textComponent.getText()).thenReturn("Leia");
    assertThat(reader.checkContainsText(textComponent, "Yoda")).isFalse();
  }

  @Test
  public void should_Return_True_If_Text_In_JTextComponent_Contains_Given_String() {
    when(textComponent.getText()).thenReturn("Yoda");
    assertThat(reader.checkContainsText(textComponent, "Yo")).isTrue();
  }
}
