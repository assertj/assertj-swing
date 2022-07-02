package org.assertj.swing.timing;

import org.assertj.swing.exception.WaitTimedOutError;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class Pause_toString {

    @Test
    public void pause_toStringOnOneCondition() {
        try {
            Pause.pause(new NeverSatisfiedCondition(), 500);
            fail("Should have timed out");
        } catch(WaitTimedOutError e) {
            assertThat(e).hasMessage("Timed out waiting for Never satisfied");
        }
    }

    @Test
    public void pause_toStringOnManyConditions() {
        try {
            Pause.pause(new Condition[] {
                    new NeverSatisfiedCondition(),
                    new NeverSatisfiedCondition()
            }, 500);
            fail("Should have timed out");
        } catch(WaitTimedOutError e) {
            assertThat(e).hasMessage("Timed out waiting for [Never satisfied, Never satisfied]");
        }
    }

}
