/*
 * Created on Apr 3, 2009
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

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

/**
 * Understands a DOM-based XML document. This class is intended for internal use only. It is just a thin wrapper around
 * a DOM <code>{@link Document}</code>. It only provides the necessary functionality needed by the FEST-Swing JUnit
 * extension.
 *
 * @author Alex Ruiz
 */
public class XmlDocument {

  private final Document document;

  /**
   * Creates a new </code>{@link XmlDocument}</code>.
   * @throws ExceptionInInitializerError if the XML document could not be created.
   */
  public XmlDocument() {
    try {
      document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
    } catch (ParserConfigurationException e) {
      throw new ExceptionInInitializerError(e);
    }
  }

  /**
   * Creates and adds a new XML root node.
   * @param name the name of the XML node to create.
   * @return the created root node.
   */
  public XmlNode newRoot(String name) {
    XmlNode root = new XmlNode(document.createElement(name));
    document.appendChild(root.target());
    return root;
  }
}
