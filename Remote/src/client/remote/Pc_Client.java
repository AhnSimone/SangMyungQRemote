package client.remote;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import server.remote.RoboMouse;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class Pc_Client {

	private final String pc = "PC";
	private BufferedReader in;
	private PrintWriter out;

	// JFrame frame = new JFrame("Remote PC");
	// JLabel lable;
	public Pc_Client() throws IOException, WriterException {
		// lable = new JLabel();
		// lable.setIcon(new ImageIcon(make_qrcode("simon")));
		// frame.add(lable);
	}

	private void run() throws IOException, WriterException {
		String serverAddress = "127.0.0.1"; // Server ip address
		Socket socket = new Socket(serverAddress, 9001);

		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);

		out.println(pc);
		while (true) {
			String line = in.readLine();
			if (line.startsWith("START")) {
				System.out.println("pc�� ���� ������ �Ǿ����ϴ�.");
				System.out.println(line.substring(5));// �Ѱ� ���� �� Ȯ��

				out.println(line.substring(5)); // �Ѱ� ���� ���� Ȯ�� �� �Ѱ��ش�.

				String qrcode_str = line.substring(5);
				showImageOnNewFrame(make_qrcode(qrcode_str));

			}
			// else if (line.startsWith("SETTINGQR")) {
			// System.out.println("qr �����͸� �޾� �ɴϴ�.");
			// out.println(test_qr);
			// } else if (line.startsWith("CONNECT")) {
			// System.out.println("����� ���� ������ �Ϸ�");
			else if (line.startsWith("CMDTEST")) {
				System.out.println("����� �޽��ϴ�.");
				System.out.println(line.substring(8, 13)); // mouse�� ¥��

				if (line.substring(8, 13).equals("mouse")) {
//					System.out.println(line.substring(8, 13)
//							+ "   x:"
//							+ line.substring(line.indexOf('x') + 1,
//									line.indexOf('y'))
//							+ " y:"
//							+ line.substring(line.indexOf('y') + 1,
//									line.indexOf('w'))
//							+ " w:"
//							+ line.substring(line.indexOf('w') + 1,
//									line.indexOf('h')) + " h:"
//							+ line.substring(line.indexOf('h') + 1));

					if ("move".equals(line.substring((line.indexOf("t") + 1),
							line.indexOf("q")))) {
						fire_event(Integer.parseInt(line.substring(
								line.indexOf('x') + 1, line.indexOf('y'))),
								Integer.parseInt(line.substring(
										line.indexOf('y') + 1,
										line.indexOf('w'))),
								Integer.parseInt(line.substring(
										line.indexOf('w') + 1,
										line.indexOf('h'))),
								Integer.parseInt(line.substring(line
										.indexOf('h') + 1)));
						
						System.out.println("move!!!");
					} else if ("left".equals(line.substring(
							(line.indexOf("t") + 1), line.indexOf("q")))) {
						try {
							Robot robot = new Robot();
							robot.mousePress(InputEvent.BUTTON1_MASK);
							robot.mouseRelease(InputEvent.BUTTON1_MASK);
							System.out.println("left!!!");
						} catch (AWTException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else if ("right".equals(line.substring(
							(line.indexOf("t") + 1), line.indexOf("q")))) {
						try {
							Robot robot = new Robot();
							robot.mousePress(InputEvent.BUTTON3_MASK);
							robot.mouseRelease(InputEvent.BUTTON3_MASK);
						} catch (AWTException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}

			}
		}
	}

	public void mouseGlide(int x1, int y1, int x2, int y2, int t, int n) {
		try {
			Robot r = new Robot();
			double dx = x2 / ((double) n);
			double dy = y2 / ((double) n);
			double dt = t / ((double) n);

			System.out.println(dx + "  " + dy + "  " + dt);
			for (int step = 1; step <= n; step++) {
				Thread.sleep((int) dt);
				System.out.println("mouseGlide" + (int) (x1 + dx * step) + "");
				r.mouseMove((int) (x1 + dx * step), (int) (y1 + dy * step));
			}
		} catch (AWTException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void fire_event(int x, int y, int w, int h) {
		Robot robot;
		Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
		PointerInfo pointerInfo = MouseInfo.getPointerInfo();
		System.out.println("�ػ� : " + res.width + " x " + res.height);
		int move_x = (int) ((float) x / w * res.width);
		int move_y = (int) ((float) y / h * res.height);

		mouseGlide(pointerInfo.getLocation().x, pointerInfo.getLocation().y,
				move_x, move_y, 10, 5); // 0.005 �ʷ� 1������ �����δ�.

	}

	private static void Where_mouse_point() {
		PointerInfo pointerInfo = MouseInfo.getPointerInfo();
		System.out.println("Mouse Position" + pointerInfo.getLocation());

		Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
		System.out.println("�ػ� : " + res.width + " x " + res.height);
	}

	private void showImageOnNewFrame(BufferedImage img) {
		JFrame frame = new JFrame();
		frame.setPreferredSize(new Dimension(600, 600));
		ImageIcon imgIcon = new ImageIcon(img);
		JLabel imgLabel = new JLabel(imgIcon);
		JScrollPane scroll = new JScrollPane(imgLabel);
		frame.add(scroll);
		frame.pack();
		frame.setVisible(true);
	}

	// input : text
	// output : qr�ڵ��� BufferedImage
	private BufferedImage make_qrcode(String text) throws IOException,
			WriterException {
		QRCodeWriter qrCodeWriter = new QRCodeWriter(); // QR �ڵ� ������
		BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE,
				100, 100);
		// QR �ڵ� encode -> Image ��ȯ�� �ʿ���

		BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
		// QR �ڵ带 buffered �� ����

		MatrixToImageWriter.writeToFile(bitMatrix, "png",
				new File("qrcode.png"));
		// ���� ���� �޼��� -> png ���Ϸ� ��ȯ

		return qrImage;

	}

	public static void main(String[] args) throws IOException, WriterException {

		Pc_Client client = new Pc_Client();
		// client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// client.frame.setVisible(true);

		// client.showImageOnNewFrame(client.make_qrcode("simon"));
		client.run();

	}

}
