create table report_rnc_apg_t (
id varchar(12),
ap varchar(4),
wk timestamp,
occ double precision,
constraint report_rnc_apg_t_pk primary key (id,ap,wk)
);