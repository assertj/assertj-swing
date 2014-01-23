/*
 * Created on Jun 7, 2009
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
package org.fest.swing.fixture;

import javax.annotation.Nonnull;
import javax.swing.JFrame;

import org.fest.swing.core.Robot;
import org.fest.swing.driver.FrameDriver;

/**
 * Implementation of {@link AbstractContainerFixture} to be used for testing.
 *
 * @author Alex Ruiz
 */
public class ContainerFixture extends AbstractContainerFixture<ContainerFixture, JFrame, FrameDriver> {
  public ContainerFixture(Robot robot, JFrame target) {
    super(ContainerFixture.class, robot, target);
  }

  @Override
  protected @Nonnull FrameDriver createDriver(@Nonnull Robot robot) {
    return new FrameDriver(robot);
  }
}
