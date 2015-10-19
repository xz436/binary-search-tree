
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
9.serialization
10.delete a node

