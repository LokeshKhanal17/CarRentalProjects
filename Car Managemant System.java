package CarRentalProjects;
//Simple Car Management System That Reflects the Pillers of OOP.
import java.util.*;
import java.time.LocalDate;

/**
 * Car Managemant System
 */
class Car_Managemant_System {
    
    public static void main(String[] args) {
    DatabaseManager databaseManager = new DatabaseManager();

    System.out.println("This Is your Login Page ......\nDo you Want to Continue as :");
    System.out.println("1. Employee \n2. Customer\n3. Exit");
    
    Scanner src = new Scanner(System.in);
    int input = src.nextInt();

    switch (input) {
        case 1:
            System.out.println("Welcome To Employee Page .....");
            Login empLogin = new Login(databaseManager);
            boolean empAuth = empLogin.login("Employee");
            if (empAuth) {
                // Sample Employee details
                Employee employee = new Employee(1, "Manager", "John Doe");
                System.out.println("Employee ID: " + employee.getEmployeeid());
                System.out.println("Role: " + employee.getRole());
                System.out.println("Name: " + employee.getName());

                // Example of adding a new car to inventory
                CarInventory inventory = new CarInventory("CarX", "MakeX", "ModelX", 2023, "Red", "REG123", true);
                inventory.AddCar();

                // Example of removing a car from inventory (Only available for managers)
                inventory.removeCar("Manager", "REG123");

                // Example of searching for a car in inventory
                Car foundCar = inventory.SearchCar("REG456");
                if (foundCar != null) {
                    System.out.println("Car Found: " + foundCar.getName());
                }
            } else {
                System.out.println("Authentication Failed for Employee");
            }
            break;
        case 2:
            System.out.println("Welcome To Customer Page .....");
            Login cusLogin = new Login(databaseManager);
            boolean cusAuth = cusLogin.login("Customer");
            if (cusAuth) {
                // Sample Customer details
                Customer customer = new Customer("Jane Doe", "1234567890");
                System.out.println("Customer Name: " + customer.getName());
                System.out.println("Phone Number: " + customer.getphoneNuber());

                // Example of renting a car
                rentalAgency agency = new rentalAgency("CarY", "MakeY", "ModelY", 2022, "Blue", "REG789", true);
                //populate the list of available cars
                agency.getAvailableCars().add(new Car("CarY", "MakeY", "ModelY", 2022, "Blue", "REG789", true));
                agency.getAvailableCars().add(new Car("CarZ", "MakeZ", "ModelZ", 2021, "Black", "REG456", true));
                agency.getAvailableCars().add(new Car("CarA", "MakeA", "ModelA", 2020, "White", "REG123", true));
                agency.getAvailableCars().add(new Car("CarB", "MakeB", "ModelB", 2019, "Red", "REG000", true));
                agency.getAvailableCars().add(new Car("CarC", "MakeC", "ModelC", 2018, "Green", "REG111", true));
                //populate the list of rented cars2
                agency.rentCar(agency.getAvailableCars().get(0));

                // Example of returning a rented car
                agency.returnCar(agency.getRentedCars().get(0));
                
            } else {
                System.out.println("Authentication Failed for Customer");
            }
            break;
        case 3:
            System.out.println("Thank You For Using Our System");
            break;
        default:
            System.out.println("You are not authenticated");
            break;
    }
}

}
class Employee
{
    private int employeeid;
    private String role;
    private String name;

    Employee(int employeeid,String role,String name ){
        this.employeeid=employeeid;
        this.role = role;
        this.name=name;
    }
    //getter and setter properties for employee.
    int getEmployeeid(){
        return this.employeeid;
    }
    String getRole(){
        return this.role;
    }
    String getName(){
        return this.name;
    }

}

interface Credentials {
    String getUserId();

    String getPassword();
}

class UserCredentials implements Credentials {
    private String userId;
    private String password;

