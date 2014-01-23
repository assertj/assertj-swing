/*
 * Created on Jul 24, 2009
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
package org.fest.swing.applet;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Lists.newArrayList;
import static org.mockito.Mockito.mock;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

/**
 * Tests for {@link BasicAppletContext#setStream(String, java.io.InputStream)} and
 * {@link BasicAppletContext#getStream(String)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class BasicAppletContext_setStream_getStream_Test extends BasicAppletContext_TestCase {
  @Test
  public void should_set_and_get_streams() {
    InputStream inputStream = mock(InputStream.class);
    context.setStream("key1", inputStream);
    assertThat(context.getStream("key1")).isSameAs(inputStream);
    assertThat(streamKeys()).hasSize(1).containsOnly("key1");
  }

  private List<String> streamKeys() {
    Iterator<String> streamKeyIterator = context.getStreamKeys();
    List<String> streamKeys = newArrayList();
    while (streamKeyIterator.hasNext()) {
      streamKeys.add(streamKeyIterator.next());
    }
    return streamKeys;
  }
}
