class Edge {
    Node node1, node2;
    boolean isVertical;

    public Edge(Node node1, Node node2) {
        this.node1 = node1;
        this.node2 = node2;
        this.isVertical = node1.getX() == node2.getX();
    }

    public void remove() {
        if (isVertical) {
            node1.setDown(null);
            node2.setUp(null);
        } else {
            node1.setRight(null);
            node2.setLeft(null);
        }
    }

    public void restore() {
        if (isVertical) {
            node1.setDown(node2);
            node2.setUp(node1);
        } else {
            node1.setRight(node2);
            node2.setLeft(node1);
        }
    }
}