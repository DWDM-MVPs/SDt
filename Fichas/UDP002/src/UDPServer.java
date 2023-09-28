import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UDPServer
{
	private static final String RESET = "\u001B[0m";
	private static final String RED = "\u001B[31m";

	private static int _expectedPacketId = 1;
	private static final Map<Integer, String> _receivedPackets = new HashMap<>();

	private static boolean isExpectingMissingPacket = false;
	private static boolean reordered = false;

	public static void main(String[] args)
	{

		try (DatagramSocket socket = new DatagramSocket(6789))
		{
			byte[] buffer = new byte[1024];

			while (true)
			{
				DatagramPacket request = new DatagramPacket(buffer, buffer.length);
				socket.receive(request);

				String[] split = new String(request.getData()).trim().split(",");

				storeData(request.getData());

				if (reordered || isExpectingMissingPacket)
				{
					reordered = false;
					List<Integer> missingPackets = getMissingPackets();
					if (!missingPackets.isEmpty())
					{
						String waitingFor = "waitingfor," + missingPackets.get(0);
						log("Sent '" + waitingFor + "' packet.");

						sendData(socket, waitingFor, request);
						continue;
					}
				}

				sendData(socket, split[1], request);
			}
		}
		catch (SocketException e)
		{
			log(RED + "Socket: " + e.getMessage());
		}
		catch (IOException e)
		{
			log(RED + "IO: " + e.getMessage());
		}
	}

	private static void sendData(DatagramSocket socket, String message, DatagramPacket originalRequest) throws IOException
	{
		byte[] data = message.getBytes();
		DatagramPacket packet = new DatagramPacket(data, data.length, originalRequest.getAddress(), originalRequest.getPort());
		socket.send(packet);
	}

	private static void storeData(byte[] receivedData)
	{
		String data = new String(receivedData).trim();
		String[] split = data.split(",");

		int packetId = Integer.parseInt(split[0]);
		String message = split[1];


		_receivedPackets.put(packetId, message);
		if (packetId == _expectedPacketId)
		{
			if (isExpectingMissingPacket)
			{
				isExpectingMissingPacket = false;
				reordered = true;
			}
			log("Received packet with ID '" + packetId + "'.");
			_expectedPacketId = _receivedPackets.keySet().stream().mapToInt(Integer::intValue).max().getAsInt() + 1;
		}
		else
		{
			isExpectingMissingPacket = true;
			log(RED + "Received message '" + message + "' with ID '" + packetId + "'. Expected ID is: '" + _expectedPacketId + "'.");
		}
	}

	private static List<Integer> getMissingPackets()
	{
		List<Integer> missingPackets = new ArrayList<>();
		int lastPacketId = 1;
		for (int packetId : _receivedPackets.keySet().stream().mapToInt(Integer::intValue).sorted().toArray())
		{
			if (packetId > lastPacketId + 1)
			{
				for (int i = lastPacketId; i < packetId; i++)
				{
					missingPackets.add(i + 1);
				}
			}
			lastPacketId = packetId;
		}
		return missingPackets;
	}

	private static void log(Object message)
	{
		System.out.println(RESET + message);
	}
}