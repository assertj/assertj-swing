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
import static org.fest.test.EqualsHashCodeContractAssert.*;

import org.fest.test.EqualsHashCodeContractTestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for <code>{@link XmlAttribute#equals(Object)}</code> and
 * <code>{@link XmlAttribute#hashCode()}</code>.
 *
 * @author Alex Ruiz
 */
public class XmlAttribute_equals_hashCode_Test implements EqualsHashCodeContractTestCase {

  private XmlAttribute attribute;

  @Before public void setUp() {
    attribute = XmlAttribute.name("firstName").value("Anakin");
  }

  @Test
  public void should_have_consistent_equals() {
    XmlAttribute other = XmlAttribute.name("firstName").value("Anakin");
    assertThat(attribute.equals(other)).isTrue();
  }

  @Test
  public void should_have_reflexive_equals() {
    assertEqualsIsReflexive(attribute);
  }

  @Test
  public void should_have_symmetric_equals() {
    XmlAttribute other = XmlAttribute.name("firstName").value("Anakin");
    assertEqualsIsSymmetric(attribute, other);
  }

  @Test
  public void should_have_transitive_equals() {
    XmlAttribute other1 = XmlAttribute.name("firstName").value("Anakin");
    XmlAttribute other2 = XmlAttribute.name("firstName").value("Anakin");
    assertEqualsIsTransitive(attribute, other1, other2);
  }

  @Test
  public void should_maintain_equals_and_hashCode_contract() {
    XmlAttribute other = XmlAttribute.name("firstName").value("Anakin");
    assertMaintainsEqualsAndHashCodeContract(attribute, other);
  }

  @Test
  public void should_not_be_equal_to_null() {
    assertIsNotEqualToNull(attribute);
  }

  @Test
  public void should_not_be_equal_to_Object_not_being_of_same_type() {
    assertThat(attribute.equals("Hello")).isFalse();
  }
}
