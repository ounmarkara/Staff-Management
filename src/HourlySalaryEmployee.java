public class HourlySalaryEmployee extends StaffMember {
    private int hoursWorked;
    private double rate;

    public HourlySalaryEmployee(String name, String address, int hoursWorked, double rate) {
        super(name, address);
        this.hoursWorked = hoursWorked;
        this.rate = rate;
    }

    public void setHoursWorked(int hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public double pay() {
        return hoursWorked * rate;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    public double getRate() {
        return rate;
    }

    @Override
    public String toString() {
        return "HourlySalaryEmployee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", hoursWorked=" + hoursWorked +
                ", rate=" + rate +
                '}';
    }
}