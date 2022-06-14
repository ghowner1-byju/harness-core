/*
 * Copyright 2022 Harness Inc. All rights reserved.
 * Use of this source code is governed by the PolyForm Free Trial 1.0.0 license
 * that can be found in the licenses directory at the root of this repository, also available at
 * https://polyformproject.org/wp-content/uploads/2020/05/PolyForm-Free-Trial-1.0.0.txt.
 */

package io.harness.steps.servicenow;

import static io.harness.rule.OwnerRule.vivekveman;

import static org.assertj.core.api.Assertions.assertThat;

import io.harness.CategoryTest;
import io.harness.category.element.UnitTests;
import io.harness.pms.sdk.core.plan.creation.beans.PlanCreationContext;
import io.harness.pms.sdk.core.plan.creation.beans.PlanCreationResponse;
import io.harness.rule.Owner;
import io.harness.steps.StepSpecTypeConstants;
import io.harness.steps.servicenow.update.ServiceNowUpdateStepNode;
import io.harness.steps.servicenow.update.ServiceNowUpdateStepPlanCreator;

import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ServiceNowUpdateStepPlanCreatorTest extends CategoryTest {
  @Test
  @Owner(developers = vivekveman)
  @Category(UnitTests.class)
  public void testGetSupportedStepTypes() {
    ServiceNowUpdateStepPlanCreator serviceNowUpdateStepPlanCreator = new ServiceNowUpdateStepPlanCreator();
    ServiceNowUpdateStepNode serviceNowUpdateStepNode = new ServiceNowUpdateStepNode();
    Set<String> stepSpecTypeConstants = new HashSet<String>();
    stepSpecTypeConstants.add(StepSpecTypeConstants.SERVICENOW_UPDATE);
    assertThat(serviceNowUpdateStepPlanCreator.getSupportedStepTypes()).isEqualTo(stepSpecTypeConstants);
    assertThat(serviceNowUpdateStepPlanCreator.getFieldClass()).isEqualTo(ServiceNowUpdateStepNode.class);

    assertThat(serviceNowUpdateStepPlanCreator.createPlanForField(
                   PlanCreationContext.builder().build(), serviceNowUpdateStepNode))
        .isEqualTo(PlanCreationResponse.class);
  }
}
