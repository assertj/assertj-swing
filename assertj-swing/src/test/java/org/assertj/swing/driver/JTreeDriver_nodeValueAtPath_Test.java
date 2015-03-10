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
package org.assertj.swing.driver;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.swing.exception.LocationUnavailableException;
import org.junit.Test;

/**
 * Tests for {@link JTreeDriver#nodeValue(javax.swing.JTree, String)}.
 * 
 * @author Alex Ruiz
 */
public class JTreeDriver_nodeValueAtPath_Test extends JTreeDriver_TestCase {
  @Test
  public void should_Return_Node_Text() {
    String value = driver.nodeValue(tree, "root/branch1/branch1.1");
    assertThat(value).isEqualTo("branch1.1");
  }

  @Test(expected = LocationUnavailableException.class)
  public void should_Throw_Error_If_Path_Does_Not_Exist() {
    driver.nodeValue(tree, "hello");
  }
}
