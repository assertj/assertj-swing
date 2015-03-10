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
import static org.assertj.swing.junit.xml.XmlAttribute.name;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for <code>{@link XmlAttributes#toString()}</code>.
 * 
 * @author Alex Ruiz
 */
public class XmlAttributes_toString_Test {

  private XmlAttributes attributes;

  @Before
  public void setUp() {
    attributes = XmlAttributes.attributes(name("firstName").value("Leia"), name("lastName").value("Organa"));
  }

  @Test
  public void should_Implement_ToString() {
    String expected = "XmlAttributes[attributes=[XmlAttribute[name='firstName',value='Leia'], XmlAttribute[name='lastName',value='Organa']]]";
    assertThat(attributes.toString()).isEqualTo(expected);
  }
}
