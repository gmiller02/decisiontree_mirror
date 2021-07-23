package decisiontree;

import support.decisiontree.DecisionTreeData;
import support.decisiontree.DecisionTreeNode;
import support.decisiontree.ID3;
import support.decisiontree.Attribute;
import java.util.List;

/**
  * This class is where your ID3 algorithm should be implemented.
  */
public class MyID3 implements ID3 {


    /**
     * Constructor. You don't need to edit this.
     */
    public MyID3() {

    }

    /**
     * This is the trigger method that actually runs the algorithm.
     * This will be called by the visualizer when you click 'train'.
     */
    @Override
    public DecisionTreeNode id3Trigger(DecisionTreeData data) {

        // TODO run the algorithm, return the root of the tree


       return this.myID3Algorithm(data, null);
    }

    /*
     * This method intakes two decisiontreedatas. This method creates the Decision tree itself, and accounts for possible
     * edge cases that may occur when the algorithem is run.
     */

    private DecisionTreeNode myID3Algorithm(DecisionTreeData data, DecisionTreeData parentData) {

        if (data.getAttributeList() == null) { // edge case when the attribute list is empty
    	    DecisionTreeNode node = new DecisionTreeNode();
    	    node.setElement(this.mostFrequent(parentData));
    	    return node;
        }

    	else if (data.getExamples() == null) { // edge case where the example list is empty
            DecisionTreeNode node = new DecisionTreeNode();
            node.setElement(this.mostFrequent(data));
            return node;
        }
    	else if(this.mostFrequentPos(data) == data.getExamples().length || this.mostFrequentNeg(data) == data.getExamples().length){ // example set is all pos or all neg
    	    DecisionTreeNode node = new DecisionTreeNode();
    	    node.setElement(this.mostFrequent(data));
    	    return node;
        }

    	else { // examples have some positive and some negative classifications
            Attribute attributeGain = this.calculateLargeInfoGain(data);
            DecisionTreeNode tree = new DecisionTreeNode();
            tree.setElement(attributeGain.getName());
            data.getAttributeList().remove(attributeGain);

            for (String val : attributeGain.getValues()) { // for each value in the dataset
                String[][] newSet = this.newSet(data, attributeGain, val); // make a new set
                DecisionTreeData newData = new DecisionTreeData(newSet, data.getAttributeList(), data.getClassifications()); // create a new tree using the new dataset
                DecisionTreeNode node = this.myID3Algorithm(newData, data); // create a new node that uses the new data set (splitting)

                tree.addChild(val, node);
            }
            return tree;
        }
    }

    /**
     * This method intakes two doubles that represent the total positive and negative totals of the tree. The method
     * intakes these two parameters and caculates their entropy by utilizing two other helper methods, one that caculates
     * a logarithmic function and the other that caculates the ration of the total positive and negative numbers. It returns
     * the total entropy of the totals.
     * @param p
     * @param n
     * @return
     */

    public double calculateEntropy(double p, double n) {
        double e = 0;
        double ratio = this.calculateRatio(p, n);
            e = -(ratio * this.calculateLog(ratio) + ((1 - ratio) * this.calculateLog(1 - ratio)));
            return e;
    }

    /**
     * This method intakes a DecisionTreeData and an attribute. It's purpose is to calculate the amount of entropy
     * left in the data tree after splitting on an attribute. It returns a double remainder, or the remaining entropy.
     * @param data
     * @param attr
     * @return
     */

    public double calculateRemainder(DecisionTreeData data, Attribute attr) {
        double remainder = 0;

        for (int i = 0; i < attr.getValues().toArray().length; i++) { // loops through all of the values of the attributte you input in an array
            double p = 0; // double that represents positive
            double n = 0; // double that represents negative

            for (int j = 0; j < data.getExamples().length; j++) { // also loops through all the examples of the tree that you input

                if (data.getExamples()[j][attr.getColumn()].equals(attr.getValues().toArray()[i])) { // if there is something at that specific index
                    if (data.getExamples()[j][data.getExamples()[0].length - 1].equals(data.getClassifications()[0])) { // increment the positive double if at first index
                        p++;
                    }
                    else if (data.getExamples()[j][data.getExamples()[0].length - 1].equals(data.getClassifications()[1])) { // increment negative it at second index
                        n++;
                    }
                }
            }
            if (p + n == 0) { // if the entropy of both numbers is zero, continue
                continue;
            }
            double sum = (p + n)/data.getExamples().length;
            remainder += sum * this.calculateEntropy(p, n); // increment remainder by the sum of the positive and negative values times the entropy of the values
        }

        return remainder;

    }

    /**
     * This method intakes a DecisionTreeData and returns a double. Its purpose is to calculate the classification that
     * occurs the most. This method loops through the data and increments the positive counter when the positive
     * classification occurs the most.
     * @param data
     * @return
     */


