# Stops Tracker App

## Overview

Stops Tracker is a simple Android app that allows users to track their journey stops and view
related information such as total distance covered and distance left. Users can switch between
kilometers (km) and miles (mi) for distance units.

## Features

1. **Generate Stops:** Users can input the number of stops they want to generate, creating a list of
   journey stops with random distances.
2. **Start Journey:** Initiates a new journey, resetting the progress made on previous stops.
3. **Next Stop:** Allows users to progress to the next stop in their journey.
4. **Distance Switch:** Users can toggle between kilometers and miles, affecting the display of
   distances throughout the app.
5. **Dynamic Stop List:** The app dynamically switches between a simple list and a lazy list based
   on the number of stops, optimizing the user interface.

## Implemented Components
### 1. **GenerateStopsInput:**
    - Takes user input for the number of stops to generate.

### 2. **generateStops:**
    - Generates a list of stops with random distances based on the user's input.

### 3. **StopListItem:**
    - Displays a single stop item with formatted distance based on the selected unit.

### 4. **DynamicStopList:**
    - Dynamically switches between a simple list and a lazy list, based on the number of stops.

### 5. **DistanceSwitchButton:**
    - A button allowing users to toggle between kilometers and miles, affecting the display of distances.

### 6. **StartJourneyButton and NextStopButton:**
    - Buttons to start a new journey and progress to the next stop, respectively.

### 7. **ProgressSection and ProgressBarSection:**
    - Displays the progress made, including the current stop, total distance covered, and distance left.
