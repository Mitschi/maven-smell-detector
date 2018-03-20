package com.github.mitschi.common;

import org.apache.maven.pom._4_0.Model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PomTree<T> {
    private Node<T> root;

    public PomTree(T rootData, String f) {
        root = new Node<T>();
        root.data = rootData;
        root.file = f;
        root.children = new ArrayList<Node<T>>();
    }

    public Node<T> getRoot() {
        return this.root;
    }

    public static int depth = 0;

    public void printTree(Node<T> startNode) {

        if(startNode.equals(this.root))
            System.out.println(((Model)startNode.getData()).getArtifactId());

        if(startNode.getChildren() != null) {
            for (Node<T> n : startNode.getChildren()) {

                for(int i = 0; i < depth; i++) {
                    System.out.print("   ");
                }

                System.out.println(" └─" + ((Model)n.getData()).getArtifactId());

                depth++;
                printTree(n);
                depth--;
            }

        }


    }

    public Node<T> findNode(Node<T> startNode, String groupID, String artifactID, String version) {

        if(startNode.getChildren() != null) {
            for(Node<T> n : startNode.getChildren()) {
                Model m = (Model)n.getData();

                int searchFields = 0;
                int matches = 0;


                if(groupID != null) {
                    searchFields++;

                    String nodeGroupID = m.getGroupId();

                    // if the node has no groupID, get the groupID of the parent
                    // TODO: what if the parent has no groupID? search parents until found or no more parents exist
                    if(nodeGroupID == null) {
                        if(m.getParent().getGroupId() != null) {
                            nodeGroupID = m.getParent().getGroupId();
                        }
                    }

                    if(nodeGroupID != null) {
                        if(nodeGroupID.equals(groupID)) {
                            matches++;
                        }
                    }
                }

                if(artifactID != null) {
                    searchFields++;
                    String nodeArtifactID = m.getArtifactId();

                    // if the node has no artifactID, get the artifactID of the parent
                    // TODO: what if the parent has no artifactID? search parents until found or no more parents exist
                    if(nodeArtifactID == null) {
                        if(m.getParent().getArtifactId() != null) {
                            nodeArtifactID = m.getParent().getArtifactId();
                        }
                    }

                    if(nodeArtifactID != null) {
                        if(nodeArtifactID.equals(artifactID)) {
                            matches++;
                        }
                    }

                }

                if(version != null) {
                    searchFields++;
                    String nodeVersion = m.getVersion();

                    // if the node has no version, get the version of the parent
                    // TODO: what if the parent has no version? search parents until found or no more parents exist
                    if(nodeVersion == null) {
                        if(m.getParent().getVersion() != null) {
                            nodeVersion = m.getParent().getVersion();
                        }
                    }

                    if(nodeVersion != null) {
                        if(nodeVersion.equals(version)) {
                            matches++;
                        }
                    }

                }

                if(matches == searchFields) {
                    return n;
                } else {
                    findNode(n, groupID, artifactID, version);
                }

            }
        }

        return null;
    }

    public static class Node<T> {
        private T data;
        private String file;
        private Node<T> parent;
        private List<Node<T>> children;

        public Node() {
            this.children = new ArrayList<>();
        }

        public void addChild(Node<T> child) {
            children.add(child);
        }

        public void removeChild(Node<T> child) {
            children.remove(child);
        }

        public List<Node<T>> getChildren() {
            return this.children;
        }

        public void setFile(String f) {
            this.file = f;
        }

        public String getFile() {
            return this.file;
        }

        public Node<T> getParent() {
            return this.parent;
        }

        public void setParent(Node<T> parent) {
            this.parent = parent;
        }

        public T getData() {
            return this.data;
        }

        public void setData(T data) {
            this.data = data;
        }
    }
}
