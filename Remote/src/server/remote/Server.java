package server.remote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class Server {
	
	private static final int PORT = 9001; //�ϴ� ��Ʈ �׽�Ʈ
	private static HashMap<Integer, Socket> QR_Key_Socket = new HashMap<Integer, Socket>();
	
	public static void main(String[] args) throws IOException {
		
		System.out.println("Server is running");
		ServerSocket listener = new ServerSocket(PORT);
		
		try{
			while(true){
				new Handler(listener.accept()).start();
			}
		}finally{
			listener.close();
		}
	}
	private static class Handler extends Thread{
		
		private final String pc = "PC";
		private final String mobile = "MO";
		
		private String QR_Key;
		
		private String QRString = "QRString"; //pc ���� ���� �ٸ��� ����� �ϴ� qr code string ��
		
		private Socket socket;
		
		private BufferedReader in;
		private PrintWriter out;
		
		public Handler(Socket accept) {
			// TODO Auto-generated constructor stub
			this.socket = accept;
		}
		public void run(){
			try{
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);
				
				while (true) {
					
					String type = in.readLine();
					System.out.println(type);
					if (type.substring(0, 2).equals(mobile))// mobile ���� ������ �Դ�.
					{
						System.out.println(type.substring(2) + " mobile ���� ���� �߽��ϴ�.");
						
						synchronized (QR_Key_Socket) {
							if (QR_Key_Socket.containsKey(type.substring(2))) // ����Ͽ��� �Էµ� qr �ڵ� ��
							{
								out.println("pc no!");
								break;//����Ͽ��� ���� ������ hashmap�� �ִٸ� ���� �Ҽ� �ִ�.
							}
							else
							{
								out.println("pc on!");
								break;
							}
						}
					}
					if (type.substring(0, 2).equals(pc))// pc ���� ������ �Դ�.
					{
						System.out.println("pc���� ���� �߽��ϴ�.");
						Random random = new Random();
						int random_i = random.nextInt(1000000000);
						System.out.println(random_i);//10�ﰡ�� ���� �� //���� �ߺ� üũ �ѹ� �������!!!!!!!!!
						
						out.println("START" + random_i);//START ��� �Է°� �Բ� qr�ڵ� ���� ����
						
						String returnValue = in.readLine(); //���� return value �� QRString ��
						System.out.println(returnValue);
						
						synchronized (QR_Key_Socket) {
							
							if (!QR_Key_Socket.containsKey(returnValue)) 
							{
								QR_Key_Socket.put(Integer.parseInt(returnValue), socket);
								
								break;//�� �̻�  pc����  �޾� �ð� ����. 
							}
						}
					} 
				}
				while(true){
					String input = in.readLine();
					
					System.out.println("����Ͽ��� ���� QR code  " + input);
					
					if(input == null)
					{
						return;
					}
					
					String key_input = input.substring(0, input.indexOf("mouse"));
					
					Iterator<Integer> iter = QR_Key_Socket.keySet().iterator();
					while (iter.hasNext()) {
						
						int key = iter.next();
						
						if(key == Integer.parseInt(key_input)) //����Ͽ��� ���� ���� pc�� key string ��
						{
							Socket value = QR_Key_Socket.get(key);
							out = new PrintWriter(value.getOutputStream(), true);
							System.out.println(input.substring(input.indexOf("mouse")));
							out.println("CMDTEST " + input.substring(input.indexOf("mouse")));// pc�� ����
						}
						break;
					}
			
				}
			}catch(IOException e)
			{
				System.out.println(e + " x1 ");
			}finally{
				try{
					socket.close();
				}catch(IOException ex)
				{
					System.out.println(ex + " x2 ");
				}
			}
		}
	
	}
}
