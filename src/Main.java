import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    private static List<StaffMember> employees = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        mockData();
        while (true) {
            displayMenu();
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    insertEmployee();
                    break;
                case "2":
                    updateEmployee();
                    break;
                case "3":
                    displayEmployees();
                    break;
                case "4":
                    removeEmployee();
                    break;
                case "5":
                    System.out.println("*Good bye!*😭😭");
                    return;
                default:
                    System.out.println("=====================");
            }
        }
    }

    private static void mockData() {
        employees.add(new SalariedEmployee("Messi", "Miami", 5000.0, 200));
        employees.add(new HourlySalaryEmployee("Peter", "Saudi Araby", 30, 35.0));
        employees.add(new Volunteer("David Beckham", "Miami", 100));
        employees.add(new SalariedEmployee("Jason", "Boeng Srolang", 3000, 20));
        employees.add(new SalariedEmployee("Luvi Fn", "Olympic", 3000, 20));
        employees.add(new SalariedEmployee("Koko", "Boeng Keng Korng", 3000, 20));
        employees.add(new SalariedEmployee("Innocent", "TaKmav St 6A", 3000, 20));
    }

    private static void displayMenu() {
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
        System.out.print("Choose an option: ");
    }

    private static void insertEmployee() {
        CellStyle center = new CellStyle(CellStyle.HorizontalAlign.center);

        boolean retry = true;
        while (retry) {
            System.out.println("\n--- Insert Employee ---\n");
            System.out.println("Choose Type: ");

            // Display employee type options in a table
            Table typeTable = new Table(3, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
            typeTable.setColumnWidth(2, 30, 50);

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
                    retry = false; // Exit the loop if the user chooses not to retry
                }
                continue;
            }

            // Calculate the next available ID
            int nextId = employees.size() + 1;
            System.out.println("ID: " + nextId); // Display the ID

            // Validate name (only letters and spaces allowed)
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
                    retry = false; // Exit the loop if the user chooses not to retry
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

    private static void displayEmployees() {
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
        Table table = new Table(9, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
        table.addCell("Type", center);
        table.addCell("ID", center);
        table.addCell("Name", center);
        table.addCell("Address", center);
        table.addCell("Bonus", center);
        table.addCell("Rate", center);
        table.addCell("Hour", center);
        table.addCell("Salary", center);
        table.addCell("Pay", center);

        int startIndex = (currentPage - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, employees.size());

        for (int i = startIndex; i < endIndex; i++) {
            StaffMember employee = employees.get(i);
            addEmployeeToTable(table, employee);
        }

        return table;
    }

    private static void addEmployeeToTable(Table table, StaffMember employee) {
        table.addCell(employee.getClass().getSimpleName());
        table.addCell(String.valueOf(employee.getId()));
        table.addCell(employee.getName());
        table.addCell(employee.getAddress());

        if (employee instanceof SalariedEmployee) {
            table.addCell(String.format("%.2f$", ((SalariedEmployee) employee).getBonus()));
        } else {
            table.addCell("---");
        }

        if (employee instanceof HourlySalaryEmployee) {
            table.addCell(String.format("%.2f$", ((HourlySalaryEmployee) employee).getRate()));
        } else {
            table.addCell("---");
        }

        if (employee instanceof HourlySalaryEmployee) {
            table.addCell(String.valueOf(((HourlySalaryEmployee) employee).getHoursWorked()) + "h");
        } else {
            table.addCell("---");
        }

        if (employee instanceof Volunteer) {
            table.addCell(String.format("%.2f$", ((Volunteer) employee).getSalary()));
        } else if (employee instanceof SalariedEmployee) {
            table.addCell(String.format("%.2f$", ((SalariedEmployee) employee).getSalary()));
        } else if (employee instanceof HourlySalaryEmployee) {
            double rate = ((HourlySalaryEmployee) employee).getRate();
            int hoursWorked = ((HourlySalaryEmployee) employee).getHoursWorked();
            double salary = rate * hoursWorked;
            table.addCell(String.format(" %.2f$", salary));
        } else {
            double salaryFix = Math.random() * 10000;
            table.addCell(String.format("%.2f$", salaryFix));
        }

        if (employee instanceof SalariedEmployee) {
            double pay = ((SalariedEmployee) employee).getSalary() + ((SalariedEmployee) employee).getBonus();
            table.addCell(String.format("%.2f$", pay));
        } else {
            table.addCell(String.format("%.2f$", employee.pay()));
        }
    }

    private static void displaySingleEmployee(StaffMember employee) {
        CellStyle center = new CellStyle(CellStyle.HorizontalAlign.center);

        Table employeeTable = new Table(9, BorderStyle.UNICODE_ROUND_BOX_WIDE, ShownBorders.ALL);
        employeeTable.addCell("Type", center);
        employeeTable.addCell("ID", center);
        employeeTable.addCell("Name", center);
        employeeTable.addCell("Address", center);
        employeeTable.addCell("Bonus", center);
        employeeTable.addCell("Rate", center);
        employeeTable.addCell("Hour", center);
        employeeTable.addCell("Salary", center);
        employeeTable.addCell("Pay", center);

        addEmployeeToTable(employeeTable, employee);

        System.out.println(employeeTable.render());
    }

    private static void updateEmployee() {
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
                    System.out.print("1. Name\t");
                    System.out.print("2. Address\t");
                    if (employee instanceof HourlySalaryEmployee) {
                        System.out.print("3. Rate\t");
                    } else if (employee instanceof SalariedEmployee) {
                        System.out.print("3. Salary\t");
                    } else if (employee instanceof Volunteer) {
                        System.out.println("3. Salary\t");
                    }
                    System.out.println("4. Cancel");

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

    private static void removeEmployee() {
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