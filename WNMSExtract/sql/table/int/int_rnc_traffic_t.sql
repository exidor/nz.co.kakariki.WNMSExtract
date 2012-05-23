create table int_rnc_traffic_t (
id varchar(12),
ts timestamp,
converl_2667 double precision, 
trmbyte_hsdpa double precision,
trmbyte_hsupa double precision,
cqi_av double precision,
constraint int_rnc_traffic_t_pk primary key (id,ts)
);
