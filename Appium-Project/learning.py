import unittest
from appium import webdriver
from appium.webdriver.common.appiumby import AppiumBy
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.common.exceptions import TimeoutException
from appium.options.android import UiAutomator2Options
import time
from selenium.webdriver import ActionChains


capabilities = dict(
    deviceName = "Xiaomi 12T",
    udid = "192.168.12.14:5555",
    platformName = "Android",
    platformVersion = "14",
    automationName = "uiautomator2",

    appPackage = "com.zhiliaoapp.musically",
    appActivity = "com.ss.android.ugc.aweme.main.MainActivity",
    noReset = True
)

appium_server_url = 'http://localhost:4724'

class TestAppium(unittest.TestCase):
    # Set up driver
    def setUp(self) -> None:
        self.driver = webdriver.Remote(appium_server_url, options=UiAutomator2Options().load_capabilities(capabilities))
        self.wait = WebDriverWait(self.driver, 5)

    # Quit driver
    def tearDown(self) -> None:
        if self.driver:
            self.driver.quit()

    # def test_learning_open(self) -> None:
    #     try:
    #         allowContent_e = WebDriverWait(self.driver, 5).until(
    #             EC.presence_of_element_located((AppiumBy.ANDROID_UIAUTOMATOR, 'new UiSelector().resourceId("com.android.permissioncontroller:id/permission_deny_and_dont_ask_again_button")')))
    #         allowContent_e.click()
    #         print("Don't allow Tiktok-M to access your contacts?")
    #     except TimeoutException:
    #         print("No found the access your contacts")
    #         #self.fail("Error.")
        
    #     print("1. Open Tiktok Testing Done")
    #     time.sleep(5)
    
    def test_learning_tap(self) -> None:
        try:
            screen_size = self.driver.get_window_size()
            # Extract width and height
            width = screen_size['width']
            height = screen_size['height']

            print(f"Screen width: {width} pixels")
            print(f"Screen height: {height} pixels")
            #time.sleep(1)
            allowContent_e = self.wait.until(EC.presence_of_element_located((AppiumBy.XPATH, '//android.widget.Button[@content-desc="Create"]/android.widget.ImageView')))
            allowContent_e.click()
            time.sleep(1)
            actions = ActionChains(self.driver)
            actions.w3c_actions.pointer_action.move_to_location((width/2), height - 300)
            actions.w3c_actions.pointer_action.pointer_down()
            actions.w3c_actions.pointer_action.pause(0.2)  # pause for 100 ms
            actions.w3c_actions.pointer_action.move_to_location(10, height - 300)
            actions.w3c_actions.pointer_action.pointer_up()
            actions.w3c_actions.pointer_action.pause(2)
            actions.w3c_actions.pointer_action.move_to_location((width/2), height - 500)
            actions.w3c_actions.pointer_action.pointer_down()
            actions.w3c_actions.pointer_action.pointer_up()
            actions.perform()
            
            #openLive_e.click()
            time.sleep(5)
        except TimeoutException:
            print("Cannot Tap on screen")



if __name__ == '__main__':
    unittest.main()
