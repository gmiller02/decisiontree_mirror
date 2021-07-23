package decisiontree;

import org.junit.Test;
import support.decisiontree.DataReader;
import support.decisiontree.DecisionTreeData;
import static org.junit.Assert.*;
import support.decisiontree.Attribute;

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
	    DecisionTreeData shortData = DataReader.readFile("src/decisiontree/decisiontree-data/short-data-training.csv");
		Attribute attr = shortData.getAttributeList().get(1);
		assertTrue(id3.calculateInformationGain(shortData, attr) == 0.34758988139079705);
	}
	
	/**
	 * TODO: add your tests below!
	 */

	@Test
	public void entropyTest() {
		MyID3 id3 = new MyID3();
		DecisionTreeData shortData = DataReader.readFile("src/decisiontree/decisiontree-data/short-data-training.csv");
		assertTrue(id3.calculateEntropy(7.0, 3.0) == 0.8812908992306927);
	}

	@Test
	public void remainderTest() {
		MyID3 id3 = new MyID3();
		DecisionTreeData shortData = DataReader.readFile("src/decisiontree/decisiontree-data/short-data-training.csv");
		Attribute attr = shortData.getAttributeList().get(1);
		assertTrue(id3.calculateRemainder(shortData, attr) == 0.6068441215341679);

	}

	@Test
	public void frequentPosTest() {
		MyID3 id3 = new MyID3();
		DecisionTreeData shortData = DataReader.readFile("src/decisiontree/decisiontree-data/short-data-training.csv");
		assertTrue(id3.mostFrequentPos(shortData) == 5);

	}

	@Test
	public void frequentNegTest() {
		MyID3 id3 = new MyID3();
		DecisionTreeData shortData = DataReader.readFile("src/decisiontree/decisiontree-data/short-data-training.csv");
		assertTrue(id3.mostFrequentNeg(shortData) == 3);
	}

	@Test
	public void ratioTest() {
		MyID3 id3 = new MyID3();
		DecisionTreeData shortData = DataReader.readFile("src/decisiontree/decisiontree-data/short-data-training.csv");
		int p = 4;
		int n = 2;
		assertTrue(id3.calculateRatio(p, n) == 2.0/3.0);
	}

	@Test
	public void logTest() {
		MyID3 id3 = new MyID3();
		int i = 0;
		int j = 4;
		assertTrue(id3.calculateLog(i) == 0);
		assertTrue(id3.calculateLog(j) == 2);
	}

	@Test
	public void frequentTest() {
		MyID3 id3 = new MyID3();
		DecisionTreeData shortData = DataReader.readFile("src/decisiontree/decisiontree-data/short-data-training.csv");
		assertTrue(id3.mostFrequent(shortData) == shortData.getClassifications()[0]);

	}

	@Test
	public void infoGainTest() {
		MyID3 id3 = new MyID3();
		DecisionTreeData shortData = DataReader.readFile("src/decisiontree/decisiontree-data/short-data-training.csv");
		Attribute attr = shortData.getAttributeList().get(1);
		assertTrue(id3.calculateInformationGain(shortData, attr) == 0.34758988139079705);


	}

	@Test
	public void newSetTest() {
		MyID3 id3 = new MyID3();


	}

	@Test
	public void largeInfoGainTest() {
		MyID3 id3 = new MyID3();
		DecisionTreeData shortData = DataReader.readFile("src/decisiontree/decisiontree-data/short-data-training.csv");
		assertTrue(id3.calculateLargeInfoGain(shortData) == shortData.getAttributeList().get(4));


	}

	@Test
	public void rowTest() {
		MyID3 id3 = new MyID3();
		DecisionTreeData shortData = DataReader.readFile("src/decisiontree/decisiontree-data/short-data-training.csv");
		Attribute attr = shortData.getAttributeList().get(1);
		String val = shortData.getExamples()[1][attr.getColumn()];
		assertTrue(id3.calculateRows(shortData, attr, val) == 5);

	}

}