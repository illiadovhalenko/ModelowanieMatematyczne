import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

enum Direction{
    LEFT, RIGHT, DOWN, UP
}

public class Graph {
    int n, m;
    Node startPoint;
    ArrayList<ArrayList<Node>> map;

    public Graph(int n_, int m_){

        n = n_;
        m = m_;
        map = new ArrayList<>(n);

        for(int y = 0; y < m; y++){
            ArrayList<Node> oneRow = new ArrayList<>(n);

            for (int x = 0; x < n; x++) {
                Node newNode = new Node(x, y);

                if(x != 0) {
                    newNode.setLeft(oneRow.get(x - 1));
                    oneRow.get(x - 1).setRight(newNode);
                }

                if(y != 0){
                    newNode.setUp(map.get(y - 1).get(x));
                    map.get(y - 1).get(x).setDown(newNode);
                }

                oneRow.add(newNode);
            }

            map.add(oneRow);
        }

        startPoint = map.get(0).get(0);
        Random random = new Random();
        int randX = (int) (random.nextGaussian()*200+500);
        int randY =  (int) (random.nextGaussian()*200+500);
        if(randX>=m)
        {
            randX=m-1;
        }
        if(randX<0)
        {
            randX=0;
        }
        if(randY>=n)
        {
            randY=n-1;
        }
        if(randY<0)
        {
            randY=0;
        }
        map.get(randY).get(randX).setFinalDestination();
//        System.out.println("Skarb: " + "(" + randX + " " + randY + ")");
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        for(int y = 0; y < n; y++){
            for(int x = 0; x < n; x++){
                stringBuilder.append(map.get(y).get(x)).append(" ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    /*
    firstMethod() - like snake
    secondMethod() - walking like in a maze (moving along one wall), spiral from the edge to the center
    thirdMethod() - spiral from the center to the edges
    randomDirectionMethod()
     */

    public int firstMethod(){
        int numberOfSteps = 0;
        boolean down = true;
        Node currentNode = startPoint;

        if(currentNode.isFinalDestination()){
//            System.out.println("Found final destination" + currentNode);
            return numberOfSteps;
        }

        while (currentNode != null){

            while((down ? currentNode.getDown() : currentNode.getUp()) != null){

//                System.out.print(currentNode + " ");

                if(currentNode.isFinalDestination()){
//                    System.out.println("Found final destination");
                    return numberOfSteps;
                }

                currentNode = (down ? currentNode.getDown() : currentNode.getUp());
                numberOfSteps++;
            }
//            System.out.print(currentNode + " ");
            if(currentNode.isFinalDestination()){
//                System.out.println("Found final destination" + currentNode);
                return numberOfSteps;
            }

            currentNode = currentNode.getRight();
            down = !down;
            numberOfSteps++;
        }

        return numberOfSteps;
    }

    /**
     * 0 -- down
     * 1 -- right
     * 2 -- up
     * 3 -- left
     * @return -- number of steps to reach finalDestination
     */

    public int randomDirectionMethod(){
        int numberOfSteps = 0;
        Node currentNode = startPoint;
        Stack<Node> stack = new Stack<>();
        if(currentNode.isFinalDestination()) return numberOfSteps;
        stack.push(currentNode);
        while (!stack.isEmpty() && !currentNode.isFinalDestination()){
//            System.out.println(currentNode);
            currentNode.setVisited();
            currentNode = stack.peek().getUnVisitedNode();
            numberOfSteps++;
            if(currentNode == null){
                currentNode = stack.pop();
//                System.out.println("pop "+ currentNode);
                numberOfSteps++;
            }else{
                stack.push(currentNode);
            }
        }
//        System.out.println(currentNode);
        return numberOfSteps;
    }

    public int secondMethod(){
        int numberOfSteps = 0;
        Node currentNode = startPoint;
        int direction = 0;
//        System.out.print(currentNode + " ");
        if(currentNode.isFinalDestination()) return numberOfSteps;
        while(!currentNode.isFinalDestination()){
            switch (direction % 4){
                case 0 -> {
                    while(currentNode.getDown() != null && !currentNode.getDown().getVisited()){
                        currentNode.setVisited();
                        currentNode = currentNode.getDown();
                        numberOfSteps++;
//                        System.out.print(currentNode + " ");
                        if(currentNode.isFinalDestination()){
//                            System.out.print(numberOfSteps);
                            return numberOfSteps;
                        }
                    }
                    direction++;
                }
                case 1->{
                    while(currentNode.getRight() != null && !currentNode.getRight().getVisited()){
                        currentNode.setVisited();
                        currentNode = currentNode.getRight();
                        numberOfSteps++;
//                        System.out.print(currentNode + " ");
                        if(currentNode.isFinalDestination()){
//                            System.out.print(numberOfSteps);
                            return numberOfSteps;
                        }
                    }
                    direction++;
                }
                case 2 ->{
                    while(currentNode.getUp() != null && !currentNode.getUp().getVisited()){
                        currentNode.setVisited();
                        currentNode = currentNode.getUp();
                        numberOfSteps++;
//                        System.out.print(currentNode + " ");
                        if(currentNode.isFinalDestination()){
//                            System.out.print(numberOfSteps);
                            return numberOfSteps;
                        }
                    }
                    direction++;
                }
                case 3->{
                    while(currentNode.getLeft() != null && !currentNode.getLeft().getVisited()){
                        currentNode.setVisited();
                        currentNode = currentNode.getLeft();
                        numberOfSteps++;
//                        System.out.print(currentNode + " ");
                        if(currentNode.isFinalDestination()){
//                            System.out.print(numberOfSteps);
                            return numberOfSteps;
                        }
                    }
                    direction++;
                }
            }
        }
        return numberOfSteps;
    }

    public int thirdMethod(){
        int numberOfSteps = 0;
        Node currentNode = startPoint;
        Node centerNode;
        boolean odd = false;

        //depends on the size where the center is
        if( n % 2 == 0) centerNode = map.get(n / 2 - 1).get(n / 2 - 1);
        else{
            centerNode = map.get(n / 2).get(n / 2);
            odd = true;
        }

        //the path to our center  right|down
        currentNode.setVisited();
//        System.out.print(currentNode + " ");
        if(currentNode.isFinalDestination()) return numberOfSteps;
        while(currentNode != centerNode){

            currentNode = currentNode.getRight();
//            System.out.print(currentNode + " ");
            currentNode.setVisited();
            numberOfSteps++;
            if(currentNode.isFinalDestination()) return numberOfSteps;

            currentNode = currentNode.getDown();
//            System.out.print(currentNode + " ");
            currentNode.setVisited();
            numberOfSteps++;
            if(currentNode.isFinalDestination()) return numberOfSteps;
        }

        // now we have currentNode == centerNode
//        System.out.print(currentNode + " ");
        if(odd) {
            for (int i = 1; i < n; i++) {
                if (i == n - 1) {
                    for (int j = 1; j <= i; j++) {
                        currentNode = currentNode.getRight();
//                        System.out.print(currentNode + " ");
                        if(!currentNode.getVisited()){
                            currentNode.setVisited();
                            numberOfSteps++;
                        }
                        if(currentNode.isFinalDestination()) return numberOfSteps;
                    }
                    for (int j = 1; j <= i; j++) {
                        currentNode = currentNode.getUp();
//                        System.out.print(currentNode + " ");
                        if(!currentNode.getVisited()){
                            currentNode.setVisited();
                            numberOfSteps++;
                        }
                        if(currentNode.isFinalDestination()) return numberOfSteps;
                    }
                    for (int j = 1; j <= i; j++) {
                        currentNode = currentNode.getLeft();
//                        System.out.print(currentNode + " ");
                        if(!currentNode.getVisited()){
                            currentNode.setVisited();
                            numberOfSteps++;
                        }
                        if(currentNode.isFinalDestination()) return numberOfSteps;
                    }
                } else if (i % 2 != 0) {
                    for (int j = 1; j <= i; j++) {
                        currentNode = currentNode.getLeft();
//                        System.out.print(currentNode + " ");
                        if(!currentNode.getVisited()){
                            currentNode.setVisited();
                            numberOfSteps++;
                        }
                        if(currentNode.isFinalDestination()) return numberOfSteps;
                    }
                    for (int j = 1; j <= i; j++) {
                        currentNode = currentNode.getDown();
//                        System.out.print(currentNode + " ");
                        if(!currentNode.getVisited()){
                            currentNode.setVisited();
                            numberOfSteps++;
                        }
                        if(currentNode.isFinalDestination()) return numberOfSteps;
                    }
                } else {
                    for (int j = 1; j <= i; j++) {
                        currentNode = currentNode.getRight();
//                        System.out.print(currentNode + " ");
                        if(!currentNode.getVisited()){
                            currentNode.setVisited();
                            numberOfSteps++;
                        }
                        if(currentNode.isFinalDestination()) return numberOfSteps;
                    }
                    for (int j = 1; j <= i; j++) {
                        currentNode = currentNode.getUp();
//                        System.out.print(currentNode + " ");
                        if(!currentNode.getVisited()){
                            currentNode.setVisited();
                            numberOfSteps++;
                        }
                        if(currentNode.isFinalDestination()) return numberOfSteps;
                    }
                }
            }
        }
        else{ //if even
            for (int i = 1; i < n; i++) {
                if (i == n - 1) {
                    for (int j = 1; j <= i; j++) {
                        currentNode = currentNode.getRight();
//                        System.out.print(currentNode + " ");
                        if(!currentNode.getVisited()){
                            currentNode.setVisited();
                            numberOfSteps++;
                        }
                        if(currentNode.isFinalDestination()) return numberOfSteps;
                    }
                    for (int j = 1; j <= i; j++) {
                        currentNode = currentNode.getUp();
//                        System.out.print(currentNode + " ");
                        if(!currentNode.getVisited()){
                            currentNode.setVisited();
                            numberOfSteps++;
                        }
                        if(currentNode.isFinalDestination()) return numberOfSteps;
                    }
                    for (int j = 1; j <= i; j++) {
                        currentNode = currentNode.getLeft();
//                        System.out.print(currentNode + " ");
                        if(!currentNode.getVisited()){
                            currentNode.setVisited();
                            numberOfSteps++;
                        }
                        if(currentNode.isFinalDestination()) return numberOfSteps;
                    }
                }
                else if(i == 1){
                    currentNode = currentNode.getRight();
//                    System.out.print(currentNode + " ");
                    if(!currentNode.getVisited()){
                        currentNode.setVisited();
                        numberOfSteps++;
                    }
                    if(currentNode.isFinalDestination()) return numberOfSteps;
                    currentNode = currentNode.getDown();
//                    System.out.print(currentNode + " ");
                    if(!currentNode.getVisited()){
                        currentNode.setVisited();
                        numberOfSteps++;
                    }
                    if(currentNode.isFinalDestination()) return numberOfSteps;
                    currentNode = currentNode.getLeft();
//                    System.out.print(currentNode + " ");
                    if(!currentNode.getVisited()){
                        currentNode.setVisited();
                        numberOfSteps++;
                    }
                    if(currentNode.isFinalDestination()) return numberOfSteps;
                    currentNode = currentNode.getUp();
//                    System.out.print(currentNode + " ");
                    if(!currentNode.getVisited()){
                        currentNode.setVisited();
                        numberOfSteps++;
                    }
                    if(currentNode.isFinalDestination()) return numberOfSteps;
                    currentNode = currentNode.getLeft();
//                    System.out.print(currentNode + " ");
                    if(!currentNode.getVisited()){
                        currentNode.setVisited();
                        numberOfSteps++;
                    }
                    if(currentNode.isFinalDestination()) return numberOfSteps;
                }
                else if(i == 2){
                    for (int j = 1; j <= i; j++) {
                        currentNode = currentNode.getDown();
//                        System.out.print(currentNode + " ");
                        if(!currentNode.getVisited()){
                            currentNode.setVisited();
                            numberOfSteps++;
                        }
                        if(currentNode.isFinalDestination()) return numberOfSteps;
                    }
                }
                else if (i % 2 == 0) {
                    for (int j = 1; j <= i; j++) {
                        currentNode = currentNode.getLeft();
//                        System.out.print(currentNode + " ");
                        if(!currentNode.getVisited()){
                            currentNode.setVisited();
                            numberOfSteps++;
                        }
                        if(currentNode.isFinalDestination()) return numberOfSteps;
                    }
                    for (int j = 1; j <= i; j++) {
                        currentNode = currentNode.getDown();
//                        System.out.print(currentNode + " ");
                        if(!currentNode.getVisited()){
                            currentNode.setVisited();
                            numberOfSteps++;
                        }
                        if(currentNode.isFinalDestination()) return numberOfSteps;
                    }
                } else {
                    for (int j = 1; j <= i; j++) {
                        currentNode = currentNode.getRight();
//                        System.out.print(currentNode + " ");
                        if(!currentNode.getVisited()){
                            currentNode.setVisited();
                            numberOfSteps++;
                        }
                        if(currentNode.isFinalDestination()) return numberOfSteps;
                    }
                    for (int j = 1; j <= i; j++) {
                        currentNode = currentNode.getUp();
//                        System.out.print(currentNode + " ");
                        if(!currentNode.getVisited()){
                            currentNode.setVisited();
                            numberOfSteps++;
                        }
                        if(currentNode.isFinalDestination()) return numberOfSteps;
                    }
                }
            }
        }
        return numberOfSteps;
    }

    public void print(){
        for (ArrayList<Node> row : map) {
            for (Node node : row) {
                System.out.print(node + " ");
            }
            System.out.println();
        }
    }

}