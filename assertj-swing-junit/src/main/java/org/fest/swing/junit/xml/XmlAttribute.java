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

import static java.lang.String.valueOf;
import static org.fest.util.Objects.*;
import static org.fest.util.Strings.*;

/**
 * Understands an attribute of a <code>{@link XmlNode}</code>. This class is intended for internal use only.  It only
 * provides the necessary functionality needed by the FEST-Swing JUnit extension.
 *
 * @author Alex Ruiz
 */
public class XmlAttribute {

  private final String name;
  private final String value;

  /**
   * Creates a new <code>{@link XmlAttributeBuilder}</code>.
   * @param name the name of the attribute that the created builder will build.
   * @return the created <code>XmlAttributeBuilder</code>.
   */
  public static XmlAttributeBuilder name(String name) {
    return new XmlAttributeBuilder(name);
  }

  /**
   * Understands creation of <code>{@link XmlAttribute}</code>s.
   *
   * @author Alex Ruiz
   */
  public static class XmlAttributeBuilder {
    private final String name;

    XmlAttributeBuilder(String name) {
      this.name = name;
    }

    /**
     * Creates a new <code>{@link XmlAttribute}</code> using the attribute name passed when this builder was created and
     * the given value.
     * @param value the value of the attribute to create.
     * @return the created <code>XmlAttribute</code>.
     */
    public XmlAttribute value(String value) {
      return new XmlAttribute(name, value);
    }

    /**
     * Creates a new <code>{@link XmlAttribute}</code> using the attribute name passed when this builder was created and
     * the given value.
     * @param value the value of the attribute to create.
     * @return the created <code>XmlAttribute</code>.
     */
    public XmlAttribute value(long value) {
      return new XmlAttribute(name, valueOf(value));
    }

    /**
     * Creates a new <code>{@link XmlAttribute}</code> using the attribute name passed when this builder was created and
     * the given value.
     * @param value the value of the attribute to create.
     * @return the created <code>XmlAttribute</code>.
     */
    public XmlAttribute value(double value) {
      return new XmlAttribute(name, valueOf(value));
    }
  }

  XmlAttribute(String name, String value) {
    this.name = name;
    this.value = value;
  }

  /**
   * Returns the name of this attribute.
   * @return the name of this attribute.
   */
  public String name() { return name; }

  /**
   * Returns the value of this attribute.
   * @return the value of this attribute.
   */
  public String value() { return value; }

  /**
   * Indicates whether the given <code>Object</code> is equal to this attribute. To be equal, the given object should be
   * a <code>{@link XmlAttribute}</code> with its name and value equal to the ones in this attribute.
   * @param obj the <code>Object</code> to compare to.
   * @return <code>true</code> if the given <code>Object</code> is equal to this attribute, <code>false</code>
   * otherwise.
   */
  @Override public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    XmlAttribute other = (XmlAttribute) obj;
    if (!areEqual(name, other.name)) return false;
    return areEqual(value, other.value);
  }

  /**
   * Returns the hash code of this attribute, based on its name and value.
   * @return the hash code of this attribute.
   */
  @Override public int hashCode() {
    int result = 1;
    result = HASH_CODE_PRIME * result + hashCodeFor(name);
    result = HASH_CODE_PRIME * result + hashCodeFor(value);
    return result;
  }

  /**
   * Returns a <code>String</code> representation of this attribute.
   * @return a <code>String</code> representation of this attribute.
   */
  @Override public String toString() {
    return concat(
        getClass().getSimpleName(), "[",
        "name=", quote(name), ",",
        "value=", quote(value), "]"
    );
  }
}
