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

import static org.fest.reflect.core.Reflection.constructor;
import static org.junit.runner.Description.createSuiteDescription;
import junit.framework.JUnit4TestCaseFacade;

import org.junit.runner.Description;

/**
 * Tests for <code>{@link Tests}</code>.
 *
 * @author Alex Ruiz
 */
public abstract class Tests_TestCase {

  final JUnit4TestCaseFacade createJUnit4TestCaseFacade(String description) {
    return constructor().withParameterTypes(Description.class)
                        .in(JUnit4TestCaseFacade.class)
                        .newInstance(createSuiteDescription(description));
  }
}
