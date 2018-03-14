package com.hww.els.common.enums;

public enum ElasticTypes {

    ESContent("hww_es_content"),
	ESRecommendHis("hww_es_recomm_his"),
	ESMemberLoc("hww_es_member_loc");
    private final String typeName;

    ElasticTypes(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return this.typeName;
    }
}
