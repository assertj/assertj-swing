/*
 * Created on Jul 15, 2008
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
 * Copyright @2008-2013 the original author or authors.
 */
package org.fest.swing.launcher;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;

import java.applet.Applet;

import org.fest.swing.exception.UnexpectedException;
import org.junit.Test;

/**
 * Tests for {@link AppletLauncher#applet(Class)}.
 * 
 * @author Yvonne Wang
 */
public class AppletLauncher_appletWithType_Test extends AppletLauncher_TestCase {
  @Test(expected = NullPointerException.class)
  public void should_throw_error_if_Applet_TypeIsNull() {
    Class<? extends Applet> type = null;
    AppletLauncher.applet(type);
  }

  @Test
  public void should_throw_error_if_Applet_cannot_be_instantiated() {
    try {
      AppletLauncher.applet(AnApplet.class);
      failWhenExpectingException();
    } catch (UnexpectedException e) {
      assertThat(e.getMessage()).contains("Unable to create a new instance");
    }
  }
}
