package org.assertj.swing.edt;

@FunctionalInterface
public interface GuiActionRunnable {
  void run() throws Exception;
}
