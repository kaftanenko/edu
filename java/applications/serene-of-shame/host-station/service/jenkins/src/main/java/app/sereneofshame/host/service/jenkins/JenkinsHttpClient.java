package app.sereneofshame.host.service.jenkins;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JenkinsHttpClient {

	// ... constants

	private static final String JENKINS_URL_POSTFIX__JSON_API = "/api/json";

	// ... properties

	private final DefaultHttpClient client;
	private final BasicHttpContext context;

	private final JenkinsHttpClientConfig config;

	// ... business methods

	public JenkinsHttpClient(final JenkinsHttpClientConfig config) {

		this.config = config;

		// Create your httpclient
		client = new DefaultHttpClient();

		// Then provide the right credentials
		client.getCredentialsProvider().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
				new UsernamePasswordCredentials(config.getUsername(), config.getPassword()));

		// Generate BASIC scheme object and stick it to the execution context
		final BasicScheme basicAuth = new BasicScheme();
		context = new BasicHttpContext();
		context.setAttribute("preemptive-auth", basicAuth);

		// Add as the first (because of the zero) request interceptor
		// It will first intercept the request and preemptively initialize the
		// authentication scheme if there is not
		client.addRequestInterceptor(new PreemptiveAuth(), 0);
	}

	public Map<String, Object> callJsonApi(final String ressourcePath) {

		final HttpGet get = new HttpGet(buildJsonApiRequestURL(ressourcePath));

		try {
			final HttpResponse response = client.execute(get, context);
			final HttpEntity entity = response.getEntity();
			final String bodyAsString = EntityUtils.toString(entity);

			final Map<String, Object> jsonContentAsMap = new ObjectMapper().readValue(bodyAsString,
					new TypeReference<Map<String, Object>>() {
					});
			return jsonContentAsMap;
		} catch (final IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	// ... helper methods

	private String buildJsonApiRequestURL(final String ressourcePath) {

		return config.getHostUrl() + ressourcePath + JENKINS_URL_POSTFIX__JSON_API;
	}

	private static class PreemptiveAuth implements HttpRequestInterceptor {

		@Override
		public void process(final HttpRequest request, final HttpContext context) throws HttpException, IOException {
			// Get the AuthState
			final AuthState authState = (AuthState) context.getAttribute(ClientContext.TARGET_AUTH_STATE);

			// If no auth scheme available yet, try to initialize it
			// preemptively
			if (authState.getAuthScheme() == null) {
				final AuthScheme authScheme = (AuthScheme) context.getAttribute("preemptive-auth");
				final CredentialsProvider credsProvider = (CredentialsProvider) context
						.getAttribute(ClientContext.CREDS_PROVIDER);
				final HttpHost targetHost = (HttpHost) context.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
				if (authScheme != null) {
					final Credentials creds = credsProvider
							.getCredentials(new AuthScope(targetHost.getHostName(), targetHost.getPort()));
					if (creds == null) {
						throw new HttpException("No credentials for preemptive authentication");
					}
					authState.setAuthScheme(authScheme);
					authState.setCredentials(creds);
				}
			}
		}
	}

}
