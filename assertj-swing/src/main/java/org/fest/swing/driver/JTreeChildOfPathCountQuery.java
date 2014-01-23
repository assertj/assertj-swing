package org.fest.swing.driver;

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Preconditions.checkNotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;

/**
 * Returns how many children a node in a {@code JTree} has. This query is executed in the event dispatch thread (EDT.)
 * 
 * @author Alex Ruiz
 */
final class JTreeChildOfPathCountQuery {
  @RunsInEDT
  static int childCount(final @Nonnull JTree tree, final @Nonnull TreePath path) {
    Integer result = execute(new GuiQuery<Integer>() {
      @Override
      protected @Nullable Integer executeInEDT() {
        Object lastPathComponent = path.getLastPathComponent();
        return tree.getModel().getChildCount(lastPathComponent);
      }
    });
    return checkNotNull(result);
  }

  private JTreeChildOfPathCountQuery() {}
}