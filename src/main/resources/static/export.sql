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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: achats; Type: TABLE; Schema: public; Owner: user
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


ALTER TABLE public.achats OWNER TO "user";

--
-- Name: achats_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.achats_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.achats_id_seq OWNER TO "user";

--
-- Name: achats_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.achats_id_seq OWNED BY public.achats.id_achat;


--
-- Name: authority_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.authority_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.authority_id_seq OWNER TO "user";

--
-- Name: authority; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.authority (
    id integer DEFAULT nextval('public.authority_id_seq'::regclass) NOT NULL,
    nom character varying(100) NOT NULL
);


ALTER TABLE public.authority OWNER TO "user";

--
-- Name: categorie; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.categorie (
    categorie_id integer NOT NULL,
    nom character varying(255) NOT NULL,
    description text,
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.categorie OWNER TO "user";

--
-- Name: categorie_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.categorie_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.categorie_id_seq OWNER TO "user";

--
-- Name: categorie_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.categorie_id_seq OWNED BY public.categorie.categorie_id;


--
-- Name: clients; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.clients (
    id_client integer NOT NULL,
    nom character varying(50) NOT NULL,
    prenom character varying(50) NOT NULL,
    email character varying(100),
    telephone character varying(20) NOT NULL,
    adresse text,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    actif boolean DEFAULT true NOT NULL
);


ALTER TABLE public.clients OWNER TO "user";

--
-- Name: clients_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.clients_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.clients_id_seq OWNER TO "user";

--
-- Name: clients_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.clients_id_seq OWNED BY public.clients.id_client;


--
-- Name: employes; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.employes (
    id_employe bigint NOT NULL,
    nom character varying(255) NOT NULL,
    prenom character varying(255) NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    password character varying(255) NOT NULL,
    actif boolean DEFAULT true NOT NULL
);


ALTER TABLE public.employes OWNER TO "user";

--
-- Name: employes_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.employes_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.employes_id_seq OWNER TO "user";

--
-- Name: employes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.employes_id_seq OWNED BY public.employes.id_employe;


--
-- Name: employes_roles; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.employes_roles (
    id_utilisateur bigint NOT NULL,
    id_role bigint NOT NULL
);


ALTER TABLE public.employes_roles OWNER TO "user";

--
-- Name: etat; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.etat (
    id integer NOT NULL,
    nom character varying NOT NULL,
    description character varying,
    type character varying NOT NULL
);


ALTER TABLE public.etat OWNER TO "user";

--
-- Name: COLUMN etat.type; Type: COMMENT; Schema: public; Owner: user
--

COMMENT ON COLUMN public.etat.type IS 'type de l''etat';


--
-- Name: etat_facture; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.etat_facture (
    id integer NOT NULL,
    libelle character varying(50) NOT NULL,
    description text
);


ALTER TABLE public.etat_facture OWNER TO "user";

--
-- Name: etat_facture_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.etat_facture_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.etat_facture_id_seq OWNER TO "user";

--
-- Name: etat_facture_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.etat_facture_id_seq OWNED BY public.etat_facture.id;


--
-- Name: etat_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.etat_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.etat_id_seq OWNER TO "user";

--
-- Name: etat_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.etat_id_seq OWNED BY public.etat.id;


--
-- Name: etat_paiement; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.etat_paiement (
    id integer NOT NULL,
    libelle character varying(50) NOT NULL,
    description text
);


ALTER TABLE public.etat_paiement OWNER TO "user";

--
-- Name: etat_paiement_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.etat_paiement_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.etat_paiement_id_seq OWNER TO "user";

--
-- Name: etat_paiement_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.etat_paiement_id_seq OWNED BY public.etat_paiement.id;


--
-- Name: etat_panier; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.etat_panier (
    id integer NOT NULL,
    libelle character varying(50) NOT NULL,
    description text
);


ALTER TABLE public.etat_panier OWNER TO "user";

--
-- Name: etat_panier_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.etat_panier_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.etat_panier_id_seq OWNER TO "user";

--
-- Name: etat_panier_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.etat_panier_id_seq OWNED BY public.etat_panier.id;


--
-- Name: etat_vente; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.etat_vente (
    id integer NOT NULL,
    libelle character varying(50) NOT NULL,
    description text
);


ALTER TABLE public.etat_vente OWNER TO "user";

--
-- Name: etat_vente_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.etat_vente_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.etat_vente_id_seq OWNER TO "user";

--
-- Name: etat_vente_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.etat_vente_id_seq OWNED BY public.etat_vente.id;


--
-- Name: factures; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.factures (
    id_facture integer NOT NULL,
    vente_id integer NOT NULL,
    numero_facture character varying(50) NOT NULL,
    date_facture timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    montant_total numeric(10,2) NOT NULL,
    statut character varying(20) DEFAULT 'En attente'::character varying NOT NULL,
    mode_paiement character varying(50),
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    etat_id integer DEFAULT 1 NOT NULL,
    CONSTRAINT factures_montant_total_check CHECK ((montant_total >= (0)::numeric))
);


ALTER TABLE public.factures OWNER TO "user";

--
-- Name: factures_id_facture_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.factures_id_facture_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.factures_id_facture_seq OWNER TO "user";

--
-- Name: factures_id_facture_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.factures_id_facture_seq OWNED BY public.factures.id_facture;


--
-- Name: fournisseurs; Type: TABLE; Schema: public; Owner: user
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


ALTER TABLE public.fournisseurs OWNER TO "user";

--
-- Name: fournisseurs_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.fournisseurs_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.fournisseurs_id_seq OWNER TO "user";

--
-- Name: fournisseurs_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.fournisseurs_id_seq OWNED BY public.fournisseurs.id;


--
-- Name: lignes_achats; Type: TABLE; Schema: public; Owner: user
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


ALTER TABLE public.lignes_achats OWNER TO "user";

--
-- Name: lignes_achats_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.lignes_achats_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.lignes_achats_id_seq OWNER TO "user";

--
-- Name: lignes_achats_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.lignes_achats_id_seq OWNED BY public.lignes_achats.id_lignes_achat;


--
-- Name: lignes_ventes; Type: TABLE; Schema: public; Owner: user
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


ALTER TABLE public.lignes_ventes OWNER TO "user";

--
-- Name: lignes_ventes_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.lignes_ventes_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.lignes_ventes_id_seq OWNER TO "user";

--
-- Name: lignes_ventes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.lignes_ventes_id_seq OWNED BY public.lignes_ventes.id_lignes_ventes;


--
-- Name: mouvement_stock; Type: TABLE; Schema: public; Owner: user
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


ALTER TABLE public.mouvement_stock OWNER TO "user";

--
-- Name: mouvement_stock_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.mouvement_stock_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.mouvement_stock_id_seq OWNER TO "user";

--
-- Name: mouvement_stock_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.mouvement_stock_id_seq OWNED BY public.mouvement_stock.id;


--
-- Name: paiements; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.paiements (
    id_paiement integer NOT NULL,
    facture_id integer NOT NULL,
    montant_paye numeric(10,2) NOT NULL,
    date_paiement timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    mode_paiement character varying(50),
    reference character varying(100),
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    etat_id integer DEFAULT 1 NOT NULL,
    CONSTRAINT paiements_montant_paye_check CHECK ((montant_paye > (0)::numeric))
);


ALTER TABLE public.paiements OWNER TO "user";

--
-- Name: paiements_id_paiement_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.paiements_id_paiement_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.paiements_id_paiement_seq OWNER TO "user";

--
-- Name: paiements_id_paiement_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.paiements_id_paiement_seq OWNED BY public.paiements.id_paiement;


--
-- Name: panier; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.panier (
    id integer NOT NULL,
    id_employe integer NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    id_etat integer DEFAULT 1 NOT NULL
);


ALTER TABLE public.panier OWNER TO "user";

--
-- Name: panier_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.panier_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.panier_id_seq OWNER TO "user";

--
-- Name: panier_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.panier_id_seq OWNED BY public.panier.id;


--
-- Name: panier_produit; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.panier_produit (
    id integer NOT NULL,
    id_panier integer NOT NULL,
    id_produit integer NOT NULL,
    quantite integer NOT NULL,
    prix_vente numeric(10,2),
    CONSTRAINT panier_produit_quantite_check CHECK ((quantite > 0))
);


ALTER TABLE public.panier_produit OWNER TO "user";

--
-- Name: COLUMN panier_produit.prix_vente; Type: COMMENT; Schema: public; Owner: user
--

COMMENT ON COLUMN public.panier_produit.prix_vente IS 'Prix de vente forcé si diferent du prix du prix de vente';


--
-- Name: panier_produit_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.panier_produit_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.panier_produit_id_seq OWNER TO "user";

--
-- Name: panier_produit_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.panier_produit_id_seq OWNED BY public.panier_produit.id;


--
-- Name: produits; Type: TABLE; Schema: public; Owner: user
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


ALTER TABLE public.produits OWNER TO "user";

--
-- Name: produits_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.produits_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.produits_id_seq OWNER TO "user";

--
-- Name: produits_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.produits_id_seq OWNED BY public.produits.id_produit;


--
-- Name: retours; Type: TABLE; Schema: public; Owner: user
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


ALTER TABLE public.retours OWNER TO "user";

--
-- Name: retours_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.retours_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.retours_id_seq OWNER TO "user";

--
-- Name: retours_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.retours_id_seq OWNED BY public.retours.id_retour;


--
-- Name: role_authority; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.role_authority (
    id_role integer NOT NULL,
    id_authority integer NOT NULL
);


ALTER TABLE public.role_authority OWNER TO "user";

--
-- Name: role_employe; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.role_employe (
    id_role integer NOT NULL,
    id_employe integer NOT NULL
);


ALTER TABLE public.role_employe OWNER TO "user";

--
-- Name: roles_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.roles_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.roles_id_seq OWNER TO "user";

--
-- Name: roles; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.roles (
    id_role integer DEFAULT nextval('public.roles_id_seq'::regclass) NOT NULL,
    nom character varying NOT NULL,
    description text
);


ALTER TABLE public.roles OWNER TO "user";

--
-- Name: types_mouvement_stock; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.types_mouvement_stock (
    id integer NOT NULL,
    code character varying(50) NOT NULL,
    nom character varying(50) NOT NULL,
    description text,
    date_creation timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    type_mouvement character varying(50)
);


ALTER TABLE public.types_mouvement_stock OWNER TO "user";

--
-- Name: types_mouvement_stock_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.types_mouvement_stock_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.types_mouvement_stock_id_seq OWNER TO "user";

--
-- Name: types_mouvement_stock_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.types_mouvement_stock_id_seq OWNED BY public.types_mouvement_stock.id;


--
-- Name: ventes; Type: TABLE; Schema: public; Owner: user
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
    etat_id integer DEFAULT 1 NOT NULL,
    CONSTRAINT ventes_montant_total_check CHECK ((montant_total >= (0)::numeric))
);


ALTER TABLE public.ventes OWNER TO "user";

--
-- Name: ventes_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.ventes_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.ventes_id_seq OWNER TO "user";

--
-- Name: ventes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.ventes_id_seq OWNED BY public.ventes.id_ventes;


--
-- Name: vue_stock_courant; Type: VIEW; Schema: public; Owner: user
--

CREATE VIEW public.vue_stock_courant AS
 SELECT id_produit,
    nom,
    stock_initial,
    ((stock_initial + COALESCE(( SELECT sum(ms.quantite) AS sum
           FROM (public.mouvement_stock ms
             JOIN public.types_mouvement_stock tms ON ((ms.type_mouvement_id = tms.id)))
          WHERE ((ms.produit_id = p.id_produit) AND ((tms.type_mouvement)::text = 'ENTREE'::text))), (0)::bigint)) - COALESCE(( SELECT sum(ms.quantite) AS sum
           FROM (public.mouvement_stock ms
             JOIN public.types_mouvement_stock tms ON ((ms.type_mouvement_id = tms.id)))
          WHERE ((ms.produit_id = p.id_produit) AND ((tms.type_mouvement)::text = 'SORTIE'::text))), (0)::bigint)) AS stock_courant
   FROM public.produits p;


