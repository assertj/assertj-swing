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
package org.assertj.swing.test;

import org.assertj.swing.lock.ScreenLock;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Allows releasing of {@link ScreenLock} locks that weren't released by the test itself.
 * 
 * @author Christian Rösch
 */
public class ScreenLockReleaser implements TestRule {

  @Override
  public Statement apply(Statement base, Description description) {
    return new ScreenLockReleaserStatement(base);
  }

  private static class ScreenLockReleaserStatement extends Statement {
    private Statement baseStatement;

    public ScreenLockReleaserStatement(Statement base) {
      this.baseStatement = base;
    }

    @Override
    public void evaluate() throws Throwable {
      try {
        baseStatement.evaluate();
      } finally {
        ScreenLock screenLock = ScreenLock.instance();
        if (screenLock.acquired()) {
          screenLock.release(screenLock.getOwner());
        }
      }
    }
  }
}
