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
import org.w3c.dom.CDATASection;
import org.w3c.dom.Node;

/**
 * Tests for <code>{@link XmlNode#addCdata(String)}</code>.
 *
 * @author Alex Ruiz
 */
public class XmlNode_addCdata_Test extends XmlNode_TestCase {

  @Test
  public void should_add_CDATA_node() {
    node.addCdata("My CDATA");
    assertThat(childNodeCountOf(target)).isEqualTo(1);
    Node child = target.getFirstChild();
    assertThat(child).isInstanceOf(CDATASection.class);
    assertThat(dataOf(child)).isEqualTo("My CDATA");
  }

}
