package Connection;

import java.io.Serializable;

class NickTaken implements Serializable
{
    private String message = "This nickname is already taken";

    public String getMessage ()
    {
        return message;
    }
}

class Success implements Serializable
{
    private String message = "Success";

    public String getMessage ()
    {
        return message;
    }
}
