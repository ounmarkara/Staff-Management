public class SalariedEmployee extends StaffMember {
    private double bonus;
    private double salary;

    public SalariedEmployee(String name, String address, double salary, double bonus) {
        super(name, address);
        this.salary = salary;
        this.bonus = bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getBonus() {
        return bonus;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public double pay() {
        return salary + bonus;
    }

    @Override
    public String toString() {
        return "SalariedEmployee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", salary=" + salary +
                ", bonus=" + bonus +
                '}';
    }
}