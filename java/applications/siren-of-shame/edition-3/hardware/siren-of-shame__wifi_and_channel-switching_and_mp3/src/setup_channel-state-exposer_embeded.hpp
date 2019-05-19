#include <Arduino.h>
#include <Adafruit_MCP23017.h>

#include "data_alarm-level.hpp"
#include "simple-traffic-light-controller.h"

// ... configuration

#define _OUTPUT_CONTROL__MCP_PIN__GREEN___5 5
#define _OUTPUT_CONTROL__MCP_PIN__YELLOW__6 6
#define _OUTPUT_CONTROL__MCP_PIN__RED_____7 7

// ... business class

class ChannelStateExposerEmbeded
{

private:
  SimpleTrafficLightController _simpleTrafficLightController;

public:
  ChannelStateExposerEmbeded(
      Adafruit_MCP23017 *mcp,
      uint8_t controlPinRed,
      uint8_t controlPinYellow,
      uint8_t controlPinGreen            //
      ) : _simpleTrafficLightController( //
              mcp,                       //
              controlPinRed,             //
              controlPinYellow,          //
              controlPinGreen            //
          ){};
  ChannelStateExposerEmbeded(                      //
      Adafruit_MCP23017 *mcp                       //
      ) : ChannelStateExposerEmbeded(              //
              mcp,                                 //
              _OUTPUT_CONTROL__MCP_PIN__RED_____7, //
              _OUTPUT_CONTROL__MCP_PIN__YELLOW__6, //
              _OUTPUT_CONTROL__MCP_PIN__GREEN___5  //
          ){};

  void setup();

  void playEffects();

  void exposeAlarmLevel(
      AlarmLevel newAlarmLevel,   //
      uint16_t rememberingTaktInMs //
  );
  void exposeAlarmLevelChange(
      AlarmLevel fromAlarmLevel,  //
      AlarmLevel toAlarmLevel,    //
      uint16_t rememberingTaktInMs //
  );
};

// ... business methods

void ChannelStateExposerEmbeded::setup()
{
  _simpleTrafficLightController.begin();
}

// ...

void ChannelStateExposerEmbeded::playEffects()
{
  _simpleTrafficLightController.playEffects();
}

void ChannelStateExposerEmbeded::exposeAlarmLevel(
    AlarmLevel newAlarmLevel,   //
    uint16_t rememberingTaktInMs //
)
{
  TrafficLightState newTrafficLightState;

  switch (newAlarmLevel)
  {
  case ALARM_LEVEL_RED:
  case ALARM_LEVEL_RED_EXPECTING_UPDATE:
    newTrafficLightState = TRAFFIC_LIGHT_RED;
    break;
  case ALARM_LEVEL_YELLOW:
  case ALARM_LEVEL_YELLOW_EXPECTING_UPDATE:
    newTrafficLightState = TRAFFIC_LIGHT_YELLOW;
    break;
  case ALARM_LEVEL_GREENBLUE:
  case ALARM_LEVEL_GREENBLUE_EXPECTING_UPDATE:
    newTrafficLightState = TRAFFIC_LIGHT_GREEN;
    break;
  default:
    newTrafficLightState = TRAFFIC_LIGHT_OFF;
  }

  _simpleTrafficLightController.switchToState(newTrafficLightState, rememberingTaktInMs);
}

void ChannelStateExposerEmbeded::exposeAlarmLevelChange(
    AlarmLevel fromAlarmLevel,  //
    AlarmLevel toAlarmLevel,    //
    uint16_t rememberingTaktInMs //
)
{
  exposeAlarmLevel(toAlarmLevel, rememberingTaktInMs);
}