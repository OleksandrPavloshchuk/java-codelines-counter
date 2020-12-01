package tests.java.code.line.counter.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * This test only counts sum of nested items
 */
public class DirCountedLinesUnitTest {
    
    @Test
    public void shouldBe100() {
                
        final FileCountedLines file31 = new FileCountedLines("3.1");
        file31.setCount(2);
        final FileCountedLines file32 = new FileCountedLines("3.2");
        file32.setCount(3);        
        final DirCountedLines dir3 = new DirCountedLines("3");
        dir3.addChild(file31);
        dir3.addChild(file32);
        final FileCountedLines file2 = new FileCountedLines("2");
        file2.setCount(5);
        
        final DirCountedLines root = new DirCountedLines("");
        root.addChild( new DirCountedLines("2"));        
        root.addChild(dir3);
        root.addChild(file2);
        
        Assert.assertEquals( 10, root.getCount());
        
        
    }
    
}
