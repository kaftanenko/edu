#ifndef simple_traffic_light_controller_h
#define simple_traffic_light_controller_h

#include "Arduino.h"
#include "Adafruit_MCP23017.h"

#include "simple-timer.h"

enum LightState
{

  LIGHT_ON,
  LIGHT_OFF,
};

enum TrafficLightState
{

  TRAFFIC_LIGHT_OFF,

  TRAFFIC_LIGHT_RED,
  TRAFFIC_LIGHT_YELLOW,
  TRAFFIC_LIGHT_GREEN
};

class SimpleTrafficLightController
{
  Adafruit_MCP23017 *_mcp;

  uint16_t _controlPinRed;
  uint16_t _controlPinYellow;
  uint16_t _controlPinGreen;

  SimpleTimer _currentRememberingTimer;
  uint16_t _currentRememberingTaktInMs = 0;
  LightState _currentLightState = LIGHT_ON;

  TrafficLightState _currentState = TRAFFIC_LIGHT_OFF;

public:
  SimpleTrafficLightController(                //
      Adafruit_MCP23017 *mcp,                  //
      uint16_t controlPinRed,                  //
      uint16_t controlPinYellow,               //
      uint16_t controlPinGreen                 //
      ) : _mcp(mcp),                           //
          _controlPinRed(controlPinRed),       //
          _controlPinYellow(controlPinYellow), //
          _controlPinGreen(controlPinGreen)    //
  {
  }
  SimpleTrafficLightController(         //
      uint16_t controlPinRed,           //
      uint16_t controlPinYellow,        //
      uint16_t controlPinGreen          //
      ) : SimpleTrafficLightController( //
              nullptr,                  //
              controlPinRed,            //
              controlPinYellow,         //
              controlPinGreen           //
          )
  {
  }

  void begin();

  TrafficLightState getCurrentState();
  void switchToState(TrafficLightState state, uint16_t rememberingTaktInMs);

  void playEffects();

private:
  void _showCurrentState();
};

#endif
