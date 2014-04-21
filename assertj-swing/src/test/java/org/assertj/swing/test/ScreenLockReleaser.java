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
