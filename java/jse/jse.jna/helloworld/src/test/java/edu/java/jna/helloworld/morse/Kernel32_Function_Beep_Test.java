package edu.java.jna.helloworld.morse;

import com.sun.jna.Native;

public class Kernel32_Function_Beep_Test {

	private static Kernel32_Function_Beep kernel32 = (Kernel32_Function_Beep) Native.loadLibrary("kernel32",
			Kernel32_Function_Beep.class);

	private static void toMorseCode(String letter) throws Exception {

		for (byte b : letter.getBytes()) {
			kernel32.Beep(1200, ((b == '.') ? 50 : 150));
			Thread.sleep(50);
		}
	}

	public static void main(String[] args) throws Exception {

		final String helloWorld[][] = { { "....", ".", ".-..", ".-..", "---" }, // HELLO
				{ ".--", "---", ".-.", ".-..", "-.." } // WORLD
		};

		for (String word[] : helloWorld) {
			for (String letter : word) {
				toMorseCode(letter);
				Thread.sleep(150);
			}
			Thread.sleep(350);
		}
	}
}