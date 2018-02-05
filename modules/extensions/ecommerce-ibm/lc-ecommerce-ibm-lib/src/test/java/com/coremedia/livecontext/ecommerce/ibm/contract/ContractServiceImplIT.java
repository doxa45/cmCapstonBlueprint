package com.coremedia.livecontext.ecommerce.ibm.contract;

import co.freeside.betamax.Betamax;
import co.freeside.betamax.MatchRule;
import com.coremedia.livecontext.ecommerce.common.CommerceId;
import com.coremedia.livecontext.ecommerce.common.Vendor;
import com.coremedia.livecontext.ecommerce.contract.Contract;
import com.coremedia.livecontext.ecommerce.ibm.IbmServiceTestBase;
import com.coremedia.livecontext.ecommerce.ibm.common.StoreContextHelper;
import com.coremedia.livecontext.ecommerce.ibm.user.UserContextHelper;
import com.coremedia.livecontext.ecommerce.user.UserContext;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import javax.inject.Inject;
import java.util.Collection;

import static com.coremedia.livecontext.ecommerce.ibm.common.WcsVersion.WCS_VERSION_7_7;
import static com.coremedia.livecontext.ecommerce.ibm.contract.ContractServiceImpl.toContractId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@ContextConfiguration(classes = IbmServiceTestBase.LocalConfig.class)
@ActiveProfiles(IbmServiceTestBase.LocalConfig.PROFILE)
public class ContractServiceImplIT extends IbmServiceTestBase {

  @Inject
  ContractServiceImpl testling;

  @Betamax(tape = "contract_testFindContractIdsForUser", match = {MatchRule.path, MatchRule.query})
  @Test
  public void testFindContractIdsForUser() throws Exception {
    StoreContextHelper.setCurrentContext(testConfig.getB2BStoreContext());

    UserContext userContext = UserContext.builder().withUserName(testConfig.getUser2Name()).build();
    UserContextHelper.setCurrentContext(userContext);

    Collection<Contract> contractIdsForUser = testling.findContractIdsForUser(UserContextHelper.getCurrentContext(), StoreContextHelper.getCurrentContext());
    assertNotNull(contractIdsForUser);

    if (WCS_VERSION_7_7.lessThan(StoreContextHelper.getWcsVersion(testConfig.getB2BStoreContext()))) {
      assertFalse("number of eligible contracts should be more than zero", contractIdsForUser.isEmpty());
    } else {
      assertTrue("number of eligible contracts should be zero", contractIdsForUser.isEmpty());
    }
  }

  @Betamax(tape = "contract_testFindContractIdsForUser", match = {MatchRule.path, MatchRule.query})
  @Test
  public void testFindContractIdsForPreviewUser() throws Exception {
    StoreContextHelper.setCurrentContext(testConfig.getB2BStoreContext());
    UserContext userContext = UserContext.builder().withUserName(testConfig.getPreviewUserName()).build();
    UserContextHelper.setCurrentContext(userContext);

    Collection<Contract> contracts = testling.findContractIdsForUser(UserContextHelper.getCurrentContext(), StoreContextHelper.getCurrentContext());
    assertNotNull(contracts);

    if (WCS_VERSION_7_7.lessThan(StoreContextHelper.getWcsVersion(testConfig.getB2BStoreContext()))) {
      assertEquals(3, contracts.size());

      for (Contract contract : contracts) {
        CommerceId contractId = contract.getId();
        assertEquals("contract id has wrong format: " + contract.getId(), Vendor.of("ibm"), contractId.getVendor());
        assertEquals("contract id has wrong format: " + contract.getId(), "contract", contractId.getCommerceBeanType().type());
        assertTrue("contract id has wrong format: " + contract.getId(), contractId.getExternalId().map(e -> e.startsWith("4000")).orElse(false));
      }
    }
  }

  @Betamax(tape = "contract_testFindContractIdsForServiceUserWithNoServiceUser", match = {MatchRule.path, MatchRule.query})
  @Test
  public void testFindContractIdsForServiceUserWithNoServiceUser() throws Exception {
    StoreContextHelper.setCurrentContext(testConfig.getB2BStoreContext());
    String contractPreviewServiceUserName = testling.getContractPreviewServiceUserName();
    try {
      testling.setContractPreviewServiceUserName(null);
      Collection<Contract> contracts = testling.findContractIdsForServiceUser(StoreContextHelper.getCurrentContext());
      assertNotNull(contracts);
      assertTrue(contracts.isEmpty());
    } finally {
      testling.setContractPreviewServiceUserName(contractPreviewServiceUserName);
    }
  }

  @Betamax(tape = "contract_testFindContractById", match = {MatchRule.path, MatchRule.query})
  @Test
  public void testFindContractById() throws Exception {
    StoreContextHelper.setCurrentContext(testConfig.getB2BStoreContext());
    UserContext userContext = UserContext.builder().withUserName(testConfig.getPreviewUserName()).build();
    UserContextHelper.setCurrentContext(userContext);

    Collection<Contract> contracts = testling.findContractIdsForUser(UserContextHelper.getCurrentContext(), StoreContextHelper.getCurrentContext());
    assertNotNull(contracts);

    if (WCS_VERSION_7_7.lessThan(StoreContextHelper.getWcsVersion(testConfig.getB2BStoreContext()))) {
      for (Contract contract : contracts) {
        CommerceId contractId = toContractId(contract.getExternalId());
        Contract contractById = testling.findContractById(contractId, testConfig.getB2BStoreContext());
        assertNotNull(contractById);
        assertEquals(contract.getExternalId(), contractById.getExternalId());
      }
    }
  }

  @Betamax(tape = "contract_testInvalidContract", match = {MatchRule.path, MatchRule.query})
  @Test
  public void testInvalidContract() throws Exception {
    StoreContextHelper.setCurrentContext(testConfig.getB2BStoreContext());
    CommerceId contractId = toContractId("xxxx");
    Contract testcontract = testling.findContractById(contractId, testConfig.getB2BStoreContext());
    assertNull(testcontract);
  }
}
