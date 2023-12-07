package by.kihtenkoolga;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Request {

    private int requestValue;

    public Request(int requestValue) {
        this.requestValue = requestValue;
    }

}
