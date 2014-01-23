/*
 * Created on Apr 12, 2009
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

import org.fest.swing.junit.xml.XmlAttribute.XmlAttributeBuilder;
import org.junit.Test;

/**
 * Tests for <code>{@link XmlAttributeBuilder#value(double)}</code>.
 *
 * @author Alex Ruiz
 */
public class XmlAttributeBuilder_value_withDouble_Test {

  @Test
  public void should_create_attribute() {
    XmlAttribute other = XmlAttribute.name("capacity").value(0.2d);
    assertThat(other.name()).isEqualTo("capacity");
    assertThat(other.value()).isEqualTo("0.2");
  }
}
