package ndr.brt;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RealCommandHandlerTest {

    @Mock private EventStore eventStore;

    @Test
    public void stamp_store_an_event_with_the_actual_date() throws Exception {
        RealCommandHandler handler = new RealCommandHandler(eventStore);

        handler.handle(new Stamp());

        verify(eventStore).store(new Stamped(any(Date.class)));
    }
}