package ai.shreds.infrastructure.repositories.mongo.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "policies")
public class PolicyDocument {

    @Id
    private String id;

    @Field
    private String name;

    @Field
    private String description;

    @Field
    private String criteria;

    @Field
    private Boolean isActive;

    // Constructors, getters, and setters

    public PolicyDocument() {}

    public PolicyDocument(String id, String name, String description, String criteria, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.criteria = criteria;
        this.isActive = isActive;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}