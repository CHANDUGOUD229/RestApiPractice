package pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UserResponse {

    private int page;
    private int per_page;
    private int total;
    private int total_pages;
    private List<User> data;
    private Support support;

    @JsonProperty("_meta")
    private Meta _meta;

    public int getPage() {
        return page;
    }

    public int getPer_page() {
        return per_page;
    }

    public int getTotal() {
        return total;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public List<User> getData() {
        return data;
    }

    public Support getSupport() {
        return support;
    }

    public Meta get_meta() {
        return _meta;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public void setData(List<User> data) {
        this.data = data;
    }

    public void setSupport(Support support) {
        this.support = support;
    }

    public void set_meta(Meta _meta) {
        this._meta = _meta;
    }
}
