package core.media;

public class MediaObject {
    private final MediaType type;
    private Object data;

    public MediaObject(MediaType type, Object data) {
        this.type = type;
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public MediaType getType() {
        return type;
    }

    
}
