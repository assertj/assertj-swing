package org.fest.swing.hierarchy;

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.timing.Pause.pause;

import java.beans.PropertyVetoException;

import javax.annotation.Nonnull;
import javax.swing.JInternalFrame;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.timing.Condition;

/**
 * Iconifies a given {@code JInternalFrame}. This task is executed in the event dispatch thread (EDT.)
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@RunsInEDT
final class JInternalFrameIconifyTask {
  static void iconify(final @Nonnull JInternalFrame internalFrame) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() throws PropertyVetoException {
        internalFrame.setIcon(true);
        pause(new Condition("JInternalFrame is iconified") {
          @Override
          public boolean test() {
            return internalFrame.isIcon();
          }
        });
      }
    });
  }

  private JInternalFrameIconifyTask() {}
}