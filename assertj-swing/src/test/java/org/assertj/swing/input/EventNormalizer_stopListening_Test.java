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
package org.assertj.swing.input;

import static org.assertj.swing.test.awt.Toolkits.newToolkitStub;

import org.assertj.swing.test.awt.ToolkitStub;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link EventNormalizer#stopListening()}.
 * 
 * @author Alex Ruiz
 */
public class EventNormalizer_stopListening_Test extends EventNormalizer_TestCase {
  private EventNormalizer eventNormalizer;

  @Before
  public void setUp() {
    eventNormalizer = new EventNormalizer();
  }

  @Test
  public void should_Detach_From_Toolkit_When_Stop_Listening() {
    ToolkitStub toolkit = newToolkitStub();
    int mask = 8;
    eventNormalizer.startListening(toolkit, delegateEventListenerMock(), mask);
    eventNormalizer.stopListening();
    checkEventNormalizerNotInToolkit(toolkit, mask);
  }
}
