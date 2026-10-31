import java.util.*;

public class HelpdeskService {
    private final List<Ticket> tickets = new ArrayList<>();
    private int nextTicketId = 1001;

    public Ticket createTicket(User user, String title,
                               String description,
                               Category category,
                               Priority priority) {
        Ticket ticket = new Ticket(
            nextTicketId, user, title, description,
            category, priority
        );

        tickets.add(ticket);
        nextTicketId++;

        return ticket;
    }

    public List<Ticket> getAllTickets() {
        return new ArrayList<>(tickets);
    }

    public Ticket findTicket(int ticketId) {
        for (Ticket ticket : tickets) {
            if (ticket.getTicketId() == ticketId) {
                return ticket;
            }
        }

        return null;
    }

    public void updateTicket(int ticketId, String title,
                             String description,
                             Category category,
                             Priority priority) {
        Ticket ticket = findTicket(ticketId);

        if (ticket == null) {
            throw new IllegalArgumentException(
                "Ticket not found."
            );
        }

        ticket.updateDetails(
            title, description, category, priority
        );
    }

    public void changeTicketStatus(int ticketId,
                                   TicketStatus status) {
        Ticket ticket = findTicket(ticketId);

        if (ticket == null) {
            throw new IllegalArgumentException(
                "Ticket not found."
            );
        }

        ticket.changeStatus(status);
    }
}