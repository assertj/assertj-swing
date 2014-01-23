/*
 * Created on Sep 24, 2007
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
package org.fest.swing.test.recorder;

import static org.fest.util.Lists.newArrayList;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * An event listener that records key events.
 *
 * @author Alex Ruiz
 */
public class KeyRecorder extends KeyAdapter {
  public static KeyRecorder attachTo(Component target) {
    return new KeyRecorder(target);
  }

  private static class KeyEventRecord {
    final int keyCode;

    KeyEventRecord(KeyEvent e) {
      this.keyCode = e.getKeyCode();
    }
  }

  private final List<KeyEventRecord> keysPressed = newKeyEventRecordList();
  private final List<KeyEventRecord> keysReleased = newKeyEventRecordList();

  private static List<KeyEventRecord> newKeyEventRecordList() {
    return new CopyOnWriteArrayList<KeyEventRecord>();
  }

  protected KeyRecorder(Component target) {
    attach(this, target);
  }

  private static void attach(KeyRecorder keyListener, Component target) {
    target.addKeyListener(keyListener);
  }

  @Override
  public void keyPressed(KeyEvent e) {
    keysPressed.add(new KeyEventRecord(e));
  }

  @Override
  public void keyReleased(KeyEvent e) {
    keysReleased.add(new KeyEventRecord(e));
  }

  public boolean noKeysReleased() {
    return keyCodesFrom(keysReleased).isEmpty();
  }

  public boolean keysWerePressed(Integer... expectedKeys) {
    return containsKeys(keysPressed, expectedKeys);
  }

  public boolean keysWereReleased(Integer... expectedKeys) {
    return containsKeys(keysReleased, expectedKeys);
  }

  private boolean containsKeys(List<KeyEventRecord> actualKeys, Integer... expectedKeys) {
    return keyCodesFrom(actualKeys).containsAll(newArrayList(expectedKeys));
  }

  private List<Integer> keyCodesFrom(List<KeyEventRecord> actualKeys) {
    List<Integer> keys = new ArrayList<Integer>();
    if (actualKeys.isEmpty()) {
      return keys;
    }
    for (KeyEventRecord r : actualKeys) {
      keys.add(r.keyCode);
    }
    return keys;
  }
}
