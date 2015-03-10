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

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 * Tests for {@link JSpinnerDriver#editor(javax.swing.JSpinner)}.
 * 
 * @author Alex Ruiz
 */
public class JSpinnerDriver_textOf_Test extends JSpinnerDriver_TestCase {
  @Test
  public void should_Return_Text() {
    updateValue("Frodo");
    assertThat(driver.textOf(spinner)).isEqualTo("Frodo");
  }

  @Test
  public void should_Return_Text_Representation_Of_Selected_Value_Even_If_Editor_Is_Not_JTextComponent() {
    setJLabelAsEditor();
    updateValue("Frodo");
    assertThat(driver.textOf(spinner)).isEqualTo("Frodo");
  }
}
