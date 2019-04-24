package com.epam.kirillcheldishkin.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PasswordEncryptor {
    private static final Logger LOGGER = LogManager.getLogger(PasswordEncryptor.class);

    public static String encrypt(String password){
        try {
            byte[] hexHash = MessageDigest.getInstance("SHA-256").digest(password.getBytes(StandardCharsets.UTF_8));
            return IntStream.range(0, hexHash.length).mapToObj(i -> Integer.toHexString(0xff & hexHash[i]))
                    .map(s -> (s.length() == 1) ? "0" + s : s).collect(Collectors.joining());
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("This algorithm of encrypting password isn't registered");
            throw new RuntimeException(e);
        }
    }
}
