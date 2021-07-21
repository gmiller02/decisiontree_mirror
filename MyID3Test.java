package decisiontree;

import org.junit.Test;
import support.decisiontree.DataReader;
import support.decisiontree.DecisionTreeData;
import java.util.Arrays;

/**
 * This class can be used to test the functionality of your MyID3 implementation.
 * Use the Heap stencil and your heap tests as a guide!
 * 
 */

public class MyID3Test {
	
	@Test
	public void simpleTest() {
	    
	    MyID3 id3 = new MyID3();

	    // This creates a DecisionTreeData object that you can use for testing.
	    DecisionTreeData shortData = DataReader.readFile("/course/cs0160/lib/decisiontree-data/short-data-training.csv");
	    // FILL
	    
	}
	
	/**
	 * TODO: add your tests below!
	 */

	@Test
	public void entropyTest() {
		MyID3 id3 = new MyID3();

	}

	@Test
	public void remainderTest() {
		MyID3 id3 = new MyID3();

	}

	@Test
	public void frequentPosTest() {
		MyID3 id3 = new MyID3();

	}

	@Test
	public void frequentNegTest() {
		MyID3 id3 = new MyID3();

	}

	@Test
	public void ratioTest() {
		MyID3 id3 = new MyID3();

	}

	@Test
	public void logTest() {
		MyID3 id3 = new MyID3();


	}

	@Test
	public void frequentTest() {
		MyID3 id3 = new MyID3();

	}

	@Test
	public void infoGainTest() {
		MyID3 id3 = new MyID3();

	}

	@Test
	public void newSetTest() {
		MyID3 id3 = new MyID3();

	}

	@Test
	public void largeInfoGainTest() {
		MyID3 id3 = new MyID3();

	}

	@Test
	public void rowTest() {
		MyID3 id3 = new MyID3();

	}

}