#ifndef simple_mp3_player_h
#define simple_mp3_player_h

#include "Arduino.h"
#include "DFRobotDFPlayerMini.h"
#include "SoftwareSerial.h"

// ... constants

#define DEFAULT_PLAYER_PIN_RX__D4 D4
#define DEFAULT_PLAYER_PIN_TX__D3 D3
#define DEFAULT_PLAYER_VOLUME__10 10 // ... 0 to 30

class SimpleMp3Player
{
private:
  SoftwareSerial *_softwareSerial;
  DFRobotDFPlayerMini _dfPlayer;

  bool _muteStateOn = false;

public:
  SimpleMp3Player(   //
      uint8_t pinRX, //
      uint8_t pinTX  //
  );
  SimpleMp3Player(                       //
      ) : SimpleMp3Player(               //
              DEFAULT_PLAYER_PIN_RX__D4, //
              DEFAULT_PLAYER_PIN_TX__D3  //
          )
  {
  }
  ~SimpleMp3Player();

  void begin();

  void mute();
  void unmute();
  void setVolume(uint8_t volume);

  void playAnyMp3WithinFolder(uint8_t folderIdx);
  void playMp3WithinFolder(uint8_t folderIdx, uint8_t mp3FileIdx);

private:
  void _printDetailInfo(uint8_t type, int value);
};

#endif