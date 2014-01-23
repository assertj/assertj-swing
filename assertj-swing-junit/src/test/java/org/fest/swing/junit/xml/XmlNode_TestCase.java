/*
 * Created on Apr 13, 2009
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
 * Copyright @2009 the original author or authors.
 */
package org.fest.swing.junit.xml;

import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Before;
import org.w3c.dom.*;

/**
 * Base test case for <code>{@link XmlNode}</code>.
 *
 * @author Alex Ruiz
 */
public abstract class XmlNode_TestCase {

  Element target;
  XmlNode node;

  @Before
  public final void setUp() throws Exception {
    Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
    target = document.createElement("person");
    document.appendChild(target);
    node = new XmlNode(target);
  }

  static String nameOf(Node n) {
    return n.getNodeName();
  }

  static Node parentOf(Node n) {
    return n.getParentNode();
  }

  static int childNodeCountOf(Node n) {
    return n.getChildNodes().getLength();
  }

  static int attributeCountOf(Node n) {
    return n.getAttributes().getLength();
  }

  static String dataOf(Node d) {
    return ((CharacterData)d).getData();
  }
}
