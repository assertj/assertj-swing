package org.assertj.swing.driver;

import static org.assertj.swing.edt.GuiActionRunner.execute;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;

/**
 * Returns the {@code JTableHeader} in a {@code JTable}. This query is executed in the event dispatch thread (EDT.)
 * 
 * @see JTable#getTableHeader()
 * 
 * @author Alex Ruiz
 */
final class JTableHeaderQuery {
  @RunsInEDT
  static @Nullable
  JTableHeader tableHeader(final @Nonnull JTable table) {
    return execute(new GuiQuery<JTableHeader>() {
      @Override
      protected @Nullable
      JTableHeader executeInEDT() {
        return table.getTableHeader();
      }
    });
  }

  private JTableHeaderQuery() {
  }
}