ALTER VIEW public.vue_stock_courant OWNER TO "user";

--
-- Name: achats id_achat; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.achats ALTER COLUMN id_achat SET DEFAULT nextval('public.achats_id_seq'::regclass);


--
-- Name: categorie categorie_id; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.categorie ALTER COLUMN categorie_id SET DEFAULT nextval('public.categorie_id_seq'::regclass);


--
-- Name: clients id_client; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.clients ALTER COLUMN id_client SET DEFAULT nextval('public.clients_id_seq'::regclass);


--
-- Name: employes id_employe; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.employes ALTER COLUMN id_employe SET DEFAULT nextval('public.employes_id_seq'::regclass);


--
-- Name: etat id; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.etat ALTER COLUMN id SET DEFAULT nextval('public.etat_id_seq'::regclass);


--
-- Name: etat_facture id; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.etat_facture ALTER COLUMN id SET DEFAULT nextval('public.etat_facture_id_seq'::regclass);


--
-- Name: etat_paiement id; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.etat_paiement ALTER COLUMN id SET DEFAULT nextval('public.etat_paiement_id_seq'::regclass);


--
-- Name: etat_panier id; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.etat_panier ALTER COLUMN id SET DEFAULT nextval('public.etat_panier_id_seq'::regclass);


--
-- Name: etat_vente id; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.etat_vente ALTER COLUMN id SET DEFAULT nextval('public.etat_vente_id_seq'::regclass);


--
-- Name: factures id_facture; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.factures ALTER COLUMN id_facture SET DEFAULT nextval('public.factures_id_facture_seq'::regclass);


--
-- Name: fournisseurs id; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.fournisseurs ALTER COLUMN id SET DEFAULT nextval('public.fournisseurs_id_seq'::regclass);


--
-- Name: lignes_achats id_lignes_achat; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.lignes_achats ALTER COLUMN id_lignes_achat SET DEFAULT nextval('public.lignes_achats_id_seq'::regclass);


--
-- Name: lignes_ventes id_lignes_ventes; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.lignes_ventes ALTER COLUMN id_lignes_ventes SET DEFAULT nextval('public.lignes_ventes_id_seq'::regclass);


--
-- Name: mouvement_stock id; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.mouvement_stock ALTER COLUMN id SET DEFAULT nextval('public.mouvement_stock_id_seq'::regclass);


--
-- Name: paiements id_paiement; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.paiements ALTER COLUMN id_paiement SET DEFAULT nextval('public.paiements_id_paiement_seq'::regclass);


--
-- Name: panier id; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.panier ALTER COLUMN id SET DEFAULT nextval('public.panier_id_seq'::regclass);


--
-- Name: panier_produit id; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.panier_produit ALTER COLUMN id SET DEFAULT nextval('public.panier_produit_id_seq'::regclass);


--
-- Name: produits id_produit; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.produits ALTER COLUMN id_produit SET DEFAULT nextval('public.produits_id_seq'::regclass);


--
-- Name: retours id_retour; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.retours ALTER COLUMN id_retour SET DEFAULT nextval('public.retours_id_seq'::regclass);


--
-- Name: types_mouvement_stock id; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.types_mouvement_stock ALTER COLUMN id SET DEFAULT nextval('public.types_mouvement_stock_id_seq'::regclass);


--
-- Name: ventes id_ventes; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.ventes ALTER COLUMN id_ventes SET DEFAULT nextval('public.ventes_id_seq'::regclass);


--
-- Data for Name: achats; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.achats (id_achat, employe_id, montant_total, created_at, updated_at, actif) FROM stdin;
40	12	0.00	2025-02-21 22:33:47.736209	\N	f
41	12	26.00	2025-02-22 16:32:31.918796	\N	t
\.


--
-- Data for Name: authority; Type: TABLE DATA; Schema: public; Owner: user
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
-- Data for Name: categorie; Type: TABLE DATA; Schema: public; Owner: user
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
-- Data for Name: clients; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.clients (id_client, nom, prenom, email, telephone, adresse, created_at, updated_at, actif) FROM stdin;
24	test	test		049516846	\N	2025-02-21 21:51:40	2025-02-11 02:25:49.388511	t
1	Client Junior  	Super client	client-junior@gmail.com	0749482336	\N	2025-02-21 21:51:36	2025-01-12 10:05:41.163419	t
\.


--
-- Data for Name: employes; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.employes (id_employe, nom, prenom, created_at, updated_at, password, actif) FROM stdin;
12	junior	junior	2025-02-10 22:39:42.907528	2025-02-10 22:39:43.047566	$2a$10$YLBqIlUvkMk6nOR9tKBPL.jf.H9bT1X8tbGHXx.Y7tVJVuNrgc..q	t
13	men	men	\N	2025-02-12 22:31:27.194866	$2a$10$R55IW0OGltH2a5SIC5cDHuxq1tqhW9e2rMdoFrsIo2cxAHp1sd7yy	t
\.


--
-- Data for Name: employes_roles; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.employes_roles (id_utilisateur, id_role) FROM stdin;
\.


--
-- Data for Name: etat; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.etat (id, nom, description, type) FROM stdin;
1	cree	Panier en cours de remplissage	PANIER
2	valide	Panier validé par l'utilisateur	PANIER
3	Annule	Panier annulé par l'utilisateur	PANIER
4	attente	panier en attente	PANIER
\.


--
-- Data for Name: etat_facture; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.etat_facture (id, libelle, description) FROM stdin;
1	GENEREE	La facture a été générée.
2	NON_GENEREE	La facture n’a pas encore été générée.
\.


--
-- Data for Name: etat_paiement; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.etat_paiement (id, libelle, description) FROM stdin;
1	EN_ATTENTE	Paiement en attente.
2	EFFECTUE	Paiement effectué avec succès.
3	REFUSE	Paiement refusé.
\.


--
-- Data for Name: etat_panier; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.etat_panier (id, libelle, description) FROM stdin;
1	EN_COURS	\N
2	VALIDE	\N
3	ANNULE	\N
\.


--
-- Data for Name: etat_vente; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.etat_vente (id, libelle, description) FROM stdin;
9	SOLDEE	\N
1	AVOIR_GENERE	Avoir généré pour le client.
4	ANNULEE	Vente annulée avant paiement.
3	PAYEE	Vente payée
5	REMBOURSEE	Vente remboursée.
6	CONFIRMEE	Vente enregistrée, en attente de paiement.
\.


--
-- Data for Name: factures; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.factures (id_facture, vente_id, numero_facture, date_facture, montant_total, statut, mode_paiement, created_at, updated_at, etat_id) FROM stdin;
1	46	FAC_TEST_2025	2025-02-21 02:42:33	145.00	CREEE	ESPECE	2025-02-21 02:43:36	2025-02-21 02:43:39	1
\.


--
-- Data for Name: fournisseurs; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.fournisseurs (id, nom, prenom, email, telephone, adresse, created_at, updated_at) FROM stdin;
\.


--
-- Data for Name: lignes_achats; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.lignes_achats (id_lignes_achat, lot_id, prix_achat, quantite, created_at, updated_at, produit_id, achat_id, actif) FROM stdin;
75	\N	13.00	3	2025-02-21 22:34:02.019229	2025-02-21 22:34:02.019229	56	40	f
77	\N	4.00	3	2025-02-22 16:15:28.189481	2025-02-22 16:15:28.189481	54	40	f
78	\N	4.00	3	2025-02-22 16:15:39.109969	2025-02-22 16:15:39.109969	54	40	f
85	\N	5.00	2	2025-02-22 16:32:49.040257	2025-02-22 16:32:49.040257	31	41	t
86	\N	4.00	2	2025-02-22 16:54:59.465585	2025-02-22 16:54:59.465585	55	41	t
87	\N	4.00	2	2025-02-22 16:55:42.447971	2025-02-22 16:55:42.447971	54	41	t
\.


--
-- Data for Name: lignes_ventes; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.lignes_ventes (id_lignes_ventes, vente_id, produit_id, quantite, prix_vente_unitaire, actif) FROM stdin;
78	50	56	1	13.00	f
77	49	56	1	13.00	f
76	47	47	38	14.99	f
75	46	56	2	10.00	f
74	45	22	1	19.99	f
79	51	56	1	13.00	t
\.


--
-- Data for Name: mouvement_stock; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.mouvement_stock (id, reference, produit_id, quantite, type_mouvement_id, date_mouvement, commentaire, created_at, updated_at, id_evenement_origine, id_ligne_origine) FROM stdin;
215	VTE_46_LIG_75	56	2	2	2025-02-21 02:33:13.687526	Généré à partir de la ligne d'un vente	2025-02-21 02:33:13.687526	2025-02-21 02:33:13.704431	46	75
216	ACH_40_LIG_75	56	3	1	2025-02-21 22:34:02.048403	Généré à partir de la ligne d'un achat	2025-02-21 22:34:02.048403	2025-02-21 22:34:02.068045	40	75
217	VTE_47_LIG_76	47	38	2	2025-02-22 10:41:07.528404	Généré à partir de la ligne d'un vente	2025-02-22 10:41:07.528404	2025-02-22 10:41:07.532156	47	76
218	VTE_49_LIG_77	56	1	2	2025-02-22 12:15:07.769341	Généré à partir de la ligne d'un vente	2025-02-22 12:15:07.769341	2025-02-22 12:15:07.784743	49	77
219	VTE_50_LIG_78	56	1	2	2025-02-22 12:15:15.47101	Généré à partir de la ligne d'un vente	2025-02-22 12:15:15.47101	2025-02-22 12:15:15.473799	50	78
220	ACH_50_LIG_78_DEL	56	1	3	2025-02-22 12:15:45.573641	Eve inverse pour ajuster le stock 	2025-02-22 12:15:45.573641	2025-02-22 12:15:45.576416	50	78
221	ACH_49_LIG_77_DEL	56	1	3	2025-02-22 12:15:46.190759	Eve inverse pour ajuster le stock 	2025-02-22 12:15:46.190759	2025-02-22 12:15:46.192669	49	77
222	ACH_47_LIG_76_DEL	47	38	3	2025-02-22 12:15:47.84354	Eve inverse pour ajuster le stock 	2025-02-22 12:15:47.84354	2025-02-22 12:15:47.845167	47	76
223	ACH_46_LIG_75_DEL	56	2	3	2025-02-22 12:15:48.434579	Eve inverse pour ajuster le stock 	2025-02-22 12:15:48.434579	2025-02-22 12:15:48.436759	46	75
224	ACH_45_LIG_74_DEL	22	1	3	2025-02-22 12:15:49.235921	Eve inverse pour ajuster le stock 	2025-02-22 12:15:49.235921	2025-02-22 12:15:49.237579	45	74
225	VTE_51_LIG_79	56	1	2	2025-02-22 12:19:31.646687	Généré à partir de la ligne d'un vente	2025-02-22 12:19:31.646687	2025-02-22 12:19:31.656423	51	79
226	ACH_40_LIG_77	54	3	1	2025-02-22 16:15:28.292482	Généré à partir de la ligne d'un achat	2025-02-22 16:15:28.292482	2025-02-22 16:15:28.304915	40	77
227	ACH_40_LIG_78	54	3	1	2025-02-22 16:15:39.126455	Généré à partir de la ligne d'un achat	2025-02-22 16:15:39.126455	2025-02-22 16:15:39.131476	40	78
228	ACH_40_LIG_75_DEL	56	3	4	2025-02-22 16:32:22.843244	Eve inverse pour ajuster le stock 	2025-02-22 16:32:22.843244	2025-02-22 16:32:22.848758	40	75
229	ACH_40_LIG_77_DEL	54	3	4	2025-02-22 16:32:22.861367	Eve inverse pour ajuster le stock 	2025-02-22 16:32:22.861367	2025-02-22 16:32:22.864039	40	77
230	ACH_40_LIG_78_DEL	54	3	4	2025-02-22 16:32:22.875004	Eve inverse pour ajuster le stock 	2025-02-22 16:32:22.875004	2025-02-22 16:32:22.877685	40	78
231	ACH_41_LIG_85	31	2	1	2025-02-22 16:32:49.057744	Généré à partir de la ligne d'un achat	2025-02-22 16:32:49.057744	2025-02-22 16:32:49.063762	41	85
232	ACH_41_LIG_86	55	2	1	2025-02-22 16:54:59.479504	Généré à partir de la ligne d'un achat	2025-02-22 16:54:59.479504	2025-02-22 16:54:59.483059	41	86
233	ACH_41_LIG_87	54	2	1	2025-02-22 16:55:42.466829	Généré à partir de la ligne d'un achat	2025-02-22 16:55:42.466829	2025-02-22 16:55:42.469573	41	87
\.


