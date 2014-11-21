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

import org.fest.swing.core.*;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.timing.Condition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

/**
 * A Condition that requires a component to be visible.
 * @author Peter Murray
 */
public class ComponentVisibleCondition extends Condition {

  private final ComponentFinder _finder;

  private final ComponentMatcher _matcher;

  private Component _found;

  /**
   * Creates a new <code>{@link ComponentVisibleCondition}</code>
   *
   * @param name The name of the component to find.
   * @param type The type of the component to find.
   */
  public ComponentVisibleCondition(String name, Class<? extends Component> type) {
    this(BasicComponentFinder.finderWithCurrentAwtHierarchy(),
         new NameMatcher(name, type, true));
  }

  /**
   * Creates a new <code>{@link ComponentVisibleCondition}</code>
   *
   * @param name The name of the component to find.
   */
  public ComponentVisibleCondition(String name) {
    this(BasicComponentFinder.finderWithCurrentAwtHierarchy(),
         new NameMatcher(name, true));
  }

  /**
   * Creates a new <code>{@link ComponentVisibleCondition}</code>
   * @param finder performs the component search.
   * @param matcher specifies the condition that the component we are looking for needs to
   * match.
   */
  public ComponentVisibleCondition(ComponentFinder finder,
                                   ComponentMatcher matcher) {
    this("Component Visible Condition", finder, matcher);
  }

  /**
   * Creates a new <code>{@link ComponentVisibleCondition}</code>
   * @param description the description of this condition.
   * @param finder performs the component search.
   * @param matcher specifies the condition that the component we are looking for needs to
   * match.
   */
  public ComponentVisibleCondition(String description,
                                   ComponentFinder finder,
                                   ComponentMatcher matcher) {
    super(description);
    _finder = finder;
    _matcher = matcher;
  }

  /**
   * Returns <code>true</code> if a component that matches the search criteria in this
   * condition's <code>{@link org.fest.swing.core.ComponentMatcher}</code> can be found. Otherwise, this
   * method returns <code>false</code>.
   * @return <code>true</code> if a matching component can be found, <code>false</code>
   *         otherwise.
   */
  @Override
  public boolean test() {
    try {
      _found = _finder.find(_matcher);
      return _found.isVisible();
    }
    catch (ComponentLookupException e) {
    }
    return false;
  }

  public Component found() {
    return _found;
  }
}
