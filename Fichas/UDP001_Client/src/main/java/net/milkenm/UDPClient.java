package net.milkenm;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPClient {
	private static int _nextMessageId = 0;

	public static void main(String[] args) {
		DatagramSocket aSocket = null;

		try {

			aSocket = new DatagramSocket();
			aSocket.setSoTimeout(5000);
			int nMessage=1;

			String m= "vou enviar esta mensagem ao servidor";

			InetAddress aHost = InetAddress.getByName("localhost");
			int serverPort = 6789;
			while(nMessage < 10) {
				if(nMessage == 4 || nMessage == 7) nMessage++;
				byte[] mb= (String.valueOf(nMessage).concat(",").concat(m)).getBytes();

				DatagramPacket request = new DatagramPacket(mb,  mb.length, aHost, serverPort);

				aSocket.send(request);

				byte[] buffer = new byte[1000];

				DatagramPacket reply = new DatagramPacket(buffer, buffer.length);

				aSocket.receive(reply);
				System.out.println("Enviei/recebi a mensagem: " + new String(reply.getData()));
				if(nMessage >= 5)
					System.out.println( new String(reply.getData()).trim());
				else
					System.out.println( new String(reply.getData()).trim());
				nMessage++;
			}

		}catch (SocketException e){System.out.println("Socket: " + e.getMessage());
			System.out.println("fail");

		}catch (IOException e){System.out.println("IO: " + e.getMessage());
			System.out.println("fail");

		}finally {if(aSocket != null) aSocket.close();}
	}

	private static int getMessageId() {
		int messageId = _nextMessageId;
		_nextMessageId++;
		return messageId;
	}
}