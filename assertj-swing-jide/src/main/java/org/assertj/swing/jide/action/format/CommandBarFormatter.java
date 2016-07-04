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
package org.assertj.swing.jide.action.format;

import java.awt.Component;

import org.assertj.core.util.Strings;
import org.assertj.swing.format.ComponentFormatterTemplate;

import com.jidesoft.action.CommandBar;

/**
 * A formatter for {@link CommandBar} implementations.
 *
 * @author Peter Murray
 */
public class CommandBarFormatter extends ComponentFormatterTemplate {

  @Override
  protected String doFormat(Component c) {
    return Strings.concat(getRealClassName(c),
                          " [name=", Strings.quote(c.getName()),
                          ", enabled=", String.valueOf(c.isEnabled()),
                          ", visible=", String.valueOf(c.isVisible()),
                          ", showing=", String.valueOf(c.isShowing()),
                          "]");
  }

  @Override
  public Class<? extends Component> targetType() {
    return CommandBar.class;
  }
}
