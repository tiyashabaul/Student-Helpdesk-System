public class Ticket {
    private final int ticketId;
    private final User requester;
    private String title;
    private String description;
    private Category category;
    private Priority priority;
    private TicketStatus status;

    public Ticket(int ticketId, User requester,
                  String title, String description,
                  Category category, Priority priority) {
        if (requester == null) {
            throw new IllegalArgumentException(
                "Requester is required."
            );
        }

        this.ticketId = ticketId;
        this.requester = requester;
        this.status = TicketStatus.OPEN;

        updateDetails(title, description, category, priority);
    }

    public void updateDetails(String title, String description,
                              Category category, Priority priority) {
        if (status == TicketStatus.CLOSED) {
            throw new IllegalStateException(
                "Closed tickets cannot be updated."
            );
        }

        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Ticket title cannot be empty."
            );
        }

        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Description cannot be empty."
            );
        }

        if (category == null || priority == null) {
            throw new IllegalArgumentException(
                "Category and priority are required."
            );
        }

        this.title = title.trim();
        this.description = description.trim();
        this.category = category;
        this.priority = priority;
    }

    public void changeStatus(TicketStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException(
                "Status is required."
            );
        }

        boolean allowed =
            (status == TicketStatus.OPEN
                && newStatus == TicketStatus.IN_PROGRESS)
            || (status == TicketStatus.IN_PROGRESS
                && newStatus == TicketStatus.RESOLVED)
            || (status == TicketStatus.RESOLVED
                && newStatus == TicketStatus.CLOSED);

        if (!allowed) {
            throw new IllegalStateException(
                "Invalid transition: " + status + " to " + newStatus
            );
        }

        status = newStatus;
    }

    public int getTicketId() {
        return ticketId;
    }

    public User getRequester() {
        return requester;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public Priority getPriority() {
        return priority;
    }

    public TicketStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "\nTicket ID: " + ticketId
            + "\nRequester: " + requester.getName()
            + "\nEmail: " + requester.getEmail()
            + "\nTitle: " + title
            + "\nDescription: " + description
            + "\nCategory: " + category
            + "\nPriority: " + priority
            + "\nStatus: " + status;
    }
}