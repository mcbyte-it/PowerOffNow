@echo off
set path=D:\WebDev\AndroidSDK\platform-tools;%PATH%

adb shell am broadcast -a android.intent.action.BOOT_COMPLETED -c android.intent.category.HOME -n com.kattanweb.android.shut/.BootCompleteReceiver
pause