package ndr.brt;

import java.util.Date;

public class RealCommandHandler implements CommandHandler {
    private EventStore eventStore;
    private EventPublisher eventPublisher;

    public RealCommandHandler() {
        this(new RealEventStore(), new RealEventPublisher());
    }

    public RealCommandHandler(EventStore eventStore, EventPublisher eventPublisher) {
        this.eventStore = eventStore;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void handle(Object any) {
        Stamped event = new Stamped(new Date());
        eventStore.store(event);
        eventPublisher.publish(event);
    }
}
