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

import java.util.Collection;

import org.fest.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for <code>{@link XmlAttributes#equals(Object)}</code>.
 *
 * @author Alex Ruiz
 */
@RunWith(Parameterized.class)
public class XmlAttributes_equals_Test  {

  private XmlAttributes attributes;

  private final XmlAttributes notEqualAttributes;

  public XmlAttributes_equals_Test(XmlAttributes notEqualAttributes) {
    this.notEqualAttributes = notEqualAttributes;
  }

  @Parameters
  public static Collection<Object[]> notEqualAttributes() {
    return Collections.list(new Object[][] {
        { XmlAttributes.attributes(name("firstName").value("Han"), name("lastName").value("Solo")) },
        { XmlAttributes.attributes() },
        { XmlAttributes.attributes(name("name").value("Yoda")) }
    });
  }

  @Before public void setUp() {
    attributes = XmlAttributes.attributes(name("firstName").value("Leia"), name("lastName").value("Organa"));
  }

  @Test
  public void shouldNotBeEqualToDifferentObject() {
    assertThat(attributes.equals(notEqualAttributes)).isFalse();
  }
}
