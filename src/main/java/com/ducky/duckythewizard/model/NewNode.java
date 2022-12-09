package com.ducky.duckythewizard.model;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class NewNode {
    AnchorPane parentAnchorPane;
    public NewNode(AnchorPane parentAnchorPane) {
        this.parentAnchorPane = parentAnchorPane;
    }

    public void addNodeToParent(Node newNode) {
        this.parentAnchorPane.getChildren().add(newNode);
    }

    public void removeNodeFromParent(Node deleteNode) {
        this.parentAnchorPane.getChildren().remove(deleteNode);
    }
}
