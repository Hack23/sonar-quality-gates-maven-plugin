package com.hack23.maven.plugin;

import static com.mashape.unirest.http.Unirest.setHttpClient;
import static java.lang.String.format;
import static org.apache.http.impl.client.HttpClients.createDefault;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hack23.maven.plugin.model.Conditions;
import com.hack23.maven.plugin.model.Measures;
import com.hack23.maven.plugin.model.MeasuresContainer;
import com.hack23.maven.plugin.model.QualityGateValue;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * The Class SonarQualityGatesMojo.
 */
@Mojo(name = "inspect")
public class SonarQualityGatesMojo extends AbstractMojo {

	/** The Constant COLON. */
	private static final String COLON = ":";

	/** The Constant SONAR_API_URL. */
	private static final String SONAR_API_URL = "%s/api/measures/search?projectKeys=%s&metricKeys=alert_status,quality_gate_details";

	/** The Constant SONAR_DEFAULT_HOST_URL. */
	private static final String SONAR_DEFAULT_HOST_URL = "http://localhost:9000";

	/** The Constant SONAR_HOST_URL. */
	private static final String SONAR_HOST_URL = "sonar.host.url";

	/** The Constant SONAR_PROJECT_KEY. */
	private static final String SONAR_PROJECT_KEY = "sonar.projectKey";

	/** The Constant STATUS_CODE_OK. */
	private static final int STATUS_CODE_OK = 200;

	/** The session. */
	@Parameter(defaultValue = "${session}", readonly = true)
	private MavenSession session;

	/** The sonar host url. */
	@Parameter(property = SONAR_HOST_URL)
	private String sonarHostUrl;

	/**
	 * Instantiates a new sonar quality gates mojo.
	 */
	@Inject
	public SonarQualityGatesMojo() {
		setHttpClient(createDefault());
	}

	/**
	 * Execute.
	 *
	 * @throws MojoExecutionException the mojo execution exception
	 * @throws MojoFailureException   the mojo failure exception
	 */
	public void execute() throws MojoExecutionException, MojoFailureException {
		final MavenProject topLevelProject = session.getTopLevelProject();
		final List<Measures> measures = retrieveSonarMeasures(
				format(SONAR_API_URL, getSonarHostUrl(topLevelProject.getProperties()), getSonarKey(topLevelProject)));

		if (measures.isEmpty()) {
			throw new MojoExecutionException(
					"\nno matching project in sonarqube for project key:" + getSonarKey(topLevelProject));
		}

		if (!measures.get(0).getValue().equals("OK")) {

			try {
				final QualityGateValue qualityGateValue = new ObjectMapper().readValue(measures.get(1).getValue(),
						QualityGateValue.class);
				final StringBuilder builder = new StringBuilder();
				builder.append("\nFailed quality gate\n");
				final List<Conditions> conditions = qualityGateValue.getConditions();
				for (final Conditions condition : conditions) {
					if (!condition.getLevel().equals("OK")) {
						builder.append(condition);
						builder.append("\n");
					}
				}

				throw new MojoExecutionException(builder.toString());

			} catch (final IOException e) {
				throw new MojoFailureException("Failed to execute mojo", e);
			}

		}
	}

	/**
	 * Retrieve sonar measures.
	 *
	 * @param url the url
	 * @return the list
	 * @throws MojoFailureException the mojo failure exception
	 */
	private List<Measures> retrieveSonarMeasures(final String url) throws MojoFailureException {
		try {
			final HttpResponse<String> response = Unirest.get(url).asString();
			final String body = response.getBody();

			if (response.getStatus() != STATUS_CODE_OK) {
				throw new MojoFailureException(MessageFormat.format(
						"Attempt to call Sonarqube responded with an error status :{0} : for url:{1} : response{2}: ",
						response.getStatus(), url, body));
			}

			return new ObjectMapper().readValue(body, MeasuresContainer.class).getMeasures();
		} catch (IOException | UnirestException e) {
			throw new MojoFailureException("Could not execute sonar-quality-gates-plugin", e);
		} finally {
			shutdown();
		}
	}

	/**
	 * Gets the sonar host url.
	 *
	 * @param properties the properties
	 * @return the sonar host url
	 */
	private String getSonarHostUrl(final Properties properties) {
		if (sonarHostUrl != null) {
			return sonarHostUrl;
		}

		return properties.containsKey(SONAR_HOST_URL) ? properties.getProperty(SONAR_HOST_URL) : SONAR_DEFAULT_HOST_URL;
	}

	/**
	 * Gets the sonar key.
	 *
	 * @param pom the pom
	 * @return the sonar key
	 */
	private String getSonarKey(final MavenProject pom) {
		if (pom.getModel().getProperties().containsKey(SONAR_PROJECT_KEY)) {
			return pom.getModel().getProperties().getProperty(SONAR_PROJECT_KEY);
		}

		return pom.getGroupId() + COLON + pom.getArtifactId();
	}

	/**
	 * Shutdown.
	 *
	 * @throws MojoFailureException the mojo failure exception
	 */
	private void shutdown() throws MojoFailureException {
		try {
			Unirest.shutdown();
		} catch (final IOException e) {
			throw new MojoFailureException("Could not properly shutdown", e);
		}
	}
}
