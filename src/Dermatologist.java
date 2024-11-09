import java.util.HashMap;

// Dermatologist class
public class Dermatologist extends Person {
    HashMap<String, String[]> availability;
    HashMap<String, HashMap<String, Boolean>> bookedSlots;


    public Dermatologist(String id, String name) {
        super(id, name);
        this.availability = new HashMap<>();
        this.bookedSlots = new HashMap<>();
        initializeAvailability();
    }


    private void initializeAvailability() {
        String[] mondayTimes = {"10:00AM", "10:15AM", "10:30AM", "10:45AM", "11:00AM", "11:15AM", "11:30AM", "11:45AM", "12:00PM", "12:15PM", "12:30PM", "12:45PM"};
        String[] wednesdayTimes = {"14:00PM", "14:15PM", "14:30PM", "14:45PM", "15:00PM", "15:15PM", "15:30PM", "15:45PM", "16:00PM", "16:15PM", "16:30PM", "16:45PM"};
        String[] fridayTimes = {"16:00PM", "16:15PM", "16:30PM", "16:45PM", "17:00PM", "17:15PM", "17:30PM", "17:45PM", "18:00PM", "18:15PM", "18:30PM", "18:45PM", "19:00PM", "19:15PM", "19:30PM", "19:45PM"};
        String[] saturdayTimes = {"09:00AM", "09:15AM", "09:30AM", "09:45AM", "10:00AM", "10:15AM", "10:30AM", "10:45AM", "11:00AM", "11:15AM", "11:30AM", "11:45AM", "12:00AM", "12:15AM"};

        // times for each day
        availability.put("MONDAY", mondayTimes);
        availability.put("WEDNESDAY", wednesdayTimes);
        availability.put("FRIDAY", fridayTimes);
        availability.put("SATURDAY", saturdayTimes);

        // booked slots for each day
        for (String day : availability.keySet()) {
            bookedSlots.put(day, new HashMap<>());
        }
    }

    // Check if the dermatologist is available at a specific time on a given day
    public boolean isAvailable(String day, String time) {
        day = day.toUpperCase();
        if (!availability.containsKey(day)) {
            return false;
        }

        // Check if the requested time slot is valid
        boolean validTimeSlot = false;
        for (String availableTime : availability.get(day)) {
            if (availableTime.equals(time)) {
                validTimeSlot = true;
                break;
            }
        }

        if (!validTimeSlot) {
            return false; // Time slot is invalid
        }

        // Check if the time slot is already booked
        Boolean isBooked = bookedSlots.get(day).get(time);
        return isBooked == null || !isBooked;
    }

    // Method to check if a specific time slot is available for booking
    public boolean checkTimeSlotAvailability(String day, String time) {
        return isAvailable(day, time);
    }

    // Book a time slot if available
    public boolean bookTimeSlot(String day, String time) {
        day = day.toUpperCase();

        // If the slot is already booked, return false
        if (bookedSlots.get(day).getOrDefault(time, false)) {
            return false;
        }

        // Otherwise, book the slot
        bookedSlots.get(day).put(time, true);
        return true;
    }


    public String getDetails() {
        return "Dermatologist Name: " + getName();
    }
}
