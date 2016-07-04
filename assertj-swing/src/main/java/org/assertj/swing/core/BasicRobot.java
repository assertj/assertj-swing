/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
package org.assertj.swing.core;

import static java.awt.event.InputEvent.BUTTON1_MASK;
import static java.awt.event.InputEvent.BUTTON2_MASK;
import static java.awt.event.InputEvent.BUTTON3_MASK;
import static java.awt.event.KeyEvent.CHAR_UNDEFINED;
import static java.awt.event.KeyEvent.KEY_TYPED;
import static java.awt.event.KeyEvent.VK_UNDEFINED;
import static java.awt.event.WindowEvent.WINDOW_CLOSING;
import static java.lang.System.currentTimeMillis;
import static java.lang.System.lineSeparator;
import static javax.swing.SwingUtilities.getWindowAncestor;
import static javax.swing.SwingUtilities.isEventDispatchThread;
import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.assertj.core.util.Sets.newHashSet;
import static org.assertj.core.util.Strings.concat;
import static org.assertj.swing.awt.AWT.centerOf;
import static org.assertj.swing.awt.AWT.visibleCenterOf;
import static org.assertj.swing.core.ActivateWindowTask.activateWindow;
import static org.assertj.swing.core.ComponentIsFocusableQuery.isFocusable;
import static org.assertj.swing.core.ComponentRequestFocusTask.giveFocusTo;
import static org.assertj.swing.core.FocusOwnerFinder.focusOwner;
import static org.assertj.swing.core.FocusOwnerFinder.inEdtFocusOwner;
import static org.assertj.swing.core.InputModifiers.unify;
import static org.assertj.swing.core.MouseButton.LEFT_BUTTON;
import static org.assertj.swing.core.MouseButton.RIGHT_BUTTON;
import static org.assertj.swing.core.Scrolling.scrollToVisible;
import static org.assertj.swing.core.WindowAncestorFinder.windowAncestorOf;
import static org.assertj.swing.edt.GuiActionRunner.execute;
import static org.assertj.swing.exception.ActionFailedException.actionFailure;
import static org.assertj.swing.format.Formatting.format;
import static org.assertj.swing.format.Formatting.inEdtFormat;
import static org.assertj.swing.hierarchy.NewHierarchy.ignoreExistingComponents;
import static org.assertj.swing.keystroke.KeyStrokeMap.keyStrokeFor;
import static org.assertj.swing.query.ComponentShowingQuery.isShowing;
import static org.assertj.swing.timing.Pause.pause;
import static org.assertj.swing.util.Modifiers.keysFor;
import static org.assertj.swing.util.Modifiers.updateModifierWithKeyCode;
import static org.assertj.swing.util.TimeoutWatch.startWatchWithTimeoutOf;

import java.applet.Applet;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.InvocationEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

import org.assertj.core.util.VisibleForTesting;
import org.assertj.swing.annotation.RunsInCurrentThread;
import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.exception.ComponentLookupException;
import org.assertj.swing.exception.UnexpectedException;
import org.assertj.swing.exception.WaitTimedOutError;
import org.assertj.swing.hierarchy.ComponentHierarchy;
import org.assertj.swing.hierarchy.ExistingHierarchy;
import org.assertj.swing.input.InputState;
import org.assertj.swing.lock.ScreenLock;
import org.assertj.swing.monitor.WindowMonitor;
import org.assertj.swing.util.Pair;
import org.assertj.swing.util.TimeoutWatch;
import org.assertj.swing.util.ToolkitProvider;

/**
 * Default implementation of {@link Robot}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 * @author Christian Rösch
 *
 * @see Robot
 */
public class BasicRobot implements Robot {
  private static final int POPUP_DELAY = 10000;
  private static final int POPUP_TIMEOUT = 5000;
  private static final int WINDOW_DELAY = 20000;

  private static final ComponentMatcher POPUP_MATCHER = new TypeMatcher(JPopupMenu.class, true);

  @GuardedBy("this")
  private volatile boolean active;

  private static final Runnable EMPTY_RUNNABLE = new Runnable() {
    @Override
    public void run() {
    }
  };

