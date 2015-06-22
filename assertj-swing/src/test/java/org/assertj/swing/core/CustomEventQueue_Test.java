/*
 * Copyright 2014 Entero Corporation. All Rights Reserved.
 * www.entero.com
 */
package org.assertj.swing.core;

import org.assertj.swing.test.recorder.KeyRecorder;
import org.assertj.swing.timing.Condition;
import org.junit.After;
import org.junit.Test;

import java.awt.*;
import java.util.concurrent.Executors;

import static java.awt.event.KeyEvent.*;
import static org.assertj.swing.timing.Pause.pause;
import static org.assertj.swing.timing.Timeout.timeout;
import static org.fest.util.Arrays.array;

/**
 * Tests for issue with slow waitForIdle with a custom event queue
 */
public class CustomEventQueue_Test extends BasicRobot_TestCase {
  private CustomEventQueue newEventQueue = new CustomEventQueue();

  @Override
  void beforeShowingWindow() {
    robot().settings().simpleWaitForIdle(true);
    Toolkit.getDefaultToolkit().getSystemEventQueue().push(newEventQueue);
  }

  @After
  public void cleanup() {
    newEventQueue.remove();
  }

  @Test
  public void type_keys_before_timeout() {
    giveFocusToTextField();
    final KeyRecorder recorder = KeyRecorder.attachTo(window().textField());
    Executors.newSingleThreadExecutor().execute(new Runnable() {
      public void run() {
        robot().pressAndReleaseKeys(VK_A, VK_B, VK_C);
      }
    });
    pause(new Condition("until all keys are typed") {
      @Override
      public boolean test() {
        Integer[] expectedKeys = array(VK_A, VK_B, VK_C);
        return recorder.keysWerePressed(expectedKeys) && recorder.keysWereReleased(expectedKeys);
      }
    }, timeout(1000));
  }

  private class CustomEventQueue extends EventQueue {
    protected void dispatchEvent(final AWTEvent event) {
      super.dispatchEvent(event);
    }

    public void remove() {
      pop();
    }
  }
}