# TaskNews

This is an breath explanation of the task

## Installation
Clone this repository and import into **Android Studio**
```bash
git clone git@github.com:ZaraTunyan/TaskNews.git
```

## Build variants
Use the Android Studio *Build Variants* button to choose between debug and release build types. Release configuration are not seted yet so use debug version for testing


## Installing APK
From Android Studio:
1. Check "edit configuration" so that the app module is selected
2. Run emulator or connect device to computer
3. Click on run section -> run 'app'

## Architecture
This project used:
* MVVM architecture by following MVI best practice principles 

## Run Test
1. To run JUnit test use -- gradlew test command 
2. UI Expresso Testing  -- gradlew connectedAndroidTest command
 
