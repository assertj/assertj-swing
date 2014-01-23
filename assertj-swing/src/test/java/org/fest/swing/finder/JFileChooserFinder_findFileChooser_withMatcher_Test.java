/*
 * Created on Aug 27, 2009
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
package org.fest.swing.finder;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.fest.assertions.Assertions.assertThat;

import javax.annotation.Nonnull;
import javax.swing.JFileChooser;

import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.exception.WaitTimedOutError;
import org.fest.swing.fixture.JFileChooserFixture;
import org.junit.Test;

/**
 * Tests for {@link JFileChooserFinder#findFileChooser(org.fest.swing.core.GenericTypeMatcher)}.
 *
 * @author Alex Ruiz
 */
public class JFileChooserFinder_findFileChooser_withMatcher_Test extends JFileChooserFinder_TestCase {
  private MyMatcher matcher;

  @Override
  void extraSetUp() {
    matcher = new MyMatcher();
  }

  @Test
  public void should_find_JFileChooser() {
    clickBrowseButton();
    JFileChooserFixture found = JFileChooserFinder.findFileChooser(matcher).using(robot);
    assertThat(found.target()).isSameAs(window.fileChooser());
  }

  @Test
  public void should_find_JFileChooser_before_given_timeout_expires() {
    window.launchDelay(200);
    clickBrowseButton();
    JFileChooserFixture found = JFileChooserFinder.findFileChooser(matcher).withTimeout(500, MILLISECONDS).using(robot);
    assertThat(found.target()).isSameAs(window.fileChooser());
  }

  @Test
  public void should_find_JFileChooser_before_given_timeout_in_ms_expires() {
    window.launchDelay(200);
    clickBrowseButton();
    JFileChooserFixture found = JFileChooserFinder.findFileChooser(matcher).withTimeout(500).using(robot);
    assertThat(found.target()).isSameAs(window.fileChooser());
  }

  @Test(expected = WaitTimedOutError.class)
  public void should_fail_if_JFileChooser_not_found() {
    JFileChooserFinder.findFileChooser(matcher).using(robot);
  }

  private static class MyMatcher extends GenericTypeMatcher<JFileChooser> {
    MyMatcher() {
      super(JFileChooser.class);
    }

    @Override
    protected boolean isMatching(@Nonnull JFileChooser fileChooser) {
      return "fileChooser".equals(fileChooser.getName());
    }
  }
}