--
-- Data for Name: paiements; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.paiements (id_paiement, facture_id, montant_paye, date_paiement, mode_paiement, reference, created_at, etat_id) FROM stdin;
\.


--
-- Data for Name: panier; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.panier (id, id_employe, created_at, id_etat) FROM stdin;
1	12	2025-02-16 21:06:26.680888	1
\.


--
-- Data for Name: panier_produit; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.panier_produit (id, id_panier, id_produit, quantite, prix_vente) FROM stdin;
103	1	56	3	13.00
\.


--
-- Data for Name: produits; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.produits (id_produit, reference, nom, description, prix_achat, prix_vente, sous_categorie, marque, unite_mesure, seuil_lot, seuil_produit, created_at, updated_at, image, stock_initial, qr_code, ean13, categorie_id, actif) FROM stdin;
21	\N	Produit test	test	12.00	12.00	\N	\N	\N	0	15	2025-02-02 10:34:33.521993	2025-02-02 10:34:33.521993		16	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000c049444154785ee5d2b10d03210c05504757a4cc0248ac715d560a0be49205c24aeebc06120b848e02c501058e6bcea13f8be61508fb1be04dbde168423861ba012c7f451c62329c060486944135261f06857a4c1c389fded9aef27ca4b6d3ee2a57d46b6692700aa46deb45124d9f39ddaf6e40fa7156b0ee4110fbd79c2eb1de9314c1b0b758ef49e2f28865ff9b4f52ce8cf5135b1282ca1e1cacf34902137d887dd39230f732d5176491b7a407c4819c6949482affc52dd493d855af43eb0b4c302415f47b293c0000000049454e44ae426082	9996614245238	2	f
10	\N	produit	descrip	13.00	13.00	\N	\N	\N	0	5	2025-01-24 23:44:12.439625	2025-01-24 23:44:12.439625	string	12	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000be49444154785ee5d3b10dc4200c05509f52a4bb0922b10663850582d8cc9dd740ca02d0a540f2c189bbd060e8e322d22b08b6f303dc5480a709e185b003d8a188237ac3694260ae25e236276f2645694e1c49c5a6b3aef27cb4b5d376950b9776835da17208b0fafa16410cf6fa3e87c233e8dc759d4f126f874e96ea7c92e874c8b15c3212974b1cffba169477c6febddedbedaae4e5745793acae720a4a0ce7a498549d4116aaa06bd7a2725ed2a1977a4e50f95754f87f6941773d5a1f060921270ac39ae30000000049454e44ae426082	9995849709669	2	f
23	\N	Coque iPhone 13	Coque transparente en silicone pour iPhone 13.	12.99	12.99	\N	\N	\N	0	\N	2025-02-02 10:34:33.521993	2025-02-09 18:03:17.781861	coque_iphone.jpg	200	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000bd49444154785ee5d3310ec3200c0550570c1d7b0124ae91ad570a1748d50b942b79f335227181b03120b98eda842c38ecf9caf2a67cdb02f89005ae26841b9611e0752ae2948be7d221f0643dda3ec5d429747de2c4f2d5664dc97c648fd33625c96edf992634895cd8ba6822c358a6e7b65d4d761c0adc3bc42e907de40ec911c804ea90445ae7f89b4f93ec2cbb37b973ad7798619f4f13f81c17a897d684ee03e6ff8713c5805b174d9c701e87daaca9f5adcc13d44d345573697d015ca60d4a6fc38de00000000049454e44ae426082	9992405575997	2	f
56	\N	Produit 1	Test 	10.00	13.00	\N	\N	\N	0	10	\N	2025-02-21 20:07:53.196885		10	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000be49444154785ee5d2b10dc3201005d04314ee3c01126bd065a57881d859c059e93ad6b0c402a6a3b074b9242426850f7a5ff78a93fe3f002a6685b3094191b9028c55798ab40db43508066f239a26f15ea3d2d2268a49c722d9a1b89fff6b7b281e6f7f379384063aa3c8d695e0e6c27cc9a9257950a827b7d495c203a1ffa696447c33ded3b983a457bfd5e59b497adf6cc49c4512bf03ee7ba2f8bf98f2a525919ebbf0495d118669bfae20ce027d97df5612f7c3704f794fd23ea7d6134709026422be85540000000049454e44ae426082	9999967883638	5	t
20	\N	ecran	ecran	32.00	32.00	\N	\N	\N	0	10	2025-02-01 12:49:42.333014	2025-02-01 12:49:42.333014		20	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000ba49444154785ee5d3b10dc4200c05509f18e01640628d7459292cc0dd4d9095e8bc06120b1c1d0592cf5192234d1cf7b1287805d2b765800ef585bb29c223b609e07529a4529ba7a61078b43e5a9d72512a3a9da8109f9eec54dc1fda63b7a7e2aaee3f3349d11474f39e451484a1852129943c59008d6c18edb3ee3d08c2fc0633a3425c9c9accda9f249e19b90fba6b2dfb9260dc528b024f99974b25ce52b72c178a79a63d8b242a987cedc94eb5fc15bee6f59da45eb7d60f4cc3201cea275e830000000049454e44ae426082	9998196520338	5	f
17	\N	Clavier	Retro eclairage	30.00	30.00	\N	\N	\N	0	15	2025-02-01 12:33:54.550121	2025-02-01 12:33:54.550121		20	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000bd49444154785ee5d3b10d84300c0550a31494b740a4ac41772b5d16e0c402b0923baf81c402972e45249f11778426897bac34af40f9760cf0a53e70372174985e00efa688434c9e9342e0c97ab43a6d4129743a716039395951d21fd96bb745494577ceac263481dcf2cf5213ad636fc7616d0bd78e129cc96a9220e911152233a159482129e4259aa3bf9a6466ccd37e4f4bfb3b000c79b3ca021f656c79b36a4237f7bf2c0dd1363f59210e04d73fa0a87d5f601cb6e3bb9a72dd5a5f424410a703b0bd300000000049454e44ae426082	9999468586366	7	f
11	\N	test	test	200.00	200.00	\N	\N	\N	0	10	2025-01-29 14:13:39.690023	2025-01-29 14:13:39.690023		10	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000bf49444154785ee5d3310ec3200c0550570c1d7b0124aed1ad572a1748d40b842b79f335907281b23120b944859005873d569637207f1b027ca82f5c4d08374c6f80f954c42126cb694060495bd4635ac3a0d08c8903e7af25eb2acf47fa386d57b9a2d97726095520e36a165130539a9e7bbfbea2b75143cd22097390f4603f200f2fe5a89e13c4dbda5c54fff924e59d317fc89c6b7b2fb949c9220a6c54edfe64112ff7b57438d1ba40b931511cd01fff80aeb6f70213b44d74d5ead2fa0155cb1c3e8a42fe3f0000000049454e44ae426082	9993866597344	3	f
16	\N	Tapis1	tapis de souris	15.00	15.00	\N	\N	\N	0	5	2025-01-31 15:38:50.051019	2025-01-31 15:38:50.051019		20	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000c049444154785ee5d33b0ec3200c06e0bfcad0adbd001247c995ca059ae666deb806522e10360f515d50f360c1610f428a3e24826d0ca41833ae26c28df002865379893c45591a04c736926953d72a31ae491225b822b2aa527ede94d956257961282a581505c7e638419184c77d71d4ad7f51c4f6d3cb48e1bf4f93745fa44d6b253451409f2e6d8fba2ec9051ef2e74ca966bc00f65cb95ff0dcf2530547696ef7a7cbe3bd9fa08bedbcf7b5268964477f745655f9ad4cc26bd49a8e7169fd008b6e19aaf85a4dec0000000049454e44ae426082	9994103521207	7	f
12	\N	Tapis	tapis de souris	15.00	15.00	\N	\N	\N	0	20	2025-01-30 16:02:33.763869	2025-01-30 16:02:33.763869		20	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000bf49444154785ee5d2b10dc4200c05509f28aecc0248ac912e2b85052eba058e95dc790da42c103a0a241fd11d210d0e7d2c9a57d9fe06f8541bdc4d080f4c33c07229e21093e5d421b0a42dea3eada15368fac481f3ab933595f7237ddeb6a95cd11c99494215c8b8328b283f707a8da59f20e2ed99a0dea12d569f510fb143e467581d7688f7262eaadf7e9272666cde589210b4df0160f265074160596d63bdb424e40facff0e17522e9a0e71c8614c47d66dedffc52f549368aad6adf505b08a159c155a988b0000000049454e44ae426082	9994384219015	7	f
55	\N	Produit	Test Produit	10.00	10.00	\N	\N	\N	0	10	\N	2025-02-11 16:02:13.724292		10	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000c049444154785ee5d1bd0d83301005e0432ee892052c790d3aaf04134016202b5d776b586201dcb9b074318a8949e19f9e6bd057a0bbf70c7c991dee26848ee5083057456cd94fec1b0413298bb249e1bf4639d326b64ed8cb6559857cf49736ab30a47e9d9584127ad9b1aa8bd59bb67588b7146560108b367551f8c2a36f9083516f8b16df5b4a3af2f1ae63bb251d9df919e38692c23b90dacf7c45c1847e3c3754e4c43ac4ab2ba2d059da90175b344f8c2f5652c887db8b521359a5b9b53ee0150f78734a48a60000000049454e44ae426082	9991477785273	2	t
22	\N	Chargeur rapide USB-C	Chargeur universel 20W pour smartphones et tablettes.	19.99	19.99	\N	\N	\N	0	\N	2025-02-02 10:34:33.521993	2025-02-09 18:03:17.73438	chargeur.jpg	5	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000ba49444154785ee5d2bd0d84300c05609f525cc90296b2061d2b5d1638740b5c5672e7359058807414917c461c3f0d897b9ed27c45243fcb20a74c7037113c28bf00faaa58d29c836483203006429bc66414799b2489be63b24b693fc673db4b6966bfefac2472897ddc66290a7bcaef76a88b65ea109e06113682cd6c90966b5d6483342451c6b55f49ba33f11fde3651d0722f0047bf8220c8a8c76512f9c8ff592a62f7edbc41927808fb5e0a5aee65e8c9adff4a3a726bfd00265a248a712175350000000049454e44ae426082	9998571924560	1	t
57	\N	Produit tes som	Test Produit	10.00	10.00	\N	\N	\N	0	10	2025-02-11 16:09:52.381134	2025-02-22 01:15:09.855863		10	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000b949444154785ee5d3bb0d03210c00509f6e802c80c41ae9b2525820a72c105672e735905820742e901c4e2181061ffd5934af32fe8174f186b30961c17c07d80e4592383bc9130247c6a199534c93423b2749525efbd950a53e327db5439560fbef99265c1359fffb8b268e9ef3e35633a8320b1a80702c091b990b4f8842e9aec7099540f1bc7eebd3547ac6f2247bac7d5f025cbbcd1a0a1cc7363f5d242f8835832e8c9eeac4544942e82f60a8fd564a836b064d2d4ead0f36cf2be3e8980e1d0000000049454e44ae426082	9990305985274	2	t
54	\N	Chargeur Oraimo Type C	Manifique chargeur Oraimo  de type C	15.00	15.00	\N	\N	\N	0	\N	\N	2025-02-10 22:03:05.968405		15	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000bd49444154785ee5d2310e84201005d031145c82846bd87925b9807282dd2bd1cd354cb800741624b3e3ee8a5a80f4d2bd82f0e70f40a713e06972d0911a01e65b214554865283c0a0884e3589926994833651743a9e9215c5f3e165daa268bb9a3babc9f930c028c5bd0826295efd3f754d1c64f001f6196ae2ab0aa4ffbd50131746de0e7bb28abef3d94b130571676e31985f288bf7403af4c756ca02b3a60e9726a1b67d9b566173bb35f1bf5ee653eaa2b6ffa2dfa4ef759c47eb0378571284aaeb9aff0000000049454e44ae426082	9997068053592	8	t
46	\N	Four à micro-ondes	Micro-ondes grill avec 10 programmes automatiques.	119.99	119.99	\N	\N	\N	0	\N	2025-02-02 10:34:33.521993	2025-02-09 18:03:18.199033	micro_ondes.jpg	30	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000bd49444154785ee5d2b10dc4200c05509f52a4bc0590b246baac142f705116c84c745e03290b1c9d0b743e90484283431fba4781ed8f418af385a7c9c2cbc20cb0dc8ac4f3ee25340890076f4d9bba5689c126891787456755c5f9c894d35625e9622912acca3a6473555024c33606a42ebfa2881dd2b052ee451375dbb40b1f49680ac8318cdcb52649012fdca098191be81b94f605de36cfa70a90601e8fffbb91fb9c1574c5f9cecc3489b7b216db5355da97fd37e5ae355de7d1fa0386d929f49207d39b0000000049454e44ae426082	9990796215454	4	t
62	\N	zeze	sds	2.00	2.00	\N	\N	\N	0	\N	\N	2025-02-12 06:56:52.215023		3	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000bf49444154785ee5d3310ec3200c0550570c8cbd0092af912d570a1748db0b942b79f3352ae502616388ea12b5295920de63b1bc01617f006457339c4d04175a0680dba158625abc2c0a8167e7c9e93445a5087592287995ceaacaf3b1db4f5b55ae84ffcc5a221319c3d64b4b49dee0c6ee752c99ee9d03ab1063de771585c4416f0269941b9720d377be967266091fb425d1d0fa5e00faddcbaa0a7c3273576eba25c220e677425b6c9e3d2a249161b0a5b3aad6bf02a32d495455ead4fa00783b0cb742522e0b0000000049454e44ae426082	9998452967969	2	f
61	\N	Pro test 1	Test	3.00	3.00	\N	\N	\N	0	\N	\N	2025-02-12 13:13:24.230269		5	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000b949444154785ee5d3b10dc4200c05504729d2dd0448acc158c7021765825bc91d6b20b140e852a0f319c977a1c1a18fbb57987c1b02d4d40e7713c284f004582f152863f4540604fe98339a31453fa850c64439d8dc24eb8ae70ba69db62b2e9cdb0d768576438025ca299ad2c771d3ef7b8ac84cc4a9653e55e91dcc1a86b43bee93d4aa6a9ced90d49a7867477c2ce776bbaaf770f6a9e257c0573126b28492fa4248bb93edaae2775d5e2e499fa2faafd87f9fa6b36ead2fa10c2565e6807a8b0000000049454e44ae426082	9996218842949	3	t
63	\N	sqdsq	sdsd	2.00	2.00	\N	\N	\N	0	\N	\N	2025-02-12 07:00:45.579848		2	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000ba49444154785ee5d2b10d84300c05d07fa2a0bc0522650dba5b892c00ba0958299dd788c402978e2292cfe8e09226c13d569a57f9db31b8a80fee268f874f23305f8a386ec97152088e8cf346a7352ae5ad4e1c595e4e5695cc47a69cb62aa9cdfe77d692ef22d9e5ccd2129999d23404851260d02bc4618279b2425b90ed2ea49094a4e6ee375f4bb233b66fb2d7daef0578159755151ccbdaf24fb72459683d3ab4e565be334b4b1c298c434e56d57e2f98faa3434bb96ead2fbb2d174f06aacf010000000049454e44ae426082	9998435605352	4	f
34	\N	Projecteur Full HD	Vidéo projecteur portable avec correction automatique.	399.99	399.99	\N	\N	\N	0	\N	2025-02-02 10:34:33.521993	2025-02-09 18:03:18.014069	projecteur.jpg	15	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000ba49444154785ee5d2b111c3200c0550e5285c6601ee58c39d570a0bc4992059898e3572c702a6a3e0ac8818c73428eaadca8f3be04b06b0a905ce260797043780f9af3cc614226681c0268c4e8be494546541228ca86d93ac2beacfebb6dbaea8127d1e13eccae163ccb313c8ab75c8d62b8100c6f07202d1d9c9a0af9360651630114dddc708bfc3c0f7b68fd336b329d45318d17ff070fdf5c089de4bbe97ab649a8432cb5053b3a22cb84e4db29e4a7fe1b967e174d4a9f501ff542340434cf36a0000000049454e44ae426082	9990706352286	6	f
67	\N	sou	ze	2.00	2.00	\N	\N	\N	0	\N	\N	2025-02-12 07:22:53.485034		2	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000bf49444154785ee5d3b10dc4200c05d07f4a91ee2640628debb2d2b1c045d9cc1d6b44ca02a1a388e4330a283438f471955738d8c680abd8f134115e842f30dfca73a0d5f1d121b83804327d5a5da7fcd1270ede86aab2a6a43f6fea6e9b92a0a19e6053641702c635ff45119bdf6499cb098a6808d18698fbd324799f63a6dc9f26bfeda34cc29e556b92a06d89a56a4532b388374a658ad23d5c79aa640b80a9ece08d2cc7d283aeb4b0575e5bf22963db729ea2b42fe9119c799aae78b4fe7b9018de55de6df00000000049454e44ae426082	9995330924052	2	t
31	\N	Télévision 55" 4K	TV UHD 4K avec HDR et assistant vocal intégré.	499.99	499.99	\N	\N	\N	0	\N	2025-02-02 10:34:33.521993	2025-02-09 18:03:17.960773	tv_4k.jpg	20	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000b749444154785ee5d3b10dc3201005d043ee930518241d2bc50be08c46c71a9658c07457a05c8e08645370d01bb9f02bcefa7c30d0651d703739503ebd01b6a13c451722a509c1eaf8d153f27a56d8ccf54591e72ec9bacafbd3cd6e7b6a3b93e492a2645ff52b827c20d40acb9c247e25fdc430965fbe06aca94d08c2b4bae51f7f24ca656c358b24ee0c79ae649194cf813eb0976492f816248573e2327c6d77a4478e3f1467d9ad59c6e2fdf11fe0cedbd3d5b96ead1f9a721bfc1f3071df0000000049454e44ae426082	9998310488728	5	t
59	\N	test	ed	2.00	2.00	\N	\N	\N	0	\N	\N	2025-02-12 06:24:51.0945		2	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000be49444154785ee5d2b10dc3201005d01f51a4cc02485e83b1c20271b28067ba8e352c7901d35158b980820d0d677ad33da413779f035767c5d544b8119ec0782ac73e2c9eb70ec186c193ee93ea156bdb25f63cdbaab3a6e27c4ed7d336c5e962ac126c8a661b747941905b266c96d4bf4e12abd50c1fb7f72288d46462514e421460621843ee5a10a780c750a66d2a66c61af70ea57dc183960ec1c6d8f6ae4fe4f042eefa44212dc251d7167bc76fb3bf2028ed8bfa22ff98a4722ead1f98eb16e3a94bd51a0000000049454e44ae426082	9993437233275	3	t
64	\N	test	tsd	2.00	2.00	\N	\N	\N	0	\N	\N	2025-02-12 07:07:04.628869		3	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000bb49444154785ee5d2bb0dc3201006e08b5ca44b1640628d74ac142670bc19ddad61c90b98ee8a487fb0646c5270d09beea3b837a1782b5d4d816e81de4463538c284bc4b743e4c5c660fa34f40ac6770911b32f2aab2af5c7a6ecb62a6c1f6331c1aac2ecc59c191421d59baa1ef6288ac2b2be30f1bc4751c4e6e106489e84a2603fcec6a36a45fffd694a33134377dbd6b6077af2de9f2af2e9065dde5f4ba3e40cbac4aec75d6b42643bf17959556df7b20079638ace7769fd00fd7a15333b325c960000000049454e44ae426082	9994620480568	2	f
47	\N	Câble HDMI 2m	Câble HDMI 4K Ultra HD compatible TV et moniteurs.	14.99	14.99	\N	\N	\N	0	\N	2025-02-02 10:34:33.521993	2025-02-09 18:03:18.214508	cable_hdmi.jpg	150	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000bd49444154785ee5d2bb0dc5200c055047142c81c41ae9b25298204c90b7923baf118905a0a388e447a47c0b1cfab83b05609b0b7cab085f1342c76604985e459cc8385e1b048e5442d3245e5da310dac4096dba755655998f1ed356c5dbd173679230c40146adde458151cdfdd1b5205eba1ca2debb964461d62bc0f9425da87caffcd0a052c85e5f9ba8aaec0c178761bf4550f9876ce339ada49202d3e52383b2d07abd34292bafaf17ea2a7981291f1b14b4e5857f64df75d5a7f5070c4207500f2bd9da0000000049454e44ae426082	9996916565898	1	t
32	\N	Support mural TV	Support inclinable et orientable pour écran jusqu'à 65".	29.99	29.99	\N	\N	\N	0	\N	2025-02-02 10:34:33.521993	2025-02-09 18:03:17.978188	support_tv.jpg	50	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000c149444154785ee5d2310ec3200c0550570cddda0b20718d6cb952b940a35ea05cc99baf112917081b0392eba8a564c1618fc5f258b03f06ded50a6713c205f303603a14714cd973ee1078b21e6d9f96d829747de2c8726a674dc97c64f7d3362595dc3f334d6822b9507ad14479c2fc1ce763a565bd5a28bd68427b1bf29d3b44d28b0958de5324851cd87ce7d3249931bfa824a168db9719c6dd6635059ecd3ad49fd6842ea4e5f7822e32efadfd437144b928b928daf60526ac493455ebd4fa0076f51a76605cb22f0000000049454e44ae426082	9996870394589	5	t
68	\N	sd	dfl	2.00	2.00	\N	\N	\N	0	\N	\N	2025-02-12 07:27:52.320572		2	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000bd49444154785ee5d2bb0dc4200c066047142c81c41ae958292c7097099889ce6b446201e82822f91c4509b982471f44f315967f83811e27c2dbe46122b5007cbb424aa82ced03028b22793524daeda03c8c8992d7e991ac2a9e0fffa6ad8a8ed2fbcd5af2211a58a4e80b859bf95ec91aca3c9c887240a89d540057878648270ceb3ca263be556e678796f8cdfcc671ceba96f81fb28ea6fc4a5d60b39af2b5836da1beb37494c32acbf6d4c57bbd7d4c495dd5b12fe48ceeab9c57eb07565f05bed09c2c740000000049454e44ae426082	9991810808898	3	t
53	\N	RE	RE	1.00	1.00	\N	\N	\N	0	\N	\N	2025-02-10 14:56:06.081105		2	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000c249444154785ee5d33b0ec3200c0050471932f60291b8065baf542e90b61768aee48d6b44e2026163b0e43a2a842c71d963b13c24cb1f01f02156b89a103aa407c0f3af3cc7448ea941e0fce8706c53888d42d3268e2ca776762a99cf8fc7694f2591ccbe334dd8476fe6d28b26494a34d95c4115754c004b8360b274e392a708c36b0833e63c4d12d275ea7ff369929db17963d984a2edbd00d85c4115380e2bd497a5c9f36708b9822e0cb32fbd68e2b85de49d69dafeca32ddeb264e55e3d2fa02b29c231c064f1fde0000000049454e44ae426082	9993024506997	2	t
52	\N	TEST	DESF	2.00	2.00	\N	\N	\N	0	\N	\N	2025-02-10 14:54:49.858854		5	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000bc49444154785ee5d2b10dc3201005d06fb94899059058235d560a0b38ca02f64ad7dd1a9158c0741448179cc4810672bd4f2e789690ee3e07a96ac5d1441828dd80fb5fb184989c2485e0d838323af9a014599d2448fe4a674de5f9d8d4d336952bda5f663dd118d82e7b2f3d499a90a6cb53213388011422bbb039478dfc8a71e17dda8ee47d55fc67be9e7266d13e684fa2a36d5f806bb5594dc1c5b17ee99ec8cea76f2f7fc47e86554842fe21a5b3a6b67dc9c7924453a50ead17c9dc14cd5cb0cf720000000049454e44ae426082	9997852898026	2	f
71	\N	Produit Herve	description	2.00	2.00	\N	\N	\N	0	\N	2025-02-12 14:25:05.131579	2025-02-12 14:25:05.138376		6	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000bc49444154785ee5d3b10dc3201005d08b5cb8cc0248ace12e2b85051c7901b3d275b786251608dd152817ace0d88de17a9f685e81f4ef0b400ef386ab09e186e909f06a8a24727292140247c6a1d12944a5d0ea2451f2d9939d2aef47e6b8eda9f2b0fd7756137691acdfb2d4c4761ed2f858da926ec204bd421c3e60ee5c52d78429b7eb51a13c68bd74bffd6aca9db14c64db5adf0bc0505257058e437e5c2aa1782a591aa2300fa290c4f56a69a2a6f5af2c63bf3771aa7d2ead2f677a20eb1cdd49af0000000049454e44ae426082	9996317156640	3	t
29	\N	Mixeur plongeant	Mixeur 3 en 1 pour soupes, sauces et smoothies.	39.99	39.99	\N	\N	\N	0	\N	2025-02-02 10:34:33.521993	2025-02-09 18:03:17.923765	mixeur.jpg	50	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000c149444154785ee5d3bb0dc3201000d08b285c660124d670e7956001475920ac741d6b58f202a1a340ba9c1563dcf8a0f789e61527ee0340a7f8c2dd84f0c06c015e4d058a293bca1d0217b443dda735760a4d9f28129f5ad9a5b8bfa0cfdd5e8a239963669250c5607ca94514f9a0e769692b2c76d230943d48e242f233953c49bcd8d563c913c481c6d3faef4f12cf2cd11b4d5bdb7b01184f2feb52e048c554372d89c7866abfa121f519f78d89a2886087323341db5f59e661bf41528d5beb07f15c185d196b8dbe0000000049454e44ae426082	9997258794120	4	t
30	\N	Fer à repasser vapeur	Fer vapeur puissant avec semelle en céramique anti-adhésive.	39.99	39.99	\N	\N	\N	0	\N	2025-02-02 10:34:33.521993	2025-02-09 18:03:17.938188	fer.jpg	30	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000ba49444154785ee5d2310ec3200c055057d9db0b20e51ad972a57281d2dccc1bd740e20265f310f51754a2b0c4610fdb4342c6df2634e7435713d38de949e44ee5912426ac1d222b6362d3a7a15730b64b4808b6f9d9a1727fdeb4dd1e0ae5c235091e8a8315b3575024abcbe2e1ff4e938f0bb070a83d2862739f225093d084b8e439c8d683a612b0c378ae9c9918a20e957d090f1f3b44b6c4b6cdef44f42a9fea90e422db5e6b42f2784f35334d655f86ef5c27a6693f97d60f1ec829044345c52f0000000049454e44ae426082	9994084242955	4	t
40	\N	Disque dur externe 1To	Stockage portable avec transfert rapide en USB 3.0.	89.99	89.99	\N	\N	\N	0	\N	2025-02-02 10:34:33.521993	2025-02-09 18:03:18.102367	disque_dur.jpg	60	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000bf49444154785ee5d2bb0dc3201000d0b328e83201126bb8f34a6681842c10af741d6b446201d3b9b0743924639c824f6f2a5e71ba2fd0e5ad7037210ca4668057538e825386f60e817122a0ea12eda653087da2803a5c2a2b8afb737fdd164531f49c594de8d7096629da727e41f11953d51591b6a358a143088f4901f8235f4dc24acfa16dc5feb495df235f453c33de833bf395c57be0cf943756165f811a28dd605d48efad4f9bb7696655f1bdc053e6aa8b8af7a29778082de5776bfd00d8a2019b39d3bf830000000049454e44ae426082	9995979734494	7	t
35	\N	PC portable i7 16Go RAM	Ordinateur portable puissant avec SSD 512Go.	899.99	899.99	\N	\N	\N	0	\N	2025-02-02 10:34:33.521993	2025-02-09 18:03:18.032634	pc_portable.jpg	10	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000b949444154785ee5d2bd0dc3201005e0175150660124d6709795cc028eb240bcd275b786252f103a0aa40b96e2e0067cbd4f345fc5bb1fc8a13eb89a0837ca23f03c154b4c39485608815d20a7d31a9522af934429af266baaf4c7eed86d53a592ffcfac273291fdbc67e9291949791a967309601dac426444dc3d299496406626854a95d425d1a9caccc4bf789f4447db1e163cea65b58520e6b8e99e58665e7f3ff445e66dbd421219a3adc99adaee0513ea249aaa75697d01022812c028c3eb990000000049454e44ae426082	9998620329902	7	t
25	\N	Lampe de chevet LED	Lampe moderne avec réglage tactile et intensité variable.	34.99	34.99	\N	\N	\N	0	\N	2025-02-02 10:34:33.521993	2025-02-09 18:03:17.819656	lampe_led.jpg	50	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000b749444154785ee5d3b10dc3201005d08b5ca4cc0248ac681688e309bcd275ac61c90b84ee8a483f90804cc3416f2a9e25c4bf8f4ca8d69bae26a61bd34cb474e511e408f80c889cd8c0664cd3a860dc9010b0bb2a5953713e6fea699b42fab0540d36c5bb1373dea0c8db2d8aa7ff394d30445879cf5914c9b4f101c9376862fb221ba4a45694e7b37dc5ce7e71fa4aef400f5fa6d5442ed5563ad3e5e979cf593a92b8c99da94260ac62fa4affca81f2629ace75697d01c87520df30c33b800000000049454e44ae426082	9994248063365	2	t
37	\N	Souris gaming sans fil	Souris ergonomique avec capteur haute précision.	49.99	49.99	\N	\N	\N	0	\N	2025-02-02 10:34:33.521993	2025-02-09 18:03:18.061886	souris.jpg	50	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000c349444154785ee5d2310ec3200c0550570c197b01a45c235bae142ed0aa172857f2e66b207181b231a0ba440d81250e7bac0cbc21f06d006eea035713c20dd302f03c157188c970ea1018d206759f7ce8148e7de2c0f9abc90e95fb23dd767ba85c71dc67260955a0d1962c92c87f27fd98ddb9d82d8386a1641144dec6748ffb2e829c4165b1fc2788d7b15956fffe24e59931bfa84c42d0fa5e1c4c258b2430ac42ac372d294f97fc76c289fc7b2859247140b734c90eb5be97bcdc4e9054ebd2fa01bbae2b32604ce23c0000000049454e44ae426082	9997272260380	7	t
38	\N	Tapis de souris XXL	Tapis antidérapant pour gaming et bureautique.	19.99	19.99	\N	\N	\N	0	\N	2025-02-02 10:34:33.521993	2025-02-09 18:03:18.074119	tapis_souris.jpg	100	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000bc49444154785ee5d23d0ec3200c05605719b2b51740e21add72a57081a63d0157f2e66b44e20261f380e41235292cfcecb158beed3d63906c36b89a106e186680a52912cfc148e810185206559f9cef14ea3e8997f852b2a2623f5279dba2e2b0feefac261c3c697b66a989c242e135ad6d897b8f0aa0431cee63784897e669b074f4ab290e6acbeed7afa6b833960feab6f67f58e1992eab2c30ec3648975553dc2e1d591ac2c1f299a526f1b81a4cc98adaef05966c1345a5b9b4be376e23aca9fbce970000000049454e44ae426082	9995128336173	7	t
66	\N	qskdlj	kqsdjf	2.00	2.00	\N	\N	\N	0	\N	\N	2025-02-12 07:21:43.428686		1	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000bd49444154785ee5d2bb0dc4200c06609f28aebc05905823ddad1416b8e816082bb9630d2416081d0592cf28979086471f8be62b907f5b06bad4067713c203d30cb0746529c4a4290d08b4951ae5987c1814aa3151207e2559553c9f95d769abe28aeadc594b288255e6c8d252e414e933b9be10e6494249d690589fe9452352dbdb1b3c3a34c48564a2dfe76b897716d517555ff95e1c4c97cbaa0a348990f73120ce82ff2c3df935c7ef8a02729392acaa7c2f6e8962ffd752a95beb07ecd5211b83da02640000000049454e44ae426082	9999408399940	3	t
70	\N	sou	ze	1.00	1.00	\N	\N	\N	0	\N	2025-02-12 12:20:33.302445	2025-02-12 12:21:32.373477		2	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000c149444154785ee5d2b10d84300c05d07f4a41c7044859838e958e095016b899dc658d482c40ba14e87c4117489a98f4b87b45d0f7c7e062363c4d8417e10d2cb7b2ecc9cdbc370873509e8636b9b951766f137bab7d91acaab89f1dca6dab8a43aa6cb02ad28680cea5af08b2ae1f3587b35d41a4cc913aed2729f0a71b16db200646f6d70e92629cd584945a52ec8cd177b9ddaa8e7b5186cf2624c52b00a6b31759acbf93fea7be91e56dccefea8a59f685d6f44ed0712fbc5d7f5a509e47eb07ecd013da5bd8f4390000000049454e44ae426082	9998777924586	2	t
45	\N	Cafetière à capsules	Machine à café avec arrêt automatique.	79.99	79.99	\N	\N	\N	0	\N	2025-02-02 10:34:33.521993	2025-02-09 18:03:18.181605	cafetiere.jpg	40	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000bb49444154785ee5d3b10dc4200c0550470c90059058e3ba5be93c01ca66ee5803290b84ce053a1fe840a189431fba8784e07f0b90611df034112c041f007fab2089f724794280ec12d939995989c529499288c3cb2e55f2053ba6bd94d40d3f3478298ac8f6bc4111e73564a4fd7f4e5330c7db6d145b06456484cba1d68426b10b95325ccba0a916ec67543a630b75fb4e750eb0869e4f132001863edb1b454ffd065dec8e576f509324721bb78969aa7fc57ca1bd5ad3b91ead1f0c2f276cba315b1b0000000049454e44ae426082	9999169229623	4	t
43	\N	Caméra de sécurité WiFi	Caméra intelligente avec détection de mouvement.	99.99	99.99	\N	\N	\N	0	\N	2025-02-02 10:34:33.521993	2025-02-09 18:03:18.150597	camera.jpg	30	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000be49444154785ee5d3b10dc4200c05509f52a4bb5b008935aecb4a61818b32012bb9f31a482c103a17483ea24b0e1a087d2c9ad7a06f63408adae06e4278609c01964b91048e466287c09032a8fae443a750f74982a493935595fa2355765b552ad6ff99b58443206dcf2c2db17a8ef1f376d712bfa282b147e9eef8e20eb102182c1dfdb5940ac5f2f0ebafa534339615cf4934b4ef8b83a9d8acaac0b0dfa6f3fdda426de5c87221f296758724909b8b6455ed7fc52d94275155ae5beb0b66df1f1458d54cf00000000049454e44ae426082	9998502819019	8	t
60	\N	test pros	 pro	2.00	2.00	\N	\N	\N	0	\N	\N	2025-02-12 06:31:18.984762		3	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000be49444154785ee5d2310ec3200c0550571932f602485ca35baf542e40d50b842b79f33590b840d918905c474d429638ecb158de80fc6d00ded517ae26841bd617c0fb54c4b954c7b543e0c838347d4ab95368fbc499e5b4648792f9c8eca73d9454b1dbce34e190c986358b26921bc63f6387a26481ed1d1461f58f7a2f1d2a726908b474d0c4739c50d27f3e4db233b61fb4e79adf21429b5611b89232b59fa589788261e9a00bd334ae593471a6e868ddb5a2f9bf801f970e9a5a5d5a3f57231a85ec0208ae0000000049454e44ae426082	9996969249226	5	f
49	\N	Casque audio Bluetooth	Casque sans fil avec réduction de bruit active.	149.99	149.99	\N	\N	\N	0	\N	2025-02-02 10:34:33.521993	2025-02-09 18:03:18.248136	casque.jpg	50	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000b749444154785ee5d2310ec3200c055057193ae60248b9627d02949bb1f91a48b940d83ca0ba466d0a0b863d6c0fc932fe18a43927dc4d011e015e007e2892c447923c2140de5270735a66250ea7244922362feb4ae723d74edb95940bdf24d85588c8ae7630448b70463abe7596583f41f6d264248a2b6be9958421c9281ac63583a552ea791b4b33630730a1b22f71d529c702d4d8a8feb429f0e1ea60abec60fcd7f52589b65dea667555f665793f7fafb654cfadf50182e7240c3c6d9f720000000049454e44ae426082	9995252740013	8	t
42	\N	Montre connectée	Smartwatch avec suivi d'activité et notifications.	129.99	129.99	\N	\N	\N	0	\N	2025-02-02 10:34:33.521993	2025-02-09 18:03:18.134399	montre.jpg	30	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000be49444154785ee5d2bb0dc4200c066047142c819435d2b15258e0925b2033b9f31a91b2007414917ce4eec8a3c0a18fbbaf40e6b70d7c2a0f4f1342c3a607186e451cc8385e2b048e544053255e5da510eac401db70fa5951291f5dd216c5dbd37d669270f1167aadee45f0ead4d4ed33133483555ee77c82a219d0805e7e1d44a57ccb682bf4cd375e2651d036b3d9d1ff9da4b4076ebd3db65216b8681ace37280bf91deb1453be5387a2d2bdc040796382b67ceda4f315083aead1fa0082d50b526bc0aa670000000049454e44ae426082	9990108469834	8	t
26	\N	Miroir mural design	Miroir rond avec cadre doré élégant pour décoration intérieure.	49.99	49.99	\N	\N	\N	0	10	2025-02-02 10:34:33.521993	2025-02-09 18:03:17.837453	miroir.jpg	30	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000c049444154785ee5d3bb0dc3201006e08b5cb8cc0248ac91ce2b9905627981cc44c71a485ec0745758f9038989697cd0fbbaef24740f8050c44a5793a59ba59168aaca21f012b035880ceb60559bba5641992621c09ba2b353c5f99c2aa73d1552622a36782aeb0daba382201713b1ebe5774e12681cf49c8ad4c4783d16f0be09516aec75e0bd6b49319c9fa0eb8a3bc346295dd3f71eeeee3fad20322ebec17cb7b2ac7ff6b9822cd6eb90dfb524048b39ef4c52fa2bddbbdfbb9674c4a5f5013a332376aa921f7e0000000049454e44ae426082	9997769093866	2	t
69	\N	xc	sxf	1.00	1.00	\N	\N	\N	0	\N	\N	2025-02-12 07:37:40.111944		2	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000c349444154785ee5d2310ec3200c0550570c1d7b0124ae918d2b950ba4ed09b892375f23522e1036065497a810b2c4618f95e50dc8f68f8177b5c0d58470c3f404789d8a38c4e43875081c6987ba4f73e8149a3e71e0fcb5c90e95f723bddff650b9a2d93293842a90f1751649acbe568fc374ae086013dccb2c92687a917e70792789d5dbce9eea7e92f253e379feef27296716cd076b1282d6ff0030b4cb3a16b8a816db2e4bd29aae2a1d4ea4fc368b240e989bd40405adf7328d43e920a9d5a5f50383461482f397aa180000000049454e44ae426082	9999613512431	2	t
51	\N	Fauteuil gaming ergonomique	Chaise gaming avec support lombaire et accoudoirs réglables.	259.98	259.98	\N	\N	\N	0	\N	2025-02-02 10:34:33.521993	2025-02-12 14:01:53.26958	chaise_gaming.jpg	15	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000be49444154785ee5d2b10d84300c0550230aba63814859231d2bc102774c70b792bbac8194054897223a9fd1054113e31e57bc48c0b763a053ad70372134094680d7a53cc514226585604a14d1a884ad56db814614c94ca76455717fde9cbbad8a2bf1e331c1aa9066975fa89087476726df5e2b85d9858f0f0ae59ebf8ea53f49dece838d69cf2288b6380d2dfff724f1cc308f43c92289efc12ffd9e5a14ef0bcfcc2af5744ad13a94d4a27855edd71dc9aadafa6bdf5df983a4a36ead1ff19e2d15b2f1069a0000000049454e44ae426082	9991866638067	2	t
65	\N	ere²sdfsd	jk	1.00	1.00	\N	\N	\N	0	\N	\N	2025-02-12 07:11:34.493758		3	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000bd49444154785ee5d23b0ec3200c06604719d8d20b20e51ad97aa5f602a1bd191bd788c405c8e6c1aaeb4879c0c0630fcaf20d26f68f81a313e06eb2d0397a0198aa1cafd6af4c0d82b7954f37c9e95661529717af52177596d5369f4ea6cd29cdac244b1dd33c1db714c4fc9ba8c3f396bcb0ffa27e605f975d0605f3734fa2241e03f8a08eff9524b12d06c7ba2433963a5fd7f60efc51d766e5255b40004b9358c3916e4548c3b4775d94f402c6ede99624f3591fa2edc9ea3ab7d61f22af137d42125dce0000000049454e44ae426082	9999535028867	3	t
48	\N	Chargeur sans fil rapide	Chargeur Qi 15W pour smartphones compatibles.	29.99	29.99	\N	\N	\N	0	\N	2025-02-02 10:34:33.521993	2025-02-09 18:03:18.234334	chargeur_sansfil.jpg	60	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000bf49444154785ee5d2bb0dc4200c06604729e86e02a4ac912e2b85052e99e06e2577ac118905a0a340f28194d71518f7a1fb0a1ebff9816ecbc3d384d0919e0196a62c05ab0d2581c0d83ea0168992110a41260a3884dbcbaacaf9ec5fdaaaa86c3d67c6099d9f60567d5b165e93fb8ce7ccea42dd51ef9540b42d9400dcfe3246e802ba7514a8e41b56b5ed37302a33db0c1ea730caff10c99f6939e516e82e1e1de465695532c59cef6a4f5db92ff09e8e1f6394f3e1f02d4568e95a8fd60f63a8171cea2e20b80000000049454e44ae426082	9991575379718	1	t
44	\N	Climatiseur portable	Climatiseur mobile avec fonction déshumidificateur.	299.99	299.99	\N	\N	\N	0	\N	2025-02-02 10:34:33.521993	2025-02-09 18:03:18.165674	climatiseur.jpg	10	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000bf49444154785ee5d2310ec3200c0550470cd9da0b58e21add7aa5728146bd40b9129baf11890bd41b43549728a16489c31e8be50d16df06904d7de06c0ad085e901301c8a84d3e4646a10384217b04d911b156c9b84259f9a6c57793ec2edb4bbca95ec7f679a8261b2be645185971e9ff7729fa2645810fab141f1dbe335953e45821d451f5a34afcd4b5ce6d3947796e44565138ae67718e1b6a656054e2253fd599ac8be61cd7224e3c5364898464735d9aee6ff0243324b9fa65aa7d60fc6091fc213cba6cc0000000049454e44ae426082	9991172124896	2	t
41	\N	Enceinte Bluetooth	Enceinte portable waterproof avec son stéréo.	59.99	59.99	\N	\N	\N	0	\N	2025-02-02 10:34:33.521993	2025-02-09 18:03:18.121143	enceinte.jpg	60	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000be49444154785ee5d2b10d03210c0550471474c902488c9295c20221d98cce6b20dd0247e7e22407745ca0c1477f740fc9e2db18b83b2b5c4d016e015e00fe54c88996c4db84c0914dc1cc49cd8a8d9b12278eae4b3654ee0f4ddfed505c2e7c37c1a1427464da0b8230be754ebdec75a25422fbc5e31f04b1b96bc554272169ff07b2f505415c2e3cd5fe24e599f106bad6492afb121f78f427095cdec1674b2d09a3c79ae544645788ffbab13805fb81b65943957d59986a6a49ed5c5a3f79c51cc54a7bcf590000000049454e44ae426082	9998351585080	8	t
24	\N	Écouteurs sans fil	Écouteurs Bluetooth avec réduction de bruit et autonomie 24h.	89.99	89.99	\N	\N	\N	0	\N	2025-02-02 10:34:33.521993	2025-02-09 18:03:17.799393	ecouteurs.jpg	80	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000bc49444154785ee5d2b10d84300c05509f18e01640ca1ae958892c00ba058e95dc798d482c70e95258e7333a203498f458695ee56fc72087fac0dd84f040ee01c64b91a4cc41b84210a80dd8d6694e9542572749a2af243b95ce47ed71da536965b7efcc123689dcb465b1f5f53c745b3f43c2bd67d8b318ca3c223f255e4b007c33618d348e4c32ffe7b3a43b13f72277ade51f00bab5832908a26b2b97650975bbcddac116cd6fbf65b1248962c825d9a9967b8903ac1d2c95bab57eecc51e8a498a28170000000049454e44ae426082	9991416692082	1	t
36	\N	Clavier mécanique RGB	Clavier gaming avec switches mécaniques et rétroéclairage.	79.99	79.99	\N	\N	\N	0	\N	2025-02-02 10:34:33.521993	2025-02-09 18:03:18.046945	clavier.jpg	40	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000be49444154785ee5d2b10dc3201005d0b3285c660124d6709795cc028eb24058e93ad6b0e4054c4781f4831513dcf84cef2f9a5720dd7d201cb2d2ddc4d4711a895e97f2083159a40691f5dab26ed3121ac5a64d08c8a74e76aabc9fd7c76d4f9513cdbf3349ac8237aecc220a1f4ad373be16528744d4a0a8c0fa817d6a499e68508ef77b9272d8b8b8fcf693943b8379736942d0f60e44653f5164a1d6a1fe2c491e0efb2c1762e5bc6910829fc7be742668fb2ff3d4d7264e55736b7d011f121eed699637c10000000049454e44ae426082	9995563847999	7	t
33	\N	Écran gaming 27"	Moniteur 144Hz avec technologie FreeSync et résolution QHD.	299.99	299.99	\N	\N	\N	0	\N	2025-02-02 10:34:33.521993	2025-02-09 18:03:17.998816	ecran_gaming.jpg	25	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000bc49444154785ee5d3310ec3200c0550570cd99a0b20718d6ebd52b9402b6ee68d6b20f50265f310d5350d515870d8e3ed0d46fe06809bfac0d98470417800bc0e153963f2bc0c083c998c764cc90f2a2e63e21c5d6e26eb4af245dba6ed4a0a4dbbc1aed0050498523d4513ccc44c23729964ea9a4f959d255cdcf269320139d336b5a27fbe40ed263a929d51ba4efb76bb2af7207d359faaf20a0006c5dfbb5ba73e5059dbded797ccb23c6fefdaa7a8fc15279f60edd3b4d7a9f5034bb824f075019f9f0000000049454e44ae426082	9993318744074	6	t
39	\N	Imprimante multifonction	Imprimante laser avec scanner et connexion WiFi.	229.99	229.99	\N	\N	\N	0	\N	2025-02-02 10:34:33.521993	2025-02-09 18:03:18.086991	imprimante.jpg	15	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000b849444154785ee5d2310ec3200c05d05f65c8965e0089a3e44ae1024d7b336f5c032917a83786482ea889c282610fdb4342f6378614e78bbb89f0202cc0da94178e1bcbde21b868994c9f865e89715d1296e08aceaa4af9bc29d35625f9622d2658150517cd554113a67177b41def14c57d817c7291a6c2340f7256d0e40716cbf1e85a53ce17d61ea5994503d8b6f23fe0e98f7caae0d2d8e8ecbaa5576eaa43299f9c7bad4998ec7b346de57dd9d2e4fe15345de7d6fa01ecb425b6d236e2480000000049454e44ae426082	9998274250317	7	t
28	\N	Couteau de chef	Couteau professionnel en acier inoxydable avec manche ergonomique.	24.99	24.99	\N	\N	\N	0	\N	2025-02-02 10:34:33.521993	2025-02-09 18:03:17.908087	couteau.jpg	75	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000c049444154785ee5d2bb0d83301006e08b5c78094bac41e795f002842cc04cd7dd1a9658007729902e4762032938dce3ca5f61ddc33ff0e1cc7037213cd87500cf4b11277281970a412093d05589975029843a71c2261d3a3b95cc477fd39e8ad7a7dbce34e1347be8acb9d65bde99b1cd5d6b924f68cd0cb96b4d147befc04ebf0aaa4ca269f0b9174ddff9061b733d45b2338c8172054df20f7229f3a9921438f02583bab079719dd80cb6ec4c93e4057abf777daa352f3c424981a2fddc5a1f9cd60f9c192496be0000000049454e44ae426082	9997059064095	4	t
27	\N	Aspirateur sans fil	Aspirateur balai puissant avec batterie longue durée.	179.99	179.99	\N	\N	\N	0	\N	2025-02-02 10:34:33.521993	2025-02-09 18:03:17.854313	aspirateur.jpg	20	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000c049444154785ee5d2b10d83301005d08b28e892052cb10663c50b84640158e9ba5bc39217883b17962e8782318d0ff79c685e81fcefdbc087f9c2d58470c3f404984e451c62b29c1a04968c45d3261f1a85439b38b07c255955b21f99e3b655c9c461ef4c137681862567d1c47ea1f41a5d839c64813e675114e13e9a076fff6942ffeefd820d9291d4ecfffb6992ce983f989b50b4de8383b1bcacbac0461fa8dcb426e419baed84137533e42c9a38ac876c4d685adf8b9ba8345155994beb07215723bbc3352a260000000049454e44ae426082	9999733830026	2	t
50	\N	Table de bureau en bois	Bureau spacieux avec pieds en métal.	189.99	189.99	\N	\N	\N	0	\N	2025-02-02 10:34:33.521993	2025-02-09 18:03:18.259982	bureau.jpg	20	\\x89504e470d0a1a0a0000000d49484452000000640000006401000000005899a8f9000000be49444154785ee5d2b10dc4200c05509f18e01640620d3a560a0b24ca029795e8bc06120b848e02c94774e44883431f8be6557c7f19e8323b3c4d0e5e2e4f00cbad9062ca96f280c0a2b44e8e29c4413935268a545e4bd655d90fe575dbaeca24f5ef8c931311d57666e194c2c7e459fb7b390093a125eb8b60d6f29d4624560a1bd61f5895dad496c26f3f4ea5b3442b9e4d303aeec583a93fb0029bc4aedb657142dab066b993f8e8330b278ae827533be374dc8b5f5c6ba2ab368fd617f3cb23cdfab570ef0000000049454e44ae426082	9999521318361	2	t
\.


