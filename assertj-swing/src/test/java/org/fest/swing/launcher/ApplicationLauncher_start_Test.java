/*
 * Created on May 23, 2008
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
import static org.fest.util.Lists.newArrayList;

import java.awt.Frame;
import java.util.List;

import javax.annotation.Nonnull;

import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.exception.UnexpectedException;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.launcher.JavaApp.ArgumentObserver;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.junit.Test;

/**
 * Tests for {@link ApplicationLauncher#start()}.
 *
 * @author Yvonne Wang
 */
public class ApplicationLauncher_start_Test extends RobotBasedTestCase {
  @Test
  public void should_throw_error_if_application_class_name_is_invalid() {
    try {
      ApplicationLauncher.application("Hello").start();
      failWhenExpectingException();
    } catch (UnexpectedException e) {
      assertThat(e.getMessage()).contains("Unable to load class 'Hello'");
    }
  }

  @Test
  public void should_launch_application_without_arguments() {
    ApplicationLauncher.application(JavaApp.class).start();
    assertFrameIsShowing();
  }

  @Test
  public void should_launch_application_without_arguments_using_FQCN() {
    ApplicationLauncher.application(JavaApp.class.getName()).start();
    assertFrameIsShowing();
  }

  @Test
  public void should_launch_application_using_arguments() {
    final List<String> arguments = newArrayList();
    ArgumentObserver observer = new ArgumentObserver() {
      @Override
      public void arguments(String[] args) {
        arguments.addAll(newArrayList(args));
      }
    };
    JavaApp.add(observer);
    ApplicationLauncher.application(JavaApp.class).withArgs("arg1", "arg2").start();
    assertFrameIsShowing();
    assertThat(arguments).containsOnly("arg1", "arg2");
  }

  @Test(expected = NullPointerException.class)
  public void should_throw_error_if_argument_array_is_null() {
    String[] args = null;
    ApplicationLauncher.application(JavaApp.class).withArgs(args).start();
  }

  private void assertFrameIsShowing() {
    FrameFixture frameFixture = WindowFinder.findFrame(new GenericTypeMatcher<Frame>(Frame.class) {
      @Override
      protected boolean isMatching(@Nonnull Frame frame) {
        return "Java Application".equals(frame.getTitle()) && frame.isShowing();
      }
    }).using(robot);
    assertThat(frameFixture).isNotNull();
  }
}
