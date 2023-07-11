package com.wpay.core.merchant.global.dto;

import javax.validation.*;
import java.util.Set;

public abstract class SelfValidating<T> implements BaseValidation {

  private final Validator validator;

  public SelfValidating() {
    try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
      validator = factory.getValidator();
    }
  }

  /**
   * Evaluates all Bean Validations on the attributes of this
   * instance.
   */
  @Override
  public void validateSelf() {
    Set<ConstraintViolation<T>> violations = validator.validate((T) this);
    if (Boolean.FALSE.equals(violations.isEmpty()))
      throw new ConstraintViolationException(violations);
  }
}
