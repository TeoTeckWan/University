import unittest
from appium import webdriver
from appium.webdriver.common.appiumby import AppiumBy
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.common.exceptions import TimeoutException
from appium.options.android import UiAutomator2Options
import time
from selenium.webdriver import ActionChains
import cv2
import numpy as np
import io
from PIL import Image
import base64

def capture_screenshot(driver, filename):
    screenshot = driver.get_screenshot_as_png()
    image = Image.open(io.BytesIO(screenshot))
    image.save(filename)
    return image

def compare_images_normal(base_image_path, test_image_path, threshold=0.95):
    base_image = cv2.imread(base_image_path)
    test_image = cv2.imread(test_image_path)
    
    # Convert images to grayscale
    base_gray = cv2.cvtColor(base_image, cv2.COLOR_BGR2GRAY)
    test_gray = cv2.cvtColor(test_image, cv2.COLOR_BGR2GRAY)
    
    # Compare images
    result = cv2.matchTemplate(test_gray, base_gray, cv2.TM_CCOEFF_NORMED)
    similarity = np.max(result)
    
    return similarity >= threshold

def capture_and_compare(driver, screenshot, base_image_path, similarity_threshold=0.9):
    # Find the element
    #element = driver.find_element(AppiumBy.ID, element_id)
    
    # Capture screenshot of the element
    #screenshot = element.screenshot_as_base64
    
    # Compare with base image
    with open(base_image_path, "rb") as image_file:
        base_image = base64.b64encode(image_file.read()).decode('utf-8')
    
    # Convert the PNG image to a Base64 string
    with open(screenshot, "rb") as image_file:
        real_image = base64.b64encode(image_file.read()).decode('utf-8')
    
    # Compare images using Appium's built-in method
    result = driver.execute_script('mobile: compareImages', {
        'mode': 'matchFeatures',
        'firstImage': base_image,
        'secondImage': real_image,
        'options': {
            #'threshold': similarity_threshold
            'visualThreshold': 0.1
        }
    })
    
    return result['score'], result['visualization']

capabilities = dict(
    deviceName = "Xiaomi 12T",
    udid = "FUFQL77T4HLJRCJJ",
    platformName = "Android",
    platformVersion = "14",
    automationName = "uiautomator2",
    enableImageElementsComparison = True
    #appPackage = "com.zhiliaoapp.musically",
    #appActivity = "com.ss.android.ugc.aweme.main.MainActivity",
    #noReset = True
)

appium_server_url = 'http://localhost:4724'

baseImage = "C:/Users/Teo Teck Wan/Desktop/Automation/Appium-Project/base/livepage.jpg"
captureImage = "C:/Users/Teo Teck Wan/Desktop/Automation/Appium-Project/check/livescreen-1.jpg"

class TestAppium(unittest.TestCase):
    # Set up driver
    def setUp(self) -> None:
        self.driver = webdriver.Remote(appium_server_url, options=UiAutomator2Options().load_capabilities(capabilities))
        self.wait = WebDriverWait(self.driver, 5)

    # Quit driver
    def tearDown(self) -> None:
        if self.driver:
            self.driver.quit()
    
    def test_validate(self) -> None:
        try:
            # Navigate to the screen you want to test
            #self.driver.find_element(AppiumBy.ID, 'some_button_id').click()
            
            # Capture screenshot
            test_screenshot = capture_screenshot(self.driver, 'test_screenshot2.png')
            
            # Compare with base image
            base_image_path = "C:/Users/Teo Teck Wan/Desktop/Automation/Appium-Project/base/livepage.jpg"
            test_image_path = 'test_screenshot2.png'
            
            similarity, diff_image = capture_and_compare(self.driver,test_image_path, base_image_path)
            
            print(f"Similarity: {similarity}")
            assert similarity >= 0.9, f"The UI does not match the expected base image. Similarity: {similarity}"
            
            # Optionally save the diff image
            if similarity < 1.0:
                with open("diff_image.png", "wb") as fh:
                    fh.write(base64.b64decode(diff_image))
            
        except TimeoutException:
            print("Cannot validate")



if __name__ == '__main__':
    unittest.main()
