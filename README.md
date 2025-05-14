# Selenium WebDriver Test Project

## Overview

This project is a Software Quality and Testing (SQAT) assignment designed to test the functionality of the website https://www.menemszol.hu/ using Selenium WebDriver. The project includes automated test cases written in Java, leveraging the Selenium framework to validate various aspects of the website, such as login functionality, profile management, static page content, and more.

The project is structured to follow the Page Object Model (POM) design pattern, ensuring maintainability and scalability of the test cases.

## Features
- Automated Testing: Tests are automated using Selenium WebDriver.
- Page Object Model (POM): Each page of the website is represented as a Java class, encapsulating its elements and actions.
- Reusable Utilities: Includes helper classes like FileUtils for file operations and Config for configuration management.
- Docker Integration: The project can be run in a Dockerized environment with Selenium Grid for distributed testing.
- Test Coverage:
  - Simple page load test
  - Login and Logout functionality
  - History test
  - Profile management (e.g., editing birthday)
  - Hover test
  - Static page validation (e.g., Cookies page)
  - Search functionality
  - Image downloading and validation

```
selenium_helper/
├── tests/
│   ├── src/
│   │   ├── test/
│   │   │   ├── java/
│   │   │   │   ├── utils/
│   │   │   │   │   ├── Config.java
│   │   │   │   │   ├── FileUtils.java
│   │   │   │   ├── MainPage.java
│   │   │   │   ├── LoginPage.java
│   │   │   │   ├── ProfilePage.java
│   │   │   │   ├── CookiesPage.java
│   │   │   │   ├── AdvertisementsPage.java
│   │   │   │   ├── SearchResultPage.java
│   │   │   │   ├── PageBase.java
│   │   │   │   ├── SeleniumTest.java
│   ├── resources/
│   │   ├── example.config.properties
|   ├── build.gradle
├── .gitignore
├── docker-compose.yml
├── Dockerfile
├── README.md
```

## Configuration
The configuration file config.properties should be located in the tests/resources/ directory. It should contain the following settings:

```properties
# Application settings
base.url=https://www.menemszol.hu/
selenium.grid.url=http://selenium:4444/wd/hub

# Credentials
username=xxxxxxx
password=xxxxxxx

# Timeouts
webdriver.wait=10

# Paths
file.download.path="resources/test-images"
```

There is an example config.properties file in the tests/resources/ directory, called example.config.properties.

TODO - validate this
## Test Cases
1. Test Page Load
  - File: SeleniumTest.java
  - Method: testLoadPage()
  - Description: Verifies that the main page loads successfully and contains the header text "Fórumok".
  - Assertions:
    - Header text contains "Fórumok".
2. Test Login and Logout
  - File: SeleniumTest.java
  - Method: testLoginLogout()
  - Description: Tests the login and logout functionality.
  - Steps:
    1. Navigate to the login page.
    2. Enter valid credentials.
    3. Verify that the username is displayed after login.
    4. Logout and verify that the login button is visible again.
  - Assertions:
  - Username matches the logged-in user.
  - Login button is visible after logout.
3. Test Profile Page and Navigation
  - File: SeleniumTest.java
  - Method: testProfileAndBack()
  - Description: Tests navigation to the profile page and back to the main page.
  - Assertions:
  - Profile page header contains the username.
    - Main page header contains "Fórumok" after navigating back.
4. Test Change Birthday
  - File: SeleniumTest.java
  - Method: testChangeBirthday()
  - Description: Tests editing the birthday on the profile page.
  - Steps:
    1. Navigate to the profile page.
    2. Edit the birthday to a random date.
    3. Verify that the updated birthday is displayed on the profile page.
  - Assertions:
    - Displayed birthday matches the updated value.
5. Test Hover Menu
  - File: SeleniumTest.java
  - Method: testHover()
  - Description: Tests the visibility of submenus when hovering over menu items.
  - Assertions:
    - Submenu visibility changes to "block" when hovered.
    - Submenu visibility changes to "none" when not hovered.
6. Test Static Page Content
  - File: SeleniumTest.java
  - Method: testStaticPage()
  - Description: Verifies the content of the Cookies page.
  - Assertions:
    - Header text contains "Cookies".
    - Links on the page match expected URLs.
    - Page image is displayed and has the correct position.
    - Metadata matches expected values.
7. Test Search Functionality
  - File: SeleniumTest.java
  - Method: testSearch()
  - Description: Tests the search functionality with multiple queries.
  - Assertions:
    - Search results contain the word "találat".
8. Test URL List Loading
  - File: SeleniumTest.java
  - Method: testUrlListLoad()
  - Description: Verifies that multiple static pages load correctly.
  - Assertions:
    - Header text matches the expected title for each page.
9. Test Advertisements Page
  - File: SeleniumTest.java
  - Method: testAdvertisementsPage()
  - Description: Tests downloading images from the advertisements page.
  - Steps:
    1. Navigate to the advertisements page.
    2. Download images from multiple pages.
    3. Verify that the correct number of images is downloaded.
    4. Optionally delete the downloaded files.
  - Assertions:
    - Number of downloaded images matches the file count in the directory.

## Running the Tests using Docker

1. Navigate to the project directory
2. Build and start the Docker containers:
    ```bash
    docker compose up -d
    ```
3. Find out the container ID of the Ubuntu container:
    ```bash
    docker ps
    ```
   Look for the container with the name `ubuntu` in the list.
4. Open a terminal inside the container:
    ```bash
    docker exec -it <container_id> bash
    ```
   Replace `<container_id>` with the actual ID of the Ubuntu container.
5. Inside the container, navigate to the tests directory:
    ```bash
    cd tests
    ```
6. Run the tests using Gradle:
    ```bash
    gradle clean && gradle test
    ```
7. View the test results in the `tests/build/reports/tests/test/index.html` file.
8. You can stop the Docker containers after the tests are completed:
    ```bash
    docker compose down
    ```
