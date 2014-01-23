package org.fest.swing.driver;

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Preconditions.checkNotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;

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

  private JMenuPopupMenuQuery() {}
}