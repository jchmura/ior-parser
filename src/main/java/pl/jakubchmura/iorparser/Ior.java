package pl.jakubchmura.iorparser;

import java.util.Objects;

public class Ior {

    private String ior;

    public String getIor() {
        return ior;
    }

    public void setIor(String ior) {
        this.ior = ior;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ior ior1 = (Ior) o;
        return Objects.equals(ior, ior1.ior);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ior);
    }
}
