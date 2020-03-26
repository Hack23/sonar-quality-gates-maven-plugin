package com.hack23.maven.plugin;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.util.Scanner;

import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.testing.MojoRule;
import org.apache.maven.plugin.testing.resources.TestResources;
import org.apache.maven.project.MavenProject;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.mashape.unirest.http.Unirest;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class SonarQualityGatesMojoTest {

	@Rule
	public MojoRule rule = new MojoRule();
	@Rule
	public TestResources resources = new TestResources();
	@Rule
	public ExpectedException exception = ExpectedException.none();
	private HttpServer server;
	private SonarEventHandler sonarEventHandler;
	private Mojo mojo;
	private MavenProject project;

	@Before
	public void setUp() throws Exception {
		sonarEventHandler = new SonarEventHandler();

		server = HttpServer.create(new InetSocketAddress(0), 0);
		server.createContext("/", sonarEventHandler);
		server.setExecutor(null);
		server.start();

		project = rule.readMavenProject(resources.getBasedir(""));
		mojo = rule.lookupConfiguredMojo(project, "inspect");

		project.getProperties().put("sonar.host.url", "http://localhost:" + server.getAddress().getPort());
	}

	@After
	public void tearDown() {
		server.stop(0);
	}

	@Test
	public void failedQualityGateTest() throws Exception {
		exception.expect(MojoExecutionException.class);
		exception.expectMessage("Failed quality gate\n"
				+ "Conditions[actual=47.05882352941177,error=95,level=ERROR,metric=new_coverage,op=LT,period=1]\n"
				+ "Conditions[actual=47.4,error=95,level=ERROR,metric=coverage,op=LT,period=<null>");

		sonarEventHandler.setResponse(200, getResponse("failedqualitygate.json"));
		mojo.execute();
	}

	@Test
	public void passedQualityGateTest() throws MojoFailureException, MojoExecutionException {
		sonarEventHandler.setResponse(200, getResponse("passedqualitygate.json"));
		mojo.execute();
	}

	@Test
	public void failedMissingProjectQualityGateTest() throws MojoFailureException, MojoExecutionException {
		exception.expect(MojoExecutionException.class);
		exception.expectMessage("no matching project in sonarqube for project key:com.hack23.maven:test-sonar-quality-gates-maven-plugin");
		sonarEventHandler.setResponse(200, getResponse("missingproject.json"));
		mojo.execute();
	}


	@Test
	public void sonarProjectKeyPropertyTest() throws MojoFailureException, MojoExecutionException, Exception, SecurityException {
		sonarEventHandler.setResponse(200, getResponse("passedqualitygate.json"));
		project.getProperties().put("sonar.projectKey", "com.hack23.maven:test-property");

		setField("sonarHostUrl","http://localhost:" + server.getAddress().getPort());
		mojo.execute();
	}

	@Test
	public void sonarLoginBasicTest() throws MojoFailureException, MojoExecutionException, Exception, SecurityException {
		sonarEventHandler.setResponse(200, getResponse("passedqualitygate.json"));

		setField("sonarLogin","sonarLogin");
		setField("sonarPassword","sonarPassword");
		mojo.execute();
	}

	@Test
	public void sonarLoginBasicWithBlankTest() throws MojoFailureException, MojoExecutionException, Exception, SecurityException {
		sonarEventHandler.setResponse(200, getResponse("passedqualitygate.json"));

		setField("sonarLogin","sonarLogin");
		setField("sonarPassword","");
		mojo.execute();
	}


	private void setField(String field, String value) throws NoSuchFieldException, IllegalAccessException {
		final Field f1 = mojo.getClass().getDeclaredField(field);
		f1.setAccessible(true);
		f1.set(mojo, value);
	}

	@Test
	public void sonarLoginPropertyTest() throws MojoFailureException, MojoExecutionException, Exception, SecurityException {
		sonarEventHandler.setResponse(200, getResponse("passedqualitygate.json"));
		project.getProperties().put("sonar.projectKey", "com.hack23.maven:test-property");


		final Field f1 = mojo.getClass().getDeclaredField("sonarHostUrl");
		f1.setAccessible(true);
		f1.set(mojo, "http://localhost:" + server.getAddress().getPort());

		mojo.execute();
	}


	@Test
	public void sonarqube404Test() throws Exception {
		exception.expect(MojoFailureException.class);
		exception.expectMessage(
				"Attempt to call Sonarqube responded with an error status :404 : for url:http://localhost:"
						+ server.getAddress().getPort()
						+ "/api/measures/search?projectKeys=com.hack23.maven:test-sonar-quality-gates-maven-plugin&metricKeys=alert_status,quality_gate_details : response: ");

		sonarEventHandler.setResponse(404, "");
		mojo.execute();
	}

	@Test
	public void unirestExceptionTest() throws MojoFailureException, MojoExecutionException {
		exception.expect(MojoFailureException.class);
		exception.expectMessage("Could not execute sonar-quality-gates-plugin");

		Unirest.setHttpClient(null);

		mojo.execute();
	}

	private String getResponse(final String file) {
		return new Scanner(getClass().getClassLoader().getResourceAsStream(file)).useDelimiter("\\Z").next();
	}

	private static final class SonarEventHandler implements HttpHandler {

		private String response = "[]";
		private int status = 200;

		public void handle(final HttpExchange httpExchange) throws IOException {

			try (OutputStream responseBody = httpExchange.getResponseBody()) {
				httpExchange.sendResponseHeaders(status, response.length());
				responseBody.write(response.getBytes());
			}
		}

		public void setResponse(final int status, final String response) {
			this.status = status;
			this.response = response;
		}
	}
}
