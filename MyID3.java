package decisiontree;

import support.decisiontree.DecisionTreeData;
import support.decisiontree.DecisionTreeNode;
import support.decisiontree.ID3;
import support.decisiontree.Attribute;

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
        //this.caculateEntropy(data);
        // TODO run the algorithm, return the root of the tree
        System.out.println(this.caculateEntropy(data));
       return null;
    }

    /*
     * TODO implement the algorithm - this is one possible method signature, feel free to change!
     */

    private DecisionTreeNode myID3Algorithm(DecisionTreeData data, DecisionTreeData parentData) {
    	Attribute attributes = new Attribute("attribute", 0);



        if (data.getAttributeList() == null) {
    	    DecisionTreeNode node = new DecisionTreeNode();
    	    //node.setElement(this.mostFrequent(parentData));
    	    return node;
        }

    	else if (attributes.getValues().isEmpty()) {
            DecisionTreeNode node = new DecisionTreeNode();
            //node.setElement(this.mostFrequent(parentData));
            return node;
        }

    	//else if (attributes.getValues())

    	else {
            Attribute attributeGain = new Attribute("attribute", 0);

        }


        return null;

    }


    public double caculateEntropy(DecisionTreeData data) {
        double e = 0;
        double ratio = this.caculateRatio(data);

        if (this.caculateRatio(data) == 0) {
            return e;
        }

        else {
            e = -(ratio * this.caculateLog(ratio) + ((1 - ratio) * this.caculateLog(1 - ratio)));
            System.out.println("This is " + e);
            return e;

        }
    }

    public double caculateRemainder(DecisionTreeData data, Attribute attr) {
        double remainder = 0;
        double p = 0;
        double n = 0;
        for (int i = 0; i < attr.getValues().toArray().length; i++) {
            for (int j = 0; j < data.getExamples().length; j++) {

            }

        }

        return remainder;

    }

    public double mostFrequentPos(DecisionTreeData data) {
        double p = 0;
        for (int i = 0; i < data.getExamples().length; i++) {
            if (data.getExamples()[i][data.getExamples()[0].length - 1].equals(data.getClassifications()[0])) {
                p++;
            }
        }
        return p;
    }

    public double mostFrequentNeg(DecisionTreeData data) {
        double n = 0;
        for (int i = 0; i < data.getExamples().length; i++) {
            if (data.getExamples()[i][data.getExamples()[0].length - 1].equals(data.getClassifications()[1])) {
                n++;
            }
        }
        return n;
    }

    public double caculateRatio(DecisionTreeData data) {

        double p = this.mostFrequentPos(data);
        double n = this.mostFrequentNeg(data);

        double r = p/(p+n);
        //System.out.println(r);

        return r;
    }

    public double caculateLog(double n) {
        return (Math.log(n) / Math.log(2));
    }




}
