@echo off
set path=D:\WebDev\AndroidSDK\platform-tools;%PATH%

adb shell am broadcast -a android.intent.action.BOOT_COMPLETED -c android.intent.category.HOME -n com.kattanweb.android.shut/.BootCompleteReceiver
rem adb shell am broadcast -a android.net.conn.CONNECTIVITY_CHANGE -c android.intent.category.HOME -n android.romstats/.ReportingServiceManager
pause