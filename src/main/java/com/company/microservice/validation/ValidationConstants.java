package com.company.microservice.validation;

public class ValidationConstants {
    public static final int MIN_ALPHA2_SIZE = 2;
    public static final int MAX_ALPHA2_SIZE = 2;
    public static final String ALPHA2_REGEX = "[a-zA-Z]{2}";

    public static final int MIN_ALPHA3_SIZE = 3;
    public static final int MAX_ALPHA3_SIZE = 3;
    public static final String ALPHA3_REGEX = "[a-zA-Z]{3}";

    public static final int MIN_KEY_SIZE = 21;
    public static final int MAX_KEY_SIZE = 21;
    public static final String KEY_REGEX = "[a-zA-Z0-9]{21}";

    public static final int MIN_CODE_SIZE = 3;
    public static final int MAX_CODE_SIZE = 3;
    public static final String CODE_REGEX = "[0-9]{3}";

    public static final int MIN_NAME_SIZE = 3;
    public static final int MAX_NAME_SIZE = 60;
    public static final String NAME_REGEX = "[a-zA-Z0-9()',.]{3,100}";
}
