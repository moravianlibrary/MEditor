ALTER TABLE ONLY meditor.user_in_role DROP CONSTRAINT user_in_role_user_id_fkey;
ALTER TABLE ONLY meditor.user_in_role DROP CONSTRAINT user_in_role_role_id_fkey;
ALTER TABLE ONLY meditor.user_in_role DROP CONSTRAINT user_in_role_fk_role;
ALTER TABLE ONLY meditor.user_in_role DROP CONSTRAINT user_in_role_fk_editor_user;
ALTER TABLE ONLY meditor.tree_structure_node DROP CONSTRAINT tree_structure_node_fk_editor_user;
ALTER TABLE ONLY meditor.tree_structure DROP CONSTRAINT tree_structure_fk_editor_user;
ALTER TABLE ONLY meditor.stored_files DROP CONSTRAINT stored_files_fk_editor_user;
ALTER TABLE ONLY meditor.recently_modified_item DROP CONSTRAINT recently_modified_item_fk_editor_user;
ALTER TABLE ONLY meditor.open_id_identity DROP CONSTRAINT open_id_identity_fk_editor_user;
ALTER TABLE ONLY meditor.lock DROP CONSTRAINT lock_fk_editor_user;
DROP INDEX meditor.tree_structure_code_idx;
DROP INDEX meditor.stored_files_uuid_idx;
DROP INDEX meditor.recently_modified_item_uuid_idx;
DROP INDEX meditor.lock_uuid_idx;
DROP INDEX meditor.image_old_fs_path_idx;
DROP INDEX meditor.image_identifier_idx;
DROP INDEX meditor.description_uuid_idx;
ALTER TABLE ONLY meditor.version DROP CONSTRAINT version_pkey;
ALTER TABLE ONLY meditor.user_in_role DROP CONSTRAINT user_in_role_pkey;
ALTER TABLE ONLY meditor.tree_structure DROP CONSTRAINT tree_structure_pkey;
ALTER TABLE ONLY meditor.tree_structure_node DROP CONSTRAINT tree_structure_node_pkey;
ALTER TABLE ONLY meditor.stored_files DROP CONSTRAINT stored_files_pkey;
ALTER TABLE ONLY meditor.role DROP CONSTRAINT role_pkey;
ALTER TABLE ONLY meditor.request_for_adding DROP CONSTRAINT request_for_adding_id_key;
ALTER TABLE ONLY meditor.recently_modified_item DROP CONSTRAINT recently_modified_item_pkey;
ALTER TABLE ONLY meditor.input_queue_item DROP CONSTRAINT path_unique;
ALTER TABLE ONLY meditor.input_queue_item_name DROP CONSTRAINT path_name_unique;
ALTER TABLE ONLY meditor.open_id_identity DROP CONSTRAINT open_id_identity_pkey;
ALTER TABLE ONLY meditor.lock DROP CONSTRAINT lock_pkey;
ALTER TABLE ONLY meditor.input_queue_item DROP CONSTRAINT input_queue_item_pkey;
ALTER TABLE ONLY meditor.input_queue_item_name DROP CONSTRAINT input_queue_item_name_pkey;
ALTER TABLE ONLY meditor.image DROP CONSTRAINT image_pkey;
ALTER TABLE ONLY meditor.image DROP CONSTRAINT image_identifier_uniq;
ALTER TABLE ONLY meditor.editor_user DROP CONSTRAINT editor_user_pkey;
DROP TABLE meditor.version;
DROP TABLE meditor.user_in_role;
DROP TABLE meditor.tree_structure_node;
DROP TABLE meditor.tree_structure;
DROP TABLE meditor.stored_files;
DROP SEQUENCE meditor.seq_user_in_role;
DROP SEQUENCE meditor.seq_tree_structure_node;
DROP SEQUENCE meditor.seq_tree_structure;
DROP SEQUENCE meditor.seq_stored_files;
DROP TABLE meditor.role;
DROP SEQUENCE meditor.seq_role;
DROP TABLE meditor.request_for_adding;
DROP SEQUENCE meditor.seq_request_for_adding;
DROP TABLE meditor.recently_modified_item;
DROP SEQUENCE meditor.seq_recently_modified_item;
DROP TABLE meditor.open_id_identity;
DROP SEQUENCE meditor.seq_open_id_identity;
DROP TABLE meditor.lock;
DROP SEQUENCE meditor.seq_lock;
DROP TABLE meditor.input_queue_item_name;
DROP SEQUENCE meditor.seq_input_queue_item_name;
DROP TABLE meditor.input_queue_item;
DROP SEQUENCE meditor.seq_input_queue_item;
DROP TABLE meditor.image;
DROP SEQUENCE meditor.seq_image;
DROP TABLE meditor.editor_user;
DROP SEQUENCE meditor.seq_user;
DROP TABLE meditor.description;
DROP SEQUENCE meditor.seq_description;
DROP SCHEMA meditor;

