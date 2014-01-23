/*
 * Created on Dec 21, 2007
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2007-2013 the original author or authors.
 */
package org.fest.swing.fixture;

import static org.fest.swing.edt.GuiActionRunner.execute;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.fest.swing.edt.GuiQuery;
import org.fest.swing.test.core.RobotBasedTestCase;
import org.junit.Test;

/**
 * Test for <a href="http://code.google.com/p/fest/issues/detail?id=80">Bug 80</a>.
 * 
 * @author Wim Deblauwe
 * @author Yvonne Wang
 */
public class Bug80_JDialogLookup_Test extends RobotBasedTestCase {
  private DialogFixture starter;

  @Override
  protected void onSetUp() {
    JDialogStarter optionPaneStarter = JDialogStarter.createNew();
    starter = new DialogFixture(robot, optionPaneStarter);
  }

  @Test
  public void should_find_nested_Dialog_two_times() {
    starter.show();
    starter.requireVisible();
    starter.button("start").click();
    showAndHideNestedDialog();
    starter.button("start").click();
    showAndHideNestedDialog();
  }

  private void showAndHideNestedDialog() {
    DialogFixture nested = starter.dialog("NestedDialog");
    nested.requireVisible();
    nested.requireModal();
    nested.button().click();
    nested.requireNotVisible();
  }

  static class JDialogStarter extends JDialog {
    static JDialogStarter createNew() {
      return execute(new GuiQuery<JDialogStarter>() {
        @Override
        protected JDialogStarter executeInEDT() {
          return new JDialogStarter();
        }
      });
    }

    private JDialogStarter() {
      setTitle(Bug80_JDialogLookup_Test.class.getSimpleName());
      setContentPane(createContentPane());
    }

    private Container createContentPane() {
      JPanel panel = new JPanel();
      JButton startButton = new JButton(new OpenJDialogAction());
      startButton.setName("start");
      panel.add(startButton);
      return panel;
    }

    private class OpenJDialogAction extends AbstractAction {
        OpenJDialogAction() {
        super("Start!");
      }

      @Override
      public void actionPerformed(ActionEvent e) {
        NestedJDialog dialog = new NestedJDialog(JDialogStarter.this);
        dialog.pack();
        dialog.setVisible(true);
      }
    }

    private static class NestedJDialog extends JDialog {
        NestedJDialog(JDialog owner) {
        super(owner, true);
        setContentPane(createContentPane());
        setName("NestedDialog");
      }

      private JPanel createContentPane() {
        JPanel result = new JPanel();
        result.add(new JLabel("Nested dialog"));
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            NestedJDialog.this.dispose();
          }
        });
        closeButton.setName("close");
        result.add(closeButton);
        return result;
      }
    }
  }
}
