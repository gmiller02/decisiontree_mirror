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
        //this.caculateEntropy(data);
        // TODO run the algorithm, return the root of the tree
        //System.out.println(this.caculateEntropy(data));
        for(Attribute attr: data.getAttributeList()) {
            System.out.println("This is" + this.caculateRemainder(data, attr));
        }
       return null;
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
            Attribute attributeGain = this.caculateLargeInfoGain(data);
            DecisionTreeNode tree = new DecisionTreeNode();
            tree.setElement(attributeGain.getName());
            for (int a = 0; a < attributeGain.getValues().toArray().length; a++) {
                //removal part what i'm stuck at
                DecisionTreeNode node = this.myID3Algorithm(data, parentData);
                tree.addChild(attributeGain.getName(),node);

            }
            return tree;

        }
    }


    public double caculateEntropy(DecisionTreeData data) {
        double e = 0;
        double ratio = this.caculateRatio(data);

        if (this.caculateRatio(data) == 0) {
            return e;
        }

        else {
            e = -(ratio * this.caculateLog(ratio) + ((1 - ratio) * this.caculateLog(1 - ratio)));
            //System.out.println("This is " + e);
            return e;

        }
    }

    private double caculateRemainder(DecisionTreeData data, Attribute attr) {
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
            remainder += sum * this.caculateEntropy(data);
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

    private double caculateRatio(DecisionTreeData data) {

        double p = this.mostFrequentPos(data);
        double n = this.mostFrequentNeg(data);

        double r = p/(p+n);
        //System.out.println(r);

        return r;
    }

    private double caculateLog(double n) {
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

    private double caculateInformationGain(DecisionTreeData data, Attribute attr) {
        double entropy = this.caculateEntropy(data);
        double remainder = this.caculateRemainder(data, attr);

        return entropy - remainder;
    }

    private Attribute caculateLargeInfoGain(DecisionTreeData data) {
        List<Attribute> attributeList = data.getAttributeList();
        double largeInfoGain = 0;
        Attribute maxAttr = attributeList.get(0);

        for (Attribute attr : attributeList) {
            double info = this.caculateInformationGain(data, maxAttr);

            if (info > largeInfoGain) {
                largeInfoGain = info;
                maxAttr = attr;
            }
        }
        return maxAttr;
    }



}
