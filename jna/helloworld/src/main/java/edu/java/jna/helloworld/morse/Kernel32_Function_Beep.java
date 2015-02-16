package edu.java.jna.helloworld.morse;

import com.sun.jna.Library;

public interface Kernel32_Function_Beep extends Library {

	boolean Beep(int frequency, int duration);
}

