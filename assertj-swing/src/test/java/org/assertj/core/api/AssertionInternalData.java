package org.assertj.core.api;

public class AssertionInternalData {
  public static <S extends AbstractAssert<S, A>, A> A getActual(AbstractAssert<S, A> assertion) {
    return assertion.actual;
  }
}
