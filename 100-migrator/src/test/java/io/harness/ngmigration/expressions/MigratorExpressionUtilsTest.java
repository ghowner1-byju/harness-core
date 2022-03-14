package io.harness.ngmigration.expressions;

import static io.harness.rule.OwnerRule.VAIBHAV_SI;

import static org.assertj.core.api.Assertions.assertThat;

import io.harness.CategoryTest;
import io.harness.category.element.UnitTests;
import io.harness.expression.ExpressionEvaluator;
import io.harness.rule.Owner;

import org.joor.Reflect;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class MigratorExpressionUtilsTest extends CategoryTest {
  @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
  ExpressionEvaluator expressionEvaluator = new ExpressionEvaluator();
  MigratorExpressionUtils migratorExpressionUtils = new MigratorExpressionUtils();
  MigratorResolveFunctor migratorResolveFunctor =
      new MigratorResolveFunctor(migratorExpressionUtils.prepareContextMap());

  @Test
  @Owner(developers = VAIBHAV_SI)
  @Category(UnitTests.class)
  public void testRender() {
    Reflect.on(migratorResolveFunctor).set("expressionEvaluator", expressionEvaluator);
    String input = "echo ${workflow.variables.var1} ${pipeline.variables.var1} ${infra.kubernetes.namespace}";
    String output = migratorResolveFunctor.processString(input);

    assertThat(output).isEqualTo("echo <+stage.variables.var1> <+pipeline.variables.var1> <+infra.namespace>");
  }
}