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

import org.junit.Test;

/**
 * Tests for {@link FontFixture#requireFamily(String)}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class FontFixture_requireFamily_Test extends FontFixture_TestCase {
  @Test
  public void should_Pass_If_Family_Is_Equal_To_Expected() {
    fixture().requireFamily("SansSerif");
  }

  @Test
  public void should_Fail_If_Family_Is_Not_Equal_To_Expected() {
    expectAssertionError("family", "[Monospace]", "[SansSerif]");
    fixture().requireFamily("Monospace");
  }

  @Test
  public void should_Fail_Showing_Description_If_Family_Is_Not_Equal_To_Expected() {
    expectAssertionError("test - family", "[Monospace]", "[SansSerif]");
    FontFixture fixture = new FontFixture(font(), "test");
    fixture.requireFamily("Monospace");
  }
}
