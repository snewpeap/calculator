package calculator;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.io.*;

public class CalculatorGUI {
	JFrame mainFrame;
	JFrame infoFrame;
	JPanel numberZone;
	JPanel symbolZone;
	JPanel funcZone;
	Monitor monitor;
	String[] symbolList = {"+","-","*","/",};
	ArrayList<String> symbolArray = new ArrayList<String>(Arrays.asList(symbolList));
	Font ft1 = new Font("Times New Roman",Font.BOLD,25);
	Font ft2 = new Font("Arial",Font.PLAIN,20);
	Font ft3 = new Font("宋体",Font.PLAIN,15);
	
	public static void main(String[] args) {
		CalculatorGUI cal = new CalculatorGUI();
		cal.showInfo();
		cal.run();
	}
	private void run() {
		mainFrame = new JFrame("垃圾计算器");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BorderLayout mainLayout = new BorderLayout();
		mainLayout.setHgap(5);
		mainLayout.setVgap(10);
		JPanel background = new JPanel(mainLayout);
		background.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		
		monitor = new Monitor("0", 1, 1);
		monitor.setFont(ft1);
		monitor.setEditable(false);
		Insets insets = new Insets(0,0,10,0);
		monitor.setMargin(insets);
		JScrollPane scroll = new JScrollPane(monitor);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		background.add(BorderLayout.NORTH,scroll);
		
		class Listener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				monitor.check_display(e,monitor);
			}
		}
		
		GridBagLayout numLayout2 = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		numberZone = new JPanel(numLayout2);
		background.add(BorderLayout.CENTER,numberZone);
		c.weighty = 1.0;c.ipadx = 60;c.ipady = 60;
		for (int i=0;i<9;i++) {
			String str = String.valueOf(i+1);
			JButton numberButton = new JButton(str);
			numberButton.addActionListener(new Listener());
			numberButton.setFont(ft2);
			c.gridx=i%3;
			c.gridy=i/3;
			numLayout2.setConstraints(numberButton, c);
			numberZone.add(numberButton);
		}
		JButton bttnZero = new JButton("0");
		JButton bttnPoint = new JButton(".");
		bttnZero.addActionListener(new Listener());
		bttnPoint.addActionListener(new Listener());
		bttnZero.setFont(ft2);
		bttnPoint.setFont(ft2);
		c.gridx = 0;c.gridy = 4;c.gridwidth = 2;
		numLayout2.setConstraints(bttnZero, c);
		numberZone.add(bttnZero);
		c.gridx = 2;c.gridy = 4;c.gridwidth = 1;
		numLayout2.setConstraints(bttnPoint, c);
		numberZone.add(bttnPoint);
		
		
		GridLayout symbolLayout = new GridLayout(5,1);
		symbolZone = new JPanel(symbolLayout);
		background.add(BorderLayout.EAST,symbolZone);
		Dimension symsize = new Dimension(60,60);
		for (int i=0;i<4;i++) {
			JButton symbolButton = new JButton(symbolList[i]);
			symbolButton.setPreferredSize(symsize);
			symbolButton.addActionListener(new Listener());
			symbolZone.add(symbolButton);
			symbolButton.setFont(ft2);
		}
		JButton bttnEqul = new JButton("=");
		bttnEqul.addActionListener(new Listener());
		bttnEqul.setFont(ft2);
		symbolZone.add(bttnEqul);
		
		funcZone = new JPanel(symbolLayout);
		background.add(BorderLayout.WEST,funcZone);
		JButton clear = new JButton("AC");
		clear.addActionListener(new Listener());
		clear.setFont(ft2);
		funcZone.add(clear);
		
		
		mainFrame.setContentPane(background);
		mainFrame.setBounds(30, 30, 300, 500);
		mainFrame.pack();
		mainFrame.setVisible(true);	
	}
	private void showInfo() {
		infoFrame = new JFrame("版本信息");
		BorderLayout infoLayout = new BorderLayout();
		JPanel bkgd = new JPanel(infoLayout);
		bkgd.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
		JTextArea infoArea = new JTextArea(10,1);
		infoArea.setFont(ft3);
		bkgd.add(BorderLayout.CENTER,infoArea);
		infoArea.setForeground(Color.BLACK);
		infoArea.setEditable(false);
		infoArea.setLineWrap(true);
		infoFrame.setContentPane(bkgd);
		infoFrame.setBounds(700, 380, 500, 300);
		infoFrame.setVisible(true);
		try {
			InputStream is = this.getClass().getResourceAsStream("/Resource/infoFile.txt");
			BufferedReader br=new BufferedReader(new InputStreamReader(is));
			String line = br.readLine();
			infoArea.append("版本号："+line+"\n");infoArea.append("版本特性：\n");
			while((line=br.readLine())!= null){
				infoArea.append(">>>>>>>"+line+"\n");
			}
			br.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
