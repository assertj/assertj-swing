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
package org.assertj.swing.junit.xml;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 * Tests for <code>{@link XmlNode#child(int)}</code>.
 * 
 * @author Alex Ruiz
 */
public class XmlNode_child_Test extends XmlNode_TestCase {

  @Test
  public void should_Return_Child_At_Given_Position() {
    XmlNode newNode = node.addNewNode("one");
    assertThat(node.child(0)).isEqualTo(newNode);
  }

  @Test
  public void should_Return_Null_If_Child_Is_Not_A_XML_Element() {
    node.addText("Hello");
    assertThat(node.child(0)).isNull();
  }
}
