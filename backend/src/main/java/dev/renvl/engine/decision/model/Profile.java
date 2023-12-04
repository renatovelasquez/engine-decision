package dev.renvl.engine.decision.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Profile {

    @Id
    private String code;
    private String type;
    private Integer creditModifier;

    public Profile() {
    }

    public Profile(String code, String type, Integer creditModifier) {
        this.code = code;
        this.type = type;
        this.creditModifier = creditModifier;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCreditModifier() {
        return creditModifier;
    }

    public void setCreditModifier(Integer creditModifier) {
        this.creditModifier = creditModifier;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "code=" + code +
                ", type='" + type + '\'' +
                ", creditModifier=" + creditModifier +
                '}';
    }
}