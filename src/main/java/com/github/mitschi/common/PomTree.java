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

    public static class Node<T> {
        private T data;
        private String file;
        private Node<T> parent;
        private List<Node<T>> children;

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
