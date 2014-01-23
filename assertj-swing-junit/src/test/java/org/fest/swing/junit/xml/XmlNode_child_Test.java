/*
 * Created on Apr 13, 2009
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * Copyright @2009 the original author or authors.
 */
package org.fest.swing.junit.xml;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

/**
 * Tests for <code>{@link XmlNode#child(int)}</code>.
 *
 * @author Alex Ruiz
 */
public class XmlNode_child_Test extends XmlNode_TestCase {

  @Test
  public void should_return_child_at_given_position() {
    XmlNode newNode = node.addNewNode("one");
    assertThat(node.child(0)).isEqualTo(newNode);
  }

  @Test
  public void should_return_null_if_child_is_not_a_XML_element() {
    node.addText("Hello");
    assertThat(node.child(0)).isNull();
  }
}