  private static final int BUTTON_MASK = BUTTON1_MASK | BUTTON2_MASK | BUTTON3_MASK;

  private static Toolkit toolkit = ToolkitProvider.instance().defaultToolkit();
  private static WindowMonitor windowMonitor = WindowMonitor.instance();
  private static InputState inputState = new InputState(toolkit);

  private final ComponentHierarchy hierarchy;
  private final Object screenLockOwner;
  private final ComponentFinder finder;
  private final Settings settings;
  private final AWTEventPoster eventPoster;
  private final InputEventGenerator eventGenerator;
  private final UnexpectedJOptionPaneFinder unexpectedJOptionPaneFinder;

  /**
   * Creates a new {@link Robot} with a new AWT hierarchy. The created {@code Robot} will not be able to access any AWT
   * and Swing {@code Component}s that were created before it.
   *
   * @return the created {@code Robot}.
   */
  public static @Nonnull Robot robotWithNewAwtHierarchy() {
    Object screenLockOwner = acquireScreenLock();
    return new BasicRobot(screenLockOwner, ignoreExistingComponents());
  }

  public static @Nonnull Robot robotWithNewAwtHierarchyWithoutScreenLock() {
    return new BasicRobot(null, ignoreExistingComponents());
  }

  /**
   * Creates a new {@link Robot} that has access to all the AWT and Swing {@code Component}s in the AWT hierarchy.
   *
   * @return the created {@code Robot}.
   */
  public static @Nonnull Robot robotWithCurrentAwtHierarchy() {
    Object screenLockOwner = acquireScreenLock();
    return new BasicRobot(screenLockOwner, new ExistingHierarchy());
  }

  // TODO document
  public static @Nonnull Robot robotWithCurrentAwtHierarchyWithoutScreenLock() {
    return new BasicRobot(null, new ExistingHierarchy());
  }

  private static @Nonnull Object acquireScreenLock() {
    Object screenLockOwner = new Object();
    ScreenLock.instance().acquire(screenLockOwner);
    return screenLockOwner;
  }

  @VisibleForTesting
  BasicRobot(@Nullable Object screenLockOwner, @Nonnull ComponentHierarchy hierarchy) {
    this.screenLockOwner = screenLockOwner;
    this.hierarchy = hierarchy;
    settings = new Settings();
    eventGenerator = new RobotEventGenerator(settings);
    eventPoster = new AWTEventPoster(toolkit, inputState, windowMonitor, settings);
    finder = new BasicComponentFinder(hierarchy, settings);
    unexpectedJOptionPaneFinder = new UnexpectedJOptionPaneFinder(finder);
    active = true;
  }

  @Override
  public @Nonnull ComponentPrinter printer() {
    return finder().printer();
  }

  @Override
  public @Nonnull ComponentFinder finder() {
    return finder;
  }

  @RunsInEDT
  @Override
  public void showWindow(@Nonnull Window w) {
    showWindow(w, null, true);
  }

  @RunsInEDT
  @Override
  public void showWindow(@Nonnull Window w, @Nonnull Dimension size) {
    showWindow(w, size, true);
  }