--
-- Data for Name: retours; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.retours (id_retour, vente_id, produit_id, quantite, raison, created_at, updated_at) FROM stdin;
\.


--
-- Data for Name: role_authority; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.role_authority (id_role, id_authority) FROM stdin;
5	6
5	4
5	5
1	5
1	1
1	3
1	6
1	2
1	4
\.


--
-- Data for Name: role_employe; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.role_employe (id_role, id_employe) FROM stdin;
2	12
4	12
3	12
1	12
4	13
\.


--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.roles (id_role, nom, description) FROM stdin;
1	ADMIN	Administrateur ayant tous les droits
2	GESTIONNAIRE_STOCK	Responsable de la gestion des stocks
3	VENDEUR	Employé chargé des ventes
4	COMPTABLE	Responsable des finances
5	Role Test	erer
\.


--
-- Data for Name: types_mouvement_stock; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.types_mouvement_stock (id, code, nom, description, date_creation, type_mouvement) FROM stdin;
6	DESTRUCTION_PRODUIT	Destruction de produit	Sortie de stock pour destruction de produits périmés ou endommagés	2025-01-29 03:37:46.183956	SORTIE
10	PERTE_VOL	Perte ou vol	Sortie de stock en raison d’une perte ou d’un vol	2025-01-29 03:37:46.183956	SORTIE
4	RETOUR_FOURNISSEUR	Retour fournisseur	Renvoi d’un produit défectueux ou non conforme au fournisseur	2025-01-29 03:37:46.183956	SORTIE
8	TRANSFERT_ENTREPOT	Transfert entre entrepôts	Mouvement de stock d’un entrepôt à un autre	2025-01-29 03:37:46.183956	SORTIE
7	DON_ASSOCIATION	Don à une association	Sortie de stock pour don à une organisation caritative	2025-01-29 03:37:46.183956	SORTIE
1	ACHAT_MARCHANDISE	Achat de marchandises	Entrée de stock suite à un achat auprès d’un fournisseur	2025-01-29 03:37:46.183956	ENTREE
5	INVENTAIRE_AJUSTEMENT	Ajustement d’inventaire	Correction du stock après un inventaire physique	2025-01-29 03:37:46.183956	ENTREE
3	RETOUR_CLIENT	Retour client	Retour en stock des produits renvoyés par un client	2025-01-29 03:37:46.183956	ENTREE
9	RECEPTION_TRANSFERT	Réception de transfert	Entrée de stock suite à un transfert provenant d’un autre entrepôt	2025-01-29 03:37:46.183956	ENTREE
2	VENTE_PRODUIT	Vente de produits	Sortie de stock pour la vente de produits aux clients	2025-01-29 03:37:46.183956	SORTIE
\.


