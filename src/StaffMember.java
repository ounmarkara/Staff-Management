public abstract class StaffMember {
    private static int nextId = 1;

    protected int id;
    protected String name;
    protected String address;

    public StaffMember(String name, String address) {
        this.id = nextId++;
        this.name = name;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public abstract double pay();

    @Override
    public String toString() {
        return "StaffMember{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}