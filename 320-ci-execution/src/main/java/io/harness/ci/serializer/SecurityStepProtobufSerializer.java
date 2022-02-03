/*
 * Copyright 2021 Harness Inc. All rights reserved.
 * Use of this source code is governed by the PolyForm Free Trial 1.0.0 license
 * that can be found in the licenses directory at the root of this repository, also available at
 * https://polyformproject.org/wp-content/uploads/2020/05/PolyForm-Free-Trial-1.0.0.txt.
 */

package io.harness.ci.serializer;

import static io.harness.beans.serializer.RunTimeInputHandler.resolveJsonNodeMapParameter;
import static io.harness.common.CIExecutionConstants.HARNESS_STEP_ID_VARIABLE;
import static io.harness.common.CIExecutionConstants.SECURITY_ENV_PREFIX;
import static io.harness.data.structure.EmptyPredicate.isEmpty;
import static io.harness.data.structure.EmptyPredicate.isNotEmpty;

import static java.util.Collections.emptyList;

import io.harness.annotations.dev.HarnessTeam;
import io.harness.annotations.dev.OwnedBy;
import io.harness.beans.serializer.RunTimeInputHandler;
import io.harness.beans.steps.stepinfo.SecurityStepInfo;
import io.harness.beans.yaml.extended.reports.JUnitTestReport;
import io.harness.beans.yaml.extended.reports.UnitTestReport;
import io.harness.beans.yaml.extended.reports.UnitTestReportType;
import io.harness.callback.DelegateCallbackToken;
import io.harness.exception.ngexception.CIStageExecutionException;
import io.harness.pms.yaml.ParameterField;
import io.harness.product.ci.engine.proto.PluginStep;
import io.harness.product.ci.engine.proto.Report;
import io.harness.product.ci.engine.proto.StepContext;
import io.harness.product.ci.engine.proto.UnitStep;
import io.harness.utils.TimeoutUtils;
import io.harness.yaml.core.timeout.Timeout;
import io.harness.yaml.core.variables.OutputNGVariable;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Singleton
@OwnedBy(HarnessTeam.STO)
public class SecurityStepProtobufSerializer implements ProtobufStepSerializer<SecurityStepInfo> {
  @Inject private Supplier<DelegateCallbackToken> delegateCallbackTokenSupplier;

  public UnitStep serializeStepWithStepParameters(SecurityStepInfo securityStepInfo, Integer port, String callbackId,
      String logKey, String identifier, ParameterField<Timeout> parameterFieldTimeout, String accountId,
      String stepName) {
    if (callbackId == null) {
      throw new CIStageExecutionException("CallbackId can not be null");
    }

    if (port == null) {
      throw new CIStageExecutionException("Port can not be null");
    }

    long timeout = TimeoutUtils.getTimeoutInSeconds(parameterFieldTimeout, securityStepInfo.getDefaultTimeout());
    StepContext stepContext = StepContext.newBuilder().setExecutionTimeoutSecs(timeout).build();

    Map<String, JsonNode> settings =
        resolveJsonNodeMapParameter("settings", "Security", identifier, securityStepInfo.getSettings(), false);
    Map<String, String> envVarMap = new HashMap<>();
    if (!isEmpty(settings)) {
      for (Map.Entry<String, JsonNode> entry : settings.entrySet()) {
        String key = SECURITY_ENV_PREFIX + entry.getKey().toUpperCase();
        envVarMap.put(key, SerializerUtils.convertJsonNodeToString(entry.getKey(), entry.getValue()));
      }
    }

    envVarMap.put(HARNESS_STEP_ID_VARIABLE, identifier);

    PluginStep.Builder builder = PluginStep.newBuilder();

    UnitTestReport reports = securityStepInfo.getReports();
    if (reports != null) {
      if (reports.getType() == UnitTestReportType.JUNIT) {
        JUnitTestReport junitTestReport = (JUnitTestReport) reports.getSpec();
        List<String> resolvedReport = junitTestReport.resolve(identifier, "run");

        Report report = Report.newBuilder().setType(Report.Type.JUNIT).addAllPaths(resolvedReport).build();
        builder.addReports(report);
      }
    }

    if (isNotEmpty(securityStepInfo.getOutputVariables())) {
      List<String> outputVarNames =
          securityStepInfo.getOutputVariables().stream().map(OutputNGVariable::getName).collect(Collectors.toList());
      builder.addAllEnvVarOutputs(outputVarNames);
    }

    PluginStep pluginStep =
        builder.setContainerPort(port)
            .setImage(RunTimeInputHandler.resolveStringParameter(
                "Image", "Security", identifier, securityStepInfo.getImage(), true))
            .addAllEntrypoint(Optional.ofNullable(securityStepInfo.getEntrypoint()).orElse(emptyList()))
            .putAllEnvironment(envVarMap)
            .setContext(stepContext)
            .build();

    return UnitStep.newBuilder()
        .setId(identifier)
        .setTaskId(callbackId)
        .setAccountId(accountId)
        .setContainerPort(port)
        .setCallbackToken(delegateCallbackTokenSupplier.get().getToken())
        .setDisplayName(stepName)
        .setPlugin(pluginStep)
        .setLogKey(logKey)
        .build();
  }
}