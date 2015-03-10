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
package org.assertj.swing.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Arrays.array;

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
  public void should_Return_Thread_With_Name_Equal_To_Main() {
    Thread mainThread = new Thread("main");
    Thread[] allThreads = array(new Thread(), mainThread);
    assertThat(identifier.mainThreadIn(allThreads)).isSameAs(mainThread);
  }

  @Test
  public void should_Return_Null_If_Thread_Array__Is_Empty() {
    assertThat(identifier.mainThreadIn(new Thread[0])).isNull();
  }

  @Test
  public void should_Return_Null_If_Thread_Array__Does_Not_Contain_Main_Thread() {
    Thread[] allThreads = array(new Thread(), new Thread());
    assertThat(identifier.mainThreadIn(allThreads)).isNull();
  }
}
