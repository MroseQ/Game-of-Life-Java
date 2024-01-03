package POLO2;

public class SystemEvent {
        private final String message;
        public SystemEvent(String message) {
            this.message = message;
        }
        void printEvents() {
            //out << this->wiadomosc << std::endl;
        }
        String getEvent() { return this.message; }

};
