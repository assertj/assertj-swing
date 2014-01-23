/*
 * Created on Nov 20, 2009
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
 * Copyright @2009-2013 the original author or authors.
 */
package org.fest.swing.edt;

import static javax.swing.SwingUtilities.isEventDispatchThread;
import static org.fest.util.Preconditions.checkNotNull;

import java.lang.ref.WeakReference;

import javax.annotation.Nonnull;
import javax.swing.JComponent;
import javax.swing.RepaintManager;

/**
 * <p>
 * This class is used to detect Event Dispatch Thread rule violations<br>
 * See <a href="http://java.sun.com/docs/books/tutorial/uiswing/misc/threads.html">How to Use Threads</a> for more info
 * </p>
 * 
 * <p>
 * This is a modification of original idea of Scott Delap.<br>
 * </p>
 * 
 * @author Scott Delap
 * @author Alexander Potochkin
 * 
 * https://swinghelper.dev.java.net/
 */
abstract class CheckThreadViolationRepaintManager extends RepaintManager {
  private final boolean completeCheck;

  private WeakReference<JComponent> lastComponent;

  CheckThreadViolationRepaintManager() {
    // it is recommended to pass the complete check
    this(true);
  }

  CheckThreadViolationRepaintManager(boolean completeCheck) {
    this.completeCheck = completeCheck;
  }

  @Override
  public synchronized void addInvalidComponent(JComponent component) {
    checkThreadViolations(checkNotNull(component));
    super.addInvalidComponent(component);
  }

  @Override
  public void addDirtyRegion(JComponent component, int x, int y, int w, int h) {
    checkThreadViolations(checkNotNull(component));
    super.addDirtyRegion(component, x, y, w, h);
  }

  private void checkThreadViolations(@Nonnull JComponent c) {
    if (!isEventDispatchThread() && (completeCheck || c.isShowing())) {
      boolean imageUpdate = false;
      boolean repaint = false;
      boolean fromSwing = false;
      StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
      for (StackTraceElement st : stackTrace) {
        if (repaint && st.getClassName().startsWith("javax.swing.")) {
          fromSwing = true;
        }
        if (repaint && "imageUpdate".equals(st.getMethodName())) {
          imageUpdate = true;
        }
        if ("repaint".equals(st.getMethodName())) {
          repaint = true;
          fromSwing = false;
        }
      }
      if (imageUpdate) {
        // assuming it is java.awt.image.ImageObserver.imageUpdate(...)
        // image was asynchronously updated, that's ok
        return;
      }
      if (repaint && !fromSwing) {
        // no problems here, since repaint() is thread safe
        return;
      }
      // ignore the last processed component
      if (lastComponent != null && c == lastComponent.get()) {
        return;
      }
      lastComponent = new WeakReference<JComponent>(c);
      violationFound(c, stackTrace);
    }
  }

  abstract void violationFound(@Nonnull JComponent c, @Nonnull StackTraceElement[] stackTrace);
}
