package edu.java.concurrency;

public class CalculatorSamplerMain {

	public static void main(String[] args) {

		for (int i = 0; i <= 10; i++) {

			final CalculatorSampler calculator = new CalculatorSampler(i);
			final Thread thread = new Thread(calculator);
			thread.start();
		}
	}

}
