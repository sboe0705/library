package de.sboe0705.library.configuration;

import java.net.URI;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotEmpty;

@Validated
public class RestClientConfiguration {

	@NotEmpty
	private String name;

	private String protocol = "http";

	@NotEmpty
	private String host;

	@NotEmpty
	private String port;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public URI getBaseURI() {
		return URI.create(getProtocol() + "://" + getHost() + ":" + getPort());
	}

}
