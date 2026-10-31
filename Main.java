import java.util.*;

public class Main {
    private static final Scanner scanner =
        new Scanner(System.in);

    private static final HelpdeskService service =
        new HelpdeskService();

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            System.out.println("\n=== STUDENT HELPDESK ===");
            System.out.println("1. Create Ticket");
            System.out.println("2. View All Tickets");
            System.out.println("3. Search Ticket");
            System.out.println("4. Update Ticket");
            System.out.println("5. Change Ticket Status");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        createTicket();
                        break;
                    case "2":
                        viewAllTickets();
                        break;
                    case "3":
                        searchTicket();
                        break;
                    case "4":
                        updateTicket();
                        break;
                    case "5":
                        changeStatus();
                        break;
                    case "6":
                        running = false;
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println(
                            "Invalid choice. Enter 1 to 6."
                        );
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        scanner.close();
    }

//---------------------------------------------------------------------------------------

    private static String readRequired(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println("Input cannot be empty.");
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            String value = readRequired(prompt);

            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
            }
        }
    }

    private static <E extends Enum<E>> E readEnum(
            String prompt, Class<E> enumClass) {
        while (true) {
            try {
                return Enum.valueOf(
                    enumClass,
                    readRequired(prompt).toUpperCase()
                );
            } catch (IllegalArgumentException e) {
                System.out.println(
                    "Invalid value. Options: "
                    + java.util.Arrays.toString(
                        enumClass.getEnumConstants()
                    )
                );
            }
        }
    }

    private static User readUser() {
        int id = readInt("Student ID: ");
        String name = readRequired("Student name: ");
        String email = readRequired("Student email: ");

        return new User(id, name, email);
    }

//-------------------------------START OF SWITCH CASES------------------------------------

    private static void createTicket() {
        User user = readUser();
        String title = readRequired("Ticket title: ");
        String description =
            readRequired("Description: ");
        Category category = readEnum(
            "Category " +
            java.util.Arrays.toString(Category.values()) + ": ",
            Category.class
        );
        Priority priority = readEnum(
            "Priority " +
            java.util.Arrays.toString(Priority.values()) + ": ",
            Priority.class
        );

        Ticket ticket = service.createTicket(
            user, title, description, category, priority
        );

        System.out.println("Ticket created successfully!");
        System.out.println(ticket);
    }

    private static void viewAllTickets() {
        if (service.getAllTickets().isEmpty()) {
            System.out.println("No tickets available.");
            return;
        }

        for (Ticket ticket : service.getAllTickets()) {
            System.out.println(ticket);
            System.out.println("--------------------");
        }
    }

    private static void searchTicket() {
        int id = readInt("Enter ticket ID: ");
        Ticket ticket = service.findTicket(id);
        
        if (ticket == null) {
            System.out.println("Ticket not found.");
        } else {
            System.out.println(ticket);
        }
    }

    private static void updateTicket() {
        int id = readInt("Ticket ID: ");

        String title = readRequired("New title: ");
        String description =
            readRequired("New description: ");
        Category category = readEnum(
            "New category: ", Category.class
        );
        Priority priority = readEnum(
            "New priority: ", Priority.class
        );
        service.updateTicket(
            id, title, description, category, priority
        );

        System.out.println("Ticket updated successfully.");
    }

    private static void changeStatus() {
        int id = readInt("Ticket ID: ");
        TicketStatus status = readEnum(
            "New status " +
            java.util.Arrays.toString(TicketStatus.values())
            + ": ", TicketStatus.class
        );

        service.changeTicketStatus(id, status);
        System.out.println("Status updated successfully.");
    }
}
