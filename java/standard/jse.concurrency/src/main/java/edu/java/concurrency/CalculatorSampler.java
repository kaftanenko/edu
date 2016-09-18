package edu.java.concurrency;

public class CalculatorSampler implements Runnable {

	private final int number;

	public CalculatorSampler(int number) {

		this.number = number;
	}

	public void run() {

		for (int i = 1; i <= 10; i++) {
			System.out.printf("%s: %d * %d = %d\n", Thread.currentThread().getName(), number, i, i * number);
		}
	}

}
