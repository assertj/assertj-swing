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

import static org.fest.util.Objects.*;
import static org.fest.util.Strings.concat;

import org.w3c.dom.*;

/**
 * Understands a DOM-based XML element. This class is intended for internal use only. It is just a thin wrapper around
 * a DOM <code>{@link Element}</code>. It only provides the necessary functionality needed by the FEST-Swing JUnit
 * extension.
 *
 * @author Alex Ruiz
 */
public class XmlNode {

  private final Element target;

  /**
   * Creates a new </code>{@link XmlNode}</code>.
   * @param target the underlying DOM element.
   */
  protected XmlNode(Element target) {
    this.target = target;
  }

  /**
   * Creates and adds a new XML node to this node.
   * @param name the name of the node to add.
   * @return the created node.
   */
  public XmlNode addNewNode(String name) {
    return new XmlNode(createAndAddChild(name));
  }

  /**
   * Creates and adds a new XML node to this node.
   * @param name the name of the node to add.
   * @param attributes the attributes of the node to add.
   * @return the created node.
   */
  public XmlNode addNewNode(String name, XmlAttributes attributes) {
    Element e = createAndAddChild(name);
    for (XmlAttribute a : attributes) addAttribute(e, a);
    return new XmlNode(e);
  }

  private Element createAndAddChild(String name) {
    Element e = document().createElement(name);
    target.appendChild(e);
    return e;
  }

  /**
   * Adds a CDATA section to this node.
   * @param data the data for the CDATA section to create.
   */
  public void addCdata(String data) {
    CDATASection s = document().createCDATASection(data);
    target.appendChild(s);
  }

  /**
   * Adds a text node to this node.
   * @param text the text of the new text node.
   */
  public void addText(String text) {
    Text textNode = document().createTextNode(text);
    target.appendChild(textNode);
  }

  private Document document() {
    return target.getOwnerDocument();
  }

  /**
   * Adds an attribute to this node.
   * @param a the attribute to add.
   */
  public void addAttribute(XmlAttribute a) {
    addAttribute(target, a);
  }

  /**
   * Adds one or more attributes to this node.
   * @param attributes the attribute(s) to add to this node.
   */
  public void addAttributes(XmlAttribute...attributes) {
    for (XmlAttribute a : attributes) addAttribute(target, a);
  }

  private static void addAttribute(Element e, XmlAttribute a) {
    e.setAttribute(a.name(), a.value());
  }

  /**
   * Returns the underlying DOM <code>{@link Element}</code>.
   * @return the underlying DOM element.
   */
  public Element target() { return target; }

  /**
   * Returns the parent node of this node.
   * @return the parent node of this node.
   */
  public XmlNode parentNode() {
    return xmlNodeFrom(target.getParentNode());
  }

  /**
   * Returns the number of children in this node.
   * @return the number of children in this node.
   */
  public int size() {
    return target.getChildNodes().getLength();
  }

  /**
   * Returns the child at the given index.
   * @param index the given index.
   * @return the child at the given index.
   */
  public XmlNode child(int index) {
    return xmlNodeFrom(target.getChildNodes().item(index));
  }

  private static XmlNode xmlNodeFrom(Node n) {
    if (!(n instanceof Element)) return null;
    return new XmlNode((Element)n);
  }

  /**
   * Returns the value of the given attribute, or an empty <code>String</code> if this node does not contain an
   * attribute with the given name.
   * @param name the name of the attribute we are looking for.
   * @return the value of the attribute with the given name, or an empty <code>String</code> if this node does not
   * contain a matching attribute.
   */
  public String valueOfAttribute(String name) {
    return target.getAttribute(name);
  }

  /**
   * Returns the name of this node.
   * @return the name of this node.
   */
  public String name() {
    return target.getNodeName();
  }

  /**
   * Returns the text content of this node.
   * @return the text content of this node.
   */
  public String text() {
    return target.getTextContent();
  }

  /**
   * Returns the number of attributes in this node.
   * @return the number of attributes in this node.
   */
  public int attributeCount() {
    return target.getAttributes().getLength();
  }

  @Override public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    XmlNode other = (XmlNode) obj;
    return target.isEqualNode(other.target);
  }

  @Override public int hashCode() {
    int result = 1;
    result = HASH_CODE_PRIME * result + hashCodeFor(name());
    return result;
  }

  @Override public String toString() {
    return concat(
        getClass().getSimpleName(), "[",
        "target=", target, "]"
    );
  }
}
