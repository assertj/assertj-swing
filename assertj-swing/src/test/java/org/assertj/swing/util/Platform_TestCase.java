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
package org.assertj.swing.util;

import static org.mockito.Mockito.mock;

import java.awt.Toolkit;

import org.assertj.swing.test.core.SequentialEDTSafeTestCase;

/**
 * Base test case for {@link Platform}.
 * 
 * @author Alex Ruiz
 */
public abstract class Platform_TestCase extends SequentialEDTSafeTestCase {
  protected OSIdentifier osIdentifier;
  protected Toolkit toolkit;

  @Override
  protected void onSetUp() {
    osIdentifier = mock(OSIdentifier.class);
    toolkit = mock(Toolkit.class);
    Platform.initialize(osIdentifier, toolkit);
  }

  @Override
  protected final void onTearDown() {
    Platform.reload();
  }
}
