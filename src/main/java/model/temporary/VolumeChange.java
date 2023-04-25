package model.temporary;

import core.DatabaseObject;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "volume_changes")
public class VolumeChange implements DatabaseObject {
    @Id
    @Column(name = "volume_id", nullable = false)
    private int volumeId;

    @Column(name = "name", nullable = true, length = 255)
    private String name;

    @Column(name = "image", nullable = true, length = 255)
    private String image;

    public int getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(int volumeId) {
        this.volumeId = volumeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VolumeChange that = (VolumeChange) o;
        return volumeId == that.volumeId && Objects.equals(name, that.name) && Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(volumeId, name, image);
    }
}
