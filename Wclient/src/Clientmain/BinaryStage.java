package Clientmain;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Collections;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

class DISPLAY_STAGES {
   public static final int INITIAL  = 1;
   public static final int MIDDLE   = 2;
   public static final int FINAL    = 3;
  }


public class BinaryStage extends Application {
	
        public Wclient extree;
        public Scene scene;
        public BTView view;
        public BorderPane bp;
        public Button btnInsert;        
        public Button btnSearch;
        public Button btnDelete;
        public VBox vbox;
	public Label traverLabel;
        public HBox hbox ;
	public TextField tKey;
        public static boolean initialStatus = false;
        public static boolean flagUpdate = true;
        /**
	 * @author WeihuaWang
	 */       
        public void initalization(Stage primaryStage)
        {            
                bp = new BorderPane();
                scene = new Scene(bp, 1000, 650);
		primaryStage.setTitle("Welcome to Binary Tree World");
		
		extree = new Wclient();  			//Edit Point
		view = new BTView(extree);          //Edit Point
		
		view.setMaxSize(600, 500);
		
		vbox=new VBox();
		traverLabel=new Label("");
		traverLabel.setAlignment(Pos.CENTER);
		traverLabel.setFont(new Font("Arial", 12));
		traverLabel.setOnMouseEntered(e->{
			traverLabel.setScaleX(1.5);
			traverLabel.setScaleY(1.5);
		});
		traverLabel.setOnMouseExited(e->{
			traverLabel.setScaleX(1);
			traverLabel.setScaleY(1);
		});
		traverLabel.setWrapText(true);
		HBox hbox = new HBox(15);
		hbox.setPadding(new Insets(15, 12, 15, 12));
		tKey = new TextField();
		btnInsert = new Button("Insert");
		btnSearch = new Button("Search");
		btnDelete = new Button("Delete");
		hbox.getChildren().addAll(new Label("Enter a Value:  "), tKey, btnInsert, btnSearch, btnDelete);
		hbox.setAlignment(Pos.CENTER);
		vbox.getChildren().addAll(traverLabel,hbox);
		vbox.setAlignment(Pos.CENTER);
	
                
                BinaryStage.initialStatus = true;
        }
        
	public void start(Stage primaryStage) {
		// main pane
		final int MAXVALUE=100;
		final int GENERATE=8;		
		
                //if ( BinaryStage.initialStatus == false )
                 initalization(primaryStage);
                
                  
                
                view.setStatus("");
		MenuBar menuBar = new MenuBar();
		Menu menuFile = new Menu("File");
		MenuItem empty = new MenuItem("New an Empty one");
		empty.setOnAction(e -> {
			extree.clear();
			traverLabel.setText("");
			view.displayTree();
			view.setStatus("Binary Tree is empty");
			tKey.clear();
		});
		MenuItem randomGen = new MenuItem("Grow randomly");
		randomGen.setOnAction(e -> {
			//extree.clear();
			traverLabel.setText("");
			ArrayList<Integer> arr = new ArrayList<Integer>();
			for (int i = 0; i < MAXVALUE; i++) {
				arr.add((int) (Math.random()*MAXVALUE+1));
			}			
			Collections.shuffle(arr);
			for(int j=0;j<GENERATE;j++){
				extree.insert(arr.get(j));
			}
			view.displayTree();
			view.setStatus("a random binary tree is built");
			tKey.clear();
		});

		MenuItem exit = new MenuItem("Exit");
		exit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				//extree.removeip();
				System.exit(0);				
			}
		});
		menuFile.getItems().addAll(empty, randomGen, exit);
		
		Menu traverse = new Menu("Traverse");
		MenuItem inorder = new MenuItem("Inorder");
		inorder.setOnAction(e -> {
                    flagUpdate = true;
			extree.inorder();
			traverLabel.setText("The Binary Tree in inorder: "+extree.result);
                        flagUpdate = false;
		});
		MenuItem preorder = new MenuItem("PreOrder");
		preorder.setOnAction(e->{
                    flagUpdate = true;
			extree.preorder();
			traverLabel.setText("The Binary Tree in preorder: "+extree.result);
                        flagUpdate = false;
		});
		MenuItem postorder = new MenuItem("PostOrder");
		postorder.setOnAction(e->{
                    flagUpdate = true;
			extree.postorder();
			traverLabel.setText("The Binary Tree in postorder: "+extree.result);
                       flagUpdate = false;
		});		
		traverse.getItems().addAll(inorder, preorder, postorder);
		menuBar.getMenus().addAll(menuFile, traverse);
		
		
		
		bp.setTop(menuBar);
		bp.setCenter(view);
		bp.setBottom(vbox);
		primaryStage.setScene(scene);		
		primaryStage.show();
		
		//////REFRESH EVERY 3 SECONDS.
                view.displayTree();
                if ( flagUpdate )
                { 
		Timeline timeline = new Timeline(new KeyFrame(
		        Duration.millis(3000),
		        ae -> view.displayTree()));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
                }
		/////REFRESH
				
		btnInsert.setOnAction(e -> {
			flagUpdate = true;
                        traverLabel.setText("");
                        view.setStatus("");
                        extree.clearList();
			int key = Integer.parseInt(tKey.getText());
			boolean insertconfirmation = extree.insert(key);
			if (!insertconfirmation) {	
                                
				view.setStatus(key + " is already in the tree");
			} else {				
				view.displayTreeInsert(key);
				view.setStatus(key + " is inserted in the tree");
			}
                        flagUpdate = false;
		});
		
		btnDelete.setOnAction(e -> {
                        flagUpdate = true;
                        traverLabel.setText("");
			int key = Integer.parseInt(tKey.getText());
			boolean pathExist = extree.path(key).isEmpty();
            double prevXValue = extree.getXValue(key);
            double prevYValue = extree.getYValue(key);
                        
            view.setStatus("");
           if( !pathExist)
                view.displayTreeDelete(key,prevXValue,prevYValue, DISPLAY_STAGES.MIDDLE,extree.getLevel(key));			
			
                
               if ( pathExist) {
                                
				view.setStatus(key + " is not in the tree");
			} else {	
                                
				view.setStatus(key + " is deleted in the tree");
			}
                        flagUpdate = false;
		});
		btnSearch.setOnAction(e -> {
                    flagUpdate = true;
			traverLabel.setText("");
			int key = Integer.parseInt(tKey.getText());			
                           view.setStatus("");
               extree.clearList();
			if (!extree.search(key)) {
                                view.displayTree();
				view.setStatus(key + " is not in the tree");
			} else {
				// animation show search steps.				
				view.displayTree();
				view.setStatus(key + " is found in the tree");
				//traverLabel.setText("The path of "+key+" is:  "+tree.path(key).toString());
			}
                        flagUpdate = false;
		});
		
	}

	public static void main(String[] args) {
		
		launch(args);		
	}
}
