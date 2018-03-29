package calculator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

public class CalculateUnit {
	private String[] symbolList = {"+","-","*","/"};
	private ArrayList<String> symbolArray = new ArrayList<String>(Arrays.asList(symbolList));
	private int[] priority = {0,0,1,1};//�������ȼ�
	private ArrayList<String> symbols;
	private ArrayList<String> numbers;
	private String result;
	private static final int DEV_DIV_SCALE = 10;
	/*
	 * CalculateUnit��ڣ��������ǰ�����ǰ��һЩ�������
	 */
	public String go(String expression) {
		try{
			if(expression.contains("NaN"))
				return "NaN";
			calculate(expression);
			if(result.endsWith(".0")) {
				String test = String.valueOf(((int)Double.parseDouble(numbers.get(0))));
				//����ת��������ת������
				return test;
			}else if(result.contains("Infinity"))//���Ը�����0.0���ΪInfinity�����ᱨ��
				return "NaN";
		}catch(Exception e) {
			result = "NaN";
		}
		return result;
	}
	/*
	 * ����һЩ�����Ӱ˵�����
	 */
	private void calculate(String s) throws Exception{
		if(s.startsWith("-"))
			s = "0"+s;
		String [] number = s.split("\\+|\\-|\\*|\\/");//�������ֺͼ������
		numbers = new ArrayList<String>(Arrays.asList(number));
		symbols = new ArrayList<String>(number.length-1);
		char[] lst = s.toCharArray();
		int counter = 0;
		for (char c:lst) {
			String check = String.valueOf(c);
			if(symbolArray.contains(check)&&!(counter==number.length-1)) {
				symbols.add(check);
				counter++;
			}
		}
		if(symbols.isEmpty()) {
			result = number[0];
			return;
		}
		int presentPrior = getMax(priority);
		loopp(presentPrior);
	}
	/**
	 * ��ȡ������������ȶ�
	 * @param arr ��������ȶ�����
	 */
	private int getMax(int[] arr) {
		int max = arr[0];
		for(int i:arr) {
			if (i>max)
				max = i;
		}
		return max;
	}
	private void addition(int flag) throws Exception{
		BigDecimal b1 = new BigDecimal(numbers.get(flag));
		BigDecimal b2 = new BigDecimal(numbers.get(flag+1));
		result = b1.add(b2).toString();
	}
	private void subtraction(int flag) throws Exception{
		BigDecimal b1 = new BigDecimal(numbers.get(flag));
		BigDecimal b2 = new BigDecimal(numbers.get(flag+1));
		result = b1.subtract(b2).toString();
	}
	private void multiplication(int flag) throws Exception{
		BigDecimal b1 = new BigDecimal(numbers.get(flag));
		BigDecimal b2 = new BigDecimal(numbers.get(flag+1));
		result = b1.multiply(b2).toString();
	}
	private void division(int flag) throws Exception{
		BigDecimal b1 = new BigDecimal(numbers.get(flag));
		BigDecimal b2 = new BigDecimal(numbers.get(flag+1));
		result = b1.divide(b2, DEV_DIV_SCALE, BigDecimal.ROUND_HALF_EVEN).toString();
	}
	/**
	 * ����ʽѭ��������������������ȶȽ�������
	 * @param presentPrior ��ǰѭ�����Լ���ķ��ŵ����ȶ�
	 * @throws Exception ��ѧ�������
	 */
	private int loopp(int presentPrior) throws Exception{
		if(presentPrior>=0) {
			for (int flag=0;flag<symbols.size();flag++) {
				String symbol = symbols.get(flag);
				int prior = priority[symbolArray.indexOf(symbol)];
				if(prior==presentPrior) {
					switch (symbol){
					case "+":
						addition(flag);
						break;
					case "-":
						subtraction(flag);
						break;
					case "*":
						multiplication(flag);
						break;
					case "/":
						division(flag);
						break;
					default:
						result = "NaN";
					}
					symbols.remove(symbol);
					numbers.add(flag, result);
					numbers.remove(flag+1);
					numbers.remove(flag+1);
					flag--;
				}
				if(flag == symbols.size()-1) {
					presentPrior--;
					loopp(presentPrior);
				}else continue;
			}
		}
		return presentPrior;
	}
}
