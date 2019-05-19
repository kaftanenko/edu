#include "simple-traffic-light-controller.h"

void SimpleTrafficLightController::begin() {

  if (_mcp == nullptr) {
    
    pinMode(_controlPinRed, OUTPUT);
    pinMode(_controlPinYellow, OUTPUT);
    pinMode(_controlPinGreen, OUTPUT);
  } else {
    _mcp->pinMode(_controlPinRed, OUTPUT);
    _mcp->pinMode(_controlPinYellow, OUTPUT);
    _mcp->pinMode(_controlPinGreen, OUTPUT);
  }
}

void SimpleTrafficLightController::playEffects() {
  
  switch (_currentState) {
    case TRAFFIC_LIGHT_OFF:
    case TRAFFIC_LIGHT_RED:
    case TRAFFIC_LIGHT_YELLOW:
    case TRAFFIC_LIGHT_GREEN:
      _lightEffects_CurrentState = LIGHT_ON;
      break;
    case TRAFFIC_LIGHT_RED_CHANGING:
    case TRAFFIC_LIGHT_YELLOW_CHANGING:
    case TRAFFIC_LIGHT_GREEN_CHANGING:
      if (_lightEffects_Timer.isOver(_lightEffects_TickRate_InMs)) {
        _lightEffects_CurrentState = _lightEffects_CurrentState == LIGHT_ON ? LIGHT_OFF : LIGHT_ON;
        _lightEffects_Timer.reset();
      }
      break;
  }
  
  _showCurrentState();
}

TrafficLightState SimpleTrafficLightController::getState() {
  return _currentState;
}

void SimpleTrafficLightController::switchTo(TrafficLightState state) {

  _currentState = state;
  
  _lightEffects_Timer.reset();
  _lightEffects_CurrentState = LIGHT_ON;

  _showCurrentState();
}

void SimpleTrafficLightController::_showCurrentState() {

  if (_mcp == nullptr) {
    
    switch (_currentState) {
      case TRAFFIC_LIGHT_OFF:
        digitalWrite(_controlPinRed, LOW);
        digitalWrite(_controlPinYellow, LOW);
        digitalWrite(_controlPinGreen, LOW);
        break;
      case TRAFFIC_LIGHT_RED:
      case TRAFFIC_LIGHT_RED_CHANGING:
        digitalWrite(_controlPinRed, _lightEffects_CurrentState == LIGHT_ON ? HIGH : LOW);
        digitalWrite(_controlPinYellow, LOW);
        digitalWrite(_controlPinGreen, LOW);
        break;
      case TRAFFIC_LIGHT_YELLOW:
      case TRAFFIC_LIGHT_YELLOW_CHANGING:
        digitalWrite(_controlPinRed, LOW);
        digitalWrite(_controlPinYellow, _lightEffects_CurrentState == LIGHT_ON ? HIGH : LOW);
        digitalWrite(_controlPinGreen, LOW);
        break;
      case TRAFFIC_LIGHT_GREEN:
      case TRAFFIC_LIGHT_GREEN_CHANGING:
        digitalWrite(_controlPinRed, LOW);
        digitalWrite(_controlPinYellow, LOW);
        digitalWrite(_controlPinGreen, _lightEffects_CurrentState == LIGHT_ON ? HIGH : LOW);
        break;
    }
  } else {
    switch (_currentState) {
      case TRAFFIC_LIGHT_OFF:
        _mcp->digitalWrite(_controlPinRed, LOW);
        _mcp->digitalWrite(_controlPinYellow, LOW);
        _mcp->digitalWrite(_controlPinGreen, LOW);
        break;
      case TRAFFIC_LIGHT_RED:
      case TRAFFIC_LIGHT_RED_CHANGING:
        _mcp->digitalWrite(_controlPinRed, _lightEffects_CurrentState == LIGHT_ON ? HIGH : LOW);
        _mcp->digitalWrite(_controlPinYellow, LOW);
        _mcp->digitalWrite(_controlPinGreen, LOW);
        break;
      case TRAFFIC_LIGHT_YELLOW:
      case TRAFFIC_LIGHT_YELLOW_CHANGING:
        _mcp->digitalWrite(_controlPinRed, LOW);
        _mcp->digitalWrite(_controlPinYellow, _lightEffects_CurrentState == LIGHT_ON ? HIGH : LOW);
        _mcp->digitalWrite(_controlPinGreen, LOW);
        break;
      case TRAFFIC_LIGHT_GREEN:
      case TRAFFIC_LIGHT_GREEN_CHANGING:
        _mcp->digitalWrite(_controlPinRed, LOW);
        _mcp->digitalWrite(_controlPinYellow, LOW);
        _mcp->digitalWrite(_controlPinGreen, _lightEffects_CurrentState == LIGHT_ON ? HIGH : LOW);
        break;
    }
  }
}

