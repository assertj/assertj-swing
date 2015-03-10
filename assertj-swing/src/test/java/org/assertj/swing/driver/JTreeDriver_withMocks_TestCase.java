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

import static org.mockito.Mockito.mock;

import javax.swing.JTree;

import org.assertj.swing.core.TestRobots;
import org.junit.BeforeClass;

/**
 * Base test case for {@link JTreeDriver} that uses mocks as part of its fixture.
 * 
 * @author Alex Ruiz
 */
public class JTreeDriver_withMocks_TestCase {
  protected static JTree tree;
  protected static JTreeDriver driver;

  @BeforeClass
  public static void setUpOnce() {
    tree = mock(JTree.class);
    driver = new JTreeDriver(TestRobots.newRobotMock());
  }
}