--
-- PostgreSQL database dump
--

-- Dumped from database version 12.6 (Ubuntu 12.6-0ubuntu0.20.04.1)
-- Dumped by pg_dump version 12.6 (Ubuntu 12.6-0ubuntu0.20.04.1)

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: categoria; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.categoria (
    id_categoria integer NOT NULL,
    nombre_categoria character varying(20) NOT NULL,
    descripcion_categoria character varying(250) NOT NULL
);


ALTER TABLE public.categoria OWNER TO admin;

--
-- Name: categoria_id_categoria_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.categoria_id_categoria_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.categoria_id_categoria_seq OWNER TO admin;

--
-- Name: categoria_id_categoria_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE public.categoria_id_categoria_seq OWNED BY public.categoria.id_categoria;


--
-- Name: detalle_pedido; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.detalle_pedido (
    id_pedido integer NOT NULL,
    id_producto integer NOT NULL,
    cantidad numeric(2,0) NOT NULL,
    devuelto boolean DEFAULT false NOT NULL
);


ALTER TABLE public.detalle_pedido OWNER TO admin;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO admin;

--
-- Name: lista_deseos; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.lista_deseos (
    id_usuario integer NOT NULL,
    id_producto integer NOT NULL
);


ALTER TABLE public.lista_deseos OWNER TO admin;

--
-- Name: pedido; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.pedido (
    id_pedido integer NOT NULL,
    comprado boolean DEFAULT false NOT NULL,
    fecha character varying(10) NOT NULL,
    id_usuario integer NOT NULL
);


ALTER TABLE public.pedido OWNER TO admin;

--
-- Name: pedido_id_pedido_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.pedido_id_pedido_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.pedido_id_pedido_seq OWNER TO admin;

--
-- Name: pedido_id_pedido_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE public.pedido_id_pedido_seq OWNED BY public.pedido.id_pedido;


--
-- Name: producto; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.producto (
    id_producto integer NOT NULL,
    nombre_producto character varying(25) NOT NULL,
    descripcion_producto character varying(250) NOT NULL,
    precio numeric(4,2) NOT NULL,
    stock numeric(3,0) NOT NULL,
    imagen character varying(100) NOT NULL,
    id_categoria integer NOT NULL,
    descripcion character varying(255)
);


ALTER TABLE public.producto OWNER TO admin;

--
-- Name: producto_id_producto_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.producto_id_producto_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.producto_id_producto_seq OWNER TO admin;

--
-- Name: producto_id_producto_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE public.producto_id_producto_seq OWNED BY public.producto.id_producto;


--
-- Name: usuario; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.usuario (
    id_usuario integer NOT NULL,
    email character varying(50) NOT NULL,
    password character varying(50) NOT NULL,
    nombre character varying(25) NOT NULL,
    apellidos character varying(50) NOT NULL,
    direccion character varying(250) NOT NULL,
    admin boolean DEFAULT false NOT NULL
);


ALTER TABLE public.usuario OWNER TO admin;

--
-- Name: usuario_id_usuario_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.usuario_id_usuario_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.usuario_id_usuario_seq OWNER TO admin;

--
-- Name: usuario_id_usuario_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE public.usuario_id_usuario_seq OWNED BY public.usuario.id_usuario;


--
-- Name: valoracion; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.valoracion (
    comentario character varying(300) NOT NULL,
    puntuacion numeric(1,0) NOT NULL,
    id_usuario integer NOT NULL,
    id_producto integer NOT NULL
);


ALTER TABLE public.valoracion OWNER TO admin;

--
-- Name: categoria id_categoria; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.categoria ALTER COLUMN id_categoria SET DEFAULT nextval('public.categoria_id_categoria_seq'::regclass);


--
-- Name: pedido id_pedido; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.pedido ALTER COLUMN id_pedido SET DEFAULT nextval('public.pedido_id_pedido_seq'::regclass);


--
-- Name: producto id_producto; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.producto ALTER COLUMN id_producto SET DEFAULT nextval('public.producto_id_producto_seq'::regclass);


--
-- Name: usuario id_usuario; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.usuario ALTER COLUMN id_usuario SET DEFAULT nextval('public.usuario_id_usuario_seq'::regclass);


