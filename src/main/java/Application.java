import model.entities.CarRental;
import model.entities.Vehicle;
import model.exceptions.DomainException;
import model.services.BrazilTaxService;
import model.services.RentalService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy HH:ss");

        try {

            System.out.println("Enter rental data");
            System.out.print("Car model: ");
            String carModel = sc.nextLine();
            System.out.print("Pickup (dd/MM/yyy hh:ss): ");
            Date start = sdf.parse(sc.nextLine());
            System.out.print("Return (dd/MM/yyy hh:ss): ");
            Date finish = sdf.parse(sc.nextLine());

            CarRental cr = new CarRental(start, finish, new Vehicle(carModel));

            System.out.print("Enter price per hour: ");
            double pricePerHour = sc.nextDouble();
            System.out.print("Enter price per day: ");
            double pricePerDay = sc.nextDouble();

            RentalService rentailService = new RentalService(pricePerDay, pricePerHour, new BrazilTaxService());
            rentailService.processInvoice(cr);

            System.out.println("INVOICE: ");
            System.out.println("Basic payment: " + String.format("%.2f", cr.getInvoice().getBasicPayment()));
            System.out.println("Tax: " + String.format("%.2f", cr.getInvoice().getTax()));
            System.out.println("Total payment: " + String.format("%.2f", cr.getInvoice().getTotalPayment()));

        } catch (ParseException e) {
            System.out.println("Invalid date format");
        } catch (DomainException e) {
            System.out.println("Error in reservation: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Unexpected error");
        }

        sc.close();

    }
}
