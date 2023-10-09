import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
	public static void main(String[] args) {
		try
		{
			int serverPort = 7896;
			ServerSocket listenSocket = new ServerSocket(serverPort);
			while (true)
			{
				// Bloqueia à espera de uma conexão
				Socket clientSocket = listenSocket.accept();
				// Processa o pedido
				Connection c = new Connection(clientSocket);
			}
		} catch (IOException e)
		{
			System.out.println("Listen: " + e.getMessage());
		}
	}
}