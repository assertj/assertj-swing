/*
 * Created on Jul 19, 2008
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
 * Copyright @2008-2013 the original author or authors.
 */
package org.fest.swing.core;

import static java.awt.AWTEvent.KEY_EVENT_MASK;
import static java.awt.event.InputEvent.CTRL_MASK;
import static java.awt.event.InputEvent.SHIFT_MASK;
import static java.awt.event.KeyEvent.KEY_PRESSED;
import static java.awt.event.KeyEvent.VK_A;
import static org.fest.swing.core.InputModifiers.modifiersMatch;
import static org.fest.swing.core.InputModifiers.unify;
import static org.fest.util.Preconditions.checkNotNull;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;

import javax.annotation.Nonnull;

import org.fest.swing.util.ToolkitProvider;
import org.fest.util.VisibleForTesting;

/**
 * <p>
 * An escape valve for users to abort a running FEST-Swing test by pressing 'Ctrl + Shift + A'. The key combination to
 * use to abort test is configurable through the method
 * {@link EmergencyAbortListener#keyCombination(KeyPressInfo)}.
 * </p>
 * 
 * <p>
 * The following example shows to use this listener in a TestNG test:
 * <pre>
 * private EmergencyAbortListener listener;
 * 
 * &#064;BeforeMethod
 * public void setUp() {
 *   // set up your test fixture.
 *   listener = EmergencyAbortListener.registerInToolkit();
 * }
 * 
 * &#064;AfterMethod
 * public void tearDown() {
 *   // clean up resources.
 *   listener.unregister();
 * }
 * </pre>
 * </p>
 * 
 * <p>
 * Changing the default key combination for aborting test:
 * <pre>
 * listener = EmergencyAbortListener.registerInToolkit().{@link EmergencyAbortListener#keyCombination(KeyPressInfo) keyCombination}(key(VK_C).modifiers(SHIFT_MASK));
 * </pre>
 * </p>
 * 
 * @author <a href="mailto:simeon.fitch@mseedsoft.com">Simeon H.K. Fitch</a>
 * @author Alex Ruiz
 */
public class EmergencyAbortListener implements AWTEventListener {
  private static final long EVENT_MASK = KEY_EVENT_MASK;

  private final Toolkit toolkit;
  private final TestTerminator testTerminator;

  private int keyCode = VK_A;
  private int modifiers = unify(CTRL_MASK, SHIFT_MASK);

  /**
   * Attaches a new instance of {@link EmergencyAbortListener} to the given AWT {@code Toolkit}.
   * Any other instances of {@code EmergencyAbortListener} will be removed from the {@code Toolkit}.
   * 
   * @return the created listener.
   */
  public static EmergencyAbortListener registerInToolkit() {
    EmergencyAbortListener listener = new EmergencyAbortListener(ToolkitProvider.instance().defaultToolkit());
    listener.register();
    return listener;
  }

  @VisibleForTesting
  EmergencyAbortListener(@Nonnull Toolkit toolkit) {
    this(toolkit, new TestTerminator());
  }

  @VisibleForTesting
  EmergencyAbortListener(@Nonnull Toolkit toolkit, @Nonnull TestTerminator testTerminator) {
    this.testTerminator = testTerminator;
    this.toolkit = toolkit;
  }

  @VisibleForTesting
  void register() {
    removePrevious();
    toolkit.addAWTEventListener(this, EVENT_MASK);
  }

  private void removePrevious() {
    AWTEventListener[] listeners = toolkit.getAWTEventListeners(EVENT_MASK);
    for (AWTEventListener listener : listeners) {
      if (listener instanceof EmergencyAbortListener) {
        toolkit.removeAWTEventListener(listener);
      }
    }
  }

  /**
   * Sets the key combination that will terminate any FEST-Swing test. The default combination is 'Ctrl + Shift + A'.
   * 
   * @param keyPressInfo contains information about the key code and modifiers.
   * @return this listener.
   * @throws NullPointerException if the {@code KeyPressInfo} is {@code null}.
   */
  public EmergencyAbortListener keyCombination(@Nonnull KeyPressInfo keyPressInfo) {
    checkNotNull(keyPressInfo);
    keyCode = keyPressInfo.keyCode();
    modifiers = unify(checkNotNull(keyPressInfo.modifiers()));
    return this;
  }

  /**
   * Removes this listener from the {@code Toolkit} this listener is attached to.
   */
  public void unregister() {
    toolkit.removeAWTEventListener(this);
  }

  /**
   * Inspects key events for the key combination that should terminate any running FEST-Swing tests.
   * 
   * @param event the event to inspect.
   * @see java.awt.event.AWTEventListener#eventDispatched(java.awt.AWTEvent)
   */
  @Override
  public void eventDispatched(AWTEvent event) {
    if (event.getID() != KEY_PRESSED) {
      return;
    }
    if (!(event instanceof KeyEvent)) {
      return;
    }
    KeyEvent e = (KeyEvent) event;
    if (e.getKeyCode() != keyCode) {
      return;
    }
    if (!modifiersMatch(e, modifiers)) {
      return;
    }
    testTerminator.terminateTests();
  }

  @VisibleForTesting
  int keyCode() {
    return keyCode;
  }

  @VisibleForTesting
  int modifiers() {
    return modifiers;
  }
}
