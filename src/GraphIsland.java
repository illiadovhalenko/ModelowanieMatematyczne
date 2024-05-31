import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Queue;


public class GraphIsland {
    int rows, cols;
    Node startPoint;
    ArrayList<ArrayList<Node>> grid;
    Random random = new Random();

    public GraphIsland(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        grid = new ArrayList<>(rows);

        for (int y = 0; y < rows; y++) {
            ArrayList<Node> row = new ArrayList<>(cols);

            for (int x = 0; x < cols; x++) {
                Node newNode = new Node(x, y);

                if (x > 0) {
                    newNode.setLeft(row.get(x - 1));
                    row.get(x - 1).setRight(newNode);
                }

                if (y > 0) {
                    newNode.setUp(grid.get(y - 1).get(x));
                    grid.get(y - 1).get(x).setDown(newNode);
                }

                row.add(newNode);
            }

            grid.add(row);
        }

        startPoint = grid.get(0).get(0);
        setRandomFinalDestination();

        int max = ((rows - 1) * cols + (cols - 1) * rows) / 2;
        int numberBrokenEdges = random.nextInt(++max);
//        System.out.println("Removing " + numberBrokenEdges + " edges.");
        removeEdges(numberBrokenEdges);
    }

    private void setRandomFinalDestination() {
        Random random = new Random();
        int randX = (int) (random.nextGaussian()*200+500);
        int randY =  (int) (random.nextGaussian()*200+500);
        if(randX>=cols)
        {
            randX=cols-1;
        }
        if(randX<0)
        {
            randX=0;
        }
        if(randY>=rows)
        {
            randY=rows-1;
        }
        if(randY<0)
        {
            randY=0;
        }
        grid.get(randY).get(randX).setFinalDestination();
//        System.out.println("Final Destination: " + "(" + randY + ", " + randX + ")");
    }

