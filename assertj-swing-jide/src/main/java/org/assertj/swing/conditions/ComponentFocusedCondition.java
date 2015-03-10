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
package org.assertj.swing.conditions;

import org.assertj.swing.core.BasicComponentFinder;
import org.assertj.swing.core.ComponentFinder;
import org.assertj.swing.core.ComponentMatcher;
import org.assertj.swing.core.NameMatcher;
import org.assertj.swing.exception.ComponentLookupException;
import org.assertj.swing.timing.Condition;

import java.awt.*;

/**
 * A Condition that requires focus to be inside a component.
 * 
 * @author Peter Murray
 */
public class ComponentFocusedCondition extends Condition {

  private final ComponentFinder _finder;

  private final ComponentMatcher _matcher;

  /**
   * Creates a new <code>{@link ComponentVisibleCondition}</code>
   * 
   * @param name The name of the component to find in the GUI.
   */
  public ComponentFocusedCondition(String name) {
	this(BasicComponentFinder.finderWithCurrentAwtHierarchy(),
	     new NameMatcher(name, true));
  }

  /**
   * Creates a new <code>{@link ComponentVisibleCondition}</code>
   * 
   * @param name The name of the component to find in the GUI.
   * @param type The type of the component to find.
   */
  public ComponentFocusedCondition(String name, Class<? extends Component> type) {
	this(BasicComponentFinder.finderWithCurrentAwtHierarchy(),
	     new NameMatcher(name, type, true));
  }

  /**
   * Creates a new <code>{@link ComponentVisibleCondition}</code>
   * 
   * @param finder performs the component search.
   * @param matcher specifies the condition that the component we are looking for needs to
   *          match.
   */
  public ComponentFocusedCondition(ComponentFinder finder,
	                               ComponentMatcher matcher) {
	this("Component Focused Condition", finder, matcher);
  }

  /**
   * Creates a new <code>{@link ComponentVisibleCondition}</code>
   * 
   * @param description the description of this condition.
   * @param finder performs the component search.
   * @param matcher specifies the condition that the component we are looking for needs to
   *          match.
   */
  public ComponentFocusedCondition(String description,
	                               ComponentFinder finder,
	                               ComponentMatcher matcher) {
	super(description);
	_finder = finder;
	_matcher = matcher;
  }

  /**
   * Returns <code>true</code> if a component that matches the search criteria in this
   * condition's <code>{@link org.assertj.swing.core.ComponentMatcher}</code> can be found.
   * Otherwise, this method returns <code>false</code>.
   * 
   * @return <code>true</code> if a matching component can be found, <code>false</code> otherwise.
   */
  @Override
  public boolean test() {
	try {
	  Component found = _finder.find(_matcher);
	  return found.isFocusOwner();
	} catch (ComponentLookupException e) {
	  // Ignore the exception and return false
	}
	return false;
  }
}