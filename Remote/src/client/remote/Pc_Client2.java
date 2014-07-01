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
				System.out.println("pc�� ���� ������ �Ǿ����ϴ�.");
				System.out.println(line.substring(5) +"\n");//�Ѱ� ���� �� Ȯ��
				
				out.println("PPString"); //�Ѱ� ���� ���� Ȯ�� �� �Ѱ��ش�. ���� Ȯ���ϴ� ���� ���ƾ� ��
			} 

			else if (line.startsWith("CMDTEST")) {
				System.out.println("����� �޽��ϴ�.");
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
	//output : qr�ڵ��� BufferedImage
	private BufferedImage make_qrcode(String text) throws IOException, WriterException 
	{
		QRCodeWriter qrCodeWriter = new QRCodeWriter(); //QR �ڵ� ������
		BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 100, 100); 
		//QR �ڵ� encode -> Image ��ȯ�� �ʿ���
		
		BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
		//QR �ڵ带 buffered �� ����
		
	    MatrixToImageWriter.writeToFile(bitMatrix, "png", new File("qrcode.png")); 
	    //���� ���� �޼��� -> png ���Ϸ� ��ȯ
	    
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
