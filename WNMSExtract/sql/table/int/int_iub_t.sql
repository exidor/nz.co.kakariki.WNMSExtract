create table int_iub_t (
  id varchar(5) NOT NULL,
  ts timestamp without time zone NOT NULL,
  eth_dl_tt double precision,
  eth_ul_tt double precision,
  pcm_nb integer,
  ima_dl_av double precision,
  ima_ul_av double precision,
  ima_dl_mx double precision,
  ima_ul_mx double precision,
  constraint int_iub_t_pkey primary key (id, ts)
);