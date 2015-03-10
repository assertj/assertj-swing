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
package org.assertj.swing.fixture;

import static org.assertj.core.util.Preconditions.checkNotNull;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import java.awt.Container;

import javax.annotation.Nonnull;
import javax.swing.JFrame;

import org.assertj.swing.annotation.RunsInEDT;
import org.assertj.swing.core.Robot;
import org.assertj.swing.edt.GuiQuery;

/**
 * Utility methods related to {@code Container}s.
 * 
 * @author Alex Ruiz
 */
public final class Containers {
  /** Name of the {@code JFrame}s created by this class. */
  public static final String CREATED_FRAME_NAME = "org.assertj.swing.CreatedFrameForContainer";

  /**
   * Creates a new {@code JFrame} and uses the given {@code Container} as its content pane. The created {@code JFrame}
   * is wrapped and displayed by a {@link FrameFixture}.
   * <p>
   * <strong>Note:</strong>This method creates a new {@link Robot}. When using this method, please do not create any
   * additional instances of {@code Robot}. Only one instance of {@code Robot} can exist per test class. If you've
   * created your own {@code Robot} e.g. by extending the base test class, use {@code #showInFrame(Robot, Container)}.
   * </p>
   * 
   * @param contentPane the {@code Container} to use as content pane for the {@code JFrame} to create.
   * @return the created {@code FrameFixture}.
   * @see #frameFor(Container)
   */
  @RunsInEDT
  public static @Nonnull
  FrameFixture showInFrame(@Nonnull Container contentPane) {
	FrameFixture frameFixture = frameFixtureFor(contentPane);
	frameFixture.show();
	return frameFixture;
  }

  /**
   * Creates a new {@code JFrame} and uses the given {@code Container} as its content pane. The created {@code JFrame}
   * is wrapped and displayed by a {@link FrameFixture}. It uses the given {@code Robot}, if you've created your own
   * {@code Robot} this method is best for you, if not use {@code #showInFrame(Container)}.
   * 
   * @param robot performs user events on the given window and verifies expected output.
   * @param contentPane the {@code Container} to use as content pane for the {@code JFrame} to create.
   * @return the created {@code FrameFixture}.
   * @see #frameFor(Container)
   */
  @RunsInEDT
  public static @Nonnull
  FrameFixture showInFrame(@Nonnull Robot robot, @Nonnull Container contentPane) {
	FrameFixture frameFixture = frameFixtureFor(robot, contentPane);
	frameFixture.show();
	return frameFixture;
  }

  /**
   * Creates a new {@code JFrame} and uses the given {@code Container} as its content pane. The created {@code JFrame}
   * is wrapped by a {@link FrameFixture}. Unlike {@link #showInFrame(Container)}, this method does <strong>not</strong>
   * display the created {@code JFrame}.
   * <p>
   * <strong>Note:</strong>This method creates a new {@link Robot}. When using this method, please do not create any
   * additional instances of {@code Robot}. Only one instance of {@code Robot} can exist per test class. If you've
   * created your own {@code Robot} e.g. by extending the base test class, use
   * {@code #frameFixtureFor(Robot, Container)}.
   * </p>
   * 
   * @param contentPane the {@code Container} to use as content pane for the {@code JFrame} to create.
   * @return the created {@code FrameFixture}.
   * @see #frameFor(Container)
   */
  @RunsInEDT
  public static @Nonnull
  FrameFixture frameFixtureFor(@Nonnull Container contentPane) {
	return new FrameFixture(frameFor(contentPane));
  }

  /**
   * Creates a new {@code JFrame} and uses the given {@code Container} as its content pane. The created {@code JFrame}
   * is wrapped by a {@link FrameFixture}. Unlike {@link #showInFrame(Container)}, this method does <strong>not</strong>
   * display the created {@code JFrame}. It uses the given {@code Robot}, if you've created your own {@code Robot} this
   * method is best for you, if not use {@code #frameFixtureFor(Container)}.
   * 
   * @param robot performs user events on the given window and verifies expected output.
   * @param contentPane the {@code Container} to use as content pane for the {@code JFrame} to create.
   * @return the created {@code FrameFixture}.
   * @see #frameFor(Container)
   */
  @RunsInEDT
  public static @Nonnull
  FrameFixture frameFixtureFor(@Nonnull Robot robot, @Nonnull Container contentPane) {
	return new FrameFixture(robot, frameFor(contentPane));
  }

  /**
   * Creates a new {@code JFrame} and uses the given {@code Container} as its content pane. The created {@code JFrame}
   * has the name specified by {@link #CREATED_FRAME_NAME}. This method is executed in the Event Dispatch Thread (EDT.)
   * 
   * @param contentPane the {@code Container} to use as content pane for the {@code JFrame} to create.
   * @return the created {@code JFrame}.
   */
  @RunsInEDT
  public static @Nonnull
  JFrame frameFor(final @Nonnull Container contentPane) {
	JFrame result = execute(new GuiQuery<JFrame>() {
	  @Override
	  protected JFrame executeInEDT() throws Throwable {
		JFrame frame = new JFrame("Created by FEST");
		frame.setName(CREATED_FRAME_NAME);
		frame.setContentPane(contentPane);
		return frame;
	  }
	});
	return checkNotNull(result);
  }

  private Containers() {
  }
}
