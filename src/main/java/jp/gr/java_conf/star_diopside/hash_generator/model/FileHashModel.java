package jp.gr.java_conf.star_diopside.hash_generator.model;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.MessageSourceAccessor;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Named
public class FileHashModel implements InitializingBean {

    @Inject
    private MessageSourceAccessor messages;

    private StringProperty digestAlgorithm = new SimpleStringProperty(MessageDigestAlgorithms.SHA_256);
    private StringProperty fileName = new SimpleStringProperty(StringUtils.EMPTY);
    private StringProperty generatedHashValue = new SimpleStringProperty();
    private StringProperty compareHashValue = new SimpleStringProperty();
    private StringProperty compareResult = new SimpleStringProperty();

    @Override
    public void afterPropertiesSet() throws Exception {
        compareResult.bind(new StringBinding() {
            {
                super.bind(generatedHashValue, compareHashValue);
            }

            @Override
            protected String computeValue() {
                String generatedHash = generatedHashValue.get();
                String compareHash = compareHashValue.get();
                if (StringUtils.isEmpty(generatedHash) || StringUtils.isEmpty(compareHash)) {
                    return StringUtils.EMPTY;
                } else if (StringUtils.equalsIgnoreCase(generatedHash, compareHash)) {
                    return messages.getMessage("match");
                } else {
                    return messages.getMessage("notMatch");
                }
            }
        });
    }

    public StringProperty digestAlgorithmProperty() {
        return digestAlgorithm;
    }

    public String getDigestAlgorithm() {
        return digestAlgorithm.get();
    }

    public void setDigestAlgorithm(String digestAlgorithm) {
        this.digestAlgorithm.set(digestAlgorithm);
    }

    public StringProperty fileNameProperty() {
        return fileName;
    }

    public String getFileName() {
        return fileName.get();
    }

    public void setFileName(String fileName) {
        this.fileName.set(fileName);
    }

    public StringProperty generatedHashValueProperty() {
        return generatedHashValue;
    }

    public String getGeneratedHashValue() {
        return generatedHashValue.get();
    }

    public void setGeneratedHashValue(String generatedHashValue) {
        this.generatedHashValue.set(generatedHashValue);
    }

    public StringProperty compareHashValueProperty() {
        return compareHashValue;
    }

    public String getCompareHashValue() {
        return compareHashValue.get();
    }

    public void setCompareHashValue(String compareHashValue) {
        this.compareHashValue.set(compareHashValue);
    }

    public StringProperty compareResultProperty() {
        return compareResult;
    }

    public String getCompareResult() {
        return compareResult.get();
    }

    public void setCompareResult(String compareResult) {
        this.compareResult.set(compareResult);
    }
}
