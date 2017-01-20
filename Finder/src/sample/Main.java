package sample;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;

public class Main extends Application {
    public FileChooser fileChooser = new FileChooser();
    TextField path = new TextField();
    TextField find = new TextField();
    TextArea result = new TextArea();
    String content;
    String pattern;
    File file;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Pane root = new Pane();
        Scene scene = new Scene(root, 1000, 500);

        Button selectBut = new Button("Открыть");
        selectBut.setOnAction(event -> {
            file = fileChooser.showOpenDialog(primaryStage);
            if (file != null){
                path.setText(file.getAbsolutePath());
            }
        });

        CheckBox regExpCheck = new CheckBox();


        Button findBut = new Button("Найти");
        findBut.setOnAction(event -> {
            content = "";
            try {
                    List<String> lines = Files.readAllLines(Paths.get(path.getText()), StandardCharsets.UTF_8);
                    for(String line: lines){
                        if (regExpCheck.isSelected()){
                            if (Pattern.matches(find.getText(),line)){
                                content +=line + "\n";
                            }
                        }else {
                            if (line.toLowerCase().contains(find.getText().toLowerCase())) {
                                content += line + "\n";
                            }
                        }
                        result.setText(content);
                    }
            } catch (IOException e) {
                e.printStackTrace();

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No file on this path"+path.getText());
                alert.showAndWait();
            }
        });

        Button showAll = new Button("показать текст");
        showAll.setOnAction(event -> {
            content = "";
            try {
                List<String> lines = Files.readAllLines(Paths.get(path.getText()), StandardCharsets.UTF_8);
                for (String line:lines){
                    content+=line+"\n";
                    result.setText(content);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Button save = new Button("Сохранить");
        save.setOnAction(event -> {
            try {
                Files.write(Paths.get(path.getText()), result.getText().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        path.setPrefWidth(400);
        selectBut.setTranslateX(405);
        selectBut.setPrefWidth(100);
        find.setTranslateY(30);
        find.setPrefWidth(400);
        findBut.setPrefWidth(100);
        findBut.setTranslateX(405);
        findBut.setTranslateY(30);
        result.setPrefWidth(999);
        result.setPrefHeight(440);
        result.setTranslateY(60);
        regExpCheck.setTranslateX(510);
        regExpCheck.setTranslateY(30);
        Label label = new Label("Использовать регулярные выражения");
        label.setTranslateY(30);
        label.setTranslateX(530);
        showAll.setTranslateX(510);
        save.setTranslateX(610);

        root.getChildren().addAll(path, selectBut, findBut, find,result, regExpCheck,label, showAll, save);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
