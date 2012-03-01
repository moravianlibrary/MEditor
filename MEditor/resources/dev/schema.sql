--
-- PostgreSQL database dump
--

DROP SCHEMA IF EXISTS meditor CASCADE;
CREATE SCHEMA meditor;
ALTER SCHEMA meditor OWNER TO meditor;

SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;
SET search_path = meditor, pg_catalog, public;
SET default_tablespace = '';
SET default_with_oids = false;

COMMENT ON SCHEMA meditor IS 'metadata editor schema';

CREATE SEQUENCE seq_description
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE meditor.seq_description OWNER TO meditor;

SELECT pg_catalog.setval('seq_description', 5, true);




CREATE TABLE description (
    id integer DEFAULT nextval('seq_description'::regclass) NOT NULL,
    uuid character varying(45),
    description character varying(16000)
);


ALTER TABLE meditor.description OWNER TO meditor;

--
-- Name: seq_user; Type: SEQUENCE; Schema: public; Owner: meditor
--

CREATE SEQUENCE seq_user
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE meditor.seq_user OWNER TO meditor;

--
-- Name: seq_user; Type: SEQUENCE SET; Schema: public; Owner: meditor
--

SELECT pg_catalog.setval('seq_user', 81, true);



CREATE SEQUENCE seq_tree_structure_node
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

CREATE SEQUENCE seq_tree_structure
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE meditor.seq_tree_structure_node OWNER TO meditor;

ALTER TABLE meditor.seq_tree_structure OWNER TO meditor;

CREATE TABLE tree_structure (
    id integer DEFAULT nextval('seq_tree_structure'::regclass) NOT NULL,
    user_id integer,
    created timestamp without time zone,
    barcode character varying(30),
    description character varying(256), 
    name character varying(101),
    input_path character varying(256),
    model character varying(20)
);


CREATE TABLE tree_structure_node (
    id integer DEFAULT nextval('seq_tree_structure_node'::regclass) NOT NULL,
    tree_id integer,
    prop_id character varying(4),
    prop_parent character varying(4),
    prop_name character varying(256),
    prop_picture character varying(256),
    prop_type character varying(40),
    prop_type_id character varying(20),
    prop_page_type character varying(25),
    prop_date_issued character varying(10),
    prop_alto_path character varying(256),
    prop_ocr_path character varying(256),
    prop_exist boolean
);

ALTER TABLE meditor.tree_structure OWNER TO meditor;

ALTER TABLE meditor.tree_structure_node OWNER TO meditor;

CREATE TABLE editor_user (
    id integer DEFAULT nextval('seq_user'::regclass) NOT NULL,
    name character varying(25),
    surname character varying(25),
    sex boolean
);


ALTER TABLE meditor.editor_user OWNER TO meditor;

CREATE SEQUENCE seq_image
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 4;


ALTER TABLE meditor.seq_image OWNER TO meditor;

SELECT pg_catalog.setval('seq_image', 18092, true);

CREATE TABLE image (
    id integer DEFAULT nextval('seq_image'::regclass) NOT NULL,
    identifier character varying(100),
    shown timestamp without time zone,
    old_fs_path character varying(255),
    imagefile character varying(255)
);


ALTER TABLE meditor.image OWNER TO meditor;

CREATE SEQUENCE seq_input_queue_item
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE meditor.seq_input_queue_item OWNER TO meditor;

SELECT pg_catalog.setval('seq_input_queue_item', 22286, true);

CREATE TABLE input_queue_item (
    id integer DEFAULT nextval('seq_input_queue_item'::regclass) NOT NULL,
    path character varying(50),
    barcode character varying(50),
    ingested boolean
);


ALTER TABLE meditor.input_queue_item OWNER TO meditor;


CREATE SEQUENCE seq_input_queue_item_name
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE meditor.seq_input_queue_item_name OWNER TO meditor;

SELECT pg_catalog.setval('seq_input_queue_item_name', 1, true);

CREATE TABLE input_queue_item_name (
    id integer DEFAULT nextval('seq_input_queue_item_name'::regclass) NOT NULL,
    path character varying(50),
    name character varying(300)
);


ALTER TABLE meditor.input_queue_item_name OWNER TO meditor;

CREATE SEQUENCE seq_lock
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE meditor.seq_lock OWNER TO meditor;

SELECT pg_catalog.setval('seq_lock', 78, true);

CREATE TABLE lock (
    id integer DEFAULT nextval('seq_lock'::regclass) NOT NULL,
    uuid character varying(45),
    description character varying(16000),
    modified timestamp without time zone,
    user_id integer
);

ALTER TABLE meditor.lock OWNER TO meditor;

CREATE SEQUENCE seq_open_id_identity
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE meditor.seq_open_id_identity OWNER TO meditor;

SELECT pg_catalog.setval('seq_open_id_identity', 48, true);

CREATE TABLE open_id_identity (
    id integer DEFAULT nextval('seq_open_id_identity'::regclass) NOT NULL,
    user_id integer,
    identity character varying(100)
);


ALTER TABLE meditor.open_id_identity OWNER TO meditor;

CREATE SEQUENCE seq_recently_modified_item
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE meditor.seq_recently_modified_item OWNER TO meditor;

SELECT pg_catalog.setval('seq_recently_modified_item', 1034, true);

CREATE TABLE recently_modified_item (
    id integer DEFAULT nextval('seq_recently_modified_item'::regclass) NOT NULL,
    uuid character varying(45),
    name character varying(300),
    description character varying(16000),
    modified timestamp without time zone,
    model integer,
    user_id integer
);


ALTER TABLE meditor.recently_modified_item OWNER TO meditor;

CREATE SEQUENCE seq_request_for_adding
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE meditor.seq_request_for_adding OWNER TO meditor;

SELECT pg_catalog.setval('seq_request_for_adding', 33, true);

CREATE TABLE request_for_adding (
    id integer DEFAULT nextval('seq_request_for_adding'::regclass) NOT NULL,
    name character varying(100),
    identity character varying(100),
    modified timestamp without time zone
);


ALTER TABLE meditor.request_for_adding OWNER TO meditor;

CREATE SEQUENCE seq_role
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE meditor.seq_role OWNER TO meditor;

SELECT pg_catalog.setval('seq_role', 14, true);

CREATE TABLE role (
    id integer DEFAULT nextval('seq_role'::regclass) NOT NULL,
    description character varying(80),
    name character varying(30)
);


ALTER TABLE meditor.role OWNER TO meditor;

CREATE SEQUENCE seq_stored_files
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE meditor.seq_stored_files OWNER TO meditor;

SELECT pg_catalog.setval('seq_stored_files', 1, true);

CREATE SEQUENCE seq_user_in_role
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

ALTER TABLE meditor.seq_user_in_role OWNER TO meditor;

SELECT pg_catalog.setval('seq_user_in_role', 31, true);

CREATE TABLE stored_files (
    id integer DEFAULT nextval('seq_stored_files'::regclass) NOT NULL,
    user_id integer,
    uuid character varying(45),
    model integer,
    description character varying(16000),
    stored timestamp without time zone,
    file_name character varying(300)
);

ALTER TABLE meditor.stored_files OWNER TO meditor;

CREATE TABLE user_in_role (
    id integer DEFAULT nextval('seq_user_in_role'::regclass) NOT NULL,
    user_id integer,
    role_id integer,
    date timestamp without time zone
);

ALTER TABLE meditor.user_in_role OWNER TO meditor;

CREATE TABLE version (
    id integer NOT NULL,
    version integer
);

ALTER TABLE meditor.version OWNER TO meditor;

INSERT INTO description VALUES (1, 'ca4c04d0-4904-11de-9fdc-000d606f5dc6', 'Common description<br>');
INSERT INTO description VALUES (2, '5fe0b160-62d5-11dd-bdc7-000d606f5dc6', '<div style="color: rgb(51, 51, 51); "><br></div><div style="color: rgb(51, 51, 51); "><br></div><div><font class="Apple-style-span" color="#800000">Je treba udelat todlenc a pak tamtononc.</font></div>');
INSERT INTO description VALUES (3, 'uuid:7d66a166-8623-11e0-8f4c-0050569d679d', 'Toto periodikum už v K4 je. Takže je možné tohle smazat.');
INSERT INTO description VALUES (4, 'uuid:04281a60-6330-11dd-ab5f-000d606f5dc6', 'sdfasdf');
INSERT INTO description VALUES (5, 'uuid:047e0290-6330-11dd-aa0c-000d606f5dc6', 'ahoj');


--
-- Data for Name: editor_user; Type: TABLE DATA; Schema: meditor; Owner: meditor
--

INSERT INTO editor_user VALUES (57, 'Václav', 'Rosecký', true);
INSERT INTO editor_user VALUES (58, 'Pepa', 'Zdepa', true);
INSERT INTO editor_user VALUES (1, 'Jiří', 'Kremser', true);
INSERT INTO editor_user VALUES (60, 'Franta', 'Běžný Uživatel', false);
INSERT INTO editor_user VALUES (61, 'Martin', 'Rehanek', false);
INSERT INTO editor_user VALUES (62, 'Violka', 'Kucerovic', false);
INSERT INTO editor_user VALUES (63, 'Pavla', 'Svastova', false);
INSERT INTO editor_user VALUES (64, 'hiep', 'vannguyen', false);
INSERT INTO editor_user VALUES (65, 'Jimmy', 'O''Regan', false);
INSERT INTO editor_user VALUES (66, 'Tomáš', 'Prachař', false);
INSERT INTO editor_user VALUES (68, 'Matous', 'Jobanek', false);
INSERT INTO editor_user VALUES (69, 'Premysl', 'Bar', false);
INSERT INTO editor_user VALUES (70, 'Eva', 'Merinska', false);
INSERT INTO editor_user VALUES (71, 'Vaclav', 'Jansa', false);
INSERT INTO editor_user VALUES (72, 'Ludek', 'Nemec', false);
INSERT INTO editor_user VALUES (73, 'Linda', 'Skolkova', false);
INSERT INTO editor_user VALUES (74, 'Petr', 'Zabicka', false);
INSERT INTO editor_user VALUES (75, 'Pavel', 'Kocourek', false);
INSERT INTO editor_user VALUES (77, 'Ivan', 'Chappel', false);
INSERT INTO editor_user VALUES (79, 'Leos', 'Junek', false);
INSERT INTO editor_user VALUES (80, 'Blanka', 'Sapáková', false);
INSERT INTO editor_user VALUES (81, 'Pavel', 'Pesta', false);
INSERT INTO editor_user VALUES (59, 'Martin', 'Rumanek', false);


--
-- Data for Name: image; Type: TABLE DATA; Schema: meditor; Owner: meditor
--

