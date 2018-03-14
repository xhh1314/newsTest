package com.hww.sns.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

/**
 * hibernate-validator校验工具类
 *
 * 参考文档：http://docs.jboss.org/hibernate/validator/5.4/reference/en-US/html_single/
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-15 10:50
 */
public class ValidatorUtils {
  private static Validator validator;

  static {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
  }

  /**
   * 校验对象
   * 
   * @param object 待校验对象
   * @param groups 待校验的组
   * @throws RRException 校验不通过，则报RRException异常
   */
  public static Map<String, String> validateEntity(Object object, Class<?>... groups) {

    Map<String, String> map = new HashMap<>();
    map.put("status", "200");

    Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
    if (!constraintViolations.isEmpty()) {
      ConstraintViolation<Object> constraint =
          (ConstraintViolation<Object>) constraintViolations.iterator().next();
      map.put("status", "500");
      map.put("message", constraint.getMessage());
    }
    return map;
  }
}
