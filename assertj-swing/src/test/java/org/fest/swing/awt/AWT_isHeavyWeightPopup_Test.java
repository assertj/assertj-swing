/*
 * Created on Nov 6, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.swing.awt;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.awt.TestWindows.singletonWindowMock;
import static org.fest.swing.test.builder.JDialogs.dialog;
import static org.fest.swing.test.builder.JFrames.frame;

import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JFrame;

import org.fest.swing.test.core.EDTSafeTestCase;
import org.junit.Test;

/**
 * Tests for {@link AWT#isHeavyWeightPopup(java.awt.Component)}.
 * 
 * @author Alex Ruiz
 */
public class AWT_isHeavyWeightPopup_Test extends EDTSafeTestCase {
  @Test
  public void should_return_false_if_Component_is_Window() {
    Window w = singletonWindowMock();
    assertThat(AWT.isHeavyWeightPopup(w)).isFalse();
  }

  @Test
  public void should_return_false_if_Component_is_Dialog() {
    JDialog d = dialog().createNew();
    assertThat(AWT.isHeavyWeightPopup(d)).isFalse();
  }

  @Test
  public void should_return_false_if_Component_is_Frame() {
    JFrame f = frame().createNew();
    assertThat(AWT.isHeavyWeightPopup(f)).isFalse();
  }

  // TODO finish testing
}
