package cashregister;


import com.sun.xml.internal.bind.v2.util.ByteArrayOutputStreamEx;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CashRegisterTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStreamEx();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStream() {
        System.setOut(originalOut);
    }

    @Test
    public void should_print_the_real_purchase_when_call_process() {
        Item item[] = new Item[]{
                new Item("first", 10),
                new Item("second", 20)
        };
        Purchase purchase = new Purchase(item);
        CashRegister cashRegister = new CashRegister(new Printer());
        //when
        cashRegister.process(purchase);
        //then
        assertThat(outContent.toString()).isEqualTo("first 10.0\nsecond 20.0\n");
    }

    @Test
    public void should_print_the_stub_purchase_when_call_process() {

        String message = "mock Message";
        Purchase purchase = mock(Purchase.class);
        CashRegister cashRegister = new CashRegister(new Printer());
        when(purchase.asString()).thenReturn(message);

        cashRegister.process(purchase);

        assertThat(outContent.toString()).isEqualTo(message);

    }


    @Test
    public void should_verify_with_process_call_with_mockito() {
        Printer mockPrinter = mock(Printer.class);
        CashRegister cashRegister = new CashRegister(mockPrinter);
        Purchase mockPurchse = mock(Purchase.class);

        cashRegister.process(mockPurchse);

        verify(mockPrinter).print(mockPurchse.asString());
        verify(mockPurchse, times(2)).asString();
    }

}
