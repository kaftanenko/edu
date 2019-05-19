#include <Arduino.h>

#include "data_alarm-level.hpp"
#include "simple-mp3-player.h"
#include "simple-timer.h"

// ... configuration

#define MP3_PLAYER__PIN_RX__D4 D4
#define MP3_PLAYER__PIN_TX__D3 D3

// ... business class

class ChannelStateExposerMp3
{

private:
  SimpleMp3Player _simpleMp3Player;

  long _currentRememberingTaktInMs = 0;
  SimpleTimer _currentRememberingTimer;

  AlarmLevel _currentAlarmLevel = ALARM_LEVEL_NONE;

public:
  ChannelStateExposerMp3(   //
      int pinRX,            //
      int pinTX             //
      ) : _simpleMp3Player( //
              pinRX,        //
              pinTX         //
          ){};
  ChannelStateExposerMp3(             //
      ) : ChannelStateExposerMp3(     //
              MP3_PLAYER__PIN_RX__D4, //
              MP3_PLAYER__PIN_TX__D3  //
          ){};

  void setup();

  void exposeAlarmLevelChange(
      AlarmLevel fromAlarmLevel,   //
      AlarmLevel toAlarmLevel,     //
      uint16_t rememberingTaktInMs //
  );

  void playEffects();
  void playWellcomeMelody();

private:
  int _getFolderIndexFor(AlarmLevel alarmLevel);
};

// ... business methods

void ChannelStateExposerMp3::setup()
{
  _simpleMp3Player.begin();
  _simpleMp3Player.setVolume(15);

  _currentRememberingTimer.reset();
}

void ChannelStateExposerMp3::exposeAlarmLevelChange(
    AlarmLevel fromAlarmLevel,    //
    AlarmLevel toAlarmLevel,      //
    uint16_t rememberingTaktInSec //
)
{
  _currentAlarmLevel = toAlarmLevel;

  _currentRememberingTimer.reset();
  _currentRememberingTaktInMs = rememberingTaktInSec * 1000;

  int folderIdx = _getFolderIndexFor(fromAlarmLevel) * 10 + _getFolderIndexFor(toAlarmLevel);
  _simpleMp3Player.playAnyMp3WithinFolder(folderIdx);
}

void ChannelStateExposerMp3::playWellcomeMelody()
{
  _simpleMp3Player.playAnyMp3WithinFolder(31);
}

void ChannelStateExposerMp3::playEffects()
{
  if (_currentRememberingTaktInMs > 0 //
      && _currentRememberingTimer.isOver(_currentRememberingTaktInMs))
  {
    _currentRememberingTimer.reset();

    int folderIdx = _getFolderIndexFor(_currentAlarmLevel);
    _simpleMp3Player.playAnyMp3WithinFolder(folderIdx);
  }
}

int ChannelStateExposerMp3::_getFolderIndexFor(AlarmLevel alarmLevel)
{
  if (alarmLevel == ALARM_LEVEL_NONE)
  {
    return 0;
  }
  else
  {
    int folderIdx = alarmLevel % 3 + 1;
    return folderIdx;
  }
}