--
-- Data for Name: ventes; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.ventes (id_ventes, client_id, date_vente, montant_total, created_at, updated_at, employe_id, actif, etat_id) FROM stdin;
50	1	\N	0.00	2025-02-22 12:15:15.437091	2025-02-22 12:15:15.437091	12	f	1
49	24	\N	0.00	2025-02-22 12:15:07.7063	2025-02-22 12:15:07.7063	12	f	1
48	1	\N	0.00	2025-02-22 10:41:37.045894	2025-02-22 10:41:37.045894	12	f	1
47	1	\N	0.00	2025-02-22 10:41:07.405214	2025-02-22 10:41:07.404672	12	f	1
46	1	\N	0.00	2025-02-21 02:33:13.419009	2025-02-21 02:33:13.419009	12	f	1
45	1	2025-02-21	0.00	2025-02-21 01:27:53	2025-02-21 01:28:07	12	f	1
51	1	\N	13.00	2025-02-22 12:19:31.61507	2025-02-22 12:19:31.61507	12	t	1
\.


--
-- Name: achats_id_seq; Type: SEQUENCE SET; Schema: public; Owner: user
--

SELECT pg_catalog.setval('public.achats_id_seq', 41, true);


--
-- Name: authority_id_seq; Type: SEQUENCE SET; Schema: public; Owner: user
--

