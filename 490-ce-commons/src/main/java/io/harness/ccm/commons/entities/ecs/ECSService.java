package io.harness.ccm.commons.entities.ecs;

import static io.harness.annotations.dev.HarnessTeam.CE;

import io.harness.annotation.StoreIn;
import io.harness.annotations.dev.OwnedBy;
import io.harness.ccm.commons.beans.Resource;
import io.harness.mongo.index.CompoundMongoIndex;
import io.harness.mongo.index.MongoIndex;
import io.harness.ng.DbAliases;
import io.harness.persistence.AccountAccess;
import io.harness.persistence.CreatedAtAware;
import io.harness.persistence.PersistentEntity;
import io.harness.persistence.UpdatedAtAware;
import io.harness.persistence.UuidAware;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@StoreIn(DbAliases.CENG)
@Entity(value = "ecsService", noClassnameStored = true)
@FieldNameConstants(innerTypeName = "ECSServiceKeys")
@OwnedBy(CE)
public final class ECSService implements PersistentEntity, UuidAware, CreatedAtAware, UpdatedAtAware, AccountAccess {
  public static List<MongoIndex> mongoIndexes() {
    return ImmutableList.<MongoIndex>builder()
        .add(CompoundMongoIndex.builder()
                 .name("unique_accountId_clusterId_serviceArn")
                 .unique(true)
                 .field(ECSServiceKeys.accountId)
                 .field(ECSServiceKeys.clusterId)
                 .field(ECSServiceKeys.serviceArn)
                 .build())
        .build();
  }
  @Id String uuid;
  String accountId;
  String clusterId;
  String serviceArn;
  String serviceName;
  Resource resource;
  Map<String, String> labels;
  long createdAt;
  long lastUpdatedAt;
}