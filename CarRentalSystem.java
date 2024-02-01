package projects.oops;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car{
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    public Car(String carId, String brand, String model, double basePricePerDay ){
        this.carId=carId;
        this.brand=brand;
        this.model=model;
        this.basePricePerDay=basePricePerDay;
        isAvailable=true;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public String getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double getBasePricePerDay() {
        return basePricePerDay;
    }

    public void rent(){
        isAvailable=false;
    }
    public void returnCar(){
        isAvailable=true;
    }


}

class Customer{
    private String customerId;
    private String name;

    public Customer(String customerId,String name){
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}

class Rental{
    private Car car;
    private Customer customer;
    private int days;
    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}

class RentalSystem{
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;
    public RentalSystem(){
        cars=new ArrayList<>();
        customers=new ArrayList<>();
        rentals=new ArrayList<>();
    }
    public void addCar(Car car){
        cars.add(car);
    }
    public void rentCar(Car car,Customer customer,int days){
        if(car.isAvailable()){
            car.rent();
            rentals.add(new Rental(car,customer,days));
            System.out.println("car Rented Successfully");
        }else {
            System.out.println("Car is Not Available for rent");
        }
    }

    public void returnCar(Car car) {
        car.returnCar();
        Rental rentalToRemove = null;
        for (Rental rental : rentals) {
            if (rental.getCar() == car) {
                rentalToRemove = rental;
                break;
            }
        }
        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);

        } else {
            System.out.println("Car was not rented.");
        }
    }
    Scanner sc = new Scanner(System.in);
    public void menu(){
        while(true){
            System.out.println("=====Car Rental System=====");
            System.out.println("1. Rent a Car:");
            System.out.println("2. Return a Car");
            System.out.println("3. Exit");
            System.out.print("Enter Your Choice : ");

            int choice=sc.nextInt();
            sc.nextLine(); //clearing input buffer


            if(choice==1){
                System.out.println("\n=====Car Rental System=====\n");
                System.out.print("Enter Your Name : ");
                String customerName = sc.nextLine();

                System.out.println("Available Cars : ");

                for(Car car:cars){
                    if(car.isAvailable()){
                        System.out.println(car.getCarId()+" - "+car.getBrand()+" "+car.getModel());
                    }
                }

                System.out.print("\nEnter the Car Id you want to Rent : ");
                String carId = sc.nextLine();

                System.out.print("\nEnter the Number of days for Rentals :");
                int rentalDays = sc.nextInt();
                sc.nextLine();

                System.out.println("\n===Rental Information===\n");
                Customer newCustomer = new Customer("CUS"+(customers.size())+1,customerName);

                Car selectedCar = null;
                for(Car car:cars){
                    if(car.getCarId().equals(carId) && car.isAvailable()){
                        selectedCar=car;
                        break;
                    }
                }

                if(selectedCar!=null) {
                    System.out.println("Customer Id : " + newCustomer.getCustomerId());
                    System.out.println("Customer Name : " + newCustomer.getName());
                    System.out.println("Car : " + selectedCar.getBrand() + " " + selectedCar.getModel());
                    System.out.println("Rental Days : " + rentalDays);
                    double totalPrice = rentalDays * selectedCar.getBasePricePerDay();
                    System.out.println("Total Price : " + totalPrice + "\n");


                    System.out.print("\nConfirm rental (Y/N): ");
                    String confirm = sc.nextLine();

                    if (confirm.equalsIgnoreCase("Y")) {
                        rentCar(selectedCar, newCustomer, rentalDays);
                    } else {
                        System.out.println("\nRental Cancelled");
                    }
                }else{
                    System.out.println("\nInvalid Car Selection or Car Nor Available for rent");
                }
            } else if (choice==2) {
                System.out.println("\n====Return a Car====\n");
                System.out.print("Enter the car ID that you want to return : ");
                String carId= sc.nextLine();

                Car carToReturn = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && !car.isAvailable()) {
                        carToReturn = car;
                        break;
                    }
                }
                if(carToReturn!=null){
                    //first find customer name
                    Customer customer=null;
                    for(Rental rental:rentals){
                        if(rental.getCar()==carToReturn){
                            customer=rental.getCustomer();
                            break;
                        }
                    }
                    if(customer!=null){
                        returnCar(carToReturn);
                        System.out.println("Car returned successfully by " + customer.getName());
                    }else {
                        System.out.println("Car was not rented or rental information is missing.");
                    }
                }else {
                    System.out.println("nvalid car ID or car is not rented.");
                }
            }else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
        System.out.println("\nThank you for using the Car Rental System!");
    }

}
public class CarRentalSystem {
    public static void main(String[] args) {

        RentalSystem rentalSystem = new RentalSystem();

        Car car1 = new Car("C001", "Toyota", "Camry", 60.0); // Different base price per day for each car
        Car car2 = new Car("C002", "Honda", "Accord", 70.0);
        Car car3 = new Car("C003", "Mahindra", "Thar", 150.0);

        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);

        rentalSystem.menu();
    }


}
