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
import static org.fest.swing.junit.xml.XmlAttribute.name;
import static org.fest.test.EqualsHashCodeContractAssert.*;

import org.fest.test.EqualsHashCodeContractTestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for <code>{@link XmlAttributes#equals(Object)}</code> and <code>{@link XmlAttributes#hashCode()}</code>.
 *
 * @author Alex Ruiz
 */
public class XmlAttributes_equals_hashCode_Test implements EqualsHashCodeContractTestCase {

  private XmlAttributes attributes;

  @Before public void setUp() {
    attributes = XmlAttributes.attributes(name("firstName").value("Leia"), name("lastName").value("Organa"));
  }

  @Test
  public void should_have_consistent_equals() {
    XmlAttributes other = XmlAttributes.attributes(name("firstName").value("Leia"), name("lastName").value("Organa"));
    assertThat(attributes.equals(other)).isTrue();
  }

  @Test
  public void should_have_reflexive_equals() {
    assertEqualsIsReflexive(attributes);
  }

  @Test
  public void should_have_symmetric_equals() {
    XmlAttributes other = XmlAttributes.attributes(name("firstName").value("Leia"), name("lastName").value("Organa"));
    assertEqualsIsSymmetric(attributes, other);
  }

  @Test
  public void should_have_transitive_equals() {
    XmlAttributes other1 = XmlAttributes.attributes(name("firstName").value("Leia"), name("lastName").value("Organa"));
    XmlAttributes other2 = XmlAttributes.attributes(name("firstName").value("Leia"), name("lastName").value("Organa"));
    assertEqualsIsTransitive(attributes, other1, other2);
  }

  @Test
  public void should_maintain_equals_and_hashCode_contract() {
    XmlAttributes other = XmlAttributes.attributes(name("firstName").value("Leia"), name("lastName").value("Organa"));
    assertMaintainsEqualsAndHashCodeContract(attributes, other);
  }

  @Test
  public void should_not_be_equal_to_null() {
    assertThat(attributes.equals(null)).isFalse();
  }

  @Test
  public void should_not_be_equal_to_Object_not_being_of_same_type() {
    assertThat(attributes.equals("Hello")).isFalse();
  }
}
