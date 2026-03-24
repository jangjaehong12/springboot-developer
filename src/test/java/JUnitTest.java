import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JUnitTest {

    @DisplayName("1+2는 3이다")
    @Test
    public void junitTest() {
        int n1 = 1;
        int n2 = 2;
        int sum = n1 + n2;
        Assertions.assertEquals(3, sum);
    }
    @DisplayName("1+3은 4이다.")
    @Test
    public void JUnitFailedTest() {

            int n1 = 1;
            int n2 = 3;
            int sum = n1 + n2;
            Assertions.assertEquals(3, sum);
        }
}
