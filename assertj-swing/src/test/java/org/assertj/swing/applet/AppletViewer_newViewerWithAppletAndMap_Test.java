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
package org.assertj.swing.applet;

import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.test.awt.TestApplets.singletonAppletMock;

import java.util.Map;

import org.assertj.swing.test.core.EDTSafeTestCase;
import org.junit.Test;

/**
 * Tests for {@link AppletViewer#newViewer(Applet, Map)}.
 *
 * @author Alex Ruiz
 */
public class AppletViewer_newViewerWithAppletAndMap_Test extends EDTSafeTestCase {
  @Test(expected = NullPointerException.class)
  public void should_Throw_Error_If_ParameterMap_Is_Null() {
    execute(() -> {
      Map<String, String> map = null;
      AppletViewer.newViewer(singletonAppletMock(), map);
    });
  }
}