SELECT pg_catalog.setval('public.authority_id_seq', 8, true);


--
-- Name: categorie_id_seq; Type: SEQUENCE SET; Schema: public; Owner: user
--

SELECT pg_catalog.setval('public.categorie_id_seq', 8, true);


--
-- Name: clients_id_seq; Type: SEQUENCE SET; Schema: public; Owner: user
--

SELECT pg_catalog.setval('public.clients_id_seq', 29, true);


--
-- Name: employes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: user
--

SELECT pg_catalog.setval('public.employes_id_seq', 13, true);


--
-- Name: etat_facture_id_seq; Type: SEQUENCE SET; Schema: public; Owner: user
--

SELECT pg_catalog.setval('public.etat_facture_id_seq', 6, true);


--
-- Name: etat_id_seq; Type: SEQUENCE SET; Schema: public; Owner: user
--

SELECT pg_catalog.setval('public.etat_id_seq', 4, true);


--
-- Name: etat_paiement_id_seq; Type: SEQUENCE SET; Schema: public; Owner: user
--

SELECT pg_catalog.setval('public.etat_paiement_id_seq', 3, true);


--
-- Name: etat_panier_id_seq; Type: SEQUENCE SET; Schema: public; Owner: user
--

SELECT pg_catalog.setval('public.etat_panier_id_seq', 3, true);


