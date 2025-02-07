--
-- PostgreSQL database dump
--

-- Dumped from database version 17.2 (Debian 17.2-1.pgdg120+1)
-- Dumped by pg_dump version 17.2 (Debian 17.2-1.pgdg120+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: type_role; Type: TYPE; Schema: public; Owner: root
--

CREATE TYPE public.type_role AS ENUM (
    'super_admin',
    'admin',
    'user',
    'guest'
);


ALTER TYPE public.type_role OWNER TO root;

--
-- Name: CAST (character varying AS public.type_role); Type: CAST; Schema: -; Owner: -
--

CREATE CAST (character varying AS public.type_role) WITH INOUT AS IMPLICIT;


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: achats; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.achats (
    id_achat integer NOT NULL,
    employe_id integer NOT NULL,
    montant_total numeric(10,2) NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT achats_montant_total_check CHECK ((montant_total >= (0)::numeric))
);


ALTER TABLE public.achats OWNER TO root;

--
-- Name: achats_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.achats_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.achats_id_seq OWNER TO root;

--
-- Name: achats_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.achats_id_seq OWNED BY public.achats.id_achat;


--
-- Name: clients; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.clients (
    id_client integer NOT NULL,
    nom character varying(50) NOT NULL,
    prenom character varying(50) NOT NULL,
    email character varying(100),
    telephone character varying(20),
    adresse text,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.clients OWNER TO root;

--
-- Name: clients_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.clients_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.clients_id_seq OWNER TO root;

--
-- Name: clients_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.clients_id_seq OWNED BY public.clients.id_client;


--
-- Name: details_lots; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.details_lots (
    id_details_lot integer NOT NULL,
    lot_id integer NOT NULL,
    produit_id integer NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    quantite integer NOT NULL,
    CONSTRAINT details_lots_quantite_check CHECK ((quantite >= 0))
);


ALTER TABLE public.details_lots OWNER TO root;

--
-- Name: details_lots_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.details_lots_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.details_lots_id_seq OWNER TO root;

--
-- Name: details_lots_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.details_lots_id_seq OWNED BY public.details_lots.id_details_lot;


--
-- Name: employes; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.employes (
    id_employe integer NOT NULL,
    nom character varying(50) NOT NULL,
    prenom character varying(50) NOT NULL,
    role public.type_role,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.employes OWNER TO root;

--
-- Name: employes_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.employes_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.employes_id_seq OWNER TO root;

--
-- Name: employes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.employes_id_seq OWNED BY public.employes.id_employe;


--
-- Name: employes_roles; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.employes_roles (
    id_utilisateur bigint NOT NULL,
    id_role bigint NOT NULL
);


ALTER TABLE public.employes_roles OWNER TO root;

--
-- Name: fournisseurs; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.fournisseurs (
    id integer NOT NULL,
    nom character varying(50) NOT NULL,
    prenom character varying(50) NOT NULL,
    email character varying(100),
    telephone character varying(20),
    adresse text,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.fournisseurs OWNER TO root;

--
-- Name: fournisseurs_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.fournisseurs_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.fournisseurs_id_seq OWNER TO root;

--
-- Name: fournisseurs_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.fournisseurs_id_seq OWNED BY public.fournisseurs.id;


--
-- Name: lignes_achats; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.lignes_achats (
    id_lignes_achat integer NOT NULL,
    lot_id integer,
    prix_achat numeric(10,2) NOT NULL,
    quantite integer NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    produit_id integer,
    achat_id integer,
    CONSTRAINT check_lot_produit_not_null CHECK (((produit_id IS NOT NULL) OR ((lot_id IS NOT NULL) AND (produit_id <> lot_id)))),
    CONSTRAINT lignes_achats_quantite_check CHECK ((quantite > 0)),
    CONSTRAINT produits_prix_achat_check CHECK ((prix_achat >= (0)::numeric))
);


ALTER TABLE public.lignes_achats OWNER TO root;

--
-- Name: lignes_achats_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.lignes_achats_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.lignes_achats_id_seq OWNER TO root;

--
-- Name: lignes_achats_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.lignes_achats_id_seq OWNED BY public.lignes_achats.id_lignes_achat;


--
-- Name: lignes_ventes; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.lignes_ventes (
    id_lignes_ventes integer NOT NULL,
    vente_id integer NOT NULL,
    produit_id integer,
    quantite integer NOT NULL,
    prix_vente_unitaire numeric(10,2) NOT NULL,
    CONSTRAINT lignes_ventes_prix_unitaire_check CHECK ((prix_vente_unitaire > (0)::numeric)),
    CONSTRAINT lignes_ventes_quantite_check CHECK ((quantite > 0))
);


ALTER TABLE public.lignes_ventes OWNER TO root;

--
-- Name: lignes_ventes_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.lignes_ventes_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.lignes_ventes_id_seq OWNER TO root;

--
-- Name: lignes_ventes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.lignes_ventes_id_seq OWNED BY public.lignes_ventes.id_lignes_ventes;


--
-- Name: lots; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.lots (
    id_lot integer NOT NULL,
    date_entree date NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    code_barres_lot character varying(50) NOT NULL
);


ALTER TABLE public.lots OWNER TO root;

--
-- Name: lots_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.lots_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.lots_id_seq OWNER TO root;

--
-- Name: lots_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.lots_id_seq OWNED BY public.lots.id_lot;


--
-- Name: paiements; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.paiements (
    id integer NOT NULL,
    vente_id integer NOT NULL,
    mode_paiement character varying(50) NOT NULL,
    montant numeric(10,2) NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT paiements_montant_check CHECK ((montant >= (0)::numeric))
);


ALTER TABLE public.paiements OWNER TO root;

--
-- Name: paiements_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.paiements_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.paiements_id_seq OWNER TO root;

--
-- Name: paiements_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.paiements_id_seq OWNED BY public.paiements.id;


--
-- Name: produits; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.produits (
    id_produit integer NOT NULL,
    reference character varying(50),
    nom character varying(100) NOT NULL,
    description text,
    prix_achat numeric(10,2) NOT NULL,
    prix_vente numeric(10,2),
    categorie character varying(50),
    sous_categorie character varying(50),
    marque character varying(50),
    unite_mesure character varying(20),
    seuil_lot integer DEFAULT 0,
    seuil_produit integer DEFAULT 0,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    image text,
    stock_initial integer DEFAULT 0,
    qr_code bytea,
    CONSTRAINT produits_check CHECK ((prix_vente >= prix_achat)),
    CONSTRAINT produits_prix_achat_check CHECK ((prix_achat >= (0)::numeric)),
    CONSTRAINT produits_seuil_lot_check CHECK ((seuil_lot >= 0)),
    CONSTRAINT produits_seuil_produit_check CHECK ((seuil_produit >= 0))
);


ALTER TABLE public.produits OWNER TO root;

--
-- Name: produits_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.produits_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.produits_id_seq OWNER TO root;

--
-- Name: produits_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.produits_id_seq OWNED BY public.produits.id_produit;


--
-- Name: retours; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.retours (
    id_retour integer NOT NULL,
    vente_id integer NOT NULL,
    produit_id integer NOT NULL,
    quantite integer NOT NULL,
    raison text NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT retours_quantite_check CHECK ((quantite > 0))
);


ALTER TABLE public.retours OWNER TO root;

--
-- Name: retours_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.retours_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.retours_id_seq OWNER TO root;

--
-- Name: retours_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.retours_id_seq OWNED BY public.retours.id_retour;


--
-- Name: roles; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.roles (
    id_role bigint NOT NULL,
    nom public.type_role NOT NULL,
    description text
);


ALTER TABLE public.roles OWNER TO root;

--
-- Name: roles_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

ALTER TABLE public.roles ALTER COLUMN id_role ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.roles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: ventes; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.ventes (
    id_ventes integer NOT NULL,
    client_id integer NOT NULL,
    date_vente date,
    montant_total numeric(10,2) NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    employe_id integer,
    CONSTRAINT ventes_montant_total_check CHECK ((montant_total >= (0)::numeric))
);


ALTER TABLE public.ventes OWNER TO root;

--
-- Name: ventes_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.ventes_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.ventes_id_seq OWNER TO root;

--
-- Name: ventes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.ventes_id_seq OWNED BY public.ventes.id_ventes;


--
-- Name: achats id_achat; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.achats ALTER COLUMN id_achat SET DEFAULT nextval('public.achats_id_seq'::regclass);


--
-- Name: clients id_client; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.clients ALTER COLUMN id_client SET DEFAULT nextval('public.clients_id_seq'::regclass);


--
-- Name: details_lots id_details_lot; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.details_lots ALTER COLUMN id_details_lot SET DEFAULT nextval('public.details_lots_id_seq'::regclass);


--
-- Name: employes id_employe; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.employes ALTER COLUMN id_employe SET DEFAULT nextval('public.employes_id_seq'::regclass);


--
-- Name: fournisseurs id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.fournisseurs ALTER COLUMN id SET DEFAULT nextval('public.fournisseurs_id_seq'::regclass);


--
-- Name: lignes_achats id_lignes_achat; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.lignes_achats ALTER COLUMN id_lignes_achat SET DEFAULT nextval('public.lignes_achats_id_seq'::regclass);


--
-- Name: lignes_ventes id_lignes_ventes; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.lignes_ventes ALTER COLUMN id_lignes_ventes SET DEFAULT nextval('public.lignes_ventes_id_seq'::regclass);


--
-- Name: lots id_lot; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.lots ALTER COLUMN id_lot SET DEFAULT nextval('public.lots_id_seq'::regclass);


--
-- Name: paiements id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.paiements ALTER COLUMN id SET DEFAULT nextval('public.paiements_id_seq'::regclass);


--
-- Name: produits id_produit; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.produits ALTER COLUMN id_produit SET DEFAULT nextval('public.produits_id_seq'::regclass);


--
-- Name: retours id_retour; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.retours ALTER COLUMN id_retour SET DEFAULT nextval('public.retours_id_seq'::regclass);


--
-- Name: ventes id_ventes; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.ventes ALTER COLUMN id_ventes SET DEFAULT nextval('public.ventes_id_seq'::regclass);


--
-- Data for Name: achats; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.achats (id_achat, employe_id, montant_total, created_at, updated_at) FROM stdin;
1	1	5000.00	2025-01-12 13:14:42.236664	\N
\.


--
-- Data for Name: clients; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.clients (id_client, nom, prenom, email, telephone, adresse, created_at, updated_at) FROM stdin;
1	cli 1 	pre cli 1	string@cli1.com	0749482336	\N	\N	2025-01-12 10:05:41.163419
\.


--
-- Data for Name: details_lots; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.details_lots (id_details_lot, lot_id, produit_id, created_at, updated_at, quantite) FROM stdin;
\.


--
-- Data for Name: employes; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.employes (id_employe, nom, prenom, role, created_at, updated_at) FROM stdin;
1	Junior il	Axel Ford	\N	\N	2025-01-12 11:00:21.626596
\.


--
-- Data for Name: employes_roles; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.employes_roles (id_utilisateur, id_role) FROM stdin;
\.


--
-- Data for Name: fournisseurs; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.fournisseurs (id, nom, prenom, email, telephone, adresse, created_at, updated_at) FROM stdin;
\.


--
-- Data for Name: lignes_achats; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.lignes_achats (id_lignes_achat, lot_id, prix_achat, quantite, created_at, updated_at, produit_id, achat_id) FROM stdin;
1	\N	1500.00	2	2025-01-12 13:27:54.91878	2025-01-12 13:27:54.91878	2	1
2	\N	1545.00	5	2025-01-20 14:21:49.428345	2025-01-20 14:21:49.428345	3	1
\.


--
-- Data for Name: lignes_ventes; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.lignes_ventes (id_lignes_ventes, vente_id, produit_id, quantite, prix_vente_unitaire) FROM stdin;
1	2	2	1	1800.00
2	2	2	1	1800.00
\.


--
-- Data for Name: lots; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.lots (id_lot, date_entree, created_at, code_barres_lot) FROM stdin;
\.


--
-- Data for Name: paiements; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.paiements (id, vente_id, mode_paiement, montant, created_at, updated_at) FROM stdin;
\.


--
-- Data for Name: produits; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.produits (id_produit, reference, nom, description, prix_achat, prix_vente, categorie, sous_categorie, marque, unite_mesure, seuil_lot, seuil_produit, created_at, updated_at, image, stock_initial, qr_code) FROM stdin;
6	\N	souris	ze	10.00	\N	info	\N	\N	\N	0	0	2025-01-20 16:23:29.028611	2025-01-20 16:23:29.028611	ze	123	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000be49444154785ee5d2bd11c3200c05e0e7a37099057c9735dc6525b380bd41b2129dd6f01d0b984e0567055ffc5ba0d05bdd57004f4290534db89b1c2a42070c7f4512c404890582753eb8a64cd1168a2ee7f292c0b0a764592dfd35976e735a6a1cb699697226383cb62c9a08038f151728dd5c7be1b1407e6ecd546fc914312a897dbb4e42532a32e1c892579a19a37f3d7f2f685afe41e6da1408961bec3ba82b6d01f9fd9caad8ed593449a0e7c7ad93d094fa73f2c6d16d5647dd5a5fae601f9e244572800000000049454e44ae426082
5	\N	cle Usb	cle usb 50GB	160.00	\N	info	\N	\N	\N	0	0	2025-01-20 16:19:16.586015	2025-01-20 16:19:16.586015	\N	60	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000bb49444154785ee5d23d0e84201005e0672c2cbd0089d7b0f34a7201cd5ec033d1710d132e201d05d959c88a3f0523bdd37d85f1cd634097d9f03629541a23303f4a93a5da922f10a4325689327959287dfb2e2fb20ef2922cabb89fb86d9b539c754e9d7152b5556853165668dd5ab912998f36e4d667395a1ab3357b165602bd9ffabd094e6142bb290ba7d019611abaff1f38c577e8be4d5d2048e771dc20af7059a18c2289f1c8c289aca62535c129de4bb7e0dc36ab735ead1f00151b78530dc7900000000049454e44ae426082
2	\N	PC portable 	pc 1T 16GB de ram  	10.00	\N	electronique	\N	\N	\N	0	0	2025-01-03 17:13:22.102282	2025-01-03 17:13:22.102282	image 1	0	\\x89504e470d0a1a0a0000000d494844520000012c0000012c01000000005106b3d80000020949444154785eed983b72c3300c44a171e15247e0517434f1683a8a8ee0d285c70c7601c90e934c526666898614f0d8ec101fd1da5fec66bde75b1b586703eb6c609d0dacb3ff8f6d062bbe5cdaadb4da1e575f1abdb62a62dc7ae06936fb36e836bd6272981994aacfcb7dde17c7b84c0f88a98c5130bf3a53bbe3060d8cb954ce2f650cdbb8488678eaf68aa961d00b124d28bae742af2962699bb10bf122352f352f53c3367b3297bc2dbb504b7b1e1507b155106bd5736976cf048f37a3e3d0155f8258dc20972c035e74e71d29c5b39ad89b67a9b848254a4d415c0e8b6dd90c5da890c6591fe3ec4bed95c0905239c27ad1b56c46e6f2650956c35ae5249b1e5b21184a4d93c530dad393232c755b39d032ac8685c705f3260d9add79a939e80b623eda1bdb0f73c930bce02c4eda7b4552c242a9900f3506bd9a6f069a98a7d435e87836f94137196c5b73b4c703528ba2eb4e5cab30418c8b0fb44cb09c533cdd727851c4f85e02ddf663d0877ce8d59258c68d6fd1283cde932afe94fd762962768cb0674f42edc5a15d124be3afa0c7cef792986104b10d9f9e59d5a8147a12697628981cb6625b5a63fb81607844a16e2562721887f98d0f6b051e2f35d4f27302ea61e8c7168f0531b5f4cd480b8b8bb41f734a3e3f6a62d8ba6ee849ae1be25e78f24f5911c327aa0a0318dc587bd1ab11d6c37eb5817536b0ce06d6d9c03a13c23e00993012d4109f8eb20000000049454e44ae426082
3	\N	Souris Usb 45	desc 1g	15.00	\N	infofg	\N	\N	\N	0	0	2025-01-13 23:44:05.259568	2025-01-13 23:44:05.259568		160	\\x89504e470d0a1a0a0000000d494844520000012c0000012c01000000005106b3d8000001ff49444154785eed983b6ec3301044d770a15247f0517434e9683a8a8fe0328561866f96b11c3a405206186e2189dcc766b03f31ca5fec16fdce8f36b0ce06d6d9c03a1b5867ff1fdb03bb948d9dcb1ea7f2515745bbb13a62faacfe47c47c8d38dd27b64f87cf0e8b986e55b738e7ea2cdd4ef72aa63356754b7fc13fb074cc75453c156f8ccfba432e5d635566e5aaf9dcb0acbd3b7ed1f97aabbd36d8610d2b1fc4d3b1ed86a1db740bba507d2cdb432598c2335f1747ac6ce4d275d9b4a0d44c4a30fcaf816483914b295893ef6b7ee32c801b564829c5d3594ae5b8b2d1abc3143b1c33f1a4b61c9cfdae9b0d9699158aa7ea0f06fd65533ca904fb6145b984836acba182bf0b241b6c4f788f265f0ba45e372b4c734ac5f4535c18f48beacf6b6659614c6ccdb82c909fb0523cf9610c28fc03aa1f5371261de875b3c19ef7254cb2d541ed45b09a60618a55a56e757ea3491faf164f86186596174acdaf99a5ab03476cd5242b47cd2c7a12959843df7473c2a4d4a69ea4f1964acc84dfe96683e564427726a59a6e9c4d73c39a6593aecda8f5a4b7da6b83215110337a678d89fc0132c5563e2fa414cb425805ba29cf2cb1607e4bddf42b481762f15e7bad30ba106db95dbd32d0a6b9626bbb17c84abcaa572f9e189f97e784afb6cc359bd2cd1123899eb5179bd86c870cb15f6d609d0dacb3817536b0ce8cb04fffc2614e95d9f2f10000000049454e44ae426082
7	\N	Sceau	sceau bebe jaune	100.00	\N	bazar	\N	\N	\N	0	0	2025-01-20 16:27:18.231827	2025-01-20 16:27:18.231827		7	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000be49444154785ee5d2b10dc4200c05509fe8ef1640620dba5b299980cb0237933bd640ca02a1a388e43352386830f4b1d2bc484ebe0d404d1d7037213c525800dc509e625291ce09c18a61453da5b4c749916efbfa220eb236c9bae2f9bc6ea7ed8a0b836b36d8156a00b59199d0e9bc8914aeff49a22dbf28c90425fd223e8a2b8ba8b058ded984b8bcfa5a3556ded9fe7997d482f81c7c78e60f0cc5f745d53e59c91c76fff749ca3babb7a72f8ac84fe91394e73b1768a6eda9d6adf503eda72ca6b55144b40000000049454e44ae426082
4	\N	souris sans fil oo	souris BT	15.00	\N	info	\N	\N	\N	0	0	2025-01-14 00:07:09.737735	2025-01-14 00:07:09.737735		10	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000bf49444154785ee5d2bd0dc3201005e06751b8cc029658235d56320bd81b242bd1b106921730dd152817ac18ff149ce97ddd579c78771cf85033ee268bc6a107c64b390eac02c70ac1d829d8ae4ed154ca9dfacae240308764452df375a7694b5aca8f796792ac0a168f9c4512c5a1454315721e504cfe5aac3f6e9adb9c4c10a52c7178ae9b90942afd58ce2229ed8cfcf0d2ff17242df7a2bfadaa100c75d86e5056cae2a6ad4f54ecb72c923838fda1751392d27c56bfb14f5bd45eb7d60fe93e1a7342387c8e0000000049454e44ae426082
\.


--
-- Data for Name: retours; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.retours (id_retour, vente_id, produit_id, quantite, raison, created_at, updated_at) FROM stdin;
\.


--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.roles (id_role, nom, description) FROM stdin;
\.


--
-- Data for Name: ventes; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.ventes (id_ventes, client_id, date_vente, montant_total, created_at, updated_at, employe_id) FROM stdin;
2	1	\N	500.00	\N	2025-01-12 09:58:19.65	1
\.


--
-- Name: achats_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.achats_id_seq', 1, true);


--
-- Name: clients_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.clients_id_seq', 1, true);


--
-- Name: details_lots_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.details_lots_id_seq', 1, false);


--
-- Name: employes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.employes_id_seq', 1, true);


--
-- Name: fournisseurs_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.fournisseurs_id_seq', 1, false);


--
-- Name: lignes_achats_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.lignes_achats_id_seq', 2, true);


--
-- Name: lignes_ventes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.lignes_ventes_id_seq', 2, true);


--
-- Name: lots_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.lots_id_seq', 1, false);


--
-- Name: paiements_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.paiements_id_seq', 1, false);


--
-- Name: produits_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.produits_id_seq', 7, true);


--
-- Name: retours_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.retours_id_seq', 1, false);


--
-- Name: roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.roles_id_seq', 1, false);


--
-- Name: ventes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.ventes_id_seq', 2, true);


--
-- Name: achats achats_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.achats
    ADD CONSTRAINT achats_pkey PRIMARY KEY (id_achat);


--
-- Name: clients clients_email_key; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.clients
    ADD CONSTRAINT clients_email_key UNIQUE (email);


--
-- Name: clients clients_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.clients
    ADD CONSTRAINT clients_pkey PRIMARY KEY (id_client);


--
-- Name: details_lots details_lots_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.details_lots
    ADD CONSTRAINT details_lots_pkey PRIMARY KEY (id_details_lot);


--
-- Name: employes employes_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.employes
    ADD CONSTRAINT employes_pkey PRIMARY KEY (id_employe);


--
-- Name: fournisseurs fournisseurs_email_key; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.fournisseurs
    ADD CONSTRAINT fournisseurs_email_key UNIQUE (email);


--
-- Name: fournisseurs fournisseurs_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.fournisseurs
    ADD CONSTRAINT fournisseurs_pkey PRIMARY KEY (id);


--
-- Name: lignes_achats lignes_achats_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.lignes_achats
    ADD CONSTRAINT lignes_achats_pkey PRIMARY KEY (id_lignes_achat);


--
-- Name: lignes_ventes lignes_ventes_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.lignes_ventes
    ADD CONSTRAINT lignes_ventes_pkey PRIMARY KEY (id_lignes_ventes);


--
-- Name: lots lots_code_barres_lot_key; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.lots
    ADD CONSTRAINT lots_code_barres_lot_key UNIQUE (code_barres_lot);


--
-- Name: lots lots_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.lots
    ADD CONSTRAINT lots_pkey PRIMARY KEY (id_lot);


--
-- Name: paiements paiements_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.paiements
    ADD CONSTRAINT paiements_pkey PRIMARY KEY (id);


--
-- Name: produits produits_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.produits
    ADD CONSTRAINT produits_pkey PRIMARY KEY (id_produit);


--
-- Name: produits produits_reference_key; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.produits
    ADD CONSTRAINT produits_reference_key UNIQUE (reference);


--
-- Name: retours retours_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.retours
    ADD CONSTRAINT retours_pkey PRIMARY KEY (id_retour);


--
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id_role);


--
-- Name: employes_roles utilisateurs_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.employes_roles
    ADD CONSTRAINT utilisateurs_roles_pkey PRIMARY KEY (id_utilisateur, id_role);


--
-- Name: ventes ventes_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.ventes
    ADD CONSTRAINT ventes_pkey PRIMARY KEY (id_ventes);


--
-- Name: achats achats_employe_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.achats
    ADD CONSTRAINT achats_employe_id_fkey FOREIGN KEY (employe_id) REFERENCES public.employes(id_employe) ON DELETE SET NULL;


--
-- Name: details_lots details_lots_lot_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.details_lots
    ADD CONSTRAINT details_lots_lot_id_fkey FOREIGN KEY (lot_id) REFERENCES public.lots(id_lot) ON DELETE CASCADE;


--
-- Name: details_lots details_lots_produit_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.details_lots
    ADD CONSTRAINT details_lots_produit_id_fkey FOREIGN KEY (produit_id) REFERENCES public.produits(id_produit) ON DELETE CASCADE;


--
-- Name: lignes_achats lignes_achats_achats_id_achat_fk; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.lignes_achats
    ADD CONSTRAINT lignes_achats_achats_id_achat_fk FOREIGN KEY (achat_id) REFERENCES public.achats(id_achat);


--
-- Name: lignes_achats lignes_achats_produits_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.lignes_achats
    ADD CONSTRAINT lignes_achats_produits_id_fk FOREIGN KEY (produit_id) REFERENCES public.produits(id_produit);


--
-- Name: lignes_ventes lignes_ventes_produit_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.lignes_ventes
    ADD CONSTRAINT lignes_ventes_produit_id_fkey FOREIGN KEY (produit_id) REFERENCES public.produits(id_produit) ON DELETE CASCADE;


--
-- Name: lignes_ventes lignes_ventes_vente_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.lignes_ventes
    ADD CONSTRAINT lignes_ventes_vente_id_fkey FOREIGN KEY (vente_id) REFERENCES public.ventes(id_ventes) ON DELETE CASCADE;


--
-- Name: paiements paiements_vente_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.paiements
    ADD CONSTRAINT paiements_vente_id_fkey FOREIGN KEY (vente_id) REFERENCES public.ventes(id_ventes) ON DELETE CASCADE;


--
-- Name: retours retours_produit_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.retours
    ADD CONSTRAINT retours_produit_id_fkey FOREIGN KEY (produit_id) REFERENCES public.produits(id_produit) ON DELETE CASCADE;


--
-- Name: retours retours_vente_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.retours
    ADD CONSTRAINT retours_vente_id_fkey FOREIGN KEY (vente_id) REFERENCES public.ventes(id_ventes) ON DELETE CASCADE;


--
-- Name: employes_roles utilisateurs_roles_id_role_fkey; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.employes_roles
    ADD CONSTRAINT utilisateurs_roles_id_role_fkey FOREIGN KEY (id_role) REFERENCES public.roles(id_role) ON DELETE CASCADE;


--
-- Name: employes_roles utilisateurs_roles_id_utilisateur_fkey; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.employes_roles
    ADD CONSTRAINT utilisateurs_roles_id_utilisateur_fkey FOREIGN KEY (id_utilisateur) REFERENCES public.employes(id_employe) ON DELETE CASCADE;


--
-- Name: ventes ventes_client_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.ventes
    ADD CONSTRAINT ventes_client_id_fkey FOREIGN KEY (client_id) REFERENCES public.clients(id_client) ON DELETE SET NULL;


--
-- Name: ventes ventes_employes_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.ventes
    ADD CONSTRAINT ventes_employes_id_fk FOREIGN KEY (employe_id) REFERENCES public.employes(id_employe);


--
-- PostgreSQL database dump complete
--

