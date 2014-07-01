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

public class Pc_Client2 {
	
	private final String pc = "PC";
	
	private BufferedReader in;
	private PrintWriter out;
	
//	JFrame frame = new JFrame("Remote PC");
//	JLabel lable;
	public Pc_Client2() throws IOException, WriterException{
//		lable = new JLabel();
//		lable.setIcon(new ImageIcon(make_qrcode("simon")));
//		frame.add(lable);
	}
	private void run() throws IOException{
		String serverAddress = "127.0.0.1"; //Server ip address
		Socket socket = new Socket(serverAddress, 9001);
		
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
		
		out.println(pc);
		while (true) {
			String line = in.readLine();
			if (line.startsWith("START")) {
				System.out.println("pc와 서버 연결이 되었습니다.");
				System.out.println(line.substring(5) +"\n");//넘겨 받은 값 확인
				
				out.println("PPString"); //넘겨 받은 값을 확인 차 넘겨준다. 위에 확인하는 값과 같아야 함
			} 

			else if (line.startsWith("CMDTEST")) {
				System.out.println("명령을 받습니다.");
				System.out.println(line.substring(8) + "\n");
			}
		}
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
	    
	    Pc_Client2 client = new Pc_Client2();
//		client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		client.frame.setVisible(true);
	    
	    client.showImageOnNewFrame(client.make_qrcode("simon"));
//		client.run();
		
	}

}