    public double mostFrequentPos(DecisionTreeData data) {
        double p = 0; // positive counter
        for (int i = 0; i < data.getExamples().length; i++) {
            if (data.getExamples()[i][data.getExamples()[0].length - 1].equals(data.getClassifications()[0])) {
                p++;
            }
        }
        return p;
    }

    /**
     * This method intakes a DecisionTreeData and returns a double. Its purpose is to calculate the classification that
     * occurs the most. This method loops through the data and increments the negative counter when the negative
     * classification occurs the most.
     * @param data
     * @return
     */

    public double mostFrequentNeg(DecisionTreeData data) {
        double n = 0; // negative counter
        for (int i = 0; i < data.getExamples().length; i++) {
            if (data.getExamples()[i][data.getExamples()[0].length - 1].equals(data.getClassifications()[1])) {
                n++;
            }
        }
        return n;
    }

    /**
     * This method calculates the ratio of the positive and negative classifications. It intakes two doubles, which represent
     * the positive and negative classifications. It returns a double that represents the remainder.
     * @param p
     * @param n
     * @return
     */

    public double calculateRatio(double p, double n) {

        double r = p/(p+n);

        return r;
    }

    /**
     * This method calculates the log2 of an inputted number (represented by double n).
     * @param n
     * @return
     */

    public double calculateLog(double n) {
        if (n == 0 || n == 0.0){
           return 0;
        }
        return (Math.log(n) / Math.log(2));
    }

    /**
     * This method counts and keeps track of classifications in an inputted decisiontreedata.
     * @param data
     * @return
     */

    public String mostFrequent(DecisionTreeData data){
        double p = this.mostFrequentPos(data); // double that represents the amount of positive classifications
        double n = this.mostFrequentNeg(data); // double that represents the amount of negative classifications

        if (p > n) { // if positive is larger than negative, return data at the first index
            return data.getClassifications()[0];
        }

        else if (p == n) { // if positive and negative are equal:
            double random = Math.random(); // generate a random number

            if (random <= 0.5) { // if random number is below or equal to 0.5
                return data.getClassifications()[0]; // return data at first index
            }
            else { // if random number is above 0.5
                return data.getClassifications()[1]; // return data at second index
            }
        }

        else { // if negative larger than positive, return data at second index
            return data.getClassifications()[1];
        }
    }

    /**
     * This method calculates the information gain, or the amount the entropy is reduced when it is split on an attribute.
     * It intakes a DecisionTreeData and an Attribute.
     * @param data
     * @param attr
     * @return
     */

    public double calculateInformationGain(DecisionTreeData data, Attribute attr) {
        double entropy = this.calculateEntropy(this.mostFrequentPos(data), this.mostFrequentNeg(data));
        double remainder = this.calculateRemainder(data, attr);

        return entropy - remainder;
    }

    /**
     * This method loops through the example array and creates a new subset that is the same size as the number of times
     * your inputted value appears and your column of attributes. It intakes a decisiontreedata, an attribute, and a string.
     * @param data
     * @param attr
     * @param val
     * @return
     */

    public String[][] newSet(DecisionTreeData data, Attribute attr, String val) {
        String[][] newSet = new String[this.calculateRows(data, attr, val)][data.getExamples()[0].length];
        int index = 0;

        for (String[] strings : data.getExamples()) {
            if (strings[attr.getColumn()].equals(val)) {
                newSet[index] = strings;
                index++;
            }
        }
        return newSet;
    }

    /**
     * This method calculates the largest information gain of each attribute in an attributelist. It intakes a DecisionTreeData
     * and returns an attribute called maxAttr, which is the attribute which has the largest information gain.
     * @param data
     * @return
     */


    public Attribute calculateLargeInfoGain(DecisionTreeData data) {
        List<Attribute> attributeList = data.getAttributeList();
        double largeInfoGain = 0;
        Attribute maxAttr = attributeList.get(0);

        for (Attribute attr : attributeList) { // loops throuch each attribute in the attribute list
            double info = this.calculateInformationGain(data, attr);

            if (info > largeInfoGain) { // if the information gain is larger than the original information gain, u[date it
                largeInfoGain = info;
                maxAttr = attr;
            }
        }
        return maxAttr;
    }

    /**
     * This method loops through all of the examples of the inputted decisiontreedata and gets a value at a specific
     * column. If that value equals the inputted value, than a counter increments the rows. It intakes a decisiontreedata,
     * an attribute, and a string value.
     * @param data
     * @param attr
     * @param val
     * @return
     */

    public int calculateRows(DecisionTreeData data, Attribute attr, String val) {
        int rows = 0;

        for (int i = 0; i < data.getExamples().length; i++) { // loops through the examples of the decisiontreedata
            if (data.getExamples()[i][attr.getColumn()].equals(val)) {
                rows++;
            }
        }
        return rows;
    }
}
