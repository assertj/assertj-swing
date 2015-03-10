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
package org.assertj.swing.driver;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 * Tests for {@link ModelValueToString#asText(Object)}.
 * 
 * @author Alex Ruiz
 */
public class ModelValueToString_asText_Test {
  @Test
  public void should_Return_Null_If_Model_Is_Null() {
    Object fromModel = null;
    assertThat(ModelValueToString.asText(fromModel)).isNull();
  }

  @Test
  public void should_Return_Text_From_Model() {
    assertThat(ModelValueToString.asText("Hello")).isEqualTo("Hello");
  }

  @Test
  public void should_Return_Null_If_Model_Does_Not_Implement_ToString() {
    class Person {
    }
    assertThat(ModelValueToString.asText(new Person())).isNull();
  }
}
