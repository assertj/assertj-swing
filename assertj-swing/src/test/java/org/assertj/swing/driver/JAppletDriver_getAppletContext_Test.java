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
import static org.assertj.swing.test.awt.TestAppletContexts.singletonAppletContextMock;

import java.applet.AppletContext;

import org.junit.Test;

/**
 * Tests for {@link JAppletDriver#getAppletContext(javax.swing.JApplet)}.
 * 
 * @author Alex Ruiz
 */
public class JAppletDriver_getAppletContext_Test extends JAppletDriver_TestCase {
  @Test
  public void should_Return_AppletContext() {
    AppletContext context = singletonAppletContextMock();
    applet().updateAppletContext(context);
    AppletContext result = driver().getAppletContext(applet());
    assertThat(result).isSameAs(context);
    assertThat(applet().wasMethodCalledInEDT("getAppletContext")).isTrue();
  }
}
