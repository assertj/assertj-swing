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
package org.assertj.swing.testng.listener;

import org.testng.IClass;

/**
 * Understands an <code>{@link IClass}</code> stub for testing purposes.
 * 
 * @author Alex Ruiz
 */
public class ClassStub implements IClass {

  private static final long serialVersionUID = 1L;

  private String name;
  private Class<?> realClass;

  /** @see org.testng.IClass#addInstance(java.lang.Object) */
  @Override
  public void addInstance(Object instance) {
  }

  /** @see org.testng.IClass#getInstanceCount() */
  @Override
  public int getInstanceCount() {
    return 0;
  }

  /** @see org.testng.IClass#getInstanceHashCodes() */
  @Override
  public long[] getInstanceHashCodes() {
    return null;
  }

  /** @see org.testng.IClass#getInstances(boolean) */
  @Override
  public Object[] getInstances(boolean create) {
    return null;
  }

  /** @see org.testng.IClass#getName() */
  @Override
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /** @see org.testng.IClass#getRealClass() */
  @Override
  public Class<?> getRealClass() {
    return realClass;
  }

  public void setRealClass(Class<?> realClass) {
    this.realClass = realClass;
  }
}
