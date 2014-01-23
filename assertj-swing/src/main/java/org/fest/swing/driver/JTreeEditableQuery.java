package org.fest.swing.driver;

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Preconditions.checkNotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JTree;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;

/**
 * Indicates whether a {@code JTree} is editable.
 * 
 * @author Alex Ruiz
 */
final class JTreeEditableQuery {
  @RunsInEDT
  static boolean isEditable(final @Nonnull JTree tree) {
    Boolean result = execute(new GuiQuery<Boolean>() {
      @Override
      protected @Nullable Boolean executeInEDT() {
        return tree.isEditable();
      }
    });
    return checkNotNull(result);
  }

  private JTreeEditableQuery() {}
}