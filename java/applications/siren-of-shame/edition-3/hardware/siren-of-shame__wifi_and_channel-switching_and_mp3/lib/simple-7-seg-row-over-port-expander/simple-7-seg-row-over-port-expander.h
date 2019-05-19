#ifndef simple_7_seg_row_over_port_expander_h
#define simple_7_seg_row_over_port_expander_h

#include "Arduino.h"
#include "Adafruit_MCP23017.h"

const uint8_t _1 = B00000110;
const uint8_t _2 = B01011011;
const uint8_t _3 = B01001111;
const uint8_t _4 = B01100110;
const uint8_t _5 = B01101101;
const uint8_t _6 = B01111101;
const uint8_t _7 = B00000111;
const uint8_t _8 = B01111111;
const uint8_t _9 = B01101111;
const uint8_t _0 = B00111111;

const uint8_t _NONE = B00000000;

const uint8_t digits[] = { _0, _1, _2, _3, _4, _5, _6, _7, _8, _9 };

class ShiftRegister74HC595 
{
private:
    Adafruit_MCP23017 * _mcp;
    int _numberOfShiftRegisters;
    int _clockPin;
    int _serialDataPin;
    int _latchPin;
    uint8_t * _digitalValues;
public:
    ShiftRegister74HC595( //
        Adafruit_MCP23017* mcp, //
        int numberOfShiftRegisters, //
        int serialDataPin, int clockPin, int latchPin //
    );
    ~ShiftRegister74HC595();
    
    void begin();
    void setAll(const uint8_t * digitalValues);
#ifdef __AVR__
    void setAll_P(const uint8_t * digitalValuesProgmem); // Experimental, PROGMEM data
#endif
    uint8_t * getAll(); 
    void set(int pin, uint8_t value);
    void setNoUpdate(int pin, uint8_t value);
    void updateRegisters();
    void setAllLow();
    void setAllHigh(); 
    uint8_t get(int pin);
};

// ...

class Simple7SegRowOverPortsExpander {

    ShiftRegister74HC595 _shiftRegister74HC595;
    int _numberOfDigits;

    public:
        Simple7SegRowOverPortsExpander( //
            Adafruit_MCP23017* mcp, //
            int numberOfDigits, //
            int serialDataPin, int clockPin, int latchPin //
        ) : _shiftRegister74HC595( //
            mcp, //
            numberOfDigits, //
            serialDataPin, clockPin, latchPin
        ), _numberOfDigits(numberOfDigits) {};

    void begin();

    void displayNone();
    void displayNumber(long value);
};

#endif

