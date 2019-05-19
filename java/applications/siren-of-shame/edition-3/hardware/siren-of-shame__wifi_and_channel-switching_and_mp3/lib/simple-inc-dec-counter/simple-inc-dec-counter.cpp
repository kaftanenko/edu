#include "simple-inc-dec-counter.h"

void SimplePlusMinusCounter::incValue()
{
  if (!_updateStop)
  {
    _updateStop = true;

    if (millis() - _lastStateChangeOnInMs >= _minStateChangeDelayInMs)
    {
      _lastStateChangeOnInMs = millis();
      _currentValue = _normalize(_currentValue + _stepValue);

      _logDebug("++: " + _currentValue);
    }
    _updateStop = false;
  }
}

void SimplePlusMinusCounter::decValue()
{
  if (!_updateStop)
  {
    _updateStop = true;
    if (millis() - _lastStateChangeOnInMs >= _minStateChangeDelayInMs)
    {

      _lastStateChangeOnInMs = millis();
      _currentValue = _normalize(_currentValue - _stepValue);

      _logDebug("--: " + _currentValue);
    }
    _updateStop = false;
  }
}

uint8_t SimplePlusMinusCounter::getValue()
{
  return _currentValue;
}

void SimplePlusMinusCounter::setValue(uint8_t value)
{
  if (!_updateStop)
  {
    _updateStop = true;

    _currentValue = _normalize(value);
    _logDebug("=: " + _currentValue);

    _updateStop = false;
  }
}

void SimplePlusMinusCounter::setRange(uint8_t min, uint8_t max, uint8_t step)
{
  _minValue = min;
  _maxValue = max;
  _stepValue = step;

  _currentValue = min;
}

void SimplePlusMinusCounter::setStateChangeDelayInMs(uint8_t delayInMs)
{
  _minStateChangeDelayInMs = delayInMs;
}

int SimplePlusMinusCounter::_normalize(uint8_t value)
{
  if (value < _minValue)
  {
    return _maxValue;
  }
  else if (value > _maxValue)
  {
    return _minValue;
  }
  else
  {
    return value;
  }
}

void SimplePlusMinusCounter::_logDebug(const String &value)
{
  if (_debugModeOn)
  {
    Serial.println(value);
  }
}