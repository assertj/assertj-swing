/*
 * Created on Jul 16, 2008
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
package org.fest.swing.core.matcher;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JFrames.frame;
import static org.fest.swing.test.builder.JTextFields.textField;

import org.fest.swing.test.core.EDTSafeTestCase;
import org.junit.Test;

/**
 * Tests for {@link FrameMatcher#matches(java.awt.Component)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class FrameMatcher_matches_any_Test extends EDTSafeTestCase {
  @Test
  public void should_always_return_true_when_matching_any_Frame() {
    FrameMatcher matcher = FrameMatcher.any();
    assertThat(matcher.matches(frame().createNew())).isTrue();
  }

  @Test
  public void should_return_false_if_Component_is_not_Frame() {
    FrameMatcher matcher = FrameMatcher.any();
    assertThat(matcher.matches(textField().createNew())).isFalse();
  }
}
