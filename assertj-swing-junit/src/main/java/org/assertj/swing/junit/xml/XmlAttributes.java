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

import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.core.util.Objects.HASH_CODE_PRIME;
import static org.assertj.core.util.Objects.areEqual;
import static org.assertj.core.util.Objects.hashCodeFor;
import static org.assertj.core.util.Strings.concat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Understands a collection of attributes of a <code>{@link XmlNode}</code>. This class is intended for internal use
 * only. It only provides the necessary functionality needed by the FEST-Swing JUnit extension.
 * 
 * @author Alex Ruiz
 */
public class XmlAttributes implements Iterable<XmlAttribute> {

  private final List<XmlAttribute> attributes = new ArrayList<XmlAttribute>();

  /**
   * Creates a new <code>{@link XmlAttributes}</code>.
   * 
   * @param attributes the actual attributes.
   * @return the created <code>XmlAttributes</code>.
   */
  public static XmlAttributes attributes(XmlAttribute... attributes) {
    return new XmlAttributes(attributes);
  }

  private XmlAttributes(XmlAttribute... attributes) {
    this.attributes.addAll(newArrayList(attributes));
  }

  /**
   * Returns an iterator containing all the <code>{@link XmlAttribute}</code>s in this collection.
   * 
   * @return an iterator containing all the <code>XmlAttribute</code>s in this collection.
   */
  @Override
  public Iterator<XmlAttribute> iterator() {
    return attributes.iterator();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    XmlAttributes other = (XmlAttributes) obj;
    return areEqual(attributes, other.attributes);
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = HASH_CODE_PRIME * result + hashCodeFor(attributes);
    return result;
  }

  @Override
  public String toString() {
    return concat(getClass().getSimpleName(), "[", "attributes=", attributes, "]");
  }
}
