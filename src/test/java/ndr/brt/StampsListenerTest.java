package ndr.brt;

import com.google.common.eventbus.EventBus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class StampsListenerTest {

    @Mock private Repository repository;

    @Test
    public void stamped_insert_new_date_in_collection() throws Exception {
        EventBus eventBus = new EventBus();
        eventBus.register(new StampsListener(repository));
        Date anyDate = new Date();

        eventBus.post(new Stamped(anyDate));

        verify(repository).insert(anyDate);
    }
}