package by.kihtenkoolga;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {

    private int responseValue;

    public Response(int responseValue) {
        this.responseValue = responseValue;
    }

}