--
-- Data for Name: categoria; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.categoria (id_categoria, nombre_categoria, descripcion_categoria) FROM stdin;
1	Deportes	Un videojuego de deportes es un videojuego de consola o de computadora que simula el campo de deportes tradicionales. Estos videojuegos son sumamente populares, el género incluye algunos de los videojuegos con más éxito de venta.
2	Shooters	Los videojuegos de disparos, tiros o shooters conforman un género que engloba muchos subgéneros basados en la acción
19	Estrategia	Consisten en trazar una estrategia para superar al contrincante. Exigen concentración, saber administrar recursos, pensar y definir estrategias.
22	Zombies	Juegos de temática apocalíptica orientado a la supervivencia ante personas infectadas o casi muertas
23	Juegos de mesa	Habilidad, preguntas y respuestas…La tecnología informática que sustituye al material tradicional del juego y hasta al adversario.
20	Simulación	Aviones, simuladores de una situación o instrumentales… Permiten experimentar e investigar el funcionamiento de máquinas, fenómenos, situaciones y asumir el mando.
\.


--
-- Data for Name: detalle_pedido; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.detalle_pedido (id_pedido, id_producto, cantidad, devuelto) FROM stdin;
4	1	1	f
6	1	1	f
7	1	1	f
8	1	2	f
9	1	16	f
10	1	2	f
11	2	3	f
12	3	1	f
17	14	1	f
17	16	1	f
5	3	2	t
5	2	1	t
7	2	1	t
27	14	1	f
28	18	1	f
29	13	1	f
30	16	1	f
30	13	1	f
31	18	1	f
31	2	1	f
32	1	1	f
33	14	1	f
33	18	1	f
34	21	1	f
34	13	1	f
35	16	1	f
35	14	1	f
36	2	1	f
36	14	1	f
37	2	1	f
37	1	1	f
38	14	1	f
39	1	1	f
40	16	1	f
40	18	1	f
41	14	1	f
41	13	1	f
42	18	1	f
42	14	1	f
42	13	1	f
43	16	1	f
43	3	1	f
44	21	1	f
44	14	1	f
44	18	1	f
45	1	1	f
45	16	1	f
45	14	1	f
46	14	1	f
46	16	1	f
46	13	1	f
47	13	1	f
47	14	1	f
49	13	1	f
49	14	1	f
50	48	1	f
50	1	1	f
54	51	1	f
55	53	1	f
55	16	1	f
55	1	1	f
55	14	1	f
55	51	2	f
55	2	1	t
56	16	1	f
\.


--
-- Data for Name: lista_deseos; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.lista_deseos (id_usuario, id_producto) FROM stdin;
1	3
2	2
2	3
6	2
6	3
6	1
1	2
10	1
3	2
3	1
3	16
\.


--
-- Data for Name: pedido; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.pedido (id_pedido, comprado, fecha, id_usuario) FROM stdin;
4	f	20-05-2021	6
5	t	24-05-2021	3
6	t	24-05-2021	3
7	t	28-05-2021	3
8	t	28-05-2021	3
9	t	28-05-2021	3
10	t	28-05-2021	3
11	t	28-05-2021	3
12	t	28-05-2021	3
17	t	29-05-2021	3
27	t	31-05-2021	3
28	t	31-05-2021	3
29	t	31-05-2021	4
30	t	31-05-2021	3
31	t	31-05-2021	3
32	t	31-05-2021	3
33	t	31-05-2021	3
34	t	31-05-2021	3
35	t	31-05-2021	3
36	t	31-05-2021	3
37	t	31-05-2021	3
38	t	31-05-2021	3
39	t	31-05-2021	3
40	t	31-05-2021	3
41	t	31-05-2021	3
42	t	31-05-2021	3
43	t	31-05-2021	3
44	t	31-05-2021	3
45	t	31-05-2021	3
46	t	31-05-2021	3
47	t	31-05-2021	3
49	t	06-06-2021	3
50	t	06-06-2021	3
54	t	13-06-2021	3
55	t	16-06-2021	3
56	f	16-06-2021	3
\.


