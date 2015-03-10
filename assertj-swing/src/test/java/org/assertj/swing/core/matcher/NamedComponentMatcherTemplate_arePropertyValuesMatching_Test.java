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
package org.assertj.swing.core.matcher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Objects.HASH_CODE_PRIME;
import static org.assertj.core.util.Objects.areEqual;
import static org.assertj.core.util.Objects.hashCodeFor;

import javax.swing.JLabel;

import org.junit.Test;

/**
 * Base test case for {@link NamedComponentMatcherTemplate}.
 * 
 * @author Alex Ruiz
 */
public class NamedComponentMatcherTemplate_arePropertyValuesMatching_Test extends
    NamedComponentMatcherTemplate_TestCase {
  @Test
  public void should_Match_Values_Using_Equality_If_Values_Are_POJOs() {
    matcher = new Matcher(JLabel.class, "hello");
    assertThat(matcher.arePropertyValuesMatching(new Dog("Bob"), new Dog("Bob"))).isTrue();
  }

  private static class Dog {
    private final String name;

    Dog(String name) {
      this.name = name;
    }

    @Override
    public int hashCode() {
      final int prime = HASH_CODE_PRIME;
      int result = 1;
      result = prime * result + hashCodeFor(name);
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      Dog other = (Dog) obj;
      return areEqual(name, other.name);
    }
  }
}
