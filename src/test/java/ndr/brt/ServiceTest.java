package ndr.brt;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ServiceTest {

    @Mock CommandHandler commandHandler;

    @Test
    public void stamp_invokes_command() throws Exception {
        Service service = new Service(commandHandler);

        service.stamp();

        verify(commandHandler).handle(isA(Stamp.class));
    }

}