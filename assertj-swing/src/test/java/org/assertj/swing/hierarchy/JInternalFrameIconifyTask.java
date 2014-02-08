package org.assertj.swing.hierarchy;

import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.timing.Pause.pause;

import java.beans.PropertyVetoException;

import javax.annotation.Nonnull;
import javax.swing.JInternalFrame;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiTask;
import org.assertj.swing.timing.Condition;

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

  private JInternalFrameIconifyTask() {
  }
}