package client.remote;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

//Mobile에서 접근한다는 가정하에 테스트
public class Mobile_Client {
	
	private final String mobile = "MO";
	
	private final String test ="QRString";
	
	private BufferedReader in;
	private PrintWriter out;
	
//	JFrame frame = new JFrame("Remote PC");
//	JLabel lable;
	public Mobile_Client() throws IOException, WriterException{
//		lable = new JLabel();
//		lable.setIcon(new ImageIcon(make_qrcode("simon")));
//		frame.add(lable);
	}
	private void run() throws IOException{
		String serverAddress = "127.0.0.1"; //Server ip address
		Socket socket = new Socket(serverAddress, 9001);
		
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
		
		out.println(mobile + "QRString");//QR 코드를 입력하면 데이터를 받아서 이것을 실행
		
//		while (true) {
			System.out.println("서버와 모바일 연결되었습니다.");
			String line = in.readLine();
			System.out.println(line);
			
			while(true)
			{
				out.println("QRString" + "test"); //pc로 보내보자!
			}
		//	out.println(test+"one");
				
		
	}
	
	private void showImageOnNewFrame(BufferedImage img) {
        JFrame frame = new JFrame();
        frame.setPreferredSize(new Dimension(600,600));
        ImageIcon imgIcon = new ImageIcon(img);
        JLabel imgLabel = new JLabel(imgIcon);
        JScrollPane scroll = new JScrollPane(imgLabel);
        frame.add(scroll);
        frame.pack();
        frame.setVisible(true);
    }
	//input : text
	//output : qr코드의 BufferedImage
	private BufferedImage make_qrcode(String text) throws IOException, WriterException 
	{
		QRCodeWriter qrCodeWriter = new QRCodeWriter(); //QR 코드 생성기
		BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 100, 100); 
		//QR 코드 encode -> Image 변환이 필요함
		
		BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
		//QR 코드를 buffered 로 담음
		
	    MatrixToImageWriter.writeToFile(bitMatrix, "png", new File("qrcode.png")); 
	    //파일 생성 메서드 -> png 파일로 변환
	    
	    return qrImage;
	    
	}
	public static void main(String[] args) throws IOException, WriterException {
	    
	    Mobile_Client client = new Mobile_Client();
//		client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		client.frame.setVisible(true);
	    
	    client.showImageOnNewFrame(client.make_qrcode("simon"));
		client.run();
		
	}

}
