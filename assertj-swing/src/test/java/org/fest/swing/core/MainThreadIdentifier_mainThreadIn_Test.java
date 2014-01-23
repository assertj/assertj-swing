/*
 * Created on May 31, 2009
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.swing.core;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.util.Arrays.array;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link MainThreadIdentifier#mainThreadIn(Thread[])}.
 * 
 * @author Alex Ruiz
 */
public class MainThreadIdentifier_mainThreadIn_Test {
  private MainThreadIdentifier identifier;

  @Before
  public void setUp() {
    identifier = new MainThreadIdentifier();
  }

  @Test
  public void should_return_thread_with_name_equal_to_main() {
    Thread mainThread = new Thread("main");
    Thread[] allThreads = array(new Thread(), mainThread);
    assertThat(identifier.mainThreadIn(allThreads)).isSameAs(mainThread);
  }

  @Test
  public void should_return_null_if_thread_array__is_empty() {
    assertThat(identifier.mainThreadIn(new Thread[0])).isNull();
  }

  @Test
  public void should_return_null_if_thread_array__does_not_contain_main_thread() {
    Thread[] allThreads = array(new Thread(), new Thread());
    assertThat(identifier.mainThreadIn(allThreads)).isNull();
  }
}
