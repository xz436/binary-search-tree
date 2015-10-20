
 *  
---------------------------------------------------------------------------------------------
下面开始总结lintcode-ladder上的题
1.看二叉树是否是valid的，也就是说左子树得是valid的，右子树得是valid的，而且左子树max< root.val右子树min>root.val
是否取等看题义吧,不过一般好像不取等哎。。。
但是奇怪的是必须讨论 root == null------root.left == null && root.right == null的情况
 public class resultType{
         boolean isValid;
         int min, max;
         resultType(boolean isValid, int min, int max){
             this.isValid = isValid;
             this.min = min;
             this.max = max;
         }
     }
    public boolean isValidBST(TreeNode root) {
        // write your code here
        //left & right both need to be validate, and left max < root.val && right.min > root.val
        if(root == null){
            return true;
        }
        resultType result = isValid(root);
        return result.isValid;
    }
    private resultType isValid(TreeNode root){
        if(root == null){
            return new resultType(true, Integer.MAX_VALUE, Integer.MIN_VALUE);
        }
        // if(root.left == null && root.right == null){
        //     return new resultType(true, root.val, root.val);
        // }
        resultType left = isValid(root.left);
        resultType right = isValid(root.right);
        
        int min = Math.min(root.val, Math.min(left.min, right.min));
        int max = Math.max(root.val, Math.max(left.max, right.max));
        boolean isValid = left.isValid && right.isValid;
        isValid = isValid && (left.max < root.val && right.min > root.val);
        
        return new resultType(isValid, min, max);
    }
2.balanced binary tree
就是左子树右子树相差不超过1，需要看depth，还有boolean那就建一个新的return type
 public class resultType{
        boolean isBalanced;
        int depth;
        resultType(boolean isBalanced, int depth){
            this.isBalanced = isBalanced;
            this.depth = depth;
        }
    }
    public boolean isBalanced(TreeNode root) {
        // write your code here
        resultType result = balanced(root);
        return result.isBalanced;
    }
    private resultType balanced(TreeNode root){
        if(root == null){
            return new resultType(true, 0);
        }
        resultType left = balanced(root.left);
        resultType right = balanced(root.right);
        int depth = Math.max(left.depth, right.depth) + 1;
        boolean isBalanced = left.isBalanced && right.isBalanced;
        isBalanced = isBalanced &&(Math.abs(left.depth - right.depth) <= 1);
        return new resultType(isBalanced, depth);
    }
3. search range in binary search tree
就是给你一个tree 让你把k1,k2之间的值都输出出来
最简单的方法就是inorder traversal 因为是递增的，注意是inorder,不是pre！！！！
然后还有个注意的地方就是当值大于k2的时候就可以break了，没必要再继续了

public ArrayList<Integer> searchRange(TreeNode root, int k1, int k2) {
        // write your code here
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
            if(k1 <= node.val && node.val <= k2){
                result.add(node.val);
            }
            if(node.val > k2){
                break;
            }
            node = node.right;
        }
        return result;
    }
4. binary search tree iterator
真是不懂啊。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。
/**
 * Definition of TreeNode:
 * public class TreeNode {
 *     public int val;
 *     public TreeNode left, right;
 *     public TreeNode(int val) {
 *         this.val = val;
 *         this.left = this.right = null;
 *     }
 * }
 * Example of iterate a tree:
 * Solution iterator = new Solution(root);
 * while (iterator.hasNext()) {
 *    TreeNode node = iterator.next();
 *    do something for node
 * } 
 */
public class Solution {
    //@param root: The root of binary tree.
    Stack<TreeNode> stack = new Stack<TreeNode>();
    public Solution(TreeNode root) {
        // write your code here
        
        TreeNode node = root;
        while(node != null){
            stack.push(node);
            node = node.left;
        }
        
    }

    //@return: True if there has next node, or false
    public boolean hasNext() {
        // write your code here
        return !stack.isEmpty();
    }
    
