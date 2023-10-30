DEFAULT_EMULATOR_DEVICE = Pixel_3a_XL_API_32
run-emulator:
	make build
ifeq ($(strip $(device)),)
	nohup emulator -avd ${DEFAULT_EMULATOR_DEVICE} &
else
	nohup emulator -avd $(device) &
endif
	sleep 10
	adb -e install app/build/outputs/apk/debug/app-debug.apk
	adb shell am start -n "com.tsdc_vynils_app.app/com.tsdc_vynils_app.app.MainActivity" -a android.intent.action.MAIN -c android.intent.category.LAUNCHER

run-device:
	make build-app
	sleep 10
	adb shell am start -n "com.tsdc_vynils_app.app/com.tsdc_vynils_app.app.MainActivity" -a android.intent.action.MAIN -c android.intent.category.LAUNCHER --splashscreen-show-icon 

list-emulator:
	emulator -list-avds

build-app:
	./gradlew :app:clean
	./gradlew assembleDebug
	./gradlew installDebug

test:
	./gradlew test

build-ci:
	./gradlew :app:clean
	./gradlew assembleDebug