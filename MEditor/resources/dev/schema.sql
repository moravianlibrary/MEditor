--
-- PostgreSQL database dump
--

SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

ALTER TABLE ONLY public.user_in_role DROP CONSTRAINT user_in_role_user_id_fkey;
ALTER TABLE ONLY public.user_in_role DROP CONSTRAINT user_in_role_role_id_fkey;
ALTER TABLE ONLY public.user_in_role DROP CONSTRAINT user_in_role_fk_role;
ALTER TABLE ONLY public.user_in_role DROP CONSTRAINT user_in_role_fk_editor_user;
ALTER TABLE ONLY public.stored_files DROP CONSTRAINT stored_files_fk_editor_user;
ALTER TABLE ONLY public.recently_modified_item DROP CONSTRAINT recently_modified_item_fk_editor_user;
ALTER TABLE ONLY public.open_id_identity DROP CONSTRAINT open_id_identity_fk_editor_user;
ALTER TABLE ONLY public.lock DROP CONSTRAINT lock_fk_editor_user;
DROP INDEX public.old_fs_path_idx;
DROP INDEX public.identifier_idx;
ALTER TABLE ONLY public.version DROP CONSTRAINT version_pkey;
ALTER TABLE ONLY public.user_in_role DROP CONSTRAINT user_in_role_pkey;
ALTER TABLE ONLY public.stored_files DROP CONSTRAINT stored_files_pkey;
ALTER TABLE ONLY public.role DROP CONSTRAINT role_pkey;
ALTER TABLE ONLY public.request_for_adding DROP CONSTRAINT request_for_adding_id_key;
ALTER TABLE ONLY public.recently_modified_item DROP CONSTRAINT recently_modified_item_pkey;
ALTER TABLE ONLY public.input_queue_item DROP CONSTRAINT path_unique;
ALTER TABLE ONLY public.open_id_identity DROP CONSTRAINT open_id_identity_pkey;
ALTER TABLE ONLY public.lock DROP CONSTRAINT lock_pkey;
ALTER TABLE ONLY public.input_queue_item DROP CONSTRAINT input_queue_item_pkey;
ALTER TABLE ONLY public.image DROP CONSTRAINT image_pkey;
ALTER TABLE ONLY public.image DROP CONSTRAINT image_identifier_uniq;
ALTER TABLE ONLY public.editor_user DROP CONSTRAINT editor_user_pkey;
DROP TABLE public.version;
DROP TABLE public.user_in_role;
DROP TABLE public.stored_files;
DROP SEQUENCE public.seq_user_in_role;
DROP SEQUENCE public.seq_stored_files;
DROP TABLE public.role;
DROP SEQUENCE public.seq_role;
DROP TABLE public.request_for_adding;
DROP SEQUENCE public.seq_request_for_adding;
DROP TABLE public.recently_modified_item;
DROP SEQUENCE public.seq_recently_modified_item;
DROP TABLE public.open_id_identity;
DROP SEQUENCE public.seq_open_id_identity;
DROP TABLE public.lock;
DROP SEQUENCE public.seq_lock;
DROP TABLE public.input_queue_item;
DROP SEQUENCE public.seq_input_queue_item;
DROP TABLE public.image;
DROP SEQUENCE public.seq_image;
DROP TABLE public.editor_user;
DROP SEQUENCE public.seq_user;
DROP TABLE public.description;
DROP SEQUENCE public.seq_description;
DROP SCHEMA public;
--
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO postgres;

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET search_path = public, pg_catalog;

--
-- Name: seq_description; Type: SEQUENCE; Schema: public; Owner: meditor
--

CREATE SEQUENCE seq_description
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.seq_description OWNER TO meditor;

--
-- Name: seq_description; Type: SEQUENCE SET; Schema: public; Owner: meditor
--

SELECT pg_catalog.setval('seq_description', 5, true);


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: description; Type: TABLE; Schema: public; Owner: meditor; Tablespace: 
--

CREATE TABLE description (
    id integer DEFAULT nextval('seq_description'::regclass) NOT NULL,
    uuid character varying(45),
    description character varying(16000)
);


ALTER TABLE public.description OWNER TO meditor;

--
-- Name: seq_user; Type: SEQUENCE; Schema: public; Owner: meditor
--

CREATE SEQUENCE seq_user
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.seq_user OWNER TO meditor;

--
-- Name: seq_user; Type: SEQUENCE SET; Schema: public; Owner: meditor
--

SELECT pg_catalog.setval('seq_user', 82, true);


--
-- Name: editor_user; Type: TABLE; Schema: public; Owner: meditor; Tablespace: 
--

CREATE TABLE editor_user (
    id integer DEFAULT nextval('seq_user'::regclass) NOT NULL,
    name character varying(25),
    surname character varying(25),
    sex boolean
);


ALTER TABLE public.editor_user OWNER TO meditor;

--
-- Name: seq_image; Type: SEQUENCE; Schema: public; Owner: meditor
--

CREATE SEQUENCE seq_image
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 4;


ALTER TABLE public.seq_image OWNER TO meditor;

--
-- Name: seq_image; Type: SEQUENCE SET; Schema: public; Owner: meditor
--

SELECT pg_catalog.setval('seq_image', 22696, true);


--
-- Name: image; Type: TABLE; Schema: public; Owner: meditor; Tablespace: 
--

CREATE TABLE image (
    id integer DEFAULT nextval('seq_image'::regclass) NOT NULL,
    identifier character varying(100),
    shown timestamp without time zone,
    old_fs_path character varying(255),
    imagefile character varying(255)
);


ALTER TABLE public.image OWNER TO meditor;

--
-- Name: seq_input_queue_item; Type: SEQUENCE; Schema: public; Owner: meditor
--

CREATE SEQUENCE seq_input_queue_item
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.seq_input_queue_item OWNER TO meditor;

--
-- Name: seq_input_queue_item; Type: SEQUENCE SET; Schema: public; Owner: meditor
--

SELECT pg_catalog.setval('seq_input_queue_item', 29027, true);


--
-- Name: input_queue_item; Type: TABLE; Schema: public; Owner: meditor; Tablespace: 
--

CREATE TABLE input_queue_item (
    id integer DEFAULT nextval('seq_input_queue_item'::regclass) NOT NULL,
    path character varying(50),
    barcode character varying(50),
    ingested boolean
);


ALTER TABLE public.input_queue_item OWNER TO meditor;

--
-- Name: seq_lock; Type: SEQUENCE; Schema: public; Owner: meditor
--

CREATE SEQUENCE seq_lock
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.seq_lock OWNER TO meditor;

--
-- Name: seq_lock; Type: SEQUENCE SET; Schema: public; Owner: meditor
--

SELECT pg_catalog.setval('seq_lock', 78, true);


--
-- Name: lock; Type: TABLE; Schema: public; Owner: meditor; Tablespace: 
--

CREATE TABLE lock (
    id integer DEFAULT nextval('seq_lock'::regclass) NOT NULL,
    uuid character varying(45),
    description character varying(16000),
    modified timestamp without time zone,
    user_id integer
);


ALTER TABLE public.lock OWNER TO meditor;

--
-- Name: seq_open_id_identity; Type: SEQUENCE; Schema: public; Owner: meditor
--

CREATE SEQUENCE seq_open_id_identity
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.seq_open_id_identity OWNER TO meditor;

--
-- Name: seq_open_id_identity; Type: SEQUENCE SET; Schema: public; Owner: meditor
--

SELECT pg_catalog.setval('seq_open_id_identity', 49, true);


--
-- Name: open_id_identity; Type: TABLE; Schema: public; Owner: meditor; Tablespace: 
--

CREATE TABLE open_id_identity (
    id integer DEFAULT nextval('seq_open_id_identity'::regclass) NOT NULL,
    user_id integer,
    identity character varying(100)
);


ALTER TABLE public.open_id_identity OWNER TO meditor;

--
-- Name: seq_recently_modified_item; Type: SEQUENCE; Schema: public; Owner: meditor
--

CREATE SEQUENCE seq_recently_modified_item
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.seq_recently_modified_item OWNER TO meditor;

--
-- Name: seq_recently_modified_item; Type: SEQUENCE SET; Schema: public; Owner: meditor
--

SELECT pg_catalog.setval('seq_recently_modified_item', 1061, true);


--
-- Name: recently_modified_item; Type: TABLE; Schema: public; Owner: meditor; Tablespace: 
--

CREATE TABLE recently_modified_item (
    id integer DEFAULT nextval('seq_recently_modified_item'::regclass) NOT NULL,
    uuid character varying(45),
    name character varying(300),
    description character varying(16000),
    modified timestamp without time zone,
    model integer,
    user_id integer
);


ALTER TABLE public.recently_modified_item OWNER TO meditor;

--
-- Name: seq_request_for_adding; Type: SEQUENCE; Schema: public; Owner: meditor
--

CREATE SEQUENCE seq_request_for_adding
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.seq_request_for_adding OWNER TO meditor;

--
-- Name: seq_request_for_adding; Type: SEQUENCE SET; Schema: public; Owner: meditor
--

SELECT pg_catalog.setval('seq_request_for_adding', 34, true);


--
-- Name: request_for_adding; Type: TABLE; Schema: public; Owner: meditor; Tablespace: 
--

CREATE TABLE request_for_adding (
    id integer DEFAULT nextval('seq_request_for_adding'::regclass) NOT NULL,
    name character varying(100),
    identity character varying(100),
    modified timestamp without time zone
);


ALTER TABLE public.request_for_adding OWNER TO meditor;

--
-- Name: seq_role; Type: SEQUENCE; Schema: public; Owner: meditor
--

CREATE SEQUENCE seq_role
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.seq_role OWNER TO meditor;

--
-- Name: seq_role; Type: SEQUENCE SET; Schema: public; Owner: meditor
--

SELECT pg_catalog.setval('seq_role', 14, true);


--
-- Name: role; Type: TABLE; Schema: public; Owner: meditor; Tablespace: 
--

CREATE TABLE role (
    id integer DEFAULT nextval('seq_role'::regclass) NOT NULL,
    description character varying(80),
    name character varying(30)
);


ALTER TABLE public.role OWNER TO meditor;

--
-- Name: seq_stored_files; Type: SEQUENCE; Schema: public; Owner: meditor
--

CREATE SEQUENCE seq_stored_files
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.seq_stored_files OWNER TO meditor;

--
-- Name: seq_stored_files; Type: SEQUENCE SET; Schema: public; Owner: meditor
--

SELECT pg_catalog.setval('seq_stored_files', 1, true);


--
-- Name: seq_user_in_role; Type: SEQUENCE; Schema: public; Owner: meditor
--

CREATE SEQUENCE seq_user_in_role
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.seq_user_in_role OWNER TO meditor;

--
-- Name: seq_user_in_role; Type: SEQUENCE SET; Schema: public; Owner: meditor
--

SELECT pg_catalog.setval('seq_user_in_role', 34, true);


--
-- Name: stored_files; Type: TABLE; Schema: public; Owner: meditor; Tablespace: 
--

CREATE TABLE stored_files (
    id integer DEFAULT nextval('seq_stored_files'::regclass) NOT NULL,
    user_id integer,
    uuid character varying(45),
    name character varying(300),
    description character varying(16000),
    stored timestamp without time zone,
    file_name character varying(300)
);


ALTER TABLE public.stored_files OWNER TO meditor;

--
-- Name: user_in_role; Type: TABLE; Schema: public; Owner: meditor; Tablespace: 
--

CREATE TABLE user_in_role (
    id integer DEFAULT nextval('seq_user_in_role'::regclass) NOT NULL,
    user_id integer,
    role_id integer,
    date timestamp without time zone
);


ALTER TABLE public.user_in_role OWNER TO meditor;

--
-- Name: version; Type: TABLE; Schema: public; Owner: meditor; Tablespace: 
--

CREATE TABLE version (
    id integer NOT NULL,
    version integer
);


ALTER TABLE public.version OWNER TO meditor;

--
-- Data for Name: description; Type: TABLE DATA; Schema: public; Owner: meditor
--

INSERT INTO description VALUES (1, 'ca4c04d0-4904-11de-9fdc-000d606f5dc6', 'Common description<br>');
INSERT INTO description VALUES (2, '5fe0b160-62d5-11dd-bdc7-000d606f5dc6', '<div style="color: rgb(51, 51, 51); "><br></div><div style="color: rgb(51, 51, 51); "><br></div><div><font class="Apple-style-span" color="#800000">Je treba udelat todlenc a pak tamtononc.</font></div>');
INSERT INTO description VALUES (3, 'uuid:7d66a166-8623-11e0-8f4c-0050569d679d', 'Toto periodikum už v K4 je. Takže je možné tohle smazat.');
INSERT INTO description VALUES (4, 'uuid:04281a60-6330-11dd-ab5f-000d606f5dc6', 'sdfasdf');
INSERT INTO description VALUES (5, 'uuid:047e0290-6330-11dd-aa0c-000d606f5dc6', 'ahoj');


--
-- Data for Name: editor_user; Type: TABLE DATA; Schema: public; Owner: meditor
--

INSERT INTO editor_user VALUES (57, 'Václav', 'Rosecký', true);
INSERT INTO editor_user VALUES (1, 'Jiří', 'Kremser', true);
INSERT INTO editor_user VALUES (60, 'Franta', 'Běžný Uživatel', false);
INSERT INTO editor_user VALUES (61, 'Martin', 'Rehanek', false);
INSERT INTO editor_user VALUES (63, 'Pavla', 'Svastova', false);
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
INSERT INTO editor_user VALUES (76, 'Jan', 'Pokorsky', false);
INSERT INTO editor_user VALUES (77, 'Ivan', 'Chappel', false);
INSERT INTO editor_user VALUES (79, 'Leos', 'Junek', false);
INSERT INTO editor_user VALUES (80, 'Blanka', 'Sapáková', false);
INSERT INTO editor_user VALUES (81, 'Pavel', 'Pesta', false);
INSERT INTO editor_user VALUES (82, 'Olga', 'Jiranova', false);


--
-- Data for Name: image; Type: TABLE DATA; Schema: public; Owner: meditor
--

