import java.util.*;

public class Node {

    private Node up, right, down, left;
    private int x, y;
    private boolean visited, finalDestination;

    public Node(int x_, int y_) {
        x = x_;
        y = y_;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setUp(Node node){
        up = node;
    }

    public void setDown(Node node){
        down = node;
    }

    public void setRight(Node node){
        right = node;
    }

    public void setLeft(Node node){
        left = node;
    }

    public Node getRight(){
        return right;
    }

    public Node getDown(){
        return down;
    }

    public Node getUp(){
        return up;
    }

    public Node getLeft(){
        return left;
    }

    @Override
    public String toString(){
        return "(" + y + " " + x + ")";
    }

    public void setFinalDestination(){
        finalDestination = true;
    }

    public boolean isFinalDestination(){
        return finalDestination;
    }

    public void setVisited(){
        visited = true;
    }

    public boolean getVisited(){
        return visited;
    }

    public Node getUnVisitedNode(){
        List<Node> list = new ArrayList<>();
        if (left != null && !left.visited)
            list.add(left);
        if (right != null && !right.visited)
            list.add(right);
        if (up != null && !up.visited)
            list.add(up);
        if(down != null && !down.visited)
            list.add(down);

        if(!list.isEmpty()) {
            Random rndm = new Random();
            int i = rndm.nextInt(list.size());
            return list.get(i);
        }else
            return null;
    }
}