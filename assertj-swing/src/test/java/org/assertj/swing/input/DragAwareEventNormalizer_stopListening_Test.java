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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.awt.Toolkit;

import javax.annotation.Nonnull;

import org.assertj.swing.test.awt.ToolkitStub;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link DragAwareEventNormalizer#stopListening()}.
 * 
 * @author Alex Ruiz
 */
public class DragAwareEventNormalizer_stopListening_Test extends DragAwareEventNormalizer_TestCase {
  private ToolkitStub toolkit;
  private EventQueueStub eventQueue;
  private int mask;

  @Override
  @Before
  public void setUp() {
    eventQueue = new EventQueueStub();
    toolkit = newToolkitStub();
    toolkit.eventQueue(eventQueue);
    mask = 8;
  }

  @Test
  public void should_Dispose_EventQueue_When_Stops_Listening() {
    final DragAwareEventQueue dragAwareEventQueue = mock(DragAwareEventQueue.class);
    eventNormalizer = new DragAwareEventNormalizer() {
      @Override
      DragAwareEventQueue createEventQueue(@Nonnull Toolkit t, long m) {
        return dragAwareEventQueue;
      }
    };
    eventNormalizer.startListening(toolkit, delegateEventListenerMock(), mask);
    eventNormalizer.stopListening();
    verify(dragAwareEventQueue).pop();
    checkEventNormalizerNotInToolkit(toolkit, mask);
  }

  @Test
  public void should_Gracefully_Stop_Listening_If_DragAwareQueue_Is_Null() {
    eventNormalizer = new DragAwareEventNormalizer() {
      @Override
      DragAwareEventQueue createEventQueue(@Nonnull Toolkit t, long m) {
        throw new RuntimeException("Thrown on purpose");
      }
    };
    eventNormalizer.startListening(toolkit, delegateEventListenerMock(), mask);
    eventNormalizer.stopListening();
    checkEventNormalizerNotInToolkit(toolkit, mask);
  }
}
