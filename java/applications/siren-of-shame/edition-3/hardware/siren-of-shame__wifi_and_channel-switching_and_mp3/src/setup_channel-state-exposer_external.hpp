#include <Arduino.h>

#include "data_alarm-level.hpp"
#include "simple-traffic-light-controller.h"

// ... configuration

#define _OUTPUT_CONTROL__PIN__GREEN___D0 D0
#define _OUTPUT_CONTROL__PIN__YELLOW__D7 D7
#define _OUTPUT_CONTROL__PIN__RED_____D8 D8

// ... business class

class ChannelStateExposerExternal
{

private:
  SimpleTrafficLightController _simpleTrafficLightController;

public:
  ChannelStateExposerExternal(           //
      uint8_t controlPinRed,             //
      uint8_t controlPinYellow,          //
      uint8_t controlPinGreen            //
      ) : _simpleTrafficLightController( //
              controlPinRed,             //
              controlPinYellow,          //
              controlPinGreen            //
          ){};
  ChannelStateExposerExternal(                  //
      ) : ChannelStateExposerExternal(          //
              _OUTPUT_CONTROL__PIN__RED_____D8, //
              _OUTPUT_CONTROL__PIN__YELLOW__D7, //
              _OUTPUT_CONTROL__PIN__GREEN___D0  //
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

void ChannelStateExposerExternal::setup()
{
  _simpleTrafficLightController.begin();
}

// ...

void ChannelStateExposerExternal::playEffects()
{
  _simpleTrafficLightController.playEffects();
}

void ChannelStateExposerExternal::exposeAlarmLevel(
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

void ChannelStateExposerExternal::exposeAlarmLevelChange(
    AlarmLevel fromAlarmLevel,  //
    AlarmLevel toAlarmLevel,    //
    uint16_t rememberingTaktInMs //
)
{
  exposeAlarmLevel(toAlarmLevel, rememberingTaktInMs);
}
