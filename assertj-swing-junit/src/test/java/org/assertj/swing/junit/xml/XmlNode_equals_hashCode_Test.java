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
import static org.assertj.swing.junit.xml.XmlAttributes.attributes;
import static org.fest.test.EqualsHashCodeContractAssert.assertEqualsIsReflexive;
import static org.fest.test.EqualsHashCodeContractAssert.assertEqualsIsSymmetric;
import static org.fest.test.EqualsHashCodeContractAssert.assertEqualsIsTransitive;
import static org.fest.test.EqualsHashCodeContractAssert.assertMaintainsEqualsAndHashCodeContract;

import org.fest.test.EqualsHashCodeContractTestCase;
import org.junit.Test;

/**
 * Tests for <code>{@link XmlNode#equals(Object)}</code> and <code>{@link XmlNode#hashCode()}</code>.
 * 
 * @author Alex Ruiz
 */
public class XmlNode_equals_hashCode_Test extends XmlNode_TestCase implements EqualsHashCodeContractTestCase {

  @Override
  @Test
  public void equals_should_be_consistent() {
    XmlNode child1 = node.addNewNode("child", attributes(name("name1").value("value1"), name("name2").value("value2")));
    XmlNode child2 = node.addNewNode("child", attributes(name("name1").value("value1"), name("name2").value("value2")));
    assertThat(child1.equals(child2)).isTrue();
    child1.addAttribute(name("name3").value("value3"));
    assertThat(child1.equals(child2)).isFalse();
  }

  @Override
  @Test
  public void equals_should_be_reflexive() {
    assertEqualsIsReflexive(node);
  }

  @Override
  @Test
  public void equals_should_be_symmetric() {
    XmlNode child1 = node.addNewNode("child", attributes(name("name1").value("value1"), name("name2").value("value2")));
    XmlNode child2 = node.addNewNode("child", attributes(name("name1").value("value1"), name("name2").value("value2")));
    assertEqualsIsSymmetric(child1, child2);
  }

  @Override
  @Test
  public void equals_should_be_transitive() {
    XmlNode child1 = node.addNewNode("child", attributes(name("name1").value("value1"), name("name2").value("value2")));
    XmlNode child2 = node.addNewNode("child", attributes(name("name1").value("value1"), name("name2").value("value2")));
    XmlNode child3 = node.addNewNode("child", attributes(name("name1").value("value1"), name("name2").value("value2")));
    assertEqualsIsTransitive(child1, child2, child3);
  }

  @Override
  @Test
  public void should_maintain_equals_and_hashCode_contract() {
    XmlNode child1 = node.addNewNode("child", attributes(name("name1").value("value1"), name("name2").value("value2")));
    XmlNode child2 = node.addNewNode("child", attributes(name("name1").value("value1"), name("name2").value("value2")));
    assertMaintainsEqualsAndHashCodeContract(child1, child2);
  }

  @Override
  @Test
  public void should_not_be_equal_to_null() {
    assertThat(node.equals(null)).isFalse();
  }

  @Override
  @Test
  public void should_not_be_equal_to_Object_of_different_type() {
    assertThat(node.equals("Hello")).isFalse();
  }
}
