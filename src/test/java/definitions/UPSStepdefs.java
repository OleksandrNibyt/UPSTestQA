 package definitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.Map;

//import static java.awt.color.ICC_Profile.getData;
import static org.assertj.core.api.Assertions.assertThat;
import static support.TestContext.getData;
import static support.TestContext.getDriver;

 public class UPSStepdefs {
    @Given("I go to {string} page")
    public void iGoToPage(String arg0) {
        getDriver().get("https://www.ups.com/us/en/Home.page");
    }

    @And("I open Shipping menu")
    public void iOpenShippingMenu() {
        WebElement shipping = getDriver().findElement(By.xpath("//nav[@class='ups-primary_nav']/ul[@class='ups-navItems_primary']/li/a[@id='ups-menuLinks1']"));
        new Actions(getDriver()).moveToElement(shipping).click().perform();
    }

    @And("I go to Create a Shipment")
    public void iGoToCreateAShipment() {
        getDriver().findElement(By.xpath("//a[contains(text(),'Create a Shipment:')]")).click();
    }

    @When("I fill out origin shipment fields")
    public void iFillOutOriginShipmentFields() {
        Map<String,String> dataFromFile = getData("customer");
        getDriver().findElement(By.xpath("//select[@id='origincountry']")).sendKeys(dataFromFile.get("country"));
        getDriver().findElement(By.xpath("//input[@id='originname']")).sendKeys(dataFromFile.get("name"));
        getDriver().findElement(By.xpath("//input[@id='originaddress1']")).sendKeys(dataFromFile.get("address"));
        getDriver().findElement(By.xpath("//input[@id='originpostal']")).sendKeys(dataFromFile.get("zip"));
        getDriver().findElement(By.xpath("//input[@id='originemail']")).sendKeys(dataFromFile.get("email"));
        getDriver().findElement(By.xpath("//input[@id='originphone']")).sendKeys(dataFromFile.get("phone"));
    }

    @And("I submit the shipment form")
    public void iSubmitTheShipmentForm() throws InterruptedException {
        Thread.sleep(1500);
        getDriver().findElement(By.xpath("//button[@id='nbsBackForwardNavigationContinueButton']")).click();
        //Thread.sleep(2000);
    }

    @Then("I verify origin shipment fields submitted")
    public void iVerifyOriginShipmentFieldsSubmitted() throws InterruptedException {
        Thread.sleep(2000);
        getDriver().findElement(By.xpath("//div[@class='ups-wrap_inner']//div[@class='ups-section']")).isDisplayed();
    }

    @When("I fill out destination shipment fields")
    public void iFillOutDestinationShipmentFields() {
        Map<String,String> dataFromFile = getData("customer");
        //getDriver().findElement(By.xpath("//select[@id='destinationcountry']")).sendKeys(dataFromFile.get("country"));
        getDriver().findElement(By.xpath("//input[@id='destinationname']")).sendKeys(dataFromFile.get("nameReciever"));
        getDriver().findElement(By.xpath("//input[@id='destinationaddress1']")).sendKeys(dataFromFile.get("addressReciever"));
        getDriver().findElement(By.xpath("//input[@id='destinationpostal']")).sendKeys(dataFromFile.get("zipReciever"));

    }

    @Then("I verify total charges appear")
    public void iVerifyTotalChargesAppear() {
        getDriver().findElement(By.xpath("//balance-bar[@class='ng-tns-c2-1 ng-star-inserted']")).isDisplayed();
    }

    @When("I submit new shipment form")
    public void iSubmitNewShipmentForm() {
        WebElement submit = getDriver().findElement(By.xpath("//button[@id='nbsBackForwardNavigationContinueButton']"));
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].click();", submit);
    }

    @And("I set packaging type")
    public void iSetPackagingType() throws InterruptedException {
        if (getDriver().findElement(By.xpath("//div[@class='ups-progress_number ups-progress_current']")).isSelected()) {
            //new WebDriverWait(getDriver(), 20).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//h2[@class='ups-section_heading ups-centered_header text-center']")));

            WebElement boxSelection = getDriver().findElement(By.xpath("//select[@id='nbsPackagePackagingTypeDropdown0']"));
            new Select(boxSelection).selectByVisibleText("UPS Express Box - Small");
            getDriver().findElement(By.xpath("//input[@id='nbsPackagePackageWeightField0']")).sendKeys("1");
        }
        Thread.sleep(1000);
    }

    @Then("I verify total charges changed")
    public void iVerifyTotalChargesChanged() {
        getDriver().findElement(By.xpath("//balance-bar[@class='ng-tns-c2-1 ng-star-inserted']")).isDisplayed();
        // String price = getDriver().findElement(By.xpath("//balance-bar[@class='ng-tns-c2-1 ng-star-inserted']//div[@id='nbsBalanceBarContainer']")).getText();
        //System.out.println(price);
    }

    @And("I select cheapest delivery option")
    public void iSelectCheapestDeliveryOption() {
        if(getDriver().findElement(By.xpath("//div[@class='ups-progress_number ups-progress_current']")).isSelected()) {
            if (getDriver().findElement(By.xpath("//div[@class='upsell-tiles ups-shipping_schedule_when']//input[@id='cust-input-Cheapest']")).isDisplayed()) {
                getDriver().findElement(By.xpath("//input[@id='cust-input-Fastest']")).click();
            }
        }
    }

    @And("I set Saturday Delivery type")
    public void iSetSaturdayDeliveryType() {
        if (getDriver().findElement(By.xpath("//div[@class='ups-progress_number ups-progress_current']")).isSelected())  {
            getDriver().findElement(By.xpath("//saturday-delivery-option[@class='ng-star-inserted']//label[@class='ups-lever']")).click();
        }
    }

    @And("I select Paypal payment type")
    public void iSelectPaypalPaymentType() {
        if (getDriver().findElement(By.xpath("//div[@class='ups-progress_number ups-progress_current']")).isSelected()) {
            getDriver().findElement(By.xpath("//div[@id='tile-4']//label[@class='ups-tile_button_content']")).click();
            assertThat(getDriver().findElement(By.xpath("//label[@class='ups-radio-custom-label ng-star-inserted']")).isSelected());
            getDriver().findElement(By.xpath("//button[@id='nbsBackForwardNavigationReviewPrimaryButton']")).click();
        }
    }

    @Then("I review all recorded details on the reviewpage")
    public void iReviewAllRecordedDetailsOnTheReviewpage() {
        Map<String,String> dataFromFile = getData("customer");
        //System.out.println(result);
        if (getDriver().findElement(By.xpath("//div[@class='ups-progress_number ups-progress_current']")).isSelected()) {

            //if (getDriver().findElement(By.xpath("//div[@class='ups-page-title_cell']//ul[@class='ups-drawer ng-star-inserted']")).isDisplayed()){
            assertThat(getDriver().findElement(By.xpath("//origin-return-drawer[@class='ng-star-inserted']//section[@class='ups-section col-md-6']")).getText().contains(dataFromFile.get("country")));
            assertThat(getDriver().findElement(By.xpath("//origin-return-drawer[@class='ng-star-inserted']//section[@class='ups-section col-md-6']")).getText().contains(dataFromFile.get("zip")));
            assertThat(getDriver().findElement(By.xpath("//origin-return-drawer[@class='ng-star-inserted']//section[@class='ups-section col-md-6']")).getText().contains(dataFromFile.get("name")));
            assertThat(getDriver().findElement(By.xpath("//origin-return-drawer[@class='ng-star-inserted']//section[@class='ups-section col-md-6']")).getText().contains(dataFromFile.get("address")));
            assertThat(getDriver().findElement(By.xpath("//origin-return-drawer[@class='ng-star-inserted']//section[@class='ups-section col-md-6']")).getText().contains(dataFromFile.get("mail")));
            assertThat(getDriver().findElement(By.xpath("//origin-return-drawer[@class='ng-star-inserted']//section[@class='ups-section col-md-6']")).getText().contains(dataFromFile.get("phone")));
            assertThat(getDriver().findElement(By.xpath("//destination[@id='nbsSpaDestination']//section[@class='ups-section col-md-6']")).getText().contains(dataFromFile.get("nameReciever")));
            assertThat(getDriver().findElement(By.xpath("//destination[@id='nbsSpaDestination']//section[@class='ups-section col-md-6']")).getText().contains(dataFromFile.get("addressReciever")));
            assertThat(getDriver().findElement(By.xpath("//destination[@id='nbsSpaDestination']//section[@class='ups-section col-md-6']")).getText().contains(dataFromFile.get("zipReciever")));


        }
    }

    @And("I cancel the shipment form")
    public void iCancelTheShipmentForm() {
        if (getDriver().findElement(By.xpath("//div[@class='ups-progress_number ups-progress_current']")).isSelected()){
            getDriver().findElement(By.xpath("//button[@id='nbsBackForwardNavigationCancelShipmentButton']")).click();
        }
    }

    @Then("I verify shipment form is reset")
    public void iVerifyShipmentFormIsReset() {
        assertThat(getDriver().findElement(By.xpath("//div[@class='ups-progress_number ups-progress_current']")).isSelected());
    }
}
