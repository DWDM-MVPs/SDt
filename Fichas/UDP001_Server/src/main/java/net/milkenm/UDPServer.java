package net.milkenm;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

public class UDPServer {
	private static int _nextMessageId = 1;

	public static void main(String[] args) {
		DatagramSocket aSocket = null;

		try {
			aSocket = new DatagramSocket(6789);
			byte[] buffer = new byte[1024];

			while (true) {
				DatagramPacket request = new DatagramPacket(buffer, buffer.length);
				aSocket.receive(request);

				String[] split =  new String(request.getData()).trim().split(",");
				if (storeData(request.getData())) {
					sendData(aSocket, split[1].getBytes(), request);
				}
				else{
					String message = "waitingfor," + _nextMessageId;
					byte[] data = message.getBytes();
					System.out.println("Sent 'waitingfor' packet.");

					sendData(aSocket, data, request);
				}
			}
		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		} finally {
			if (aSocket != null) aSocket.close();
		}
	}

	private static void sendData(DatagramSocket socket, byte[] data, DatagramPacket originalRequest) throws IOException {
		DatagramPacket packet = new DatagramPacket(data, data.length, originalRequest.getAddress(), originalRequest.getPort());
		socket.send(packet);
	}

	private static boolean storeData(byte[] receivedData) {
		String message = new String(receivedData).trim();

		String[] split = message.split(",");

		int messageId = Integer.parseInt(split[0]);
		String content = split[1];

		System.out.println("Received message '" + content + "' with ID '" + messageId + "'. Expected ID is: '" + _nextMessageId + "'.");

		if (messageId != _nextMessageId) {
			return false;
		}

		_nextMessageId++;
		return true;
	}
}