CREATE SCHEMA meditor;
ALTER SCHEMA meditor OWNER TO meditor;
COMMENT ON SCHEMA meditor IS 'metadata editor schema';


CREATE SEQUENCE seq_action
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
ALTER TABLE meditor.seq_action OWNER TO meditor;
CREATE TABLE action (
	id integer DEFAULT nextval('seq_action'::regclass) NOT NULL,
	editor_user_id integer NOT NULL,
	timestamp timestamp without time zone NOT NULL,
	PRIMARY KEY (id)
	);
ALTER TABLE meditor.action OWNER TO meditor;


CREATE TABLE right_in_role (
	editor_right_name character varying(30) NOT NULL,
	role_name character varying(30) NOT NULL,
	PRIMARY KEY (editor_right_name, role_name)
	);
ALTER TABLE meditor.right_in_role OWNER TO meditor;


CREATE TABLE version (
	version integer NOT NULL,
	PRIMARY KEY (version)
	);
ALTER TABLE meditor.version OWNER TO meditor;


CREATE TABLE image (
	identifier character varying(100) NOT NULL,
	shown timestamp without time zone NOT NULL,
	old_fs_path character varying(255) NOT NULL,
	imagefile character varying(255) NOT NULL,
	PRIMARY KEY (identifier)
	);
ALTER TABLE meditor.image OWNER TO meditor;


CREATE SEQUENCE seq_tree_structure_node
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
ALTER TABLE meditor.seq_tree_structure_node OWNER TO meditor;
CREATE TABLE tree_structure_node (
	id integer DEFAULT nextval('seq_tree_structure_node'::regclass) NOT NULL,
	tree_structure_id integer NOT NULL,
	prop_id character varying(4),
	prop_parent character varying(4),
	prop_name character varying(256),
	prop_picture_or_uuid character varying(256),
	prop_model_id character varying(20),
	prop_type character varying(25),
	prop_date_or_int_part_name character varying(256),
	prop_note_or_int_subtitle character varying(1000),
	prop_part_number_or_alto character varying(256),
	prop_aditional_info_or_ocr character varying(256),
	prop_exist boolean, PRIMARY KEY (id)
	);
ALTER TABLE meditor.tree_structure_node OWNER TO meditor;


CREATE TABLE users_right (
	editor_user_id integer  NOT NULL,
	editor_right_name character varying(30) NOT NULL,
	PRIMARY KEY (editor_user_id, editor_right_name)
	);
ALTER TABLE meditor.users_right OWNER TO meditor;


CREATE SEQUENCE seq_request_to_admin
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
ALTER TABLE meditor.seq_request_to_admin OWNER TO meditor;
CREATE TABLE request_to_admin (
	id integer DEFAULT nextval('seq_request_to_admin'::regclass) NOT NULL,
	admin_editor_user_id integer NOT NULL,
	type character varying(300) NOT NULL,
	object character varying(300) NOT NULL,
	description character varying(16000),
	solved boolean DEFAULT 'false', 
	PRIMARY KEY (id)
	);
ALTER TABLE meditor.request_to_admin OWNER TO meditor;


CREATE TABLE crud_request_to_admin_action (
	request_to_admin_id integer NOT NULL, 
	type character varying(1) NOT NULL
	) INHERITS (action);


CREATE TABLE editor_right (
	name character varying(30) NOT NULL, 
	description character varying(255),
	PRIMARY KEY (name)
	);
ALTER TABLE meditor.editor_right OWNER TO meditor;


CREATE TABLE role (
	name character varying(30) NOT NULL,
	description character varying(255),
	PRIMARY KEY (name)
	);
ALTER TABLE meditor.role OWNER TO meditor;


CREATE TABLE users_role (
	editor_user_id integer NOT NULL,
	role_name character varying(30) NOT NULL,
	PRIMARY KEY (editor_user_id, role_name)
	);
ALTER TABLE meditor.users_role OWNER TO meditor;


