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
    udid = "FUFQL77T4HLJRCJJ",
    platformName = "Android",
    platformVersion = "14",
    automationName = "uiautomator2",

    #appPackage = "com.zhiliaoapp.musically",
    #appActivity = "com.ss.android.ugc.aweme.main.MainActivity",
    #noReset = True
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
    
    def test_learning_tap(self) -> None:
        try:
            # Initialize ActionBuilder
            giftPanel_e = self.wait.until(EC.presence_of_element_located((AppiumBy.ID, 'com.zhiliaoapp.musically:id/gift_panel_list')))
            # Locate all child elements inside the gift panel
            gift_elements = giftPanel_e.find_elements(AppiumBy.XPATH, ".//*")  # This finds all child elements

            # Print out each element in the list
            for index, gift in enumerate(gift_elements):
                if gift.text != "" and not gift.text.isdigit():
                    print(f"Element {index+1}: {gift.text}")
            actions = ActionChains(self.driver)
            send = self.wait.until(EC.presence_of_element_located((AppiumBy.ANDROID_UIAUTOMATOR, 'new UiSelector().resourceId("com.zhiliaoapp.musically:id/item_send_btn")')))
            location = send.location
            size = send.size
            #print(location['x'], location['y'], size["width"])
            centerX = location['x'] + (size["width"]/2)
            centerY = location['y'] + (size["height"]/2)

            # Create an ActionChains object to perform the actions
            actions = ActionChains(self.driver)

            # Long press
            # actions.w3c_actions.pointer_action.move_to_location(centerX, centerY)
            # actions.w3c_actions.pointer_action.pointer_down()
            # actions.w3c_actions.pointer_action.pointer_up()
            # actions.w3c_actions.pointer_action.pause(0.2)  # pause for 100 ms
            # actions.w3c_actions.pointer_action.pointer_down()
            # actions.w3c_actions.pointer_action.pause(4)
            # actions.w3c_actions.pointer_action.pointer_up()
            # actions.perform()

            # Swap and print
            for i in range(10):
                actions.w3c_actions.pointer_action.move_to_location(centerX, centerY)
                actions.w3c_actions.pointer_action.pointer_down()
                actions.w3c_actions.pointer_action.pause(0.2)  # pause for 100 ms
                actions.w3c_actions.pointer_action.move_to_location(centerX, centerY-300)
                actions.w3c_actions.pointer_action.pointer_up()
                actions.perform()

                # Initialize ActionBuilder
                giftPanel_e = self.wait.until(EC.presence_of_element_located((AppiumBy.ID, 'com.zhiliaoapp.musically:id/gift_panel_list')))
                # Locate all child elements inside the gift panel
                gift_elements = giftPanel_e.find_elements(AppiumBy.XPATH, ".//*")  # This finds all child elements
                # Print out each element in the list
                for index, gift in enumerate(gift_elements):
                    if gift.text != "" and not gift.text.isdigit():
                        print(f"Element {index+1}: {gift.text}")
            
            
            #openLive_e.click()
            time.sleep(5)
        except TimeoutException:
            print("Cannot Tap on screen")



if __name__ == '__main__':
    unittest.main()
