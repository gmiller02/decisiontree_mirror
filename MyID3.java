package decisiontree;

import support.decisiontree.DecisionTreeData;
import support.decisiontree.DecisionTreeNode;
import support.decisiontree.ID3;
import support.decisiontree.Attribute;

import java.util.ArrayList;
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
     * TODO implement the algorithm - this is one possible method signature, feel free to change!
     */

    private DecisionTreeNode myID3Algorithm(DecisionTreeData data, DecisionTreeData parentData) {

        if (data.getAttributeList() == null) {
    	    DecisionTreeNode node = new DecisionTreeNode();
    	    node.setElement(this.mostFrequent(data));
    	    return node;
        }

    	else if (data.getExamples() == null) {
            DecisionTreeNode node = new DecisionTreeNode();
            node.setElement(this.mostFrequent(parentData));
            return node;
        }
    	else if(this.mostFrequentPos(data) == data.getExamples().length ||
        this.mostFrequentNeg(data) == data.getExamples().length){
    	    DecisionTreeNode node = new DecisionTreeNode();
    	    node.setElement(this.mostFrequent(data));
    	    return node;
        }

    	else {
            Attribute attributeGain = this.calculateLargeInfoGain(data);
            DecisionTreeNode tree = new DecisionTreeNode();
            tree.setElement(attributeGain.getName());
            ArrayList<Attribute> attributesList = data.getAttributeList();
            attributesList.remove(attributeGain);
            for (int a = 0; a < attributeGain.getValues().toArray().length; a++) {
                DecisionTreeData newTree = new DecisionTreeData(data.getExamples(), attributesList, data.getClassifications());
                DecisionTreeNode node = this.myID3Algorithm(newTree, data);
                node.setElement(attributeGain.getName());
                tree.addChild(attributeGain.getName(),node);

            }
            return tree;
        }
    }


    public double calculateEntropy(double p, double n) {
        double e = 0;
        double ratio = this.calculateRatio(p, n);

        if (this.calculateRatio(p, n) == 0) {
            return e;
        }
        else {
            e = -(ratio * this.calculateLog(ratio) + ((1 - ratio) * this.calculateLog(1 - ratio)));
            return e;
        }
    }

    private double calculateRemainder(DecisionTreeData data, Attribute attr) {
        double remainder = 0;
        double p = 0;
        double n = 0;
        for (int i = 0; i < attr.getValues().toArray().length; i++) {
            for (int j = 0; j < data.getExamples().length; j++) {
                String inAttr = data.getExamples()[j][attr.getColumn()];
                String inValue = data.getExamples()[j][data.getExamples()[0].length - 1];
                if (inAttr.equals(attr.getValues().toArray()[i])) {
                    if (inValue.equals(data.getClassifications()[0])) {
                        p++;
                    }
                    else if (inValue.equals(data.getClassifications()[0])) {
                        n++;
                    }
                }
            }
            if (p + n == 0) {
                continue;
            }
            double sum = (p + n)/data.getExamples().length;
            remainder += sum * this.calculateEntropy(p, n);
        }

        return remainder;

    }



    private double mostFrequentPos(DecisionTreeData data) {
        double p = 0;
        for (int i = 0; i < data.getExamples().length; i++) {
            if (data.getExamples()[i][data.getExamples()[0].length - 1].equals(data.getClassifications()[0])) {
                p++;
            }
        }
        return p;
    }

    private double mostFrequentNeg(DecisionTreeData data) {
        double n = 0;
        for (int i = 0; i < data.getExamples().length; i++) {
            if (data.getExamples()[i][data.getExamples()[0].length - 1].equals(data.getClassifications()[1])) {
                n++;
            }
        }
        return n;
    }

    private double calculateRatio(double p, double n) {

        double r = p/(p+n);

        return r;
    }

    private double calculateLog(double n) {
        return (Math.log(n) / Math.log(2));
    }

    private String mostFrequent(DecisionTreeData data){
        double p = this.mostFrequentPos(data);
        double n = this.mostFrequentNeg(data);

        if (p > n) {
            return data.getClassifications()[0];
        }

        else if (p == n) {
            double random = Math.random();

            if (random <= 0.5) {
                return data.getClassifications()[0];
            }
            else {
                return data.getClassifications()[1];
            }
        }

        else {
            return data.getClassifications()[1];
        }
    }

    private double calculateInformationGain(DecisionTreeData data, Attribute attr) {
        double entropy = this.calculateEntropy(this.mostFrequentPos(data), this.mostFrequentNeg(data));
        double remainder = this.calculateRemainder(data, attr);

        return entropy - remainder;
    }

    private double calculateSubset(DecisionTreeData data, Attribute attr, String subset, String value) {
        Arraylist<> subset = new ArrayList<>();

    }


    private Attribute calculateLargeInfoGain(DecisionTreeData data) {
        List<Attribute> attributeList = data.getAttributeList();
        double largeInfoGain = 0;
        Attribute maxAttr = attributeList.get(0);

        for (Attribute attr : attributeList) {
            double info = this.calculateInformationGain(data, maxAttr);

            if (info > largeInfoGain) {
                largeInfoGain = info;
                maxAttr = attr;
            }
        }
        return maxAttr;
    }



}
