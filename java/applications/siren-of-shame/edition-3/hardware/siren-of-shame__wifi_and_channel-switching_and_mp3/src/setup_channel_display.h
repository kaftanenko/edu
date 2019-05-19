#include <simple-7-seg-row-over-port-expander.h>

// ... configuration: 7_SEG_CONTROLLER

#define _7_SEG_CONTROLLER__MCP_PIN__DS_____8  8 // data/DS
#define _7_SEG_CONTROLLER__MCP_PIN__STCP___9  9 // latch/STCP
#define _7_SEG_CONTROLLER__MCP_PIN__SHCP__10 10 // clock/SHCP
#define _7_SEG_CONTROLLER__SHIFT_REGS_CNT__2  2 // number of shift registers attached in series

Simple7SegRowOverPortsExpander * simple7SegController;

byte channelDisplay_CurrentValue = 1;

// ... business methods

byte channelDisplay_GetCurrentValue() {

  return channelDisplay_CurrentValue;
}

void channelDisplay_DisplayValue(byte newValue) {

  channelDisplay_CurrentValue = newValue;
  simple7SegController->displayNumber(channelDisplay_CurrentValue);
}

// ... setup method

void channelDisplay_Setup() {

  simple7SegController = new Simple7SegRowOverPortsExpander( //
    &mcp,
    _7_SEG_CONTROLLER__SHIFT_REGS_CNT__2,
    _7_SEG_CONTROLLER__MCP_PIN__DS_____8,
    _7_SEG_CONTROLLER__MCP_PIN__SHCP__10,
    _7_SEG_CONTROLLER__MCP_PIN__STCP___9
  );

  channelDisplay_DisplayValue(channelDisplay_CurrentValue);
}