--
-- Name: etat_vente_id_seq; Type: SEQUENCE SET; Schema: public; Owner: user
--

SELECT pg_catalog.setval('public.etat_vente_id_seq', 12, true);


--
-- Name: factures_id_facture_seq; Type: SEQUENCE SET; Schema: public; Owner: user
--

SELECT pg_catalog.setval('public.factures_id_facture_seq', 1, true);


--
-- Name: fournisseurs_id_seq; Type: SEQUENCE SET; Schema: public; Owner: user
--

SELECT pg_catalog.setval('public.fournisseurs_id_seq', 1, false);


--
-- Name: lignes_achats_id_seq; Type: SEQUENCE SET; Schema: public; Owner: user
--

SELECT pg_catalog.setval('public.lignes_achats_id_seq', 87, true);


--
-- Name: lignes_ventes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: user
--

SELECT pg_catalog.setval('public.lignes_ventes_id_seq', 79, true);


--
-- Name: mouvement_stock_id_seq; Type: SEQUENCE SET; Schema: public; Owner: user
--

SELECT pg_catalog.setval('public.mouvement_stock_id_seq', 233, true);


--
-- Name: paiements_id_paiement_seq; Type: SEQUENCE SET; Schema: public; Owner: user
--

SELECT pg_catalog.setval('public.paiements_id_paiement_seq', 1, false);


--
-- Name: panier_id_seq; Type: SEQUENCE SET; Schema: public; Owner: user
--

SELECT pg_catalog.setval('public.panier_id_seq', 1, true);


--
-- Name: panier_produit_id_seq; Type: SEQUENCE SET; Schema: public; Owner: user
--

SELECT pg_catalog.setval('public.panier_produit_id_seq', 103, true);


--
-- Name: produits_id_seq; Type: SEQUENCE SET; Schema: public; Owner: user
--

SELECT pg_catalog.setval('public.produits_id_seq', 71, true);


--
-- Name: retours_id_seq; Type: SEQUENCE SET; Schema: public; Owner: user
--

SELECT pg_catalog.setval('public.retours_id_seq', 1, false);


--
-- Name: roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: user
--

SELECT pg_catalog.setval('public.roles_id_seq', 7, true);


--
-- Name: types_mouvement_stock_id_seq; Type: SEQUENCE SET; Schema: public; Owner: user
--

