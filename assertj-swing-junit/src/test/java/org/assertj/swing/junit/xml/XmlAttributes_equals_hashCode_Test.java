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
import static org.fest.test.EqualsHashCodeContractAssert.assertEqualsIsReflexive;
import static org.fest.test.EqualsHashCodeContractAssert.assertEqualsIsSymmetric;
import static org.fest.test.EqualsHashCodeContractAssert.assertEqualsIsTransitive;
import static org.fest.test.EqualsHashCodeContractAssert.assertMaintainsEqualsAndHashCodeContract;

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

  @Before
  public void setUp() {
    attributes = XmlAttributes.attributes(name("firstName").value("Leia"), name("lastName").value("Organa"));
  }

  @Override
  @Test
  public void equals_should_be_consistent() {
    XmlAttributes other = XmlAttributes.attributes(name("firstName").value("Leia"), name("lastName").value("Organa"));
    assertThat(attributes.equals(other)).isTrue();
  }

  @Override
  @Test
  public void equals_should_be_reflexive() {
    assertEqualsIsReflexive(attributes);
  }

  @Override
  @Test
  public void equals_should_be_symmetric() {
    XmlAttributes other = XmlAttributes.attributes(name("firstName").value("Leia"), name("lastName").value("Organa"));
    assertEqualsIsSymmetric(attributes, other);
  }

  @Override
  @Test
  public void equals_should_be_transitive() {
    XmlAttributes other1 = XmlAttributes.attributes(name("firstName").value("Leia"), name("lastName").value("Organa"));
    XmlAttributes other2 = XmlAttributes.attributes(name("firstName").value("Leia"), name("lastName").value("Organa"));
    assertEqualsIsTransitive(attributes, other1, other2);
  }

  @Override
  @Test
  public void should_maintain_equals_and_hashCode_contract() {
    XmlAttributes other = XmlAttributes.attributes(name("firstName").value("Leia"), name("lastName").value("Organa"));
    assertMaintainsEqualsAndHashCodeContract(attributes, other);
  }

  @Override
  @Test
  public void should_not_be_equal_to_null() {
    assertThat(attributes.equals(null)).isFalse();
  }

  @Override
  @Test
  public void should_not_be_equal_to_Object_of_different_type() {
    assertThat(attributes.equals("Hello")).isFalse();
  }
}
