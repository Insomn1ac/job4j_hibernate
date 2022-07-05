import org.junit.Assert;
import org.junit.Test;

public class TriggerTest {

    @Test
    public void when1Then1() {
        Assert.assertEquals(1, new Trigger().trigger());
    }
} 