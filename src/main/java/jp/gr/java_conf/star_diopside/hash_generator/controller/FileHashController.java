package jp.gr.java_conf.star_diopside.hash_generator.controller;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import javax.inject.Inject;
import javax.inject.Named;

import jp.gr.java_conf.star_diopside.hash_generator.service.FileHashService;

import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.dialog.DialogStyle;
import org.controlsfx.dialog.Dialogs;

@Named
public class FileHashController implements Initializable {

    @Inject
    private FileHashService fileHashService;

    @FXML
    private ChoiceBox<String> digestAlgorithm;

    @FXML
    private TextField fileName;

    @FXML
    private TextField generatedHashValue;

    @FXML
    private TextField compareHashValue;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        digestAlgorithm.setItems(FXCollections.observableArrayList(MessageDigestAlgorithms.MD2,
                MessageDigestAlgorithms.MD5, MessageDigestAlgorithms.SHA_1, MessageDigestAlgorithms.SHA_256,
                MessageDigestAlgorithms.SHA_384, MessageDigestAlgorithms.SHA_512));
        digestAlgorithm.setValue(MessageDigestAlgorithms.SHA_256);
    }

    @FXML
    private void selectFile() {
        FileChooser chooser = new FileChooser();
        chooser.setInitialFileName(fileName.getText());
        File file = chooser.showOpenDialog(fileName.getScene().getWindow());
        if (file != null) {
            fileName.setText(file.toString());
        }
    }

    @FXML
    private void generateHash() {
        Path path = Paths.get(fileName.getText());
        if (Files.isReadable(path) && !Files.isDirectory(path)) {
            generatedHashValue.setText(fileHashService.generateFileHashString(path, digestAlgorithm.getValue()));
        } else {
            Dialogs.create().style(DialogStyle.NATIVE).owner(fileName.getScene().getWindow()).title("Warning")
                    .message("ファイルを読み込むことができません。").showWarning();
        }
    }

    @FXML
    private void compareHash() {
        boolean result = StringUtils.equalsIgnoreCase(generatedHashValue.getText(), compareHashValue.getText());
        Dialogs.create().style(DialogStyle.NATIVE).owner(fileName.getScene().getWindow()).title("Result")
                .message(Boolean.toString(result)).showInformation();
    }
}
