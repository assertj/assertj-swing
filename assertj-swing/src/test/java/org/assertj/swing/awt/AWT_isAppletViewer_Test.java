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
package org.assertj.swing.awt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.test.builder.JTextFields.textField;

import org.junit.Test;

/**
 * Tests for {@link AWT#isAppletViewer(java.awt.Component)}.
 * 
 * @author Alex Ruiz
 */
public class AWT_isAppletViewer_Test {
  @Test
  public void should_Return_False_If_Component_Is_Not_AppletViewer() {
    assertThat(AWT.isAppletViewer(textField().createNew())).isFalse();
  }

  @Test
  public void should_Return_False_If_Component_Is_Null() {
    assertThat(AWT.isAppletViewer(null)).isFalse();
  }
}