SELECT pg_catalog.setval('public.types_mouvement_stock_id_seq', 10, true);


--
-- Name: ventes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: user
--

SELECT pg_catalog.setval('public.ventes_id_seq', 51, true);


--
-- Name: achats achats_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.achats
    ADD CONSTRAINT achats_pkey PRIMARY KEY (id_achat);


--
-- Name: authority authority_pk; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.authority
    ADD CONSTRAINT authority_pk PRIMARY KEY (id);


--
-- Name: authority authority_pk_2; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.authority
    ADD CONSTRAINT authority_pk_2 UNIQUE (nom);


--
-- Name: categorie categorie_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.categorie
    ADD CONSTRAINT categorie_pkey PRIMARY KEY (categorie_id);


--
-- Name: clients clients_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.clients
    ADD CONSTRAINT clients_pkey PRIMARY KEY (id_client);


--
-- Name: employes employes_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.employes
    ADD CONSTRAINT employes_pkey PRIMARY KEY (id_employe);


--
-- Name: etat_facture etat_facture_libelle_key; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.etat_facture
    ADD CONSTRAINT etat_facture_libelle_key UNIQUE (libelle);


--
-- Name: etat_facture etat_facture_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.etat_facture
    ADD CONSTRAINT etat_facture_pkey PRIMARY KEY (id);


--
-- Name: etat_paiement etat_paiement_nom_key; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.etat_paiement
    ADD CONSTRAINT etat_paiement_nom_key UNIQUE (libelle);


--
-- Name: etat_paiement etat_paiement_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.etat_paiement
    ADD CONSTRAINT etat_paiement_pkey PRIMARY KEY (id);


--
-- Name: etat_panier etat_panier_libelle_key; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.etat_panier
    ADD CONSTRAINT etat_panier_libelle_key UNIQUE (libelle);


--
-- Name: etat_panier etat_panier_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.etat_panier
    ADD CONSTRAINT etat_panier_pkey PRIMARY KEY (id);


--
-- Name: etat etat_pk; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.etat
    ADD CONSTRAINT etat_pk PRIMARY KEY (id);


--
-- Name: etat_vente etat_vente_libelle_key; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.etat_vente
    ADD CONSTRAINT etat_vente_libelle_key UNIQUE (libelle);


--
-- Name: etat_vente etat_vente_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.etat_vente
    ADD CONSTRAINT etat_vente_pkey PRIMARY KEY (id);


--
-- Name: factures factures_numero_facture_key; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.factures
    ADD CONSTRAINT factures_numero_facture_key UNIQUE (numero_facture);


--
-- Name: factures factures_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.factures
    ADD CONSTRAINT factures_pkey PRIMARY KEY (id_facture);


--
-- Name: fournisseurs fournisseurs_email_key; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.fournisseurs
    ADD CONSTRAINT fournisseurs_email_key UNIQUE (email);


--
-- Name: fournisseurs fournisseurs_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.fournisseurs
    ADD CONSTRAINT fournisseurs_pkey PRIMARY KEY (id);


--
-- Name: lignes_achats lignes_achats_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.lignes_achats
    ADD CONSTRAINT lignes_achats_pkey PRIMARY KEY (id_lignes_achat);


--
-- Name: lignes_ventes lignes_ventes_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.lignes_ventes
    ADD CONSTRAINT lignes_ventes_pkey PRIMARY KEY (id_lignes_ventes);


--
-- Name: mouvement_stock mouvement_stock_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.mouvement_stock
    ADD CONSTRAINT mouvement_stock_pkey PRIMARY KEY (id);


--
-- Name: paiements paiements_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.paiements
    ADD CONSTRAINT paiements_pkey PRIMARY KEY (id_paiement);


--
-- Name: panier panier_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.panier
    ADD CONSTRAINT panier_pkey PRIMARY KEY (id);


--
-- Name: panier_produit panier_produit_id_panier_id_produit_key; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.panier_produit
    ADD CONSTRAINT panier_produit_id_panier_id_produit_key UNIQUE (id_panier, id_produit);


--
-- Name: panier_produit panier_produit_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.panier_produit
    ADD CONSTRAINT panier_produit_pkey PRIMARY KEY (id);


--
-- Name: produits produits_pk; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.produits
    ADD CONSTRAINT produits_pk UNIQUE (ean13);


--
-- Name: produits produits_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.produits
    ADD CONSTRAINT produits_pkey PRIMARY KEY (id_produit);


--
-- Name: produits produits_reference_key; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.produits
    ADD CONSTRAINT produits_reference_key UNIQUE (reference);


--
-- Name: retours retours_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.retours
    ADD CONSTRAINT retours_pkey PRIMARY KEY (id_retour);


--
-- Name: role_authority role_authority_pk; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.role_authority
    ADD CONSTRAINT role_authority_pk PRIMARY KEY (id_authority, id_role);


--
-- Name: role_employe role_employe_pk; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.role_employe
    ADD CONSTRAINT role_employe_pk PRIMARY KEY (id_role, id_employe);


--
-- Name: roles roles_pk; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pk PRIMARY KEY (id_role);


--
-- Name: types_mouvement_stock types_mouvement_stock_code_key; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.types_mouvement_stock
    ADD CONSTRAINT types_mouvement_stock_code_key UNIQUE (code);


--
-- Name: types_mouvement_stock types_mouvement_stock_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.types_mouvement_stock
    ADD CONSTRAINT types_mouvement_stock_pkey PRIMARY KEY (id);


--
-- Name: clients unique_telephone; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.clients
    ADD CONSTRAINT unique_telephone UNIQUE (telephone);


--
-- Name: employes_roles utilisateurs_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.employes_roles
    ADD CONSTRAINT utilisateurs_roles_pkey PRIMARY KEY (id_utilisateur, id_role);


--
-- Name: ventes ventes_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.ventes
    ADD CONSTRAINT ventes_pkey PRIMARY KEY (id_ventes);


--
-- Name: achats achats_employe_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.achats
    ADD CONSTRAINT achats_employe_id_fkey FOREIGN KEY (employe_id) REFERENCES public.employes(id_employe) ON DELETE SET NULL;


--
-- Name: factures factures_etat_facture_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.factures
    ADD CONSTRAINT factures_etat_facture_id_fk FOREIGN KEY (etat_id) REFERENCES public.etat_facture(id);


--
-- Name: factures factures_vente_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.factures
    ADD CONSTRAINT factures_vente_id_fkey FOREIGN KEY (vente_id) REFERENCES public.ventes(id_ventes) ON DELETE CASCADE;


--
-- Name: mouvement_stock fk_type_mouvement; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.mouvement_stock
    ADD CONSTRAINT fk_type_mouvement FOREIGN KEY (type_mouvement_id) REFERENCES public.types_mouvement_stock(id) ON DELETE CASCADE;


--
-- Name: lignes_achats lignes_achats_achats_id_achat_fk; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.lignes_achats
    ADD CONSTRAINT lignes_achats_achats_id_achat_fk FOREIGN KEY (achat_id) REFERENCES public.achats(id_achat);


--
-- Name: lignes_achats lignes_achats_produits_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.lignes_achats
    ADD CONSTRAINT lignes_achats_produits_id_fk FOREIGN KEY (produit_id) REFERENCES public.produits(id_produit);


--
-- Name: lignes_ventes lignes_ventes_produit_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.lignes_ventes
    ADD CONSTRAINT lignes_ventes_produit_id_fkey FOREIGN KEY (produit_id) REFERENCES public.produits(id_produit) ON DELETE CASCADE;


--
-- Name: lignes_ventes lignes_ventes_vente_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.lignes_ventes
    ADD CONSTRAINT lignes_ventes_vente_id_fkey FOREIGN KEY (vente_id) REFERENCES public.ventes(id_ventes) ON DELETE CASCADE;


--
-- Name: mouvement_stock mouvement_stock_produits_id_produit_fk; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.mouvement_stock
    ADD CONSTRAINT mouvement_stock_produits_id_produit_fk FOREIGN KEY (produit_id) REFERENCES public.produits(id_produit);


--
-- Name: paiements paiements_etat_paiement_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.paiements
    ADD CONSTRAINT paiements_etat_paiement_id_fk FOREIGN KEY (etat_id) REFERENCES public.etat_paiement(id);


--
-- Name: paiements paiements_facture_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.paiements
    ADD CONSTRAINT paiements_facture_id_fkey FOREIGN KEY (facture_id) REFERENCES public.factures(id_facture) ON DELETE CASCADE;


--
-- Name: panier panier_etat_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.panier
    ADD CONSTRAINT panier_etat_id_fk FOREIGN KEY (id_etat) REFERENCES public.etat(id);


--
-- Name: panier panier_id_employe_fkey; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.panier
    ADD CONSTRAINT panier_id_employe_fkey FOREIGN KEY (id_employe) REFERENCES public.employes(id_employe) ON DELETE CASCADE;


--
-- Name: panier_produit panier_produit_id_panier_fkey; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.panier_produit
    ADD CONSTRAINT panier_produit_id_panier_fkey FOREIGN KEY (id_panier) REFERENCES public.panier(id) ON DELETE CASCADE;


--
-- Name: panier_produit panier_produit_id_produit_fkey; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.panier_produit
    ADD CONSTRAINT panier_produit_id_produit_fkey FOREIGN KEY (id_produit) REFERENCES public.produits(id_produit) ON DELETE CASCADE;


--
-- Name: produits produits_categorie_categorie-id_fk; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.produits
    ADD CONSTRAINT "produits_categorie_categorie-id_fk" FOREIGN KEY (categorie_id) REFERENCES public.categorie(categorie_id);


--
-- Name: retours retours_produit_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.retours
    ADD CONSTRAINT retours_produit_id_fkey FOREIGN KEY (produit_id) REFERENCES public.produits(id_produit) ON DELETE CASCADE;


--
-- Name: retours retours_vente_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.retours
    ADD CONSTRAINT retours_vente_id_fkey FOREIGN KEY (vente_id) REFERENCES public.ventes(id_ventes) ON DELETE CASCADE;


--
-- Name: role_authority role_authority_authority_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.role_authority
    ADD CONSTRAINT role_authority_authority_id_fk FOREIGN KEY (id_authority) REFERENCES public.authority(id);


--
-- Name: role_authority role_authority_roles_id_role_fk; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.role_authority
    ADD CONSTRAINT role_authority_roles_id_role_fk FOREIGN KEY (id_role) REFERENCES public.roles(id_role);


--
-- Name: role_employe role_employe_id_employe_fk; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.role_employe
    ADD CONSTRAINT role_employe_id_employe_fk FOREIGN KEY (id_employe) REFERENCES public.employes(id_employe);


--
-- Name: role_employe role_employe_roles_id_role_fk; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.role_employe
    ADD CONSTRAINT role_employe_roles_id_role_fk FOREIGN KEY (id_role) REFERENCES public.roles(id_role);


--
-- Name: employes_roles utilisateurs_roles_id_utilisateur_fkey; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.employes_roles
    ADD CONSTRAINT utilisateurs_roles_id_utilisateur_fkey FOREIGN KEY (id_utilisateur) REFERENCES public.employes(id_employe) ON DELETE CASCADE;


--
-- Name: ventes ventes_client_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.ventes
    ADD CONSTRAINT ventes_client_id_fkey FOREIGN KEY (client_id) REFERENCES public.clients(id_client) ON DELETE SET NULL;


--
-- Name: ventes ventes_employes_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.ventes
    ADD CONSTRAINT ventes_employes_id_fk FOREIGN KEY (employe_id) REFERENCES public.employes(id_employe);


--
-- Name: ventes ventes_etat_vente_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.ventes
    ADD CONSTRAINT ventes_etat_vente_id_fk FOREIGN KEY (etat_id) REFERENCES public.etat_vente(id);


--
-- PostgreSQL database dump complete
--