    //@return: return next node
    public TreeNode next() {
        // write your code here
        TreeNode node = stack.pop();
        TreeNode node1 = node;
        node = node.right;
         while(node != null){------empty stack?
            stack.push(node);
            node = node.left;
        }
        return node1;
    }
}
5. min depth of binary tree
就是注意分别讨论左右子树是同时为null，还是只有一个为null还是都不为null
   public int minDepth(TreeNode root) {
        // write your code here
        if(root == null){
            return 0;
        }
        if(root.left == null && root.right == null){
            return 1;
        }
        int left = minDepth(root.left);
        int right = minDepth(root.right);
        if(root.left == null){
            return right + 1;
        }
        if(root.right == null){
            return left + 1;
        }
        return Math.min(left, right) + 1;
    }
    6. insert a node to binary search tree
    写了个简单版本的，但是应该会写非recursion方法的！！！！
    public TreeNode insertNode(TreeNode root, TreeNode node) {
        // write your code here
        if(root == null){
            return node;
        }
        
        if(node.val >= root.val){
        root.right = insertNode(root.right, node);    
        }
        else{
        root.left = insertNode(root.left, node);    
        }
        
        return root;
    }
7. binary tree aigzag level order traversal
简单！就跟level order 一样,看行数奇数还是偶数，决定level的写法
 public ArrayList<ArrayList<Integer>> zigzagLevelOrder(TreeNode root) {
        // write your code here
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        if(root == null){
            return result;
        }
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        int row = 1;
        while(!queue.isEmpty()){
            int size = queue.size();
            ArrayList<Integer> level = new ArrayList<Integer>();
            for(int i = 0; i < size; i++){
                TreeNode node = queue.poll();
                if(row % 2 == 0){
                    level.add(0,node.val);
                }
                else{
                    level.add(node.val);
                }
                if(node.left != null){
                    queue.offer(node.left);
                }
                if(node.right != null){
                    queue.offer(node.right);
                }
            }
            result.add(level);
            row++;
        }
        return result;
    }


8. 从底向上level order
    public ArrayList<ArrayList<Integer>> levelOrderBottom(TreeNode root) {
        // write your code here
              ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        if(root == null){
            return result;
        }
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        while(!queue.isEmpty()){
            int size = queue.size();
            ArrayList<Integer> level = new ArrayList<Integer>();
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
            result.add(0, level);
         
        }
        return result;
    }
以上是lintcode ladder中的题。。。二叉树太多，以后每天来5道吧。。。
9. construct bianary tree from preorder and inorder traversal

想法其实很简单，就是先分别把preorder和inorder写出来，然后通过preorder找到root.val然后通过post找到
如何分左右子树
值得注意的是退出条件preStart > preEnd 时返回null，这个可以用1,2举例，1的右子树为null 看这个时候preStart=2, preEnd = 1
public TreeNode buildTree(int[] preorder, int[] inorder) {
        // write your code here
        return buildTree(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }
    private TreeNode buildTree(int[] preorder, int preStart, int preEnd, int[] inorder, int inStart, int inEnd){
        // if(preorder == null || preorder.length == 0){
        //     return null;
        // }
        // if(preStart == preEnd ){
        //     return new TreeNode(preStart);
        // }
        if(preStart > preEnd || inStart > inEnd){
            return null;
        }
        TreeNode root = new TreeNode(preorder[preStart]);
        int index = 0;
        //search index of root in inorder
        for(int i = inStart; i <= inEnd; i++){
            if(inorder[i] == root.val){
                index = i;
                break;
            }
        }
        TreeNode left = buildTree(preorder, preStart + 1, preStart + index - inStart, inorder, inStart, index - 1);
        TreeNode right = buildTree(preorder, preStart + index - inStart + 1, preEnd, inorder, index + 1, inEnd);
        root.left = left;
        root.right = right;
        return root;
    }
}

