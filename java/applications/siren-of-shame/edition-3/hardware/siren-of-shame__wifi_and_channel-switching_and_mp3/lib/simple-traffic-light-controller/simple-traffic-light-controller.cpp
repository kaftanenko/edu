#include "simple-traffic-light-controller.h"

void SimpleTrafficLightController::begin()
{
  if (_mcp == nullptr)
  {
    pinMode(_controlPinRed, OUTPUT);
    pinMode(_controlPinYellow, OUTPUT);
    pinMode(_controlPinGreen, OUTPUT);
  }
  else
  {
    _mcp->pinMode(_controlPinRed, OUTPUT);
    _mcp->pinMode(_controlPinYellow, OUTPUT);
    _mcp->pinMode(_controlPinGreen, OUTPUT);
  }
}

TrafficLightState SimpleTrafficLightController::getCurrentState()
{
  return _currentState;
}

void SimpleTrafficLightController::playEffects()
{
  if (_currentRememberingTaktInMs > 0 //
      && _currentRememberingTimer.isOver(_currentRememberingTaktInMs))
  {
    _currentRememberingTimer.reset();
    _currentLightState = _currentLightState == LIGHT_ON ? LIGHT_OFF : LIGHT_ON;

    _showCurrentState();
  }
}

void SimpleTrafficLightController::switchToState( //
    TrafficLightState state,                      //
    uint16_t rememberingTaktInMs                   //
)
{
  _currentState = state;
  _currentLightState = LIGHT_ON;

  _currentRememberingTimer.reset();
  _currentRememberingTaktInMs = rememberingTaktInMs;

  _showCurrentState();
}

void SimpleTrafficLightController::_showCurrentState()
{
  if (_mcp == nullptr)
  {
    switch (_currentState)
    {
    case TRAFFIC_LIGHT_OFF:
      digitalWrite(_controlPinRed, LOW);
      digitalWrite(_controlPinYellow, LOW);
      digitalWrite(_controlPinGreen, LOW);
      break;
    case TRAFFIC_LIGHT_RED:
      digitalWrite(_controlPinRed, _currentLightState == LIGHT_ON ? HIGH : LOW);
      digitalWrite(_controlPinYellow, LOW);
      digitalWrite(_controlPinGreen, LOW);
      break;
    case TRAFFIC_LIGHT_YELLOW:
      digitalWrite(_controlPinRed, LOW);
      digitalWrite(_controlPinYellow, _currentLightState == LIGHT_ON ? HIGH : LOW);
      digitalWrite(_controlPinGreen, LOW);
      break;
    case TRAFFIC_LIGHT_GREEN:
      digitalWrite(_controlPinRed, LOW);
      digitalWrite(_controlPinYellow, LOW);
      digitalWrite(_controlPinGreen, _currentLightState == LIGHT_ON ? HIGH : LOW);
      break;
    }
  }
  else
  {
    switch (_currentState)
    {
    case TRAFFIC_LIGHT_OFF:
      _mcp->digitalWrite(_controlPinRed, LOW);
      _mcp->digitalWrite(_controlPinYellow, LOW);
      _mcp->digitalWrite(_controlPinGreen, LOW);
      break;
    case TRAFFIC_LIGHT_RED:
      _mcp->digitalWrite(_controlPinRed, _currentLightState == LIGHT_ON ? HIGH : LOW);
      _mcp->digitalWrite(_controlPinYellow, LOW);
      _mcp->digitalWrite(_controlPinGreen, LOW);
      break;
    case TRAFFIC_LIGHT_YELLOW:
      _mcp->digitalWrite(_controlPinRed, LOW);
      _mcp->digitalWrite(_controlPinYellow, _currentLightState == LIGHT_ON ? HIGH : LOW);
      _mcp->digitalWrite(_controlPinGreen, LOW);
      break;
    case TRAFFIC_LIGHT_GREEN:
      _mcp->digitalWrite(_controlPinRed, LOW);
      _mcp->digitalWrite(_controlPinYellow, LOW);
      _mcp->digitalWrite(_controlPinGreen, _currentLightState == LIGHT_ON ? HIGH : LOW);
      break;
    }
  }
}
