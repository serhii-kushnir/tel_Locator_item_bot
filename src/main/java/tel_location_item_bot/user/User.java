package tel_location_item_bot.user;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class User {

    private String username;
    private String password;
    private String email;
}