--
-- Data for Name: producto; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.producto (id_producto, nombre_producto, descripcion_producto, precio, stock, imagen, id_categoria, descripcion) FROM stdin;
48	Immortals Fenyx Rising	Ponte en la piel de Fenyx y salva a los dioses griegos de una tenebrosa maldición. Enfréntate a bestias mitológicas y domina los poderes legendarios.	15.00	11	Immortals.jpg	19	\N
52	Assassins Creed Valhalla	Los asesinatos de Valhalla se centra en la historia de Arnar, un perfilador criminal de la policía de Oslo que es enviado de vuelta a casa.	70.00	45	ACValhalla.jpg	19	\N
3	The Binding Of Isaac	Es un remake del primer The Binding of Isaac que ahora llega a PC. Diseñado por el creador original, Edmund McMillen y desarrollado por Nicalis junto a su equipo.	40.00	28	Isaac.jpg	2	\N
21	Euro Truck Simulator 2	Es la esperadísima segunda parte del popular simulador de conducción de vehículos pesados, considerado por muchos como el mejor simulador que existe de este tipo.	20.00	16	eurotruck.jpg	20	\N
18	Mortal Kombat 11	Mortal Kombat 11 seguirá con su mitología y extenso universo, En esta ocasión se continuará tras la derrota de Shinnok, ofreciéndonos un corrompido Raiden que amenazará a todos.	60.00	48	MortalKombat.jpg	1	\N
51	Dying Light	Dying Light es un juego con un amplio y peligroso mundo abierto ubicado en la ciudad ficticia de Harran.	24.00	18	DyingLight.jpg	22	\N
53	Death Stranding	La trama de Death Stranding transcurre en un mundo postapocalíptico en el que un evento conocido como "Death Stranding" fusionó el mundo de los vivos y el de los muertos.	58.00	55	DeathStranding.jpg	19	\N
16	Horizon Zero Dawn	Horizon Zero Dawn se proyecta en el planeta Tierra en el año 3040, en un escenario post-apocalíptico donde los seres humanos han vuelto a la época primitiva como resultado de una catástrofe desconocida.	75.50	37	Horizon.jpg	2	\N
1	FIFA 19	Es un videojuego de simulación de fútbol desarrollado por EA, como parte de la serie FIFA de Electronic Arts. Juega con los mejores futbolistas del mundo	45.50	19	FIFA22.jpg	1	\N
14	Cyberpunk 2077	Cyberpunk 2077 es una historia de acción y aventura en mundo abierto ambientada en Night City, una megalópolis obsesionada con el poder. Tu personaje es V, un mercenario que persigue un implante único que permite alcanzar la inmortalidad.	50.00	73	cyberpunk.jpg	2	\N
2	Red Dead Redemption 2	Arthur Morgan y su banda son forajidos en busca y captura. Mientras los agentes federales y los mejores cazarrecompensas de la nación les pisan los talones.	65.50	13	RedDead2.jpg	2	\N
57	Kingdom Hearts III	Donald y Goofy, dos emisarios enviados por el Rey Mickey desde el Castillo de Disney, se unen a Sora para impedir que una fuerza maligna conocida como los Sincorazón invada el universo	60.00	12	Kingdomhearts3.jpg	19	\N
13	Days Gone	Days Gone nos lleva a un futuro apocalíptico dominado por zombies, o más bien, que no son sino humanos infectados por un virus que los ha convertido en criaturas salvajes y hambrientas.	40.00	57	DaysGone.jpg	2	\N
58	WWE 2K15	WWE 2K15 es un juego de simulación de lucha que da comienzo a una nueva era de videojuegos de la WWE!	15.00	20	WWE.jpg	20	\N
\.


--
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.usuario (id_usuario, email, password, nombre, apellidos, direccion, admin) FROM stdin;
1	alejandro@gmail.com	alejandro	Alejandro	Sanchez Garcia	Naranjas y Limones	f
2	domingo@gmail.com	domingo	Domingo	Garrido	Huescalon	f
4	esperanza.morillo9@gmail.com	espe1971	Esperanza	Morillo	Calle Fuenteventura, 12	f
6	yerayroberto@gmail.com	yeraytioner	Yeray	Roberto	Calle Padre Pío, 71	f
7	robertpuiu@gmail.com	robertcapucha	Robert	Puiu	Calle Atlantico, 23	f
8	adrianmartin@gmail.com	robeimposstor	Adrián	Martin	Calle La Piedra, 32	f
9	josemariarivas@gmail.com	chemote	Jose Maria	Rivas	Calle Puerto Perico	f
10	esperanza.morillo8@gmail.com	estrellita	Esperanzita	Baco	Calle Estrella Nueva	f
15	anonimo@gmail.com	asdfg	Anonimo	anonimus	Calle la anonima	t
16	yeye@gmail.com	qwerty	Yeye	Baco	Calle Paris	t
3	m.a.bacomorillo@gmail.com	m.22011999	Miguel Ángel	Baco Morillo	La Palma, 28	t
\.


