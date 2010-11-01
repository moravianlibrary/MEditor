/**
 * Metadata Editor
 * @author Jiri Kremser
 *  
 */

package org.fedora.api;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


// TODO: Auto-generated Javadoc
/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.fedora.api package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    /** The Constant _ObjectFieldsCDate_QNAME. */
    private final static QName _ObjectFieldsCDate_QNAME = new QName("", "cDate");
    
    /** The Constant _ObjectFieldsOwnerId_QNAME. */
    private final static QName _ObjectFieldsOwnerId_QNAME = new QName("", "ownerId");
    
    /** The Constant _ObjectFieldsState_QNAME. */
    private final static QName _ObjectFieldsState_QNAME = new QName("", "state");
    
    /** The Constant _ObjectFieldsLabel_QNAME. */
    private final static QName _ObjectFieldsLabel_QNAME = new QName("", "label");
    
    /** The Constant _ObjectFieldsPid_QNAME. */
    private final static QName _ObjectFieldsPid_QNAME = new QName("", "pid");
    
    /** The Constant _ObjectFieldsDcmDate_QNAME. */
    private final static QName _ObjectFieldsDcmDate_QNAME = new QName("", "dcmDate");
    
    /** The Constant _ObjectFieldsMDate_QNAME. */
    private final static QName _ObjectFieldsMDate_QNAME = new QName("", "mDate");
    
    /** The Constant _FieldSearchResultListSession_QNAME. */
    private final static QName _FieldSearchResultListSession_QNAME = new QName("", "listSession");
    
    /** The Constant _ListSessionExpirationDate_QNAME. */
    private final static QName _ListSessionExpirationDate_QNAME = new QName("", "expirationDate");
    
    /** The Constant _FieldSearchQueryConditions_QNAME. */
    private final static QName _FieldSearchQueryConditions_QNAME = new QName("", "conditions");
    
    /** The Constant _FieldSearchQueryTerms_QNAME. */
    private final static QName _FieldSearchQueryTerms_QNAME = new QName("", "terms");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.fedora.api
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AddRelationship }.
     *
     * @return the adds the relationship
     */
    public AddRelationship createAddRelationship() {
        return new AddRelationship();
    }

    /**
     * Create an instance of {@link IngestResponse }.
     *
     * @return the ingest response
     */
    public IngestResponse createIngestResponse() {
        return new IngestResponse();
    }

    /**
     * Create an instance of {@link DescribeRepositoryResponse }.
     *
     * @return the describe repository response
     */
    public DescribeRepositoryResponse createDescribeRepositoryResponse() {
        return new DescribeRepositoryResponse();
    }

    /**
     * Create an instance of {@link RepositoryInfo }.
     *
     * @return the repository info
     */
    public RepositoryInfo createRepositoryInfo() {
        return new RepositoryInfo();
    }

    /**
     * Create an instance of {@link DatastreamDef }.
     *
     * @return the datastream def
     */
    public DatastreamDef createDatastreamDef() {
        return new DatastreamDef();
    }

    /**
     * Create an instance of {@link GetDatastreamDissemination }.
     *
     * @return the gets the datastream dissemination
     */
    public GetDatastreamDissemination createGetDatastreamDissemination() {
        return new GetDatastreamDissemination();
    }

    /**
     * Create an instance of {@link GetDatastreams }.
     *
     * @return the gets the datastreams
     */
    public GetDatastreams createGetDatastreams() {
        return new GetDatastreams();
    }

    /**
     * Create an instance of {@link Ingest }.
     *
     * @return the ingest
     */
    public Ingest createIngest() {
        return new Ingest();
    }

    /**
     * Create an instance of {@link DatastreamBinding }.
     *
     * @return the datastream binding
     */
    public DatastreamBinding createDatastreamBinding() {
        return new DatastreamBinding();
    }

    /**
     * Create an instance of {@link ListMethodsResponse }.
     *
     * @return the list methods response
     */
    public ListMethodsResponse createListMethodsResponse() {
        return new ListMethodsResponse();
    }

    /**
     * Create an instance of {@link PurgeRelationship }.
     *
     * @return the purge relationship
     */
    public PurgeRelationship createPurgeRelationship() {
        return new PurgeRelationship();
    }

    /**
     * Create an instance of {@link ExportResponse }.
     *
     * @return the export response
     */
    public ExportResponse createExportResponse() {
        return new ExportResponse();
    }

    /**
     * Create an instance of {@link CompareDatastreamChecksumResponse }.
     *
     * @return the compare datastream checksum response
     */
    public CompareDatastreamChecksumResponse createCompareDatastreamChecksumResponse() {
        return new CompareDatastreamChecksumResponse();
    }

    /**
     * Create an instance of {@link MethodParmDef }.
     *
     * @return the method parm def
     */
    public MethodParmDef createMethodParmDef() {
        return new MethodParmDef();
    }

    /**
     * Create an instance of {@link ModifyDatastreamByValueResponse }.
     *
     * @return the modify datastream by value response
     */
    public ModifyDatastreamByValueResponse createModifyDatastreamByValueResponse() {
        return new ModifyDatastreamByValueResponse();
    }

    /**
     * Create an instance of {@link ObjectMethodsDef }.
     *
     * @return the object methods def
     */
    public ObjectMethodsDef createObjectMethodsDef() {
        return new ObjectMethodsDef();
    }

    /**
     * Create an instance of {@link GetDatastreamDisseminationResponse }.
     *
     * @return the gets the datastream dissemination response
     */
    public GetDatastreamDisseminationResponse createGetDatastreamDisseminationResponse() {
        return new GetDatastreamDisseminationResponse();
    }

    /**
     * Create an instance of {@link ObjectProfile.ObjModels }
     *
     * @return the obj models
     */
    public ObjectProfile.ObjModels createObjectProfileObjModels() {
        return new ObjectProfile.ObjModels();
    }

    /**
     * Create an instance of {@link GetObjectProfile }.
     *
     * @return the gets the object profile
     */
    public GetObjectProfile createGetObjectProfile() {
        return new GetObjectProfile();
    }

    /**
     * Create an instance of {@link AddRelationshipResponse }.
     *
     * @return the adds the relationship response
     */
    public AddRelationshipResponse createAddRelationshipResponse() {
        return new AddRelationshipResponse();
    }

    /**
     * Create an instance of {@link GetRelationships }.
     *
     * @return the gets the relationships
     */
    public GetRelationships createGetRelationships() {
        return new GetRelationships();
    }

    /**
     * Create an instance of {@link ListDatastreams }.
     *
     * @return the list datastreams
     */
    public ListDatastreams createListDatastreams() {
        return new ListDatastreams();
    }

    /**
     * Create an instance of {@link PurgeDatastreamResponse }.
     *
     * @return the purge datastream response
     */
    public PurgeDatastreamResponse createPurgeDatastreamResponse() {
        return new PurgeDatastreamResponse();
    }

    /**
     * Create an instance of {@link FieldSearchResult.ResultList }
     *
     * @return the result list
     */
    public FieldSearchResult.ResultList createFieldSearchResultResultList() {
        return new FieldSearchResult.ResultList();
    }

    /**
     * Create an instance of {@link GetObjectProfileResponse }.
     *
     * @return the gets the object profile response
     */
    public GetObjectProfileResponse createGetObjectProfileResponse() {
        return new GetObjectProfileResponse();
    }

    /**
     * Create an instance of {@link FieldSearchQuery }.
     *
     * @return the field search query
     */
    public FieldSearchQuery createFieldSearchQuery() {
        return new FieldSearchQuery();
    }

    /**
     * Create an instance of {@link GetObjectHistoryResponse }.
     *
     * @return the gets the object history response
     */
    public GetObjectHistoryResponse createGetObjectHistoryResponse() {
        return new GetObjectHistoryResponse();
    }

    /**
     * Create an instance of {@link SetDatastreamStateResponse }.
     *
     * @return the sets the datastream state response
     */
    public SetDatastreamStateResponse createSetDatastreamStateResponse() {
        return new SetDatastreamStateResponse();
    }

    /**
     * Create an instance of {@link DatastreamBindingMap.DsBindings }
     *
     * @return the ds bindings
     */
    public DatastreamBindingMap.DsBindings createDatastreamBindingMapDsBindings() {
        return new DatastreamBindingMap.DsBindings();
    }

    /**
     * Create an instance of {@link ModifyDatastreamByValue }.
     *
     * @return the modify datastream by value
     */
    public ModifyDatastreamByValue createModifyDatastreamByValue() {
        return new ModifyDatastreamByValue();
    }

    /**
     * Create an instance of {@link ModifyObject }.
     *
     * @return the modify object
     */
    public ModifyObject createModifyObject() {
        return new ModifyObject();
    }

    /**
     * Create an instance of {@link AddDatastream }.
     *
     * @return the adds the datastream
     */
    public AddDatastream createAddDatastream() {
        return new AddDatastream();
    }

    /**
     * Create an instance of {@link MIMETypedStream }.
     *
     * @return the MIME typed stream
     */
    public MIMETypedStream createMIMETypedStream() {
        return new MIMETypedStream();
    }

    /**
     * Create an instance of {@link GetObjectHistory }.
     *
     * @return the gets the object history
     */
    public GetObjectHistory createGetObjectHistory() {
        return new GetObjectHistory();
    }

    /**
     * Create an instance of {@link FindObjectsResponse }.
     *
     * @return the find objects response
     */
    public FindObjectsResponse createFindObjectsResponse() {
        return new FindObjectsResponse();
    }

    /**
     * Create an instance of {@link GetDatastreamsResponse }.
     *
     * @return the gets the datastreams response
     */
    public GetDatastreamsResponse createGetDatastreamsResponse() {
        return new GetDatastreamsResponse();
    }

    /**
     * Create an instance of {@link FieldSearchQuery.Conditions }
     *
     * @return the conditions
     */
    public FieldSearchQuery.Conditions createFieldSearchQueryConditions() {
        return new FieldSearchQuery.Conditions();
    }

    /**
     * Create an instance of {@link SetDatastreamState }.
     *
     * @return the sets the datastream state
     */
    public SetDatastreamState createSetDatastreamState() {
        return new SetDatastreamState();
    }

    /**
     * Create an instance of {@link SetDatastreamVersionableResponse }.
     *
     * @return the sets the datastream versionable response
     */
    public SetDatastreamVersionableResponse createSetDatastreamVersionableResponse() {
        return new SetDatastreamVersionableResponse();
    }

    /**
     * Create an instance of {@link CompareDatastreamChecksum }.
     *
     * @return the compare datastream checksum
     */
    public CompareDatastreamChecksum createCompareDatastreamChecksum() {
        return new CompareDatastreamChecksum();
    }

    /**
     * Create an instance of {@link PurgeObject }.
     *
     * @return the purge object
     */
    public PurgeObject createPurgeObject() {
        return new PurgeObject();
    }

    /**
     * Create an instance of {@link ArrayOfString }.
     *
     * @return the array of string
     */
    public ArrayOfString createArrayOfString() {
        return new ArrayOfString();
    }

    /**
     * Create an instance of {@link DescribeRepository }.
     *
     * @return the describe repository
     */
    public DescribeRepository createDescribeRepository() {
        return new DescribeRepository();
    }

    /**
     * Create an instance of {@link PurgeDatastream }.
     *
     * @return the purge datastream
     */
    public PurgeDatastream createPurgeDatastream() {
        return new PurgeDatastream();
    }

    /**
     * Create an instance of {@link ModifyDatastreamByReference }.
     *
     * @return the modify datastream by reference
     */
    public ModifyDatastreamByReference createModifyDatastreamByReference() {
        return new ModifyDatastreamByReference();
    }

    /**
     * Create an instance of {@link GetObjectXMLResponse }.
     *
     * @return the gets the object xml response
     */
    public GetObjectXMLResponse createGetObjectXMLResponse() {
        return new GetObjectXMLResponse();
    }

    /**
     * Create an instance of {@link ListDatastreamsResponse }.
     *
     * @return the list datastreams response
     */
    public ListDatastreamsResponse createListDatastreamsResponse() {
        return new ListDatastreamsResponse();
    }

    /**
     * Create an instance of {@link GetDatastreamHistory }.
     *
     * @return the gets the datastream history
     */
    public GetDatastreamHistory createGetDatastreamHistory() {
        return new GetDatastreamHistory();
    }

    /**
     * Create an instance of {@link ListMethods }.
     *
     * @return the list methods
     */
    public ListMethods createListMethods() {
        return new ListMethods();
    }

    /**
     * Create an instance of {@link PurgeObjectResponse }.
     *
     * @return the purge object response
     */
    public PurgeObjectResponse createPurgeObjectResponse() {
        return new PurgeObjectResponse();
    }

    /**
     * Create an instance of {@link RelationshipTuple }.
     *
     * @return the relationship tuple
     */
    public RelationshipTuple createRelationshipTuple() {
        return new RelationshipTuple();
    }

    /**
     * Create an instance of {@link ObjectProfile }.
     *
     * @return the object profile
     */
    public ObjectProfile createObjectProfile() {
        return new ObjectProfile();
    }

    /**
     * Create an instance of {@link GetNextPIDResponse }.
     *
     * @return the gets the next pid response
     */
    public GetNextPIDResponse createGetNextPIDResponse() {
        return new GetNextPIDResponse();
    }

    /**
     * Create an instance of {@link ObjectMethodsDef.MethodParmDefs }
     *
     * @return the method parm defs
     */
    public ObjectMethodsDef.MethodParmDefs createObjectMethodsDefMethodParmDefs() {
        return new ObjectMethodsDef.MethodParmDefs();
    }

    /**
     * Create an instance of {@link ListSession }.
     *
     * @return the list session
     */
    public ListSession createListSession() {
        return new ListSession();
    }

    /**
     * Create an instance of {@link FieldSearchResult }.
     *
     * @return the field search result
     */
    public FieldSearchResult createFieldSearchResult() {
        return new FieldSearchResult();
    }

    /**
     * Create an instance of {@link AddDatastreamResponse }.
     *
     * @return the adds the datastream response
     */
    public AddDatastreamResponse createAddDatastreamResponse() {
        return new AddDatastreamResponse();
    }

    /**
     * Create an instance of {@link DatastreamBindingMap }.
     *
     * @return the datastream binding map
     */
    public DatastreamBindingMap createDatastreamBindingMap() {
        return new DatastreamBindingMap();
    }

    /**
     * Create an instance of {@link FindObjects }.
     *
     * @return the find objects
     */
    public FindObjects createFindObjects() {
        return new FindObjects();
    }

    /**
     * Create an instance of {@link Property }.
     *
     * @return the property
     */
    public Property createProperty() {
        return new Property();
    }

    /**
     * Create an instance of {@link GetObjectXML }.
     *
     * @return the gets the object xml
     */
    public GetObjectXML createGetObjectXML() {
        return new GetObjectXML();
    }

    /**
     * Create an instance of {@link GetDatastream }.
     *
     * @return the gets the datastream
     */
    public GetDatastream createGetDatastream() {
        return new GetDatastream();
    }

    /**
     * Create an instance of {@link Datastream }.
     *
     * @return the datastream
     */
    public Datastream createDatastream() {
        return new Datastream();
    }

    /**
     * Create an instance of {@link GetDatastreamResponse }.
     *
     * @return the gets the datastream response
     */
    public GetDatastreamResponse createGetDatastreamResponse() {
        return new GetDatastreamResponse();
    }

    /**
     * Create an instance of {@link GetDatastreamHistoryResponse }.
     *
     * @return the gets the datastream history response
     */
    public GetDatastreamHistoryResponse createGetDatastreamHistoryResponse() {
        return new GetDatastreamHistoryResponse();
    }

    /**
     * Create an instance of {@link Condition }.
     *
     * @return the condition
     */
    public Condition createCondition() {
        return new Condition();
    }

    /**
     * Create an instance of {@link GetRelationshipsResponse }.
     *
     * @return the gets the relationships response
     */
    public GetRelationshipsResponse createGetRelationshipsResponse() {
        return new GetRelationshipsResponse();
    }

    /**
     * Create an instance of {@link ModifyDatastreamByReferenceResponse }.
     *
     * @return the modify datastream by reference response
     */
    public ModifyDatastreamByReferenceResponse createModifyDatastreamByReferenceResponse() {
        return new ModifyDatastreamByReferenceResponse();
    }

    /**
     * Create an instance of {@link ModifyObjectResponse }.
     *
     * @return the modify object response
     */
    public ModifyObjectResponse createModifyObjectResponse() {
        return new ModifyObjectResponse();
    }

    /**
     * Create an instance of {@link SetDatastreamVersionable }.
     *
     * @return the sets the datastream versionable
     */
    public SetDatastreamVersionable createSetDatastreamVersionable() {
        return new SetDatastreamVersionable();
    }

    /**
     * Create an instance of {@link ResumeFindObjectsResponse }.
     *
     * @return the resume find objects response
     */
    public ResumeFindObjectsResponse createResumeFindObjectsResponse() {
        return new ResumeFindObjectsResponse();
    }

    /**
     * Create an instance of {@link GetNextPID }.
     *
     * @return the gets the next pid
     */
    public GetNextPID createGetNextPID() {
        return new GetNextPID();
    }

    /**
     * Create an instance of {@link ResumeFindObjects }.
     *
     * @return the resume find objects
     */
    public ResumeFindObjects createResumeFindObjects() {
        return new ResumeFindObjects();
    }

    /**
     * Create an instance of {@link GetDisseminationResponse }.
     *
     * @return the gets the dissemination response
     */
    public GetDisseminationResponse createGetDisseminationResponse() {
        return new GetDisseminationResponse();
    }

    /**
     * Create an instance of {@link GetDissemination.Parameters }
     *
     * @return the parameters
     */
    public GetDissemination.Parameters createGetDisseminationParameters() {
        return new GetDissemination.Parameters();
    }

    /**
     * Create an instance of {@link GetDissemination }.
     *
     * @return the gets the dissemination
     */
    public GetDissemination createGetDissemination() {
        return new GetDissemination();
    }

    /**
     * Create an instance of {@link ObjectFields }.
     *
     * @return the object fields
     */
    public ObjectFields createObjectFields() {
        return new ObjectFields();
    }

    /**
     * Create an instance of {@link PurgeRelationshipResponse }.
     *
     * @return the purge relationship response
     */
    public PurgeRelationshipResponse createPurgeRelationshipResponse() {
        return new PurgeRelationshipResponse();
    }

    /**
     * Create an instance of {@link Export }.
     *
     * @return the export
     */
    public Export createExport() {
        return new Export();
    }

    /**
     * Create an instance of {@link MIMETypedStream.Header }
     *
     * @return the header
     */
    public MIMETypedStream.Header createMIMETypedStreamHeader() {
        return new MIMETypedStream.Header();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}.
     *
     * @param value the value
     * @return the JAXB element< string>
     */
    @XmlElementDecl(namespace = "", name = "cDate", scope = ObjectFields.class)
    public JAXBElement<String> createObjectFieldsCDate(String value) {
        return new JAXBElement<String>(_ObjectFieldsCDate_QNAME, String.class, ObjectFields.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}.
     *
     * @param value the value
     * @return the JAXB element< string>
     */
    @XmlElementDecl(namespace = "", name = "ownerId", scope = ObjectFields.class)
    public JAXBElement<String> createObjectFieldsOwnerId(String value) {
        return new JAXBElement<String>(_ObjectFieldsOwnerId_QNAME, String.class, ObjectFields.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}.
     *
     * @param value the value
     * @return the JAXB element< string>
     */
    @XmlElementDecl(namespace = "", name = "state", scope = ObjectFields.class)
    public JAXBElement<String> createObjectFieldsState(String value) {
        return new JAXBElement<String>(_ObjectFieldsState_QNAME, String.class, ObjectFields.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}.
     *
     * @param value the value
     * @return the JAXB element< string>
     */
    @XmlElementDecl(namespace = "", name = "label", scope = ObjectFields.class)
    public JAXBElement<String> createObjectFieldsLabel(String value) {
        return new JAXBElement<String>(_ObjectFieldsLabel_QNAME, String.class, ObjectFields.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}.
     *
     * @param value the value
     * @return the JAXB element< string>
     */
    @XmlElementDecl(namespace = "", name = "pid", scope = ObjectFields.class)
    public JAXBElement<String> createObjectFieldsPid(String value) {
        return new JAXBElement<String>(_ObjectFieldsPid_QNAME, String.class, ObjectFields.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}.
     *
     * @param value the value
     * @return the JAXB element< string>
     */
    @XmlElementDecl(namespace = "", name = "dcmDate", scope = ObjectFields.class)
    public JAXBElement<String> createObjectFieldsDcmDate(String value) {
        return new JAXBElement<String>(_ObjectFieldsDcmDate_QNAME, String.class, ObjectFields.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}.
     *
     * @param value the value
     * @return the JAXB element< string>
     */
    @XmlElementDecl(namespace = "", name = "mDate", scope = ObjectFields.class)
    public JAXBElement<String> createObjectFieldsMDate(String value) {
        return new JAXBElement<String>(_ObjectFieldsMDate_QNAME, String.class, ObjectFields.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListSession }{@code >}}.
     *
     * @param value the value
     * @return the JAXB element< list session>
     */
    @XmlElementDecl(namespace = "", name = "listSession", scope = FieldSearchResult.class)
    public JAXBElement<ListSession> createFieldSearchResultListSession(ListSession value) {
        return new JAXBElement<ListSession>(_FieldSearchResultListSession_QNAME, ListSession.class, FieldSearchResult.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}.
     *
     * @param value the value
     * @return the JAXB element< string>
     */
    @XmlElementDecl(namespace = "", name = "expirationDate", scope = ListSession.class)
    public JAXBElement<String> createListSessionExpirationDate(String value) {
        return new JAXBElement<String>(_ListSessionExpirationDate_QNAME, String.class, ListSession.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FieldSearchQuery.Conditions }{@code >}}
     *
     * @param value the value
     * @return the JAXB element< field search query. conditions>
     */
    @XmlElementDecl(namespace = "", name = "conditions", scope = FieldSearchQuery.class)
    public JAXBElement<FieldSearchQuery.Conditions> createFieldSearchQueryConditions(FieldSearchQuery.Conditions value) {
        return new JAXBElement<FieldSearchQuery.Conditions>(_FieldSearchQueryConditions_QNAME, FieldSearchQuery.Conditions.class, FieldSearchQuery.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}.
     *
     * @param value the value
     * @return the JAXB element< string>
     */
    @XmlElementDecl(namespace = "", name = "terms", scope = FieldSearchQuery.class)
    public JAXBElement<String> createFieldSearchQueryTerms(String value) {
        return new JAXBElement<String>(_FieldSearchQueryTerms_QNAME, String.class, FieldSearchQuery.class, value);
    }

}
