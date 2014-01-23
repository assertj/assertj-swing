/*
 * Created on Dec 19, 2008
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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JDialogs.dialog;
import static org.fest.swing.test.builder.JTextFields.textField;

import java.awt.Component;

import javax.annotation.Nonnull;
import javax.swing.JDialog;

import org.fest.swing.test.core.EDTSafeTestCase;
import org.junit.Test;

/**
 * Test case for <a href="http://code.google.com/p/fest/issues/detail?id=257">Bug 257</a>.
 *
 * @author Juhos Csaba-Zsolt
 * @author Alex Ruiz
 */
public class Bug257_GenericMatcherMatchesAnyType_Test extends EDTSafeTestCase {
  @Test
  public void should_not_match_Components_other_than_specified() {
    Class<JDialog> typeToMatch = JDialog.class;
    GenericTypeMatcher<JDialog> matcher = new ExtendedGenericTypeMatcher<JDialog>(typeToMatch);
    assertThat(matcher.matches(textField().createNew())).isFalse();
    assertThat(matcher.matches(dialog().createNew())).isTrue();
  }

  private static class ExtendedGenericTypeMatcher<T extends Component> extends GenericTypeMatcher<T> {
    ExtendedGenericTypeMatcher(Class<T> supportedType) {
      super(supportedType);
    }

    @Override
    protected boolean isMatching(final @Nonnull T component) {
      return true;
    }
  }
}
