package ndr.brt;

public interface EventStore {
    void store(Stamped stamped);
}
