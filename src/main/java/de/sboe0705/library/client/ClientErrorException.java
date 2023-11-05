package de.sboe0705.library.client;

public class ClientErrorException extends Exception {

	public ClientErrorException(String clientName, Throwable cause) {
		super("Unexpected error from client '" + clientName + "'", cause);
	}

}