CREATE TABLE ldap_identity (
	editor_user_id integer NOT NULL,
	identity character varying(160) NOT NULL,
	PRIMARY KEY (identity)
	);
ALTER TABLE meditor.ldap_identity OWNER TO meditor;


CREATE TABLE shibboleth_identity (
	editor_user_id integer NOT NULL,
	identity character varying(160) NOT NULL,
	PRIMARY KEY (identity)
	);
ALTER TABLE meditor.shibboleth_identity OWNER TO meditor;


CREATE TABLE open_id_identity (
	editor_user_id integer NOT NULL,
	identity character varying(160) NOT NULL,
	PRIMARY KEY (identity)
	);
ALTER TABLE meditor.open_id_identity OWNER TO meditor;


CREATE TABLE conversion (
	input_queue_directory_path character varying(300) NOT NULL,
	PRIMARY KEY (id)
	) INHERITS (action);;
ALTER TABLE meditor.conversion OWNER TO meditor;


CREATE TABLE input_queue (
	directory_path character varying(300) NOT NULL,
	name character varying(300),
	PRIMARY KEY (directory_path)
	);
ALTER TABLE meditor.input_queue OWNER TO meditor;


CREATE TABLE input_queue_item (
	path character varying(300) NOT NULL, 
	barcode character varying(300) NOT NULL, 
	ingested boolean DEFAULT 'false', 
	PRIMARY KEY (path)
	);
ALTER TABLE meditor.input_queue_item OWNER TO meditor;


CREATE TABLE crud_saved_edited_object_action (
	saved_edited_object_id integer NOT NULL,
	type character varying(1) NOT NULL
	) INHERITS (action);
ALTER TABLE meditor.crud_saved_edited_object_action OWNER TO meditor;


CREATE TABLE crud_lock_action (
	lock_id integer NOT NULL,
	type character varying(1) NOT NULL
	) INHERITS (action);
ALTER TABLE meditor.crud_lock_action OWNER TO meditor;


CREATE TABLE crud_tree_structure_action (
	tree_structure_id integer NOT NULL,
	type character varying(1) NOT NULL
	) INHERITS (action);
ALTER TABLE meditor.crud_tree_structure_action OWNER TO meditor;


CREATE TABLE long_running_process (
	name character varying(300) NOT NULL,
	finished timestamp without time zone
	) INHERITS (action);
ALTER TABLE meditor.long_running_process OWNER TO meditor;


CREATE TABLE crud_digital_object_action (
	digital_object_uuid character varying(45) NOT NULL,
	type character varying(1) NOT NULL
	) INHERITS (action);
ALTER TABLE meditor.crud_digital_object_action OWNER TO meditor;


CREATE TABLE crud_do_action_with_top_object (
	top_digital_object_uuid character varying(45) NOT NULL
	) INHERITS (crud_digital_object_action);
ALTER TABLE meditor.crud_do_action_with_top_object OWNER TO meditor;


CREATE SEQUENCE seq_tree_structure
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
ALTER TABLE meditor.seq_tree_structure OWNER TO meditor;
CREATE TABLE tree_structure (
	id integer DEFAULT nextval('seq_tree_structure'::regclass) NOT NULL,
	barcode character varying(30) NOT NULL,
	description character varying(255),
	name character varying(8192),
	model character varying(50),
	state boolean DEFAULT 'true' NOT NULL,
	input_queue_directory_path character varying(300) NOT NULL,
	PRIMARY KEY (id)
	);
ALTER TABLE meditor.tree_structure OWNER TO meditor;


CREATE TABLE description (
	editor_user_id integer NOT NULL,
	digital_object_uuid character varying(45) NOT NULL,
	description character varying(16000) NOT NULL,
	PRIMARY KEY (editor_user_id, digital_object_uuid)
	);
ALTER TABLE meditor.description OWNER TO meditor;


CREATE TABLE digital_object (
	uuid character varying(45) NOT NULL,
	model character varying(50) NOT NULL,
	name character varying(300),
	description character varying(16000),
	input_queue_directory_path character varying(300),
	state boolean DEFAULT 'true' NOT NULL,
	PRIMARY KEY (uuid)
	);
ALTER TABLE meditor.digital_object OWNER TO meditor;


CREATE SEQUENCE seq_saved_edited_object
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
ALTER TABLE meditor.seq_saved_edited_object OWNER TO meditor;
CREATE TABLE saved_edited_object (
	id integer DEFAULT nextval('seq_saved_edited_object'::regclass) NOT NULL,
	digital_object_uuid character varying(45) NOT NULL,
	file_name character varying(300) NOT NULL,
	description character varying(16000),
	state boolean DEFAULT 'true' NOT NULL,
	PRIMARY KEY (id)
	);
