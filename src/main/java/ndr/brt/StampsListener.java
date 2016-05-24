package ndr.brt;

import com.google.common.eventbus.Subscribe;

public class StampsListener {
    private Repository repository;

    public StampsListener() {
        this(null); // TODO: implement repository
    }

    public StampsListener(Repository repository) {
        this.repository = repository;
    }

    @Subscribe
    public void consume(Stamped event) {
        repository.insert(event.getDate());
    }
}
