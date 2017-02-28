package com.someco.repo.common;

import org.alfresco.service.namespace.QName;

public class SomeCoModel {

	public final static String SOMECO_MODEL_PREFIX = "sc";
	public final static String SOMECO_MODEL_URI = "http://www.someco.com/model/content/1.0";

	public final static QName TYPE_DOC = QName.createQName(SOMECO_MODEL_URI, "doc");
	public final static QName TYPE_MARKETINGDOC = QName.createQName(SOMECO_MODEL_URI, "marketingDoc");
	public final static QName TYPE_WHITEPAPER = QName.createQName(SOMECO_MODEL_URI, "whitepaper");
	public final static QName TYPE_HRDOC = QName.createQName(SOMECO_MODEL_URI, "hrDoc");
	public final static QName TYPE_HRPOLICY = QName.createQName(SOMECO_MODEL_URI, "hrPolicy");
	public final static QName TYPE_SALESDOC = QName.createQName(SOMECO_MODEL_URI, "salesDoc");
	public final static QName TYPE_OPSDOC = QName.createQName(SOMECO_MODEL_URI, "opsDoc");
	public final static QName TYPE_LEGALDOC = QName.createQName(SOMECO_MODEL_URI, "legalDoc");
	public final static QName TYPE_RATING = QName.createQName(SOMECO_MODEL_URI, "rating");

	public final static QName ASPECT_WEBABLE = QName.createQName(SOMECO_MODEL_URI, "webable");
	public final static QName ASPECT_CLIENTRELATED = QName.createQName(SOMECO_MODEL_URI, "clientRelated");
	public final static QName ASPECT_RATEABLE = QName.createQName(SOMECO_MODEL_URI, "rateable");

	public final static QName PROP_CAMPAIGN = QName.createQName(SOMECO_MODEL_URI, "campaign");
	public final static QName PROP_PUBLISHED = QName.createQName(SOMECO_MODEL_URI, "published");
	public final static QName PROP_ISACTIVE = QName.createQName(SOMECO_MODEL_URI, "isActive");
	public final static QName PROP_CLIENTNAME = QName.createQName(SOMECO_MODEL_URI, "clientName");
	public final static QName PROP_PROJECTNAME = QName.createQName(SOMECO_MODEL_URI, "projectName");
	public final static QName PROP_RATING = QName.createQName(SOMECO_MODEL_URI, "rating");
	public final static QName PROP_RATER = QName.createQName(SOMECO_MODEL_URI, "rater");
	public final static QName PROP_AVERAGERATING = QName.createQName(SOMECO_MODEL_URI, "averageRating");
	public final static QName PROP_TOTALRATING = QName.createQName(SOMECO_MODEL_URI, "totalRating");
	public final static QName PROP_RATINGCOUNT = QName.createQName(SOMECO_MODEL_URI, "ratingCount");

	public final static QName ASSOC_RELATEDOCUMENTS = QName.createQName(SOMECO_MODEL_URI, "relatedDocuments");
	public final static QName ASSOC_RATINGS = QName.createQName(SOMECO_MODEL_URI, "ratings");

}
