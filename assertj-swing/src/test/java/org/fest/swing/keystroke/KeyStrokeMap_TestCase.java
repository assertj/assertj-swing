/*
 * Created on May 16, 2009
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
package org.fest.swing.keystroke;

import static java.awt.event.InputEvent.CTRL_MASK;
import static java.awt.event.KeyEvent.VK_A;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Lists.newArrayList;
import static org.mockito.Mockito.mock;

import java.util.Collection;

import javax.swing.KeyStroke;

import org.junit.After;
import org.junit.Before;

/**
 * Base test case for {@link KeyStrokeMap}.
 *
 * @author Alex Ruiz
 */
public abstract class KeyStrokeMap_TestCase {
  KeyStrokeMappingProvider provider;
  KeyStroke keyStroke;
  KeyStrokeMapping mapping;
  Collection<KeyStrokeMapping> mappings;

  @Before
  public final void setUp() {
    provider = mock(KeyStrokeMappingProvider.class);
    keyStroke = KeyStroke.getKeyStroke(VK_A, CTRL_MASK);
    mapping = new KeyStrokeMapping('A', keyStroke);
    mappings = newArrayList();
    mappings.add(mapping);
    KeyStrokeMap.clearKeyStrokes();
    assertThat(KeyStrokeMap.hasKeyStrokes()).isFalse();
  }

  @After
  public final void tearDown() {
    KeyStrokeMap.updateKeyStrokeMapCollection(new KeyStrokeMapCollection());
    KeyStrokeMap.reloadFromSystemSettings();
  }
}
