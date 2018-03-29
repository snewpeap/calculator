package calculator;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Monitor extends JTextArea {
	/**
	 * 版本号，怎么用？
	 */
	private static final long serialVersionUID = 1L;

	CalculateUnit kevin;
	private String[] symbolList = {"+","-","*","/"};
	private ArrayList<String> symbolArray = new ArrayList<String>(Arrays.asList(symbolList));
	private boolean empty = true;//是否首个输入
	private boolean pointAvai = true;//控制小数点数量
	private int freeZero = -1;//控制0数量，-1时不受限
	private String str;

	public Monitor(String arg0, int arg1, int arg2) {
		super(arg0, arg1, arg2);
	}
	/*
	 * 获取monitor的最后一个字符
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
	 * 在用户按键（未实现）/输入后进行合法性检查
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
	 * 清除键（AC）功能
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
	 * 输入运算符的检查及显示
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
	 * 输入数字的检查及显示
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
	 * 输入0的检查和显示
	 */
	private void zeroCheck(Monitor m) {
		if(freeZero==0) 
			m.append("");
		else 
			m.append(str);
			freeZero--;
	}
	/*
	 * 输入小数点的检查和显示
	 */
	private void pointCheck(Monitor m) throws BadLocationException {
		if(empty) {
			m.setText("0.");;
			empty = false;
			pointAvai = false;
			freeZero = -1;
		}else if(!empty) {
			//遍历当前monitor检查是否可以添加小数点：在一个运算符或首个数字后有小数点则不能再加
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
