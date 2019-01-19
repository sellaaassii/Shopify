package simplesendreceive.example.com.shopify.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Collect {

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("collection_id")
    @Expose
    private long collectionId;

    @SerializedName("product_id")
    @Expose
    private long productId;

    @SerializedName("featured")
    @Expose
    private Boolean featured;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("position")
    @Expose
    private Integer position;

    @SerializedName("sort_value")
    @Expose
    private String sortValue;

    public long getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Integer collectionId) {
        this.collectionId = collectionId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Boolean getFeatured() {
        return featured;
    }

    public void setFeatured(Boolean featured) {
        this.featured = featured;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getSortValue() {
        return sortValue;
    }

    public void setSortValue(String sortValue) {
        this.sortValue = sortValue;
    }

    @Override
    public String toString() {
        return "Collect {" +
                "id=" + id +
                ", collectionId=" + collectionId +
                ", productId=" + productId +
                '}';
    }

}