INSERT INTO image VALUES (17925, '6795da7f-6111-398d-ad7c-736cf9fb5946', '2012-01-17 12:16:36.588041', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0005.jpg', '/home/job/.meditor/images/6795da7f-6111-398d-ad7c-736cf9fb5946.jp2');
INSERT INTO image VALUES (17932, 'a892f658-22ed-3cd3-abb0-7b0a750668e7', '2012-01-17 12:16:36.658655', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0012.jpg', '/home/job/.meditor/images/a892f658-22ed-3cd3-abb0-7b0a750668e7.jp2');
INSERT INTO image VALUES (17939, '04a698e4-66e5-3337-9a0d-c1725aa0261f', '2012-01-17 12:16:36.737277', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0019.jpg', '/home/job/.meditor/images/04a698e4-66e5-3337-9a0d-c1725aa0261f.jp2');
INSERT INTO image VALUES (17945, 'cddccc20-1065-3226-ab6d-8c63148b53fb', '2012-01-17 12:16:36.786436', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0025.jpg', '/home/job/.meditor/images/cddccc20-1065-3226-ab6d-8c63148b53fb.jp2');
INSERT INTO image VALUES (17951, 'a2e880e9-7102-3e2d-b6d5-d4d543c9fb21', '2012-01-17 12:16:36.842577', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0031.jpg', '/home/job/.meditor/images/a2e880e9-7102-3e2d-b6d5-d4d543c9fb21.jp2');
INSERT INTO image VALUES (17956, 'c904d358-f814-3a83-af1e-cfd9b48e661e', '2012-01-17 12:16:36.905253', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0036.jpg', '/home/job/.meditor/images/c904d358-f814-3a83-af1e-cfd9b48e661e.jp2');
INSERT INTO image VALUES (17961, '4eaca001-1eda-33c5-a44d-ef4905390fbb', '2012-01-17 12:16:36.953106', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0041.jpg', '/home/job/.meditor/images/4eaca001-1eda-33c5-a44d-ef4905390fbb.jp2');
INSERT INTO image VALUES (17966, 'f50aacad-90c5-3151-8d20-02e0ca7c03af', '2012-01-17 12:16:37.026251', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0046.jpg', '/home/job/.meditor/images/f50aacad-90c5-3151-8d20-02e0ca7c03af.jp2');
INSERT INTO image VALUES (17973, '3289f487-6a9f-3efa-be15-10ec78eceb91', '2012-01-17 10:48:54.327362', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0001.JPG', '/home/job/.meditor/images/3289f487-6a9f-3efa-be15-10ec78eceb91.jp2');
INSERT INTO image VALUES (17979, 'ab09845b-9d31-3536-8647-869b61fcd204', '2012-01-17 10:48:54.397763', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0007.JPG', '/home/job/.meditor/images/ab09845b-9d31-3536-8647-869b61fcd204.jp2');
INSERT INTO image VALUES (17985, '4306f9fa-aa1b-3507-89f9-78e5cbf8b847', '2012-01-17 10:48:54.460907', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0013.JPG', '/home/job/.meditor/images/4306f9fa-aa1b-3507-89f9-78e5cbf8b847.jp2');
INSERT INTO image VALUES (17974, 'bbb62716-9be0-3da4-8b32-7c100c6f4f1e', '2012-01-17 10:48:54.336289', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0002.JPG', '/home/job/.meditor/images/bbb62716-9be0-3da4-8b32-7c100c6f4f1e.jp2');
INSERT INTO image VALUES (17980, '3213ae6d-38e2-35a8-b0c3-00696c98b6c8', '2012-01-17 10:48:54.406406', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0008.JPG', '/home/job/.meditor/images/3213ae6d-38e2-35a8-b0c3-00696c98b6c8.jp2');
INSERT INTO image VALUES (17986, 'ac023e5e-f790-3451-9ef7-629c01277ada', '2012-01-17 10:48:54.472707', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0014.JPG', '/home/job/.meditor/images/ac023e5e-f790-3451-9ef7-629c01277ada.jp2');
INSERT INTO image VALUES (17991, 'a2d52fe9-7985-3ba6-9b7d-3d983c9c83f6', '2012-01-17 10:48:54.528914', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0019.JPG', '/home/job/.meditor/images/a2d52fe9-7985-3ba6-9b7d-3d983c9c83f6.jp2');
INSERT INTO image VALUES (17993, '738a3126-77f6-35be-827f-b09accaad4f1', '2012-01-17 10:48:54.544799', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0021.JPG', '/home/job/.meditor/images/738a3126-77f6-35be-827f-b09accaad4f1.jp2');
INSERT INTO image VALUES (18001, '44266293-40e8-3b3a-87ae-e98a00232537', '2012-01-17 10:48:54.640904', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0029.JPG', '/home/job/.meditor/images/44266293-40e8-3b3a-87ae-e98a00232537.jp2');
INSERT INTO image VALUES (18002, 'aa27f99f-3152-3901-9200-333d951fa561', '2012-01-17 10:48:54.656814', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0030.JPG', '/home/job/.meditor/images/aa27f99f-3152-3901-9200-333d951fa561.jp2');
INSERT INTO image VALUES (18011, '2a21251b-f216-3b89-be2d-793febe2d785', '2012-01-17 10:48:54.78552', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0039.JPG', '/home/job/.meditor/images/2a21251b-f216-3b89-be2d-793febe2d785.jp2');
INSERT INTO image VALUES (18016, '9425d415-36f2-37b9-b013-cccb16bdeee3', '2012-01-17 10:48:54.840105', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0044.JPG', '/home/job/.meditor/images/9425d415-36f2-37b9-b013-cccb16bdeee3.jp2');
INSERT INTO image VALUES (18020, 'b4caf10a-9303-36e1-adbc-f1618edd3a46', '2012-01-17 10:48:54.887808', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0048.JPG', '/home/job/.meditor/images/b4caf10a-9303-36e1-adbc-f1618edd3a46.jp2');
INSERT INTO image VALUES (18022, '22945493-8327-325c-96b3-f923a1d0c021', '2012-01-17 10:48:54.907655', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0050.JPG', '/home/job/.meditor/images/22945493-8327-325c-96b3-f923a1d0c021.jp2');
INSERT INTO image VALUES (18027, '856d89a4-75b8-36dc-b9d0-ee947065ce42', '2012-01-17 10:48:54.961498', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0055.JPG', '/home/job/.meditor/images/856d89a4-75b8-36dc-b9d0-ee947065ce42.jp2');
INSERT INTO image VALUES (18035, '3ebf7afb-11ba-3970-94d5-9f24d5e0e990', '2012-01-17 10:48:55.083554', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0063.JPG', '/home/job/.meditor/images/3ebf7afb-11ba-3970-94d5-9f24d5e0e990.jp2');
INSERT INTO image VALUES (18039, '2792a2df-fb82-3d65-9ac2-7811aa9b1e3e', '2012-01-17 10:48:55.147687', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0067.JPG', '/home/job/.meditor/images/2792a2df-fb82-3d65-9ac2-7811aa9b1e3e.jp2');
INSERT INTO image VALUES (18042, '0fca0ef6-5c1a-3d9f-9760-674805f4a629', '2012-01-17 10:48:55.182904', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0070.JPG', '/home/job/.meditor/images/0fca0ef6-5c1a-3d9f-9760-674805f4a629.jp2');
INSERT INTO image VALUES (18045, '7bfd504f-314a-39f3-8a4b-30de85d5e62d', '2012-01-17 10:48:55.209898', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0073.JPG', '/home/job/.meditor/images/7bfd504f-314a-39f3-8a4b-30de85d5e62d.jp2');
INSERT INTO image VALUES (18048, 'c7d10fa7-3dff-3872-9793-efe7fdc17c3a', '2012-01-17 10:48:55.241977', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0076.JPG', '/home/job/.meditor/images/c7d10fa7-3dff-3872-9793-efe7fdc17c3a.jp2');
INSERT INTO image VALUES (18051, 'c4988bc7-3be3-35ba-8c0a-64d31a6d8bfd', '2012-01-17 10:48:55.289198', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0079.JPG', '/home/job/.meditor/images/c4988bc7-3be3-35ba-8c0a-64d31a6d8bfd.jp2');
INSERT INTO image VALUES (18052, 'aee61305-4c40-3d4f-9e91-be6cfa0d4ae6', '2012-01-17 10:48:55.299224', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0080.JPG', '/home/job/.meditor/images/aee61305-4c40-3d4f-9e91-be6cfa0d4ae6.jp2');
INSERT INTO image VALUES (18057, '902b3b8c-cab4-3929-8578-9f87b5a978ed', '2012-01-17 10:48:55.382047', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0085.JPG', '/home/job/.meditor/images/902b3b8c-cab4-3929-8578-9f87b5a978ed.jp2');
INSERT INTO image VALUES (18060, '89601092-6796-3496-947d-c7160a3f82c8', '2012-01-17 10:48:55.422285', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0088.JPG', '/home/job/.meditor/images/89601092-6796-3496-947d-c7160a3f82c8.jp2');
INSERT INTO image VALUES (18063, '641a953d-53e9-3ded-93db-f70d5d05ae48', '2012-01-17 10:48:55.455109', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0091.JPG', '/home/job/.meditor/images/641a953d-53e9-3ded-93db-f70d5d05ae48.jp2');
INSERT INTO image VALUES (18065, '220bcf36-eaf4-3668-91a2-ca84366d32d8', '2012-01-17 10:48:55.48607', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0093.JPG', '/home/job/.meditor/images/220bcf36-eaf4-3668-91a2-ca84366d32d8.jp2');
INSERT INTO image VALUES (18067, 'b34df10f-3a02-34a1-a54a-913d8ae0ec49', '2012-01-17 10:48:55.508087', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0095.JPG', '/home/job/.meditor/images/b34df10f-3a02-34a1-a54a-913d8ae0ec49.jp2');
INSERT INTO image VALUES (18069, 'fb7c9fc5-a765-376c-84cf-dbc3dfb50167', '2012-01-17 10:48:55.534349', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0097.JPG', '/home/job/.meditor/images/fb7c9fc5-a765-376c-84cf-dbc3dfb50167.jp2');
INSERT INTO image VALUES (18071, '2937476f-b6d1-3bca-a829-0f23e7d36b34', '2012-01-17 10:48:55.55518', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0099.JPG', '/home/job/.meditor/images/2937476f-b6d1-3bca-a829-0f23e7d36b34.jp2');
INSERT INTO image VALUES (17926, 'baf9be13-b1c7-3d72-ae12-4bd10e29df80', '2012-01-17 12:16:36.595743', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0006.jpg', '/home/job/.meditor/images/baf9be13-b1c7-3d72-ae12-4bd10e29df80.jp2');
INSERT INTO image VALUES (17933, 'ce23b432-127c-306d-bcfb-faf151cba145', '2012-01-17 12:16:36.66654', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0013.jpg', '/home/job/.meditor/images/ce23b432-127c-306d-bcfb-faf151cba145.jp2');
INSERT INTO image VALUES (17940, '584f94c0-3587-3600-aea5-789a0a0fca21', '2012-01-17 12:16:36.745932', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0020.jpg', '/home/job/.meditor/images/584f94c0-3587-3600-aea5-789a0a0fca21.jp2');
INSERT INTO image VALUES (17946, 'a4a336f3-07a6-364e-9b51-20abee0c154d', '2012-01-17 12:16:36.801636', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0026.jpg', '/home/job/.meditor/images/a4a336f3-07a6-364e-9b51-20abee0c154d.jp2');
INSERT INTO image VALUES (17952, '8af398e0-e42d-3eca-86f3-f343f406e948', '2012-01-17 12:16:36.852399', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0032.jpg', '/home/job/.meditor/images/8af398e0-e42d-3eca-86f3-f343f406e948.jp2');
INSERT INTO image VALUES (17957, '56df79b2-bc6b-33de-ac21-27a01f2a99ae', '2012-01-17 12:16:36.913441', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0037.jpg', '/home/job/.meditor/images/56df79b2-bc6b-33de-ac21-27a01f2a99ae.jp2');
INSERT INTO image VALUES (17962, '2ef61885-6590-3a85-bdda-a01a5c32a404', '2012-01-17 12:16:36.969136', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0042.jpg', '/home/job/.meditor/images/2ef61885-6590-3a85-bdda-a01a5c32a404.jp2');
INSERT INTO image VALUES (17967, 'e328f584-cbc9-3fbc-9658-ceac4a2ba097', '2012-01-17 12:16:37.036864', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0047.jpg', '/home/job/.meditor/images/e328f584-cbc9-3fbc-9658-ceac4a2ba097.jp2');
INSERT INTO image VALUES (18073, 'a7d8e27c-5b5a-3bbe-8d18-32e94ff6aacf', '2012-01-17 12:15:29.383434', '/home/job/.meditor/input/periodical/000258273/1234/0010.jpg', '/home/job/.meditor/images/a7d8e27c-5b5a-3bbe-8d18-32e94ff6aacf.jp2');
INSERT INTO image VALUES (18074, 'f1294c73-5b16-3bcf-8c15-bf73a161daf0', '2012-01-17 12:15:29.383434', '/home/job/.meditor/input/periodical/000258273/1234/0011.jpg', '/home/job/.meditor/images/f1294c73-5b16-3bcf-8c15-bf73a161daf0.jp2');
INSERT INTO image VALUES (17975, 'a921b792-12c4-3228-a920-d7cb13ea19e5', '2012-01-17 10:48:54.350422', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0003.JPG', '/home/job/.meditor/images/a921b792-12c4-3228-a920-d7cb13ea19e5.jp2');
INSERT INTO image VALUES (17981, 'a3ad5f4d-099f-3553-aa73-a813405820d1', '2012-01-17 10:48:54.417854', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0009.JPG', '/home/job/.meditor/images/a3ad5f4d-099f-3553-aa73-a813405820d1.jp2');
INSERT INTO image VALUES (17987, '161d7e4f-7b55-3929-b438-f33cf504bb02', '2012-01-17 10:48:54.485587', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0015.JPG', '/home/job/.meditor/images/161d7e4f-7b55-3929-b438-f33cf504bb02.jp2');
INSERT INTO image VALUES (17992, '127fa17c-9682-3682-8512-10bbdefdaef8', '2012-01-17 10:48:54.536901', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0020.JPG', '/home/job/.meditor/images/127fa17c-9682-3682-8512-10bbdefdaef8.jp2');
INSERT INTO image VALUES (17994, '9f2567c2-5fe0-363a-8d5e-ea6b10c6280a', '2012-01-17 10:48:54.552488', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0022.JPG', '/home/job/.meditor/images/9f2567c2-5fe0-363a-8d5e-ea6b10c6280a.jp2');
INSERT INTO image VALUES (18003, '6fa7f348-6469-329c-a28a-9c354fb3004e', '2012-01-17 10:48:54.672795', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0031.JPG', '/home/job/.meditor/images/6fa7f348-6469-329c-a28a-9c354fb3004e.jp2');
INSERT INTO image VALUES (18007, 'e285865e-110c-3fdf-bc8a-33dfe9b29752', '2012-01-17 10:48:54.728225', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0035.JPG', '/home/job/.meditor/images/e285865e-110c-3fdf-bc8a-33dfe9b29752.jp2');
INSERT INTO image VALUES (18012, '4c7affc1-3499-3f6b-8d9e-1f3f86fcdb89', '2012-01-17 10:48:54.793847', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0040.JPG', '/home/job/.meditor/images/4c7affc1-3499-3f6b-8d9e-1f3f86fcdb89.jp2');
INSERT INTO image VALUES (18017, 'd3511656-f26e-36d9-9ca8-7fd070f6f486', '2012-01-17 10:48:54.856376', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0045.JPG', '/home/job/.meditor/images/d3511656-f26e-36d9-9ca8-7fd070f6f486.jp2');
INSERT INTO image VALUES (18023, 'ff537e0b-e8aa-37ab-ac7b-66227fc18ba5', '2012-01-17 10:48:54.9166', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0051.JPG', '/home/job/.meditor/images/ff537e0b-e8aa-37ab-ac7b-66227fc18ba5.jp2');
INSERT INTO image VALUES (18028, '59bb00ba-2fb4-34f7-966e-92ff67f86967', '2012-01-17 10:48:54.973381', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0056.JPG', '/home/job/.meditor/images/59bb00ba-2fb4-34f7-966e-92ff67f86967.jp2');
INSERT INTO image VALUES (18075, '30cde3b5-14f4-3ed2-b316-4f45120678ca', '2012-01-17 12:15:29.383434', '/home/job/.meditor/input/periodical/000258273/1234/0012.jpg', '/home/job/.meditor/images/30cde3b5-14f4-3ed2-b316-4f45120678ca.jp2');
INSERT INTO image VALUES (18076, '17eeff33-547a-3346-959b-a9e212909cb6', '2012-01-17 12:15:29.383434', '/home/job/.meditor/input/periodical/000258273/1234/0013.jpg', '/home/job/.meditor/images/17eeff33-547a-3346-959b-a9e212909cb6.jp2');
INSERT INTO image VALUES (18077, '9a435b74-cc26-3c2f-8bf9-919bec19f2b9', '2012-01-17 12:15:29.383434', '/home/job/.meditor/input/periodical/000258273/1234/0014.jpg', '/home/job/.meditor/images/9a435b74-cc26-3c2f-8bf9-919bec19f2b9.jp2');
INSERT INTO image VALUES (18078, '078d8f80-4e86-3bc4-a35a-e8497d8b77a3', '2012-01-17 12:15:29.383434', '/home/job/.meditor/input/periodical/000258273/1234/0015.jpg', '/home/job/.meditor/images/078d8f80-4e86-3bc4-a35a-e8497d8b77a3.jp2');
INSERT INTO image VALUES (17976, '48d3468e-febb-3275-8f77-9bbc9237ca69', '2012-01-17 10:48:54.361959', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0004.JPG', '/home/job/.meditor/images/48d3468e-febb-3275-8f77-9bbc9237ca69.jp2');
INSERT INTO image VALUES (17982, '80db63a3-c465-3df8-91a1-c574616ee2c2', '2012-01-17 10:48:54.425336', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0010.JPG', '/home/job/.meditor/images/80db63a3-c465-3df8-91a1-c574616ee2c2.jp2');
INSERT INTO image VALUES (17988, '2cbc26d7-7de6-38ce-8980-d4e9fda24ad1', '2012-01-17 10:48:54.49419', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0016.JPG', '/home/job/.meditor/images/2cbc26d7-7de6-38ce-8980-d4e9fda24ad1.jp2');
INSERT INTO image VALUES (17995, '1e44aba1-0fa1-3fba-a2d7-73c46f7a1e6c', '2012-01-17 10:48:54.560712', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0023.JPG', '/home/job/.meditor/images/1e44aba1-0fa1-3fba-a2d7-73c46f7a1e6c.jp2');
INSERT INTO image VALUES (17998, '46b6e109-ceaf-3341-ad03-f2b7862f074a', '2012-01-17 10:48:54.606947', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0026.JPG', '/home/job/.meditor/images/46b6e109-ceaf-3341-ad03-f2b7862f074a.jp2');
INSERT INTO image VALUES (18004, '5fda058e-3a5a-3c71-ac5f-08332c7f4141', '2012-01-17 10:48:54.680679', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0032.JPG', '/home/job/.meditor/images/5fda058e-3a5a-3c71-ac5f-08332c7f4141.jp2');
INSERT INTO image VALUES (18008, '14076f94-a566-3977-90e9-e34ef67adaa7', '2012-01-17 10:48:54.744841', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0036.JPG', '/home/job/.meditor/images/14076f94-a566-3977-90e9-e34ef67adaa7.jp2');
INSERT INTO image VALUES (18013, 'f4143c47-e9d3-375a-afdb-74e3490012f1', '2012-01-17 10:48:54.808222', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0041.JPG', '/home/job/.meditor/images/f4143c47-e9d3-375a-afdb-74e3490012f1.jp2');
INSERT INTO image VALUES (18018, '04ae9e04-12d6-3936-912e-ffec313ba875', '2012-01-17 10:48:54.864898', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0046.JPG', '/home/job/.meditor/images/04ae9e04-12d6-3936-912e-ffec313ba875.jp2');
INSERT INTO image VALUES (18024, '7240ea5a-5e02-363b-a86b-33dd818333c9', '2012-01-17 10:48:54.924855', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0052.JPG', '/home/job/.meditor/images/7240ea5a-5e02-363b-a86b-33dd818333c9.jp2');
INSERT INTO image VALUES (18029, '436fc823-6e58-3e92-a0ed-5ab0783ee9dd', '2012-01-17 10:48:54.989038', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0057.JPG', '/home/job/.meditor/images/436fc823-6e58-3e92-a0ed-5ab0783ee9dd.jp2');
INSERT INTO image VALUES (18032, 'fba0d894-c679-32a8-a452-b7c8c6d80824', '2012-01-17 10:48:55.035807', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0060.JPG', '/home/job/.meditor/images/fba0d894-c679-32a8-a452-b7c8c6d80824.jp2');
INSERT INTO image VALUES (18036, '4047ff43-a344-3b9b-8e60-be31c9954115', '2012-01-17 10:48:55.09967', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0064.JPG', '/home/job/.meditor/images/4047ff43-a344-3b9b-8e60-be31c9954115.jp2');
INSERT INTO image VALUES (18079, 'c84e3c47-1e06-3f06-a90f-2799aee471ad', '2012-01-17 12:15:29.383434', '/home/job/.meditor/input/periodical/000258273/1234/0016.jpg', '/home/job/.meditor/images/c84e3c47-1e06-3f06-a90f-2799aee471ad.jp2');
INSERT INTO image VALUES (18080, '4bf4616c-f097-3fd3-9be5-1895fb8f6168', '2012-01-17 12:15:29.383434', '/home/job/.meditor/input/periodical/000258273/1234/0017.jpg', '/home/job/.meditor/images/4bf4616c-f097-3fd3-9be5-1895fb8f6168.jp2');
INSERT INTO image VALUES (18081, '74513cd1-f203-37f1-8725-c3e48c88a20a', '2012-01-17 12:15:29.383434', '/home/job/.meditor/input/periodical/000258273/1234/0018.jpg', '/home/job/.meditor/images/74513cd1-f203-37f1-8725-c3e48c88a20a.jp2');
INSERT INTO image VALUES (18082, 'e5d2a8c3-77f5-337c-87b2-998c87704e73', '2012-01-17 12:15:29.383434', '/home/job/.meditor/input/periodical/000258273/1234/0019.jpg', '/home/job/.meditor/images/e5d2a8c3-77f5-337c-87b2-998c87704e73.jp2');
INSERT INTO image VALUES (17977, '40cebbb4-df8c-3441-a2f4-d50d99829463', '2012-01-17 10:48:54.37393', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0005.JPG', '/home/job/.meditor/images/40cebbb4-df8c-3441-a2f4-d50d99829463.jp2');
INSERT INTO image VALUES (17983, 'cc32c215-2f49-3f47-8ecb-c593f0376003', '2012-01-17 10:48:54.439441', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0011.JPG', '/home/job/.meditor/images/cc32c215-2f49-3f47-8ecb-c593f0376003.jp2');
INSERT INTO image VALUES (17989, '2f4eb400-2695-3f0e-9eb3-643a5e30ac0c', '2012-01-17 10:48:54.502883', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0017.JPG', '/home/job/.meditor/images/2f4eb400-2695-3f0e-9eb3-643a5e30ac0c.jp2');
INSERT INTO image VALUES (17996, '133cbe81-2a40-3b2d-a8f8-dd7566db9a46', '2012-01-17 10:48:54.577779', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0024.JPG', '/home/job/.meditor/images/133cbe81-2a40-3b2d-a8f8-dd7566db9a46.jp2');
INSERT INTO image VALUES (17999, 'bc782eff-0775-3dd8-a4c4-bf9c4ceb7b4f', '2012-01-17 10:48:54.614872', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0027.JPG', '/home/job/.meditor/images/bc782eff-0775-3dd8-a4c4-bf9c4ceb7b4f.jp2');
INSERT INTO image VALUES (18005, 'ec12d16a-d76d-3eb7-84d3-e1f9b7cf19c2', '2012-01-17 10:48:54.696666', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0033.JPG', '/home/job/.meditor/images/ec12d16a-d76d-3eb7-84d3-e1f9b7cf19c2.jp2');
INSERT INTO image VALUES (18009, 'a55ab64d-2710-3fea-8542-29e375fce2f9', '2012-01-17 10:48:54.760662', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0037.JPG', '/home/job/.meditor/images/a55ab64d-2710-3fea-8542-29e375fce2f9.jp2');
INSERT INTO image VALUES (18014, '98e77c3e-4960-3b54-a04c-30455aeb398d', '2012-01-17 10:48:54.81667', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0042.JPG', '/home/job/.meditor/images/98e77c3e-4960-3b54-a04c-30455aeb398d.jp2');
INSERT INTO image VALUES (18019, 'a46dd63e-e318-3028-90f5-4075866103aa', '2012-01-17 10:48:54.875591', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0047.JPG', '/home/job/.meditor/images/a46dd63e-e318-3028-90f5-4075866103aa.jp2');
INSERT INTO image VALUES (18025, 'd8b53049-bb5d-3224-afb4-879b2698aba4', '2012-01-17 10:48:54.944309', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0053.JPG', '/home/job/.meditor/images/d8b53049-bb5d-3224-afb4-879b2698aba4.jp2');
INSERT INTO image VALUES (18030, 'b7f878ea-f1f9-3c52-b0a6-fbb1cde91cc5', '2012-01-17 10:48:55.003558', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0058.JPG', '/home/job/.meditor/images/b7f878ea-f1f9-3c52-b0a6-fbb1cde91cc5.jp2');
INSERT INTO image VALUES (18033, '28721e8b-98f6-3ccd-9864-42b04756e9d7', '2012-01-17 10:48:55.051514', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0061.JPG', '/home/job/.meditor/images/28721e8b-98f6-3ccd-9864-42b04756e9d7.jp2');
INSERT INTO image VALUES (18037, '2d8965c1-c5b2-34ae-884a-c2f6dcf4ea93', '2012-01-17 10:48:55.115444', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0065.JPG', '/home/job/.meditor/images/2d8965c1-c5b2-34ae-884a-c2f6dcf4ea93.jp2');
INSERT INTO image VALUES (18040, 'cb0cb3f0-6f6a-3608-8936-236e224641d4', '2012-01-17 10:48:55.159095', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0068.JPG', '/home/job/.meditor/images/cb0cb3f0-6f6a-3608-8936-236e224641d4.jp2');
INSERT INTO image VALUES (18043, '6da5d234-8336-35af-a0b1-94d6254cc10c', '2012-01-17 10:48:55.191348', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0071.JPG', '/home/job/.meditor/images/6da5d234-8336-35af-a0b1-94d6254cc10c.jp2');
INSERT INTO image VALUES (18046, 'eadb4de3-b839-3708-a16d-146aa1f9ea72', '2012-01-17 10:48:55.21908', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0074.JPG', '/home/job/.meditor/images/eadb4de3-b839-3708-a16d-146aa1f9ea72.jp2');
INSERT INTO image VALUES (18049, 'df28b31d-16d5-3e3a-a0e6-057b460c253c', '2012-01-17 10:48:55.250245', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0077.JPG', '/home/job/.meditor/images/df28b31d-16d5-3e3a-a0e6-057b460c253c.jp2');
INSERT INTO image VALUES (18053, 'b6cec433-bd23-3ba8-8df0-37c2f7cb7f89', '2012-01-17 10:48:55.324389', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0081.JPG', '/home/job/.meditor/images/b6cec433-bd23-3ba8-8df0-37c2f7cb7f89.jp2');
INSERT INTO image VALUES (18055, '2d292ceb-fad4-31f6-8fe1-9f8ea53d88e4', '2012-01-17 10:48:55.350883', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0083.JPG', '/home/job/.meditor/images/2d292ceb-fad4-31f6-8fe1-9f8ea53d88e4.jp2');
INSERT INTO image VALUES (18058, '61158a77-ef26-3ef0-8c61-d53d8920dadb', '2012-01-17 10:48:55.399041', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0086.JPG', '/home/job/.meditor/images/61158a77-ef26-3ef0-8c61-d53d8920dadb.jp2');
INSERT INTO image VALUES (18061, '620ddaa8-8878-38e5-a7b7-5deb1cdbc003', '2012-01-17 10:48:55.431273', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0089.JPG', '/home/job/.meditor/images/620ddaa8-8878-38e5-a7b7-5deb1cdbc003.jp2');
INSERT INTO image VALUES (18064, 'd77fc5d2-706c-3d5d-85d8-a3fd5a2a860e', '2012-01-17 10:48:55.476062', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0092.JPG', '/home/job/.meditor/images/d77fc5d2-706c-3d5d-85d8-a3fd5a2a860e.jp2');
INSERT INTO image VALUES (18066, '586e2a5e-a9ef-37cd-9f94-c46ab27f3d2c', '2012-01-17 10:48:55.497439', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0094.JPG', '/home/job/.meditor/images/586e2a5e-a9ef-37cd-9f94-c46ab27f3d2c.jp2');
INSERT INTO image VALUES (18068, '4b4f6e33-97b3-3f38-9408-a85295db86a2', '2012-01-17 10:48:55.518553', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0096.JPG', '/home/job/.meditor/images/4b4f6e33-97b3-3f38-9408-a85295db86a2.jp2');
INSERT INTO image VALUES (18070, '71c4494e-bd43-3a4e-921f-f43fea93f5d8', '2012-01-17 10:48:55.54631', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0098.JPG', '/home/job/.meditor/images/71c4494e-bd43-3a4e-921f-f43fea93f5d8.jp2');
INSERT INTO image VALUES (18083, 'f6b5df7a-5f4c-37fe-938d-564857454c13', '2012-01-17 12:15:29.383434', '/home/job/.meditor/input/periodical/000258273/1234/0020.jpg', '/home/job/.meditor/images/f6b5df7a-5f4c-37fe-938d-564857454c13.jp2');
INSERT INTO image VALUES (18084, 'f1b9af49-457b-30fe-9b71-7ecf18352208', '2012-01-17 12:15:29.383434', '/home/job/.meditor/input/periodical/000258273/1234/0021.jpg', '/home/job/.meditor/images/f1b9af49-457b-30fe-9b71-7ecf18352208.jp2');
INSERT INTO image VALUES (18085, '3272737a-9a7a-3e75-9ecd-f6c0b070b372', '2012-01-17 12:15:29.383434', '/home/job/.meditor/input/periodical/000258273/1234/0022.jpg', '/home/job/.meditor/images/3272737a-9a7a-3e75-9ecd-f6c0b070b372.jp2');
INSERT INTO image VALUES (18086, '0b2fc5a2-559b-352d-8e89-190a921bc7be', '2012-01-17 12:15:29.383434', '/home/job/.meditor/input/periodical/000258273/1234/0023.jpg', '/home/job/.meditor/images/0b2fc5a2-559b-352d-8e89-190a921bc7be.jp2');
INSERT INTO image VALUES (18087, '9e32d5c4-34cc-3aed-853f-72ba893b258b', '2012-01-17 12:15:29.383434', '/home/job/.meditor/input/periodical/000258273/1234/0024.jpg', '/home/job/.meditor/images/9e32d5c4-34cc-3aed-853f-72ba893b258b.jp2');
INSERT INTO image VALUES (18088, 'f2653678-d3cf-3cde-8673-fe9387039230', '2012-01-17 12:15:29.383434', '/home/job/.meditor/input/periodical/000258273/1234/0025.jpg', '/home/job/.meditor/images/f2653678-d3cf-3cde-8673-fe9387039230.jp2');
INSERT INTO image VALUES (18089, '86e8c0eb-dffa-3f54-8f53-5bded1d078e6', '2012-01-17 12:15:29.383434', '/home/job/.meditor/input/periodical/000258273/1234/0026.jpg', '/home/job/.meditor/images/86e8c0eb-dffa-3f54-8f53-5bded1d078e6.jp2');
INSERT INTO image VALUES (18090, '5ed41931-ff21-339d-8103-3e30658f2771', '2012-01-17 12:15:29.383434', '/home/job/.meditor/input/periodical/000258273/1234/0027.jpg', '/home/job/.meditor/images/5ed41931-ff21-339d-8103-3e30658f2771.jp2');
INSERT INTO image VALUES (18091, 'c675a89d-2e57-3494-8825-3539e0961c1c', '2012-01-17 12:15:29.383434', '/home/job/.meditor/input/periodical/000258273/1234/0028.jpg', '/home/job/.meditor/images/c675a89d-2e57-3494-8825-3539e0961c1c.jp2');
INSERT INTO image VALUES (18092, 'a1bb9473-fcb6-3c62-84ed-8786e0436220', '2012-01-17 12:15:29.383434', '/home/job/.meditor/input/periodical/000258273/1234/0029.jpg', '/home/job/.meditor/images/a1bb9473-fcb6-3c62-84ed-8786e0436220.jp2');
INSERT INTO image VALUES (17927, '593db23b-e0b3-3686-89ca-dd19f0d0163e', '2012-01-17 12:16:36.610158', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0007.jpg', '/home/job/.meditor/images/593db23b-e0b3-3686-89ca-dd19f0d0163e.jp2');
INSERT INTO image VALUES (17934, '3fffeb99-44e4-32ff-ab7f-968bdb191e8e', '2012-01-17 12:16:36.682346', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0014.jpg', '/home/job/.meditor/images/3fffeb99-44e4-32ff-ab7f-968bdb191e8e.jp2');
INSERT INTO image VALUES (17978, '1257ac27-bda3-31ea-9434-a7d20ed1d853', '2012-01-17 10:48:54.386405', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0006.JPG', '/home/job/.meditor/images/1257ac27-bda3-31ea-9434-a7d20ed1d853.jp2');
INSERT INTO image VALUES (17984, '506821ab-fdd4-3ade-8b62-ff681f1f09ea', '2012-01-17 10:48:54.452906', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0012.JPG', '/home/job/.meditor/images/506821ab-fdd4-3ade-8b62-ff681f1f09ea.jp2');
INSERT INTO image VALUES (17990, '1217b5ca-aedb-30b4-ab12-9805aa9242a1', '2012-01-17 10:48:54.51309', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0018.JPG', '/home/job/.meditor/images/1217b5ca-aedb-30b4-ab12-9805aa9242a1.jp2');
INSERT INTO image VALUES (17997, '8a341e26-3fc4-33eb-b709-0a0916c9fd37', '2012-01-17 10:48:54.594405', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0025.JPG', '/home/job/.meditor/images/8a341e26-3fc4-33eb-b709-0a0916c9fd37.jp2');
INSERT INTO image VALUES (18000, '63e3c762-59b9-308e-b6a7-50d245c0dd69', '2012-01-17 10:48:54.631492', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0028.JPG', '/home/job/.meditor/images/63e3c762-59b9-308e-b6a7-50d245c0dd69.jp2');
INSERT INTO image VALUES (18006, 'dab03798-3757-3ef0-aab4-eaa9d9348d7e', '2012-01-17 10:48:54.713144', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0034.JPG', '/home/job/.meditor/images/dab03798-3757-3ef0-aab4-eaa9d9348d7e.jp2');
INSERT INTO image VALUES (18010, 'b718552f-e63f-3483-9874-b9b308344dd4', '2012-01-17 10:48:54.769166', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0038.JPG', '/home/job/.meditor/images/b718552f-e63f-3483-9874-b9b308344dd4.jp2');
INSERT INTO image VALUES (18015, 'cbfc4567-6657-3f33-8d7e-91f75fa25729', '2012-01-17 10:48:54.825802', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0043.JPG', '/home/job/.meditor/images/cbfc4567-6657-3f33-8d7e-91f75fa25729.jp2');
INSERT INTO image VALUES (18021, '4d6c16ea-ce4d-3dbc-8f57-6e1f2ef1de27', '2012-01-17 10:48:54.89568', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0049.JPG', '/home/job/.meditor/images/4d6c16ea-ce4d-3dbc-8f57-6e1f2ef1de27.jp2');
INSERT INTO image VALUES (18026, 'fb753b36-226e-3dad-81f0-99aad283b4d9', '2012-01-17 10:48:54.953655', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0054.JPG', '/home/job/.meditor/images/fb753b36-226e-3dad-81f0-99aad283b4d9.jp2');
INSERT INTO image VALUES (18031, 'b3ac9ecc-ab8d-3dd2-ac25-8d54099500c8', '2012-01-17 10:48:55.02053', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0059.JPG', '/home/job/.meditor/images/b3ac9ecc-ab8d-3dd2-ac25-8d54099500c8.jp2');
INSERT INTO image VALUES (18034, 'e31efeee-9c87-3376-a23d-c6dce3d25214', '2012-01-17 10:48:55.0677', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0062.JPG', '/home/job/.meditor/images/e31efeee-9c87-3376-a23d-c6dce3d25214.jp2');
INSERT INTO image VALUES (18038, 'd8a7c4cf-0814-333b-81f8-9badd10ed0bc', '2012-01-17 10:48:55.131332', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0066.JPG', '/home/job/.meditor/images/d8a7c4cf-0814-333b-81f8-9badd10ed0bc.jp2');
INSERT INTO image VALUES (18041, 'bc1eeac1-6dc8-3162-a88b-c44ece5dbdec', '2012-01-17 10:48:55.174362', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0069.JPG', '/home/job/.meditor/images/bc1eeac1-6dc8-3162-a88b-c44ece5dbdec.jp2');
INSERT INTO image VALUES (18044, '87b45721-1628-3ef1-b8f2-ec56d39a2ff8', '2012-01-17 10:48:55.20086', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0072.JPG', '/home/job/.meditor/images/87b45721-1628-3ef1-b8f2-ec56d39a2ff8.jp2');
INSERT INTO image VALUES (18047, 'a8195212-b957-3283-9cde-542048b2ba5e', '2012-01-17 10:48:55.228008', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0075.JPG', '/home/job/.meditor/images/a8195212-b957-3283-9cde-542048b2ba5e.jp2');
INSERT INTO image VALUES (18050, 'd461a8ba-d9f2-3fd6-8324-b72d2c2b8597', '2012-01-17 10:48:55.259916', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0078.JPG', '/home/job/.meditor/images/d461a8ba-d9f2-3fd6-8324-b72d2c2b8597.jp2');
INSERT INTO image VALUES (18054, '264b4191-d416-3a05-a461-fe70c3547304', '2012-01-17 10:48:55.33473', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0082.JPG', '/home/job/.meditor/images/264b4191-d416-3a05-a461-fe70c3547304.jp2');
INSERT INTO image VALUES (18056, '832d9684-7480-3904-a891-45a16368ff8f', '2012-01-17 10:48:55.366335', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0084.JPG', '/home/job/.meditor/images/832d9684-7480-3904-a891-45a16368ff8f.jp2');
INSERT INTO image VALUES (18059, '12a34a97-6fe1-315b-98b7-395c328f890f', '2012-01-17 10:48:55.409362', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0087.JPG', '/home/job/.meditor/images/12a34a97-6fe1-315b-98b7-395c328f890f.jp2');
INSERT INTO image VALUES (18062, '6644f48e-c7ad-3100-b1b9-b30bfc8ad243', '2012-01-17 10:48:55.440348', '/home/job/.meditor/input/periodical/000000217/51-1942-0/0090.JPG', '/home/job/.meditor/images/6644f48e-c7ad-3100-b1b9-b30bfc8ad243.jp2');
INSERT INTO image VALUES (17921, '925b6c08-6383-3411-b1f4-e2f94d207622', '2012-01-17 12:16:36.552808', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0001.jpg', '/home/job/.meditor/images/925b6c08-6383-3411-b1f4-e2f94d207622.jp2');
INSERT INTO image VALUES (17928, '944da24d-0234-3f98-86f5-85b480c922f2', '2012-01-17 12:16:36.617572', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0008.jpg', '/home/job/.meditor/images/944da24d-0234-3f98-86f5-85b480c922f2.jp2');
INSERT INTO image VALUES (17935, '842f5da3-6546-3929-b85c-b2df0e5b7537', '2012-01-17 12:16:36.698722', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0015.jpg', '/home/job/.meditor/images/842f5da3-6546-3929-b85c-b2df0e5b7537.jp2');
INSERT INTO image VALUES (17941, '62141f75-e1d6-37f4-9470-865b0d7ea59f', '2012-01-17 12:16:36.753737', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0021.jpg', '/home/job/.meditor/images/62141f75-e1d6-37f4-9470-865b0d7ea59f.jp2');
INSERT INTO image VALUES (17947, '03518b70-1193-3bb7-8513-1ced923bd259', '2012-01-17 12:16:36.808893', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0027.jpg', '/home/job/.meditor/images/03518b70-1193-3bb7-8513-1ced923bd259.jp2');
INSERT INTO image VALUES (17922, '97fd3d0b-967a-329a-8e61-c9e968bce493', '2012-01-17 12:16:36.564215', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0002.jpg', '/home/job/.meditor/images/97fd3d0b-967a-329a-8e61-c9e968bce493.jp2');
INSERT INTO image VALUES (17929, 'c4120a35-4645-379e-bf37-aa0b8409073a', '2012-01-17 12:16:36.626262', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0009.jpg', '/home/job/.meditor/images/c4120a35-4645-379e-bf37-aa0b8409073a.jp2');
INSERT INTO image VALUES (17936, '6c6ec55d-8e69-3709-b08c-ce2479dedf0d', '2012-01-17 12:16:36.713951', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0016.jpg', '/home/job/.meditor/images/6c6ec55d-8e69-3709-b08c-ce2479dedf0d.jp2');
INSERT INTO image VALUES (17942, 'd086b6c3-5e19-3ec5-8a3f-090c04df099d', '2012-01-17 12:16:36.761761', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0022.jpg', '/home/job/.meditor/images/d086b6c3-5e19-3ec5-8a3f-090c04df099d.jp2');
INSERT INTO image VALUES (17948, 'db7d3337-4e9f-3fa9-be54-62675bfc543f', '2012-01-17 12:16:36.817252', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0028.jpg', '/home/job/.meditor/images/db7d3337-4e9f-3fa9-be54-62675bfc543f.jp2');
INSERT INTO image VALUES (17953, '0fdf23bf-0ede-3639-b21c-933cd313e338', '2012-01-17 12:16:36.869248', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0033.jpg', '/home/job/.meditor/images/0fdf23bf-0ede-3639-b21c-933cd313e338.jp2');
INSERT INTO image VALUES (17958, '4e5a4703-b38c-37e5-80c7-1709ca358981', '2012-01-17 12:16:36.921084', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0038.jpg', '/home/job/.meditor/images/4e5a4703-b38c-37e5-80c7-1709ca358981.jp2');
INSERT INTO image VALUES (17963, '73ab9421-a62a-3bfc-a989-b726be64803c', '2012-01-17 12:16:36.977526', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0043.jpg', '/home/job/.meditor/images/73ab9421-a62a-3bfc-a989-b726be64803c.jp2');
INSERT INTO image VALUES (17968, '0bc28726-3521-341e-9491-e6edfc1ef7c3', '2012-01-17 12:16:37.044813', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0048.jpg', '/home/job/.meditor/images/0bc28726-3521-341e-9491-e6edfc1ef7c3.jp2');
INSERT INTO image VALUES (17923, 'e581bfd6-deaf-3dca-aba5-0411355d6803', '2012-01-17 12:16:36.572573', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0003.jpg', '/home/job/.meditor/images/e581bfd6-deaf-3dca-aba5-0411355d6803.jp2');
INSERT INTO image VALUES (17930, '20636e57-1fc5-3618-94ba-5ab80295cf38', '2012-01-17 12:16:36.634094', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0010.jpg', '/home/job/.meditor/images/20636e57-1fc5-3618-94ba-5ab80295cf38.jp2');
INSERT INTO image VALUES (17937, '11e932ef-6a84-340e-ab81-5075f55c2b23', '2012-01-17 12:16:36.721934', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0017.jpg', '/home/job/.meditor/images/11e932ef-6a84-340e-ab81-5075f55c2b23.jp2');
INSERT INTO image VALUES (17943, 'ee4094b2-4fde-3162-bc48-b4fb99cdddf5', '2012-01-17 12:16:36.76915', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0023.jpg', '/home/job/.meditor/images/ee4094b2-4fde-3162-bc48-b4fb99cdddf5.jp2');
INSERT INTO image VALUES (17949, '4adc7241-7ec4-3cc2-819a-bb3ce19e6882', '2012-01-17 12:16:36.82494', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0029.jpg', '/home/job/.meditor/images/4adc7241-7ec4-3cc2-819a-bb3ce19e6882.jp2');
INSERT INTO image VALUES (17954, '9f80a54a-0ec4-36b6-b038-0668d81aba59', '2012-01-17 12:16:36.87712', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0034.jpg', '/home/job/.meditor/images/9f80a54a-0ec4-36b6-b038-0668d81aba59.jp2');
INSERT INTO image VALUES (17959, 'dc842dfd-bbd4-3a30-aae2-6868695b8a33', '2012-01-17 12:16:36.937244', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0039.jpg', '/home/job/.meditor/images/dc842dfd-bbd4-3a30-aae2-6868695b8a33.jp2');
INSERT INTO image VALUES (17964, 'b7ddb746-fd4c-321f-a373-6e39e2c847f3', '2012-01-17 12:16:36.993319', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0044.jpg', '/home/job/.meditor/images/b7ddb746-fd4c-321f-a373-6e39e2c847f3.jp2');
INSERT INTO image VALUES (17969, 'e0420807-c4c9-392c-b472-bde5d151e54f', '2012-01-17 12:16:37.054402', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0049.jpg', '/home/job/.meditor/images/e0420807-c4c9-392c-b472-bde5d151e54f.jp2');
INSERT INTO image VALUES (17924, 'f892d171-e41c-3fa6-939b-1c9500ba0e90', '2012-01-17 12:16:36.580534', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0004.jpg', '/home/job/.meditor/images/f892d171-e41c-3fa6-939b-1c9500ba0e90.jp2');
INSERT INTO image VALUES (17931, 'f9512148-a3cb-36ab-ba88-b74e1477069f', '2012-01-17 12:16:36.642523', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0011.jpg', '/home/job/.meditor/images/f9512148-a3cb-36ab-ba88-b74e1477069f.jp2');
INSERT INTO image VALUES (17938, 'c40eb057-2dde-31b0-8c04-264997857c8d', '2012-01-17 12:16:36.729688', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0018.jpg', '/home/job/.meditor/images/c40eb057-2dde-31b0-8c04-264997857c8d.jp2');
INSERT INTO image VALUES (17944, 'b42cabbc-0f84-32e8-b718-df0aedead326', '2012-01-17 12:16:36.777612', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0024.jpg', '/home/job/.meditor/images/b42cabbc-0f84-32e8-b718-df0aedead326.jp2');
INSERT INTO image VALUES (17950, 'dd9d5bfd-c70a-30a2-b3ce-de0f0c22c0aa', '2012-01-17 12:16:36.833376', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0030.jpg', '/home/job/.meditor/images/dd9d5bfd-c70a-30a2-b3ce-de0f0c22c0aa.jp2');
INSERT INTO image VALUES (17955, 'e449c1de-0207-399b-9543-5fd976025f59', '2012-01-17 12:16:36.889292', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0035.jpg', '/home/job/.meditor/images/e449c1de-0207-399b-9543-5fd976025f59.jp2');
INSERT INTO image VALUES (17960, 'd81b2ac4-9bef-332d-bdf3-b3e88d40f0a0', '2012-01-17 12:16:36.944828', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0040.jpg', '/home/job/.meditor/images/d81b2ac4-9bef-332d-bdf3-b3e88d40f0a0.jp2');
INSERT INTO image VALUES (17965, '8638c91e-9be2-31bd-8722-f34287cbf485', '2012-01-17 12:16:37.009995', '/home/job/.meditor/input/periodical/000258273/12-1936-0/0045.jpg', '/home/job/.meditor/images/8638c91e-9be2-31bd-8722-f34287cbf485.jp2');


--
-- Data for Name: input_queue_item; Type: TABLE DATA; Schema: meditor; Owner: meditor
--

INSERT INTO input_queue_item VALUES (22282, '/periodical/000258273/1234', '1234', true);
INSERT INTO input_queue_item VALUES (22283, '/periodical/000258273/12-1936-0', '12-1936-0', true);
INSERT INTO input_queue_item VALUES (22276, '/monograph', 'monograph', false);
INSERT INTO input_queue_item VALUES (22277, '/monograph/000258273/12-1936-0', '12-1936-0', true);
INSERT INTO input_queue_item VALUES (22278, '/monograph/000258273', '000258273', true);
INSERT INTO input_queue_item VALUES (22279, '/periodical', 'periodical', false);
INSERT INTO input_queue_item VALUES (22280, '/periodical/000000217/51-1942-0', '51-1942-0', true);
INSERT INTO input_queue_item VALUES (22281, '/periodical/000000217', '000000217', true);
INSERT INTO input_queue_item VALUES (22284, '/periodical/000258273', '000258273', true);
INSERT INTO input_queue_item VALUES (22285, '/periodical/123456789/4-1912-0', '4-1912-0', false);
INSERT INTO input_queue_item VALUES (22286, '/periodical/123456789', '123456789', false);


--
-- Data for Name: lock; Type: TABLE DATA; Schema: meditor; Owner: meditor
--



--
-- Data for Name: open_id_identity; Type: TABLE DATA; Schema: meditor; Owner: meditor
--

INSERT INTO open_id_identity VALUES (16, 1, 'https://www.google.com/profiles/109255519115168093543');
INSERT INTO open_id_identity VALUES (17, 57, 'https://www.google.com/profiles/109168724441055178992');
INSERT INTO open_id_identity VALUES (19, 58, 'fantomas');
INSERT INTO open_id_identity VALUES (21, 60, 'https://www.google.com/profiles/111227031824448142223');
INSERT INTO open_id_identity VALUES (23, 61, 'https://www.google.com/profiles/102338850479877788412');
INSERT INTO open_id_identity VALUES (27, 63, 'https://www.google.com/profiles/112025888785317734498');
INSERT INTO open_id_identity VALUES (28, 64, 'https://www.google.com/profiles/108088542979033146722');
INSERT INTO open_id_identity VALUES (29, 65, 'https://www.google.com/profiles/114804369744409916883');
INSERT INTO open_id_identity VALUES (30, 66, 'https://www.google.com/profiles/106800333256714812404');
INSERT INTO open_id_identity VALUES (31, 62, 'https://www.google.com/profiles/110312070406589750015');
INSERT INTO open_id_identity VALUES (32, 68, 'https://www.google.com/profiles/109427901142654977833');
INSERT INTO open_id_identity VALUES (33, 69, 'https://www.google.com/profiles/107158839619798289683');
INSERT INTO open_id_identity VALUES (34, 70, 'https://www.google.com/profiles/104695347440971724820');
INSERT INTO open_id_identity VALUES (35, 71, 'http://www.facebook.com/profile.php?id=1132050174');
INSERT INTO open_id_identity VALUES (36, 72, 'https://www.google.com/profiles/115659944869044068968');
INSERT INTO open_id_identity VALUES (37, 73, 'https://www.google.com/profiles/108991013130146150896');
INSERT INTO open_id_identity VALUES (38, 74, 'https://www.google.com/profiles/107603833070632995546');
INSERT INTO open_id_identity VALUES (39, 1, 'http://www.linkedin.com/profile?viewProfile=uA8VssC4VZ');
INSERT INTO open_id_identity VALUES (40, 75, 'https://www.google.com/profiles/101122328644663140746');
INSERT INTO open_id_identity VALUES (42, 77, 'https://www.google.com/profiles/103957005951093347146');
INSERT INTO open_id_identity VALUES (44, 79, 'https://www.google.com/profiles/108722414269138650521');
INSERT INTO open_id_identity VALUES (45, 63, 'http://pavluska.myopenid.com/');
INSERT INTO open_id_identity VALUES (46, 81, 'http://pesta.myopenid.com/');
INSERT INTO open_id_identity VALUES (47, 69, 'https://www.google.com/profiles/112339578258292421280');
INSERT INTO open_id_identity VALUES (48, 1, 'http://www.facebook.com/profile.php?id=1611572776');
INSERT INTO open_id_identity VALUES (26, 59, 'https://www.google.com/profiles/103895332427778311042');




--
-- Data for Name: recently_modified_item; Type: TABLE DATA; Schema: meditor; Owner: meditor
--

INSERT INTO recently_modified_item VALUES (793, 'uuid:7ec83962-e7b9-45cd-bef3-dcbb7715fe2f', 'Proti výminečnému stavu v Praze! /', '', '2011-11-30 19:01:12.9102', 0, 1);
INSERT INTO recently_modified_item VALUES (834, 'uuid:1e67de35-b9d9-11e0-8dee-005056be0007', '8', '', '2011-12-19 12:55:25.174723', 4, 68);
INSERT INTO recently_modified_item VALUES (794, 'uuid:0721a7e5-8cf0-4cd2-b99b-abcfdabb78ff', 'Kázanj při weřegné sláwnosti smutků : nad smrti neygasněgssjho arcywýwody rakauského, kardynala, a arcybiskupa holomauckého Jana Rudolfa w chrámu arcybiskupským dne 17. srpna 1831 /', '', '2011-12-04 19:37:32.722173', 0, 1);
INSERT INTO recently_modified_item VALUES (817, 'uuid:ff09038d-b9d8-11e0-8dee-005056be0007', '[1]', '', '2011-12-05 00:57:14.058851', 5, 68);
INSERT INTO recently_modified_item VALUES (819, 'uuid:6b736b26-c8e5-4c8d-8b2c-7a5d7f72ce68', 'M??hrisch-schlesischer Correspondent : Illustriertes Morgenbatt', '', '2011-12-07 11:35:35.602603', 0, 1);
INSERT INTO recently_modified_item VALUES (940, 'uuid:5bd604fd-070c-11e1-aa24-0050569d679d', 'Jižní Morava ...', '', '2012-01-04 15:24:01.327461', 2, 68);
INSERT INTO recently_modified_item VALUES (835, 'uuid:1fa8c2a8-b9d9-11e0-8dee-005056be0007', '31', '', '2011-12-13 14:43:02.213365', 5, 68);
INSERT INTO recently_modified_item VALUES (904, 'uuid:57b93e65-d925-11e0-b33a-0050569d679d', 'Divadelní šepty', '', '2012-01-04 09:11:28.632867', 2, 68);
INSERT INTO recently_modified_item VALUES (905, 'uuid:661dc954-ef8d-4e41-9814-9a4f4713393b', 'Wasser-Heil-Anstalt des Dr. Winternitz in Kaltenleutgeben, bei Wien /', '', '2012-01-04 13:58:59.577683', 0, 61);
INSERT INTO recently_modified_item VALUES (734, 'uuid:fecd5a1a-b9d8-11e0-8dee-005056be0007', 'Hudební listy', '', '2011-11-28 20:42:12.307152', 2, 1);
INSERT INTO recently_modified_item VALUES (735, 'uuid:e7d58993-e38d-11e0-822b-001e4ff27ac1', 'Moje první lásky (a jiné milostné povídky)', '', '2011-11-28 20:42:44.850276', 0, 1);
INSERT INTO recently_modified_item VALUES (737, 'uuid:086a5e8a-d899-4f2f-add9-e72f24583e9e', '114', '', '2011-11-28 20:44:10.752917', 5, 1);
INSERT INTO recently_modified_item VALUES (736, 'uuid:a5f2da0d-364a-4be0-9d73-1971fe022eaa', 'Orbis Pictus :In Hungaricum, Germanicum, Et Slavicum Translatus, Et Hic Ibive Emendatus /Ioann. A...', '', '2011-11-30 15:22:12.468654', 0, 1);
INSERT INTO recently_modified_item VALUES (824, 'uuid:13f650ad-6447-11e0-8ad7-0050569d679d', 'Duha', '', '2011-12-06 09:46:06.778597', 2, 79);
INSERT INTO recently_modified_item VALUES (825, 'uuid:a0e8eb22-ad76-4bf9-98f7-c812ca2131e6', 'Soupis knížek lidového čtení z fondů Universitní knihovny v Brně /', '', '2011-12-06 09:46:10.662793', 0, 79);
INSERT INTO recently_modified_item VALUES (941, 'uuid:5bd6a13e-070c-11e1-aa24-0050569d679d', '39', '', '2012-01-04 15:28:41.468164', 3, 68);
INSERT INTO recently_modified_item VALUES (740, 'uuid:a5f2da0d-364a-4be0-9d73-1971fe022eaa', 'Orbis Pictus :In Hungaricum, Germanicum, Et Slavicum Translatus, Et Hic Ibive Emendatus /Ioann. A...', '', '2011-11-28 23:39:45.404841', 0, 68);
INSERT INTO recently_modified_item VALUES (742, 'uuid:ede716b9-f66d-4ebe-b421-8a8143b862bc', '1', '', '2011-11-28 23:40:17.193355', 5, 68);
INSERT INTO recently_modified_item VALUES (743, 'uuid:661dc954-ef8d-4e41-9814-9a4f4713393b', 'Wasser-Heil-Anstalt des Dr. Winternitz in Kaltenleutgeben, bei Wien /', '', '2011-11-28 23:40:28.287751', 0, 68);
INSERT INTO recently_modified_item VALUES (744, 'uuid:086a5e8a-d899-4f2f-add9-e72f24583e9e', '114', '', '2011-11-28 23:40:38.890533', 5, 68);
INSERT INTO recently_modified_item VALUES (745, 'uuid:e7d58993-e38d-11e0-822b-001e4ff27ac1', 'Moje první lásky (a jiné milostné povídky)', '', '2011-11-28 23:40:50.49238', 0, 68);
INSERT INTO recently_modified_item VALUES (750, 'uuid:0e79f4c7-b9d9-11e0-8dee-005056be0007', '2', '', '2011-11-28 23:43:28.161756', 4, 68);
INSERT INTO recently_modified_item VALUES (751, 'uuid:0f196919-b9d9-11e0-8dee-005056be0007', '6', '', '2011-11-28 23:43:40.257743', 5, 68);
INSERT INTO recently_modified_item VALUES (749, 'uuid:02482902-b9d9-11e0-8dee-005056be0007', '[37]', '', '2011-11-28 23:43:57.416216', 5, 68);
INSERT INTO recently_modified_item VALUES (748, 'uuid:024283b1-b9d9-11e0-8dee-005056be0007', '10', '', '2011-11-28 23:44:01.455807', 4, 68);
INSERT INTO recently_modified_item VALUES (738, 'uuid:661dc954-ef8d-4e41-9814-9a4f4713393b', 'Wasser-Heil-Anstalt des Dr. Winternitz in Kaltenleutgeben, bei Wien /', '', '2012-01-03 22:43:57.776395', 0, 1);
INSERT INTO recently_modified_item VALUES (861, 'uuid:50b1120a-7e63-4555-832e-8e82379c9dd8', '4', '', '2011-12-22 15:14:37.04016', 5, 68);
INSERT INTO recently_modified_item VALUES (753, 'uuid:13d850c2-b9d9-11e0-8dee-005056be0007', '[13]', '', '2011-12-02 10:33:48.317441', 5, 68);
INSERT INTO recently_modified_item VALUES (821, 'uuid:13f650ad-6447-11e0-8ad7-0050569d679d', 'Duha', '', '2011-12-06 09:24:12.287882', 2, 68);
INSERT INTO recently_modified_item VALUES (752, 'uuid:13d2ab71-b9d9-11e0-8dee-005056be0007', '4', '', '2012-01-02 08:50:43.87232', 4, 68);
INSERT INTO recently_modified_item VALUES (755, 'uuid:fee8f86c-b9d8-11e0-8dee-005056be0007', '1', '', '2011-12-22 14:38:45.08031', 4, 68);
INSERT INTO recently_modified_item VALUES (760, 'uuid:f0d485b7-b382-4d13-8a31-c136b2112eef', '"Krejčíkovo vypravování !Ve stínu lípy"" a jeho blíženec v ""Kandidátu nesmrtelnosti"""', '', '2012-01-02 09:06:24.987075', 6, 68);
INSERT INTO recently_modified_item VALUES (747, 'uuid:fee798db-b9d8-11e0-8dee-005056be0007', 'I', '', '2011-12-02 11:14:37.014493', 3, 68);
INSERT INTO recently_modified_item VALUES (739, 'uuid:ede716b9-f66d-4ebe-b421-8a8143b862bc', '1', '', '2011-11-29 14:11:45.464246', 5, 1);
INSERT INTO recently_modified_item VALUES (812, 'uuid:2115cde6-4d90-4345-a949-8042bc4d45ba', '4', '', '2011-12-02 12:02:59.256909', 5, 68);
INSERT INTO recently_modified_item VALUES (807, 'uuid:a0e8eb22-ad76-4bf9-98f7-c812ca2131e6', 'Soupis knížek lidového čtení z fondů Universitní knihovny v Brně /', '', '2011-12-04 19:37:24.072064', 0, 1);
INSERT INTO recently_modified_item VALUES (822, 'uuid:f52f7aa4-6447-11e0-8ad7-0050569d679d', '2', '', '2011-12-06 10:56:48.334206', 3, 68);
INSERT INTO recently_modified_item VALUES (764, 'uuid:13d850c2-b9d9-11e0-8dee-005056be0007', '[13]', '', '2011-11-29 14:03:37.724586', 5, 61);
INSERT INTO recently_modified_item VALUES (763, 'uuid:f0d485b7-b382-4d13-8a31-c136b2112eee', '"2.Krejčíkovo vypravování !Ve stínu lípy"" a jeho blíženec v ""Kandidátu nesmrtelnosti"""', '', '2012-01-03 22:44:33.973365', 6, 1);
INSERT INTO recently_modified_item VALUES (762, 'uuid:13d850c2-b9d9-11e0-8dee-005056be0007', '[13]', '', '2011-11-30 14:20:27.544443', 5, 1);
INSERT INTO recently_modified_item VALUES (769, 'uuid:fa130a4b-832a-4b4f-a7ae-e5e0f94f7e50', 'PI', '', '2011-11-29 14:52:56.440287', 4, 1);
INSERT INTO recently_modified_item VALUES (770, 'uuid:42338da9-5287-41a3-a79b-8956ed28acc2', 'IP1', '', '2011-11-29 14:53:16.190216', 6, 1);
INSERT INTO recently_modified_item VALUES (865, 'uuid:7f3bc29e-9369-47c5-836c-b5d2068afc2d', '2', '', '2011-12-23 09:37:15.607992', 5, 68);
INSERT INTO recently_modified_item VALUES (786, 'uuid:13d2ab71-b9d9-11e0-8dee-005056be0007', '4', '', '2011-11-30 14:20:34.281417', 4, 1);
INSERT INTO recently_modified_item VALUES (785, 'uuid:f0d485b7-b382-4d13-8a31-c136b2112eef', '"Krejčíkovo vypravování !Ve stínu lípy"" a jeho blíženec v ""Kandidátu nesmrtelnosti"""', '', '2011-11-30 14:24:45.391366', 6, 1);
INSERT INTO recently_modified_item VALUES (787, 'uuid:147490c3-b9d9-11e0-8dee-005056be0007', '14', '', '2011-11-30 14:24:53.096533', 5, 1);
INSERT INTO recently_modified_item VALUES (788, 'uuid:fee8f86c-b9d8-11e0-8dee-005056be0007', '1', '', '2011-11-30 15:37:27.176478', 4, 1);
INSERT INTO recently_modified_item VALUES (791, 'uuid:20558c0e-0694-451b-a57a-335722e62991', 'Divadelní list Národního divadla v Brně', '', '2011-11-30 16:23:16.989223', 2, 1);
INSERT INTO recently_modified_item VALUES (816, 'uuid:00cf92bf-b9d9-11e0-8dee-005056be0007', '3', '', '2011-12-06 08:33:30.871753', 5, 68);
INSERT INTO recently_modified_item VALUES (832, 'uuid:6062021b-b0fd-482e-9806-0132a299710d', 'Die Fische unserer fliessenden Gewässer : Ursachen der Abnahme der Fische und Mittel, die fliessenden Gewässer wieder zu bevölkern /', '', '2011-12-08 17:09:49.829535', 0, 60);
INSERT INTO recently_modified_item VALUES (818, 'uuid:64938a9f-d8af-4f35-8933-3d25558a97e9', 'Evolutionary computation in economics and finance /', '', '2012-01-11 17:01:43.206794', 0, 1);
INSERT INTO recently_modified_item VALUES (854, 'uuid:fe81c5a6-435d-11dd-b505-00145e5790ea', '20', '', '2012-01-03 13:37:08.560825', 4, 68);
INSERT INTO recently_modified_item VALUES (866, 'uuid:b87c7f01-21f1-4310-8906-9f1b0fd34a8e', '3', '', '2011-12-23 09:43:03.848826', 5, 68);
INSERT INTO recently_modified_item VALUES (845, 'uuid:db04e235-435e-11dd-b505-00145e5790ea', '(1)', '', '2012-01-02 08:01:20.276694', 5, 68);
INSERT INTO recently_modified_item VALUES (902, 'uuid:be555c6f-bc69-4b28-a47f-874dad14a9b0', 'Finanční věda. /', '', '2012-01-03 22:43:51.170113', 0, 1);
INSERT INTO recently_modified_item VALUES (855, 'uuid:b8a56808-435d-11dd-b505-00145e5790ea', '38', '', '2012-01-02 08:02:39.155505', 4, 68);
INSERT INTO recently_modified_item VALUES (853, 'uuid:90558288-435f-11dd-b505-00145e5790ea', '(2)', '', '2012-01-02 16:18:28.701402', 5, 68);
INSERT INTO recently_modified_item VALUES (789, 'uuid:9204f7c1-3306-44fa-a888-be62a63d4900', 'SP', '', '2012-01-03 22:59:10.891154', 5, 1);
INSERT INTO recently_modified_item VALUES (911, 'uuid:18327d81-06d8-11e1-aa24-0050569d679d', 'Jižní Morava ...', '', '2012-01-04 15:00:57.476421', 2, 68);
INSERT INTO recently_modified_item VALUES (831, 'uuid:6062021b-b0fd-482e-9806-0132a299710d', 'Die Fische unserer fliessenden Gewässer : Ursachen der Abnahme der Fische und Mittel, die fliessenden Gewässer wieder zu bevölkern /', '', '2012-01-06 14:06:32.550529', 0, 69);
INSERT INTO recently_modified_item VALUES (968, 'uuid:df685853-b058-4ac1-baeb-8fe083ec3e69', '12 Etudes pour le Cor chromatique et le Cor simple avec accomp. de Piano. [hudebnina] /', '', '2012-01-06 15:00:55.606768', 0, 1);
INSERT INTO recently_modified_item VALUES (847, 'uuid:b23d3ce2-435d-11dd-b505-00145e5790ea', '1', '', '2011-12-21 07:52:47.151199', 3, 68);
INSERT INTO recently_modified_item VALUES (848, 'uuid:b8cf84b6-435d-11dd-b505-00145e5790ea', '1', '', '2011-12-21 07:59:07.106788', 4, 68);
INSERT INTO recently_modified_item VALUES (849, 'uuid:b8cf84b7-435d-11dd-b505-00145e5790ea', '2', '', '2011-12-21 07:59:15.280184', 4, 68);
INSERT INTO recently_modified_item VALUES (846, 'uuid:ae876087-435d-11dd-b505-00145e5790ea', 'Národní listy', '', '2011-12-21 08:09:44.546141', 2, 68);
INSERT INTO recently_modified_item VALUES (850, 'uuid:b2ab683b-435d-11dd-b505-00145e5790ea', '15', '', '2011-12-21 08:09:53.641604', 3, 68);
INSERT INTO recently_modified_item VALUES (851, 'uuid:b2aca0bc-435d-11dd-b505-00145e5790ea', '16', '', '2011-12-21 08:10:53.636415', 3, 68);
INSERT INTO recently_modified_item VALUES (852, 'uuid:b8a78a28-435d-11dd-b505-00145e5790ea', '1', '', '2011-12-21 08:18:21.512937', 4, 68);
INSERT INTO recently_modified_item VALUES (974, 'uuid:188a27e8-9839-11e0-bd44-0050569d679d', 'Böhmische Chronik, vom Ursprung der Böhmen', '', '2012-01-17 09:32:33.250923', 0, 68);
INSERT INTO recently_modified_item VALUES (986, 'uuid:aae4c1b7-9832-11e0-bd44-0050569d679d', 'Auszug aus dem neuen Viehseuchen- und
                Rinderpestgesetze vom 29. Februar 1880 (R.-G.-Bl. XIV
                und XV St.) für Gemeindeorgane und Landwirthe', '', '2012-01-17 10:14:35.197686', 0, 68);
INSERT INTO recently_modified_item VALUES (973, 'uuid:18a529f9-9839-11e0-bd44-0050569d679d', '[1a]', '', '2012-01-13 08:34:46.128486', 5, 68);
INSERT INTO recently_modified_item VALUES (975, 'uuid:0ba65551-983a-11e0-bd44-0050569d679d', '343', '', '2012-01-13 08:43:55.30456', 5, 68);
INSERT INTO recently_modified_item VALUES (746, 'uuid:fecd5a1a-b9d8-11e0-8dee-005056be0007', 'Hudební listy', '', '2012-01-17 12:14:36.41202', 2, 68);
INSERT INTO recently_modified_item VALUES (903, 'uuid:2115720a-b9d9-11e0-8dee-005056be0007', '9', '', '2012-01-17 12:15:05.447947', 4, 68);
INSERT INTO recently_modified_item VALUES (1034, 'uuid:e4d052ef-381c-4fbe-9c9b-59205eeaa90c', 'Divadelní list Národního divadla v Brně', '', '2012-01-17 12:49:58.267287', 2, 68);
INSERT INTO recently_modified_item VALUES (885, 'uuid:211b3e6b-b9d9-11e0-8dee-005056be0007', '[33]', '', '2012-01-17 12:14:48.49875', 5, 68);
INSERT INTO recently_modified_item VALUES (754, 'uuid:f0d485b7-b382-4d13-8a31-c136b2112eee', '"2.Krejčíkovo vypravování !Ve stínu lípy"" a jeho blíženec v ""Kandidátu nesmrtelnosti"""', '', '2012-01-17 12:15:17.333859', 6, 68);


--
-- Data for Name: request_for_adding; Type: TABLE DATA; Schema: meditor; Owner: meditor
--

INSERT INTO request_for_adding VALUES (23, 'Jiří Herman', 'http://www.facebook.com/profile.php?id=1536979671', '2011-08-16 11:40:36.511668');
INSERT INTO request_for_adding VALUES (27, 'novelli.metis', 'https://www.google.com/profiles/109009001433606918660', '2011-11-09 08:45:52.106863');
INSERT INTO request_for_adding VALUES (29, 'FiserMarek', 'https://www.google.com/profiles/117997991055033788704', '2011-12-02 16:35:52.937103');
INSERT INTO request_for_adding VALUES (30, 'bar', 'https://www.google.com/profiles/112339578258292421280', '2011-12-14 14:13:10.686979');
INSERT INTO request_for_adding VALUES (32, 'pesta', 'http://pesta.myopenid.com/', '2011-12-15 13:08:45.448639');


--
-- Data for Name: role; Type: TABLE DATA; Schema: meditor; Owner: meditor
--

INSERT INTO role VALUES (3, 'Can publish modified documents', 'can_publish');
INSERT INTO role VALUES (4, 'Can edit ocr data stream', 'edit_ocr');
INSERT INTO role VALUES (6, 'Can edit structure of dig. obj.', 'edit_structure');
INSERT INTO role VALUES (7, 'Can edit MODS data stream', 'edit_mods');
INSERT INTO role VALUES (8, 'Can edit DC data stream', 'edit_dc');
INSERT INTO role VALUES (9, 'Can add or remove users, roles, identities', 'edit_users');
INSERT INTO role VALUES (1, 'Can do anything', 'admin');
INSERT INTO role VALUES (10, 'Can view OCR data stream', 'view_ocr');
INSERT INTO role VALUES (11, 'Can view IMG_FULL data stream', 'view_full');
INSERT INTO role VALUES (12, 'Can view BIBLIO_MODS data stream', 'view_mods');
INSERT INTO role VALUES (13, 'Can view DC data stream', 'view_dc');
INSERT INTO role VALUES (14, 'Can view users, roles, identities', 'view_users');


--
-- Data for Name: stored_files; Type: TABLE DATA; Schema: meditor; Owner: meditor
--

INSERT INTO stored_files VALUES (1, 68, 'uuid:ad4853eb-b844-11e0-95bd-005056be0007', '1', 'my desc', '2011-11-07 08:39:34.684651', 'test file name');


--
-- Data for Name: user_in_role; Type: TABLE DATA; Schema: meditor; Owner: meditor
--

INSERT INTO user_in_role VALUES (1, 1, 1, '2010-12-13 18:22:16.482');
INSERT INTO user_in_role VALUES (5, 1, 3, '2010-12-15 22:19:42.977731');
INSERT INTO user_in_role VALUES (10, 58, 8, '2010-12-16 21:31:18.962469');
INSERT INTO user_in_role VALUES (11, 58, 7, '2010-12-16 21:31:23.087308');
INSERT INTO user_in_role VALUES (14, 60, 8, '2010-12-17 16:20:36.069317');
INSERT INTO user_in_role VALUES (15, 60, 7, '2010-12-17 16:20:40.049073');
INSERT INTO user_in_role VALUES (16, 60, 4, '2010-12-17 16:20:44.651338');
INSERT INTO user_in_role VALUES (17, 60, 6, '2010-12-17 16:20:48.242028');
INSERT INTO user_in_role VALUES (19, 58, 13, '2011-01-01 14:56:53.837292');
INSERT INTO user_in_role VALUES (20, 62, 1, '2012-02-02 13:11:31.433027');
INSERT INTO user_in_role VALUES (21, 61, 3, '2011-01-10 17:49:22.891444');
INSERT INTO user_in_role VALUES (22, 63, 3, '2011-03-12 00:36:28.95624');
INSERT INTO user_in_role VALUES (23, 63, 9, '2011-03-12 00:36:39.874551');
INSERT INTO user_in_role VALUES (24, 63, 7, '2011-05-03 11:05:57.391595');
INSERT INTO user_in_role VALUES (25, 63, 10, '2011-05-03 11:06:05.211382');
INSERT INTO user_in_role VALUES (26, 63, 1, '2011-05-03 11:06:09.05488');
INSERT INTO user_in_role VALUES (27, 63, 6, '2011-05-03 11:06:19.380688');
INSERT INTO user_in_role VALUES (28, 63, 13, '2011-05-03 11:06:29.18266');
INSERT INTO user_in_role VALUES (29, 68, 3, '2011-06-10 12:55:29.927104');
INSERT INTO user_in_role VALUES (30, 68, 9, '2011-06-10 12:55:35.426358');
INSERT INTO user_in_role VALUES (31, 74, 1, '2011-07-19 11:20:34.784693');


--
-- Data for Name: version; Type: TABLE DATA; Schema: meditor; Owner: meditor
--

INSERT INTO version VALUES (1, 1);


--
-- Name: editor_user_pkey; Type: CONSTRAINT; Schema: meditor; Owner: meditor; Tablespace: 
--

ALTER TABLE ONLY editor_user
    ADD CONSTRAINT editor_user_pkey PRIMARY KEY (id);



ALTER TABLE ONLY tree_structure_node
    ADD CONSTRAINT tree_structure_node_pkey PRIMARY KEY (id);

ALTER TABLE ONLY tree_structure
    ADD CONSTRAINT tree_structure_pkey PRIMARY KEY (id);

ALTER TABLE ONLY tree_structure
    ADD CONSTRAINT tree_structure_fk_editor_user FOREIGN KEY (user_id) REFERENCES editor_user(id) MATCH FULL;

ALTER TABLE ONLY tree_structure_node
    ADD CONSTRAINT tree_structure_node_fk_editor_user FOREIGN KEY (tree_id) REFERENCES tree_structure(id) MATCH FULL;


--
-- Name: image_identifier_uniq; Type: CONSTRAINT; Schema: meditor; Owner: meditor; Tablespace: 
--

ALTER TABLE ONLY image
    ADD CONSTRAINT image_identifier_uniq UNIQUE (identifier);


--
-- Name: image_pkey; Type: CONSTRAINT; Schema: meditor; Owner: meditor; Tablespace: 
--

ALTER TABLE ONLY image
    ADD CONSTRAINT image_pkey PRIMARY KEY (id);


--
-- Name: input_queue_item_pkey; Type: CONSTRAINT; Schema: meditor; Owner: meditor; Tablespace: 
--

ALTER TABLE ONLY input_queue_item
    ADD CONSTRAINT input_queue_item_pkey PRIMARY KEY (id);

--
-- Name: input_queue_item_name_pkey; Type: CONSTRAINT; Schema: meditor; Owner: meditor; Tablespace: 
--

ALTER TABLE ONLY input_queue_item_name
    ADD CONSTRAINT input_queue_item_name_pkey PRIMARY KEY (id);

--
-- Name: lock_pkey; Type: CONSTRAINT; Schema: meditor; Owner: meditor; Tablespace: 
--

ALTER TABLE ONLY lock
    ADD CONSTRAINT lock_pkey PRIMARY KEY (id);


--
-- Name: open_id_identity_pkey; Type: CONSTRAINT; Schema: meditor; Owner: meditor; Tablespace: 
--

ALTER TABLE ONLY open_id_identity
    ADD CONSTRAINT open_id_identity_pkey PRIMARY KEY (id);


--
-- Name: path_unique; Type: CONSTRAINT; Schema: meditor; Owner: meditor; Tablespace: 
--

ALTER TABLE ONLY input_queue_item
    ADD CONSTRAINT path_unique UNIQUE (path);

--
-- Name: path_unique; Type: CONSTRAINT; Schema: meditor; Owner: meditor; Tablespace: 
--

ALTER TABLE ONLY input_queue_item_name
    ADD CONSTRAINT path_name_unique UNIQUE (path);

--
-- Name: recently_modified_item_pkey; Type: CONSTRAINT; Schema: meditor; Owner: meditor; Tablespace: 
--

ALTER TABLE ONLY recently_modified_item
    ADD CONSTRAINT recently_modified_item_pkey PRIMARY KEY (id);


--
-- Name: request_for_adding_id_key; Type: CONSTRAINT; Schema: meditor; Owner: meditor; Tablespace: 
--

ALTER TABLE ONLY request_for_adding
    ADD CONSTRAINT request_for_adding_id_key UNIQUE (id);


--
-- Name: role_pkey; Type: CONSTRAINT; Schema: meditor; Owner: meditor; Tablespace: 
--

ALTER TABLE ONLY role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);


--
-- Name: stored_files_pkey; Type: CONSTRAINT; Schema: meditor; Owner: meditor; Tablespace: 
--

ALTER TABLE ONLY stored_files
    ADD CONSTRAINT stored_files_pkey PRIMARY KEY (id);


--
-- Name: user_in_role_pkey; Type: CONSTRAINT; Schema: meditor; Owner: meditor; Tablespace: 
--

ALTER TABLE ONLY user_in_role
    ADD CONSTRAINT user_in_role_pkey PRIMARY KEY (id);


--
-- Name: version_pkey; Type: CONSTRAINT; Schema: meditor; Owner: meditor; Tablespace: 
--

ALTER TABLE ONLY version
    ADD CONSTRAINT version_pkey PRIMARY KEY (id);


--
-- Name: identifier_idx; Type: INDEX; Schema: meditor; Owner: meditor; Tablespace: 
--

CREATE UNIQUE INDEX image_identifier_idx ON image USING btree (identifier);

CREATE INDEX image_old_fs_path_idx ON image USING btree (old_fs_path);

CREATE INDEX recently_modified_item_uuid_idx ON recently_modified_item USING btree (uuid);

CREATE INDEX description_uuid_idx ON description USING btree (uuid);

CREATE INDEX lock_uuid_idx ON lock USING btree (uuid);

CREATE INDEX stored_files_uuid_idx ON lock USING btree (uuid);

CREATE INDEX tree_structure_code_idx ON tree_structure USING btree (barcode);



--
-- Name: lock_fk_editor_user; Type: FK CONSTRAINT; Schema: meditor; Owner: meditor
--

ALTER TABLE ONLY lock
    ADD CONSTRAINT lock_fk_editor_user FOREIGN KEY (user_id) REFERENCES editor_user(id) MATCH FULL;


--
-- Name: open_id_identity_fk_editor_user; Type: FK CONSTRAINT; Schema: meditor; Owner: meditor
--

ALTER TABLE ONLY open_id_identity
    ADD CONSTRAINT open_id_identity_fk_editor_user FOREIGN KEY (user_id) REFERENCES editor_user(id) MATCH FULL;


--
-- Name: recently_modified_item_fk_editor_user; Type: FK CONSTRAINT; Schema: meditor; Owner: meditor
--

ALTER TABLE ONLY recently_modified_item
    ADD CONSTRAINT recently_modified_item_fk_editor_user FOREIGN KEY (user_id) REFERENCES editor_user(id) MATCH FULL;


--
-- Name: stored_files_fk_editor_user; Type: FK CONSTRAINT; Schema: meditor; Owner: meditor
--

ALTER TABLE ONLY stored_files
    ADD CONSTRAINT stored_files_fk_editor_user FOREIGN KEY (user_id) REFERENCES editor_user(id) MATCH FULL;


--
-- Name: user_in_role_fk_editor_user; Type: FK CONSTRAINT; Schema: meditor; Owner: meditor
--

ALTER TABLE ONLY user_in_role
    ADD CONSTRAINT user_in_role_fk_editor_user FOREIGN KEY (user_id) REFERENCES editor_user(id) MATCH FULL;


--
-- Name: user_in_role_fk_role; Type: FK CONSTRAINT; Schema: meditor; Owner: meditor
--

ALTER TABLE ONLY user_in_role
    ADD CONSTRAINT user_in_role_fk_role FOREIGN KEY (role_id) REFERENCES role(id) MATCH FULL;


--
-- Name: user_in_role_role_id_fkey; Type: FK CONSTRAINT; Schema: meditor; Owner: meditor
--

ALTER TABLE ONLY user_in_role
    ADD CONSTRAINT user_in_role_role_id_fkey FOREIGN KEY (role_id) REFERENCES role(id);


--
-- Name: user_in_role_user_id_fkey; Type: FK CONSTRAINT; Schema: meditor; Owner: meditor
--

ALTER TABLE ONLY user_in_role
    ADD CONSTRAINT user_in_role_user_id_fkey FOREIGN KEY (user_id) REFERENCES editor_user(id);










---**********************************************************************************
--- <Insert changes to previous version here>
---**********************************************************************************



---**********************************************************************************
--- </Insert changes to previous version here>
---**********************************************************************************












--
-- Name: meditor; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA meditor FROM PUBLIC;
REVOKE ALL ON SCHEMA meditor FROM meditor;
REVOKE ALL ON SCHEMA meditor FROM postgres;
GRANT ALL ON SCHEMA meditor TO postgres;
GRANT ALL ON SCHEMA meditor TO meditor;
GRANT ALL ON SCHEMA meditor TO PUBLIC;


--
-- PostgreSQL database dump complete
--

