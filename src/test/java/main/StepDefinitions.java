package main;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StepDefinitions {

    private ArrayList<String> carRegFound = new ArrayList<String>();
    private ArrayList<Car> carInput = new ArrayList<Car>();
    private ArrayList<Car> carOutput = new ArrayList<Car>();
    private ArrayList<Car> carFound = new ArrayList<Car>();
    private ArrayList<Car> carNotFound = new ArrayList<Car>();
    private String pRegistration, pMake, pModel, pColour, pYear;
    WebDriver driver;

    @Before
    public void setup() throws IOException {
        inputFile();
        outputFile();
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.get("https://cartaxcheck.co.uk/");
        driver.manage().window().maximize();
    }

    @After
    public void shutDown() {
        driver.quit();
    }

    @Given("User inputs their car reg on the website")
    public void givenMethod() throws InterruptedException {
        for(int i = 0; i < carRegFound.size(); i++) {
            driver.findElement(By.id("vrm-input")).sendKeys(carRegFound.get(i));
            driver.findElement(By.className("jsx-1164392954")).click();
            Thread.sleep(2500);
            boolean isDisplayed = false;
            try {
                isDisplayed = driver.findElement(By.className("jsx-4054927204")).isDisplayed();
            } catch (NoSuchElementException e) {
                ;
            }
            Thread.sleep(1000);
            pRegistration = carRegFound.get(i);
            if(isDisplayed) {
                pMake = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[5]/div[1]/div/span/div[2]/dl[2]/dd")).getText();
                pModel = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[5]/div[1]/div/span/div[2]/dl[3]/dd")).getText();
                pColour = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[5]/div[1]/div/span/div[2]/dl[4]/dd")).getText();
                pYear = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[5]/div[1]/div/span/div[2]/dl[5]/dd")).getText();
                int pIntYear = Integer.parseInt(pYear);
                carInput.add(new Car(pRegistration, pMake, pModel, pColour, pIntYear));
            } else {
                carInput.add(new Car(pRegistration));
            }
            driver.navigate().to("https://cartaxcheck.co.uk/");
            Thread.sleep(1000);
        }
    }

    @When("User wants to check their car details")
    public void whenMethod() {
        for(int i = 0; i < carOutput.size(); i++) {
            for(int x = 0; x < carInput.size(); x++) {
                if (carOutput.get(i).toString().trim().equals(carInput.get(x).toString().trim())) {
                    carFound.add(carOutput.get(i));
                    break;
                }
                if(x == carInput.size() -1) {
                    carNotFound.add(carOutput.get(i));
                }
            }
        }
        System.out.println("Input array contains: ");
        for(Car c : carInput) {
            System.out.println(c);
        }
        System.out.println("\nOutput array contains: ");
        for(Car c : carOutput) {
            System.out.println(c);
        }
        System.out.println("\nCars matched: ");
        for(Car c : carFound) {
            System.out.println(c);
        }
        System.out.println("\nCar not matched: ");
        for(Car c : carNotFound) {
            System.out.println(c);
        }
    }

    @Then("User receives details of their car by reg num")
    public void thenMethod() {
        for(int i = 0; i < carInput.size(); i++) {
            assertTrue(carInput.toString().contains(carOutput.get(i).toString()), carOutput.get(i)+" could not be found");
        }
    }

    public void inputFile() throws IOException {
        try {
            File file = new File("./car_input.txt");
            String regex = "[A-Z]{2}[0-9]{2}\\s?[A-Z]{3}";
            Scanner reader = new Scanner(file);
            while(reader.hasNext()) {
                String data = reader.nextLine();
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(data.trim());

                while(matcher.find()) {
                    carRegFound.add(matcher.group().replaceAll("\\s+",""));
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    public void outputFile() throws IOException {
        try {
            File file = new File("./car_output.txt");
            Scanner reader = new Scanner(file);
            reader.nextLine();
            while(reader.hasNext()) {
                String data = reader.nextLine();
                if(!data.isEmpty()) {
                    String[] elements = data.split(",");
                    List<String> newElements = Arrays.asList(elements);
                    ArrayList<String> listOfStrings = new ArrayList<String>(newElements);
                    pRegistration = listOfStrings.get(0);
                    pMake = listOfStrings.get(1);
                    pModel = listOfStrings.get(2);
                    pColour = listOfStrings.get(3);
                    pYear = listOfStrings.get(4);
                    int pIntYear = Integer.parseInt(pYear);
                    carOutput.add(new Car(pRegistration, pMake, pModel, pColour, pIntYear));
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }
}