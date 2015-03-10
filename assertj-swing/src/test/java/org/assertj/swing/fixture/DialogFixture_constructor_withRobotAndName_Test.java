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
import static org.assertj.swing.test.builder.JDialogs.dialog;

import java.awt.Dialog;

import org.assertj.swing.exception.ComponentLookupException;
import org.assertj.swing.test.ScreenLockReleaser;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link DialogFixture#DialogFixture(org.assertj.swing.core.Robot, String)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class DialogFixture_constructor_withRobotAndName_Test extends RobotBasedTestCase {
  @Rule
  public ScreenLockReleaser lockReleaser = new ScreenLockReleaser();

  @Test
  public void should_Lookup_Showing_Dialog_By_Name() {
    Dialog target = dialog().withName("dialog").withTitle(getClass().getSimpleName()).createAndShow();
    DialogFixture fixture = new DialogFixture(robot, "dialog");
    assertThat(fixture.robot()).isSameAs(robot);
    assertThat(fixture.target()).isSameAs(target);
  }

  @Test(expected = ComponentLookupException.class)
  public void should_Throw_Error_If_Dialog_With_Matching_Name_Is_Not_Showing() {
    dialog().withName("dialog").createNew();
    new DialogFixture(robot, "dialog");
  }

  @Test(expected = ComponentLookupException.class)
  public void should_Throw_Error_If_A_Dialog_With_Matching_Name_Is_Not_Found() {
    dialog().withName("a dialog").createNew();
    new DialogFixture(robot, "dialog");
  }
}
