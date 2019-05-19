#ifndef simple_traffic_light_controller_h
#define simple_traffic_light_controller_h

#include "Arduino.h"
#include "Adafruit_MCP23017.h"

#include "simple-timer.h"

enum LightState {

  LIGHT_ON,
  LIGHT_OFF,
};

enum TrafficLightState {

  TRAFFIC_LIGHT_OFF,
  
  TRAFFIC_LIGHT_RED,
  TRAFFIC_LIGHT_YELLOW,
  TRAFFIC_LIGHT_GREEN,
  
  TRAFFIC_LIGHT_RED_CHANGING,
  TRAFFIC_LIGHT_YELLOW_CHANGING,
  TRAFFIC_LIGHT_GREEN_CHANGING,
};

class SimpleTrafficLightController {
  
  Adafruit_MCP23017 * _mcp;

  uint8_t _controlPinRed;
  uint8_t _controlPinYellow;
  uint8_t _controlPinGreen;
    
  int _lightEffects_TickRate_InMs;
  
  SimpleTimer _lightEffects_Timer;
  LightState _lightEffects_CurrentState = LIGHT_OFF;

  TrafficLightState _currentState = TRAFFIC_LIGHT_OFF;
  
  public:
    SimpleTrafficLightController(
      Adafruit_MCP23017 * mcp,
      uint8_t controlPinRed,
      uint8_t controlPinYellow,
      uint8_t controlPinGreen,
      int lightEffects_TickRate_InMs
    ) : 
      _mcp(mcp),
      _controlPinRed(controlPinRed),
      _controlPinYellow(controlPinYellow),
      _controlPinGreen(controlPinGreen),
      _lightEffects_TickRate_InMs(lightEffects_TickRate_InMs)
    {}
    SimpleTrafficLightController(
      uint8_t controlPinRed,
      uint8_t controlPinYellow,
      uint8_t controlPinGreen,
      int lightEffects_TickRate_InMs
    ) : SimpleTrafficLightController(
      nullptr,
      controlPinRed,
      controlPinYellow,
      controlPinGreen,
      lightEffects_TickRate_InMs
    ) {}
  
    void begin();
  
    void playEffects();
  
    TrafficLightState getState();
    void switchTo(TrafficLightState state);
  
  private:
    void _showCurrentState();
};

#endif
