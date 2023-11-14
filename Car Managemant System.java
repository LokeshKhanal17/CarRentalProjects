package CarRentalProjects;
//Simple Car Management System That Reflects the Pillers of OOP.
import java.util.*;
import java.time.LocalDate;
import javax.swing.plaf.synth.SynthStyle;

import javafx.scene.paint.PhongMaterial;

/**
 * Car Managemant System
 */
class Car_Managemant_System {
    
    public static void main(String[] args) {
        System.out.println("This Is your Login Page ......\nDo you Want to Continue as :");
        System.out.println("1. Employee \n2.Customer\n3.Exit ");
        Scanner src = new Scanner(System.in);
        int input = src.nextInt();
        //if user is employee propmt with the screen that employee can see and perform task.
        //if user is customer propmt with the screen that customer can see and perform task.
        
        // while(input != 1 || input!= 2 || input!= 3){
        //     System.out.println("Please Enter Valid Input");
        //     input = src.nextInt();
        // }
        switch (input) {
            case 1:
                System.out.println("Welcome To Employee Page .....");
                Login emplogin = new Login();
                boolean empusrauth  = emplogin.login();
                System.out.println(empusrauth);
                break;
            case 2:
                System.out.println("Welcome To Customer Page .....");
                Login cuslogin = new Login();
                boolean cususrauth  = cuslogin.login();
                System.out.println(cususrauth);
                
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

class UserCredentials {
    private String userId;
    private String password;

    public UserCredentials(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return "Manager";
    }

    public String getPassword() {
        return "password1";
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

class Login{
    private List<CustomerCredentials> CustomerDatabase;
    private List<EmployeeCredentials> EmployeeDatabase;

    // Constructor
    public Login() {
        CustomerDatabase = new ArrayList<>();
        EmployeeDatabase = new ArrayList<>();
        // Add some sample data
        CustomerDatabase.add(new CustomerCredentials("Lokesh", "password1"));
        CustomerDatabase.add(new CustomerCredentials("Ram", "password2"));
        CustomerDatabase.add(new CustomerCredentials("Hari", "password3"));
        EmployeeDatabase.add(new EmployeeCredentials("Manager", "password1"));
        EmployeeDatabase.add(new EmployeeCredentials("Supervisor", "password2"));
        EmployeeDatabase.add(new EmployeeCredentials("employee", "password3"));
    }
    //login
    public boolean login(){
            
            Scanner src = new Scanner(System.in);
            System.out.println("Enter your UserId.");
            String Userid = src.nextLine();
            System.out.println("Enter your Password.");
            String Password = src.nextLine();
            boolean isAuthentic = authenticate(Userid, Password);
            return isAuthentic;
        }
    //auth
    public boolean authenticate(String enteredUserId, String enteredPassword) {
        for (UserCredentials userCredentials : EmployeeDatabase) {
            if (userCredentials.getUserId().equals(enteredUserId) && userCredentials.getPassword().equals(enteredPassword)) {
                // Authentication successful
                return true;
            }
        }
        // Authentication failed
        return false;

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
    ArrayList getAvailableCars(){
        return this.listOfAvailableCars;
        
    }
    ArrayList getRentedCars(){
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

