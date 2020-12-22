import javafx.application.Application;
import javafx.stage.Stage;
import viewcontroller.PocketCalcC;

public class TheMain extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        PocketCalcC.show(primaryStage);
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}