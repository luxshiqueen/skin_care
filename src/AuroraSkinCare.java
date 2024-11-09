import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.Comparator;


public class AuroraSkinCare {
    static List<Patient> patients = new ArrayList<>();
    static List<Dermatologist> dermatologists = new ArrayList<>();
    static List<Treatment> treatments = new ArrayList<>();
    static List<Appointment> appointments = new ArrayList<>();

    static int patientCounter = 1;
    static int appointmentCounter = 1;

    public static void main(String[] args) {
        initializeData();
        Scanner scanner = new Scanner(System.in);

        System.out.println("++++++++++----------+++++++++");
        System.out.println("++++++++++----------++++++++++");
        System.out.println("Welcome to Aurora Skin Care.");
        System.out.println("+++++++++--------------+++++++++");
        System.out.println("+++++++++--------------+++++++");


        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.println("+++++++++++++++++++++++++++++++++++");

        if (username.equals("lux") && password.equals("123")) {
            boolean exit = false;
            while (!exit) {

                System.out.println("Main Menu:");
                System.out.println("++++++++++++++++++++++++++++++++++");
                System.out.println("1. Create Appointment");
                System.out.println("2. View Appointments");
                System.out.println("3. Search Appointment");
                System.out.println("4. View Appointments Filter by Date");
                System.out.println("5. Update Appointment");
                System.out.println("6. Payment");
                System.out.println("7. Close");
                System.out.println("+++++++++++++++++++++++++++++++++++");
                System.out.print("Select an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        makeAppointment(scanner);
                        break;
                    case 2:
                        viewAppointments();
                        break;
                    case 3:
                        searchAppointment(scanner);
                        break;
                    case 4:
                        viewAppointmentsFilter();
                        break;
                    case 5:
                        updateAppointment(scanner);
                        break;
                    case 6:
                        payForAppointment(scanner);
                        break;
                    case 7:
                        exit = true;
                        System.out.println("Closing...");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        } else {
            System.out.println("Invalid.");
        }
        scanner.close();
    }


    private static void makeAppointment(Scanner scanner) {

        System.out.println("Register new patient ...");
        System.out.print("Enter your Name: ");
        String name = scanner.nextLine();
        String nic = "";
        while (true) {
            System.out.print("Enter your NIC (9 digits): ");
            nic = scanner.nextLine();
            if (nic.length() == 9 && nic.matches("[0-9]+")) {
                nic = nic + "V";
                System.out.println("NIC saved as: " + nic);
                break;
            } else {
                System.out.println("Invalid NIC. Please enter a 9-digit NIC number.");
            }
        }
        String email = "";
        while (true) {
            System.out.print("Enter your Email: ");
            email = scanner.nextLine();

            if (!email.contains("@")) {
                email = email + "@gmail.com";
            }

            if (email.matches("^[A-Za-z0-9]+@[A-Za-z0-9]+\\.[A-Za-z]{2,6}$")) {
                System.out.println("Email saved as: " + email);
                break;
            } else {
                System.out.println("Invalid email format. Please enter a valid email address.");
            }
        }
        String phone = "";
        while (true) {
            System.out.print("Enter your Phone number (10 digits): ");
            phone = scanner.nextLine();
            if (phone.length() == 10 && phone.matches("[0-9]+")) {
                break;
            } else {
                System.out.println("Invalid phone number. Please enter a 10-digit phone number.");
            }
        }

        String patientId = "P" + patientCounter++;
        Patient patient = new Patient(patientId, name, nic, email, phone);
        patients.add(patient);

        System.out.println("Patient registered successfully!");
        System.out.println("+++++++++++++++++++++++++++++++++++");

        System.out.println("Select Dermatologist:");
        for (Dermatologist d : dermatologists) {
            System.out.println(d.getId() + ". " + d.getName());
        }

        System.out.print("Enter dermatologist number: ");
        String doctorId = scanner.nextLine();

        Dermatologist selectedDoctor = null;
        for (Dermatologist d : dermatologists) {
            if (d.getId().equals(doctorId)) {
                selectedDoctor = d;
                break;
            }
        }

        if (selectedDoctor == null) {
            System.out.println("Invalid dermatologist choice!");
            return;
        }

        System.out.print("Enter appointment date (yyyy-mm-dd): ");
        String inputDate = scanner.nextLine();
        LocalDate date;

        try {
            date = LocalDate.parse(inputDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String dayOfWeek = date.getDayOfWeek().name();
            System.out.println("The day of the week is: " + dayOfWeek);

            if (!selectedDoctor.availability.containsKey(dayOfWeek)) {
                System.out.println("Selected dermatologist is not available on this day.");
                return;
            }

            System.out.println("Available times for " + dayOfWeek + ":");
            String[] times = selectedDoctor.availability.get(dayOfWeek);
            for (String time : times) {
                boolean isBooked = selectedDoctor.bookedSlots.get(dayOfWeek).getOrDefault(time, false);
                System.out.println(time + (isBooked ? " (Already Booked)" : ""));
            }

            System.out.print("Enter appointment time: ");
            String time = scanner.nextLine();

            if (!selectedDoctor.bookTimeSlot(dayOfWeek, time)) {
                System.out.println("This time slot is already booked. Please choose another time.");
                return;
            }

            System.out.println("+++++++++++++++++++++++++++++++++++");

            System.out.println("Select Treatment:");
            for (int i = 0; i < treatments.size(); i++) {
                System.out.printf("%d. %s - LKR %.2f%n", i + 1, treatments.get(i).getName(), treatments.get(i).getPrice());
            }

            System.out.print("Enter treatment number: ");
            int treatmentChoice = scanner.nextInt();
            Treatment selectedTreatment = null;

            if (treatmentChoice >= 1 && treatmentChoice <= treatments.size()) {
                selectedTreatment = treatments.get(treatmentChoice - 1);
            } else {
                System.out.println("Invalid treatment choice!");
                return;
            }

            System.out.println("A registration fee of LKR 500.00 will be applied.");
            System.out.print("Do you accept the registration fee? (yes/no): ");
            scanner.nextLine();

            String acceptFee = scanner.nextLine().trim().toLowerCase();

            if (!acceptFee.equals("yes")) {
                System.out.println("Registration fee not accepted. Appointment booking canceled.");
                return;
            }

            double registrationFee = 500.00;
            double treatmentFee = selectedTreatment.getPrice();
            double totalFeeBeforeTax = treatmentFee + registrationFee;
            double tax = totalFeeBeforeTax * 0.025;
            double totalFee = Math.ceil((totalFeeBeforeTax + tax) * 100) / 100;


            System.out.println("Total amount after the Appointment Success:");
            System.out.printf("Treatment Fee: LKR %.2f%n", treatmentFee);
            System.out.printf("Registration Fee: LKR %.2f%n", registrationFee);
            System.out.printf("Subtotal (before tax): LKR %.2f%n", totalFeeBeforeTax);
            System.out.printf("Tax (2.5%%): LKR %.2f%n", tax);
            System.out.printf("Total Fee: LKR %.2f%n", totalFee);

            String appointmentId = "P" + appointmentCounter++;
            appointments.add(new Appointment(appointmentId, selectedDoctor, date, time, patient, treatmentFee, registrationFee, totalFee, "Pending"));
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.println("Appointment booked successfully with ID: " + appointmentId);
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format! Please enter the date in the format yyyy-mm-dd.");
        }
    }


    private static void initializeData() {
        dermatologists.add(new Dermatologist("D1", "Dr. John"));
        dermatologists.add(new Dermatologist("D2", "Dr. Lux"));

        treatments.add(new Treatment("Acne Treatment=", 2750.00));
        treatments.add(new Treatment("Mole Removal=", 3850.00));
        treatments.add(new Treatment("Skin Whitening=", 7650.00));
        treatments.add(new Treatment("Laser Treatment=", 12500.00));
    }

    private static void viewAppointments() {
        System.out.println("++++++++++++++++++++++++++++++++++++++++");
        System.out.println("               Appointments             ");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++");

        System.out.printf("%-10s | %-15s | %-10s | %-10s | %-10s | %-15s | %-15s | %-10s | %-10s | %-10s%n",
                "ID", "Doctor", "Date", "Day", "Time", "Patient", "Treatment Fee", "Reg. Fee", "Total Fee", "Status");

        if (appointments.isEmpty()) {
            System.out.println("No appointments available.");
        } else {
            for (Appointment appointment : appointments) {
                System.out.printf("%-10s | %-15s | %-10s | %-10s | %-10s | %-15s | %-15.2f | %-10.2f | %-10.2f | %-10s%n",
                        appointment.id,
                        appointment.doctor.getName(),
                        appointment.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        appointment.getDay(),
                        appointment.getTime(),
                        appointment.getPatient().getName(),
                        appointment.getTreatmentFee(),
                        appointment.getRegistrationFee(),
                        appointment.getTotalFee(),
                        appointment.getStatus());
            }
        }
    }

    private static void viewAppointmentsFilter() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the date to view appointments (format: yyyy-MM-dd): ");
        String inputDate = scanner.nextLine();

        LocalDate specifiedDate;
        try {
            specifiedDate = LocalDate.parse(inputDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            return;
        }

        appointments.sort(Comparator.comparing(Appointment::getDate)
                .thenComparing(Appointment::getTime));

        boolean appointmentsFound = false;

        System.out.println("Appointments on " + specifiedDate + ":");
        for (Appointment appointment : appointments) {
            if (appointment.getDate().equals(specifiedDate)) {
                System.out.printf("ID: %s, Patient: %s, Dermatologist: %s, Time: %s, Total Fee: LKR %.2f, Status: %s%n",
                        appointment.id, appointment.getPatient().getName(), appointment.doctor.getName(), appointment.getTime(),
                        appointment.getTotalFee(), appointment.getStatus());
                appointmentsFound = true;
            }
        }

        if (!appointmentsFound) {
            System.out.println("No appointments available on " + specifiedDate + ".");
        }
    }

    private static void updateAppointment(Scanner scanner) {
        System.out.println("Existing Appointments:");
        viewAppointments();

        System.out.print("Enter the ID of the appointment to update: ");
        String appointmentId = scanner.nextLine();
        Appointment selectedAppointment = null;

        for (Appointment appointment : appointments) {
            if (appointment.id.equals(appointmentId)) {
                selectedAppointment = appointment;
                break;
            }
        }

        if (selectedAppointment == null) {
            System.out.println("Appointment not found!");
            return;
        }

        System.out.print("Enter the new appointment date (yyyy-mm-dd) (or press Enter to keep the current date: " +
                selectedAppointment.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "): ");
        String newDateInput = scanner.nextLine();
        LocalDate newDate = selectedAppointment.getDate(); // Default to current date

        if (!newDateInput.isEmpty()) {
            try {
                newDate = LocalDate.parse(newDateInput, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format! Please enter the date in yyyy-mm-dd format.");
                return;
            }
        }

        String newDay = newDate.getDayOfWeek().name(); // Get day of the week from new date

        System.out.print("Enter the new appointment time (or press Enter to keep the current time: " +
                selectedAppointment.getTime() + "): ");
        String newTime = scanner.nextLine();
        if (newTime.isEmpty()) {
            newTime = selectedAppointment.getTime();
        }

        if (!selectedAppointment.doctor.availability.containsKey(newDay) ||
                !selectedAppointment.doctor.isAvailable(newDay, newTime)) {
            System.out.println("The selected dermatologist is not available on the new date and time.");
            return;
        }

        selectedAppointment.setDate(newDate);
        selectedAppointment.setTime(newTime);

        System.out.println("Appointment updated successfully!");
    }

    private static void searchAppointment(Scanner scanner) {
        System.out.println("Search for Appointment:");
        System.out.println("1. By Appointment ID");
        System.out.println("2. By Patient Name");
        System.out.println("3. By Appointment Date");
        System.out.print("Select search option: ");
        int searchOption = scanner.nextInt();
        scanner.nextLine();

        switch (searchOption) {

            case 1:
                System.out.print("Enter appointment ID: ");
                String appointmentId = scanner.nextLine();
                boolean foundById = false;

                for (Appointment appointment : appointments) {
                    if (appointment.id.equalsIgnoreCase(appointmentId)) {
                        display(appointment);
                        foundById = true;
                    }
                }

                if (!foundById) {
                    System.out.println("No appointment found with ID: " + appointmentId);
                }
                break;

            case 2:
                System.out.print("Enter patient name: ");
                String patientName = scanner.nextLine();
                boolean foundByName = false;

                for (Appointment appointment : appointments) {
                    if (appointment.getPatient().getName().equalsIgnoreCase(patientName)) {
                        display(appointment);
                        foundByName = true;
                    }
                }

                if (!foundByName) {
                    System.out.println("No appointment found for patient: " + patientName);
                }
                break;


            case 3:
                System.out.print("Enter appointment date (yyyy-mm-dd): ");
                String dateInput = scanner.nextLine();
                LocalDate searchDate;

                try {
                    searchDate = LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    boolean foundByDate = false;

                    for (Appointment appointment : appointments) {
                        if (appointment.getDate().equals(searchDate)) {
                            display(appointment);
                            foundByDate = true;
                        }
                    }
                    if (!foundByDate) {
                        System.out.println("No appointments found on date: " + dateInput);
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format! Please enter the date in the format yyyy-mm-dd.");
                }
                break;

            default:
                System.out.println("Invalid search option.");
        }
    }

    private static void display(Appointment appointment) {
        System.out.printf("Appointment ID: %s, Doctor: %s, Date: %s, Time: %s, Patient: %s, Status: %s%n",
                appointment.id,
                appointment.doctor.getName(),
                appointment.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                appointment.getTime(),
                appointment.getPatient().getName(),
                appointment.getStatus());
    }


    private static void payForAppointment(Scanner scanner) {

        System.out.println("++++++++++++++++++++++++++++++++++++++++");
        System.out.println("          Unpaid Appointments           ");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++");

        System.out.printf("%-10s | %-15s | %-10s | %-10s | %-10s | %-15s | %-15s | %-10s | %-10s%n",
                "ID", "Doctor", "Date", "Day", "Time", "Patient",
                "Treatment Fee", "Reg. Fee", "Total Fee", "Status");

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        boolean hasUnpaidAppointments = false;
        for (Appointment appointment : appointments) {
            if (!appointment.getStatus().equals("Paid")) {
                hasUnpaidAppointments = true;
                System.out.printf("%-10s | %-15s | %-10s | %-10s | %-10s | %-15s | %-15.2f | %-10.2f | %-10.2f | %-10s%n",
                        appointment.id,
                        appointment.doctor.getName(),
                        appointment.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        appointment.getDay(),
                        appointment.getTime(),
                        appointment.getPatient().getName(),
                        appointment.getTreatmentFee(),
                        appointment.getRegistrationFee(),
                        appointment.getTotalFee(),
                        appointment.getStatus());
            }
        }

        if (!hasUnpaidAppointments) {
            System.out.println("No unpaid appointments available.");
            return;
        }

        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        System.out.print("Enter the ID of the appointment to pay for (or type 'exit' to cancel): ");
        String appointmentId = scanner.nextLine();

        if (appointmentId.equalsIgnoreCase("exit")) {
            return;
        }

        for (Appointment appointment : appointments) {
            if (appointment.id.equals(appointmentId) && !appointment.getStatus().equals("Paid")) {

                double subtotal = appointment.getTreatmentFee() + appointment.getRegistrationFee();
                double tax = subtotal * 0.025;
                double finalAmount = Math.round(subtotal + tax);
                appointment.setStatus("Paid");

                // Short Explanation of Calculation
                System.out.println("The final amount is calculated as follows:");
                System.out.println("1. Treatment Cost + Registration Fee = Subtotal");
                System.out.println("2. Subtotal + 2.5% Tax = Final Amount (rounded to the nearest whole number)");

                // Final invoice display
                System.out.println("++++++++++++++++++++++++++++++++++++++++");
                System.out.println("               Invoice                  ");
                System.out.println("+++++++++++++++++++++++++++++++++++++++++");
                System.out.printf("%-20s | %-10s%n", "Appointment ID:", appointment.id);
                System.out.printf("%-20s | LKR %.2f%n", "Treatment Cost:", appointment.getTreatmentFee());
                System.out.printf("%-20s | LKR %.2f%n", "Registration Fee:", appointment.getRegistrationFee());
                System.out.printf("%-20s | LKR %.2f%n", "Subtotal:", subtotal);
                System.out.printf("%-20s | LKR %.2f%n", "Tax (2.5%):", tax);
                System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
                System.out.printf("%-20s | LKR %.2f%n", "Final Amount:", finalAmount);
                System.out.println("+++++++++++++++++++++++++++++++++++++++++++");

                return;
            }
        }

        System.out.println("Appointment not found or already paid.");
    }

}

