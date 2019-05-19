#include <Arduino.h>
#include <Adafruit_MCP23017.h>

#include "simple-7-seg-row-over-port-expander.h"

// ... configuration

#define _7_SEG_CONTROLLER__MCP_PIN__DS_____8 8  // data/DS
#define _7_SEG_CONTROLLER__MCP_PIN__STCP___9 9  // latch/STCP
#define _7_SEG_CONTROLLER__MCP_PIN__SHCP__10 10 // clock/SHCP

// ... business class

class ChannelNumberDisplay
{

private:
  uint _currentChannelNumber = 0;
  Simple7SegRowOverPortsExpander _simple7SegController;

public:
  ChannelNumberDisplay(                                //
      Adafruit_MCP23017 *mcp,                          //
      uint digitsNumber,                               //
      uint serialDataPin, uint clockPin, uint latchPin //
      ) : _simple7SegController(                       //
              mcp,                                     //
              digitsNumber,                            //
              serialDataPin, clockPin, latchPin        //
          ){};
  ChannelNumberDisplay(                             //
      Adafruit_MCP23017 *mcp,                       //
      uint digitsNumber                             //
      ) : ChannelNumberDisplay(                     //
              mcp,                                  //
              digitsNumber,                         //
              _7_SEG_CONTROLLER__MCP_PIN__DS_____8, //
              _7_SEG_CONTROLLER__MCP_PIN__SHCP__10, //
              _7_SEG_CONTROLLER__MCP_PIN__STCP___9  //
          ){};

  void setup();

  uint getDisplayValue();

  void displayNone();
  void displayValue(uint channel);
};

// ... business methods

void ChannelNumberDisplay::setup()
{
  _simple7SegController.begin();
  displayNone();
}

uint ChannelNumberDisplay::getDisplayValue()
{
  return _currentChannelNumber;
}

void ChannelNumberDisplay::displayNone()
{
  _currentChannelNumber = 0;
  _simple7SegController.displayNone();
}

void ChannelNumberDisplay::displayValue(uint channel)
{
  _currentChannelNumber = channel;
  _simple7SegController.displayNumber(_currentChannelNumber);
}
