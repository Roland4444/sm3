package util;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.NoArgGenerator;

import java.io.Serializable;
import java.util.UUID;

public class timeBasedUUID implements Serializable {
    public UUID uuid;
    public String generate() {
        NoArgGenerator timeBasedGenerator = Generators.timeBasedGenerator();
        this.uuid = timeBasedGenerator.generate();
        return uuid.toString();
    }
}
