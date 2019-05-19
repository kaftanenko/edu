#include "data_alarm-level.h"
#include "simple-mp3-player.h"

// ... configuration: MP3_PLAYER

#define MP3_PLAYER__PIN_RX__D4 D4
#define MP3_PLAYER__PIN_TX__D3 D3

SimpleMp3Player outputMp3Player(

  MP3_PLAYER__PIN_RX__D4, 
  MP3_PLAYER__PIN_TX__D3
);

SimpleTimer currentAlarmLevel_Remembering_Timer;

// ... business methods

void outputMp3Player_Setup() {

  //pinMode(PLUS_MINUS_SWITCH__INC__INT_PIN__D5, INPUT_PULLUP);
  //pinMode(PLUS_MINUS_SWITCH__DEC__INT_PIN__D6, INPUT_PULLUP);

  attachInterrupt(
    digitalPinToInterrupt(PLUS_MINUS_SWITCH__DEC__INT_PIN__D6), 
    decCallback, 
    FALLING
  );
  attachInterrupt(
    digitalPinToInterrupt(PLUS_MINUS_SWITCH__INC__INT_PIN__D5), 
    incCallback, 
    FALLING
  );
}

// ...

int _getFolderIndexFor(AlarmLevel alarmLevel);

void outputMp3Player_OnChange_AlarmLevevel(
  AlarmLevel fromAlarmLevel, 
  AlarmLevel toAlarmLevel
) {

  int folderIdx = _getFolderIndexFor(fromAlarmLevel) * 10 + _getFolderIndexFor(toAlarmLevel);
  outputMp3Player.playAnyMp3WithinFolder(folderIdx);
}

void outputMp3Player_PlayEffects(
  AlarmLevel alarmLevel, 
  long rememberingTaktInMs
) {

  if (currentAlarmLevel_Remembering_Timer.isOver(rememberingTaktInMs)) {

    currentAlarmLevel_Remembering_Timer.reset();
    // Serial.println("Remember about current state: " + currentAlarmLevelConfig->_alarmLevelName + " (each " + currentAlarmLevelConfig->_rememberingTaktInMs + " ms)");

    int folderIdx = _getFolderIndexFor(alarmLevel);
    outputMp3Player.playAnyMp3WithinFolder(folderIdx);
  }
}

// ... helper methods

int _getFolderIndexFor(AlarmLevel alarmLevel) {

  int folderIdx = alarmLevel % 3 + 1;
  return folderIdx;
}
