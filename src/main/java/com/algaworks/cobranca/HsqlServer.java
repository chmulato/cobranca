package com.algaworks.cobranca;

import org.hsqldb.Database;
import org.hsqldb.HsqlException;
import org.hsqldb.server.WebServer;

public class HsqlServer implements Runnable {

	/**
	 * Start the hsql server locally, with an HTTP interface.
	 * @param args
	 */
	private static WebServer ws;

	public void stopDB() {
		if (ws != null) {
			try {
				ws.checkRunning(true);
				System.out.println("db is running. stopping now");
				stopServer2();
			} catch (HsqlException hsqle) {
				System.out.println("db is already stopped");
			}
		} else {
			System.out.println("DB not started. it is null");
		}
	}

	private void stopServer2() {
		ws.shutdownWithCatalogs(Database.CLOSEMODE_NORMAL);
	}

	public void startDB() {
		if (ws != null) {
			try {
				ws.checkRunning(false);
				System.out.println("check running is false");
				startServer2();
			} catch (HsqlException hsqle) {
				// already running.
				System.out.println("Server is already running.");
				return;
			}
		} else {
			// start the server, it is null
			System.out.println("server is null, starting now");
			startServer2();
		}
	}

	private WebServer startServer2() {
		ws = new WebServer();
		ws.setDatabasePath(0, "file:" + HsqlServerConst.DB_FILENAME);
		ws.setDatabaseName(0, HsqlServerConst.DB_NAME);
		ws.setPort(HsqlServerConst.DB_PORT);
		ws.start();
		return ws;
	}

	@Override
	public void run() {
		startDB();
	}

}