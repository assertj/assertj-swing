package org.fest.swing.driver;

import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.util.Preconditions.checkNotNull;

import javax.annotation.Nonnull;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;

/**
 * Indicates whether the node (in a {@code JTree}) identified by the given path is expanded. This query is executed in
 * the event dispatch thread (EDT.)
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
final class JTreeExpandedPathQuery {
  @RunsInEDT
  static boolean isExpanded(final @Nonnull JTree tree, final @Nonnull TreePath path) {
    Boolean result = execute(new GuiQuery<Boolean>() {
      @Override
      protected Boolean executeInEDT() {
        return tree.isExpanded(path);
      }
    });
    return checkNotNull(result);
  }

  private JTreeExpandedPathQuery() {}
}