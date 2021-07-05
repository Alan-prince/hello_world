package ch11;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class StopWatch extends JFrame {
	JLabel contentLab = new JLabel("00 : 00 : 00.00");
	long begin, elapsed, total;
	boolean b = false;

	private void init() {
		JButton startBtn = new JButton("开始");
		JButton pauseBtn = new JButton("暂停");
		JButton resumeBtn = new JButton("继续");
		JButton resetBtn = new JButton("复位");

		setLayout(null);
		pauseBtn.setEnabled(false);
		resumeBtn.setEnabled(false);
		resetBtn.setEnabled(false);

		startBtn.setSize(80, 50);
		startBtn.setLocation(10, 5);
		this.add(startBtn);

		pauseBtn.setSize(80, 50);
		pauseBtn.setLocation(100, 5);
		this.add(pauseBtn);

		resumeBtn.setSize(80, 50);
		resumeBtn.setLocation(190, 5);
		this.add(resumeBtn);

		resetBtn.setSize(80, 50);
		resetBtn.setLocation(280, 5);
		this.add(resetBtn);

		contentLab.setSize(400, 100);
		contentLab.setLocation(10, 60);
		Font font = new Font("宋体", Font.BOLD, 40);
		contentLab.setFont(font);
		this.add(contentLab);

		startBtn.addActionListener(e -> {
			b = true;
			total = 0;
			begin = System.currentTimeMillis();
			startBtn.setEnabled(false);
			pauseBtn.setEnabled(true);
			resetBtn.setEnabled(true);

		});
		pauseBtn.addActionListener(e -> {
			b = false;
			total += elapsed;
			pauseBtn.setEnabled(false);
			resumeBtn.setEnabled(true);
		});
		resumeBtn.addActionListener(e -> {
			b = true;
			begin = System.currentTimeMillis();
			pauseBtn.setEnabled(true);
			resumeBtn.setEnabled(false);
		});
		resetBtn.addActionListener(e -> {
			b = false;
			contentLab.setText("00 : 00 : 00.00");
			startBtn.setEnabled(true);
			pauseBtn.setEnabled(false);
			resumeBtn.setEnabled(false);
			resetBtn.setEnabled(false);
		});

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 200);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);

	}

	public void on() {
		new Thread(() -> {
			while (true) {
				if (b) {
					elapsed = System.currentTimeMillis() - begin;
					int h = (int) ((total + elapsed) / (60 * 60 * 1000));
					int m = (int) ((total + elapsed) % (60 * 60 * 1000) / (60 * 1000));
					int s = (int) ((total + elapsed) % (60 * 1000) / 1000);
					int ms = (int) ((total + elapsed) % 1000 / (1000 / 24));
					contentLab.setText(String.format("%02d : %02d : %02d.%02d", h, m, s, ms));
				}
				try {
					Thread.sleep(1000 / 24);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public static void main(String[] args) {
		StopWatch t = new StopWatch();
		t.init();
		t.on();
	}
}
