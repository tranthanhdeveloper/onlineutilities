package net.onlineutilities.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tools")
public class ToolsEntity {
    private long id;
    private String name;
    private String description;
    private String title;
    private String seoKeyword;
    private String seoDescription;
    private String seoCanonicalUrl;
    private String seoOgTitle;
    private String seoOgImage;
    private String seoOgType;
    private String seoOgUrl;
    private String seoOgDescription;
    private Long related1;
    private Long related2;
    private Long related10;
    private Long related9;
    private Long related8;
    private Long related7;
    private Long related6;
    private Long related5;
    private Long related4;
    private Long related3;

    @Id
    @Column(name = "ID")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "Title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "seo_keyword")
    public String getSeoKeyword() {
        return seoKeyword;
    }

    public void setSeoKeyword(String seoKeyword) {
        this.seoKeyword = seoKeyword;
    }

    @Basic
    @Column(name = "seo_description")
    public String getSeoDescription() {
        return seoDescription;
    }

    public void setSeoDescription(String seoDescription) {
        this.seoDescription = seoDescription;
    }

    @Basic
    @Column(name = "seo_canonical_url")
    public String getSeoCanonicalUrl() {
        return seoCanonicalUrl;
    }

    public void setSeoCanonicalUrl(String seoCanonicalUrl) {
        this.seoCanonicalUrl = seoCanonicalUrl;
    }

    @Basic
    @Column(name = "seo_og_title")
    public String getSeoOgTitle() {
        return seoOgTitle;
    }

    public void setSeoOgTitle(String seoOgTitle) {
        this.seoOgTitle = seoOgTitle;
    }

    @Basic
    @Column(name = "seo_og_image")
    public String getSeoOgImage() {
        return seoOgImage;
    }

    public void setSeoOgImage(String seoOgImage) {
        this.seoOgImage = seoOgImage;
    }

    @Basic
    @Column(name = "seo_og_type")
    public String getSeoOgType() {
        return seoOgType;
    }

    public void setSeoOgType(String seoOgType) {
        this.seoOgType = seoOgType;
    }

    @Basic
    @Column(name = "seo_og_url")
    public String getSeoOgUrl() {
        return seoOgUrl;
    }

    public void setSeoOgUrl(String seoOgUrl) {
        this.seoOgUrl = seoOgUrl;
    }

    @Basic
    @Column(name = "seo_og_description")
    public String getSeoOgDescription() {
        return seoOgDescription;
    }

    public void setSeoOgDescription(String seoOgDescription) {
        this.seoOgDescription = seoOgDescription;
    }

    @Basic
    @Column(name = "related_1")
    public Long getRelated1() {
        return related1;
    }

    public void setRelated1(Long related1) {
        this.related1 = related1;
    }

    @Basic
    @Column(name = "related_2")
    public Long getRelated2() {
        return related2;
    }

    public void setRelated2(Long related2) {
        this.related2 = related2;
    }

    @Basic
    @Column(name = "related_10")
    public Long getRelated10() {
        return related10;
    }

    public void setRelated10(Long related10) {
        this.related10 = related10;
    }

    @Basic
    @Column(name = "related_9")
    public Long getRelated9() {
        return related9;
    }

    public void setRelated9(Long related9) {
        this.related9 = related9;
    }

    @Basic
    @Column(name = "related_8")
    public Long getRelated8() {
        return related8;
    }

    public void setRelated8(Long related8) {
        this.related8 = related8;
    }

    @Basic
    @Column(name = "related_7")
    public Long getRelated7() {
        return related7;
    }

    public void setRelated7(Long related7) {
        this.related7 = related7;
    }

    @Basic
    @Column(name = "related_6")
    public Long getRelated6() {
        return related6;
    }

    public void setRelated6(Long related6) {
        this.related6 = related6;
    }

    @Basic
    @Column(name = "related_5")
    public Long getRelated5() {
        return related5;
    }

    public void setRelated5(Long related5) {
        this.related5 = related5;
    }

    @Basic
    @Column(name = "related_4")
    public Long getRelated4() {
        return related4;
    }

    public void setRelated4(Long related4) {
        this.related4 = related4;
    }

    @Basic
    @Column(name = "related_3")
    public Long getRelated3() {
        return related3;
    }

    public void setRelated3(Long related3) {
        this.related3 = related3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToolsEntity that = (ToolsEntity) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(title, that.title) && Objects.equals(seoKeyword, that.seoKeyword) && Objects.equals(seoDescription, that.seoDescription) && Objects.equals(seoCanonicalUrl, that.seoCanonicalUrl) && Objects.equals(seoOgTitle, that.seoOgTitle) && Objects.equals(seoOgImage, that.seoOgImage) && Objects.equals(seoOgType, that.seoOgType) && Objects.equals(seoOgUrl, that.seoOgUrl) && Objects.equals(seoOgDescription, that.seoOgDescription) && Objects.equals(related1, that.related1) && Objects.equals(related2, that.related2) && Objects.equals(related10, that.related10) && Objects.equals(related9, that.related9) && Objects.equals(related8, that.related8) && Objects.equals(related7, that.related7) && Objects.equals(related6, that.related6) && Objects.equals(related5, that.related5) && Objects.equals(related4, that.related4) && Objects.equals(related3, that.related3);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, title, seoKeyword, seoDescription, seoCanonicalUrl, seoOgTitle, seoOgImage, seoOgType, seoOgUrl, seoOgDescription, related1, related2, related10, related9, related8, related7, related6, related5, related4, related3);
    }
}
