package priotask;
import java.time.LocalDate;
 
public class SchoolEvent {
    private String eventName;
    private LocalDate eventDate;
    private String eventType;
 
    public SchoolEvent(String eventName, LocalDate eventDate, String eventType) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventType = eventType;
    }
 
    public String getEventName() {
        return eventName;
    }
 
    public LocalDate getEventDate() {
        return eventDate;
    }
 
    public String getEventType() {
        return eventType;
    }
}
 