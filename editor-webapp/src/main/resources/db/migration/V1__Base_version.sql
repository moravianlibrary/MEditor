CREATE SEQUENCE seq_action
START WITH 1
INCREMENT BY 1
NO MAXVALUE
NO MINVALUE
CACHE 1;

CREATE TABLE action (
  id             INTEGER DEFAULT nextval('seq_action' :: REGCLASS) NOT NULL,
  editor_user_id INTEGER                                           NOT NULL,
  timestamp      TIMESTAMP WITHOUT TIME ZONE                       NOT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE right_in_role (
  editor_right_name CHARACTER VARYING(30) NOT NULL,
  role_name         CHARACTER VARYING(30) NOT NULL,
  PRIMARY KEY (editor_right_name, role_name)
);

CREATE TABLE version (
  version INTEGER NOT NULL,
  PRIMARY KEY (version)
);

CREATE TABLE image (
  identifier  CHARACTER VARYING(100)      NOT NULL,
  shown       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  old_fs_path CHARACTER VARYING(255)      NOT NULL,
  imagefile   CHARACTER VARYING(255)      NOT NULL,
  PRIMARY KEY (identifier)
);

CREATE SEQUENCE seq_tree_structure_node
START WITH 1
INCREMENT BY 1
NO MAXVALUE
NO MINVALUE
CACHE 1;

CREATE TABLE tree_structure_node (
  id                         INTEGER DEFAULT nextval('seq_tree_structure_node' :: REGCLASS) NOT NULL,
  tree_structure_id          INTEGER                                                        NOT NULL,
  prop_id                    CHARACTER VARYING(4),
  prop_parent                CHARACTER VARYING(4),
  prop_name                  CHARACTER VARYING(256),
  prop_picture_or_uuid       CHARACTER VARYING(256),
  prop_model_id              CHARACTER VARYING(20),
  prop_type                  CHARACTER VARYING(25),
  prop_date_or_int_part_name CHARACTER VARYING(256),
  prop_note_or_int_subtitle  CHARACTER VARYING(1000),
  prop_part_number_or_alto   CHARACTER VARYING(256),
  prop_aditional_info_or_ocr CHARACTER VARYING(256),
  prop_exist                 BOOLEAN, PRIMARY KEY (id)
);

CREATE TABLE users_right (
  editor_user_id    INTEGER               NOT NULL,
  editor_right_name CHARACTER VARYING(30) NOT NULL,
  PRIMARY KEY (editor_user_id, editor_right_name)
);

CREATE SEQUENCE seq_request_to_admin
START WITH 1
INCREMENT BY 1
NO MAXVALUE
NO MINVALUE
CACHE 1;

CREATE TABLE request_to_admin (
  id                   INTEGER DEFAULT nextval('seq_request_to_admin' :: REGCLASS) NOT NULL,
  admin_editor_user_id INTEGER                                                     NOT NULL,
  type                 CHARACTER VARYING(300)                                      NOT NULL,
  object               CHARACTER VARYING(300)                                      NOT NULL,
  description          CHARACTER VARYING(16000),
  solved               BOOLEAN DEFAULT 'false',
  PRIMARY KEY (id)
);

CREATE TABLE crud_request_to_admin_action (
  request_to_admin_id INTEGER              NOT NULL,
  type                CHARACTER VARYING(1) NOT NULL
)
  INHERITS (action);


CREATE TABLE editor_right (
  name        CHARACTER VARYING(30) NOT NULL,
  description CHARACTER VARYING(255),
  PRIMARY KEY (name)
);

CREATE TABLE role (
  name        CHARACTER VARYING(30) NOT NULL,
  description CHARACTER VARYING(255),
  PRIMARY KEY (name)
);

CREATE TABLE users_role (
  editor_user_id INTEGER               NOT NULL,
  role_name      CHARACTER VARYING(30) NOT NULL,
  PRIMARY KEY (editor_user_id, role_name)
);

CREATE TABLE ldap_identity (
  editor_user_id INTEGER                NOT NULL,
  identity       CHARACTER VARYING(160) NOT NULL,
  PRIMARY KEY (identity)
);

CREATE TABLE shibboleth_identity (
  editor_user_id INTEGER                NOT NULL,
  identity       CHARACTER VARYING(160) NOT NULL,
  PRIMARY KEY (identity)
);

CREATE TABLE open_id_identity (
  editor_user_id INTEGER                NOT NULL,
  identity       CHARACTER VARYING(160) NOT NULL,
  PRIMARY KEY (identity)
);

CREATE TABLE conversion (
  input_queue_directory_path CHARACTER VARYING(300) NOT NULL,
  PRIMARY KEY (id)
)
  INHERITS (action);
;

CREATE TABLE input_queue (
  directory_path CHARACTER VARYING(300) NOT NULL,
  name           CHARACTER VARYING(300),
  PRIMARY KEY (directory_path)
);

CREATE TABLE input_queue_item (
  path     CHARACTER VARYING(300) NOT NULL,
  barcode  CHARACTER VARYING(300) NOT NULL,
  ingested BOOLEAN DEFAULT 'false',
  PRIMARY KEY (path)
);

CREATE TABLE crud_saved_edited_object_action (
  saved_edited_object_id INTEGER              NOT NULL,
  type                   CHARACTER VARYING(1) NOT NULL
)
  INHERITS (action);

CREATE TABLE crud_lock_action (
  lock_id INTEGER              NOT NULL,
  type    CHARACTER VARYING(1) NOT NULL
)
  INHERITS (action);

CREATE TABLE crud_tree_structure_action (
  tree_structure_id INTEGER              NOT NULL,
  type              CHARACTER VARYING(1) NOT NULL
)
  INHERITS (action);

CREATE TABLE long_running_process (
  name     CHARACTER VARYING(300) NOT NULL,
  finished TIMESTAMP WITHOUT TIME ZONE
)
  INHERITS (action);

CREATE TABLE crud_digital_object_action (
  digital_object_uuid CHARACTER VARYING(45) NOT NULL,
  type                CHARACTER VARYING(1)  NOT NULL
)
  INHERITS (action);

CREATE TABLE crud_do_action_with_top_object (
  top_digital_object_uuid CHARACTER VARYING(45) NOT NULL
)
  INHERITS (crud_digital_object_action);

CREATE SEQUENCE seq_tree_structure
START WITH 1
INCREMENT BY 1
NO MAXVALUE
NO MINVALUE
CACHE 1;


CREATE TABLE tree_structure (
  id                         INTEGER DEFAULT nextval('seq_tree_structure' :: REGCLASS) NOT NULL,
  barcode                    CHARACTER VARYING(30)                                     NOT NULL,
  description                CHARACTER VARYING(255),
  name                       CHARACTER VARYING(8192),
  model                      CHARACTER VARYING(50),
  state                      BOOLEAN DEFAULT 'true'                                    NOT NULL,
  input_queue_directory_path CHARACTER VARYING(300)                                    NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE description (
  editor_user_id      INTEGER                  NOT NULL,
  digital_object_uuid CHARACTER VARYING(45)    NOT NULL,
  description         CHARACTER VARYING(16000) NOT NULL,
  PRIMARY KEY (editor_user_id, digital_object_uuid)
);

CREATE TABLE digital_object (
  uuid                       CHARACTER VARYING(45)  NOT NULL,
  model                      CHARACTER VARYING(50)  NOT NULL,
  name                       CHARACTER VARYING(300),
  description                CHARACTER VARYING(16000),
  input_queue_directory_path CHARACTER VARYING(300),
  state                      BOOLEAN DEFAULT 'true' NOT NULL,
  PRIMARY KEY (uuid)
);

CREATE SEQUENCE seq_saved_edited_object
START WITH 1
INCREMENT BY 1
NO MAXVALUE
NO MINVALUE
CACHE 1;

CREATE TABLE saved_edited_object (
  id                  INTEGER DEFAULT nextval('seq_saved_edited_object' :: REGCLASS) NOT NULL,
  digital_object_uuid CHARACTER VARYING(45)                                          NOT NULL,
  file_name           CHARACTER VARYING(300)                                         NOT NULL,
  description         CHARACTER VARYING(16000),
  state               BOOLEAN DEFAULT 'true'                                         NOT NULL,
  PRIMARY KEY (id)
);

CREATE SEQUENCE seq_lock
START WITH 1
INCREMENT BY 1
NO MAXVALUE
NO MINVALUE
CACHE 1;

CREATE TABLE lock (
  id                  INTEGER DEFAULT nextval('seq_lock' :: REGCLASS) NOT NULL,
  digital_object_uuid CHARACTER VARYING(45)                           NOT NULL,
  description         CHARACTER VARYING(16000),
  state               BOOLEAN DEFAULT 'true'                          NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE log_in_out (
  type BOOLEAN NOT NULL
)
  INHERITS (action);

CREATE TABLE user_edit (
  edited_editor_user_id INTEGER              NOT NULL,
  description           CHARACTER VARYING(8000),
  type                  CHARACTER VARYING(1) NOT NULL
)
  INHERITS (action);

CREATE SEQUENCE seq_editor_user
START WITH 1
INCREMENT BY 1
NO MAXVALUE
NO MINVALUE
CACHE 1;

CREATE TABLE editor_user (
  id      INTEGER DEFAULT nextval('seq_editor_user' :: REGCLASS) NOT NULL,
  name    CHARACTER VARYING(50),
  surname CHARACTER VARYING(50)                                  NOT NULL,
  state   BOOLEAN DEFAULT 'true'                                 NOT NULL,
  CONSTRAINT id PRIMARY KEY (id)
);
--
-- PLease do not change these lines - Do not neither delete nor change the order of the lines
-- This are system default users. It is directly interconnected with the source code.
-- Every change has to be reflected in the java code in the class Constants enum DEFAULT_SYSTEM_USERS
--
INSERT INTO editor_user (name, surname, state) VALUES ('non-existent', 'non-existent', TRUE);
INSERT INTO editor_user (name, surname, state) VALUES ('time', 'time', TRUE);
--

--
-- Admin right and role
--
INSERT INTO editor_right (name, description) VALUES ('ALL', '');
INSERT INTO role (name, description) VALUES ('Administrator', 'Can do everything');
INSERT INTO right_in_role (editor_right_name, role_name) VALUES ('ALL', 'Administrator');

--
-- Default user with admin right
--
INSERT INTO editor_user (name, surname, state) VALUES ('Medit', 'Medit', TRUE);
INSERT INTO ldap_identity (editor_user_id, identity) VALUES (3, 'medit');
INSERT INTO users_role (editor_user_id, role_name) VALUES (3, 'Administrator');

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
CREATE INDEX tree_structure_barcode ON tree_structure (barcode)
  WHERE state = TRUE;
CREATE INDEX description_editor_user_id ON description (editor_user_id);
CREATE INDEX digital_object_input_queue_directory_path ON digital_object (input_queue_directory_path);
CREATE INDEX saved_edited_object_digital_object_uuid ON saved_edited_object (digital_object_uuid)
  WHERE state = TRUE;
CREATE INDEX lock_digital_object_uuid ON lock (digital_object_uuid)
  WHERE state = TRUE;
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

