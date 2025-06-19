package com.tioprasetioa.www.mtsn3rohul.Utils;

import android.util.Log;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.collect.Lists;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Accesstoken {
    private static final String firebaseMessagingScope = "https://www.googleapis.com/auth/firebase.messaging";

    public String getAccessToken() {
        try {
            String jsonString = "{\n" +
                    "  \"type\": \"service_account\",\n" +
                    "  \"project_id\": \"peronpintar\",\n" +
                    "  \"private_key_id\": \"608ab11583cfa5013f547cfe88c7019e3009078d\",\n" +
                    "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\n" +
                    "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDR56f793fOkPmJ\\nxM/oww1XxSOkkkecxwHnSmqP37E/jxYSwIjy3AngD2jDxJAkcDyb7dr3LF0dFXGw\\n8CmFwCfK65sOB8b6aRhufeFK5xU8K5hhN9r9gg4MAt/tXCdnVLQ21DGIDODxL17B\\nWNlXAmwvmUAXOi/pG5JfP0yu9Q1HI5JvC0eqxO/4Yh1wx0iGQB8lHvBWYcqajkgb\\n0bRu28UAJ1Jc9BxOBQTtCUQHuyUw5/2T/cDiexHTfKo56QJuUUCxzXkZewR3621K\\nibczX4cBOPdLOLLsMYpDnMzO0k9p4zBtipJmQN3+m+z9Ef1pi5d1DMuAQdRZ4R4T\\nPN4Jnd4xAgMBAAECggEACFORddMuVPeqQzbj7aTjIBxJWpHIHNkS41sv2MIz9HeX\\nSN1YJJSDupG4UBLsvUB+68tZ8fXrQagxAnOC0I2IgCoSOtKMwZbIQ2wwTJM+SkzY\\nA0jYboSP5kkTTrkAaFhs7hBC+lWGDMgAxCTjbJ12r8XhmKpHVHT/PIXOltG3GgW/\\nd2d2GXg6/ZN8zeiUojZ/LG8IJKG8dsCbt7WRTqGxYDP1HgbSis4ClHyID7/Gs+Ss\\nTV258LAPhGmAUQnWOsEYVVIsyZdsmh79aCq4rkhVOfNfpy6x+bDIR3LSk4m5Wm6g\\nHQmzlcxX5EBD6nZC807RsWhfSH70X08VPqQivRzkCwKBgQDWT/7LvDmmG+xlOae/\\nk6mhBqASwBYrGEDa/5O4lw4jZFguFGXyBU/qkOHNIfpIcUXh1snQVy68Bpk6inNh\\nv784UgdzNRYGx609mkO0kCkzqs+zuA2LqqvIutrIkZo510wXxANjQmCUPFRQdUP+\\ns8Z2FIIbFsb6GSMSEyZ/yrUEUwKBgQD6vC3iwRD9NY9uy1iYiVjM+6pI2OyY33/8\\nMxbb3nC4q+Br9qG+oXnBPgo+KMe4g3f7apnbw9TWekIL1HheTV2MQd3StZHJdDby\\nHiMMN/RE350M7T8v2ByFX8NxJOp6iAazdxk/KWXMdmkdfOfr8bGc6yfXyN3rXv9l\\n51pYkaTC6wKBgQCpS9owoUk9EhPlRu1f5kr63Fv5U+pvMqKRHZJZIGLXzH3oMM7z\\nqASgfMETJgMr1OdqZx1Xynctq1zCJPrOvgVVfV9LQCVdR8Fvx3wymCskGIwpIXz5\\n1uILGKiaBoM5q/z9TSeL8rSOhTXptSj4fJWLU9oPgie/C7prndss2jmy5wKBgHeF\\nDDpy3a5VwbGGrQzJK9NKg7mF7gJRR6jokZTtS7478BaG0kMUpf0/nZJTziBeSutF\\nvWHMz8ANNz58YYRSTK13GE5AzuXAyBBR3VMNuRq7nRl0XhJoafAJ3hgpCLyv6sHR\\nm+9HxIbaSvELgWIvLAMo9S0fSunPtt7UkXR2uJtRAoGBALwQ0O6l0OGlEp8gjpt2\\njUuO0NTMm44SRIh8zU70Brd9zmOu0ClNslMm8eqzExALVQTPGrSh155P1iCCFphO\\nWTgBsxZffkZS3oycUOIH2KlBIvTIf6hFodI52Wd0MeOmWrCvODpUpBkR3uKmHIy5\\n2zu5Zlpoh5hbYW+NZ/TpspSo\\n-----END PRIVATE KEY-----\\n\",\n" +
                    "  \"client_email\": \"firebase-adminsdk-mx7y8@peronpintar.iam.gserviceaccount.com\",\n" +
                    "  \"client_id\": \"112755621753148619847\",\n" +
                    "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
                    "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
                    "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
                    "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-mx7y8%40peronpintar.iam.gserviceaccount.com\",\n" +
                    "  \"universe_domain\": \"googleapis.com\"\n" +
                    "}";

            InputStream stream = new ByteArrayInputStream(jsonString.getBytes(StandardCharsets.UTF_8));
            GoogleCredentials googleCredentials = GoogleCredentials.fromStream(stream)
                    .createScoped(Lists.newArrayList(firebaseMessagingScope));

            googleCredentials.refresh();
            return googleCredentials.getAccessToken().getTokenValue();
        } catch (IOException e) {
            Log.e("error", "Error fetching access token: " + e.getMessage());
            return null;
        }
    }

}
