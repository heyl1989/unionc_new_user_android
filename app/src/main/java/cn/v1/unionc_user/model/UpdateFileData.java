package cn.v1.unionc_user.model;

/**
 * Created by qy on 2018/3/8.
 */

public class UpdateFileData extends BaseData {


    /**
     * originalPath : /unionWeb/original/158/8/13/87fc2b2e-039d-4f2a-8039-7b6a50601f82_temphead.jpg
     * path : /unionWeb/original/158/8/13/87fc2b2e-039d-4f2a-8039-7b6a50601f82_temphead.jpg
     */

    private String originalPath;
    private String path;

    public String getOriginalPath() {
        return originalPath;
    }

    public void setOriginalPath(String originalPath) {
        this.originalPath = originalPath;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
