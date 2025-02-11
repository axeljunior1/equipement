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
-- Name: type_mvt; Type: TYPE; Schema: public; Owner: root
--

CREATE TYPE public.type_mvt AS ENUM (
    'ENTREE',
    'SORTIE'
);


ALTER TYPE public.type_mvt OWNER TO root;

--
-- Name: CAST (character varying AS public.type_mvt); Type: CAST; Schema: -; Owner: -
--

CREATE CAST (character varying AS public.type_mvt) WITH INOUT AS IMPLICIT;


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
    actif boolean DEFAULT true NOT NULL,
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
-- Name: authority_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.authority_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.authority_id_seq OWNER TO root;

--
-- Name: authority; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.authority (
    id integer DEFAULT nextval('public.authority_id_seq'::regclass) NOT NULL,
    nom character varying(100) NOT NULL
);


ALTER TABLE public.authority OWNER TO root;

--
-- Name: categorie; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.categorie (
    categorie_id integer NOT NULL,
    nom character varying(255) NOT NULL,
    description text,
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.categorie OWNER TO root;

--
-- Name: categorie_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.categorie_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.categorie_id_seq OWNER TO root;

--
-- Name: categorie_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.categorie_id_seq OWNED BY public.categorie.categorie_id;


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
-- Name: employes; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.employes (
    id_employe bigint NOT NULL,
    nom character varying(255) NOT NULL,
    prenom character varying(255) NOT NULL,
    role character varying(255),
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    password character varying(255) NOT NULL,
    actif boolean DEFAULT true NOT NULL
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
    actif boolean DEFAULT true NOT NULL,
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
    actif boolean DEFAULT true NOT NULL,
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
-- Name: mouvement_stock; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.mouvement_stock (
    id integer NOT NULL,
    reference character varying(50),
    produit_id integer NOT NULL,
    quantite integer NOT NULL,
    type_mouvement_id integer NOT NULL,
    date_mouvement timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    commentaire text,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    id_evenement_origine integer NOT NULL,
    id_ligne_origine integer NOT NULL,
    CONSTRAINT mouvement_stock_quantite_check CHECK ((quantite > 0))
);


ALTER TABLE public.mouvement_stock OWNER TO root;

--
-- Name: mouvement_stock_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.mouvement_stock_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.mouvement_stock_id_seq OWNER TO root;

--
-- Name: mouvement_stock_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.mouvement_stock_id_seq OWNED BY public.mouvement_stock.id;


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
    sous_categorie character varying(50),
    marque character varying(50),
    unite_mesure character varying(20),
    seuil_lot integer DEFAULT 0,
    seuil_produit integer DEFAULT 10,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    image text,
    stock_initial integer DEFAULT 0,
    qr_code bytea,
    ean13 character varying(13),
    categorie_id integer,
    actif boolean DEFAULT false NOT NULL,
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
-- Name: role_authority; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.role_authority (
    id_role integer NOT NULL,
    id_authority integer NOT NULL
);


ALTER TABLE public.role_authority OWNER TO root;

--
-- Name: role_employe; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.role_employe (
    id_employe integer NOT NULL,
    id_role integer NOT NULL
);


ALTER TABLE public.role_employe OWNER TO root;

--
-- Name: roles_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.roles_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.roles_id_seq OWNER TO root;

--
-- Name: roles; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.roles (
    id_role integer DEFAULT nextval('public.roles_id_seq'::regclass) NOT NULL,
    nom character varying NOT NULL,
    description text
);


ALTER TABLE public.roles OWNER TO root;

--
-- Name: types_mouvement_stock; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.types_mouvement_stock (
    id integer NOT NULL,
    code character varying(50) NOT NULL,
    nom character varying(50) NOT NULL,
    description text,
    type_mouvement public.type_mvt NOT NULL,
    date_creation timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT types_mouvement_stock_type_mouvement_check CHECK (((type_mouvement)::text = ANY (ARRAY[('ENTREE'::character varying)::text, ('SORTIE'::character varying)::text])))
);


ALTER TABLE public.types_mouvement_stock OWNER TO root;

--
-- Name: types_mouvement_stock_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.types_mouvement_stock_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.types_mouvement_stock_id_seq OWNER TO root;

--
-- Name: types_mouvement_stock_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.types_mouvement_stock_id_seq OWNED BY public.types_mouvement_stock.id;


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
    actif boolean DEFAULT true NOT NULL,
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
-- Name: vue_stock_courant; Type: VIEW; Schema: public; Owner: root
--

CREATE VIEW public.vue_stock_courant AS
 SELECT id_produit,
    nom,
    stock_initial,
    ((stock_initial + COALESCE(( SELECT sum(ms.quantite) AS sum
           FROM (public.mouvement_stock ms
             JOIN public.types_mouvement_stock tms ON ((ms.type_mouvement_id = tms.id)))
          WHERE ((ms.produit_id = p.id_produit) AND (tms.type_mouvement = 'ENTREE'::public.type_mvt))), (0)::bigint)) - COALESCE(( SELECT sum(ms.quantite) AS sum
           FROM (public.mouvement_stock ms
             JOIN public.types_mouvement_stock tms ON ((ms.type_mouvement_id = tms.id)))
          WHERE ((ms.produit_id = p.id_produit) AND (tms.type_mouvement = 'SORTIE'::public.type_mvt))), (0)::bigint)) AS stock_courant
   FROM public.produits p;


ALTER VIEW public.vue_stock_courant OWNER TO root;

--
-- Name: achats id_achat; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.achats ALTER COLUMN id_achat SET DEFAULT nextval('public.achats_id_seq'::regclass);


--
-- Name: categorie categorie_id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.categorie ALTER COLUMN categorie_id SET DEFAULT nextval('public.categorie_id_seq'::regclass);


--
-- Name: clients id_client; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.clients ALTER COLUMN id_client SET DEFAULT nextval('public.clients_id_seq'::regclass);


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
-- Name: mouvement_stock id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.mouvement_stock ALTER COLUMN id SET DEFAULT nextval('public.mouvement_stock_id_seq'::regclass);


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
-- Name: types_mouvement_stock id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.types_mouvement_stock ALTER COLUMN id SET DEFAULT nextval('public.types_mouvement_stock_id_seq'::regclass);


--
-- Name: ventes id_ventes; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.ventes ALTER COLUMN id_ventes SET DEFAULT nextval('public.ventes_id_seq'::regclass);


--
-- Data for Name: achats; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.achats (id_achat, employe_id, montant_total, created_at, updated_at, actif) FROM stdin;
15	4	0.00	2025-02-02 22:13:06.000917	\N	t
16	4	0.00	2025-02-05 02:12:50.733864	\N	f
19	4	0.00	2025-02-05 02:45:07.107713	\N	f
20	4	0.00	2025-02-05 03:52:08.273209	\N	f
21	4	0.00	2025-02-05 04:25:42.01304	\N	t
22	4	0.00	2025-02-07 04:45:54.588682	\N	f
23	4	0.00	2025-02-08 09:16:37.030306	\N	t
24	4	0.00	2025-02-08 09:28:28.067179	\N	t
\.


--
-- Data for Name: authority; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.authority (id, nom) FROM stdin;
1	CREER_PRODUIT
2	SUPPRIMER_PRODUIT
3	VOIR_PRODUIT
4	GERER_STOCK
5	VALIDER_VENTE
6	GENERER_RAPPORT
\.


--
-- Data for Name: categorie; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.categorie (categorie_id, nom, description, created_at, updated_at) FROM stdin;
1	Accessoires Téléphone	Tous les accessoires pour téléphones mobiles : étuis, chargeurs, câbles, coques, etc.	2025-01-25 10:21:29.823468+00	2025-01-25 10:21:29.823468+00
2	Maison	Articles pour la maison : décoration, meubles, luminaires, etc.	2025-01-25 10:21:29.823468+00	2025-01-25 10:21:29.823468+00
3	Bazar	Produits divers et variés pour le quotidien : articles de loisir, petits gadgets, etc.	2025-01-25 10:21:29.823468+00	2025-01-25 10:21:29.823468+00
4	Cuisine	Équipements et accessoires pour la cuisine : ustensiles, électroménagers, vaisselle, etc.	2025-01-25 10:21:29.823468+00	2025-01-25 10:21:29.823468+00
5	Télévision	Produits liés à la télévision : TV, supports, télécommandes, etc.	2025-01-25 10:21:29.823468+00	2025-01-25 10:21:29.823468+00
6	Écrans	Tous types d’écrans : moniteurs, écrans d’ordinateur, projecteurs, etc.	2025-01-25 10:21:29.823468+00	2025-01-25 10:21:29.823468+00
7	Matériel Informatique	Tout le matériel nécessaire pour l’informatique : ordinateurs, périphériques, composants, etc.	2025-01-25 10:21:29.823468+00	2025-01-25 10:21:29.823468+00
8	Électronique	Produits électroniques divers : objets connectés, gadgets électroniques, appareils audio-vidéo, etc.	2025-01-25 10:21:29.823468+00	2025-01-25 10:21:29.823468+00
\.


--
-- Data for Name: clients; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.clients (id_client, nom, prenom, email, telephone, adresse, created_at, updated_at) FROM stdin;
1	cli 1 	pre cli 1	string@cli1.com	0749482336	\N	\N	2025-01-12 10:05:41.163419
18	cli 2 test	pre cli 2 test	string@cli1.com	0749482337	\N	\N	2025-01-29 20:29:06.529902
23					\N	\N	2025-01-30 03:08:02.299405
\.


--
-- Data for Name: employes; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.employes (id_employe, nom, prenom, role, created_at, updated_at, password, actif) FROM stdin;
4	admin	administrateur	ADMIN	2025-01-28 00:23:49.933132	2025-01-28 00:23:49.933132	$2a$10$76WZfvrWTE5K0LUn9WWE9.ecnlZlAKyT.GI0i3Mk1YnIf8nEnjoUy	t
2	user	prenom	USER	2025-01-27 02:04:40.813077	2025-01-27 02:04:40.813077	$2a$10$WilGubwNhqkCTtjm3NI8O.WUny5BRaD7rgCnJ.h/krQSTYtU1uCFu	t
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

COPY public.lignes_achats (id_lignes_achat, lot_id, prix_achat, quantite, created_at, updated_at, produit_id, achat_id, actif) FROM stdin;
37	\N	200.00	12	2025-02-02 22:20:16.017298	2025-02-02 22:20:16.017298	11	15	t
42	\N	15.00	10	2025-02-05 04:01:09.643136	2025-02-05 04:01:09.643136	12	20	t
43	\N	200.00	10	2025-02-05 04:25:54.017058	2025-02-05 04:25:54.017058	11	21	t
49	\N	30.00	1	2025-02-07 05:06:35.902231	2025-02-07 05:06:35.902231	17	22	f
48	\N	12.00	3	2025-02-07 05:03:51.192442	2025-02-07 05:03:51.192442	21	22	f
50	\N	15.00	2	2025-02-07 05:38:15.243491	2025-02-07 05:38:15.243491	16	20	f
46	\N	12.00	2	2025-02-07 05:01:48.834816	2025-02-07 05:01:48.834816	21	22	f
47	\N	12.00	2	2025-02-07 05:02:07.399272	2025-02-07 05:02:07.399272	21	22	f
51	\N	32.00	3	2025-02-08 09:17:19.205409	2025-02-08 09:17:19.205409	20	23	t
52	\N	32.00	7	2025-02-08 09:28:52.42039	2025-02-08 09:28:52.42039	20	24	t
53	\N	30.00	10	2025-02-08 09:29:10.78148	2025-02-08 09:29:10.78148	17	24	f
54	\N	32.00	10	2025-02-08 09:29:32.265185	2025-02-08 09:29:32.265185	20	24	t
\.


--
-- Data for Name: lignes_ventes; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.lignes_ventes (id_lignes_ventes, vente_id, produit_id, quantite, prix_vente_unitaire, actif) FROM stdin;
20	22	12	1	15.00	t
21	22	20	2	32.00	t
22	22	11	7	200.00	t
24	22	12	10	15.00	t
28	24	12	1	15.00	t
\.


--
-- Data for Name: mouvement_stock; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.mouvement_stock (id, reference, produit_id, quantite, type_mouvement_id, date_mouvement, commentaire, created_at, updated_at, id_evenement_origine, id_ligne_origine) FROM stdin;
32	VTE_22_LIG_21	20	2	2	2025-02-02 14:24:52.942189	Généré à partir de la ligne d'une vente	\N	\N	22	21
34	VTE_22_LIG_22	11	7	2	2025-02-02 21:25:25.300171	Généré à partir de la ligne d'une vente	\N	\N	22	22
37	ACH_15_LIG_37	11	12	1	2025-02-02 22:20:16.030561	Généré à partir de la ligne d'un achat	\N	\N	15	37
31	VTE_22_LIG_20	12	1	2	2025-02-02 14:24:52.926032	Généré à partir de la ligne d'une vente	\N	\N	22	20
36	VTE_22_LIG_24	12	10	2	2025-02-02 21:37:27.345305	Généré à partir de la ligne d'une vente	\N	\N	22	24
42	ACH_20_LIG_42	12	10	1	2025-02-05 04:01:09.688617	Généré à partir de la ligne d'un achat	\N	\N	20	42
43	ACH_21_LIG_43	11	10	1	2025-02-05 04:25:54.027061	Généré à partir de la ligne d'un achat	\N	\N	21	43
45	ACH_21_LIG_45	16	10	1	2025-02-05 04:26:05.693294	Généré à partir de la ligne d'un achat	\N	\N	21	45
44	ACH_21_LIG_44	20	10	1	2025-02-05 04:25:59.496691	Généré à partir de la ligne d'un achat	\N	\N	21	44
46	VTE_23_LIG_25	11	3	2	2025-02-05 04:31:24.277451	Généré à partir de la ligne d'une vente	\N	\N	23	25
47	VTE_23_LIG_26	10	2	2	2025-02-05 04:31:36.145534	Généré à partir de la ligne d'une vente	\N	\N	23	26
48	VTE_23_LIG_27	10	6	2	2025-02-05 04:54:51.120975	Généré à partir de la ligne d'une vente	\N	\N	23	27
49	VTE_24_LIG_28	12	1	2	2025-02-05 17:00:43.378869	Généré à partir de la ligne d'une vente	\N	\N	24	28
50	ACH_22_LIG_46	21	2	1	2025-02-07 05:01:56.572951	Généré à partir de la ligne d'un achat	2025-02-07 05:01:56.572951	\N	22	46
51	ACH_22_LIG_47	21	2	1	2025-02-07 05:02:15.042807	Généré à partir de la ligne d'un achat	2025-02-07 05:02:15.042807	\N	22	47
52	ACH_22_LIG_48	21	3	1	2025-02-07 05:03:51.199874	Généré à partir de la ligne d'un achat	2025-02-07 05:03:51.199874	\N	22	48
53	ACH_22_LIG_49	17	1	1	2025-02-07 05:06:35.909364	Généré à partir de la ligne d'un achat	2025-02-07 05:06:35.909364	\N	22	49
55	ACH_22_LIG_49_DEL	17	1	4	2025-02-07 05:08:55.177553	Eve inverse pour ajuster le stock 	2025-02-07 05:08:55.177553	\N	22	49
56	ACH_22_LIG_49_DEL	17	1	4	2025-02-07 05:10:50.990626	Eve inverse pour ajuster le stock 	2025-02-07 05:10:50.990626	\N	22	49
57	ACH_22_LIG_48_DEL	21	3	4	2025-02-07 05:26:15.722624	Eve inverse pour ajuster le stock 	2025-02-07 05:26:15.722624	\N	22	48
58	ACH_20_LIG_50	16	2	1	2025-02-07 05:38:15.250289	Généré à partir de la ligne d'un achat	2025-02-07 05:38:15.250289	\N	20	50
59	ACH_20_LIG_50_DEL	16	2	4	2025-02-07 05:38:32.083287	Eve inverse pour ajuster le stock 	2025-02-07 05:38:32.083287	\N	20	50
60	ACH_22_LIG_46_DEL	21	2	4	2025-02-07 05:58:14.84794	Eve inverse pour ajuster le stock 	2025-02-07 05:58:14.84794	\N	22	46
61	ACH_22_LIG_47_DEL	21	2	4	2025-02-07 05:58:14.859257	Eve inverse pour ajuster le stock 	2025-02-07 05:58:14.859257	\N	22	47
62	ACH_22_LIG_49_DEL	17	1	4	2025-02-07 05:58:14.865194	Eve inverse pour ajuster le stock 	2025-02-07 05:58:14.865194	\N	22	49
63	ACH_22_LIG_48_DEL	21	3	4	2025-02-07 05:58:14.86936	Eve inverse pour ajuster le stock 	2025-02-07 05:58:14.86936	\N	22	48
64	ACH_23_LIG_51	20	3	1	2025-02-08 09:17:19.222704	Généré à partir de la ligne d'un achat	2025-02-08 09:17:19.222704	\N	23	51
65	ACH_24_LIG_52	20	7	1	2025-02-08 09:28:52.426618	Généré à partir de la ligne d'un achat	2025-02-08 09:28:52.426618	\N	24	52
66	ACH_24_LIG_53	17	10	1	2025-02-08 09:29:10.788519	Généré à partir de la ligne d'un achat	2025-02-08 09:29:10.788519	\N	24	53
67	ACH_24_LIG_53_DEL	17	10	4	2025-02-08 09:29:25.480913	Eve inverse pour ajuster le stock 	2025-02-08 09:29:25.480913	\N	24	53
68	ACH_24_LIG_54	20	10	1	2025-02-08 09:29:32.273042	Généré à partir de la ligne d'un achat	2025-02-08 09:29:32.273042	\N	24	54
\.


--
-- Data for Name: paiements; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.paiements (id, vente_id, mode_paiement, montant, created_at, updated_at) FROM stdin;
\.


--
-- Data for Name: produits; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.produits (id_produit, reference, nom, description, prix_achat, prix_vente, sous_categorie, marque, unite_mesure, seuil_lot, seuil_produit, created_at, updated_at, image, stock_initial, qr_code, ean13, categorie_id, actif) FROM stdin;
12	\N	Tapis	tapis de souris	15.00	\N	\N	\N	\N	0	20	2025-01-30 16:02:33.763869	2025-01-30 16:02:33.763869		20	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000bf49444154785ee5d2b10dc4200c05509f28aecc0248ac912e2b85052eba058e95dc790da42c103a0a241fd11d210d0e7d2c9a57d9fe06f8541bdc4d080f4c33c07229e21093e5d421b0a42dea3eada15368fac481f3ab933595f7237ddeb6a95cd11c99494215c8b8328b283f707a8da59f20e2ed99a0dea12d569f510fb143e467581d7688f7262eaadf7e9272666cde589210b4df0160f265074160596d63bdb424e40facff0e17522e9a0e71c8614c47d66dedffc52f549368aad6adf505b08a159c155a988b0000000049454e44ae426082	9994384219015	7	t
11	\N	test	test	200.00	\N	\N	\N	\N	0	10	2025-01-29 14:13:39.690023	2025-01-29 14:13:39.690023		10	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000bf49444154785ee5d3310ec3200c0550570c1d7b0124aed1ad572a1748d40b842b79f335907281b23120b944859005873d569637207f1b027ca82f5c4d08374c6f80f954c42126cb694060495bd4635ac3a0d08c8903e7af25eb2acf47fa386d57b9a2d97726095520e36a165130539a9e7bbfbea2b75143cd22097390f4603f200f2fe5a89e13c4dbda5c54fff924e59d317fc89c6b7b2fb949c9220a6c54edfe64112ff7b57438d1ba40b931511cd01fff80aeb6f70213b44d74d5ead2fa0155cb1c3e8a42fe3f0000000049454e44ae426082	9993866597344	3	f
20	\N	ecran	ecran	32.00	\N	\N	\N	\N	0	10	2025-02-01 12:49:42.333014	2025-02-01 12:49:42.333014		20	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000ba49444154785ee5d3b10dc4200c05509f18e01640628d7459292cc0dd4d9095e8bc06120b1c1d0592cf5192234d1cf7b1287805d2b765800ef585bb29c223b609e07529a4529ba7a61078b43e5a9d72512a3a9da8109f9eec54dc1fda63b7a7e2aaee3f3349d11474f39e451484a1852129943c59008d6c18edb3ee3d08c2fc0633a3425c9c9accda9f249e19b90fba6b2dfb9260dc528b024f99974b25ce52b72c178a79a63d8b242a987cedc94eb5fc15bee6f59da45eb7d60f4cc3201cea275e830000000049454e44ae426082	9998196520338	5	t
16	\N	Tapis1	tapis de souris	15.00	\N	\N	\N	\N	0	5	2025-01-31 15:38:50.051019	2025-01-31 15:38:50.051019		20	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000c049444154785ee5d33b0ec3200c06e0bfcad0adbd001247c995ca059ae666deb806522e10360f515d50f360c1610f428a3e24826d0ca41833ae26c28df002865379893c45591a04c736926953d72a31ae491225b822b2aa527ede94d956257961282a581505c7e638419184c77d71d4ad7f51c4f6d3cb48e1bf4f93745fa44d6b253451409f2e6d8fba2ec9051ef2e74ca966bc00f65cb95ff0dcf2530547696ef7a7cbe3bd9fa08bedbcf7b5268964477f745655f9ad4cc26bd49a8e7169fd008b6e19aaf85a4dec0000000049454e44ae426082	9994103521207	7	t
17	\N	Clavier	Retro eclairage	30.00	\N	\N	\N	\N	0	15	2025-02-01 12:33:54.550121	2025-02-01 12:33:54.550121		20	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000bd49444154785ee5d3b10d84300c0550a31494b740a4ac41772b5d16e0c402b0923baf81c402972e45249f11778426897bac34af40f9760cf0a53e70372174985e00efa688434c9e9342e0c97ab43a6d4129743a716039395951d21fd96bb745494577ceac263481dcf2cf5213ad636fc7616d0bd78e129cc96a9220e911152233a159482129e4259aa3bf9a6466ccd37e4f4bfb3b000c79b3ca021f656c79b36a4237f7bf2c0dd1363f59210e04d73fa0a87d5f601cb6e3bb9a72dd5a5f424410a703b0bd300000000049454e44ae426082	9999468586366	7	t
10	\N	produit	descrip	13.00	\N	\N	\N	\N	0	5	2025-01-24 23:44:12.439625	2025-01-24 23:44:12.439625	string	12	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000be49444154785ee5d3b10dc4200c05509f52a4bb0922b10663850582d8cc9dd740ca02d0a540f2c189bbd060e8e322d22b08b6f303dc5480a709e185b003d8a188237ac3694260ae25e236276f2645694e1c49c5a6b3aef27cb4b5d376950b9776835da17208b0fafa16410cf6fa3e87c233e8dc759d4f126f874e96ea7c92e874c8b15c3212974b1cffba169477c6febddedbedaae4e5745793acae720a4a0ce7a498549d4116aaa06bd7a2725ed2a1977a4e50f95754f87f6941773d5a1f060921270ac39ae30000000049454e44ae426082	9995849709669	\N	f
21	\N	Produit test	test	12.00	\N	\N	\N	\N	0	15	2025-02-02 10:34:33.521993	2025-02-02 10:34:33.521993		16	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000c049444154785ee5d2b10d03210c05504757a4cc0248ac715d560a0be49205c24aeebc06120b848e02c501058e6bcea13f8be61508fb1be04dbde168423861ba012c7f451c62329c060486944135261f06857a4c1c389fded9aef27ca4b6d3ee2a57d46b6692700aa46deb45124d9f39ddaf6e40fa7156b0ee4110fbd79c2eb1de9314c1b0b758ef49e2f28865ff9b4f52ce8cf5135b1282ca1e1cacf34902137d887dd39230f732d5176491b7a407c4819c6949482affc52dd493d855af43eb0b4c302415f47b293c0000000049454e44ae426082	9996614245238	3	f
\.


--
-- Data for Name: retours; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.retours (id_retour, vente_id, produit_id, quantite, raison, created_at, updated_at) FROM stdin;
\.


--
-- Data for Name: role_authority; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.role_authority (id_role, id_authority) FROM stdin;
\.


--
-- Data for Name: role_employe; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.role_employe (id_employ, id_role) FROM stdin;
\.


--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.roles (id_role, nom, description) FROM stdin;
1	ADMIN	Administrateur ayant tous les droits
2	GESTIONNAIRE_STOCK	Responsable de la gestion des stocks
3	VENDEUR	Employé chargé des ventes
4	COMPTABLE	Responsable des finances
5	Role Test	\N
\.


--
-- Data for Name: types_mouvement_stock; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.types_mouvement_stock (id, code, nom, description, type_mouvement, date_creation) FROM stdin;
1	ACHAT_MARCHANDISE	Achat de marchandises	Entrée de stock suite à un achat auprès d’un fournisseur	ENTREE	2025-01-29 03:37:46.183956
2	VENTE_PRODUIT	Vente de produits	Sortie de stock pour la vente de produits aux clients	SORTIE	2025-01-29 03:37:46.183956
3	RETOUR_CLIENT	Retour client	Retour en stock des produits renvoyés par un client	ENTREE	2025-01-29 03:37:46.183956
4	RETOUR_FOURNISSEUR	Retour fournisseur	Renvoi d’un produit défectueux ou non conforme au fournisseur	SORTIE	2025-01-29 03:37:46.183956
5	INVENTAIRE_AJUSTEMENT	Ajustement d’inventaire	Correction du stock après un inventaire physique	ENTREE	2025-01-29 03:37:46.183956
6	DESTRUCTION_PRODUIT	Destruction de produit	Sortie de stock pour destruction de produits périmés ou endommagés	SORTIE	2025-01-29 03:37:46.183956
7	DON_ASSOCIATION	Don à une association	Sortie de stock pour don à une organisation caritative	SORTIE	2025-01-29 03:37:46.183956
8	TRANSFERT_ENTREPOT	Transfert entre entrepôts	Mouvement de stock d’un entrepôt à un autre	SORTIE	2025-01-29 03:37:46.183956
9	RECEPTION_TRANSFERT	Réception de transfert	Entrée de stock suite à un transfert provenant d’un autre entrepôt	ENTREE	2025-01-29 03:37:46.183956
10	PERTE_VOL	Perte ou vol	Sortie de stock en raison d’une perte ou d’un vol	SORTIE	2025-01-29 03:37:46.183956
\.


--
-- Data for Name: ventes; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.ventes (id_ventes, client_id, date_vente, montant_total, created_at, updated_at, employe_id, actif) FROM stdin;
22	23	\N	0.00	\N	2025-02-02 14:24:52.896471	2	t
23	23	\N	0.00	\N	2025-02-05 04:31:24.258008	2	t
24	18	\N	0.00	\N	2025-02-05 17:00:43.344933	2	t
\.


--
-- Name: achats_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.achats_id_seq', 24, true);


--
-- Name: authority_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.authority_id_seq', 8, true);


--
-- Name: categorie_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.categorie_id_seq', 8, true);


--
-- Name: clients_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.clients_id_seq', 23, true);


--
-- Name: employes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.employes_id_seq', 4, true);


--
-- Name: fournisseurs_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.fournisseurs_id_seq', 1, false);


--
-- Name: lignes_achats_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.lignes_achats_id_seq', 54, true);


--
-- Name: lignes_ventes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.lignes_ventes_id_seq', 28, true);


--
-- Name: mouvement_stock_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.mouvement_stock_id_seq', 68, true);


--
-- Name: paiements_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.paiements_id_seq', 1, false);


--
-- Name: produits_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.produits_id_seq', 21, true);


--
-- Name: retours_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.retours_id_seq', 1, false);


--
-- Name: roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.roles_id_seq', 5, true);


--
-- Name: types_mouvement_stock_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.types_mouvement_stock_id_seq', 10, true);


--
-- Name: ventes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.ventes_id_seq', 27, true);


--
-- Name: achats achats_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.achats
    ADD CONSTRAINT achats_pkey PRIMARY KEY (id_achat);


--
-- Name: authority authority_pk; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.authority
    ADD CONSTRAINT authority_pk PRIMARY KEY (id);


--
-- Name: authority authority_pk_2; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.authority
    ADD CONSTRAINT authority_pk_2 UNIQUE (nom);


--
-- Name: categorie categorie_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.categorie
    ADD CONSTRAINT categorie_pkey PRIMARY KEY (categorie_id);


--
-- Name: clients clients_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.clients
    ADD CONSTRAINT clients_pkey PRIMARY KEY (id_client);


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
-- Name: mouvement_stock mouvement_stock_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.mouvement_stock
    ADD CONSTRAINT mouvement_stock_pkey PRIMARY KEY (id);


--
-- Name: paiements paiements_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.paiements
    ADD CONSTRAINT paiements_pkey PRIMARY KEY (id);


--
-- Name: produits produits_pk; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.produits
    ADD CONSTRAINT produits_pk UNIQUE (ean13);


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
-- Name: role_authority role_authority_pk; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.role_authority
    ADD CONSTRAINT role_authority_pk PRIMARY KEY (id_authority, id_role);


--
-- Name: role_employe role_employe_pk; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.role_employe
    ADD CONSTRAINT role_employe_pk PRIMARY KEY (id_employ, id_role);


--
-- Name: roles roles_pk; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pk PRIMARY KEY (id_role);


--
-- Name: types_mouvement_stock types_mouvement_stock_code_key; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.types_mouvement_stock
    ADD CONSTRAINT types_mouvement_stock_code_key UNIQUE (code);


--
-- Name: types_mouvement_stock types_mouvement_stock_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.types_mouvement_stock
    ADD CONSTRAINT types_mouvement_stock_pkey PRIMARY KEY (id);


--
-- Name: clients unique_telephone; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.clients
    ADD CONSTRAINT unique_telephone UNIQUE (telephone);


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
-- Name: mouvement_stock fk_type_mouvement; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.mouvement_stock
    ADD CONSTRAINT fk_type_mouvement FOREIGN KEY (type_mouvement_id) REFERENCES public.types_mouvement_stock(id) ON DELETE CASCADE;


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
-- Name: mouvement_stock mouvement_stock_produits_id_produit_fk; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.mouvement_stock
    ADD CONSTRAINT mouvement_stock_produits_id_produit_fk FOREIGN KEY (produit_id) REFERENCES public.produits(id_produit);


--
-- Name: paiements paiements_vente_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.paiements
    ADD CONSTRAINT paiements_vente_id_fkey FOREIGN KEY (vente_id) REFERENCES public.ventes(id_ventes) ON DELETE CASCADE;


--
-- Name: produits produits_categorie_categorie-id_fk; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.produits
    ADD CONSTRAINT "produits_categorie_categorie-id_fk" FOREIGN KEY (categorie_id) REFERENCES public.categorie(categorie_id);


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
-- Name: role_authority role_authority_authority_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.role_authority
    ADD CONSTRAINT role_authority_authority_id_fk FOREIGN KEY (id_authority) REFERENCES public.authority(id);


--
-- Name: role_authority role_authority_roles_id_role_fk; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.role_authority
    ADD CONSTRAINT role_authority_roles_id_role_fk FOREIGN KEY (id_role) REFERENCES public.roles(id_role);


--
-- Name: role_employe role_employe_id_employe_fk; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.role_employe
    ADD CONSTRAINT role_employe_id_employe_fk FOREIGN KEY (id_employ) REFERENCES public.employes(id_employe);


--
-- Name: role_employe role_employe_roles_id_role_fk; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.role_employe
    ADD CONSTRAINT role_employe_roles_id_role_fk FOREIGN KEY (id_role) REFERENCES public.roles(id_role);


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

