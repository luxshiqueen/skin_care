public class Person {
    private String id;
    private String name;

    // Constructor
    public Person(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Method to get details of the person
    public String getDetails() {
        return "Name: " + name + ", ID: " + id;
    }
}
