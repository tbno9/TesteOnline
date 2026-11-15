package ro.ubm.paw.testeonline.web.dto;

/** Raspunsul la login: tokenul, tipul lui si cate secunde e valabil. */
public record TokenResponse(String accessToken, String tokenType, long expiresIn) { }
