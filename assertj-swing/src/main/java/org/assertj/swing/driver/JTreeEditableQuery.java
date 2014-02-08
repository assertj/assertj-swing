package org.assertj.swing.driver;

import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JTree;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;

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
      protected @Nullable
      Boolean executeInEDT() {
        return tree.isEditable();
      }
    });
    return checkNotNull(result);
  }

  private JTreeEditableQuery() {
  }
}