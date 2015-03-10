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
package org.assertj.swing.finder;

import org.junit.Test;

/**
 * Tests for {@link WindowFinder#findFrame(String)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class WindowFinder_findFrame_byName_withInvalidInput_Test {
  @Test(expected = NullPointerException.class)
  public void should_Throw_Error_If_Name_Is_Null() {
    String name = null;
    WindowFinder.findFrame(name);
  }

  @Test(expected = IllegalArgumentException.class)
  public void should_Throw_Error_If_Name_Is_Empty() {
    WindowFinder.findFrame("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void should_Throw_Error_If_Timeout_Is_Negative() {
    WindowFinder.findFrame("frame").withTimeout(-20);
  }

  @Test(expected = NullPointerException.class)
  public void should_Throw_Error_If_Time_Unit_Is_Null() {
    WindowFinder.findFrame("frame").withTimeout(10, null);
  }
}
