create table int_cell3g_traffic_t (
rcid varchar(12),
rfid int,
ucid varchar(12),
ts timestamp,
converl_2667 double precision, 
trmbyte_hsdpa double precision,
trmbyte_hsupa double precision,
cqi_av double precision,
constraint int_cell3g_traffic_t_pk primary key (rcid,rfid,ucid,ts)
);
