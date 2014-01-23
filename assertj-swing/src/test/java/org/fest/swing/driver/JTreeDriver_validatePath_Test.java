/*
 * Created on Dec 26, 2009
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

import javax.swing.JTree;

import org.fest.swing.exception.LocationUnavailableException;
import org.junit.Test;

/**
 * Tests for {@link JTreeDriver#checkPathExists(JTree, String)}.
 * 
 * @author Alex Ruiz
 */
public class JTreeDriver_validatePath_Test extends JTreeDriver_TestCase {
  @Test
  public void should_return_node_by_path() {
    driver.checkPathExists(tree, "root/branch1/branch1.1");
  }

  @Test(expected = LocationUnavailableException.class)
  public void should_throw_error_if_path_does_not_exist() {
    driver.checkPathExists(tree, "hello!");
  }
}