    public UserCredentials(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public String getPassword() {
        return password;
    }
}

class CustomerCredentials extends UserCredentials {
    public CustomerCredentials(String userId, String password) {
        super(userId, password);
    }
}

class EmployeeCredentials extends UserCredentials {
    public EmployeeCredentials(String userId, String password) {
        super(userId, password);
    }
}

class DatabaseManager {
    private List<CustomerCredentials> customerDatabase;
    private List<EmployeeCredentials> employeeDatabase;

    // Constructor
    public DatabaseManager() {
        customerDatabase = new ArrayList<>();
        employeeDatabase = new ArrayList<>();
        // Add some sample data
        customerDatabase.add(new CustomerCredentials("Lokesh", "password1"));
        customerDatabase.add(new CustomerCredentials("Ram", "password2"));
        customerDatabase.add(new CustomerCredentials("Hari", "password3"));
        employeeDatabase.add(new EmployeeCredentials("Manager", "password1"));
        employeeDatabase.add(new EmployeeCredentials("Supervisor", "password2"));
        employeeDatabase.add(new EmployeeCredentials("employee", "password3"));
    }

    public List<CustomerCredentials> getCustomerDatabase() {
        return customerDatabase;
    }

    public List<EmployeeCredentials> getEmployeeDatabase() {
        return employeeDatabase;
    }
}

class Login {
    private DatabaseManager databaseManager;

    // Constructor
    public Login(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    // login
    public boolean login(String role) {
        Scanner src = new Scanner(System.in);
        System.out.println(role);
        System.out.println("Enter your UserId.");
        String userId = src.nextLine();
        System.out.println("Enter your Password.");
        String password = src.nextLine();
        
        //src.close();
        return authenticate(role, userId, password);
    }

    // auth
    public boolean authenticate(String role, String enteredUserId, String enteredPassword) {
        List<? extends Credentials> database;

        // System.out.println("enteredUserId : " + enteredUserId);
        // System.out.println("enteredPassword : " + enteredPassword);
        // System.out.println("role : " + role);
        // System.out.println("Check if the manager and employee ");
        // System.out.println("employee".equals(role.toLowerCase()));
        // System.out.println("customer".equals(role.toLowerCase()));
        // Get the database based on the role

        if ("customer".equals(role.toLowerCase())) {
            database = databaseManager.getCustomerDatabase();
           // System.out.println("customer database : " + database.get(0).getUserId());
        } else if ("employee".equals(role.toLowerCase())) {
            database = databaseManager.getEmployeeDatabase();
           // System.out.println("employee database : " + database);
        } else {
            throw new IllegalArgumentException("Invalid role");
        }
        // Check if the user exists in the database
        for (Credentials credentials : database) {

            if (credentials.getUserId().equals(enteredUserId) && credentials.getPassword().equals(enteredPassword)) {
                // Authentication successful
                return true;
            }
        }
        // Authentication failed
        return false;
    }
}

class Customer {
    private String name;
    private String phoneNumber;
    private ArrayList<RentalHist> rentalHistory = new ArrayList<RentalHist>();

    Customer(String name,String phoneNumber){
        this.name = name;
        this.phoneNumber=phoneNumber;
    }
    //getter and setter properties for customer.
    String getName(){
        return this.name;
    }
    String getphoneNuber(){
        return this.phoneNumber;
    }
}

class RentalHist{
    private LocalDate date;
    private String carType;
    private int duration; // its in hours
    private Experience experience;

    RentalHist(LocalDate date,String carType,int duration,Experience experience){
        this.carType=carType;
        this.duration=duration;
        this.date=date;
        this.experience=experience;
    }
    //getter and setter properties for RentalHist.
    LocalDate getDate(){
        return this.date;
    }
    String getCarType(){
        return this.carType;
    }
    int getDuration(){
        return this.duration;
    }
    Experience getExperience(){
        return this.experience;
    }
}

class Car{
    private String name;
    private String make;
    private String model;
    private double year;
    private String color;
    private String registrationNumber;
    private boolean currentStatus;
    //constructor
    Car(String name,String make,String model,double year,String color,String registrationNumber,boolean currentStatus){
        this.name=name;
        this.make=make;
        this.model=model;
        this.year=year;
        this.color=color;
        this.registrationNumber=registrationNumber;
        this.currentStatus= true;
    }

