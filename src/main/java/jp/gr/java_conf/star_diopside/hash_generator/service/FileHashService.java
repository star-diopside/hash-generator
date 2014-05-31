package jp.gr.java_conf.star_diopside.hash_generator.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;

import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

@Named
@Singleton
public class FileHashService {

    public String generateFileHashString(Path path) {
        return generateFileHashString(path, MessageDigestAlgorithms.SHA_256);
    }

    public String generateFileHashString(Path path, String algorithm) {
        MessageDigest digest = DigestUtils.getDigest(algorithm);
        try (InputStream in = Files.newInputStream(path)) {
            return Hex.encodeHexString(DigestUtils.updateDigest(digest, in).digest());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
