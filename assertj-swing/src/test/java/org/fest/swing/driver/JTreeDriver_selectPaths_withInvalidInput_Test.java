/*
 * Created on Jul 16, 2009
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
package org.fest.swing.driver;

import org.junit.Test;

/**
 * Tests for {@link JTreeDriver#selectPaths(javax.swing.JTree, String[])}.
 * 
 * @author Alex Ruiz
 */
public class JTreeDriver_selectPaths_withInvalidInput_Test extends JTreeDriver_withMocks_TestCase {
  @Test(expected = NullPointerException.class)
  public void should_throw_error_if_array_is_null() {
    String[] paths = null;
    driver.selectPaths(tree, paths);
  }

  @Test(expected = IllegalArgumentException.class)
  public void should_throw_error_if_array_is_empty() {
    String[] paths = new String[0];
    driver.selectPaths(tree, paths);
  }
}
