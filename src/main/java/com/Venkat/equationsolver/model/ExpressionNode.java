package com.Venkat.equationsolver.model;

public class ExpressionNode {

    private String value;
    private ExpressionNode left;
    private ExpressionNode right;

    public ExpressionNode(String value){
        this.value=value; // for leaf node
    }

    public ExpressionNode(String value, ExpressionNode left, ExpressionNode right){
        this.value=value;
        this.left=left; // for operators
        this.right=right;
    }

    public String getValue(){
        return value;
    }

    public void setvalue(String value){
        this.value=value;
    }

    public ExpressionNode getLeft(){
        return left;
    }

    public void setLeft(ExpressionNode left){
        this.left=left;
    }

    public ExpressionNode getRight(){
        return right;
    }

    public void setRight(ExpressionNode right){
        this.right=right;
    }

}
