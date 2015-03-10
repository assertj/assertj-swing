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

import org.assertj.swing.test.ScreenLockReleaser;
import org.assertj.swing.test.core.EDTSafeTestCase;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link DialogFixture#DialogFixture(Dialog)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class DialogFixture_constructor_withDialog_Test extends EDTSafeTestCase {
  @Rule
  public ScreenLockReleaser lockReleaser = new ScreenLockReleaser();

  private DialogFixture fixture;

  @After
  public void tearDown() {
    if (fixture != null) {
      fixture.cleanUp();
    }
  }

  @Test
  public void should_Create_New_Robot_And_Use_Given_Dialog_As_Target() {
    Dialog target = dialog().createNew();
    fixture = new DialogFixture(target);
    assertThat(fixture.robot()).isNotNull();
    assertThat(fixture.target()).isSameAs(target);
  }

  @Test(expected = NullPointerException.class)
  public void should_Throw_Error_If_Dialog_Is_Null() {
    fixture = new DialogFixture((Dialog) null);
  }
}
