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
	
	private static final int PORT = 9001; //일단 포트 테스트
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
		
		private String QRString = "QRString"; //pc 마다 전부 다르게 내줘야 하는 qr code string 값
		
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
					if (type.substring(0, 2).equals(mobile))// mobile 에서 접속이 왔다.
					{
						System.out.println(type.substring(2) + " mobile 에서 접속 했습니다.");
						
						synchronized (QR_Key_Socket) {
							if (QR_Key_Socket.containsKey(type.substring(2))) // 모바일에서 입력된 qr 코드 값
							{
								out.println("pc no!");
								break;//모바일에서 전송 받은게 hashmap에 있다면 접속 할수 있다.
							}
							else
							{
								out.println("pc on!");
								break;
							}
						}
					}
					if (type.substring(0, 2).equals(pc))// pc 에서 접속이 왔다.
					{
						System.out.println("pc에서 접속 했습니다.");
						Random random = new Random();
						int random_i = random.nextInt(1000000000);
						System.out.println(random_i);//10억가지 랜덤 수 //랜덤 중복 체크 한번 해줘야함!!!!!!!!!
						
						out.println("START" + random_i);//START 라는 입력과 함께 qr코드 값을 전달
						
						String returnValue = in.readLine(); //현제 return value 는 QRString 임
						System.out.println(returnValue);
						
						synchronized (QR_Key_Socket) {
							
							if (!QR_Key_Socket.containsKey(returnValue)) 
							{
								QR_Key_Socket.put(Integer.parseInt(returnValue), socket);
								
								break;//더 이상  pc에서  받아 올건 없다. 
							}
						}
					} 
				}
				while(true){
					String input = in.readLine();
					
					System.out.println("모바일에서 받은 QR code  " + input);
					
					if(input == null)
					{
						return;
					}
					
					String key_input = input.substring(0, input.indexOf("mouse"));
					
					Iterator<Integer> iter = QR_Key_Socket.keySet().iterator();
					while (iter.hasNext()) {
						
						int key = iter.next();
						
						if(key == Integer.parseInt(key_input)) //모바일에서 전송 받은 pc의 key string 값
						{
							Socket value = QR_Key_Socket.get(key);
							out = new PrintWriter(value.getOutputStream(), true);
							System.out.println(input.substring(input.indexOf("mouse")));
							out.println("CMDTEST " + input.substring(input.indexOf("mouse")));// pc로 전달
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
