package com.github.blog.shared.validator.constraints;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.validation.ConstraintValidatorContext;

public class LengthValidatorTest {

  @DataProvider
  public Object[][] samplesInitialize() {
    return new Object[][] {
        {-1, 0, "The min parameter cannot be negative."},
        {0, -1, "The max parameter cannot be negative."},
        {1, 0, "The max parameter cannot be less than min parameter."},
        {0, Integer.MAX_VALUE, null},
        {1, 2, null},
        {0, 0, null}
    };
  }

  @Test(dataProvider = "samplesInitialize")
  public void testInitialize(int min, int max, String error) {
    Length length = createLength(min, max);
    LengthValidator validator = new LengthValidator();

    try {
      validator.initialize(length);

      Assert.assertNull(error);
    } catch (IllegalArgumentException e) {
      Assert.assertEquals(e.getMessage(), error);
    }
  }

  @DataProvider
  public Object[][] samplesIsValid() {
    return new Object[][] {
        {0, Integer.MAX_VALUE, null, null},
        {0, Integer.MAX_VALUE, "", null},
        {0, Integer.MAX_VALUE, "xx", null},

        {1, Integer.MAX_VALUE, null, "required"},
        {1, Integer.MAX_VALUE, "", "required"},
        {4, Integer.MAX_VALUE, "", "required"},

        {4, 4, null, "required"},
        {4, 4, "", "required"},
        {4, 4, "x", "length.exact"},
        {4, 4, "xx", "length.exact"},
        {4, 4, "test", null},

        {4, Integer.MAX_VALUE, "x", "length.min"},
        {4, Integer.MAX_VALUE, "hi", "length.min"},
        {4, Integer.MAX_VALUE, "hey", "length.min"},
        {4, Integer.MAX_VALUE, "test", null},

        {3, 4, null, "required"},
        {3, 4, "", "required"},
        {3, 4, "x", "length.range"},
        {3, 4, "xx", "length.range"},
        {3, 4, "xxxxx", "length.range"},
        {3, 4, "hey", null},
        {3, 4, "test", null},

        {0, 4, null, null},
        {0, 4, "", null},
        {0, 4, "x", null},
        {0, 4, "xx", null},
        {0, 4, "hey", null},
        {0, 4, "test", null},
        {0, 4, "xxxxx", "length.max"},
        {0, 4, "xxxxxx", "length.max"}
    };
  }

  @Test(dataProvider = "samplesIsValid")
  public void testIsValid(int min, int max, String value, String error) {
    ConstraintValidatorContext.ConstraintViolationBuilder builder = Mockito
        .mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
    ConstraintValidatorContext context = Mockito
        .mock(ConstraintValidatorContext.class);
    ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
    Mockito
        .when(context.buildConstraintViolationWithTemplate(captor.capture()))
        .thenReturn(builder);
    Mockito
        .when(builder.addConstraintViolation())
        .thenReturn(context);
    LengthValidator validator = new LengthValidator();
    Length length = createLength(min, max);
    validator.initialize(length);

    boolean ok = validator.isValid(value, context);

    if (ok) {
      Assert.assertNull(error);
    } else {
      Assert.assertEquals(
          captor.getValue(),
          String.format(
              "{com.github.blog.shared.validator.constraints.%s}", error));
    }
  }

  private static Length createLength(int min, int max) {
    Map<String, Object> values = new HashMap<>();
    for (Method method : Length.class.getDeclaredMethods()) {
      values.put(method.getName(), method.getDefaultValue());
    }

    Length length = Mockito.mock(Length.class);

    Mockito.when(length.min()).thenReturn(min);
    Mockito.when(length.max()).thenReturn(max);
    Mockito.when(length.message())
        .thenReturn((String) values.get("message"));
    Mockito.when(length.messageRequired())
        .thenReturn((String) values.get("messageRequired"));
    Mockito.when(length.messageExact())
        .thenReturn((String) values.get("messageExact"));
    Mockito.when(length.messageMaxLength())
        .thenReturn((String) values.get("messageMaxLength"));
    Mockito.when(length.messageMinLength())
        .thenReturn((String) values.get("messageMinLength"));

    return length;
  }
}