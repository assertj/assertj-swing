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
import static org.fest.swing.junit.xml.XmlAttribute.name;
import static org.fest.swing.junit.xml.XmlAttributes.attributes;

import org.junit.Test;
import org.w3c.dom.Element;

/**
 * Tests for <code>{@link XmlNode#addNewNode(String, XmlAttributes)}</code>.
 *
 * @author Alex Ruiz
 */
public class XmlNode_addNewNodeWithAttributes_Test extends XmlNode_TestCase {

  @Test
  public void should_add_new_child_node() {
    XmlNode newNode = node.addNewNode("new", attributes(name("name1").value("value1"), name("name2").value("value2")));
    Element child = newNode.target();
    assertThat(nameOf(child)).isEqualTo("new");
    assertThat(attributeCountOf(child)).isEqualTo(2);
    assertThat(child.getAttribute("name1")).isEqualTo("value1");
    assertThat(child.getAttribute("name2")).isEqualTo("value2");
    assertThat(parentOf(child)).isSameAs(target);
  }

}
