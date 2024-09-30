import unittest
from appium import webdriver
from appium.options.common import AppiumOptions
from appium.webdriver.common.appiumby import AppiumBy
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.common.exceptions import TimeoutException
import time


capabilities = dict(
    deviceName = "Xiaomi 12T",
    udid = "FUFQL77T4HLJRCJJ",
    platformName = "Android",
    platformVersion = "14",
    automationName = "uiautomator2",
)

appium_server_url = 'http://localhost:4724'

class TestAppium(unittest.TestCase):
    # Set up driver
    def setUp(self) -> None:
        self.driver = webdriver.Remote(appium_server_url, options=AppiumOptions().load_capabilities(capabilities))
        self.wait = WebDriverWait(self.driver, 20)

    # Quit driver
    def tearDown(self) -> None:
        if self.driver:
            self.driver.quit()

    def test_sendGift(self) -> None:
        try:
            # Open Gift Panel
            createLive_e = self.wait.until(EC.presence_of_element_located((AppiumBy.ID, 'com.zhiliaoapp.musically:id/gift_icon_image')))
            createLive_e.click()

            # Open Treasure Box
            treasureBox_e = self.wait.until(EC.presence_of_element_located((AppiumBy.ACCESSIBILITY_ID, 'Treasure Box')))
            treasureBox_e.click()

            # Select Other Options
            otherOptions_e = self.wait.until(EC.presence_of_element_located((AppiumBy.ACCESSIBILITY_ID, 'Other options')))
            otherOptions_e.click()

            # Enter Countdown Period
            countDown_e = self.wait.until(EC.presence_of_element_located((AppiumBy.XPATH, '//android.widget.FrameLayout[@resource-id="com.zhiliaoapp.musically:id/engine_container"]/android.widget.FrameLayout/android.widget.FrameLayout/com.bytedance.ies.xelement.input.LynxInputView[3]')))
            countDown_e.click()
            self.driver.set_clipboard_text("")
            self.driver.set_clipboard_text("1") # Copy
            time.sleep(1)
            self.driver.press_keycode(279) # Paste

            # Enter Quantity
            quantity_e = self.wait.until(EC.presence_of_element_located((AppiumBy.XPATH, '//android.widget.FrameLayout[@resource-id="com.zhiliaoapp.musically:id/engine_container"]/android.widget.FrameLayout/android.widget.FrameLayout/com.bytedance.ies.xelement.input.LynxInputView[2]')))
            quantity_e.click()
            time.sleep(1)
            self.driver.press_keycode(279)

            # Enter Coins
            coin_e = self.wait.until(EC.presence_of_element_located((AppiumBy.XPATH, '//android.widget.FrameLayout[@resource-id="com.zhiliaoapp.musically:id/engine_container"]/android.widget.FrameLayout/android.widget.FrameLayout/com.bytedance.ies.xelement.input.LynxInputView[1]')))
            coin_e.click()
            self.driver.set_clipboard_text("100")
            time.sleep(1)
            self.driver.press_keycode(279)

            self.driver.back() # Back button

            # Click Send Treasure Box
            sendButton_e = self.wait.until(EC.presence_of_element_located((AppiumBy.ACCESSIBILITY_ID, 'Send')))
            sendButton_e.click()

        except TimeoutException:
            self.fail("Error.")

    def test_collectTreasureBox(self) -> None:
        try:
            time.sleep(1)
        except TimeoutException:
            self.fail("Error.")

if __name__ == '__main__':
    unittest.main()
