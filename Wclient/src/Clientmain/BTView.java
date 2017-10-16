package Clientmain;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.scene.layout.Pane;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.shape.StrokeLineCap;

public class BTView extends Pane {

    /**
     * @author WeihuaWang
     */
    static int dx = 1;
    static int dy = 1;
    private Wclient tree = new Wclient();
    private double radius = 15;
    private double vGap = 40;

    public BTView(Wclient tree) {
        this.tree = tree;
        setStatus("Tree is empty");
    }

    public void setStatus(String msg) {
        getChildren().add(new Text(20, 20, msg));
    }

    public void displayTree() {
        this.getChildren().clear();
         
        if (tree.getRoot() != null) {
            displayTreeSearch(Wclient.temproot, getWidth() / 2, vGap, getWidth() / 4);
        }
    }

    public void displayTreeInsert(int pr_iVlaue) {
        this.getChildren().clear();
        tree.clearList();
        if (tree.getRoot() != null) {
            displayTreeSearchNew(Wclient.temproot, getWidth() / 2, vGap, getWidth() / 4, pr_iVlaue);
        
            new Timeline(
            new KeyFrame(new Duration(7_00), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                  //  tree.insert(pr_iVlaue);           
                }
            })).play();
        
        }
    }

    public void displayTreeDelete(int pr_iVlaue, double prevXValue, double prevYValue, int displayStatus,int pr_iLevel) {
        this.getChildren().clear();
        if (Wclient.temproot != null) {
            displayTreeSearchDelete(Wclient.temproot, getWidth() / 2, vGap, prevXValue, prevYValue,
                    getWidth() / 4, pr_iVlaue, displayStatus, false, pr_iLevel);
        
         new Timeline(
            new KeyFrame(new Duration(7_00), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    tree.delete(pr_iVlaue);           
                }
            })).play();    
            
        new Timeline(
            new KeyFrame(new Duration(7_00), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    displayTree();
                   
                }
            })).play();
        
        }
        
    }

    private void displayTreeSearch(Node root, double x, double y, double hGap) {
        System.out.println("==** Data: "+root.element+" Level : "+root.level );
        System.out.println("Previous data  Data: "+root.element+" X: " + x + " Y: " + y );
        if (root != null) {
            root.xOldValue = root.xValue = x;
            root.yOldValue = root.yValue = y;

        }//if ends for setting x & y

        if (root.left != null) {
            Line l_lineObj = new Line(x, y, x, y);
            l_lineObj.setStrokeWidth(2);
            l_lineObj.setStrokeLineCap(StrokeLineCap.ROUND);
            if (tree.checkItemInSearchList(root.left.element)) {
                System.out.println("ELEMENT LEFT COLOR" + root.element);
                l_lineObj.setStroke(Color.web("E96811"));
            } else {
                l_lineObj.setStroke(Color.web("000000"));
            }

            getChildren().add(l_lineObj);

            new SequentialTransition(
                    new Timeline(
                            new KeyFrame(Duration.ONE, new KeyValue(l_lineObj.endXProperty(), x - hGap),
                                    new KeyValue(l_lineObj.endYProperty(), y + vGap))
                    )).play();

            displayTreeSearch(root.left, x - hGap, y + vGap, hGap / 2);
        }
        if (root.right != null) {
            Line l_lineObj = new Line(x, y, x, y);
            l_lineObj.setStrokeWidth(2);
            l_lineObj.setStrokeLineCap(StrokeLineCap.ROUND);
            if (tree.checkItemInSearchList(root.right.element)) {
                System.out.println("ELEMENT RIGHT COLOR" + root.element);
                l_lineObj.setStroke(Color.web("E96811"));
            } else {
                l_lineObj.setStroke(Color.web("000000"));
            }

            getChildren().add(l_lineObj);

            new SequentialTransition(
                    new Timeline(
                            new KeyFrame(Duration.ONE, new KeyValue(l_lineObj.endXProperty(), x + hGap),
                                    new KeyValue(l_lineObj.endYProperty(), y + vGap))
                    )).play();

            displayTreeSearch(root.right, x + hGap, y + vGap, hGap / 2);
        }

        Circle circle = new Circle(x, y, radius);
        circle.setFill(Color.ANTIQUEWHITE);

        if (tree.checkItemInSearchList(root.element)) {
            circle.setFill(Color.DARKGRAY);
            circle.setStroke(Color.RED);

            Timeline tl = new Timeline(new KeyFrame(Duration.millis(2000),
                    new KeyValue(circle.scaleXProperty(), 1.2), new KeyValue(circle.scaleYProperty(), 1.2)));
            tl.setCycleCount(Timeline.INDEFINITE);
            tl.setAutoReverse(true);

            new SequentialTransition(tl).play();

        } else {
            circle.setStroke(Color.BLUEVIOLET);
        }

        getChildren().addAll(circle, new Text(x - 8, y + 4, root.element + ""));

    }

    private void displayTreeSearchNew(Node root, double x, double y, double hGap, int pr_newNodeValue) {
        long duration = 4_000L;
            System.out.println("==** Data: "+root.element+" Level : "+root.level );
           System.out.println("Previous data  Data: "+root.element+" X: " + x + " Y: " + y );
        
        if (root != null) {
            root.xOldValue = root.xValue = x;
            root.yOldValue = root.yValue = y;

        }//if ends for setting x & y

          System.out.println("==>> DISPLAY  DATA " + root.element + " X: " + root.xValue+" Y: "+root.yValue);

        
        if (root.left != null) {

            Line l_lineObj = new Line(x, y, x, y);
            l_lineObj.setStrokeWidth(2);
            l_lineObj.setStrokeLineCap(StrokeLineCap.ROUND);

            if (tree.checkItemInSearchList(root.left.element)) {
                System.out.println("ELEMENT LEFT COLOR" + root.element);
                l_lineObj.setStroke(Color.web("E96811"));

            } else {
                l_lineObj.setStroke(Color.web("000000"));
            }

            getChildren().add(l_lineObj);

            if (root.left.element == pr_newNodeValue) {
                
                new SequentialTransition(
                        new Timeline(
                                new KeyFrame(new Duration(duration / 8), new KeyValue(l_lineObj.endXProperty(), x - hGap),
                                        new KeyValue(l_lineObj.endYProperty(), y + vGap))
                        )).play();
            } else {
                new SequentialTransition(
                        new Timeline(
                                new KeyFrame(Duration.ONE, new KeyValue(l_lineObj.endXProperty(), x - hGap),
                                        new KeyValue(l_lineObj.endYProperty(), y + vGap))
                        )).play();
            }

            displayTreeSearchNew(root.left, x - hGap, y + vGap, hGap / 2, pr_newNodeValue);
        }
        if (root.right != null) {

            Line l_lineObj = new Line(x, y, x, y);
            l_lineObj.setStrokeWidth(2);

            if (tree.checkItemInSearchList(root.right.element)) {
                
                l_lineObj.setStroke(Color.web("E96811"));
            } else {
                l_lineObj.setStroke(Color.web("000000"));
            }

            getChildren().add(l_lineObj);

            if (pr_newNodeValue == root.right.element) {
                
                new SequentialTransition(
                        new Timeline(
                                new KeyFrame(new Duration(duration / 8), new KeyValue(l_lineObj.endXProperty(), x + hGap),
                                        new KeyValue(l_lineObj.endYProperty(), y + vGap))
                        )).play();
            } else {
                new SequentialTransition(
                        new Timeline(
                                new KeyFrame(Duration.ONE, new KeyValue(l_lineObj.endXProperty(), x + hGap),
                                        new KeyValue(l_lineObj.endYProperty(), y + vGap))
                        )).play();
            }
            displayTreeSearchNew(root.right, x + hGap, y + vGap, hGap / 2, pr_newNodeValue);
        }

        Circle circle = new Circle(x, y, radius);
        circle.setFill(Color.ANTIQUEWHITE);

        if (tree.checkItemInSearchList(root.element)) {
            circle.setStroke(Color.RED);

        } else {
            circle.setStroke(Color.BLUEVIOLET);
        }

        getChildren().addAll(circle, new Text(x - 8, y + 4, root.element + ""));

    }

      private void displayTreeSearchDelete(Node root, double x, double y,
            double prevXValue, double prevYValue, double hGap, int pr_newNodeValue,
            int displayStatus, boolean flagStatus, int pr_iLevel) {
        long duration = 4_000L;

     //   System.out.println(" DELETE ELEMENT" + root.element);
        System.out.println("\n Data: "+root.element+" X: " + prevXValue + " Y: " 
                + prevYValue +" Flag : "+flagStatus+" LEVEL : "+pr_iLevel);
     //     System.out.println(" X : "+x+" Y : "+y );
     
//   System.out.println("DISPLAY  DATA " + root.element + "X: " + root.xValue);

        if (flagStatus) {
            root.xValue = prevXValue;
            root.yValue = prevYValue;
        }

        if (root.left != null) {

            if (root.left.element == pr_newNodeValue && (root.left.left != null)) {

                Line l_lineObj = new Line(root.xValue, root.yValue, root.left.left.xValue, root.left.left.yValue);//for movement
                l_lineObj.setStrokeWidth(2);
                l_lineObj.setStrokeLineCap(StrokeLineCap.ROUND);

                if (tree.checkItemInSearchList(root.left.element)) {
                 //
                  //  System.out.println("ELEMENT LEFT COLOR" + root.element);
                    l_lineObj.setStroke(Color.web("E96811"));
                } else {
                    l_lineObj.setStroke(Color.web("000000"));
                }

                getChildren().add(l_lineObj);
                //System.out.println("DELETE ENTER 11");
                new SequentialTransition(
                        new Timeline(
                                new KeyFrame(new Duration(duration / 8), new KeyValue(l_lineObj.endXProperty(), prevXValue),
                                        new KeyValue(l_lineObj.endYProperty(), prevYValue))
                        )).play();//playing transition from child to parent x & y
            } else if (root.left.element == pr_newNodeValue && (root.left.right != null) && (root.left.left == null)) {

                Line l_lineObj = new Line(root.xValue, root.yValue, root.left.right.xValue, root.left.right.yValue);//for movement
                l_lineObj.setStrokeWidth(2);
                l_lineObj.setStrokeLineCap(StrokeLineCap.ROUND);

                if (tree.checkItemInSearchList(root.left.element)) {
                //    System.out.println("ELEMENT LEFT COLOR" + root.element);
                    l_lineObj.setStroke(Color.web("E96811"));
                } else {
                    l_lineObj.setStroke(Color.web("000000"));
                }

                getChildren().add(l_lineObj);
               // System.out.println("DELETE ENTER 11");
                new SequentialTransition(
                        new Timeline(
                                new KeyFrame(new Duration(duration / 8), new KeyValue(l_lineObj.endXProperty(), prevXValue),
                                        new KeyValue(l_lineObj.endYProperty(), prevYValue))
                        )).play();//playing transition from child to parent x & y
            } else if (root.left.element == pr_newNodeValue && (root.left.left == null)) {
                //nothing to do for deleting the leaf node
            } else {

                Line l_lineObj  = null;
               if( flagStatus )
                    l_lineObj = new Line(root.xOldValue, root.yOldValue, root.left.xValue, root.left.yValue);
               else
                    l_lineObj = new Line(root.xValue, root.yValue, root.left.xValue, root.left.yValue);
                
                
                l_lineObj.setStrokeWidth(2);
                l_lineObj.setStrokeLineCap(StrokeLineCap.ROUND);

                if (tree.checkItemInSearchList(root.left.element)) {
               //     System.out.println("ELEMENT LEFT COLOR" + root.element);
                    l_lineObj.setStroke(Color.web("E96811"));
                } else {
                //    l_lineObj.setStroke(Color.web("000000"));
                }

                getChildren().add(l_lineObj);

                if (flagStatus) {
                    
                    if( root.level == pr_iLevel ){
                        new SequentialTransition(
                            new Timeline(
                                    new KeyFrame(new Duration(duration / 8), new KeyValue(l_lineObj.startXProperty(), prevXValue),
                                            new KeyValue(l_lineObj.startYProperty(), prevYValue))
                            )).play();
                    }else
                    {
                        new SequentialTransition(
                            new Timeline(
                                    new KeyFrame(new Duration(duration / 8), new KeyValue(l_lineObj.startXProperty(), root.xValue),
                                            new KeyValue(l_lineObj.startYProperty(), root.yValue))
                            )).play();
                   
                    }

                } else {
                    new SequentialTransition(
                            new Timeline(
                                     new KeyFrame(Duration.ONE, new KeyValue(l_lineObj.endXProperty(), root.left.xValue),
                                            new KeyValue(l_lineObj.endYProperty(), root.left.yValue))
                            )).play();

                }

            }

            if (displayStatus == DISPLAY_STAGES.MIDDLE && root.left.element == pr_newNodeValue) {
                if (root.left.left != null) {
                    flagStatus = true;
               //     System.out.println("DELETE 22 A LEFT DATA" + root.left.left.element + "X: " + root.left.left.xValue
               //             + "Y :" + root.left.left.yValue);

                    if (root.left.right != null) {
                        Line l_lineObj = new Line(root.left.left.xValue, root.left.left.yValue, root.left.right.xValue, root.left.right.yValue);//for movement
                        l_lineObj.setStrokeWidth(2);
                        l_lineObj.setStrokeLineCap(StrokeLineCap.ROUND);

                        if (tree.checkItemInSearchList(root.left.element)) {
                            l_lineObj.setStroke(Color.web("E96811"));
                        } else {
                            l_lineObj.setStroke(Color.web("000000"));
                        }

                        getChildren().add(l_lineObj);
                        new SequentialTransition(
                                new Timeline(
                                        new KeyFrame(new Duration(duration / 8), new KeyValue(l_lineObj.startXProperty(), prevXValue),
                                                new KeyValue(l_lineObj.startYProperty(), prevYValue))
                                )).play();//playing transition from child to parent x & y

                    }

                    displayTreeSearchDelete(root.left.left, root.left.left.xValue, root.left.left.yValue,
                            prevXValue, prevYValue, hGap, pr_newNodeValue, displayStatus, 
                            flagStatus, pr_iLevel); 
                }

                if (root.left.right != null && root.left.left == null) {
                    flagStatus = true;
                    displayTreeSearchDelete(root.left.right, root.left.right.xValue, root.left.right.yValue,
                            prevXValue, prevYValue, hGap, pr_newNodeValue, displayStatus, flagStatus, pr_iLevel);
                } else if (root.left.right != null) {
                    displayTreeSearchDelete(root.left.right, root.left.right.xValue, root.left.right.yValue,
                            prevXValue, prevYValue, hGap, pr_newNodeValue, displayStatus, false, pr_iLevel);
                }

            } else {
                displayTreeSearchDelete(root.left, root.left.xValue, root.left.yValue, 
                        prevXValue, prevYValue,hGap, pr_newNodeValue, displayStatus, false, pr_iLevel);
            }

        }//left side ends

        if (root.right != null) {

            if (root.right.element == pr_newNodeValue && (root.right.left != null)) {

                Line l_lineObj = new Line(x, y, root.right.left.xValue, root.right.left.yValue);//for movement
                l_lineObj.setStrokeWidth(2);
                l_lineObj.setStrokeLineCap(StrokeLineCap.ROUND);

                if (tree.checkItemInSearchList(root.right.element)) {
                //    System.out.println("ELEMENT LEFT COLOR" + root.element);
                    l_lineObj.setStroke(Color.web("E96811"));
                } else {
                    l_lineObj.setStroke(Color.web("000000"));
                }

                getChildren().add(l_lineObj);
          //      System.out.println("DELETE ENTER 11");
                new SequentialTransition(
                        new Timeline(
                                new KeyFrame(new Duration(duration / 8), new KeyValue(l_lineObj.endXProperty(), prevXValue),
                                        new KeyValue(l_lineObj.endYProperty(), prevYValue))
                        )).play();//playing transition from child to parent x & y
            } 
            else if (root.right.element == pr_newNodeValue && (root.right.right != null)) {

                Line l_lineObj = new Line(x, y, root.right.right.xValue, root.right.right.yValue);//for movement
                l_lineObj.setStrokeWidth(2);
                l_lineObj.setStrokeLineCap(StrokeLineCap.ROUND);

                if (tree.checkItemInSearchList(root.right.element)) {
                    l_lineObj.setStroke(Color.web("E96811"));
                } else {
                    l_lineObj.setStroke(Color.web("000000"));
                }

                getChildren().add(l_lineObj);
                new SequentialTransition(
                        new Timeline(
                                new KeyFrame(new Duration(duration / 8), new KeyValue(l_lineObj.endXProperty(), prevXValue),
                                        new KeyValue(l_lineObj.endYProperty(), prevYValue))
                        )).play();//playing transition from child to parent x & y
            }
            else if (root.right.element == pr_newNodeValue && (root.right.right == null)) {
                //nothing to do for leaf nodes
            } else {

                Line l_lineObj = new Line(x, y, root.right.xValue, root.right.yValue);
                l_lineObj.setStrokeWidth(2);
                l_lineObj.setStrokeLineCap(StrokeLineCap.ROUND);

                if (tree.checkItemInSearchList(root.right.element)) {
                   // System.out.println("ELEMENT LEFT COLOR" + root.element);
                    l_lineObj.setStroke(Color.web("E96811"));
                } else {
                    l_lineObj.setStroke(Color.web("000000"));
                }

                getChildren().add(l_lineObj);

                if (flagStatus ) {
                    
                    if ( root.level == pr_iLevel )
                    {
                        new SequentialTransition(
                            new Timeline(
                                    new KeyFrame(new Duration(duration / 8), new KeyValue(l_lineObj.startXProperty(), prevXValue),
                                            new KeyValue(l_lineObj.startYProperty(), prevYValue))
                            )).play();
                    }else
                    {
                        new SequentialTransition(
                            new Timeline(
                                    new KeyFrame(new Duration(duration / 8), new KeyValue(l_lineObj.startXProperty(), root.xValue),
                                            new KeyValue(l_lineObj.startYProperty(), root.yValue))
                            )).play();
                        
                        
                    }
                    
                } else {
                    new SequentialTransition(
                            new Timeline(
                                    new KeyFrame(Duration.ONE, new KeyValue(l_lineObj.endXProperty(), root.right.xValue),
                                            new KeyValue(l_lineObj.endYProperty(), root.right.yValue))
                            )).play();

                }

            }

            if (displayStatus == DISPLAY_STAGES.MIDDLE && root.right.element == pr_newNodeValue) {
                if (root.right.left != null) {
                    flagStatus = true;
                    
                    if (root.right.right != null) {
                        Line l_lineObj = new Line(root.right.left.xValue, root.right.left.yValue, root.right.right.xValue, root.right.right.yValue);//for movement
                        l_lineObj.setStrokeWidth(2);
                        l_lineObj.setStrokeLineCap(StrokeLineCap.ROUND);

                        if (tree.checkItemInSearchList(root.right.element)) {
                            l_lineObj.setStroke(Color.web("E96811"));
                        } else {
                            l_lineObj.setStroke(Color.web("000000"));
                        }

                        getChildren().add(l_lineObj);
                        new SequentialTransition(
                                new Timeline(
                                        new KeyFrame(new Duration(duration / 8), new KeyValue(l_lineObj.startXProperty(), prevXValue),
                                                new KeyValue(l_lineObj.startYProperty(), prevYValue))
                                )).play();//playing transition from child to parent x & y

                    }

                    displayTreeSearchDelete(root.right.left, root.right.left.xValue, root.right.left.yValue,
                            prevXValue, prevYValue, hGap, pr_newNodeValue, displayStatus, flagStatus, pr_iLevel);
                }

                if (root.right.right != null && root.right.left == null) {
                    flagStatus = true;
                    
                    displayTreeSearchDelete(root.right.right, root.right.right.xValue, root.right.right.yValue,
                            prevXValue, prevYValue, hGap, pr_newNodeValue, displayStatus, flagStatus, pr_iLevel);
                } else if (root.right.right != null) {

                    displayTreeSearchDelete(root.right.right, root.right.right.xValue, root.right.right.yValue,
                            prevXValue, prevYValue, hGap, pr_newNodeValue, displayStatus, false, pr_iLevel);
                }

            } else {
                    displayTreeSearchDelete(root.right, root.right.xValue, root.right.yValue, 
                        prevXValue, prevYValue,hGap, pr_newNodeValue, displayStatus, false, pr_iLevel);
            }
        }//else ends

        Circle circle = null;
        

        Text l_textObj = null;
        if (flagStatus) {
            l_textObj = new Text(root.xOldValue - 8, root.yOldValue + 4, root.element + "");
            circle = new Circle(root.xOldValue, root.yOldValue, radius);
        } else {
            l_textObj = new Text(x - 8, y + 4, root.element + "");
            circle = new Circle(x, y, radius);
        }
        
        circle.setFill(Color.ANTIQUEWHITE);

        //System.out.println("=====>> DATA" + root.element + "X: " + root.xValue + "Y :" + root.yValue);
        //System.out.println("OLD==>> DATA" + root.element + "X: " + root.xOldValue + "Y :" + root.yOldValue);

        if (tree.checkItemInSearchList(root.element)) {
            circle.setStroke(Color.RED);
        } else {
            circle.setStroke(Color.BLUEVIOLET);
        }

        getChildren().addAll(circle, l_textObj);
        if (displayStatus == DISPLAY_STAGES.MIDDLE && flagStatus) {
            new SequentialTransition(
                    new Timeline(
                            new KeyFrame(new Duration(duration / 8), new KeyValue(circle.centerXProperty(), root.xValue),
                                    new KeyValue(circle.centerYProperty(), root.yValue)),
                            new KeyFrame(new Duration(duration / 8), new KeyValue(l_textObj.xProperty(), root.xValue - 8),
                                    new KeyValue(l_textObj.yProperty(), root.yValue + 4))
                    )).play();
            flagStatus = false;
        }
    }
}
