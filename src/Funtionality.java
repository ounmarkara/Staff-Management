import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Funtionality {

    private static List<StaffMember> employees = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public void mockData() {
        employees.add(new SalariedEmployee("Messi", "Miami", 5000.0, 200));
        employees.add(new HourlySalaryEmployee("Peter", "Saudi Araby", 30, 35.0));
        employees.add(new Volunteer("David Beckham", "Miami", 100));
        employees.add(new SalariedEmployee("Jason", "Boeng Srolang", 3000, 20));
        employees.add(new SalariedEmployee("Luvi Fn", "Olympic", 4000, 10));
        employees.add(new SalariedEmployee("Koko", "Boeng Keng Korng", 800, 40));
        employees.add(new SalariedEmployee("Innocent", "TaKmav St 6A", 800, 50));
    }

    public void displayMenu() {
        CellStyle center = new CellStyle(CellStyle.HorizontalAlign.center);

        Table menuTable = new Table(1, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
        menuTable.addCell("STAFF MANAGEMENT SYSTEM", center);
        menuTable.addCell("1. Insert Employee", center);
        menuTable.addCell("2. Update Employee", center);
        menuTable.addCell("3. Display Employee", center);
        menuTable.addCell("4. Remove Employee", center);
        menuTable.addCell("5. Exit", center);

        System.out.println(menuTable.render());
        System.out.println("--------------------");
        System.out.print("=> Choose an option: ");
    }

    public void insertEmployee() {
        CellStyle center = new CellStyle(CellStyle.HorizontalAlign.center);

        boolean retry = true;
        while (retry) {
            System.out.println("\n--- Insert Employee ---\n");
            System.out.println("Choose Type: ");

            Table typeTable = new Table(3, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
            typeTable.setColumnWidth(2, 0, 50);

            typeTable.addCell("1. Volunteer", center);
            typeTable.addCell("2. Salaried Employee", center);
            typeTable.addCell("3. Hourly Employee", center);

            System.out.println(typeTable.render());
            System.out.println("---------------------------");

            System.out.print(" => Enter employee type : ");
            int type;
            try {
                type = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                System.out.print("Do you want to try again? (Y/N): ");
                String option = scanner.nextLine().trim().toUpperCase();
                if (!option.equals("Y")) {
                    retry = false;
                }
                continue;
            }

            int nextId = employees.size() + 1;
            System.out.println("ID: " + nextId);

            String name = validateName();

            System.out.print("=> Enter Address: ");
            String address = scanner.nextLine();

            try {
                switch (type) {
                    case 1:
                        System.out.print("=> Enter salary: ");
                        double salary = Double.parseDouble(scanner.nextLine());
                        employees.add(new Volunteer(name, address, salary));
                        break;
                    case 2:
                        System.out.print("=> Enter hours worked: ");
                        int hoursWorked = Integer.parseInt(scanner.nextLine());
                        System.out.print("=> Enter rate per hour: ");
                        double rate = Double.parseDouble(scanner.nextLine());
                        employees.add(new HourlySalaryEmployee(name, address, hoursWorked, rate));
                        break;
                    case 3:
                        System.out.print("=> Enter base salary: ");
                        double baseSalary = Double.parseDouble(scanner.nextLine());
                        System.out.print("=> Enter bonus: ");
                        double bonus = Double.parseDouble(scanner.nextLine());
                        employees.add(new SalariedEmployee(name, address, baseSalary, bonus));
                        break;
                    default:
                        System.out.println("Invalid employee type.");
                        System.out.print("Do you want to try again? (Y/N): ");
                        String option = scanner.nextLine().trim().toUpperCase();
                        if (!option.equals("Y")) {
                            retry = false;
                        }
                        continue;
                }
                System.out.println("* You added " + name + " of type " + type + " Successfully! *");
                displayEmployees();
                retry = false;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                System.out.print("Do you want to try again? (Y/N): ");
                String option = scanner.nextLine().trim().toUpperCase();
                if (!option.equals("Y")) {
                    retry = false;
                }
            }
        }
    }

    private static String validateName() {
        String name;
        while (true) {
            System.out.print("=> Enter Name: ");
            name = scanner.nextLine().trim();
            if (name.matches("[a-zA-Z ]+")) {
                break;
            } else {
                System.out.println("Invalid name. Only alphabetic characters and spaces are allowed.");
            }
        }
        return name;
    }

    public void displayEmployees() {
        int currentPage = 1;
        int pageSize = 3;
        int totalEmployees = employees.size();
        int totalPages = (int) Math.ceil((double) totalEmployees / pageSize);
        CellStyle center = new CellStyle(CellStyle.HorizontalAlign.center);

        while (true) {
            if (employees.isEmpty()) {
                System.out.println("No employees found.");
            } else {
                Table table = createEmployeeTable(center, currentPage, pageSize);
                System.out.println(table.render());
            }

            System.out.println("\n--- Display Employee (Page " + currentPage + " of " + totalPages + ") ---\n");

            System.out.print("1. First Page\t");
            System.out.print("2. Next Page\t");
            System.out.print("3. Previous Page\t");
            System.out.print("4. Last Page\t");
            System.out.println("5. Exit");

            System.out.print("Choose an option: ");
            String paginationOption = scanner.nextLine();

            switch (paginationOption) {
                case "1":
                    currentPage = 1;
                    break;
                case "2":
                    if (currentPage < totalPages) {
                        currentPage++;
                    } else {
                        System.out.println("You are already on the last page.");
                    }
                    break;
                case "3":
                    if (currentPage > 1) {
                        currentPage--;
                    } else {
                        System.out.println("You are already on the first page.");
                    }
                    break;
                case "4":
                    currentPage = totalPages;
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static Table createEmployeeTable(CellStyle center, int currentPage, int pageSize) {
        // Determine dynamic headers based on employee types in the current page
        List<String> headers = new ArrayList<>();
        headers.add("Type");
        headers.add("ID");
        headers.add("Name");
        headers.add("Address");

        for (int i = (currentPage - 1) * pageSize; i < Math.min(currentPage * pageSize, employees.size()); i++) {
            StaffMember employee = employees.get(i);
            if (employee instanceof HourlySalaryEmployee) {
                if (!headers.contains("Rate")) headers.add("Rate");
                if (!headers.contains("Hour")) headers.add("Hour");
            } else if (employee instanceof SalariedEmployee) {
                if (!headers.contains("Bonus")) headers.add("Bonus");
            }
            if (!headers.contains("Salary")) headers.add("Salary");
            if (!headers.contains("Pay")) headers.add("Pay");
        }

        Table table = new Table(headers.size(), BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
        for (String header : headers) {
            table.addCell(header, center);
        }

        int startIndex = (currentPage - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, employees.size());

        for (int i = startIndex; i < endIndex; i++) {
            StaffMember employee = employees.get(i);
            addEmployeeToTable(table, employee, headers);
        }

        return table;
    }

    public static void addEmployeeToTable(Table table, StaffMember employee, List<String> headers) {
        for (String header : headers) {
            switch (header) {
                case "Type":
                    table.addCell(employee.getClass().getSimpleName());
                    break;
                case "ID":
                    table.addCell(String.valueOf(employee.getId()));
                    break;
                case "Name":
                    table.addCell(employee.getName());
                    break;
                case "Address":
                    table.addCell(employee.getAddress());
                    break;
                case "Bonus":
                    if (employee instanceof SalariedEmployee) {
                        table.addCell(String.format("%.2f$", ((SalariedEmployee) employee).getBonus()));
                    } else {
                        table.addCell("---");
                    }
                    break;
                case "Rate":
                    if (employee instanceof HourlySalaryEmployee) {
                        table.addCell(String.format("%.2f$", ((HourlySalaryEmployee) employee).getRate()));
                    } else {
                        table.addCell("---");
                    }
                    break;
                case "Hour":
                    if (employee instanceof HourlySalaryEmployee) {
                        table.addCell(String.valueOf(((HourlySalaryEmployee) employee).getHoursWorked()) + "h");
                    } else {
                        table.addCell("---");
                    }
                    break;
                case "Salary":
                    if (employee instanceof Volunteer) {
                        table.addCell(String.format("%.2f$", ((Volunteer) employee).getSalary()));
                    } else if (employee instanceof SalariedEmployee) {
                        table.addCell(String.format("%.2f$", ((SalariedEmployee) employee).getSalary()));
                    } else if (employee instanceof HourlySalaryEmployee) {
                        double rate = ((HourlySalaryEmployee) employee).getRate();
                        int hoursWorked = ((HourlySalaryEmployee) employee).getHoursWorked();
                        double salary = rate * hoursWorked;
                        table.addCell(String.format("%.2f$", salary));
                    } else {
                        table.addCell("---");
                    }
                    break;
                case "Pay":
                    if (employee instanceof SalariedEmployee) {
                        double pay = ((SalariedEmployee) employee).getSalary() + ((SalariedEmployee) employee).getBonus();
                        table.addCell(String.format("%.2f$", pay));
                    } else {
                        table.addCell(String.format("%.2f$", employee.pay()));
                    }
                    break;
            }
        }
    }

    public void displaySingleEmployee(StaffMember employee) {
        CellStyle center = new CellStyle(CellStyle.HorizontalAlign.center);

        List<String> headers = new ArrayList<>();
        headers.add("Type");
        headers.add("ID");
        headers.add("Name");
        headers.add("Address");

        if (employee instanceof HourlySalaryEmployee) {
            headers.add("Rate");
            headers.add("Hour");
        } else if (employee instanceof SalariedEmployee) {
            headers.add("Bonus");
        }
        headers.add("Salary");
        headers.add("Pay");

        Table employeeTable = new Table(headers.size(), BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
        for (String header : headers) {
            employeeTable.addCell(header, center);
        }

        addEmployeeToTable(employeeTable, employee, headers);
        System.out.println(employeeTable.render());
    }

    public void updateEmployee() {
        CellStyle center = new CellStyle(CellStyle.HorizontalAlign.center);
        boolean retry = true;
        while (retry) {
            System.out.println("\n======* Update Employee Information *======");
            System.out.print("=> Enter or Search ID to Update: ");
            int id;
            try {
                id = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                continue;
            }

            Optional<StaffMember> employeeOpt = employees.stream()
                    .filter(e -> e.getId() == id)
                    .findFirst();

            if (employeeOpt.isPresent()) {
                StaffMember employee = employeeOpt.get();

                System.out.println("\n--- Employee Details ---");
                displaySingleEmployee(employee);

                boolean updateRetry = true;
                while (updateRetry) {
                    System.out.println("\nSelect one column to update:");
                    System.out.print("1.Name\t");
                    System.out.print("2.Address\t");
                    if (employee instanceof HourlySalaryEmployee) {
                        System.out.print("3.Rate\t");
                    } else if (employee instanceof SalariedEmployee) {
                        System.out.print("3.Salary\t");
                    } else if (employee instanceof Volunteer) {
                        System.out.println("3.Salary\t");
                    }
                    System.out.println("4.Cancel");

                    System.out.print("=> Choose an option: ");
                    String updateOption = scanner.nextLine();

                    switch (updateOption) {
                        case "1":
                            System.out.print("Enter new name: ");
                            String name = scanner.nextLine();
                            if (!name.isEmpty()) {
                                employee.setName(name);
                                System.out.println("*Name updated successfully!*");
                                displaySingleEmployee(employee);
                            }
                            break;
                        case "2":
                            System.out.print("Enter new address: ");
                            String address = scanner.nextLine();
                            if (!address.isEmpty()) {
                                employee.setAddress(address);
                                System.out.println("*Address updated successfully!*");
                                displaySingleEmployee(employee);
                            }
                            break;
                        case "3":
                            if (employee instanceof HourlySalaryEmployee) {
                                System.out.print("Enter new rate: ");
                                String rateInput = scanner.nextLine();
                                if (!rateInput.isEmpty()) {
                                    try {
                                        double rate = Double.parseDouble(rateInput);
                                        ((HourlySalaryEmployee) employee).setRate(rate);
                                        System.out.println("*Rate updated successfully!*");
                                        displaySingleEmployee(employee);
                                    } catch (NumberFormatException e) {
                                        System.out.println("Invalid input. Rate must be a number.");
                                    }
                                }
                            } else if (employee instanceof SalariedEmployee) {
                                System.out.print("Enter new salary: ");
                                String salaryInput = scanner.nextLine();
                                if (!salaryInput.isEmpty()) {
                                    try {
                                        double salary = Double.parseDouble(salaryInput);
                                        ((SalariedEmployee) employee).setSalary(salary);
                                        System.out.println("Salary updated successfully!");
                                        displaySingleEmployee(employee);
                                    } catch (NumberFormatException e) {
                                        System.out.println("Invalid input. Salary must be a number.");
                                    }
                                }
                            } else if (employee instanceof Volunteer) {
                                System.out.print("Enter new salary: ");
                                String salaryInput = scanner.nextLine();
                                if (!salaryInput.isEmpty()) {
                                    try {
                                        double salary = Double.parseDouble(salaryInput);
                                        ((Volunteer) employee).setSalary(salary);
                                        System.out.println("Salary updated successfully!");
                                        displaySingleEmployee(employee);
                                    } catch (NumberFormatException e) {
                                        System.out.println("Invalid input. Salary must be a number.");
                                    }
                                }
                            }
                            break;
                        case "4":
                            System.out.println("Update canceled.");
                            updateRetry = false;
                            break;
                        default:
                            System.out.println("Invalid option. Please try again.");
                    }
                }

                retry = false;
            } else {
                System.out.println("Employee not found.");
                System.out.print("Do you want to try again? (Y/N): ");
                String option = scanner.nextLine().trim().toUpperCase();
                if (!option.equals("Y")) {
                    retry = false;
                }
            }
        }
    }

    public void removeEmployee() {
        boolean retry = true;
        while (retry) {
            System.out.println("\n--- Remove Employee ---");
            System.out.print("Enter employee ID: ");
            int id;
            try {
                id = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                continue;
            }

            List<StaffMember> remainingEmployees = employees.stream()
                    .filter(e -> e.getId() != id)
                    .collect(Collectors.toList());

            if (remainingEmployees.size() < employees.size()) {
                employees = remainingEmployees;
                System.out.println("Employee removed successfully!");
                displayEmployees();
                retry = false;
            } else {
                System.out.println("Employee not found.");
                System.out.print("Do you want to try again? (Y/N): ");
                String option = scanner.nextLine().trim().toUpperCase();
                if (!option.equals("Y")) {
                    retry = false;
                }
            }
        }
    }
}