    private void removeEdges(int numberBrokenEdges) {
        ArrayList<Edge> edges = new ArrayList<>();

        // Collect all edges
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                Node node = grid.get(y).get(x);
                if (node.getRight() != null) edges.add(new Edge(node, node.getRight()));
                if (node.getDown() != null) edges.add(new Edge(node, node.getDown()));
            }
        }

        int removedEdges = 0;
        while (removedEdges < numberBrokenEdges && !edges.isEmpty()) {
            int index = random.nextInt(edges.size());
            Edge edge = edges.get(index);
            edges.remove(index);

            // Temporarily remove edge
            edge.remove();

            // Check if graph is still connected
            if (isConnected()) {
                removedEdges++;
            } else {
                // Restore edge if graph is not connected
                edge.restore();
            }
        }
    }

    private boolean isConnected() {
        Set<Node> visited = new HashSet<>();
        Stack<Node> stack = new Stack<>();
        stack.push(startPoint);

        while (!stack.isEmpty()) {
            Node node = stack.pop();
            if (!visited.contains(node)) {
                visited.add(node);
                if (node.getRight() != null && !visited.contains(node.getRight())) stack.push(node.getRight());
                if (node.getDown() != null && !visited.contains(node.getDown())) stack.push(node.getDown());
                if (node.getLeft() != null && !visited.contains(node.getLeft())) stack.push(node.getLeft());
                if (node.getUp() != null && !visited.contains(node.getUp())) stack.push(node.getUp());
            }
        }

        // Check if all nodes are visited
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                if (!visited.contains(grid.get(y).get(x))) {
                    return false;
                }
            }
        }
        return true;
    }

    public void drawGraph(String filePath) {
        int imageSize = 600;
        int margin = 50;
        int nodeRadius = 10;
        int stepX = (imageSize - 2 * margin) / (cols - 1);
        int stepY = (imageSize - 2 * margin) / (rows - 1);

        BufferedImage image = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fill background
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, imageSize, imageSize);

        // Draw edges
        g2d.setColor(Color.BLACK);
        for (ArrayList<Node> row : grid) {
            for (Node node : row) {
                int x1 = margin + node.getX() * stepX;
                int y1 = margin + node.getY() * stepY;

                if (node.getRight() != null) {
                    int x2 = margin + node.getRight().getX() * stepX;
                    int y2 = margin + node.getRight().getY() * stepY;
                    g2d.drawLine(x1, y1, x2, y2);
                }

                if (node.getDown() != null) {
                    int x2 = margin + node.getDown().getX() * stepX;
                    int y2 = margin + node.getDown().getY() * stepY;
                    g2d.drawLine(x1, y1, x2, y2);
                }
            }
        }

        // Draw nodes
        for (ArrayList<Node> row : grid) {
            for (Node node : row) {
                int x = margin + node.getX() * stepX;
                int y = margin + node.getY() * stepY;
                if (node.isFinalDestination()) {
                    g2d.setColor(Color.RED);
                } else {
                    g2d.setColor(Color.BLUE);
                }
                g2d.fillOval(x - nodeRadius, y - nodeRadius, 2 * nodeRadius, 2 * nodeRadius);
            }
        }

        // Save image to file
        try {
            ImageIO.write(image, "PNG", new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        g2d.dispose();
    }

    public int passAllGraph(){
        int numberOfSteps = 0;
        boolean down = true;
        Node currentNode = startPoint;

        if(currentNode.isFinalDestination()){
            System.out.println("Found final destination" + currentNode);
            return numberOfSteps;
        }

        while (currentNode != null){

            while((down ? currentNode.getDown() : currentNode.getUp()) != null){

                System.out.print(currentNode + " ");

                if(currentNode.isFinalDestination()){
                    System.out.println("Found final destination");
                    return numberOfSteps;
                }

                currentNode = (down ? currentNode.getDown() : currentNode.getUp());
                numberOfSteps++;
            }
            System.out.print(currentNode + " ");
            if(currentNode.isFinalDestination()){
                System.out.println("Found final destination" + currentNode);
                return numberOfSteps;
            }

            currentNode = currentNode.getRight();
            down = !down;
            numberOfSteps++;
        }

        return numberOfSteps;
    }

//    public int depthFirstSearch() {
//        int numberOfSteps = 0;
//        if(startPoint.isFinalDestination()){
//            System.out.println("Found final destination" + startPoint);
//            return numberOfSteps;
//        }
//        Set<Node> visited = new HashSet<>();
//        Stack<Node> stack = new Stack<>();
//        stack.push(startPoint);
//
//        System.out.println("Visited: ");
//        while (!stack.isEmpty()) {
//            Node node = stack.pop();
//            if (!visited.contains(node)) {
//                visited.add(node);
//                numberOfSteps++;
//                System.out.print(node + " ");
//
//                if (node.getRight() != null && !visited.contains(node.getRight())) stack.push(node.getRight());
//                if (node.getDown() != null && !visited.contains(node.getDown())) stack.push(node.getDown());
//                if (node.getLeft() != null && !visited.contains(node.getLeft())) stack.push(node.getLeft());
//                if (node.getUp() != null && !visited.contains(node.getUp())) stack.push(node.getUp());
//            }
//        }
//        System.out.println("Number of steps:" + numberOfSteps);
//        return numberOfSteps;
//    }
    public int depthFirstSearch() {
        int numberOfSteps = 0;
        if(startPoint.isFinalDestination()){
//            System.out.println("Found final destination " + startPoint);
//            System.out.println("Number of steps: " + numberOfSteps);
            return numberOfSteps;
        }
        Set<Node> visited = new HashSet<>();
        Stack<Node> path = new Stack<>();
        List<Node> result = new ArrayList<>();
        path.push(startPoint);

        while (!path.isEmpty()) {
            Node current = path.peek();
            if(current.isFinalDestination()){
//                System.out.println("Found final destination " + current);
                result.add(current);
                numberOfSteps++;
//                System.out.println("DFS Path with Backtracking: " + result);
//                System.out.println("Number of steps: " + numberOfSteps);
                return numberOfSteps;
            }
            result.add(current);
            numberOfSteps++;

            if (!visited.contains(current)) {
                visited.add(current);
            }

            Node next = getUnvisitedNeighbor(current, visited);
            if (next != null) {
                path.push(next);
            } else {
                path.pop();
            }
        }

//        System.out.println("DFS Path with Backtracking: " + result);
//        System.out.println("Number of steps: " + numberOfSteps);
        return numberOfSteps;
    }

    private Node getUnvisitedNeighbor(Node node, Set<Node> visited) {
        if (node.getRight() != null && !visited.contains(node.getRight())) return node.getRight();
        if (node.getDown() != null && !visited.contains(node.getDown())) return node.getDown();
        if (node.getLeft() != null && !visited.contains(node.getLeft())) return node.getLeft();
        if (node.getUp() != null && !visited.contains(node.getUp())) return node.getUp();
        return null;
    }

    public int breadthFirstSearch() {
        int numberOfSteps = 0;
        if(startPoint.isFinalDestination()){
            System.out.println("Found final destination" + startPoint);
            return numberOfSteps;
        }
        Set<Node> visited = new HashSet<>();
        Queue<Node> queue = new LinkedList<>();
        queue.add(startPoint);

        System.out.println("Visited: ");
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (!visited.contains(node)) {
                visited.add(node);
                numberOfSteps++;
                System.out.print(node + " ");

                if (node.getRight() != null && !visited.contains(node.getRight())) queue.add(node.getRight());
                if (node.getDown() != null && !visited.contains(node.getDown())) queue.add(node.getDown());
                if (node.getLeft() != null && !visited.contains(node.getLeft())) queue.add(node.getLeft());
                if (node.getUp() != null && !visited.contains(node.getUp())) queue.add(node.getUp());
            }
        }
        System.out.println("Number of steps:" + numberOfSteps);
        return numberOfSteps;
    }
}