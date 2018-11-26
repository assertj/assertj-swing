/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2018 the original author or authors.
 */
package org.assertj.swing.driver;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

import org.assertj.swing.data.TableCell;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.assertj.swing.test.swing.TestTable;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * Test for <a href="https://github.com/joel-costigliola/assertj-swing/issues/169">github.com - assertj-swing #169</a>
 * 
 * @author Angelo Gülle
 */
public class GitHub_169_DisabledComponentTest extends RobotBasedTestCase {

  @Rule
  public TemporaryFolder temporaryFolder = new TemporaryFolder();

  protected FrameFixture window;

  @Override
  protected void onSetUp() {
    DisabledTableFrame mainFrame = GuiActionRunner.execute(() -> new DisabledTableFrame());

    window = new FrameFixture(robot, mainFrame);
  }

  @Test
  public void openPopupMenu() {
    window.table().showPopupMenuAt(TableCell.row(0).column(0));
  }

  class DisabledTableFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public DisabledTableFrame() {
      setSize(200, 100);
      setTitle(getClass().getCanonicalName());
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      JTable table = new TestTable(1, 1);
      add(table);

      table.addMouseListener(new MouseAdapter() {

        @Override
        public void mousePressed(MouseEvent e) {
          if (e.isPopupTrigger()) {
            showPopup();
          }
        }

        @SuppressWarnings("unused")
        private void showPopup() {
          new PopupMenu();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
          if (e.isPopupTrigger()) {
            showPopup();
          }
        }

      });

      table.setEnabled(false);
      setVisible(true);
    }
  }

  class PopupMenu extends JPopupMenu {

    private static final long serialVersionUID = 1L;

    public PopupMenu() {

      JMenuItem menuItem = new JMenuItem("TEST");
      add(menuItem);
      setVisible(true);
    }
  }
}