  @RunsInEDT
  @Override
  public void showWindow(@Nonnull final Window w, @Nullable final Dimension size, final boolean pack) {
    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        if (pack) {
          packAndEnsureSafePosition(w);
        }
        if (size != null) {
          w.setSize(size);
        }
        w.setVisible(true);
      }
    });
    waitForWindow(w);
  }

  @RunsInCurrentThread
  private void packAndEnsureSafePosition(@Nonnull Window w) {
    w.pack();
    w.setLocation(100, 100);
  }

  @RunsInEDT
  private void waitForWindow(@Nonnull Window w) {
    long start = currentTimeMillis();
    while (!windowMonitor.isWindowReady(w) || !isShowing(w)) {
      long elapsed = currentTimeMillis() - start;
      if (elapsed > WINDOW_DELAY) {
        throw new WaitTimedOutError(concat("Timed out waiting for Window to open (", String.valueOf(elapsed), "ms)"));
      }
      pause();
    }
  }

  @RunsInEDT
  @Override
  public void close(@Nonnull Window w) {
    WindowEvent event = new WindowEvent(w, WINDOW_CLOSING);
    // If the window contains an applet, send the event on the applet's queue instead to ensure a shutdown from the
    // applet's context (assists AppletViewer cleanup).
    Component applet = findAppletDescendent(w);
    EventQueue eventQueue = windowMonitor.eventQueueFor(applet != null ? applet : w);
    checkNotNull(eventQueue).postEvent(event);
    waitForIdle();
  }

  /**
   * Returns the {@code Applet} descendant of the given AWT {@code Container}, if any.
   *
   * @param c the given {@code Container}.
   * @return the {@code Applet} descendant of the given AWT {@code Container}, or {@code null} if none is found.
   */
  @RunsInEDT
  private @Nullable Applet findAppletDescendent(@Nonnull Container c) {
    List<Component> found = newArrayList(finder.findAll(c, new TypeMatcher(Applet.class)));
    if (found.size() == 1) {
      return (Applet) found.get(0);
    }
    return null;
  }

  @RunsInEDT
  @Override
  public void focusAndWaitForFocusGain(@Nonnull Component c) {
    focus(c, true);
  }

  @RunsInEDT
  @Override
  public void focus(@Nonnull Component c) {
    focus(c, false);
  }

  @RunsInEDT
  private void focus(@Nonnull Component target, boolean wait) {
    Component currentOwner = inEdtFocusOwner();
    if (currentOwner == target) {
      return;
    }
    FocusMonitor focusMonitor = FocusMonitor.attachTo(target);
    Component newOwner = inEdtFocusOwner();
    if (newOwner == target) {
      target.removeFocusListener(focusMonitor);
      return;
    }
    // for pointer focus
    moveMouse(target);
    // Make sure the correct window is in front
    activateWindowOfFocusTarget(target, currentOwner);
    giveFocusTo(target);
    try {
      if (wait) {
        TimeoutWatch watch = startWatchWithTimeoutOf(settings().timeoutToBeVisible());
        while (!focusMonitor.hasFocus()) {
          if (watch.isTimeOut()) {
            throw actionFailure(concat("Focus change to ", format(target), " failed", " focus owner: ",
                                       format(FocusOwnerFinder.focusOwner())));
          }
          pause();
        }
      }
    } finally {
      target.removeFocusListener(focusMonitor);
    }
  }

  @RunsInEDT
  private void activateWindowOfFocusTarget(@Nullable Component target, @Nullable Component currentOwner) {
    Pair<Window, Window> windowAncestors = windowAncestorsOf(currentOwner, target);
    Window currentOwnerAncestor = windowAncestors.first;
    Window targetAncestor = windowAncestors.second;
    if (currentOwnerAncestor == targetAncestor) {
      return;
    }
    activate(checkNotNull(targetAncestor));
    waitForIdle();
  }

  @RunsInEDT
  private static Pair<Window, Window> windowAncestorsOf(final @Nullable Component one, final @Nullable Component two) {
    return execute(new GuiQuery<Pair<Window, Window>>() {
      @Override
      protected Pair<Window, Window> executeInEDT() throws Throwable {
        return Pair.of(windowAncestor(one), windowAncestor(two));
      }

      private @Nullable Window windowAncestor(Component c) {
        return (c != null) ? windowAncestorOf(c) : null;
      }
    });
  }

  /**
   * Activates the given AWT {@code Window}. "Activate" means that the given window gets the keyboard focus.
   *
   * @param w the window to activate.
   */
  @RunsInEDT
  private void activate(@Nonnull Window w) {
    activateWindow(w);
    moveMouse(w); // For pointer-focus systems
  }

  @RunsInEDT
  @Override
  public synchronized void cleanUp() {
    cleanUp(true);
  }

  @RunsInEDT
  @Override
  public synchronized void cleanUpWithoutDisposingWindows() {
    cleanUp(false);
  }

  @RunsInEDT
  private void cleanUp(boolean disposeWindows) {
    try {
      if (disposeWindows) {
        disposeWindows(hierarchy);
      }
      releaseMouseButtons();
    } finally {
      active = false;
      releaseScreenLock();
    }
  }

  private void releaseScreenLock() {
    ScreenLock screenLock = ScreenLock.instance();
    if (screenLock.acquiredBy(screenLockOwner)) {
      screenLock.release(screenLockOwner);
    }
  }

  @RunsInEDT
  private static void disposeWindows(final @Nonnull ComponentHierarchy hierarchy) {
    execute(() -> {
      for (Container c : hierarchy.roots()) {
        if (c instanceof Window) {
          dispose(hierarchy, (Window) c);
        }
      }
    });
  }

  @RunsInCurrentThread
  private static void dispose(final @Nonnull ComponentHierarchy hierarchy, @Nonnull Window w) {
    hierarchy.dispose(w);
    w.setVisible(false);
    w.dispose();
  }

  @RunsInEDT
  @Override
  public void click(@Nonnull Component c) {
    click(c, LEFT_BUTTON);
  }

  @RunsInEDT
  @Override
  public void rightClick(@Nonnull Component c) {
    click(c, RIGHT_BUTTON);
  }

  @RunsInEDT
  @Override
  public void click(@Nonnull Component c, @Nonnull MouseButton button) {
    click(c, button, 1);
  }

  @RunsInEDT
  @Override
  public void doubleClick(@Nonnull Component c) {
    click(c, LEFT_BUTTON, 2);
  }

  @RunsInEDT
  @Override
  public void click(@Nonnull Component c, @Nonnull MouseButton button, int times) {
    Point where = visibleCenterOf(c);
    if (c instanceof JComponent) {
      where = scrollIfNecessary((JComponent) c);
    }
    click(c, where, button, times);
  }

  private @Nonnull Point scrollIfNecessary(@Nonnull JComponent c) {
    scrollToVisible(this, c);
    return visibleCenterOf(c);
  }

  @RunsInEDT
  @Override
  public void click(@Nonnull Component c, @Nonnull Point where) {
    click(c, where, LEFT_BUTTON, 1);
  }

  @RunsInEDT
  @Override
  public void click(@Nonnull Point where, @Nonnull MouseButton button, int times) {
    doClick(null, where, button, times);
  }

  @RunsInEDT
  @Override
  public void click(@Nonnull Component c, @Nonnull Point where, @Nonnull MouseButton button, int times) {
    doClick(c, where, button, times);
  }

  private void doClick(@Nullable Component c, @Nonnull Point where, @Nonnull MouseButton button, int times) {
    int mask = button.mask;
    int modifierMask = mask & ~BUTTON_MASK;
    mask &= BUTTON_MASK;
    final int finalMask = mask;
    pressModifiersWhileRunning(modifierMask, () -> doClickWhileModifiersPressed(c, where, times, finalMask));
    waitForIdle();
  }

  private void doClickWhileModifiersPressed(Component c, Point where, int times, int mask) {
    // From Abbot: Adjust the auto-delay to ensure we actually get a multiple click
    // In general clicks have to be less than 200ms apart, although the actual setting is not readable by Java.
    int delayBetweenEvents = settings.delayBetweenEvents();
    if (shouldSetDelayBetweenEventsToZeroWhenClicking(times)) {
      settings.delayBetweenEvents(0);
    }
    if (c == null) {
      eventGenerator.pressMouse(where, mask);
      for (int i = times; i > 1; i--) {
        eventGenerator.releaseMouse(mask);
        eventGenerator.pressMouse(mask);
      }
    } else {
      eventGenerator.pressMouse(c, where, mask);
      for (int i = times; i > 1; i--) {
        eventGenerator.releaseMouse(mask);
        eventGenerator.pressMouse(mask);
      }
    }
    settings.delayBetweenEvents(delayBetweenEvents);
    eventGenerator.releaseMouse(mask);
  }

  private boolean shouldSetDelayBetweenEventsToZeroWhenClicking(int times) {
    return times > 1 /* FEST-137: && settings.delayBetweenEvents() * 2 > 200 */;
  }

  @Override
  public void pressModifiers(int modifierMask) {
    for (int modifierKey : keysFor(modifierMask)) {
      pressKey(modifierKey);
    }
  }

  @Override
  public void pressModifiersWhileRunning(int modifierMask, Runnable runnable) {
    pressModifiers(modifierMask);
    try {
      runnable.run();
    } finally {
      releaseModifiers(modifierMask);
    }
  }

  @Override
  public void releaseModifiers(int modifierMask) {
    // For consistency, release in the reverse order of press.
    int[] modifierKeys = keysFor(modifierMask);
    for (int i = modifierKeys.length - 1; i >= 0; i--) {
      releaseKey(modifierKeys[i]);
    }
  }

  @RunsInEDT
  @Override
  public void moveMouse(@Nonnull Component c) {
    moveMouse(c, visibleCenterOf(c));
  }

  @RunsInEDT
  @Override
  public void moveMouse(@Nonnull Component c, @Nonnull Point p) {
    moveMouse(c, p.x, p.y);
  }

  @RunsInEDT
  @Override
  public void moveMouse(@Nonnull Component c, int x, int y) {
    if (!waitForComponentToBeReady(c, settings.timeoutToBeVisible())) {
      throw actionFailure(concat("Could not obtain position of component ", format(c)));
    }
    eventGenerator.moveMouse(c, x, y);
    waitForIdle();
  }

  @Override
  public void moveMouse(@Nonnull Point p) {
    moveMouse(p.x, p.y);
  }

  @Override
  public void moveMouse(int x, int y) {
    eventGenerator.moveMouse(x, y);
  }

  @Override
  public void pressMouse(@Nonnull MouseButton button) {
    eventGenerator.pressMouse(button.mask);
  }

  @Override
  public void pressMouseWhileRunning(@Nonnull MouseButton button, @Nonnull Runnable runnable) {
    pressMouse(button);
    try {
      runnable.run();
    } finally {
      releaseMouse(button);
    }
  }

  @Override
  public void pressMouse(@Nonnull Component c, @Nonnull Point where) {
    pressMouse(c, where, LEFT_BUTTON);
  }

  @Override
  public void pressMouseWhileRunning(@Nonnull Component c, @Nonnull Point where, @Nonnull Runnable runnable) {
    pressMouseWhileRunning(c, where, LEFT_BUTTON, runnable);
  }

  @Override
  public void pressMouse(@Nonnull Component c, @Nonnull Point where, @Nonnull MouseButton button) {
    jitter(c, where);
    moveMouse(c, where.x, where.y);
    eventGenerator.pressMouse(c, where, button.mask);
  }

  @Override
  public void pressMouseWhileRunning(@Nonnull Component c, @Nonnull Point where, @Nonnull MouseButton button,
                                     @Nonnull Runnable runnable) {
    pressMouse(c, where, button);
    try {
      runnable.run();
    } finally {
      releaseMouse(button);
    }
  }

  @Override
  public void pressMouse(@Nonnull Point where, @Nonnull MouseButton button) {
    eventGenerator.pressMouse(where, button.mask);
  }

  @Override
  public void pressMouseWhileRunning(@Nonnull Point where, @Nonnull MouseButton button, @Nonnull Runnable runnable) {
    pressMouse(where, button);
    try {
      runnable.run();
    } finally {
      releaseMouse(button);
    }
  }

  @RunsInEDT
  @Override
  public void releaseMouse(@Nonnull MouseButton button) {
    mouseRelease(button.mask);
  }

  @RunsInEDT
  @Override
  public void releaseMouseButtons() {
    int buttons = inputState.buttons();
    if (buttons == 0) {
      return;
    }
    mouseRelease(buttons);
  }

  @Override
  public void rotateMouseWheel(@Nonnull Component c, int amount) {
    moveMouse(c);
    rotateMouseWheel(amount);
  }

  @Override
  public void rotateMouseWheel(int amount) {
    eventGenerator.rotateMouseWheel(amount);
    waitForIdle();
  }

  @RunsInEDT
  @Override
  public void jitter(@Nonnull Component c) {
    jitter(c, visibleCenterOf(c));
  }

  @RunsInEDT
  @Override
  public void jitter(@Nonnull Component c, @Nonnull Point where) {
    int x = where.x;
    int y = where.y;
    moveMouse(c, (x > 0 ? x - 1 : x + 1), y);
  }

  /** Wait the given number of milliseconds for the component to be showing and ready. */
  @RunsInEDT
  private boolean waitForComponentToBeReady(@Nonnull Component c, long timeout) {
    if (isReadyForInput(c)) {
      return true;
    }
    TimeoutWatch watch = startWatchWithTimeoutOf(timeout);
    while (!isReadyForInput(c)) {
      if (c instanceof JPopupMenu) {
        // wiggle the mouse over the parent menu item to ensure the sub-menu shows
        Pair<Component, Point> invokerAndCenterOfInvoker = invokerAndCenterOfInvoker((JPopupMenu) c);
        Component invoker = invokerAndCenterOfInvoker.first;
        if (invoker instanceof JMenu) {
          jitter(invoker, invokerAndCenterOfInvoker.second);
        }
      }
      if (watch.isTimeOut()) {
        return false;
      }
      pause();
    }
    return true;
  }

  @RunsInEDT
  private static @Nonnull Pair<Component, Point> invokerAndCenterOfInvoker(final @Nonnull JPopupMenu popupMenu) {
    Pair<Component, Point> result = execute(new GuiQuery<Pair<Component, Point>>() {
      @Override
      protected Pair<Component, Point> executeInEDT() {
        Component invoker = checkNotNull(popupMenu.getInvoker());
        return Pair.of(invoker, centerOf(invoker));
      }
    });
    return checkNotNull(result);
  }

  @RunsInEDT
  @Override
  public void enterText(@Nonnull String text) {
    checkNotNull(text);
    if (text.isEmpty()) {
      return;
    }
    for (char character : text.toCharArray()) {
      type(character);
    }
    waitForIdle();
  }

  @RunsInEDT
  @Override
  public void type(char character) {
    KeyStroke keyStroke = keyStrokeFor(character);
    if (keyStroke == null) {
      Component focus = focusOwner();
      if (focus == null) {
        return;
      }
      KeyEvent keyEvent = keyEventFor(focus, character);
      // Allow any pending robot events to complete; otherwise we might stuff the typed event before previous
      // robot-generated events are posted.
      waitForIdle();
      eventPoster.postEvent(focus, keyEvent);
      return;
    }
    keyPressAndRelease(keyStroke.getKeyCode(), keyStroke.getModifiers());
  }

  private KeyEvent keyEventFor(Component c, char character) {
    return new KeyEvent(c, KEY_TYPED, currentTimeMillis(), 0, VK_UNDEFINED, character);
  }

  @RunsInEDT
  @Override
  public void pressAndReleaseKey(int keyCode, @Nonnull int... modifiers) {
    keyPressAndRelease(keyCode, unify(modifiers));
    waitForIdle();
  }

  @RunsInEDT
  @Override
  public void pressAndReleaseKeys(@Nonnull int... keyCodes) {
    for (int keyCode : keyCodes) {
      keyPressAndRelease(keyCode, 0);
      waitForIdle();
      pause(50); // it seems that even when waiting for idle the events are not completely propagated
    }
  }

  @RunsInEDT
  private void keyPressAndRelease(int keyCode, int modifiers) {
    int updatedModifiers = updateModifierWithKeyCode(keyCode, modifiers);
    if (updatedModifiers == modifiers) {
      pressModifiersWhileRunning(updatedModifiers, () -> {
        doPressKey(keyCode);
        eventGenerator.releaseKey(keyCode);
      });
    }
  }

  @RunsInEDT
  @Override
  public void pressKey(int keyCode) {
    doPressKey(keyCode);
    waitForIdle();
  }

  @Override
  public void pressKeyWhileRunning(int keyCode, Runnable runnable) {
    pressKey(keyCode);
    try {
      runnable.run();
    } finally {
      releaseKey(keyCode);
    }
  }

  @RunsInEDT
  private void doPressKey(int keyCode) {
    eventGenerator.pressKey(keyCode, CHAR_UNDEFINED);
  }

  @RunsInEDT
  @Override
  public void releaseKey(int keyCode) {
    eventGenerator.releaseKey(keyCode);
    waitForIdle();
  }

  @RunsInEDT
  private void mouseRelease(int buttons) {
    eventGenerator.releaseMouse(buttons);
  }

  @RunsInEDT
  @Override
  public void waitForIdle() {
    waitIfNecessary();
    if (settings.simpleWaitForIdle()) {
      simpleWaitForIdle();
    } else {
      Collection<EventQueue> queues = windowMonitor.allEventQueues();
      if (queues.size() == 1) {
        waitForIdle(checkNotNull(toolkit.getSystemEventQueue()));
        return;
      }
      // FIXME this resurrects dead event queues
      for (EventQueue queue : queues) {
        waitForIdle(checkNotNull(queue));
      }
    }
  }

  private void waitIfNecessary() {
    int delayBetweenEvents = settings.delayBetweenEvents();
    int eventPostingDelay = settings.eventPostingDelay();
    if (eventPostingDelay > delayBetweenEvents) {
      pause(eventPostingDelay - delayBetweenEvents);
    }
  }

  private void simpleWaitForIdle() {
    if (EventQueue.isDispatchThread()) {
      throw new IllegalThreadStateException("Cannot call method from the event dispatcher thread");
    }
    try {
      EventQueue.invokeAndWait(EMPTY_RUNNABLE);
    } catch (Exception e) {
      throw new UnexpectedException("could not invokeAndWait", e);
    }
  }

  private void waitForIdle(@Nonnull EventQueue eventQueue) {
    if (EventQueue.isDispatchThread()) {
      throw new IllegalThreadStateException("Cannot call method from the event dispatcher thread");
    }
    // Abbot: as of Java 1.3.1, robot.waitForIdle only waits for the last event on the queue at the time of this
    // invocation to be processed. We need better than that. Make sure the given event queue is empty when this method
    // returns.
    // We always post at least one idle event to allow any current event dispatch processing to finish.
    long start = currentTimeMillis();
    do {
      // Timed out waiting for idle
      int idleTimeout = settings.idleTimeout();
      if (postInvocationEvent(eventQueue, idleTimeout)) {
        break;
      }
      // Timed out waiting for idle event queue
      if (currentTimeMillis() - start > idleTimeout) {
        break;
      }
      // Force a yield
      pause();
      // Abbot: this does not detect invocation events (i.e. what gets posted with EventQueue.invokeLater), so if
      // someone is repeatedly posting one, we might get stuck. Not too worried, since if a Runnable keeps calling
      // invokeLater on itself, *nothing* else gets much chance to run, so it seems to be a bad programming practice.
    } while (eventQueue.peekEvent() != null);
  }

  /** Indicates whether we timed out waiting for the invocation to run. */
  @RunsInEDT
  private boolean postInvocationEvent(@Nonnull EventQueue eventQueue, long timeout) {
    Object lock = new RobotIdleLock();
    synchronized (lock) {
      InvocationEvent event = new InvocationEvent(toolkit, EMPTY_RUNNABLE, lock, true);
      eventQueue.postEvent(event);
      long start = currentTimeMillis();
      try {
        while (!event.isDispatched() && currentTimeMillis() - start < timeout) {
          lock.wait(timeout);
        }
        return (currentTimeMillis() - start) >= settings.idleTimeout();
      } catch (InterruptedException e) {
      }
      return false;
    }
  }

  private static class RobotIdleLock {
    RobotIdleLock() {
    }
  }

  @Override
  public boolean isDragging() {
    return inputState.dragInProgress();
  }

  @RunsInEDT
  @Override
  public @Nonnull JPopupMenu showPopupMenu(@Nonnull Component invoker) {
    return showPopupMenu(invoker, visibleCenterOf(invoker));
  }

  @RunsInEDT
  @Override
  public @Nonnull JPopupMenu showPopupMenu(@Nonnull Component invoker, @Nonnull Point location) {
    if (isFocusable(invoker)) {
      focusAndWaitForFocusGain(invoker);
    }
    click(invoker, location, RIGHT_BUTTON, 1);
    JPopupMenu popup = findActivePopupMenu();
    if (popup == null) {
      throw new ComponentLookupException(concat("Unable to show popup at ", location, " on ", inEdtFormat(invoker)));
    }
    long start = currentTimeMillis();
    while (!isWindowAncestorReadyForInput(popup) && currentTimeMillis() - start > POPUP_DELAY) {
      pause();
    }
    return popup;
  }

  @RunsInEDT
  private boolean isWindowAncestorReadyForInput(final JPopupMenu popup) {
    return checkNotNull(execute(() -> isReadyForInput(checkNotNull(getWindowAncestor(popup)))));
  }

  /**
   * <p>
   * Indicates whether the given AWT or Swing {@code Component} is ready for input.
   * </p>
   *
   * <p>
   * <b>Note:</b> This method is accessed in the current executing thread. Such thread may or may not be the event
   * dispatch thread (EDT). Client code must call this method from the EDT.
   * </p>
   *
   * @param c the given {@code Component}.
   * @return {@code true} if the given {@code Component} is ready for input, {@code false} otherwise.
   * @throws org.assertj.swing.exception.ActionFailedException if the given {@code Component} does not have a
   *           {@code Window} ancestor.
   */
  @Override
  @RunsInCurrentThread
  public boolean isReadyForInput(@Nonnull Component c) {
    Window w = windowAncestorOf(c);
    if (w == null) {
      throw actionFailure(concat("Component ", format(c), " does not have a Window ancestor"));
    }
    return c.isShowing() && windowMonitor.isWindowReady(w);
  }

  @RunsInEDT
  @Override
  public @Nullable JPopupMenu findActivePopupMenu() {
    JPopupMenu popup = activePopupMenu();
    if (popup != null || isEventDispatchThread()) {
      return popup;
    }
    TimeoutWatch watch = startWatchWithTimeoutOf(POPUP_TIMEOUT);
    while ((popup = activePopupMenu()) == null) {
      if (watch.isTimeOut()) {
        break;
      }
      pause(100);
    }
    return popup;
  }

  @RunsInEDT
  private @Nullable JPopupMenu activePopupMenu() {
    List<Component> found = newArrayList(finder().findAll(POPUP_MATCHER));
    if (found.size() >= 1) {
      return findOuterPopupMenu(found);
    }
    return null;
  }

  @RunsInEDT
  private JPopupMenu findOuterPopupMenu(List<Component> found) {
    if (found.size() == 1) {
      return (JPopupMenu) found.get(0);
    }
    List<JPopupMenu> innerMenus = newArrayList();
    for (Component component : found) {
      innerMenus.addAll(popupMenus(((JPopupMenu) component).getComponents()));
    }
    found.removeAll(innerMenus);
    if (found.size() == 1) {
      return (JPopupMenu) found.get(0);
    }
    throw multiplePopupMenusFound(found);
  }

  @RunsInEDT
  private static @Nonnull ComponentLookupException multiplePopupMenusFound(@Nonnull Collection<Component> found) {
    StringBuilder message = new StringBuilder();
    String format = "Found more than one popup menu.%n%nFound:";
    message.append(String.format(format));
    appendComponents(message, found);
    if (!found.isEmpty()) {
      message.append(lineSeparator());
    }
    throw new ComponentLookupException(message.toString(), found);
  }

  @RunsInEDT
  private static void appendComponents(final @Nonnull StringBuilder message, final @Nonnull Collection<Component> found) {
    execute(() -> {
      for (Component c : found) {
        message.append(String.format("%n%s", format(c)));
      }
    });
  }

  @RunsInEDT
  private Collection<? extends JPopupMenu> popupMenus(Component[] components) {
    Set<JPopupMenu> menus = newHashSet();
    for (Component component : components) {
      if (component instanceof JPopupMenu) {
        menus.add((JPopupMenu) component);
      }
    }
    return menus;
  }

  @RunsInEDT
  @Override
  public void requireNoJOptionPaneIsShowing() {
    unexpectedJOptionPaneFinder.requireNoJOptionPaneIsShowing();
  }

  @Override
  public @Nonnull Settings settings() {
    return settings;
  }

  @Override
  public @Nonnull ComponentHierarchy hierarchy() {
    return hierarchy;
  }

  @Override
  public synchronized boolean isActive() {
    return active;
  }

  @VisibleForTesting
  final @Nullable Object screenLockOwner() {
    return screenLockOwner;
  }
}
