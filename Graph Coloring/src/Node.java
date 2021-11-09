
import java.util.ArrayList;
import java.util.Objects;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Hamed Khashehchi
 */
public class Node {

    private int priority;
    private String name;
    private ArrayList<Node> adjacent;
    private ArrayList<Node> connection;
    private ArrayList<Color> domain;
    private Color color;

    public Node(int priority, String name, ArrayList<Color> domain) {
        this.priority = priority;
        this.name = name;
        this.adjacent = new ArrayList<>();
        this.connection = new ArrayList<>();
        this.domain = domain;
        setColor(this.domain.get((int) (Math.floor(Math.random() * this.domain.size()))));
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Node> getConnection() {
        return connection;
    }

    public ArrayList<Node> getAdjacent() {
        return adjacent;
    }

    public int getPriority() {
        return priority;
    }

    public void addAdjacent(Node adjacent) {
        this.adjacent.add(adjacent);
        this.addConnection(adjacent);
    }

    public void addConnection(Node connection) {
        this.connection.add(connection);
    }

    @Override
    public String toString() {
        return "{ name = " + this.name + ", priority = " + this.priority + ", color = " + this.color + ", adjacent = " + this.adjacent + ", connections = " + this.connection + "}"; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.priority;
        hash = 67 * hash + Objects.hashCode(this.name);
        hash = 67 * hash + Objects.hashCode(this.adjacent);
        hash = 67 * hash + Objects.hashCode(this.connection);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Node other = (Node) obj;
        if (this.priority != other.priority) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.adjacent, other.adjacent)) {
            return false;
        }
        if (!Objects.equals(this.connection, other.connection)) {
            return false;
        }
        return true;
    }

}
