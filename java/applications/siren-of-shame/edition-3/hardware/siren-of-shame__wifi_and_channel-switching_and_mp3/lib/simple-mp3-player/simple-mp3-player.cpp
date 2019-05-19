#include "simple-mp3-player.h"

// ... constructors

SimpleMp3Player::SimpleMp3Player(uint8_t pinRX, uint8_t pinTX)
{
  _softwareSerial = new SoftwareSerial(pinRX, pinTX);
}

SimpleMp3Player::~SimpleMp3Player()
{
  delete _softwareSerial;
}

// ... buiness methods

void SimpleMp3Player::begin()
{
  _softwareSerial->begin(9600);

  Serial.println("[simple-mp3-player] ... initializing (May take 3~5 seconds)");

  if (!_dfPlayer.begin(*_softwareSerial))
  {
    Serial.println("  Unable to begin:");
    Serial.println("  1.Please recheck the connection!");
    Serial.println("  2.Please insert the SD card!");
  }
  else
  {
    Serial.println("[simple-mp3-player] ... is online.");
    _dfPlayer.volume(DEFAULT_PLAYER_VOLUME__10); // ... 0 to 30
  }
}

void SimpleMp3Player::mute()
{
  Serial.println("[simple-mp3-player] ... muted.");
  _muteStateOn = true;
}

void SimpleMp3Player::unmute()
{
  Serial.println("[simple-mp3-player] ... unmuted.");
  _muteStateOn = false;
}

void SimpleMp3Player::setVolume(uint8_t volume)
{
  Serial.printf("[simple-mp3-player] ... volume set to: %d.\n", volume);
  _dfPlayer.volume(volume); // ... 0 to 30
}

void SimpleMp3Player::playAnyMp3WithinFolder(uint8_t folderIdx)
{
  Serial.printf("[simple-mp3-player] ... play any melody from the folder '%d'.\n", folderIdx);

  int fileCounts = _dfPlayer.readFileCountsInFolder(folderIdx);

  if (fileCounts > 0)
  {
    int mp3FileIdx = random(1, fileCounts);
    playMp3WithinFolder(folderIdx, mp3FileIdx);
  }
  else
  {
    Serial.printf("[simple-mp3-player] ... no files found within the folder '%d'.\n", folderIdx);
  }
}

void SimpleMp3Player::playMp3WithinFolder(uint8_t folderIdx, uint8_t mp3FileIdx)
{
  if (!_muteStateOn)
  {
    Serial.printf("[simple-mp3-player] ... play melody '%d' within the folder '%d'.\n", mp3FileIdx, folderIdx);
    _dfPlayer.playFolder(folderIdx, mp3FileIdx);
  }
}

void SimpleMp3Player::_printDetailInfo(uint8_t type, int value)
{
  switch (type)
  {
  case TimeOut:
    Serial.println(F("Time Out!"));
    break;
  case WrongStack:
    Serial.println(F("Stack Wrong!"));
    break;
  case DFPlayerCardInserted:
    Serial.println(F("Card Inserted!"));
    break;
  case DFPlayerCardRemoved:
    Serial.println(F("Card Removed!"));
    break;
  case DFPlayerCardOnline:
    Serial.println(F("Card Online!"));
    break;
  case DFPlayerPlayFinished:
    Serial.print(F("Number:"));
    Serial.print("" + value);
    Serial.println(F(" Play Finished!"));
    break;
  case DFPlayerError:
    Serial.print(F("DFPlayerError:"));
    switch (value)
    {
    case Busy:
      Serial.println(F("Card not found"));
      break;
    case Sleeping:
      Serial.println(F("Sleeping"));
      break;
    case SerialWrongStack:
      Serial.println(F("Get Wrong Stack"));
      break;
    case CheckSumNotMatch:
      Serial.println(F("Check Sum Not Match"));
      break;
    case FileIndexOut:
      Serial.println(F("File Index Out of Bound"));
      break;
    case FileMismatch:
      Serial.println(F("Cannot Find File"));
      break;
    case Advertise:
      Serial.println(F("In Advertise"));
      break;
    default:
      break;
    }
    break;
  default:
    break;
  }
}
