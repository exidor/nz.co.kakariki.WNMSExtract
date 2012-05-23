create table int_nodeb_t(
id varchar(6),
ts timestamp,
cpu_load_ccm_mx int,
cpu_load_cem_mx int,
imagroup_dl_av double precision,
imagroup_ul_av double precision,
imagroup_dl_mx double precision,
imagroup_ul_mx double precision,
constraint int_nodeb_t_pk primary key (id,ts)
);