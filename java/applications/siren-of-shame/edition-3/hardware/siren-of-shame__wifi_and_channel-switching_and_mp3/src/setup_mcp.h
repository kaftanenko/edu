#include "Adafruit_MCP23017.h"

// ... configuration: PORTS_EXPANDER_MCP

#define MCP__CONTROL_PIN__UNKNOWN ...

Adafruit_MCP23017 mcp;

// ... business methods

void mcp_Setup() {
  
  mcp.begin();
}
