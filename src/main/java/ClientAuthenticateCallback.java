import org.apache.kafka.common.security.auth.AuthenticateCallbackHandler;
import org.apache.kafka.common.security.oauthbearer.OAuthBearerTokenCallback;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.AppConfigurationEntry;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ClientAuthenticateCallback implements AuthenticateCallbackHandler {
    private boolean isConfigured = false;

    public void configure(Map<String, ?> map, String saslMechanism, List<AppConfigurationEntry> jaasConfigEntries) {
        System.out.println("Inside client configure method");

        this.isConfigured = true;
    }

    public void close() {
        System.out.println("Inside client close method");

    }

    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        System.out.println("Inside client handle callbacks method");

        if (this.isConfigured) {
            for (Callback callback : callbacks) {
                if (callback instanceof OAuthBearerTokenCallback)
                    try {
                        OAuthBearerTokenCallback tokenCallback = (OAuthBearerTokenCallback) callback;
                        handleCallback(tokenCallback);
                    } catch (Exception ex) {

                    }
            }
        }
    }

    private void handleCallback(OAuthBearerTokenCallback callback) {

        OAuthBearerTokenJwt tokenJwt = new OAuthBearerTokenJwt("heythere", 6000000,
                System.currentTimeMillis(), "chenthill");

        System.out.println("In OAuth Bearer token callback!!!");
        callback.token(tokenJwt);
    }
}
