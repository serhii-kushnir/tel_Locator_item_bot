package tel_location_item_bot.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class AuthResponse {
    
    @JsonProperty("token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }
}
