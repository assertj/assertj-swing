/*
 * Created on Apr 16, 2008
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
package org.fest.swing.fixture;

import static org.junit.rules.ExpectedException.none;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link FontFixture#requireName(String)}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class FontFixture_requireName_Test extends FontFixture_TestCase {
  @Rule
  public ExpectedException thrown = none();

  @Test
  public void should_pass_if_name_is_equal_to_expected() {
    fixture().requireName("SansSerif");
  }

  @Test
  public void should_fail_if_name_is_not_equal_to_expected() {
    thrown.expect(AssertionError.class);
    thrown.expectMessage("[name] expected:<'[Monospace]'> but was:<'[SansSerif]'>");
    fixture().requireName("Monospace");
  }

  @Test
  public void should_fail_showing_description_if_name_is_not_equal_to_expected() {
    thrown.expect(AssertionError.class);
    thrown.expectMessage("[test - name] expected:<'[Monospace]'> but was:<'[SansSerif]'>");
    FontFixture fixture = new FontFixture(font(), "test");
    fixture.requireName("Monospace");
  }
}
