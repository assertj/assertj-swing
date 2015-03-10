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
package org.assertj.swing.fixture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.test.builder.JFrames.frame;

import java.awt.Frame;

import org.assertj.swing.exception.ComponentLookupException;
import org.assertj.swing.test.ScreenLockReleaser;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link FrameFixture#FrameFixture(org.assertj.swing.core.Robot, String)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class FrameFixture_constructor_withRobotAndName_Test extends RobotBasedTestCase {
  @Rule
  public ScreenLockReleaser lockReleaser = new ScreenLockReleaser();

  @Test
  public void should_Lookup_Showing_Frame_By_Name() {
    Frame target = frame().withName("frame").withTitle(getClass().getSimpleName()).createAndShow();
    FrameFixture fixture = new FrameFixture(robot, "frame");
    assertThat(fixture.robot()).isSameAs(robot);
    assertThat(fixture.target()).isSameAs(target);
  }

  @Test(expected = ComponentLookupException.class)
  public void should_Throw_Error_If_Frame_With_Matching_Name_Is_Not_Showing() {
    frame().withName("frame").createNew();
    new FrameFixture(robot, "frame");
  }

  @Test(expected = ComponentLookupException.class)
  public void should_Throw_Error_If_A_Frame_With_Matching_Name_Is_Not_Found() {
    frame().withName("a frame").createNew();
    new FrameFixture(robot, "frame");
  }
}
