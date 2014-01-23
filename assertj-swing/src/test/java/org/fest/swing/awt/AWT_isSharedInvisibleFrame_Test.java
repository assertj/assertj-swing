/*
 * Created on Oct 12, 2007
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
package org.fest.swing.awt;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JDialogs.dialog;
import static org.fest.swing.test.builder.JTextFields.textField;

import javax.swing.JDialog;

import org.fest.swing.test.core.EDTSafeTestCase;
import org.junit.Test;

/**
 * Tests for {@link AWT#isSharedInvisibleFrame(java.awt.Component)}.
 * 
 * @author Alex Ruiz
 */
public class AWT_isSharedInvisibleFrame_Test extends EDTSafeTestCase {
  @Test
  public void should_return_true_if_Component_is_shared_invisible_Frame() {
    JDialog dialog = dialog().createNew();
    assertThat(AWT.isSharedInvisibleFrame(dialog.getOwner())).isTrue();
  }

  @Test
  public void should_return_false_if_Component_is_not_shared_invisible_frame() {
    assertThat(AWT.isSharedInvisibleFrame(textField().createNew())).isFalse();
  }

  @Test
  public void should_return_false_if_Component_is_Null() {
    assertThat(AWT.isSharedInvisibleFrame(null)).isFalse();
  }
}
