package io.dm848.microservices;

import io.dm848.microservices.userservice.UserServer;
import io.dm848.microservices.registrationservice.RegistrationServer;
import io.dm848.microservices.videoservice.VideoServer;
import io.dm848.microservices.webserverservice.WebServer;

/**
 * Only purpose of this file is to allow the services to be started from
 * command-line.
 * Has to be the <code>Main-Class</code> in the jar's <code>MANIFEST.MF</code>.
 */
public class Main {

	public static void main(String[] args) {

		String serverName = "NO-VALUE";

		for (int i=0; i<args.length; i+=2) {
			switch (args[i]) {
				case "-port":
					// Optionally set the HTTP port to listen on, overrides
					// value in the <server-name>-server.yml file
					System.setProperty("server.port", args[i+1]);
					// Fall through into ..

				case "-name":
					serverName = args[i+1].toLowerCase();
					break;

				default:
					usage();
					return;
			}
		}


		if (serverName.equals("registration") || serverName.equals("reg")) {
			RegistrationServer.main(args);
		} else if (serverName.equals("user") || serverName.equals("users")) {
			UserServer.main(args);
		} else if (serverName.equals("video") || serverName.equals("videos")) {
			VideoServer.main(args);
		} else if (serverName.equals("webserver") || serverName.equals("web")) {
			WebServer.main(args);
		} else {
			System.out.println("Unknown server name: " + serverName);
			usage();
		}
	}

	private static void usage() {
		System.out.println("Usage: java -jar ... -name <server-name> -port [server-port]");
		System.out.println("       where server-name is 'registration', 'user', 'video', or 'webserver' and server-port > 1024");
	}
}
