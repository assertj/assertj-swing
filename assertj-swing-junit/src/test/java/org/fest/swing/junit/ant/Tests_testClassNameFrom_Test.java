/*
 * Created on Mar 19, 2009
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
package org.fest.swing.junit.ant;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.reflect.core.Reflection.*;
import junit.framework.JUnit4TestCaseFacade;
import junit.framework.TestCase;

import org.apache.tools.ant.taskdefs.optional.junit.JUnitTaskMirrorImpl;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest;
import org.junit.Test;

/**
 * Tests for <code>{@link Tests}</code>.
 *
 * @author Alex Ruiz
 */
public abstract class Tests_testClassNameFrom_Test extends Tests_TestCase {

  @Test
  public void shouldReturnTestClassNameFromVmExitErrorTest() {
    Class<?> vmExitErrorTestClass = staticInnerClass("VmExitErrorTest").in(JUnitTaskMirrorImpl.class).get();
    Object test = constructor().withParameterTypes(String.class, JUnitTest.class, String.class)
                               .in(vmExitErrorTestClass)
                               .newInstance("someMessage", new JUnitTest("testClassName"), "testName");
    assertThat(test).isInstanceOf(junit.framework.Test.class);
    assertThat(Tests.testClassNameFrom((junit.framework.Test)test)).isEqualTo("testClassName");
  }

  @Test
  public void shouldReturnToStringAsClassNameIfTestIsJUnit4TestCaseFacade() {
    JUnit4TestCaseFacade test = createJUnit4TestCaseFacade("hello");
    assertThat(Tests.testClassNameFrom(test)).isEqualTo(JUnit4TestCaseFacade.class.getName());
  }

  @Test
  public void shouldReturnToStringWithoutClassNameAsClasNameIfTestIsJUnit4TestCaseFacade() {
    JUnit4TestCaseFacade test = createJUnit4TestCaseFacade("hello(world)");
    assertThat(Tests.testClassNameFrom(test)).isEqualTo("world");
  }

  @Test
  public void shouldReturnToStringAsClassNameIfTestIsInstanceOfTestCase() {
    TestCase test = new TestCase("Leia") {};
    assertThat(Tests.testClassNameFrom(test)).isEqualTo(test.getClass().getName());
  }

}
