import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.sql.Driver;
import java.time.Duration;

public class Main {
    static public void main(String[] args){
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        // Open the test page/browser
        String url = "https://www.clock-software.com/demo-clockpms/index.html";
        driver.get(url);

        // Max the window
        driver.manage().window().maximize();

        firstPage(driver);
        secondPage(driver);
        thirdPage(driver);
        fourthPage(driver);
        fifthPage(driver);
        sixthPage(driver);

        driver.quit();
    }

    /*
        1. Select a valid date, and number of rooms and start the booking process
        2. Under Deluxe Apartment, select the most expensive package
        3. Select any 2 add ons
        4. Validate all details – Date, no of nights, room type, rate, add on (extra services charges), total
        5. Add traveler details and payment method to CC

        6. Use a dummy Visa CC and complete payment
        7. Validate Booking complete msg
    */

    static public void sixthPage(WebDriver driver){
        WebElement confirmationMessage = driver.findElement(By.xpath("//*[@id=\"top_position_container\"]/div[2]/h1"));
        if(confirmationMessage.getText().equals("Thank you for your booking!"))
            System.out.println("All good! We're done!");
        else System.out.println("The confirmation message isn't quite right!");
    }

    static public void fifthPage(WebDriver driver){
        WebElement cardNumber = driver.findElement(By.id("cardNumber"));
        cardNumber.sendKeys("4367623693756999");

        Select cardBrand = new Select(driver.findElement(By.id("credit_card_collect_purchase_brand")));
        cardBrand.selectByIndex(1);

        Select expiresMonth = new Select(driver.findElement(By.id("cardExpirationMonth")));
        expiresMonth.selectByIndex(2);

        Select expiresYear = new Select(driver.findElement(By.id("cardExpirationYear")));
        expiresYear.selectByIndex(3);

        WebElement billingAddress = driver.findElement(By.id("credit_card_collect_purchase_address"));
        billingAddress.sendKeys("5 Lime Lane");

        WebElement zip = driver.findElement(By.id("credit_card_collect_purchase_zip"));
        zip.sendKeys("5LL LL5");

        WebElement city = driver.findElement(By.id("credit_card_collect_purchase_city"));
        city.sendKeys("Gotham");

        WebElement state = driver.findElement(By.id("credit_card_collect_purchase_state"));
        state.sendKeys("Florida");

        Select country = new Select(driver.findElement(By.id("credit_card_collect_purchase_country")));
        country.selectByIndex(5);

        WebElement pay = driver.findElement(By.xpath("//button[@class='btn btn-success btn-lg btn-block']"));
        pay.click();
    }

