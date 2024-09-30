import unittest
from appium import webdriver
from appium.webdriver.common.appiumby import AppiumBy
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.common.exceptions import TimeoutException
from appium.options.android import UiAutomator2Options
import time
from selenium.webdriver import ActionChains
#import desired_caps


capabilities = dict(
    deviceName = "Xiaomi 12T",
    udid = "AJ3D022429002329",
    platformName = "Android",
    platformVersion = "14",
    automationName = "uiautomator2",

    #appPackage = "com.zhiliaoapp.musically",
    #appActivity = "com.ss.android.ugc.aweme.main.MainActivity",
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
    
    def test_learning_tap(self) -> None:
        try:

            giftPanel_e = self.wait.until(EC.presence_of_element_located((AppiumBy.ID, 'com.zhiliaoapp.musically:id/gift_panel_list')))
            #print(giftPanel_e.text)
            self.driver.execute_script('gesture: scrollElementIntoView',
                      {'scrollableView': giftPanel_e.id, 'strategy': 'id', 'selector': giftPanel_e,
                       'percentage': 50, 'direction': 'up', 'maxCount': 3})
            
            #print(giftPanel_e.text)


            # self.driver.execute_script('gesture: scrollElementIntoView',
            #                     {'scrollableView': list_view.id, 'strategy': 'accessibility id', 'selector': 'Picker',
            #                     'percentage': 50, 'direction': 'up', 'maxCount': 3})
            time.sleep(5)
            profile_e = self.wait.until(EC.presence_of_element_located((AppiumBy.ANDROID_UIAUTOMATOR, 'new UiSelector().text("Profile")')))
            profile_e.click()

            account_e = self.wait.until(EC.presence_of_element_located((AppiumBy.ANDROID_UIAUTOMATOR, 'new UiSelector().resourceId("com.zhiliaoapp.musically:id/jlp")')))
            account_e.click()

            # Locate all buttons inside the RecyclerView
            buttons = self.driver.find_elements(AppiumBy.XPATH, "//androidx.recyclerview.widget.RecyclerView//android.widget.Button")

            # Iterate through each button and retrieve the 'content-desc' attribute
            for button in buttons:
                content_desc = button.get_attribute("contentDescription")  # or use "content-desc"
                #if content_desc and content_desc != "Add account":  # Check if the content-desc is not empty
                    #previousLogin[]


            #print(previousLogin)

            # addAccount_e = self.wait.until(EC.presence_of_element_located((AppiumBy.ANDROID_UIAUTOMATOR, 'new UiSelector().text("Add account")')))
            # addAccount_e.click()

            # region_e = self.wait.until(EC.presence_of_element_located((AppiumBy.ANDROID_UIAUTOMATOR, 'new UiSelector().resourceId("com.zhiliaoapp.musically:id/cw0")')))
            # region = region_e.text
            #if region == "+86":


        #     # Create an ActionChains object to perform the actions
        #     actions = ActionChains(self.driver)
        #     #openLive_e = self.wait.until(EC.presence_of_element_located((AppiumBy.ANDROID_UIAUTOMATOR, 'new UiSelector().className("android.widget.ImageView").instance(13)')))
            
        #     #location = openLive_e.location
        #     #x = location['x']
        #    # y = location['y']
        #    # size = openLive_e.size
        #    # print("x: ", x, "     y: ", y, "            size: ", size)
            
        #     actions.w3c_actions.pointer_action.move_to_location(497, 1200)
        #     actions.w3c_actions.pointer_action.pointer_down()
        #     actions.w3c_actions.pointer_action.pause(2)
        #     actions.w3c_actions.pointer_action.pointer_up()
        #     actions.perform()
            
            
            #openLive_e.click()
            time.sleep(5)
        except TimeoutException:
            print("Cannot Tap on screen")



if __name__ == '__main__':
    unittest.main()
