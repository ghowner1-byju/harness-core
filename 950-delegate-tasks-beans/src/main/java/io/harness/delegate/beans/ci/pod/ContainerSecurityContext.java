/*
 * Copyright 2022 Harness Inc. All rights reserved.
 * Use of this source code is governed by the PolyForm Free Trial 1.0.0 license
 * that can be found in the licenses directory at the root of this repository, also available at
 * https://polyformproject.org/wp-content/uploads/2020/05/PolyForm-Free-Trial-1.0.0.txt.
 */

package io.harness.delegate.beans.ci.pod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ContainerSecurityContext {
  Boolean allowPrivilegeEscalation;
  Boolean privileged;
  String procMount;
  Boolean readOnlyRootFilesystem;
  Boolean runAsNonRoot;
  Integer runAsGroup;
  Integer runAsUser;
  ContainerCapabilities capabilities;
}