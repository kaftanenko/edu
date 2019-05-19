#ifndef simple_mp3_player_h
#define simple_mp3_player_h

#include "Arduino.h"
#include "DFRobotDFPlayerMini.h"
#include "SoftwareSerial.h"

// ... constants

#define DEFAULT_PLAYER_PIN_RX__D4 D4
#define DEFAULT_PLAYER_PIN_TX__D3 D3
#define DEFAULT_PLAYER_VOLUME__15 15 // ... 0 to 30

class SimpleMp3Player {

  bool _muteStateOn;
  SoftwareSerial* _softwareSerial;
  DFRobotDFPlayerMini _dfPlayer;

  public:

    SimpleMp3Player(int pinRX, int pinTX);
    SimpleMp3Player() : 
      SimpleMp3Player(DEFAULT_PLAYER_PIN_RX__D4, DEFAULT_PLAYER_PIN_TX__D3) {}
    ~SimpleMp3Player();

    void mute();
    void unmute();
    void setVolume(int volume);

    void playAnyMp3WithinFolder(int folderIdx);
    void playMp3WithinFolder(int folderIdx, int mp3FileIdx);

  private:
    void printDetailInfo(uint8_t type, int value);
};

#endif