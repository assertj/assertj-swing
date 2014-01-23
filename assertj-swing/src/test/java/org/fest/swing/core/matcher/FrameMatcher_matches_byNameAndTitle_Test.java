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

import javax.swing.JFrame;

import org.fest.swing.test.core.EDTSafeTestCase;
import org.junit.Test;

/**
 * Tests for {@link FrameMatcher#matches(java.awt.Component)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class FrameMatcher_matches_byNameAndTitle_Test extends EDTSafeTestCase {
  @Test
  public void should_return_true_if_name_and_title_are_equal_to_expected() {
    FrameMatcher matcher = FrameMatcher.withName("frame").andTitle("Hello");
    JFrame frame = frame().withName("frame").withTitle("Hello").createNew();
    assertThat(matcher.matches(frame)).isTrue();
  }

  @Test
  public void should_return_true_if_name_is_equal_to_expected_and_title_matches_pattern() {
    FrameMatcher matcher = FrameMatcher.withName("frame").andTitle("Hel.*");
    JFrame frame = frame().withName("frame").withTitle("Hello").createNew();
    assertThat(matcher.matches(frame)).isTrue();
  }
}
