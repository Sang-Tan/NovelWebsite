package model;

public class Volume {
    private int id;
    private Novel novelId;
    private String name;
    private String image;
    private int orderIndex;

    public Volume() {
    }

    public Volume(int id, Novel novelId, String name, String image, int orderIndex) {
        this.id = id;
        this.novelId = novelId;
        this.name = name;
        this.image = image;
        this.orderIndex = orderIndex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Novel getNovelId() {
        return novelId;
    }

    public void setNovelId(Novel novelId) {
        this.novelId = novelId;
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

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }
}
