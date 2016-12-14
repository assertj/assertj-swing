package org.assertj.swing.fixture;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.finder.JFileChooserFinder;
import org.assertj.swing.test.core.RobotBasedTestCase;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/** Test for <a href="https://github.com/joel-costigliola/assertj-swing/issues/191">github.com - assertj-swing #191</a> */
public class GitHub_191_FileChooser_Test extends RobotBasedTestCase {
  @Rule
  public TemporaryFolder temporaryFolder = new TemporaryFolder();

  protected FrameFixture window;

  @Override
  protected void onSetUp() {
    FileChooserFrame mainFrame = GuiActionRunner.execute(() -> {
      return new FileChooserFrame();
    });
    window = new FrameFixture(robot, mainFrame);
  }

  @Test
  public void openFileChooser() throws IOException {
    selectFile();
  }

  @Test
  public void openFileChooserAgain() throws IOException {
    selectFile();
  }

  private void selectFile() throws IOException {
    window.button().click();
    JFileChooserFixture fileChooser = JFileChooserFinder.findFileChooser()
                                                        .using(robot);

    File tempFile = temporaryFolder.newFile("issue-191.txt");
    System.out.println(tempFile.getAbsolutePath());
    fileChooser.setCurrentDirectory(temporaryFolder.getRoot());
    fileChooser.selectFile(tempFile);
    fileChooser.approve();

    window.textBox().requireText(tempFile.getAbsolutePath());
  }

  private class FileChooserFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public FileChooserFrame() {
      setSize(200, 100);
      setTitle(getClass().getCanonicalName());
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      setLayout(new FlowLayout());

      JTextField textField = new JTextField();
      textField.setPreferredSize(new Dimension(200, 20));
      JButton button = new JButton("FileChooser");
      JFrame parentFrame = this;
      button.addActionListener(e -> {
        JFileChooser fileChooser = new JFileChooser();
        int returnVal = fileChooser.showOpenDialog(parentFrame);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
          File file = fileChooser.getSelectedFile();
          textField.setText(file.getAbsolutePath());
        }
      });

      add(textField);
      add(button);
      setVisible(true);
    }
  }
}