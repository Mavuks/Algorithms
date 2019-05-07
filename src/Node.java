
import java.util.*;

public class Node {

    /// Abiks.   https://gist.github.com/not-much-io/01c6abfd14a9df1fefb7
    // Natukene Abiks, väga aru ei saanud mida ta tegi seal... see programm pidi tegema vasakpoolse suluesituse.    https://enos.itcollege.ee/~ylari/I231/Node.java

    private String name;
    private Node firstChild;
    private Node nextSibling;

    Node (String n, Node d, Node r) {
        this.name = n;
        this.firstChild = d;
        this.nextSibling  = r;

    }

    public static Node parsePostfix (String s) {
        if (s.contains(",,")) { throw new RuntimeException("String; " + s + " Contains two commas.");
        } else if (s.contains("\t")) { throw new RuntimeException("String; " + s + " Contains tab char");
        } else if (s.contains(" ")) { throw new RuntimeException("String: " + s +" Contains space");
        } else if (s.contains("()")) { throw new RuntimeException("String: " + s + " Contains empty subtree");
        } else if (s.contains("((") && s.contains("))")) { throw new RuntimeException("String: " + s + " Contain two Brackets");
        } else if (s.contains(",") && !(s.contains("(") && s.contains(")"))) { throw new RuntimeException("String:" + s + " Contains two roots");}
        for (int i = 0; i < s.length(); i++){
            if(s.charAt(i) == '(' && s.charAt(i+1) == ',')
                throw new RuntimeException("String:"+ s +" Contains comma error, parenthesis can't be followed by comma ");
        }


        Stack<Node> stack = new Stack<>();
        Node node = new Node(null,null,null);
        StringTokenizer string = new StringTokenizer(s,"(),",true);
        while(string.hasMoreTokens()){
            String token = string.nextToken().trim();
            switch (token) {
                case "(":
                    stack.push(node);
                    node.firstChild = new Node(null, null, null);
                    node = node.firstChild;
                    break;
                case ")":
                    node = stack.pop();
                    break;
                case ",":
                    if (stack.empty())
                        throw new RuntimeException("Stack Is Empty!");
                    node.nextSibling = new Node(null, null, null);
                    node = node.nextSibling;
                    break;
                default:
                    node.name = token;
                    break;
            }
        }
        return node;
    }

    public String leftParentheticRepresentation() {
        StringBuilder string = new StringBuilder();
        string.append(this.name);
        if (this.firstChild != null){
            string.append("(");
            string.append(this.firstChild.leftParentheticRepresentation());
            string.append(")");
        }
        if (this.nextSibling != null){
            string.append(",");
            string.append(this.nextSibling.leftParentheticRepresentation());
        }
        return string.toString();


    }

    public static void main (String[] param) {
        String s = " ";
        Node t = Node.parsePostfix (s);
        String v = t.leftParentheticRepresentation();
        System.out.println (s + " ==> " + v); // (B1,C)A ==> A(B1,C)
    }
}