--
-- Data for Name: valoracion; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.valoracion (comentario, puntuacion, id_usuario, id_producto) FROM stdin;
Este juego no me ha gustado nada	1	2	2
No esta nada mal	4	1	1
Pesaos callarse ya	2	1	2
Me gusta más la nba	2	2	1
A mi también me gusta la nba pero bueno este esta muy bien	4	3	1
Ya no me gusta tanto	3	3	3
Me sigue gustando	4	3	2
Un juego de camiones que guapo jja	3	3	21
Este juego me gusta bastante	4	3	16
Me gusto mucho al principio pero ya estoy un poco cansado	3	3	18
Este juego esta lleno de bugs, no entiendo porque lo han hecho tan mal	2	3	14
Lo estoy jugando y tiene una pintaza	4	3	51
\.


--
-- Name: categoria_id_categoria_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.categoria_id_categoria_seq', 2, true);


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.hibernate_sequence', 58, true);


--
-- Name: pedido_id_pedido_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.pedido_id_pedido_seq', 4, true);


--
-- Name: producto_id_producto_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.producto_id_producto_seq', 3, true);


--
-- Name: usuario_id_usuario_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.usuario_id_usuario_seq', 16, true);


--
-- Name: categoria pk_categoria; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.categoria
    ADD CONSTRAINT pk_categoria PRIMARY KEY (id_categoria);


--
-- Name: detalle_pedido pk_detalle_pedido; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.detalle_pedido
    ADD CONSTRAINT pk_detalle_pedido PRIMARY KEY (id_pedido, id_producto);


--
-- Name: lista_deseos pk_lista_deseos; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.lista_deseos
    ADD CONSTRAINT pk_lista_deseos PRIMARY KEY (id_usuario, id_producto);


--
-- Name: pedido pk_pedido; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.pedido
    ADD CONSTRAINT pk_pedido PRIMARY KEY (id_pedido);


--
-- Name: producto pk_producto; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.producto
    ADD CONSTRAINT pk_producto PRIMARY KEY (id_producto);


--
-- Name: usuario pk_usuario; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT pk_usuario PRIMARY KEY (id_usuario);


--
-- Name: valoracion pk_valoracion; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.valoracion
    ADD CONSTRAINT pk_valoracion PRIMARY KEY (id_usuario, id_producto);


--
-- Name: detalle_pedido detalle_pedido_id_pedido_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.detalle_pedido
    ADD CONSTRAINT detalle_pedido_id_pedido_fkey FOREIGN KEY (id_pedido) REFERENCES public.pedido(id_pedido);


--
-- Name: detalle_pedido detalle_pedido_id_producto_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.detalle_pedido
    ADD CONSTRAINT detalle_pedido_id_producto_fkey FOREIGN KEY (id_producto) REFERENCES public.producto(id_producto);


--
-- Name: lista_deseos lista_deseos_id_producto_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.lista_deseos
    ADD CONSTRAINT lista_deseos_id_producto_fkey FOREIGN KEY (id_producto) REFERENCES public.producto(id_producto);


--
-- Name: lista_deseos lista_deseos_id_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.lista_deseos
    ADD CONSTRAINT lista_deseos_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES public.usuario(id_usuario);


--
-- Name: pedido pedido_id_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.pedido
    ADD CONSTRAINT pedido_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES public.usuario(id_usuario);


--
-- Name: producto producto_id_categoria_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.producto
    ADD CONSTRAINT producto_id_categoria_fkey FOREIGN KEY (id_categoria) REFERENCES public.categoria(id_categoria);


--
-- Name: valoracion valoracion_id_producto_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.valoracion
    ADD CONSTRAINT valoracion_id_producto_fkey FOREIGN KEY (id_producto) REFERENCES public.producto(id_producto);


--
-- Name: valoracion valoracion_id_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.valoracion
    ADD CONSTRAINT valoracion_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES public.usuario(id_usuario);


--
-- PostgreSQL database dump complete
--

