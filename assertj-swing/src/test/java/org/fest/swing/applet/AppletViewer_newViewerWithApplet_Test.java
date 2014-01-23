/*
 * Created on Jul 24, 2009
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
package org.fest.swing.applet;

import static javax.swing.SwingUtilities.getAncestorOfClass;
import static org.fest.assertions.Assertions.assertThat;

import java.awt.Container;

import org.junit.Test;

/**
 * Tests for {@link AppletViewer#newViewer(java.applet.Applet)}.
 * 
 * @author Alex Ruiz
 */
public class AppletViewer_newViewerWithApplet_Test extends AppletViewer_TestCase {
  @Test(expected = NullPointerException.class)
  public void should_throw_error_if_Applet_is_null() {
    AppletViewer.newViewer(null);
  }

  @Test
  public void should_load_applet_when_created() {
    assertThatAppletIsShowingAndViewerIsLoaded();
    Container ancestor = getAncestorOfClass(AppletViewer.class, applet);
    assertThat(ancestor).isSameAs(viewer);
    fixture.label("status").requireText("Applet loaded");
    assertThat(viewer.getApplet()).isSameAs(applet);
    assertThat(viewer.getAppletStub()).isInstanceOf(BasicAppletStub.class);
  }
}
