package ndr.brt;

import com.google.common.eventbus.EventBus;

import java.util.Date;

public class RealCommandHandler implements CommandHandler {
    private EventStore eventStore;
    private EventBus eventBus;

    public RealCommandHandler() {
        this(new RealEventStore(), createEventBus());
    }

    private static EventBus createEventBus() {
        EventBus eventBus = new EventBus();
        eventBus.register(new StampsListener());
        return eventBus;
    }

    public RealCommandHandler(EventStore eventStore, EventBus eventBus) {
        this.eventStore = eventStore;
        this.eventBus = eventBus;
    }

    @Override
    public void handle(Object any) {
        Stamped event = new Stamped(new Date());
        eventStore.store(event);
        eventBus.post(event);
    }
}
