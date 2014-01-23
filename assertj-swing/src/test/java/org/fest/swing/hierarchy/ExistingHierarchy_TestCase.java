/*
 * Created on Oct 20, 2007
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
 * Copyright @2007-2013 the original author or authors.
 */
package org.fest.swing.hierarchy;

import org.fest.swing.test.core.EDTSafeTestCase;
import org.junit.Before;

/**
 * Base test case for {@link ExistingHierarchy}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public abstract class ExistingHierarchy_TestCase extends EDTSafeTestCase {
  ExistingHierarchy hierarchy;

  @Before
  public final void setUp() {
    hierarchy = new ExistingHierarchy();
  }
}
