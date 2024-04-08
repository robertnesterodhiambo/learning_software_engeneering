#include <Firmata.h>

// Pin Definitions
#define SOLAR_PANEL_PIN A0
#define BATTERY_PIN A1
#define LOAD_PIN 3 // Pin to control the load (e.g., LED as a representative)
#define LIGHT_SWITCH_PIN 4 // Pin to control the light switch

// Global Variables
float solarPanelVoltage = 0.0;
float batteryVoltage = 0.0;
float loadVoltage = 0.0;
float batteryCapacity = 12.0; // Battery capacity in volts
float batteryCharge = 0.0; // Initial battery charge
float maxBatteryCharge = 14.0; // Maximum battery charge
float minBatteryCharge = 10.0; // Minimum battery charge
bool lightsOn = false; // Initial state of lights

void setup() {
  // Initialize Firmata
  Firmata.begin();

  // Set pin modes
  pinMode(LOAD_PIN, OUTPUT);
  pinMode(LIGHT_SWITCH_PIN, INPUT_PULLUP); // Use internal pull-up resistor
}

void loop() {
  // Update Firmata to communicate with the Java application
  Firmata.processInput();

  // Simulate solar panel voltage (0-5V)
  solarPanelVoltage = analogRead(SOLAR_PANEL_PIN) * (5.0 / 1023.0);

  // Simulate battery charging/discharging
  if (solarPanelVoltage > batteryVoltage) {
    // Charge battery from solar panel
    batteryVoltage = solarPanelVoltage;
    batteryCharge = min(batteryCharge + 0.1, maxBatteryCharge); // Charging rate
  } else {
    // Discharge battery to power load and lights
    batteryVoltage = batteryCharge / batteryCapacity * 5.0; // Assuming 5V output
    batteryCharge = max(batteryCharge - 0.1, minBatteryCharge); // Discharging rate
  }

  // Simulate load operation (e.g., LED)
  loadVoltage = batteryCharge / batteryCapacity * 5.0; // Assuming 5V output
  analogWrite(LOAD_PIN, map(loadVoltage, 0, 5, 0, 255)); // Adjust brightness based on battery charge

  // Check light switch state
  if (digitalRead(LIGHT_SWITCH_PIN) == LOW) {
    // If switch is pressed, toggle lights
    lightsOn = !lightsOn;
  }

  // Control lights
  if (lightsOn && batteryCharge > minBatteryCharge) {
    // Turn lights on if switch is pressed and battery has sufficient charge
    digitalWrite(LOAD_PIN, HIGH);
  } else {
    // Turn lights off if switch is not pressed or battery is low
    digitalWrite(LOAD_PIN, LOW);
  }
}
