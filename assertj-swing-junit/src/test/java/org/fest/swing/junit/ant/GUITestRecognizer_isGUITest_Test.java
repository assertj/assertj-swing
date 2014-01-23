/*
 * Created on Apr 24, 2009
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
package org.fest.swing.junit.ant;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for <code>{@link GUITestRecognizer#isGUITest(String, String)}</code>.
 *
 * @author Alex Ruiz
 */
public class GUITestRecognizer_isGUITest_Test {

  private static final String TEST_CLASS_NAME = SomeTestFake.class.getName();

  private static GUITestRecognizer recognizer;

  @BeforeClass public static void setUpOnce() {
    recognizer = new GUITestRecognizer();
  }

  @Test
  public void should_return_true_if_method_has_annotation() {
    boolean isGuiTest = recognizer.isGUITest(TEST_CLASS_NAME, "guiTest");
    assertThat(isGuiTest).isTrue();
  }

  @Test
  public void should_return_false_if_method_does_not_have_annotation() {
    boolean isGuiTest = recognizer.isGUITest(TEST_CLASS_NAME, "nonGuiTest");
    assertThat(isGuiTest).isFalse();
  }

  @Test
  public void should_return_false_in_case_of_error() {
    boolean isGuiTest = recognizer.isGUITest(TEST_CLASS_NAME, "someMethod");
    assertThat(isGuiTest).isFalse();
  }
}
