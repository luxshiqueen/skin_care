class Patient extends Person {
    private String nic;
    private String email;
    private String phone;

    public Patient(String id, String name, String nic, String email, String phone) {
        super(id, name);
        this.nic = nic;
        this.email = email;
        this.phone = phone;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String getDetails() {
        return "Patient Name: " + getName() + ", NIC: " + nic + ", Email: " + email + ", Phone: " + phone;
    }
}
