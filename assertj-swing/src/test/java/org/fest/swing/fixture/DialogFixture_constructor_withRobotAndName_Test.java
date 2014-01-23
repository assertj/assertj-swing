/*
 * Created on Nov 17, 2009
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
package org.fest.swing.fixture;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JDialogs.dialog;

import java.awt.Dialog;

import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.junit.Test;

/**
 * Tests for {@link DialogFixture#DialogFixture(org.fest.swing.core.Robot, String)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class DialogFixture_constructor_withRobotAndName_Test extends RobotBasedTestCase {
  @Test
  public void should_lookup_showing_dialog_by_name() {
    Dialog target = dialog().withName("dialog")
                            .withTitle(getClass().getSimpleName())
                            .createAndShow();
    DialogFixture fixture = new DialogFixture(robot, "dialog");
    assertThat(fixture.robot()).isSameAs(robot);
    assertThat(fixture.target()).isSameAs(target);
  }

  @Test(expected = ComponentLookupException.class)
  public void should_throw_error_if_dialog_with_matching_name_is_not_showing() {
    dialog().withName("dialog").createNew();
    new DialogFixture(robot, "dialog");
  }

  @Test(expected = ComponentLookupException.class)
  public void should_throw_error_if_a_dialog_with_matching_name_is_not_found() {
    dialog().withName("a dialog").createNew();
    new DialogFixture(robot, "dialog");
  }
}
