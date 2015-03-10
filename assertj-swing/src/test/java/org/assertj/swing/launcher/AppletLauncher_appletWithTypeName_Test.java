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
package org.assertj.swing.launcher;

import javax.swing.JButton;

import org.assertj.swing.exception.UnexpectedException;
import org.junit.Test;

/**
 * Tests for {@link AppletLauncher#applet(String)}.
 * 
 * @author Yvonne Wang
 */
public class AppletLauncher_appletWithTypeName_Test extends AppletLauncher_TestCase {
  @Test(expected = NullPointerException.class)
  public void should_Throw_Error_If_Applet_Type_Name_Is_Null() {
    String type = null;
    AppletLauncher.applet(type);
  }

  @Test(expected = IllegalArgumentException.class)
  public void should_Throw_Error_If_Applet_Type_Name_Is_Empty() {
    AppletLauncher.applet("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void should_Throw_Error_If_Type_Name_Is_Does_Not_Belong_To_An_Applet() {
    AppletLauncher.applet(JButton.class.getName());
  }

  @Test
  public void should_Throw_Error_If_Applet_Type_Does_Not_Exist() {
    thrown.expect(UnexpectedException.class, "Unable to load class Hello");
    AppletLauncher.applet("Hello");
  }

  @Test
  public void should_Throw_Error_If_Applet_Cannot_Be_Instantiated_From_Type_Name() {
    thrown.expect(UnexpectedException.class, "Unable to create a new instance");
    AppletLauncher.applet(AnApplet.class.getName());
  }
}
