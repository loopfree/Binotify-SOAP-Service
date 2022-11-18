--
-- PostgreSQL database dump
--

-- Dumped from database version 15.0
-- Dumped by pg_dump version 15.0

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: sub_status; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.sub_status AS ENUM (
    'PENDING',
    'ACCEPTED',
    'REJECTED'
);


ALTER TYPE public.sub_status OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: Logging; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Logging" (
    id integer NOT NULL,
    description character(256) NOT NULL,
    ip character(16) NOT NULL,
    endpoint character(256) NOT NULL,
    requested_at timestamp without time zone NOT NULL
);


ALTER TABLE public."Logging" OWNER TO postgres;

--
-- Name: Logging_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Logging_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Logging_id_seq" OWNER TO postgres;

--
-- Name: Logging_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Logging_id_seq" OWNED BY public."Logging".id;


--
-- Name: Subscription; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Subscription" (
    creator_id integer NOT NULL,
    subscriber_id integer NOT NULL,
    status public.sub_status DEFAULT 'PENDING'::public.sub_status NOT NULL
);


ALTER TABLE public."Subscription" OWNER TO postgres;

--
-- Name: Logging id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Logging" ALTER COLUMN id SET DEFAULT nextval('public."Logging_id_seq"'::regclass);


--
-- Data for Name: Logging; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Logging" (id, description, ip, endpoint, requested_at) FROM stdin;
\.


--
-- Data for Name: Subscription; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Subscription" (creator_id, subscriber_id, status) FROM stdin;
\.


--
-- Name: Logging_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Logging_id_seq"', 1, false);


--
-- Name: Logging Logging_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Logging"
    ADD CONSTRAINT "Logging_pkey" PRIMARY KEY (id);


--
-- Name: Subscription Subscription_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Subscription"
    ADD CONSTRAINT "Subscription_pkey" PRIMARY KEY (creator_id, subscriber_id);


--
-- PostgreSQL database dump complete
--

