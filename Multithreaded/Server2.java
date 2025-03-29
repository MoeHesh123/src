// package example2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server2 {

	public static final int UPPERCASE_PORT = 7777;

	public static int clientNumber = 0; // to keep track of the number of clients connecting to the server.

	public static void main(String[] args) throws IOException {
		System.out.println("The server started .. ");
		System.out.println("Local IP: " + InetAddress.getLocalHost().getHostAddress());

		try (ServerSocket ss = new ServerSocket(UPPERCASE_PORT)) {
			while (true) {
				new Thread(new CaseChanger(ss.accept(), clientNumber++)).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static class CaseChanger implements Runnable {
		Socket socket;
		int clientNo;

		public CaseChanger(Socket s, int clientNo) {
			this.socket = s;
			this.clientNo = clientNo;
			System.out.println("Connection with Client #" + clientNo + " at socket " + socket);
		}

		public void run() {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
				PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);

				// Send a welcome message to the client.
				out.println("Hello, you are client #" + clientNo + ".");
				String message = null;
				while (true) {
					if ((message = br.readLine()) == null || message.equals("."))
						break;
					String convertedMessage = null;
					convertedMessage = message.toUpperCase();
					System.out.println(convertedMessage);
				}
				System.out.close();
				br.close();
			} catch (IOException e) {
				System.out.println("Error handling client# " + this.clientNo + ": " + e);
			} finally {
				try {
					socket.close();
				} catch (IOException e) {
					System.out.println("Couldn't close a socket, what's going on?");
				}
				System.out.println("Connection with client# " + this.clientNo + " closed");
			}
		}
	}

}
