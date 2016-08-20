package jp.gr.java_conf.star_diopside.hash_generator.controller;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Security;
import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.support.MessageSourceAccessor;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import jp.gr.java_conf.star_diopside.hash_generator.model.FileHashModel;
import jp.gr.java_conf.star_diopside.hash_generator.service.FileHashService;

@Named
public class FileHashController implements Initializable {

    @Inject
    private FileHashModel model;

    @Inject
    private FileHashService fileHashService;

    @Inject
    private MessageSourceAccessor messages;

    @FXML
    private ChoiceBox<String> digestAlgorithm;

    @FXML
    private TextField fileName;

    @FXML
    private TextField generatedHashValue;

    @FXML
    private TextField compareHashValue;

    @FXML
    private Label compareResult;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        digestAlgorithm.setItems(FXCollections
                .observableArrayList(Security.getAlgorithms("MessageDigest").stream().sorted().toArray(String[]::new)));
        digestAlgorithm.valueProperty().bindBidirectional(model.digestAlgorithmProperty());
        fileName.textProperty().bindBidirectional(model.fileNameProperty());
        generatedHashValue.textProperty().bind(model.generatedHashValueProperty());
        model.compareHashValueProperty().bind(compareHashValue.textProperty());
        compareResult.textProperty().bind(model.compareResultProperty());
    }

    @FXML
    private void selectFile() {
        FileChooser chooser = new FileChooser();
        chooser.setInitialFileName(model.getFileName());
        File file = chooser.showOpenDialog(fileName.getScene().getWindow());
        if (file != null) {
            model.setFileName(file.toString());
        }
    }

    @FXML
    private void generateHash() {
        Path path = Paths.get(model.getFileName());
        if (Files.isReadable(path) && !Files.isDirectory(path)) {
            model.setGeneratedHashValue(fileHashService.generateFileHashString(path, model.getDigestAlgorithm()));
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText(messages.getMessage("warning.fileNotAccess"));
            alert.showAndWait();
        }
    }
}
