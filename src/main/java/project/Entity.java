package project;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Entity {

    private int integerField;
    private String stringField;
    private boolean booleanField;
    private LocalDate localDateField;
    private LocalDateTime localDateTimeField;

    public Entity(int integerField,
                  String stringField,
                  boolean booleanField,
                  LocalDate localDateField,
                  LocalDateTime localDateTimeField) {

        this.integerField = integerField;
        this.stringField = stringField;
        this.booleanField = booleanField;
        this.localDateField = localDateField;
        this.localDateTimeField = localDateTimeField;
    }

    public int getIntegerField() {
        return integerField;
    }

    public void setIntegerField(int integerField) {
        this.integerField = integerField;
    }

    public String getStringField() {
        return stringField;
    }

    public void setStringField(String stringField) {
        this.stringField = stringField;
    }

    public boolean isBooleanField() {
        return booleanField;
    }

    public void setBooleanField(boolean booleanField) {
        this.booleanField = booleanField;
    }

    public LocalDate getLocalDateField() {
        return localDateField;
    }

    public void setLocalDateField(LocalDate localDateField) {
        this.localDateField = localDateField;
    }

    public LocalDateTime getLocalDateTimeField() {
        return localDateTimeField;
    }

    public void setLocalDateTimeField(LocalDateTime localDateTimeField) {
        this.localDateTimeField = localDateTimeField;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return integerField == entity.integerField &&
                booleanField == entity.booleanField &&
                stringField.equals(entity.stringField) &&
                localDateField.equals(entity.localDateField) &&
                localDateTimeField.equals(entity.localDateTimeField);
    }

    @Override
    public int hashCode() {
        return Objects.hash(integerField, stringField, booleanField, localDateField, localDateTimeField);
    }

    @Override
    public String toString() {
        return "Entity{" +
                "integerField=" + integerField +
                ", stringField='" + stringField + '\'' +
                ", booleanField=" + booleanField +
                ", localDateField=" + localDateField +
                ", localDateTimeField=" + localDateTimeField +
                '}';
    }
}
