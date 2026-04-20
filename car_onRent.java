package CarRentalSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car {

    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    public Car(String carId, String brand, String model, double basePricePerday) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerday;
        this.isAvailable = true;
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

    public double caluculatePrice(int rentalDays) {
        return basePricePerDay * rentalDays;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnCar() {
        isAvailable = true;
    }
}

class Customer {

    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
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

class Rental {

    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getcar() {
        return car;
    }

    public Customer getcustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}

class CarRental_System {

    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRental_System() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days) {
        if (car.isAvailable()) {
            car.rent();
            rentals.add(new Rental(car, customer, days));
        } else {
            System.out.println("Car is not available for rent.");
        }
    }

    public void returnCar(Car car) {
        car.returnCar();
        Rental rentaltoRemove = null;
        for (Rental rental : rentals) {
            if (rental.getcar() == car) {
                rentaltoRemove = rental;
                break;
            }
        }
        if (rentaltoRemove != null) {
            rentals.remove(rentaltoRemove);
        } else {
            System.out.println("Car was not Rented.");
        }
    }

    public void menu() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("===== Car Rental System =====");
            System.out.println("1. Rent a Car.");
            System.out.println("2. Return Car.");
            System.out.println("3. Exit.");
            System.out.println("Enter your choice:");
            int choice = sc.nextInt();
            sc.nextLine();
            if(choice == 1){
                System.out.println("\n== Rent a Car ==\n");
                System.out.print("Enter your Name :");
                String customerName = sc.nextLine();
                System.out.println("\n Available cars :");
                for (Car car : cars) {
                    if (car.isAvailable()) {
                        System.out.println(car.getCarId() + "-" + car.getBrand() + " " + car.getModel());
                    }
                }
                System.out.print("\n Enter the CarID you want to rent :");
                String carId = sc.nextLine();
                System.out.print("Enter the number of days for rental :");
                int rentlaDays = sc.nextInt();
                sc.nextLine();
                Customer newCustomer = new Customer("cus" + (customers.size() + 1), customerName);
                addCustomer(newCustomer);
                Car selectedCar = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && car.isAvailable()) {
                        selectedCar = car;
                        break;
                    }
                }
                if (selectedCar != null) {
                    double totalprice = selectedCar.caluculatePrice(rentlaDays);
                    System.out.println("\n== Rental Information ==\n");
                    System.out.println("Custmer ID :" + newCustomer.getCustomerId());
                    System.out.println("Customer Name :" + newCustomer.getName());
                    System.out.println("Car :" + selectedCar.getBrand() + " " + selectedCar.getModel());
                    System.out.println("Rental Days :" + rentlaDays);
                    System.out.printf("Total Price :$%2f%n", totalprice);
                    System.out.println("\n Confirme Rental(Y/N):");
                    String confirm = sc.nextLine();
                    if (confirm.equalsIgnoreCase("y")) {
                        rentCar(selectedCar, newCustomer, rentlaDays);
                        System.out.println("\n Car rented Scuccessfully...");
                    } else {
                        System.out.println("\nRental Cancelled...");
                    }
                } else {
                    System.out.println("\nInvalid Car Selection or car not available for ");
                }
            } else if (choice == 2) {
                System.out.println("\nReturn a Car ==\n");
                System.out.println("Enter the Car Id you want to return :");
                String carId = sc.nextLine();
                Car cartoReturn = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && !car.isAvailable()) {
                        cartoReturn = car;
                        break;
                    }

                }
                if (cartoReturn != null) {
                    Customer customer = null;
                    for (Rental rental : rentals) {
                        if (rental.getcar() == cartoReturn) {
                            customer = rental.getcustomer();
                            break;
                        }
                    }
                    if (customer != null) {
                        returnCar(cartoReturn);
                        System.out.println("Car returned Successfully by " + customer.getName());
                    } else {
                        System.out.println("Car was not found, rental information is missing.");
                    }
                } else {
                    System.out.println("Invalid CarID or car is not Rented.");
                }
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice. Please enter a valid option !.");
            }
        }
        System.out.println("\n Thankyou for using the car Rental System !!.");
    }
}

public class car_onRent {

    public static void main(String[] args) {
        CarRental_System rentalSystem = new CarRental_System();
        Car car1 = new Car("C001", "Toyota", "Fortuner", 20.0);
        Car car2 = new Car("C002", "Mahindra", "Thar", 20.0);
        Car car3 = new Car("C003", "Suzuki", "Dzire", 15.0);
        Car car4 = new Car("C004", "TATA", "Zest", 10.0);
        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);
        rentalSystem.addCar(car4);

        rentalSystem.menu();
    }
}
