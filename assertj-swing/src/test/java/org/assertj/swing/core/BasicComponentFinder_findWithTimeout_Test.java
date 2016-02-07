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

import org.assertj.swing.hierarchy.ExistingHierarchy;
import org.assertj.swing.timing.Pause;
import org.junit.Test;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link BasicComponentFinder#findAll(ComponentMatcher)}.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class BasicComponentFinder_findWithTimeout_Test extends BasicComponentFinder_TestCase {
    @Test
    public void should_try_to_find_until_timeout_passed() {

        Settings settings = new Settings();
        finder = new BasicComponentFinder(new ExistingHierarchy(), settings);


        Executors.newSingleThreadExecutor().execute(() -> {
            Pause.pause(250);
            EventQueue.invokeLater(() -> window.button.setText("A Changed Button"));
        });

        Component found = finder.find(new GenericTypeMatcher<JButton>(JButton.class) {
            @Override
            protected boolean isMatching(@Nonnull JButton component) {
                return "A Changed Button".equals(component.getText());
            }
        });


        assertThat(found).isEqualTo(window.button);
    }


}
