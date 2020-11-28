package tests.java.code.line.counter;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import tests.java.code.line.counter.builder.CounterFactory;
import tests.java.code.line.counter.model.CountedLines;

public class PrinterUnitTest {

    @Mock
    private PrintStream ps;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldBePrettyOutput() throws IOException {
        final ArgumentCaptor<String> templatesCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<Object> argsCaptor = ArgumentCaptor.forClass(Object.class);
        when(ps.printf(templatesCaptor.capture(), argsCaptor.capture())).thenReturn(ps);

        final CountedLines countedLines = CounterFactory
                .build("src/test/resources/samples")
                .count();
        new Printer().print(countedLines, ps);
        final List<String> actualPrintedTemplates = templatesCaptor.getAllValues();
        Assert.assertEquals(4, actualPrintedTemplates.size());
        Assert.assertTrue(actualPrintedTemplates.stream().allMatch(s -> "%s%s : %d\n".equals(s)));

        final List<Object> actualArgsList = argsCaptor.getAllValues();
        Assert.assertEquals(12, actualArgsList.size());
        final Iterator<Object> argIt = actualArgsList.iterator();
        Assert.assertEquals("", argIt.next());
        Assert.assertEquals("samples", argIt.next());
        Assert.assertEquals(new Integer(102), ((Integer)argIt.next()));
        Assert.assertEquals("  ", argIt.next());
        Assert.assertEquals("3-lines.java", argIt.next());
        Assert.assertEquals(new Integer(3), ((Integer)argIt.next()));
        Assert.assertEquals("  ", argIt.next());
        Assert.assertEquals("Calculator.java", argIt.next());
        Assert.assertEquals(new Integer(94), ((Integer)argIt.next()));
        Assert.assertEquals("  ", argIt.next());
        Assert.assertEquals("5-lines.java", argIt.next());
        Assert.assertEquals(new Integer(5), ((Integer)argIt.next()));
        
        

    }

}
