package org.assertj.swing.driver;

import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;

/**
 * Returns the pop-up menu associated with a {@link JMenu}. This query is executed in the event dispatch thread (EDT.)
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
final class JMenuPopupMenuQuery {
  @RunsInEDT
  static @Nonnull JPopupMenu popupMenuOf(final @Nonnull JMenu menu) {
    JPopupMenu result = execute(new GuiQuery<JPopupMenu>() {
      @Override
      protected @Nullable JPopupMenu executeInEDT() {
        return menu.getPopupMenu();
      }
    });
    return checkNotNull(result);
  }

  private JMenuPopupMenuQuery() {
  }
}