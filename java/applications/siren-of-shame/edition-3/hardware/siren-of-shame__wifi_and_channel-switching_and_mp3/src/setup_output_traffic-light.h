#include "data_alarm-level.h"
#include "simple-traffic-light-controller.h"

// ... configuration

#define OUTPUT_CONTROL__PIN__GREEN___D0 D0
#define OUTPUT_CONTROL__PIN__YELLOW__D7 D7
#define OUTPUT_CONTROL__PIN__RED_____D8 D8

SimpleTrafficLightController simpleTrafficLightController(
  OUTPUT_CONTROL__PIN__RED_____D8,
  OUTPUT_CONTROL__PIN__YELLOW__D7,
  OUTPUT_CONTROL__PIN__GREEN___D0,
  600
);

#define OUTPUT_CONTROL__MCP_PIN__GREEN___5 5
#define OUTPUT_CONTROL__MCP_PIN__YELLOW__6 6
#define OUTPUT_CONTROL__MCP_PIN__RED_____7 7

SimpleTrafficLightController simpleTrafficLightMcpController(
  &mcp, 
  OUTPUT_CONTROL__MCP_PIN__RED_____7,
  OUTPUT_CONTROL__MCP_PIN__YELLOW__6,
  OUTPUT_CONTROL__MCP_PIN__GREEN___5,
  600
);

// ... business methods

void outputTrafficLight_Setup() {

  simpleTrafficLightController.begin();
  simpleTrafficLightMcpController.begin();
}

// ...

void outputTrafficLight_PlayEffects() {

    simpleTrafficLightController.playEffects();
    simpleTrafficLightMcpController.playEffects();
}

void outputTrafficLight_OnChange_AlarmLevevel(
  AlarmLevel fromAlarmLevel, 
  AlarmLevel toAlarmLevel,
  bool applyEffects
) {

    TrafficLightState newTrafficLightState;
  
    switch (toAlarmLevel) {
      case ALARM_LEVEL_RED:
      case ALARM_LEVEL_RED_EXPECTING_UPDATE:
        newTrafficLightState = applyEffects ? TRAFFIC_LIGHT_RED_CHANGING : TRAFFIC_LIGHT_RED;
        break;
      case ALARM_LEVEL_YELLOW:
      case ALARM_LEVEL_YELLOW_EXPECTING_UPDATE:
        newTrafficLightState = applyEffects ? TRAFFIC_LIGHT_YELLOW_CHANGING : TRAFFIC_LIGHT_YELLOW;
        break;
      case ALARM_LEVEL_GREENBLUE:
      case ALARM_LEVEL_GREENBLUE_EXPECTING_UPDATE:
        newTrafficLightState = applyEffects ? TRAFFIC_LIGHT_GREEN_CHANGING : TRAFFIC_LIGHT_GREEN;
        break;
    }
    
    simpleTrafficLightController.switchTo(newTrafficLightState);
    simpleTrafficLightMcpController.switchTo(newTrafficLightState);
}
