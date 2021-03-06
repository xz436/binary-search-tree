* TODO: 一定要能熟练地写出所有问题的递归和非递归做法！ 
 * 
 * 1. 求二叉树中的节点个数: getNodeNumRec（递归），getNodeNum（迭代） 
 * 2. 求二叉树的深度: getDepthRec（递归），getDepth  
 * 3. 前序遍历，中序遍历，后序遍历: preorderTraversalRec, preorderTraversal, inorderTraversalRec, postorderTraversalRec 
 * (https://en.wikipedia.org/wiki/Tree_traversal#Pre-order_2) 
 * 4.分层遍历二叉树（按层次从上往下，从左往右）: levelTraversal, levelTraversalRec（递归解法！） 
 * 5. 将二叉查找树变为有序的双向链表: convertBST2DLLRec, convertBST2DLL 
 * 6. 求二叉树第K层的节点个数：getNodeNumKthLevelRec, getNodeNumKthLevel 
 * 7. 求二叉树中叶子节点的个数：getNodeNumLeafRec, getNodeNumLeaf 
 * 8. 判断两棵二叉树是否相同的树：isSameRec, isSame 
 * 9. 判断二叉树是不是平衡二叉树：isAVLRec 
 * 10. 求二叉树的镜像（破坏和不破坏原来的树两种情况）：mirrorRec, mirrorCopyRec 
 * 10.1 判断两个树是否互相镜像：isMirrorRec 
 * 11. 求二叉树中两个节点的最低公共祖先节点：getLastCommonParent, getLastCommonParentRec, getLastCommonParentRec2 
 * 12. 求二叉树中节点的最大距离：getMaxDistanceRec 
 * 13. 由前序遍历序列和中序遍历序列重建二叉树：rebuildBinaryTreeRec 
 * 14.判断二叉树是不是完全二叉树：isCompleteBinaryTree, isCompleteBinaryTreeRec 
 *  
tree class 的写法: 定义出你需要的变量+constructor
private static class TreeNode{
	int val;
	TreeNode left;
	TreeNode right;

	public TreeNode(int val){
		this.val = val;
	}
}

类似的graph的定义同理
class undirectedGraphNode{
	int label;
	ArrayList<undirectedGraphNode> neighbors;
	undirectedGraphNode(int x){
		label = x;
		neighbors = new ArrayList<undirectedGraphNode>();
	}
}


 * 1. 求二叉树中的节点个数: getNodeNumRec（递归），getNodeNum（迭代）

<1>（递归）--O(n)
（1）如果二叉树为空，节点个数为0
（2）不为空，节点个数 = 左子树节点个数 + 右子树节点个数 + 1(root);
public static int getNodeNumRec(TreeNode root){
	if(root == null){
		return 0;
	}
	return getNodeNumRec(root.left) + getNodeNumRec(root.right) + 1;
}

<2> （迭代）interation
public static int getNodeNumRec(TreeNode root){
	if(root == null){
		return 0;
	}
	int count = 0;
	Queue<TreeNode> queue = new LinkedList<TreeNode>();
	queue.offer(root);
	while(!queue.isEmpty()){
		int size = queue.size();
		for(int i = 0; i < size; i++){
			TreeNode node = queue.poll();
			count++;
			if(node.left != null){
				queue.offer(node.left);
			}
			if(node.right != null){
				queue.offer(node.right);
			}
		}
	}
	return count;
}

* 2. 求二叉树的深度: getDepthRec（递归），getDepth  
<1>递归
public int getDepthRec(TreeNode root){
	if(root == null){
		return 0;
	}
	int left = getDepthRec(root.left);
	int right = getDepthRec(root.right);
	return Math.max(left, right) + 1;
}

<2>queue
public int getDepth(TreeNode root){
	if(root == null){
		return 0;
	}
	int dep = 0;
	Queue<TreeNode> queue = new LinkedList<TreeNode>();
	queue.offer(root);
	while(!queue.isEmpty()){
		int size = queue.size();
		for(int i = 0; i < size; i++){
			TreeNode node = queue.poll();
			if(node.left != null){
				queue.offer(node.left);
			}
			if(node.right != null){
				queue.offer(node.right);
			}
		}
		dep++;
	}
	return dep;
}

 * 3. 前序遍历，中序遍历，后序遍历: preorderTraversalRec, preorderTraversal, inorderTraversalRec, postorderTraversalRec 
 * (https://en.wikipedia.org/wiki/Tree_traversal#Pre-order_2) 
   3.1 preorderTraversalRec
public static ArrayList<Integer> preorderTraversalRec(TreeNode root){
	ArrayList<Integer> result = new ArrayList<Integer>();
	if(root == null){
		return result;
	}
	
	return preorderTraversalRec(result, root);
}
private static ArrayList<Integer> preorderTraversalRec(ArrayList<Integer> result, TreeNode root){
	if(root == null){
		return result;
	}
	result.add(root.val);
	preorderTraversalRec(result, root.left);
	preorderTraversalRec(result, root.right);
	return result;
}
----------------------------------------------------------------------------------
public static ArrayList<Integer> preorderTraversal(TreeNode root){
	ArrayList<Integer> result = new ArrayList<Integer>();
	if(root == null){
		return result;
	}
	Stack<TreeNode> stack = new Stack<TreeNode>();
	stack.push(root);
	while(!stack.isEmpty()){
		TreeNode node = stack.pop();
		result.add(node.val);
		if(node.right != null){
			stack.push(node.right);
		}
		if(node.left != null){
			stack.push(node.left);
		}
	}
	return result;
}

3.2 inorderTraversalRec
public ArrayList<Integer> inorderTraversal(TreeNode root){
	ArrayList<Integer> result = new ArrayList<Integer>();
	if(root == null){
		return result;
	}
	return inorderTraversal(result, root);
}
private ArrayList<Integer> inorderTraversal(ArrayList<Integer> result, TreeNode root){
	if(root == null){
		return result;
	}
	inorderTraversal(result, root.left);
	result.add(root.val);
	inorderTraversal(result, root.right);
	return result;
}
-----------------------------------------------------------------------
public ArrayList<Integer> inorderTraversal(TreeNode root){
	ArrayList<Integer> result = new ArrayList<Integer>();
	if(root == null){
		return result;
	}
	Stack<TreeNode> stack = new Stack<TreeNode>();
	TreeNode node = root;
	while(node != null || !stack.isEmpty()){
		while(node != null){
			stack.push(node);
			node = node.left;
		}
		node = stack.pop();
		result.add(node.val);
		node = node.right;
	}
	return result;
}

3.3 postorderTraversal

public static ArrayList<Integer> postorderTraversal(TreeNode root){
	ArrayList<Integer> result = new ArrayList<Integer>();
	if(root == null){
		return result;
	}
	Stack<TreeNode> stack = new Stack<TreeNode>();
	stack.push(root);
	while(!stack.isEmpty()){
		TreeNode node = stack.pop();
		result.add(0, node.val);
		if(node.left != null){
			stack.push(node.left);
		}
		if(node.right != null){
			stack.push(node.right);
		}
	}
	return result;
}
 * 4.分层遍历二叉树（按层次从上往下，从左往右）: levelTraversal, levelTraversalRec（递归解法！）
别忘了建了queue之后，先吧一个root压进去，然后再看queue 的size，然后开始写，每pop出来一个点之后
都要check左右子树，然后不为空就压倒queue里面，千万别忘了

 public static ArrayList<ArrayList<Integer>> levelTraversal(TreeNode root){
 	ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
 	if(root == null){
 		return result;
 	}
 	Queue<TreeNode> queue = new LinkedList<TreeNode>();
 	queue.offer(root);

 	while(!queue.isEmpty()){
 		ArrayList<Integer> level = new ArrayList<Integer>();
 		int size = queue.size();
 		for(int i = 0; i < size; i++){
 			TreeNode node = queue.poll();
 			level.add(node.val);
 				if(node.left != null){
 			    queue.offer(node.left);
 			}
 			if(node.right != null){
 			    queue.offer(node.right);
 			}
 		}
 		result.add(level);
 	}
 	return result;
 } 
level order Tree_traversal 递归解法不常见，但是我们看一下
public ArrayList<ArrayList<Integer>> levelTraversal(TreeNode root){
	ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
	if(root == null){
		return result;
	}
	return dfs(root, 0, result);
}
private ArrayList<ArrayList<Integer>> dfs(TreeNode root, int level, ArrayList<ArrayList<Integer>> result){
	if(root == null){
		return result;
	}
	if(level >= result.size()){
		result.add(new ArrayList<Integer>());
	}
	result.get(level).add(root.val);
	dfs(root.left, level + 1, result);
	dfs(root.right, level + 1, result);
	return result;
}
 * 5. 将二叉查找树变为有序的双向链表: convertBST2DLLRec, convertBST2DLL 

 * 6. 求二叉树第K层的节点个数：getNodeNumKthLevelRec, getNodeNumKthLevel
利用root为null时return 0, k == 1是return1，然后利用divide and conquer就行
 public static int getNodeNumKthLevel(TreeNode root, int k){
 	if(root == null){
 		return 0;
 	}
 	if(k == 1){
 		return 1;
 	}
 	int numLeft = getNodeNumKthLevel(root.left, k - 1);
 	int numRight = getNodeNumKthLevel(root.right, k - 1);
 	return numLeft + numRight;
 } 

 ----------------------------------
 利用level order,每层k--，然后k==0 就返回
public static int getNodeNumKthLevel(TreeNode root, int k){
	if(root == null){
		return 0;
	}
	Queue<TreeNode> queue = new LinkedList<TreeNode>();
	TreeNode node = root;
    while(node != null || !queue.isEmpty()){
    	int size = queue.size();
    	k--;
    	if(k == 0) return size;
    
    			for(int i = 0; i < size; i++){
    		node = queue.poll();
    		if(node.left != null){
    			queue.offer(node.left);
    		}
    		if(node.right != null){
    			queue.offer(node.right);
    		}
    	}
    }

}



 * 7. 求二叉树中叶子节点的个数：getNodeNumLeafRec, getNodeNumLeaf
注意讨论root为null和没有儿子的情况，
 public static int getNodeNumLeaf(TreeNode root){
 	if(root == null){
 		return 0；
 	}
 	if(root.left == null && root.right == null){
 		return 1;
 	}
 	return getNodeNumLeaf(root.left) + getNodeNumLeaf(root.right);
 } 
 * 8. 判断两棵二叉树是否相同的树：isSameRec, isSame 
  递归解法：  
     * （1）如果两棵二叉树都为空，返回真 
     * （2）如果两棵二叉树一棵为空，另一棵不为空，返回假  
     * （3）如果两棵二叉树都不为空，如果对应的左子树和右子树都同构返回真，其他返回假 
  public boolean isSame(TreeNode n1, TreeNode n2){
  	if(n1 == null && n2 == null){
  		return true;
  	}
  	if(n1 == null || n2 == null){
  		return false;
  	}
  	if(n1.val != n2.val){
  		return false;
  	}
  	boolean left = isSame(root.left);
  	boolean right = isSame(root.right);
  	return left && right;
  }
 * 9. 判断二叉树是不是平衡二叉树：isAVLRec 
  1）如果二叉树为空，返回真 
（2）如果二叉树不为空，这里用到一个小trick，就是return maxDpeth != -1以此判断是否是平衡树，因为平衡树
的要求有：左子树为平衡树，右子树为平衡树，且左右子树高度差不超过1，注意：这里出现了高度！
public static boolean isAVL(TreeNode root){
	if(root == null){
		return true;
	}
	return maxDepth != -1;
}
private static int maxDepth(TreeNode root){
	if(root == null){
		return 0;
	}
	int left = maxDepth(root.left);
	int right = maxDepth(root.right);
	if(left == -1 || right == -1 || Math.abs(left - right) > 1){
		return -1;
	}
	return Math.max(left, right) + 1;
}
 * 10. 求二叉树的镜像（破坏和不破坏原来的树两种情况）：mirrorRec, mirrorCopyRec 
破坏原来的树
public static TreeNode mirrorCopy(TreeNode n1){
	if(n1 == null)
		return null;
	TreeNode left = mirrorCopy(n1.left);
	TreeNode right = mirrorCopy(n1.right);
	n1.left = right;
	n1.right = left;
	return n1;
}
不破坏原来的树，那就新建一个树呗
public static TreeNode mirrorCopy(TreeNode n1){
	if(n1 == null){
		return null;
	}
	TreeNode newTree = new TreeNode(n1.val);
	newTree.left = mirrorCopy(n1.right);
	newTree.right = mirrorCopy(n2.left);
	return newTree;
}

 * 10.1 判断两个树是否互相镜像：isMirrorRec 
public static boolean isMirror(TreeNode n1, TreeNode n2){
	if(n1 == null && n2 == null){
		return true;
	}
	if(n1 == null || n2 == null){
		return false;
	}
	if(n1.val == n2.val){
		return isMirror(n1.left, n2.left) && isMirror(n1.right, n2.right);
	}
	return false;
}
 * 11. 求二叉树中两个节点的最低公共祖先节点：getLastCommonParent, getLastCommonParentRec, getLastCommonParentRec2 
public satic TreeNode getLastCommonParent(TreeNode root, TreeNode n1, TreeNode n2){
	if(root == null || n1 == root || n2 == root){
		return root;
	}
	TreeNode left = getLastCommonParent(root.left, n1, n2);
	TreeNode right = getLastCommonParent(root.right, n1, n2);
	if(left != null && right != null){
		return root;
	}
	if(left != null){
		return left;
	}
	if(right != null){
		return right;
	}
	return null;
}
 * 12. 求二叉树中节点的最大距离：getMaxDistanceRec 
这题一定要弄清楚你定义的maxDepth是什么，想象一下，如果求最大距离，横跨左右两个子树的情况就是left.maxDepth + right.maxDepth
这个跟之前算max path sum不同，因为那个得加root.val
再注意一下root == null时，想定义maxDepth = 0,那直接result = new ResultType(0, 0);就完成了定义
public class ResultType{
	int maxDistance, maxDepth;
	public ResultType(int maxDistance, int maxDepth){
		this.maxDistance = maxDistance;
		this.maxDepth = maxDepth;
	}
}
public static int getMaxDistance(TreeNode root){
	ResultType result = getMax(root);
	return result.maxDistance;
}
private static ResultType getMax(TreeNode root){
	if(root == null){
		ResultType result = new ResultType(0, 0);
		return result;
	}
	ResultType left = getMax(root.left);
	ResultType right = getMax(root.right);
	maxDepth = Math.max(left.maxDepth, right.maxDepth) + 1;
	maxDistance = Math.max(left.maxDepth + right.maxDepth, Math.max(left.maxDistance, right.maxDistance));
	return new ResultType(maxDistance, maxDepth);
}

类似Binary tree maximum path sum 那个题

public class returntype{
	int max;
	int singleMax;
	returntype(int max, int singleMax){
		this.max = max;
		this.singleMax = singleMax;
	}
}
    public int maxPathSum(TreeNode root) {
        // write your code here
        if(root == null){
		return 0;
	}
	returntype result = getMax(root);
	return result.max;
    }
    private returntype getMax(TreeNode root){
	if(root == null){
		return new returntype(Integer.MIN_VALUE, 0);
	}
	//divide
	returntype left = getMax(root.left);
	returntype right = getMax(root.right);
	//conquer
	int singleMax = Math.max(left.singleMax, right.singleMax) + root.val;
	singleMax = Math.max(singleMax, 0);
	int max = Math.max(left.max, right.max);
	max = Math.max(left.singleMax + right.singleMax + root.val, max);
	return new returntype(max, singleMax);
}

 * 13. 由前序遍历序列和中序遍历序列重建二叉树：rebuildBinaryTreeRec 
 * 14.判断二叉树是不是完全二叉树：isCompleteBinaryTree, isCompleteBinaryTreeRec

感觉其实有点复杂爱。。。肯定得设定一个mustHaveNoChild来控制遇到null之后的情况，本来想的是看座子树
是否为null 然后看右子树，但是这样有点复杂，因为右子树的情况完全取决于左子树，所以简单的方法就是一起看
public static boolean isCompleteBinaryTree(TreeNode root){
	if(root == null){
		return false;
	}
	Queue<TreeNode> queue = new LinkedList<TreeNode>();
	queue.offer(root);
	boolean mustHaveNoChild = false;
	while(!queue.isEmpty()){
		int size = queue.size();
		for(int i = 0; i < size; i++){
			TreeNode node = queue.poll();
			if(mustHaveNoChild){
				if(node.left != null|| node.right != null){
					return false;
				}
			}
			else{

   //          if(node.left != null){
			// 	queue.offer(root.left);
			// }
			// else{
			// 	mustHaveNoChild = true;

			// }
			// if(node.right != null){
			// 	queue.offer(root.right);
			// }
			//if left, right not null
				if(node.left != null && node.right != null){
					queue.offer(node.left);
					queue.offer(node.right);
				}
				else if(node.left != null && node.right == null){
					queue.offer(node.left);
					mustHaveNoChild = true;
				}
				else if(node.left == null && node.right != null){
					return false;
				}
				else if(node.left == null && node.right == null){
					mustHaveNoChild = true;
				}
			}	
		}
	}
	return true;
}