10. construct binary tree from inorder and postorder traversal
跟上个题类似
public TreeNode buildTree(int[] inorder, int[] postorder) {
        // write your code here
        return buildTree(inorder, 0, inorder.length - 1, postorder, 0, postorder.length - 1);
    }
    private TreeNode buildTree(int[] inorder, int inStart, int inEnd, int[] postorder, int postStart, int postEnd){
        if(inStart > inEnd || postStart > postEnd){
            return null;
        }
        int index = 0;
        TreeNode root = new TreeNode(postorder[postEnd]);
        for(int i = inStart; i <= inEnd; i++){
            if(inorder[i] == root.val){
                index = i;
                break;
            }
        }
        TreeNode left = buildTree(inorder, inStart, index - 1, postorder, postStart, index - 1 - inStart + postStart);
        TreeNode right = buildTree(inorder, index + 1, inEnd, postorder, index - inStart + postStart, postEnd -1);
        //X - postStart = index - 1-inStart
        root.left = left;
        root.right = right;
        return root;
    }
11. convert sorted array to binary search tree 
想法很简单就是用recursion，root肯定是sorted array中的中间值，先找出来之后。再确定left, right
所以肯定需要starting, ending 的 index来表示left, right 所以建个helper function有starting
和ending index的
 public TreeNode sortedArrayToBST(int[] A) {  
        // write your code here
        return sortedArrayToBST(A, 0, A.length - 1);
    }  
    private TreeNode sortedArrayToBST(int[] A, int start, int end){
        if(A == null || start > end){
           return null; 
        }
        if(start == end){
            return new TreeNode(A[start]);
        }
        TreeNode root = new TreeNode(A[(start + end)/2]);
        TreeNode left = sortedArrayToBST(A, start, (start + end)/2 - 1);
        TreeNode right = sortedArrayToBST(A, (start + end)/2 + 1, end);
        root.left = left;
        root.right = right;
        return root;
    }
所以类似的以后要是给定一个树，我们能用inorder traverse来遍历这个树，那就能重建树啦

12. convert sorted list to binary search tree
想法很简单，类似于given sorted array to make BST,第一步就是找到list的中点作为root
然后对左边的list调用函数作为left，
注意1：要把slow前的那个点接null
2：left = sort(head)
想一下如果root就是head，那怎么办，应该这时候直接让left = null啦！
 public TreeNode sortedListToBST(ListNode head) {  
        // write your code here
        if(head == null){
            return null;
        }
        if(head.next == null){
           return new TreeNode(head.val); 
        }
        //find the middle node of list first
        ListNode pre = new ListNode(0);
        pre.next = head;
        ListNode slow = head;
        ListNode fast = head.next;
        while(fast != null && fast.next != null){
            fast = fast.next.next;
            slow = slow.next;
            pre = pre.next;
        }
        pre.next = null;
        TreeNode root = new TreeNode(slow.val);
        TreeNode left = null;
        if(slow != head){
            left = sortedListToBST(head);
        }
        TreeNode right = sortedListToBST(slow.next);
        root.left = left;
        root.right = right;
        return root;
    }
13. construct bianry search tree by preorderTraversal
public TreeNode preToBST(int[] A){
	if(A == null){
		return null;
	}
	return preToBST(A, 0, A.length - 1);
}
private TreeNode preToBST(int[] A, int start, int end){
	if(A == null){
		return null;
	}
	if(start > end){
		return null;
	}
	TreeNode root = new TreeNode(A[start]);
	//find the first index that is larger than root.val，so try to find the "index" where A[index] is larger than A[start]
	//by using binary search. if we cant find such index, it means there is nothing in right child. so we check whether end > start
	//if so it means, it must has a left child 
	int a = start;
	int b = end;
	int mid;
	int index = start;
	while(a + 1 < b){
		mid = (b - a)/2 + a;
		if(A[mid] > root.val){
			b = mid;
		}
		else{
			a = mid;
		}
	}
	if(A[a] > root.val){
		index = a;
	}
	else if(A[b] > root.val){
		index = b;
	}
	index = -1;
	TreeNode left = null;
	TreeNode right = null;
	if(index != -1){
		right = preToBST(A, index, end);
	}
	if(end > start){
		if(index != -1){
			left = preToBST(A, start + 1, index - 1);
		}
		else{
			left = preToBST(A, start + 1, end);
		}
	}
	root.left = left;
	root.right = right;
	return root;
}



9.serialization
10.delete a node