ALTER TABLE meditor.saved_edited_object OWNER TO meditor;


CREATE SEQUENCE seq_lock
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
ALTER TABLE meditor.seq_lock OWNER TO meditor;
CREATE TABLE lock (
	id integer DEFAULT nextval('seq_lock'::regclass) NOT NULL,
	digital_object_uuid character varying(45) NOT NULL,
	description character varying(16000),
	state boolean DEFAULT 'true' NOT NULL,
	PRIMARY KEY (id)
	);
ALTER TABLE meditor.lock OWNER TO meditor;


CREATE TABLE log_in_out (
	type boolean NOT NULL
	) INHERITS (action);
ALTER TABLE meditor.log_in_out OWNER TO meditor;


CREATE TABLE user_edit (
	edited_editor_user_id integer NOT NULL,
	description character varying(8000),
	type character varying(1) NOT NULL
	) INHERITS (action);
ALTER TABLE meditor.user_edit OWNER TO meditor;


CREATE SEQUENCE seq_editor_user
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;
ALTER TABLE meditor.seq_editor_user OWNER TO meditor;
CREATE TABLE editor_user (
	id integer DEFAULT nextval('seq_editor_user'::regclass) NOT NULL,
	name character varying(50),
	surname character varying(50) NOT NULL,
	state boolean DEFAULT 'true' NOT NULL,
	CONSTRAINT id PRIMARY KEY (id)
	);
ALTER TABLE meditor.editor_user OWNER TO meditor;


CREATE UNIQUE INDEX image_identifier ON image (identifier);
CREATE UNIQUE INDEX ldap_identity_identity ON ldap_identity (identity);
CREATE UNIQUE INDEX shibboleth_identity_identity ON shibboleth_identity (identity);
CREATE UNIQUE INDEX open_id_identity_identity ON open_id_identity (identity);
CREATE UNIQUE INDEX input_queue_directory_path ON input_queue (directory_path);
CREATE UNIQUE INDEX tree_structure_id ON tree_structure (id);
CREATE UNIQUE INDEX digital_object_uuid ON digital_object (uuid);
CREATE UNIQUE INDEX action_id ON action (id);
CREATE UNIQUE INDEX editor_user_id ON editor_user (id);
CREATE UNIQUE INDEX input_queue_item_path ON input_queue_item (path);
CREATE UNIQUE INDEX role_name ON role (name);
CREATE INDEX right_in_role_role_name ON right_in_role (role_name);
CREATE INDEX image_old_fs_path ON image (old_fs_path);
CREATE INDEX tree_structure_node_tree_structure_id ON tree_structure_node (tree_structure_id);
CREATE INDEX users_right_editor_user_id ON users_right (editor_user_id);
CREATE INDEX users_role_editor_user_id ON users_role (editor_user_id);
CREATE INDEX ldap_identity_editor_user_id ON ldap_identity (editor_user_id);
CREATE INDEX shibboleth_identity_editor_user_id ON shibboleth_identity (editor_user_id);
CREATE INDEX open_id_identity_editor_user_id ON open_id_identity (editor_user_id);
CREATE INDEX conversion_input_queue_directory_path ON conversion (input_queue_directory_path);
CREATE INDEX crud_digital_object_action_digital_object_uuid ON crud_digital_object_action (digital_object_uuid);
CREATE INDEX tree_structure_barcode ON tree_structure (barcode) WHERE state = true;
CREATE INDEX description_editor_user_id ON description (editor_user_id);
CREATE INDEX digital_object_input_queue_directory_path ON digital_object (input_queue_directory_path);
CREATE INDEX saved_edited_object_digital_object_uuid ON saved_edited_object (digital_object_uuid) WHERE state = true;
CREATE INDEX lock_digital_object_uuid ON lock (digital_object_uuid) WHERE state = true;
CREATE INDEX user_edit_edited_editor_user_id ON user_edit (edited_editor_user_id);
CREATE INDEX action_editor_user_id ON action (editor_user_id);
CREATE INDEX action_timestamp ON action (timestamp);

