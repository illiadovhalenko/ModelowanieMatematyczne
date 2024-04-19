import java.util.ArrayList;
import java.util.Random;

enum Direction{
    LEFT, RIGHT, DOWN,
}
public class Graph {
    int n;
    Node startPoint;
    ArrayList<ArrayList<Node>> map;
    public Graph(int n_){
        n = n_;
        map = new ArrayList<>(n);
        for(int y=0; y<n; y++) {
            ArrayList<Node> oneRow = new ArrayList<>(n);
            for (int x = 0; x < n; x++) {
                Node newNode = new Node(x, y);
                if(x!=0) {
                    newNode.setLeft(oneRow.get(x-1));
                    oneRow.get(x-1).setRight(newNode);
                }
                if(y!=0){
                    newNode.setUp(map.get(y-1).get(x));
                    map.get(y-1).get(x).setDown(newNode);
                }
                oneRow.add(newNode);
            }
            map.add(oneRow);
        }
        startPoint = map.get(0).get(0);
        Random random = new Random();
        int randX = random.nextInt(n);
        int randY = random.nextInt(n);
        map.get(randY).get(randX).setFinalDestination();
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        for(int y=0; y<n; y++){
            for(int x=0; x<n; x++){
                stringBuilder.append(map.get(y).get(x)).append(" ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public int firstMethod(){
        int numberOfSteps = 0;
        boolean down = true;
        Node currentNode = startPoint;
//        System.out.println(currentNode.getRight());
        while (currentNode.getRight()!=null){
            while((down ? currentNode.getDown() : currentNode.getUp())!= null){
                System.out.println("0"+currentNode);
                if(currentNode.isFinalDestination()){
                    System.out.println("Found final destination"+ currentNode);
                    return numberOfSteps;
                }
                currentNode = (down ? currentNode.getDown() : currentNode.getUp());
                numberOfSteps++;
            }
            System.out.println("1"+currentNode);
            if(currentNode.isFinalDestination()){
                System.out.println("Found final destination" + currentNode);
                return numberOfSteps;
            }
            currentNode = currentNode.getRight();
            down = !down;
            numberOfSteps++;
        }
        return -1;
    }

    /**
     * 0 -- down
     * 1 -- right
     * 2 -- up
     * 3 -- left
     * @return -- number of steps to reach finalDestination
     */
//    public int secondMethod(){
//        int numberOfSteps = 0;
//        Node currentNode = startPoint;
//        currentNode.setVisited();
//        int direction = 0;
//        while(!currentNode.isFinalDestination()){
//            System.out.println("lalal");
//        }
//    }
    public int randomDirectionMethod(){
        int numberOfSteps = 0;
        Node currentNode = startPoint;
        int direction = 0;
        Random random = new Random();
        while(!currentNode.isFinalDestination()){
            direction = random.nextInt(4);
            switch (direction){
                case 0 -> {
                    if(currentNode.getDown()!=null){
                        currentNode = currentNode.getDown();
                        numberOfSteps++;
                    }
                }
                case 1->{
                    if(currentNode.getRight()!=null){
                        currentNode = currentNode.getRight();
                        numberOfSteps++;
                    }
                }
                case 2 ->{
                    if(currentNode.getUp()!=null){
                        currentNode = currentNode.getUp();
                        numberOfSteps++;
                    }
                }
                case 3->{
                    if(currentNode.getLeft()!=null){
                        currentNode = currentNode.getLeft();
                        numberOfSteps++;
                    }
                }
            }
        }
        return numberOfSteps;
    }

    public int thirdMethod(){
        int numberOfSteps = 0;

        return -1;
    }
}


