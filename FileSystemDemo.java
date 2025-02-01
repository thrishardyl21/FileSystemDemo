import java.util.*;

class Node {
    String name;
    List<Node> children;

    public Node(String name) {
        this.name = name;
        this.children = new ArrayList<>();
    }

    public Node findChild(String name) {
        for (Node child : children) {
            if (child.name.equals(name)) {
                return child;
            }
        }
        return null;
    }

    public void printStructure(String indent) {
        System.out.println(indent + name);
        for (Node child : children) {
            child.printStructure(indent + "  ");
        }
    }
}

class FileSystem {
    Node root;

    public FileSystem() {
        this.root = new Node("Root");
    }

    public void create(String parentName, String folderName) {
        Node parent = findNode(root, parentName);
        if (parent != null) {
            parent.children.add(new Node(folderName));
            System.out.println("Created folder: " + folderName + " under " + parentName);
        } else {
            System.out.println("Parent folder not found.");
        }
    }

    public void rename(String oldName, String newName) {
        Node node = findNode(root, oldName);
        if (node != null) {
            node.name = newName;
            System.out.println("Renamed " + oldName + " to " + newName);
        } else {
            System.out.println("Folder not found.");
        }
    }

    public void delete(String folderName) {
        deleteNode(root, folderName);
    }

    public void move(String folderName, String newParentName) {
        Node node = findNode(root, folderName);
        Node newParent = findNode(root, newParentName);

        if (node != null && newParent != null) {
            deleteNode(root, folderName); // Remove from old location
            newParent.children.add(node); // Add to new location
            System.out.println("Moved " + folderName + " to " + newParentName);
        } else {
            System.out.println("Move operation failed: Folder or destination not found.");
        }
    }

    private Node findNode(Node current, String name) {
        if (current.name.equals(name)) {
            return current;
        }
        for (Node child : current.children) {
            Node found = findNode(child, name);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    private boolean deleteNode(Node current, String name) {
        Iterator<Node> iterator = current.children.iterator();
        while (iterator.hasNext()) {
            Node child = iterator.next();
            if (child.name.equals(name)) {
                iterator.remove();
                System.out.println("Deleted folder: " + name);
                return true;
            } else {
                if (deleteNode(child, name)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void display() {
        root.printStructure("");
    }
}

public class FileSystemDemo {
    public static void main(String[] args) {
        FileSystem fs = new FileSystem();

        // Step 1: Create Folder1 under Root
        fs.create("Root", "Folder1");

        // Step 2: Create Folder2 under Root
        fs.create("Root", "Folder2");

        // Step 3: Rename Folder2 to NewFile
        fs.rename("Folder2", "NewFile");

        // Step 4: Delete Folder1
        fs.delete("Folder1");

        // Display final structure
        System.out.println("\nFinal File System Structure:");
        fs.display();
    }
}
