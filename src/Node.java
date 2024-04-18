public class Node {
    private Node up, right, down, left;
    private int x, y;
    private boolean visited, finalDestination;
    public Node(int x_, int y_) {
        x = x_;
        y = y_;
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
    @Override
    public String toString(){
        return "("+y+" "+x+")";
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
}
