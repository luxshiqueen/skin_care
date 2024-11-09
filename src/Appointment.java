import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Appointment class
public class Appointment {

    String id;
    Dermatologist doctor;
    private LocalDate date;
    private String day;
    private String time;
    private Patient patient;
    private double treatmentFee;
    private double registrationFee;
    private double totalFee;
    private String status;

    // Constructor
    public Appointment(String id, Dermatologist doctor, LocalDate date, String time, Patient patient,
                       double treatmentFee, double registrationFee, double totalFee, String status) {
        this.id = id;
        this.doctor = doctor;
        this.date = date;
        this.day = date.getDayOfWeek().name();
        this.time = time;
        this.patient = patient;
        this.treatmentFee = treatmentFee;
        this.registrationFee = registrationFee;
        this.totalFee = totalFee;
        this.status = status;
    }

    // Getter and Setter methods
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
        this.day = date.getDayOfWeek().name();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public double getTreatmentFee() {
        return treatmentFee;
    }

    public void setTreatmentFee(double treatmentFee) {
        this.treatmentFee = treatmentFee;
    }

    public double getRegistrationFee() {
        return registrationFee;
    }

    public void setRegistrationFee(double registrationFee) {
        this.registrationFee = registrationFee;
    }

    public double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Method to return detailed
    public String getDetails() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return "Appointment ID: " + id + "\n" +
                "Doctor: " + doctor.getName() + "\n" +
                "Date: " + date.format(formatter) + "\n" +
                "Day: " + day + "\n" +
                "Time: " + time + "\n" +
                "Patient: " + patient.getName() + "\n" +
                "Treatment Fee: " + treatmentFee + "\n" +
                "Registration Fee: " + registrationFee + "\n" +
                "Total Fee: " + totalFee + "\n" +
                "Status: " + status;
    }

    @Override
    public String toString() {
        return getDetails();
    }
}
