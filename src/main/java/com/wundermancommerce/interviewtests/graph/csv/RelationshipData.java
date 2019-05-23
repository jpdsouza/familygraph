package com.wundermancommerce.interviewtests.graph.csv;

public class RelationshipData {
    private String person1;
    private String person2;
    private String relatedBy;

    public String getPerson1() {
        return person1;
    }

    public void setPerson1(String person1) {
        this.person1 = person1;
    }

    public String getPerson2() {
        return person2;
    }

    public void setPerson2(String person2) {
        this.person2 = person2;
    }

    public String getRelatedBy() {
        return relatedBy;
    }

    public void setRelatedBy(String relatedBy) {
        this.relatedBy = relatedBy;
    }

    public RelationshipData(String person1, String person2, String relatedBy) {
        this.person1 = person1;
        this.person2 = person2;
        this.relatedBy = relatedBy;
    }

}


