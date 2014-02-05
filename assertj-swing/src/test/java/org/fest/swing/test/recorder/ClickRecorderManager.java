package org.fest.swing.test.recorder;

import static org.fest.util.Lists.newArrayList;

import java.awt.Component;
import java.util.List;

import javax.annotation.Nonnull;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class ClickRecorderManager implements TestRule {
  private List<ToolkitClickRecorder> attachedToolkitClickRecorders = newArrayList();

  @Override
  public Statement apply(Statement base, Description description) {
    return new RecorderStatement(base);
  }

  public @Nonnull
  ClickRecorder attachDirectlyTo(@Nonnull Component target) {
    return ClickRecorder.attachTo(target);
  }

  public @Nonnull
  ToolkitClickRecorder attachToToolkitFor(@Nonnull Component target) {
    ToolkitClickRecorder clickRecorder = ToolkitClickRecorder.attachTo(target);
    attachedToolkitClickRecorders.add(clickRecorder);
    return clickRecorder;
  }

  private class RecorderStatement extends Statement {

    private Statement base;

    public RecorderStatement(Statement base) {
      this.base = base;
    }

    @Override
    public void evaluate() throws Throwable {
      try {
        base.evaluate();
      } finally {
        for (ToolkitClickRecorder recorder : attachedToolkitClickRecorders) {
          ToolkitClickRecorder.remove(recorder);
        }
        attachedToolkitClickRecorders.clear();
      }
    }
  }
}
