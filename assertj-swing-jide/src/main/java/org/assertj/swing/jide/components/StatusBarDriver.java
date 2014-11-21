/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * Copyright @2008-2010 the original author or authors.
 */

package org.fest.swing.jide.components;

import java.awt.*;
import com.jidesoft.status.LabelStatusBarItem;
import com.jidesoft.status.StatusBar;
import static org.fest.assertions.Assertions.assertThat;
import org.fest.swing.core.Robot;
import org.fest.swing.driver.JComponentDriver;

/**
 * A Driver for the Jide {@link StatusBar} class.
 * @author Peter Murray
 */
public class StatusBarDriver extends JComponentDriver {

  public StatusBarDriver(Robot robot) {
    super(robot);
  }

  public void requireText(StatusBar bar, String text) {
    LabelStatusBarItem labelItem = getLabelItem(bar);
    assertThat(labelItem).isNotNull();
    assertThat(labelItem.getText()).isSameAs(text);
  }

  public void containsText(StatusBar bar, String text) {
    LabelStatusBarItem item = getLabelItem(bar);
    assertThat(item).isNotNull();
    assertThat(item.getText()).contains(text);
  }

  private static LabelStatusBarItem getLabelItem(StatusBar bar) {
    for (Component c : bar.getComponents()) {
      if (c instanceof LabelStatusBarItem) {
        return (LabelStatusBarItem)c;
      }
    }
    return null;
  }
}