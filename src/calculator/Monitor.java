package calculator;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Monitor extends JTextArea {
	/**
	 * �汾�ţ���ô�ã�
	 */
	private static final long serialVersionUID = 1L;

	CalculateUnit kevin;
	private String[] symbolList = {"+","-","*","/"};
	private ArrayList<String> symbolArray = new ArrayList<String>(Arrays.asList(symbolList));
	private boolean empty = true;//�Ƿ��׸�����
	private boolean pointAvai = true;//����С��������
	private int freeZero = -1;//����0������-1ʱ������
	private String str;

	public Monitor(String arg0, int arg1, int arg2) {
		super(arg0, arg1, arg2);
	}
	/*
	 * ��ȡmonitor�����һ���ַ�
	 */
	private String getEndOfPresent(Monitor m) {
		String endOfPresent = "";
		if(!empty) {
			String present = m.getText();
			endOfPresent = present.substring(present.length()-1);
		}
		return endOfPresent;
	}
	/*
	 * ���û�������δʵ�֣�/�������кϷ��Լ��
	 */
	public void check_display(ActionEvent e,Monitor m) {
		str = e.getActionCommand();
		if(empty)
			m.setText("");
		if(symbolArray.contains(str)) {
			symbolCheck(m);
		}else if(str.equals(".")) {
			try {
				pointCheck(m);
			} catch (BadLocationException e1) {
				m.setText("ERROR");
				e1.printStackTrace();
			}
		}else if(str.equals("0")) {
			zeroCheck(m);
		}else if(str.equals("=")) {
			kevin = new CalculateUnit();
			String expression = m.getText();
			m.setText(kevin.go(expression));
			TreatEqul(m);
		}else if(str.equals("AC")) {
			clear(m);
		}
		else {
			numberCheck(m);
		}
	}
	/*
	 * �������AC������
	 */
	private void clear(Monitor m) {
		m.setText("0");
		empty = true;
		pointAvai = true;
		freeZero = -1;
	}
	private void TreatEqul(Monitor m) {
		if(m.getText().equals("0")||m.getText().equals("NaN")) {
			empty = true;
			pointAvai = true;
		}else {
			empty = false;
		}
		freeZero = -1;
	}
	/*
	 * ����������ļ�鼰��ʾ
	 */
	private void symbolCheck(Monitor m) {
		if(empty) {
			m.setText("0"+str);
		}else if(!empty) {
			String lastChar = getEndOfPresent(m);
			if(symbolArray.contains(lastChar)||lastChar.equals(".")) 
				m.setText(m.getText().substring(0, m.getText().length()-1));
			m.append(str);
			pointAvai = true;
			freeZero = 1;
		}
	}
	/*
	 * �������ֵļ�鼰��ʾ
	 */
	private void numberCheck(Monitor m) {
		if(freeZero == 0)
			m.setText(m.getText().substring(0, m.getText().length()-1));
		m.append(str);
		freeZero = -1;
		empty = false;
		pointAvai = true;
	}
	/*
	 * ����0�ļ�����ʾ
	 */
	private void zeroCheck(Monitor m) {
		if(freeZero==0) 
			m.append("");
		else 
			m.append(str);
			freeZero--;
	}
	/*
	 * ����С����ļ�����ʾ
	 */
	private void pointCheck(Monitor m) throws BadLocationException {
		if(empty) {
			m.setText("0.");;
			empty = false;
			pointAvai = false;
			freeZero = -1;
		}else if(!empty) {
			//������ǰmonitor����Ƿ�������С���㣺��һ����������׸����ֺ���С���������ټ�
			for(int i=m.getText().length()-2;i>=0;i--) {
				String until = m.getText(i,1);
				if(symbolArray.contains(until)||i==0) {
					for(int flag=i;flag<m.getText().length();flag++) {
						String trace = m.getText(flag,1);
						if (trace.equals(".")) {
							pointAvai = false;
							break;
						}
					}
					break;
				}
			}if(pointAvai) {
				m.append(str);
				pointAvai = false;
				freeZero = -1;
			}else m.append("");
		}
	}
}
