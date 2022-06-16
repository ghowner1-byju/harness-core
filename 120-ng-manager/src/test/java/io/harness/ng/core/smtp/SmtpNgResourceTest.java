/*
 * Copyright 2022 Harness Inc. All rights reserved.
 * Use of this source code is governed by the PolyForm Free Trial 1.0.0 license
 * that can be found in the licenses directory at the root of this repository, also available at
 * https://polyformproject.org/wp-content/uploads/2020/05/PolyForm-Free-Trial-1.0.0.txt.
 */

package io.harness.ng.core.smtp;

import static io.harness.annotations.dev.HarnessTeam.PL;
import static io.harness.connector.accesscontrol.SMTPAccessControlPermissions.DELETE_SMTP_PERMISSION;
import static io.harness.connector.accesscontrol.SMTPAccessControlPermissions.EDIT_SMTP_PERMISSION;
import static io.harness.connector.accesscontrol.SMTPAccessControlPermissions.VIEW_SMTP_PERMISSION;
import static io.harness.rule.OwnerRule.MANKRIT;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

import io.harness.CategoryTest;
import io.harness.accesscontrol.acl.api.Resource;
import io.harness.accesscontrol.acl.api.ResourceScope;
import io.harness.accesscontrol.clients.AbstractAccessControlClient;
import io.harness.accesscontrol.clients.AccessControlClient;
import io.harness.annotations.dev.OwnedBy;
import io.harness.category.element.UnitTests;
import io.harness.connector.accesscontrol.ResourceTypes;
import io.harness.rule.Owner;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@OwnedBy(PL)
public class SmtpNgResourceTest extends CategoryTest {
  private SmtpNgService smtpNgService;
  private AccessControlClient accessControlClient;

  @Before
  public void setup() {
    smtpNgService = mock(SmtpNgServiceImpl.class);
    accessControlClient = mock(AbstractAccessControlClient.class);
  }

  @Test
  @Owner(developers = MANKRIT)
  @Category(UnitTests.class)
  public void testIfAccessSuccessfully() {
    doNothing()
        .when(accessControlClient)
        .checkForAccessOrThrow(
            ResourceScope.of(any(), null, null), Resource.of(ResourceTypes.SMTP, null), EDIT_SMTP_PERMISSION, any());
    doNothing()
        .when(accessControlClient)
        .checkForAccessOrThrow(
            ResourceScope.of(any(), null, null), Resource.of(ResourceTypes.SMTP, null), DELETE_SMTP_PERMISSION, any());
    doNothing()
        .when(accessControlClient)
        .checkForAccessOrThrow(
            ResourceScope.of(any(), null, null), Resource.of(ResourceTypes.SMTP, null), VIEW_SMTP_PERMISSION, any());
    assertThat(smtpNgService).isNotNull();
  }
}
