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

package org.fest.swing.conditions;

import org.fest.swing.core.BasicComponentFinder;
import org.fest.swing.core.ComponentFinder;
import org.fest.swing.core.ComponentMatcher;
import org.fest.swing.core.NameMatcher;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.timing.Condition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

/**
 * A Condition that requires the focus to be inside the component.
 * @author Peter Murray
 */
public class FocusInContainerCondition extends Condition {

  private static final Logger LOGGER = LoggerFactory.getLogger("fest.conditions");

  private final ComponentFinder _finder;

  private final ComponentMatcher _matcher;

  /**
   * Creates a new <code>{@link org.fest.swing.conditions.FocusInContainerCondition}</code>
   *
   * @param name The name of the component to find.
   * @param type The type of the component to find.
   */
  public FocusInContainerCondition(String name, Class<? extends Component> type) {
    this(BasicComponentFinder.finderWithCurrentAwtHierarchy(),
         new NameMatcher(name, type, true));
  }

  /**
   * Creates a new <code>{@link org.fest.swing.conditions.FocusInContainerCondition}</code>
   *
   * @param name The name of the component to find.
   */
  public FocusInContainerCondition(String name) {
    this(BasicComponentFinder.finderWithCurrentAwtHierarchy(),
         new NameMatcher(name, true));
  }

  /**
   * Creates a new <code>{@link org.fest.swing.conditions.FocusInContainerCondition}</code>
   * @param finder performs the component search.
   * @param matcher specifies the condition that the component we are looking for needs to
   * match.
   */
  public FocusInContainerCondition(ComponentFinder finder,
                                   ComponentMatcher matcher) {
    this("Container contains component with focus", finder, matcher);
  }

  /**
   * Creates a new <code>{@link org.fest.swing.conditions.FocusInContainerCondition}</code>
   * @param description the description of this condition.
   * @param finder performs the component search.
   * @param matcher specifies the condition that the component we are looking for needs to
   * match.
   */
  public FocusInContainerCondition(String description,
                                   ComponentFinder finder,
                                   ComponentMatcher matcher) {
    super(description);
    _finder = finder;
    _matcher = matcher;
  }

  /**
   * Returns <code>true</code> if the focus is in either the container that we are
   * matching on, or one of its children contained inside it.
   * @return <code>true</code> if the focus is in the component or contained within it,
   *         <code>false</code> otherwise.
   */
  @Override
  public boolean test() {
    try {
      Component found = _finder.find(_matcher);
      debug("testing focus inside component :: {}", found);

      Component focusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager()
              .getFocusOwner();
      debug("\tActual focus owner: {}", focusOwner);

      if (found == focusOwner) {
        debug("Focus is on the actual component :: returning true");
        return true;
      } else {
        if (found instanceof Container) {
          Container container = (Container)found;
          boolean containsFocus = container.isAncestorOf(focusOwner);
          debug("Component is a Container, does it contain the focus? {}", containsFocus);
          return containsFocus;
        }
      }
    }
    catch (ComponentLookupException e) {
      debug("Component was not found {}", e);
    }
    debug("Focus is not within the container being tested");
    return false;
  }

  private void debug(String message, Object... args) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(message, args);
    }
  }
}