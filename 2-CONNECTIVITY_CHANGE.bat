@echo off
rem adb shell am broadcast -a android.intent.action.BOOT_COMPLETED -c android.intent.category.HOME -n android.romstats/.ReportingServiceManager
adb shell am broadcast -a android.net.conn.CONNECTIVITY_CHANGE -c android.intent.category.HOME -n android.romstats/.ReportingServiceManager
pause