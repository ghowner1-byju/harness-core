package io.harness.accesscontrol.principals.serviceaccounts;

import static io.harness.annotations.dev.HarnessTeam.PL;
import static io.harness.rule.OwnerRule.KARAN;

import static junit.framework.TestCase.assertEquals;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.harness.accesscontrol.AccessControlCoreTestBase;
import io.harness.accesscontrol.principals.serviceaccounts.persistence.ServiceAccountDao;
import io.harness.accesscontrol.roleassignments.RoleAssignmentService;
import io.harness.annotations.dev.OwnedBy;
import io.harness.category.element.UnitTests;
import io.harness.ng.beans.PageRequest;
import io.harness.ng.beans.PageResponse;
import io.harness.rule.Owner;

import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.transaction.support.TransactionTemplate;

@OwnedBy(PL)
public class ServiceAccountServiceImplTest extends AccessControlCoreTestBase {
  private ServiceAccountDao serviceAccountDao;
  private RoleAssignmentService roleAssignmentService;
  private TransactionTemplate transactionTemplate;
  private ServiceAccountService serviceAccountService;

  @Before
  public void setup() {
    serviceAccountDao = mock(ServiceAccountDao.class);
    roleAssignmentService = mock(RoleAssignmentService.class);
    transactionTemplate = mock(TransactionTemplate.class);
    serviceAccountService =
        spy(new ServiceAccountServiceImpl(serviceAccountDao, transactionTemplate, roleAssignmentService));
  }

  @Test
  @Owner(developers = KARAN)
  @Category(UnitTests.class)
  public void testCreateIfNotPresent() {
    ServiceAccount serviceAccount = ServiceAccount.builder().build();
    when(serviceAccountDao.createIfNotPresent(serviceAccount)).thenReturn(serviceAccount);
    assertEquals(serviceAccount, serviceAccountService.createIfNotPresent(serviceAccount));
    verify(serviceAccountDao, times(1)).createIfNotPresent(serviceAccount);
  }

  @Test
  @Owner(developers = KARAN)
  @Category(UnitTests.class)
  public void testList() {
    PageRequest pageRequest = PageRequest.builder().build();
    String scopeIdentifier = randomAlphabetic(10);
    PageResponse pageResponse = PageResponse.getEmptyPageResponse(pageRequest);
    when(serviceAccountDao.list(pageRequest, scopeIdentifier)).thenReturn(pageResponse);
    assertEquals(pageResponse, serviceAccountService.list(pageRequest, scopeIdentifier));
    verify(serviceAccountDao, times(1)).list(pageRequest, scopeIdentifier);
  }

  @Test
  @Owner(developers = KARAN)
  @Category(UnitTests.class)
  public void testGet() {
    String identifier = randomAlphabetic(10);
    String scopeIdentifier = randomAlphabetic(10);
    Optional<ServiceAccount> serviceAccountOptional = Optional.of(ServiceAccount.builder().build());
    when(serviceAccountDao.get(identifier, scopeIdentifier)).thenReturn(serviceAccountOptional);
    assertEquals(serviceAccountOptional, serviceAccountService.get(identifier, scopeIdentifier));
    verify(serviceAccountDao, times(1)).get(identifier, scopeIdentifier);
  }

  @Test
  @Owner(developers = KARAN)
  @Category(UnitTests.class)
  public void testDeleteIfPresent() {
    String identifier = randomAlphabetic(10);
    String scopeIdentifier = randomAlphabetic(10);
    Optional<ServiceAccount> serviceAccountOptional = Optional.of(ServiceAccount.builder().build());
    when(serviceAccountDao.get(identifier, scopeIdentifier)).thenReturn(serviceAccountOptional);
    when(transactionTemplate.execute(any())).thenReturn(1L);
    assertEquals(serviceAccountOptional, serviceAccountService.deleteIfPresent(identifier, scopeIdentifier));
    verify(serviceAccountDao, times(1)).get(identifier, scopeIdentifier);
    verify(transactionTemplate, times(1)).execute(any());
  }

  @Test
  @Owner(developers = KARAN)
  @Category(UnitTests.class)
  public void testDeleteIfPresentNotFound() {
    String identifier = randomAlphabetic(10);
    String scopeIdentifier = randomAlphabetic(10);
    Optional<ServiceAccount> serviceAccountOptional = Optional.empty();
    when(serviceAccountDao.get(identifier, scopeIdentifier)).thenReturn(serviceAccountOptional);
    assertEquals(serviceAccountOptional, serviceAccountService.deleteIfPresent(identifier, scopeIdentifier));
    verify(serviceAccountDao, times(1)).get(identifier, scopeIdentifier);
  }
}