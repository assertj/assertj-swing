/*
 * Created on Mar 13, 2009
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * Copyright @2009 the original author or authors.
 */
package org.fest.swing.junit.v4_5.runner;

import org.fest.swing.annotation.GUITest;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Understands a JUnit test to be used to manually test <code>{@link GUITestRunner}</code>.
 *
 * @author Alex Ruiz
 */
@RunWith(GUITestRunner.class)
@GUITest
public class SomeGUITestFake {

  @Test public void successfulGUITest() {}

  @Ignore("enable for manual testing")
  @Test public void failedGUITest() {
    throw new RuntimeException("Failed on purpose");
  }
}
