package com.example.demo3;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventDispatcher;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Random;

//======================================================// Главный класс
public class App extends Application {
    Pane root_pane = new Pane();

    boolean rect1Direction = true;
    boolean rect2Direction = true;
    boolean rect3Direction = true;
    boolean rect4Direction = true;

    public void moveRectangle(Rectangle rectangle, int n) {
        rectangle.setFill(Color.GRAY);
        boolean rectD = true;
        if (n == 1) rectD = rect1Direction;
        if (n == 2) rectD = rect2Direction;
        if (n == 3) rectD = rect3Direction;
        if (n == 4) rectD = rect4Direction;


        if (rectD) {
            rectangle.setHeight(rectangle.getHeight() + 1);
        } else {
            rectangle.setHeight(rectangle.getHeight() - 1);
        }

        if (rectangle.getHeight() < 10) {
            if (n == 1) rect1Direction = true;
            if (n == 2) rect2Direction = true;
            if (n == 3) rect3Direction = true;
            if (n == 4) rect4Direction = true;
        } else if (rectangle.getHeight() >= 500) {
            if (n == 1) rect1Direction = false;
            if (n == 2) rect2Direction = false;
            if (n == 3) rect3Direction = false;
            if (n == 4) rect4Direction = false;
        }
    }

    double width = 20;
    double height = 200;

    private Rectangle getRectangle(double x, double y) {
        Rectangle rectangle = new Rectangle(width, height, Color.GRAY);
        rectangle.setX(x);
        rectangle.setY(y);
        return rectangle;
    }

    Rectangle rect1 = getRectangle(10, 50);
    Rectangle rect2 = getRectangle(50, 50);
    Rectangle rect3 = getRectangle(90, 50);
    Rectangle rect4 = getRectangle(130, 50);


    Text t1 = getText(String.valueOf(rect1.getHeight() * rect1.getWidth()), rect1.getX(), rect1.getY() - 20, Color.BLACK);
    Text t2 = getText(String.valueOf(rect2.getHeight() * rect2.getWidth()), rect2.getX(), rect2.getY() - 20, Color.BLACK);
    Text t3 = getText(String.valueOf(rect3.getHeight() * rect3.getWidth()), rect3.getX(), rect3.getY() - 20, Color.BLACK);
    Text t4 = getText(String.valueOf(rect4.getHeight() * rect4.getWidth()), rect4.getX(), rect4.getY() - 20, Color.BLACK);
    Text totaL = getText("", 200, 700, Color.BLACK);




    public Text getText(String str, double x, double y, Color c) {
        Text sr1 = new Text();
        sr1.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        sr1.setX(x);
        sr1.setY(y);
        sr1.setText(str);
        sr1.setFill(c);

        return sr1;
    }
    @Override
    public void start(Stage stage) {
        //====== заголовок окна
        stage.setTitle("Лабораторна работа №2. Вар 10. КН-19 Кухоль Є.В.");
        stage.setResizable(false); //фиксированный размер  //====== создать узлы сцены c графикой

        Task<Void> moveRect1 = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (true) {
                    Platform.runLater(() -> moveRectangle(rect1, 1));
                    Thread.sleep(15);
                }
            }
        };

        Task<Void> moveRect2 = new Task<>() {
            @Override
            protected Void call() throws Exception {
                while (true) {
                    Platform.runLater(() -> moveRectangle(rect2, 2));

                    Thread.sleep(30);
                }
            }
        };

        Task<Void> moveRect3 = new Task<>() {
            @Override
            protected Void call() throws Exception {
                while (true) {
                    Platform.runLater(() -> moveRectangle(rect3, 3));

                    Thread.sleep(10);
                }
            }
        };

        Task<Void> moveRect4 = new Task<>() {
            @Override
            protected Void call() throws Exception {
                while (true) {
                    Platform.runLater(() -> moveRectangle(rect4, 4));

                    Thread.sleep(20);
                }
            }
        };

        Task<Void> calcS = new Task<>() {
            @Override
            protected Void call() throws Exception {
                while (true) {
                    Platform.runLater(() -> {
                        double s1 = rect1.getHeight() * rect1.getWidth();
                        double s2 = rect2.getHeight() * rect2.getWidth();
                        double s3 = rect3.getHeight() * rect3.getWidth();
                        double s4 = rect4.getHeight() * rect4.getWidth();
                        double total = s1 + s2 + s3 + s4;

                        t1.setText(String.valueOf(s1));
                        t2.setText(String.valueOf(s2));
                        t3.setText(String.valueOf(s3));
                        t4.setText(String.valueOf(s4));

                        totaL.setText("Total S: " + total);
                    });

                    Thread.sleep(100);
                }
            }
        };


        new Thread(calcS).start();
        new Thread(moveRect1).start();
        new Thread(moveRect2).start();
        new Thread(moveRect3).start();
        new Thread(moveRect4).start();


        stage.setOnCloseRequest((evt) -> {
            calcS.cancel();
            moveRect1.cancel();
            moveRect3.cancel();
            moveRect2.cancel();
            moveRect4.cancel();
        });

        root_pane.getChildren().addAll(rect1, rect2, rect3, rect4, t1, t2, t3, t4, totaL);
        Scene scene = new Scene(root_pane, 500, 1000, Color.TRANSPARENT);  //====== поместить сцену в окно
        stage.setScene(scene);
        stage.show();
    }

    //====================================================== // Точка входа в программу (запускает метод start) //======================================================
    public static void main(String[] args) {
        launch(args);
    }
}