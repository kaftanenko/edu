#ifndef data_alarm_level_h
#define data_alarm_level_h

enum AlarmLevel
{
  ALARM_LEVEL_RED,
  ALARM_LEVEL_YELLOW,
  ALARM_LEVEL_GREENBLUE,

  ALARM_LEVEL_RED_EXPECTING_UPDATE,
  ALARM_LEVEL_YELLOW_EXPECTING_UPDATE,
  ALARM_LEVEL_GREENBLUE_EXPECTING_UPDATE,

  ALARM_LEVEL_NONE,
};

AlarmLevel _getAlarmLevelByName(char* alarmLevelName)
{
  if (strcmp(alarmLevelName, "RED") == 0)
  {
    return ALARM_LEVEL_RED;
  }
  else if (strcmp(alarmLevelName, "YELLOW") == 0)
  {
    return ALARM_LEVEL_YELLOW;
  }
  else if (strcmp(alarmLevelName, "GREENBLUE") == 0)
  {
    return ALARM_LEVEL_GREENBLUE;
  }
  else if (strcmp(alarmLevelName, "RED_EXPECTING_UPDATE") == 0)
  {
    return ALARM_LEVEL_RED_EXPECTING_UPDATE;
  }
  else if (strcmp(alarmLevelName, "YELLOW_EXPECTING_UPDATE") == 0)
  {
    return ALARM_LEVEL_YELLOW_EXPECTING_UPDATE;
  }
  else if (strcmp(alarmLevelName, "GREENBLUE_EXPECTING_UPDATE") == 0)
  {
    return ALARM_LEVEL_GREENBLUE_EXPECTING_UPDATE;
  }
  else
  {
    return ALARM_LEVEL_NONE;
  }
}

#endif