    //getters and setters for car class.
    String getName(){
        return this.name;
    }
    String getMake(){
        return this.make;
    }
    String getModel(){
        return this.model;
    }
    double getYear(){
        return this.year;
    }
    String getColor(){
        return this.color;
    }
    String getRegistrationNumber(){
        return this.registrationNumber;
    }
    boolean getCurrentStatus(){
        return this.currentStatus;
    }
    void setCurrentStatus(boolean currentStatus){
        this.currentStatus=currentStatus;
    }


}

class rentalAgency extends Car{
    private ArrayList<Car> listOfAvailableCars = new ArrayList<>();
    private ArrayList<Car> listOfRentedCars = new ArrayList<>();
    //constructor
    rentalAgency(String name,String make,String model,double year,String color,String registrationNumber,boolean currentStatus){
        super(name, make, model, year, color, registrationNumber, currentStatus);
        this.listOfAvailableCars = new ArrayList<>();
        this.listOfRentedCars = new ArrayList<>();
    }
    //Methods 

    void rentCar(Car car){
        //rent a car , check if the car is available or not.
        //if available then rent the car and update the status of the car.
        //if not available then show the message that the car is not available.
        if(car.getCurrentStatus()==true){
            car.setCurrentStatus(false);
            listOfRentedCars.add(car);
            listOfAvailableCars.remove(car);        }
        else{
            System.out.println("The Car is not available");
        }
    }
    void returnCar(Car car){
        //renturn a car 
        //at first check which car customer want to returns
        //then check if the car is rented or not
        //if rented then return the car and update the status of the car.
        //if not rented then show the message that the car is not rented.
        if(car.getCurrentStatus()==false){
            System.out.println("The Car is not rented");
        }
        else{
            car.setCurrentStatus(true);
            listOfAvailableCars.add(car);
            listOfRentedCars.remove(car);
        }
        

    }
    ArrayList<Car> getAvailableCars(){
        return this.listOfAvailableCars;
        
    }
    ArrayList<Car> getRentedCars(){
        return this.listOfRentedCars;
    }

}
class CarInventory  extends Car{
    private ArrayList<Car> listOfAllCars;
    //constructor
    CarInventory(String name,String make,String model,double year,String color,String registrationNumber,boolean currentStatus){
        super(name, make, model, year, color, registrationNumber, currentStatus);
        this.listOfAllCars = new ArrayList<>();
    }
    
//add car to the inventory
    void AddCar(){
        //add a car to the inventory.
        //check if the car is already in the inventory or not.
        //if not then add the car to the inventory.
        //if yes then show the message that the car is already in the inventory.
        for(Car car : listOfAllCars){
            if(car.getRegistrationNumber().equals(this.getRegistrationNumber())){
                System.out.println("The Car is already in the inventory");
                return;
            }
        }
        listOfAllCars.add(this);
    }
    //if your employee role is manager you can remove car from inventory 
    //if your employee role is not manager you can't remove car from inventory
    void removeCar(String role,String registrationNumber){
        //remove a car from the inventory.
        //check if the car is in the inventory or not.
        //if yes then remove the car from the inventory.
        //if not then show the message that the car is not in the inventory.
        if(role.equals("MANAGER")){
            for(Car car : listOfAllCars){
                if(car.getRegistrationNumber().equals(registrationNumber)){
                    listOfAllCars.remove(car);
                    return;
                }
            }
            System.out.println("The Car is not in the inventory");
        }
        else{
            System.out.println("You are not authorized to remove the car from the inventory");
        }
    }
    Car SearchCar(String registrationNumber){
        //search a car by registration number.
        //if found then return the car.
        //if not found then show the message that the car is not found.
        for(Car car : listOfAllCars){
            if(car.getRegistrationNumber().equals(registrationNumber)){
                return car;
            }
        }
        System.out.println("The Car is not found");
        return null;
    }
}

//enum for experience
enum Experience{
    BAD,
    GOOD,
    VERYGOOD,
    EXCELLENT,
    WONDERFUL
}

