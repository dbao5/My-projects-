import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Fill in the implementation details of the class DecisionTree using this file. Any methods or
 * secondary classes that you want are fine but we will only interact with those methods in the
 * DecisionTree framework.
 */
public class DecisionTreeImpl {
	public DecTreeNode root;
	public List<List<Integer>> trainData;
	public int maxPerLeaf;
	public int maxDepth;
	public int numAttr;

	// Build a decision tree given a training set
	DecisionTreeImpl(List<List<Integer>> trainDataSet, int mPerLeaf, int mDepth) {
		this.trainData = trainDataSet;
		this.maxPerLeaf = mPerLeaf;
		this.maxDepth = mDepth;
		if (this.trainData.size() > 0) this.numAttr = trainDataSet.get(0).size() - 1;
		this.root = decBulidTree();
	}

	private DecTreeNode decBulidTree() {
		return decBulidTree(this.trainData, 0);
	}

	private DecTreeNode decBulidTree(List<List<Integer>> rowData, int depth){
		HashMap<Integer, Integer> numbers = count(rowData);

		int labelZ = numbers.get(0);
		int labelO = numbers.get(1);

		DecTreeNode checkNode = checkStop(labelO, labelZ, depth, rowData);
		if(checkNode != null){
			return checkNode;
		}

		ArrayList<Integer> attr = findAttr(rowData);

		int A = attr.get(0);
		int thres = attr.get(1);

		List<List<List<Integer>>> dataProcess = splitPoint(rowData, A, thres);

		checkNode = new DecTreeNode(0, A, thres);
		checkNode.left = decBulidTree(dataProcess.get(0), depth+1);
		checkNode.right = decBulidTree(dataProcess.get(1), depth+1);
		return checkNode;
	}

	private ArrayList<Integer> findAttr(List<List<Integer>> data) {
		int Attr = 0;
		int thres = 0;
		double BiggestInfoGain = 0;
		double entropy = entropy(data);

		for (int i = 0; i < this.numAttr; i++) {
			for (int j = 0; j < 10; j++) {
				int v = j+1;

				double Gain = entropy - childEntropy(data, i, v);
				if(Gain > BiggestInfoGain){
					BiggestInfoGain = Gain;
					Attr = i;
					thres = v;
				}
			}
		}

		ArrayList<Integer> result =  new ArrayList<>();
		result.add(Attr);
		result.add(thres);
		return result;
	}

	private double childEntropy(List<List<Integer>> data, int attribute, int threshold) {
		List<List<List<Integer>>> result = splitPoint(data, attribute, threshold);
		List<List<Integer>> a = result.get(0);
		List<List<Integer>> b = result.get(1);
		double entropy = 0;

		if(a.size() != 0){
			entropy += ((double)a.size() / data.size()) * entropy(a);
		}

		if(b.size() != 0){
			entropy += ((double)b.size() / data.size()) * entropy(b);
		}

		return entropy;
	}

	private double entropy(List<List<Integer>> data) {
		double entropy = 0;
		HashMap<Integer, Integer> count = count(data);

		int zero = count.get(0);
		int one = count.get(1);

		if(zero != 0){
			double p = (double) zero / (zero + one);
			entropy -= p*Math.log(p)/Math.log(2);
		}

		if(one != 0){
			double p = (double) one / (zero + one);
			entropy -= p*Math.log(p)/Math.log(2);
		}

		return entropy;
	}


	private List<List<List<Integer>>> splitPoint(List<List<Integer>> data, int attribute, int threshold) {
		List<List<List<Integer>>> result = new ArrayList<>();
		List<List<Integer>> smaller = new ArrayList<>();
		List<List<Integer>> greater = new ArrayList<>();
		result.add(smaller);
		result.add(greater);
		for (List<Integer> list : data) {
			if(list.get(attribute) <= threshold){
				smaller.add(list);
			}else{
				greater.add(list);
			}
		}
		return result;
	}

	private DecTreeNode checkStop(int i, int o, int depth, List<List<Integer>> rowData){
		if(o == 0){
			return new DecTreeNode(1, 0, 0);
		}

		if(i == 0){
			return new DecTreeNode(0, 0, 0);
		}

		if(rowData.size() <= this.maxPerLeaf || depth >= this.maxDepth){
			if(o > i){
				return new DecTreeNode(0, 0, 0);
			}else{
				return new DecTreeNode(1, 0, 0);
			}
		}
		return null;
	}



	public int classify(List<Integer> instance) {
		return classify(instance, root);
	}

	private int classify(List<Integer> instance, DecTreeNode node){
		if(node.isLeaf()){
			return node.classLabel;
		}

		int value = instance.get(node.attribute);
		DecTreeNode next = value <= node.threshold ? node.left : node.right;
		return classify(instance, next);
	}
	private HashMap<Integer, Integer> count(List<List<Integer>> rowData) {
		HashMap<Integer, Integer> result = new HashMap<>();
		result.put(0, 0);
		result.put(1, 0);
		for (List<Integer> d : rowData) {
			Integer v = d.get(d.size()-1);
			if(!result.containsKey(v)){
				result.put(v, 0);
			}
			result.put(v, result.get(v)+1);
		}
		return result;
	}

	// Print the decision tree in the specified format
	public void printTree() {
		printTreeNode("", this.root);
	}

	public void printTreeNode(String prefixStr, DecTreeNode node) {
		String printStr = prefixStr + "X_" + node.attribute;
		System.out.print(printStr + " <= " + String.format("%d", node.threshold));
		if(node.left.isLeaf()) {
			System.out.println(" : " + String.valueOf(node.left.classLabel));
		}
		else {
			System.out.println();
			printTreeNode(prefixStr + "|\t", node.left);
		}
		System.out.print(printStr + " > " + String.format("%d", node.threshold));
		if(node.right.isLeaf()) {
			System.out.println(" : " + String.valueOf(node.right.classLabel));
		}
		else {
			System.out.println();
			printTreeNode(prefixStr + "|\t", node.right);
		}
	}

	public double printTest(List<List<Integer>> testDataSet) {
		int numEqual = 0;
		int numTotal = 0;
		for (int i = 0; i < testDataSet.size(); i ++)
		{
			int prediction = classify(testDataSet.get(i));
			int groundTruth = testDataSet.get(i).get(testDataSet.get(i).size() - 1);
			System.out.println(prediction);
			if (groundTruth == prediction) {
				numEqual++;
			}
			numTotal++;
		}
		double accuracy = numEqual*100.0 / (double)numTotal;
		System.out.println(String.format("%.2f", accuracy) + "%");
		return accuracy;
	}
}
