package controller.Kien_Controller;

import com.jfoenix.controls.JFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import models.DataModel;
import models.Question;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class QuizResultScreenController implements Initializable {

    @FXML
    private MFXScrollPane scrollPane;

    @FXML
    private Label completedOn;

    @FXML
    private Label finishReviewBtn;

    @FXML
    private Label grade;

    @FXML
    private Label marks;

    @FXML
    private VBox progressContent;

    @FXML
    private FlowPane progressPane;

    @FXML
    private VBox quizListContainer;

    public Label startedOn;

    @FXML
    private Label state;

    @FXML
    private Label time;

    @FXML
    private HBox userSection;

    VBox[] progressNodes;
    private Integer questionQuantity;
    private Map<Integer, Integer> userAnswer;

    public void setStartedOn(String text) {
        this.startedOn.setText(text);
    }

    public void setCompletedOn(String text) {
        this.completedOn.setText(text);
    }

    public void setTime(String time) {
        this.time.setText(time);
    }

    public void setMarks(String marks) {
        this.marks.setText(marks);
    }

    public void setGrade(String grade) {
        this.grade.setText(grade);
    }

    public void addQuestionList() {
        for (int i = 0; i < questionQuantity; i++) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Kien_FXML/QuestionLayout.fxml"));
            try {
                Node node = fxmlLoader.load();
                QuestionLayoutController questionLayoutController = fxmlLoader.getController();
                questionLayoutController.setQuestionNum(i + 1);
                questionLayoutController.setAddAnswer();
                if (userAnswer.get(i) != null) {
                    RadioButton selectedRadio = (RadioButton) questionLayoutController.questionBox.getChildren().get(userAnswer.get(i));
                    selectedRadio.setSelected(true);
                }
                for (int j= 1;j<=4;++j) {
                    JFXRadioButton radioButton = (JFXRadioButton) questionLayoutController.questionBox.getChildren().get(j);
                    radioButton.setDisable(true);
                    radioButton.setStyle("-fx-opacity: 1;");
                    radioButton.setSelectedColor(Color.GRAY);
                }
                quizListContainer.getChildren().add(node);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void renderNavigation() {
        progressNodes = new VBox[questionQuantity];
        for (int i = 0; i < questionQuantity; i++) {
            FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("/Kien_FXML/QuestionRectangle.fxml"));
            try {
                Node node1 = fxmlLoader1.load();
                QuestionRectangleController questionRectangleController = fxmlLoader1.getController();
                questionRectangleController.setNumber(i + 1);
                questionRectangleController.setAnswered();
                progressNodes[i] = questionRectangleController.getRectangle();
                scrollToQuestion(i);
                progressPane.getChildren().add(node1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void scrollToQuestion(int questionIndex) {
        final int i = questionIndex;
        progressNodes[questionIndex].setOnMouseClicked(event -> {
            double scrollToY;
            if (i <= 2) scrollToY = quizListContainer.getChildren().get(i).getLayoutY();
            else scrollToY = quizListContainer.getChildren().get(i + 1).getLayoutY();
            double tmp = (quizListContainer.getHeight() / progressNodes.length) / quizListContainer.getHeight();
            if (i > 0.5 * progressNodes.length) {
                scrollPane.setVvalue(scrollToY / quizListContainer.getHeight() + tmp);
            } else
                scrollPane.setVvalue(scrollToY / quizListContainer.getHeight());
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        questionQuantity = DataModel.getInstance().getNumber();
        userAnswer = DataModel.getInstance().getUserAnswer();
        addQuestionList();
        renderNavigation();
        userSection.setBorder(new Border(new BorderStroke(Paint.valueOf("#ccc"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 1, 0, 1))));
        progressContent.setBorder(new Border(new BorderStroke(Paint.valueOf("#ccc"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 1, 1, 1))));
    }
}