    static public void fourthPage(WebDriver driver) {
        try {
            if (!validateDetails(driver)) {
                throw new Exception("Details are not all valid");
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            validateDetailsPrint(driver);
            return;
        }

        String emailXPath = "(//input[@title='E-mail'])";
        WebElement email = driver.findElement(By.xpath(emailXPath));
        email.sendKeys("Bob.Jillanson@CompanyEmail.com");

        String lastNameXPath = "(//input[@title='Last name'])";
        WebElement lastName = driver.findElement(By.xpath(lastNameXPath));
        lastName.sendKeys("Jillanson");

        String firstNameXPath = "(//input[@title='First name'])";
        WebElement firstName = driver.findElement(By.xpath(firstNameXPath));
        firstName.sendKeys("Bob");

        String phoneXPath = "(//input[@title='Phone'])";
        WebElement phone = driver.findElement(By.xpath(phoneXPath));
        phone.sendKeys("01516378195");

        String paymentMethodXPath = "(//input[@class='guarantee-type'])[3]";
        WebElement paymentMethod = driver.findElement(By.xpath(paymentMethodXPath));
        paymentMethod.click();

        String agreePolicyXPath = "(//input[@type='checkbox'])[2]";
        WebElement agreePolicy = driver.findElement(By.xpath(agreePolicyXPath));
        agreePolicy.click();

        String createBookingButtonXPath = "(//input[@name='commit'])[2]";
        WebElement createBooking = driver.findElement(By.xpath(createBookingButtonXPath));
        createBooking.click();
    }

    static private boolean validateDetails(WebDriver driver){
        String arrivalDateXPath = "/html//div[@id='top_position_container']/div[3]/div[3]/div[1]/div[@class='panel-body']/div[1]/div[@class='col-sm-7']";
        WebElement arrivalDate = driver.findElement(By.xpath(arrivalDateXPath));
        String expectedArrivalDate = "26 Oct 2022";
        boolean arrivaldateVal = arrivalDate.getText().equals(expectedArrivalDate);

        String departureDateXPath = "/html//div[@id='top_position_container']/div[3]/div[3]/div[1]/div[@class='panel-body']/div[3]/div[@class='col-sm-7']";
        WebElement departureDate = driver.findElement(By.xpath(departureDateXPath));
        String expectedDepartureDate = "30 Oct 2022";
        boolean departureDateVal = departureDate.getText().equals(expectedDepartureDate);

        String nightsXPath = "/html//div[@id='top_position_container']/div[3]/div[3]/div[1]/div[@class='panel-body']/div[2]/div[@class='col-sm-7']";
        WebElement nights = driver.findElement(By.xpath(nightsXPath));
        String expectedNights = "4";
        boolean nightsVal = nights.getText().equals(expectedNights);

        String roomTypeXPath = "/html//div[@id='top_position_container']/div[3]/div[3]/div[1]/div[@class='panel-body']/div[5]/div[@class='col-sm-7']";
        WebElement roomType = driver.findElement(By.xpath(roomTypeXPath));
        String expectedRoomType = "Double Superior Room";
        boolean roomTypeVal = roomType.getText().equals(expectedRoomType);

        String RateXPath = "/html//div[@id='top_position_container']/div[3]/div[3]/div[1]/div[@class='panel-body']/div[6]/div[@class='col-sm-7']";
        WebElement rate = driver.findElement(By.xpath(RateXPath));
        String expectedRate = "Package Rate Spa for Family Max +";
        boolean rateVal = rate.getText().equals(expectedRate);

        String addOnsXPath = "/html//div[@id='top_position_container']/div[3]/div[3]/div[1]/div[@class='panel-body']/div[10]/div[@class='col-sm-7']";
        WebElement addOns = driver.findElement(By.xpath(addOnsXPath));
        String expectedAddOns = "15.00 EUR";
        boolean addOnsVal = addOns.getText().equals(expectedAddOns);

        String totalXPath = "/html//div[@id='top_position_container']/div[3]/div[3]//h3[.='1,405.00 EUR']";
        WebElement total = driver.findElement(By.xpath(totalXPath));
        String expectedTotal = "1,405.00 EUR";
        boolean totalVal = total.getText().equals(expectedTotal);

        return arrivaldateVal && departureDateVal && nightsVal && roomTypeVal && rateVal && addOnsVal && totalVal;
    }

    static private boolean validateDetailsPrint(WebDriver driver){
        String arrivalDateXPath = "/html//div[@id='top_position_container']/div[3]/div[3]/div[1]/div[@class='panel-body']/div[1]/div[@class='col-sm-7']";
        WebElement arrivalDate = driver.findElement(By.xpath(arrivalDateXPath));
        String expectedArrivalDate = "26 Oct 2022";
        boolean arrivaldateVal = arrivalDate.getText().equals(expectedArrivalDate);
        System.out.println("arrivaldateVal: " + arrivaldateVal + "|" + arrivalDate.getText() + "=/=" + expectedArrivalDate);

        String departureDateXPath = "/html//div[@id='top_position_container']/div[3]/div[3]/div[1]/div[@class='panel-body']/div[3]/div[@class='col-sm-7']";
        WebElement departureDate = driver.findElement(By.xpath(departureDateXPath));
        String expectedDepartureDate = "30 Oct 2022";
        boolean departureDateVal = departureDate.getText().equals(expectedDepartureDate);
        System.out.println("departureDateVal: " + departureDateVal + "|" + departureDate.getText() + "=/=" + expectedDepartureDate);

        String nightsXPath = "/html//div[@id='top_position_container']/div[3]/div[3]/div[1]/div[@class='panel-body']/div[2]/div[@class='col-sm-7']";
        WebElement nights = driver.findElement(By.xpath(nightsXPath));
        String expectedNights = "4";
        boolean nightsVal = nights.getText().equals(expectedNights);
        System.out.println("nightsVal: " + nightsVal + "|" + nights.getText() + "=/=" + expectedNights);

        String roomTypeXPath = "/html//div[@id='top_position_container']/div[3]/div[3]/div[1]/div[@class='panel-body']/div[5]/div[@class='col-sm-7']";
        WebElement roomType = driver.findElement(By.xpath(roomTypeXPath));
        String expectedRoomType = "Double Superior Room";
        boolean roomTypeVal = roomType.getText().equals(expectedRoomType);
        System.out.println("roomTypeVal: " + roomTypeVal + "|" + roomType.getText() + "=/=" + expectedRoomType);

        String RateXPath = "/html//div[@id='top_position_container']/div[3]/div[3]/div[1]/div[@class='panel-body']/div[6]/div[@class='col-sm-7']";
        WebElement rate = driver.findElement(By.xpath(RateXPath));
        String expectedRate = "Package Rate Spa for Family Max +";
        boolean rateVal = rate.getText().equals(expectedRate);
        System.out.println("rateVal: " + rateVal + "|" + rate.getText() + "=/=" + expectedRate);

        String addOnsXPath = "/html//div[@id='top_position_container']/div[3]/div[3]/div[1]/div[@class='panel-body']/div[10]/div[@class='col-sm-7']";
        WebElement addOns = driver.findElement(By.xpath(addOnsXPath));
        String expectedAddOns = "15.00 EUR";
        boolean addOnsVal = addOns.getText().equals(expectedAddOns);
        System.out.println("addOnsVal: "+ addOnsVal + "|" + addOns.getText() + "=/=" + expectedAddOns);

        String totalXPath = "/html//div[@id='top_position_container']/div[3]/div[3]//h3[.='1,405.00 EUR']";
        WebElement total = driver.findElement(By.xpath(totalXPath));
        String expectedTotal = "1,405.00 EUR";
        boolean totalVal = total.getText().equals(expectedTotal);
        System.out.println("totalVal: " + totalVal + "|" + total.getText() + "=/=" + expectedTotal);

        return arrivaldateVal && departureDateVal && nightsVal && roomTypeVal && rateVal && addOnsVal && totalVal;
    }

    static public void thirdPage(WebDriver driver){

        driver.switchTo().frame("clock_pms_iframe_1");
        String airportTransferQuantityBoxXPath = "(//input[@placeholder=\"Quantity\"])[1]";
        WebElement airportTransferQuantityBox = driver.findElement(By.xpath(airportTransferQuantityBoxXPath));
        airportTransferQuantityBox.sendKeys("1");

        String fitnessQuantityBoxXPath = "(//input[@placeholder=\"Quantity\"])[4]";;
        WebElement fitnessQuantityBox = driver.findElement(By.xpath(fitnessQuantityBoxXPath));
        fitnessQuantityBox.sendKeys("1");

        String addServicesButtonXPath = "/html//form[@id='new_form']/div[6]/div[@class='col-sm-6 col-sm-offset-6']/span[@class='pull-right']/input[@name='commit']";
        WebElement addServicesButton = driver.findElement(By.xpath(addServicesButtonXPath));
        addServicesButton.click();
    }

    static public void secondPage(WebDriver driver){

        driver.switchTo().frame("clock_pms_iframe_1");
        String selectButtonXpath = "(//a[@class='btn btn-success '])[18]";
        WebElement roomSelectButton = driver.findElement(By.xpath(selectButtonXpath));
        roomSelectButton = driver.findElement(By.xpath(selectButtonXpath));
        roomSelectButton.click();
        driver.switchTo().defaultContent();
    }

    static public void firstPage(WebDriver driver){
        // Select the date box then pick the 25th day of the month
        WebElement arrivalBox = driver.findElement(By.id("date-start"));
        arrivalBox.click();
        WebElement day25 = driver.findElement(By.xpath("/html/body/div[3]/div[@class='datepicker-days']/table/tbody/tr[5]/td[4]"));
        day25.click();

        // Select the Nights box and write 4 in it
        WebElement nights = driver.findElement(By.id("to-place"));
        nights.clear();
        nights.sendKeys("4");

        // Select the book button and click
        WebElement bookButton = driver.findElement(By.name("commit"));
        bookButton.click();
    }
}
