import org.junit.jupiter.api.Test;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertTimeout;

public class StudentTest {

    @Test
    public void testWire0() {
        assertTimeout(Duration.ofMillis(1000), () -> {
            Utilities.test("./test/inputs/wire0.in");
        });
    }
    @Test
    public void testWire1() {
        assertTimeout(Duration.ofMillis(1000), () -> {
            Utilities.test("./test/inputs/wire1.in");
        });
    }

}
