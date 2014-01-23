/*
 * Created on Mar 18, 2009
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
package org.fest.swing.junit.v4_3_1.runner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.internal.runners.BeforeAndAfterRunner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

class InnerRunner extends BeforeAndAfterRunner {
  
  private final GUITestRunner delegate;
  private final RunNotifier notifier;

  InnerRunner(GUITestRunner delegate, RunNotifier notifier) {
    super(delegate.testClass(), BeforeClass.class, AfterClass.class, null);
    this.delegate = delegate;
    this.notifier = notifier;
  }

  protected void runUnprotected() {
    delegate.doRun(notifier);
  }

  protected void addFailure(Throwable targetException) {
    notifier.fireTestFailure(new Failure(delegate.getDescription(), targetException));
  }
}