ALTER TABLE right_in_role ADD CONSTRAINT right_in_role_fk_role FOREIGN KEY (role_name) REFERENCES role (name);
ALTER TABLE right_in_role ADD CONSTRAINT right_in_role_fk_editor_right FOREIGN KEY (editor_right_name) REFERENCES editor_right (name);
ALTER TABLE tree_structure_node ADD CONSTRAINT tree_structure_node_fk_tree_structure FOREIGN KEY (tree_structure_id) REFERENCES tree_structure (id);
ALTER TABLE users_right ADD CONSTRAINT users_right_fk_editor_user FOREIGN KEY (editor_user_id) REFERENCES editor_user (id);
ALTER TABLE users_right ADD CONSTRAINT users_right_fk_editor_right FOREIGN KEY (editor_right_name) REFERENCES editor_right (name);
ALTER TABLE users_role ADD CONSTRAINT users_role_fk_editor_user FOREIGN KEY (editor_user_id) REFERENCES editor_user (id);
ALTER TABLE users_role ADD CONSTRAINT users_role_fk_role_name FOREIGN KEY (role_name) REFERENCES role (name);
ALTER TABLE request_to_admin ADD CONSTRAINT request_to_admin_fk_editor_user_admin FOREIGN KEY (admin_editor_user_id) REFERENCES editor_user (id);
ALTER TABLE shibboleth_identity ADD CONSTRAINT shibboleth_identity_fk_editor_user FOREIGN KEY (editor_user_id) REFERENCES editor_user (id);
ALTER TABLE open_id_identity ADD CONSTRAINT open_id_identity_fk_editor_user FOREIGN KEY (editor_user_id) REFERENCES editor_user (id);
ALTER TABLE ldap_identity ADD CONSTRAINT ldap_identity_fk_editor_user FOREIGN KEY (editor_user_id) REFERENCES editor_user (id);
ALTER TABLE conversion ADD CONSTRAINT conversion_fk_input_queue FOREIGN KEY (input_queue_directory_path) REFERENCES input_queue (directory_path);
ALTER TABLE tree_structure ADD CONSTRAINT tree_structure_fk_input_queue FOREIGN KEY (input_queue_directory_path) REFERENCES input_queue (directory_path);
ALTER TABLE digital_object ADD CONSTRAINT digital_object_fk_input_queue FOREIGN KEY (input_queue_directory_path) REFERENCES input_queue (directory_path);
ALTER TABLE crud_saved_edited_object_action ADD CONSTRAINT crud_saved_edited_object_action_fk_saved_edited_object FOREIGN KEY (saved_edited_object_id) REFERENCES saved_edited_object (id);
ALTER TABLE crud_lock_action ADD CONSTRAINT crud_lock_action_fk_lock FOREIGN KEY (lock_id) REFERENCES lock (id);
ALTER TABLE crud_tree_structure_action ADD CONSTRAINT crud_tree_structure_action_fk_tree_structure FOREIGN KEY (tree_structure_id) REFERENCES tree_structure (id);
ALTER TABLE crud_do_action_with_top_object ADD CONSTRAINT crud_do_action_with_top_object_fk_top_digital_object FOREIGN KEY (top_digital_object_uuid) REFERENCES digital_object (uuid);
ALTER TABLE crud_digital_object_action ADD CONSTRAINT crud_digital_object_action_fk_digital_object FOREIGN KEY (digital_object_uuid) REFERENCES digital_object (uuid);
ALTER TABLE description ADD CONSTRAINT description_fk_editor_user FOREIGN KEY (editor_user_id) REFERENCES editor_user (id);
ALTER TABLE description ADD CONSTRAINT description_fk_digital_object FOREIGN KEY (digital_object_uuid) REFERENCES digital_object (uuid);
ALTER TABLE lock ADD CONSTRAINT lock_fk_digital_object FOREIGN KEY (digital_object_uuid) REFERENCES digital_object (uuid);
ALTER TABLE saved_edited_object ADD CONSTRAINT saved_edited_object_fk_digital_object FOREIGN KEY (digital_object_uuid) REFERENCES digital_object (uuid);
ALTER TABLE user_edit ADD CONSTRAINT user_edit_fk_edited_editor_user FOREIGN KEY (edited_editor_user_id) REFERENCES editor_user (id);
ALTER TABLE action ADD CONSTRAINT action_fk_editor_user FOREIGN KEY (editor_user_id) REFERENCES editor_user (id);
ALTER TABLE crud_request_to_admin_action ADD CONSTRAINT crud_request_to_admin_action_fk_request_to_admin FOREIGN KEY (request_to_admin_id) REFERENCES request_to_admin (id);
