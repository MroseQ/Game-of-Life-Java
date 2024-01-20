package POLO2;

public class SystemEvent {
    private final String message;

    public SystemEvent(String message) {
        this.message = message;
        System.out.println(message);
    }

    public String getEvent() {
        return this.message;
    }

}