INSERT INTO image VALUES (21725, 'e692b894-44c5-32b5-a5f9-ca92b97acfdc', '2012-01-19 10:21:38.811478', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0001.jpg', '/home/meditor/.meditor/images/e692b894-44c5-32b5-a5f9-ca92b97acfdc.jp2');
INSERT INTO image VALUES (22574, '74bc4762-3159-31c6-9ad9-63b1693afc1f', '2012-01-20 13:54:47.503197', '/home/meditor/input/monograph/2619217556/0002.jpg', '/home/meditor/.meditor/images/74bc4762-3159-31c6-9ad9-63b1693afc1f.jp2');
INSERT INTO image VALUES (22575, '5bfc092b-cfaf-38a6-bb7a-7b355e79b4dc', '2012-01-20 13:54:47.521151', '/home/meditor/input/monograph/2619217556/0003.jpg', '/home/meditor/.meditor/images/5bfc092b-cfaf-38a6-bb7a-7b355e79b4dc.jp2');
INSERT INTO image VALUES (22576, 'bf66ef78-13d7-3021-86c2-9861f7e3159d', '2012-01-20 13:54:47.528854', '/home/meditor/input/monograph/2619217556/0004.jpg', '/home/meditor/.meditor/images/bf66ef78-13d7-3021-86c2-9861f7e3159d.jp2');
INSERT INTO image VALUES (22577, '7dce12b6-6af9-3ff5-af4d-0e278b17cea4', '2012-01-20 13:54:47.536816', '/home/meditor/input/monograph/2619217556/0005.jpg', '/home/meditor/.meditor/images/7dce12b6-6af9-3ff5-af4d-0e278b17cea4.jp2');
INSERT INTO image VALUES (22578, 'b68982c7-9a8d-3b6b-920d-c45ff5b1999b', '2012-01-20 13:54:47.544788', '/home/meditor/input/monograph/2619217556/0006.jpg', '/home/meditor/.meditor/images/b68982c7-9a8d-3b6b-920d-c45ff5b1999b.jp2');
INSERT INTO image VALUES (22579, '36326e3d-7b49-360e-8b95-d65f898bae8c', '2012-01-20 13:54:47.552743', '/home/meditor/input/monograph/2619217556/0007.jpg', '/home/meditor/.meditor/images/36326e3d-7b49-360e-8b95-d65f898bae8c.jp2');
INSERT INTO image VALUES (22580, '9168fb4a-c78e-3134-92ed-c720d3af0237', '2012-01-20 13:54:47.560839', '/home/meditor/input/monograph/2619217556/0008.jpg', '/home/meditor/.meditor/images/9168fb4a-c78e-3134-92ed-c720d3af0237.jp2');
INSERT INTO image VALUES (22581, 'c152523d-3409-31ba-b2c0-ac1a217afe19', '2012-01-20 13:54:47.562211', '/home/meditor/input/monograph/2619217556/0009.jpg', '/home/meditor/.meditor/images/c152523d-3409-31ba-b2c0-ac1a217afe19.jp2');
INSERT INTO image VALUES (22582, '59a4f092-780d-39a4-a669-c4e41747275b', '2012-01-20 13:54:47.563619', '/home/meditor/input/monograph/2619217556/0010.jpg', '/home/meditor/.meditor/images/59a4f092-780d-39a4-a669-c4e41747275b.jp2');
INSERT INTO image VALUES (22583, 'e22e15cb-5e86-380d-b3cb-4339dff46228', '2012-01-20 13:54:47.56504', '/home/meditor/input/monograph/2619217556/0011.jpg', '/home/meditor/.meditor/images/e22e15cb-5e86-380d-b3cb-4339dff46228.jp2');
INSERT INTO image VALUES (22584, 'bb4b626c-25e5-3daa-b0cf-91e3a4f2794a', '2012-01-20 13:54:47.566417', '/home/meditor/input/monograph/2619217556/0012.jpg', '/home/meditor/.meditor/images/bb4b626c-25e5-3daa-b0cf-91e3a4f2794a.jp2');
INSERT INTO image VALUES (22585, 'bc5e7014-2900-3732-b68d-cc51dc7dea69', '2012-01-20 13:54:47.567842', '/home/meditor/input/monograph/2619217556/0013.jpg', '/home/meditor/.meditor/images/bc5e7014-2900-3732-b68d-cc51dc7dea69.jp2');
INSERT INTO image VALUES (22586, '26e2a51c-8786-324c-9e96-de24a39b2785', '2012-01-20 13:54:47.569213', '/home/meditor/input/monograph/2619217556/0014.jpg', '/home/meditor/.meditor/images/26e2a51c-8786-324c-9e96-de24a39b2785.jp2');
INSERT INTO image VALUES (22587, '2d34b398-424a-3868-b178-8c0c0e64b088', '2012-01-20 13:54:47.570602', '/home/meditor/input/monograph/2619217556/0015.jpg', '/home/meditor/.meditor/images/2d34b398-424a-3868-b178-8c0c0e64b088.jp2');
INSERT INTO image VALUES (22588, '290b408d-1ddf-332c-821e-dd73d28fa739', '2012-01-20 13:54:47.580748', '/home/meditor/input/monograph/2619217556/0016.jpg', '/home/meditor/.meditor/images/290b408d-1ddf-332c-821e-dd73d28fa739.jp2');
INSERT INTO image VALUES (22589, 'fc060325-eddb-30c5-98a8-66bfd129d234', '2012-01-20 13:54:47.582093', '/home/meditor/input/monograph/2619217556/0017.jpg', '/home/meditor/.meditor/images/fc060325-eddb-30c5-98a8-66bfd129d234.jp2');
INSERT INTO image VALUES (22590, '84fd1afc-f44c-3912-b70f-2e11a467ff15', '2012-01-20 13:54:47.583563', '/home/meditor/input/monograph/2619217556/0018.jpg', '/home/meditor/.meditor/images/84fd1afc-f44c-3912-b70f-2e11a467ff15.jp2');
INSERT INTO image VALUES (22591, '181766dd-452d-3174-addd-be66fc77b6d4', '2012-01-20 13:54:47.584947', '/home/meditor/input/monograph/2619217556/0019.jpg', '/home/meditor/.meditor/images/181766dd-452d-3174-addd-be66fc77b6d4.jp2');
INSERT INTO image VALUES (22592, 'a78ea20a-008c-3c93-a28d-e06a33288764', '2012-01-20 13:54:47.58645', '/home/meditor/input/monograph/2619217556/0020.jpg', '/home/meditor/.meditor/images/a78ea20a-008c-3c93-a28d-e06a33288764.jp2');
INSERT INTO image VALUES (22593, '5a12efff-b590-3363-bf5a-121c8b8a943e', '2012-01-20 13:54:47.587892', '/home/meditor/input/monograph/2619217556/0021.jpg', '/home/meditor/.meditor/images/5a12efff-b590-3363-bf5a-121c8b8a943e.jp2');
INSERT INTO image VALUES (22594, '6ca3ad88-ce44-388b-af12-bb7abc6b546c', '2012-01-20 13:54:47.589269', '/home/meditor/input/monograph/2619217556/0022.jpg', '/home/meditor/.meditor/images/6ca3ad88-ce44-388b-af12-bb7abc6b546c.jp2');
INSERT INTO image VALUES (22595, '3ab88ee8-b892-39f9-8cac-e84fadbfb441', '2012-01-20 13:54:47.590644', '/home/meditor/input/monograph/2619217556/0023.jpg', '/home/meditor/.meditor/images/3ab88ee8-b892-39f9-8cac-e84fadbfb441.jp2');
INSERT INTO image VALUES (22596, '14f60a6d-fe82-3689-8cde-593a3c99ea8f', '2012-01-20 13:54:47.592014', '/home/meditor/input/monograph/2619217556/0024.jpg', '/home/meditor/.meditor/images/14f60a6d-fe82-3689-8cde-593a3c99ea8f.jp2');
INSERT INTO image VALUES (22597, '9fe9f540-7b36-3abd-8ff4-a68d92ade649', '2012-01-20 13:54:47.593413', '/home/meditor/input/monograph/2619217556/0025.jpg', '/home/meditor/.meditor/images/9fe9f540-7b36-3abd-8ff4-a68d92ade649.jp2');
INSERT INTO image VALUES (22598, 'd6d0fa69-b433-324a-b7c1-303e4f2a281d', '2012-01-20 13:54:47.59484', '/home/meditor/input/monograph/2619217556/0026.jpg', '/home/meditor/.meditor/images/d6d0fa69-b433-324a-b7c1-303e4f2a281d.jp2');
INSERT INTO image VALUES (22599, 'b20e6d23-ee7e-3230-9328-780ad61e8efc', '2012-01-20 13:54:47.596183', '/home/meditor/input/monograph/2619217556/0027.jpg', '/home/meditor/.meditor/images/b20e6d23-ee7e-3230-9328-780ad61e8efc.jp2');
INSERT INTO image VALUES (22600, 'c387077f-5e3f-399e-83aa-2d90b9c32cc3', '2012-01-20 13:54:47.597563', '/home/meditor/input/monograph/2619217556/0028.jpg', '/home/meditor/.meditor/images/c387077f-5e3f-399e-83aa-2d90b9c32cc3.jp2');
INSERT INTO image VALUES (22601, '0382459a-5a81-30cd-9da5-e21c54f1de9f', '2012-01-20 13:54:47.60059', '/home/meditor/input/monograph/2619217556/0029.jpg', '/home/meditor/.meditor/images/0382459a-5a81-30cd-9da5-e21c54f1de9f.jp2');
INSERT INTO image VALUES (22602, '02a47ca0-217a-3256-9745-f05ac47f500b', '2012-01-20 13:54:47.601972', '/home/meditor/input/monograph/2619217556/0030.jpg', '/home/meditor/.meditor/images/02a47ca0-217a-3256-9745-f05ac47f500b.jp2');
INSERT INTO image VALUES (22603, '1add9825-93eb-3f9d-916e-866698fda548', '2012-01-20 13:54:47.603363', '/home/meditor/input/monograph/2619217556/0031.jpg', '/home/meditor/.meditor/images/1add9825-93eb-3f9d-916e-866698fda548.jp2');
INSERT INTO image VALUES (22604, 'e058a1e5-4d1d-37c5-99ff-f893dfaf0ecd', '2012-01-20 13:54:47.604861', '/home/meditor/input/monograph/2619217556/0032.jpg', '/home/meditor/.meditor/images/e058a1e5-4d1d-37c5-99ff-f893dfaf0ecd.jp2');
INSERT INTO image VALUES (22605, 'd21a364c-1def-37d7-b652-ab993bf9ef8a', '2012-01-20 13:54:47.606183', '/home/meditor/input/monograph/2619217556/0033.jpg', '/home/meditor/.meditor/images/d21a364c-1def-37d7-b652-ab993bf9ef8a.jp2');
INSERT INTO image VALUES (22606, '4db138cf-6756-38ad-a03c-f9c0c7673415', '2012-01-20 13:54:47.607571', '/home/meditor/input/monograph/2619217556/0034.jpg', '/home/meditor/.meditor/images/4db138cf-6756-38ad-a03c-f9c0c7673415.jp2');
INSERT INTO image VALUES (22607, '90e5bd8e-21c2-3c80-9a20-3d54be35614c', '2012-01-20 13:54:47.608981', '/home/meditor/input/monograph/2619217556/0035.jpg', '/home/meditor/.meditor/images/90e5bd8e-21c2-3c80-9a20-3d54be35614c.jp2');
INSERT INTO image VALUES (22608, '3016080b-2d94-323e-a0b3-f2400cabf897', '2012-01-20 13:54:47.610367', '/home/meditor/input/monograph/2619217556/0036.jpg', '/home/meditor/.meditor/images/3016080b-2d94-323e-a0b3-f2400cabf897.jp2');
INSERT INTO image VALUES (22609, 'f4f2f701-2a83-3031-9087-5b9b049cd8a8', '2012-01-20 13:54:47.611755', '/home/meditor/input/monograph/2619217556/0037.jpg', '/home/meditor/.meditor/images/f4f2f701-2a83-3031-9087-5b9b049cd8a8.jp2');
INSERT INTO image VALUES (22610, '4b609b72-262b-3938-aa79-e4b5fde9a836', '2012-01-20 13:54:47.620587', '/home/meditor/input/monograph/2619217556/0038.jpg', '/home/meditor/.meditor/images/4b609b72-262b-3938-aa79-e4b5fde9a836.jp2');
INSERT INTO image VALUES (22611, 'b06fdb98-53ac-315d-a592-aef1eb212bae', '2012-01-20 13:54:47.621969', '/home/meditor/input/monograph/2619217556/0039.jpg', '/home/meditor/.meditor/images/b06fdb98-53ac-315d-a592-aef1eb212bae.jp2');
INSERT INTO image VALUES (22617, '9398089b-87e9-35b5-a3bb-441084667be2', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0001.jpg', '/home/meditor/.meditor/images/9398089b-87e9-35b5-a3bb-441084667be2.jp2');
INSERT INTO image VALUES (22618, 'bb151567-cefb-3251-9517-f3f11a0c9c07', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0002.jpg', '/home/meditor/.meditor/images/bb151567-cefb-3251-9517-f3f11a0c9c07.jp2');
INSERT INTO image VALUES (22619, '16a33320-2e22-3828-9682-7902529d07db', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0003.jpg', '/home/meditor/.meditor/images/16a33320-2e22-3828-9682-7902529d07db.jp2');
INSERT INTO image VALUES (22620, '55ec9ec8-fcf0-35de-ada6-d5213f6fdff5', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0004.jpg', '/home/meditor/.meditor/images/55ec9ec8-fcf0-35de-ada6-d5213f6fdff5.jp2');
INSERT INTO image VALUES (22621, '649e6f69-60ac-3f52-950c-955b0a95ab15', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0005.jpg', '/home/meditor/.meditor/images/649e6f69-60ac-3f52-950c-955b0a95ab15.jp2');
INSERT INTO image VALUES (22622, '4fca7a75-914a-3ea6-8f64-5e3bb6fa66c8', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0006.jpg', '/home/meditor/.meditor/images/4fca7a75-914a-3ea6-8f64-5e3bb6fa66c8.jp2');
INSERT INTO image VALUES (22623, '28f48076-ec85-3187-adb1-cdf65e64af7e', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0007.jpg', '/home/meditor/.meditor/images/28f48076-ec85-3187-adb1-cdf65e64af7e.jp2');
INSERT INTO image VALUES (22624, 'b1b102ec-964c-3c88-9568-539a50ad48d9', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0008.jpg', '/home/meditor/.meditor/images/b1b102ec-964c-3c88-9568-539a50ad48d9.jp2');
INSERT INTO image VALUES (22625, 'c383c680-e9e9-3ad5-a26b-66b45b9967b0', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0009.jpg', '/home/meditor/.meditor/images/c383c680-e9e9-3ad5-a26b-66b45b9967b0.jp2');
INSERT INTO image VALUES (22626, '45fc6bff-39e5-31d9-aeea-46fee680c155', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0010.jpg', '/home/meditor/.meditor/images/45fc6bff-39e5-31d9-aeea-46fee680c155.jp2');
INSERT INTO image VALUES (22627, 'd612f457-ab29-30c2-a473-01822306e615', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0011.jpg', '/home/meditor/.meditor/images/d612f457-ab29-30c2-a473-01822306e615.jp2');
INSERT INTO image VALUES (22628, '9b4249ea-75f9-3c8d-9605-2369f75e3c20', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0012.jpg', '/home/meditor/.meditor/images/9b4249ea-75f9-3c8d-9605-2369f75e3c20.jp2');
INSERT INTO image VALUES (22629, '505828fe-97b8-3191-8e2b-e4318697f513', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0013.jpg', '/home/meditor/.meditor/images/505828fe-97b8-3191-8e2b-e4318697f513.jp2');
INSERT INTO image VALUES (22630, 'c5073b3f-903f-38ee-9af1-3a13e4fa50f8', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0014.jpg', '/home/meditor/.meditor/images/c5073b3f-903f-38ee-9af1-3a13e4fa50f8.jp2');
INSERT INTO image VALUES (22631, '8d82adf0-6202-3baf-a77b-2977ef8bd3bf', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0015.jpg', '/home/meditor/.meditor/images/8d82adf0-6202-3baf-a77b-2977ef8bd3bf.jp2');
INSERT INTO image VALUES (22632, '4d75e1b1-556c-3232-b10f-e7c4aeed2c73', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0016.jpg', '/home/meditor/.meditor/images/4d75e1b1-556c-3232-b10f-e7c4aeed2c73.jp2');
INSERT INTO image VALUES (22633, '9bbbfc58-ac9e-3e19-9bdc-e58df3f4b361', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0017.jpg', '/home/meditor/.meditor/images/9bbbfc58-ac9e-3e19-9bdc-e58df3f4b361.jp2');
INSERT INTO image VALUES (22634, 'f7f0f266-e456-34dd-bc5d-1efd2a9534b7', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0018.jpg', '/home/meditor/.meditor/images/f7f0f266-e456-34dd-bc5d-1efd2a9534b7.jp2');
INSERT INTO image VALUES (22635, '64460dcb-7322-3dd0-9c65-4cf48d8b60ca', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0019.jpg', '/home/meditor/.meditor/images/64460dcb-7322-3dd0-9c65-4cf48d8b60ca.jp2');
INSERT INTO image VALUES (22636, '429035f0-7618-3829-b4ed-8ff716d0bf66', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0020.jpg', '/home/meditor/.meditor/images/429035f0-7618-3829-b4ed-8ff716d0bf66.jp2');
INSERT INTO image VALUES (22637, '23398fae-4aee-372d-beb7-07201b5cf61c', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0021.jpg', '/home/meditor/.meditor/images/23398fae-4aee-372d-beb7-07201b5cf61c.jp2');
INSERT INTO image VALUES (22638, 'dbc0768c-5af4-333e-9501-d38067f8e3fc', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0022.jpg', '/home/meditor/.meditor/images/dbc0768c-5af4-333e-9501-d38067f8e3fc.jp2');
INSERT INTO image VALUES (22639, '2b3b7ab2-d851-3ca0-a7b7-f27b7db65d6d', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0023.jpg', '/home/meditor/.meditor/images/2b3b7ab2-d851-3ca0-a7b7-f27b7db65d6d.jp2');
INSERT INTO image VALUES (22640, '8a607e7e-e9c1-39e3-ae8f-2fea1646aa19', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0024.jpg', '/home/meditor/.meditor/images/8a607e7e-e9c1-39e3-ae8f-2fea1646aa19.jp2');
INSERT INTO image VALUES (22641, 'ced960ac-7821-391c-a596-ee4a130833f8', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0025.jpg', '/home/meditor/.meditor/images/ced960ac-7821-391c-a596-ee4a130833f8.jp2');
INSERT INTO image VALUES (22642, '971689c1-77cb-3dc9-938a-0e816f38d315', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0026.jpg', '/home/meditor/.meditor/images/971689c1-77cb-3dc9-938a-0e816f38d315.jp2');
INSERT INTO image VALUES (22643, '1ca7e648-0c36-3717-aa74-f1e16f0d6038', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0027.jpg', '/home/meditor/.meditor/images/1ca7e648-0c36-3717-aa74-f1e16f0d6038.jp2');
INSERT INTO image VALUES (22644, '848d2320-2ba0-35dd-95f1-6864b8fa44cf', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0028.jpg', '/home/meditor/.meditor/images/848d2320-2ba0-35dd-95f1-6864b8fa44cf.jp2');
INSERT INTO image VALUES (22645, '7a7cfef0-0268-3897-abe5-4986067af2e7', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0029.jpg', '/home/meditor/.meditor/images/7a7cfef0-0268-3897-abe5-4986067af2e7.jp2');
INSERT INTO image VALUES (22646, 'bab3fc46-9ebe-378b-80e8-e243b0d31a45', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0030.jpg', '/home/meditor/.meditor/images/bab3fc46-9ebe-378b-80e8-e243b0d31a45.jp2');
INSERT INTO image VALUES (22647, '5f61a21b-4fbd-3692-ac43-efd8b594e066', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0031.jpg', '/home/meditor/.meditor/images/5f61a21b-4fbd-3692-ac43-efd8b594e066.jp2');
INSERT INTO image VALUES (22613, '688eb0e9-de21-3792-9965-d1d52038f184', '2012-01-20 13:54:47.62489', '/home/meditor/input/monograph/2619217556/0041.jpg', '/home/meditor/.meditor/images/688eb0e9-de21-3792-9965-d1d52038f184.jp2');
INSERT INTO image VALUES (22614, '6a8eea4b-4b19-3541-b02e-675cd5934cb4', '2012-01-20 13:54:47.6263', '/home/meditor/input/monograph/2619217556/0042.jpg', '/home/meditor/.meditor/images/6a8eea4b-4b19-3541-b02e-675cd5934cb4.jp2');
INSERT INTO image VALUES (22615, 'e36bfca1-17d7-39d9-bd2c-0b13af8b90e7', '2012-01-20 13:54:47.627676', '/home/meditor/input/monograph/2619217556/0043.jpg', '/home/meditor/.meditor/images/e36bfca1-17d7-39d9-bd2c-0b13af8b90e7.jp2');
INSERT INTO image VALUES (22616, 'd7aefd44-a467-3305-a837-c382d29ea974', '2012-01-20 13:54:47.629055', '/home/meditor/input/monograph/2619217556/0044.jpg', '/home/meditor/.meditor/images/d7aefd44-a467-3305-a837-c382d29ea974.jp2');
INSERT INTO image VALUES (22648, 'fe2c0eca-d391-3cb6-a7ba-f0ba28b4d921', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0032.jpg', '/home/meditor/.meditor/images/fe2c0eca-d391-3cb6-a7ba-f0ba28b4d921.jp2');
INSERT INTO image VALUES (22649, 'ed94fa54-2cf0-3e1f-8086-f3c111422e6e', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0033.jpg', '/home/meditor/.meditor/images/ed94fa54-2cf0-3e1f-8086-f3c111422e6e.jp2');
INSERT INTO image VALUES (22650, '64643429-9ccd-3019-8a87-837ed4c6322f', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0034.jpg', '/home/meditor/.meditor/images/64643429-9ccd-3019-8a87-837ed4c6322f.jp2');
INSERT INTO image VALUES (22651, 'a7596556-dbcd-36a8-91fa-e3396f623c9d', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0035.jpg', '/home/meditor/.meditor/images/a7596556-dbcd-36a8-91fa-e3396f623c9d.jp2');
INSERT INTO image VALUES (22652, 'e5699e4c-38f0-36f9-8121-9baf633dd8d5', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0036.jpg', '/home/meditor/.meditor/images/e5699e4c-38f0-36f9-8121-9baf633dd8d5.jp2');
INSERT INTO image VALUES (22653, '30f8af07-10d6-3cee-9b98-3c49517ee4a2', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0037.jpg', '/home/meditor/.meditor/images/30f8af07-10d6-3cee-9b98-3c49517ee4a2.jp2');
INSERT INTO image VALUES (22654, '945da28f-51da-3261-9bec-c6d232561843', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0038.jpg', '/home/meditor/.meditor/images/945da28f-51da-3261-9bec-c6d232561843.jp2');
INSERT INTO image VALUES (22655, '1cd93b80-2635-3277-98b7-e682d22f0525', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0039.jpg', '/home/meditor/.meditor/images/1cd93b80-2635-3277-98b7-e682d22f0525.jp2');
INSERT INTO image VALUES (22656, 'e07f9382-1a79-3673-9c7e-db0f50697108', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0040.jpg', '/home/meditor/.meditor/images/e07f9382-1a79-3673-9c7e-db0f50697108.jp2');
INSERT INTO image VALUES (22657, '647d20c4-e4ee-3a5d-af81-e0c1b8e9ca6f', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0041.jpg', '/home/meditor/.meditor/images/647d20c4-e4ee-3a5d-af81-e0c1b8e9ca6f.jp2');
INSERT INTO image VALUES (22658, 'a8a7042b-a763-3be9-bde9-68caab537d9b', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0042.jpg', '/home/meditor/.meditor/images/a8a7042b-a763-3be9-bde9-68caab537d9b.jp2');
INSERT INTO image VALUES (22659, 'd6ea0410-c415-3535-8f79-876b0b02982f', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0043.jpg', '/home/meditor/.meditor/images/d6ea0410-c415-3535-8f79-876b0b02982f.jp2');
INSERT INTO image VALUES (22660, '7fb2416b-ac30-33ba-95f4-4f326dc45565', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0044.jpg', '/home/meditor/.meditor/images/7fb2416b-ac30-33ba-95f4-4f326dc45565.jp2');
INSERT INTO image VALUES (22661, 'c81b5c59-fedb-34d2-a7f6-e7eaa264f4d4', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0045.jpg', '/home/meditor/.meditor/images/c81b5c59-fedb-34d2-a7f6-e7eaa264f4d4.jp2');
INSERT INTO image VALUES (22662, '525e27d2-52fc-3b92-9b04-458e2938a082', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0046.jpg', '/home/meditor/.meditor/images/525e27d2-52fc-3b92-9b04-458e2938a082.jp2');
INSERT INTO image VALUES (22663, '1016fa5f-ca03-31c7-8dc3-43893d4b966d', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0047.jpg', '/home/meditor/.meditor/images/1016fa5f-ca03-31c7-8dc3-43893d4b966d.jp2');
INSERT INTO image VALUES (22664, '93df348a-0f3a-3bc6-b349-7f7144824d63', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0048.jpg', '/home/meditor/.meditor/images/93df348a-0f3a-3bc6-b349-7f7144824d63.jp2');
INSERT INTO image VALUES (22665, '46b1d488-9c9a-3574-a41b-e6ad0bd00fc0', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0049.jpg', '/home/meditor/.meditor/images/46b1d488-9c9a-3574-a41b-e6ad0bd00fc0.jp2');
INSERT INTO image VALUES (22666, 'f73c69ec-9fbc-37a7-8027-318589d1317f', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0050.jpg', '/home/meditor/.meditor/images/f73c69ec-9fbc-37a7-8027-318589d1317f.jp2');
INSERT INTO image VALUES (22667, '8c0d4104-3511-3e07-9e7f-6c3834253fb8', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0051.jpg', '/home/meditor/.meditor/images/8c0d4104-3511-3e07-9e7f-6c3834253fb8.jp2');
INSERT INTO image VALUES (22668, '4b709235-c61e-3d68-a74e-cdba5d12d2bf', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0052.jpg', '/home/meditor/.meditor/images/4b709235-c61e-3d68-a74e-cdba5d12d2bf.jp2');
INSERT INTO image VALUES (22669, '320780fb-3290-3fb9-854f-ebacf8965871', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0053.jpg', '/home/meditor/.meditor/images/320780fb-3290-3fb9-854f-ebacf8965871.jp2');
INSERT INTO image VALUES (22670, '1d62b1c9-ec31-3e82-8412-5eb135f4d83e', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0054.jpg', '/home/meditor/.meditor/images/1d62b1c9-ec31-3e82-8412-5eb135f4d83e.jp2');
INSERT INTO image VALUES (22671, '93b8cdb2-7e0c-3219-a8eb-b7a9ae605555', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0055.jpg', '/home/meditor/.meditor/images/93b8cdb2-7e0c-3219-a8eb-b7a9ae605555.jp2');
INSERT INTO image VALUES (22672, 'eeb47bd4-ff83-3b9f-88b3-805f50d1fa91', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0056.jpg', '/home/meditor/.meditor/images/eeb47bd4-ff83-3b9f-88b3-805f50d1fa91.jp2');
INSERT INTO image VALUES (22673, '13170c16-9725-3df2-8c3d-1b955e0bb366', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0057.jpg', '/home/meditor/.meditor/images/13170c16-9725-3df2-8c3d-1b955e0bb366.jp2');
INSERT INTO image VALUES (22674, 'dab6f443-1a6e-3ba1-bf44-5aedb1f7dd0e', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0058.jpg', '/home/meditor/.meditor/images/dab6f443-1a6e-3ba1-bf44-5aedb1f7dd0e.jp2');
INSERT INTO image VALUES (22675, 'd0646c53-4f28-35d2-9d68-72770fcb9d3e', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0059.jpg', '/home/meditor/.meditor/images/d0646c53-4f28-35d2-9d68-72770fcb9d3e.jp2');
INSERT INTO image VALUES (22676, 'd61e73a5-413b-36b5-8cdc-3cca5a9f4e89', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0060.jpg', '/home/meditor/.meditor/images/d61e73a5-413b-36b5-8cdc-3cca5a9f4e89.jp2');
INSERT INTO image VALUES (22677, '4116c887-b332-3f4f-8588-3e4e7dd17749', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0061.jpg', '/home/meditor/.meditor/images/4116c887-b332-3f4f-8588-3e4e7dd17749.jp2');
INSERT INTO image VALUES (22678, '5f0b990f-de7a-3c63-937e-89515fac895a', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0062.jpg', '/home/meditor/.meditor/images/5f0b990f-de7a-3c63-937e-89515fac895a.jp2');
INSERT INTO image VALUES (22679, 'fb99f2ca-078b-32d3-bc7c-0872d7b37f90', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0063.jpg', '/home/meditor/.meditor/images/fb99f2ca-078b-32d3-bc7c-0872d7b37f90.jp2');
INSERT INTO image VALUES (22680, '2f8c612e-d51f-326f-b4eb-f2b9d99a9c4f', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0064.jpg', '/home/meditor/.meditor/images/2f8c612e-d51f-326f-b4eb-f2b9d99a9c4f.jp2');
INSERT INTO image VALUES (22681, '0263ff79-e5f1-3fbd-9c04-ce778a181c1e', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0065.jpg', '/home/meditor/.meditor/images/0263ff79-e5f1-3fbd-9c04-ce778a181c1e.jp2');
INSERT INTO image VALUES (22682, '2a7500d5-ae1b-3836-b506-4ffb2667ab99', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0066.jpg', '/home/meditor/.meditor/images/2a7500d5-ae1b-3836-b506-4ffb2667ab99.jp2');
INSERT INTO image VALUES (22683, 'c5e841ac-ef15-384c-9120-e9af0edc7384', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0067.jpg', '/home/meditor/.meditor/images/c5e841ac-ef15-384c-9120-e9af0edc7384.jp2');
INSERT INTO image VALUES (22684, 'e8e09f2c-df9d-3868-805c-db5c0611dcc1', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0068.jpg', '/home/meditor/.meditor/images/e8e09f2c-df9d-3868-805c-db5c0611dcc1.jp2');
INSERT INTO image VALUES (22685, '3a9df766-819b-335e-905b-b3f4783c39ce', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0069.jpg', '/home/meditor/.meditor/images/3a9df766-819b-335e-905b-b3f4783c39ce.jp2');
INSERT INTO image VALUES (22686, 'd43b9eb9-4f42-3b8a-b517-0f8d100d9a00', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0070.jpg', '/home/meditor/.meditor/images/d43b9eb9-4f42-3b8a-b517-0f8d100d9a00.jp2');
INSERT INTO image VALUES (22687, 'e63db4f3-ed43-3c08-81fb-4533cabb572c', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0071.jpg', '/home/meditor/.meditor/images/e63db4f3-ed43-3c08-81fb-4533cabb572c.jp2');
INSERT INTO image VALUES (22688, 'a73c8194-3f74-34bc-bd4f-1de52dea5a43', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0072.jpg', '/home/meditor/.meditor/images/a73c8194-3f74-34bc-bd4f-1de52dea5a43.jp2');
INSERT INTO image VALUES (22689, 'fea660eb-dc89-3888-ad69-d0c735d451ca', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0073.jpg', '/home/meditor/.meditor/images/fea660eb-dc89-3888-ad69-d0c735d451ca.jp2');
INSERT INTO image VALUES (22690, '61f685a4-9a5f-35bf-a3c1-7f09e50d2dc7', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0074.jpg', '/home/meditor/.meditor/images/61f685a4-9a5f-35bf-a3c1-7f09e50d2dc7.jp2');
INSERT INTO image VALUES (22691, '33708b10-6598-3649-9c93-911aa22b5f0b', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0075.jpg', '/home/meditor/.meditor/images/33708b10-6598-3649-9c93-911aa22b5f0b.jp2');
INSERT INTO image VALUES (22692, '360329d5-ff46-3011-8ecc-d1833a9e3098', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0076.jpg', '/home/meditor/.meditor/images/360329d5-ff46-3011-8ecc-d1833a9e3098.jp2');
INSERT INTO image VALUES (22693, '74eba444-528c-36e4-9574-4a2c2eee5fad', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0077.jpg', '/home/meditor/.meditor/images/74eba444-528c-36e4-9574-4a2c2eee5fad.jp2');
INSERT INTO image VALUES (22694, '04219d03-c75a-3b22-9488-8a223c6dd72d', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0078.jpg', '/home/meditor/.meditor/images/04219d03-c75a-3b22-9488-8a223c6dd72d.jp2');
INSERT INTO image VALUES (22695, '94894dba-c8f9-3c3e-9c64-3c6c9db9b0a2', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0079.jpg', '/home/meditor/.meditor/images/94894dba-c8f9-3c3e-9c64-3c6c9db9b0a2.jp2');
INSERT INTO image VALUES (22696, '77881c96-c68a-363c-88d1-995cf802a6f1', '2012-01-20 12:21:50.988611', '/home/meditor/input/periodical/000173829/2610446094/39-2010-04/0080.jpg', '/home/meditor/.meditor/images/77881c96-c68a-363c-88d1-995cf802a6f1.jp2');
INSERT INTO image VALUES (21585, 'aea5f184-ec1c-3c5a-b2ec-4be8367b94a0', '2012-01-20 12:01:26.569385', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0049.jpg', '/home/meditor/.meditor/images/aea5f184-ec1c-3c5a-b2ec-4be8367b94a0.jp2');
INSERT INTO image VALUES (21586, '7071c4ee-12c7-3626-a4f9-3c442c22c86a', '2012-01-20 12:01:26.571187', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0050.jpg', '/home/meditor/.meditor/images/7071c4ee-12c7-3626-a4f9-3c442c22c86a.jp2');
INSERT INTO image VALUES (21587, '8cad4417-92fc-3488-b3c0-d83219c5a7f6', '2012-01-20 12:01:26.573142', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0051.jpg', '/home/meditor/.meditor/images/8cad4417-92fc-3488-b3c0-d83219c5a7f6.jp2');
INSERT INTO image VALUES (21588, '6b2a9e9a-d45d-33b7-b74a-43f69859e76c', '2012-01-20 12:01:26.574895', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0052.jpg', '/home/meditor/.meditor/images/6b2a9e9a-d45d-33b7-b74a-43f69859e76c.jp2');
INSERT INTO image VALUES (21589, 'e149b317-e822-3795-8868-0bf8ac71cfc4', '2012-01-20 12:01:26.576418', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0053.jpg', '/home/meditor/.meditor/images/e149b317-e822-3795-8868-0bf8ac71cfc4.jp2');
INSERT INTO image VALUES (21590, 'd3605555-78f2-3211-add3-d813460c9807', '2012-01-20 12:01:26.577917', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0054.jpg', '/home/meditor/.meditor/images/d3605555-78f2-3211-add3-d813460c9807.jp2');
INSERT INTO image VALUES (21591, 'acc04099-9caf-3cf1-b6e0-443f7f30d663', '2012-01-20 12:01:26.579412', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0055.jpg', '/home/meditor/.meditor/images/acc04099-9caf-3cf1-b6e0-443f7f30d663.jp2');
INSERT INTO image VALUES (21592, '248faa95-63f8-3131-8749-3f139a97409c', '2012-01-20 12:01:26.582336', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0056.jpg', '/home/meditor/.meditor/images/248faa95-63f8-3131-8749-3f139a97409c.jp2');
INSERT INTO image VALUES (21593, 'd211f1be-7d92-3e06-bfaf-7265ea14b670', '2012-01-20 12:01:26.583902', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0057.jpg', '/home/meditor/.meditor/images/d211f1be-7d92-3e06-bfaf-7265ea14b670.jp2');
INSERT INTO image VALUES (21594, '0fcbf521-e4f3-365a-a383-c842c79f1355', '2012-01-20 12:01:26.585402', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0058.jpg', '/home/meditor/.meditor/images/0fcbf521-e4f3-365a-a383-c842c79f1355.jp2');
INSERT INTO image VALUES (21595, 'eaf5b442-74a1-3cd0-b4ae-c35c4ae6435d', '2012-01-20 12:01:26.586881', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0059.jpg', '/home/meditor/.meditor/images/eaf5b442-74a1-3cd0-b4ae-c35c4ae6435d.jp2');
INSERT INTO image VALUES (22573, 'f4d27af1-2ae6-323d-9e5c-bf941fd364cd', '2012-01-20 13:54:47.500522', '/home/meditor/input/monograph/2619217556/0001.jpg', '/home/meditor/.meditor/images/f4d27af1-2ae6-323d-9e5c-bf941fd364cd.jp2');
INSERT INTO image VALUES (22612, 'bbc1be1b-de19-3a4c-a437-9a0ec630e177', '2012-01-20 13:54:47.62334', '/home/meditor/input/monograph/2619217556/0040.jpg', '/home/meditor/.meditor/images/bbc1be1b-de19-3a4c-a437-9a0ec630e177.jp2');
INSERT INTO image VALUES (21760, 'b2a98e71-14a1-3a41-b8d6-b296f4da8f27', '2012-01-19 10:21:38.914342', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0036.jpg', '/home/meditor/.meditor/images/b2a98e71-14a1-3a41-b8d6-b296f4da8f27.jp2');
INSERT INTO image VALUES (21761, '7c4e7ac7-2004-3985-8de2-700b36042510', '2012-01-19 10:21:38.915988', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0037.jpg', '/home/meditor/.meditor/images/7c4e7ac7-2004-3985-8de2-700b36042510.jp2');
INSERT INTO image VALUES (21539, 'c46d77f8-00ab-3037-adfd-84c27c1bacb3', '2012-01-20 12:01:26.493527', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0003.jpg', '/home/meditor/.meditor/images/c46d77f8-00ab-3037-adfd-84c27c1bacb3.jp2');
INSERT INTO image VALUES (21540, '07cfc659-ee4b-3e0c-a6bb-dbc6ab053c33', '2012-01-20 12:01:26.49508', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0004.jpg', '/home/meditor/.meditor/images/07cfc659-ee4b-3e0c-a6bb-dbc6ab053c33.jp2');
INSERT INTO image VALUES (21541, 'b991e155-0ac4-3ac2-aecd-63bd360b2e74', '2012-01-20 12:01:26.496583', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0005.jpg', '/home/meditor/.meditor/images/b991e155-0ac4-3ac2-aecd-63bd360b2e74.jp2');
INSERT INTO image VALUES (21542, '54eebdee-f094-3038-8c59-e7bf8c5d8d62', '2012-01-20 12:01:26.498141', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0006.jpg', '/home/meditor/.meditor/images/54eebdee-f094-3038-8c59-e7bf8c5d8d62.jp2');
INSERT INTO image VALUES (21543, '15a86f3e-c571-3104-93a8-f26b933c65cf', '2012-01-20 12:01:26.499616', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0007.jpg', '/home/meditor/.meditor/images/15a86f3e-c571-3104-93a8-f26b933c65cf.jp2');
INSERT INTO image VALUES (21544, '6c472c6b-b792-393c-891d-ef90dcaf01cd', '2012-01-20 12:01:26.501096', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0008.jpg', '/home/meditor/.meditor/images/6c472c6b-b792-393c-891d-ef90dcaf01cd.jp2');
INSERT INTO image VALUES (21545, '369baec4-4d91-3791-a08b-957e1283c37a', '2012-01-20 12:01:26.502652', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0009.jpg', '/home/meditor/.meditor/images/369baec4-4d91-3791-a08b-957e1283c37a.jp2');
INSERT INTO image VALUES (21546, 'd3cfeedb-7b9d-39fe-9793-ae403a37b3d9', '2012-01-20 12:01:26.504254', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0010.jpg', '/home/meditor/.meditor/images/d3cfeedb-7b9d-39fe-9793-ae403a37b3d9.jp2');
INSERT INTO image VALUES (21547, 'c2aad7df-9a40-3a4b-acc2-c8d14ca512c7', '2012-01-20 12:01:26.505804', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0011.jpg', '/home/meditor/.meditor/images/c2aad7df-9a40-3a4b-acc2-c8d14ca512c7.jp2');
INSERT INTO image VALUES (21548, '18a5d943-e7bf-32b1-b009-94931919b424', '2012-01-20 12:01:26.507325', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0012.jpg', '/home/meditor/.meditor/images/18a5d943-e7bf-32b1-b009-94931919b424.jp2');
INSERT INTO image VALUES (21549, '2c20bdb2-d630-3b1e-993d-4877afd315f4', '2012-01-20 12:01:26.508912', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0013.jpg', '/home/meditor/.meditor/images/2c20bdb2-d630-3b1e-993d-4877afd315f4.jp2');
INSERT INTO image VALUES (21550, '746b496b-94b6-3a2f-804c-128c933152b6', '2012-01-20 12:01:26.51048', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0014.jpg', '/home/meditor/.meditor/images/746b496b-94b6-3a2f-804c-128c933152b6.jp2');
INSERT INTO image VALUES (21538, '9a686b03-76bf-3944-9518-14003e42e0a6', '2012-01-20 12:01:26.491866', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0002.jpg', '/home/meditor/.meditor/images/9a686b03-76bf-3944-9518-14003e42e0a6.jp2');
INSERT INTO image VALUES (21555, 'ebc9e4e9-a9f2-3430-ab15-e77ab6c9b224', '2012-01-20 12:01:26.521235', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0019.jpg', '/home/meditor/.meditor/images/ebc9e4e9-a9f2-3430-ab15-e77ab6c9b224.jp2');
INSERT INTO image VALUES (21556, '4e692572-7e12-39b4-96e7-802ccd906639', '2012-01-20 12:01:26.522858', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0020.jpg', '/home/meditor/.meditor/images/4e692572-7e12-39b4-96e7-802ccd906639.jp2');
INSERT INTO image VALUES (21557, 'd88c977d-c63c-3f35-9530-746926271cff', '2012-01-20 12:01:26.524382', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0021.jpg', '/home/meditor/.meditor/images/d88c977d-c63c-3f35-9530-746926271cff.jp2');
INSERT INTO image VALUES (21558, 'd2b5e165-154a-31a1-9eb2-ca5e2abca391', '2012-01-20 12:01:26.525877', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0022.jpg', '/home/meditor/.meditor/images/d2b5e165-154a-31a1-9eb2-ca5e2abca391.jp2');
INSERT INTO image VALUES (21559, 'd9627dae-aba5-3aaf-9844-8e1a45ef5a55', '2012-01-20 12:01:26.527734', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0023.jpg', '/home/meditor/.meditor/images/d9627dae-aba5-3aaf-9844-8e1a45ef5a55.jp2');
INSERT INTO image VALUES (21560, 'ab0bce1f-8fc1-3452-a0e9-8d05118bc15a', '2012-01-20 12:01:26.529617', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0024.jpg', '/home/meditor/.meditor/images/ab0bce1f-8fc1-3452-a0e9-8d05118bc15a.jp2');
INSERT INTO image VALUES (21561, '74e80b3f-91c2-3978-ace7-56bf0b560624', '2012-01-20 12:01:26.531377', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0025.jpg', '/home/meditor/.meditor/images/74e80b3f-91c2-3978-ace7-56bf0b560624.jp2');
INSERT INTO image VALUES (21562, 'a32e8869-a756-39a5-92b6-9c6dfdc0d5f9', '2012-01-20 12:01:26.533041', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0026.jpg', '/home/meditor/.meditor/images/a32e8869-a756-39a5-92b6-9c6dfdc0d5f9.jp2');
INSERT INTO image VALUES (21563, 'eb9f0e06-2965-3df0-9c12-81fb773e2ccb', '2012-01-20 12:01:26.534564', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0027.jpg', '/home/meditor/.meditor/images/eb9f0e06-2965-3df0-9c12-81fb773e2ccb.jp2');
INSERT INTO image VALUES (21564, 'f90aa355-bf4b-3be1-aaa4-af755f0adcc8', '2012-01-20 12:01:26.536183', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0028.jpg', '/home/meditor/.meditor/images/f90aa355-bf4b-3be1-aaa4-af755f0adcc8.jp2');
INSERT INTO image VALUES (21565, 'b1e29ad4-19c1-3637-92ad-7cd21a2900e8', '2012-01-20 12:01:26.53765', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0029.jpg', '/home/meditor/.meditor/images/b1e29ad4-19c1-3637-92ad-7cd21a2900e8.jp2');
INSERT INTO image VALUES (21566, '269a4dde-158e-330e-a1ee-ce80079ff261', '2012-01-20 12:01:26.53917', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0030.jpg', '/home/meditor/.meditor/images/269a4dde-158e-330e-a1ee-ce80079ff261.jp2');
INSERT INTO image VALUES (21567, '9ef3e0bb-93e8-3d1a-846a-2f7319e5557f', '2012-01-20 12:01:26.540703', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0031.jpg', '/home/meditor/.meditor/images/9ef3e0bb-93e8-3d1a-846a-2f7319e5557f.jp2');
INSERT INTO image VALUES (21568, '02742162-c582-337e-b438-018e35ebde3e', '2012-01-20 12:01:26.542329', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0032.jpg', '/home/meditor/.meditor/images/02742162-c582-337e-b438-018e35ebde3e.jp2');
INSERT INTO image VALUES (21569, '8ebd580a-1e03-3e5a-89eb-01cc735812fd', '2012-01-20 12:01:26.544024', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0033.jpg', '/home/meditor/.meditor/images/8ebd580a-1e03-3e5a-89eb-01cc735812fd.jp2');
INSERT INTO image VALUES (21570, '0b55e58c-6b2d-3338-9c21-afd8b96ebbd6', '2012-01-20 12:01:26.545662', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0034.jpg', '/home/meditor/.meditor/images/0b55e58c-6b2d-3338-9c21-afd8b96ebbd6.jp2');
INSERT INTO image VALUES (21571, 'bafe8503-cde6-3f36-9370-db417f604caa', '2012-01-20 12:01:26.547253', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0035.jpg', '/home/meditor/.meditor/images/bafe8503-cde6-3f36-9370-db417f604caa.jp2');
INSERT INTO image VALUES (21573, '4e7d28a4-b674-3232-bbc0-7493e9e3080c', '2012-01-20 12:01:26.550432', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0037.jpg', '/home/meditor/.meditor/images/4e7d28a4-b674-3232-bbc0-7493e9e3080c.jp2');
INSERT INTO image VALUES (21574, '124d3685-e46b-3e5d-8ed6-46096aea830b', '2012-01-20 12:01:26.551969', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0038.jpg', '/home/meditor/.meditor/images/124d3685-e46b-3e5d-8ed6-46096aea830b.jp2');
INSERT INTO image VALUES (21575, '18876b51-4910-3360-82d5-12741f8019c7', '2012-01-20 12:01:26.553682', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0039.jpg', '/home/meditor/.meditor/images/18876b51-4910-3360-82d5-12741f8019c7.jp2');
INSERT INTO image VALUES (21576, '4571ef5f-c85d-32e9-adbb-783cc9d2456b', '2012-01-20 12:01:26.555189', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0040.jpg', '/home/meditor/.meditor/images/4571ef5f-c85d-32e9-adbb-783cc9d2456b.jp2');
INSERT INTO image VALUES (21577, 'e307af86-07e2-3882-925a-e06895a72f45', '2012-01-20 12:01:26.556933', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0041.jpg', '/home/meditor/.meditor/images/e307af86-07e2-3882-925a-e06895a72f45.jp2');
INSERT INTO image VALUES (21578, 'd594b237-6897-317b-ba57-8de1980dce51', '2012-01-20 12:01:26.558474', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0042.jpg', '/home/meditor/.meditor/images/d594b237-6897-317b-ba57-8de1980dce51.jp2');
INSERT INTO image VALUES (21579, '14337819-6fc2-3128-aaf2-d48d30110949', '2012-01-20 12:01:26.560189', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0043.jpg', '/home/meditor/.meditor/images/14337819-6fc2-3128-aaf2-d48d30110949.jp2');
INSERT INTO image VALUES (21580, '89a8b0a9-7a68-3e75-aba6-44f23c8c6407', '2012-01-20 12:01:26.561612', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0044.jpg', '/home/meditor/.meditor/images/89a8b0a9-7a68-3e75-aba6-44f23c8c6407.jp2');
INSERT INTO image VALUES (21581, '5ffba096-d297-3fff-a549-99ddae2a9864', '2012-01-20 12:01:26.563082', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0045.jpg', '/home/meditor/.meditor/images/5ffba096-d297-3fff-a549-99ddae2a9864.jp2');
INSERT INTO image VALUES (21582, '72e2fad5-187a-30d5-964a-91a7bf4794f0', '2012-01-20 12:01:26.564855', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0046.jpg', '/home/meditor/.meditor/images/72e2fad5-187a-30d5-964a-91a7bf4794f0.jp2');
INSERT INTO image VALUES (21583, 'eaa6d046-bfa1-3d06-a5b7-feeaa3809c32', '2012-01-20 12:01:26.566332', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0047.jpg', '/home/meditor/.meditor/images/eaa6d046-bfa1-3d06-a5b7-feeaa3809c32.jp2');
INSERT INTO image VALUES (21584, 'c94a789d-221b-359f-afa8-d4ceefa9a004', '2012-01-20 12:01:26.567882', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0048.jpg', '/home/meditor/.meditor/images/c94a789d-221b-359f-afa8-d4ceefa9a004.jp2');
INSERT INTO image VALUES (21596, '6a9890ff-c490-3cb9-97dd-ebde6bb50c92', '2012-01-20 12:01:26.588349', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0060.jpg', '/home/meditor/.meditor/images/6a9890ff-c490-3cb9-97dd-ebde6bb50c92.jp2');
INSERT INTO image VALUES (21597, '72dd8daf-e181-3a36-a85b-77e10f1a248f', '2012-01-20 12:01:26.589832', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0061.jpg', '/home/meditor/.meditor/images/72dd8daf-e181-3a36-a85b-77e10f1a248f.jp2');
INSERT INTO image VALUES (21552, 'e14699b3-a1a9-3226-b312-c987de700bbd', '2012-01-20 12:01:26.514287', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0016.jpg', '/home/meditor/.meditor/images/e14699b3-a1a9-3226-b312-c987de700bbd.jp2');
INSERT INTO image VALUES (21553, '2f5cff0d-b2bd-3159-99c3-14b3b8148e43', '2012-01-20 12:01:26.516035', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0017.jpg', '/home/meditor/.meditor/images/2f5cff0d-b2bd-3159-99c3-14b3b8148e43.jp2');
INSERT INTO image VALUES (21554, 'a312ff82-0fc5-3ea1-9795-a5ac522011ef', '2012-01-20 12:01:26.519732', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0018.jpg', '/home/meditor/.meditor/images/a312ff82-0fc5-3ea1-9795-a5ac522011ef.jp2');
INSERT INTO image VALUES (21598, 'a3007ce5-7b7c-359f-91ba-04b24a5098e2', '2012-01-20 12:01:26.59139', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0062.jpg', '/home/meditor/.meditor/images/a3007ce5-7b7c-359f-91ba-04b24a5098e2.jp2');
INSERT INTO image VALUES (21599, '4a4461c2-0088-3fdc-b990-a229282441cb', '2012-01-20 12:01:26.592927', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0063.jpg', '/home/meditor/.meditor/images/4a4461c2-0088-3fdc-b990-a229282441cb.jp2');
INSERT INTO image VALUES (21603, '138cab36-b938-3b65-b564-01cd59835493', '2012-01-20 12:01:26.600139', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0067.jpg', '/home/meditor/.meditor/images/138cab36-b938-3b65-b564-01cd59835493.jp2');
INSERT INTO image VALUES (21604, '8a0274cb-26f4-3549-b8ad-f475965f351d', '2012-01-20 12:01:26.601638', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0068.jpg', '/home/meditor/.meditor/images/8a0274cb-26f4-3549-b8ad-f475965f351d.jp2');
INSERT INTO image VALUES (21605, '5ee0b33a-6715-3da4-92c6-44288c3e5f4d', '2012-01-20 12:01:26.603414', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0069.jpg', '/home/meditor/.meditor/images/5ee0b33a-6715-3da4-92c6-44288c3e5f4d.jp2');
INSERT INTO image VALUES (21606, '5e7c4344-331c-37bc-9e97-37d256745ee5', '2012-01-20 12:01:26.605118', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0070.jpg', '/home/meditor/.meditor/images/5e7c4344-331c-37bc-9e97-37d256745ee5.jp2');
INSERT INTO image VALUES (21607, '6cb6543c-9e88-3917-ac79-0bfad228885d', '2012-01-20 12:01:26.606671', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0071.jpg', '/home/meditor/.meditor/images/6cb6543c-9e88-3917-ac79-0bfad228885d.jp2');
INSERT INTO image VALUES (21608, '60c01897-3069-3988-a357-a04340e651e7', '2012-01-20 12:01:26.608227', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0072.jpg', '/home/meditor/.meditor/images/60c01897-3069-3988-a357-a04340e651e7.jp2');
INSERT INTO image VALUES (21600, '25d5c58f-860c-3b68-a6ac-33a422a061e9', '2012-01-20 12:01:26.594397', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0064.jpg', '/home/meditor/.meditor/images/25d5c58f-860c-3b68-a6ac-33a422a061e9.jp2');
INSERT INTO image VALUES (21601, 'bf30acaf-6a37-35ac-a722-1bd25636a0ae', '2012-01-20 12:01:26.596011', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0065.jpg', '/home/meditor/.meditor/images/bf30acaf-6a37-35ac-a722-1bd25636a0ae.jp2');
INSERT INTO image VALUES (21602, '3fa95325-981d-332e-a99a-b3fbc01dfc78', '2012-01-20 12:01:26.598298', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0066.jpg', '/home/meditor/.meditor/images/3fa95325-981d-332e-a99a-b3fbc01dfc78.jp2');
INSERT INTO image VALUES (21726, '1ee7459b-74cc-3f31-8ec9-002cfe72151c', '2012-01-19 10:21:38.813961', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0002.jpg', '/home/meditor/.meditor/images/1ee7459b-74cc-3f31-8ec9-002cfe72151c.jp2');
INSERT INTO image VALUES (21727, '5e63cd54-a08b-3007-8ee1-317cc682ad18', '2012-01-19 10:21:38.815672', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0003.jpg', '/home/meditor/.meditor/images/5e63cd54-a08b-3007-8ee1-317cc682ad18.jp2');
INSERT INTO image VALUES (21728, 'fd75914e-37c6-317b-8bab-78586641bea3', '2012-01-19 10:21:38.836566', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0004.jpg', '/home/meditor/.meditor/images/fd75914e-37c6-317b-8bab-78586641bea3.jp2');
INSERT INTO image VALUES (21729, 'f2f581b3-98c6-3a11-bda4-dd7d8f9965c7', '2012-01-19 10:21:38.838187', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0005.jpg', '/home/meditor/.meditor/images/f2f581b3-98c6-3a11-bda4-dd7d8f9965c7.jp2');
INSERT INTO image VALUES (21730, '42d293d5-8d99-3064-8497-52418aef652b', '2012-01-19 10:21:38.839788', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0006.jpg', '/home/meditor/.meditor/images/42d293d5-8d99-3064-8497-52418aef652b.jp2');
INSERT INTO image VALUES (21731, '2a713a8a-c9ff-3d81-bb11-b65ec3403b6d', '2012-01-19 10:21:38.841336', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0007.jpg', '/home/meditor/.meditor/images/2a713a8a-c9ff-3d81-bb11-b65ec3403b6d.jp2');
INSERT INTO image VALUES (21732, 'cb0e767d-72fc-3da1-a9b3-5ab622cdf54d', '2012-01-19 10:21:38.842952', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0008.jpg', '/home/meditor/.meditor/images/cb0e767d-72fc-3da1-a9b3-5ab622cdf54d.jp2');
INSERT INTO image VALUES (21733, '6b565db2-f3d1-3b91-aa05-afdecc8b324f', '2012-01-19 10:21:38.84466', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0009.jpg', '/home/meditor/.meditor/images/6b565db2-f3d1-3b91-aa05-afdecc8b324f.jp2');
INSERT INTO image VALUES (21734, '413fe665-db5d-3da0-b34c-6a9757a372f7', '2012-01-19 10:21:38.846244', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0010.jpg', '/home/meditor/.meditor/images/413fe665-db5d-3da0-b34c-6a9757a372f7.jp2');
INSERT INTO image VALUES (21735, '29d042c1-8560-3dc5-bda7-fb11241964f8', '2012-01-19 10:21:38.847863', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0011.jpg', '/home/meditor/.meditor/images/29d042c1-8560-3dc5-bda7-fb11241964f8.jp2');
INSERT INTO image VALUES (21736, 'd0da7e0d-20bc-3942-a5c1-051f22321c64', '2012-01-19 10:21:38.849484', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0012.jpg', '/home/meditor/.meditor/images/d0da7e0d-20bc-3942-a5c1-051f22321c64.jp2');
INSERT INTO image VALUES (21737, 'd686f543-3934-3e38-a6a2-c90b6f5a6731', '2012-01-19 10:21:38.851146', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0013.jpg', '/home/meditor/.meditor/images/d686f543-3934-3e38-a6a2-c90b6f5a6731.jp2');
INSERT INTO image VALUES (21738, 'fb8c3a94-08e7-3b1a-9c5f-1f2cc10de1ed', '2012-01-19 10:21:38.852831', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0014.jpg', '/home/meditor/.meditor/images/fb8c3a94-08e7-3b1a-9c5f-1f2cc10de1ed.jp2');
INSERT INTO image VALUES (21739, '9f789e8f-c21e-3b2d-a139-16c736272739', '2012-01-19 10:21:38.85447', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0015.jpg', '/home/meditor/.meditor/images/9f789e8f-c21e-3b2d-a139-16c736272739.jp2');
INSERT INTO image VALUES (21740, '17efc688-ec79-38bd-a8da-52480e4b1cb8', '2012-01-19 10:21:38.856124', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0016.jpg', '/home/meditor/.meditor/images/17efc688-ec79-38bd-a8da-52480e4b1cb8.jp2');
INSERT INTO image VALUES (21741, 'dce86189-1923-37ad-a317-11f73fcba577', '2012-01-19 10:21:38.857738', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0017.jpg', '/home/meditor/.meditor/images/dce86189-1923-37ad-a317-11f73fcba577.jp2');
INSERT INTO image VALUES (21742, 'fccdcfa9-e1cf-3c73-a2ba-4d0bf40b6f83', '2012-01-19 10:21:38.859353', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0018.jpg', '/home/meditor/.meditor/images/fccdcfa9-e1cf-3c73-a2ba-4d0bf40b6f83.jp2');
INSERT INTO image VALUES (21743, '99a98ead-1c1d-3186-889b-1af7b5f187dd', '2012-01-19 10:21:38.864377', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0019.jpg', '/home/meditor/.meditor/images/99a98ead-1c1d-3186-889b-1af7b5f187dd.jp2');
INSERT INTO image VALUES (21744, '93b03b47-8f3c-37bb-9271-729e526f5a40', '2012-01-19 10:21:38.872399', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0020.jpg', '/home/meditor/.meditor/images/93b03b47-8f3c-37bb-9271-729e526f5a40.jp2');
INSERT INTO image VALUES (21745, 'f1c57b83-e40d-35fd-a1b0-43c8db0caabf', '2012-01-19 10:21:38.880619', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0021.jpg', '/home/meditor/.meditor/images/f1c57b83-e40d-35fd-a1b0-43c8db0caabf.jp2');
INSERT INTO image VALUES (21746, '98e81b29-d26e-34f4-849b-812f9393e7c7', '2012-01-19 10:21:38.882331', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0022.jpg', '/home/meditor/.meditor/images/98e81b29-d26e-34f4-849b-812f9393e7c7.jp2');
INSERT INTO image VALUES (21747, '990595d0-1d89-3162-9129-6ed7c90ff46a', '2012-01-19 10:21:38.884009', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0023.jpg', '/home/meditor/.meditor/images/990595d0-1d89-3162-9129-6ed7c90ff46a.jp2');
INSERT INTO image VALUES (21748, 'a0a5c6a2-7cc2-39d6-bf60-6b8af5c5ef21', '2012-01-19 10:21:38.885621', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0024.jpg', '/home/meditor/.meditor/images/a0a5c6a2-7cc2-39d6-bf60-6b8af5c5ef21.jp2');
INSERT INTO image VALUES (21749, '4a9cc0bd-fd75-3ebe-8b5a-7dfb3ac31a51', '2012-01-19 10:21:38.887384', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0025.jpg', '/home/meditor/.meditor/images/4a9cc0bd-fd75-3ebe-8b5a-7dfb3ac31a51.jp2');
INSERT INTO image VALUES (21750, '8d24c5f6-7833-3a4b-a5d5-a922b64c7407', '2012-01-19 10:21:38.889184', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0026.jpg', '/home/meditor/.meditor/images/8d24c5f6-7833-3a4b-a5d5-a922b64c7407.jp2');
INSERT INTO image VALUES (21751, '18e38e28-28c4-311e-bdb8-fa8d10b02355', '2012-01-19 10:21:38.890972', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0027.jpg', '/home/meditor/.meditor/images/18e38e28-28c4-311e-bdb8-fa8d10b02355.jp2');
INSERT INTO image VALUES (21752, '9e728660-2156-30bf-8131-98337b8128e4', '2012-01-19 10:21:38.892604', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0028.jpg', '/home/meditor/.meditor/images/9e728660-2156-30bf-8131-98337b8128e4.jp2');
INSERT INTO image VALUES (21753, '6a825a1f-6847-35c9-b86f-15e3eacd9e16', '2012-01-19 10:21:38.89422', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0029.jpg', '/home/meditor/.meditor/images/6a825a1f-6847-35c9-b86f-15e3eacd9e16.jp2');
INSERT INTO image VALUES (21754, '96e40b53-c1d5-3ab9-9796-62849625e5f3', '2012-01-19 10:21:38.904327', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0030.jpg', '/home/meditor/.meditor/images/96e40b53-c1d5-3ab9-9796-62849625e5f3.jp2');
INSERT INTO image VALUES (21755, '0ba4a8a8-7f1d-3f10-8277-749f2c4f52b3', '2012-01-19 10:21:38.905969', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0031.jpg', '/home/meditor/.meditor/images/0ba4a8a8-7f1d-3f10-8277-749f2c4f52b3.jp2');
INSERT INTO image VALUES (21756, '3cffdcc9-1716-3a27-842b-85f1afcd1ab6', '2012-01-19 10:21:38.907715', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0032.jpg', '/home/meditor/.meditor/images/3cffdcc9-1716-3a27-842b-85f1afcd1ab6.jp2');
INSERT INTO image VALUES (21757, '292081a1-0331-387f-b553-51b25339542b', '2012-01-19 10:21:38.909423', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0033.jpg', '/home/meditor/.meditor/images/292081a1-0331-387f-b553-51b25339542b.jp2');
INSERT INTO image VALUES (21758, 'cc497f6c-859e-329d-b99e-c0f865a6580a', '2012-01-19 10:21:38.911101', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0034.jpg', '/home/meditor/.meditor/images/cc497f6c-859e-329d-b99e-c0f865a6580a.jp2');
INSERT INTO image VALUES (21759, 'e8c2a453-5413-30a0-9d55-476290882040', '2012-01-19 10:21:38.912726', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0035.jpg', '/home/meditor/.meditor/images/e8c2a453-5413-30a0-9d55-476290882040.jp2');
INSERT INTO image VALUES (21762, '8cc41a8a-2865-34bb-9a12-0112790b2b36', '2012-01-19 10:21:38.918537', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0038.jpg', '/home/meditor/.meditor/images/8cc41a8a-2865-34bb-9a12-0112790b2b36.jp2');
INSERT INTO image VALUES (21763, '53056562-9643-36ca-9603-e943b67377a4', '2012-01-19 10:21:38.920225', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0039.jpg', '/home/meditor/.meditor/images/53056562-9643-36ca-9603-e943b67377a4.jp2');
INSERT INTO image VALUES (21764, '685eb798-f927-3631-ac26-1293112b598b', '2012-01-19 10:21:38.92183', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0040.jpg', '/home/meditor/.meditor/images/685eb798-f927-3631-ac26-1293112b598b.jp2');
INSERT INTO image VALUES (21765, '23bcdab9-65b2-31c8-8dee-889073fd0566', '2012-01-19 10:21:38.923442', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0041.jpg', '/home/meditor/.meditor/images/23bcdab9-65b2-31c8-8dee-889073fd0566.jp2');
INSERT INTO image VALUES (21766, 'feeba9d8-a427-3964-80d3-83dc138565f0', '2012-01-19 10:21:38.925316', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0042.jpg', '/home/meditor/.meditor/images/feeba9d8-a427-3964-80d3-83dc138565f0.jp2');
INSERT INTO image VALUES (21767, '42e9a9c0-340c-3130-b4a8-481f1a12a14c', '2012-01-19 10:21:38.92705', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0043.jpg', '/home/meditor/.meditor/images/42e9a9c0-340c-3130-b4a8-481f1a12a14c.jp2');
INSERT INTO image VALUES (21768, 'adbec399-4bec-338f-a56e-05e36c38f5fd', '2012-01-19 10:21:38.936244', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0044.jpg', '/home/meditor/.meditor/images/adbec399-4bec-338f-a56e-05e36c38f5fd.jp2');
INSERT INTO image VALUES (21769, 'eccc2a8d-b969-32ed-8f69-5522846959db', '2012-01-19 10:21:38.944194', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0045.jpg', '/home/meditor/.meditor/images/eccc2a8d-b969-32ed-8f69-5522846959db.jp2');
INSERT INTO image VALUES (21770, '22bb9b16-de0d-3856-9f54-00d90e10c78e', '2012-01-19 10:21:38.945827', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0046.jpg', '/home/meditor/.meditor/images/22bb9b16-de0d-3856-9f54-00d90e10c78e.jp2');
INSERT INTO image VALUES (21771, 'bda69504-32e2-3237-bc2d-f84cc7b4696b', '2012-01-19 10:21:38.947485', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0047.jpg', '/home/meditor/.meditor/images/bda69504-32e2-3237-bc2d-f84cc7b4696b.jp2');
INSERT INTO image VALUES (21772, 'd6d7ff1e-4b59-3b79-9e32-845634dfb024', '2012-01-19 10:21:38.949212', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0048.jpg', '/home/meditor/.meditor/images/d6d7ff1e-4b59-3b79-9e32-845634dfb024.jp2');
INSERT INTO image VALUES (21773, '2b22f626-a1a2-3fe5-8505-d440c4918883', '2012-01-19 10:21:38.950804', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0049.jpg', '/home/meditor/.meditor/images/2b22f626-a1a2-3fe5-8505-d440c4918883.jp2');
INSERT INTO image VALUES (21774, '128d2136-cee5-3b4a-9221-a1500abd9ee2', '2012-01-19 10:21:38.952483', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0050.jpg', '/home/meditor/.meditor/images/128d2136-cee5-3b4a-9221-a1500abd9ee2.jp2');
INSERT INTO image VALUES (21775, '567f35a7-09f6-308e-a9fb-54b5eca1cbb8', '2012-01-19 10:21:38.95689', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0051.jpg', '/home/meditor/.meditor/images/567f35a7-09f6-308e-a9fb-54b5eca1cbb8.jp2');
INSERT INTO image VALUES (21776, '514bbcf6-34b2-3939-b083-1aa5b325f51f', '2012-01-19 10:21:38.958816', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0052.jpg', '/home/meditor/.meditor/images/514bbcf6-34b2-3939-b083-1aa5b325f51f.jp2');
INSERT INTO image VALUES (21777, '5c54a65a-32cf-392b-a68c-4358d4190c9b', '2012-01-19 10:21:38.960573', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0053.jpg', '/home/meditor/.meditor/images/5c54a65a-32cf-392b-a68c-4358d4190c9b.jp2');
INSERT INTO image VALUES (21778, '0d804c12-d67f-381d-aeff-ddcfc4516eb9', '2012-01-19 10:21:38.968126', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0054.jpg', '/home/meditor/.meditor/images/0d804c12-d67f-381d-aeff-ddcfc4516eb9.jp2');
INSERT INTO image VALUES (21779, '891c4420-8ea1-383d-a282-0fe29f7df270', '2012-01-19 10:21:38.969746', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0055.jpg', '/home/meditor/.meditor/images/891c4420-8ea1-383d-a282-0fe29f7df270.jp2');
INSERT INTO image VALUES (21780, 'ab6c9d00-8eb8-39e5-b6a1-3a8849bea70c', '2012-01-19 10:21:38.976051', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0056.jpg', '/home/meditor/.meditor/images/ab6c9d00-8eb8-39e5-b6a1-3a8849bea70c.jp2');
INSERT INTO image VALUES (21781, '347a10b3-07c9-31a9-ae0b-069300021085', '2012-01-19 10:21:38.977623', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0057.jpg', '/home/meditor/.meditor/images/347a10b3-07c9-31a9-ae0b-069300021085.jp2');
INSERT INTO image VALUES (21782, '286bfbea-af96-3667-9804-106df154c7cb', '2012-01-19 10:21:38.979185', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0058.jpg', '/home/meditor/.meditor/images/286bfbea-af96-3667-9804-106df154c7cb.jp2');
INSERT INTO image VALUES (21783, '7dcad29f-f418-32d8-8a7d-8225a721ef96', '2012-01-19 10:21:38.980767', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0059.jpg', '/home/meditor/.meditor/images/7dcad29f-f418-32d8-8a7d-8225a721ef96.jp2');
INSERT INTO image VALUES (21784, '9591e867-f478-325b-8a7f-f268d248d38b', '2012-01-19 10:21:38.982493', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0060.jpg', '/home/meditor/.meditor/images/9591e867-f478-325b-8a7f-f268d248d38b.jp2');
INSERT INTO image VALUES (21785, 'a24fd89b-7d78-3979-9352-10d546557e1e', '2012-01-19 10:21:38.984137', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0061.jpg', '/home/meditor/.meditor/images/a24fd89b-7d78-3979-9352-10d546557e1e.jp2');
INSERT INTO image VALUES (21786, 'c8a3ced0-0588-3b47-a296-ac1425e3f09d', '2012-01-19 10:21:38.992312', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0062.jpg', '/home/meditor/.meditor/images/c8a3ced0-0588-3b47-a296-ac1425e3f09d.jp2');
INSERT INTO image VALUES (21787, 'a351820b-2373-3798-8a5c-d356883bda75', '2012-01-19 10:21:38.993897', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0063.jpg', '/home/meditor/.meditor/images/a351820b-2373-3798-8a5c-d356883bda75.jp2');
INSERT INTO image VALUES (21788, '86efedfe-d6a3-3b2f-95d7-209de19994ee', '2012-01-19 10:21:38.995534', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0064.jpg', '/home/meditor/.meditor/images/86efedfe-d6a3-3b2f-95d7-209de19994ee.jp2');
INSERT INTO image VALUES (21789, '7b036487-4703-310f-84d6-94c5020914a9', '2012-01-19 10:21:38.997131', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0065.jpg', '/home/meditor/.meditor/images/7b036487-4703-310f-84d6-94c5020914a9.jp2');
INSERT INTO image VALUES (21790, 'a1207846-bbdc-3d12-90ad-50295839a85f', '2012-01-19 10:21:38.99874', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0066.jpg', '/home/meditor/.meditor/images/a1207846-bbdc-3d12-90ad-50295839a85f.jp2');
INSERT INTO image VALUES (21791, 'e1d27d40-a60c-3b8b-b638-ec17479e28be', '2012-01-19 10:21:39.008439', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0067.jpg', '/home/meditor/.meditor/images/e1d27d40-a60c-3b8b-b638-ec17479e28be.jp2');
INSERT INTO image VALUES (21792, '728f60a4-9c80-3653-9806-14e3d4a1ca38', '2012-01-19 10:21:39.010079', '/home/meditor/input/periodical/000173829/2610382158/38-2009-04/0068.jpg', '/home/meditor/.meditor/images/728f60a4-9c80-3653-9806-14e3d4a1ca38.jp2');
INSERT INTO image VALUES (21614, 'd70c98b7-f9a5-35a6-98a9-12827b649393', '2012-01-19 12:20:17.189512', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0002.jpg', '/home/meditor/.meditor/images/d70c98b7-f9a5-35a6-98a9-12827b649393.jp2');
INSERT INTO image VALUES (21620, '4403201b-7cc0-3803-8807-b104d377a541', '2012-01-19 12:20:17.229217', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0008.jpg', '/home/meditor/.meditor/images/4403201b-7cc0-3803-8807-b104d377a541.jp2');
INSERT INTO image VALUES (21655, '6b8fd470-33e7-3675-89aa-bbac13a9d262', '2012-01-19 12:20:17.330004', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0043.jpg', '/home/meditor/.meditor/images/6b8fd470-33e7-3675-89aa-bbac13a9d262.jp2');
INSERT INTO image VALUES (21702, 'afe492f9-5fa1-3955-9ad7-ca56530e9cb2', '2012-01-19 12:20:17.441058', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0090.jpg', '/home/meditor/.meditor/images/afe492f9-5fa1-3955-9ad7-ca56530e9cb2.jp2');
INSERT INTO image VALUES (21703, '938231cf-688e-334e-b709-11bffd069ad0', '2012-01-19 12:20:17.442608', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0091.jpg', '/home/meditor/.meditor/images/938231cf-688e-334e-b709-11bffd069ad0.jp2');
INSERT INTO image VALUES (21689, '573f13d4-63bf-3686-8c06-5d1fbd49a848', '2012-01-19 12:20:17.40955', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0077.jpg', '/home/meditor/.meditor/images/573f13d4-63bf-3686-8c06-5d1fbd49a848.jp2');
INSERT INTO image VALUES (21690, '474cf4a7-f43e-38f3-ab10-3acabcad6531', '2012-01-19 12:20:17.411112', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0078.jpg', '/home/meditor/.meditor/images/474cf4a7-f43e-38f3-ab10-3acabcad6531.jp2');
INSERT INTO image VALUES (21691, '1cb6a039-c873-337b-8ecc-bccae43551c7', '2012-01-19 12:20:17.412594', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0079.jpg', '/home/meditor/.meditor/images/1cb6a039-c873-337b-8ecc-bccae43551c7.jp2');
INSERT INTO image VALUES (21692, 'a500ea3c-00f6-3043-8aae-ba7c856ed8dd', '2012-01-19 12:20:17.414072', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0080.jpg', '/home/meditor/.meditor/images/a500ea3c-00f6-3043-8aae-ba7c856ed8dd.jp2');
INSERT INTO image VALUES (21693, '8ba66c7f-cb97-31ce-a462-e284e6b90a7e', '2012-01-19 12:20:17.415565', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0081.jpg', '/home/meditor/.meditor/images/8ba66c7f-cb97-31ce-a462-e284e6b90a7e.jp2');
INSERT INTO image VALUES (21694, '7fed3896-f12e-37ea-abd3-5c7fe030cc4c', '2012-01-19 12:20:17.429433', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0082.jpg', '/home/meditor/.meditor/images/7fed3896-f12e-37ea-abd3-5c7fe030cc4c.jp2');
INSERT INTO image VALUES (21695, '4b353eb1-f7d1-3e3d-a108-c5b6b3ad2c41', '2012-01-19 12:20:17.430938', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0083.jpg', '/home/meditor/.meditor/images/4b353eb1-f7d1-3e3d-a108-c5b6b3ad2c41.jp2');
INSERT INTO image VALUES (21696, '55fceb1d-0420-3926-bf42-2c22d187eae5', '2012-01-19 12:20:17.432413', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0084.jpg', '/home/meditor/.meditor/images/55fceb1d-0420-3926-bf42-2c22d187eae5.jp2');
INSERT INTO image VALUES (21697, '8d9665b9-dee4-3962-9ce1-cbe874b0fc15', '2012-01-19 12:20:17.433847', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0085.jpg', '/home/meditor/.meditor/images/8d9665b9-dee4-3962-9ce1-cbe874b0fc15.jp2');
INSERT INTO image VALUES (21698, '860f73dd-4e4a-3dc4-ae3f-4c90ccbd5a44', '2012-01-19 12:20:17.435254', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0086.jpg', '/home/meditor/.meditor/images/860f73dd-4e4a-3dc4-ae3f-4c90ccbd5a44.jp2');
INSERT INTO image VALUES (21699, '3920e1a5-8ebf-3007-a022-53cd9f239a03', '2012-01-19 12:20:17.436701', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0087.jpg', '/home/meditor/.meditor/images/3920e1a5-8ebf-3007-a022-53cd9f239a03.jp2');
INSERT INTO image VALUES (21700, 'c9ad2edc-6f6a-3980-a75a-625ad140c01c', '2012-01-19 12:20:17.438115', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0088.jpg', '/home/meditor/.meditor/images/c9ad2edc-6f6a-3980-a75a-625ad140c01c.jp2');
INSERT INTO image VALUES (21701, 'a74a37eb-5294-3a4c-9557-c92ac01fa4dc', '2012-01-19 12:20:17.439603', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0089.jpg', '/home/meditor/.meditor/images/a74a37eb-5294-3a4c-9557-c92ac01fa4dc.jp2');
INSERT INTO image VALUES (21537, '470508e3-929c-397d-bafe-800048dc81b0', '2012-01-20 12:01:26.489763', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0001.jpg', '/home/meditor/.meditor/images/470508e3-929c-397d-bafe-800048dc81b0.jp2');
INSERT INTO image VALUES (21572, '76a3bc43-6cf0-3da6-b8fd-62224d4813da', '2012-01-20 12:01:26.548944', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0036.jpg', '/home/meditor/.meditor/images/76a3bc43-6cf0-3da6-b8fd-62224d4813da.jp2');
INSERT INTO image VALUES (21609, 'bb5ea62c-695d-361d-a370-36ed250fa099', '2012-01-20 12:01:26.60989', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0073.jpg', '/home/meditor/.meditor/images/bb5ea62c-695d-361d-a370-36ed250fa099.jp2');
INSERT INTO image VALUES (21706, '990683ef-4c11-39ac-954a-296cf776528c', '2012-01-19 12:20:17.187524', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0001.jpg', '/home/meditor/.meditor/images/990683ef-4c11-39ac-954a-296cf776528c.jp2');
INSERT INTO image VALUES (21717, '69d0db40-a308-3446-915d-9e19dce2e1f6', '2012-01-19 12:20:17.218528', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0006.jpg', '/home/meditor/.meditor/images/69d0db40-a308-3446-915d-9e19dce2e1f6.jp2');
INSERT INTO image VALUES (21619, '10930ea7-93fc-3b5e-9b38-6809bb22cfd4', '2012-01-19 12:20:17.226527', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0007.jpg', '/home/meditor/.meditor/images/10930ea7-93fc-3b5e-9b38-6809bb22cfd4.jp2');
INSERT INTO image VALUES (21610, '21d094ee-0468-3fce-b547-32de1eb15bac', '2012-01-20 12:01:26.611703', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0074.jpg', '/home/meditor/.meditor/images/21d094ee-0468-3fce-b547-32de1eb15bac.jp2');
INSERT INTO image VALUES (21611, '223d9f68-030b-3260-849d-c8057b923b0d', '2012-01-20 12:01:26.613471', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0075.jpg', '/home/meditor/.meditor/images/223d9f68-030b-3260-849d-c8057b923b0d.jp2');
INSERT INTO image VALUES (21612, '0e605bc8-49ad-3cc5-ad3d-ebfa7020db7b', '2012-01-20 12:01:26.615169', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0076.jpg', '/home/meditor/.meditor/images/0e605bc8-49ad-3cc5-ad3d-ebfa7020db7b.jp2');
INSERT INTO image VALUES (21654, '8804c426-c10c-3e09-b35f-bb6353003353', '2012-01-19 12:20:17.328495', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0042.jpg', '/home/meditor/.meditor/images/8804c426-c10c-3e09-b35f-bb6353003353.jp2');
INSERT INTO image VALUES (21721, '599c4e02-2340-3ada-a5fb-16c3bcc5d8b0', '2012-01-19 12:20:17.262973', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0022.jpg', '/home/meditor/.meditor/images/599c4e02-2340-3ada-a5fb-16c3bcc5d8b0.jp2');
INSERT INTO image VALUES (21635, '613026ff-bd75-3132-be4d-20c615db6c31', '2012-01-19 12:20:17.264429', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0023.jpg', '/home/meditor/.meditor/images/613026ff-bd75-3132-be4d-20c615db6c31.jp2');
INSERT INTO image VALUES (21712, '327c10d4-e77b-3250-ac90-7aaaba5383ab', '2012-01-19 12:20:17.265944', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0024.jpg', '/home/meditor/.meditor/images/327c10d4-e77b-3250-ac90-7aaaba5383ab.jp2');
INSERT INTO image VALUES (21722, 'f1ab82a6-3f50-3236-9df5-730202501828', '2012-01-19 12:20:17.274271', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0025.jpg', '/home/meditor/.meditor/images/f1ab82a6-3f50-3236-9df5-730202501828.jp2');
INSERT INTO image VALUES (21615, 'adbcee7f-009e-3d61-8a20-8afc19fc8a36', '2012-01-19 12:20:17.20638', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0003.jpg', '/home/meditor/.meditor/images/adbcee7f-009e-3d61-8a20-8afc19fc8a36.jp2');
INSERT INTO image VALUES (21616, '45e563d0-b00e-3d07-beed-26efe79de7de', '2012-01-19 12:20:17.208021', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0004.jpg', '/home/meditor/.meditor/images/45e563d0-b00e-3d07-beed-26efe79de7de.jp2');
INSERT INTO image VALUES (21617, '5dbe4b5a-1678-3022-a639-d78c1a0e38d2', '2012-01-19 12:20:17.209515', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0005.jpg', '/home/meditor/.meditor/images/5dbe4b5a-1678-3022-a639-d78c1a0e38d2.jp2');
INSERT INTO image VALUES (21621, '494e786a-922a-32e3-aae5-1ab93289b3fe', '2012-01-19 12:20:17.230708', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0009.jpg', '/home/meditor/.meditor/images/494e786a-922a-32e3-aae5-1ab93289b3fe.jp2');
INSERT INTO image VALUES (21622, 'fbb1f65b-9462-341b-98b1-695bf57b49bd', '2012-01-19 12:20:17.232157', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0010.jpg', '/home/meditor/.meditor/images/fbb1f65b-9462-341b-98b1-695bf57b49bd.jp2');
INSERT INTO image VALUES (21623, '0027dc34-972d-34e7-a110-49cd890df259', '2012-01-19 12:20:17.246407', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0011.jpg', '/home/meditor/.meditor/images/0027dc34-972d-34e7-a110-49cd890df259.jp2');
INSERT INTO image VALUES (21718, '9d75a87b-cb15-37cb-ae51-ebb8182ab5e8', '2012-01-19 12:20:17.247976', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0012.jpg', '/home/meditor/.meditor/images/9d75a87b-cb15-37cb-ae51-ebb8182ab5e8.jp2');
INSERT INTO image VALUES (21625, '0cda1caf-bdb8-3759-83d4-50d939279163', '2012-01-19 12:20:17.249448', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0013.jpg', '/home/meditor/.meditor/images/0cda1caf-bdb8-3759-83d4-50d939279163.jp2');
INSERT INTO image VALUES (21626, '3b92f004-dd44-3dc1-8bee-4f40ed06ca73', '2012-01-19 12:20:17.250995', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0014.jpg', '/home/meditor/.meditor/images/3b92f004-dd44-3dc1-8bee-4f40ed06ca73.jp2');
INSERT INTO image VALUES (21627, 'bcf711d9-cefa-3441-9c9e-b375a4dee346', '2012-01-19 12:20:17.252451', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0015.jpg', '/home/meditor/.meditor/images/bcf711d9-cefa-3441-9c9e-b375a4dee346.jp2');
INSERT INTO image VALUES (21628, '2b1a1d7a-8065-3730-bc94-db5f9d0ade03', '2012-01-19 12:20:17.25401', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0016.jpg', '/home/meditor/.meditor/images/2b1a1d7a-8065-3730-bc94-db5f9d0ade03.jp2');
INSERT INTO image VALUES (21629, 'a54f47d6-4366-32c2-812b-ec6280754d60', '2012-01-19 12:20:17.255524', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0017.jpg', '/home/meditor/.meditor/images/a54f47d6-4366-32c2-812b-ec6280754d60.jp2');
INSERT INTO image VALUES (21630, 'c48485f3-1ba7-3f07-9a2b-7900626aff44', '2012-01-19 12:20:17.257054', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0018.jpg', '/home/meditor/.meditor/images/c48485f3-1ba7-3f07-9a2b-7900626aff44.jp2');
INSERT INTO image VALUES (21631, '3bbbcc36-43ec-3129-88d2-99bcad0e8a21', '2012-01-19 12:20:17.258507', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0019.jpg', '/home/meditor/.meditor/images/3bbbcc36-43ec-3129-88d2-99bcad0e8a21.jp2');
INSERT INTO image VALUES (21632, '64209d94-104e-3258-b26c-5d6cb5287c4c', '2012-01-19 12:20:17.260007', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0020.jpg', '/home/meditor/.meditor/images/64209d94-104e-3258-b26c-5d6cb5287c4c.jp2');
INSERT INTO image VALUES (21633, 'bcbb1087-76b1-302c-959a-8b16d282c6ac', '2012-01-19 12:20:17.26147', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0021.jpg', '/home/meditor/.meditor/images/bcbb1087-76b1-302c-959a-8b16d282c6ac.jp2');
INSERT INTO image VALUES (21720, '52b4e119-eb8c-3239-bce7-54f20d3d2d3a', '2012-01-19 12:20:17.306496', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0028.jpg', '/home/meditor/.meditor/images/52b4e119-eb8c-3239-bce7-54f20d3d2d3a.jp2');
INSERT INTO image VALUES (21641, 'c6b5cf09-0d9a-3341-a5f1-30410073734b', '2012-01-19 12:20:17.308055', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0029.jpg', '/home/meditor/.meditor/images/c6b5cf09-0d9a-3341-a5f1-30410073734b.jp2');
INSERT INTO image VALUES (21705, 'c7929805-cfc7-3374-9961-2020ab19c662', '2012-01-19 12:20:17.309536', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0030.jpg', '/home/meditor/.meditor/images/c7929805-cfc7-3374-9961-2020ab19c662.jp2');
INSERT INTO image VALUES (21643, '93902197-f52f-336a-a039-1dee8a529971', '2012-01-19 12:20:17.311177', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0031.jpg', '/home/meditor/.meditor/images/93902197-f52f-336a-a039-1dee8a529971.jp2');
INSERT INTO image VALUES (21644, '2055bfda-1e9c-38e9-a22c-11749731aff5', '2012-01-19 12:20:17.312718', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0032.jpg', '/home/meditor/.meditor/images/2055bfda-1e9c-38e9-a22c-11749731aff5.jp2');
INSERT INTO image VALUES (21645, '28cfc7bf-4ca2-328f-abc4-9f1db36a105a', '2012-01-19 12:20:17.314234', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0033.jpg', '/home/meditor/.meditor/images/28cfc7bf-4ca2-328f-abc4-9f1db36a105a.jp2');
INSERT INTO image VALUES (21646, '747196e6-bab9-3331-9b28-f7e62d0bc38b', '2012-01-19 12:20:17.315819', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0034.jpg', '/home/meditor/.meditor/images/747196e6-bab9-3331-9b28-f7e62d0bc38b.jp2');
INSERT INTO image VALUES (21647, '59363520-c5ae-36dd-b46e-9c417f0ecc5e', '2012-01-19 12:20:17.317288', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0035.jpg', '/home/meditor/.meditor/images/59363520-c5ae-36dd-b46e-9c417f0ecc5e.jp2');
INSERT INTO image VALUES (21648, 'e9d4fb0d-724f-31c7-a12d-b8a79e6b016b', '2012-01-19 12:20:17.318749', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0036.jpg', '/home/meditor/.meditor/images/e9d4fb0d-724f-31c7-a12d-b8a79e6b016b.jp2');
INSERT INTO image VALUES (21649, '15adb5d6-7282-39bd-8241-2f8fc5b36f01', '2012-01-19 12:20:17.320619', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0037.jpg', '/home/meditor/.meditor/images/15adb5d6-7282-39bd-8241-2f8fc5b36f01.jp2');
INSERT INTO image VALUES (21714, '173f19d1-59cb-3e9f-b1c4-b9aec4a742c2', '2012-01-19 12:20:17.322117', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0038.jpg', '/home/meditor/.meditor/images/173f19d1-59cb-3e9f-b1c4-b9aec4a742c2.jp2');
INSERT INTO image VALUES (21651, '65c19024-a010-3625-b8ff-1b4b7398f170', '2012-01-19 12:20:17.323675', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0039.jpg', '/home/meditor/.meditor/images/65c19024-a010-3625-b8ff-1b4b7398f170.jp2');
INSERT INTO image VALUES (21652, 'b84224f3-ffb3-3b7a-9fa3-97bc91b8c304', '2012-01-19 12:20:17.325311', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0040.jpg', '/home/meditor/.meditor/images/b84224f3-ffb3-3b7a-9fa3-97bc91b8c304.jp2');
INSERT INTO image VALUES (21653, '6ad6f56b-5a91-3feb-a425-81156ec4846a', '2012-01-19 12:20:17.326893', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0041.jpg', '/home/meditor/.meditor/images/6ad6f56b-5a91-3feb-a425-81156ec4846a.jp2');
INSERT INTO image VALUES (21656, '580ac5fb-3142-3fa4-9d76-7f74431cb094', '2012-01-19 12:20:17.331488', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0044.jpg', '/home/meditor/.meditor/images/580ac5fb-3142-3fa4-9d76-7f74431cb094.jp2');
INSERT INTO image VALUES (21657, '4424eaec-5c9e-38e8-946f-942f6d90c339', '2012-01-19 12:20:17.332887', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0045.jpg', '/home/meditor/.meditor/images/4424eaec-5c9e-38e8-946f-942f6d90c339.jp2');
INSERT INTO image VALUES (21658, 'b6f099c4-f8b1-3a23-9e05-9ea32c285b98', '2012-01-19 12:20:17.33434', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0046.jpg', '/home/meditor/.meditor/images/b6f099c4-f8b1-3a23-9e05-9ea32c285b98.jp2');
INSERT INTO image VALUES (21659, '8de9923a-a732-3ff2-9cdb-6d7f2a705a7e', '2012-01-19 12:20:17.335851', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0047.jpg', '/home/meditor/.meditor/images/8de9923a-a732-3ff2-9cdb-6d7f2a705a7e.jp2');
INSERT INTO image VALUES (21660, 'c5a7b06b-5833-39d5-8eb3-9e5e0bd4e95b', '2012-01-19 12:20:17.337316', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0048.jpg', '/home/meditor/.meditor/images/c5a7b06b-5833-39d5-8eb3-9e5e0bd4e95b.jp2');
INSERT INTO image VALUES (21661, 'a9fa45d8-2504-3592-9377-e0325eed3aa6', '2012-01-19 12:20:17.338771', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0049.jpg', '/home/meditor/.meditor/images/a9fa45d8-2504-3592-9377-e0325eed3aa6.jp2');
INSERT INTO image VALUES (21662, '160ae33b-673c-3192-a497-918b8e90e5b6', '2012-01-19 12:20:17.340218', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0050.jpg', '/home/meditor/.meditor/images/160ae33b-673c-3192-a497-918b8e90e5b6.jp2');
INSERT INTO image VALUES (21663, '7444e95d-e9c3-3c92-982d-ff72caba6563', '2012-01-19 12:20:17.34171', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0051.jpg', '/home/meditor/.meditor/images/7444e95d-e9c3-3c92-982d-ff72caba6563.jp2');
INSERT INTO image VALUES (21664, '4e305597-d407-3560-ada8-7adda83b3a34', '2012-01-19 12:20:17.343176', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0052.jpg', '/home/meditor/.meditor/images/4e305597-d407-3560-ada8-7adda83b3a34.jp2');
INSERT INTO image VALUES (21710, '33bd8dae-5ae6-34b4-952a-b3eed67e77d9', '2012-01-19 12:20:17.34469', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0053.jpg', '/home/meditor/.meditor/images/33bd8dae-5ae6-34b4-952a-b3eed67e77d9.jp2');
INSERT INTO image VALUES (21666, '7460e63c-37af-3552-9608-25bc9bec9001', '2012-01-19 12:20:17.346255', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0054.jpg', '/home/meditor/.meditor/images/7460e63c-37af-3552-9608-25bc9bec9001.jp2');
INSERT INTO image VALUES (21667, '7263f2dc-4b4d-39b0-bbab-8141a6c3e0b2', '2012-01-19 12:20:17.347785', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0055.jpg', '/home/meditor/.meditor/images/7263f2dc-4b4d-39b0-bbab-8141a6c3e0b2.jp2');
INSERT INTO image VALUES (21711, 'bafa4a4a-f1f3-305e-bb64-e66a853699db', '2012-01-19 12:20:17.349357', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0056.jpg', '/home/meditor/.meditor/images/bafa4a4a-f1f3-305e-bb64-e66a853699db.jp2');
INSERT INTO image VALUES (21669, '73ac0748-008d-376d-9865-22fd89fe5c3f', '2012-01-19 12:20:17.350956', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0057.jpg', '/home/meditor/.meditor/images/73ac0748-008d-376d-9865-22fd89fe5c3f.jp2');
INSERT INTO image VALUES (21670, 'c4443acd-0222-3f59-ba9c-d11cca86d339', '2012-01-19 12:20:17.352506', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0058.jpg', '/home/meditor/.meditor/images/c4443acd-0222-3f59-ba9c-d11cca86d339.jp2');
INSERT INTO image VALUES (21671, '804879e4-5226-3875-96f5-250acd32fbaa', '2012-01-19 12:20:17.354045', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0059.jpg', '/home/meditor/.meditor/images/804879e4-5226-3875-96f5-250acd32fbaa.jp2');
INSERT INTO image VALUES (21672, '645e70e4-7cf0-3a4e-9da8-1b7b6c77b08d', '2012-01-19 12:20:17.355605', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0060.jpg', '/home/meditor/.meditor/images/645e70e4-7cf0-3a4e-9da8-1b7b6c77b08d.jp2');
INSERT INTO image VALUES (21673, '2452a652-81c6-3370-bfb1-5e5a87e13569', '2012-01-19 12:20:17.35711', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0061.jpg', '/home/meditor/.meditor/images/2452a652-81c6-3370-bfb1-5e5a87e13569.jp2');
INSERT INTO image VALUES (21674, '9a364a64-1212-3ac8-be33-22404f431e55', '2012-01-19 12:20:17.358742', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0062.jpg', '/home/meditor/.meditor/images/9a364a64-1212-3ac8-be33-22404f431e55.jp2');
INSERT INTO image VALUES (21639, 'c35476b3-9814-39fb-ab39-a03af007f78a', '2012-01-19 12:20:17.290435', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0027.jpg', '/home/meditor/.meditor/images/c35476b3-9814-39fb-ab39-a03af007f78a.jp2');
INSERT INTO image VALUES (21719, '934eb8c9-8762-3bf7-9af4-fc8b67ee20fa', '2012-01-19 12:20:17.282328', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0026.jpg', '/home/meditor/.meditor/images/934eb8c9-8762-3bf7-9af4-fc8b67ee20fa.jp2');
INSERT INTO image VALUES (21675, 'b16c3cc9-c5a2-300a-9773-74cbb4d6eae8', '2012-01-19 12:20:17.36031', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0063.jpg', '/home/meditor/.meditor/images/b16c3cc9-c5a2-300a-9773-74cbb4d6eae8.jp2');
INSERT INTO image VALUES (21676, '8722e943-8ea2-3917-ac93-aa24980ee843', '2012-01-19 12:20:17.362005', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0064.jpg', '/home/meditor/.meditor/images/8722e943-8ea2-3917-ac93-aa24980ee843.jp2');
INSERT INTO image VALUES (21677, '0176c9d0-d74c-36b5-a3de-9c7857c49261', '2012-01-19 12:20:17.363594', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0065.jpg', '/home/meditor/.meditor/images/0176c9d0-d74c-36b5-a3de-9c7857c49261.jp2');
INSERT INTO image VALUES (21678, '23eedbfc-2fc8-3f84-8fe8-acc2b98f54f9', '2012-01-19 12:20:17.365158', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0066.jpg', '/home/meditor/.meditor/images/23eedbfc-2fc8-3f84-8fe8-acc2b98f54f9.jp2');
INSERT INTO image VALUES (21679, 'f9e3b18f-a0c4-3f1c-9a4c-758e4a9035da', '2012-01-19 12:20:17.366757', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0067.jpg', '/home/meditor/.meditor/images/f9e3b18f-a0c4-3f1c-9a4c-758e4a9035da.jp2');
INSERT INTO image VALUES (21680, '3b248314-e6e5-3a4b-81c0-d858dd28357c', '2012-01-19 12:20:17.368296', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0068.jpg', '/home/meditor/.meditor/images/3b248314-e6e5-3a4b-81c0-d858dd28357c.jp2');
INSERT INTO image VALUES (21682, 'c29cc869-5431-38db-961f-b4bc6209b932', '2012-01-19 12:20:17.371429', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0070.jpg', '/home/meditor/.meditor/images/c29cc869-5431-38db-961f-b4bc6209b932.jp2');
INSERT INTO image VALUES (21683, '74c85fe8-5de2-35af-a55d-f71037bbc366', '2012-01-19 12:20:17.381506', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0071.jpg', '/home/meditor/.meditor/images/74c85fe8-5de2-35af-a55d-f71037bbc366.jp2');
INSERT INTO image VALUES (21684, '192fd95b-9c68-3238-8207-3a71a2825ae4', '2012-01-19 12:20:17.383022', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0072.jpg', '/home/meditor/.meditor/images/192fd95b-9c68-3238-8207-3a71a2825ae4.jp2');
INSERT INTO image VALUES (21685, '258ea92a-5619-3d53-bd33-91e654dfef84', '2012-01-19 12:20:17.384477', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0073.jpg', '/home/meditor/.meditor/images/258ea92a-5619-3d53-bd33-91e654dfef84.jp2');
INSERT INTO image VALUES (22413, '7604155f-7dd2-3f71-9bb8-2c7d1e8de116', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0001.jpg', '/home/meditor/.meditor/images/7604155f-7dd2-3f71-9bb8-2c7d1e8de116.jp2');
INSERT INTO image VALUES (22414, 'e256c353-cb0b-395f-9680-939ef7e5d4c2', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0002.jpg', '/home/meditor/.meditor/images/e256c353-cb0b-395f-9680-939ef7e5d4c2.jp2');
INSERT INTO image VALUES (22415, 'f4eebab1-b8d5-3796-bf0f-5d8992f424a8', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0003.jpg', '/home/meditor/.meditor/images/f4eebab1-b8d5-3796-bf0f-5d8992f424a8.jp2');
INSERT INTO image VALUES (22416, '6a873df0-5a8e-3d2c-b860-2a3ae4a186e5', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0004.jpg', '/home/meditor/.meditor/images/6a873df0-5a8e-3d2c-b860-2a3ae4a186e5.jp2');
INSERT INTO image VALUES (22417, '1290cdd7-3ded-3cfb-a805-b1680a341d51', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0005.jpg', '/home/meditor/.meditor/images/1290cdd7-3ded-3cfb-a805-b1680a341d51.jp2');
INSERT INTO image VALUES (21686, 'f7f52b44-ebcc-3047-82ff-27faf21042f9', '2012-01-19 12:20:17.385927', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0074.jpg', '/home/meditor/.meditor/images/f7f52b44-ebcc-3047-82ff-27faf21042f9.jp2');
INSERT INTO image VALUES (21687, 'aa392b90-72be-39c3-bfbd-da344fecb09b', '2012-01-19 12:20:17.387396', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0075.jpg', '/home/meditor/.meditor/images/aa392b90-72be-39c3-bfbd-da344fecb09b.jp2');
INSERT INTO image VALUES (21688, '53e0ed96-2b63-3f86-af96-2dbfe7c3aad0', '2012-01-19 12:20:17.405509', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0076.jpg', '/home/meditor/.meditor/images/53e0ed96-2b63-3f86-af96-2dbfe7c3aad0.jp2');
INSERT INTO image VALUES (21704, 'da966abb-2835-304b-816a-107818d2650e', '2012-01-19 12:20:17.444109', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0092.jpg', '/home/meditor/.meditor/images/da966abb-2835-304b-816a-107818d2650e.jp2');
INSERT INTO image VALUES (21681, '375a6231-d51e-36df-9c8e-535b1974aa82', '2012-01-19 12:20:17.369866', '/home/meditor/input/periodical/000173829/2610382158/38-2009-01_02/0069.jpg', '/home/meditor/.meditor/images/375a6231-d51e-36df-9c8e-535b1974aa82.jp2');
INSERT INTO image VALUES (22418, '1adf71d3-5c73-31f4-802c-17647cff49f8', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0006.jpg', '/home/meditor/.meditor/images/1adf71d3-5c73-31f4-802c-17647cff49f8.jp2');
INSERT INTO image VALUES (22419, '415cf97c-7907-34bd-9273-ee2633e4fe90', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0007.jpg', '/home/meditor/.meditor/images/415cf97c-7907-34bd-9273-ee2633e4fe90.jp2');
INSERT INTO image VALUES (22420, '05215660-90ab-3ed0-872e-ce3aba9564dc', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0008.jpg', '/home/meditor/.meditor/images/05215660-90ab-3ed0-872e-ce3aba9564dc.jp2');
INSERT INTO image VALUES (22421, '3a7f1589-a0d9-3dd6-a242-cbe1c562e1a9', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0009.jpg', '/home/meditor/.meditor/images/3a7f1589-a0d9-3dd6-a242-cbe1c562e1a9.jp2');
INSERT INTO image VALUES (22422, '03a129be-5819-3d5f-83d2-3bb2b2f01c26', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0010.jpg', '/home/meditor/.meditor/images/03a129be-5819-3d5f-83d2-3bb2b2f01c26.jp2');
INSERT INTO image VALUES (22423, 'dc06669c-9941-3d38-8db5-d11ecd6d30e8', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0011.jpg', '/home/meditor/.meditor/images/dc06669c-9941-3d38-8db5-d11ecd6d30e8.jp2');
INSERT INTO image VALUES (22424, 'd5036033-3da2-3cd4-8a90-fa639b0b41ac', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0012.jpg', '/home/meditor/.meditor/images/d5036033-3da2-3cd4-8a90-fa639b0b41ac.jp2');
INSERT INTO image VALUES (22425, 'f19f9b43-5cc9-3111-a035-884582c7f661', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0013.jpg', '/home/meditor/.meditor/images/f19f9b43-5cc9-3111-a035-884582c7f661.jp2');
INSERT INTO image VALUES (22426, '8397ec52-953c-369c-91b1-a79a2d51cfaa', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0014.jpg', '/home/meditor/.meditor/images/8397ec52-953c-369c-91b1-a79a2d51cfaa.jp2');
INSERT INTO image VALUES (22427, '3ee4deb4-64ed-3306-bdd6-46cb51cbf43e', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0015.jpg', '/home/meditor/.meditor/images/3ee4deb4-64ed-3306-bdd6-46cb51cbf43e.jp2');
INSERT INTO image VALUES (22428, 'e907e08e-5310-3ba4-8f9c-87274409d56a', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0016.jpg', '/home/meditor/.meditor/images/e907e08e-5310-3ba4-8f9c-87274409d56a.jp2');
INSERT INTO image VALUES (22429, 'b7d15721-6f29-30fb-9a90-393599c2ad91', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0017.jpg', '/home/meditor/.meditor/images/b7d15721-6f29-30fb-9a90-393599c2ad91.jp2');
INSERT INTO image VALUES (22430, '54b49261-3f07-3226-b608-159969eb8ad2', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0018.jpg', '/home/meditor/.meditor/images/54b49261-3f07-3226-b608-159969eb8ad2.jp2');
INSERT INTO image VALUES (22431, '9133f9dc-a2fa-3c49-a2f5-b50b3d825eed', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0019.jpg', '/home/meditor/.meditor/images/9133f9dc-a2fa-3c49-a2f5-b50b3d825eed.jp2');
INSERT INTO image VALUES (22432, 'f7c77d2a-92b6-3d73-a2b7-37cbed42210f', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0020.jpg', '/home/meditor/.meditor/images/f7c77d2a-92b6-3d73-a2b7-37cbed42210f.jp2');
INSERT INTO image VALUES (22433, '73bed0a6-047f-376b-8317-40bf70821861', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0021.jpg', '/home/meditor/.meditor/images/73bed0a6-047f-376b-8317-40bf70821861.jp2');
INSERT INTO image VALUES (22434, '9d562a87-57ff-38b4-8277-554b50b8b63a', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0022.jpg', '/home/meditor/.meditor/images/9d562a87-57ff-38b4-8277-554b50b8b63a.jp2');
INSERT INTO image VALUES (22435, 'f3106d1b-f39b-35d2-bdc7-03f60874348c', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0023.jpg', '/home/meditor/.meditor/images/f3106d1b-f39b-35d2-bdc7-03f60874348c.jp2');
INSERT INTO image VALUES (22436, 'f9d993c2-f067-39a9-9f70-a1567a3126db', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0024.jpg', '/home/meditor/.meditor/images/f9d993c2-f067-39a9-9f70-a1567a3126db.jp2');
INSERT INTO image VALUES (22437, '147f4906-a4a5-3784-9886-89da55e9e1e5', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0025.jpg', '/home/meditor/.meditor/images/147f4906-a4a5-3784-9886-89da55e9e1e5.jp2');
INSERT INTO image VALUES (22438, 'da08491d-2443-37ea-8c3b-192f33288bf7', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0026.jpg', '/home/meditor/.meditor/images/da08491d-2443-37ea-8c3b-192f33288bf7.jp2');
INSERT INTO image VALUES (22439, 'b9adce8f-b416-3911-bdcc-5a47030695a8', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0027.jpg', '/home/meditor/.meditor/images/b9adce8f-b416-3911-bdcc-5a47030695a8.jp2');
INSERT INTO image VALUES (22440, '87626719-fdc7-3cba-9db4-954f14c94e22', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0028.jpg', '/home/meditor/.meditor/images/87626719-fdc7-3cba-9db4-954f14c94e22.jp2');
INSERT INTO image VALUES (22441, '0e87bf7f-ac63-340d-9832-9994a599d774', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0029.jpg', '/home/meditor/.meditor/images/0e87bf7f-ac63-340d-9832-9994a599d774.jp2');
INSERT INTO image VALUES (22442, 'ba2b4198-7f22-3b99-a1be-a06de080601c', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0030.jpg', '/home/meditor/.meditor/images/ba2b4198-7f22-3b99-a1be-a06de080601c.jp2');
INSERT INTO image VALUES (22443, '11c37eb0-24ca-3d6f-94e7-27ec8aaef694', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0031.jpg', '/home/meditor/.meditor/images/11c37eb0-24ca-3d6f-94e7-27ec8aaef694.jp2');
INSERT INTO image VALUES (22444, '3445abf2-68db-3ace-a22a-778df5a541c9', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0032.jpg', '/home/meditor/.meditor/images/3445abf2-68db-3ace-a22a-778df5a541c9.jp2');
INSERT INTO image VALUES (22445, 'f0a7a5b2-f0b4-32f7-a7e1-d51d0fca9d74', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0033.jpg', '/home/meditor/.meditor/images/f0a7a5b2-f0b4-32f7-a7e1-d51d0fca9d74.jp2');
INSERT INTO image VALUES (22446, '6beb7fca-51ed-3677-b144-13a80e7f7be3', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0034.jpg', '/home/meditor/.meditor/images/6beb7fca-51ed-3677-b144-13a80e7f7be3.jp2');
INSERT INTO image VALUES (22447, '6ddb6375-d46d-3521-9fea-5b131c9479bd', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0035.jpg', '/home/meditor/.meditor/images/6ddb6375-d46d-3521-9fea-5b131c9479bd.jp2');
INSERT INTO image VALUES (22448, '7668c7ae-b2eb-390f-8018-e7827bbf178a', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0036.jpg', '/home/meditor/.meditor/images/7668c7ae-b2eb-390f-8018-e7827bbf178a.jp2');
INSERT INTO image VALUES (22449, '43b8d7ec-ca13-34a7-841f-80e20fa05d49', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0037.jpg', '/home/meditor/.meditor/images/43b8d7ec-ca13-34a7-841f-80e20fa05d49.jp2');
INSERT INTO image VALUES (22450, 'f688dd88-fc39-3620-a91a-dc3dfd9609df', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0038.jpg', '/home/meditor/.meditor/images/f688dd88-fc39-3620-a91a-dc3dfd9609df.jp2');
INSERT INTO image VALUES (22451, '31c449b2-6a2e-32b2-b86d-ae337191fed4', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0039.jpg', '/home/meditor/.meditor/images/31c449b2-6a2e-32b2-b86d-ae337191fed4.jp2');
INSERT INTO image VALUES (22452, 'f88c0466-0145-33bf-82d1-2066b078f3c4', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0040.jpg', '/home/meditor/.meditor/images/f88c0466-0145-33bf-82d1-2066b078f3c4.jp2');
INSERT INTO image VALUES (22453, 'c9e03b8c-1c44-370c-913e-6770b5991a6b', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0041.jpg', '/home/meditor/.meditor/images/c9e03b8c-1c44-370c-913e-6770b5991a6b.jp2');
INSERT INTO image VALUES (22454, '1ee8fe4e-fe9d-3251-982d-35ea9a725068', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0042.jpg', '/home/meditor/.meditor/images/1ee8fe4e-fe9d-3251-982d-35ea9a725068.jp2');
INSERT INTO image VALUES (22455, 'e2475b8b-3ba9-36ff-ae26-48c19ce57e83', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0043.jpg', '/home/meditor/.meditor/images/e2475b8b-3ba9-36ff-ae26-48c19ce57e83.jp2');
INSERT INTO image VALUES (22456, '8804d44d-6511-3546-aafa-c5a59f747950', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0044.jpg', '/home/meditor/.meditor/images/8804d44d-6511-3546-aafa-c5a59f747950.jp2');
INSERT INTO image VALUES (22457, 'e368aa01-a978-3b8e-8bd9-9f317615bfa3', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0045.jpg', '/home/meditor/.meditor/images/e368aa01-a978-3b8e-8bd9-9f317615bfa3.jp2');
INSERT INTO image VALUES (22458, '470d9dd2-6fc0-3f89-83e3-c09fea661e7d', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0046.jpg', '/home/meditor/.meditor/images/470d9dd2-6fc0-3f89-83e3-c09fea661e7d.jp2');
INSERT INTO image VALUES (22459, '51675b81-7258-3032-a67a-751a7b51de20', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0047.jpg', '/home/meditor/.meditor/images/51675b81-7258-3032-a67a-751a7b51de20.jp2');
INSERT INTO image VALUES (22460, '3039ee16-3f30-3a04-a7f8-8f03a7159127', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0048.jpg', '/home/meditor/.meditor/images/3039ee16-3f30-3a04-a7f8-8f03a7159127.jp2');
INSERT INTO image VALUES (22461, '4adadeaf-030c-304f-9c2a-6e7427c40946', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0049.jpg', '/home/meditor/.meditor/images/4adadeaf-030c-304f-9c2a-6e7427c40946.jp2');
INSERT INTO image VALUES (22462, 'c9c069fa-74f5-33ae-bd2e-4800cd928e92', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0050.jpg', '/home/meditor/.meditor/images/c9c069fa-74f5-33ae-bd2e-4800cd928e92.jp2');
INSERT INTO image VALUES (22463, 'cb76b01e-a00d-3f50-9402-37f8a64bd90d', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0051.jpg', '/home/meditor/.meditor/images/cb76b01e-a00d-3f50-9402-37f8a64bd90d.jp2');
INSERT INTO image VALUES (22464, '789eaae5-19a5-34d2-9a23-49a290b505bf', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0052.jpg', '/home/meditor/.meditor/images/789eaae5-19a5-34d2-9a23-49a290b505bf.jp2');
INSERT INTO image VALUES (22465, '664b856c-5c2a-3deb-98ae-a30ccde13635', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0053.jpg', '/home/meditor/.meditor/images/664b856c-5c2a-3deb-98ae-a30ccde13635.jp2');
INSERT INTO image VALUES (22466, '8b9d148f-d0fb-3965-9c0c-5f0424bf38ce', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0054.jpg', '/home/meditor/.meditor/images/8b9d148f-d0fb-3965-9c0c-5f0424bf38ce.jp2');
INSERT INTO image VALUES (22467, '424e3977-963a-3d95-8c7e-cfa7dcd15b07', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0055.jpg', '/home/meditor/.meditor/images/424e3977-963a-3d95-8c7e-cfa7dcd15b07.jp2');
INSERT INTO image VALUES (22468, 'c2a9d9e6-eb77-3441-98f2-112458a3d07a', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0056.jpg', '/home/meditor/.meditor/images/c2a9d9e6-eb77-3441-98f2-112458a3d07a.jp2');
INSERT INTO image VALUES (22469, '89dc8163-1081-3e2f-995d-a1722a993d7c', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0057.jpg', '/home/meditor/.meditor/images/89dc8163-1081-3e2f-995d-a1722a993d7c.jp2');
INSERT INTO image VALUES (22470, '0b2d4553-aae1-32c1-8d10-b8384aa0fd94', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0058.jpg', '/home/meditor/.meditor/images/0b2d4553-aae1-32c1-8d10-b8384aa0fd94.jp2');
INSERT INTO image VALUES (22471, 'c537ff76-6c4c-3e94-a021-432269a2b2c4', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0059.jpg', '/home/meditor/.meditor/images/c537ff76-6c4c-3e94-a021-432269a2b2c4.jp2');
INSERT INTO image VALUES (22472, '1f5b3b11-7e0f-37a5-9785-e4bd08bc77b6', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0060.jpg', '/home/meditor/.meditor/images/1f5b3b11-7e0f-37a5-9785-e4bd08bc77b6.jp2');
INSERT INTO image VALUES (22473, '29387a3f-337e-34ca-9db3-25243d869ebd', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0061.jpg', '/home/meditor/.meditor/images/29387a3f-337e-34ca-9db3-25243d869ebd.jp2');
INSERT INTO image VALUES (22474, '2eec8b20-9063-32cc-bda0-4cd4fe994fe7', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0062.jpg', '/home/meditor/.meditor/images/2eec8b20-9063-32cc-bda0-4cd4fe994fe7.jp2');
INSERT INTO image VALUES (22475, '04cef108-eeab-3ba5-8d9f-ac296a76a3d5', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0063.jpg', '/home/meditor/.meditor/images/04cef108-eeab-3ba5-8d9f-ac296a76a3d5.jp2');
INSERT INTO image VALUES (22476, '1ab9785f-44d8-374d-8b19-30b22a4123a3', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0064.jpg', '/home/meditor/.meditor/images/1ab9785f-44d8-374d-8b19-30b22a4123a3.jp2');
INSERT INTO image VALUES (22477, '83f9e233-e2ab-3ef0-9e66-e532e1114f7c', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0065.jpg', '/home/meditor/.meditor/images/83f9e233-e2ab-3ef0-9e66-e532e1114f7c.jp2');
INSERT INTO image VALUES (22478, '8c7f0080-5920-3bdf-95d8-bb98d24e332d', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0066.jpg', '/home/meditor/.meditor/images/8c7f0080-5920-3bdf-95d8-bb98d24e332d.jp2');
INSERT INTO image VALUES (22479, '8790fa02-4356-33b3-82d6-e0195884e682', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0067.jpg', '/home/meditor/.meditor/images/8790fa02-4356-33b3-82d6-e0195884e682.jp2');
INSERT INTO image VALUES (22480, '4141df52-68d0-373c-9fd8-d4290a09ec5f', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0068.jpg', '/home/meditor/.meditor/images/4141df52-68d0-373c-9fd8-d4290a09ec5f.jp2');
INSERT INTO image VALUES (22481, '138e5e1c-0d3d-3998-b4a3-9b7cf4c19e3a', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0069.jpg', '/home/meditor/.meditor/images/138e5e1c-0d3d-3998-b4a3-9b7cf4c19e3a.jp2');
INSERT INTO image VALUES (22482, '82a9b0fe-abe0-386f-ada4-1ffc472706ca', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0070.jpg', '/home/meditor/.meditor/images/82a9b0fe-abe0-386f-ada4-1ffc472706ca.jp2');
INSERT INTO image VALUES (22483, '5f6498df-016d-3002-bc19-bf058a78f128', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0071.jpg', '/home/meditor/.meditor/images/5f6498df-016d-3002-bc19-bf058a78f128.jp2');
INSERT INTO image VALUES (22484, 'b8b097db-0328-3b67-bd2a-3459ef96661e', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0072.jpg', '/home/meditor/.meditor/images/b8b097db-0328-3b67-bd2a-3459ef96661e.jp2');
INSERT INTO image VALUES (22485, '48275039-661e-30ca-86b8-32a1ef4d335e', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0073.jpg', '/home/meditor/.meditor/images/48275039-661e-30ca-86b8-32a1ef4d335e.jp2');
INSERT INTO image VALUES (22486, '369fd5cf-f728-3d58-a8df-2b04ec70f83c', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0074.jpg', '/home/meditor/.meditor/images/369fd5cf-f728-3d58-a8df-2b04ec70f83c.jp2');
INSERT INTO image VALUES (22487, 'f71f829c-bca2-35ea-9d94-4d971608844e', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0075.jpg', '/home/meditor/.meditor/images/f71f829c-bca2-35ea-9d94-4d971608844e.jp2');
INSERT INTO image VALUES (22488, '9412048e-8563-3eb5-b961-c3e432348359', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0076.jpg', '/home/meditor/.meditor/images/9412048e-8563-3eb5-b961-c3e432348359.jp2');
INSERT INTO image VALUES (22489, '7656feca-5828-3543-9faf-8344a840ad7f', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0077.jpg', '/home/meditor/.meditor/images/7656feca-5828-3543-9faf-8344a840ad7f.jp2');
INSERT INTO image VALUES (22490, 'cdcc5932-4c01-31e7-883f-c8aacd1a3ba6', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0078.jpg', '/home/meditor/.meditor/images/cdcc5932-4c01-31e7-883f-c8aacd1a3ba6.jp2');
INSERT INTO image VALUES (22491, '312788cf-c11f-35b2-b7e2-2aa2312bd2e7', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0079.jpg', '/home/meditor/.meditor/images/312788cf-c11f-35b2-b7e2-2aa2312bd2e7.jp2');
INSERT INTO image VALUES (22492, '3b6424d1-148a-3565-af08-fa222606818a', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0080.jpg', '/home/meditor/.meditor/images/3b6424d1-148a-3565-af08-fa222606818a.jp2');
INSERT INTO image VALUES (22493, '84c4b64d-00b0-39c9-9d60-5b754e6189e9', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0081.jpg', '/home/meditor/.meditor/images/84c4b64d-00b0-39c9-9d60-5b754e6189e9.jp2');
INSERT INTO image VALUES (22494, '4433b374-c0e8-3219-8ed9-68c92752d50a', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0082.jpg', '/home/meditor/.meditor/images/4433b374-c0e8-3219-8ed9-68c92752d50a.jp2');
INSERT INTO image VALUES (22495, '2d46b20c-d1bb-37e0-8ad4-66b94701a025', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0083.jpg', '/home/meditor/.meditor/images/2d46b20c-d1bb-37e0-8ad4-66b94701a025.jp2');
INSERT INTO image VALUES (22496, 'a6a23434-11c8-3b66-bf2a-0f7334f6b5a8', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0084.jpg', '/home/meditor/.meditor/images/a6a23434-11c8-3b66-bf2a-0f7334f6b5a8.jp2');
INSERT INTO image VALUES (22497, '0cf13d3e-5295-37b6-8e73-b5b0d6b76917', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0085.jpg', '/home/meditor/.meditor/images/0cf13d3e-5295-37b6-8e73-b5b0d6b76917.jp2');
INSERT INTO image VALUES (22498, 'a72c2bd5-8927-308e-94f4-3980b99732d7', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0086.jpg', '/home/meditor/.meditor/images/a72c2bd5-8927-308e-94f4-3980b99732d7.jp2');
INSERT INTO image VALUES (22499, 'e6e47eaa-26b9-3a81-b5e6-65a89c166ad3', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0087.jpg', '/home/meditor/.meditor/images/e6e47eaa-26b9-3a81-b5e6-65a89c166ad3.jp2');
INSERT INTO image VALUES (22500, '27bb883d-aca1-3421-92e6-12501197f406', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0088.jpg', '/home/meditor/.meditor/images/27bb883d-aca1-3421-92e6-12501197f406.jp2');
INSERT INTO image VALUES (22501, '9d2c31a7-2143-32fc-8b99-9f08da7fac72', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0089.jpg', '/home/meditor/.meditor/images/9d2c31a7-2143-32fc-8b99-9f08da7fac72.jp2');
INSERT INTO image VALUES (22502, '4e08a42d-45df-3d60-b3b2-110188342a81', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0090.jpg', '/home/meditor/.meditor/images/4e08a42d-45df-3d60-b3b2-110188342a81.jp2');
INSERT INTO image VALUES (22503, 'a3214870-0ae2-3992-a431-2f3321dd4650', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0091.jpg', '/home/meditor/.meditor/images/a3214870-0ae2-3992-a431-2f3321dd4650.jp2');
INSERT INTO image VALUES (22504, '37df99bf-f000-3a1f-95e7-841d387f79bd', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0092.jpg', '/home/meditor/.meditor/images/37df99bf-f000-3a1f-95e7-841d387f79bd.jp2');
INSERT INTO image VALUES (22505, '296e5979-b18e-389c-a317-8a0b2f3ef3ea', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0093.jpg', '/home/meditor/.meditor/images/296e5979-b18e-389c-a317-8a0b2f3ef3ea.jp2');
INSERT INTO image VALUES (22506, 'd5fc7705-0ffe-32cd-b67b-0720bc3ba1af', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0094.jpg', '/home/meditor/.meditor/images/d5fc7705-0ffe-32cd-b67b-0720bc3ba1af.jp2');
INSERT INTO image VALUES (22507, 'babea9c0-5fe7-3684-be0c-29ab795eec07', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0095.jpg', '/home/meditor/.meditor/images/babea9c0-5fe7-3684-be0c-29ab795eec07.jp2');
INSERT INTO image VALUES (22508, '4cf5b1b2-f4da-330e-ae13-64f9e24197fd', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0096.jpg', '/home/meditor/.meditor/images/4cf5b1b2-f4da-330e-ae13-64f9e24197fd.jp2');
INSERT INTO image VALUES (22509, '2eb0c377-a997-32e5-9d7c-0a0d9288d84a', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0097.jpg', '/home/meditor/.meditor/images/2eb0c377-a997-32e5-9d7c-0a0d9288d84a.jp2');
INSERT INTO image VALUES (22510, '6e7b203e-af41-375f-8690-599301e5424d', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0098.jpg', '/home/meditor/.meditor/images/6e7b203e-af41-375f-8690-599301e5424d.jp2');
INSERT INTO image VALUES (22511, '4075fb63-f013-3024-8158-2d9800c37495', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0099.jpg', '/home/meditor/.meditor/images/4075fb63-f013-3024-8158-2d9800c37495.jp2');
INSERT INTO image VALUES (22512, 'c85560f8-0b7a-3c3a-aca6-e265becad2a6', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0100.jpg', '/home/meditor/.meditor/images/c85560f8-0b7a-3c3a-aca6-e265becad2a6.jp2');
INSERT INTO image VALUES (22513, '1383ba8c-b2d0-3b7e-840f-58b9ad17b4af', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0101.jpg', '/home/meditor/.meditor/images/1383ba8c-b2d0-3b7e-840f-58b9ad17b4af.jp2');
INSERT INTO image VALUES (22514, 'd3637025-31d7-32c0-b625-e6d043d7e0f6', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0102.jpg', '/home/meditor/.meditor/images/d3637025-31d7-32c0-b625-e6d043d7e0f6.jp2');
INSERT INTO image VALUES (22515, 'cabbca33-368e-330e-b4e5-f5703f9a8804', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0103.jpg', '/home/meditor/.meditor/images/cabbca33-368e-330e-b4e5-f5703f9a8804.jp2');
INSERT INTO image VALUES (22516, '5adb4372-35f3-3f2a-b161-e1874104b6da', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0104.jpg', '/home/meditor/.meditor/images/5adb4372-35f3-3f2a-b161-e1874104b6da.jp2');
INSERT INTO image VALUES (22517, '307a833f-61f2-3bcb-bef1-471a4a3b2e36', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0105.jpg', '/home/meditor/.meditor/images/307a833f-61f2-3bcb-bef1-471a4a3b2e36.jp2');
INSERT INTO image VALUES (22518, 'd9190d45-2997-394e-8555-32d1397d9385', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0106.jpg', '/home/meditor/.meditor/images/d9190d45-2997-394e-8555-32d1397d9385.jp2');
INSERT INTO image VALUES (22519, '4e2e3ed2-8cd3-3e00-9e89-3445ed701243', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0107.jpg', '/home/meditor/.meditor/images/4e2e3ed2-8cd3-3e00-9e89-3445ed701243.jp2');
INSERT INTO image VALUES (22520, '5672469b-89a9-3722-822a-aae9ee521c33', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0108.jpg', '/home/meditor/.meditor/images/5672469b-89a9-3722-822a-aae9ee521c33.jp2');
INSERT INTO image VALUES (22521, '487c0d7a-e260-3529-b4ee-579ef98d1188', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0109.jpg', '/home/meditor/.meditor/images/487c0d7a-e260-3529-b4ee-579ef98d1188.jp2');
INSERT INTO image VALUES (22522, 'c1727efb-8c74-3e2c-8e29-a5bd596a5502', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0110.jpg', '/home/meditor/.meditor/images/c1727efb-8c74-3e2c-8e29-a5bd596a5502.jp2');
INSERT INTO image VALUES (22523, '475ae5cf-88b5-3c89-bda5-ae84118802ea', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0111.jpg', '/home/meditor/.meditor/images/475ae5cf-88b5-3c89-bda5-ae84118802ea.jp2');
INSERT INTO image VALUES (22524, 'c685b2ff-65f7-3e74-b6b5-bc3aacd312e9', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0112.jpg', '/home/meditor/.meditor/images/c685b2ff-65f7-3e74-b6b5-bc3aacd312e9.jp2');
INSERT INTO image VALUES (22525, '7f13ebc8-c026-30a0-ae60-9a023b851515', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0113.jpg', '/home/meditor/.meditor/images/7f13ebc8-c026-30a0-ae60-9a023b851515.jp2');
INSERT INTO image VALUES (22526, '7b68f595-30d3-30cc-99b4-1c02cfb1e1d0', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0114.jpg', '/home/meditor/.meditor/images/7b68f595-30d3-30cc-99b4-1c02cfb1e1d0.jp2');
INSERT INTO image VALUES (22527, '9bdd902e-c5af-3b75-aad7-c5796acf246d', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0115.jpg', '/home/meditor/.meditor/images/9bdd902e-c5af-3b75-aad7-c5796acf246d.jp2');
INSERT INTO image VALUES (22528, '9c61fe30-f52a-3cc3-86f1-62a679cc7285', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0116.jpg', '/home/meditor/.meditor/images/9c61fe30-f52a-3cc3-86f1-62a679cc7285.jp2');
INSERT INTO image VALUES (22529, '30d294c0-5bb0-33f1-ad3a-5a4db2f84071', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0117.jpg', '/home/meditor/.meditor/images/30d294c0-5bb0-33f1-ad3a-5a4db2f84071.jp2');
INSERT INTO image VALUES (22530, '2888071e-f72e-3a18-933e-f2f26dda0a56', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0118.jpg', '/home/meditor/.meditor/images/2888071e-f72e-3a18-933e-f2f26dda0a56.jp2');
INSERT INTO image VALUES (22531, '602297ee-c353-32cb-81bd-10238e7eb114', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0119.jpg', '/home/meditor/.meditor/images/602297ee-c353-32cb-81bd-10238e7eb114.jp2');
INSERT INTO image VALUES (22532, 'bc500817-828e-30b2-9ea9-238746461016', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0120.jpg', '/home/meditor/.meditor/images/bc500817-828e-30b2-9ea9-238746461016.jp2');
INSERT INTO image VALUES (22533, '64c19987-4ecb-300d-bb88-7cff9517c7f1', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0121.jpg', '/home/meditor/.meditor/images/64c19987-4ecb-300d-bb88-7cff9517c7f1.jp2');
INSERT INTO image VALUES (22534, '2861ade8-f1ad-32ee-8840-f4232ea5674f', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0122.jpg', '/home/meditor/.meditor/images/2861ade8-f1ad-32ee-8840-f4232ea5674f.jp2');
INSERT INTO image VALUES (22535, '28b9fa87-3b23-3aec-b008-5664cb4db059', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0123.jpg', '/home/meditor/.meditor/images/28b9fa87-3b23-3aec-b008-5664cb4db059.jp2');
INSERT INTO image VALUES (22536, '47af06ba-a5ce-391c-af7e-eea4018dcec9', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0124.jpg', '/home/meditor/.meditor/images/47af06ba-a5ce-391c-af7e-eea4018dcec9.jp2');
INSERT INTO image VALUES (22537, '91472c06-bc3a-3807-94a2-0ce6f096d1db', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0125.jpg', '/home/meditor/.meditor/images/91472c06-bc3a-3807-94a2-0ce6f096d1db.jp2');
INSERT INTO image VALUES (22538, 'fd55450b-ea2c-302c-8324-ee2c44f49cb4', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0126.jpg', '/home/meditor/.meditor/images/fd55450b-ea2c-302c-8324-ee2c44f49cb4.jp2');
INSERT INTO image VALUES (22539, '5cf0475d-75e3-37d5-8f3d-9f2dd6f1e48d', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0127.jpg', '/home/meditor/.meditor/images/5cf0475d-75e3-37d5-8f3d-9f2dd6f1e48d.jp2');
INSERT INTO image VALUES (22540, '6314487c-65f1-3c89-b278-5353a9c41a05', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0128.jpg', '/home/meditor/.meditor/images/6314487c-65f1-3c89-b278-5353a9c41a05.jp2');
INSERT INTO image VALUES (22541, '8f5d34b8-3088-38d8-9de0-c29c47374cf3', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0129.jpg', '/home/meditor/.meditor/images/8f5d34b8-3088-38d8-9de0-c29c47374cf3.jp2');
INSERT INTO image VALUES (22542, '974fbcf1-7158-325e-93c4-dd7c79f0e05e', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0130.jpg', '/home/meditor/.meditor/images/974fbcf1-7158-325e-93c4-dd7c79f0e05e.jp2');
INSERT INTO image VALUES (22543, 'a59e3f98-9e85-388e-be12-6dfb460cfdfd', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0131.jpg', '/home/meditor/.meditor/images/a59e3f98-9e85-388e-be12-6dfb460cfdfd.jp2');
INSERT INTO image VALUES (22544, '893200f5-9ef7-31c7-a1e6-f0b9be614f47', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0132.jpg', '/home/meditor/.meditor/images/893200f5-9ef7-31c7-a1e6-f0b9be614f47.jp2');
INSERT INTO image VALUES (22545, 'e09bafb9-53f6-3e29-bf4a-4737f3060663', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0133.jpg', '/home/meditor/.meditor/images/e09bafb9-53f6-3e29-bf4a-4737f3060663.jp2');
INSERT INTO image VALUES (22546, '633aa6d8-7f4c-35a5-a838-34cf028d65db', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0134.jpg', '/home/meditor/.meditor/images/633aa6d8-7f4c-35a5-a838-34cf028d65db.jp2');
INSERT INTO image VALUES (22547, '55b9f855-3e37-31b4-baa0-2def7895e9b4', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0135.jpg', '/home/meditor/.meditor/images/55b9f855-3e37-31b4-baa0-2def7895e9b4.jp2');
INSERT INTO image VALUES (22548, '30424c30-1c37-361c-beb4-e318886c6ca2', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0136.jpg', '/home/meditor/.meditor/images/30424c30-1c37-361c-beb4-e318886c6ca2.jp2');
INSERT INTO image VALUES (22549, '0514eba7-a215-337b-9975-d8461d143976', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0137.jpg', '/home/meditor/.meditor/images/0514eba7-a215-337b-9975-d8461d143976.jp2');
INSERT INTO image VALUES (22550, '181c4ceb-7bef-3f6a-8013-7f2b04926757', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0138.jpg', '/home/meditor/.meditor/images/181c4ceb-7bef-3f6a-8013-7f2b04926757.jp2');
INSERT INTO image VALUES (22551, 'c0f2616e-d37e-3feb-924d-83633570e0d5', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0139.jpg', '/home/meditor/.meditor/images/c0f2616e-d37e-3feb-924d-83633570e0d5.jp2');
INSERT INTO image VALUES (22552, '1897c452-77c3-3245-a3ac-fdfd9dfa35d5', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0140.jpg', '/home/meditor/.meditor/images/1897c452-77c3-3245-a3ac-fdfd9dfa35d5.jp2');
INSERT INTO image VALUES (22553, '657ad5b3-701a-3b6c-bcc4-768bbde039e3', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0141.jpg', '/home/meditor/.meditor/images/657ad5b3-701a-3b6c-bcc4-768bbde039e3.jp2');
INSERT INTO image VALUES (22554, '00fa68fa-7759-3ded-93ad-fc578c2f7fe2', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0142.jpg', '/home/meditor/.meditor/images/00fa68fa-7759-3ded-93ad-fc578c2f7fe2.jp2');
INSERT INTO image VALUES (22555, '12e58a3f-1318-3b05-9de7-4091a829632c', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0143.jpg', '/home/meditor/.meditor/images/12e58a3f-1318-3b05-9de7-4091a829632c.jp2');
INSERT INTO image VALUES (22556, '60968ea5-cc25-3aba-b041-28df8dfd9356', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0144.jpg', '/home/meditor/.meditor/images/60968ea5-cc25-3aba-b041-28df8dfd9356.jp2');
INSERT INTO image VALUES (22557, '52386b12-9780-3aa9-9c63-f9dc7d89e531', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0145.jpg', '/home/meditor/.meditor/images/52386b12-9780-3aa9-9c63-f9dc7d89e531.jp2');
INSERT INTO image VALUES (22558, '547ec531-3df3-3c43-8d02-b1f5202a268a', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0146.jpg', '/home/meditor/.meditor/images/547ec531-3df3-3c43-8d02-b1f5202a268a.jp2');
INSERT INTO image VALUES (22559, 'fae2d7e4-e858-3c36-bb92-ded47d8cd172', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0147.jpg', '/home/meditor/.meditor/images/fae2d7e4-e858-3c36-bb92-ded47d8cd172.jp2');
INSERT INTO image VALUES (22560, '971af39b-9c92-3802-9bd7-c07f7653902f', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0148.jpg', '/home/meditor/.meditor/images/971af39b-9c92-3802-9bd7-c07f7653902f.jp2');
INSERT INTO image VALUES (22561, 'ef961eb0-bc04-3c96-8c9f-246effc1634f', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0149.jpg', '/home/meditor/.meditor/images/ef961eb0-bc04-3c96-8c9f-246effc1634f.jp2');
INSERT INTO image VALUES (22562, '7bfcb4c6-767b-3e34-b018-a78a0d7cd52f', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0150.jpg', '/home/meditor/.meditor/images/7bfcb4c6-767b-3e34-b018-a78a0d7cd52f.jp2');
INSERT INTO image VALUES (22563, '3d3f69f0-780c-30d7-be71-31f2b715ebea', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0151.jpg', '/home/meditor/.meditor/images/3d3f69f0-780c-30d7-be71-31f2b715ebea.jp2');
INSERT INTO image VALUES (22564, '9d59e6cf-2611-3014-b379-c81fa3c2aef5', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0152.jpg', '/home/meditor/.meditor/images/9d59e6cf-2611-3014-b379-c81fa3c2aef5.jp2');
INSERT INTO image VALUES (22565, 'c6281293-436f-3408-b9be-c1e3e0ff442b', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0153.jpg', '/home/meditor/.meditor/images/c6281293-436f-3408-b9be-c1e3e0ff442b.jp2');
INSERT INTO image VALUES (22566, '6d43447b-8202-36d1-b557-071447fc3542', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0154.jpg', '/home/meditor/.meditor/images/6d43447b-8202-36d1-b557-071447fc3542.jp2');
INSERT INTO image VALUES (22567, '1c6c47ed-57f4-37de-aab8-320cec8e9172', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0155.jpg', '/home/meditor/.meditor/images/1c6c47ed-57f4-37de-aab8-320cec8e9172.jp2');
INSERT INTO image VALUES (22568, '1f97dc55-9e75-329e-bdbc-4df08adb4121', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0156.jpg', '/home/meditor/.meditor/images/1f97dc55-9e75-329e-bdbc-4df08adb4121.jp2');
INSERT INTO image VALUES (22569, 'd847e087-da02-31b2-b82a-ebb1aab92d60', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0157.jpg', '/home/meditor/.meditor/images/d847e087-da02-31b2-b82a-ebb1aab92d60.jp2');
INSERT INTO image VALUES (22570, '95b18c84-b03a-3956-a3be-a1da5335711e', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0158.jpg', '/home/meditor/.meditor/images/95b18c84-b03a-3956-a3be-a1da5335711e.jp2');
INSERT INTO image VALUES (22571, 'd0619144-dc8e-3ae7-9261-980e409decb0', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0159.jpg', '/home/meditor/.meditor/images/d0619144-dc8e-3ae7-9261-980e409decb0.jp2');
INSERT INTO image VALUES (22572, '6c750906-c6c4-33c1-9071-17d03ca39d21', '2012-01-19 00:23:51.93708', '/home/meditor/input/monograph/2619210199/0160.jpg', '/home/meditor/.meditor/images/6c750906-c6c4-33c1-9071-17d03ca39d21.jp2');
INSERT INTO image VALUES (21551, '44544a9b-9ec1-30c9-8273-a75e4069a660', '2012-01-20 12:01:26.512141', '/home/meditor/input/periodical/000173829/2610382158/38-2009-03/0015.jpg', '/home/meditor/.meditor/images/44544a9b-9ec1-30c9-8273-a75e4069a660.jp2');


--
-- Data for Name: input_queue_item; Type: TABLE DATA; Schema: public; Owner: meditor
--

INSERT INTO input_queue_item VALUES (28848, '/periodical/test', 'test', false);
INSERT INTO input_queue_item VALUES (28849, '/periodical/000458588nepouzivat/1-1885-0', '1-1885-0', false);
INSERT INTO input_queue_item VALUES (28850, '/periodical/000458588nepouzivat/28-1912-100002', '28-1912-100002', false);
INSERT INTO input_queue_item VALUES (28851, '/periodical/000458588nepouzivat/5-1889-0', '5-1889-0', false);
INSERT INTO input_queue_item VALUES (28852, '/periodical/000458588nepouzivat/28-1912-3Q', '28-1912-3Q', false);
INSERT INTO input_queue_item VALUES (28853, '/periodical/000458588nepouzivat/23-1907-2P', '23-1907-2P', false);
INSERT INTO input_queue_item VALUES (28854, '/periodical/000458588nepouzivat/8-1892-0', '8-1892-0', false);
INSERT INTO input_queue_item VALUES (28855, '/periodical/000458588nepouzivat/31-1915-900', '31-1915-900', false);
INSERT INTO input_queue_item VALUES (28856, '/periodical/000458588nepouzivat/24-1908-1', '24-1908-1', false);
INSERT INTO input_queue_item VALUES (28857, '/periodical/000458588nepouzivat/9-1893-0', '9-1893-0', false);
INSERT INTO input_queue_item VALUES (28858, '/periodical/000458588nepouzivat/7-1891-0', '7-1891-0', false);
INSERT INTO input_queue_item VALUES (28859, '/periodical/000458588nepouzivat/31-1915-2a', '31-1915-2a', false);
INSERT INTO input_queue_item VALUES (28860, '/periodical/000458588nepouzivat/31-1915-164', '31-1915-164', false);
INSERT INTO input_queue_item VALUES (28861, '/periodical/000458588nepouzivat/24-1908-80', '24-1908-80', false);
INSERT INTO input_queue_item VALUES (28862, '/periodical/000458588nepouzivat/26-1910-1', '26-1910-1', false);
INSERT INTO input_queue_item VALUES (28863, '/periodical/000458588nepouzivat/31-1915-326', '31-1915-326', false);
INSERT INTO input_queue_item VALUES (28864, '/periodical/000458588nepouzivat/37-1921-3Q', '37-1921-3Q', false);
INSERT INTO input_queue_item VALUES (28865, '/periodical/000458588nepouzivat/29-1913-1', '29-1913-1', false);
INSERT INTO input_queue_item VALUES (28866, '/periodical/000458588nepouzivat/37-1921-180', '37-1921-180', false);
INSERT INTO input_queue_item VALUES (28867, '/periodical/000458588nepouzivat/25-1909-2P', '25-1909-2P', false);
INSERT INTO input_queue_item VALUES (28868, '/periodical/000458588nepouzivat/31-1915-1a', '31-1915-1a', false);
INSERT INTO input_queue_item VALUES (28869, '/periodical/000458588nepouzivat/31-1915-3a', '31-1915-3a', false);
INSERT INTO input_queue_item VALUES (28870, '/periodical/000458588nepouzivat/32-1916-456', '32-1916-456', false);
INSERT INTO input_queue_item VALUES (28871, '/periodical/000458588nepouzivat/37-1921-171', '37-1921-171', false);
INSERT INTO input_queue_item VALUES (28872, '/periodical/000458588nepouzivat/37-1921-4Q', '37-1921-4Q', false);
INSERT INTO input_queue_item VALUES (28873, '/periodical/000458588nepouzivat/28-1912-500', '28-1912-500', false);
INSERT INTO input_queue_item VALUES (28874, '/periodical/000458588nepouzivat/32-1916-300', '32-1916-300', false);
INSERT INTO input_queue_item VALUES (28875, '/periodical/000458588nepouzivat/23-1907-1P', '23-1907-1P', false);
INSERT INTO input_queue_item VALUES (28876, '/periodical/000458588nepouzivat/32-1916-301', '32-1916-301', false);
INSERT INTO input_queue_item VALUES (28877, '/periodical/000458588nepouzivat/29-1913-79', '29-1913-79', false);
INSERT INTO input_queue_item VALUES (28878, '/periodical/000458588nepouzivat/2610439401jpg', '2610439401jpg', false);
INSERT INTO input_queue_item VALUES (28879, '/periodical/000458588nepouzivat/27-1911-1', '27-1911-1', false);
INSERT INTO input_queue_item VALUES (28880, '/periodical/000458588nepouzivat/24-1908-259', '24-1908-259', false);
INSERT INTO input_queue_item VALUES (28881, '/periodical/000458588nepouzivat/32-1916-1', '32-1916-1', false);
INSERT INTO input_queue_item VALUES (28882, '/periodical/000458588nepouzivat/3-1887-0', '3-1887-0', false);
INSERT INTO input_queue_item VALUES (28883, '/periodical/000458588nepouzivat/4-1888-0', '4-1888-0', false);
INSERT INTO input_queue_item VALUES (28884, '/periodical/000458588nepouzivat/28-1912-4Q', '28-1912-4Q', false);
INSERT INTO input_queue_item VALUES (28885, '/periodical/000458588nepouzivat/31-1915-1', '31-1915-1', false);
INSERT INTO input_queue_item VALUES (28886, '/periodical/000458588nepouzivat/27-1911-0', '27-1911-0', false);
INSERT INTO input_queue_item VALUES (28887, '/periodical/000458588nepouzivat/27-1911-147', '27-1911-147', false);
INSERT INTO input_queue_item VALUES (28888, '/periodical/000458588nepouzivat/28-1912-1P', '28-1912-1P', false);
INSERT INTO input_queue_item VALUES (28889, '/periodical/000458588nepouzivat/2-1886-0', '2-1886-0', false);
INSERT INTO input_queue_item VALUES (28890, '/periodical/000458588nepouzivat/24-1908-167', '24-1908-167', false);
INSERT INTO input_queue_item VALUES (28891, '/periodical/000458588nepouzivat/25-1909-1P', '25-1909-1P', false);
INSERT INTO input_queue_item VALUES (28892, '/periodical/000458588nepouzivat/6-1890-0', '6-1890-0', false);
INSERT INTO input_queue_item VALUES (28893, '/periodical/000458588nepouzivat', '000458588nepouzivat', false);
INSERT INTO input_queue_item VALUES (28894, '/periodical/001164725/15-1876-0', '15-1876-0', false);
INSERT INTO input_queue_item VALUES (28895, '/periodical/001164725/20-1882-0', '20-1882-0', false);
INSERT INTO input_queue_item VALUES (28896, '/periodical/001164725/2-1863-0', '2-1863-0', false);
INSERT INTO input_queue_item VALUES (28897, '/periodical/001164725/11-1872-0', '11-1872-0', false);
INSERT INTO input_queue_item VALUES (28898, '/periodical/001164725/17-1878-0', '17-1878-0', false);
INSERT INTO input_queue_item VALUES (28899, '/periodical/001164725/24-1887-0', '24-1887-0', false);
INSERT INTO input_queue_item VALUES (28900, '/periodical/001164725/33-1895-0', '33-1895-0', false);
INSERT INTO input_queue_item VALUES (28901, '/periodical/001164725/22-1884-0', '22-1884-0', false);
INSERT INTO input_queue_item VALUES (28902, '/periodical/001164725/27-1889-0', '27-1889-0', false);
INSERT INTO input_queue_item VALUES (28903, '/periodical/001164725/13-1874-0', '13-1874-0', false);
INSERT INTO input_queue_item VALUES (28904, '/periodical/001164725/18-1880-1', '18-1880-1', false);
INSERT INTO input_queue_item VALUES (28905, '/periodical/001164725/4-1865-0', '4-1865-0', false);
INSERT INTO input_queue_item VALUES (28906, '/periodical/001164725/31-1894-0', '31-1894-0', false);
INSERT INTO input_queue_item VALUES (28907, '/periodical/001164725/7-1868-0', '7-1868-0', false);
INSERT INTO input_queue_item VALUES (28908, '/periodical/001164725/14-1875-0', '14-1875-0', false);
INSERT INTO input_queue_item VALUES (28909, '/periodical/001164725/5-1866-0', '5-1866-0', false);
INSERT INTO input_queue_item VALUES (28910, '/periodical/001164725/30-1892-0', '30-1892-0', false);
INSERT INTO input_queue_item VALUES (28911, '/periodical/001164725/12-1873-0', '12-1873-0', false);
INSERT INTO input_queue_item VALUES (28912, '/periodical/001164725/16-1877-0', '16-1877-0', false);
INSERT INTO input_queue_item VALUES (28913, '/periodical/001164725/9-1870-0', '9-1870-0', false);
INSERT INTO input_queue_item VALUES (28914, '/periodical/001164725/6-1867-0', '6-1867-0', false);
INSERT INTO input_queue_item VALUES (28915, '/periodical/001164725/8-1869-0', '8-1869-0', false);
INSERT INTO input_queue_item VALUES (28916, '/periodical/001164725/10-1871-0', '10-1871-0', false);
INSERT INTO input_queue_item VALUES (28917, '/periodical/001164725/26-1888-0', '26-1888-0', false);
INSERT INTO input_queue_item VALUES (28918, '/periodical/001164725/3-1864-0', '3-1864-0', false);
INSERT INTO input_queue_item VALUES (28919, '/periodical/001164725/25-1887-0', '25-1887-0', false);
INSERT INTO input_queue_item VALUES (28920, '/periodical/001164725/18-1880-2', '18-1880-2', false);
INSERT INTO input_queue_item VALUES (28921, '/periodical/001164725', '001164725', false);
INSERT INTO input_queue_item VALUES (28922, '/periodical/000379217/5-1924-0', '5-1924-0', false);
INSERT INTO input_queue_item VALUES (28923, '/periodical/000379217/3-1922-0', '3-1922-0', false);
INSERT INTO input_queue_item VALUES (28924, '/periodical/000379217/2-1921-0', '2-1921-0', false);
INSERT INTO input_queue_item VALUES (28925, '/periodical/000379217/4-1923-0', '4-1923-0', false);
INSERT INTO input_queue_item VALUES (28926, '/periodical/000379217/1-1920-0', '1-1920-0', false);
INSERT INTO input_queue_item VALUES (28927, '/periodical/000379217', '000379217', false);
INSERT INTO input_queue_item VALUES (28928, '/periodical/000194565/22-1913-0', '22-1913-0', false);
INSERT INTO input_queue_item VALUES (28929, '/periodical/000194565/20-1911-0', '20-1911-0', false);
INSERT INTO input_queue_item VALUES (28930, '/periodical/000194565/21-1912-0', '21-1912-0', false);
INSERT INTO input_queue_item VALUES (28931, '/periodical/000194565/14-1905-0', '14-1905-0', false);
INSERT INTO input_queue_item VALUES (28932, '/periodical/000194565/28-1919-0', '28-1919-0', false);
INSERT INTO input_queue_item VALUES (28933, '/periodical/000194565/27-1918-0', '27-1918-0', false);
INSERT INTO input_queue_item VALUES (28934, '/periodical/000194565/26-1917-0', '26-1917-0', false);
INSERT INTO input_queue_item VALUES (28935, '/periodical/000194565/19-1910-0', '19-1910-0', false);
INSERT INTO input_queue_item VALUES (28936, '/periodical/000194565/24-1915-0', '24-1915-0', false);
INSERT INTO input_queue_item VALUES (28937, '/periodical/000194565/25-1916-0', '25-1916-0', false);
INSERT INTO input_queue_item VALUES (28938, '/periodical/000194565/23-1914-0', '23-1914-0', false);
INSERT INTO input_queue_item VALUES (28939, '/periodical/000194565', '000194565', false);
INSERT INTO input_queue_item VALUES (28940, '/periodical/000458588/38-1922-90', '38-1922-90', false);
INSERT INTO input_queue_item VALUES (28941, '/periodical/000458588/36-1920-1', '36-1920-1', false);
INSERT INTO input_queue_item VALUES (28942, '/periodical/000458588/35-1919-90', '35-1919-90', false);
INSERT INTO input_queue_item VALUES (28943, '/periodical/000458588/36-1920-271', '36-1920-271', false);
INSERT INTO input_queue_item VALUES (28944, '/periodical/000458588/34-1918-269', '34-1918-269', false);
INSERT INTO input_queue_item VALUES (28945, '/periodical/000458588/37-1921-1', '37-1921-1', false);
INSERT INTO input_queue_item VALUES (28946, '/periodical/000458588/36-1920-92', '36-1920-92', false);
INSERT INTO input_queue_item VALUES (28947, '/periodical/000458588/36-1920-180', '36-1920-180', false);
INSERT INTO input_queue_item VALUES (28948, '/periodical/000458588/29-1913-225', '29-1913-225', false);
INSERT INTO input_queue_item VALUES (28949, '/periodical/000458588/33-1917-196', '33-1917-196', false);
INSERT INTO input_queue_item VALUES (28950, '/periodical/000458588/35-1919-1', '35-1919-1', false);
INSERT INTO input_queue_item VALUES (28951, '/periodical/000458588/29-1913-149', '29-1913-149', false);
INSERT INTO input_queue_item VALUES (28952, '/periodical/000458588/33-1917-1', '33-1917-1', false);
INSERT INTO input_queue_item VALUES (28953, '/periodical/000458588/35-1919-225', '35-1919-225', false);
INSERT INTO input_queue_item VALUES (28954, '/periodical/000458588/33-1917-269', '33-1917-269', false);
INSERT INTO input_queue_item VALUES (28955, '/periodical/000458588/35-1919-179', '35-1919-179', false);
INSERT INTO input_queue_item VALUES (28956, '/periodical/000458588/34-1918-177', '34-1918-177', false);
INSERT INTO input_queue_item VALUES (28957, '/periodical/000458588', '000458588', false);
INSERT INTO input_queue_item VALUES (28958, '/periodical/000412273/8-2003-0', '8-2003-0', false);
INSERT INTO input_queue_item VALUES (28959, '/periodical/000412273/15-2010-3', '15-2010-3', false);
INSERT INTO input_queue_item VALUES (28960, '/periodical/000412273/15-2010-4', '15-2010-4', false);
INSERT INTO input_queue_item VALUES (28961, '/periodical/000412273/13-2008-0', '13-2008-0', false);
INSERT INTO input_queue_item VALUES (28962, '/periodical/000412273/12-2007-0', '12-2007-0', false);
INSERT INTO input_queue_item VALUES (28963, '/periodical/000412273/7-2002-0', '7-2002-0', false);
INSERT INTO input_queue_item VALUES (28964, '/periodical/000412273/3-1998-0', '3-1998-0', false);
INSERT INTO input_queue_item VALUES (28965, '/periodical/000412273/5-2000-0', '5-2000-0', false);
INSERT INTO input_queue_item VALUES (28966, '/periodical/000412273/10-2005-0', '10-2005-0', false);
INSERT INTO input_queue_item VALUES (28967, '/periodical/000412273/11-2006-0', '11-2006-0', false);
INSERT INTO input_queue_item VALUES (28968, '/periodical/000412273/9-2004-0', '9-2004-0', false);
INSERT INTO input_queue_item VALUES (28969, '/periodical/000412273/4-1999-0', '4-1999-0', false);
INSERT INTO input_queue_item VALUES (28970, '/periodical/000412273/14-2009-1', '14-2009-1', false);
INSERT INTO input_queue_item VALUES (28971, '/periodical/000412273/15-2010-1', '15-2010-1', false);
INSERT INTO input_queue_item VALUES (28972, '/periodical/000412273/2-1997-0', '2-1997-0', false);
INSERT INTO input_queue_item VALUES (28973, '/periodical/000412273/6-2001-0', '6-2001-0', false);
INSERT INTO input_queue_item VALUES (28974, '/periodical/000412273', '000412273', false);
INSERT INTO input_queue_item VALUES (28975, '/periodical/rovnost/21-1905-1', '21-1905-1', false);
INSERT INTO input_queue_item VALUES (28976, '/periodical/rovnost/22-1906-0', '22-1906-0', false);
INSERT INTO input_queue_item VALUES (28977, '/periodical/rovnost/21-1905-78', '21-1905-78', false);
INSERT INTO input_queue_item VALUES (28978, '/periodical/rovnost', 'rovnost', false);
INSERT INTO input_queue_item VALUES (28979, '/periodical/rovnost_testE4/2610439381_ed', '2610439381_ed', false);
INSERT INTO input_queue_item VALUES (28980, '/periodical/rovnost_testE4/2610439383_ed', '2610439383_ed', false);
INSERT INTO input_queue_item VALUES (28981, '/periodical/rovnost_testE4', 'rovnost_testE4', false);
INSERT INTO input_queue_item VALUES (28982, '/periodical/000250592/10-1974-0', '10-1974-0', false);
INSERT INTO input_queue_item VALUES (28983, '/periodical/000250592/8-1972-0', '8-1972-0', false);
INSERT INTO input_queue_item VALUES (28984, '/periodical/000250592/30-1994-0', '30-1994-0', false);
INSERT INTO input_queue_item VALUES (28985, '/periodical/000250592/29-1993-0', '29-1993-0', false);
INSERT INTO input_queue_item VALUES (28986, '/periodical/000250592/44-2008-0', '44-2008-0', false);
INSERT INTO input_queue_item VALUES (28987, '/periodical/000250592/28-1992-0', '28-1992-0', false);
INSERT INTO input_queue_item VALUES (28988, '/periodical/000250592/18-1982-0', '18-1982-0', false);
INSERT INTO input_queue_item VALUES (28989, '/periodical/000250592/35-1999-0', '35-1999-0', false);
INSERT INTO input_queue_item VALUES (28990, '/periodical/000250592/23-1987-0', '23-1987-0', false);
INSERT INTO input_queue_item VALUES (28991, '/periodical/000250592/21-1985-0', '21-1985-0', false);
INSERT INTO input_queue_item VALUES (28992, '/periodical/000250592/45-2009-0', '45-2009-0', false);
INSERT INTO input_queue_item VALUES (28993, '/periodical/000250592/3-1967-0', '3-1967-0', false);
INSERT INTO input_queue_item VALUES (28994, '/periodical/000250592/12-1976-0', '12-1976-0', false);
INSERT INTO input_queue_item VALUES (28995, '/periodical/000250592/2-1966-0', '2-1966-0', false);
INSERT INTO input_queue_item VALUES (28996, '/periodical/000250592/43-2007-0', '43-2007-0', false);
INSERT INTO input_queue_item VALUES (28997, '/periodical/000250592/13-1977-0', '13-1977-0', false);
INSERT INTO input_queue_item VALUES (28998, '/periodical/000250592/32-1996-0', '32-1996-0', false);
INSERT INTO input_queue_item VALUES (28999, '/periodical/000250592/16-1980-0', '16-1980-0', false);
INSERT INTO input_queue_item VALUES (29000, '/periodical/000250592/36-2000-0', '36-2000-0', false);
INSERT INTO input_queue_item VALUES (29001, '/periodical/000250592/37-2001-0', '37-2001-0', false);
INSERT INTO input_queue_item VALUES (29002, '/periodical/000250592/38-2002-0', '38-2002-0', false);
INSERT INTO input_queue_item VALUES (29003, '/periodical/000250592/46-2010-0', '46-2010-0', false);
INSERT INTO input_queue_item VALUES (29004, '/periodical/000250592/5-1969-0', '5-1969-0', false);
INSERT INTO input_queue_item VALUES (29005, '/periodical/000250592/27-1991-0', '27-1991-0', false);
INSERT INTO input_queue_item VALUES (29006, '/periodical/000250592/39-2003-0', '39-2003-0', false);
INSERT INTO input_queue_item VALUES (29007, '/periodical/000250592/9-1973-0', '9-1973-0', false);
INSERT INTO input_queue_item VALUES (29008, '/periodical/000250592/34-1998-0', '34-1998-0', false);
INSERT INTO input_queue_item VALUES (29009, '/periodical/000250592/14-1978-0', '14-1978-0', false);
INSERT INTO input_queue_item VALUES (29010, '/periodical/000250592/4-1968-0', '4-1968-0', false);
INSERT INTO input_queue_item VALUES (29011, '/periodical/000250592/42-2006-0', '42-2006-0', false);
INSERT INTO input_queue_item VALUES (29012, '/periodical/000250592/26-1990-0', '26-1990-0', false);
INSERT INTO input_queue_item VALUES (29013, '/periodical/000250592/15-1979-0', '15-1979-0', false);
INSERT INTO input_queue_item VALUES (29014, '/periodical/000250592/25-1989-0', '25-1989-0', false);
INSERT INTO input_queue_item VALUES (29015, '/periodical/000250592/19-1983-0', '19-1983-0', false);
INSERT INTO input_queue_item VALUES (29016, '/periodical/000250592/11-1975-0', '11-1975-0', false);
INSERT INTO input_queue_item VALUES (29017, '/periodical/000250592/7-1971-0', '7-1971-0', false);
INSERT INTO input_queue_item VALUES (29018, '/periodical/000250592/24-1988-0', '24-1988-0', false);
INSERT INTO input_queue_item VALUES (29019, '/periodical/000250592/20-1984-0', '20-1984-0', false);
INSERT INTO input_queue_item VALUES (29020, '/periodical/000250592/17-1981-0', '17-1981-0', false);
INSERT INTO input_queue_item VALUES (29021, '/periodical/000250592/40-2004-0', '40-2004-0', false);
INSERT INTO input_queue_item VALUES (29022, '/periodical/000250592/22-1986-0', '22-1986-0', false);
INSERT INTO input_queue_item VALUES (29023, '/periodical/000250592/33-1997-0', '33-1997-0', false);
INSERT INTO input_queue_item VALUES (29024, '/periodical/000250592/41-2005-0', '41-2005-0', false);
INSERT INTO input_queue_item VALUES (29025, '/periodical/000250592/31-1995-0', '31-1995-0', false);
INSERT INTO input_queue_item VALUES (29026, '/periodical/000250592/6-1970-0', '6-1970-0', false);
INSERT INTO input_queue_item VALUES (29027, '/periodical/000250592', '000250592', false);
INSERT INTO input_queue_item VALUES (28511, '/monograph', 'monograph', false);
INSERT INTO input_queue_item VALUES (28512, '/monograph/3119961662', '3119961662', false);
INSERT INTO input_queue_item VALUES (28513, '/monograph/2619217556', '2619217556', true);
INSERT INTO input_queue_item VALUES (28514, '/monograph/2619421240', '2619421240', false);
INSERT INTO input_queue_item VALUES (28515, '/monograph/2619207421', '2619207421', false);
INSERT INTO input_queue_item VALUES (28516, '/monograph/2619429829', '2619429829', false);
INSERT INTO input_queue_item VALUES (28517, '/monograph/2619223797', '2619223797', false);
INSERT INTO input_queue_item VALUES (28518, '/monograph/2619419040', '2619419040', false);
INSERT INTO input_queue_item VALUES (28519, '/monograph/2619417717', '2619417717', false);
INSERT INTO input_queue_item VALUES (28520, '/monograph/2610471250', '2610471250', false);
INSERT INTO input_queue_item VALUES (28521, '/monograph/2619360997', '2619360997', false);
INSERT INTO input_queue_item VALUES (28522, '/monograph/2619417730', '2619417730', false);
INSERT INTO input_queue_item VALUES (28523, '/monograph/2619417084', '2619417084', false);
INSERT INTO input_queue_item VALUES (28524, '/monograph/2619421435', '2619421435', false);
INSERT INTO input_queue_item VALUES (28525, '/monograph/2619421053', '2619421053', false);
INSERT INTO input_queue_item VALUES (28526, '/monograph/2619204685', '2619204685', false);
INSERT INTO input_queue_item VALUES (28527, '/monograph/2619429820', '2619429820', false);
INSERT INTO input_queue_item VALUES (28528, '/monograph/2619262529', '2619262529', false);
INSERT INTO input_queue_item VALUES (28529, '/monograph/2610029373', '2610029373', false);
INSERT INTO input_queue_item VALUES (28530, '/monograph/2619210199', '2619210199', true);
INSERT INTO input_queue_item VALUES (28531, '/monograph/2619218640', '2619218640', false);
INSERT INTO input_queue_item VALUES (28532, '/monograph/2619419044', '2619419044', false);
INSERT INTO input_queue_item VALUES (28533, '/monograph/2619415199', '2619415199', false);
INSERT INTO input_queue_item VALUES (28534, '/monograph/2619416163', '2619416163', false);
INSERT INTO input_queue_item VALUES (28535, '/monograph/2619417718', '2619417718', false);
INSERT INTO input_queue_item VALUES (28536, '/monograph/2619429818', '2619429818', false);
INSERT INTO input_queue_item VALUES (28537, '/monograph/2619421440', '2619421440', false);
INSERT INTO input_queue_item VALUES (28538, '/monograph/2619361107', '2619361107', false);
INSERT INTO input_queue_item VALUES (28539, '/monograph/2619419031-1', '2619419031-1', false);
INSERT INTO input_queue_item VALUES (28540, '/monograph/2610203052', '2610203052', false);
INSERT INTO input_queue_item VALUES (28541, '/monograph/2619216076', '2619216076', false);
INSERT INTO input_queue_item VALUES (28542, '/monograph/2619417731', '2619417731', false);
INSERT INTO input_queue_item VALUES (28543, '/monograph/2619211986', '2619211986', false);
INSERT INTO input_queue_item VALUES (28544, '/monograph/26199918175', '26199918175', false);
INSERT INTO input_queue_item VALUES (28545, '/monograph/2619417085', '2619417085', false);
INSERT INTO input_queue_item VALUES (28546, '/monograph/2619419032', '2619419032', false);
INSERT INTO input_queue_item VALUES (28547, '/monograph/2619417728', '2619417728', false);
INSERT INTO input_queue_item VALUES (28548, '/monograph/2610493519', '2610493519', false);
INSERT INTO input_queue_item VALUES (28549, '/monograph/2619213255', '2619213255', false);
INSERT INTO input_queue_item VALUES (28550, '/monograph/2619421241', '2619421241', false);
INSERT INTO input_queue_item VALUES (28551, '/monograph/2619249449', '2619249449', false);
INSERT INTO input_queue_item VALUES (28552, '/monograph/2619416367', '2619416367', false);
INSERT INTO input_queue_item VALUES (28553, '/monograph/2619360988', '2619360988', false);
INSERT INTO input_queue_item VALUES (28554, '/monograph/2619210002', '2619210002', false);
INSERT INTO input_queue_item VALUES (28555, '/monograph/2619419034', '2619419034', false);
INSERT INTO input_queue_item VALUES (28556, '/monograph/000831081', '000831081', false);
INSERT INTO input_queue_item VALUES (28557, '/monograph/2619414563', '2619414563', false);
INSERT INTO input_queue_item VALUES (28558, '/monograph/2619242400', '2619242400', false);
INSERT INTO input_queue_item VALUES (28559, '/monograph/2619794367', '2619794367', false);
INSERT INTO input_queue_item VALUES (28560, '/monograph/2619421046', '2619421046', false);
INSERT INTO input_queue_item VALUES (28561, '/monograph/2619419043', '2619419043', false);
INSERT INTO input_queue_item VALUES (28562, '/monograph/2619991623', '2619991623', false);
INSERT INTO input_queue_item VALUES (28563, '/monograph/7777777777', '7777777777', false);
INSERT INTO input_queue_item VALUES (28564, '/monograph/2619222936', '2619222936', false);
INSERT INTO input_queue_item VALUES (28565, '/monograph/2619727279', '2619727279', false);
INSERT INTO input_queue_item VALUES (28566, '/monograph/2619421056', '2619421056', false);
INSERT INTO input_queue_item VALUES (28567, '/monograph/2619421058', '2619421058', false);
INSERT INTO input_queue_item VALUES (28568, '/monograph/2619757297', '2619757297', false);
INSERT INTO input_queue_item VALUES (28569, '/monograph/2619211713', '2619211713', false);
INSERT INTO input_queue_item VALUES (28570, '/monograph/2619421434', '2619421434', false);
INSERT INTO input_queue_item VALUES (28571, '/monograph/2619421049', '2619421049', false);
INSERT INTO input_queue_item VALUES (28572, '/monograph/2619306297', '2619306297', false);
INSERT INTO input_queue_item VALUES (28573, '/monograph/2619421051', '2619421051', false);
INSERT INTO input_queue_item VALUES (28574, '/monograph/2619416162', '2619416162', false);
INSERT INTO input_queue_item VALUES (28575, '/monograph/2619211703', '2619211703', false);
INSERT INTO input_queue_item VALUES (28576, '/monograph/2619358224', '2619358224', false);
INSERT INTO input_queue_item VALUES (28577, '/monograph/2619251949', '2619251949', false);
INSERT INTO input_queue_item VALUES (28578, '/monograph/2619419023', '2619419023', false);
INSERT INTO input_queue_item VALUES (28579, '/monograph/2619421445', '2619421445', false);
INSERT INTO input_queue_item VALUES (28580, '/monograph/2619416372', '2619416372', false);
INSERT INTO input_queue_item VALUES (28581, '/monograph/2619754991', '2619754991', false);
INSERT INTO input_queue_item VALUES (28582, '/monograph/2619419024', '2619419024', false);
INSERT INTO input_queue_item VALUES (28583, '/monograph/2619213993', '2619213993', false);
INSERT INTO input_queue_item VALUES (28584, '/monograph/2619253587', '2619253587', false);
INSERT INTO input_queue_item VALUES (28585, '/monograph/2619789458', '2619789458', false);
INSERT INTO input_queue_item VALUES (28586, '/monograph/2619416384', '2619416384', false);
INSERT INTO input_queue_item VALUES (28587, '/monograph/2619421229', '2619421229', false);
INSERT INTO input_queue_item VALUES (28588, '/monograph/2619761619', '2619761619', false);
INSERT INTO input_queue_item VALUES (28589, '/monograph/2619210260', '2619210260', false);
INSERT INTO input_queue_item VALUES (28590, '/monograph/2619421242', '2619421242', false);
INSERT INTO input_queue_item VALUES (28591, '/monograph/2619414554', '2619414554', false);
INSERT INTO input_queue_item VALUES (28592, '/monograph/2619421237', '2619421237', false);
INSERT INTO input_queue_item VALUES (28593, '/monograph/2619421244', '2619421244', false);
INSERT INTO input_queue_item VALUES (28594, '/monograph/2619978037', '2619978037', false);
INSERT INTO input_queue_item VALUES (28595, '/monograph/2619416161', '2619416161', false);
INSERT INTO input_queue_item VALUES (28596, '/monograph/2619228718', '2619228718', false);
INSERT INTO input_queue_item VALUES (28597, '/monograph/2619257245', '2619257245', false);
INSERT INTO input_queue_item VALUES (28598, '/monograph/2619414564', '2619414564', false);
INSERT INTO input_queue_item VALUES (28599, '/monograph/2619216041', '2619216041', false);
INSERT INTO input_queue_item VALUES (28600, '/monograph/2619218075', '2619218075', false);
INSERT INTO input_queue_item VALUES (28601, '/monograph/2619213674', '2619213674', false);
INSERT INTO input_queue_item VALUES (28602, '/monograph/2619225141', '2619225141', false);
INSERT INTO input_queue_item VALUES (28603, '/monograph/2619417721', '2619417721', false);
INSERT INTO input_queue_item VALUES (28604, '/monograph/2619414561', '2619414561', false);
INSERT INTO input_queue_item VALUES (28605, '/monograph/2619414555', '2619414555', false);
INSERT INTO input_queue_item VALUES (28606, '/monograph/2619411368', '2619411368', false);
INSERT INTO input_queue_item VALUES (28607, '/monograph/2619216031', '2619216031', false);
INSERT INTO input_queue_item VALUES (28608, '/monograph/2619419042', '2619419042', false);
INSERT INTO input_queue_item VALUES (28609, '/monograph/2619421443', '2619421443', false);
INSERT INTO input_queue_item VALUES (28610, '/monograph/2619417714', '2619417714', false);
INSERT INTO input_queue_item VALUES (28611, '/monograph/2619212506', '2619212506', false);
INSERT INTO input_queue_item VALUES (28612, '/monograph/2619417712', '2619417712', false);
INSERT INTO input_queue_item VALUES (28613, '/monograph/2619414562', '2619414562', false);
INSERT INTO input_queue_item VALUES (28614, '/monograph/2619784551', '2619784551', false);
INSERT INTO input_queue_item VALUES (28615, '/monograph/2619325397', '2619325397', false);
INSERT INTO input_queue_item VALUES (28616, '/monograph/2619421054', '2619421054', false);
INSERT INTO input_queue_item VALUES (28617, '/monograph/2619417737', '2619417737', false);
INSERT INTO input_queue_item VALUES (28618, '/monograph/2619421243', '2619421243', false);
INSERT INTO input_queue_item VALUES (28619, '/monograph/2619731753', '2619731753', false);
INSERT INTO input_queue_item VALUES (28620, '/monograph/2619421437', '2619421437', false);
INSERT INTO input_queue_item VALUES (28621, '/monograph/2619210052', '2619210052', false);
INSERT INTO input_queue_item VALUES (28622, '/monograph/2619419039', '2619419039', false);
INSERT INTO input_queue_item VALUES (28623, '/monograph/2619416394', '2619416394', false);
INSERT INTO input_queue_item VALUES (28624, '/monograph/2619419035', '2619419035', false);
INSERT INTO input_queue_item VALUES (28625, '/monograph/2619429581', '2619429581', false);
INSERT INTO input_queue_item VALUES (28626, '/monograph/2619211959', '2619211959', false);
INSERT INTO input_queue_item VALUES (28627, '/monograph/2619212444', '2619212444', false);
INSERT INTO input_queue_item VALUES (28628, '/monograph/2619885391', '2619885391', false);
INSERT INTO input_queue_item VALUES (28629, '/monograph/2619419025', '2619419025', false);
INSERT INTO input_queue_item VALUES (28630, '/monograph/2619969843', '2619969843', false);
INSERT INTO input_queue_item VALUES (28631, '/monograph/2619937339', '2619937339', false);
INSERT INTO input_queue_item VALUES (28632, '/monograph/2619218861', '2619218861', false);
INSERT INTO input_queue_item VALUES (28633, '/monograph/2619416388', '2619416388', false);
INSERT INTO input_queue_item VALUES (28634, '/monograph/2619421238', '2619421238', false);
INSERT INTO input_queue_item VALUES (28635, '/monograph/2619360989', '2619360989', false);
INSERT INTO input_queue_item VALUES (28636, '/monograph/2619417078', '2619417078', false);
INSERT INTO input_queue_item VALUES (28637, '/monograph/2619416371', '2619416371', false);
INSERT INTO input_queue_item VALUES (28638, '/monograph/2619419041', '2619419041', false);
INSERT INTO input_queue_item VALUES (28639, '/monograph/2619421448', '2619421448', false);
INSERT INTO input_queue_item VALUES (28640, '/monograph/2619421441', '2619421441', false);
INSERT INTO input_queue_item VALUES (28641, '/monograph/2619342091', '2619342091', false);
INSERT INTO input_queue_item VALUES (28642, '/monograph/2619349720', '2619349720', false);
INSERT INTO input_queue_item VALUES (28643, '/monograph/2619429825', '2619429825', false);
INSERT INTO input_queue_item VALUES (28644, '/monograph/2619416380', '2619416380', false);
INSERT INTO input_queue_item VALUES (28645, '/monograph/2610469036', '2610469036', false);
INSERT INTO input_queue_item VALUES (28646, '/monograph/2619752578', '2619752578', false);
INSERT INTO input_queue_item VALUES (28647, '/monograph/2619360924', '2619360924', false);
INSERT INTO input_queue_item VALUES (28648, '/monograph/2619930285', '2619930285', false);
INSERT INTO input_queue_item VALUES (28649, '/monograph/2619417081', '2619417081', false);
INSERT INTO input_queue_item VALUES (28650, '/monograph/2619417077', '2619417077', false);
INSERT INTO input_queue_item VALUES (28651, '/monograph/2619416396', '2619416396', false);
INSERT INTO input_queue_item VALUES (28652, '/monograph/2619421236', '2619421236', false);
INSERT INTO input_queue_item VALUES (28653, '/monograph/2619429824', '2619429824', false);
INSERT INTO input_queue_item VALUES (28654, '/monograph/2610469034', '2610469034', false);
INSERT INTO input_queue_item VALUES (28655, '/monograph/2619417723', '2619417723', false);
INSERT INTO input_queue_item VALUES (28656, '/monograph/2610473470', '2610473470', false);
INSERT INTO input_queue_item VALUES (28657, '/monograph/2619223617', '2619223617', false);
INSERT INTO input_queue_item VALUES (28658, '/monograph/2619421050', '2619421050', false);
INSERT INTO input_queue_item VALUES (28659, '/monograph/2619419047', '2619419047', false);
INSERT INTO input_queue_item VALUES (28660, '/monograph/2619724183', '2619724183', false);
INSERT INTO input_queue_item VALUES (28661, '/monograph/2619421232', '2619421232', false);
INSERT INTO input_queue_item VALUES (28662, '/monograph/2619416368', '2619416368', false);
INSERT INTO input_queue_item VALUES (28663, '/monograph/2619429826', '2619429826', false);
INSERT INTO input_queue_item VALUES (28664, '/monograph/2619221665', '2619221665', false);
INSERT INTO input_queue_item VALUES (28665, '/monograph/2619214119', '2619214119', false);
INSERT INTO input_queue_item VALUES (28666, '/monograph/2619416370', '2619416370', false);
INSERT INTO input_queue_item VALUES (28667, '/monograph/2619219194', '2619219194', false);
INSERT INTO input_queue_item VALUES (28668, '/monograph/2619417716', '2619417716', false);
INSERT INTO input_queue_item VALUES (28669, '/monograph/2619417734', '2619417734', false);
INSERT INTO input_queue_item VALUES (28670, '/monograph/2619251052', '2619251052', false);
INSERT INTO input_queue_item VALUES (28671, '/monograph/2619256329', '2619256329', false);
INSERT INTO input_queue_item VALUES (28672, '/monograph/2619414560', '2619414560', false);
INSERT INTO input_queue_item VALUES (28673, '/monograph/2610469032', '2610469032', false);
INSERT INTO input_queue_item VALUES (28674, '/monograph/2619253784', '2619253784', false);
INSERT INTO input_queue_item VALUES (28675, '/monograph/2619416369', '2619416369', false);
INSERT INTO input_queue_item VALUES (28676, '/monograph/2619419022', '2619419022', false);
INSERT INTO input_queue_item VALUES (28677, '/monograph/2619224703', '2619224703', false);
INSERT INTO input_queue_item VALUES (28678, '/monograph/2619416395', '2619416395', false);
INSERT INTO input_queue_item VALUES (28679, '/monograph/2619419031', '2619419031', false);
INSERT INTO input_queue_item VALUES (28680, '/monograph/2619229757', '2619229757', false);
INSERT INTO input_queue_item VALUES (28681, '/monograph/2619253583', '2619253583', false);
INSERT INTO input_queue_item VALUES (28682, '/monograph/2619360980', '2619360980', false);
INSERT INTO input_queue_item VALUES (28683, '/monograph/2619361001', '2619361001', false);
INSERT INTO input_queue_item VALUES (28684, '/monograph/000830944', '000830944', false);
INSERT INTO input_queue_item VALUES (28685, '/monograph/2619969304', '2619969304', false);
INSERT INTO input_queue_item VALUES (28686, '/monograph/2619417733', '2619417733', false);
INSERT INTO input_queue_item VALUES (28687, '/monograph/2619210342', '2619210342', false);
INSERT INTO input_queue_item VALUES (28688, '/monograph/2619203969', '2619203969', false);
INSERT INTO input_queue_item VALUES (28689, '/monograph/2619421044', '2619421044', false);
INSERT INTO input_queue_item VALUES (28690, '/monograph/2619421048', '2619421048', false);
INSERT INTO input_queue_item VALUES (28691, '/monograph/2619429828', '2619429828', false);
INSERT INTO input_queue_item VALUES (28692, '/monograph/2610469033', '2610469033', false);
INSERT INTO input_queue_item VALUES (28693, '/monograph/2619369640', '2619369640', false);
INSERT INTO input_queue_item VALUES (28694, '/monograph/2619421052', '2619421052', false);
INSERT INTO input_queue_item VALUES (28695, '/monograph/2619224833', '2619224833', false);
INSERT INTO input_queue_item VALUES (28696, '/monograph/2619235175', '2619235175', false);
INSERT INTO input_queue_item VALUES (28697, '/monograph/2619421228', '2619421228', false);
INSERT INTO input_queue_item VALUES (28698, '/monograph/000831091', '000831091', false);
INSERT INTO input_queue_item VALUES (28699, '/monograph/2619874655', '2619874655', false);
INSERT INTO input_queue_item VALUES (28700, '/monograph/2619416373', '2619416373', false);
INSERT INTO input_queue_item VALUES (28701, '/monograph/2619421047', '2619421047', false);
INSERT INTO input_queue_item VALUES (28702, '/monograph/2619206559', '2619206559', false);
INSERT INTO input_queue_item VALUES (28703, '/monograph/2619216174', '2619216174', false);
INSERT INTO input_queue_item VALUES (28704, '/monograph/2619417708', '2619417708', false);
INSERT INTO input_queue_item VALUES (28705, '/monograph/2619359261', '2619359261', false);
INSERT INTO input_queue_item VALUES (28706, '/monograph/2619419033', '2619419033', false);
INSERT INTO input_queue_item VALUES (28707, '/monograph/2619429821', '2619429821', false);
INSERT INTO input_queue_item VALUES (28708, '/monograph/2619416393', '2619416393', false);
INSERT INTO input_queue_item VALUES (28709, '/monograph/2610435937', '2610435937', false);
INSERT INTO input_queue_item VALUES (28710, '/monograph/2619706529', '2619706529', false);
INSERT INTO input_queue_item VALUES (28711, '/monograph/2619827591', '2619827591', false);
INSERT INTO input_queue_item VALUES (28712, '/monograph/2619211603', '2619211603', false);
INSERT INTO input_queue_item VALUES (28713, '/monograph/2619416377', '2619416377', false);
INSERT INTO input_queue_item VALUES (28714, '/monograph/2619211646', '2619211646', false);
INSERT INTO input_queue_item VALUES (28715, '/monograph/2619416374', '2619416374', false);
INSERT INTO input_queue_item VALUES (28716, '/monograph/2619228218', '2619228218', false);
INSERT INTO input_queue_item VALUES (28717, '/monograph/2619429578', '2619429578', false);
INSERT INTO input_queue_item VALUES (28718, '/monograph/2610469035', '2610469035', false);
INSERT INTO input_queue_item VALUES (28719, '/monograph/2619417709', '2619417709', false);
INSERT INTO input_queue_item VALUES (28720, '/monograph/2610466147', '2610466147', false);
INSERT INTO input_queue_item VALUES (28721, '/monograph/2619360986', '2619360986', false);
INSERT INTO input_queue_item VALUES (28722, '/monograph/2610279426', '2610279426', false);
INSERT INTO input_queue_item VALUES (28723, '/monograph/2619416385', '2619416385', false);
INSERT INTO input_queue_item VALUES (28724, '/monograph/2619725210', '2619725210', false);
INSERT INTO input_queue_item VALUES (28725, '/monograph/2619216569', '2619216569', false);
INSERT INTO input_queue_item VALUES (28726, '/monograph/2619416366', '2619416366', false);
INSERT INTO input_queue_item VALUES (28727, '/monograph/6666666666', '6666666666', false);
INSERT INTO input_queue_item VALUES (28728, '/monograph/2619414553', '2619414553', false);
INSERT INTO input_queue_item VALUES (28729, '/monograph/2619421245', '2619421245', false);
INSERT INTO input_queue_item VALUES (28730, '/monograph/2619414558', '2619414558', false);
INSERT INTO input_queue_item VALUES (28731, '/monograph/2619212989', '2619212989', false);
INSERT INTO input_queue_item VALUES (28732, '/monograph/2619429827', '2619429827', false);
INSERT INTO input_queue_item VALUES (28733, '/monograph/2619417711', '2619417711', false);
INSERT INTO input_queue_item VALUES (28734, '/monograph/2619416375', '2619416375', false);
INSERT INTO input_queue_item VALUES (28735, '/monograph/2619421230', '2619421230', false);
INSERT INTO input_queue_item VALUES (28736, '/monograph/2619417726', '2619417726', false);
INSERT INTO input_queue_item VALUES (28737, '/monograph/2619421235', '2619421235', false);
INSERT INTO input_queue_item VALUES (28738, '/monograph/2619770992', '2619770992', false);
INSERT INTO input_queue_item VALUES (28739, '/monograph/2619417735', '2619417735', false);
INSERT INTO input_queue_item VALUES (28740, '/monograph/2619416378', '2619416378', false);
INSERT INTO input_queue_item VALUES (28741, '/monograph/2619417719', '2619417719', false);
INSERT INTO input_queue_item VALUES (28742, '/monograph/2619417080', '2619417080', false);
INSERT INTO input_queue_item VALUES (28743, '/monograph/2619421436', '2619421436', false);
INSERT INTO input_queue_item VALUES (28744, '/monograph/2619255791', '2619255791', false);
INSERT INTO input_queue_item VALUES (28745, '/monograph/2619371882', '2619371882', false);
INSERT INTO input_queue_item VALUES (28746, '/monograph/2610402007', '2610402007', false);
INSERT INTO input_queue_item VALUES (28747, '/monograph/2619419037', '2619419037', false);
INSERT INTO input_queue_item VALUES (28748, '/monograph/2619421057', '2619421057', false);
INSERT INTO input_queue_item VALUES (28749, '/monograph/2619417729', '2619417729', false);
INSERT INTO input_queue_item VALUES (28750, '/monograph/2610472025', '2610472025', false);
INSERT INTO input_queue_item VALUES (28751, '/monograph/2619421233', '2619421233', false);
INSERT INTO input_queue_item VALUES (28752, '/monograph/2619419036', '2619419036', false);
INSERT INTO input_queue_item VALUES (28753, '/monograph/2620001199', '2620001199', false);
INSERT INTO input_queue_item VALUES (28754, '/monograph/2619417079', '2619417079', false);
INSERT INTO input_queue_item VALUES (28755, '/monograph/2619421045', '2619421045', false);
INSERT INTO input_queue_item VALUES (28756, '/monograph/2619213810', '2619213810', false);
INSERT INTO input_queue_item VALUES (28757, '/monograph/2619421447', '2619421447', false);
INSERT INTO input_queue_item VALUES (28758, '/monograph/2619361066', '2619361066', false);
INSERT INTO input_queue_item VALUES (28759, '/monograph/2619222619', '2619222619', false);
INSERT INTO input_queue_item VALUES (28760, '/monograph/2619421444', '2619421444', false);
INSERT INTO input_queue_item VALUES (28761, '/monograph/2619251155', '2619251155', false);
INSERT INTO input_queue_item VALUES (28762, '/monograph/2619417707', '2619417707', false);
INSERT INTO input_queue_item VALUES (28763, '/monograph/2619417720', '2619417720', false);
INSERT INTO input_queue_item VALUES (28764, '/monograph/2619414557', '2619414557', false);
INSERT INTO input_queue_item VALUES (28765, '/monograph/2619217555', '2619217555', false);
INSERT INTO input_queue_item VALUES (28766, '/monograph/2619429822', '2619429822', false);
INSERT INTO input_queue_item VALUES (28767, '/monograph/2619862638', '2619862638', false);
INSERT INTO input_queue_item VALUES (28768, '/monograph/2619231146', '2619231146', false);
INSERT INTO input_queue_item VALUES (28769, '/monograph/2619421055', '2619421055', false);
INSERT INTO input_queue_item VALUES (28770, '/monograph/2619917608', '2619917608', false);
INSERT INTO input_queue_item VALUES (28771, '/monograph/2619421234', '2619421234', false);
INSERT INTO input_queue_item VALUES (28772, '/monograph/2619213679', '2619213679', false);
INSERT INTO input_queue_item VALUES (28773, '/monograph/2619207406', '2619207406', false);
INSERT INTO input_queue_item VALUES (28774, '/monograph/2619419054', '2619419054', false);
INSERT INTO input_queue_item VALUES (28775, '/monograph/2619429823', '2619429823', false);
INSERT INTO input_queue_item VALUES (28776, '/monograph/2619417725', '2619417725', false);
INSERT INTO input_queue_item VALUES (28777, '/monograph/3119974971', '3119974971', false);
INSERT INTO input_queue_item VALUES (28778, '/monograph/2619419026', '2619419026', false);
INSERT INTO input_queue_item VALUES (28779, '/monograph/2619218355', '2619218355', false);
INSERT INTO input_queue_item VALUES (28780, '/monograph/2619851089', '2619851089', false);
INSERT INTO input_queue_item VALUES (28781, '/periodical', 'periodical', false);
INSERT INTO input_queue_item VALUES (28782, '/periodical/123456789/4-1912-0', '4-1912-0', false);
INSERT INTO input_queue_item VALUES (28783, '/periodical/123456789', '123456789', false);
INSERT INTO input_queue_item VALUES (28784, '/periodical/000258273/12-1936-0', '12-1936-0', true);
INSERT INTO input_queue_item VALUES (28785, '/periodical/000258273', '000258273', true);
INSERT INTO input_queue_item VALUES (28786, '/periodical/000000217/51-1942-0', '51-1942-0', false);
INSERT INTO input_queue_item VALUES (28787, '/periodical/000000217', '000000217', false);
INSERT INTO input_queue_item VALUES (28788, '/periodical/000173829/2610407170/38-2009-11', '38-2009-11', false);
INSERT INTO input_queue_item VALUES (28789, '/periodical/000173829/2610407170/38-2009-12', '38-2009-12', false);
INSERT INTO input_queue_item VALUES (28790, '/periodical/000173829/2610407170/38-2009-09', '38-2009-09', false);
INSERT INTO input_queue_item VALUES (28791, '/periodical/000173829/2610407170/38-2009-07_08', '38-2009-07_08', false);
INSERT INTO input_queue_item VALUES (28792, '/periodical/000173829/2610407170/38-2009-10', '38-2009-10', false);
INSERT INTO input_queue_item VALUES (28793, '/periodical/000173829/2610407170', '2610407170', false);
INSERT INTO input_queue_item VALUES (28794, '/periodical/000173829/2610382158/38-2009-05', '38-2009-05', false);
INSERT INTO input_queue_item VALUES (28795, '/periodical/000173829/2610382158/38-2009-01_02', '38-2009-01_02', true);
INSERT INTO input_queue_item VALUES (28796, '/periodical/000173829/2610382158/38-2009-03', '38-2009-03', true);
INSERT INTO input_queue_item VALUES (28797, '/periodical/000173829/2610382158/38-2009-04', '38-2009-04', true);
INSERT INTO input_queue_item VALUES (28798, '/periodical/000173829/2610382158/38-2009-06', '38-2009-06', false);
INSERT INTO input_queue_item VALUES (28799, '/periodical/000173829/2610382158', '2610382158', false);
INSERT INTO input_queue_item VALUES (28800, '/periodical/000173829/2610470089/39-2010-09', '39-2010-09', false);
INSERT INTO input_queue_item VALUES (28801, '/periodical/000173829/2610470089/39-2010-12', '39-2010-12', false);
INSERT INTO input_queue_item VALUES (28802, '/periodical/000173829/2610470089/39-2010-10', '39-2010-10', false);
INSERT INTO input_queue_item VALUES (28803, '/periodical/000173829/2610470089/39-2010-07_08', '39-2010-07_08', false);
INSERT INTO input_queue_item VALUES (28804, '/periodical/000173829/2610470089/39-2010-11', '39-2010-11', false);
INSERT INTO input_queue_item VALUES (28805, '/periodical/000173829/2610470089', '2610470089', false);
INSERT INTO input_queue_item VALUES (28806, '/periodical/000173829/2610446094/39-2010-05', '39-2010-05', false);
INSERT INTO input_queue_item VALUES (28807, '/periodical/000173829/2610446094/39-2010-01_02', '39-2010-01_02', false);
INSERT INTO input_queue_item VALUES (28808, '/periodical/000173829/2610446094/39-2010-06', '39-2010-06', false);
INSERT INTO input_queue_item VALUES (28809, '/periodical/000173829/2610446094/39-2010-04', '39-2010-04', false);
INSERT INTO input_queue_item VALUES (28810, '/periodical/000173829/2610446094/39-2010-03', '39-2010-03', false);
INSERT INTO input_queue_item VALUES (28811, '/periodical/000173829/2610446094', '2610446094', false);
INSERT INTO input_queue_item VALUES (28812, '/periodical/000173829/BC_/40-2011-12', '40-2011-12', false);
INSERT INTO input_queue_item VALUES (28813, '/periodical/000173829/BC_/40-2011-09', '40-2011-09', false);
INSERT INTO input_queue_item VALUES (28814, '/periodical/000173829/BC_/40-2011-10', '40-2011-10', false);
INSERT INTO input_queue_item VALUES (28815, '/periodical/000173829/BC_/40-2011-07_08', '40-2011-07_08', false);
INSERT INTO input_queue_item VALUES (28816, '/periodical/000173829/BC_/40-2011-11', '40-2011-11', false);
INSERT INTO input_queue_item VALUES (28817, '/periodical/000173829/BC_', 'BC_', false);
INSERT INTO input_queue_item VALUES (28818, '/periodical/000173829/2610494780/40-2011-04', '40-2011-04', false);
INSERT INTO input_queue_item VALUES (28819, '/periodical/000173829/2610494780/40-2011-01_02', '40-2011-01_02', false);
INSERT INTO input_queue_item VALUES (28820, '/periodical/000173829/2610494780/40-2011-03', '40-2011-03', false);
INSERT INTO input_queue_item VALUES (28821, '/periodical/000173829/2610494780/40-2011-05', '40-2011-05', false);
INSERT INTO input_queue_item VALUES (28822, '/periodical/000173829/2610494780/40-2011-06', '40-2011-06', false);
INSERT INTO input_queue_item VALUES (28823, '/periodical/000173829/2610494780', '2610494780', false);
INSERT INTO input_queue_item VALUES (28824, '/periodical/000173829', '000173829', false);
INSERT INTO input_queue_item VALUES (28825, '/periodical/000724247/26-1886-0', '26-1886-0', false);
INSERT INTO input_queue_item VALUES (28826, '/periodical/000724247', '000724247', false);
INSERT INTO input_queue_item VALUES (28827, '/periodical/22-1906-2', '22-1906-2', false);
INSERT INTO input_queue_item VALUES (28828, '/periodical/000454262/29-1920-0', '29-1920-0', false);
INSERT INTO input_queue_item VALUES (28829, '/periodical/000454262/39-1930-0', '39-1930-0', false);
INSERT INTO input_queue_item VALUES (28830, '/periodical/000454262/34-1925-0', '34-1925-0', false);
INSERT INTO input_queue_item VALUES (28831, '/periodical/000454262/30-1921-0', '30-1921-0', false);
INSERT INTO input_queue_item VALUES (28832, '/periodical/000454262/36-1927-0', '36-1927-0', false);
INSERT INTO input_queue_item VALUES (28833, '/periodical/000454262/43-1934-0', '43-1934-0', false);
INSERT INTO input_queue_item VALUES (28834, '/periodical/000454262/32-1923-0', '32-1923-0', false);
INSERT INTO input_queue_item VALUES (28835, '/periodical/000454262/41-1932-0', '41-1932-0', false);
INSERT INTO input_queue_item VALUES (28836, '/periodical/000454262/31-1922-0', '31-1922-0', false);
INSERT INTO input_queue_item VALUES (28837, '/periodical/000454262', '000454262', false);
INSERT INTO input_queue_item VALUES (28838, '/periodical/000166022/3-1993-0', '3-1993-0', false);
INSERT INTO input_queue_item VALUES (28839, '/periodical/000166022/5-1995-0', '5-1995-0', false);
INSERT INTO input_queue_item VALUES (28840, '/periodical/000166022/1-1991-0', '1-1991-0', false);
INSERT INTO input_queue_item VALUES (28841, '/periodical/000166022/2-1992-0', '2-1992-0', false);
INSERT INTO input_queue_item VALUES (28842, '/periodical/000166022/4-1994-0', '4-1994-0', false);
INSERT INTO input_queue_item VALUES (28843, '/periodical/000166022/0-1991-0', '0-1991-0', false);
INSERT INTO input_queue_item VALUES (28844, '/periodical/000166022', '000166022', false);
INSERT INTO input_queue_item VALUES (28845, '/periodical/test/3-1911-0', '3-1911-0', false);
INSERT INTO input_queue_item VALUES (28846, '/periodical/test/1-1909-0', '1-1909-0', false);
INSERT INTO input_queue_item VALUES (28847, '/periodical/test/2-1910-0', '2-1910-0', false);


--
-- Data for Name: lock; Type: TABLE DATA; Schema: public; Owner: meditor
--



--
-- Data for Name: open_id_identity; Type: TABLE DATA; Schema: public; Owner: meditor
--

INSERT INTO open_id_identity VALUES (16, 1, 'https://www.google.com/profiles/109255519115168093543');
INSERT INTO open_id_identity VALUES (17, 57, 'https://www.google.com/profiles/109168724441055178992');
INSERT INTO open_id_identity VALUES (21, 60, 'https://www.google.com/profiles/111227031824448142223');
INSERT INTO open_id_identity VALUES (23, 61, 'https://www.google.com/profiles/102338850479877788412');
INSERT INTO open_id_identity VALUES (27, 63, 'https://www.google.com/profiles/112025888785317734498');
INSERT INTO open_id_identity VALUES (29, 65, 'https://www.google.com/profiles/114804369744409916883');
INSERT INTO open_id_identity VALUES (30, 66, 'https://www.google.com/profiles/106800333256714812404');
INSERT INTO open_id_identity VALUES (32, 68, 'https://www.google.com/profiles/109427901142654977833');
INSERT INTO open_id_identity VALUES (33, 69, 'https://www.google.com/profiles/107158839619798289683');
INSERT INTO open_id_identity VALUES (34, 70, 'https://www.google.com/profiles/104695347440971724820');
INSERT INTO open_id_identity VALUES (35, 71, 'http://www.facebook.com/profile.php?id=1132050174');
INSERT INTO open_id_identity VALUES (36, 72, 'https://www.google.com/profiles/115659944869044068968');
INSERT INTO open_id_identity VALUES (37, 73, 'https://www.google.com/profiles/108991013130146150896');
INSERT INTO open_id_identity VALUES (38, 74, 'https://www.google.com/profiles/107603833070632995546');
INSERT INTO open_id_identity VALUES (39, 1, 'http://www.linkedin.com/profile?viewProfile=uA8VssC4VZ');
INSERT INTO open_id_identity VALUES (40, 75, 'https://www.google.com/profiles/101122328644663140746');
INSERT INTO open_id_identity VALUES (41, 76, 'https://www.google.com/profiles/104089873407129540837');
INSERT INTO open_id_identity VALUES (42, 77, 'https://www.google.com/profiles/103957005951093347146');
INSERT INTO open_id_identity VALUES (44, 79, 'https://www.google.com/profiles/108722414269138650521');
INSERT INTO open_id_identity VALUES (45, 63, 'http://pavluska.myopenid.com/');
INSERT INTO open_id_identity VALUES (46, 81, 'http://pesta.myopenid.com/');
INSERT INTO open_id_identity VALUES (47, 69, 'https://www.google.com/profiles/112339578258292421280');
INSERT INTO open_id_identity VALUES (48, 1, 'http://www.facebook.com/profile.php?id=1611572776');
INSERT INTO open_id_identity VALUES (49, 82, 'http://jiranova.myopenid.com/');


--
-- Data for Name: recently_modified_item; Type: TABLE DATA; Schema: public; Owner: meditor
--

INSERT INTO recently_modified_item VALUES (793, 'uuid:7ec83962-e7b9-45cd-bef3-dcbb7715fe2f', 'Proti výminečnému stavu v Praze! /', '', '2011-11-30 19:01:12.9102', 0, 1);
INSERT INTO recently_modified_item VALUES (885, 'uuid:211b3e6b-b9d9-11e0-8dee-005056be0007', '[33]', '', '2012-01-04 09:04:33.36022', 5, 68);
INSERT INTO recently_modified_item VALUES (834, 'uuid:1e67de35-b9d9-11e0-8dee-005056be0007', '8', '', '2011-12-19 12:55:25.174723', 4, 68);
INSERT INTO recently_modified_item VALUES (794, 'uuid:0721a7e5-8cf0-4cd2-b99b-abcfdabb78ff', 'Kázanj při weřegné sláwnosti smutků : nad smrti neygasněgssjho arcywýwody rakauského, kardynala, a arcybiskupa holomauckého Jana Rudolfa w chrámu arcybiskupským dne 17. srpna 1831 /', '', '2011-12-04 19:37:32.722173', 0, 1);
INSERT INTO recently_modified_item VALUES (991, 'uuid:8b648794-3777-4eef-b9a5-89d966b543e7', '66', '', '2012-01-20 14:02:38.067756', 5, 1);
INSERT INTO recently_modified_item VALUES (818, 'uuid:64938a9f-d8af-4f35-8933-3d25558a97e9', 'Evolutionary computation in economics and finance /', '', '2012-01-20 14:05:57.468614', 0, 1);
INSERT INTO recently_modified_item VALUES (817, 'uuid:ff09038d-b9d8-11e0-8dee-005056be0007', '[1]', '', '2011-12-05 00:57:14.058851', 5, 68);
INSERT INTO recently_modified_item VALUES (903, 'uuid:2115720a-b9d9-11e0-8dee-005056be0007', '9', '', '2012-01-17 08:34:59.128478', 4, 68);
INSERT INTO recently_modified_item VALUES (1001, 'uuid:d5fa2c9d-5ab6-47a4-b88f-f48ec0432585', '12 Etudes pour le Cor chromatique et le Cor simple avec accomp. de Piano. [hudebnina] /', '', '2012-01-20 22:13:51.287141', 0, 1);
INSERT INTO recently_modified_item VALUES (940, 'uuid:5bd604fd-070c-11e1-aa24-0050569d679d', 'Jižní Morava ...', '', '2012-01-04 15:24:01.327461', 2, 68);
INSERT INTO recently_modified_item VALUES (819, 'uuid:6b736b26-c8e5-4c8d-8b2c-7a5d7f72ce68', 'M??hrisch-schlesischer Correspondent : Illustriertes Morgenbatt', '', '2012-01-20 14:06:07.534163', 0, 1);
INSERT INTO recently_modified_item VALUES (835, 'uuid:1fa8c2a8-b9d9-11e0-8dee-005056be0007', '31', '', '2011-12-13 14:43:02.213365', 5, 68);
INSERT INTO recently_modified_item VALUES (904, 'uuid:57b93e65-d925-11e0-b33a-0050569d679d', 'Divadelní šepty', '', '2012-01-04 09:11:28.632867', 2, 68);
INSERT INTO recently_modified_item VALUES (1004, 'uuid:c75369e3-773c-4294-b5f7-ffa6ab24eda7', 'Divadelní list Národního divadla v Brně', '', '2012-01-11 18:18:01.771855', 2, 1);
INSERT INTO recently_modified_item VALUES (895, 'uuid:c6db2b90-1400-4293-b2ca-968cc3613180', 'Divadelní list Národního divadla v Brně', '', '2012-01-13 10:56:45.330759', 2, 68);
INSERT INTO recently_modified_item VALUES (886, 'uuid:5af55e83-90ef-4568-ac87-e9a24f45f6f1', 'Divadelní list Národního divadla v Brně', '', '2012-01-16 15:16:38.222882', 2, 68);
INSERT INTO recently_modified_item VALUES (905, 'uuid:661dc954-ef8d-4e41-9814-9a4f4713393b', 'Wasser-Heil-Anstalt des Dr. Winternitz in Kaltenleutgeben, bei Wien /', '', '2012-01-04 13:58:59.577683', 0, 61);
INSERT INTO recently_modified_item VALUES (734, 'uuid:fecd5a1a-b9d8-11e0-8dee-005056be0007', 'Hudební listy', '', '2011-11-28 20:42:12.307152', 2, 1);
INSERT INTO recently_modified_item VALUES (735, 'uuid:e7d58993-e38d-11e0-822b-001e4ff27ac1', 'Moje první lásky (a jiné milostné povídky)', '', '2011-11-28 20:42:44.850276', 0, 1);
INSERT INTO recently_modified_item VALUES (737, 'uuid:086a5e8a-d899-4f2f-add9-e72f24583e9e', '114', '', '2011-11-28 20:44:10.752917', 5, 1);
INSERT INTO recently_modified_item VALUES (736, 'uuid:a5f2da0d-364a-4be0-9d73-1971fe022eaa', 'Orbis Pictus :In Hungaricum, Germanicum, Et Slavicum Translatus, Et Hic Ibive Emendatus /Ioann. A...', '', '2011-11-30 15:22:12.468654', 0, 1);
INSERT INTO recently_modified_item VALUES (824, 'uuid:13f650ad-6447-11e0-8ad7-0050569d679d', 'Duha', '', '2011-12-06 09:46:06.778597', 2, 79);
INSERT INTO recently_modified_item VALUES (825, 'uuid:a0e8eb22-ad76-4bf9-98f7-c812ca2131e6', 'Soupis knížek lidového čtení z fondů Universitní knihovny v Brně /', '', '2011-12-06 09:46:10.662793', 0, 79);
INSERT INTO recently_modified_item VALUES (941, 'uuid:5bd6a13e-070c-11e1-aa24-0050569d679d', '39', '', '2012-01-04 15:28:41.468164', 3, 68);
INSERT INTO recently_modified_item VALUES (896, 'uuid:aa02b1b1-ae79-4a0e-a977-47b78a7511ea', 'PV', '', '2012-01-02 16:06:47.634358', 3, 68);
INSERT INTO recently_modified_item VALUES (740, 'uuid:a5f2da0d-364a-4be0-9d73-1971fe022eaa', 'Orbis Pictus :In Hungaricum, Germanicum, Et Slavicum Translatus, Et Hic Ibive Emendatus /Ioann. A...', '', '2011-11-28 23:39:45.404841', 0, 68);
INSERT INTO recently_modified_item VALUES (743, 'uuid:661dc954-ef8d-4e41-9814-9a4f4713393b', 'Wasser-Heil-Anstalt des Dr. Winternitz in Kaltenleutgeben, bei Wien /', '', '2011-11-28 23:40:28.287751', 0, 68);
INSERT INTO recently_modified_item VALUES (744, 'uuid:086a5e8a-d899-4f2f-add9-e72f24583e9e', '114', '', '2011-11-28 23:40:38.890533', 5, 68);
INSERT INTO recently_modified_item VALUES (745, 'uuid:e7d58993-e38d-11e0-822b-001e4ff27ac1', 'Moje první lásky (a jiné milostné povídky)', '', '2011-11-28 23:40:50.49238', 0, 68);
INSERT INTO recently_modified_item VALUES (750, 'uuid:0e79f4c7-b9d9-11e0-8dee-005056be0007', '2', '', '2011-11-28 23:43:28.161756', 4, 68);
INSERT INTO recently_modified_item VALUES (751, 'uuid:0f196919-b9d9-11e0-8dee-005056be0007', '6', '', '2011-11-28 23:43:40.257743', 5, 68);
INSERT INTO recently_modified_item VALUES (749, 'uuid:02482902-b9d9-11e0-8dee-005056be0007', '[37]', '', '2011-11-28 23:43:57.416216', 5, 68);
INSERT INTO recently_modified_item VALUES (861, 'uuid:50b1120a-7e63-4555-832e-8e82379c9dd8', '4', '', '2011-12-22 15:14:37.04016', 5, 68);
INSERT INTO recently_modified_item VALUES (753, 'uuid:13d850c2-b9d9-11e0-8dee-005056be0007', '[13]', '', '2011-12-02 10:33:48.317441', 5, 68);
INSERT INTO recently_modified_item VALUES (821, 'uuid:13f650ad-6447-11e0-8ad7-0050569d679d', 'Duha', '', '2011-12-06 09:24:12.287882', 2, 68);
INSERT INTO recently_modified_item VALUES (888, 'uuid:8ba3d867-5660-4b98-b660-b3747d77c209', 'IP', '', '2012-01-04 09:01:45.374663', 6, 68);
INSERT INTO recently_modified_item VALUES (746, 'uuid:fecd5a1a-b9d8-11e0-8dee-005056be0007', 'Hudební listy', '', '2012-01-20 12:54:04.728537', 2, 68);
INSERT INTO recently_modified_item VALUES (752, 'uuid:13d2ab71-b9d9-11e0-8dee-005056be0007', '4', '', '2012-01-02 08:50:43.87232', 4, 68);
INSERT INTO recently_modified_item VALUES (1011, 'uuid:17729ffe-ef76-430f-b343-23711d90ec72', '13', '', '2012-01-13 09:07:10.917051', 5, 69);
INSERT INTO recently_modified_item VALUES (755, 'uuid:fee8f86c-b9d8-11e0-8dee-005056be0007', '1', '', '2012-01-17 08:35:37.631551', 4, 68);
INSERT INTO recently_modified_item VALUES (742, 'uuid:ede716b9-f66d-4ebe-b421-8a8143b862bc', '1', '', '2012-01-18 09:22:37.948336', 5, 68);
INSERT INTO recently_modified_item VALUES (747, 'uuid:fee798db-b9d8-11e0-8dee-005056be0007', 'I', '', '2012-01-20 14:06:13.735557', 3, 68);
INSERT INTO recently_modified_item VALUES (738, 'uuid:661dc954-ef8d-4e41-9814-9a4f4713393b', 'Wasser-Heil-Anstalt des Dr. Winternitz in Kaltenleutgeben, bei Wien /', '', '2012-01-20 14:06:20.333942', 0, 1);
INSERT INTO recently_modified_item VALUES (754, 'uuid:f0d485b7-b382-4d13-8a31-c136b2112eee', '"2.Krejčíkovo vypravování !Ve stínu lípy"" a jeho blíženec v ""Kandidátu nesmrtelnosti"""', '', '2012-01-19 08:00:14.651773', 6, 68);
INSERT INTO recently_modified_item VALUES (760, 'uuid:f0d485b7-b382-4d13-8a31-c136b2112eef', '"Krejčíkovo vypravování !Ve stínu lípy"" a jeho blíženec v ""Kandidátu nesmrtelnosti"""', '', '2012-01-19 08:02:17.060511', 6, 68);
INSERT INTO recently_modified_item VALUES (739, 'uuid:ede716b9-f66d-4ebe-b421-8a8143b862bc', '1', '', '2011-11-29 14:11:45.464246', 5, 1);
INSERT INTO recently_modified_item VALUES (748, 'uuid:024283b1-b9d9-11e0-8dee-005056be0007', '10', '', '2012-01-19 09:37:39.047318', 4, 68);
INSERT INTO recently_modified_item VALUES (897, 'uuid:b58f2aee-94a1-475e-9c04-eee44da303c2', 'IP', '', '2012-01-02 15:56:46.522521', 6, 68);
INSERT INTO recently_modified_item VALUES (1050, 'uuid:2610e8c8-bdbf-448b-9579-4842ba564cc3', 'PV', '', '2012-01-20 11:30:38.753098', 3, 68);
INSERT INTO recently_modified_item VALUES (812, 'uuid:2115cde6-4d90-4345-a949-8042bc4d45ba', '4', '', '2011-12-02 12:02:59.256909', 5, 68);
INSERT INTO recently_modified_item VALUES (807, 'uuid:a0e8eb22-ad76-4bf9-98f7-c812ca2131e6', 'Soupis knížek lidového čtení z fondů Universitní knihovny v Brně /', '', '2011-12-04 19:37:24.072064', 0, 1);
INSERT INTO recently_modified_item VALUES (862, 'uuid:675dae8b-4038-49ad-9bb5-99e2c8d8f553', 'Divadelní list Národního divadla v Brně', '', '2011-12-29 13:54:07.872007', 2, 68);
INSERT INTO recently_modified_item VALUES (889, 'uuid:c9922136-1b27-411c-b642-4b6cd7c434a3', 'PV', '', '2012-01-13 09:24:57.304699', 3, 68);
INSERT INTO recently_modified_item VALUES (898, 'uuid:89d79b84-05ec-4f3a-856d-da1ff8ae48a6', '2', '', '2012-01-02 14:52:47.101742', 5, 68);
INSERT INTO recently_modified_item VALUES (822, 'uuid:f52f7aa4-6447-11e0-8ad7-0050569d679d', '2', '', '2011-12-06 10:56:48.334206', 3, 68);
INSERT INTO recently_modified_item VALUES (764, 'uuid:13d850c2-b9d9-11e0-8dee-005056be0007', '[13]', '', '2011-11-29 14:03:37.724586', 5, 61);
INSERT INTO recently_modified_item VALUES (763, 'uuid:f0d485b7-b382-4d13-8a31-c136b2112eee', '"2.Krejčíkovo vypravování !Ve stínu lípy"" a jeho blíženec v ""Kandidátu nesmrtelnosti"""', '', '2012-01-03 22:44:33.973365', 6, 1);
INSERT INTO recently_modified_item VALUES (890, 'uuid:0bb0f3b6-736a-4a17-95a9-46490e353612', '17', '', '2012-01-02 14:10:27.336932', 5, 68);
INSERT INTO recently_modified_item VALUES (762, 'uuid:13d850c2-b9d9-11e0-8dee-005056be0007', '[13]', '', '2011-11-30 14:20:27.544443', 5, 1);
INSERT INTO recently_modified_item VALUES (1014, 'uuid:8c148db6-895a-4aad-bc34-d95a5e7c7fcd', 'Finanční věda. /', '', '2012-01-13 17:02:12.671683', 0, 1);
INSERT INTO recently_modified_item VALUES (863, 'uuid:633a8d68-a639-40a3-a52b-80401cda8cda', 'PV', '', '2011-12-29 13:53:55.000451', 3, 68);
INSERT INTO recently_modified_item VALUES (1015, 'uuid:c58ab9ae-0d27-4a55-9229-77b9f3989db5', '1', '', '2012-01-13 17:02:30.976357', 5, 1);
INSERT INTO recently_modified_item VALUES (899, 'uuid:1c424717-4e00-4001-8a89-a3c5d127ae1f', '1', '', '2012-01-02 15:25:45.413667', 5, 68);
INSERT INTO recently_modified_item VALUES (1061, 'uuid:1bc56df0-b9d9-11e0-8dee-005056be0007', '7', '', '2012-01-20 13:12:32.738447', 4, 69);
INSERT INTO recently_modified_item VALUES (1059, 'uuid:fecd5a1a-b9d8-11e0-8dee-005056be0007', 'Hudební listy', '', '2012-01-20 13:12:41.415049', 2, 69);
INSERT INTO recently_modified_item VALUES (1060, 'uuid:fee798db-b9d8-11e0-8dee-005056be0007', 'I', '', '2012-01-20 13:18:47.187359', 3, 69);
INSERT INTO recently_modified_item VALUES (1016, 'uuid:59cb9f30-e366-4ba9-89b8-4be0deedf916', '1', '', '2012-01-20 13:58:22.280551', 5, 1);
INSERT INTO recently_modified_item VALUES (891, 'uuid:89973eb6-2d36-4362-91e6-d50500b639fe', 'PV2', '', '2012-01-09 13:50:57.815927', 3, 68);
INSERT INTO recently_modified_item VALUES (769, 'uuid:fa130a4b-832a-4b4f-a7ae-e5e0f94f7e50', 'PI', '', '2011-11-29 14:52:56.440287', 4, 1);
INSERT INTO recently_modified_item VALUES (770, 'uuid:42338da9-5287-41a3-a79b-8956ed28acc2', 'IP1', '', '2011-11-29 14:53:16.190216', 6, 1);
INSERT INTO recently_modified_item VALUES (865, 'uuid:7f3bc29e-9369-47c5-836c-b5d2068afc2d', '2', '', '2011-12-23 09:37:15.607992', 5, 68);
INSERT INTO recently_modified_item VALUES (1021, 'uuid:85495043-a0af-11e0-9938-005056be0007', '[1]', '', '2012-01-18 08:50:33.284203', 5, 68);
INSERT INTO recently_modified_item VALUES (1022, 'uuid:85492932-a0af-11e0-9938-005056be0007', 'new title', '', '2012-01-18 08:50:36.154921', 4, 68);
INSERT INTO recently_modified_item VALUES (1023, 'uuid:85492931-a0af-11e0-9938-005056be0007', '1-1', '', '2012-01-18 08:50:37.643538', 3, 68);
INSERT INTO recently_modified_item VALUES (892, 'uuid:f092b9fd-4a8a-4586-8640-ab807ac20b15', 'IP2', '', '2012-01-04 08:59:56.328372', 6, 68);
INSERT INTO recently_modified_item VALUES (786, 'uuid:13d2ab71-b9d9-11e0-8dee-005056be0007', '4', '', '2011-11-30 14:20:34.281417', 4, 1);
INSERT INTO recently_modified_item VALUES (785, 'uuid:f0d485b7-b382-4d13-8a31-c136b2112eef', '"Krejčíkovo vypravování !Ve stínu lípy"" a jeho blíženec v ""Kandidátu nesmrtelnosti"""', '', '2011-11-30 14:24:45.391366', 6, 1);
INSERT INTO recently_modified_item VALUES (787, 'uuid:147490c3-b9d9-11e0-8dee-005056be0007', '14', '', '2011-11-30 14:24:53.096533', 5, 1);
INSERT INTO recently_modified_item VALUES (791, 'uuid:20558c0e-0694-451b-a57a-335722e62991', 'Divadelní list Národního divadla v Brně', '', '2011-11-30 16:23:16.989223', 2, 1);
INSERT INTO recently_modified_item VALUES (816, 'uuid:00cf92bf-b9d9-11e0-8dee-005056be0007', '3', '', '2011-12-06 08:33:30.871753', 5, 68);
INSERT INTO recently_modified_item VALUES (854, 'uuid:fe81c5a6-435d-11dd-b505-00145e5790ea', '20', '', '2012-01-03 13:37:08.560825', 4, 68);
INSERT INTO recently_modified_item VALUES (866, 'uuid:b87c7f01-21f1-4310-8906-9f1b0fd34a8e', '3', '', '2011-12-23 09:43:03.848826', 5, 68);
INSERT INTO recently_modified_item VALUES (845, 'uuid:db04e235-435e-11dd-b505-00145e5790ea', '(1)', '', '2012-01-02 08:01:20.276694', 5, 68);
INSERT INTO recently_modified_item VALUES (902, 'uuid:be555c6f-bc69-4b28-a47f-874dad14a9b0', 'Finanční věda. /', '', '2012-01-03 22:43:51.170113', 0, 1);
INSERT INTO recently_modified_item VALUES (855, 'uuid:b8a56808-435d-11dd-b505-00145e5790ea', '38', '', '2012-01-02 08:02:39.155505', 4, 68);
INSERT INTO recently_modified_item VALUES (853, 'uuid:90558288-435f-11dd-b505-00145e5790ea', '(2)', '', '2012-01-02 16:18:28.701402', 5, 68);
INSERT INTO recently_modified_item VALUES (789, 'uuid:9204f7c1-3306-44fa-a888-be62a63d4900', 'SP', '', '2012-01-03 22:59:10.891154', 5, 1);
INSERT INTO recently_modified_item VALUES (911, 'uuid:18327d81-06d8-11e1-aa24-0050569d679d', 'Jižní Morava ...', '', '2012-01-04 15:00:57.476421', 2, 68);
INSERT INTO recently_modified_item VALUES (788, 'uuid:fee8f86c-b9d8-11e0-8dee-005056be0007', '1', '', '2012-01-20 14:06:28.672991', 4, 1);
INSERT INTO recently_modified_item VALUES (968, 'uuid:df685853-b058-4ac1-baeb-8fe083ec3e69', '12 Etudes pour le Cor chromatique et le Cor simple avec accomp. de Piano. [hudebnina] /', '', '2012-01-06 15:00:55.606768', 0, 1);
INSERT INTO recently_modified_item VALUES (847, 'uuid:b23d3ce2-435d-11dd-b505-00145e5790ea', '1', '', '2011-12-21 07:52:47.151199', 3, 68);
INSERT INTO recently_modified_item VALUES (848, 'uuid:b8cf84b6-435d-11dd-b505-00145e5790ea', '1', '', '2011-12-21 07:59:07.106788', 4, 68);
INSERT INTO recently_modified_item VALUES (849, 'uuid:b8cf84b7-435d-11dd-b505-00145e5790ea', '2', '', '2011-12-21 07:59:15.280184', 4, 68);
INSERT INTO recently_modified_item VALUES (846, 'uuid:ae876087-435d-11dd-b505-00145e5790ea', 'Národní listy', '', '2011-12-21 08:09:44.546141', 2, 68);
INSERT INTO recently_modified_item VALUES (850, 'uuid:b2ab683b-435d-11dd-b505-00145e5790ea', '15', '', '2011-12-21 08:09:53.641604', 3, 68);
INSERT INTO recently_modified_item VALUES (851, 'uuid:b2aca0bc-435d-11dd-b505-00145e5790ea', '16', '', '2011-12-21 08:10:53.636415', 3, 68);
INSERT INTO recently_modified_item VALUES (852, 'uuid:b8a78a28-435d-11dd-b505-00145e5790ea', '1', '', '2011-12-21 08:18:21.512937', 4, 68);
INSERT INTO recently_modified_item VALUES (973, 'uuid:89973eb6-2d36-4362-91e6-d50500b639fe', 'PV2', '', '2012-01-09 13:38:31.908776', 3, 69);
INSERT INTO recently_modified_item VALUES (972, 'uuid:5af55e83-90ef-4568-ac87-e9a24f45f6f1', 'Divadelní list Národního divadla v Brně', '', '2012-01-13 09:06:18.191794', 2, 69);
INSERT INTO recently_modified_item VALUES (988, 'uuid:87f6e957-90de-11e0-a36c-0050569d679d', 'Adressbuch von Brünn', '', '2012-01-09 14:45:35.747009', 2, 68);
INSERT INTO recently_modified_item VALUES (1035, 'uuid:32cf94c4-db96-4fa5-b980-89369fe221ce', 'Divadelní list Národního divadla v Brně', '', '2012-01-18 14:02:57.791277', 2, 68);
INSERT INTO recently_modified_item VALUES (1034, 'uuid:a61615be-c290-4d10-b127-40e03c6ca8cd', 'Divadelní list Národního divadla v Brně', '', '2012-01-18 14:02:58.970601', 2, 68);
INSERT INTO recently_modified_item VALUES (1036, 'uuid:15af7fe6-69e8-41af-aa12-62cfd6c23566', 'pv', '', '2012-01-18 14:34:48.396629', 3, 68);


--
-- Data for Name: request_for_adding; Type: TABLE DATA; Schema: public; Owner: meditor
--

INSERT INTO request_for_adding VALUES (23, 'Jiří Herman', 'http://www.facebook.com/profile.php?id=1536979671', '2011-08-16 11:40:36.511668');
INSERT INTO request_for_adding VALUES (27, 'novelli.metis', 'https://www.google.com/profiles/109009001433606918660', '2011-11-09 08:45:52.106863');
INSERT INTO request_for_adding VALUES (29, 'FiserMarek', 'https://www.google.com/profiles/117997991055033788704', '2011-12-02 16:35:52.937103');


--
-- Data for Name: role; Type: TABLE DATA; Schema: public; Owner: meditor
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
-- Data for Name: stored_files; Type: TABLE DATA; Schema: public; Owner: meditor
--

INSERT INTO stored_files VALUES (1, 68, 'uuid:ad4853eb-b844-11e0-95bd-005056be0007', 'test', 'my desc', '2011-11-07 08:39:34.684651', 'test file name');


--
-- Data for Name: user_in_role; Type: TABLE DATA; Schema: public; Owner: meditor
--

INSERT INTO user_in_role VALUES (1, 1, 1, '2010-12-13 18:22:16.482');
INSERT INTO user_in_role VALUES (5, 1, 3, '2010-12-15 22:19:42.977731');
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
INSERT INTO user_in_role VALUES (32, 69, 3, '2012-01-09 07:59:38.019503');
INSERT INTO user_in_role VALUES (33, 81, 3, '2012-01-09 08:02:00.615417');
INSERT INTO user_in_role VALUES (34, 82, 3, '2012-01-09 15:09:46.987617');


--
-- Data for Name: version; Type: TABLE DATA; Schema: public; Owner: meditor
--

INSERT INTO version VALUES (1, 1);


--
-- Name: editor_user_pkey; Type: CONSTRAINT; Schema: public; Owner: meditor; Tablespace: 
--

ALTER TABLE ONLY editor_user
    ADD CONSTRAINT editor_user_pkey PRIMARY KEY (id);


--
-- Name: image_identifier_uniq; Type: CONSTRAINT; Schema: public; Owner: meditor; Tablespace: 
--

ALTER TABLE ONLY image
    ADD CONSTRAINT image_identifier_uniq UNIQUE (identifier);


--
-- Name: image_pkey; Type: CONSTRAINT; Schema: public; Owner: meditor; Tablespace: 
--

ALTER TABLE ONLY image
    ADD CONSTRAINT image_pkey PRIMARY KEY (id);


--
-- Name: input_queue_item_pkey; Type: CONSTRAINT; Schema: public; Owner: meditor; Tablespace: 
--

ALTER TABLE ONLY input_queue_item
    ADD CONSTRAINT input_queue_item_pkey PRIMARY KEY (id);


--
-- Name: lock_pkey; Type: CONSTRAINT; Schema: public; Owner: meditor; Tablespace: 
--

ALTER TABLE ONLY lock
    ADD CONSTRAINT lock_pkey PRIMARY KEY (id);


--
-- Name: open_id_identity_pkey; Type: CONSTRAINT; Schema: public; Owner: meditor; Tablespace: 
--

ALTER TABLE ONLY open_id_identity
    ADD CONSTRAINT open_id_identity_pkey PRIMARY KEY (id);


--
-- Name: path_unique; Type: CONSTRAINT; Schema: public; Owner: meditor; Tablespace: 
--

ALTER TABLE ONLY input_queue_item
    ADD CONSTRAINT path_unique UNIQUE (path);


--
-- Name: recently_modified_item_pkey; Type: CONSTRAINT; Schema: public; Owner: meditor; Tablespace: 
--

ALTER TABLE ONLY recently_modified_item
    ADD CONSTRAINT recently_modified_item_pkey PRIMARY KEY (id);


--
-- Name: request_for_adding_id_key; Type: CONSTRAINT; Schema: public; Owner: meditor; Tablespace: 
--

ALTER TABLE ONLY request_for_adding
    ADD CONSTRAINT request_for_adding_id_key UNIQUE (id);


--
-- Name: role_pkey; Type: CONSTRAINT; Schema: public; Owner: meditor; Tablespace: 
--

ALTER TABLE ONLY role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);


--
-- Name: stored_files_pkey; Type: CONSTRAINT; Schema: public; Owner: meditor; Tablespace: 
--

ALTER TABLE ONLY stored_files
    ADD CONSTRAINT stored_files_pkey PRIMARY KEY (id);


--
-- Name: user_in_role_pkey; Type: CONSTRAINT; Schema: public; Owner: meditor; Tablespace: 
--

ALTER TABLE ONLY user_in_role
    ADD CONSTRAINT user_in_role_pkey PRIMARY KEY (id);


--
-- Name: version_pkey; Type: CONSTRAINT; Schema: public; Owner: meditor; Tablespace: 
--

ALTER TABLE ONLY version
    ADD CONSTRAINT version_pkey PRIMARY KEY (id);


--
-- Name: identifier_idx; Type: INDEX; Schema: public; Owner: meditor; Tablespace: 
--

CREATE UNIQUE INDEX identifier_idx ON image USING btree (identifier);


--
-- Name: old_fs_path_idx; Type: INDEX; Schema: public; Owner: meditor; Tablespace: 
--

CREATE UNIQUE INDEX old_fs_path_idx ON image USING btree (old_fs_path);


--
-- Name: lock_fk_editor_user; Type: FK CONSTRAINT; Schema: public; Owner: meditor
--

ALTER TABLE ONLY lock
    ADD CONSTRAINT lock_fk_editor_user FOREIGN KEY (user_id) REFERENCES editor_user(id) MATCH FULL;


--
-- Name: open_id_identity_fk_editor_user; Type: FK CONSTRAINT; Schema: public; Owner: meditor
--

ALTER TABLE ONLY open_id_identity
    ADD CONSTRAINT open_id_identity_fk_editor_user FOREIGN KEY (user_id) REFERENCES editor_user(id) MATCH FULL;


--
-- Name: recently_modified_item_fk_editor_user; Type: FK CONSTRAINT; Schema: public; Owner: meditor
--

ALTER TABLE ONLY recently_modified_item
    ADD CONSTRAINT recently_modified_item_fk_editor_user FOREIGN KEY (user_id) REFERENCES editor_user(id) MATCH FULL;


--
-- Name: stored_files_fk_editor_user; Type: FK CONSTRAINT; Schema: public; Owner: meditor
--

ALTER TABLE ONLY stored_files
    ADD CONSTRAINT stored_files_fk_editor_user FOREIGN KEY (user_id) REFERENCES editor_user(id) MATCH FULL;


--
-- Name: user_in_role_fk_editor_user; Type: FK CONSTRAINT; Schema: public; Owner: meditor
--

ALTER TABLE ONLY user_in_role
    ADD CONSTRAINT user_in_role_fk_editor_user FOREIGN KEY (user_id) REFERENCES editor_user(id) MATCH FULL;


--
-- Name: user_in_role_fk_role; Type: FK CONSTRAINT; Schema: public; Owner: meditor
--

ALTER TABLE ONLY user_in_role
    ADD CONSTRAINT user_in_role_fk_role FOREIGN KEY (role_id) REFERENCES role(id) MATCH FULL;


--
-- Name: user_in_role_role_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: meditor
--

ALTER TABLE ONLY user_in_role
    ADD CONSTRAINT user_in_role_role_id_fkey FOREIGN KEY (role_id) REFERENCES role(id);


--
-- Name: user_in_role_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: meditor
--

ALTER TABLE ONLY user_in_role
    ADD CONSTRAINT user_in_role_user_id_fkey FOREIGN KEY (user_id) REFERENCES editor_user(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

