package org.apache.shindig.gadgets.oauth2.persistence.sample;

import java.util.Set;

import org.apache.shindig.gadgets.oauth2.MockUtils;
import org.apache.shindig.gadgets.oauth2.OAuth2Accessor;
import org.apache.shindig.gadgets.oauth2.OAuth2Message;
import org.apache.shindig.gadgets.oauth2.OAuth2Token;
import org.apache.shindig.gadgets.oauth2.persistence.OAuth2Client;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JSONOAuth2PersisterTest extends MockUtils {
  private JSONOAuth2Persister persister;

  @Before
  public void setUp() throws Exception {
    this.persister = MockUtils.getDummyPersister();
  }

  @Test
  public void testCreateToken_1() throws Exception {
    final OAuth2Token result = this.persister.createToken();

    Assert.assertNotNull(result);
  }

  @Test
  public void testLoadClients_1() throws Exception {

    final Set<OAuth2Client> result = this.persister.loadClients();

    Assert.assertNotNull(result);
    Assert.assertEquals(2, result.size());

    for (final OAuth2Client client : result) {
      final String gadgetUri = client.getGadgetUri();
      Assert.assertNotNull(gadgetUri);
      final boolean goodClient = (gadgetUri.equals(MockUtils.GADGET_URI1) || gadgetUri
          .equals(MockUtils.GADGET_URI2));
      Assert.assertTrue(goodClient);
      if (gadgetUri.equals(MockUtils.GADGET_URI1)) {
        Assert.assertEquals(MockUtils.AUTHORIZE_URL, client.getAuthorizationUrl());
        Assert.assertEquals(OAuth2Message.BASIC_AUTH_TYPE, client.getClientAuthenticationType());
        Assert.assertEquals(MockUtils.CLIENT_ID1, client.getClientId());
        Assert
            .assertEquals(MockUtils.CLIENT_SECRET1, new String(client.getClientSecret(), "UTF-8"));
        Assert.assertEquals(MockUtils.getDummyEncrypter(), client.getEncrypter());
        Assert.assertEquals(OAuth2Message.AUTHORIZATION, client.getGrantType());
        Assert.assertEquals(MockUtils.REDIRECT_URI, client.getRedirectUri());
        Assert.assertEquals(MockUtils.SERVICE_NAME, client.getServiceName());
        Assert.assertEquals(MockUtils.TOKEN_URL, client.getTokenUrl());
        Assert.assertEquals(OAuth2Accessor.Type.CONFIDENTIAL, client.getType());
        Assert.assertEquals(true, client.isAllowModuleOverride());
        Assert.assertEquals(true, client.isAuthorizationHeader());
        Assert.assertEquals(false, client.isUrlParameter());
      } else if (gadgetUri.equals(MockUtils.GADGET_URI2)) {
        Assert.assertEquals(MockUtils.AUTHORIZE_URL, client.getAuthorizationUrl());
        Assert.assertEquals(OAuth2Message.STANDARD_AUTH_TYPE, client.getClientAuthenticationType());
        Assert.assertEquals(MockUtils.CLIENT_ID2, client.getClientId());
        Assert
            .assertEquals(MockUtils.CLIENT_SECRET2, new String(client.getClientSecret(), "UTF-8"));
        Assert.assertEquals(MockUtils.getDummyEncrypter(), client.getEncrypter());
        Assert.assertEquals(OAuth2Message.CLIENT_CREDENTIALS, client.getGrantType());
        Assert.assertEquals(MockUtils.REDIRECT_URI, client.getRedirectUri());
        Assert.assertEquals(MockUtils.SERVICE_NAME, client.getServiceName());
        Assert.assertEquals(MockUtils.TOKEN_URL, client.getTokenUrl());
        Assert.assertEquals(OAuth2Accessor.Type.PUBLIC, client.getType());
        Assert.assertEquals(false, client.isAllowModuleOverride());
        Assert.assertEquals(false, client.isAuthorizationHeader());
        Assert.assertEquals(true, client.isUrlParameter());
      } else {
        throw new RuntimeException("Bad client found " + gadgetUri);
      }
    }
  }
}