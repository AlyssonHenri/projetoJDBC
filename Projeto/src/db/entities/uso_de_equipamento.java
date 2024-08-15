package db.entities;

import java.util.Objects;

public class uso_de_equipamento {
    private String tempo_de_uso;

    public String getTempo_de_uso() {
        return tempo_de_uso;
    }

    public void setTempo_de_uso(String tempo_de_uso) {
        this.tempo_de_uso = tempo_de_uso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        uso_de_equipamento that = (uso_de_equipamento) o;
        return Objects.equals(tempo_de_uso, that.tempo_de_uso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tempo_de_uso);
    }

    @Override
    public String toString() {
        return "uso_de_equipamento{" +
                "tempo_de_uso='" + tempo_de_uso + '\'' +
                '}';
    }
}
