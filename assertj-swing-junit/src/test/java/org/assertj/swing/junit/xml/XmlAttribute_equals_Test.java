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
import static org.assertj.core.util.Lists.newArrayList;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for <code>{@link XmlAttribute#equals(Object)}</code>.
 * 
 * @author Alex Ruiz
 */
@RunWith(Parameterized.class)
public class XmlAttribute_equals_Test {

  private XmlAttribute attribute;

  private final XmlAttribute notEqualAttribute;

  public XmlAttribute_equals_Test(XmlAttribute notEqualAttribute) {
    this.notEqualAttribute = notEqualAttribute;
  }

  @Parameters
  public static Collection<Object[]> notEqualAttributes() {
    return newArrayList(new Object[][] { { XmlAttribute.name("firstName").value("Leia") },
        { XmlAttribute.name("first").value("Anakin") }, { XmlAttribute.name("lastName").value("Skywalker") } });
  }

  @Before
  public void setUp() {
    attribute = XmlAttribute.name("firstName").value("Anakin");
  }

  @Test
  public void shouldNotBeEqualToDifferentObject() {
    assertThat(attribute.equals(notEqualAttribute)).isFalse();
  }
}
