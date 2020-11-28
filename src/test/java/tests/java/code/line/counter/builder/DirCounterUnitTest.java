package tests.java.code.line.counter.builder;

import java.io.IOException;
import java.util.Iterator;
import org.junit.Assert;
import org.junit.Test;
import tests.java.code.line.counter.model.CountedLines;

public class DirCounterUnitTest {
    
    @Test
    public void shouldBe0() throws IOException {
        final Counter counter = CounterFactory.build("src/test/resources/samples");
        Assert.assertNotNull(counter);
        CountedLines countedLines = counter.count();
        Assert.assertNotNull(countedLines);
        Assert.assertEquals("samples", countedLines.getName());
        Assert.assertEquals(102, countedLines.getCount());        
        final Iterator<CountedLines> it = countedLines.getChildren();
        Assert.assertNotNull(it);
        countedLines = it.next();
        Assert.assertEquals("3-lines.java", countedLines.getName());
        Assert.assertEquals(3, countedLines.getCount());  
        countedLines = it.next();
        Assert.assertEquals("Calculator.java", countedLines.getName());
        Assert.assertEquals(94, countedLines.getCount()); 
        countedLines = it.next();
        Assert.assertEquals("5-lines.java", countedLines.getName());
        Assert.assertEquals(5, countedLines.getCount());  
    }
    
}
