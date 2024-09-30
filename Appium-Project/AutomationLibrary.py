from appium import webdriver
from appium.webdriver.common.appiumby import AppiumBy
from selenium.webdriver.support.ui import WebDriverWait
from appium.options.android import UiAutomator2Options
from selenium.webdriver import ActionChains
import time
import warnings

# Set up capabilities and url for the connection
# Input:  device (Str), id (Str), platform (Str), version (Str), driver (Str), package (Str), activity (Str)
# Output: capabilities (Dict), url (Str)
def preInformation(**kwargs):
    input_params = ['device', 'id', 'platform', 'version', 'driver', 'package', 'activity']
    
    # Check if all required parameters are provided
    for param in input_params:
        if param not in kwargs:
            raise ValueError(f"Error: '{param}' is required.")
    
    # Default values
    package = kwargs.get('package', "")
    activity = kwargs.get('activity', "")
    reset = kwargs.get('reset', True)
    port = kwargs.get('port', "4723")

    # Prepare capabilities dictionary
    capabilities = dict(
        # Device Information
        deviceName = kwargs['device'],       # Device Model
        udid = kwargs['id'],                 # Device Unique ID
        platformName = kwargs['platform'],   # Device Platform (Android / iOS)
        platformVersion = kwargs['version'], # Platform Version
        automationName = kwargs['driver'],   # Automation Driver

        # Apps Information
        appPackage = package,                # Package to run the apps
        appActivity = activity,              # Activity to run the apps
        noReset = reset                      # Cache Controller
    )
    
    # Prepare URL
    url = f"http://localhost:{port}"         # Appium URL

    return capabilities, url

# Connection of Appium
# Input:  capabilities (Dict), url (Str), freeze (Int)
# Output: driver (WebDriver), wait (WebDriver)
def startConnect(capabilities, url, freeze = 5):
    # Validate capabilities
    if not capabilities:
        warnings.warn("Warning: 'capabilities' is empty or not provided.", UserWarning)
    elif not isinstance(capabilities, dict):
        raise TypeError("Error: 'capabilities' must be a dictionary.")
    
    # Validate URL
    if not url:
        warnings.warn("Warning: 'url' is empty or not provided.", UserWarning)
    elif not isinstance(url, str):
        raise TypeError("Error: 'url' must be a string.")
    
    # Establish the connection to the Appium server
    driver = webdriver.Remote(url, options=UiAutomator2Options().load_capabilities(capabilities))
    
    # Set up a WebDriverWait object
    wait = WebDriverWait(driver, freeze)

    return driver, wait

# Element Center Position
# Input:  element (Element Selector)
# Output: cx [Center of x of element] (int), cy [Center of y of element] (int), width (int), height (int)
def getElementPosition(element):
    position = element.location
    size = element.size
    width = size["width"]
    height = size["height"]
    cx = position['x'] + (width/2)
    cy = position['y'] + (height/2)

    return cx, cy, width, height

# Phone Screen Size
# Input:  driver (WebDriver)
# Output: width (int), height (int)
def getPhoneSize(driver):
    screen_size = driver.get_window_size()
    # Extract width and height
    width = screen_size['width']
    height = screen_size['height']

    return width, height

# Tap Gesture
# Input:  actions (ActionChains), x [Position x-index to tap] (Int), y [Position y-index to tap] (Int)
# Output: None
def tapGesture(actions, x, y):
    actions.w3c_actions.pointer_action.move_to_location(x, y)
    actions.w3c_actions.pointer_action.pointer_down()
    actions.w3c_actions.pointer_action.pointer_up()

# Double Tap Gesture
# Input:  actions (ActionChains), x [Position x-index to double tap] (Int), y [Position y-index to double tap] (Int)
# Output: None
def doubleTapGesture(actions, x, y):
    actions.w3c_actions.pointer_action.move_to_location(x, y)
    actions.w3c_actions.pointer_action.pointer_down()
    actions.w3c_actions.pointer_action.pointer_up()
    actions.w3c_actions.pointer_action.pause(0.3)
    actions.w3c_actions.pointer_action.pointer_down()
    actions.w3c_actions.pointer_action.pointer_up()

# Long Press Gesture
# Input:  actions (ActionChains), x [Position x-index to long press] (Int), y [Position y-index to long press] (Int)
# Output: None
def longPressGesture(actions, x, y, time):
    actions.w3c_actions.pointer_action.move_to_location(x, y)
    actions.w3c_actions.pointer_action.pointer_down()
    actions.w3c_actions.pointer_action.pause(time)
    actions.w3c_actions.pointer_action.pointer_up()

# Swipe (Up / Down / Left / Right) Gesture
# Input:  actions (ActionChains), x1, y1 [Initial position x/y-index] (Int),  x2, y2 [Final position x/y-index] (Int)
# Output: None
def swipeGesture(actions, x1, y1, x2, y2):
    actions.w3c_actions.pointer_action.move_to_location(x1, y1)
    actions.w3c_actions.pointer_action.pointer_down()
    actions.w3c_actions.pointer_action.pause(0.3)
    actions.w3c_actions.pointer_action.move_to_location(x2, y2)
    actions.w3c_actions.pointer_action.pointer_up()


