package edu.ilyav.api.models;

import java.io.Serializable;

/**
 * Created by ivald on 2018-09-03.
 */
public class WebResponse implements Serializable {
    private static final long serialVersionUID = 1;

    private String result;

    public WebResponse() {}

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
