package ndr.brt;

import java.util.Date;

public class RealCommandHandler implements CommandHandler {
    private EventStore eventStore;

    public RealCommandHandler() {
        this(new RealEventStore());
    }

    public RealCommandHandler(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    @Override
    public void handle(Object any) {
        eventStore.store(new Stamped(new Date()));
    }
}
